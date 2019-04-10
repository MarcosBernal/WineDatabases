import exercise3.JDBCConnector;
import exercise4.BatchQuery;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AssignmentSolver {

    public static void main(String[] args) throws Exception {
        HashMap<String, String> expected_args = argscheck_args(args);
        int number = -1;
        while(number != 0) {
            System.out.println("Insert the exercise that you want to do (1-9 and 0 is exit)");
            try {
                number = Integer.parseInt(System.console().readLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("Only number are allowed!!! ");
            }
            if (number > 0 && number < 10)
                do_exercise(number, expected_args);

            number = -0;
        }
    }

    public static void do_exercise(int number, HashMap<String, String> expected_args) throws SQLException, IOException {
        switch(number){
            case 1:
            case 2: System.out.println("No done yet D:");
                    break;
            case 3:
                JDBCConnector myJdbc = new JDBCConnector(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"));
                ResultSet result3 = myJdbc.executeQuery("SELECT count(*) from user");
                while(result3.next())
                    System.out.println("There are " + result3.getInt("count(*)") + " users");
                result3.close();
                System.out.println("Exercise 3 complete!!");
                break;
            case 4:
                BatchQuery myQueryBatch = new BatchQuery(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"));
                myQueryBatch.insertWineScoringBatchQuery("wine_scoring_guide.data.csv");
                myQueryBatch.insertWineScoringBatchQuery("wine_user_review.data.csv");
                System.out.println("Exercise 4 complete!!");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: System.out.println("No done yet D:");
                    break;

        }
    }

    public static HashMap argscheck_args(String[] args){
        if(args.length != 3){
            System.out.println("Require 3 parameters: host, password and database\nTry again!! Closing...");
            System.exit(1);
        }
        HashMap<String,String> expected_args = new HashMap();
        expected_args.put("host", args[0]);
        expected_args.put("password", args[1]);
        expected_args.put("database", args[2]);
        return expected_args;
    }
}
