package com.service;

import com.entity.userCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface userCouponService {
    public int add(userCoupon u);

    public userCoupon selectBuFid(int fid);

    public List<userCoupon> selectByFuserid(int fuserid);

    public int selectCountByFuserid(int fuserid);

    public int updateState(@Param("fid") int fid, @Param("fstate") int fstate);

    public int selectIsNew(String fuserid,String fname);
}
