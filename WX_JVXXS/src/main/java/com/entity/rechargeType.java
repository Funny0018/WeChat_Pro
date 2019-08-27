package com.entity;

import java.io.Serializable;

public class rechargeType implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private double fprice;
    private String fdec;
    private double famount;
    private int fstate;
    private String fviptext;
    private String fviptime;
    private String fimgurl;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"fid\":")
                .append(fid);
        sb.append(",\"fprice\":")
                .append(fprice);
        sb.append(",\"fdec\":\"")
                .append(fdec).append('\"');
        sb.append(",\"famount\":")
                .append(famount);
        sb.append(",\"fstate\":")
                .append(fstate);
        sb.append(",\"fviptext\":\"")
                .append(fviptext).append('\"');
        sb.append(",\"fviptime\":\"")
                .append(fviptime).append('\"');
        sb.append(",\"fimgurl\":\"")
                .append(fimgurl).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public rechargeType() {
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

    public double getFprice() {
        return fprice;
    }

    public void setFprice(double fprice) {
        this.fprice = fprice;
    }

    public String getFdec() {
        return fdec;
    }

    public void setFdec(String fdec) {
        this.fdec = fdec;
    }

    public double getFamount() {
        return famount;
    }

    public void setFamount(double famount) {
        this.famount = famount;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public String getFviptext() {
        return fviptext;
    }

    public void setFviptext(String fviptext) {
        this.fviptext = fviptext;
    }

    public String getFviptime() {
        return fviptime;
    }

    public void setFviptime(String fviptime) {
        this.fviptime = fviptime;
    }

    public rechargeType(int fid, double fprice, String fdec, double famount, int fstate, String fviptext, String fviptime, String fimgurl) {
        this.fid = fid;
        this.fprice = fprice;
        this.fdec = fdec;
        this.famount = famount;
        this.fstate = fstate;
        this.fviptext = fviptext;
        this.fviptime = fviptime;
        this.fimgurl = fimgurl;
    }

    public String getFimgurl() {
        return fimgurl;
    }

    public void setFimgurl(String fimgurl) {
        this.fimgurl = fimgurl;
    }
}
