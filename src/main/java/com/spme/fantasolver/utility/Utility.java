package com.spme.fantasolver.utility;

import com.spme.fantasolver.entity.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Utility {
    private static Properties properties;
    private static InputStream fileInputStream;

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

    public static String getFormattedRoles(Set<Role> roles) {
        StringBuilder rolesString = new StringBuilder();
        for (Role role: roles) {
            rolesString.append(role.name()).append(", ");
        }
        if (rolesString.length() > 0) {
            rolesString.setLength(rolesString.length() - 2);
        }
        return rolesString.toString();
    }
}
