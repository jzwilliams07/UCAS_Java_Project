import GUI.MyFrame;
import dataContainer.DataReader;
import readIO.Reader;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Auther:
 * @Date: 2022/5/14 10:52
 * @Description:
 */

public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        Reader reader = new Reader("Resources/DXYArea.csv");
        try {
            reader.read(200);
        }
        catch (IOException e) {
            System.out.println(e);
        }

        DataReader dataReader = new DataReader(reader.getDailyInfo(), reader.getTimeToIndex());

        EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try{
                    MyFrame frame = new MyFrame(dataReader);
                    frame.setDataReader(dataReader);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
