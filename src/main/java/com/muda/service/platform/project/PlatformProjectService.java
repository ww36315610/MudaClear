package com.muda.service.platform.project;

import com.muda.beans.platform.project.PlatformProject;

import java.util.List;

public interface PlatformProjectService {


    /**
     * 增加一个对象
     *
     * @param platformProject
     * @return
     */
    int save(PlatformProject platformProject);

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
     * @param platformProject
     * @return
     */
    int update(PlatformProject platformProject);


    /**
     * 修改计划列表
     * @param platformProject
     * @return
     */
    int updatePlanList(PlatformProject platformProject);

    /**
     * @param pageNum
     * @param pageSize
     * @param projectName
     * @return
     */
    List<PlatformProject> get(int pageNum, int pageSize, String projectName);

    /**
     * 获取项目列表
     * @return
     */
    List<String> findByName();

}
