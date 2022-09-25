package rest;

import com.sun.net.httpserver.HttpServer;
import database.DatabaseConnection;
import database.item.Employee;
import management.Schedule;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class API {
    public static void main(String[] args) throws IOException {

        // Connect to the database.

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // Create the server

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

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

        server.createContext("/api/employee/schedule", (exchange -> {

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String URI = exchange.getRequestURI().toString();
                long userID = Long.parseLong(URI.substring(URI.lastIndexOf('/') + 1));
                JSONObject jsonObject = getSchedule(databaseConnection, userID);

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


        server.setExecutor(null); // creates a default executor
        server.start();

    }

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

    public static JSONObject getSchedule(DatabaseConnection databaseConnection, long employeeID) {
        Schedule schedule = databaseConnection.getEmployeeSchedule(employeeID);
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < 24; i++) {
            jsonObject.append(String.valueOf(i), schedule.getTable()[i]);
        }

        return jsonObject;
    }

}
