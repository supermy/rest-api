package com.supermy.geo.mongo;

import org.springframework.data.annotation.Id;

/**
 * Created by moyong on 16/5/13.
 */
public class Location {
    @Id private String id;

    /**
     * 司机姓名
     */
    private String driver ;

    private double[] position;

    public Location() {}

    public Location(String id, double x, double y) {
        this.id = id;
        this.position = new double[] {x,y};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] pos) {
        this.position = pos;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }


    @Override
    public String toString() {
        return String.format("%s,%s(%1.3f, %1.3f)", id,driver,position[0], position[1]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
