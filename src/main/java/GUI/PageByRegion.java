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
 * @Description:
 */

public class PageByRegion extends JPanel {

    private JComboBox<String> countryBox;
    private JComboBox<String> provinceBox;
    private JButton generateButton;

    private ChartPanel chartPanel;
    private LineChart xy = new LineChart();;
    private XYDataset dataset;

    private DataReader dataReader;

    public PageByRegion() {
        setLayout(null);
        setBackground(new Color(25, 200, 30));


//        LineChart xy = new LineChart();
//        XYDataset dataset = xy.createDateset();
//        JFreeChart chart = xy.createChart(dataset);
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setBounds(100, 200, 760, 300);
//        add(chartPanel);

        countryBox = new JComboBox<>();
        countryBox.setBounds(180, 100,80, 20);
        add(countryBox);

        provinceBox = new JComboBox<>();
        provinceBox.setBounds(180, 140,80, 20);
        add(provinceBox);

        JTextField countryText = new JTextField("Country: ");
        countryText.setBounds(100, 100, 80, 20);
        add(countryText);

        JTextField provinceText = new JTextField("Province: ");
        provinceText.setBounds(100, 140, 80, 20);
        add(provinceText);

        generateButton = new JButton("generate image");
        generateButton.setBounds(780, 140, 80, 20);
        add(generateButton);


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

    public void plotLineChart(Region region) {
        dataset = xy.createDatasetByTime(dataReader.getTime(),
                dataReader.getInfoByRegion(region, dataReader.getTime()));
        JFreeChart chart = xy.createChartByTime(dataset);
        if (chartPanel != null) {
            remove(chartPanel);
        }
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 200, 760, 300);
        add(chartPanel);
        updateUI();
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public void setCountryBox(Set<Region> regions) {
        Set<String> countrySelect = new HashSet<>();
        for (Region r : regions) {
            countrySelect.add(r.getCountry());
        }

        for (String country : countrySelect) {
            countryBox.addItem(country);
        }
    }

    public void setProvinceBox(Set<Region> regions) {
        if (countryBox.getSelectedItem() == null) {
            return;
        }

        provinceBox.removeAllItems();

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
