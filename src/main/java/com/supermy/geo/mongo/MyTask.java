package com.supermy.geo.mongo;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by moyong on 16/5/13.
 */
@Deprecated
public class MyTask {
//    mongo.tasks={"_id":"%s","time":Date("%s"),"passenger":"%s","phone":"%s","type": "Point", "position": [%s,%s]}
//    mongo.tasks1={"match":{time:Date("%s"),"driver":"%s","phone":"%s","type": "Point","position": [%s,%s]},status:"ok"}

    @Id private String id;

    private Date time;
    private String passenger;
    private String phone;
    private String type;
    private double[] position;
    private String status;

    private Location match;

    public MyTask() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getMatch() {
        return match;
    }

    public void setMatch(Location match) {
        this.match = match;
    }
}
