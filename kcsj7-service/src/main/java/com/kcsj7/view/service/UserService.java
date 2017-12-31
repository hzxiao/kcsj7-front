package com.kcsj7.view.service;

import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
public interface UserService {
    Map<String,Object> verifyUser(Map<String,Object> data);
    void addUser(Map<String,Object> data);
    Map<String, Object> getUserByUsername(String username);
    Map<String, Object> getUserByUid(Integer userId);
}
