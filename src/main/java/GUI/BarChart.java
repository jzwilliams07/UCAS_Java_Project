package GUI;

import dataContainer.Info;
import dataContainer.Region;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Auther:
 * @Date: 2022/5/16 15:07
 * @Description: 该类继承 JFrame，用于画出每日确诊人数最多的五个省份的柱状图，返回的是 JFreeChart 类
 */

public class BarChart extends JFrame{

    public BarChart() {

    }

    /**
     * 根据每日确诊人数选取每日确诊人数最多的五个省份，生成画图的数据类 CategoryDataset
     * @param InfoByRegion 该日的每个地区的确诊信息
     * @return 返回用于画柱状图的数据 CategoryDataset
     */
    public CategoryDataset createDatasetByRegion(Map<Region, Info> InfoByRegion) {
        // 初始化一个空的dataset对象
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // 使用Map的迭代器，将Map对象转化为List
        List<Map.Entry<Region, Info>> list = new ArrayList<>(InfoByRegion.entrySet());
        // 将List对象排序，重写 compare() 方法决定排序的顺序，根据确诊人数的大小按从大到小排序
        Collections.sort(list, new Comparator<Map.Entry<Region, Info>>() {
            @Override
            public int compare(Map.Entry<Region, Info> o1, Map.Entry<Region, Info> o2) {
                return Integer.compare(o2.getValue().getSuspectedCount(), o1.getValue().getSuspectedCount());
            }
        });

        // 取每日确诊人数最多的五个省份添加进dataset
        int i = 0;
        for (Map.Entry<Region, Info> mapping : list) {
            if (i == 5) {
                break;
            }
            dataset.setValue(mapping.getValue().getSuspectedCount(),
                    "SuspectedCount", mapping.getKey().getProvince());
            i++;
        }

        return dataset;
    }


    /**
     * 根据传入的dataset画出柱状图
     * @param dataset 含地区的每日确证人数
     * @return JFrameChart 图表对象
     */
    public JFreeChart createChartByRegion(CategoryDataset dataset) {

        // 设置字体
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        //设置标题字体
        mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
        //设置轴向字体
        mChartTheme.setLargeFont(new Font("宋体", Font.BOLD, 15));
        //设置图例字体
        mChartTheme.setRegularFont(new Font("宋体", Font.BOLD, 15));
        //应用主题样式
        ChartFactory.setChartTheme(mChartTheme);

        // 生成柱状图
        JFreeChart barChart = ChartFactory.createBarChart(
                "每日确诊人数最多的五个省份",
                "省份",
                "人数",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        // 设置图表背景颜色
        barChart.setBackgroundPaint(new Color(255, 235, 205));
        return barChart;
    }

    public static void main(String[] args) {

    }
}
