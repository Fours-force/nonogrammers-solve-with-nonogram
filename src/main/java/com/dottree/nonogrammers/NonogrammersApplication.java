package com.dottree.nonogrammers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication//(exclude= DataSourceAutoConfiguration.class)
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.dottree.nonogrammers", "templates"})
public class NonogrammersApplication {
		public static void main(String[] args) {
			SpringApplication.run(NonogrammersApplication.class, args);
	}

}
