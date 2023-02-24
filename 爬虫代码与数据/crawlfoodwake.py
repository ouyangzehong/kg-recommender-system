# -*- coding = utf-8 -*-
# @Time : 2022-05-07 19:25
# @Author : 欧阳泽洪
# @File : crawlfoodwake.py
# @Software : PyCharm

# 爬取唤醒食物网站 获取部分特征实体
# https://www.foodwake.com/sort/show-single-nutrition

from bs4 import BeautifulSoup  # 网页解析，获取数据
import re  # 正则表达式 进行文字匹配
import urllib.request, urllib.error  # 定制url，获取网页数据
import ssl  # 网页证书验证
import xlwt  # 进行excel操作
import sqlite3  # 进行SOLite数据库操作
import time

def main():
    url={
        "高维生素B1":"https://www.foodwake.com/sort/sort-single-nutrition/b1",
        "高维生素B2":"https://www.foodwake.com/sort/sort-single-nutrition/b2",
        "高维生素B6":"https://www.foodwake.com/sort/sort-single-nutrition/b6",
        "高维生素C":"https://www.foodwake.com/sort/sort-single-nutrition/vc",
        "高维生素E":"https://www.foodwake.com/sort/sort-single-nutrition/ve/asc/0",
        "低胆固醇":"https://www.foodwake.com/sort/sort-single-nutrition/cholesterol/asc/0",
        "高钾":"https://www.foodwake.com/sort/sort-single-nutrition/k",
        "高镁":"https://www.foodwake.com/sort/sort-single-nutrition/mg",
        "低钠":"https://www.foodwake.com/sort/sort-single-nutrition/na/asc/0",
        "高钙":"https://www.foodwake.com/sort/sort-single-nutrition/ca",
        "高铁":"https://www.foodwake.com/sort/sort-single-nutrition/fe",
        "高锌":"https://www.foodwake.com/sort/sort-single-nutrition/zu",
        "高叶酸":"https://www.foodwake.com/sort/sort-single-nutrition/b9"
    }
    baseurl = url
    # 1.爬取网页
    datalist = getdata(baseurl)
    # print(datalist)
    savepath = "foodfeature.xls"
    # 3.保存数据
    saveData(datalist, savepath)
    # # 4.保存到数据库
    # dbpath = "movie.db"


# 获得名称
findTitle=re.compile(r'<a class="text-dark".*>(.*?)</a>',re.S)





def getdata(baseurl):
    datalist=[]
    # 获取网页内容
    for key,values in baseurl.items():
        print(key,values)
        html=askURL(values)
        # 2.逐一解析数据
        soup = BeautifulSoup(html, "html.parser")  # 获取文件
        tab = soup.find_all('tr')
        del (tab[0])  # 去除标题
        # print(tab[0]) # 测试
        for item in tab:
            data=[] # 每一个食物的信息
            item = str(item)
            title = re.findall(findTitle, item)[0]
            # print(title)
            # 去掉别名
            if '（' in title:
                title = title.split('（')[0]

            data.append(title)
            data.append(key)
            print(data) # 测试
            datalist.append(data)

    return datalist


    # return datalist








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



# 保存数据
def saveData(datalist, savepath):
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)  # 1.创建workbook对象
    sheet = book.add_sheet("食品信息", cell_overwrite_ok=True)  # 2.创建工作表
    sheet.write(0, 0, "食品名称")  # 列名
    sheet.write(0, 1, "特征")  # 列名

    for i in range(0, len(datalist)):
        data=datalist[i]
        sheet.write(i + 1, 0, data[0])
        sheet.write(i + 1, 1, data[1])

    book.save(savepath)  # 4.保存数据表
    print("数据已保存到%s" % savepath)





if __name__ == '__main__':
    main()
    print("爬取完毕")
