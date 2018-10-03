package com.wzl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by thinkpad on 2018/9/30.
 */

@Controller
public class HelloController {

    @GetMapping("/abc")
    public String abc(Model model) {
        model.addAttribute("msg", "fhoqifjqpiodjqw");
        return "success";
    }
}
