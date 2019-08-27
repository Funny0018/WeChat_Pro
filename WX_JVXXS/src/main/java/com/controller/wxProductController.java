package com.controller;

import com.entity.*;
import com.service.productService;
import com.service.productTypeService;
import com.tools.publicTools;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class wxProductController {
    @Resource(name = "productService")
    private com.service.productService productService;
    @Resource(name = "producttagService")
    private com.service.producttagService producttagService;
    @Resource(name = "productTypeService")
    private com.service.productTypeService productTypeService;


    private final static Logger logger = LoggerFactory.getLogger(wxProductController.class);
    //    private static String url = "https://xxs.wj910018.com/";
    private static String url = "https://xxs.xixiansheng.cn/";

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/wxGeTypeProduct")
    public @ResponseBody
    Map<String, Object> wxGeTypeProduct(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = 1;
        int pageSize = 10;
        String type = request.getParameter("type");
        System.out.println("============" + crruPage + "===" + pageSize + "===============================");
        List<product> productList = productService.selectByQueryPageForWx(crruPage, pageSize, type);
        int maxCount = productService.selectCount(type);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (product p : productList) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":" + p.getFvipprice() + ",\"fsalled\":\"" + p.getFsalled() + "\",\"ftoptag\":\"" + p.getFtoptag() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetProductDetail")
    public @ResponseBody
    Map<String, Object> wxGetProductDetail(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int fproductid = Integer.parseInt(request.getParameter("fproductid"));
        product p = productService.selectByFid(fproductid);
        List<productImgs> productImgsList = p.getFproductImgs();
        List<productVideo> productVideoList = p.getFproductVideo();
        String imgsInfo = "[";
        for (productImgs pi : productImgsList) {
            imgsInfo += "{\"fid\":\"" + pi.getFid() + "\",\"fproductid\":\"" + pi.getFproductid() + "\",\"fimageurl\":\"" + pi.getFimageurl() + "\"},";
        }
        if (imgsInfo != "[") {
            imgsInfo = imgsInfo.substring(0, imgsInfo.length() - 1);
        }
        imgsInfo += "]";
        String videoInfo = "[";
        for (productVideo pi : productVideoList) {
            videoInfo += "{\"fid\":\"" + pi.getFid() + "\",\"fproductid\":\"" + pi.getFproductid() + "\",\"fvideourl\":\"" + pi.getFvideourl() + "\"},";
        }
        if (videoInfo != "[") {
            videoInfo = videoInfo.substring(0, videoInfo.length() - 1);
        }
        videoInfo += "]";
        producttype ptype = productTypeService.selectById(p.getFtype());
        if (ptype == null) {
            ptype = new producttype(0, "无", 1, 0);
        }
        String tagInfo = "[";
        for (String pt : p.getFtag().split(",")) {
            tagInfo += "{\"fname\":\"" + pt + "\"},";
        }
        if (tagInfo != "[") {
            tagInfo = tagInfo.substring(0, tagInfo.length() - 1);
        }
        tagInfo += "]";


        a = "{\"fProductId\":" + p.getfProductId() + ",\"ftype\":" + p.getFtype() + ",\"typename\":\"" + ptype.getFname() + "\",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"fproductImgs\":" + imgsInfo + ",\"fproductVideo\":" + videoInfo + ",\"fVipPrice\":" + p.getFvipprice() + ",\"count\":0,\"tags\":" + tagInfo + ",\"fsalled\":\"" + p.getFsalled() + "\",\"fdetailImg\":\"" + p.getFdetailImg() + "\",\"edittag\":\"" + p.getFtag() + "\",\"ftoptag\":\"" + p.getFtoptag() + "\",\"fistejia\":\"" + p.getFistejia() + "\",\"fleftcount\":\""+p.getFleftcount()+"\"}";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetHotProduct")
    public @ResponseBody
    Map<String, Object> wxGetHotProduct(HttpServletRequest request, HttpServletResponse response) {

        List<product> productList = productService.selectHotProduct();
        String a = "[";
        for (product p : productList) {
//            a += "{\"fProductId\":" + p.getfProductId() + ",\"ftype\":" + p.getFtype() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":\"0\",\"fVipPrice\":" + p.getFvipprice() + "},";
            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":" + p.getFvipprice() + ",\"fsalled\":\"" + p.getFsalled() + "\",\"ftoptag\":\"" + p.getFtoptag() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetKeyProduct")
    public @ResponseBody
    Map<String, Object> wxGetKeyProduct(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = Integer.parseInt(request.getParameter("crruPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String key = request.getParameter("key");
//        int type = Integer.parseInt(request.getParameter("type"));
        System.out.println("============" + crruPage + "===" + pageSize + "===============================");
        if (key != "") {
            key = "%" + key + "%";
        }
        List<product> productList = productService.selectByKey(crruPage, pageSize, key);
        int maxCount = productService.selectCountByKey(key);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (product p : productList) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"ftype\":" + p.getFtype() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":\"0\",\"fVipPrice\":" + p.getFvipprice() + ",\"ftoptag\":\"" + p.getFtoptag() + "\",\"fistejia\":\""+p.getFistejia()+"\",\"fleftcount\":\""+p.getFleftcount()+"\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/wxGetDefaultProductTejia")
    public @ResponseBody
    Map<String, Object> wxGetDefaultProductTejia(HttpServletRequest request, HttpServletResponse response) {
        List<product> productList = productService.selectTejiaByNone();

        String a = "[";
        for (product p : productList) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":" + p.getFvipprice() + ",\"fsalled\":\"" + p.getFsalled() + "\",\"ftoptag\":\"" + p.getFtoptag() + "\",\"fistejia\":\""+p.getFistejia()+"\",\"fleftcount\":\""+p.getFleftcount()+"\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetDefaultProduct")
    public @ResponseBody
    Map<String, Object> wxGetDefaultProduct(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = Integer.parseInt(request.getParameter("crruPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String type = (request.getParameter("type"));
//        int crruPage =5;
//        int pageSize =5;
//        int type = 0;
        producttype producttype = productTypeService.selectById(Integer.valueOf(type));
        if (producttype != null && producttype.getFparentid() == 0) {
            List<producttype> producttypeList = productTypeService.selectByParentId(producttype.getFtypeid());
            for (producttype pt : producttypeList) {
                type += "," + pt.getFtypeid();
            }
        }
        System.out.println("============" + crruPage + "===" + pageSize + "===============================");
        int maxCount = 0;
        List<product> productList = null;
        if (type.equals("99999")) {
            productList = productService.selectByNoneForWxTeJia(crruPage, pageSize);
            maxCount = productService.selectCountTeJia();
        } else {
            productList = productService.selectByQueryPageForWx(crruPage, pageSize, type);
            maxCount = productService.selectCount(type);
        }
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (product p : productList) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":" + p.getFvipprice() + ",\"fsalled\":\"" + p.getFsalled() + "\",\"ftoptag\":\"" + p.getFtoptag() + "\",\"fistejia\":\""+p.getFistejia()+"\",\"fleftcount\":\""+p.getFleftcount()+"\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/wxGetCartProductInfo")
    public @ResponseBody
    Map<String, Object> GetCartProductInfo(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        try {
            String fids = request.getParameter("ids");
//        fids = "1,2,4";
            if (fids != "") {

                List<String> idList = new ArrayList<String>();
                for (String id : fids.split(",")) {
                    idList.add(id);
                }

                List<product> productList = productService.selectByFids(idList);

                a = "[";
                logger.debug(fids);
                for (product p : productList) {
                    a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":\"" + p.getFvipprice() + "\",\"checked\":false},";
                }
                a = a.substring(0, a.length() - 1);
                a += "]";
            }
        } catch (Exception e) {
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxIsOnSall")
    public @ResponseBody
    Map<String, Object> wxIsOnSall(HttpServletRequest request, HttpServletResponse response) {
        String fids = request.getParameter("ids");
        String[] idlist = fids.split(",");
        List<product> productList = new ArrayList<product>();
        for (String id : idlist) {
            product p = productService.selectByFid(Integer.parseInt(id));
            productList.add(p);
        }
        String a = "";
        for (product p : productList) {
            if (p.getFstate() > 0) {
                a += p.getfProductId() + ",";
//                a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":\"" + p.getFvipprice() + "\",\"checked\":true,\"fsalled\":" + p.getFsalled() + "},";
            }
        }

        if (a != "") {
            a = a.substring(0, a.length() - 1);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxRecheckCart")
    public @ResponseBody
    Map<String, Object> wxRecheckCart(HttpServletRequest request, HttpServletResponse response) {
        String fids = request.getParameter("ids");
        String[] idlist = fids.split(",");
        List<product> productList = new ArrayList<product>();
        for (String id : idlist) {
            product p = productService.selectByFid(Integer.parseInt(id));
            productList.add(p);
        }
        String a = "[";
        for (product p : productList) {
            if (p.getFstate() > 0) {
//                a += p.getfProductId() + ",";
                a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":\"" + p.getFvipprice() + "\",\"checked\":true,\"fsalled\":" + p.getFsalled() + "},";
            }
        }

        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    //后台代码
//    @RequestMapping("/back/GetDefaultProduct")
//    public @ResponseBody
//    Map<String, Object> GetDefaultProduct(HttpServletRequest request, HttpServletResponse response) {
//        int crruPage = Integer.parseInt(request.getParameter("crruPage"));
//        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
//        int type = Integer.parseInt(request.getParameter("type"));
//        System.out.println("============" + crruPage + "===" + pageSize + "===============================");
//        List<product> productList = productService.selectByQueryPage(crruPage, pageSize, type);
//        int maxCount = productService.selectCount(type);
//        int maxPage = 0;
//        if (maxCount % pageSize == 0) {
//            maxPage = maxCount / pageSize;
//        } else {
//            maxPage = maxCount / pageSize + 1;
//        }
//        String a = "[";
//        for (product p : productList) {
//            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":0,\"fVipPrice\":" + p.getFvipprice() + "},";
//        }
//        if (a != "[") {
//            a = a.substring(0, a.length() - 1);
//        }
//        a += "]";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("massage", a);
//        map.put("maxCount", maxCount);
//        map.put("maxPage", maxPage);
//        return map;
//    }
    @RequestMapping("/back/updateProductInfo")
    public @ResponseBody
    Map<String, Object> updateProductInfo(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        String info = request.getParameter("info");
        product p = productService.selectByFid(id);
        switch (type) {
            case "fname":
                p.setfName(info);
                break;
            case "fdesc":
                p.setfDesc(info);
                break;
            case "fprice":
                p.setfPrice(Double.parseDouble(info));
                break;
            case "fvipprice":
                p.setFvipprice(Double.parseDouble(info));
                break;
            case "fsalled":
                p.setFsalled(Integer.parseInt(info));
                break;
            case "fsort":
                p.setFsort(Integer.parseInt(info));
                break;
        }
        int result = productService.updateByFidForBack(p);
        if (result > 0) {
            a = "修改成功";
        } else {
            a = "修改失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
//        map.put("id", product.getfProductId());
        return map;
    }

    @RequestMapping("/back/updateProduct")
    public @ResponseBody
    Map<String, Object> updateProduct(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String info = request.getParameter("info");

        JSONObject jo = JSONObject.fromObject(info);
//        "fProductId": $("#id").val(),
//                "ftype": $("#productTypeSelect").html(),
//                "fName": $("#name").val(),
//                "fDesc": $("#desc").val(),
//                "fDescDetail": $("#descdetail").val(),
//                "fOldPrice": $("#oldprice").val(),
//                "fPrice": $("#price").val(),
//                "fUnit": $("#unit").val(),
//                "fsalled": $("#salled").val(),ftoptag
//                "edittag": $("#tag").val()
        product product1 = productService.selectByFid(jo.getInt("fProductId"));
        product1.setfName(jo.getString("fName"));
        product1.setfDesc(jo.getString("fDesc"));
        product1.setfDescDetail(jo.getString("fDescDetail"));
        product1.setfOldPrice(jo.getDouble("fOldPrice"));
        product1.setfPrice(jo.getDouble("fPrice"));
        product1.setFvipprice(jo.getDouble("vipprice"));
        product1.setfUnit(jo.getString("fUnit"));
        product1.setFtype(jo.getInt("ftype"));
        product1.setFtag(jo.getString("edittag").replace("，", ","));
        product1.setFtoptag(jo.getString("editftoptag").replace("，", ","));
        product1.setFsalled(jo.getInt("fsalled"));
        product1.setFleftcount(jo.getInt("fsalled"));
        int result = productService.updateByFidForBack(product1);
        if (result > 0) {
            a = "修改成功";
        } else {
            a = "修改失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
//        map.put("id", product.getfProductId());
        return map;
    }

    @RequestMapping("/back/addKeyProduct")
    public @ResponseBody
    Map<String, Object> addKeyProduct(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        Date day = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        product product = new product(0, "", "", "", "", "", 0, 0, 0, "", 0, "", "", df1.format(day), 0, 1, 0, 0, 0, 0,0, null, null);
        productService.add(product);
        if (product.getfProductId() > 0) {
            a = "新增成功";
        } else {
            a = "新增失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("id", product.getfProductId());
        return map;
    }

    @RequestMapping("/back/addKeyProductTejia")
    public @ResponseBody
    Map<String, Object> addKeyProductTejia(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        Date day = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        product product = new product(0, "", "", "", "", "", 0, 0, 0, "", 0, "", "", df1.format(day), 0, 1, 0, 0, 0, 1,0, null, null);
        productService.add(product);
        if (product.getfProductId() > 0) {
            a = "新增成功";
        } else {
            a = "新增失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("id", product.getfProductId());
        return map;
    }

    @RequestMapping("/back/getKeyProduct")
    public @ResponseBody
    Map<String, Object> getKeyProduct(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int type = Integer.parseInt(request.getParameter("type"));
        String key = request.getParameter("key");
        int issall = Integer.parseInt(request.getParameter("issall"));
        int ishot = Integer.parseInt(request.getParameter("ishot"));
        System.out.println("============" + crruPage + "===" + pageSize + "===============================");
        if (key != "") {
            key = "%" + key + "%";
        }
        List<product> productList = productService.selectByKeyAndType(crruPage, pageSize, type, key, issall, ishot);
        int maxCount = productService.selectCountByKeyAndType(type, key, issall, ishot);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (product p : productList) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":\"0\",\"fVipPrice\":" + p.getFvipprice() + ",\"typename\":\"" + (p.getFtype() <= 0 ? "" : productTypeService.selectById(p.getFtype()).getFname()) + "\",\"fsalled\":\"" + p.getFsalled() + "\",\"fsort\":\"" + p.getFsort() + "\",\"fistop\":\"" + (p.getFistop() == 1 ? "是" : "否") + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/back/getKeyProductTejia")
    public @ResponseBody
    Map<String, Object> getKeyProductTejia(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String key = request.getParameter("key");
        int issall = Integer.parseInt(request.getParameter("issall"));
        System.out.println("============" + crruPage + "===" + pageSize + "===============================");
        if (key != "") {
            key = "%" + key + "%";
        }
        List<product> productList = productService.selectByKeyAndTypeTejia(crruPage, pageSize, key, issall);
        int maxCount = productService.selectCountByKeyAndTypeTejia(key, issall);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (product p : productList) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":\"0\",\"fVipPrice\":" + p.getFvipprice() + ",\"typename\":\"" + (p.getFtype() <= 0 ? "" : productTypeService.selectById(p.getFtype()).getFname()) + "\",\"fsalled\":\"" + p.getFsalled() + "\",\"fsort\":\"" + p.getFsort() + "\",\"fistop\":\"" + (p.getFistop() == 1 ? "是" : "否") + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/back/updateType")
    public @ResponseBody
    Map<String, Object> updateType(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fids = request.getParameter("key");
        int typeid = Integer.parseInt(request.getParameter("typeid"));
        List<String> ids = new ArrayList<String>();
        for (String s : fids.split("\\|")) {
            ids.add(s);
        }
        int result = productService.updateType(ids, typeid);
        if (result >= 0) {
            a = "更新成功";
        } else {
            a = "更新失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/editSalled")
    public @ResponseBody
    Map<String, Object> editSalled(HttpServletRequest request, HttpServletResponse response) {
        String info = request.getParameter("key");
        int issall = Integer.parseInt(request.getParameter("issall"));
        List<String> ids = new ArrayList<String>();
        for (String s : info.split("\\|")) {
            ids.add(s);
        }
        int result = productService.updateSalled(ids, issall);
        String a = "";
        if (result > 0) {
            switch (issall) {
                case -1:
                    a = "删除成功";
                    break;
                case 0:
                    a = "下架成功";
                    break;
                case 1:
                    a = "上架成功";
                    break;
            }
        } else {
            switch (issall) {
                case -1:
                    a = "删除失败";
                    break;
                case 0:
                    a = "下架失败";
                    break;
                case 1:
                    a = "上架失败";
                    break;
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/deleteImage")
    public @ResponseBody
    Map<String, Object> deleteImage(HttpServletRequest request, HttpServletResponse response) {
        String reportGroupId = request.getParameter("id");

        String a = "";
        int result = productService.deleteImagesByFid(Integer.parseInt(reportGroupId));
        if (result > 0) {
            a = "删除成功";
        } else {
            a = "删除失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/deleteVideos")
    public @ResponseBody
    Map<String, Object> deleteVideos(HttpServletRequest request, HttpServletResponse response) {
        String reportGroupId = request.getParameter("id");

        String a = "";
        int result = productService.deleteVideoByFid(Integer.parseInt(reportGroupId));
        if (result > 0) {
            a = "删除成功";
        } else {
            a = "删除失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updatehot")
    public @ResponseBody
    Map<String, Object> updatehot(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("key");
        int hot = Integer.parseInt(request.getParameter("hot"));
        List<String> ids = new ArrayList<String>();
        for (String s : id.split("\\|")) {
            ids.add(s);
        }
        String a = "";
        int result = productService.updatehot(ids, hot);
        if (result > 0) {
            a = "修改成功";
        } else {
            a = "修改失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updateSort")
    public @ResponseBody
    Map<String, Object> updateSort(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("key");
        int sort = Integer.parseInt(request.getParameter("sort"));
        List<String> ids = new ArrayList<String>();
        for (String s : id.split("\\|")) {
            ids.add(s);
        }
        String a = "";
        int result = productService.updateSort(ids, sort);
        if (result > 0) {
            a = "修改成功";
        } else {
            a = "修改失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updateTop")
    public @ResponseBody
    Map<String, Object> updateTop(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("key");
        int top = Integer.parseInt(request.getParameter("top"));
        List<String> ids = new ArrayList<String>();
        for (String s : id.split("\\|")) {
            ids.add(s);
        }
        String a = "";
        int result = productService.updateTop(ids, top);
        if (result > 0) {
            a = "修改成功";
        } else {
            a = "修改失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/upLoadImageDetail")
    public @ResponseBody
    Map<String, Object> upLoadImageDetail(HttpServletRequest request, HttpServletResponse response) {
        String reportGroupId = request.getParameter("id");

        String a = "";
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map = uploadFile(request, "imagefileDetail", reportGroupId);

        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/upLoadVideo")
    public @ResponseBody
    Map<String, Object> upLoadVideo(HttpServletRequest request, HttpServletResponse response) {
        String reportGroupId = request.getParameter("id");

        String a = "";
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map = uploadFile(request, "videoFile", reportGroupId);

        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/upLoadImages")
    public @ResponseBody
    Map<String, Object> upLoadImages(HttpServletRequest request, HttpServletResponse response) {
        String reportGroupId = request.getParameter("id");

        String a = "";
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map = uploadFile(request, "imagefiles", reportGroupId);

        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/upLoadImage")
    public @ResponseBody
    Map<String, Object> upLoadImage(HttpServletRequest request, HttpServletResponse response) {
        String reportGroupId = request.getParameter("id");
        String a = "";
        Map<String, Object> map = new HashMap<String, Object>();
        //调用通用接口上传文件
        map = uploadFile(request, "imagefile", reportGroupId);
        map.put("massage", a);
        return map;
    }

    /**
     * 上传文件通用接口
     *
     * @param request       请求体
     * @param dstFileName   html上传组件中(input中name属性)，上传文件体名称，通过此名称获取所有上传的文件map
     * @param reportGroupId （特殊）上传报告所述报告组id
     */
    protected Map<String, Object> uploadFile(HttpServletRequest request, String dstFileName, String reportGroupId) {
        Map<String, Object> ret = new HashMap<String, Object>();

        Date day = new Date();
        String dir = "";
//        if (dstFileName.equals("imagefile")) {
//            dir = "D:\\Images\\product";
//        } else if (dstFileName.equals("imagefileDetail")) {
//            dir = "D:\\Images\\productDetail";
//        } else if (dstFileName.equals("videoFile")) {
//            dir = "D:\\Images\\video\\productHead" + reportGroupId;
//        } else {
//            dir = "D:\\Images\\products\\" + reportGroupId;
//        }
        if (dstFileName.equals("imagefile")) {
            dir = "D:\\Images\\productsImages\\" + reportGroupId + "\\product";
        } else if (dstFileName.equals("imagefileDetail")) {
            dir = "D:\\Images\\productsImages\\" + reportGroupId + "\\productDetail";
        } else if (dstFileName.equals("videoFile")) {
            dir = "D:\\Images\\productsImages\\" + reportGroupId + "\\video\\productHead";
        } else {
            dir = "D:\\Images\\productsImages\\" + reportGroupId + "\\products\\";
        }
        //判断保存文件的路径是否存在
        File fileUploadPath = new File(dir);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }

        if (ServletFileUpload.isMultipartContent(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multipartRequest.getFiles(dstFileName);
            for (MultipartFile item : fileList) {
                String fileName = "";        //当前上传文件全名称
                String fileType = "";        //当前上传文件类型
                String saveFileName = "";    //保存到服务器目录的文件名称
                String reportAddr = "";      //保存到服务器目录的文件全路径
                try {
                    SimpleDateFormat df = new SimpleDateFormat("YYMMdd_HHmmss");
                    fileName = item.getOriginalFilename();
                    fileType = item.getContentType();
                    saveFileName = reportGroupId + "_" + df.format(day) + "_" + fileName;
                    reportAddr = fileUploadPath + "/" + saveFileName;
//                    reportAddr = reportAddr.replace("/", File.separator).replace("\\", File.separator);

                    File savedFile = new File(fileUploadPath, saveFileName);
                    item.transferTo(savedFile);
                    if (dstFileName.equals("imagefile")) {
                        product p = productService.selectByFid(Integer.parseInt(reportGroupId));
                        p.setfImgUrl(url + reportAddr.replace("D:\\", "").replace("\\", "/"));
                        int result = productService.updateByFid(p);
                        if (result > 0) {
                            ret.put("message", "上传成功");
                        } else {
                            ret.put("message", "上传失败");
                        }
                    } else if (dstFileName.equals("imagefileDetail")) {
                        product p = productService.selectByFid(Integer.parseInt(reportGroupId));
                        p.setFdetailImg(url + reportAddr.replace("D:\\", "").replace("\\", "/"));
                        int result = productService.updateByFid(p);
                        if (result > 0) {
                            ret.put("message", "上传成功");
                        } else {
                            ret.put("message", "上传失败");
                        }
                    } else if (dstFileName.equals("videoFile")) {
                        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        productVideo pv = new productVideo(0, Integer.parseInt(reportGroupId), url + reportAddr.replace("D:\\", "").replace("\\", "/"), 1, df1.format(day));
                        productService.insertVideo(pv);
                        int result = pv.getFid();
                        if (result > 0) {
                            ret.put("message", "上传成功");
                        } else {
                            ret.put("message", "上传失败");
                        }
                    } else {
                        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        productImgs pi = new productImgs(0, Integer.parseInt(reportGroupId), url + reportAddr.replace("D:\\", "").replace("\\", "/"), 1, df1.format(day));
                        productService.insertImages(pi);
                        int result = pi.getFid();
                        if (result > 0) {
                            ret.put("message", "上传成功");
                        } else {
                            ret.put("message", "上传失败");
                        }
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage() + "::::::::::::::::::::::::::::::::::::::::::::::::::");
                    logger.error(e.getMessage());
                    ret.put("success", false);
                    ret.put("message", e.getMessage());
                    publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
                }
            }
        }
        return ret;
    }
}
