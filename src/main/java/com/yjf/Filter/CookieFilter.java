package com.yjf.Filter;


import com.yjf.entity.User;
import com.yjf.utils.JsonUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author 余俊锋
 * @date 2020/9/23 12:36
 * @Description
 */
@WebFilter("/*")
public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
 *@Description 如果检测到访问的用户未登录，判断是否有cookie,使用cookie登录
 *@author 余俊锋
 *@date 2020/9/23 13:34
 *@params servletRequest
 * @param servletResponse
 * @param filterChain
 *@return void
 */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url=request.getRequestURI();
        if (url.endsWith("/xb/toIndex")||url.endsWith("/index.html")||
                url.endsWith("/doLogin")||url.contains("/kaptcha")
                ||url.endsWith("/doRegister")||url.endsWith("/doCheckUsername")||url.contains("/user/getCode")
                ||url.contains("/changePassword")||url.contains("/forget.html")||url.contains("register.html")||url.contains("js")||url.contains("css")
                ||url.contains("img")||url.contains("vendor")||url.contains("fonts")||url.contains("bootstrap")||url.contains("upload")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if (request.getSession().getAttribute("loginUser")==null){

            Cookie[] cookies = request.getCookies();
            if (cookies!=null&&cookies.length>0){
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    if ("cookie_login".equals(name)){
                        String value = cookie.getValue();
                        try {
                            User user= JsonUtils.jsonToPojo(URLDecoder.decode(value,"UTF-8"), User.class);
                            System.out.println(user);
                            request.getSession().setAttribute("loginUser",user);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

       filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
