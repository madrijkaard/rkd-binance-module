package com.rkd.binance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class of the module.
 */
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {

    /**
     * Method responsible for starting the application.
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }
}
