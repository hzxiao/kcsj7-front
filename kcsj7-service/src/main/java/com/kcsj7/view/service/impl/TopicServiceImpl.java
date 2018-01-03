package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.TopicDao;
import com.kcsj7.view.filter.util.JwtUtil;
import com.kcsj7.view.service.TopicService;
import com.kcsj7.view.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
@Service
public class TopicServiceImpl implements TopicService {
    private static Logger log = Logger.getLogger(TopicServiceImpl.class);

    @Resource
    private TopicDao topicDao;

    @Autowired
    private JwtUtil jwtUtil;
    @Resource
    private UserService userService;

    @Override
    public Integer addTopic(Map<String, Object> data) {
        data = fillRequest(data);
        return topicDao.addTopic(data);
    }

    @Override
    public Integer updateTopic(Map<String, Object> data) {
        return topicDao.updateTopic(data);
    }

    @Override
    public Map<String, Object> selectTopicById(Integer topicId) {
        return topicDao.selectTopicById(topicId);
    }

    @Override
    public List<Map<String, Object>> listTopics(Map<String, Object> data) {
        return topicDao.listTopics(data);
    }

    public Map<String,Object> fillRequest(Map<String,Object> data){
        if (!ObjectUtils.isEmpty(data.get("token"))){
            String username =jwtUtil.getUsernameFromToken(data.get("token").toString());
            Map<String,Object> user = userService.getUserByUsername(username);
            data.put("createBy",user.get("userId"));
            data.put("createName",user.get("username"));
            if (!ObjectUtils.isEmpty(user.get("author"))){
                data.put("createName",user.get("author"));
            }
        }
        return data;
    }
}
