package com.service.impl;

import com.dao.userCouponDao;
import com.entity.userCoupon;
import com.service.userCouponService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userCouponService")
public class userConponServiceImpl implements userCouponService {
    @Resource(name = "userCouponDao")
    private userCouponDao userCouponDao;

    public int add(userCoupon u) {
        return userCouponDao.add(u);
    }

    public userCoupon selectBuFid(int fid){
        return userCouponDao.selectBuFid(fid);
    }
    public List<userCoupon> selectByFuserid(int fuserid) {
        return userCouponDao.selectByFuserid(fuserid);
    }

    public int selectCountByFuserid(int fuserid) {
        return userCouponDao.selectCountByFuserid(fuserid);
    }

    public int updateState(int fid, int fstate) {
        return userCouponDao.updateState(fid, fstate);
    }

    public int selectIsNew(String fuserid, String fname) {
        return userCouponDao.selectIsNew(fuserid, fname);
    }
}
