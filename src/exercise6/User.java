package exercise6;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int user_id;
    private String name;

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public User(int user_id, String name) {
        this.user_id = user_id;
        this.name = name;
    }

    public User(ResultSet resultSet){
        try {
            this.user_id = resultSet.getInt("user_id");
            this.name = resultSet.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "User id: " + this.user_id + " has name: " + this.name;
    }
}
