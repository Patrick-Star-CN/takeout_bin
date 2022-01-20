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