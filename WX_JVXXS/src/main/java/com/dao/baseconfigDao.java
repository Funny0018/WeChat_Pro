package com.dao;

import com.entity.baseconfig;
import org.springframework.stereotype.Repository;

@Repository("baseconfigDao")
public interface baseconfigDao {
    public baseconfig selectByNone();
    public int updateInfo(baseconfig baseconfig);
}
