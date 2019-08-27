package com.dao;

import com.entity.prientdevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface prientdeviceDao {
    public int add(prientdevice prientdevice);

    public List<prientdevice> selectByNone();

    public prientdevice selectByFID(@Param("fid") String fid);

    public int deleteIds(Map<String, Object> map);

    public int update(prientdevice prientdevice);
}
