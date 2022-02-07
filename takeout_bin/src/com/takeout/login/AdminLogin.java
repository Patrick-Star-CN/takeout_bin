package com.takeout.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

/**
 * @Auther: codelover123hxy
 * @Date: 2022/1/24 - 01 - 24 - 20:07
 * @Description: 管理员登录
 * @version: 2.0
 */

public class AdminLogin {
    public static String LoginToInbin(Connection conn, String getAdminName, String getPassword) {
        //管理员登录，判断用户名与密码与数据库中的是否一致
        String res = null;
        try {
            String sql = "select * from admin";
            PreparedStatement psql = conn.prepareStatement(sql);
            ResultSet re = psql.executeQuery();
            re.next();
            String adminName = re.getString("adminName");
            String password = re.getString("password");
            if (adminName.equals(getAdminName) != true) {
                res = "Wrong name";
            } else {
                if (password.equals(getPassword) != true)
                    res = "Wrong password";
                else {
                    res = "success";
                }
            }
            re.close();
            psql.close();
        } catch (SQLException e) {
            e.printStackTrace();
            res = "SQLError";
        } catch (Exception e) {
            e.printStackTrace();
            res = "error";
        } finally {
            return res;
        }
    }

    public AdminLogin() {

    }
}
