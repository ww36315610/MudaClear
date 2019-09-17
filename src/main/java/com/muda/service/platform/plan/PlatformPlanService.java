package com.muda.service.platform.plan;

import com.muda.beans.platform.plan.PlatformPlan;
import com.muda.beans.platform.project.PlatformProject;

import java.util.List;

public interface PlatformPlanService {


    /**
     * 增加一个对象
     *
     * @param platformPlan
     * @return
     */
    int save(PlatformPlan platformPlan);

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
     * @param platformPlan
     * @return
     */
    int update(PlatformPlan platformPlan);


    /**
     * 修改计划列表
     *
     * @param platformPlan
     * @return
     */
    int updatePlanList(PlatformPlan platformPlan);

    /**
     * @param pageNum
     * @param pageSize
     * @param planName
     * @return
     */
    List<PlatformPlan> get(int pageNum, int pageSize, String planName);

    /**
     * 获取项目列表
     *
     * @return
     */
    List<String> findByName();
    List<String> findByPlanNameLianDong(String projectName);

}
