package com.dao;

import com.entity.producttype;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("producttypeDao")
public interface producttypeDao {
    public List<producttype> selectByNoneAll();

    public List<producttype> selectByNone();

    public producttype selectById(@Param("fid") int fid);

    public  List<producttype> selectByParentId(@Param("fid") int fid);

    public int updateById(producttype producttype);

    public void add(producttype producttype);


}
