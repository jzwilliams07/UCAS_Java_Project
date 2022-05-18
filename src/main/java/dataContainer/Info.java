package dataContainer;

/**
 * @Auther:
 * @Date: 2022/5/17 16:19
 * @Description:
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
