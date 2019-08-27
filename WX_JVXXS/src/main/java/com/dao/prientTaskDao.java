package com.dao;

import com.entity.prientTask;
import org.springframework.stereotype.Repository;

@Repository("prientTaskDao")
public interface prientTaskDao {
    public void add(prientTask prientTask);
}
