package com.service.impl;

import com.entity.prientdevice;
import com.service.prientdeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("prientdeviceService")
public class prientdeviceServiceImpl implements prientdeviceService {

    @Resource(name = "prientdeviceDao")
    private com.dao.prientdeviceDao prientdeviceDao;

    public List<prientdevice> selectByNone() {
        return prientdeviceDao.selectByNone();
    }

    public prientdevice selectByFID(String fid) {
        return prientdeviceDao.selectByFID(fid);
    }


    public int deleteIds(List<String> fids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fids", fids);
        return prientdeviceDao.deleteIds(map);
    }

    public int add(prientdevice prientdevice){
        return prientdeviceDao.add(prientdevice);
    }
    public int update(prientdevice prientdevice) {
        prientdevice p = prientdeviceDao.selectByFID(String.valueOf(prientdevice.getFid()));
        int a = -1;
        if (p == null) {
            a = prientdeviceDao.add(prientdevice);
        } else {
            a = prientdeviceDao.update(prientdevice);
        }
        return a;
    }
}
