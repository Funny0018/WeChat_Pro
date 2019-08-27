package com.dao;

import com.entity.errorLog;
import org.springframework.stereotype.Repository;

@Repository("errorLogDao")
public interface errorLogDao {
    public int add(errorLog errorLog);
}
