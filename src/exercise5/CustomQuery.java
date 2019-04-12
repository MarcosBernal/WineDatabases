package exercise5;

import exercise3.JDBCConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


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

    // Since there is not a column called name we will use title in its place
    public ResultSet getWineFromNameAndDescription(String title, String description){
        String query = "";

        if(title != null && title != "" && description != null && description != "")
            query = "SELECT * FROM wine WHERE title = '" + title + "' AND description = '" + description + "';";
        else if(title != null && title != "")
            query = "SELECT * FROM wine WHERE title = '" + title + "';";
        else if(description != null && description != "")
            query = "SELECT * FROM wine WHERE description = '" + description + "';";

        return executeQuery(query);
    }

    public ArrayList<Double> filterWineScoreListById(int wine_id){
        ArrayList<Double> wineScoreList = new ArrayList<>();
        ResultSet result = executeQuery("SELECT score FROM wine_scoring_guide WHERE wine_id = '" + wine_id + "';");
        try {
            while (!result.next())
                wineScoreList.add(result.getDouble("score"));
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return wineScoreList;
    }

    public HashMap<String, Double> getNumberOfReviewsAndMeanScore(){

        HashMap result = new HashMap<String, Double>();

        ResultSet queryResultSet = executeQuery("SELECT count(*) as nreviews, avg(score) as mean " +
                "FROM wine_scoring_guide");
        try {
            queryResultSet.next();
            result.put("nreviews", Double.valueOf(queryResultSet.getInt("nreviews")));
            result.put("mean", queryResultSet.getDouble("mean"));
        } catch (SQLException e) {
            result.put("nreviews", 0.0);
            result.put("mean", 0.0);
        }
        return result;
    }

    public double getScoreOfWineId(int wine_id){
        ArrayList<Double> list = filterWineScoreListById(wine_id);

        double score = 0;
        Iterator<Double> i = list.iterator();
        while(i.hasNext())
            score += i.next();
        return score/list.size();

    }


}
