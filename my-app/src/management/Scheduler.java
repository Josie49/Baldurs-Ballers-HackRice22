package management;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.awt.Point;

import database.DatabaseConnection;
import database.item.*;

public class Scheduler {


    private DatabaseConnection toBase;

    // Work order
    // Skills: Array of Strings
    // Location: String
    // Time needed: Integer (in hours)
    // Details: Arbitrary String

    // Employee
    // Phone number: String
    // Skills: Array of strings
    // Availability: 2 separate integers
    // Starting Location: String


    public Scheduler(DatabaseConnection toBase) {
        this.toBase = toBase;
    }



    public AbstractMap.SimpleEntry<ArrayList<Employee>, ArrayList<Job>> scheduleAll(ArrayList<Employee> empList, ArrayList<Job> jobList){
        // Number of employees
        int numEmps = empList.size();
        for (Job emptyJob : jobList){
            // Variable to determine whether current iteration can be safely exited
            boolean jobScoped = false;
            // Acceptable distance between any two jobs, increases to 100km then exits the loop
            int distToler = 0;
            while (jobScoped != true){
                distToler++;
                // Look through all employees to compare aspects to current job
                for (int i = 0; i < numEmps; i++){
                    Employee curEmp = empList.get(i);
                    // First is the skills check, as it is absolutely mandatory and the most selective
                    if (curEmp.getSkills().containsAll(emptyJob.getSkills())){
                        Schedule curSched = curEmp.getSchedule();
                        // Turn schedule into Array of periods of free time
                        ArrayList<Point> curFree = scheduleParse(curSched);
                        for (Point block : curFree){
                            // If available free time fits job description, continue to location comparison
                            if (block.y - block.x >= emptyJob.getLength()){
                                long[] jobSched = curSched.getTable();
                                long jobID = 0;
                                for (int k = block.x; k >= 0; k--){
                                    if (jobSched[k] != 0){
                                        jobID = jobSched[k];
                                        break;
                                    }
                                }
                                // Reverse search to find "current" job and isolate its location if there is a previous job on schedule
                                Location recentJobLocation = null;
                                if (jobID != 0){
                                    for (Job pastJob : jobList){
                                        if (pastJob.getJobID() == jobID){
                                            recentJobLocation = pastJob.getLocation();
                                        }
                                    }
                                }
                                // If this is employee's first job of the day, the recent location is their starting position
                                else {
                                    recentJobLocation = curEmp.getStartingLocation();
                                }
                                // With all conditions met but location, compare "current" location to requested job location
                                if (recentJobLocation.radialDist(emptyJob.getLocation()) <= distToler){
                                    // Modify their existing table to reflect newly allocated job
                                    long[] curTable = curSched.getTable();
                                    for (int j = block.y; j > block.x; j--){
                                            curTable[j] = emptyJob.getJobID();
                                    }
                                    // In case java referencing is weird, affirm that their schedule is actually changed
                                    curEmp.setSchedule(new Schedule(curTable));
                                    // Assign employee to job
                                    emptyJob.setAssignedEmployee(curEmp);
                                    toBase.assignJob(curEmp, emptyJob, block.x);

                                    // Allow loop to move to next job
                                    jobScoped = true;
                                    break;
                                }
                            }
                        }   
                    } 
                }
                if (distToler > 100){
                    jobScoped = true;
                }
            }
        }
        
        return new SimpleEntry<ArrayList<Employee>,ArrayList<Job>>(empList, jobList);
    }


    private ArrayList<Point> scheduleParse(Schedule schedule){
        long[] curTab = schedule.getTable();
        // Represents sequence of free slots
        int curStreak = 0;
        ArrayList<Point> blocks = new ArrayList<>();
        // For all 24 hours in a day
        for (int i = 0; i < 24; i++){
            // If it's the last value and a streak is going, end it
            if (i == 23 && curStreak > 0){
                blocks.add(new Point(i-curStreak, i));
                return blocks;
            }
            // Increment continued streaks
            else if (curTab[i] == 0){
                curStreak++;
            }
            // End and add current streaks
            else if (curTab[i] > 0 && curStreak > 0){
                blocks.add(new Point(i-curStreak, i));
                curStreak = 0;
            }
        }
        return blocks;
    }

    public DatabaseConnection getDataBaseConnection(){
        return this.toBase;
    }

    
}
