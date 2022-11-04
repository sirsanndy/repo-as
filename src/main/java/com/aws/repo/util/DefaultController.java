package com.aws.repo.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class DefaultController {
    protected String clientIp;

    protected void getClientIp(HttpServletRequest request){
        this.clientIp = Objects.nonNull(request.getHeader("X-FORWARDED-FOR"))? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }
}
