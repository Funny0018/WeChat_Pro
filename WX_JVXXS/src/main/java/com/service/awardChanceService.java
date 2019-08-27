package com.service;

import com.entity.awardChance;

import java.util.List;

public interface awardChanceService {
    public List<awardChance> selectByNone();
    public int update(awardChance a);
}
