package top.uaian.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/loginverify")
@Controller //返回非JSON内容，这里不能用RestController
public class LoginVerifyController {

    @RequestMapping("/password")
    public String verifyUsername(@RequestParam("username") String username){
        if("xukainan".equals(username)) {
            System.out.println("该用户不存在！");
            return "login";
        }
        return "index";
    }
}
