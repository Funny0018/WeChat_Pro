package com.dao;

import com.entity.shareLeader;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("shareLeaderDao")
public interface shareLeaderDao {
    public int add(shareLeader shareLeader);

    public shareLeader selectByFid(@Param("fid") int fid);

    public int update(shareLeader shareLeader);

    public shareLeader selectByFuserid(Map<String, Object> map);

    public int selectCountByFuserid(Map<String, Object> map);

    public List<String> selectTop();
    public List<String> selectTop100();
    public String selectCountByUser(@Param("fuserid")String fuserid);
}
