package com.dottree.nonogrammers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    String currentDirectory = System.getProperty("user.dir");
    String location = "file:" + currentDirectory + "/src/main/resources/static/images/";
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        System.out.println("=================== : " +location);
        registry.addResourceHandler("/images/**").addResourceLocations(location);
    }
}