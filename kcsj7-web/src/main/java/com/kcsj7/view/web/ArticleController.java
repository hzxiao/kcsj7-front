package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.service.ArticleService;
import com.kcsj7.view.service.ProgramaService;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    private static Logger log = Logger.getLogger(ArticleController.class);

    @Resource
    ArticleService articleService;

    @Resource
    ProgramaService programaService;

    @RequestMapping(value = "/api/article/add", method = RequestMethod.POST)
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
            if (ObjectUtils.isEmpty(request.get("tag"))){
                return ResponseResult.createFailResult("发表失败,标签不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("category"))){
                return ResponseResult.createFailResult("发表失败,分类不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("programaName"))){
                return ResponseResult.createFailResult("发表失败,栏目不能为空",null);
            }
            Map<String,Object> programa = programaService.selectProgramaByName(request.get("programaName").toString());
            if (ObjectUtils.isEmpty(programa)){
                return ResponseResult.createFailResult("发表失败,栏目不存在",null);
            }
            if (!ObjectUtils.isEmpty(programa.get("programaId"))){
                request.put("programaId",programa.get("programaId"));
            }
            articleService.addArticle(request);
            Map<String,Object> article = articleService.selectArticleByTitle(request.get("title").toString());
            if (ObjectUtils.isEmpty(article)){
                return ResponseResult.createFailResult("发表失败",null);
            }
            result.put("article",article);
            return ResponseResult.createSuccessResult("发表成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
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


    @RequestMapping(value = "/api/article/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> update(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("articleId"))){
                return ResponseResult.createNotFoundResult("文章不存在", null);
            }
            Map<String,Object> data = articleService.selectArticleById((Integer) request.get("articleId"));
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("文章不存在", null);
            }
            if (!ObjectUtils.isEmpty(request.get("programaName"))){
                Map<String,Object> programa = programaService.selectProgramaByName(request.get("programaName").toString());
                if (ObjectUtils.isEmpty(programa)){
                    return ResponseResult.createFailResult("更新文章失败,栏目不存在",null);
                }
                if (!ObjectUtils.isEmpty(programa.get("programId"))){
                    request.put("programaId",programa.get("programId"));
                }
            }

            articleService.updateArticle(request);
            Map<String,Object> article = articleService.selectArticleById((Integer) request.get("articleId"));
            if (ObjectUtils.isEmpty(article)){
                return ResponseResult.createFailResult("更新文章失败",null);
            }
            result.put("article",article);
            return ResponseResult.createSuccessResult("更新文章成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/article/listArticles", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> listArticles(@RequestBody Map<String,Object> request) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            List<Integer> programaList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(request.get("programaName"))){
                Map<String,Object> programa = programaService.selectProgramaByName(request.get("programaName").toString());
                if (!ObjectUtils.isEmpty(programa)&&!ObjectUtils.isEmpty(programa.get("programaId"))){
                    programaList.add((Integer)programa.get("programaId"));
                    List<Map<String,Object>> programaChild = programaService.listProgramaByParentId((Integer)programa.get("programaId"));
                    if (!ObjectUtils.isEmpty(programaChild)){
                        for (Map<String,Object> child:programaChild){
                            programaList.add((Integer)child.get("programaId"));
                        }
                    }
                }
            }
            if (programaList.size()>0){
                request.put("programaIds",programaList);
            }

            List<Map<String,Object>> articleList = articleService.listArticles(request);
            result.put("articleList", articleList);
            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

}
