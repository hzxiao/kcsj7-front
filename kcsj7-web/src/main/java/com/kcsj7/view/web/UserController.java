package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.filter.util.JwtUtil;
import com.kcsj7.view.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
@RestController
public class UserController  {
    private static Logger log = Logger.getLogger(UserController.class);
    @Resource
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> login(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            if (ObjectUtils.isEmpty(request.get("username"))){
                return ResponseResult.createFailResult("用户名不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("pwd"))){
                return ResponseResult.createFailResult("密码不能为空",null);
            }
            Map<String,Object> data = userService.verifyUser(request);
            if (ObjectUtils.isEmpty(data)){
                return ResponseResult.createFailResult("登录失败",null);
            }
//            String token = JwtUtil.generToken(((Map<String,Object>)data.get("user")).get("username").toString(),null,null);
//            data.put("token",token);
            return ResponseResult.createSuccessResult("登录成功",data);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> register(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            if (ObjectUtils.isEmpty(request.get("username"))){
                return ResponseResult.createFailResult("注册失败,用户名不能为空",null);
            }
            if (ObjectUtils.isEmpty(request.get("pwd"))){
                return ResponseResult.createFailResult("注册失败,密码不能为空",null);
            }

            if (userService.getUserByUsername(request.get("username").toString())!=null){
                return ResponseResult.createFailResult("注册失败,用户名重复", null);
            }
            return ResponseResult.createSuccessResult("注册成功", userService.addUser(request));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/api/user/name/{username}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUsername(@PathVariable String username) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = userService.getUserByUsername(username);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("用户不存在", null);
            }
            result.put("user", data);

            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUserId(@PathVariable Integer userId) {
        Map<String,Object> result = new HashMap<String,Object>();
        System.out.println(userId);
        try {
            Map<String,Object> data = userService.getUserByUid(userId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("用户不存在", null);
            }
            result.put("user", data);

            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
