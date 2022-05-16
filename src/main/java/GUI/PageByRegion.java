package GUI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther:
 * @Date: 2022/5/16 14:21
 * @Description:
 */

public class PageByRegion extends JPanel {

    public PageByRegion() {
        setLayout(null);
        setBackground(new Color(25, 200, 30));


        LineChart xy = new LineChart();
        XYDataset dataset = xy.createDateset();
        JFreeChart chart = xy.createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 200, 760, 300);
        add(chartPanel);

        JComboBox<String> countryBox = new JComboBox<>();
        countryBox.setBounds(180, 100,80, 20);
        add(countryBox);

        JComboBox<String> provinceBox = new JComboBox<>();
        provinceBox.setBounds(180, 140,80, 20);
        add(provinceBox);

        JTextField countryText = new JTextField("Country: ");
        countryText.setBounds(100, 100, 80, 20);
        add(countryText);

        JTextField provinceText = new JTextField("Province: ");
        provinceText.setBounds(100, 140, 80, 20);
        add(provinceText);
    }

    public static void main(String[] args) {
    }
}
