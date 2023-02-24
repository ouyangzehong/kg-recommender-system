from flask import Flask, render_template, request, jsonify
import datetime

from ExcuteDB.mydb import fetch_allData, insert_oneData
from FoodRecommender.modou2.modultwo import getfruitfordm
from FoodRecommender.src.main import recommender, single_inquire_neo4j
import json

app = Flask(__name__)


# @app.route('/<name>')
# def hello_world(name):
#     return 'Hello %s!' % name

# 注册
@app.route('/test/register')
def register():
    return render_template('test/register.html')


@app.route('/result', methods=['POST', 'GET'])
def result():
    if request.method == 'POST':
        result = request.form
        return render_template('test/result.html', result=result)


# 首页
@app.route('/')
def home():
    return "欢迎"


# 首页返回推荐结果
@app.route('/recommend', methods=['POST', 'GET'])
def foodrecommend():
    result = recommender(10, 5)
    data = []
    for i in result:
        item = {}
        item["name"] = i[0]
        item["image"] = i[1]
        item["heat"] = i[2]
        item["cho"] = i[3]
        item["e460"] = i[4]
        item["fat"] = i[5]
        item["pro"] = i[6]
        data.append(item)
    print(data)
    return json.dumps(data, ensure_ascii=False)


# 查询事物返回数据
@app.route('/requirefood', methods=['POST', 'GET'])
def fetchfood():
    # 解析请求数据
    if request.method == 'POST':
        # 1.处理请求 获得数据
        qingqiu = request.form
        print(
            qingqiu)  # ImmutableMultiDict([('title', '米饭')]) request.form输出的数据类型是ImmutableMultiDict，只需在后边使用to_dict()方法即可转变成dict方法。
        qingqiu = qingqiu.to_dict()["title"]
        print(qingqiu)  # 米饭
        # 2.用请求数据访问数据库或者neo4j 获取返回数据
        getdata = single_inquire_neo4j(qingqiu)  # 调用neo4j 获得了列表数据
        # 3.处理数据为json对象 用于服务器返回
        jsondata = {}
        jsondata["name"] = getdata[0]
        jsondata["image"] = getdata[1]
        jsondata["heat"] = getdata[2]
        jsondata["cho"] = getdata[3]
        jsondata["e460"] = getdata[4]
        jsondata["fat"] = getdata[5]
        jsondata["pro"] = getdata[6]
        print(json.dumps(jsondata, ensure_ascii=False))
        return json.dumps(jsondata, ensure_ascii=False)


# get请求 糖尿病适宜食用水果
@app.route('/FruitForDM')
def fetchdmfruit():
    data = getfruitfordm("详细版本糖尿病水果.xls")
    return json.dumps(data, ensure_ascii=False)


# get请求 糖尿病适宜食用蔬菜
@app.route('/vegetableForDM')
def fetchdmvegetable():
    data = getfruitfordm("详细版糖尿病适宜食用蔬菜.xls")
    return json.dumps(data, ensure_ascii=False)


# get请求 糖尿病适宜食用谷物豆类
@app.route('/grainForDM')
def fetchdmgrain():
    data = getfruitfordm("详细版糖尿病适宜食用谷物豆类.xls")
    return json.dumps(data, ensure_ascii=False)


# get请求 糖尿病适宜食用肉类
@app.route('/meatForDM')
def fetchdmmeat():
    data = getfruitfordm("详细版糖尿病适宜食用肉类.xls")
    return json.dumps(data, ensure_ascii=False)


# get请求 糖尿病适宜食用干果
@app.route('/dryFruitForDM')
def fetchdmdryfruit():
    data = getfruitfordm("详细版糖尿病适宜食用干果.xls")
    return json.dumps(data, ensure_ascii=False)


# get请求 糖尿病适宜食用食用油及调味品
@app.route('/condimentForDM')
def fetchdmcondiment():
    data = getfruitfordm("详细版糖尿病适宜食用调味品.xls")
    return json.dumps(data, ensure_ascii=False)


@app.route('/login')
def fetch_useraccount():
    sql = 'select * from user_account'
    data = fetch_allData("ExcuteDB/hellofood.db", sql)
    datalist = []

    for tp in data:
        datadict = {}
        datadict["userid"] = tp[1]
        datadict["username"] = tp[2]
        datadict["password"] = tp[3]
        datalist.append(datadict)
    print(datalist)
    return json.dumps(datalist, ensure_ascii=False)


@app.route('/register', methods=['POST', 'GET'])
def add_useraccount():
    if request.method == 'POST':
        # 1.处理请求 获得数据
        qingqiu = request.form
        userid = qingqiu.to_dict()["userid"]
        username = qingqiu.to_dict()["username"]
        password = qingqiu.to_dict()["password"]
        sql = 'INSERT INTO user_account(user_id,user_name,user_password) VALUES (?, ?,?);'
        user_data = (userid, username, password)
        insert_oneData("ExcuteDB/hellofood.db", sql, user_data)


if __name__ == '__main__':
    app.run()
    fetch_useraccount()
