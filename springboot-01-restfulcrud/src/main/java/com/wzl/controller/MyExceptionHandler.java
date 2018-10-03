package com.wzl.controller;

import com.wzl.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2018/9/30.
 */

@ControllerAdvice
public class MyExceptionHandler {

    // 定制错误的json数据
    // 1) 自定义异常处理&返回定制json数据
    // 缺点：没有自适应效果，浏览器客户端返回的都是json
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String, Object> handleException(Exception e) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", "user.notexist");
//        map.put("message", e.getMessage());
//        return map;
//    }


    //2) 转发到/error进行自适应响应效果处理
//    @ExceptionHandler(UserNotExistException.class)
//    public String handleException(Exception e, HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//
//        //要传入原来的错误码，不然转发过去变成200了
//        //否则就不会进入定制错误页面的解析流程
///*
//        Integer statusCode=(Integer) request
//                .getAttribute("javax.servlet.error.statsu_code");
//*/
//        request.setAttribute("javax.servlet.error.status_code", 500);
//        map.put("code", "user.notexist");
//        map.put("message", e.getMessage());
//        //转发到/error
//        return "forward:/error";
//    }

    //3） 将我们的定制消息携带出去
    //出现错误以后，回来到/error请求，会被BasicErrorController处理，
    // 响应出去可以获取的数据是由getErrorAttributes得到的（AbstractErrorController(ErrorController)规定的方法）
    // 两种方法：1. 编写ErrorController实现类/AbstractErrorController子类，放在容器中
    //          2. 页面上能用的数据，或是json返回能用的数据都是通过errorAttributes.getErrorAttributes得到
    //          容器中DefaultErrorAttributes默认进行数据处理的

    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notexist");
        map.put("message", e.getMessage());
        request.setAttribute("ext", map);
        //转发到/error
        return "forward:/error";
    }

    //最终效果：响应式自适应的，可以通过定制ErrorAttributes改变需要返回的内容



}
