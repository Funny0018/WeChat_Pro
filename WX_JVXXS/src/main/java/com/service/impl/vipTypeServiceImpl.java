package com.service.impl;

import com.dao.vipTypeDao;
import com.entity.vipType;
import com.service.vipTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("vipTypeService")
public class vipTypeServiceImpl implements vipTypeService {
    @Resource(name = "vipTypeDao")
    private vipTypeDao vipTypeDao;

    public List<vipType> selectByNone() {
        return vipTypeDao.selectByNone();
    }

}
