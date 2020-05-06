package top.uaian.resources.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {


    @RequestMapping("/get")
    public String get(){
        return "xukainan";
    }
}
