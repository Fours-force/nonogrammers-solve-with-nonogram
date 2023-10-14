package com.dottree.nonogrammers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//(exclude= DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.dottree.nonogrammers", "templates"})
@MapperScan(value={"com.dottree.nonogrammers.dao"})
public class NonogrammersApplication {
	public static void main(String[] args) {
		SpringApplication.run(NonogrammersApplication.class, args);
	}

}
