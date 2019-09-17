package com.muda.dao.platform.job;

import com.muda.beans.platform.cases.PlatformPlanCase;
import com.muda.beans.platform.job.PlatformJob;

import java.util.List;

public interface PlatformJobDao {
    //增
    int save(PlatformJob platformJob);

    //删
    int delete(Integer id);

    //改
    int update(PlatformJob platformJob);

    //修改计划列表
    int updatePlanList(PlatformJob platformJob);

    //查

    /**
     * 分页查询[模糊查询]
     *
     * @param jobName
     * @return
     */
    List<PlatformJob> get(String jobName);

    /**
     * 批量删除[根据IDS]
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);

    int insert(PlatformJob platformJob);

    Integer findCount();
}
