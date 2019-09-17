package com.muda.service.muda_project.Imp;

import com.github.pagehelper.PageHelper;
import com.muda.beans.muda_project.MudaProject;
import com.muda.dao.muda_project.MudaProjectDao;
import com.muda.service.muda_project.MudaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MudaProjectServiceImpl implements MudaProjectService {
    @Autowired
    MudaProjectDao mudaProjectDao;


    @Override
    public int save(MudaProject mudaProject) {
        return mudaProjectDao.save(mudaProject);
    }

    @Override
    public int delete(Integer id) {
        return mudaProjectDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return mudaProjectDao.batchRemove(ids);
    }

    @Override
    public int update(MudaProject mudaProject) {
        return mudaProjectDao.update(mudaProject);
    }

    @Override
    public List<MudaProject> get(int pageNum, int pageSize, String projectName) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return mudaProjectDao.get(projectName);
    }


}
