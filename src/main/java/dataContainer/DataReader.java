package dataContainer;

import java.util.*;
@SuppressWarnings({"all"})
/**
 * @Auther:
 * @Date: 2022/5/18 19:07
 * @Description: 本类用于读取dataContainer这个包中的其他类存储的信息，通过一个时间与索引的hashmap实现按索引访问不同时间或时间段的需求，
 * 通过一个arraylist按索引存储每日的Info类对象（包含各项疫情信息），通过本类的getInfoByTime方法即可实现按时间查找每日疫情信息的需求，本类还通过一个getInfoByRegion方法
 * 实现按地区查找指定时间内的疫情信息
 */

public class DataReader {

    private List<DailyInfo> dailyInfo;      //该List用于按索引存储每日的疫情信息（包括不同地区的信息）
    private Map<Time, Integer> timeToIndex; //该Map用于存储时间与索引的关系，以实现通过索引查找到某日信息点的需求

    public DataReader() {

    }

    /**
     * 通过传入dailyInfo包含疫情信息的List和存储时间-索引关系的Map来初始化DataReader对象
     * @param dailyInfo 按索引存储的每日疫情信息
     * @param timeToIndex   存储日期与索引的关系
     */
    public DataReader(List<DailyInfo> dailyInfo, Map<Time, Integer> timeToIndex) {
        this.dailyInfo = dailyInfo;
        this.timeToIndex = timeToIndex;
    }

    /**
     * 该方法用于获取保存了用户指定的起始和终止时间之间的时间对应的Time对象的List
     * @param strStartTime 用户指定的起始时间，格式为20xx/xx/xx xx:xx:xx
     * @param strEndTime    用户指定的终止时间，格式为20xx/xx/xx xx:xx:xx
     * @return 存储Time类对象的List
     */
    public List<Time> getTime(String strStartTime, String strEndTime) {
        Time startTime = new Time(strStartTime);    //将传入的起始时间字符串转化为一个Time类对象
        Time endTime = new Time(strEndTime);        //将终止时间字符串也转化为一个Time类对象
        int startIndex = timeToIndex.get(startTime);    //通过timeToIndex这个map获取起始时间对应的索引
        int endIndex = timeToIndex.get(endTime);        //通过timeToIndex这个map获取终止时间对应的索引

        List<Time> times = new ArrayList<>();           //创建一个用于保存起始时间和终止时间之间的Time对象的List
        for (int i = startIndex; i <= endIndex; ++i) {
            times.add(dailyInfo.get(i).getTime());      //通过索引取出dailyinfo这个list中每个对象存储的与索引相对应的时间并存入times这个list中
        }

        return times;
    }

    /**
     * 用户不指定起始和终止时间，则将所有日期对应的Time类对象存入times这个list中
     * @return 存储Time类对象的List
     */
    public List<Time> getTime() {
        List<Time> times = new ArrayList<>();

        for (DailyInfo info : dailyInfo) {              //将dailyInfo中所有日期存入times这个list中
            times.add(info.getTime());
        }

        return times;
    }

    /**
     * 本方法传入所需查询的地区Region类对象和存储时间的list，查找某个用户指定地区的指定时间段内的疫情信息
     * @param region 用户传入的所需查找的地区的Region类对象
     * @param times 用户指定的时间段，不指定则为csv文件包括的所有时间
     * @return  一个存储Info类对象的List
     */
    public List<Info> getInfoByRegion(Region region, List<Time> times) {
        List<Info> infos = new ArrayList<>();
        for (int i = 0; i < times.size(); ++i) {
            int index = timeToIndex.get(times.get(i));  //得到该时间下对应的索引
            DailyInfo tmp = dailyInfo.get(i);           //通过索引取出存储在dailyInfo这个List中的当日所有信息
            if (tmp.getInfo(region) == null) {          //判断当日信息中是否存储了该地区的信息
                infos.add(infos.get(infos.size() - 1)); //若为空，则往当前infos这个索引中add上一个存入的那个元素（即疫情数据与前一天相同）
            }
            else {
                infos.add(tmp.getInfo(region));         //若能查找到该Region的信息，则将这一时间中的该Region的信息取出并且添加到infos这个List中
            }
        }
        return infos;
    }

    /**
     * 本方法传入所需查找的某个日期的Time类对象，通过timeToIndex这个hashmap获取到该日期对应的索引下标，
     * 再通过该索引下标获取到当日的dailyInfo疫情信息，随后通过getInfoByRegion方法获取到该日期中所有地区的疫情信息
     * @param time  传入的所需查找点的日期的Time对象
     * @return  一个含有当日所有地区信息的Map
     */
    public Map<Region, Info> getInfoByTime(Time time) {
        int index = timeToIndex.get(time);                 //获取传入的日期的Time对象对应的索引下标
        return dailyInfo.get(index).getInfoByRegion();     //通过当前日期的索引下标获取在dailyInfo中当前日期的疫情信息，并调用getInfoByRegion方法获取当日所有地区信息
    }

    /**
     * 本方法传入用户指定的起始时间和终止时间字符串将其全部转化为Time对象，通过自定义方法获取其对应索引，
     * 通过dailyInfo这个List获取到用户指定时间范围内每一天的疫情数据通过自定义方法获得Map<Region,Info>,再通过hashmap的keyset方法获取到所有region
     * 填入regions集合中并返回，这样就得到了用户指定时间范围内存在数据的所有地区的名字
     * @param strStartTime  用户指定的起始时间字符串
     * @param strEndTime    用户指定的终止时间字符串
     * @return  一个包含指定时间段内的所有Region对象的集合
     */
    public Set<Region> getRegion(String strStartTime, String strEndTime) {
        Time startTime = new Time(strStartTime);        //将传入日期字符串转为Time对象
        Time endTime = new Time(strEndTime);
        int startIndex = timeToIndex.get(startTime);    //通过timeToIndex这个Map获取到时间对应的索引下标
        int endIndex = timeToIndex.get(endTime);

        Set<Region> regions = new HashSet<>();
        for (int i = startIndex; i <= endIndex; ++i) {
            regions.addAll(dailyInfo.get(i).getInfoByRegion().keySet());    //通过索引获取到当日点的dailyInfo并将当日存在疫情信息的所有Region添加到Set regions中
        }

        return regions;
    }

    /**
     * 用于用户未指定日期，默认获取所有时间内存在疫情信息的Region
     * @return
     */
    public Set<Region> getRegion() {
        Set<Region> regions = new HashSet<>();
        for (DailyInfo info : dailyInfo) {
            regions.addAll(info.getInfoByRegion().keySet());    //将dailyInfo中每一个元素中存在的Region对象全部加入到regions这个Set中
        }

        return regions;
    }


    public static void main(String[] args) {
    }
}
