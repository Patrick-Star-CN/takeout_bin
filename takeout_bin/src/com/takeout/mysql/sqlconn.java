package com.takeout.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:07
 * @Description: com.takeout.mysql
 * @version: 1.5
 */
public class sqlconn {
    static Connection conn;//数据库会话

    public static List<TakeoutDataHistory> fetchData(String consigneeName) {
        //查询某人的历史记录方法
        try {
            String sql = "select * from history where consigneeName = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setString(1, consigneeName);
            ResultSet re = psql.executeQuery();
            //将取出的数据存入history列表中
            List<TakeoutDataHistory> history = new ArrayList<TakeoutDataHistory>();
            TakeoutDataHistory history_ = new TakeoutDataHistory();
            while(re.next()) {
                history_.setPhoneNum(re.getString("phoneNum"));
                history_.setCoordinate(re.getInt("coordinate"));
                history_.setConsigneeName(consigneeName);
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

    public static void insertDataToInbin(TakeoutDataInbin inbin) {
        //在柜内数据表中添加新的数据
        try {
            PreparedStatement psql = conn.prepareStatement("insert into inbindata (code, phoneNum, coordinate, consigneeName, date)" + "values(?, ?, ?, ?, ?)");  //用preparedStatement预处理来执行sql语句
            psql.setString(1, inbin.getCode());
            psql.setString(2, inbin.getPhoneNum());
            psql.setInt(3, inbin.getCoordinate());
            psql.setString(4, inbin.getConsigneeName());
            java.util.Date utilDate = new java.util.Date();
            Timestamp date = new Timestamp(utilDate.getTime());
            psql.setTimestamp(5, date);
            psql.executeUpdate();
            psql.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<TakeoutDataInbin> fetchData() {
        //查询柜中外卖数据方法
        try {
            Statement statement = conn.createStatement();
            String sql = "select * from inbindata";
            ResultSet re = statement.executeQuery(sql);
            List<TakeoutDataInbin> inbin = new ArrayList<TakeoutDataInbin>();
            TakeoutDataInbin inbin_ = new TakeoutDataInbin();
            while(re.next()) {
                inbin_.setId(re.getInt("id"));
                inbin_.setPhoneNum(re.getString("phoneNum"));
                inbin_.setCoordinate(re.getInt("coordinate"));
                inbin_.setConsigneeName(re.getString("consigneeName"));
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

    public static void moveData(int id) {
        //将目标id的外卖数据从inbindata数据表中取出并删除，正则存入history数据表中的方法
        try {
            //取出
            String sql = "select * from inbindata where id = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet re = psql.executeQuery();
            Timestamp date, date_out;
            java.util.Date utilDate_out;
            TakeoutDataHistory history = new TakeoutDataHistory();
            while(re.next()) {
                utilDate_out = new java.util.Date();
                date_out = new Timestamp(utilDate_out.getTime());
                date = re.getTimestamp("date");
                history.setPhoneNum(re.getString("phoneNum"));
                history.setCoordinate(re.getInt("coordinate"));
                history.setConsigneeName(re.getString("consigneeName"));
                history.setDate(date);
                //存入
                var psqlNew = conn.prepareStatement("insert into history (phoneNum, coordinate, consigneeName, date, date_out)" + "values(?, ?, ?, ?, ?)");
                psqlNew.setString(1, history.getPhoneNum());
                psqlNew.setInt(2, history.getCoordinate());
                psqlNew.setString(3, history.getConsigneeName());
                psqlNew.setTimestamp(4, history.getDate());
                psqlNew.setTimestamp(5, date_out);
                psqlNew.executeUpdate();
                psqlNew.close();
            }
            PreparedStatement psqlDel;
            //删除
            psqlDel = conn.prepareStatement("delete from inbindata where id = ?");
            psqlDel.setFloat(1, id);
            psqlDel.executeUpdate();
            psqlDel.close();
            psql.close();
            re.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int judge(String code) {
        //传入取货码判断情况方法，传出1为该取货码只有一个对应的柜内外卖或有多个但都为同一个手机号名下的，传出2为该取货码有多个对应柜内外卖且不全都为同一个手机号名下的，传出0为出错或该取货码无对应外卖数据
        try {
            String sql = "select * from inbindata where code = ?";
            PreparedStatement psql = conn.prepareStatement(sql;
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
            re.next();
            while(re.next()) {
                if(re.getString("phoneNum") != phoneNum) {
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

    public static TakeoutDataInbin fetchDate(String code) {
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

    private static TakeoutDataInbin getTakeoutDataInbin(PreparedStatement psql, ResultSet re, TakeoutDataInbin inbin) throws SQLException {
        //从数据表中提取数据到内存
        inbin.setId(re.getInt("id"));
        inbin.setCoordinate(re.getInt("coordinate"));
        inbin.setConsigneeName(re.getString("consigneeName"));
        inbin.setDate(re.getTimestamp("date"));
        inbin.setPhoneNum(re.getString("phoneNum"));
        inbin.setCode(re.getString("code"));
        re.close();
        psql.close();
        return inbin;
    }

    public static TakeoutDataInbin fetchDateByPhoneNum(String phoneNum) {
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

    public static void conn() {
        //数据库连接方法
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/takeout_bin";
        String user = "root";
        String password = "cxcxcx4,";
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconn() {
        //数据库结束连接方法
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
