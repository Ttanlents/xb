package com.yjf.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 余俊锋
 * @date 2020/11/21 15:31
 * @Description
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //需要拦截的路径，通常都是多个，所以使用数组形式   只要session中有登录信息  立马就会放行
        String[] addPathPatterns = {
                "/**"
        };
        //不需要的拦截路径，同上
        String[] excludePathPatterns = {
                "/toIndex","/index.html","/doLogin","/static/**","/kaptcha/**","/doRegister","/doCheckUsername","/user/getCode","/user/changePassword","/js"
                ,"/css"  ,"/img"
        };
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);




    }
}
