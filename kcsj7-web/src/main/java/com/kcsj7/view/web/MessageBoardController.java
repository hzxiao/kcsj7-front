package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.service.MessageBoardService;
import com.kcsj7.view.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/3.
 */
@Controller
public class MessageBoardController {
    private static Logger log = Logger.getLogger(MessageBoardController.class);

    @Resource
    private MessageBoardService messageBoardService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/api/board/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> add(HttpServletRequest httpServletRequest, @RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (!ObjectUtils.isEmpty(httpServletRequest.getParameter("token"))){
                request.put("token",httpServletRequest.getParameter("token"));
            }
            if (ObjectUtils.isEmpty(request.get("userId"))){
                return ResponseResult.createFailResult("留言失败,指定留言用户不能为空",null);
            }
            Map<String,Object> userId = userService.getUserByUid((Integer) request.get("userId"));
            if (ObjectUtils.isEmpty(userId)){
                return ResponseResult.createFailResult("留言失败,指定留言用户不存在",null);
            }
            if (ObjectUtils.isEmpty(request.get("title"))){
                return ResponseResult.createFailResult("留言失败,标题不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("content"))){
                return ResponseResult.createFailResult("留言失败,内容不能为空",null);
            }
            messageBoardService.addMessageBoard(request);
            if (ObjectUtils.isEmpty(request.get("messageId"))){
                return ResponseResult.createFailResult("留言失败",null);
            }
            Integer messageId = (Integer)request.get("messageId");
            Map<String,Object> messageBoard = messageBoardService.selectMessageBoardById(messageId);
            if (ObjectUtils.isEmpty(messageBoard)){
                return ResponseResult.createFailResult("留言失败",null);
            }
            
            result.put("messageBoard",messageBoard);
            return ResponseResult.createSuccessResult("留言成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/board/{messageId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUserId(@PathVariable Integer messageId) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = messageBoardService.selectMessageBoardById(messageId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("留言不存在", null);
            }
            result.put("messageBoard", data);

            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }


    @RequestMapping(value = "/api/board/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> update(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("messageId"))){
                return ResponseResult.createNotFoundResult("留言不存在", null);
            }
            Map<String,Object> data = messageBoardService.selectMessageBoardById((Integer) request.get("messageId"));
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("留言不存在", null);
            }

            Integer messageId = messageBoardService.updateMessageBoard(request);
            Map<String,Object> messageBoard = messageBoardService.selectMessageBoardById((Integer) request.get("messageId"));
            if (ObjectUtils.isEmpty(messageBoard)){
                return ResponseResult.createFailResult("更新留言失败",null);
            }

            result.put("messageBoard",messageBoard);
            return ResponseResult.createSuccessResult("更新留言成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/board/listMessages", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> listMessages(@RequestBody Map<String,Object> request) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            if (!ObjectUtils.isEmpty(request.get("toUserId"))){
                Map<String,Object> userId = userService.getUserByUid((Integer) request.get("toUserId"));
                if (ObjectUtils.isEmpty(userId)){
                    return ResponseResult.createFailResult("列出留言失败,指定留言用户不存在",null);
                }
            }
            if (!ObjectUtils.isEmpty(request.get("userId"))){
                Map<String,Object> userId = userService.getUserByUid((Integer) request.get("userId"));
                if (ObjectUtils.isEmpty(userId)){
                    return ResponseResult.createFailResult("列出留言失败,当前用户不存在",null);
                }
            }
            List<Map<String,Object>> messageBoardList = messageBoardService.listMessageBoards(request);
            result.put("messageBoardList", messageBoardList);
            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
