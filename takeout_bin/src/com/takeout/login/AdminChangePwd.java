package com.takeout.login;

import java.sql.*;

/**
 * @Auther: Qing-Qiu
 * @Date: 2022/1/24 - 01 - 24 - 20:50
 * @Description: 管理员密码修改
 * @version: 1.0
 */

public class AdminChangePwd {
    public static void ChangePwd(Connection conn, String oldPwd, String newPwd) {
        try {
            String sql1 = "select * from admin";
            PreparedStatement psql1 = conn.prepareStatement(sql1);
            ResultSet re = psql1.executeQuery();
            re.next();
            String password = re.getString("password");
            if (password.equals(oldPwd)) {
                String sql2 = "update admin set password =? where id =?";
                PreparedStatement psql2 = conn.prepareStatement(sql2);
                psql2.setObject(1, newPwd);
                psql2.executeUpdate();
                System.out.println("密码更改成功！\n您的新密码是 " + newPwd);
                psql2.close();
            } else {
                System.out.println("原密码错误，请重新输入！");
            }
            re.close();
            psql1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
