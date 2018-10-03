package com.wzl.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by thinkpad on 2018/10/1.
 */
public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {

    public HelloSpringApplicationRunListener(SpringApplication application, String[] args) {

    }

    //监听容器开始
    @Override
    public void starting() {
        //容器创建之前调用starting
        System.out.println("SpringApplicationRunListener...starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        //创建环境完成后调用environmentPrepared(),表示环境准备完成
        Object o = environment.getSystemProperties().get("os.name");
        System.out.println("SpringApplicationRunListener...environmentPrepared..." + o);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        //将environment保存到ioc，并且initializers()
        //initializers(): 回调之前保存的所有的ApplicationContextInitializer的initialize方法
        //回调contextPrepared()
        System.out.println("SpringApplicationRunListener...contextPrepared..." + context);

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        //preparedContext运行完成以后调用contextLoaded
        System.out.println("SpringApplicationRunListener...contextLoaded..." + context);

    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...started...");

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...running...");

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener...failed...");

    }
}
