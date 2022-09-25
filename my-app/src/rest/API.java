package rest;

import com.sun.net.httpserver.HttpServer;
import database.DatabaseConnection;
import database.item.Employee;
import database.item.Job;
import database.item.Location;
import database.item.Skills;
import management.Schedule;
import management.Scheduler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class API {
    public static void main(String[] args) throws IOException {

        // Connect to the database.

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Scheduler scheduler = new Scheduler(databaseConnection);
        scheduler.scheduleAll(
            new ArrayList<>(databaseConnection.getEmployees()),
            new ArrayList<>(databaseConnection.getUnfinishedJobs()));

        // Create the server

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Get the employee's info.

        server.createContext("/api/employee/info", (exchange -> {

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String URI = exchange.getRequestURI().toString();
                long userID = Long.parseLong(URI.substring(URI.lastIndexOf('/') + 1));
                JSONObject jsonObject = getEmployee(databaseConnection, userID);

                exchange.getResponseHeaders().set("Content-Type", "application/json");

                String responseText = jsonObject.toString();

                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        // Get the employee's schedule.

        server.createContext("/api/employee/schedule", (exchange -> {

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String URI = exchange.getRequestURI().toString();
                long userID = Long.parseLong(URI.substring(URI.lastIndexOf('/') + 1));
                JSONObject jsonObject = getSchedule(databaseConnection, userID);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

                String responseText = jsonObject.toString();

                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        // Add an employee to the database.

        server.createContext("/api/employee/add", (exchange -> {

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.getRequestHeaders().set("Content-Type", "application/json");

                JSONObject request;

                try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    request = new JSONObject(response.toString());
                }

                long employeeID = addEmployee(databaseConnection, request);
                String responseText = "Added employee " + employeeID + " to the database.\n";

                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        // Add a job to the database.

        server.createContext("/api/job/add", (exchange ->   {

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.getRequestHeaders().set("Content-Type", "application/json");

                JSONObject request;

                try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    request = new JSONObject(response.toString());
                }

                long jobID = addJob(databaseConnection, request);
                String responseText = "Added job " + jobID + " to the database.\n";

                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();

    }

    /**
     * Adds an employee to the database.
     *
     * @param databaseConnection a connection to the database
     * @param employeeInfo the JSON representation of the employee
     * @return the employee ID
     */
    public static long addEmployee(DatabaseConnection databaseConnection, JSONObject employeeInfo) {
        HashSet<Skills> skills = new HashSet<>();
        JSONArray inputSkills = employeeInfo.getJSONArray("skills");
        for (int i = 0; i < inputSkills.length(); i++) {
            skills.add(Skills.getSkill(Integer.parseInt(inputSkills.get(i).toString())));
        }
        Employee employee = new Employee(
            (short) employeeInfo.getInt("shiftStart"), (short) employeeInfo.getInt("shiftEnd"),
            employeeInfo.getString("phoneNumber"),
            new Location(employeeInfo.getDouble("startingLatitude"), employeeInfo.getDouble("startingLongitude")),
            skills);

        databaseConnection.addEmployee(employee);
        return employee.getEmployeeID();
    }

    /**
     * Get the employee as a JSON.
     *
     * @param databaseConnection a connection to a database.
     * @param employeeID the employee ID
     * @return the JSON representation of the employee
     */
    public static JSONObject getEmployee(DatabaseConnection databaseConnection, long employeeID) {
        Employee employee = databaseConnection.getEmployee(employeeID);
        JSONObject jsonObject = new JSONObject();

        jsonObject.append("employeeID", employee.getEmployeeID());
        jsonObject.append("phoneNumber", employee.getPhoneNumber());
        jsonObject.append("startingLatitude", employee.getStartingLocation().getLatitude());
        jsonObject.append("startingLongitude", employee.getStartingLocation().getLongitude());
        jsonObject.append("shiftStart", employee.getShiftStart());
        jsonObject.append("shiftEnd", employee.getShiftEnd());
        jsonObject.append("skills", employee.getSkills());

        return jsonObject;
    }

    /**
     * Adds a job to the database.
     *
     * @param databaseConnection the database connection
     * @param jobInfo the JSON representation of the job
     * @return the job ID
     */
    public static long addJob(DatabaseConnection databaseConnection, JSONObject jobInfo) {
        HashSet<Skills> skills = new HashSet<>();
        JSONArray inputSkills = jobInfo.getJSONArray("skills");
        for (int i = 0; i < inputSkills.length(); i++) {
            skills.add(Skills.getSkill(Integer.parseInt(inputSkills.get(i).toString())));
        }

        Job job = new Job((short) jobInfo.getInt("length"), jobInfo.getString("details"),
            new Location(jobInfo.getDouble("startingLatitude"), jobInfo.getDouble("startingLongitude")),
            skills);

        databaseConnection.addJob(job);
        return job.getJobID();
    }

    /**
     * Get the employee's schedule as a JSON.
     *
     * @param databaseConnection the database connect
     * @param employeeID the employee ID
     * @return the JSON representation of the employee's schedule
     */
    public static JSONObject getSchedule(DatabaseConnection databaseConnection, long employeeID) {
        Schedule schedule = databaseConnection.getEmployeeSchedule(employeeID);
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < 24; i++) {
            jsonObject.append(String.valueOf(i), schedule.getTable()[i]);
        }

        return jsonObject;
    }

}
