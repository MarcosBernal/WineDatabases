package exercise7;

import exercise6.PseudoORM;
import exercise6.Wine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneTransactionPerWine extends PseudoORM {

    public OneTransactionPerWine(String host, String password, String database) {
        super(host, password, database);
        try {
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void commitAndRollbackIfError(String query){
        try {
            this.conn.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Failure when commiting query: " + query + " rolling back");
                this.conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Failure rolling back!!");
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String query){
        ResultSet result = super.executeQuery(query);
        commitAndRollbackIfError(query);
        return result;
    }

    public boolean executeUpdate(String query){
        boolean result = false;
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            result = stmt.execute();
        } catch (SQLException e) {
            System.err.println("Error with query: \n" + query + "\n");
            e.printStackTrace();
            System.exit(1);
        }
        return result;
    }

    private int retrieveForeignKey(String table, String nameValue, String foreignKey){
        ResultSet result = executeQuery("SELECT " + foreignKey + " FROM " + table + " WHERE name = '" + nameValue.replaceAll("'","") + "'");
        int foreigKey = -1;
        try {
            if (result.next()){
                foreigKey = result.getInt(foreignKey);
            }
            else{
                    System.err.println("There is not " + foreignKey + " key. Check the table: '" + table + "' and look for the name: '" + nameValue + "'");
                    System.err.println("Insert it before continuing");
                    System.exit(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foreigKey;
    }

    public HashMap<Wine, ArrayList<ScoringGuide>> parseCSVtoHashCodeWithWineAndGuides(String pathFile, String delimiter) throws IOException, SQLException {

        BufferedReader reader = new BufferedReader(new FileReader(pathFile));

        String line;
        reader.readLine(); // Removing header

        HashMap<Wine, ArrayList<ScoringGuide>> wineGuideHashMap = new HashMap<>();
        Wine wine;
        ScoringGuide guide;
        Wine[] temp_wine_variable;

        while ((line = reader.readLine()) != null) {
            String[] values = line.trim().replaceAll("\"", "").replaceAll("'", "").split(delimiter);

            //title; variety; winery; country; description; designation; points; price; province; region; area; taster_name; taster_twitter_handle
            //  0       1         2       3        4            5          6       7        8        9      10      11          12
            temp_wine_variable = getWinesByNameOrDescription(values[0], values[4]);

            if (temp_wine_variable != null)
                wine = temp_wine_variable[0];
            else
                wine = new Wine(-1,
                        values[0],
                        values[4],
                        retrieveForeignKey("grape_variety", values[1], "grape_variety_id"),
                        values[5],
                        retrieveForeignKey("winery", values[2], "winery_id"),
                        retrieveForeignKey("region", values[9], "region_id"));

            guide = new ScoringGuide(-1,
                    retrieveForeignKey("taster", values[11], "taster_id"),
                    new Date(System.currentTimeMillis()),
                    Double.parseDouble(values[6]),
                    Double.parseDouble(values[7])
            );

            if (!wineGuideHashMap.containsKey(wine)) {
                wineGuideHashMap.put(wine, new ArrayList<>());
            }

            wineGuideHashMap.get(wine).add(guide);
        }
        return wineGuideHashMap;
    }

    public void insertMultipleScores(HashMap<Wine, ArrayList<ScoringGuide>> wineGuideHashMap){
        String temporalVariable = "";
        String wine_query = "";
        String score_query = "";
        String on_duplicate_key_update_suffix;
        for(Map.Entry<Wine, ArrayList<ScoringGuide>> entry : wineGuideHashMap.entrySet()) {
            Wine wine = entry.getKey();
            ArrayList<ScoringGuide> scores = entry.getValue();

            score_query = "INSERT INTO wine_scoring_guide (wine_id, taster_id, date, score, price) VALUES ";
            on_duplicate_key_update_suffix = " ON DUPLICATE KEY UPDATE date = COALESCE( VALUES(date), date, now())," +
                    "score = COALESCE( VALUES(score), score), price = COALESCE( VALUES(price), price);" ;

            if(wine.getWine_id() == -1){
                temporalVariable = "SET @new_wine_id = (SELECT MAX(wine_id)+1 FROM wine w);";
                wine_query = "INSERT INTO wine (wine_id, title, description, grape_variety_id, designation, winery_id, region_id)" +
                        " VALUES ( @new_wine_id, '" + wine.getName() + "', '" + wine.getDescription() + "', " + wine.getGrape_variety_id() + ", '" + wine.getDesignation() +
                        "', " + wine.getWinery_id() + ", " + wine.getRegion_id() + ")"; // super method does not commit

                for(ScoringGuide score : scores)
                    score_query += "( @new_wine_id, " + score.getTaster_id() + ", '" + score.getDate() + "', " + score.getScore() + ", " + score.getPrice() + "), ";


            }
            else{
                wine_query = "UPDATE wine SET title = '" + wine.getName() + "', description = '" + wine.getDescription() +
                        "', grape_variety_id ='" + wine.getGrape_variety_id() + "', designation = '" + wine.getDesignation() +
                        "', winery_id = '" + wine.getWinery_id() + "', region_id = '" + wine.getRegion_id() +
                        "' WHERE wine_id = '" + wine.getWine_id() + "';"; // super method does not commit

                for(ScoringGuide score : scores)
                    score_query += "( " + wine.getWine_id() + ", " + score.getTaster_id() + ", '" + score.getDate() + "', " + score.getScore() + ", " + score.getPrice() + "), ";
            }

            score_query = score_query.substring(0, score_query.length() - 2) + on_duplicate_key_update_suffix;

            if (temporalVariable != "")
                executeUpdate(temporalVariable);
            executeUpdate(wine_query);
            executeUpdate(score_query);

            commitAndRollbackIfError(temporalVariable + wine_query + score_query);
            System.out.println("Inserting " + scores.size() + " new scores for wine: " + wine.toString());
        }
    }

}
