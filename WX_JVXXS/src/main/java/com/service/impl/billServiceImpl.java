package com.service.impl;

import com.entity.*;
import com.service.billService;
import com.service.userService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("billService")
public class billServiceImpl implements billService {

    @Resource(name = "billDao")
    private com.dao.billDao billDao;

    @Resource(name = "deliveryAddressDao")
    private com.dao.deliveryAddressDao deliveryAddressDao;
    @Resource(name = "billDeliveryAddressDao")
    private com.dao.billDeliveryAddressDao billDeliveryAddressDao;

    @Resource(name = "productDao")
    private com.dao.productDao productDao;
    @Resource(name = "billProductDao")
    private com.dao.billProductDao billProductDao;


    @Resource(name = "userDao")
    private com.dao.userDao userDao;

    public String add(bill bill) {
        boolean result = true;
        Date day = new Date();

        SimpleDateFormat df = new SimpleDateFormat("YYMMdd");
        String billno = billDao.selectMaxBillno("%XXS" + df.format(day) + "%");
        if (billno == null) {
            billno = "XXS" + df.format(day) + "0001";
        } else {
            billno = "XXS" + df.format(day) + String.format("%04d", Integer.parseInt(billno.replace("XXS" + df.format(day), "")) + 1);
        }
        bill.setFbillNo(billno);
        deliveryAddress deliveryAddress = deliveryAddressDao.selectByFid(bill.getBillDeliveryAddress().getfDeliveryAddressid());
        billDeliveryAddress billDeliveryAddress = new billDeliveryAddress(deliveryAddress);
        bill.setBillDeliveryAddress(billDeliveryAddress);
        List<product> productList = new ArrayList<product>();
        List<String> productIDList = new ArrayList<String>();
        for (billProduct p : bill.getBillProducts()) {
            productIDList.add(String.valueOf(p.getFproductID()));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fids", productIDList);
        productList = productDao.selectByFids(map);
        List<billProduct> billProductList = new ArrayList<billProduct>();
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (productList.get(i).getfProductId() == bill.getBillProducts().get(j).getFproductID()) {
                    billProduct b = new billProduct(productList.get(i), bill.getBillProducts().get(j).getFcount());
                    billProductList.add(b);
                }
            }
        }
        bill.setBillProducts(billProductList);

        user u = userDao.selectByFuserid(String.valueOf(bill.getFuserID()));
        float price = 0;
        for (billProduct b : billProductList) {
            if (u.getFisvip() > 0) {
                price += b.getFvipprice() * b.getFcount();
            } else {
                price += b.getfPrice() * b.getFcount();
            }
        }
        price+= bill.getFyunfei()-bill.getFdikouAmount()-bill.getFhongbaoAmount()-bill.getFvipamount();
        bill.setFfinalAmount(price);
        try {
            billDao.add(bill);
            if (bill.getFbillid() > 0) {
                bill.getBillDeliveryAddress().setFbillid(bill.getFbillid());

                billDeliveryAddressDao.add(bill.getBillDeliveryAddress());
                for (billProduct bp : bill.getBillProducts()) {
                    bp.setfBillID(bill.getFbillid());

                }

                billProductDao.add(bill.getBillProducts());
            }
            for (billProduct bp : bill.getBillProducts()) {
                if (bp.getfBillProductID() <= 0) {
                    result = false;
                }
            }
            if (bill.getBillDeliveryAddress().getFbillDeliveryAddressid() <= 0) {
                result = false;
            }
            if (result) {
                return bill.getFbillNo();
            } else {
                return "保存失败";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public bill selectByFbillno(String fbillno) {
        bill bill = billDao.selectByFbillno(fbillno);
        billDeliveryAddress billDeliveryAddress = billDeliveryAddressDao.selectByFbillid(bill.getFbillid());
        List<billProduct> billProductList = billProductDao.selectByFbillid(bill.getFbillid());
        bill.setBillDeliveryAddress(billDeliveryAddress);
        bill.setBillProducts(billProductList);
        return bill;
    }

    public bill selectByBillSimple(String fbillno) {
        return billDao.selectByFbillno(fbillno);
    }

    public int updateState(bill bill) {
        return billDao.updateState(bill);
    }


    public List<bill> selectByQueryPage(int currPage, int pageSize, int fstate, int userid) {
        List<bill> billList = new ArrayList<bill>();
        if (fstate == -1) {
            Map<String, Object> map = new HashedMap();
            map.put("currIndex", (currPage - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("userid", userid);
            billList = billDao.selectByNone(map);
        } else if (fstate >= 0) {
            Map<String, Object> map = new HashedMap();
            map.put("currIndex", (currPage - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("fstate", fstate);
            map.put("userid", userid);
            billList = billDao.selectByType(map);
        }
        List<billProduct> products = new ArrayList<billProduct>();
        for (bill b : billList) {
            products = new ArrayList<billProduct>();
            products = billProductDao.selectByFbillid(b.getFbillid());
            b.setBillProducts(products);
        }
        return billList;
    }

    public List<bill> selectByQueryPageForKey(int currPage, int pageSize, int fstate, String key) {
        List<bill> billList = new ArrayList<bill>();
        if (fstate == -1) {
            Map<String, Object> map = new HashedMap();
            map.put("currIndex", (currPage - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("key", key);
            billList = billDao.selectByNone(map);
        } else if (fstate >= 0) {
            Map<String, Object> map = new HashedMap();
            map.put("currIndex", (currPage - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("fstate", fstate);
            map.put("key", key);
            billList = billDao.selectByType(map);
        }
        List<billProduct> products = new ArrayList<billProduct>();
        for (bill b : billList) {
            products = new ArrayList<billProduct>();
            products = billProductDao.selectByFbillid(b.getFbillid());
            b.setBillProducts(products);
        }
        return billList;
    }

    public int selectCount(int fstate, int userid, String key) {
        if (fstate == -1) {
            return billDao.selectCountByNone(userid, key);
        } else {
            return billDao.selectCountByType(fstate, userid, key);
        }
    }

    public int selectVipPriceForUser(int fuserid) {
        return billDao.selectVipPriceForUser(fuserid);
    }
    //后台

    public int deleteByIds(String ids) {
        List<String> idlist = new ArrayList<String>();
        for (String v : ids.split("\\|")) {
            idlist.add(v);
        }
        Map<String, Object> map = new HashedMap();
        map.put("ids", idlist);
        return billDao.deleteByIds(map);
    }
}
