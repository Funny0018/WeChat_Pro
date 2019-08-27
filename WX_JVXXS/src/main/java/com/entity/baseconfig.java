package com.entity;

import java.io.Serializable;
import java.util.List;

public class baseconfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private List<optionalCity>  foptionalCity;
    private double fyunfei;
    private double fbaoyou;
    private int fpagesize;
    private int forderpagesize;
    private int fsharecount;
    private int fopenshare;
    private int fopentejia;

    public baseconfig(){}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public List<optionalCity> getFoptionalCity() {
        return foptionalCity;
    }

    public void setFoptionalCity(List<optionalCity> foptionalCity) {
        this.foptionalCity = foptionalCity;
    }

    public double getFyunfei() {
        return fyunfei;
    }

    public void setFyunfei(double fyunfei) {
        this.fyunfei = fyunfei;
    }

    public double getFbaoyou() {
        return fbaoyou;
    }

    public void setFbaoyou(double fbaoyou) {
        this.fbaoyou = fbaoyou;
    }

    public int getFpagesize() {
        return fpagesize;
    }

    public void setFpagesize(int fpagesize) {
        this.fpagesize = fpagesize;
    }

    public int getForderpagesize() {
        return forderpagesize;
    }

    public void setForderpagesize(int forderpagesize) {
        this.forderpagesize = forderpagesize;
    }

    public int getFsharecount() {
        return fsharecount;
    }

    public void setFsharecount(int fsharecount) {
        this.fsharecount = fsharecount;
    }

    public int getFopenshare() {
        return fopenshare;
    }

    public void setFopenshare(int fopenshare) {
        this.fopenshare = fopenshare;
    }

    public int getFopentejia() {
        return fopentejia;
    }

    public void setFopentejia(int fopentejia) {
        this.fopentejia = fopentejia;
    }

    public baseconfig(int fid, List<optionalCity> foptionalCity, double fyunfei, double fbaoyou, int fpagesize, int forderpagesize, int fsharecount, int fopenshare, int fopentejia) {

        this.fid = fid;
        this.foptionalCity = foptionalCity;
        this.fyunfei = fyunfei;
        this.fbaoyou = fbaoyou;
        this.fpagesize = fpagesize;
        this.forderpagesize = forderpagesize;
        this.fsharecount = fsharecount;
        this.fopenshare = fopenshare;
        this.fopentejia = fopentejia;
    }
}
