package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.MessageBoardDao;
import com.kcsj7.view.filter.util.JwtUtil;
import com.kcsj7.view.service.MessageBoardService;
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
public class MessageBoardServiceImpl implements MessageBoardService{
    private static Logger log = Logger.getLogger(MessageBoardServiceImpl.class);

    @Resource
    private MessageBoardDao messageBoardDao;
    @Resource
    private UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public Integer addMessageBoard(Map<String, Object> data) {
        data = fillRequest(data);
        return messageBoardDao.addMessageBoard(data);
    }

    @Override
    public Integer updateMessageBoard(Map<String, Object> data) {
        return messageBoardDao.updateMessageBoard(data);
    }

    @Override
    public Map<String, Object> selectMessageBoardById(Integer data) {
        return messageBoardDao.selectMessageBoardById(data);
    }

    @Override
    public List<Map<String, Object>> listMessageBoards(Map<String, Object> data) {
        return messageBoardDao.listMessageBoards(data);
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
