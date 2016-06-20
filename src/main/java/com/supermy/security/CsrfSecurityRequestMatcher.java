package com.supermy.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
/**
 * Created by moyong on 15/6/22.
 * 原因在于：启用csrf后，所有http请求都被会CsrfFilter拦截，而CsrfFilter中有一个私有类DefaultRequiresCsrfMatcher
 *  2         private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
 *
 * POST方法被排除在外了，也就是说只有GET|HEAD|TRACE|OPTIONS这4类方法会被放行，其它Method的http请求，都要验证_csrf的token是否正确，而通常post方式调用rest服务时，又没有_csrf的token，所以校验失败。
 *
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {
    public CsrfSecurityRequestMatcher() {
        String[] words = {"api", "rest"};

        List<String> wordList = Arrays.asList(words);
        this.execludeUrls = wordList;
    }

    private Pattern allowedMethods = Pattern
            .compile("^(GET|HEAD|TRACE|OPTIONS)$");

    public boolean matches(HttpServletRequest request) {

        if (execludeUrls != null && execludeUrls.size() > 0) {
            String servletPath = request.getServletPath();
            for (String url : execludeUrls) {
                if (servletPath.contains(url)) {
                    return false;
                }
            }
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    /**
     * 需要排除的url列表
     */
    private List<String> execludeUrls;

    public List<String> getExecludeUrls() {
        return execludeUrls;
    }

    public void setExecludeUrls(List<String> execludeUrls) {
        this.execludeUrls = execludeUrls;
    }
}