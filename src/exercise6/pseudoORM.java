package exercise6;
import exercise5.CustomQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class pseudoORM extends CustomQuery{

    public pseudoORM(String host, String password, String database) {
        super(host, password, database);
    }

    public Wine getWine(int wine_id){
        ResultSet result = this.executeQuery("SELECT * FROM wine WHERE wine ='" + wine_id+ "';");
        try {
            result.next();
        } catch (SQLException e) {
            return null;
        }
        return new Wine(result);
    }

    public Wine[] getWinesByNameOrDescription(String name, String description){
        ResultSet result = this.getWineFromNameAndDescription(name, description);
        ArrayList<Wine> wine_list = new ArrayList<Wine>();

        try {
        while(result.next())
            wine_list.add(new Wine(result));
        } catch (SQLException e) {
            return null;
        }
        return wine_list.toArray(new Wine[wine_list.size()]);
    }

}
