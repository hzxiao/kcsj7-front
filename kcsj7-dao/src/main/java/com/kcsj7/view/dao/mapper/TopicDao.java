package com.kcsj7.view.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
public interface TopicDao {
    Integer addTopic(Map<String, Object> data);
    Integer updateTopic(Map<String, Object> data);
    Map<String, Object> selectTopicById(Integer topicId);
    List<Map<String, Object>> listTopics(Map<String, Object> data);
}
