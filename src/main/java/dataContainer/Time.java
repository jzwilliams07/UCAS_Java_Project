package dataContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: xxxxlc
 * @Date: 2022/5/17 16:25
 * @Description: 该class用来存储时间
 */

public class Time {

    private int year;
    private int month;
    private int day;
    private int hour;

    public Time() {

    }

    public Time(int year, int month, int day, int hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    /**
     * 这个初始化函数建立用来处理.csv中的字符串: 20xx/xx/xx xx:xx:xx
     * @param time 20xx/xx/xx xx:xx:xx
     */
    public Time(String time) {
//        year = readYear(time);
//        month = readMonth(time);
//        day = readDay(time);
//        hour = readHour(time);
        this();
        this.setTime(time);
    }

    public void setTime (String time) {
        year = readYear(time);
        month = readMonth(time);
        day = readDay(time);
        hour = readHour(time);
    }

    public int readYear(String time) {
        Pattern yearRex = Pattern.compile("\\d{4}");
        Matcher yearMatcher = yearRex.matcher(time);
        if (yearMatcher.find()) {
            return readDigit(yearMatcher.group());
        }
        return -1;
    }

    public int readMonth(String time) {
        Pattern monthRex = Pattern.compile("/\\d+/");
        Matcher monthMatcher = monthRex.matcher(time);
        if (monthMatcher.find()) {
            return readDigit(monthMatcher.group());
        }
        return -1;
    }

    public int readDay(String time) {
        Pattern dayRex = Pattern.compile("/\\d+\\s");
        Matcher dayMatcher = dayRex.matcher(time);
        if (dayMatcher.find()) {
            return readDigit(dayMatcher.group());
        }
        return -1;
    }

    public int readHour(String time) {
        Pattern hourRex = Pattern.compile("\\s\\d{2}:");
        Matcher hourMatcher = hourRex.matcher(time);
        if (hourMatcher.find()) {
            return readDigit(hourMatcher.group());
        }
        return -1;
    }

    public int readDigit(String s) {
        String strDigit = "";
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isDigit(s.charAt(i))) {
                strDigit += s.charAt(i);
            }
        }
        return Integer.parseInt(strDigit);
    }


    @Override
    public String toString() {
        return year + "-" + month + "-" + day + "-" + hour;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Time t) {
            return this.year == t.year && this.month == t.month && this.day == t.day;
        }
        return false;
    }

    /**
     * 使用Map容器必须实现 equals() 方法
     * @return int
     */
    @Override
    public int hashCode() {
        int h = 0;
        h = 31 * h + String.valueOf(year).hashCode();
        h = 31 * h + String.valueOf(month).hashCode();
        h = 31 * h + String.valueOf(day).hashCode();
        return h;
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public static void main(String[] args) {
    }
}
