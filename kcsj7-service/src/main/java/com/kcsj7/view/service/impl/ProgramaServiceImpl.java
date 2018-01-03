package com.kcsj7.view.service.impl;

import com.kcsj7.view.dao.mapper.ProgramaDao;
import com.kcsj7.view.service.ProgramaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by tying on 2018/1/2.
 */
@Service
public class ProgramaServiceImpl implements ProgramaService{

    @Resource
    private ProgramaDao programaDao;

    @Override
    public void addPrograma(Map<String, Object> data) {
        programaDao.addPrograma(data);
    }

    @Override
    public void updatePrograma(Map<String, Object> data) {
        programaDao.updatePrograma(data);
    }

    @Override
    public Map<String, Object> selectProgramaById(Integer data) {
        return programaDao.selectProgramaById(data);
    }

    @Override
    public Map<String, Object> selectProgramaByName(String data) {
        return programaDao.selectProgramaByName(data);
    }

    @Override
    public List<Map<String, Object>> listProgramaByParentId(Integer data) {
        return programaDao.listProgramaByParentId(data);
    }
}
