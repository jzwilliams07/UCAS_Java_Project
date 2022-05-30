package dataContainer;
@SuppressWarnings({"all"})
/**
 * @Auther:
 * @Date: 2022/5/17 16:19
 * @Description: 本类用于存储csv表中的疫情相关信息，包括 confirmedCount确诊病例, curedCount治愈病例, deadCoun死亡案例,
 *               suspectedCount 疑似病例 四个 int属性 ，本类可配合Region类和Time类使用，满足按地区查找和按时间查找疫情信息两种不同的需求
 */

public class Info {
    // 已确诊人数
    protected int confirmedCount;

    // 已治愈总人数
    protected int curedCount;

    // 已死亡总人数
    protected int deadCount;

    // 今日确诊人数
    protected int suspectedCount;

    /**
     *
     * @param confirmedCount int类型的确诊病例数
     * @param curedCount    int类型的治愈病例数
     * @param deadCount     int类型的死亡病例数
     * @param suspectedCount    int类型的疑似病例数
     */
    public Info(int confirmedCount, int curedCount, int deadCount, int suspectedCount) {
        this.confirmedCount = confirmedCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
        this.suspectedCount = suspectedCount;
    }

    @Override
    public String toString() {
        return "Info{" +
                "confirmedCount=" + confirmedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", suspectedCount=" + suspectedCount +
                '}';
    }
    /****************************************************************
     * Getter function
     ***************************************************************/
    public int getConfirmedCount() {
        return confirmedCount;
    }

    public int getCuredCount() {
        return curedCount;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public int getSuspectedCount() {
        return suspectedCount;
    }

    public static void main(String[] args) {
        Info info = new Info(10, 10, 10, 10);
        System.out.println(info);
    }
}
