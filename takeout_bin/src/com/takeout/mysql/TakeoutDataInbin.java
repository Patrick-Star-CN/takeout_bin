package com.takeout.mysql;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:07
 * @Description: 用于储存在柜中的外卖数据
 * @version: 1.5
 */
public class TakeoutDataInbin extends TakeoutData {
    private int id;
    private String code;

    public int getId() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public TakeoutDataInbin() {

    }
}
