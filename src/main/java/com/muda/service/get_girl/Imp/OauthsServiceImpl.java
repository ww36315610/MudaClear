package com.muda.service.get_girl.Imp;

import com.github.pagehelper.PageHelper;
import com.muda.beans.get_girl.Oauths;
import com.muda.dao.get_girl.OauthsDao;
import com.muda.service.get_girl.OauthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OauthsServiceImpl implements OauthsService {
    @Autowired
    OauthsDao oauthsDao;

    @Override
    public int save(Oauths oauths) {
        return oauthsDao.save(oauths);
    }

    @Override
    public int delete(Integer id) {
        return oauthsDao.delete(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return oauthsDao.batchRemove(ids);
    }

    @Override
    public int update(Oauths oauths) {
        return oauthsDao.update(oauths);
    }

    @Override
    public List<Oauths> get(int pageNum, int pageSize, String projectName) {
        //使用PageHelper来支持分页
        PageHelper.startPage(pageNum, pageSize);
        return oauthsDao.get(projectName);
    }
}
