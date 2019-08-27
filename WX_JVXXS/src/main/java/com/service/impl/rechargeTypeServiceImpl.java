package com.service.impl;

import com.dao.rechargeTypeDao;
import com.entity.rechargeType;
import com.service.rechargeTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("rechargeTypeService")
public class rechargeTypeServiceImpl implements rechargeTypeService {

    @Resource(name = "rechargeTypeDao")
    private com.dao.rechargeTypeDao rechargeTypeDao;

    public List<rechargeType> selectByNone() {
        return rechargeTypeDao.selectByNone();
    }
}
