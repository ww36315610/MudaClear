package com.muda.service.platform.plan.Imp;

import com.github.pagehelper.PageHelper;
import com.muda.beans.platform.plan.PlatformPlan;
import com.muda.beans.platform.project.PlatformProject;
import com.muda.dao.platform.plan.PlatformPlanDao;
import com.muda.dao.platform.project.PlatformProjectDao;
import com.muda.service.platform.plan.PlatformPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformPlanServiceImpl implements PlatformPlanService {
    @Autowired
    PlatformPlanDao platformPlanDao;

    @Override
    public int save(PlatformPlan platformPlan) {
        return platformPlanDao.save(platformPlan);
    }

    @Override
    public int delete(Integer id) {
        return platformPlanDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return platformPlanDao.batchRemove(ids);
    }

    @Override
    public int update(PlatformPlan platformPlan) {
        return platformPlanDao.update(platformPlan);
    }

    @Override
    public int updatePlanList(PlatformPlan platformPlan) {
        return platformPlanDao.update(platformPlan);
    }

    @Override
    public List<PlatformPlan> get(int pageNum, int pageSize, String planName) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return platformPlanDao.get(planName);
    }

    @Override
    public List<String> findByName() {
        return platformPlanDao.findByName();
    }

    public List<String> findByPlanNameLianDong(String projectName) {
        return platformPlanDao.findByPlanNameLianDong(projectName);
    }

}
