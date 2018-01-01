package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.ArticleDao;
import com.kcsj7.view.service.ArticleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService{
    private static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private ArticleDao articleDao;

    @Override
    public void addArticle(Map<String, Object> data) {
        articleDao.addArticle(data);
    }

    @Override
    public void updateArticle(Map<String, Object> data) {

    }

    @Override
    public Map<String, Object> selectArticleById(Integer articleId) {
        return articleDao.selectArticleById(articleId);
    }
}
