package com.service;

import com.entity.shareLeader;

import java.util.List;

public interface shareLeaderService {
    public int add(shareLeader shareLeader);

    public shareLeader selectByFid(int fid);

    public int update(shareLeader shareLeader);

    public shareLeader selectByFuserid(int fuserid, String fjoinuserid);

    public int selectCountByFuserid(int fuserid, String time);

    public List<String> selectTop();

    public List<String> selectTop100();

    public String selectCountByUser(String fuserd);
}
