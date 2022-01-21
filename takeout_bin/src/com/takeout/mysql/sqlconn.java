package com.takeout.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/21 - 01 - 21 - 0:39
 * @Description: com.takeout.mysql
 * @version: 1.0
 */
public class sqlconn {
    //属性
    static Connection conn;//数据库会话

    //方法
    public static List<TakeoutDataHistory> fetchData(String consigneeName) {
        //查询某人的历史记录方法
        try {
            String sql = "select * from history where consigneeName = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setString(1, consigneeName);
            ResultSet re = psql.executeQuery();
            int coordinate_X, coordinate_Y;
            Timestamp date;
            List<TakeoutDataHistory> history = new ArrayList<TakeoutDataHistory>();
            TakeoutDataHistory his = new TakeoutDataHistory();
            while(re.next()) {
                coordinate_X = re.getInt("coordinate_X");
                coordinate_Y = re.getInt("coordinate_Y");
                date = re.getTimestamp("date");
                his.setCoordinate_X(coordinate_X);
                his.setCoordinate_Y(coordinate_Y);
                his.setConsigneeName(consigneeName);
                his.setDate(date);
                history.add(his);
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
            PreparedStatement psql = conn.prepareStatement("insert into inbindata (coordinate_X, coordinate_Y, consigneeName, date)" + "values(?, ?, ?, ?)");  //用preparedStatement预处理来执行sql语句
            psql.setInt(1, inbin.getCoordinate_X());
            psql.setInt(2, inbin.getCoordinate_Y());
            psql.setString(3, inbin.getConsigneeName());
            java.util.Date utilDate = new java.util.Date();
            Timestamp date = new Timestamp(utilDate.getTime());
            psql.setTimestamp(4, date);
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
            int coordinate_X, coordinate_Y, id;
            Timestamp date;
            String consigneeName;
            List<TakeoutDataInbin> inbin = new ArrayList<TakeoutDataInbin>();
            TakeoutDataInbin inbin_ = new TakeoutDataInbin();
            while(re.next()) {
                coordinate_X = re.getInt("coordinate_X");
                coordinate_Y = re.getInt("coordinate_Y");
                date = re.getTimestamp("date");
                consigneeName = re.getString("consigneeName");
                id = re.getInt("id");
                inbin_.setId(id);
                inbin_.setCoordinate_X(coordinate_X);
                inbin_.setCoordinate_Y(coordinate_Y);
                inbin_.setConsigneeName(consigneeName);
                inbin_.setDate(date);
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
        try {
            String sql = "select * from inbindata where id = ?";
            PreparedStatement psql = conn.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet re = psql.executeQuery();
            int coordinate_X, coordinate_Y;
            Timestamp date;
            String consigneeName;
            TakeoutDataHistory history = new TakeoutDataHistory();
            while(re.next()) {
                coordinate_X = re.getInt("coordinate_X");
                coordinate_Y = re.getInt("coordinate_Y");
                consigneeName = re.getString("consigneeName");
                date = re.getTimestamp("date");
                history.setCoordinate_X(coordinate_X);
                history.setCoordinate_Y(coordinate_Y);
                history.setConsigneeName(consigneeName);
                history.setDate(date);
                PreparedStatement psqlNew = conn.prepareStatement("insert into inbindata (coordinate_X, coordinate_Y, consigneeName, date)" + "values(?, ?, ?, ?)");
                psqlNew.setInt(1, coordinate_X);
                psqlNew.setInt(2, coordinate_Y);
                psqlNew.setString(3, consigneeName);
                psqlNew.setTimestamp(4, date);
                psqlNew.executeUpdate();
                psqlNew.close();
            }
            PreparedStatement psqlDel;
            psqlDel = conn.prepareStatement("delete from inbindata where id = ");
            psqlDel.setInt(1, id);
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

    public static void conn() {
        //数据库连接方法
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/takeout_bin";
        String user = "root";
        String password = "cxcxcx4,";
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if(!conn.isClosed()) {
                System.out.println("Succeeded connection to the Database!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, can't find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
