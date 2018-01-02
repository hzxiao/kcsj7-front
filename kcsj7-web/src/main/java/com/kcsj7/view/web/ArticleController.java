package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.service.ArticleService;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private static Logger log = Logger.getLogger(UserController.class);

    @Resource
    ArticleService articleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> add(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("title"))){
                return ResponseResult.createFailResult("发表失败,标题不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("content"))){
                return ResponseResult.createFailResult("发表失败,内容不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("tag"))){
                return ResponseResult.createFailResult("发表失败,标签不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("category"))){
                return ResponseResult.createFailResult("发表失败,分类不能为空",null);
            }

            articleService.addArticle(request);
            return ResponseResult.createSuccessResult("发表成功",null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

//    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult<Map<String, Object>> getUserByAUsername(@PathVariable Integer userId) {
//        Map<String,Object> result = new HashMap<String,Object>();
//        try {
//            Map<String,Object> data = userService.getUserByUsername(username);
//            if (ObjectUtils.isEmpty(data)) {
//                return ResponseResult.createNotFoundResult("用户不存在", null);
//            }
//            result.put("user", data);
//
//            return ResponseResult.createSuccessResult("success", result);
//        }catch (Exception e){
//            log.error(e.getMessage(), e);
//            return ResponseResult.createFailResult(e.getMessage(),null);
//        }
//    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUserId(@PathVariable Integer articleId) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = articleService.selectArticleById(articleId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("文章不存在", null);
            }
            result.put("article", data);

            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
