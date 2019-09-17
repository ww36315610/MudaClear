package com.muda.service.muda_project;

import com.muda.beans.muda_project.MudaProject;
import com.muda.beans.project.Project;

import java.util.List;

public interface MudaProjectService {


    /**
     * 增加一个对象
     *
     * @param mudaProject
     * @return
     */
    int save(MudaProject mudaProject);

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
     * 修改一个对象
     *
     * @param mudaProject
     * @return
     */
    int update(MudaProject mudaProject);


    /**
     * @param pageNum
     * @param pageSize
     * @param projectName
     * @return
     */
    List<MudaProject> get(int pageNum, int pageSize, String projectName);

}
