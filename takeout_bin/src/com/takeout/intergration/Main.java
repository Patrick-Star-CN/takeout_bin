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
            String phoneNum;
            Connection conn = Sqlconn.conn();
            TakeoutDataInbin inbin = new TakeoutDataInbin();
            phoneNum = sc.next();
            inbin.setPhoneNum(phoneNum);
            inbin.setCode(phoneNum.substring(phoneNum.length() - 4));
            inbin.setCoordinate(sc.nextInt());
            ChangeData.insertDataToInbin(conn, inbin);
            Sqlconn.disconn(conn);
        } else if(typeNum == 2) {
            Connection conn = Sqlconn.conn();
            List<TakeoutDataInbin> inbin = FetchData.fetchData(conn);
            if(inbin.size() != 0) {
                System.out.println(inbin.get(0).getPhoneNum());
            } else {
                System.out.println("Sorry, this bin doesn't have any takeout!");
            }
            Sqlconn.disconn(conn);
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
            Connection conn = Sqlconn.conn();
            String code = sc.next();
            if(FetchData.judge(conn, code) == 1) {
                TakeoutDataInbin inbin = FetchData.fetchDate(conn, code);
                System.out.println(inbin.getId() + " " + inbin.getPhoneNum());
                ChangeData.moveData(conn, inbin.getId());
            } else if(FetchData.judge(conn, code) == 2) {
                System.out.println("Please scan your whole phone number...");
                String phoneNum = sc.next();
                TakeoutDataInbin inbin = FetchData.fetchDateByPhoneNum(conn, phoneNum);
                if(inbin == null) {
                    System.out.println("Sorry, you don't have any takeout in this bin!");
                } else {
                    System.out.println(inbin.getId() + " " + inbin.getPhoneNum());
                    ChangeData.moveData(conn, inbin.getId());
                }
            } else {
                System.out.println("Sorry, you don't have any takeout in this bin!");
            }
            Sqlconn.disconn(conn);
        }
    }
}
