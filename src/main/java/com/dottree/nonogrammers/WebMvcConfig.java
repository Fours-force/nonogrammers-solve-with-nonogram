package com.dottree.nonogrammers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration // 설정을 위한 어노테이션 spring bean
public class WebMvcConfig implements WebMvcConfigurer{

    @Autowired
    private PermissionInterceptor interceptor;

    // 서버에 업로드 된 이미지와 웹 이미지 주소와의 매핑 설정
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/images/**") // 그 뒤에 무언가가 있다 ** 웹이미지 주소
//                .addResourceLocations("file://" + FileManagerService.FILE_UPLOAD_PATH); // mac은 // 두개임 윈도우는 /// 3개
//    }

    // 인터셉터를 설정에 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(interceptor)
                .addPathPatterns("/**")  // -> /밑의 모든 것들 확인
                .excludePathPatterns("/favicon.ico", "/error", "/static/**", "/logout");
    }
}
