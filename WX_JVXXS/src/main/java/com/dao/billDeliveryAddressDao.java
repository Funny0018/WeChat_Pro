package com.dao;

import com.entity.billDeliveryAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("billDeliveryAddressDao")
public interface billDeliveryAddressDao {
    public void add(billDeliveryAddress billDeliveryAddress);

    public billDeliveryAddress selectByFbillid(@Param("fbillid") int fbillid);
}
