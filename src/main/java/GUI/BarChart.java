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
 * @Description:
 */

public class BarChart extends JFrame{

    public BarChart() {
        initUI();
    }

    public void initUI() {
        CategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(46, "Gold medals", "USA");
        dataset.setValue(38, "Gold medals", "China");
        dataset.setValue(22, "Gold medals", "Russia");
        dataset.setValue(13, "Gold medals", "South Korea");
        dataset.setValue(11, "Gold medals", "Germany");

        return dataset;
    }

    public CategoryDataset createDatasetByRegion(Map<Region, Info> InfoByRegion) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Map.Entry<Region, Info>> list = new ArrayList<>(InfoByRegion.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Region, Info>>() {
            @Override
            public int compare(Map.Entry<Region, Info> o1, Map.Entry<Region, Info> o2) {
                return Integer.compare(o2.getValue().getSuspectedCount(), o1.getValue().getSuspectedCount());
            }
        });

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

    public JFreeChart createChart(CategoryDataset dataset) {
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


        JFreeChart barChart = ChartFactory.createBarChart(
                "Olympic gold medals in London",
                "Country",
                "Gold medals",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        return barChart;
    }

    public JFreeChart createChartByRegion(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "SuspectedCount in each province",
                "Country province",
                "SuspectedCount",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        return barChart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BarChart ex = new BarChart();
            ex.setVisible(true);
        });
    }
}
