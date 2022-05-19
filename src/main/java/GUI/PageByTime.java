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
 * @Description:
 */

public class PageByTime extends JPanel {

    private JTextField jtf;

    private JComboBox<String> yearBox;
    private JComboBox<String> monthBox;
    private JComboBox<String> dayBox;
    private JButton generateButton;

    private DataReader dataReader;

    private BarChart xy = new BarChart();
    private CategoryDataset dataset;
    private ChartPanel chartPanel;


    public PageByTime() {
        setLayout(null);
        setBackground(new Color(220, 25, 25));



        yearBox = new JComboBox<>();
        yearBox.setBounds(180, 60,80, 20);
        add(yearBox);

        yearBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setDayBox(dataReader.getTime());
                setDayBox(dataReader.getTime());
            }
        });

        monthBox = new JComboBox<>();
        monthBox.setBounds(180, 100,80, 20);
        add(monthBox);

        monthBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setDayBox(dataReader.getTime());
            }
        });

        dayBox = new JComboBox<>();
        dayBox.setBounds(180, 140,80, 20);
        add(dayBox);

        JTextField yearText = new JTextField("Year: ");
        yearText.setBounds(100, 60, 80, 20);
        add(yearText);

        JTextField monthText = new JTextField("Month: ");
        monthText.setBounds(100, 100, 80, 20);
        add(monthText);

        JTextField dayText = new JTextField("Day: ");
        dayText.setBounds(100, 140, 80, 20);
        add(dayText);

        generateButton = new JButton("generate image");
        generateButton.setBounds(780, 140, 80, 20);
        add(generateButton);

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

    public void plotBarChart(Map<Region, Info> InfoByRegion) {
        dataset = xy.createDatasetByRegion(InfoByRegion);
        JFreeChart chart = xy.createChartByRegion(dataset);
        if (chartPanel != null) {
            remove(chartPanel);
        }
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 200, 760, 300);
        add(chartPanel);
        updateUI();
    }

    public void setYearBox(List<Time> times) {
        Set<String> yearSelect = new HashSet<>();
        for (Time time : times) {
            yearSelect.add(Integer.toString(time.getYear()));
        }

        for (String year : yearSelect) {
            yearBox.addItem(year);
        }
    }

    public void setMonthBox(List<Time> times) {
        if (yearBox.getSelectedItem() == null) {
            return;
        }

        monthBox.removeAllItems();

        int yearSelect = Integer.parseInt((String) yearBox.getSelectedItem());

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

    public void setDayBox(List<Time> times) {
        if (monthBox.getSelectedItem() == null || yearBox.getSelectedItem() == null) {
            return;
        }

        dayBox.removeAllItems();

        int monthSelect = Integer.parseInt((String) monthBox.getSelectedItem());
        int yearSelect = Integer.parseInt((String) yearBox.getSelectedItem());

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

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public static void main(String[] args) {
    }
}
