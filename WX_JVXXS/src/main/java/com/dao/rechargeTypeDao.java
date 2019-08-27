package com.dao;

import com.entity.rechargeType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("rechargeTypeDao")
public interface rechargeTypeDao {
    public List<rechargeType> selectByNone();
    public rechargeType selectByFid(@Param("fid")int fid);
}
