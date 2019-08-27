package com.service;

import com.entity.deliveryAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface deliveryAddressService {
    public List<deliveryAddress> selectByFuserid(int fuserid);

    public int add(deliveryAddress deliveryAddress);

    public deliveryAddress selectByFid(int fid);

    public int delete(int fid);
}
