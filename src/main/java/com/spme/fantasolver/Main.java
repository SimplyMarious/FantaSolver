package com.spme.fantasolver;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.utility.Utility;
import java.util.Properties;

public class Main {

    @Generated
    public static void main(String[] args){
        setUpDependencies();
        FantaSolver.main(args);

    }

    @Generated
    private static void setUpDependencies() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }
}
