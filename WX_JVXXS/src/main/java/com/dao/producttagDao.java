package com.dao;

import com.entity.producttag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface producttagDao {

    public List<producttag> selectByFids(Map fids);

    public producttag selectById(int fid);

    public List<producttag> selectByNone();

    public int updateById(producttag producttag);

    public void add(producttag producttag);
}
