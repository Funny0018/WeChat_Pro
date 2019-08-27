package com.service.impl;

import com.dao.optionalCityDao;
import com.entity.optionalCity;
import com.service.optionalCityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("optionalCityService")
public class optionalCityServiceImpl implements optionalCityService {
    @Resource(name = "optionalCityDao")
    private com.dao.optionalCityDao optionalCityDao;

    public List<optionalCity> selectByNone() {
        return optionalCityDao.selectByNone();
    }

    public int deleteIds(List<String> fids) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fids", fids);
        return optionalCityDao.deleteIds(map);
    }

    public int update(optionalCity optionalCity) {
        if (optionalCity.getFid() == 0) {
            optionalCityDao.add(optionalCity);
            return optionalCity.getFid();
        } else {
            return optionalCityDao.update(optionalCity);
        }
    }
}
