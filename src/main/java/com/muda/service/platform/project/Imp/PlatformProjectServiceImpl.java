package com.muda.service.platform.project.Imp;

import com.github.pagehelper.PageHelper;
import com.muda.beans.platform.project.PlatformProject;
import com.muda.dao.platform.project.PlatformProjectDao;
import com.muda.service.platform.project.PlatformProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformProjectServiceImpl implements PlatformProjectService {
    @Autowired
    PlatformProjectDao platformProjectDao;


    @Override
    public int save(PlatformProject platformProject) {
        return platformProjectDao.save(platformProject);
    }

    @Override
    public int delete(Integer id) {
        return platformProjectDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return platformProjectDao.batchRemove(ids);
    }

    @Override
    public int update(PlatformProject platformProject) {
        return platformProjectDao.update(platformProject);
    }

    @Override
    public int updatePlanList(PlatformProject platformProject) {
        return platformProjectDao.update(platformProject);
    }

    @Override
    public List<PlatformProject> get(int pageNum, int pageSize, String projectName) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return platformProjectDao.get(projectName);
    }

    @Override
    public List<String> findByName() {
       return platformProjectDao.findByName();
    }


}
