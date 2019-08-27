package com.dao;

import com.entity.billProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("billProductDao")
public interface billProductDao {
    public void add(List<billProduct> billProduct);

    public List<billProduct> selectByFbillid(@Param("fbillid") int fbillid);
}
