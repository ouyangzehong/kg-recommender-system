package Util;

import java.util.List;

import Bean.FoodBean;

public class DataUtil {
    public static List<FoodBean> foodBeans;

    public static List<FoodBean> getFoodBeans() {
        return foodBeans;
    }

    public static void setFoodBeans(List<FoodBean> foodBeans) {
        DataUtil.foodBeans = foodBeans;
    }
}
