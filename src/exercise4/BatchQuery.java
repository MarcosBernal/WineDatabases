package exercise4;

import exercise3.JDBCConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class BatchQuery extends JDBCConnector {

    private int batchSize;

    public BatchQuery(String host, String password, String database, int batchSize) {
        super(host, password, database, "?rewriteBatchedStatements=true");
        this.batchSize = batchSize;

    }

    public void insertWineScoringBatchQuery(String pathFile, String delimiter) throws IOException, SQLException {

        BufferedReader reader = new BufferedReader(new FileReader(pathFile));

        PreparedStatement statement = this.conn.prepareStatement("INSERT IGNORE INTO wine_scoring_guide " +
                "(wine_id, taster_id, date, score, price) VALUES (?, ?, ?, ?, ?)");

//        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO wine_scoring_guide " +
//                "(wine_id, taster_id, date, score, price) VALUES (?, ?, ?, ?, ?)"+
//                    "ON DUPLICATE KEY UPDATE " +
//                "date = COALESCE( VALUES(date), date)," +
//                "price = COALESCE( VALUES(price), price)");

        String line;
        int i = 0;
        reader.readLine(); // Removing header
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");

        while ((line = reader.readLine()) != null) {
            String[] values = line.trim().replaceAll("\"","").split(delimiter);
            if (values.length < 5)
                continue;
            try {
                statement.setInt(1, Integer.parseInt(values[0]));
            } catch (java.lang.NumberFormatException e) {
                continue; //Not value in having a null wine scored
                //statement.setNull(1, Types.DECIMAL);
            }
            try {
                statement.setInt(2, Integer.parseInt(values[1]));
            } catch (java.lang.NumberFormatException e) {
                continue; //Not value in having a score without taster ref
                //statement.setNull(2, Types.DECIMAL);
            }
            try {
                statement.setDate(3, new java.sql.Date(date_format.parse(values[2]).getTime()));
            } catch (ParseException e) {
                statement.setNull(3, Types.DATE);
            }
            try {
                statement.setDouble(4, Double.parseDouble(values[3]));
            } catch (java.lang.NumberFormatException e) {
                continue; //Not value in having a score without score points
                //statement.setNull(4, Types.DECIMAL);
            }
            try {
                statement.setDouble(5, Double.parseDouble(values[4]));
            } catch (java.lang.NumberFormatException e) {
                statement.setNull(5, Types.DECIMAL);
            }

            i++;

            statement.addBatch();

            if (i % batchSize == 0 || !reader.ready()) {
                statement.executeLargeBatch(); // Execute every 1000 items
                System.out.println(LocalDateTime.now() + ": Iteration number: " + i);
            }
        }

        statement.executeBatch();
    }

    public void insertWineUserReviewBatchQuery(String pathFile, String delimiter) throws IOException, SQLException {

        BufferedReader reader = new BufferedReader(new FileReader(pathFile));
        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO wine_user_review " +
                "(wine_id, user_id, date, score) VALUES (?, ?, ?, ?)" +
                        "ON DUPLICATE KEY UPDATE " +
                "date = COALESCE( VALUES(date), date)");

        String line;
        int i = 0;
        reader.readLine(); // Removing header
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");

        while ((line = reader.readLine()) != null) {
            String[] values = line.trim().replaceAll("\"","").split(delimiter);
            try {
                statement.setInt(1, Integer.parseInt(values[0]));
            } catch (java.lang.NumberFormatException e) {
                continue; //Not value in having a null wine scored
                //statement.setNull(1, Types.DECIMAL);
            }
            try {
                statement.setInt(2, Integer.parseInt(values[1]));
            } catch (java.lang.NumberFormatException e) {
                continue; //Not value in having a score without taster ref
                //statement.setNull(2, Types.DECIMAL);
            }
            try {
                statement.setDate(3, new java.sql.Date(date_format.parse(values[2]).getTime()));
            } catch (ParseException e) {
                statement.setNull(3, Types.DATE);
            }
            try {
                statement.setDouble(4, Double.parseDouble(values[3]));
            } catch (java.lang.NumberFormatException e) {
                continue; //Not value in having a score without score points
                //statement.setNull(4, Types.DECIMAL);
            }

            i++;

            statement.addBatch();

            if (i % batchSize == 0 || !reader.ready()) {
                statement.executeLargeBatch(); // Execute every 1000 items
                System.out.println(LocalDateTime.now() + ": Iteration number: " + i);
            }
        }

        statement.executeBatch();
    }


}
