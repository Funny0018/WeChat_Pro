package com.service.impl;

import com.dao.vipBillDao;
import com.dao.vipTypeDao;
import com.entity.user;
import com.entity.vipBill;
import com.entity.vipType;
import com.service.vipBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service("vipBillService")

public class vipBillServiceImpl implements vipBillService {
    @Resource(name = "vipBillDao")
    private vipBillDao vipBillDao;
    @Resource(name = "vipTypeDao")
    private vipTypeDao vipTypeDao;
    @Resource(name = "userDao")
    private com.dao.userDao userDao;


    public void insertInto(vipBill vipBill) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("YYMMdd");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billno = vipBillDao.selectMaxBillno("%XVC" + df.format(day) + "%");
        if (billno == null) {
            billno = "XVC" + df.format(day) + "0001";
        } else {
            billno = "XVC" + df.format(day) + String.format("%04d", Integer.parseInt(billno.replace("XVC" + df.format(day), "")) + 1);
        }
        vipBill.setFbillno(billno);
        vipBill.setFdate(df1.format(day));
        vipBillDao.insertInto(vipBill);
    }

    public vipBill selectByBillno(String billno) {
        return vipBillDao.selectByBillno(billno);
    }

    public int selectVipIsTehui(int userid) {
        return vipBillDao.selectVipIsTehui(userid);
    }
//    public int updateState(vipBill vipBill) {
//        int updateVipBill = vipBillDao.updateState(vipBill);
//        vipType vipType = vipTypeDao.selectByFid(vipBill.getFviptype());
//        String time = vipType.getFtime();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        user user = userDao.selectByFuserid(String.valueOf(vipBill.getFuserid()));
//        user.setFisvip(1);
//        if ((user.getFvipenddate() == "1900-01-01 00:00:00") || (((new Date()).after(sdf.parse(user.getFvipenddate(), new ParsePosition(0)))))) {
//            Date date = new Date();
//            Calendar nowTime = Calendar.getInstance();
//            nowTime.add(Calendar.YEAR, Integer.parseInt(time.split(",")[0]));//要增加什么，在这里写
//            nowTime.add(Calendar.MONTH, Integer.parseInt(time.split(",")[1]));//要增加什么，在这里写
//            nowTime.add(Calendar.DATE, Integer.parseInt(time.split(",")[2]));//要增加什么，在这里写
//            user.setFvipstartdate(sdf.format(date));
//            user.setFvipenddate(sdf.format(nowTime.getTime()));
//        } else {
//            String str = user.getFvipenddate();
//            Date date = sdf.parse(str, new ParsePosition(0));
//            Calendar nowTime = Calendar.getInstance();
//            nowTime.setTime(date);
//            nowTime.add(Calendar.YEAR, Integer.parseInt(time.split(",")[0]));//要增加什么，在这里写
//            nowTime.add(Calendar.MONTH, Integer.parseInt(time.split(",")[1]));//要增加什么，在这里写
//            nowTime.add(Calendar.DATE, Integer.parseInt(time.split(",")[2]));//要增加什么，在这里写
////            user.setFvipstartdate(sdf.format(date));
//            user.setFvipenddate(sdf.format(nowTime.getTime()));
//        }
//        int updateUser = userDao.updateUserInfo(user);
//        if (updateVipBill > 0 && updateUser > 0) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }
}
