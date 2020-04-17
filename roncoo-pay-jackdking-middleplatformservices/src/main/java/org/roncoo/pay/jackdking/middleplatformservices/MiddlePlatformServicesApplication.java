package org.roncoo.pay.jackdking.middleplatformservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class MiddlePlatformServicesApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MiddlePlatformServicesApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MiddlePlatformServicesApplication.class, args);
    }

}
