# -*- coding = utf-8 -*-
# @Time : 2022-05-18 18:12
# @Author : 欧阳泽洪
# @File : dealwith39.py
# @Software : PyCharm
import string
import sys
from urllib.parse import quote

import xlrd
from bs4 import BeautifulSoup  # 网页解析，获取数据
import re  # 正则表达式 进行文字匹配
import urllib.request, urllib.error  # 定制url，获取网页数据
import ssl  # 网页证书验证
import xlwt  # 进行excel操作
import sqlite3  # 进行SOLite数据库操作

# 找食物名
from crawlbohe import askDetailURL

findTittle = re.compile(r'<img alt="(.*?)"', re.S)

# 食物图片连接
findImageSrc = re.compile(r'<img.*src="(.*?)"', re.S)
# 食物详情链接
findLink = re.compile(r'<a href="(.*?)"', re.S)
# 食物热量
findHead = re.compile(r'<span class="stress red1">(.*?)</span>', re.S)




def dealstring(path):
    # 第一步
    book = xlrd.open_workbook(path)
    sheet = book.sheets()[0]
    print(sheet.nrows)
    data = []
    for i in range(0, sheet.nrows):
        # 食物名称 及其营养素
        data.append(sheet.cell_value(i, 0))
    return data

def geteffect(path):
    book = xlrd.open_workbook(path)
    sheet = book.sheets()[0]
    print(sheet.nrows)
    data = []
    for i in range(0, sheet.nrows):
        # 食物名称 及其营养素
        data.append(sheet.cell_value(i, 1))
    return data


# 得到一个指定url的网页内容
def askURL(url):
    html = ""
    header = {
        "User-Agent": "Mozilla / 5.0(Windows NT10.0; Win64;x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36"
    }
    context = ssl._create_unverified_context()
    try:
        url = quote(url, safe=string.printable) # url不能出现中文 加此句转换
        req = urllib.request.Request(url=url, headers=header)
        respose = urllib.request.urlopen(req, context=context)
        html = respose.read().decode("utf-8")
        # print(html)
    except urllib.error.URLError as e:
        print(e)
        if hasattr(e, "code"):
            # print(e, code)
            print("code")
        if hasattr(e, "reason"):
            print("reason")
            # print(e, reason)

    return html


def crawldata(baseurl,data,effect):
    # 1.爬取网页
    # 2.逐一解析数据
    # 3.返回数据
    datalist=[]
    i=0
    for name in data:

        data2 = []
        url=baseurl+name
        html=askURL(url)
        # 第二步 逐一解析数据
        soup = BeautifulSoup(html, "html.parser")  # 获取文件
        n=0 # 判断是否有丢失数据
        itemlist=soup.find_all('li', class_="item clearfix")
        for item in itemlist:
            n=n+1
            item = str(item)
            print(name)
            if len(re.findall(findTittle, item))==0:
                if n==len(itemlist):
                    print("第%d条数据丢失" % i)
                continue
            tittle = re.findall(findTittle, item)[0]
            tittle=tittle.split("(")[0]
            imgSrc = re.findall(findImageSrc, item)[0]
            if tittle == name:

                detaillink = "https://www.boohee.com" + re.findall(findLink, item)[0]
                html_detail = askDetailURL(detaillink)
                soup_detail = BeautifulSoup(html_detail, "html.parser")  # 获取文件
                item2 = soup_detail.find_all('div', class_="content")  # 内容详情在第二个div里面
                item2 = str(item2[1])
                head = re.findall(findHead, item2)[0]
                data2.append(data[i])
                data2.append(head)
                data2.append(imgSrc)
                data2.append(effect[i])
                datalist.append(data2)
                break
            if n==len(itemlist):
                print("第%d条数据丢失"%i)
        i = i + 1


    return datalist



def savaData2DB(datalist, dbpath):
    init_db(dbpath)
    conn = sqlite3.connect(dbpath)
    cursor = conn.cursor()
    for data in datalist:
        for index in range(len(data)):
            data[index] = '"' + data[index] + '"'
        print(data)
        sql = '''
        insert into FruitforDM(
        name,heat,image,effect)
        values (%s)''' % ",".join(data)
        cursor.execute(sql)
    conn.commit()
    conn.close()
    print("成功存储到数据库")


def init_db(dbpath):
    sql = '''
    create table FruitforDM(
    id integer primary key autoincrement,
    name varchar ,
    heat text,
    image text ,
    effect text 
    )
    '''
    conn = sqlite3.connect(dbpath)
    cursor = conn.cursor()
    cursor.execute(sql)
    conn.commit()
    conn.close()

# 保存数据
def saveData(datalist, savepath):
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)  # 1.创建workbook对象
    sheet = book.add_sheet("食品信息", cell_overwrite_ok=True)  # 2.创建工作表
    col = ("食品名称", "热量", "图片链接", "作用")
    for i in range(0, 4):
        sheet.write(0, i, col[i])  # 列名
    for i in range(0, len(datalist)):
        print("正在写入第%d条" % i)
        data = datalist[i]
        for j in range(0, 4):
            sheet.write(i + 1, j, data[j])
    book.save(savepath)  # 4.保存数据表
    print("数据已保存到%s" % savepath)

if __name__ == '__main__':
    # 1.从xls文件获取食物名
    # 2.从文件里获取食物作用
    # 3.用爬虫获取图片,热量
    #
    # 4.融合数据
    # 3.将食物名，热量，图片，食物作用存入数据库
    data = dealstring("糖尿病适宜食用调味品.xls")
    effect = geteffect("糖尿病适宜食用调味品.xls")
    datalist=crawldata("https://www.boohee.com/food/search?keyword=",data,effect)
    print(datalist)
    saveData(datalist,"详细版糖尿病适宜食用调味品.xls")
