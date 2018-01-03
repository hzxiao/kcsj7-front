package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.service.ArticleService;
import com.kcsj7.view.service.CommentArticleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
@Controller
@RequestMapping("/api/article/comment")
public class CommentArticleController {

    private static Logger log = Logger.getLogger(CommentArticleController.class);

    @Resource
    private CommentArticleService commentArticleService;
    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> add(@RequestHeader Map<String ,Object> header, @RequestBody Map<String,Object> request){
        request.put("token",header.get("token"));
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("articleId"))){
                return ResponseResult.createFailResult("评论失败,文章不能为空",null);
            }
            Map<String,Object> article = articleService.selectArticleById((Integer) request.get("articleId"));
            if (ObjectUtils.isEmpty(article)){
                return ResponseResult.createFailResult("评论失败,文章不存在",null);
            }
            commentArticleService.addCommentArticle(request);
            if (ObjectUtils.isEmpty(request.get("commentId"))){
                return ResponseResult.createFailResult("评论失败",null);
            }
            Integer commentId = (Integer)request.get("commentId");
            Map<String,Object> commentArticle = commentArticleService.selectCommentArticleById(commentId);
            if (ObjectUtils.isEmpty(commentArticle)){
                return ResponseResult.createFailResult("评论失败",null);
            }
            // 好评与差评次数
            if (!ObjectUtils.isEmpty(article.get("commentMark"))){
                if ("2".equals(article.get("commentMark"))){
                    article.put("goodCommentCount",((Integer)article.get("goodCommentCount"))+1);
                }else if ("3".equals(article.get("commentMark"))){
                    article.put("badCommentCount",((Integer)article.get("badCommentCount"))+1);
                }
            }
            article.put("commentCount",((Integer)article.get("commentCount"))+1);
            articleService.updateArticle(article);

            result.put("commentArticle",commentArticle);
            return ResponseResult.createSuccessResult("评论成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUserId(@PathVariable Integer commentId) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = commentArticleService.selectCommentArticleById(commentId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("评论不存在", null);
            }
            result.put("commentArticle", data);

            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> update(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("commentId"))){
                return ResponseResult.createNotFoundResult("评论不存在", null);
            }
            Map<String,Object> data = commentArticleService.selectCommentArticleById((Integer) request.get("commentId"));
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("评论不存在", null);
            }

            Integer commentId = commentArticleService.updateCommentArticle(request);
            Map<String,Object> commentArticle = commentArticleService.selectCommentArticleById((Integer) request.get("commentId"));
            if (ObjectUtils.isEmpty(commentArticle)){
                return ResponseResult.createFailResult("更新评论失败",null);
            }

            result.put("commentArticle",commentArticle);
            return ResponseResult.createSuccessResult("更新评论成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/listComments", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> listComments(@RequestBody Map<String,Object> request) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            if (!ObjectUtils.isEmpty(request.get("articleId"))){
                Map<String,Object> article = articleService.selectArticleById((Integer) request.get("articleId"));
                if (ObjectUtils.isEmpty(article)){
                    return ResponseResult.createFailResult("列出评论失败,文章不存在",null);
                }
            }
            List<Map<String,Object>> commentList = commentArticleService.listCommentArticles(request);
            result.put("commentList", commentList);
            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
