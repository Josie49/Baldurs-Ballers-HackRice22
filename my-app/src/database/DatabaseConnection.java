package database;

import database.item.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Phoebe Scaccia
 */
public class DatabaseConnection {

    private Connection connection = null;
    private Statement statement = null;

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
            this.statement = connection.createStatement();
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
                "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            preparedEmployeeStatement.setLong(1, employee.getEmployeeID());
            preparedEmployeeStatement.setString(2, employee.getPhoneNumber());
            preparedEmployeeStatement.setString(3, location.getAddress1());
            preparedEmployeeStatement.setString(4, location.getAddress2());
            preparedEmployeeStatement.setString(5, location.getCity());
            preparedEmployeeStatement.setString(6, location.getState());
            preparedEmployeeStatement.setString(7, location.getZip());
            preparedEmployeeStatement.setShort(8, employee.getShiftStart());
            preparedEmployeeStatement.setShort(9, employee.getShiftEnd());

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

            ResultSet results = statement.executeQuery("SELECT * FROM EMPLOYEE WHERE employeeID = " + employeeID);
            if (results.next()) {
                Location location =
                    new Location(results.getString(3), results.getString(4),
                        results.getString(5), results.getString(6), results.getString(7));

                return new Employee(
                    employeeID, results.getShort(8), results.getShort(9),
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
            ResultSet results = statement.executeQuery("SELECT * FROM EMPLOYEE");
            while (results.next()) {
                Location location =
                    new Location(results.getString(3), results.getString(4),
                        results.getString(5), results.getString(6), results.getString(7));

                employees.add(
                    new Employee(
                        results.getLong(1), results.getShort(8), results.getShort(9),
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
            ResultSet results = statement.executeQuery("SELECT * FROM EMPLOYEE_SKILLS WHERE employeeID = " + employeeID);
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
                "INSERT INTO JOB VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            preparedJobStatement.setLong(1, job.getJobID());
            preparedJobStatement.setString(2, location.getAddress1());
            preparedJobStatement.setString(3, location.getAddress2());
            preparedJobStatement.setString(4, location.getCity());
            preparedJobStatement.setString(5, location.getState());
            preparedJobStatement.setString(6, location.getZip());
            preparedJobStatement.setShort(7, job.getLength());
            preparedJobStatement.setString(8, job.getDetails());
            preparedJobStatement.setBoolean(9, false);

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

            ResultSet results = statement.executeQuery("SELECT * FROM JOB WHERE jobID = " + jobID);
            if (results.next()) {
                Location location =
                    new Location(results.getString(2), results.getString(3),
                        results.getString(4), results.getString(5), results.getString(6));

                return new Job(
                    jobID, results.getShort(7), results.getString(8), location,
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
            ResultSet results = statement.executeQuery("SELECT * FROM JOB");
            while (results.next()) {
                Location location =
                    new Location(results.getString(2), results.getString(3),
                        results.getString(4), results.getString(5), results.getString(6));

                jobs.add(
                    new Job(
                        results.getLong(1), results.getShort(7), results.getString(8),
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
            ResultSet results = statement.executeQuery("SELECT * FROM JOB WHERE completion = 0");
            while (results.next()) {
                Location location =
                    new Location(results.getString(2), results.getString(3),
                        results.getString(4), results.getString(5), results.getString(6));

                jobs.add(
                    new Job(
                        results.getLong(1), results.getShort(7), results.getString(8),
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
            ResultSet results = statement.executeQuery("SELECT * FROM JOB_SKILLS WHERE jobID = " + jobID);
            while (results.next()) {
                skills.add(Skills.getSkill(results.getShort(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return skills;
    }
}
