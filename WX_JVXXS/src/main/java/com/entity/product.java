package com.entity;

import java.io.Serializable;
import java.util.List;

public class product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fProductId;
    private String fName;
    private String fImgUrl;
    private String fdetailImg;
    private String fDesc;
    private String fDescDetail;
    private double fOldPrice;
    private double fPrice;
    private double fvipprice;
    private String fUnit;
    private int ftype;
    private String ftag;
    private String ftoptag;
    private String fcreatedate;
    private int fsalled;
    private int fstate;
    private int fishot;
    private int fsort;
    private int fistop;
    private  int fistejia;
    private int fleftcount;

    public int getFleftcount() {
        return fleftcount;
    }

    public void setFleftcount(int fleftcount) {
        this.fleftcount = fleftcount;
    }

    public int getFistejia() {
        return fistejia;
    }

    public void setFistejia(int fistejia) {
        this.fistejia = fistejia;
    }

    private List<productImgs> fproductImgs;

    private List<productVideo> fproductVideo;

    public product(int fProductId, String fName, String fImgUrl, String fdetailImg, String fDesc, String fDescDetail, double fOldPrice, double fPrice, double fvipprice, String fUnit, int ftype, String ftag, String ftoptag, String fcreatedate, int fsalled, int fstate, int fishot, int fsort, int fistop, int fistejia, int fleftcount, List<productImgs> fproductImgs, List<productVideo> fproductVideo) {
        this.fProductId = fProductId;
        this.fName = fName;
        this.fImgUrl = fImgUrl;
        this.fdetailImg = fdetailImg;
        this.fDesc = fDesc;
        this.fDescDetail = fDescDetail;
        this.fOldPrice = fOldPrice;
        this.fPrice = fPrice;
        this.fvipprice = fvipprice;
        this.fUnit = fUnit;
        this.ftype = ftype;
        this.ftag = ftag;
        this.ftoptag = ftoptag;
        this.fcreatedate = fcreatedate;
        this.fsalled = fsalled;
        this.fstate = fstate;
        this.fishot = fishot;
        this.fsort = fsort;
        this.fistop = fistop;
        this.fistejia = fistejia;
        this.fleftcount = fleftcount;
        this.fproductImgs = fproductImgs;
        this.fproductVideo = fproductVideo;
    }

    public List<productVideo> getFproductVideo() {
        return fproductVideo;
    }

    public void setFproductVideo(List<productVideo> fproductVideo) {
        this.fproductVideo = fproductVideo;
    }

    public int getFistop() {
        return fistop;
    }

    public void setFistop(int fistop) {
        this.fistop = fistop;
    }

    public int getFsort() {
        return fsort;
    }

    public void setFsort(int fsort) {
        this.fsort = fsort;
    }

    public int getFishot() {
        return fishot;
    }

    public void setFishot(int fishot) {
        this.fishot = fishot;
    }

    public product() {
    }

    public String getFtoptag() {
        return ftoptag;
    }

    public void setFtoptag(String ftoptag) {
        this.ftoptag = ftoptag;
    }

    public int getFsalled() {
        return fsalled;
    }

    public void setFsalled(int fsalled) {
        this.fsalled = fsalled;
    }


    public String getFdetailImg() {
        return fdetailImg;
    }

    public void setFdetailImg(String fdetailImg) {
        this.fdetailImg = fdetailImg;
    }

    public String getFtag() {

        return ftag;
    }

    public void setFtag(String ftag) {
        this.ftag = ftag;
    }


    public List<productImgs> getFproductImgs() {
        return fproductImgs;
    }

    public void setFproductImgs(List<productImgs> fproductImgs) {
        this.fproductImgs = fproductImgs;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getfProductId() {
        return fProductId;
    }

    public void setfProductId(int fProductId) {
        this.fProductId = fProductId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfImgUrl() {
        return fImgUrl;
    }

    public void setfImgUrl(String fImgUrl) {
        this.fImgUrl = fImgUrl;
    }

    public String getfDesc() {
        return fDesc;
    }

    public void setfDesc(String fDesc) {
        this.fDesc = fDesc;
    }

    public String getfDescDetail() {
        return fDescDetail;
    }

    public void setfDescDetail(String fDescDetail) {
        this.fDescDetail = fDescDetail;
    }

    public double getfOldPrice() {
        return fOldPrice;
    }

    public void setfOldPrice(double fOldPrice) {
        this.fOldPrice = fOldPrice;
    }

    public double getfPrice() {
        return fPrice;
    }

    public void setfPrice(double fPrice) {
        this.fPrice = fPrice;
    }

    public String getfUnit() {
        return fUnit;
    }

    public void setfUnit(String fUnit) {
        this.fUnit = fUnit;
    }

    public int getFtype() {
        return ftype;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }

    public String getFcreatedate() {
        return fcreatedate;
    }

    public void setFcreatedate(String fcreatedate) {
        this.fcreatedate = fcreatedate;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public double getFvipprice() {
        return fvipprice;
    }

    public void setFvipprice(double fvipprice) {
        this.fvipprice = fvipprice;
    }

}
