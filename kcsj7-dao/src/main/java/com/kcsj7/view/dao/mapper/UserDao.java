package com.kcsj7.view.dao.mapper;

import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
public interface UserDao {
    Map<String,Object> verifyUser(Map<String,Object> data);
    void updateUser(Map<String,Object> data);
}
