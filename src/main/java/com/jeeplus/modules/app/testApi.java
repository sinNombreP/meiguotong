package com.jeeplus.modules.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author 彭善智
 * @Title: testApi
 * @ProjectName meiguotong
 * @Description: TODO
 * @date 2019/6/412:53
 */
@Controller
public class testApi {

    @RequestMapping("hello")
    @ResponseBody
    public  Date hello(){
        return  new Date();
    }

}
