package com.service.impl;

import com.entity.baseconfig;
import com.entity.optionalCity;
import com.service.baseConfigService;
import org.springframework.stereotype.Service;
import com.dao.baseconfigDao;

import javax.annotation.Resource;
import java.util.List;

@Service("baseConfigService")
public class baseConfigServiceImpl implements baseConfigService {

    @Resource(name = "baseconfigDao")
    private baseconfigDao baseconfigDao;
    @Resource(name = "optionalCityDao")
    private com.dao.optionalCityDao optionalCityDao;


    public baseconfig selectByNone() {
        baseconfig baseconfig = baseconfigDao.selectByNone();
        baseconfig.setFoptionalCity(optionalCityDao.selectByNone());
        return baseconfig;
    }

    public int updateInfo(baseconfig baseconfig){
        return baseconfigDao.updateInfo(baseconfig);
    }
}
