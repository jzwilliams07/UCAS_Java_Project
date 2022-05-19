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
        if (!InfoByRegion.containsKey(region)) {
            return new Info(0, 0, 0, 0);
        }
        return InfoByRegion.get(region);
    }

    public void setRegion(Region region, Info info) {
        InfoByRegion.put(region, info);
    }

    public static void main(String[] args) {
    }
}
