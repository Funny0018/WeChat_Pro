package com.service.impl;

import com.entity.shareLeader;
import com.service.shareLeaderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("shareLeaderService")
public class shareLeaderServiceImpl implements shareLeaderService {

    @Resource(name = "shareLeaderDao")
    private com.dao.shareLeaderDao shareLeaderDao;

    public int add(shareLeader shareLeader) {
        return shareLeaderDao.add(shareLeader);
    }


    public shareLeader selectByFid(int fid) {
        return shareLeaderDao.selectByFid(fid);
    }

    public int update(shareLeader shareLeader) {
        return shareLeaderDao.update(shareLeader);
    }


    public shareLeader selectByFuserid(int fuserid, String fjoinuserid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fuserid", fuserid);
        map.put("fjoinuserid", "%" + fjoinuserid + "%");


        return shareLeaderDao.selectByFuserid(map);
    }


    public int selectCountByFuserid(int fuserid, String time) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fuserid", fuserid);
        map.put("time", time);
        return shareLeaderDao.selectCountByFuserid(map);
    }


    public List<String> selectTop(){
        return shareLeaderDao.selectTop();
    }
    public List<String> selectTop100(){
        return shareLeaderDao.selectTop100();
    }

    public String selectCountByUser(String fuserid){
        return shareLeaderDao.selectCountByUser(fuserid);
    }
}
