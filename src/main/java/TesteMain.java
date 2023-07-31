import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TesteMain {
    public static void main(String[] args) throws IOException {

        InputStream inputStream = TesteMain.class.getClassLoader().getResourceAsStream("properties.properties");
        Properties prop = new Properties();
        prop.load(inputStream);

        System.out.println(prop.getProperty("api.java_home"));


    }
}
