package com.kcsj7.view.service.impl;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.dao.mapper.UserDao;
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
        result.put("user",userDao.verifyUser(data));
        return result;

    }
}
