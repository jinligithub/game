package edu.xpu.game.controller.auto;

import com.alibaba.fastjson.JSONObject;
import edu.xpu.game.entity.ProductCategory;
import edu.xpu.game.service.auto.CategoryService;
import edu.xpu.game.service.auto.CategoryServiceImpl;
import edu.xpu.game.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tim
 * @version 1.0
 * @className CategoryController
 * @description
 * @date 2019-05-31 12:49
 */
@Slf4j
@RestController
@RequestMapping("/category")
@ResponseBody
public class CategoryController {
    private final CategoryServiceImpl service;


    @Autowired
    public CategoryController(CategoryServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/click")
    public String getClick(@RequestParam(value = "category", required = false) Integer category){
        ResultVO<ProductCategory> ret = new ResultVO<>();
        ret.setCode(0);
        ret.setMsg("Success");
        ret.setData(service.hotTop());

        if(category != null){
            ProductCategory click = service.addCategoryClick(category);
            ret.setData(click);
        }
        return JSONObject.toJSONString(ret, true);
    }
}
