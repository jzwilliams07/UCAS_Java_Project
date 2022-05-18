import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther:
 * @Date: 2022/5/18 18:49
 * @Description:
 */

public class Log4jTest {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(Log4jTest.class);
        LOG.info("开始记录日志...");
        LOG.info("循环打印0到10之间的整数");
        for(int i=0;i<10;i++) {
            System.out.println(i);
        }
        LOG.info("结束日志!");
    }
}
