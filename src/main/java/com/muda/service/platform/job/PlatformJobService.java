package com.muda.service.platform.job;

import com.muda.beans.platform.job.PlatformJob;

import java.util.List;

public interface PlatformJobService {


    /**
     * 增加一个对象
     *
     * @param platformJob
     * @return
     */
    int save(PlatformJob platformJob);

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
     * @param platformJob
     * @return
     */
    int update(PlatformJob platformJob);
    
    /**
     * @param pageNum
     * @param pageSize
     * @param jobName
     * @return
     */
    List<PlatformJob> get(int pageNum, int pageSize, String jobName);



    int insert(PlatformJob platformJob);

    Integer findCount();


}
