package com.dao;

import com.entity.vipBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("vipBillDao")
public interface vipBillDao {
    public void insertInto(vipBill vipBill);

    public String selectMaxBillno(@Param("billno") String billno);

    public vipBill selectByBillno(@Param("billno") String billno);

    public int updateState(vipBill vipBill);

    public int selectVipIsTehui(@Param("userid") int userid);
}
