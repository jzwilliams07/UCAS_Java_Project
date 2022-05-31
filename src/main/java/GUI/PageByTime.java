package GUI;

import dataContainer.DataReader;
import dataContainer.Info;
import dataContainer.Region;
import dataContainer.Time;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther:
 * @Date: 2022/5/16 14:18
 * @Description: 根据时间的选取画出在这个时刻确诊人数前五的省份的柱状图
 */

public class PageByTime extends JPanel {


    private JComboBox<String> yearBox;                  // 选择年份的下拉框
    private JComboBox<String> monthBox;                 // 选择月份的下拉框
    private JComboBox<String> dayBox;                   // 选择日期的下拉框
    private JButton generateButton;                     // 生成折线图的按钮

    private DataReader dataReader;                      // 数据获取器

    private BarChart xy = new BarChart();               // 柱状图
    private CategoryDataset dataset;                    // 数据集
    private ChartPanel chartPanel;                      // 画柱状图的界面


    public PageByTime() {
        // 设置该界面的布局
        setLayout(null);
        // 设置该界面的颜色
        setBackground(new Color(255, 235, 205));

        // 设置年份选择的下拉框的位置大小颜色，并添加进界面
        yearBox = new JComboBox<>();
        yearBox.setBounds(180, 60,80, 20);
        add(yearBox);

        // 添加下拉框的监听器，点击则添加选项
        yearBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setMonthBox(dataReader.getTime());
                setDayBox(dataReader.getTime());
            }
        });

        // 设置月份选择的下拉框的位置大小颜色，并添加进界面
        monthBox = new JComboBox<>();
        monthBox.setBounds(180, 100,80, 20);
        add(monthBox);
        // 添加下拉框的监听器，点击则添加选项
        monthBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setDayBox(dataReader.getTime());
            }
        });

        // 设置天数选择的下拉框的位置大小颜色，并添加进界面
        dayBox = new JComboBox<>();
        dayBox.setBounds(180, 140,80, 20);
        add(dayBox);

        // 添加三个文字框对象，设置大小和颜色及位置，并加入界面
        JTextField yearText = new JTextField("Year: ");
        yearText.setBackground(new Color(255, 235, 205));
        yearText.setBounds(100, 60, 80, 20);
        add(yearText);

        JTextField monthText = new JTextField("Month: ");
        monthText.setBackground(new Color(255, 235, 205));
        monthText.setBounds(100, 100, 80, 20);
        add(monthText);

        JTextField dayText = new JTextField("Day: ");
        dayText.setBackground(new Color(255, 235, 205));
        dayText.setBounds(100, 140, 80, 20);
        add(dayText);

        // 创建生成按钮的大小，布局，颜色
        generateButton = new JButton("generate image");
        generateButton.setBounds(780, 140, 80, 20);
        add(generateButton);
        // 创建生成按钮的监听器，当按下按钮时，读取下拉框的信息，并根据读取到的信息生成柱状图
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yearBox.getSelectedItem() == null
                        || monthBox.getSelectedItem() == null
                        || dayBox.getSelectedItem() == null) {
                    return;
                }
                int year = Integer.parseInt((String) yearBox.getSelectedItem());
                int month = Integer.parseInt((String) monthBox.getSelectedItem());
                int day = Integer.parseInt((String) dayBox.getSelectedItem());

                Time time = new Time(year, month, day, 0);
                plotBarChart(dataReader.getInfoByTime(time));
            }
        });
    }

    /**
     * 根据传入的数据，画出柱状图
     * @param InfoByRegion 数据获取器传入的各个地区的疫情数据
     */
    public void plotBarChart(Map<Region, Info> InfoByRegion) {
        // 创建数据集
        dataset = xy.createDatasetByRegion(InfoByRegion);
        // 创建图表
        JFreeChart chart = xy.createChartByRegion(dataset);
        // 如果之前有图则全部清空图表页面
        if (chartPanel != null) {
            remove(chartPanel);
        }
        // 重新生成图表页面，并添加新画的柱状图
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 200, 760, 300);
        add(chartPanel);
        updateUI();
    }

    /**
     * 设置年份盒子内的下拉框选型
     * @param times
     */
    public void setYearBox(List<Time> times) {
        // 集合去重
        Set<String> yearSelect = new HashSet<>();
        for (Time time : times) {
            yearSelect.add(Integer.toString(time.getYear()));
        }

        for (String year : yearSelect) {
            yearBox.addItem(year);
        }
    }

    /**
     * 设置月份盒子内的下拉框选型
     * @param times
     */
    public void setMonthBox(List<Time> times) {
        if (yearBox.getSelectedItem() == null) {
            return;
        }

        monthBox.removeAllItems();

        int yearSelect = Integer.parseInt((String) yearBox.getSelectedItem());
        // 集合去重
        Set<String> monthSelect = new HashSet<>();

        for (Time time : times) {
            int year = time.getYear();
            if (year == yearSelect) {
                monthSelect.add(Integer.toString(time.getMonth()));
            }
        }

        for (String month : monthSelect) {
            monthBox.addItem(month);
        }
    }

    /**
     * 设置天数盒子内的下拉框选型
     * @param times
     */
    public void setDayBox(List<Time> times) {
        if (monthBox.getSelectedItem() == null || yearBox.getSelectedItem() == null) {
            return;
        }

        dayBox.removeAllItems();

        int monthSelect = Integer.parseInt((String) monthBox.getSelectedItem());
        int yearSelect = Integer.parseInt((String) yearBox.getSelectedItem());
        // 集合去重
        Set<String> daySelect = new HashSet<>();

        for (Time time : times) {
            int year = time.getYear();
            int month = time.getMonth();

            if (year == yearSelect && month == monthSelect) {
                daySelect.add(Integer.toString(time.getDay()));
                dayBox.addItem(Integer.toString(time.getDay()));
            }
        }

        for (String day : daySelect) {
            dayBox.addItem(day);
        }
    }

    /**
     * 设置数据获取器
     * @param dataReader
     */
    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public static void main(String[] args) {
    }
}
