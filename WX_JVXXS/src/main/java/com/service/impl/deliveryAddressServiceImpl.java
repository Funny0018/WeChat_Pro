package com.service.impl;

import com.dao.deliveryAddressDao;
import com.entity.deliveryAddress;
import com.service.deliveryAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("deliveryAddressService")
public class deliveryAddressServiceImpl implements deliveryAddressService {
    @Resource(name = "deliveryAddressDao")
    private deliveryAddressDao deliveryAddressDao;

    public List<deliveryAddress> selectByFuserid(int fuserid) {
        return deliveryAddressDao.selectByFuserid(fuserid);
    }

    public int add(deliveryAddress deliveryAddress) {
        if (deliveryAddress.getFid() > 0) {
            return deliveryAddressDao.update(deliveryAddress);
        } else {
            deliveryAddressDao.add(deliveryAddress);
            return deliveryAddress.getFid();
        }
    }

    public deliveryAddress selectByFid(int fid) {
        return deliveryAddressDao.selectByFid(fid);
    }

    public int delete(int fid) {
        return deliveryAddressDao.delete(fid);
    }
}
