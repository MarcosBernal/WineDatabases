package exercise7;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoringGuide {
    private int wine_id;
    private int taster_id;
    private Date date;
    private double score;
    private double price;

    public int getWine_id() {
        return wine_id;
    }

    public int getTaster_id() {
        return taster_id;
    }

    public Date getDate() {
        return date;
    }

    public double getScore() {
        return score;
    }

    public double getPrice() {
        return price;
    }


    public ScoringGuide(int wine_id, int taster_id, Date date, double score, double price) {
        this.wine_id = wine_id;
        this.taster_id = taster_id;
        this.date = date;
        this.score = score;
        this.price = price;
    }

    public ScoringGuide(ResultSet queryResult) {
        try {
            this.wine_id = queryResult.getInt("wine_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.taster_id = queryResult.getInt("taster_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.date = queryResult.getDate("date");
        } catch (SQLException e) {
            this.date = null;
        }
        try {
            this.score = queryResult.getDouble("score");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.price = queryResult.getDouble("price");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Score of wine id: '" + wine_id + "' from tasted id: '" + taster_id + "' is scored as: '" + score + "' at: '" + date.toString() + "'";
    }
}
