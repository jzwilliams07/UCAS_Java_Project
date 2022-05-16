package GUI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther:
 * @Date: 2022/5/16 14:18
 * @Description:
 */

public class PageByTime extends JPanel {

    private JTextField jtf;

    public PageByTime() {
        setLayout(null);
        setBackground(new Color(220, 25, 25));

        BarChart xy = new BarChart();
        CategoryDataset dataset = xy.createDataset();
        JFreeChart chart = xy.createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 200, 760, 300);
        add(chartPanel);

        JComboBox<String> yearBox = new JComboBox<>();
        yearBox.setBounds(180, 60,80, 20);
        add(yearBox);

        JComboBox<String> monthBox = new JComboBox<>();
        monthBox.setBounds(180, 100,80, 20);
        add(monthBox);

        JComboBox<String> dayBox = new JComboBox<>();
        dayBox.setBounds(180, 140,80, 20);
        add(dayBox);

        JTextField yearText = new JTextField("Year: ");
        yearText.setBounds(100, 60, 80, 20);
        add(yearText);

        JTextField monthText = new JTextField("Month: ");
        monthText.setBounds(100, 100, 80, 20);
        add(monthText);

        JTextField dayText = new JTextField("Month: ");
        dayText.setBounds(100, 140, 80, 20);
        add(dayText);
    }

    public static void main(String[] args) {
    }
}
