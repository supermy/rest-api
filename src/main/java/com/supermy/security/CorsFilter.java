package com.supermy.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by moyong on 16/3/17.
 */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter {//implements Filter {
//
//    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
//        HttpServletResponse response = (HttpServletResponse) res
//        response.setHeader("Access-Control-Allow-Origin", "*")
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with")
//        response.setHeader("Access-Control-Max-Age", "3600")
//        if (request.getMethod()!="OPTIONS") {
//            chain.doFilter(req, res)
//        } else {
//        }
//    }
//
//    void init(FilterConfig filterConfig) {}
//
//    void destroy() {}

}