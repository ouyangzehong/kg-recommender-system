# -*- coding = utf-8 -*-
# @Time : 2022-05-07 10:40
# @Author : 欧阳泽洪
# @File : prehandle.py
# @Software : PyCharm

import xlrd
import string


# 提取食物营养素
def getoutfood():
    s_food = set()

    out_food = open("../out_food.csv", "w", encoding='utf-8')
    out_food.write("tittle,imagesrc,head,cho,fat,pr,e460\n")
    # 打开excel
    book = xlrd.open_workbook("../food.xls")
    sheet = book.sheets()[0]
    print(sheet.nrows)
    data = []
    for i in range(1, sheet.nrows):
        # 食物名称 及其营养素
        if sheet.cell_value(i, 0) in s_food:
            print("去重" + sheet.cell_value(i, 0))
            continue
        s_food.add(sheet.cell_value(i, 0))
        tittle = sheet.cell_value(i, 0)
        tittle = "\"" + tittle + "\""
        head = sheet.cell_value(i, 3)
        head = "\"" + head + "\""
        cho = sheet.cell_value(i, 4)
        cho = "\"" + cho + "\""
        fat = sheet.cell_value(i, 5)
        fat = "\"" + fat + "\""
        pr = sheet.cell_value(i, 6)
        pr = "\"" + pr + "\""
        e460 = sheet.cell_value(i, 7)
        e460 = "\"" + e460 + "\""
        imagesrc = sheet.cell_value(i, 1)
        imagesrc = "\"" + imagesrc + "\""
        out_food.write(f"{tittle},{imagesrc},{head},{cho},{fat},{pr},{e460}\n")
    out_food.close()


# 提取食物种类数据
def getfoodkind():
    s_food = set()

    out_kind = open("../out_kind.csv", "w", encoding='utf-8')
    out_kind.write("tittle,kind\n")
    # 打开excel
    book = xlrd.open_workbook("../food.xls")
    sheet = book.sheets()[0]
    print(sheet.nrows)

    for i in range(1, sheet.nrows):
        # 食物名称 及种类
        if sheet.cell_value(i, 0) in s_food:
            print("去重" + sheet.cell_value(i, 0))
            continue
        s_food.add(sheet.cell_value(i, 0))
        tittle = sheet.cell_value(i, 0)
        tittle = "\"" + tittle + "\""
        kind = sheet.cell_value(i, 8)
        kind = "\"" + kind + "\""
        out_kind.write(f"{tittle},{kind}\n")
    out_kind.close()


# 提取食物特征
# 选取规则：GB 28050-2011《食品安全国家标准 预包装食品营养标签通则》
def getfoodfeature():
    s_food = set()
    s_food2 = set()


    out_feature = open("../out_feature.csv", "w", encoding='utf-8')
    out_feature.write("tittle,feature\n")
    # 打开foodexcel
    book = xlrd.open_workbook("../food.xls")
    sheet = book.sheets()[0]
    print(sheet.nrows)

    for i in range(1, sheet.nrows):
        # 食物名称 及种类
        if sheet.cell_value(i, 0) in s_food:
            print("去重" + sheet.cell_value(i, 0))
            continue
        s_food.add(sheet.cell_value(i, 0))
        head=float(sheet.cell_value(i, 3))
        cho = sheet.cell_value(i, 4)
        cho=float(cho)
        fat = sheet.cell_value(i, 5)
        fat = float(fat)
        pr = sheet.cell_value(i, 6)
        pr = float(pr)
        e460 = sheet.cell_value(i, 7)
        if e460=="一":
            e460="0.0"
        e460 = float(e460)
        # 筛选高热量食物
        if head > 200 :
            tittle=sheet.cell_value(i, 0)
            tittle = "\"" + tittle + "\""
            feature = "\"" + "高热量" + "\""
            out_feature.write(f"{tittle},{feature}\n")
        # 筛选高糖食物
        if cho > 50 :
            tittle = sheet.cell_value(i, 0)
            tittle = "\"" + tittle + "\""
            feature = "\"" + "高糖" + "\""
            out_feature.write(f"{tittle},{feature}\n")
        # 筛选高油脂食物
        if fat > 20 :
            tittle = sheet.cell_value(i, 0)
            tittle = "\"" + tittle + "\""
            feature = "\"" + "高脂肪" + "\""
            out_feature.write(f"{tittle},{feature}\n")
        # 筛选低热量食物
        if head < 40 and head > 1:
            tittle=sheet.cell_value(i, 0)
            tittle = "\"" + tittle + "\""
            feature = "\"" + "低热量" + "\""
            out_feature.write(f"{tittle},{feature}\n")
        # 筛选高蛋白食物
        if pr > 10:
            tittle=sheet.cell_value(i, 0)
            tittle = "\"" + tittle + "\""
            feature = "\"" + "高蛋白" + "\""
            out_feature.write(f"{tittle},{feature}\n")
        # 筛选高纤维食物
        if e460 > 3.5:
            tittle=sheet.cell_value(i, 0)
            tittle = "\"" + tittle + "\""
            feature = "\"" + "高纤维" + "\""
            out_feature.write(f"{tittle},{feature}\n")
    # 打开foodfeature.xls文件
    wb_feature=xlrd.open_workbook("../foodfeature.xls")
    ws_feature = wb_feature.sheets()[0]
    for i in range(1, ws_feature.nrows):
        if ws_feature.cell_value(i, 0) in s_food2:
            print("去重" + ws_feature.cell_value(i, 0))
            continue
        s_food2.add(ws_feature.cell_value(i, 0))
        tittle=ws_feature.cell_value(i,0)
        tittle = "\"" + tittle + "\""
        feature=ws_feature.cell_value(i,1)
        feature = "\"" + feature + "\""
        out_feature.write(f"{tittle},{feature}\n")

    # 关闭写文件
    out_feature.close()


if __name__ == "__main__":
    # getoutfood()
    # getfoodkind()
    getfoodfeature()
