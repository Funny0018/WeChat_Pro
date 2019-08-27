package com.dao;

import com.entity.bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("billDao")
public interface billDao {

    public void add(bill bill);

    public String selectMaxBillno(@Param("billno") String billno);


    public bill selectByFbillno(@Param("fbillno") String fbillno);

    //    public int updateState(@Param("fbillno") String fbillno, @Param("fstate") String fstate, @Param("fpaydate") String fpaydate, @Param("fsenddate") String fsenddate, @Param("farrivedate") String farrivedate);
    public int updateState(bill bill);

    public List<bill> selectByNone(Map<String, Object> map);

    public List<bill> selectByType(Map<String, Object> map);

    public int selectCountByType(@Param("fstate") int fstate, @Param("userid") int userid, @Param("key") String key);

    public int selectCountByNone(@Param("userid") int userid, @Param("key") String key);

    public int deleteByIds(Map<String, Object> map);

    public int selectVipPriceForUser(@Param("fuserid") int fuserid);
}
