package com.muda.service.cases.imp;

import com.github.pagehelper.PageHelper;
import com.muda.beans.cases.Case;
import com.muda.dao.cases.CaseDao;
import com.muda.service.cases.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    CaseDao caseDao;


    @Override
    public List<Case> get(int pageNum, int pageSize, String caseName) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return caseDao.get(caseName);
    }

    @Override
    public int save(Case cases) {
        return caseDao.save(cases);
    }

    @Override
    public int update(Case cases) {
        return caseDao.update(cases);
    }

    @Override
    public int delete(Integer id) {
        return caseDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return caseDao.batchRemove(ids);
    }

    public List<String> findByCaseName() {
        return caseDao.findByCaseName();
    }

    @Override
    public List<Case> findAll() {
        return caseDao.findAll();
    }

    @Override
    public List<Case> findByPage(int pageNum, int pageSize) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return caseDao.findByPage();
    }

    @Override
    public Integer findCount() {
        return caseDao.findCount();
    }

}
