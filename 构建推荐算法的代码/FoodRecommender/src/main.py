# -*- coding = utf-8 -*-
# @Time : 2022-05-01 13:12
# @Author : 欧阳泽洪
# @File : main.py
# @Software : PyCharm

from neo4j import GraphDatabase
import pandas as pd

uri = "neo4j://localhost:7687"
driver = GraphDatabase.driver(uri, auth=("neo4j", "569107"))

k = 10  # 寻找最相似的邻居的个数
food_common = 3  # 多少个食物被共同评价才能计算相似度
users_common = 2  # 评价过食物的相似用户的最小数量
threshold_sim = 0.9  # 最小相似度


def load_data():
    with driver.session() as session:
        session.run("""MATCH ()-[r]->() DELETE r""")
        session.run("""MATCH (r) DELETE r""")

        print("Loading food...")

        # 加载数据，创建食物标签,食物名,每100克热量(大卡),每100克碳水化合物(克), 每100克脂肪(克), 每100克蛋白质(克), 每100克纤维素(克)属性的实体
        session.run("""
               LOAD CSV WITH HEADERS FROM "file:///out_food.csv" AS csv
               CREATE (:食物 {食物名: csv.tittle,图片链接: csv.imagesrc, 热量: csv.head, 碳水化合物: csv.cho,脂肪: csv.fat,蛋白质: csv.pr,纤维素:csv.e460})
               """)

        # 加载食物类型数据 MERGE是搜索给定模式，如果存在，则返回结果如果它不存在于图中，则它创建新的节点/关系并返回结果。
        print("Loading foodkinds...")

        session.run("""
                    LOAD CSV WITH HEADERS FROM "file:///out_kind.csv" AS csv
                    MERGE (f:食物 {食物名: csv.tittle})
                    MERGE (k:类别 {种类: csv.kind})
                    CREATE (f)-[:属于种类]->(k)
                    """)
        # 加载特征数据
        print("Loading foodfeatures...")
        session.run("""
                   LOAD CSV WITH HEADERS FROM "file:///out_feature.csv" AS csv
                   MERGE (f:食物 {食物名: csv.tittle})
                   MERGE (g:特征 {特征: csv.feature})
                   CREATE (f)-[:拥有特征]->(g)
                   """)
        # 加载评分数据，
        session.run("""
                    LOAD CSV WITH HEADERS FROM "file:///out_foodgrade.csv" AS csv
                    MERGE (m:食物 {食物名: csv.tittle}) 
                    MERGE (u:用户 {用户id: toInteger(csv.userid)})
                    CREATE (u)-[:评价 {评分 : toInteger(csv.grade)}]->(m)
                    """)


def queries():
    while True:
        userid = int(input("请输入要为哪位用户推荐食物，输入其ID即可: "))
        m = int(input("为该用户推荐多少个食物呢？ "))
        genres = []
        if int(input("是否需要过滤不适宜的食物?（输入0或1）")):  # 过滤掉不喜欢的类型
            with driver.session() as session:
                try:
                    q = session.run(f"""MATCH (T:特征) RETURN T.特征 AS genre""")
                    result = []
                    for i, r in enumerate(q):  # 加索引
                        # print(r)
                        result.append(r["genre"])  # 找到图谱中所有的食物特征
                    df = pd.DataFrame(result, columns=["特征"])  # 创建表格
                    print()
                    print(df)
                    inp = input("输入不喜欢的类型索引即可，例如：1 2 3  ")
                    if len(inp) != 0:
                        inp = inp.split(" ")
                        genres = [df["特征"].iloc[int(x)] for x in inp]  # 选取要排除的食物
                except:
                    print("Error !")

        with driver.session() as session:  # 找到当前ID评分的食物
            q = session.run(f"""
                           MATCH (u1:用户{{用户id:{userid}}})-[r:评价]-(m:食物)
                           RETURN m.食物名 AS title, r.评分 AS grade
                           ORDER BY grade DESC
                           """)

            print()
            print("您的评份排名如下:")

            result = []
            for r in q:
                result.append([r["title"], r["grade"]])

            if len(result) == 0:
                print("No ratings found")
            else:
                df = pd.DataFrame(result, columns=["title", "grade"])
                print()
                print(df.to_string(index=False))
            print()

            # 清空相似度图谱

            session.run(f"""
                           MATCH (u1:用户)-[s:相似]-(u2:用户)
                           DELETE s
                           """)
            # 构建当前用户的相似度图谱
            # 找到当前用户评分的食物以及这些食物被其他用户评分的用户，with是把查询集合当做结果以便后面用where 余弦相似度计算
            session.run(f"""
                           MATCH (u1:用户 {{用户id:{userid}}})-[r1:评价]-(m:食物)-[r2:评价]-(u2:用户)
                           WITH
                               u1, u2,
                               COUNT(m) AS food_common,
                               SUM(r1.评分 * r2.评分)/(SQRT(SUM(r1.评分^2)) * SQRT(SUM(r2.评分^2))) AS sim
                           WHERE food_common >= {food_common} AND sim > {threshold_sim}
                           MERGE (u1)-[s:相似]-(u2)
                           SET s.相似度 = sim
                           """)

            Q_GENRE = ""
            if (len(genres) > 0):
                Q_GENRE = "AND ((SIZE(gen) > 0) AND "
                Q_GENRE += "(ANY(x IN " + str(genres) + " WHERE x IN gen))"
                Q_GENRE += ")"
            # 找到相似的用户，然后看他们喜欢什么食物 Collect：将所有值收集到一个集合list中
            q = session.run(f"""
                               MATCH (u1:用户 {{用户id:{userid}}})-[s:相似]-(u2:用户)
                               WITH u1, u2, s
                               ORDER BY s.相似度 DESC LIMIT {k}
                               MATCH (m:食物)-[r:评价]-(u2)
                               OPTIONAL MATCH (g:特征)--(m)
                               WITH u1, u2, s, m, r, COLLECT(DISTINCT g.特征) AS gen
                               WHERE NOT((m)-[:评价]-(u1)) {Q_GENRE}
                               WITH
                                   m.食物名 AS title,
                                   SUM(r.评分 * s.相似度)/SUM(s.相似度) AS grade,
                                   COUNT(u2) AS num,
                                   gen
                               WHERE num >= {users_common}
                               RETURN title, grade, num, gen
                               ORDER BY grade DESC, num DESC
                               LIMIT {m}
                               """)

            print("推荐食物:")

            result = []
            for r in q:
                result.append([r["title"], r["grade"], r["num"], r["gen"]])
            if len(result) == 0:
                print("No recommendations found")
                print()
                continue
            df = pd.DataFrame(result, columns=["食物", "平均分值", "推荐人数", "种类"])
            print()
            print(df.to_string(index=False))
            print()


def recommender(userid, m):
    genres = []
    with driver.session() as session:

        # 清空相似度图谱
        session.run(f"""
                              MATCH (u1:用户)-[s:相似]-(u2:用户)
                              DELETE s
                              """)
        # 构建当前用户的相似度图谱

        # 找到当前用户评分的食物以及这些食物被其他用户评分的用户，with是把查询集合当做结果以便后面用where 余弦相似度计算
        session.run(f"""
                              MATCH (u1:用户 {{用户id:{userid}}})-[r1:评价]-(m:食物)-[r2:评价]-(u2:用户)
                              WITH
                                  u1, u2,
                                  COUNT(m) AS food_common,
                                  SUM(r1.评分 * r2.评分)/(SQRT(SUM(r1.评分^2)) * SQRT(SUM(r2.评分^2))) AS sim
                              WHERE food_common >= {food_common} AND sim > {threshold_sim}
                              MERGE (u1)-[s:相似]-(u2)
                              SET s.相似度 = sim
                              """)

        Q_GENRE = ""
        if (len(genres) > 0):
            Q_GENRE = "AND ((SIZE(gen) > 0) AND "
            Q_GENRE += "(ANY(x IN " + str(genres) + " WHERE x IN gen))"
            Q_GENRE += ")"
        # 找到相似的用户，然后看他们喜欢什么食物 Collect：将所有值收集到一个集合list中
        q = session.run(f"""
                                  MATCH (u1:用户 {{用户id:{userid}}})-[s:相似]-(u2:用户)
                                  WITH u1, u2, s
                                  ORDER BY s.相似度 DESC LIMIT {k}
                                  MATCH (m:食物)-[r:评价]-(u2)
                                  OPTIONAL MATCH (g:类别)--(m)
                                  WITH u1, u2, s, m, r, COLLECT(DISTINCT g.种类) AS gen
                                  WHERE NOT((m)-[:评价]-(u1)) {Q_GENRE}
                                  WITH
                                      m.食物名 AS title,
                                      m.图片链接 AS image,
                                      m.热量 AS heat,
                                      m.碳水化合物 AS cho,
                                      m.纤维素 AS e460,
                                      m.脂肪 AS fat,
                                      m.蛋白质 AS pro,
                                      SUM(r.评分 * s.相似度)/SUM(s.相似度) AS grade,
                                      COUNT(u2) AS num,
                                      gen
                                  WHERE num >= {users_common}
                                  RETURN title,image, grade, num, gen, heat, e460, fat, pro,cho
                                  ORDER BY grade DESC, num DESC
                                  LIMIT {m}
                                  """)

        result = []
        for r in q:
            result.append([r["title"], r["image"], r["heat"], r["cho"],r["e460"], r["fat"], r["pro"]])
        if len(result) == 0:
            print("No recommendations found")
            print()
            # continue
        df = pd.DataFrame(result, columns=["食物", "图片", "热量(大卡/100克)", "碳水化合物(克/100克)","纤维素(克/100克)", "脂肪(克/100克)", "蛋白质(克/100克)"])
        print()
        print(df.to_string(index=False))
        print()
        return result

# 通过知识图谱查询食物数据
def single_inquire_neo4j(title):
    title='"'+title+'"'
    print(title)
    with driver.session() as session:  # 找到当前ID评分的食物
        q = session.run(f"""
                              MATCH (m:食物{{食物名:{title}}})
                              RETURN m.食物名 AS title, m.图片链接 AS image,m.热量 AS heat,m.碳水化合物 AS cho,
                              m.纤维素 AS e460, m.脂肪 AS fat, m.蛋白质 AS pro
                              """)
        result=[]
        for r in q:
            result.append(r["title"])
            result.append(r["image"])
            result.append(r["heat"])
            result.append(r["cho"])
            result.append(r["e460"])
            result.append(r["fat"])
            result.append(r["pro"])
        print(result)
        return result

# 通过sqlite数据库查询数据


if __name__ == "__main__":
    # single_inquire_neo4j("米饭")
    # recommender(10,5)
    if int(input("是否需要重新加载并创建知识图谱？（请选择输入0或1）")):
        load_data()
    queries()
