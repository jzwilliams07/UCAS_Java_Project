package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther:
 * @Date: 2022/5/16 11:13
 * @Description:
 */

public class LineChart extends JFrame {

    public LineChart() {
        initUI();
    }

    public void initUI() {
        XYDataset dataset = createDateset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "该地区新冠疫情确证病例、治愈病例、死亡病例随时间变化情况",
                "时间",
                "人数",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        return chart;
    }

    public XYDataset createDateset() {
        XYSeries series1 = new XYSeries("test1");
        series1.add(18,567);
        series1.add(20,612);
        series1.add(25, 800);
        XYSeries series2 = new XYSeries("test2");
        series2.add(18,530);
        series2.add(20,660);
        series2.add(25, 750);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LineChart ex = new LineChart();
            ex.setVisible(true);
        });
    }
}
