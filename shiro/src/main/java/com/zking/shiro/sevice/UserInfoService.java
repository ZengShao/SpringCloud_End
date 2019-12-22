package com.zking.shiro.sevice;

import com.zking.shiro.model.UserInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);

//    int doRegister(UserInfo user);//用户注册
}