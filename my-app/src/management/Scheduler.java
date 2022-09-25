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

                    if (empList.get(i).getSkills().containsAll(emptyJob.getSkills()) && radialDist() <= distToler){
                        emptyJob.setAssignedEmployee(empList.get(i));
                        jobScoped = true;
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
        for
    }

    
}
