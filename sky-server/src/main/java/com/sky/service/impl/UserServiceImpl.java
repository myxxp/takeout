package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {



        //获取openID


        String openId = getOpenId(userLoginDTO.getCode());

        //判断openID是否存在，如果存在则返回用户信息，如果不存在抛出异常
        if (openId == null) {
            log.error("用户不存在");
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //如果用户不存在，则创建用户信息
        User user = userMapper.getByOpenId(openId);
        if(user == null){
           user =  User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        //返回用户信息
        return user;

    }

    private String getOpenId(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);
        JSONObject jsonObject = JSONObject.parseObject(json);
        return jsonObject.getString("openid");
    }
}
