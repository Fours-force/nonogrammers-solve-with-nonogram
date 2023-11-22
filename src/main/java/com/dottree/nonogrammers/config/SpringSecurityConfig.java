package com.dottree.nonogrammers.config;


import com.dottree.nonogrammers.config.jwt.JwtAuthenticationFilter;
import com.dottree.nonogrammers.config.jwt.JwtAuthorizationFilter;
import com.dottree.nonogrammers.config.jwt.JwtProperties;
import com.dottree.nonogrammers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private UserRepository ur;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtProperties jwtProperties;


    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/images/**");
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/images/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthorizationFilter())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auth/join", "/api/v1/auth/login", "/api/v1/auth/reset-password",
                                "api/v1/auth/check-nickname", "api/v1/auth/check-baekjoon",
                                "/api/v1/auth/email-verification", "/api/v1/auth/verify-code",
                                "/post", "/post/free", "/post/qa", "/post/nono", "/post/notice",
                                "/search", "/detail").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), jwtProperties);
        return jwtAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManagerBean(), ur, jwtProperties);
    }
}



