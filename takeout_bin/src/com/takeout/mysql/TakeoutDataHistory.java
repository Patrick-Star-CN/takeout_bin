package com.takeout.mysql;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Author: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:07
 * @Description: 用于储存已被取出的外卖数据
 * @version: 1.5
 */
public class TakeoutDataHistory extends TakeoutData {
    private Timestamp date_out;

    public Timestamp getDate_out() {
        return date_out;
    }
    public void setDate_out(Timestamp date_out) {
        this.date_out = date_out;
    }

    public TakeoutDataHistory() {

    }
}
