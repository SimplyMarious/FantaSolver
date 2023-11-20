package com.spme.fantasolver;

import com.spme.fantasolver.utility.Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Main {
    public static void main(String[] args){
        setUpDependencies();
        Application.main(args);

    }

    private static void setUpDependencies() {
        try {
            Utility.setPropertiesReadingTools(new Properties(), new FileInputStream("src/main/resources/config.properties"));
        } catch (FileNotFoundException e) {
            System.err.println("Properties file not found.");
            System.exit(1);
        }
    }
}
