

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class takeoutLogin {
        /**
         * @Auther codelover123hxy
         * @Date: 2022/1/24
         */

        public static void LoginToInbin(String getAdminName,String getPassword){
                try{
                        String sql="select * from inbindata";
                        PreparedStatement psql=conn.prepareStatement(sql);
                        ResultSet re=psql.executeQuery();
                        String adminName=re.getString("adminName");
                        String password=re.getString("password");
                        if (adminName.equals(getAdminName)!=true)
                        {
                                System.out.println("管理员用户名错误，请重新输入!");
                        }
                        else{
                                if (password.equals(getPassword)!=true)
                                        System.out.println("管理员密码错误，请重新输入！");
                                else {
                                        System.out.println("管理员登录成功");
                                        re.close();
                                        psql.close();
                                }

                }catch (SQLException e){
                        e.printStackTrace();
                }catch (Exception e){
                        e.printStackTrace();
                }
        }
}
