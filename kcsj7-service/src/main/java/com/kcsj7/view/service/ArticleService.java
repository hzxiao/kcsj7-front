package com.kcsj7.view.service;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    void addArticle(Map<String, Object> data);
    void updateArticle(Map<String, Object> data);
    Map<String, Object> selectArticleById(Integer articleId);
    Map<String, Object> selectArticleByTitle(String title);
    List<Map<String, Object>> listArticles(Map<String, Object> data);


}
