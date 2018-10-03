package com.wzl.controller;

import com.wzl.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by thinkpad on 2018/10/1.
 */

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;


    @GetMapping("/hello")
    public String hello() {
        return helloService.sayHello("hahahatestetstetset");
    }
}
