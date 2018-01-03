package com.kcsj7.view.web;

import com.kcsj7.view.common.util.ResponseResult;
import com.kcsj7.view.service.ProgramaService;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/2.
 */
@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    private static Logger log = Logger.getLogger(ProgramaController.class);

    @Resource
    ProgramaService programaService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> add(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ObjectUtils.isEmpty(request.get("programaName"))){
                return ResponseResult.createFailResult("创建栏目失败,栏目名称不能为空",null);
            }
            String parentPath = "";
            if (!ObjectUtils.isEmpty(request.get("parentName"))){
                Map<String,Object> parent = programaService.selectProgramaByName(request.get("parentName").toString());
                if (parent == null) {
                    return ResponseResult.createFailResult("创建栏目失败,父栏目不存在",null);
                }
                if (!ObjectUtils.isEmpty(parent.get("programaPath"))){
                    parentPath = parent.get("programaPath").toString()+"/";
                }
            }
            request.put("programaPath",parentPath+request.get("programaName").toString());
            programaService.addPrograma(request);
            Map<String,Object> programa = programaService.selectProgramaByName(request.get("programaName").toString());
            if (ObjectUtils.isEmpty(programa)){
                return ResponseResult.createFailResult("创建栏目失败",null);
            }
            result.put("programa",programa);
            return ResponseResult.createSuccessResult("创建栏目成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/{programaId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> getUserByAUserId(@PathVariable Integer programaId) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = programaService.selectProgramaById(programaId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("栏目不存在", null);
            }
            result.put("programa", data);
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
            if (ObjectUtils.isEmpty(request.get("programaId"))){
                return ResponseResult.createNotFoundResult("栏目不存在", null);
            }
            Map<String,Object> data = programaService.selectProgramaById((Integer) request.get("programaId"));
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("栏目不存在", null);
            }
            String parentPath = "";
            if (!ObjectUtils.isEmpty(data.get("parentName"))){
                Map<String,Object> parent = programaService.selectProgramaByName(data.get("parentName").toString());
                if (!ObjectUtils.isEmpty(parent.get("programaPath"))){
                    parentPath = parent.get("programaPath").toString()+"/";
                }
            }
            request.put("programaPath",parentPath+request.get("programaName").toString());

            programaService.updatePrograma(request);
            Map<String,Object> programa = programaService.selectProgramaById((Integer) request.get("programaId"));
            if (ObjectUtils.isEmpty(programa)){
                return ResponseResult.createFailResult("更新栏目失败",null);
            }
            result.put("programa",programa);
            return ResponseResult.createSuccessResult("更新栏目成功",result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/listChild/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> listChild(@PathVariable Integer parentId) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String,Object> data = programaService.selectProgramaById(parentId);
            if (ObjectUtils.isEmpty(data)) {
                return ResponseResult.createNotFoundResult("父栏目不存在", null);
            }
            List<Map<String,Object>> programaList = programaService.listProgramaByParentId(parentId);
            result.put("programaList", programaList);
            return ResponseResult.createSuccessResult("success", result);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
