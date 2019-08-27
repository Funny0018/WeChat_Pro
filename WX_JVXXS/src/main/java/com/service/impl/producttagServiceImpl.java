package com.service.impl;

import com.entity.producttag;
import com.service.producttagService;
import com.dao.producttagDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("producttagService")
public class producttagServiceImpl implements producttagService {
    @Resource(name = "producttagDao")
    private producttagDao producttagDao;

    public List<producttag> selectByFids(String ids) {
        List<String> idlist = new ArrayList<String>();
        for (String i : ids.split(",")) {
            idlist.add(i);
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fids", idlist);

        List<producttag> producttagList = producttagDao.selectByFids(map);


        if (producttagList == null || producttagList.size() == 0) {
            List<producttag> retList = new ArrayList<>();
            retList.add(new producttag(0, "", 0));
            return retList;
        } else {
            return producttagList;
        }
    }

    public List<producttag> selectByNone() {
        return producttagDao.selectByNone();
    }

    public producttag selectById(int fid) {
        return producttagDao.selectById(fid);
    }

    public void add(producttag producttag) {
        producttagDao.add(producttag);
    }

    public int deleteById(int fid) {
        producttag pt = producttagDao.selectById(fid);
        pt.setFstate(-1);
        return producttagDao.updateById(pt);
    }

    public int updateFname(String fname, int fid) {
        producttag pt = producttagDao.selectById(fid);
        pt.setFname(fname);
        return producttagDao.updateById(pt);
    }
}
