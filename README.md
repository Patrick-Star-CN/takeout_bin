# takeout_bin

# 一些简单概述

这是牛牛马马团队在大一上寒假计院软件设计大赛中的参赛作品。

制作的是一款外卖柜操作软件，可以给予用户、外卖员、管理员一些操控外卖柜的功能。

语言：java（后端） + 

# mysql数据库设计约定

库名：takeout_bin

## 数据表一

表名：inbindata

|  字段名  |  字段类型  |  字段长度  |  备注  |
|  ----  |  ----  |  ----  |  ----  |
|  id  |  INT UNSIGNED  |  255  |  NOT NULL, KEY, AUTO_INCREMENT  |
|  coordinate_X  |  INT UNSIGNED  |  255  |  NOT NULL  |
|  coordinate_Y  |  INT UNSIGNED  |  255  |  NOT NULL  |
|  consigneeName  |  VARCHAR  |  255  |  NOT NULL  |
|  date  |  DATETIME  |  0  |  NOT NULL  |

## 数据表二

表名：history

|  字段名  |  字段类型  |  字段长度  |  备注  |
|  ----  |  ----  |  ----  |  ----  |
|  coordinate_X  |  INT UNSIGNED  |  255  |  NOT NULL  |
|  coordinate_Y  |  INT UNSIGNED  |  255  |  NOT NULL  |
|  consigneeName  |  VARCHAR  |  255  |  NOT NULL, KEY1  |
|  date  |  DATETIME  |  0  |  NOT NULL, KEY2  |

# mysql方法接口

``` java
public static void sqlconn.conn() //用于连接预设好的数据库

public static void sqlconn.disconn() //用于断开与数据库的连接

public static void sqlconn.insertDataToInbin(TakeoutDataInbin inbin) //传入存好x坐标、y坐标、收货人id的结构体，自动生成存货时间，并存入inbindata数据表

public static List<TakeoutDataInbin> fetchData() //查询并传出柜中存着所有外卖数据，包括x坐标、y坐标、收货人id、存货时间

public static List<TakeoutDataHistory> fetchData(String consigneeName) //传入所需用户id，传出他在history数据表中的历史外卖数据，包括x坐标、y坐标、存货时间

public static void moveData(int id) //传入所取外卖的数据id，将该条数据复制到history数据表中，并删除inbindata数据表中对应那条
```