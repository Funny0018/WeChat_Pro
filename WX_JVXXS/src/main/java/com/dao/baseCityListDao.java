package com.dao;

import com.entity.baseCityList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("baseCityListDao")
public interface baseCityListDao {
    public List<baseCityList> selectByFName(@Param("fname") String fname);
}
