package GUI;

import dataContainer.DataReader;
import dataContainer.Info;
import dataContainer.Region;
import dataContainer.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther:
 * @Date: 2022/5/16 13:54
 * @Description:
 */

public class MyFrame extends JFrame {
    private JPanel contentPane;
    public CardLayout cardLayout = new CardLayout();

    private JButton buttonByTime;
    private JButton buttonByRegion;

    private PageByTime page1;
    private PageByRegion page2;

    private DataReader dataReader;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try{
                    MyFrame frame = new MyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MyFrame(DataReader dataReader) {
        this();
        setDataReader(dataReader);
        setPage1(dataReader.getTime());
        setPage2(dataReader.getRegion());
    }

    public MyFrame() {
        setTitle("Covid-19 display window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 720);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(60, 60, 60));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 40, 960, 660);
        contentPane.add(panel);
        panel.setLayout(cardLayout);

        page1 = new PageByTime();
        page2 = new PageByRegion();
        panel.add(page1, "page1");
        panel.add(page2, "page2");

        buttonByTime = new JButton("check by time");
        buttonByTime.setBackground(new Color(255, 235, 205));
        buttonByTime.setFocusPainted(false);
        buttonByTime.setBorderPainted(false);
        buttonByTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "page1");
                if (dataReader != null) {
                    setPage1(dataReader.getTime());
                }
            }
        });
        buttonByTime.setBounds(0, 0, 480, 40);
        contentPane.add(buttonByTime);

        buttonByRegion = new JButton("check by Region");
        buttonByRegion.setBackground(new Color(240, 255, 240));
        buttonByRegion.setFocusPainted(false);
        buttonByRegion.setBorderPainted(false);
        buttonByRegion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "page2");
                if (dataReader != null) {
                    setPage2(dataReader.getRegion());
                }
            }
        });
        buttonByRegion.setBounds(480, 0, 480, 40);
        contentPane.add(buttonByRegion);
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public void setPage1(List<Time> times) {
        page1.setDataReader(dataReader);
        page1.setYearBox(times);
        page1.setMonthBox(times);
        page1.setDayBox(times);
    }

    public void setPage2(Set<Region> regions) {
        page2.setDataReader(dataReader);
        page2.setCountryBox(regions);
        page2.setProvinceBox(regions);
    }
}
