package com.dottree.nonogrammers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    String currentDirectory = System.getProperty("user.dir");
    // String location="file:///c:/kosastudy/springedu/src/main/resources/static/images/";
//    String correctedPath = currentDirectory.re"file:~/Downloads/nonogrammers/src/main/resources/static/images/1/";place("\\", "/");

//    String location = "file:/Users/COM/Desktop/project/nonogrammers-solve-with-nonogram/src/main/resources/static/images/";
    String location = "file:/Users/jasonmilian/Downloads/nonogrammers/src/main/resources/static/images/";

//"file:~/Downloads/nonogrammers-solve-with-nonogram/src/main/resources/static/images/1/";
//    " + correctedPath + "/uni-pet/src/main/resources/static/img/mypage/upload/
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        System.out.println("=================== : " +location);
        registry.addResourceHandler("/images/**").addResourceLocations(location);
    }
}

//package com.dottree.nonogrammers;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MyConfig implements WebMvcConfigurer {
//    //@Value("${app.file.storage.mapping}")
//    String currentDirectory = System.getProperty("user.dir");
//    // String location="file:///c:/kosastudy/springedu/src/main/resources/static/images/";
////    String correctedPath = currentDirectory.replace("\\", "/");
//    String location = "file:////nonogrammers-solve-with-nonogram/src/main/resources/static/images/profile/";
////    profile/userId/
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        System.out.println("=================== : " +location);
//        registry.addResourceHandler("/images/profile/**").addResourceLocations(location);
//    }
//}