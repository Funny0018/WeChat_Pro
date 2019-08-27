package com.service;

import com.entity.backuser;

public interface backUserService {
    public backuser selectByNameAndPsd(backuser backuser);
    public int updateBakcUser(backuser backuser);
}
