package com.takeout.intergration;

import com.takeout.login.AdminLogin;
import com.takeout.mysql.*;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:29
 * @Description: com.takeout.intergration
 * @version: 2.1
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int typeNum = sc.nextInt();
        if(typeNum == 1) {

        } else if(typeNum == 2) {

        } else if(typeNum == 3) {
            Connection conn = Sqlconn.conn();
            List<TakeoutDataHistory> history = FetchData.fetchData(conn, sc.next());
            if(history.size() != 0) {
                System.out.println(history.get(0).getPhoneNum());
            } else {
                System.out.println("Sorry, you don't have any data of takeout in history!");
            }
            Sqlconn.disconn(conn);
        } else if(typeNum == 4) {

        } else if(typeNum == 5) {

        }
    }
}
