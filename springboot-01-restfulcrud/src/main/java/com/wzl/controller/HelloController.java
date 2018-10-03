package com.wzl.controller;

import com.wzl.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by thinkpad on 2018/9/19.
 */

@Controller
public class HelloController {

//    @RequestMapping({"/","/index.html"})
//    public String index() {
//        return "login";
//    }

    @ResponseBody
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam("user") String user) {
        if (user.equals("aaa")) {
            throw new UserNotExistException();
        }
        return "Hello World";
    }


    //查出一些数据，在页面显示
    @RequestMapping("/success")
    public String succcess(Map<String, Object> map) {
        map.put("hello", "<h1>qqqqqqqqqqqq</h1>");
        map.put("users", Arrays.asList("zhangsan", "lisi", "wangwu"));
        //classpath: /templates/success.html
        return "success";
    }
}
