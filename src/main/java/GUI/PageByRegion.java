package GUI;

import dataContainer.DataReader;
import dataContainer.Region;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Auther:
 * @Date: 2022/5/16 14:21
 * @Description: 根据所选地区展示该地区随时间变化的确诊人数、死亡人数、治愈人数的折线图
 */

public class PageByRegion extends JPanel {

    private JComboBox<String> countryBox;           // 下拉框：选择国家
    private JComboBox<String> provinceBox;          // 下拉框：选择地区
    private JButton generateButton;                 // 生成图像的按钮

    private ChartPanel chartPanel;                  // 折线图界面
    private LineChart xy = new LineChart();         // 折线图
    private XYDataset dataset;                      // 折线图数据集

    private DataReader dataReader;                  // 数据读取器

    /**
     * 初始化界面的样式、布局、颜色等参数
     */
    public PageByRegion() {
        // 该界面的布局设置为null
        setLayout(null);
        // 设置界面的颜色
        setBackground(new Color(240, 255, 240));


//        LineChart xy = new LineChart();
//        XYDataset dataset = xy.createDateset();
//        JFreeChart chart = xy.createChart(dataset);
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setBounds(100, 200, 760, 300);
//        add(chartPanel);

        // 创建两个下拉框对象，设置位置和大小，并添加进界面才能显示
        countryBox = new JComboBox<>();
        countryBox.setBounds(180, 100,80, 20);
        add(countryBox);

        provinceBox = new JComboBox<>();
        provinceBox.setBounds(180, 140,80, 20);
        add(provinceBox);

        // 添加两个文字框对象，设置大小和颜色及位置，并加入界面
        JTextField countryText = new JTextField("Country: ");
        countryText.setBackground(new Color(240, 255, 240));
        countryText.setBounds(100, 100, 80, 20);
        add(countryText);

        JTextField provinceText = new JTextField("Province: ");
        provinceText.setBackground(new Color(240, 255, 240));
        provinceText.setBounds(100, 140, 80, 20);
        add(provinceText);

        // 创建生成按钮的大小，布局，颜色
        generateButton = new JButton("generate image");
        generateButton.setBounds(780, 140, 80, 20);
        add(generateButton);

        // 创建生成按钮的监听器，当按下按钮时，读取下拉框的信息，并根据读取到的信息生成折线图
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String country = (String) countryBox.getSelectedItem();
                String province = (String) provinceBox.getSelectedItem();
                Region region = new Region(country, province);

                plotLineChart(region);
            }
        });
    }

    /**
     * 根据传入的地区，从数据读取器dataReader中获得相应的数据，画出折线图
     * @param region 传入的地区
     */
    public void plotLineChart(Region region) {
        // 获取数据集
        dataset = xy.createDatasetByTime(dataReader.getTime(),
                dataReader.getInfoByRegion(region, dataReader.getTime()));
        // 画图
        JFreeChart chart = xy.createChartByTime(dataset);
        // 如果之前存在图像，则先清空图像，否则图像会被之前图像覆盖
        if (chartPanel != null) {
            remove(chartPanel);
        }
        // 创建图像界面画图
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 200, 760, 300);
        add(chartPanel);
        // 更新
        updateUI();
    }

    /**
     * 设置数据获取器
     * @param dataReader
     */
    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    /**
     * 根据地区设置国家下拉框中的选项
     * @param regions
     */
    public void setCountryBox(Set<Region> regions) {
        // 创建Set对象，去除重复的国家
        Set<String> countrySelect = new HashSet<>();
        for (Region r : regions) {
            countrySelect.add(r.getCountry());
        }

        for (String country : countrySelect) {
            countryBox.addItem(country);
        }
    }

    /**
     * 根据国家地区设置省份下拉框的选型
     * @param regions
     */
    public void setProvinceBox(Set<Region> regions) {
        // 如果没选取国家，就不能确定有哪些省份
        if (countryBox.getSelectedItem() == null) {
            return;
        }
        // 首先先清空下拉框选项，排除上一次的影响
        provinceBox.removeAllItems();

        // 获得下拉框国家中的选型，然后在将相同国家的省份添加到下拉框之中
        String country = (String) countryBox.getSelectedItem();
        for (Region r : regions) {
            if (Objects.equals(country, r.getCountry())) {
                provinceBox.addItem(r.getProvince());
            }
        }
    }

    public static void main(String[] args) {
    }
}
