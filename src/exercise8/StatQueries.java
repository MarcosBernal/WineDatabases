package exercise8;
import exercise3.JDBCConnector;
import exercise5.CustomQuery;
import exercise6.User;
import exercise6.Wine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatQueries extends JDBCConnector {

    public StatQueries(String host, String password, String database) {
        super(host, password, database, null);
    }

    private User[] resultSetToUserArray(ResultSet result){
        ArrayList<User> user_list = new ArrayList<>();

        try {
            while (result.next())
                user_list.add(new User(result));
        } catch (SQLException e) {
            return null;
        }

        if (user_list.isEmpty())
            return null;
        else
            return user_list.toArray(new User[user_list.size()]);
    }

    @Override
    public ResultSet executeQuery(String query){
        System.out.println("Query to execute: '" + query + "'");
        return super.executeQuery(query);
    }

    public User[] getUserWithNoReviews(){
        ResultSet result = this.executeQuery("SELECT u.user_id, u.name FROM user u " +
                "LEFT JOIN wine_user_review r ON r.user_id = u.user_id WHERE r.user_id is NULL");
        return resultSetToUserArray(result);
    }

    public User[] getUsersWithMostReviews(){
        ResultSet result = this.executeQuery(
                "SELECT u.* FROM " +
                    "(SELECT user_id, RANK() OVER( ORDER BY count(score)DESC) as ranked, count(score) as reviews FROM wine_user_review r GROUP BY r.user_id) sub" +
                "JOIN user u ON u.user_id = sub.user_id WHERE sub.ranked = 1");
        return resultSetToUserArray(result);
    }

    public User[] getUsersWithTheClosestReviewsToGuide(){
        ResultSet result = this.executeQuery(
                "SELECT u.* FROM " +
                        "(SELECT r.user_id, SUM(ABS(g.score - r.score)) as diff_in_score, RANK() OVER( ORDER BY SUM(ABS(g.score - r.score)) ASC) as ranked FROM wine_user_review r " +
                        "JOIN (SELECT wine_id, avg(score) as score FROM wine_scoring_guide GROUP BY wine_id) g ON r.wine_id = g.wine_id GROUP BY r.user_id) sub" +
                        "JOIN user u ON u.user_id = sub.user_id WHERE sub.ranked = 1");
        return resultSetToUserArray(result);
    }

    public String[] getMultinationalWineries(){
        ResultSet result = this.executeQuery(
                "SELECT wr.* FROM " +
                        "(SELECT sub.winery_id, count(sub.country_id) as n_countries FROM " +
                            "(SELECT DISTINCT w.winery_id, r.country_id FROM wine w JOIN region r ON r.region_id = w.region_id) sub " +
                        "GROUP BY sub.winery_id) sub2 " +
                "JOIN winery wr ON wr.winery_id = sub2.winery_id WHERE sub2.n_countries > 2");

        ArrayList<String> winery_list = new ArrayList<>();

        try {
            while (result.next())
                winery_list.add("Winery name: " + result.getString("name") + " with id: " + result.getString("winery_id"));
        } catch (SQLException e) {
            return null;
        }

        if (winery_list.isEmpty())
            return null;
        else
            return winery_list.toArray(new String[winery_list.size()]);
    }

}
