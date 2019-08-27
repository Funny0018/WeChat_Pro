package com.service;

import com.entity.producttag;

import java.util.List;
import java.util.Map;

public interface producttagService {
    public List<producttag> selectByFids(String fids);

    public List<producttag> selectByNone();

    public void add(producttag producttag);

    public producttag selectById(int fid);

    public int deleteById(int fid);

    public int updateFname(String fname, int fid);
}
