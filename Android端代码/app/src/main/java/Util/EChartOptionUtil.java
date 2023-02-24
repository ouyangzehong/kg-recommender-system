package Util;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;

public class EChartOptionUtil {
    /**
     * 画折线图
     *
     * @param xAxis x轴的相关配置
     * @param yAxis y轴的相关配置
     * @return
     */
    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        //通过option指定图表的配置项和数据
        GsonOption option = new GsonOption();
        option.title("周营养素曲线");//折线图的标题
        option.legend("含量");//添加图例
        option.tooltip().trigger(Trigger.axis);//提示框（详见tooltip），鼠标悬浮交互时的信息提示

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);//添加y轴

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);//坐标轴线，默认显示，属性show控制显示与否，属性lineStyle（详见lineStyle）控制线条样式
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);//添加坐标轴的类目属性
        option.xAxis(categorxAxis);//x轴为类目轴

        Line line = new Line();
        Line line2 = new Line();
        Line line3 = new Line();

        Object[] yAxis2= new Object[]{
                220, 512, 782, 652, 999, 1120, 999
        };
        Object[] yAxis3= new Object[]{
                200, 600, 800, 752, 999, 800, 990
        };
        //设置折线的相关属性
        line.smooth(true).name("碳水化合物").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        line2.smooth(true).name("蛋白质").data(yAxis2).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        line3.smooth(true).name("脂肪").data(yAxis3).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");

        //添加数据，将数据添加到option中
        option.series(line);
        option.series(line2);
        option.series(line3);
        return option;
    }

    /**
     * 封装柱状图的option
     * @param xAxis x 轴的相关数据
     * @param yAxis y 轴的相关数据
     * @return 返回封装好的柱状图的option
     */
    public static GsonOption getBarChartOptions(Object[] xAxis, Object[] yAxis){
        GsonOption option = new GsonOption();
        option.title("营养素");
        option.legend("含量");
        option.tooltip().trigger(Trigger.axis);


        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);//添加y轴，将y轴设置为值轴

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.data(xAxis);//设置x轴的类目属性
        option.xAxis(categorxAxis);//添加x轴

        Bar bar = new Bar();
        //设置柱状图的相关属性
        bar.name("含量（克）").data(yAxis).itemStyle().normal().setBarBorderColor("rgba(0,0,0,0.4)");
        option.series(bar);

        return option;
    }


}
