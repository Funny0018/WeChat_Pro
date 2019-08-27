package com.service.impl;

import com.dao.productDao;
import com.dao.productImgsDao;
import com.dao.producttagDao;
import com.dao.productVideoDao;
import com.entity.product;
import com.entity.productImgs;
import com.entity.productVideo;
import com.service.productService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productService")
public class productServiceImpl implements productService {

    @Resource(name = "productDao")
    private productDao productDao;
    @Resource(name = "productImgsDao")
    private productImgsDao productImgsDao;
    @Resource(name = "productVideoDao")
    private productVideoDao productVideoDao;
    @Resource(name = "producttagDao")
    private producttagDao producttagDao;

    public List<product> selectByKeyAndType(int currPage, int pageSize, int type, String key, int issall, int ishot) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("issall", issall);
        map.put("ftype", type);
        map.put("key", key);
        map.put("ishot", ishot);
        return productDao.selectByKeyAndType(map);

    }

    public List<product> selectByKeyAndTypeTejia(int currPage, int pageSize,  String key, int issall) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("issall", issall);
        map.put("key", key);
        return productDao.selectByKeyAndTypeTejia(map);

    }

    public int selectCountByKeyAndType(int type, String key, int issall, int ishot) {
        Map<String, Object> map = new HashedMap();
        map.put("issall", issall);
        map.put("ftype", type);
        map.put("key", key);
        map.put("ishot", ishot);
        return productDao.selectCountByKeyAndType(map);

    }
    public int selectCountByKeyAndTypeTejia( String key, int issall) {
        Map<String, Object> map = new HashedMap();
        map.put("issall", issall);
        map.put("key", key);
        return productDao.selectCountByKeyAndTypeTejia(map);

    }

    public List<product> selectByKey(int currPage, int pageSize, String key) {
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        if (key != "") {
            map.put("key", key);
            return productDao.selectByKey(map);
        } else {
            return productDao.selectByNone(map);
        }
    }

    public int selectCountByKey(String key) {
        if (key != "") {
            return productDao.selectCountByKey(key);
        } else {
            return productDao.selectCountByNone();
        }
    }

    public List<product> selectByQueryPage(int currPage, int pageSize, int type) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        if (type == 0) {
            return productDao.selectByNone(map);
        } else if (type > 0) {
            map.put("ftype", type);
            return productDao.selectByType(map);
        } else {
            return new ArrayList<product>();
        }
    }

    public List<product> selectByQueryPageForWx(int currPage, int pageSize, String type) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        if (type.equals("0")) {
            return productDao.selectByNoneForWx(map);
        } else {
            List<Integer> list = new ArrayList<Integer>();
            for (String s : type.split(",")) {
                list.add(Integer.valueOf(s));
            }
            map.put("ftype", list);
            return productDao.selectByTypeForWx(map);
        }
    }

    public List<product> selectByNoneForWxTeJia(int currPage, int pageSize) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return productDao.selectByNoneForWxTeJia(map);


    }

    public List<product> selectByFids(List<String> fids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fids", fids);
        return productDao.selectByFids(map);
    }


    public int selectCount(String ftype) {
        if (ftype.equals("0")) {
            return productDao.selectCountByNone();
        } else {
            Map<String, Object> map = new HashedMap();
            List<Integer> list = new ArrayList<Integer>();
            for (String s : ftype.split(",")) {
                list.add(Integer.valueOf(s));
            }
            map.put("ftype", list);
            return productDao.selectCountByType(map);
    }
    }

    public int selectCountTeJia(){
        return productDao.selectCountByNoneTeJia();
    }
    public product selectByFid(int fid) {
        product p = productDao.selectByFid(fid);
        if (p != null) {
            List<productImgs> productImgsList = productImgsDao.selectByProductID(p.getfProductId());

            if (productImgsList == null || productImgsList.size() == 0) {
                productImgsList.add(new productImgs(0, 0, "https://xxs.xixiansheng.cn/WX_JVXXS/wxStatic/images/noImgs.png", 1, ""));
                p.setFproductImgs(productImgsList);
            } else if (productImgsList.size() < 5) {
                p.setFproductImgs(productImgsList);
            } else {
                List<productImgs> newproductImgsList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    newproductImgsList.add(productImgsList.get(i));
                }
                p.setFproductImgs(newproductImgsList);
            }
            List<productVideo> productVideoList = productVideoDao.selectByProductID(p.getfProductId());
            if (productVideoList == null || productVideoList.size() == 0) {
                productVideoList.add(new productVideo(0, 0, "0", 1, ""));
                p.setFproductVideo(productVideoList);
            } else if (productVideoList.size() < 5) {
                p.setFproductVideo(productVideoList);
            } else {
                List<productVideo> newproductVideoList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    newproductVideoList.add(productVideoList.get(i));
                }
                p.setFproductVideo(newproductVideoList);
            }
        }
        return p;
    }

    public int updateByFid(product p) {
        return productDao.updateByFid(p);
    }


    public void add(product product) {
        productDao.add(product);
    }

    public int updateByFidForBack(product product) {
        return productDao.updateByFidForBack(product);
    }

    public int updateSalled(List<String> ids, int issall) {
        Map<String, Object> map = new HashMap<>();
        map.put("fids", ids);
        map.put("issall", issall);
        return productDao.updateSalled(map);
    }

    public int updatehot(List<String> ids, int fishot) {
        Map<String, Object> map = new HashMap<>();
        map.put("fids", ids);
        map.put("fishot", fishot);
        return productDao.updatehot(map);
    }

    public int updateSort(List<String> ids, int srot) {
        Map<String, Object> map = new HashMap<>();
        map.put("fids", ids);
        map.put("fsrot", srot);
        return productDao.updateSort(map);
    }

    public int updateType(List<String> ids, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("fids", ids);
        map.put("ftype", type);
        return productDao.updateType(map);

    }

    public int updateTop(List<String> ids, int top) {
        Map<String, Object> map = new HashMap<>();
        map.put("fids", ids);
        map.put("fistop", top);
        return productDao.updateTop(map);

    }

    public List<product> selectHotProduct() {
        return productDao.selectHotProduct();
    }


    public int deleteVideoByFid(int fid) {
        return productVideoDao.deleteVideoByFid(fid);
    }

    public void insertVideo(productVideo productVideo) {
        productVideoDao.insertVideo(productVideo);
    }


    public int deleteImagesByFid(int fid) {
        return productImgsDao.deleteImagesByFid(fid);
    }

    public void insertImages(productImgs productImgs) {
        productImgsDao.insertImages(productImgs);
    }


    public List<product> selectTejiaByNone() {
        return productDao.selectTejiaByNone();
    }
}
