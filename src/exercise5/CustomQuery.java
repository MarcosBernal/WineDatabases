package exercise5;

import exercise3.JDBCConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class CustomQuery extends JDBCConnector {

    public CustomQuery(String host, String password, String database) {
        super(host, password, database, null);
    }

    public int getNumberOfInstances(String table, String column){
        String countColumn = "count(" + column + ")";
        String query = "SELECT " + countColumn + " FROM " + table + ";";
        try {
            ResultSet result = executeQuery(query);
            result.next();
            return result.getInt(countColumn);
        } catch(SQLException e) {
            System.out.println("Error: " + e + ". Column: " + countColumn + " was not found. With the query: " + query);
            return 0;
        }

    }

    public ResultSet getWineFromNameAndDescription(String name, String description){
        return executeQuery("SELECT * FROM wine WHERE name = '" + name + "' AND description = '" + description + ";");
    }


}
