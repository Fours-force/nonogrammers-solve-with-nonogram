package com.dottree.nonogrammers;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // 요청 url을 가져온다.
        String uri = request.getRequestURI(); // 요청 들어온 것에 대한 path를 가져올 수 있음
        logger.info("[##### preHandler - get uri] uri : {}", uri);

        // 로그인 여부 확인 - 세션확인
        HttpSession session = request.getSession();
        Integer userId = (Integer)session.getAttribute("userId"); // 비로그인일 수 있음

        // 접근못하게 막고 싶은 url 설정 - myPage
        if (userId == null && (uri.startsWith("/user") || uri.startsWith("/ingnono") || uri.startsWith("/solvednono"))) {
            response.sendRedirect("/");

            return false; // 컨트롤러 수행안함
        }

        // 로그인 이면서 user로 시작하는 주소로 접속한 경우 => 메인페이지로 redirect, return false를 하여 기존 controller 수행 방지
//        if (userId != null && uri.startsWith("/user")) {
//            if (uri.equals("/user/sign_out")) {
//                return true;
//            }
//
//            response.sendRedirect("/main");
//
//            return false; // 컨트롤러 수행안함
//        }

        return true; // 컨트롤러 수행
    }

    // view 객체가 있다는 것은 아직 jsp가 HTML로 변환되기 전 단계이다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
        String uri = request.getRequestURI(); // 요청 들어온 것에 대한 path를 가져올 수 있음
        logger.info("[$$$$$ postHandle - get uri] uri : {}", uri);
    }

    // jsp가 HTML로 최종 변환된 후 (api가 끝난 후)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("[@@@@@ afterCompletion]");
    }
}
