package com.muda.service.get_girl;

import com.muda.beans.get_girl.Oauths;

import java.util.List;

public interface OauthsService {
    /**
     * 增加一个对象
     *
     * @param oauths
     * @return
     */
    int save(Oauths oauths);

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
     * @param oauths
     * @return
     */
    int update(Oauths oauths);


    /**
     * @param pageNum
     * @param pageSize
     * @param projectName
     * @return
     */
    List<Oauths> get(int pageNum, int pageSize, String projectName);
}
