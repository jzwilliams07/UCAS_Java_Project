package readIO;

import com.csvreader.CsvReader;
import dataContainer.DailyInfo;
import dataContainer.Info;
import dataContainer.Region;
import dataContainer.Time;
import org.jfree.data.time.Day;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Auther: xxxxlc
 * @Date: 2022/5/14 10:54
 * @Description: read csv
 */

public class Reader {

    private String filePath;
    private String fileName;

    private CsvReader csvReader;

    private List<DailyInfo> dailyInfo;

    private Map<Time, Integer> timeToIndex;

    private Map<String, Integer> readCategories;

    private Set<Day> readDaySet;



    public Reader(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public Reader(String filePath) {
        String[] buff = filePath.split("/");
        this.filePath = "";
        for (int i = 0; i < buff.length - 1; ++i) {
            this.filePath = this.filePath + buff[i] + "/";
        }
        this.fileName = buff[buff.length - 1];
    }

    public Map<String, Integer> initReadCategories() {
        Map<String, Integer> categories = new HashMap<>();
        categories.put("countryName", 2);
        categories.put("provinceName", 4);
        categories.put("province_confirmedCount", 7);
        categories.put("province_suspectedCount", 8);
        categories.put("province_curedCount", 9);
        categories.put("province_deadCount", 10);
        categories.put("updateTime", 11);
        return categories;
    }

    public void read(int readDay) throws IOException {
        readCategories = initReadCategories();
        dailyInfo = new ArrayList<>();
        timeToIndex = new HashMap<>();
        readDaySet = new HashSet<>();

        csvReader = new CsvReader(filePath + fileName, ',', StandardCharsets.UTF_8);
        csvReader.readHeaders();

        int day = 0;
        while (csvReader.readRecord()) {

            // 首先判断时间是否跨过一天
            String t = csvReader.get(readCategories.get("updateTime"));
            Time time = new Time(t);

            Day dayTemp = new Day(time.getDay(), time.getMonth(), time.getYear());
            // 如果时间跨过一天则创建新的 DailyInfo
            if (!readDaySet.contains(dayTemp)) {
                readDaySet.add(dayTemp);
                day++;
                if (day > readDay) {
                    break;
                }
                Map<Region, Info> infoByRegion = new HashMap<>();
                DailyInfo d = new DailyInfo(time, infoByRegion);
                dailyInfo.add(d);
                timeToIndex.put(time, day - 1);
            }


            String country = csvReader.get(readCategories.get("countryName"));
            String province = csvReader.get(readCategories.get("provinceName"));

            int confirmedCount = Integer.parseInt(csvReader.get(readCategories.get("province_confirmedCount")));
            int curedCount = Integer.parseInt(csvReader.get(readCategories.get("province_curedCount")));
            int deadCount = Integer.parseInt(csvReader.get(readCategories.get("province_deadCount")));
            int suspectedCount = Integer.parseInt(csvReader.get(readCategories.get("province_suspectedCount")));
            Info info = new Info(confirmedCount, curedCount, deadCount, suspectedCount);

            if (Objects.equals(country, "中国")) {
                Region region = new Region(country, province);
                dailyInfo.get(day - 1).setRegion(region, info);
            }
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public CsvReader getCsvReader() {
        return csvReader;
    }

    public List<DailyInfo> getDailyInfo() {
        return dailyInfo;
    }

    public Map<Time, Integer> getTimeToIndex() {
        return timeToIndex;
    }

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
