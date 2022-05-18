package dataContainer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Auther:
 * @Date: 2022/5/17 16:40
 * @Description:
 */

public class TimeTest {
    public Time time;


    @Test
    public void testReadYear() {
        time = new Time();
        assertEquals(2021, time.readYear("2021/5/12  18:35:01"));
        assertEquals(2022, time.readYear("2022/5/12  12:07:00"));
    }

    @Test
    public void testReadMonth() {
        time = new Time();
        assertEquals(11, time.readMonth("2021/11/12  18:35:01"));
        assertEquals(5, time.readMonth("2022/5/12  12:07:00"));
    }

    @Test
    public void testReadDay() {
        time = new Time();
        assertEquals(6, time.readDay("2021/11/6  18:35:01"));
        assertEquals(12, time.readDay("2022/5/12  12:07:00"));
    }

    @Test
    public void testReadHour() {
        time = new Time();
        assertEquals(6, time.readHour("2021/11/6  06:35:01"));
        assertEquals(12, time.readHour("2022/5/12  12:07:00"));
    }
}
