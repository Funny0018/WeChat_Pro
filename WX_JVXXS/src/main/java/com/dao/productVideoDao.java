package com.dao;

import com.entity.productVideo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("productVideoDao")
public interface productVideoDao {
    public List<productVideo> selectByProductID(@Param("fproductid") int fproductid);

    public int deleteVideoByFid(@Param("fid") int fid);

    public void insertVideo(productVideo productVideo);
}
