package com.service.impl;

import com.dao.TestDao;
import com.service.testService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("testService")
public class textServiceImpl implements testService {
    @Resource(name = "TestDao")
    private TestDao testDao;

    public String selectFnameByFid(String fid) {
        return testDao.selectFnameByFid(fid);
    }
}
