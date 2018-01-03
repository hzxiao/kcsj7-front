package com.kcsj7.view.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
public interface MessageBoardDao {
    Integer addMessageBoard(Map<String, Object> data);
    Integer updateMessageBoard(Map<String, Object> data);
    Map<String, Object> selectMessageBoardById(Integer articleId);
    List<Map<String, Object>> listMessageBoards(Map<String, Object> data);
}
