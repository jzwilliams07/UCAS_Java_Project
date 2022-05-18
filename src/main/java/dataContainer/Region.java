package dataContainer;

import java.util.Objects;

/**
 * @Auther:
 * @Date: 2022/5/18 16:45
 * @Description:
 */

public class Region {

    private String country;
    private String province;

    public Region() {

    }

    public Region(String country, String province) {
        this.country = country;
        this.province = province;
    }

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

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public static void main(String[] args) {
    }
}
