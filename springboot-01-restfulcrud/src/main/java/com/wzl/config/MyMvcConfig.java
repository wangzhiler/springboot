package com.wzl.config;

import com.wzl.component.MyLocaleResolver;
import com.wzl.controller.LoginHandlerInterceptor;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by thinkpad on 2018/9/19.
 */

//使用WebMvcConfigurer扩展SpringMVC的功能
//@EnableWebMVC 不要接管SpringMVC

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        //浏览器发送 /wzl请求来到
//        registry.addViewController("/aa").setViewName("success");
//    }




    //所有的WebMvcConfigured组件会一起起作用
    @Bean //将组件注册在容器
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer configurer = new WebMvcConfigurer() {

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //Springboot 已经做好了静态资源映射
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                        .excludePathPatterns("/index.html", "/", "/user/login", "/webjars/**", "/static/**"
//                                , "/asserts/**");
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }
        };


        return configurer;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
