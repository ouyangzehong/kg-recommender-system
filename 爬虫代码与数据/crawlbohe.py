# -*- coding = utf-8 -*-
# @Time : 2022-05-01 16:26
# @Author : 欧阳泽洪
# @File : crawlbohe.py
# @Software : PyCharm

# 爬取薄荷网食物实体与属性
# https://www.boohee.com/

from bs4 import BeautifulSoup  # 网页解析，获取数据
import re  # 正则表达式 进行文字匹配
import urllib.request, urllib.error  # 定制url，获取网页数据
import ssl  # 网页证书验证
import xlwt  # 进行excel操作
import sqlite3  # 进行SOLite数据库操作
import time


def main():
    baseurl = "https://www.boohee.com/food/view_menu?page="
    # 1.爬取网页
    datalist = getdata(baseurl)
    # print(datalist)
    savepath = "菜肴.csv"
    # 3.保存数据
    saveData(datalist, savepath)
    # # 4.保存到数据库
    # dbpath = "movie.db"


# 用正则表达式进行匹配
# 找食物名
findTittle = re.compile(r'<img alt="(.*?)"', re.S)
# 食物详情链接
findLink = re.compile(r'<a href="(.*?)"', re.S)
# 食物图片连接
findImageSrc = re.compile(r'<img.*src="(.*?)"', re.S)

# 食物热量
findHead = re.compile(r'<span class="stress red1">(.*?)</span>', re.S)
# 碳水化合物
findCHO = re.compile(r'<span class="dd">(.*?)</span>', re.S)
# 脂肪(克)
findFat = re.compile(r'<dd><span class="dt">脂肪.*>(.*?)</span></dd>', re.S)
# 蛋白质(克)
findPr = re.compile(r'<dd><span class="dt">蛋白质.*>(.*?)</span></dd>', re.S)
# 纤维素(克)
findE460 = re.compile(r'<dd class="last"><span class="dt">纤维素.*>(.*?)</span></dd>', re.S)
# 分类
findClass = re.compile(r'<a href=.*>(.*?)</a>', re.S)


def getdata(baseurl):
    # 获取网页内容
    datalist = []
    for i in range(0, 10):
        page = i + 1
        url = baseurl + str(page)
        print("正在爬取第%d页..." % page)
        html = askURL(url)
        # 2.逐一解析数据
        soup = BeautifulSoup(html, "html.parser")  # 获取文件
        for item in soup.find_all('li', class_="item clearfix"):
            # print(item)  # 测试
            data = []  # 保存一个食物的所有信息

            # 进入第一个页面
            item = str(item)
            tittle = re.findall(findTittle, item)[0]
            data.append(tittle)  # 添加名称
            imgSrc = re.findall(findImageSrc, item)[0]
            data.append(imgSrc)  # 添加图片地址
            detaillink = "https://www.boohee.com" + re.findall(findLink, item)[0]
            data.append(detaillink)  # 添加详情页链接
            print(tittle)
            if tittle == "麻婆豆腐":
                data.append("109.31")
                data.append("5.54")
                data.append("5.93")
                data.append("8.64")
                data.append("0.81")
                data.append("家常菜")
                datalist.append(data)
                print("到了麻婆豆腐")
                continue

            if tittle == "炒面/炒粉":
                data.append("200.51")
                data.append("34.46")
                data.append("7.52")
                data.append("6.76")
                data.append("0.7")
                data.append("家常菜")
                datalist.append(data)
                print("到了炒面/炒粉")
                continue
            # 进入详情页

            html_detail = askDetailURL(detaillink)
            soup_detail = BeautifulSoup(html_detail, "html.parser")  # 获取文件
            print(len(datalist))
            item2 = soup_detail.find_all('div', class_="content")  # 内容详情在第二个div里面
            # print(item2)
            item2 = str(item2[1])

            # 详情页的内容获取
            # 热量 碳水化合物 脂肪 蛋白质 纤维素
            head = re.findall(findHead, item2)[0]
            data.append(head)  # 添加热量
            cho = re.findall(findCHO, item2)[3]
            data.append(cho)  # 添加碳水化合物
            fat = re.findall(findCHO, item2)[4]
            data.append(fat)  # 添加脂肪
            pr = re.findall(findCHO, item2)[5]
            data.append(pr)  # 添加蛋白质
            e460 = re.findall(findCHO, item2)[6]
            data.append(e460)  # 添加纤维素

            # 分类
            item3 = soup_detail.find_all('div', class_="content")[0]  # 分类在详情页的第一个div里面
            item3 = str(item3)
            cla = re.findall(findClass, item3)[0]
            data.append(cla)
            # print(data) # 测试

            datalist.append(data)  # 把处理好的食品的信息放进datalist
        time.sleep(3)  # 休息三秒 防爬虫检测
    return datalist


# 得到一个指定url的网页内容
def askURL(url):
    html = ""
    header = {
        "User-Agent": "Mozilla / 5.0(Windows NT10.0; Win64;x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36"
    }
    rqs = urllib.request.Request(url=url, headers=header)

    context = ssl._create_unverified_context()
    try:
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


# 得到一个指定url的网页内容
def askDetailURL(url):
    html = ""
    header = {
        "User-Agent": "Mozilla / 5.0(Windows NT10.0; Win64;x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36"
    }
    rqs = urllib.request.Request(url=url, headers=header)

    context = ssl._create_unverified_context()
    try:
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


# 保存数据
def saveData(datalist, savepath):
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)  # 1.创建workbook对象
    sheet = book.add_sheet("食品信息", cell_overwrite_ok=True)  # 2.创建工作表
    col = ("食品名称", "图片链接", "详情页链接", "每100克热量(大卡)", "每100克碳水化合物(克)", "每100克脂肪(克)", "每100克蛋白质(克)", "每100克纤维素(克)", "分类")
    for i in range(0, 9):
        sheet.write(0, i, col[i])  # 列名
    for i in range(0, len(datalist)):
        print("正在写入第%d条" % i)
        data = datalist[i]
        for j in range(0, 9):
            sheet.write(i + 1, j, data[j])
    book.save(savepath)  # 4.保存数据表
    print("数据已保存到%s" % savepath)


if __name__ == '__main__':
    main()
    print("爬取完毕")
