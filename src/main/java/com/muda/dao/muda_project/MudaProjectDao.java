package com.muda.dao.muda_project;

import com.muda.beans.cases.Case;
import com.muda.beans.muda_project.MudaProject;

import java.util.List;

/**
 * MMybaties的Mapper接口，用于时间增删改查、分页
 */
public interface MudaProjectDao {
    //增
    int save(MudaProject mudaProject);

    //删
    int delete(Integer id);

    //改
    int update(MudaProject mudaProject);

    //查
    /**
     * 分页查询[模糊查询]
     *
     * @param projectName
     * @return
     */
    List<MudaProject> get(String projectName);

    /**
     * 批量删除[根据IDS]
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);

}
