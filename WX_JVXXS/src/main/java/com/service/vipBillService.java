package com.service;

import com.entity.vipBill;
import org.apache.ibatis.annotations.Param;

public interface vipBillService {
    public void insertInto(vipBill vipBill);

    public vipBill selectByBillno(String billno);
//    public int updateState(vipBill vipBill);

    public int selectVipIsTehui(int userid);
}
