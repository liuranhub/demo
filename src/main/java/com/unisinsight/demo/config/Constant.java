package com.unisinsight.demo.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class Constant {
    public static Integer INIT_THREADS = 8;

    static {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(
                    new ClassPathResource("application.properties"));
            String maxSize = properties.getProperty("initThreads");
            if(maxSize != null) {
                INIT_THREADS = Integer.parseInt(maxSize);
            }
            System.out.println("INIT_THREADS:" + INIT_THREADS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
