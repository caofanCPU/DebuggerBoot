package com.xyz.caofancpu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * FileName: HtmlPageController
 *
 * @author: caofanCPU
 * @date: 2019/2/15 12:28
 */
@Controller
public class HtmlPageController {
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
