package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("TestDao")
public interface TestDao {
    public String selectFnameByFid(@Param("fid") String fid);
}
