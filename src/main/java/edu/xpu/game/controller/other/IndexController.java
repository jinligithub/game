package edu.xpu.game.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tim
 * @version 1.0
 * @className IndexController
 * @description 静态资源控制器
 * @date 2019-06-05 12:12
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}