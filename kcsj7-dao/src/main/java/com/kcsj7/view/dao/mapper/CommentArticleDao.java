package com.kcsj7.view.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
public interface CommentArticleDao {
    Integer addCommentArticle(Map<String, Object> data);
    Integer updateCommentArticle(Map<String, Object> data);
    Map<String, Object> selectCommentArticleById(Integer commentId);
    List<Map<String, Object>> listCommentArticles(Map<String, Object> data);
}
