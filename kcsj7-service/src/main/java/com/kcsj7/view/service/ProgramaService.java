package com.kcsj7.view.service;

import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/2.
 */
public interface ProgramaService {
    void addPrograma(Map<String, Object> data);
    void updatePrograma(Map<String, Object> data);
    Map<String, Object> selectProgramaById(Integer data);
    Map<String, Object> selectProgramaByName(String data);
    List<Map<String, Object>> listProgramaByParentId(Integer data);
    List<Map<String, Object>> listProgramas(Map<String, Object> data);
    Map<String, Object> listArticles(Map<String, Object> data);


}
