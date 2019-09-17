package com.muda.dao.get_girl;

import com.muda.beans.get_girl.Oauths;

import java.util.List;

/**
 * MMybaties的Mapper接口，用于时间增删改查、分页
 */
public interface OauthsDao {
    //增
    int save(Oauths oauths);

    //删
    int delete(Integer id);

    //改
    int update(Oauths oauths);

    //查

    /**
     * 分页查询[模糊查询]
     *
     * @param projectName
     * @return
     */
    List<Oauths> get(String projectName);

    /**
     * 批量删除[根据IDS]
     *
     * @param ids
     * @return
     */
    int batchRemove(Integer[] ids);

}
