package com.kcsj7.view.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/2.
 */
public interface ProgramaDao {
    void addPrograma(Map<String, Object> data);
    void updatePrograma(Map<String, Object> data);
    Map<String, Object> selectProgramaById(Integer programaId);
    Map<String, Object> selectProgramaByName(String programaName);
    List<Map<String,Object>> listProgramaByParentId(Integer parentId);
}
