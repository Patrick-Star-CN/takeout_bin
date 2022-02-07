# takeout_bin

# summary

This is a project of takeout_bin, which is created by Team Cattle&Horse, for the Winter Vacation Software Design Competition of School of computer scinece and software, ZJUT.

front-end: HTML + CSS + JavaScript

back-end: Java

# Packages

* commons-beanutils-1.9.3.jar
* commons-collections-3.2.1.jar
* commons-lang-2.6.jar
* commons-logging-1.2.jar
* ezmorph-1.0.6.jar
* json-lib-2.4-jdk15.jar
* mysql-connector-java-8.0.28.jar

# MySQL Database Disign Convention

database name: takeout_bin

## the first data table: inbindata

|  field name  |  field type  |  field lenth  |  remarks  |
|  ----  |  ----  |  ----  |  ----  |
|  id  |  int unsigned  |  255  |  NOT NULL, KEY, AUTO_INCREMENT  |
|  code  |  varchar  |  4  |  NOT NULL  |
|  phoneNum  |  varchar  |  255  |  NOT NULL  |
|  coordinate  |  int unsigned  |  255  |  NOT NULL  |
|  date  |  timestamp  |  6  |  NOT NULL  |

## the second data table: history

|  field name  |  field type  |  field lenth  |  remarks  |
|  ----  |  ----  |  ----  |  ----  |
|  phoneNum  |  varchar  |  255  |  NOT NULL, KEY1  |
|  coordinate  |  int unsigned  |  255  |  NOT NULL  |
|  date  |  timestamp  |  6  |  NOT NULL, KEY2  |
|  date_out  |  timestamp  |  6  |  NOT NULL  |

## the third data table: admin

|  field name  |  field type  |  field lenth  |  remarks  |
|  ----  |  ----  |  ----  |  ----  |
|  adminName  |  varchar  |  255  |  NOT NULL  |
|  password  |  varchar  |  255  |  NOT NULL  |

# MySQL Method

``` java
package com.takeout.mysql;

public static Connection Sqlconn.conn() //connect to the database whose parameters have been fixed
/*returns:
    conn - the Connection to database 
Returns:
    a connection to database */

public static void Sqlconn.disconn(connection conn) //break the connection to database
/* Parameters: 
    conn - the connection to database */

public static String ChangeData.insertDataToInbin(Connection conn, TakeoutDataInbin inbin) //add data to Table inbindata
/*Parameters:
    conn - the connection to database
    inbin - the data structure of takeout, include phoneNum, code, coordinate, date 
Returns:
    a result message of the method */

public static String ChangeData.moveData(Connection conn, int id) //move the data from inbindata to history when the takeout has been taken out
/*Parameters:
    conn - the connection to database
    id - the id number of takeout which has been taken out 
Returns:
    a result message of the method */

public static List<TakeoutDataHistory> FetchData.fetchData(Connection conn, String phoneNum) //fetch the datas of user in Table history by user's phone number
/*Parameters:
    conn - the connection to database
    phoneNum - the phone number of user
Returns:
    a list of this user's takeoutdata in Table history */

public static List<TakeoutDataInbin> FetchData.fetchData(Connection conn) //fetch the all datas in Table inbindata
/*Parameters:
    conn - the connection to database
Returns:
    a list of takeoutdata in Table inbindata */

public static int FetchData.judge(Connection conn, String code) //judge the status of this code
/*Parameters:
    conn - the connection to database
Returns:
    a type number: 
        1 means the code has only one takeout or has more takeout but they belong to the same phone number
        2 means the code has 2 or more takeout and they don't belong to the same phone number
        0 means the code has no takeout or error */

public static TakeoutDataInbin FetchData.fetchDate(Connection conn, String code) //fetch the top takeout data of the code
/*Parameters:
    conn - the connection to database
    code - the last 4 of consignee's phone number
Returns:
    the data of the consignee's top takeout data in inbindata */

public static TakeoutDataInbin FetchData.fetchDateByPhoneNum(Connection conn, String phoneNum)  //fetch the top takeout data of the phone number
/*Parameters:
    conn - the connection to database
    phoneNum - the consignee's phone number
Returns:
    the data of the consignee's top takeout data in inbindata */
```

# adminLogin Method

``` java
package com.takeout.login;

public static String AdminLogin.LoginToInbin(Connection conn, String getAdminName, String getPassword) //admin login
/*Parameters:
    conn - the connection to database
    getAdminName - the name of admin
    getPassword - the password of admin
Returns:
    a message of login state */
```

# Usage

```sh
cd takeout_bin

# open file index.html
# choose your identity to login
```