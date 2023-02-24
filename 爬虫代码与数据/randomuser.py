# -*- coding = utf-8 -*-
# @Time : 2022-05-07 22:39
# @Author : 欧阳泽洪
# @File : randomuser.py
# @Software : PyCharm
import xlrd
import xlwt  # 写数据
import random  #随机数

def main():
    # 1.制造数据
    datalist=[]
    # 读进来食物数据
    foodtitle=[]
    book = xlrd.open_workbook("food.xls")
    sheet = book.sheets()[0]
    print(sheet.nrows)
    s_food=set()
    for i in range(1, sheet.nrows):
        # 食物名称 及种类
        if sheet.cell_value(i, 0) in s_food:
            print("去重" + sheet.cell_value(i, 0))
            continue
        s_food.add(sheet.cell_value(i, 0))
        tittle = sheet.cell_value(i, 0)
        tittle = "\"" + tittle + "\""
        foodtitle.append(tittle)

    # 生成用户评分数据

    for item in foodtitle:
        # 随机生成（0，50）评价人数 随机生成（1，300）之间不重复的用户id 随机生成(0,5)的评分
        number=random.randint(0, 50)
        userid=random.sample(range(1,300),number)
        print(number)
        for i in range(number):
            datausergrade = []
            id=userid[i]
            grade=random.randint(0,5)
            datausergrade.append(id) # 添加id
            datausergrade.append(item) # 添加食物名
            datausergrade.append(grade) # 添加评分
            datalist.append(datausergrade)
            print(datausergrade)
    # 存储数据
    print(datalist)
    out_foodgrade = open("out_foodgrade.csv", "w", encoding='utf-8')
    out_foodgrade.write("userid,tittle,grade\n")
    for i in datalist:
        out_foodgrade.write(f"{i[0]},{i[1]},{i[2]}\n")
    out_foodgrade.close()


if __name__ == '__main__':
    main()
    print("数据制造完毕")