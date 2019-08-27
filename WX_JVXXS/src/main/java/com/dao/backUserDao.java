package com.dao;

import com.entity.backuser;
import org.springframework.stereotype.Repository;

@Repository("backUserDao")
public interface backUserDao {
    public backuser selectByNameAndPsd(backuser backuser);

    public int updateBakcUser(backuser backuser);
}
