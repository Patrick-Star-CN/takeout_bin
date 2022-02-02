package com.takeout.intergration;

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
 * @Date: 2022/1/30 - 01 - 30 - 19:52
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class FetchHistoryServlet extends HttpServlet {
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
        JSONArray data = new JSONArray();
        JSONObject data_ = new JSONObject();
        ServletOutputStream out = resp.getOutputStream();
        String phoneNum = jsonIn.getString("phoneNum");

        Connection conn = Sqlconn.conn();
        List<TakeoutDataHistory> takeoutDataHistoryList = FetchData.fetchData(conn, phoneNum);
        Sqlconn.disconn(conn);
        if(takeoutDataHistoryList == null) {
            jsonOut.put("message", "error");
        } else {
            for(int i = 0; i < takeoutDataHistoryList.size(); i ++) {
                data_.put("imdate", Long.toString(takeoutDataHistoryList.get(i).getDate().getTime()));
                data_.put("location", takeoutDataHistoryList.get(i).getCoordinate());
                data_.put("exdate", Long.toString(takeoutDataHistoryList.get(i).getDate_out().getTime()));
                data.add(data_);
                data_.clear();
            }
            jsonOut.put("message", "success");
            jsonOut.put("data", data);
        }
        out.print(jsonOut.toString());
    }

    public FetchHistoryServlet() {

    }
}
