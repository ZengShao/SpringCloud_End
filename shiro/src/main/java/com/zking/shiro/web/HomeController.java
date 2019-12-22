package com.zking.shiro.web;

import com.zking.shiro.model.LoginResult;
import com.zking.shiro.sevice.LoginService;
import com.zking.shiro.util.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    LoginService loginService;

    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

//    @RequestMapping(value = "/login",method = RequestMethod.GET)
//    public String toLogin(Map<String, Object> map,HttpServletRequest request)
//    {
//        loginService.logout();
//        return "/login";
//    }

    @GetMapping(value = "/login")
    public JsonData login(String  userName,String password) throws Exception{
        System.out.println("login()");
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("password");

        JsonData jsonData = new JsonData();

        System.out.println(userName);
        System.out.println(password);
        JsonData jsonData1 = loginService.login(userName,password);
//        if(loginResult.isLogin())
//        {
//            jsonData.setCode(0);
//            jsonData.setMessage("登陆成功");
            jsonData.setResult(jsonData1);
            return jsonData1;
//        }
//        else {
//            jsonData.setCode(-1);
//            jsonData.setMessage("登陆失败");
//            map.put("msg",loginResult.getResult());
//            map.put("userName",userName);
//            return jsonData;
//        }
    }

    @RequestMapping(value = "/loginaa",method = RequestMethod.POST)
    public JsonData loginaa(@RequestParam String username, @RequestParam String password) throws Exception {
        System.out.println("HomeController.loginaa()");
        SecurityUtils.getSubject().logout();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Map<String, Object> map = new HashMap<>();
        JsonData jsonData = new JsonData();
        try {
            subject.login(token);
            String sessionid = (String) subject.getSession().getId();
            System.out.println("sessionid======"+sessionid);
            jsonData.setCode(0);
            jsonData.setMessage("登陆成功");
            jsonData.setResult(map);
            map.put("msg", sessionid);
            return jsonData;

        } catch (AuthenticationException e) {
          String msg= "用户名或者密码不正确";
            map.put("msg", msg);
            // 此方法不处理登录成功,由shiro进行处理
            jsonData.setCode(-1);
            jsonData.setMessage("登陆失败");
            return jsonData;
        }
    }

    //被踢出后跳转的页面
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickOut() {
        return "/kickOut2";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }

}