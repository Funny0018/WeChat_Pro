package com.dao;

import com.entity.userCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userCouponDao")
public interface userCouponDao {
    public int add(userCoupon u);
    public userCoupon selectBuFid(@Param("fid") int fid);

    public List<userCoupon> selectByFuserid(@Param("fuserid") int fuserid);

    public int selectCountByFuserid(@Param("fuserid") int fuserid);

    public int updateState(@Param("fid") int fid,@Param("fstate")int fstate);
    public int selectIsNew(@Param("fuserid") String fuserid, @Param("fname") String fname);
}
