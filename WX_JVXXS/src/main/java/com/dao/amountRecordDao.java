package com.dao;

import com.entity.amountRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository("amountRecordDao")
public interface amountRecordDao {
    public List<amountRecord> selectByUserid(@Param("userid") int userid);

    public void add(amountRecord amountRecord);

    public amountRecord selectByfwxno(@Param("fwxno") String fwxno);

    public List<amountRecord> selectByUserAndType(Map<String,Object> map);
    public int selectCountByUserAndType(Map<String,Object> map);
}
