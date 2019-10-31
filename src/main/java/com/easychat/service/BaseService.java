package com.easychat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Zed
 * date: 2019/10/10.
 * description:
 */
public class BaseService {
    @Value("${sys.test.value}")
    private boolean isTest;
    
    protected String getUserId() {
        if (isTest) {
            return "5db6be2dgc";
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return (String) requestAttributes.getRequest().getAttribute("token");
        }
        return null;
    }
}
