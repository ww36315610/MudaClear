package com.muda.dao.project;

import com.muda.beans.project.Project;

import java.util.List;

public interface ProjectDao {

    /**
     *
     * @param projectName
     * @return
     */
    List<Project> get(String projectName);

    /**
     * 增加一个对象
     *
     * @param project
     * @return
     */
    int save(Project project);

    /**
     * 修改一个对象
     *
     * @param project
     * @return
     */
    int update(Project project);


    /**
     * 根据Id删除一个对象
     *
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 根据Ids删除多个数据
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<Project> findAll();

    /**
     * 分页查询
     *
     * @return
     */
    List<Project> findByPage();

}
