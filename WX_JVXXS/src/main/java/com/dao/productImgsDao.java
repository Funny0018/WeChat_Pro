package com.dao;

import com.entity.productImgs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productImgsDao")
public interface productImgsDao {
    public List<productImgs> selectByProductID(@Param("fproductid") int fproductid);

    public int deleteImagesByFid(@Param("fid") int fid);

    public void insertImages(productImgs productImgs);
}
