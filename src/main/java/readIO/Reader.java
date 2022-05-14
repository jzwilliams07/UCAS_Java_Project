package readIO;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: xxxxlc
 * @Date: 2022/5/14 10:54
 * @Description: read csv
 */

public class Reader {

    public static void read() throws IOException {
        CsvReader csvReader = new CsvReader("Resources/DXYArea.csv", ',', StandardCharsets.UTF_8);

        csvReader.readHeaders();
        System.out.println(csvReader.getHeader(0));
        int n = 0;

        while (csvReader.readRecord() && n <= 10) {
            System.out.println(csvReader.get(1));
            n++;
        }
    }

    public static void main(String[] args) {
        try {
            read();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
