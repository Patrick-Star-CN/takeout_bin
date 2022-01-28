package com.takeout.login;

/**
 * @Auther: codelover123hxy
 * @Date: 2022/1/24 - 01 - 24 - 20:07
 * @Description: com.takeout.login
 * @version: 1.0
 */

import java.sql.*;

public class AdminLogin {
    public static void LoginToInbin(Connection conn, String getAdminName, String getPassword) {
        try {
            String sql = "select * from admin";
            PreparedStatement psql = conn.prepareStatement(sql);
            ResultSet re = psql.executeQuery();
            re.next();
            String adminName = re.getString("adminName");
            String password = re.getString("password");
            if (adminName.equals(getAdminName) != true) {
                System.out.println("管理员用户名错误，请重新输入!");
            } else {
                if (password.equals(getPassword) != true)
                    System.out.println("管理员密码错误，请重新输入！");
                else {
                    System.out.println("管理员登录成功");
                }
            }
            re.close();
            psql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
