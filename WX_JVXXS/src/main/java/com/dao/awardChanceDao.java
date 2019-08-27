package com.dao;

import com.entity.awardChance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("awardChanceDao")
public interface awardChanceDao {
    public List<awardChance> selectByNone();

    public int update(awardChance a);
}
