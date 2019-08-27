package com.service;

import com.entity.bill;

import java.util.List;
import java.util.Map;

public interface billService {
    public String add(bill bill);

    public bill selectByFbillno(String fbillno);

    public int updateState(bill bill);

    public List<bill> selectByQueryPage(int currPage, int pageSize, int type, int userid);

    public List<bill> selectByQueryPageForKey(int currPage, int pageSize, int type, String key);

    public bill selectByBillSimple(String fbillno);

    public int selectCount(int fstate, int userid, String key);


    public int deleteByIds(String ids);

    public int selectVipPriceForUser(int fuserid);
}
