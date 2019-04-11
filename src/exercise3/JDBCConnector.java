package exercise3;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class JDBCConnector {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    protected Connection conn;


    public JDBCConnector(String host, String password, String database, String options)
    {
        String urlOptions = (options != null) ? options : "";
        try {
            Class.forName(JDBC_DRIVER);
            String url = "jdbc:mysql://" + host +":3306/" + database + urlOptions;
            System.out.println("Connecting to " + url);
            this.conn = DriverManager.getConnection(url,"root", password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            Statement stmt = this.conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result; //need to do a close after iterating over the resultset
    }
}
