package com.takeout.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:07
 * @Description: com.takeout.mysql
 * @version: 1.5
 */
public class Sqlconn {

    public static Connection conn() {
        //数据库连接方法
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/takeout_bin";
        String user = "root";
        String password = "cxcxcx4,";
        Connection conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return conn;
        }
    }

    public static void disconn(Connection conn) {
        //数据库结束连接方法
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
