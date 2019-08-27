package com.entity;

import java.io.Serializable;

public class awardChanceFroCoupon implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private int fcouponID;
    private double fchance;
    private coupon fcoupon;

    public awardChanceFroCoupon(int fid, int fcouponID, double fchance, coupon fcoupon) {
        this.fid = fid;
        this.fcouponID = fcouponID;
        this.fchance = fchance;
        this.fcoupon = fcoupon;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFcouponID() {
        return fcouponID;
    }

    public void setFcouponID(int fcouponID) {
        this.fcouponID = fcouponID;
    }

    public double getFchance() {
        return fchance;
    }

    public void setFchance(double fchance) {
        this.fchance = fchance;
    }

    public coupon getFcoupon() {
        return fcoupon;
    }

    public void setFcoupon(coupon fcoupon) {
        this.fcoupon = fcoupon;
    }
}
