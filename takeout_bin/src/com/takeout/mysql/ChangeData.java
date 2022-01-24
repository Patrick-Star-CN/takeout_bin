package com.takeout.mysql;

import java.sql.*;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/24 - 01 - 24 - 20:19
 * @Description: com.takeout.mysql
 * @version: 1.0
 */
public class ChangeData {
    public static void insertDataToInbin(Connection conn, TakeoutDataInbin inbin) {
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



    public static void moveData(Connection conn, int id) {
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
}
