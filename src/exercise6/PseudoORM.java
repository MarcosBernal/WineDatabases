package exercise6;
import exercise5.CustomQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PseudoORM extends CustomQuery{

    public PseudoORM(String host, String password, String database) {
        super(host, password, database);
    }

    public Wine getWine(int wine_id){
        ResultSet result = this.executeQuery("SELECT * FROM wine WHERE wine_id ='" + wine_id+ "';");
        try {
            result.next();
        } catch (SQLException e) {
            return null;
        }
        return new Wine(result);
    }

    public Wine[] getWinesByNameOrDescription(String name, String description){
        ResultSet result = this.getWineFromNameAndDescription(name, description);
        ArrayList<Wine> wine_list = new ArrayList();

        try {
        while(result.next())
            wine_list.add(new Wine(result));
        } catch (SQLException e) {
            return null;
        }
        return wine_list.toArray(new Wine[wine_list.size()]);
    }

    public User[] getUsersThatHaveReviewOfWine(int wine_id){
        ResultSet reviewersOfWineId = this.executeQuery("SELECT u.* FROM user u " +
                "JOIN wine_user_review r ON u.user_id = r.user_id WHERE wine_id = '" + wine_id +"';");
        ArrayList<User> user_list = new ArrayList();
        try {
            while (reviewersOfWineId.next())
                user_list.add(new User(reviewersOfWineId));
        } catch (SQLException e) {
            return null;
        }
        return user_list.toArray(new User[user_list.size()]);

    }

}
