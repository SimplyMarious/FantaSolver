package com.spme.fantasolver.utility;

import com.spme.fantasolver.annotations.Generated;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Utility {
    private static Properties properties;
    private static InputStream fileInputStream;

    @Generated
    private Utility() {}

    public static void setPropertiesReadingTools(Properties properties, InputStream fileInputStream){
        Utility.properties = properties;
        Utility.fileInputStream = fileInputStream;
    }

    public static String getValueFromProperties(String key) throws IOException {
        properties.load(fileInputStream);
        return properties.getProperty(key);
    }

    public static boolean checkStringValidity(String string, int minLength, int maxLength){
        if(string == null || minLength < 0 || maxLength < 0 || minLength > maxLength){
            throw new IllegalArgumentException("Invalid argument");
        }
        return minLength <= string.length() && string.length() <= maxLength;
    }

    public static boolean areStringsDifferentFromEachOther(List<String> strings) {
        if(strings == null || strings.isEmpty() || strings.size() == 1){
            throw new IllegalArgumentException();
        }
        Set<String> stringSet = new HashSet<>(strings);
        return stringSet.size() == strings.size();
    }

    public static String getFormattedStrings(List<String> strings) {
        if(strings == null || strings.isEmpty()){
            throw new IllegalArgumentException();
        }
        StringBuilder formattedStrings = new StringBuilder();
        for (String string: strings) {
            formattedStrings.append(string).append(", ");
        }
        formattedStrings.setLength(formattedStrings.length() - 2);

        return formattedStrings.toString();
    }
}
