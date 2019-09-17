package com.muda.service.project.Imp;

import com.github.pagehelper.PageHelper;
import com.muda.beans.project.Project;
import com.muda.dao.project.ProjectDao;
import com.muda.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao projectDao;

    @Override
    public List<Project> get(int pageNum, int pageSize, String projectName) {
        PageHelper.startPage(pageNum, pageSize);
        return projectDao.get(projectName);
    }

    @Override
    public int save(Project project) {
        return projectDao.save(project);
    }

    @Override
    public int update(Project project) {
        System.out.println(project.toString());
        return projectDao.update(project);
    }

    @Override
    public int delete(Integer id) {
        return projectDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return projectDao.batchRemove(ids);
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public List<Project> findByPage(int pageNum, int pageSize) {

        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return projectDao.findByPage();
    }
}
