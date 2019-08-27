package com.service.impl;

import com.entity.errorLog;
import com.service.errorLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("errorLogService")
public class errorLogServiceImpl implements errorLogService {

    @Resource(name = "errorLogDao")
    private com.dao.errorLogDao errorLogDao;

    public int add(errorLog errorLog) {
        return errorLogDao.add(errorLog);
    }
}
