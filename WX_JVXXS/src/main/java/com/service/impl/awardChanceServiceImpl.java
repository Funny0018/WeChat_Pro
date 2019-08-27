package com.service.impl;

import com.dao.awardChanceDao;
import com.entity.awardChance;
import com.service.awardChanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("awardChanceService")
public class awardChanceServiceImpl implements awardChanceService {
    @Resource(name = "awardChanceDao")
    private awardChanceDao awardChanceDao;

    public List<awardChance> selectByNone() {
        return awardChanceDao.selectByNone();
    }

    public int update(awardChance a) {
        return awardChanceDao.update(a);
    }
}
