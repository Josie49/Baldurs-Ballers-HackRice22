package database;

import database.item.*;
import management.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Phoebe Scaccia
 */
public class DatabaseConnection {

    private Connection connection = null;

    /**
     * Starts up the database connection.
     */
    public DatabaseConnection() {
        String connectionUrl =
            "jdbc:sqlserver://PHOEBE:1433;"
                + "database=HackRice12;"
                + "user=sa;"
                + "password=secret;"
                + "encrypt=true;"
                + "trustServerCertificate=true;";

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("JDBC driver loaded");
        }
        catch (Exception e)
        {
            System.err.println("Error loading JDBC driver");
            e.printStackTrace(System.err);
            System.exit(0);
        }

        try {
            this.connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected to database");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add an employee to the database.
     *
     * @param employee an Employee
     */
    public void addEmployee(Employee employee) {
        Location location = employee.getStartingLocation();
        try {
            PreparedStatement preparedEmployeeStatement = this.connection.prepareStatement(
                "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?)"
            );

            preparedEmployeeStatement.setLong(1, employee.getEmployeeID());
            preparedEmployeeStatement.setString(2, employee.getPhoneNumber());
            preparedEmployeeStatement.setDouble(3, location.getLatitude());
            preparedEmployeeStatement.setDouble(4, location.getLongitude());
            preparedEmployeeStatement.setShort(5, employee.getShiftStart());
            preparedEmployeeStatement.setShort(6, employee.getShiftEnd());

            preparedEmployeeStatement.execute();

            PreparedStatement preparedSkillsStatement = this.connection.prepareStatement(
                "INSERT INTO EMPLOYEE_SKILLS VALUES(?, ?)"
            );

            for (Skills skill : employee.getSkills()) {
                preparedSkillsStatement.setLong(1, employee.getEmployeeID());
                preparedSkillsStatement.setShort(2, (short) skill.ordinal());
                preparedSkillsStatement.execute();
            }

            System.out.println("Added Employee " + employee.getEmployeeID());

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Gets an employee by their ID.
     *
     * @param employeeID an employee ID
     * @return an Employee object containing the employee's information, or null if the employee does not exist.
     */
    public Employee getEmployee(long employeeID) {
        try {

            // Query the database.

            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM EMPLOYEE WHERE employeeID = " + employeeID);
            if (results.next()) {
                Location location =
                    new Location(results.getDouble(3), results.getDouble(4));

                return new Employee(
                    employeeID, results.getShort(5), results.getShort(6),
                    results.getString(2), location, this.getEmployeeSkills(employeeID));
            } else {
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

    /**
     * Gets all the employees in the database.
     *
     * @return a list of Employees
     */
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM EMPLOYEE");
            while (results.next()) {
                Location location =
                    new Location(results.getDouble(3), results.getDouble(4));

                employees.add(
                    new Employee(
                        results.getLong(1), results.getShort(5), results.getShort(6),
                        results.getString(2), location,
                        this.getEmployeeSkills(results.getLong(1))
                    )
                );
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return employees;
    }

    /**
     * Gets the set of all skills that an employee has.
     *
     * @param employeeID the employee's ID
     * @return a set of Skills
     */
    public HashSet<Skills> getEmployeeSkills(long employeeID) {
        HashSet<Skills> skills = new HashSet<>();
        try {
            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM EMPLOYEE_SKILLS WHERE employeeID = " + employeeID);
            while (results.next()) {
                skills.add(Skills.getSkill(results.getInt(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return skills;
    }

    /**
     * Add a job to the database.
     *
     * @param job a Job
     */
    public void addJob(Job job) {
        Location location = job.getLocation();

        try {
            PreparedStatement preparedJobStatement = this.connection.prepareStatement(
                "INSERT INTO JOB VALUES (?, ?, ?, ?, ?, ?)"
            );

            preparedJobStatement.setLong(1, job.getJobID());
            preparedJobStatement.setDouble(2, location.getLatitude());
            preparedJobStatement.setDouble(3, location.getLongitude());
            preparedJobStatement.setShort(4, job.getLength());
            preparedJobStatement.setString(5, job.getDetails());
            preparedJobStatement.setBoolean(6, false);

            preparedJobStatement.execute();

            PreparedStatement preparedSkillsStatement = this.connection.prepareStatement(
                "INSERT INTO JOB_SKILLS VALUES(?, ?)"
            );

            for (Skills skill : job.getSkills()) {
                preparedSkillsStatement.setLong(1, job.getJobID());
                preparedSkillsStatement.setShort(2, (short) skill.ordinal());
                preparedSkillsStatement.execute();
            }

            System.out.println("Added Job " + job.getJobID());

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Gets a job by its ID.
     *
     * @param jobID the job ID
     * @return a Job, or null if it does not exist
     */
    public Job getJob(long jobID) {
        try {

            // Query the database.

            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM JOB WHERE jobID = " + jobID);
            if (results.next()) {
                Location location =
                    new Location(results.getDouble(2), results.getDouble(3));

                return new Job(
                    jobID, results.getShort(4), results.getString(5), location,
                    this.getJobSkills(jobID));
            } else {
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

    /**
     * Gets all the jobs in the database.
     *
     * @return a list of Jobs
     */
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();

        try {
            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM JOB");
            while (results.next()) {
                Location location =
                    new Location(results.getDouble(2), results.getDouble(3));

                jobs.add(
                    new Job(
                        results.getLong(1), results.getShort(4), results.getString(5),
                        location, this.getJobSkills(results.getLong(1))));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return jobs;
    }

    /**
     * Gets all the incomplete jobs in the database.
     *
     * @return a list of Jobs
     */
    public List<Job> getUnfinishedJobs() {
        List<Job> jobs = new ArrayList<>();

        try {
            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM JOB WHERE completion = 0");
            while (results.next()) {
                Location location =
                    new Location(results.getDouble(2), results.getDouble(3));

                jobs.add(
                    new Job(
                        results.getLong(1), results.getShort(4), results.getString(5),
                        location, this.getJobSkills(results.getLong(1))));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return jobs;
    }

    /**
     * Gets the skills required for a job.
     *
     * @param jobID a job ID
     * @return a set of Skills
     */
    public HashSet<Skills> getJobSkills(long jobID) {
        HashSet<Skills> skills = new HashSet<>();
        try {
            ResultSet results = connection.createStatement().executeQuery("SELECT * FROM JOB_SKILLS WHERE jobID = " + jobID);
            while (results.next()) {
                skills.add(Skills.getSkill(results.getShort(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return skills;
    }

    /**
     * Assigns a job to an employee at the given time.
     *
     * @param employee an Employee
     * @param job a Job
     * @param time the start time
     */
    public void assignJob(Employee employee, Job job, int time) {
        try {
            connection.createStatement().execute(
                "INSERT INTO ASSIGNMENTS VALUES (" + employee.getEmployeeID() + ", " + job.getJobID() + ", " +
                    time + ")");
            connection.createStatement().execute("UPDATE JOB SET completion = 1 WHERE jobID = " + job.getJobID());
            System.out.println("Assigned " + job.getJobID() + " to employeee " + employee.getEmployeeID());
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Gets an employee's schedule.
     *
     * @param employeeID the employee's ID
     * @return a Schedule
     */
    public Schedule getEmployeeSchedule(long employeeID) {
        Schedule schedule = new Schedule();
        try {
            ResultSet results = this.connection.createStatement().executeQuery(
                "SELECT * FROM ASSIGNMENTS WHERE employeeID = " + employeeID);
            long[] timeTable = schedule.getTable();
            while (results.next()) {
                long jobID = results.getLong(2);
                short time = results.getShort(3);
                Job job = this.getJob(jobID);
                for (int i = 0; i < job.getLength(); i++) {
                    timeTable[time + i] = jobID;
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return schedule;
    }

    /**
     * Removes all scheduled jobs.
     */
    public void removeScheduledJobs() {
        try {
            connection.createStatement().execute("DELETE FROM ASSIGNMENTS");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
