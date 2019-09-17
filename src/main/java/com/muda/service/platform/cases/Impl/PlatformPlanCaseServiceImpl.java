package com.muda.service.platform.cases.Impl;

import com.github.pagehelper.PageHelper;
import com.muda.beans.platform.cases.PlatformPlanCase;
import com.muda.beans.platform.plan.PlatformPlan;
import com.muda.dao.platform.cases.PlatformPlanCaseDao;
import com.muda.dao.platform.plan.PlatformPlanDao;
import com.muda.service.platform.cases.PlatformPlanCaseService;
import com.muda.service.platform.plan.PlatformPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformPlanCaseServiceImpl implements PlatformPlanCaseService {
    @Autowired
    PlatformPlanCaseDao platformPlanCaseDao;

    @Override
    public int save(PlatformPlanCase platformPlanCase) {
        return platformPlanCaseDao.save(platformPlanCase);
    }

    @Override
    public int delete(Integer id) {
        return platformPlanCaseDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return platformPlanCaseDao.batchRemove(ids);
    }

    @Override
    public int update(PlatformPlanCase platformPlanCase) {
        return platformPlanCaseDao.update(platformPlanCase);
    }

    @Override
    public int updatePlanList(PlatformPlanCase platformPlanCase) {
        return platformPlanCaseDao.updatePlanList(platformPlanCase);
    }

    @Override
    public List<PlatformPlanCase> get(int pageNum, int pageSize, String caseName) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return platformPlanCaseDao.get(caseName);
    }
}
