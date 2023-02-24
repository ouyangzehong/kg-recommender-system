# -*- coding = utf-8 -*-
# @Time : 2022-05-18 20:11
# @Author : 欧阳泽洪
# @File : modultwo.py
# @Software : PyCharm
import xlrd
def getfruitfordm(path):
    book = xlrd.open_workbook(path)
    sheet = book.sheets()[0]
    print(sheet.nrows)
    datalist = []
    for i in range(1, sheet.nrows):
        data={}
        # 食物名称 热量 图片 功效
        data["name"]=sheet.cell_value(i, 0)
        data["heat"]=sheet.cell_value(i, 1)
        data["image"]=sheet.cell_value(i, 2)
        data["effect"]=sheet.cell_value(i, 3)

        datalist.append(data)
    return datalist



if __name__ == '__main__':
    datalist=getfruitfordm("详细版本糖尿病水果.xls")
    datalist2=getfruitfordm("详细版糖尿病适宜食用蔬菜.xls")
    datalist3=getfruitfordm("详细版糖尿病适宜食用谷物豆类.xls")
    print(datalist3)
