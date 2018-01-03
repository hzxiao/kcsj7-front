package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.service.TopicService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
@Controller
@RequestMapping("")
public class TopicController {
    private static Logger log = Logger.getLogger(TopicController.class);

    @Resource
    TopicService topicService;


    @RequestMapping(value = "/api/topic/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> add(HttpServletRequest httpServletRequest, @RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (!ObjectUtils.isEmpty(httpServletRequest.getParameter("token"))){
                request.put("token",httpServletRequest.getParameter("token"));
            }
            if (ObjectUtils.isEmpty(request.get("title"))){
                return ResponseResult.createFailResult("发表失败,标题不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("content"))){
                return ResponseResult.createFailResult("发表失败,内容不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("category"))){
                return ResponseResult.createFailResult("发表失败,分类不能为空",null);
            }
            
            Integer topicId = topicService.addTopic(request);
            Map<String,Object> topic = topicService.selectTopicById((Integer) request.get("topicId"));
            if (ObjectUtils.isEmpty(topic)){
                return ResponseResult.createFailResult("发表失败",null);
            }
            result.put("topic",topic);
            return ResponseResult.createSuccessResult("发表成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/topic/{topicId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUserId(@PathVariable Integer topicId) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = topicService.selectTopicById(topicId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("话题不存在", null);
            }
            result.put("topic", data);

            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/api/topic/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> update(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("topicId"))){
                return ResponseResult.createNotFoundResult("话题不存在", null);
            }
            Map<String,Object> data = topicService.selectTopicById((Integer) request.get("topicId"));
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("话题不存在", null);
            }
            Integer topicId = topicService.updateTopic(request);
            Map<String,Object> topic = topicService.selectTopicById((Integer) request.get("topicId"));
            if (ObjectUtils.isEmpty(topic)){
                return ResponseResult.createFailResult("更新话题失败",null);
            }
            result.put("topic",topic);
            return ResponseResult.createSuccessResult("更新话题成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/topic/listTopics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> listTopics(@RequestBody Map<String,Object> request) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<Map<String,Object>> topicList = topicService.listTopics(request);
            result.put("topicList", topicList);
            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
