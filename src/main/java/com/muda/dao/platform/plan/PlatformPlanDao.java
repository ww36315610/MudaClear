package com.muda.dao.platform.plan;


import com.muda.beans.platform.plan.PlatformPlan;

import java.util.List;

public interface PlatformPlanDao {
    //增
    int save(PlatformPlan platformPlan);

    //删
    int delete(Integer id);

    //改
    int update(PlatformPlan platformPlan);

    //修改计划列表
    int updatePlanList(PlatformPlan platformPlan);

    //查

    /**
     * 分页查询[模糊查询]
     *
     * @param planName
     * @return
     */
    List<PlatformPlan> get(String planName);

    /**
     * 获取所有projectName
     *
     * @return
     */
    List<String> findByName();
    List<String> findByPlanNameLianDong(String projectName);

    /**
     * 批量删除[根据IDS]
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);
}
