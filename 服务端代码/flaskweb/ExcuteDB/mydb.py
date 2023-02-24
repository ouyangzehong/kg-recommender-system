# -*- coding = utf-8 -*-
# @Time : 2022-05-22 12:40
# @Author : 欧阳泽洪
# @File : mydb.py
# @Software : PyCharm

# 导入sqlite3模块
from sqlite3 import Error
import sqlite3


# 1.建表
def creat_table(db_file, sql):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    print('连接成功')
    cour.execute(sql)  # 创建表的命令
    print("建表成功")
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 2.增加单条student信息
def insert_oneData(db_file, sql, onedata):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()

    # 编写sql语句
    # 添加语句sql：INSERT INTO 表名(列名)
    # VALUES (<列1的值>,[<列2的值>,<列3的值>]);
    # ?-占位符，在cour.execute()参数中，传入数据元组
    # 由于id设置了自增，不需要我们进行输入

    cour.execute(sql, onedata)

    # 提交数据-同步到数据库文件-增删改查，除了查询以外有需要进行提交
    conn.commit()
    # 打印受影响的行数
    print(cour.rowcount)
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 3.增加多条信息
def insert_manyData(db_file, sql, onedata):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    # 执行sql语句
    cour.executemany(sql, onedata)
    conn.commit()
    # 打印受影响的行数
    print(cour.rowcount)
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 4.修改数据
def update_Data(db_file, sql, student_data):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    # 编写sql语句
    # 修改语句sql：UPDATE  <表名>
    # SET  <列名1>=<值1>[,<列名2>=<值2>]
    # [WHERE <条件>];
    # ?-占位符，在cour.execute()参数中，传入数据元组
    # 由于id设置了主键、自增，不能修改

    # 执行sql语句
    cour.execute(sql, student_data)
    # 提交数据-同步到数据库文件-增删改查，除了查询以外有需要进行提交
    conn.commit()
    # 打印受影响的行数
    print(cour.rowcount)
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 5.删除一条数据
def delet_oneData(db_file, sql, student_data):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    # 编写sql语句
    # 删除语句sql：DELETE FROM 表名
    # WHERE <列名1>=<值1>
    # ?-占位符，在cour.execute()参数中，传入数据元组

    # 执行sql语句
    cour.execute(sql, student_data)
    # 提交数据-同步到数据库文件-增删改查，除了查询以外有需要进行提交
    conn.commit()
    # 打印受影响的行数
    print(cour.rowcount)
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 6.删除多条数据
def delet_manyData(db_file, sql, student_data):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    # 编写sql语句
    # 删除语句sql：DELETE FROM 表名
    # WHERE <列名1>=<值1>
    # ?-占位符，在cour.execute()参数中，传入数据元组

    # 执行sql语句
    cour.executemany(sql, student_data)
    # 提交数据-同步到数据库文件-增删改查，除了查询以外有需要进行提交
    conn.commit()
    # 打印受影响的行数
    print(cour.rowcount)
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 7.查找某一条数据
def fetch_oneData(db_file, sql, name):
    # 连接数据库
    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    # 编写sql语句
    # 查询语句sql：select 列名(*-所有列) from 表名 [where 条件]
    # ?-占位符，在cour.execute()参数中，传入数据元组

    # 执行sql语句
    cour.execute(sql, name)
    # 打印查询结果
    print(cour.fetchall())
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()


# 8.查询所有数据
def fetch_allData(db_file, sql):
    # 数据库文件路径

    conn = sqlite3.connect(db_file)
    # 创建游标
    cour = conn.cursor()
    # 编写sql语句
    # 查询语句sql：select 列名(*-所有列) from 表名 [where 条件]

    # 执行sql语句
    cour.execute(sql)
    # 打印查询结果
    result=cour.fetchall()
    # 关闭游标
    cour.close()
    # 关闭连接
    conn.close()
    return result


def help():
    db_file = 'student_data.db'
    # 1.建表
    sql = 'create table student2(id integer primary key autoincrement, sname varchar(20) not null , sage int not null)'
    creat_table(db_file, sql)

    # 2.插入一个数据
    sql = 'INSERT INTO student(sname, sage) VALUES (?, ?);'
    student_data = ('沐沐', 18)
    insert_oneData(db_file, sql, student_data)

    # 3.插入多个数据
    sql = 'INSERT INTO student(sname, sage) VALUES (?, ?);'
    onedata = [('若若', 19), ('柜子', 20)]
    insert_manyData(db_file, sql, onedata)

    # 4.更新数据
    sql = 'update student set sname=?, sage=? where id=?'
    student_data = ('小小', 18, 1)
    update_Data(db_file, sql, student_data)

    # 5.删除一个数据
    sql = 'delete from student where id=?'
    student_data = (1,)
    delet_oneData(db_file, sql, student_data)

    # 6.删除多个数据
    sql = 'delete from student where id=?'
    student_data = [(2,), (3,), (4,)]
    delet_manyData(db_file, sql, student_data)

    # 7.查询单个数据
    sql = 'select * from student where sname=?'
    name = ('柜子',)
    fetch_oneData(db_file, sql, name)

    # 8.查询多个数据
    sql = 'select * from student'
    fetch_allData(db_file, sql)


# 创建一个hellofood数据库 创建一个用户账号表user_account


if __name__ == '__main__':
    db_file = 'hellofood.db'
    # 一、创建一个hellofood数据库 创建一个用户账号表user_account
    # sql = 'create table user_account(id integer primary key autoincrement,user_id  varchar(20) not null , user_name varchar(20), user_password varchar(20) not null)'
    # creat_table(db_file,sql)

    # 二、插入一个数据 用户id：ouyang 昵称：欧阳 密码：123456
    # sql = 'INSERT INTO user_account(user_id,user_name,user_password) VALUES (?, ?,?);'
    # user_data = ('ouyang',"欧阳", "123456")
    # insert_oneData(db_file, sql, user_data)

    # 三、返回所有用户账户信息
    sql = 'select * from user_account'
    data=fetch_allData(db_file, sql)
    print(data)




