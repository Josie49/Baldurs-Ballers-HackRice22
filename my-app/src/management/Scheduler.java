package management;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Point;

import database.item.Employee;
import database.item.Job;

public class Scheduler {

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


    public Scheduler() {

    }



    private Map scheduleAll(ArrayList<Employee> empList, ArrayList<Job> jobList){
        int numEmps = empList.size();
        int[] empDist = new int[numEmps];
        for (int i = 0; i < numEmps; i++){
            empDist[i] = -1;
        }
        for (Job emptyJob : jobList){
            boolean jobScoped = false;
            int distToler = 0;
            while (jobScoped != true){
                distToler++;
                for (int i = 0; i < numEmps; i++){
                    Employee curEmp = empList.get(i);
                    if (curEmp.getSkills().containsAll(emptyJob.getSkills()) && radialDist() <= distToler){
                        Schedule curSched = curEmp.getSchedule();
                        ArrayList<Point> curFree = scheduleParse(curSched);
                        for (Point block : curFree){
                            if (block.y - block.x >= emptyJob.getLength()){
                                boolean[] curTable = curSched.getTable();
                                for (int j = block.y; j > block.x; j--){
                                    curTable[j] = true;
                                }
                                emptyJob.setAssignedEmployee(empList.get(i));
                                jobScoped = true;
                                break;
                            }
                        }
                    }
                }
                if (distToler > 100){
                    jobScoped = true;
                }
            }
        }
        
        return null;
    }

    private double radialDist(){
        
    }

    private ArrayList<Point> scheduleParse(Schedule schedule){
        boolean[] curTab = schedule.getTable();
        int curStreak = 0;
        ArrayList<Point> blocks = new ArrayList<>();
        for (int i = 0; i < 24; i++){
            if (i == 23 && curStreak > 0){
                blocks.add(new Point(i-curStreak, i));
                return blocks;
            }
            else if (curTab[i] == false){
                curStreak++;
            }
            else if (curTab[i] == true && curStreak > 0){
                blocks.add(new Point(i-curStreak, i));
            }
        }
        return blocks;
    }

    
}
