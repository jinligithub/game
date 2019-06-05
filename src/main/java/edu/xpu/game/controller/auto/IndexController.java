package edu.xpu.game.controller.auto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tim
 * @version 1.0
 * @className IndexController
 * @description
 * @date 2019-06-05 12:12
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("aaa.html")
    public String index(){
        return "index";
    }
}
