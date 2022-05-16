package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

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

        PageByTime page1 = new PageByTime();
        PageByRegion page2 = new PageByRegion();
        panel.add(page1, "page1");
        panel.add(page2, "page2");

        buttonByTime = new JButton("check by time");
        buttonByTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "page1");
            }
        });
        buttonByTime.setBounds(0, 0, 480, 40);
        contentPane.add(buttonByTime);

        buttonByRegion = new JButton("check by Region");
        buttonByRegion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "page2");
            }
        });
        buttonByRegion.setBounds(480, 0, 480, 40);
        contentPane.add(buttonByRegion);
    }
}
