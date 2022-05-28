package readIO;

/*******************************************************
 * Compilation: javac Reader.java
 ******************************************************/

import com.csvreader.CsvReader;
import dataContainer.DailyInfo;
import dataContainer.Info;
import dataContainer.Region;
import dataContainer.Time;
import org.jfree.data.time.Day;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Auther: xxxxlc
 * @Date: 2022/5/14 10:54
 * @Description: 使用依赖 CsvReader 对数据文件csv进行读取，然后将读取的数据储存进相应的数据结构内
 * 该读取类是根据日期天数进行读取，可以读取任意天数的新冠疫情确诊病例的数据
 * 该类是针对 DXYArea.csv 文件写的读取方法，其他 .cvs 文件无法使用该类进行读取，主要是 read() 方法是针对该文件的方法
 * 如果要对其他文件进行读取，需修改 read() 方法
 */

public class Reader {

    private String filePath;                            // DXYArea.csv 文件所在的路径
    private String fileName;                            // 文件名：DXYArea.csv
    private CsvReader csvReader;                        // 依赖中的对象

    private List<DailyInfo> dailyInfo;                  // 存储 DailyInfo 类的列表
    private Map<Time, Integer> timeToIndex;             // 该符号表用来储存日期与List<DailyInfo>中下标的关系
    private Map<String, Integer> readCategories;        // 该符号表用来储存 .csv 文件中需要读取的目录与其列数的关系
    private Set<Day> readDaySet;                        // 该集合储存已读取的日期，以天为单位


    /**
     * 使用文件路径和文件名分别对该对象进行初始化
     * @param filePath 文件所在路径
     * @param fileName 文件名，必须以.csv后缀结尾
     * @throws NoSuchFieldException 如果不是csv文件则报错
     */
    public Reader(String filePath, String fileName) {
        if (isCsvFile(fileName)) {
            try {
                throw new NoSuchFieldException("读取的文件不是csv文件");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * 直接输入文件的路径和文件名，需要将其分开
     * @param filePath 文件路径(含路径名)
     */
    public Reader(String filePath) {
        String[] buff = filePath.split("/");
        this.filePath = "";
        for (int i = 0; i < buff.length - 1; ++i) {
            this.filePath = this.filePath + buff[i] + "/";
        }
        this.fileName = buff[buff.length - 1];
    }


    /**
     * 定制要读取csv文件内的目录与信息和列数之间的关系
     * @return 将该关系用一个符号表进行存储
     */
    public Map<String, Integer> initReadCategories() {
        Map<String, Integer> categories = new HashMap<>();
        categories.put("countryName", 2);                       // 地区所属国家
        categories.put("provinceName", 4);                      // 地区省份
        categories.put("province_confirmedCount", 7);           // 每日确诊人数
        categories.put("province_suspectedCount", 8);           // 总确诊人数
        categories.put("province_curedCount", 9);               // 总治愈人数
        categories.put("province_deadCount", 10);               // 总死亡人数
        categories.put("updateTime", 11);                       // 时间
        return categories;
    }

    /**
     * 读取 csv 文件中的疫情数据
     * @param readDay 需要在文件中读取的具体天数，从此刻向之前读取
     * @throws IOException 如果读取错误就抛出读取异常
     */
    public void read(int readDay) throws IOException {
        // 获取需要读取的目录的列表
        readCategories = initReadCategories();
        // 初始化每日疫情信息的列表，使用 ArrayList 可变数组，不用担心数组大小变化
        dailyInfo = new ArrayList<>();
        timeToIndex = new HashMap<>();
        readDaySet = new HashSet<>();

        // 初始化 csvReader 对象
        csvReader = new CsvReader(filePath + fileName, ',', StandardCharsets.UTF_8);
        // 读取 csv 文件的表头
        csvReader.readHeaders();

        // 初始化天数为 0，没读取一天该变量+1，直到其大于等于 readDay 则停止读取
        int day = 0;
        while (csvReader.readRecord()) {

            // 首先判断时间是否跨过一天
            String t = csvReader.get(readCategories.get("updateTime"));
            // 使用时间字符串初始化时间类
            Time time = new Time(t);

            // 使用java.time中的Day类，对该类进行初始化，将该类放入时间的集合readDaySet，如果集合中存在该天，证明这一条数据不属于
            // 新的一天，则不需要将数据存入下一天中
            Day dayTemp = new Day(time.getDay(), time.getMonth(), time.getYear());

            // 如果时间跨过一天则创建新的 DailyInfo
            if (!readDaySet.contains(dayTemp)) {
                readDaySet.add(dayTemp);
                day++;
                // 如果天数大于需要读取的天数则停止读取
                if (day > readDay) {
                    break;
                }
                // 在新的 DailyInfo 中初始化它的成员，并将时间放入它的成员之中
                Map<Region, Info> infoByRegion = new HashMap<>();
                DailyInfo d = new DailyInfo(time, infoByRegion);
                dailyInfo.add(d);
                // 将时间与 DailyInfo 列表中的下标的联系使用map存储起来
                timeToIndex.put(time, day - 1);
            }

            // 读取此行的地区信息：国家和省份
            String country = csvReader.get(readCategories.get("countryName"));
            String province = csvReader.get(readCategories.get("provinceName"));

            // 读取此行的疫情信息：每日确诊人数、总确诊人数、总死亡人数、总治愈人数
            int confirmedCount = Integer.parseInt(csvReader.get(readCategories.get("province_confirmedCount")));
            int curedCount = Integer.parseInt(csvReader.get(readCategories.get("province_curedCount")));
            int deadCount = Integer.parseInt(csvReader.get(readCategories.get("province_deadCount")));
            int suspectedCount = Integer.parseInt(csvReader.get(readCategories.get("province_suspectedCount")));
            // 创建一个新的 Info 对象用来存储每日疫情信息
            Info info = new Info(confirmedCount, curedCount, deadCount, suspectedCount);

            // 该判断语句是限定只读取国家为中国的疫情数据信息
            if (Objects.equals(country, "中国")) {
                Region region = new Region(country, province);
                dailyInfo.get(day - 1).setRegion(region, info);
            }
        }
    }

    /******************************************************************
     * Helper function
     *****************************************************************/

    /**
     * 判断该文件是否是 csv 文件
     * @param fileName 文件名
     * @return 是 csv 文件返回 true，不是 csv 文件返回 false
     */
    public boolean isCsvFile(String fileName) {
        // 使用 spilt() 对文件字符串进行分割
        String[] buff = fileName.split("\\.");
        return buff[buff.length - 1].equals("csv");
    }


    /****************************************************************
     * Getter function
     ***************************************************************/

    /**
     * 返回读取的文件路径
     * @return 返回文件夹的路径的字符串
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 返回文件名
     * @return 返回文件名的字符串
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 返回读取文件的对象
     * @return 对象？
     */
    public CsvReader getCsvReader() {
        return csvReader;
    }

    /**
     * 返回存储每日信息<DailyInfo>的列表
     * @return 存储每日信息<DailyInfo>的列表
     */
    public List<DailyInfo> getDailyInfo() {
        return dailyInfo;
    }

    /**
     * 返回时间与列表下标转化的Map
     * @return 返回时间与列表下标转化的Map
     */
    public Map<Time, Integer> getTimeToIndex() {
        return timeToIndex;
    }

    /**
     * 返回需要读取的csv文件内的目录与列数的Map
     * @return 返回需要读取的csv文件内的目录与列数的Map
     */
    public Map<String, Integer> getReadCategories() {
        return readCategories;
    }

    public static void main(String[] args) {
        Reader reader = new Reader("Resources/DXYArea.csv");
        try {
            reader.read(10);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}
