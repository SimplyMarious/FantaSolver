package com.spme.fantasolver.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utility {
    private static Properties properties;
    private static InputStream fileInputStream;

    public static void setPropertiesReadingTools(Properties properties, InputStream fileInputStream){
        Utility.properties = properties;
        Utility.fileInputStream = fileInputStream;
    }

    public static String getValueFromProperties(String key) throws IOException {
        properties.load(fileInputStream);
        return properties.getProperty(key);
    }

    public static boolean checkStringValidity(String string, int minLength, int maxLength){
        return minLength <= string.length() && string.length() <= maxLength;
    }
}
