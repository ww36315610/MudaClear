package com.muda.service.platform.job.Impl;

import com.github.pagehelper.PageHelper;
import com.muda.beans.platform.cases.PlatformPlanCase;
import com.muda.beans.platform.job.PlatformJob;
import com.muda.dao.platform.job.PlatformJobDao;
import com.muda.service.platform.job.PlatformJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformJobServiceImpl implements PlatformJobService {
    @Autowired
    PlatformJobDao platformJobDao;

    @Override
    public int save(PlatformJob platformJob) {
        return platformJobDao.save(platformJob);
    }

    @Override
    public int delete(Integer id) {
        return platformJobDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return platformJobDao.batchRemove(ids);
    }

    @Override
    public int update(PlatformJob platformJob) {
        return platformJobDao.update(platformJob);
    }

    @Override
    public List<PlatformJob> get(int pageNum, int pageSize, String jobName) {
        PageHelper.startPage(pageNum, pageSize);
        return platformJobDao.get(jobName);
    }

    @Override
    public int insert(PlatformJob platformJob) {
        return platformJobDao.insert(platformJob);
    }

    @Override
    public Integer findCount() {
        return platformJobDao.findCount();
    }
}
