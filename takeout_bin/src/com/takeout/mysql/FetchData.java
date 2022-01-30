package com.takeout.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/24 - 01 - 24 - 20:10
 * @Description: com.takeout.mysql
 * @version: 1.0
 */
public class FetchData {
    public static List<TakeoutDataHistory> fetchData(Connection conn, String phoneNum) {
        //查询某人的历史记录方法
        try {
            String sql = "select * from history where phoneNum = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setString(1, phoneNum);
            ResultSet re = psql.executeQuery();
            //将取出的数据存入history列表中
            List<TakeoutDataHistory> history = new ArrayList<TakeoutDataHistory>();
            while(re.next()) {
                TakeoutDataHistory history_ = new TakeoutDataHistory();
                history_.setPhoneNum(phoneNum);
                history_.setCoordinate(re.getInt("coordinate"));
                history_.setDate(re.getTimestamp("date"));
                history_.setDate_out(re.getTimestamp("date_out"));
                history.add(history_);
            }
            psql.close();
            re.close();
            return history;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<TakeoutDataInbin> fetchData(Connection conn) {
        //查询柜中外卖数据方法
        try {
            Statement statement = conn.createStatement();
            String sql = "select * from inbindata";
            ResultSet re = statement.executeQuery(sql);
            List<TakeoutDataInbin> inbin = new ArrayList<TakeoutDataInbin>();
            while(re.next()) {
                TakeoutDataInbin inbin_ = new TakeoutDataInbin();
                inbin_.setId(re.getInt("id"));
                inbin_.setPhoneNum(re.getString("phoneNum"));
                inbin_.setCoordinate(re.getInt("coordinate"));
                inbin_.setDate(re.getTimestamp("date"));
                inbin.add(inbin_);
            }
            return inbin;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int judge(Connection conn, String code) {
        //传入取货码判断情况方法，传出1为该取货码只有一个对应的柜内外卖或有多个但都为同一个手机号名下的，传出2为该取货码有多个对应柜内外卖且不全都为同一个手机号名下的，传出0为出错或该取货码无对应外卖数据
        try {
            String sql = "select * from inbindata where code = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setString(1, code);
            ResultSet re = psql.executeQuery();
            if(!re.next()) {
                return 0;
            }
            if(re.isLast()) {
                re.close();
                psql.close();
                return 1;
            }
            re.next();
            String phoneNum = re.getString("phoneNum");
            while(re.next()) {
                if(!phoneNum.equals(re.getString("phoneNum"))) {
                    re.close();
                    psql.close();
                    return 2;
                }
            }
            re.close();
            psql.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static TakeoutDataInbin fetchDate(Connection conn, String code) {
        //通过传入取货码来取出外卖
        try {
            String sql = "select * from inbindata where code = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setString(1, code);
            ResultSet re = psql.executeQuery();
            TakeoutDataInbin inbin = new TakeoutDataInbin();
            re.next();
            return getTakeoutDataInbin(psql, re, inbin);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TakeoutDataInbin fetchDateByPhoneNum(Connection conn, String phoneNum) {
        //通过传入取货码来取出外卖
        try {
            String sql = "select * from inbindata where phoneNum = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setString(1, phoneNum);
            ResultSet re = psql.executeQuery();
            re.next();
            TakeoutDataInbin inbin = new TakeoutDataInbin();
            return getTakeoutDataInbin(psql, re, inbin);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static TakeoutDataInbin getTakeoutDataInbin(PreparedStatement psql, ResultSet re, TakeoutDataInbin inbin) throws SQLException {
        //从数据表中提取数据到内存
        inbin.setId(re.getInt("id"));
        inbin.setCoordinate(re.getInt("coordinate"));
        inbin.setDate(re.getTimestamp("date"));
        inbin.setPhoneNum(re.getString("phoneNum"));
        inbin.setCode(re.getString("code"));
        re.close();
        psql.close();
        return inbin;
    }

    public FetchData() {

    }
}
