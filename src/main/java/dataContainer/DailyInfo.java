package dataContainer;

import java.util.Map;

/**
 * @Auther:
 * @Date: 2022/5/17 16:19
 * @Description:
 */

public class DailyInfo {
    private Time time;
    private Map<Region, Info> InfoByRegion;

    public DailyInfo() {

    }

    public DailyInfo(Time time, Map<Region, Info> InfoByRegion) {
        this.time = time;
        this.InfoByRegion = InfoByRegion;
    }

    public Time getTime() {
        return time;
    }

    public Map<Region, Info> getInfoByRegion() {
        return InfoByRegion;
    }

    public Info getInfo(Region region) {
        return InfoByRegion.get(region);
    }

    public static void main(String[] args) {
    }
}
