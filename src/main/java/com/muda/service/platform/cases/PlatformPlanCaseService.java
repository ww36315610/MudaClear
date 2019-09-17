package com.muda.service.platform.cases;

import com.muda.beans.platform.cases.PlatformPlanCase;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlatformPlanCaseService {


    /**
     * 增加一个对象
     *
     * @param platformPlanCase
     * @return
     */
    int save(PlatformPlanCase platformPlanCase);

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
     * @param platformPlanCase
     * @return
     */
    int update(PlatformPlanCase platformPlanCase);


    /**
     * 修改计划列表
     * @param platformPlanCase
     * @return
     */
    int updatePlanList(PlatformPlanCase platformPlanCase);

    /**
     * @param pageNum
     * @param pageSize
     * @param caseName
     * @return
     */
    List<PlatformPlanCase> get(int pageNum, int pageSize, String caseName);

}
