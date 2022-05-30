package dataContainer;

import java.util.Objects;
@SuppressWarnings({"all"})
/**
 * @Auther:
 * @Date: 2022/5/18 16:45
 * @Description: 该类用于保存地区信息，
 *               包括国家country，和省份province两个字符串属性，配合Info类使用，实现按地区查找疫情信息的需求
 */

public class Region {

    private String country;
    private String province;

    public Region() {

    }

    /**
     * Region类中的两个字符串变量 country, province 分别用于保存国家和省份信息
     * @param country   国家
     * @param province  省份
     *
     */
    public Region(String country, String province) {
        this.country = country;
        this.province = province;
    }

    /**
     * 重写了equals方法，country和province两个属性相同则两个Region对象相同
     * @param o  传入的需要进行比较的另一个对象
     * @return  若满足相等条件则返回true否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Region r) {
            return Objects.equals(this.country, r.country) && Objects.equals(this.province, r.province);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 0;
        h = 31 * h + country.hashCode();
        h = 31 * h + province.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return country + province;
    }
    /****************************************************************
     * Getter function & Setter function
     ***************************************************************/
    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public static void main(String[] args) {
    }
}
