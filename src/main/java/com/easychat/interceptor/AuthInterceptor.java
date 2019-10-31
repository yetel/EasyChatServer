package com.easychat.interceptor;

import com.easychat.bean.ResultCode;
import com.easychat.util.SessionUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zed
 * date: 2019/10/10.
 * description: 用于鉴权
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Value("${sys.test.value}")
    private boolean isTest;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if (isTest) {
            return true;
        }
        if (request.getRequestURI().contains("swagger")) {
            return true;
        }

        if (request.getRequestURI().contains("system")) {
            return true;
        }

        if (request.getRequestURI().contains("image")) {
            return true;
        }
        String token = request.getHeader("token");
        if (token == null || "".equals(token)) {
            throw ResultCode.NO_SESSION.build();
        }
        
        String userId = SessionUtil.getUserIdByToken(token);
        if (userId == null || "".equals(userId)) {
            throw ResultCode.INVALID_SESSION.build();
        }

        Channel channel = SessionUtil.getChannel(userId);
        if (channel == null) {
            throw ResultCode.EXP_SESSIOS.build();
        }

        request.setAttribute("token", userId);
        return true;
    }
}
