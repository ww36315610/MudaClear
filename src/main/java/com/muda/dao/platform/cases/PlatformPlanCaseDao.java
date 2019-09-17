package com.muda.dao.platform.cases;


import com.muda.beans.platform.cases.PlatformPlanCase;
import com.muda.beans.platform.plan.PlatformPlan;

import java.util.List;

public interface PlatformPlanCaseDao {
    //增
    int save(PlatformPlanCase platformPlanCase);

    //删
    int delete(Integer id);

    //改
    int update(PlatformPlanCase platformPlanCase);

    //修改计划列表
    int updatePlanList(PlatformPlanCase platformPlanCase);

    //查

    /**
     * 分页查询[模糊查询]
     *
     * @param caseName
     * @return
     */
    List<PlatformPlanCase> get(String caseName);

    /**
     * 批量删除[根据IDS]
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);
}
