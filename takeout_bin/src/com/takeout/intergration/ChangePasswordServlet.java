package com.takeout.intergration;

import com.takeout.login.AdminChangePwd;
import com.takeout.mysql.FetchData;
import com.takeout.mysql.Sqlconn;
import com.takeout.mysql.TakeoutDataHistory;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/31 - 01 - 31 - 14:09
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class ChangePasswordServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //设置编码类型
        resp.setContentType("application/json;charset=utf-8");
        //设置响应头运行ajax跨域访问
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");

        //处理传入的json并声明一些需要的对象
        JSONObject jsonIn = JsonReader.receivePost(req);
        JSONObject jsonOut = new JSONObject();
        ServletOutputStream out = resp.getOutputStream();
        String oldPassword = jsonIn.getString("oldpwd");
        String newPassword = jsonIn.getString("newpwd");

        //完成数据库连接与一些主要操作
        Connection conn = Sqlconn.conn();
        String res = AdminChangePwd.ChangePwd(conn, oldPassword, newPassword);
        Sqlconn.disconn(conn);

        //配置传出所需的json并发送
        jsonOut.put("message", res);
        out.print(jsonOut.toString());
    }

    public ChangePasswordServlet() {

    }
}
