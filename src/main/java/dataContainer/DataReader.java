package dataContainer;

import java.util.*;

/**
 * @Auther:
 * @Date: 2022/5/18 19:07
 * @Description:
 */

public class DataReader {

    private List<DailyInfo> dailyInfo;
    private Map<Time, Integer> timeToIndex;

    public DataReader() {

    }

    public DataReader(List<DailyInfo> dailyInfo, Map<Time, Integer> timeToIndex) {
        this.dailyInfo = dailyInfo;
        this.timeToIndex = timeToIndex;
    }

    public List<Time> getTime(String strStartTime, String strEndTime) {
        Time startTime = new Time(strStartTime);
        Time endTime = new Time(strEndTime);
        int startIndex = timeToIndex.get(startTime);
        int endIndex = timeToIndex.get(endTime);

        List<Time> times = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; ++i) {
            times.add(dailyInfo.get(i).getTime());
        }

        return times;
    }

    public List<Time> getTime() {
        List<Time> times = new ArrayList<>();

        for (DailyInfo info : dailyInfo) {
            times.add(info.getTime());
        }

        return times;
    }

    public List<Info> getInfoByRegion(Region region, List<Time> times) {
        List<Info> infos = new ArrayList<>();
        for (int i = 0; i < times.size(); ++i) {
            int index = timeToIndex.get(times.get(i));
            DailyInfo tmp = dailyInfo.get(i);
            if (tmp.getInfo(region) == null) {
                infos.add(infos.get(infos.size() - 1));
            }
            else {
                infos.add(tmp.getInfo(region));
            }
        }
        return infos;
    }

    public Map<Region, Info> getInfoByTime(Time time) {
        int index = timeToIndex.get(time);
        return dailyInfo.get(index).getInfoByRegion();
    }

    public Set<Region> getRegion(String strStartTime, String strEndTime) {
        Time startTime = new Time(strStartTime);
        Time endTime = new Time(strEndTime);
        int startIndex = timeToIndex.get(startTime);
        int endIndex = timeToIndex.get(endTime);

        Set<Region> regions = new HashSet<>();
        for (int i = startIndex; i <= endIndex; ++i) {
            regions.addAll(dailyInfo.get(i).getInfoByRegion().keySet());
        }

        return regions;
    }

    public Set<Region> getRegion() {
        Set<Region> regions = new HashSet<>();
        for (DailyInfo info : dailyInfo) {
            regions.addAll(info.getInfoByRegion().keySet());
        }

        return regions;
    }


    public static void main(String[] args) {
    }
}
