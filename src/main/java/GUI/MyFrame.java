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
 * @Description: 该类为程序的主框架，为最基本的界面，包含了两个按钮和两个子界面
 */

public class MyFrame extends JFrame {
    private JPanel contentPane;                             // 主界面
    public CardLayout cardLayout = new CardLayout();        // 界面的布局控制对象

    private JButton buttonByTime;                           // 时间按钮，按一下切换界面
    private JButton buttonByRegion;                         // 地区按钮，按一下切换界面

    private PageByTime page1;                               // 按时间画柱状图界面
    private PageByRegion page2;                             // 按地区画折线图界面

    private DataReader dataReader;                          // 按需求返回数据的数据读取对象

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

    /**
     * 初始化主界面，初始化时间界面和地区界面，接受数据读取对象 dataReader
     * @param dataReader 按需返回需要的数据类型
     */
    public MyFrame(DataReader dataReader) {
        // 主界面初始化 MyFrame()
        this();

        // 初始化数据读取对象 dataReader
        setDataReader(dataReader);

        // 初始化时间界面
        setPage1(dataReader.getTime());

        // 初始化地区界面
        setPage2(dataReader.getRegion());
    }

    /**
     * 主界面初始化
     */
    public MyFrame() {
        // 设置窗口名称
        setTitle("Covid-19 display window");
        // 设置窗口关闭时，程序停止
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口尺寸
        setSize(960, 720);
        // 设置窗口生成位置
        setLocationRelativeTo(null);

        // 创建主界面
        contentPane = new JPanel();
        // 设置主界面颜色
        contentPane.setBackground(new Color(60, 60, 60));
        // 添加界面进入窗口
        setContentPane(contentPane);
        // 设置主界面布局为null。因为主界面不需要任何变化操作
        contentPane.setLayout(null);

        // 创建新的界面，用来放置之后的变化的时间界面和地区界面
        JPanel panel = new JPanel();
        // 设置界面的大小
        panel.setBounds(0, 40, 960, 660);
        // 将该界面添加进入主界面
        contentPane.add(panel);
        // 设置其布局格式
        panel.setLayout(cardLayout);

        // 创建时间界面和地区界面，分别画出柱状图和折线图
        page1 = new PageByTime();
        page2 = new PageByRegion();
        // 在之间的界面中添加两个界面
        panel.add(page1, "page1");
        panel.add(page2, "page2");

        // 创建两个按钮，设置其大小和位置、颜色、样式
        // 添加监听器，当点击按钮的时间，立刻调用前面panel的布局切换界面
        buttonByTime = new JButton("check by time");
        buttonByTime.setBackground(new Color(255, 235, 205));
        buttonByTime.setFocusPainted(false);
        buttonByTime.setBorderPainted(false);
        buttonByTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "page1");
                // 如果设置了dataReader，就立刻读取选项框中的内容
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

    /**
     * 设置 dataReader
     * @param dataReader
     */
    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    /**
     * 将待 Time 的列表传递给时间界面，根据时间设置三个下拉框的参数
     * @param times
     */
    public void setPage1(List<Time> times) {
        page1.setDataReader(dataReader);
        page1.setYearBox(times);
        page1.setMonthBox(times);
        page1.setDayBox(times);
    }

    /**
     * 将地区的列表传递给地区界面，根据地区设置三个下拉框的参数
     * @param regions
     */
    public void setPage2(Set<Region> regions) {
        page2.setDataReader(dataReader);
        page2.setCountryBox(regions);
        page2.setProvinceBox(regions);
    }
}
