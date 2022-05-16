package GUI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Auther:
 * @Date: 2022/5/16 10:30
 * @Description:
 */

public class MainForm {

    // 界面：根据时间选择画柱状图
    private JPanel panelByTime;
    private JButton test1Button;
    private JButton test2Button;
    private JPanel panel;
    private JComboBox comboBox1;

    // 界面：根据地区选择画折线图
    private JLabel panelByRegion;

    // 根据时间的折线图
    private JFreeChart chartByTime;

    // 显式折线图的组件
    private ChartPanel chartPanelByTime;

    public CardLayout cardLayout = new CardLayout();

    private JLabel a;
    private JLabel b;


    public MainForm() {
        test1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        test2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(mainForm.panelByTime);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口大小
        frame.setSize(960, 720);

        // 设置窗口居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainForm.a = new JLabel("text 1");
        mainForm.b = new JLabel("text 2");
        mainForm.panel.add(mainForm.a, "p1");
        mainForm.panel.add(mainForm.b, "p2");
    }
}
