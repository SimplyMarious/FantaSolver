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
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }
}
