package com.service.impl;


import com.dao.producttypeDao;
import com.entity.producttype;
import com.service.productTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productTypeService")
public class productTypeServiceImpl implements productTypeService {
    @Resource(name = "producttypeDao")
    private producttypeDao producttypeDao;

    public List<producttype> selectByNone() {
        return producttypeDao.selectByNone();
    }

    public List<producttype> selectByNoneAll(){
        return producttypeDao.selectByNoneAll();
    }

    public producttype selectById(int fid) {
        return producttypeDao.selectById(fid);
    }

    public  List<producttype> selectByParentId(int fid) {
        return producttypeDao.selectByParentId(fid);
    }

    public int deleteById(int fid) {
        producttype pt = producttypeDao.selectById(fid);
        pt.setFstate(-1);
        return producttypeDao.updateById(pt);
    }

    public int updateFname(String fname, int fid,int fparentid) {
        producttype pt = producttypeDao.selectById(fid);
        pt.setFname(fname);
        pt.setFparentid(fparentid);
        return producttypeDao.updateById(pt);
    }

    public void add(producttype producttype) {
        producttypeDao.add(producttype);
    }

}
