package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.UserDao;
import com.kcsj7.view.filter.util.JwtUtil;
import com.kcsj7.view.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);


    @Resource
    private UserDao userDao;
    @Override
    public Map<String, Object> verifyUser(Map<String, Object> data) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> user = userDao.verifyUser(data);
        if (ObjectUtils.isEmpty(user)){
            return null;
        }
        Map<String,Object> updateParam = new HashMap<>();
        updateParam.put("userId",user.get("userId"));
        updateParam.put("bisStatus",user.get("bisStatus"));
        //改变用户状态
        userDao.updateUser(updateParam);
        //获取token
        String token = JwtUtil.generToken(user.get("userId").toString(),null,user.get("username").toString());
        user.put("token",token);
        result.put("user",user);
        return result;
    }

    @Override
    public Map<String,Object> addUser(Map<String, Object> data) {
        Map<String,Object> result = new HashMap<>();
        data.put("author",data.get("username"));
        userDao.addUser(data);
        Map<String,Object> user = getUserByUsername(data.get("username").toString());
        if (user==null){
           return null;
        }
        String token = JwtUtil.generToken(data.get("userId").toString(),null,data.get("username").toString());
        user.put("token",token);
        result.put("user",user);
        return result;
    }

    @Override
    public Map<String, Object> getUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    @Override
    public Map<String, Object> getUserByUid(Integer userId) {
        return userDao.selectUserByUid(userId);
    }
}
