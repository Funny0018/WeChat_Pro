package com.service.impl;

import com.dao.backUserDao;
import com.entity.backuser;
import com.service.backUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("backUserService")
public class backUserServiceImpl implements backUserService {
    @Resource(name = "backUserDao")
    private backUserDao backUserDao;

    public backuser selectByNameAndPsd(backuser backuser) {
        backuser bu = backUserDao.selectByNameAndPsd(backuser);
        if (bu != null && bu.getFid() > 0) {
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            bu.setFlogintime(sf.format(date));
            backUserDao.updateBakcUser(backuser);
        }
        return bu;
    }

    public int updateBakcUser(backuser backuser) {
        return backUserDao.updateBakcUser(backuser);
    }
}
