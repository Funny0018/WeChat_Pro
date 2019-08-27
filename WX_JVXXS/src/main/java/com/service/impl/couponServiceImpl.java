package com.service.impl;

import com.dao.couponDao;
import com.entity.coupon;
import com.service.couponService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("couponService")
public class couponServiceImpl implements couponService {
    @Resource(name = "couponDao")
    private couponDao couponDao;

    public List<coupon> selectByNone() {
        return couponDao.selectByNone();
    }


    public List<coupon> selectByQueryForBack(int fstate, String fname, String ftype) {

        Map<String, Object> map = new HashedMap();
        map.put("fstate", fstate);
        if (fname != "") {
            map.put("fname", "%" + fname + "%");
        } else {
            map.put("fname", fname);
        }
        map.put("ftype", ftype);
        return couponDao.selectByQueryForBack(map);
    }

    public int updateState(String fstate, List<String> fids) {
        Map<String, Object> map = new HashedMap();
        map.put("fstate", fstate);
        map.put("fids", fids);
        return couponDao.updateState(map);
    }

    public coupon selectById(int fid) {
        return couponDao.selectById(fid);
    }

    public int update(coupon coupon) {
        if (coupon.getFid() == 0) {
            return couponDao.add(coupon);
        } else {
            return couponDao.update(coupon);
        }
    }


    public List<coupon> selectByKey(String key) {
        return couponDao.selectByKey(key);
    }
}
