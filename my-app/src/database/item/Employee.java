package database.item;

import management.Schedule;

import java.util.HashSet;

public class Employee {
    private static long counter = 1;

    private long employeeID;
    private short shiftStart;
    private short shiftEnd;
    private String phoneNumber;
    private Location startingLocation;
    private Schedule schedule;
    private HashSet<Skills> skills;

    /**
     * A constructor for a new employee.
     *
     * @param shiftStart the hour that their shift starts (starting at 0)
     * @param shiftEnd the hour that their shift ends
     * @param phoneNumber their phone number
     * @param startingLocation their base location
     * @param skills their skills
     */
    public Employee(short shiftStart, short shiftEnd, String phoneNumber, Location startingLocation, HashSet<Skills> skills) {
        this.employeeID = counter;
        counter++;

        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.phoneNumber = phoneNumber;
        this.startingLocation = startingLocation;
        this.skills = skills;

        this.setSchedule(new Schedule());
    }

    /**
     * A constructor for an employee with an ID.
     *
     * @param employeeID the employee's ID
     * @param shiftStart the hour that their shift starts (starting at 0)
     * @param shiftEnd the hour that their shift ends
     * @param phoneNumber their phone number
     * @param startingLocation their base location
     * @param skills their skills
     */
    public Employee(long employeeID, short shiftStart, short shiftEnd, String phoneNumber, Location startingLocation, HashSet<Skills> skills) {
        this.employeeID = employeeID;

        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.phoneNumber = phoneNumber;
        this.startingLocation = startingLocation;
        this.skills = skills;

        this.setSchedule(new Schedule());
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public short getShiftStart() {
        return shiftStart;
    }

    public short getShiftEnd() {
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
        if (this.shiftStart < this.shiftEnd) {
            for (int i = this.shiftEnd; i < this.shiftStart + 24; i++) {
                this.schedule.timeTable[i % 24] = -1;
            }
        } else {
            for (int i = this.shiftEnd; i < this.shiftStart; i++) {
                this.schedule.timeTable[i] = -1;
            }
        }

    }

    public HashSet<Skills> getSkills() {
        return skills;
    }
}
