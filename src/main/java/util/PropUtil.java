package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
    public static Properties loadProperties(String propertiesPath) {
        try (InputStream input = new FileInputStream(propertiesPath)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException("Properties file is not initialized",e);
        }
    }
}
