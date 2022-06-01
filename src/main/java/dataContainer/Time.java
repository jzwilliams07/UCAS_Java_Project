package dataContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SuppressWarnings({"all"})
/**
 * @Auther: xxxxlc
 * @Date: 2022/5/17 16:25
 * @Description: 该class用来存储时间
 * 该类以 year month day hour四个 int 属性存储时间信息
 * 该类通过四个read方法通过正则表达式匹配从csv表中time一栏中的字符串中获取时间信息，配合Info类使用，实现按时间查找疫情信息的需求
 */

public class Time {

    private int year;
    private int month;
    private int day;
    private int hour;

    public Time() {

    }

    /**
     * Time类对象中四个分别用于存储年 月 日 时的四个int变量
     * @param year  存储年份信息
     * @param month 存储月份信息
     * @param day   存储日期信息
     * @param hour  存储时间信息
     */
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

    /**
     * 使用四个编写的自定义read方法从传入的time字符串中分别读取并转化为int类型的年、月、日、具体时间的信息
     * @param time 20xx/xx/xx xx:xx:xx
     */
    public void setTime (String time) {
        year = readYear(time);
        month = readMonth(time);
        day = readDay(time);
        hour = readHour(time);
    }

    /**
     * readYear方法传入一个csv文件当中的时间字符串，使用正则表达式\\d{4}匹配到20XX关于年份的信息，
     * 并且使用readDigit方法将匹配的年份信息的字符串转化为int类型并返回
     * @param time 20xx/xx/xx xx:xx:xx
     * @return 匹配到就返回int类型的年份信息，否则返回-1
     */
    public int readYear(String time) {
        Pattern yearRex = Pattern.compile("\\d{4}");    //编译正则表达式并放入Pattern类对象yearRex中，以下read方法同理
        Matcher yearMatcher = yearRex.matcher(time);           //使用yearRex的matcher方法在time字符串中匹配与正则表达式相符的结果放入Matcher类对象yearMatcher中，以下read方法同理
        if (yearMatcher.find()) {                          //使用yearMatcher的find方法判断是否有与正则表达式相符的结果，返回一个boolean值，以下read方法同理
            return readDigit(yearMatcher.group());         //如果有匹配结果，则使用readDigit方法将匹配到的字符串转化为int类型返回，以下read方法同理
        }
        return -1;
    }

    /**
     * readMonth方法中使用正则表达式/\d+/匹配到time字符串中的/xx/月份的信息
     * 并使用readDigit方法将匹配到的月份信息转化为int类型返回
     * @param time 20xx/xx/xx xx:xx:xx
     * @return 匹配到就返回int类型的月份信息否则返回-1
     */
    public int readMonth(String time) {
        Pattern monthRex = Pattern.compile("/\\d+/");   //使用正则表达式/\d+/匹配到time字符串中的/xx/月份的信息
        Matcher monthMatcher = monthRex.matcher(time);
        if (monthMatcher.find()) {
            return readDigit(monthMatcher.group());
        }
        return -1;
    }

    /**
     * readDay方法中使用正则表达式/\d+\s匹配到time字符串20xx/xx/xx xx:xx:xx中的/xx 日期的信息
     * 匹配到就使用readDigit方法将匹配到的日期信息转化为int类型返回，否则返回-1
     * @param time 20xx/xx/xx xx:xx:xx
     * @return 返回int类型的日期信息
     */
    public int readDay(String time) {
        Pattern dayRex = Pattern.compile("/\\d+\\s");   //使用正则表达式/\d+\s匹配到time字符串20xx/xx/xx xx:xx:xx中的/xx 日期的信息
        Matcher dayMatcher = dayRex.matcher(time);
        if (dayMatcher.find()) {
            return readDigit(dayMatcher.group());
        }
        return -1;
    }

    /**
     * readHour方法中使用正则表达式\s\d{2}:匹配到time字符串20xx/xx/xx xx:xx:xx中的 xx:时间的信息
     * 匹配到就使用readDigit方法将匹配到的时间信息转化为int类型返回，否则返回-1
     * @param time  20xx/xx/xx xx:xx:xx
     * @return  返回int类型的小时信息
     */
    public int readHour(String time) {
        Pattern hourRex = Pattern.compile("\\s\\d{2}:"); //匹配到time字符串20xx/xx/xx xx:xx:xx中的 xx:时间的信息
        Matcher hourMatcher = hourRex.matcher(time);
        if (hourMatcher.find()) {
            return readDigit(hourMatcher.group());
        }
        return -1;
    }

    /******************************************************************
     * Helper function
     *****************************************************************/

    /**
     * readDigit方法将传入的与正则表达式相匹配的字符串转化为int类型并返回
     * @param s 与正则表达式相匹配的字符串
     * @return  返回int类型的日期信息
     */
    public int readDigit(String s) {
        String strDigit = "";                           //初始化一个空字符串
        for (int i = 0; i < s.length(); ++i) {          //判断传入的字符串中每个字符是否是数字
            if (Character.isDigit(s.charAt(i))) {
                strDigit += s.charAt(i);                //如果是数字则将其加入strDigit字符串中
            }
        }
        return Integer.parseInt(strDigit);              //将存储日期信息的strDigit字符串转化为int类型并返回
    }

    /**
     * 重写了Time类的 toString方法，便于直接打印Time对象
     * @return 20xx-xx-xx-xx形式的字符串形式的日期
     */
    @Override
    public String toString() {
        return year + "-" + month + "-" + day + "-" + hour;
    }

    /**
     * 重写了Time类的 equals方法，年，月，日完全相同的两个Time类对象则返回true
     * @param o 传入的需要比较的对象
     * @return 满足相等条件则返回true，否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Time t) {                                                      //首先判断是否是在与Time对象进行比较
            return this.year == t.year && this.month == t.month && this.day == t.day;   //年，月，日三个属性完全相同则返回true,表示相同对象
        }
        return false;                                                                   //否则返回false
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
    /****************************************************************
     * Getter function
     ***************************************************************/

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
