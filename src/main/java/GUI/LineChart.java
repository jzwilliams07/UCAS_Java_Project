package GUI;

import dataContainer.DailyInfo;
import dataContainer.Info;
import dataContainer.Region;
import dataContainer.Time;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @Auther:
 * @Date: 2022/5/16 11:13
 * @Description: 根据数据集画出确诊人数，死亡人数，治愈人数随时间的变化的曲线图
 */

public class LineChart extends JFrame {

    public LineChart() {

    }

    /**
     * 根据数据集 xyDataset 画出确诊人数，死亡人数，治愈人数随时间的变化的曲线图
     * @param xyDataset 数据集
     * @return JFrameChart 曲线图
     */
    public JFreeChart createChartByTime(XYDataset xyDataset) {
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

        // 创建曲线图对象
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "该地区确诊人数、死亡人数、治愈人数随时间变化图",
                "时间",
                "人数",
                xyDataset,
                true,
                false,
                false
        );
        // 图例
        LegendTitle legend = chart.getLegend();
        // 设置曲线图背景颜色
        legend.setBackgroundPaint(new Color(240, 255, 240));
        // 设置图表背景颜色
        chart.setBackgroundPaint(new Color(240, 255, 240));
        return chart;
    }

    /**
     * 根据Time类型的List和infos类型的List生成带时间戳的数据集
     * @param times 时间的列表
     * @param infos 对应每个时间的疫情信息：确诊人数，死亡人数，治愈人数
     * @return 带时间戳的数据集(横坐标为时间)
     */
    public TimeSeriesCollection createDatasetByTime(List<Time> times, List<Info> infos) {
        // 创建时间数据集空对象
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        // 依次创建数据集中的时间序列，因为需求是画三条折线图在同一折线图，所以同时需要三个序列
        TimeSeries confirmedCountByTime = new TimeSeries("confirmedCount");
        TimeSeries curedCountByTime = new TimeSeries("curedCount");
        TimeSeries deadCountByTime = new TimeSeries("deadCount");

        // 分别给三个时间序列添加时间和数据
        for (int i = 0; i < times.size(); ++i) {

            confirmedCountByTime.add(new Day(times.get(i).getDay(), times.get(i).getMonth(),
                            times.get(i).getYear()),
                            infos.get(i).getConfirmedCount());

            curedCountByTime.add(new Day(times.get(i).getDay(), times.get(i).getMonth(),
                            times.get(i).getYear()),
                            infos.get(i).getCuredCount());

            deadCountByTime.add(new Day(times.get(i).getDay(), times.get(i).getMonth(),
                            times.get(i).getYear()),
                            infos.get(i).getDeadCount());
        }

        // 将时间序列依次加入时间数据集
        dataset.addSeries(confirmedCountByTime);
        dataset.addSeries(curedCountByTime);
        dataset.addSeries(deadCountByTime);

        return dataset;
    }


    public static void main(String[] args) {

    }
}
