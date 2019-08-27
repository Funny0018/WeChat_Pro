package com.dao;

import com.entity.optionalCity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("optionalCityDao")
public interface optionalCityDao {
    public List<optionalCity> selectByNone();
    public int deleteIds(Map<String, Object> map);
    public int update(optionalCity optionalCity);
    public void add(optionalCity optionalCity);
}
