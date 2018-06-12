package com.redisson.configs;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.io.FileNotFoundException;

public final class Log4JLogger{

    public static void init(String configFileName) throws FileNotFoundException {
        System.out.println(String.format("init method. Params: configFileName=%s.", configFileName));

        if (!new File(configFileName).exists()) {
            System.out.println("No logger properties");
            throw new FileNotFoundException("no logger properties were found.");
        }

        try {
            if(configFileName == null) {
                System.out.println("init method. WARNING: Config file is null. Will apply basic configuration");
                BasicConfigurator.configure();
            }
            else if(configFileName.endsWith(".xml")) {
                DOMConfigurator.configure(configFileName);
            }
            else if(configFileName.endsWith(".properties")) {
                PropertyConfigurator.configure(configFileName);
            }
            else {
                System.out.println(
                        String.format("init method. WARNING: Unknown type of the config file %s. Will apply basic configuration", configFileName));
                BasicConfigurator.configure();
            }
        } catch (Exception e) {
            System.out.println("LOGGER not initialized");
        }

    }
}
