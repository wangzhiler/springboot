package com.wzl.config;

import com.wzl.filter.MyFilter;
import com.wzl.listener.MyListener;
import com.wzl.servlet.MyServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by thinkpad on 2018/9/30.
 */

@Configuration
public class MyServerConfig {

    //注册三大组件 Servlet, Filter, Listener
    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean registrationBean
                = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello", "/myServlet"));
        return registrationBean;
    }



    @Bean
    public ServletListenerRegistrationBean myListener() {
        ServletListenerRegistrationBean<MyListener> registrationBean
                = new ServletListenerRegistrationBean<MyListener>(new MyListener());
        return registrationBean;
    }



    //配置嵌入式的Servelt容器
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            //定制嵌入式的Servlet容器相关规则
            @Override
            public void customize(ConfigurableWebServerFactory configurableWebServerFactory) {
                configurableWebServerFactory.setPort(8080);

            }
        };
    }


}
