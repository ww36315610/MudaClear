package com.muda.dao.platform.project;

import com.muda.beans.platform.project.PlatformProject;

import java.util.List;

/**
 * MMybaties的Mapper接口，用于时间增删改查、分页
 */
public interface PlatformProjectDao {
    //增
    int save(PlatformProject platformProject);

    //删
    int delete(Integer id);

    //改
    int update(PlatformProject platformProject);

    //修改计划列表
    int updatePlanList(PlatformProject platformProject);

    //查

    /**
     * 分页查询[模糊查询]
     *
     * @param projectName
     * @return
     */
    List<PlatformProject> get(String projectName);

    /**
     * 获取所有projectName
     *
     * @return
     */
    List<String> findByName();

    /**
     * 批量删除[根据IDS]
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);

}
