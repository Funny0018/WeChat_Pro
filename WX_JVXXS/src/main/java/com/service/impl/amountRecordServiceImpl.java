package com.service.impl;

import com.dao.amountRecordDao;
import com.dao.billDao;
import com.dao.userDao;
import com.dao.vipBillDao;
import com.dao.vipTypeDao;
import com.dao.rechargeTypeDao;
import com.dao.billProductDao;
import com.dao.productDao;
import com.entity.*;
import com.service.amountRecordService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("amountRecordService")
public class amountRecordServiceImpl implements amountRecordService {
    @Resource(name = "amountRecordDao")
    private amountRecordDao amountRecordDao;
    @Resource(name = "billDao")
    private billDao billDao;
    @Resource(name = "userDao")
    private userDao userDao;
    @Resource(name = "vipBillDao")
    private vipBillDao vipBillDao;
    @Resource(name = "vipTypeDao")
    private vipTypeDao vipTypeDao;
    @Resource(name = "rechargeTypeDao")
    private rechargeTypeDao rechargeTypeDao;
    @Resource(name = "billProductDao")
    private billProductDao billProductDao;
    @Resource(name = "productDao")
    private productDao productDao;

    @Override
    public void add(amountRecord amountRecord) {
        amountRecord a = amountRecordDao.selectByfwxno(amountRecord.getFwxno());
        if (a == null) {
            amountRecordDao.add(amountRecord);
        } else {
            amountRecord.setFid(1);
        }
    }

    //    @Override
    public int payOffSale(bill b, String billno, SimpleDateFormat df, Date day, String wxno, String attach, String wxAmount) {

        int vip = -1;
        int wx = -1;
        int v = billDao.updateState(b);
        if (b.getFstate() == 1) {
            List<billProduct> billProductList = billProductDao.selectByFbillid(b.getFbillid());
            List<String> fidList = new ArrayList<String>();
            for (billProduct bp : billProductList) {
                fidList.add(String.valueOf(bp.getFproductID()));
                product p = productDao.selectByFid(bp.getFproductID());
                p.setFsalled(p.getFsalled() + (int) bp.getFcount());
                productDao.updateByFid(p);
            }
        }
        if (b.getFvipamount() > 0) {
            vip = 0;
            user user = userDao.selectByFuserid(String.valueOf(b.getFuserID()));
            user.setFmoney(user.getFmoney() - b.getFvipamount());
            int userresult = userDao.updateUserInfo(user);
            amountRecord amountRecord = new amountRecord(0, df.format(day), b.getFuserID(), b.getFvipamount(), 4, billno, null, attach, "", "会员抵扣", b.getFvipamount());
            amountRecordDao.add(amountRecord);
            if (userresult > 0 && amountRecord.getFid() > 0) {
                vip = 1;
            }
        }
        if (b.getFfinalAmount() > 0) {
            wx = 0;
            amountRecord amountRecord = new amountRecord(0, df.format(day), b.getFuserID(), b.getFfinalAmount(), 1, billno, wxno, attach, "", "零售单", Double.parseDouble(wxAmount) / 100);
            amountRecordDao.add(amountRecord);
            //增加积分
            user user = userDao.selectByFuserid(String.valueOf(b.getFuserID()));
            user.setFjifen(user.getFjifen() + b.getFfinalAmount() * 10);
            userDao.updateUserInfo(user);


            if (amountRecord.getFid() > 0) {
                wx = 1;
            }
        }
        if ((v > 0) && (wx != 0) && (vip != 0)) {
            return 1;
        } else {
            return 0;
        }
    }

    public int payOffJoinVip(vipBill vipBill, String billno, SimpleDateFormat df, Date day, String wxno, String attach, String wxAmount) {
//        int v = vipBillDao.updateState(vipBill);

        int updateVipBill = vipBillDao.updateState(vipBill);
        //////// 更新用户会员状态和到期时间
        vipType vipType = vipTypeDao.selectByFid(vipBill.getFviptype());
        String time = vipType.getFtime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        user user = userDao.selectByFuserid(String.valueOf(vipBill.getFuserid()));
        user.setFisvip(1);
        if ((user.getFvipenddate() == "1900-01-01 00:00:00") || (((new Date()).after(sdf.parse(user.getFvipenddate(), new ParsePosition(0)))))) {
            Date date = new Date();
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.YEAR, Integer.parseInt(time.split(",")[0]));//要增加什么，在这里写
            nowTime.add(Calendar.MONTH, Integer.parseInt(time.split(",")[1]));//要增加什么，在这里写
            nowTime.add(Calendar.DATE, Integer.parseInt(time.split(",")[2]));//要增加什么，在这里写
            user.setFvipstartdate(sdf.format(date));
            user.setFvipenddate(sdf.format(nowTime.getTime()));
        } else {
            String str = user.getFvipenddate();
            Date date = sdf.parse(str, new ParsePosition(0));
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(date);
            nowTime.add(Calendar.YEAR, Integer.parseInt(time.split(",")[0]));//要增加什么，在这里写
            nowTime.add(Calendar.MONTH, Integer.parseInt(time.split(",")[1]));//要增加什么，在这里写
            nowTime.add(Calendar.DATE, Integer.parseInt(time.split(",")[2]));//要增加什么，在这里写
//            user.setFvipstartdate(sdf.format(date));
            user.setFvipenddate(sdf.format(nowTime.getTime()));
        }
        int updateUser = userDao.updateUserInfo(user);

        //////
        amountRecord amountRecord = new amountRecord(0, df.format(day), vipBill.getFuserid(), vipBill.getFamount(), 2, billno, wxno, attach, "", "会员办理", Double.parseDouble(wxAmount) / 100);
        amountRecordDao.add(amountRecord);
        if (updateVipBill > 0 && amountRecord.getFid() > 0 && updateUser > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int payOffRecharge(vipBill vipBill, String billno, SimpleDateFormat df, Date day, String wxno, String attach, String wxAmount) {
        int v = vipBillDao.updateState(vipBill);
        rechargeType rechargeType = new rechargeType();
        if (vipBill.getFrechargetype() != 0) {
            rechargeType = rechargeTypeDao.selectByFid(vipBill.getFrechargetype());
        } else {
            rechargeType.setFprice(vipBill.getFamount());
            rechargeType.setFamount(vipBill.getFamount());
            rechargeType.setFviptime("0,0,0");
        }
        String time = rechargeType.getFviptime();
        user user = userDao.selectByFuserid(String.valueOf(vipBill.getFuserid()));
        user.setFmoney(user.getFmoney() + rechargeType.getFamount());
        //会员积分
        user.setFjifen(user.getFjifen() + rechargeType.getFamount() * 10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ((user.getFvipenddate() == "1900-01-01 00:00:00") || (((new Date()).after(sdf.parse(user.getFvipenddate(), new ParsePosition(0)))))) {
            Date date = new Date();
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.YEAR, Integer.parseInt(time.split(",")[0]));//要增加什么，在这里写
            nowTime.add(Calendar.MONTH, Integer.parseInt(time.split(",")[1]));//要增加什么，在这里写
            nowTime.add(Calendar.DATE, Integer.parseInt(time.split(",")[2]));//要增加什么，在这里写
            user.setFvipstartdate(sdf.format(date));
            user.setFvipenddate(sdf.format(nowTime.getTime()));
        } else {
            String str = user.getFvipenddate();
            Date date = sdf.parse(str, new ParsePosition(0));
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(date);
            nowTime.add(Calendar.YEAR, Integer.parseInt(time.split(",")[0]));//要增加什么，在这里写
            nowTime.add(Calendar.MONTH, Integer.parseInt(time.split(",")[1]));//要增加什么，在这里写
            nowTime.add(Calendar.DATE, Integer.parseInt(time.split(",")[2]));//要增加什么，在这里写
            user.setFvipenddate(sdf.format(nowTime.getTime()));
        }
        int updateUser = userDao.updateUserInfo(user);
        amountRecord amountRecord = new amountRecord(0, df.format(day), vipBill.getFuserid(), vipBill.getFamount(), 3, billno, wxno, attach, "", "会员充值", Double.parseDouble(wxAmount) / 100);
        amountRecordDao.add(amountRecord);
        if (v > 0 && amountRecord.getFid() > 0 && updateUser > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public List<amountRecord> selectByUserAndType(int currPage, int pageSize, List<Integer> type, int userid) {
        if (currPage <= 0) {
            currPage = 1;
        }
        Map<String, Object> map = new HashedMap();
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("fstate", type);
        map.put("userid", userid);
        return amountRecordDao.selectByUserAndType(map);
    }

    public int selectCountByUserAndType(List<Integer> type, int userid) {

        Map<String, Object> map = new HashedMap();
        map.put("fstate", type);
        map.put("userid", userid);
        return amountRecordDao.selectCountByUserAndType(map);
    }
}

