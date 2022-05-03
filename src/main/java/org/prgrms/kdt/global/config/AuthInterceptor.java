package org.prgrms.kdt.global.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = (String) request.getSession().getAttribute("userId");

        if(userId == null) {
            response.sendRedirect("/users");
            return false;
        } else {
            return true;
        }
    }
}
