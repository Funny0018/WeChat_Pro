package com.service.impl;

import com.entity.prientTask;
import com.service.prientTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("prientTaskService")
public class prientTaskServiceImpl implements prientTaskService {

    @Resource(name = "prientTaskDao")
    private com.dao.prientTaskDao prientTaskDao;

    public void add(prientTask prientTask) {
        prientTaskDao.add(prientTask);
    }
}
