package com.wzl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by thinkpad on 2018/10/3.
 */

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/*").hasRole("VIP1")
                .antMatchers("/level2/*").hasRole("VIP2")
                .antMatchers("/level3/*").hasRole("VIP3");

        //开启自动配置的登录功能，效果：没有权限就来到登录页面
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");
        //1. /login来到登录页面
        //2. 重定向到/login?error表示登录失败
        //3. 更多详细规定
        //4, 默认post形式的/login代表处理登录
        //5. 一旦定制loginpage, nameloginpage的post请求就是登录






        //开启自动配置的注销
//        http.logout();
        // 1. 访问 /logout 表示用户注销，清空sesion
        // 2. 注销成功会返回 /login?logout 页面
        http.logout().logoutSuccessUrl("/"); //注销成功来到首页

        //开启记住我功能
        //cookie 14days
        http.rememberMe().rememberMeParameter("remember");
        //登录成功后，将cookie发送给浏览器，以后访问页面带上cookie，只要通过检查就可以免登录
        //点击注销会删除cookie


    }

    //定义认证规则
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("password")).roles("VIP1", "VIP2", "VIP3")
                .and()
                .withUser("lisi").password(passwordEncoder().encode("123456")).roles("VIP1")
                .and()
                .withUser("wangwu").password(passwordEncoder().encode("123456")).roles("VIP2", "VIP3");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
