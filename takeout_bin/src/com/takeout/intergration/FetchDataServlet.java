package com.takeout.intergration;

import com.takeout.mysql.FetchData;
import com.takeout.mysql.Sqlconn;
import com.takeout.mysql.TakeoutDataInbin;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/29 - 01 - 29 - 10:42
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class FetchDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码类型
        response.setContentType("application/json;charset=utf-8");
        //设置响应头运行ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        JSONObject jsonOut = new JSONObject();
        JSONArray data = new JSONArray();
        JSONObject data_ = new JSONObject();
        ServletOutputStream out = response.getOutputStream();

        Connection conn = Sqlconn.conn();
        List<TakeoutDataInbin> takeoutDataInbinList = FetchData.fetchData(conn);
        Sqlconn.disconn(conn);

        for(int i = 0; i < takeoutDataInbinList.size(); i ++) {
            data_.put("id", Integer.toString(takeoutDataInbinList.get(i).getId()));
            data_.put("phoneNum", takeoutDataInbinList.get(i).getPhoneNum());
            data_.put("date", Long.toString(takeoutDataInbinList.get(i).getDate().getTime()));
            data_.put("location", takeoutDataInbinList.get(i).getCoordinate());
            data.add(data_);
            data_.clear();
        }
        jsonOut.put("data", data);
        out.print(jsonOut.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public FetchDataServlet() {

    }
}
