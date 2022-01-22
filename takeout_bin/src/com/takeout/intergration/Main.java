package com.takeout.intergration;

import com.takeout.mysql.TakeoutDataHistory;
import com.takeout.mysql.TakeoutDataInbin;
import com.takeout.mysql.sqlconn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/22 - 01 - 22 - 12:29
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int typeNum = sc.nextInt();
        if(typeNum == 1) {
            String phoneNum;
            sqlconn.conn();
            TakeoutDataInbin inbin = new TakeoutDataInbin();
            phoneNum = sc.next();
            inbin.setPhoneNum(phoneNum);
            inbin.setCode(phoneNum.substring(phoneNum.length() - 4));
            inbin.setCoordinate(sc.nextInt());
            inbin.setConsigneeName(sc.next());
            sqlconn.insertDataToInbin(inbin);
            sqlconn.disconn();
        } else if(typeNum == 2) {
            sqlconn.conn();
            List<TakeoutDataInbin> inbin = sqlconn.fetchData();
            if(inbin.size() != 0) {
                System.out.println(inbin.get(0).getPhoneNum());
            } else {
                System.out.println("Sorry, this bin doesn't have any takeout!");
            }
            sqlconn.disconn();
        } else if(typeNum == 3) {
            sqlconn.conn();
            List<TakeoutDataHistory> history = sqlconn.fetchData(sc.next());
            if(history.size() != 0) {
                System.out.println(history.get(0).getPhoneNum());
            } else {
                System.out.println("Sorry, you don't have any data of takeout in history!");
            }
            sqlconn.disconn();
        } else if(typeNum == 4) {
            sqlconn.conn();
            sqlconn.moveData(sc.nextInt());
            sqlconn.disconn();
        } else if(typeNum == 5) {
            sqlconn.conn();
            String code = sc.next();
            if(sqlconn.judge(code) == 1) {
                TakeoutDataInbin inbin = sqlconn.fetchDate(code);
                System.out.println(inbin.getId() + " " + inbin.getPhoneNum());
                sqlconn.moveData(inbin.getId());
            } else if(sqlconn.judge(code) == 2) {
                System.out.println("Please scan your whole phone number...");
                String phoneNum = sc.next();
                TakeoutDataInbin inbin = sqlconn.fetchDateByPhoneNum(phoneNum);
                if(inbin == null) {
                    System.out.println("Sorry, you don't have any takeout in this bin!");
                } else {
                    System.out.println(inbin.getId() + " " + inbin.getPhoneNum());
                    sqlconn.moveData(inbin.getId());
                }
            } else {
                System.out.println("Sorry, you don't have any takeout in this bin!");
            }
            sqlconn.disconn();
        }
    }
}
