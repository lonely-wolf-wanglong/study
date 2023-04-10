package com.example.demo.controller;

import com.example.demo.annotation.SysLog;
import com.example.demo.entity.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @RequestMapping("/hello")
    @SysLog(operModule = "系统管理", operMenu = "生产日志填报", operName = "更新", operDesc = "更新生产日志填报")
    public R HelloWorld(){
        return R.ok("Hello World");
    }
}
