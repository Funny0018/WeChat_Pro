package com.dao;

import com.entity.user;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userDao")
public interface userDao {
    public user selectByFopenid(String fopenid);

    public void insertIntoUser(user user);

    public user selectByFuserid(String fuserid);

    public int updateUserInfo(user user);

    public List<user> selectBack(Map map);

    public int selectCountBack(int ftype);

}
