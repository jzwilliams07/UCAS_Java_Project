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
