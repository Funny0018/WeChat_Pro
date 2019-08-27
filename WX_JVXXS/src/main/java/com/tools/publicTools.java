package com.tools;

import com.entity.bill;
import com.entity.billProduct;
import com.entity.user;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class publicTools {


    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCouponTypeName(int typeID) {
        String type = "";
        switch (typeID) {
            case 1:
                type = "金额抵扣";
                break;
            case 2:
                type = "商品抵扣";
                break;
        }
        return type;
    }

    public static String getStateName(int fstate) {
        String statename = "";
        switch (fstate) {
            case 0:
                statename = "待付款";
                break;
            case 1:
                statename = "待发货";
                break;
            case 2:
                statename = "待收货";
                break;
            case 3:
                statename = "已完成";
                break;
            default:
                statename = "已取消";
                break;
        }
        return statename;
    }

    public static float toFloat(String info) {
        try {
            return Float.parseFloat(info.replace("￥", ""));
        } catch (Exception e) {
            return (float) 0.00;
        }
    }

    public static String getPrientBill(bill bill, boolean isvip) {
        String jsonContent = "[";
        jsonContent += "{\"Alignment\":1,\"BaseText\":\"" + printUnit.StringToBase64("西鲜生订单", "GBK") + "\",\"Bold\":1,\"FontSize\":1,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("单据状态：" + getStateName(bill.getFstate()), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("单据编号：" + bill.getFbillNo(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("下单时间：" + bill.getFbilldate(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("付款时间：" + bill.getFpaydate(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("------------商品信息------------", "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";

        List<billProduct> billProductList = bill.getBillProducts();
        for (billProduct b : billProductList) {
            jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64(b.getfName(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
            if (isvip) {
                jsonContent += ",{\"Alignment\":2,\"BaseText\":\"" + printUnit.StringToBase64("X" + b.getFcount() + "    " + b.getFvipprice(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
            } else {
                jsonContent += ",{\"Alignment\":2,\"BaseText\":\"" + printUnit.StringToBase64("X" + b.getFcount() + "    " + b.getfPrice(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
            }
        }
        if (bill.getFyunfei() > 0) {
            jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("运费", "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
            jsonContent += ",{\"Alignment\":2,\"BaseText\":\"" + printUnit.StringToBase64("X1    " + bill.getFyunfei(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        }

        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("--------------------------------", "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":2,\"BaseText\":\"" + printUnit.StringToBase64("总计：￥" + bill.getFfinalAmount(), "GBK") + "\",\"Bold\":1,\"FontSize\":1,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("--------------------------------", "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("收货姓名：" + bill.getBillDeliveryAddress().getfName(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("收货地址：" + bill.getBillDeliveryAddress().getfCity() + "  " + bill.getBillDeliveryAddress().getfAddress() + "  " + bill.getBillDeliveryAddress().getfBuildNo(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";
        jsonContent += ",{\"Alignment\":0,\"BaseText\":\"" + printUnit.StringToBase64("联系电话：" + bill.getBillDeliveryAddress().getfMobile(), "GBK") + "\",\"Bold\":0,\"FontSize\":0,\"PrintType\":0}";

        jsonContent += "]";
        return jsonContent;
    }

    public static void writeInText(String fileName, String info) {

        File file = new File("D:\\logs\\" + fileName + "\\" + df.format(new Date()) + fileName + ".txt");
        try {
            // 如果文件不存在则创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file, true);
            outputStream.write((info + "\r\n").getBytes());
            outputStream.close();
        } catch (IOException e) {

        }
    }

    public static String printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        return elapsedHours + "," + elapsedMinutes + "," + elapsedSeconds;

    }

}
