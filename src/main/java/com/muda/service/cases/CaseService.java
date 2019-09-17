package com.muda.service.cases;


import com.muda.beans.cases.Case;

import java.util.List;

public interface CaseService {
    /**
     *
     * @param caseName
     * @return
     */
    List<Case> get(int pageNum,int pageSize,String caseName);

    /**
     * 增加一个对象
     *
     * @param cases
     * @return
     */
    int save(Case cases);

    /**
     * 修改一个对象
     *
     * @param cases
     * @return
     */
    int update(Case cases);


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
     * 获取所有caseName
     *
     * @return
     */
    List<String> findByCaseName();

    /**
     * 获取所有数据
     *
     * @return
     */
    List<Case> findAll();

    /**
     * 分页查询
     *
     * @return
     */
    List<Case> findByPage(int pageNum,int pageSize);



    Integer findCount();

}
