package com.service;

import com.entity.prientdevice;

import java.util.List;

public interface prientdeviceService {
    public List<prientdevice> selectByNone();

    public prientdevice selectByFID(String fid);

    public int deleteIds(List<String> fids);

    public int update(prientdevice prientdevice);

    public int add(prientdevice prientdevice);
}
