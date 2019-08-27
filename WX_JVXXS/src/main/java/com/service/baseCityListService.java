package com.service;

import com.entity.baseCityList;

import java.util.List;

public interface baseCityListService {
    public List<baseCityList> selectByFName(String fname);
}
