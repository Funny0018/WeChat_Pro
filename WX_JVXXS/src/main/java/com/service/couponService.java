package com.service;

import com.entity.coupon;

import java.util.List;
import java.util.Map;

public interface couponService {
    public List<coupon> selectByNone();

    public List<coupon> selectByQueryForBack(int fstate, String fname, String ftype);

    public int updateState(String fstate, List<String> fids);

    public int update(coupon coupon);

    public coupon selectById(int fid);

    public List<coupon> selectByKey( String key);
}
