package com.takeout.intergration;

import com.takeout.mysql.ChangeData;
import com.takeout.mysql.FetchData;
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
 * @Date: 2022/1/30 - 01 - 30 - 11:39
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class MoveDataServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //设置编码类型
        resp.setContentType("application/json;charset=utf-8");
        //设置响应头运行ajax跨域访问
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");

        JSONObject jsonIn = JsonReader.receivePost(req);
        JSONObject jsonOut = new JSONObject();
        ServletOutputStream out = resp.getOutputStream();
        String phoneNum = jsonIn.getString("phoneNum");
        int id;
        String res = null;

        Connection conn = Sqlconn.conn();
        TakeoutDataInbin data = FetchData.fetchDateByPhoneNum(conn, phoneNum);
        if(data != null) {
            id = data.getId();
            ChangeData.moveData(conn, id);
            res = Integer.toString(data.getCoordinate());
        } else {
            res = "error";
        }
        Sqlconn.disconn(conn);

        jsonOut.put("message", res);
        out.print(jsonOut.toString());
    }

    public MoveDataServlet() {

    }
}
