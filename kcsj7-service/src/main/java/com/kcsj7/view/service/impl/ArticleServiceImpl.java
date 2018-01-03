package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.ArticleDao;
import com.kcsj7.view.filter.util.JwtUtil;
import com.kcsj7.view.service.ArticleService;
import com.kcsj7.view.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService{
    private static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private ArticleDao articleDao;

    @Autowired
    private JwtUtil jwtUtil;
    @Resource
    private UserService userService;

    @Override
    public void addArticle(Map<String, Object> data) {
        data = fillRequest(data);
        articleDao.addArticle(data);
    }

    @Override
    public void updateArticle(Map<String, Object> data) {
        articleDao.updateArticle(data);
    }

    @Override
    public Map<String, Object> selectArticleById(Integer articleId) {
        return articleDao.selectArticleById(articleId);
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


    @Override
    public Map<String, Object> selectArticleByTitle(String title) {
        return articleDao.selectArticleByTitle(title);
    }

    @Override
    public List<Map<String, Object>> listArticles(Map<String, Object> data) {
        return articleDao.listArticles(data);
    }
}
