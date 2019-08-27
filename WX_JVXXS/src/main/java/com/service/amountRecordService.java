package com.service;

import com.entity.amountRecord;
import com.entity.bill;
import com.entity.vipBill;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface amountRecordService {
    public void add(amountRecord amountRecord);

    public int payOffSale(bill b, String billno, SimpleDateFormat df, Date day, String wxno, String attach, String wxAmount);

    public int payOffJoinVip(vipBill vipBill, String billno, SimpleDateFormat df, Date day, String wxno, String attach, String wxAmount);

    public int payOffRecharge(vipBill vipBill, String billno, SimpleDateFormat df, Date day, String wxno, String attach, String wxAmount);


    public List<amountRecord> selectByUserAndType(int currPage, int pageSize, List<Integer> type, int userid);

    public int selectCountByUserAndType(List<Integer> type, int userid);
}
