package com.service;

import com.entity.user;

import java.util.List;
import java.util.Map;

public interface userService {

    public user selectByFopenid(String fopenid);

    public user selectByFopenidForHave(String fopenid);

    public void insertIntoUser(user user);

    public user selectByFuserid(String fuserid);

    public int updateUserInfo(user user);

    public List<user> selectBack(int id, int currPage, int pageSize, int type);

    public int selectCountBack(int type);
}
