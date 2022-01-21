package com.takeout.mysql;

import java.sql.Timestamp;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/21 - 01 - 21 - 16:33
 * @Description: 用于储存已被取出的外卖数据
 * @version: 1.0
 */
public class TakeoutDataHistory {
    private int coordinate_X, coordinate_Y;
    private Timestamp date;
    private String consigneeName;
    public int getCoordinate_X() {
        return coordinate_X;
    }
    public int getCoordinate_Y() {
        return coordinate_Y;
    }
    public String getConsigneeName() {
        return consigneeName;
    }
    public Timestamp getDate() {
        return date;
    }
    public void setCoordinate_X(int coordinate_X) {
        this.coordinate_X = coordinate_X;
    }
    public void setCoordinate_Y(int coordinate_Y) {
        this.coordinate_Y = coordinate_Y;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }
}
