package com.dao;

import com.entity.deliveryAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("deliveryAddressDao")
public interface deliveryAddressDao {
    public List<deliveryAddress> selectByFuserid(@Param("fuserid") int fuserid);

    public deliveryAddress selectByFid(@Param("fid") int fid);

    public void add(deliveryAddress deliveryAddress);

    public int update(deliveryAddress deliveryAddress);

    public int delete(@Param("fid")int fid);
}
