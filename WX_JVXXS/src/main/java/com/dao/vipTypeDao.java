package com.dao;

import com.entity.vipType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vipTypeDao")
public interface vipTypeDao {
    public List<vipType> selectByNone();

    public vipType selectByFid(@Param("fid") int fid);
}
