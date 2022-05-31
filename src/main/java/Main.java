import GUI.MyFrame;
import dataContainer.DataReader;
import readIO.Reader;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Auther:
 * @Date: 2022/5/14 10:52
 * @Description: 该疫情读取画图的主程序，直接运行
 */

public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        // 创建读取数据的对象
        Reader reader = new Reader("src/main/resources/DXYArea.csv");
        try {
            // 读取两百天的疫情数据
            reader.read(200);
        }
        catch (IOException e) {
            // 有错误就打印
            System.out.println(e);
        }
        // 创建数据获取器
        DataReader dataReader = new DataReader(reader.getDailyInfo(), reader.getTimeToIndex());

        // 时间队列执行窗口的生成
        EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try{
                    // 创建窗口对象
                    MyFrame frame = new MyFrame(dataReader);
                    // 设置窗口对象的数据获取器
                    frame.setDataReader(dataReader);
                    // 设置窗口可见
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
