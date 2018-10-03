package com.wzl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by thinkpad on 2018/9/17.
 */

//@ResponseBody
//@Controller
@RestController
public class HelloController {

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello world quick!!!";
    }
}
