package com.entity;

import java.io.Serializable;

public class awardChance implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private int fcouponID;
    private double fchance;

    public awardChance() {
    }

    public awardChance(int fid, int fcouponID, double fchance) {
        this.fid = fid;
        this.fcouponID = fcouponID;
        this.fchance = fchance;
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
}
