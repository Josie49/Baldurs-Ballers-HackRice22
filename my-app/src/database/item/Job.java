package database.item;

import java.util.HashSet;

public class Job {
    private static long counter = 0;

    private long jobID;
    private int length;
    private Employee assignedEmployee;
    private String details;
    private Location location;
    private HashSet<Skills> skills;

    /**
     * A constructor for a job.
     *
     * @param length the length of the job in hours
     * @param details the details of the job
     * @param location the location of the job
     * @param skills the skills needed for the job
     */
    public Job(int length, String details, Location location, HashSet<Skills> skills) {
        this.jobID = counter;
        counter++;

        this.length = length;
        this.details = details;
        this.location = location;
        this.skills = skills;
    }


    public long getJobID() {
        return jobID;
    }

    public int getLength() {
        return length;
    }

    public void setAssignedEmployee(Employee employee) {
        this.assignedEmployee = employee;
    }

    public String getDetails() {
        return details;
    }

    public Location getLocation() {
        return location;
    }

    public HashSet<Skills> getSkills() {
        return skills;
    }
}
