package com.service.impl;

import com.dao.baseCityListDao;
import com.entity.baseCityList;
import com.service.baseCityListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("baseCityListService")
public class baseCityListServiceImpl implements baseCityListService {
    @Resource(name = "baseCityListDao")
    private baseCityListDao baseCityListDao;

    public List<baseCityList> selectByFName(String fname) {
        return baseCityListDao.selectByFName(fname);
    }
}
