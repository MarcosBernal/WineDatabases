package exercise6;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Wine {
    private int wine_id;
    private String name;
    private String description;
    private int grape_variety_id;
    private String designation;
    private int winery_id;
    private int region_id;

    public Wine(int wine_id, String name, String description, int grape_variety_id, String designation, int winery_id, int region_id){
        this.wine_id = wine_id;
        this.name = name;
        this.description = description;
        this.grape_variety_id = grape_variety_id;
        this.designation = designation;
        this.winery_id = winery_id;
        this.region_id = region_id;
    }

    public int getWine_id() {
        return wine_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getGrape_variety_id() {
        return grape_variety_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setWine_id(int wine_id) {
        this.wine_id = wine_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGrape_variety_id(int grape_variety_id) {
        this.grape_variety_id = grape_variety_id;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setWinery_id(int winery_id) {
        this.winery_id = winery_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getWinery_id() {
        return winery_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public Wine(ResultSet queryResult) {
        try {
            this.wine_id = queryResult.getInt("wine_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.name = queryResult.getString("name");
        } catch (SQLException e) {
            this.designation = null;
        }
        try {
            this.description = queryResult.getString("description");
        } catch (SQLException e) {
            this.designation = null;
        }
        try {
            this.grape_variety_id = queryResult.getInt("grape_variety_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.designation = queryResult.getString("designation");
        } catch (SQLException e) {
            this.designation = null;
        }
        try {
            this.winery_id = queryResult.getInt("winery_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.region_id = queryResult.getInt("region_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "id: " + wine_id + "Wine name: " + name + " with the designation: " + designation + " has the grape variety id: " + grape_variety_id;
    }
}
