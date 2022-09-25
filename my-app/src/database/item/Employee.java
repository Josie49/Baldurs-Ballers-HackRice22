package database.item;

import java.util.HashSet;

public class Employee {
    private static long counter = 0;

    private long employeeID;
    private int shiftStart;
    private int shiftEnd;
    private String phoneNumber;
    private Location startingLocation;
    private Schedule schedule;
    private HashSet<Skills> skills;

    /**
     * A constructor for an employee.
     *
     * @param shiftStart the hour that their shift starts (starting at 0)
     * @param shiftEnd the hour that their shift ends
     * @param phoneNumber their phone number
     * @param startingLocation their base location
     * @param skills their skills
     */
    public Employee(int shiftStart, int shiftEnd, String phoneNumber, Location startingLocation, HashSet<Skills> skills) {
        this.employeeID = counter;
        counter++;

        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.phoneNumber = phoneNumber;
        this.startingLocation = startingLocation;
        this.skills = skills;

        this.schedule = new Schedule();
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public int getShiftStart() {
        return shiftStart;
    }

    public int getShiftEnd() {
        return shiftEnd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
