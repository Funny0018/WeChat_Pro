package com.service;

import com.entity.producttype;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface productTypeService {
    public List<producttype> selectByNone();

    public List<producttype> selectByNoneAll();

    public producttype selectById(int fid);

    public List<producttype> selectByParentId(int fid);

    public int deleteById(int fid);

    public int updateFname(String fname, int fid, int fparenttype);

    public void add(producttype producttype);
}
