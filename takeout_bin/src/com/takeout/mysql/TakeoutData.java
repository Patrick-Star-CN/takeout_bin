package com.takeout.mysql;

import java.sql.Timestamp;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:07
 * @Description: com.takeout.mysql
 * @version: 1.5
 */
public class TakeoutData {
    private int coordinate;
    private Timestamp date;
    private String consigneeName, phoneNum;

    public int getCoordinate() {
        return coordinate;
    }
    public String getConsigneeName() {
        return consigneeName;
    }
    public Timestamp getDate() {
        return date;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public TakeoutData() {

    }
}
