package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.CommentArticleDao;
import com.kcsj7.view.filter.util.JwtUtil;
import com.kcsj7.view.service.CommentArticleService;
import com.kcsj7.view.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
@Service
public class CommentArticleServiceImpl implements CommentArticleService{
    private static Logger log = Logger.getLogger(CommentArticleServiceImpl.class);

    @Resource
    private CommentArticleDao commentArticleDao;
    @Resource
    private UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public Integer addCommentArticle(Map<String, Object> data) {
        data = fillRequest(data);
        Map<String,Object> articleId = new HashMap<>();
        //评论次序
        articleId.put("articleId",data);
        articleId.put("status",1);
        List<Map<String,Object>> commentList = commentArticleDao.listCommentArticles(articleId);
        if (commentList!=null){
            data.put("commentOrder",commentList.size()+1);
        }

        return commentArticleDao.addCommentArticle(data);
    }

    @Override
    public Integer updateCommentArticle(Map<String, Object> data) {
        return commentArticleDao.updateCommentArticle(data);
    }

    @Override
    public Map<String, Object> selectCommentArticleById(Integer articleId) {
        return commentArticleDao.selectCommentArticleById(articleId);
    }


    @Override
    public List<Map<String, Object>> listCommentArticles(Map<String, Object> data) {
        return commentArticleDao.listCommentArticles(data);
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
