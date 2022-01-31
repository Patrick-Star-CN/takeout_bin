package com.takeout.login;

import java.sql.*;

/**
 * @Auther: Qing-Qiu
 * @Date: 2022/1/24 - 01 - 24 - 20:50
 * @Description: 管理员密码修改
 * @version: 1.2
 */

public class AdminChangePwd {
    public static String ChangePwd(Connection conn, String oldPwd, String newPwd) {
        //输入新旧管理员密码，首先判断旧密码是否与数据库内的一直，再更新密码
        String res = null;
        try {
            String sql1 = "select * from admin";
            PreparedStatement psql1 = conn.prepareStatement(sql1);
            ResultSet re = psql1.executeQuery();
            re.next();
            String password = re.getString("password");
            if (password.equals(oldPwd)) {
                String sql2 = "update admin set password =?";
                PreparedStatement psql2 = conn.prepareStatement(sql2);
                psql2.setObject(1, newPwd);
                psql2.executeUpdate();
                psql2.close();
                res = "success";
            } else {
                res = "wrongoldpwd";
            }
            re.close();
            psql1.close();
        } catch (SQLException e) {
            e.printStackTrace();
            res = "SQlerror";
        } catch (Exception e) {
            e.printStackTrace();
            res = "error";
        } finally {
            return res;
        }
    }

    public AdminChangePwd() {

    }
}
