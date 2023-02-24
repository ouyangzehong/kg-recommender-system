package Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 欧阳 on 2022/5/10.
 * Gson 数据解析封装
 */

public class GsonUtil {

    /**
     * 私有化构造方法
     */
    private GsonUtil() {
    }

    /**
     * 静态内部类做单例
     */
    private static class SingletonHolder {
        private static final Gson gson = new Gson();
    }

    /**
     * 获取单例方法
     * @return
     */
    public static final Gson getInstance() {
        return SingletonHolder.gson;
    }



    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (getInstance() != null) {
            gsonString = getInstance().toJson(object);
        }
        return gsonString;
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (getInstance() != null) {
            t = getInstance().fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
   /* public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (getInstance() != null) {
            list = getInstance().fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }*/

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> GsonToList(String json, Class<T> cls) {

        List<T> list = new ArrayList<T>();
        if (getInstance()!=null){
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for(final JsonElement elem : array){
                list.add(getInstance().fromJson(elem, cls));
            }
        }

        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (getInstance() != null) {
            list = getInstance().fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (getInstance() != null) {
            map = getInstance().fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}