package com.takeout.intergration;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/24 - 01 - 24 - 20:50
 * @Description: 管理员登录servlet
 * @version: 1.7
 */

import com.takeout.login.AdminLogin;
import com.takeout.mysql.Sqlconn;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码类型
        response.setContentType("application/json;charset=utf-8");
        //设置响应头运行ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        JSONObject jsonIn = JsonReader.receivePost(request);
        JSONObject jsonOut = new JSONObject();
        ServletOutputStream out = response.getOutputStream();

        Connection conn = Sqlconn.conn();
        String res = AdminLogin.LoginToInbin(conn, jsonIn.getString("name"), jsonIn.getString("password"));
        Sqlconn.disconn(conn);
        jsonOut.put("message", res);
        out.print(jsonOut.toString());
    }

    public LoginServlet() {

    }
}
