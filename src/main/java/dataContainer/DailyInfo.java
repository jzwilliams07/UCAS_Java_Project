package dataContainer;

import java.util.Map;
@SuppressWarnings({"all"})
/**
 * @Auther:
 * @Date: 2022/5/17 16:19
 * @Description: 本类用于存储每日的Info类的信息，配合Region类和Time类使用，实现按地区或时间查找Info类的疫情信息
 * 本类包括私有的Time类的本日的日期属性time，以及以hashmap形式存储的每日的各地区的疫情信息Map类属性 InfoByRegion
 */

public class DailyInfo {
    private Time time;                              //当前DailyInfo对象的具体时间
    private Map<Region, Info> InfoByRegion;         //以Map保存的当前日期的按地区存储的疫情信息

    public DailyInfo() {

    }

    /**
     * 使用 Time类对象time 和 按地区存储的疫情信息的hashmap初始化DailyInfo对象
     * @param time 当前DailyInfo对象的具体日期时间
     * @param InfoByRegion 以Map保存的当前日期的按地区存储的疫情信息
     */
    public DailyInfo(Time time, Map<Region, Info> InfoByRegion) {
        this.time = time;
        this.InfoByRegion = InfoByRegion;
    }
    /****************************************************************
     * Getter function & Setter function
     ***************************************************************/
    public Time getTime() {
        return time;
    }

    public Map<Region, Info> getInfoByRegion() {
        return InfoByRegion;
    }

    /**
     * getInfo方法说明：
     * 传入用户指定的所需查询的地区的对应的Region类对象region，
     * 首先判断该region是否存在于按地区区别的疫情信息的hashmap中
     * 不存在返回空，存在该地区则在InfoByRegion这个hashmap中取出该region对应的疫情信息
     * @param region 用户指定的传入的所需查找的地区对应的Region类对象
     * @return 不存在该地区则返回空，若能查找到则返回该地区对应的Info类对象
     */
    public Info getInfo(Region region) {
        if (!InfoByRegion.containsKey(region)) {            //判断传入的region是否存在于InfoByRegion的hashmap中
            return null;                                    //不存在则返回空
        }
        return InfoByRegion.get(region);                    //存在该Region则返回其对应的Info类对象
    }

    public void setRegion(Region region, Info info) {
        InfoByRegion.put(region, info);
    }

    public static void main(String[] args) {
    }
}
