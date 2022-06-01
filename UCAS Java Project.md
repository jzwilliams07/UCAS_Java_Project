# UCAS Java Project

## 作业说明

- 本作业希望读取包含不同地区不同时间内的疫情信息的csv文件
- 通过 readIO 包实现读取csv文件中的信息的功能
- 通过 dataContainer 包实现按时间或按地区存储读取到的疫情信息供GUI使用和查找
- 通过 GUI 包实现按地区或时间展示疫情数据的图形界面


## 数据说明

DXYArea.csv

数据来源：[BlankerL/DXY-COVID-19-Data: 2019新型冠状病毒疫情时间序列数据仓库 | COVID-19/2019-nCoV Infection Time Series Data Warehouse (github.com)](https://github.com/BlankerL/DXY-COVID-19-Data)

该.csv文件包含了从新冠疫情爆发至今的各个地区每日的确诊人数、治愈人数、死亡人数的变化情况，选择性读取表格中以下数据完成Project

| 表头             | 包含信息     |
| :------------- | -------- |
| continent      | 州        |
| country        | 国家       |
| province       | 省份       |
| confirmedCount | 总共确诊病例数量 |
| suspectedCount | 当日确诊病例数量 |
| curedCount     | 总共治愈病例   |
| deadCount      | 总共死亡病例   |
| updateTime     | 更新时间     |

---



## Package readIO

**本包功能**

- 该模块主要用于读取.csv文件中的新冠疫情数据

**class Reader** 

**本类功能** 

- 使用依赖 CsvReader 对数据文件csv进行读取，然后将读取的数据储存进相应的数据结构内
- 该读取类是根据日期天数进行读取，可以读取任意天数的新冠疫情确诊病例的数据
- 该类是针对 DXYArea.csv 文件写的读取方法，其他 .cvs 文件无法使用该类进行读取，主要是 read() 方法是针对该文件的方法
- 如果要对其他文件进行读取，需修改 read() 方法

**具体实现**

借助依赖csvreader进行csv文件的读取

```xml
<dependency>       			  
	<groupId>net.sourceforge.javacsv</groupId>
	<artifactId>javacsv</artifactId>
	<version>2.0</version>
</dependency>
```

**本类属性** 

```java
private String filePath;                            // DXYArea.csv 文件所在的路径
    private String fileName;                            // 文件名：DXYArea.csv
    private CsvReader csvReader;                        // 依赖中的对象
    private List<DailyInfo> dailyInfo;                  // 存储 DailyInfo 类的列表
    private Map<Time, Integer> timeToIndex;      	   // 该符号表用来储存日期与List<DailyInfo>中下标的关系
    private Map<String, Integer> readCategories; 	   // 该符号表用来储存 .csv 文件中需要读取的目录与其列数的关系
    private Set<Day> readDaySet;                        // 该集合储存已读取的日期，以天为单位
```

**本类重要方法说明** 

本类初始化 构造方法：

传入文件路径，并且用户指定文件名的构造方法，以及只传入文件路径，并未说明具体文件名的构造方法：

```java
     /**
     *功能说明 
     *使用文件路径和文件名分别对该对象进行初始化
     * @param filePath 文件所在路径
     * @param fileName 文件名，必须以.csv后缀结尾
     * @throws NoSuchFieldException 如果不是csv文件则报错
     */
public Reader(String filePath, String fileName)
  	/**
     * 直接输入文件的路径和文件名，需要将其分开
     * @param filePath 文件路径(含路径名)
     */
public Reader(String filePath)
```

*initReadCategories方法*

```java
    /**
     *功能说明： 
     *定制要读取csv文件内的目录与信息和列数之间的关系
     * @return 将该关系用一个符号表进行存储
     */
public Map<String, Integer> initReadCategories()
```

*read方法*

```java
/**
 * 功能说明：
 * 读取 csv 文件中的疫情数据
 * @param readDay 需要在文件中读取的具体天数，从此刻向之前读取
 * @throws IOException 如果读取错误就抛出读取异常
 */
public void read(int readDay) throws IOException
```

*isCsvFile方法*

```java
/**
 * 功能说明：
 * 判断该文件是否是 csv 文件
 * @param fileName 文件名
 * @return 是 csv 文件返回 true，不是 csv 文件返回 false
 */
public boolean isCsvFile(String fileName)
```



---

## Package dataContainer

**本包说明** 

- 该包主要用于存储读取的数据
- 采取一次读完.csv文件的方法，将所需的数据一次性存储以供GUI查找
- 设计class用于存储数据信息及查询信息

**本包 类说明** 

**class DataReader** 

*类说明*

- 本类用于读取dataContainer这个包中的其他类存储的信息，通过一个时间与索引的hashmap实现按索引访问不同时间或时间段的需求
- 通过一个arraylist按索引存储每日的Info类对象（包含各项疫情信息），通过本类的getInfoByTime方法即可实现按时间查找每日疫情信息的需求
- 本类还通过一个getInfoByRegion方法实现按地区查找指定时间内的疫情信息

*属性&方法说明* 

```java
private List<DailyInfo> dailyInfo   //该List用于按索引存储每日的疫情信息（包括不同地区的信息）
private Map<Time, Interge> timeToIndex	//该Map用于存储时间与索引的关系，以实现通过索引查找到某日信息点的需求
/**
*DataReader构造函数说明：
* 通过传入dailyInfo包含疫情信息的List和存储时间-索引关系的Map来初始化DataReader对象
* @param dailyInfo 按索引存储的每日疫情信息
* @param timeToIndex   存储日期与索引的关系
*/
public DataReader(DailyInfo dailyInfo, Map<Time, Interge> timeToIndex)	
 /**
 *getTime方法(有参)说明：
 * 该方法用于获取保存了用户指定的起始和终止时间之间的时间对应的Time对象的List
 * @param strStartTime 用户指定的起始时间，格式为20xx/xx/xx xx:xx:xx
 * @param strEndTime    用户指定的终止时间，格式为20xx/xx/xx xx:xx:xx
 * @return 存储Time类对象的List
 */
public List<Time> getTime(String strStartTime, String strEndTime)
/**
*getTime方法(无参)说明：
* 用户不指定起始和终止时间，则将所有日期对应的Time类对象存入times这个list中
* @return 存储Time类对象的List
*/
public List<Time> getTime()
 /**
 *getInfoByRegion方法说明：
 * 本方法传入所需查询的地区Region类对象和存储时间的list，查找某个用户指定地区的指定时间段内的疫情信息
 * @param region 用户传入的所需查找的地区的Region类对象
 * @param times 用户指定的时间段，不指定则为csv文件包括的所有时间
 * @return  一个存储Info类对象的List
 */
public List<Info> getInfoByRegion(Region region, List<Time> times)
/**
*getInfoByTime方法说明：
* 本方法传入所需查找的某个日期的Time类对象，通过timeToIndex这个hashmap获取到该日期对应的索引下标，
* 再通过该索引下标获取到当日的dailyInfo疫情信息，随后通过getInfoByRegion方法获取到该日期中所有地区的疫情信息
* @param time  传入的所需查找点的日期的Time对象
* @return  一个含有当日所有地区信息的Map
*/
public Map<Region, Info> getInfoByTime(Time time)
/**
*getRegion(有参)方法说明：
* 本方法传入用户指定的起始时间和终止时间字符串将其全部转化为Time对象，通过自定义方法获取其对应索引，
* 通过dailyInfo这个List获取到用户指定时间范围内每一天的疫情数据通过自定义方法获得Map<Region,Info>,
* 再通过hashmap的keyset方法获取到所有region
* 填入regions集合中并返回，这样就得到了用户指定时间范围内存在数据的所有地区的名字
* @param strStartTime  用户指定的起始时间字符串
* @param strEndTime    用户指定的终止时间字符串
* @return  一个包含指定时间段内的所有Region对象的集合
*/
public Set<Region> getRegion(String strStartTime, String strEndTime)
 /**
 *getRegion(无参)方法说明：
 * 用于用户未指定日期，默认获取所有时间内存在疫情信息的Region
 * @return 一个包含所有时间段内有疫情信息的Region对象的集合
 */
public Set<Region> getRegion()
```
**class DailyInfo** 

*类说明*

- 本类用于存储每日的Info类的信息，配合Region类和Time类使用，实现按地区或时间查找Info类的疫情信息
- 本类包括私有的Time类的本日的日期属性time，以及以hashmap形式存储的每日的各地区的疫情信息Map类属性 InfoByRegion 

*属性&方法说明*

```java
private Time time;                              //当前DailyInfo对象的具体时间
private Map<Region, Info> InfoByRegion;         //以Map保存的当前日期的按地区存储的疫情信息
 /**
     *DailyInfo构造方法说明
     * 使用 Time类对象time 和 按地区存储的疫情信息的hashmap初始化DailyInfo对象
     * @param time 当前DailyInfo对象的具体日期时间
     * @param InfoByRegion 以Map保存的当前日期的按地区存储的疫情信息
     */
public DailyInfo(Time time, Map<Region, Info> InfoByRegion)
/**
     * getInfo方法说明：
     * 传入用户指定的所需查询的地区的对应的Region类对象region，
     * 首先判断该region是否存在于按地区区别的疫情信息的hashmap中
     * 不存在返回空，存在该地区则在InfoByRegion这个hashmap中取出该region对应的疫情信息
     * @param region 用户指定的传入的所需查找的地区对应的Region类对象
     * @return 不存在该地区则返回空，若能查找到则返回该地区对应的Info类对象
     */
public Info getInfo(Region region)
```

**class Time**

*类说明*

- 该类以 year month day hour四个 int 属性存储时间信息
- 该类通过四个read方法通过正则表达式匹配从csv表中time一栏中的字符串中获取时间信息，配合Info类使用，实现按时间查找疫情信息的需求

*属性&方法说明* 

```java
int year
int month
int day
int hour

/**
     * Time构造方法1说明
     * Time类对象中四个分别用于存储年 月 日 时的四个int变量
     * @param year  存储年份信息
     * @param month 存储月份信息
     * @param day   存储日期信息
     * @param hour  存储时间信息
     */
public Time(int year, int month, int day, int hour)
/**	
     * Time构造方法2说明
     * 这个初始化函数建立用来处理.csv中的字符串: 20xx/xx/xx xx:xx:xx
     * @param time 20xx/xx/xx xx:xx:xx
     */
public Time(String time)
/**  
     * setTime方法说明：
     * 使用四个编写的自定义read方法从传入的time字符串中分别读取并转化为int类型的年、月、日、具体时间的信息
     * @param time 20xx/xx/xx xx:xx:xx
     */
public void setTime (String time)
/**  
     * readYear方法说明：
     * readYear方法传入一个csv文件当中的时间字符串，使用正则表达式\\d{4}匹配到20XX关于年份的信息，
     * 并且使用readDigit方法将匹配的年份信息的字符串转化为int类型并返回
     * @param time 20xx/xx/xx xx:xx:xx
     * @return 匹配到就返回int类型的年份信息，否则返回-1
     */
public int readYear(String time)
 /**
     * readMonth方法说明：
     * readMonth方法中使用正则表达式/\d+/匹配到time字符串中的/xx/月份的信息
     * 并使用readDigit方法将匹配到的月份信息转化为int类型返回
     * @param time 20xx/xx/xx xx:xx:xx
     * @return 匹配到就返回int类型的月份信息否则返回-1
     */
public int readMonth(String time)
 /** 
     * readDay方法说明：
     * readDay方法中使用正则表达式/\d+\s匹配到time字符串20xx/xx/xx xx:xx:xx中的/xx 日期的信息
     * 匹配到就使用readDigit方法将匹配到的日期信息转化为int类型返回，否则返回-1
     * @param time 20xx/xx/xx xx:xx:xx
     * @return 返回int类型的日期信息
     */
public int readDay(String time)
 /**
     * readHour方法说明：
     * readHour方法中使用正则表达式\s\d{2}:匹配到time字符串20xx/xx/xx xx:xx:xx中的 xx:时间的信息
     * 匹配到就使用readDigit方法将匹配到的时间信息转化为int类型返回，否则返回-1
     * @param time  20xx/xx/xx xx:xx:xx
     * @return  返回int类型的小时信息
     */
public int readHour(String time)
/**
     * readDigit方法说明：
     * readDigit方法将传入的与正则表达式相匹配的字符串转化为int类型并返回
     * @param s 与正则表达式相匹配的字符串
     * @return  返回int类型的日期信息
     */
public int readDigit(String s)
```
}

**class Region** 

*类说明* 

- 该类用于保存地区信息
- 包括国家country，和省份province两个字符串属性，配合Info类使用，实现按地区查找疫情信息的需求

*属性&方法说明* 

```java
String country
String province
/**
     * Region构造方法(有参)说明
     * Region类中的两个字符串变量 country, province 分别用于保存国家和省份信息
     * @param country   国家
     * @param province  省份
     *
     */
public Region(String country, String province)
/**
     * equals(重写)方法说明：
     * 重写了equals方法，country和province两个属性相同则两个Region对象相同
     * @param o  传入的需要进行比较的另一个对象
     * @return  若满足相等条件则返回true否则返回false
     */
@Override
public boolean equals(Object o)

```
}

**class Info** 

*类说明* 

- 本类用于存储csv表中的疫情相关信息，包括 confirmedCount确诊病例, curedCount治愈病例, deadCoun死亡案例，suspectedCount 疑似病例 四个 int属性
- 本类可配合Region类和Time类使用，满足按地区查找和按时间查找疫情信息两种不同的需求

*属性&方法说明* 

```java
/**
     * Info构造方法(有参)方法说明：
     * @param confirmedCount int类型的确诊病例数
     * @param curedCount    int类型的治愈病例数
     * @param deadCount     int类型的死亡病例数
     * @param suspectedCount    int类型的疑似病例数
     */
public Info(int confirmedCount, int curedCount, int deadCount, int suspectedCount)

```
---

## Package GUI 

**本包功能** 

- 该模块主要用于展示读取的数据
- 使用SwingX与JfreeChart依赖编写界面

**本包 类说明** 

**class BarChart**

*类说明* 

- 该类继承 JFrame，用于画出每日确诊人数最多的五个省份的柱状图，返回的是 JFreeChart 类

*属性&方法说明* 

```java
/**
 * createDatasetByRegion方法说明：
 * 根据每日确诊人数选取每日确诊人数最多的五个省份，生成画图的数据类 CategoryDataset
 * @param InfoByRegion 该日的每个地区的确诊信息
 * @return 返回用于画柱状图的数据 CategoryDataset
 */
public CategoryDataset createDatasetByRegion(Map<Region, Info> InfoByRegion)
/**
     * createChartByRegion方法说明
     * 根据传入的dataset画出柱状图
     * @param dataset 含地区的每日确证人数
     * @return JFrameChart 图表对象
     */
public JFreeChart createChartByRegion(CategoryDataset dataset)
```

**class LineChart**

*类说明* 

- 根据数据集画出确诊人数，死亡人数，治愈人数随时间的变化的曲线图

*属性&方法说明*

```java
/**
 * createChartByTime方法说明：
 * 根据数据集 xyDataset 画出确诊人数，死亡人数，治愈人数随时间的变化的曲线图
 * @param xyDataset 数据集
 * @return JFrameChart 曲线图
 */
public JFreeChart createChartByTime(XYDataset xyDataset)
/**
 * createDatasetByTime方法说明：
 * 根据Time类型的List和infos类型的List生成带时间戳的数据集
 * @param times 时间的列表
 * @param infos 对应每个时间的疫情信息：确诊人数，死亡人数，治愈人数
 * @return 带时间戳的数据集(横坐标为时间)
 */
public TimeSeriesCollection createDatasetByTime(List<Time> times, List<Info> infos)
```

**class MyFrame** 

*类说明* 

- 该类为程序的主框架，为最基本的界面，包含了两个按钮和两个子界面

*属性&方法说明* 

```java
private JPanel contentPane;                             // 主界面
public CardLayout cardLayout = new CardLayout();        // 界面的布局控制对象
private JButton buttonByTime;                           // 时间按钮，按一下切换界面
private JButton buttonByRegion;                         // 地区按钮，按一下切换界面
private PageByTime page1;                               // 按时间画柱状图界面
private PageByRegion page2;                             // 按地区画折线图界面
private DataReader dataReader;                          // 按需求返回数据的数据读取对象
/**
 * MyFrame方法说明：
 * 初始化主界面，初始化时间界面和地区界面，接受数据读取对象 dataReader
 * @param dataReader 按需返回需要的数据类型
 */
public MyFrame(DataReader dataReader)
/**
* 主界面初始化方法
*/
public MyFrame()
/**
* setDataReader方法说明：
* 设置 dataReader
* @param dataReader
*/
public void setDataReader(DataReader dataReader)
/**
 * setPage1方法说明：
 * 将待 Time 的列表传递给时间界面，根据时间设置三个下拉框的参数
 * @param times
 */
public void setPage1(List<Time> times)
/**
* setPage2方法说明：
* 将地区的列表传递给地区界面，根据地区设置三个下拉框的参数
* @param regions
*/
public void setPage2(Set<Region> regions)
```

**class PageByRegion** 

*类说明*

- 根据所选地区展示该地区随时间变化的确诊人数、死亡人数、治愈人数的折线图

*属性&方法说明* 

```java
private JComboBox<String> countryBox;           // 下拉框：选择国家
private JComboBox<String> provinceBox;          // 下拉框：选择地区
private JButton generateButton;                 // 生成图像的按钮
private ChartPanel chartPanel;                  // 折线图界面
private LineChart xy = new LineChart();         // 折线图
private XYDataset dataset;                      // 折线图数据集
private DataReader dataReader;                  // 数据读取器
/**
 * PageByRegion(无参)构造方法说明：
 * 初始化界面的样式、布局、颜色等参数
 */
public PageByRegion()
 /**
  * plotLineChart方法说明：
  * 根据传入的地区，从数据读取器dataReader中获得相应的数据，画出折线图
  * @param region 传入的地区
  */
public void plotLineChart(Region region)
/**
 * setDataReader方法说明：
 * 设置数据获取器
 * @param dataReader
 */
public void setDataReader(DataReader dataReader)
 /**
   * setCountryBox方法说明：
   * 根据地区设置国家下拉框中的选项
   * @param regions
   */
public void setCountryBox(Set<Region> regions)
/**
 * setProvinceBox方法说明：
 * 根据国家地区设置省份下拉框的选型
 * @param regions
 */
public void setProvinceBox(Set<Region> regions)
```

**class PageByTime**

*类说明*

- 根据时间的选取画出在这个时刻确诊人数前五的省份的柱状图

*属性&方法说明* 

```java
private JComboBox<String> yearBox;                  // 选择年份的下拉框
private JComboBox<String> monthBox;                 // 选择月份的下拉框
private JComboBox<String> dayBox;                   // 选择日期的下拉框
private JButton generateButton;                     // 生成折线图的按钮
private DataReader dataReader;                      // 数据获取器
private BarChart xy = new BarChart();               // 柱状图
private CategoryDataset dataset;                    // 数据集
private ChartPanel chartPanel;                      // 画柱状图的界面
 /**
 * plotBarChart方法说明：
 * 根据传入的数据，画出柱状图
 * @param InfoByRegion 数据获取器传入的各个地区的疫情数据
 */
public void plotBarChart(Map<Region, Info> InfoByRegion)
 /**
 * setYearBox方法说明：
 * 设置年份盒子内的下拉框选型
 * @param times
 */
public void setYearBox(List<Time> times)
/**
 * setMonthBox方法说明:
 * 设置月份盒子内的下拉框选型
 * @param times
 */
public void setMonthBox(List<Time> times)
 /**
 * setDayBox方法说明：
 * 设置天数盒子内的下拉框选型
 * @param times
 */
public void setDayBox(List<Time> times)
/**
 * setDataReader方法说明：
 * 设置数据获取器
 * @param dataReader
 */
public void setDataReader(DataReader dataReader)
```

