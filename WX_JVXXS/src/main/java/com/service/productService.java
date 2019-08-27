package com.service;

import com.entity.product;
import com.entity.productImgs;
import com.entity.productVideo;

import java.util.List;
import java.util.Map;

public interface productService {


    public List<product> selectByFids(List<String> fids);


    public List<product> selectByQueryPage(int currPage, int pageSize, int type);

    public List<product> selectByQueryPageForWx(int currPage, int pageSize, String type);

    public List<product> selectByNoneForWxTeJia(int currPage, int pageSize);

    public List<product> selectByKeyAndType(int currPage, int pageSize, int type, String key, int issall, int ishot);

    public List<product> selectByKeyAndTypeTejia(int currPage, int pageSize, String key, int issall);

    public int selectCountByKeyAndType(int type, String key, int issall, int ishot);
public int selectCountByKeyAndTypeTejia(String key,int issall);
    public int selectCount(String ftype);

    public int selectCountTeJia();


    public List<product> selectByKey(int currPage, int pageSize, String key);

    public int selectCountByKey(String key);

    public product selectByFid(int fproductid);


    public int updateByFid(product p);


    public int updateByFidForBack(product product);


    public void add(product product);


    public int updateSalled(List<String> ids, int issall);

    public int updatehot(List<String> ids, int fishot);

    public int updateSort(List<String> ids, int srot);

    public int updateTop(List<String> ids, int top);

    public int updateType(List<String> ids, int type);

    public List<product> selectHotProduct();

    public List<product> selectTejiaByNone();


    public int deleteImagesByFid(int fid);

    public void insertImages(productImgs productImgs);

    public int deleteVideoByFid(int fid);

    public void insertVideo(productVideo productVideo);
}
