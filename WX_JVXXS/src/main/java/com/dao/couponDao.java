package com.dao;

import com.entity.coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("couponDao")
public interface couponDao {
    public List<coupon> selectByNone();

    public List<coupon> selectByQueryForBack(Map<String, Object> map);

    public int updateState(Map<String, Object> map);

    public int update(coupon coupon);

    public int add(coupon coupon);

    public coupon selectById(@Param("fid") int fid);

    public List<coupon> selectByKey(@Param("fname") String key);
}
