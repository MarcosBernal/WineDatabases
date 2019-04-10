package exercise4;

import exercise3.JDBCConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;


public class BatchQuery extends JDBCConnector {

    public BatchQuery(String host, String password, String database) {
        super(host, password, database);
    }

    public void insertWineScoringBatchQuery(String pathFile, String delimiter) throws IOException, SQLException {

        BufferedReader reader = new BufferedReader(new FileReader(pathFile));
        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO wine_scoring_guide " +
                "(wine_id, taster_id, date, score, price) VALUES (?, ?, ?, ?, ?)");

        String line;
        int i = 0;
        reader.readLine(); // Removing header
        Date date_format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String[] values = line.trim().replaceAll("\"","").split(delimiter);
            System.out.println(values[0] + values[1] + values[2] + values[3] + values[4]);
            statement.setInt(1, Integer.parseInt(values[0]));
            statement.setInt(2, Integer.parseInt(values[1]));
            statement.setDate(3, date_format.parse(values[2]));
            statement.setDouble(4, Double.parseDouble(values[3]));
            statement.setDouble(5, Double.parseDouble(values[4]));

            i++;

            statement.addBatch();

            if (i % 1000 == 0 || !reader.ready()) {
                statement.executeBatch(); // Execute every 1000 items.
            }
        }
    }

    public void insertWineUserReviewBatchQuery(String pathFile, String delimiter) throws IOException, SQLException {

        BufferedReader reader = new BufferedReader(new FileReader(pathFile));
        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO wine_scoring_guide " +
                "(wine_id, user_id, date, score) VALUES (?, ?, ?, ?)");

        String line;
        int i = 0;
        reader.readLine(); // Removing header
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String[] values = line.trim().replaceAll("\"","").split(delimiter);
            statement.setInt(1, Integer.parseInt(values[0]));
            statement.setInt(2, Integer.parseInt(values[1]));
            statement.setDate(3, Date.valueOf(values[2]));
            statement.setDouble(4, Double.parseDouble(values[3]));

            i++;

            statement.addBatch();

            if (i % 1000 == 0 || !reader.ready()) {
                statement.executeBatch(); // Execute every 1000 items.
            }
        }
    }


}
