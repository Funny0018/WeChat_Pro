package com.service.impl;

import com.dao.userDao;
import com.entity.user;
import com.service.userService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("userService")
public class userServiceImpl implements userService {
    @Resource(name = "userDao")
    private com.dao.userDao userDao;
    public user selectByFopenidForHave(String fopenid){
        return userDao.selectByFopenid(fopenid);
    }
    public user selectByFopenid(String fopenid) {
        user user = userDao.selectByFopenid(fopenid);
        if (user == null) {
            user = new user();
            user.setFopenid(fopenid);
            user.setFunionid("");
            user.setFmoney(0.00);
            userDao.insertIntoUser(user);
            return user;
        } else {
            return user;
        }
    }

    public void insertIntoUser(user user) {
        userDao.insertIntoUser(user);
    }

    public user selectByFuserid(String fuserid) {
        return userDao.selectByFuserid(fuserid);
    }

    public int updateUserInfo(user user) {
        return userDao.updateUserInfo(user);
    }


    public List<user> selectBack(int userid, int currPage, int pageSize, int type) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("ftype", type);
        map.put("userid", userid);
        return userDao.selectBack(map);

    }


    public int selectCountBack(int ftype) {
        return userDao.selectCountBack(ftype);
    }
}
