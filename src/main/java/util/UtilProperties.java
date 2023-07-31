package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilProperties {
    private static UtilProperties instancia;
    private static Properties properties;

    private UtilProperties(){
        InputStream inputStream = UtilProperties.class
                .getClassLoader().getResourceAsStream("properties.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static UtilProperties getInstance(){
        if (instancia == null)
            instancia = new UtilProperties();

        return instancia;
    }

    public String getProperties(String keyProperties){
        return properties.getProperty(keyProperties);
    }



}
