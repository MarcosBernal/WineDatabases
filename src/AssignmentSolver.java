import exercise3.JDBCConnector;
import exercise4.BatchQuery;
import exercise5.CustomQuery;
import exercise6.PseudoORM;
import exercise6.User;
import exercise6.Wine;
import exercise7.OneTransactionPerWine;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

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
        }
    }

    public static void do_exercise(int number, HashMap<String, String> expected_args) throws SQLException, IOException {
        switch(number){
            case 0:
                break;
            case 1:
            case 2:
                System.out.println("No done yet D:");
                break;
            case 3:
                JDBCConnector myJdbc = new JDBCConnector(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"), null);
                ResultSet result3 = myJdbc.executeQuery("SELECT count(*) from user");
                while(result3.next())
                    System.out.println("There are " + result3.getInt("count(*)") + " users");
                result3.close();
                System.out.println("Exercise 3 complete!!");
                break;
            case 4:
                BatchQuery myQueryBatch = new BatchQuery(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"), 50000);

                System.out.println(">> Uploading wine_scoring_guide!!");
                myQueryBatch.insertWineScoringBatchQuery("src/exercise4/wine_scoring_guide.data.csv", ";");

                System.out.println(">> Uploading wine_user_review!!");
                myQueryBatch.insertWineUserReviewBatchQuery("src/exercise4/wine_user_review.data.csv", ";");


                System.out.println("Exercise 4 complete!!");
                break;
            case 5:
                CustomQuery myCustomQuery = new CustomQuery(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"));
                int numberOfWines = myCustomQuery.getNumberOfInstances("wine", "wine_id");
                System.out.println("Number of wines registries: " + numberOfWines);

                ResultSet wine1 = myCustomQuery.getWineFromNameAndDescription("Quinta dos Avidagos 2011 Avidagos Red (Douro)", null);
                while(wine1.next())
                    System.out.println("Found wine name: " + wine1.getString("title") + " with designation: " + wine1.getString("designation"));

                ResultSet wine2 = myCustomQuery.getWineFromNameAndDescription(null, "Aromas include tropical fruit, broom, brimstone and dried herb. The palate isn\\'t overly expressive, offering unripened apple, citrus and dried sage alongside brisk acidity.");
                while(wine2.next())
                    System.out.println("Found wine name: " + wine2.getString("title") + " with designation: " + wine2.getString("designation"));

                int wine_id = 1;
                Iterator<Double> wineScoreListIterator = myCustomQuery.filterWineScoreListById(wine_id).iterator();
                System.out.print("Score list of wine with id: " + wine_id + " {");
                while(wineScoreListIterator.hasNext())
                    System.out.print(wineScoreListIterator.next() + ", ");
                System.out.println("}");

                HashMap<String, Double> scores = myCustomQuery.getNumberOfReviewsAndMeanScore();
                System.out.println("From " + scores.get("nreviews").intValue() + " scores with a mean of " + scores.get("mean"));

                System.out.println("N* id: " + wine_id + " has " + myCustomQuery.getScoreOfWineId(wine_id) + " in average");
                break;
            case 6:
                PseudoORM myPseudoORM = new PseudoORM(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"));
                int wine_id_1 = 3209;
                Wine wine_1 = myPseudoORM.getWine(wine_id_1);
                System.out.println("If you look for wine id: " + wine_id_1 + " it returns: " + wine_1.toString());

                Wine[] wine_list = myPseudoORM.getWinesByNameOrDescription(wine_1.getName(), null);
                if(wine_list.length > 0)
                    System.out.println("However there are others wines that share the name");
                for(int i = 0; i < wine_list.length; i++)
                    System.out.println("N* " + (i+1) + " " + wine_list[i].toString());

                int wine_id_with_many_reviews = 40610;
                wine_1 = myPseudoORM.getWine(wine_id_with_many_reviews);
                wine_list = myPseudoORM.getWinesByNameOrDescription(wine_1.getName(), null);
                if(wine_list.length > 0)
                    System.out.println("However there are others wines that share the name");
                for(int i = 0; i < wine_list.length; i++)
                    System.out.println("N* " + (i+1) + " " + wine_list[i].toString());

                User[] user_list = myPseudoORM.getUsersThatHaveReviewOfWine(wine_id_with_many_reviews);
                System.out.println("The following users (" + user_list.length + ") have written a review of: " + wine_1.toString());
                for(int i = 0; i < user_list.length; i++)
                    System.out.println("N* " + (i+1) + " " + user_list[i].toString());

                break;
            case 7:
                OneTransactionPerWine myOneTransactioner = new OneTransactionPerWine(expected_args.get("host"), expected_args.get("password"), expected_args.get("database"));
                myOneTransactioner.parseCSVtoHashCodeWithWineAndGuides("src/exercise7/nuevas_catas.csv" , ";");

                break;
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
