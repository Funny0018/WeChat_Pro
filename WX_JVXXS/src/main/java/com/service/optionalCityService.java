package com.service;

import com.entity.optionalCity;

import java.util.List;

public interface optionalCityService {
    public List<optionalCity> selectByNone();
    public int deleteIds(List<String> fids);
    public int update(optionalCity optionalCity);
}
