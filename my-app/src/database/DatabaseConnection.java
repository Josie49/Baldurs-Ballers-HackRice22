package database;
import database.item.Employee;
import database.item.Job;
import database.item.Location;
import database.item.Skills;

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
                    employeeID, results.getInt(8), results.getInt(9),
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
                        results.getLong(1), results.getInt(8), results.getInt(9),
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
                        results.getLong(1), results.getInt(7), results.getString(8),
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
                        results.getLong(1), results.getInt(7), results.getString(8),
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
                skills.add(Skills.getSkill(results.getInt(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return skills;
    }
}
