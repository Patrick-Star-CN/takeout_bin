package com.takeout.intergration;

import com.takeout.mysql.ChangeData;
import com.takeout.mysql.Sqlconn;
import com.takeout.mysql.TakeoutDataInbin;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/30 - 01 - 30 - 10:12
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class AddDataServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //设置编码类型
        resp.setContentType("application/json;charset=utf-8");
        //设置响应头运行ajax跨域访问
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");

        //获取前端传入的json与声明一些需要的对象
        JSONObject jsonIn = JsonReader.receivePost(req);
        JSONObject jsonOut = new JSONObject();
        ServletOutputStream out = resp.getOutputStream();
        TakeoutDataInbin inbin = new TakeoutDataInbin();
        String phoneNum = jsonIn.getString("phoneNum");

        //根据设计好的方法要求，对传入的数据做一些处理
        inbin.setPhoneNum(phoneNum);
        inbin.setCode(phoneNum.substring(phoneNum.length() - 4));
        inbin.setCoordinate(Integer.parseInt(jsonIn.getString("location")));

        //进行数据库连接，完成主题操作
        Connection conn = Sqlconn.conn();
        String res = ChangeData.insertDataToInbin(conn, inbin);
        Sqlconn.disconn(conn);

        //设置好将要传出的json并传出
        jsonOut.put("message", res);
        out.print(jsonOut.toString());
    }

    public AddDataServlet() {

    }
}
