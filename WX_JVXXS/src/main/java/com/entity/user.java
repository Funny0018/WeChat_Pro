package com.entity;

import java.io.Serializable;

public class user implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fuserid;
    private String fopenid;
    private String funionid;
    private double fmoney;
    private int fisvip;
    private String fvipstartdate;
    private String fvipenddate;
    private int fawardtime;
    private String fimageurl;
    private String fnikename;
    private int fnewcoupon;
    private String falertinfo;
    private double fjifen;

    public user() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"fuserid\":")
                .append(fuserid);
        sb.append(",\"fopenid\":\"")
                .append(fopenid).append('\"');
        sb.append(",\"funionid\":\"")
                .append(funionid).append('\"');
        sb.append(",\"fmoney\":")
                .append(fmoney);
        sb.append(",\"fisvip\":")
                .append(fisvip);
        sb.append(",\"fvipstartdate\":\"")
                .append(fvipstartdate).append('\"');
        sb.append(",\"fvipenddate\":\"")
                .append(fvipenddate).append('\"');
        sb.append(",\"fawardtime\":")
                .append(fawardtime);
        sb.append(",\"fimageurl\":\"")
                .append(fimageurl).append('\"');
        sb.append(",\"fnikename\":\"")
                .append(fnikename).append('\"');
        sb.append(",\"fnewcoupon\":")
                .append(fnewcoupon);
        sb.append(",\"falertinfo\":\"")
                .append(falertinfo).append('\"');
        sb.append(",\"fjifen\":")
                .append(fjifen);
        sb.append('}');
        return sb.toString();
    }

    public double getFjifen() {
        return fjifen;
    }

    public void setFjifen(double fjifen) {
        this.fjifen = fjifen;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFuserid() {
        return fuserid;
    }

    public void setFuserid(int fuserid) {
        this.fuserid = fuserid;
    }

    public String getFopenid() {
        return fopenid;
    }

    public void setFopenid(String fopenid) {
        this.fopenid = fopenid;
    }

    public String getFunionid() {
        return funionid;
    }

    public void setFunionid(String funionid) {
        this.funionid = funionid;
    }

    public double getFmoney() {
        return fmoney;
    }

    public void setFmoney(double fmoney) {
        this.fmoney = fmoney;
    }

    public int getFisvip() {
        return fisvip;
    }

    public void setFisvip(int fisvip) {
        this.fisvip = fisvip;
    }

    public String getFvipstartdate() {
        return fvipstartdate;
    }

    public void setFvipstartdate(String fvipstartdate) {
        this.fvipstartdate = fvipstartdate;
    }

    public String getFvipenddate() {
        return fvipenddate;
    }

    public void setFvipenddate(String fvipenddate) {
        this.fvipenddate = fvipenddate;
    }

    public int getFawardtime() {
        return fawardtime;
    }

    public void setFawardtime(int fawardtime) {
        this.fawardtime = fawardtime;
    }

    public String getFimageurl() {
        return fimageurl;
    }

    public void setFimageurl(String fimageurl) {
        this.fimageurl = fimageurl;
    }

    public String getFnikename() {
        return fnikename;
    }

    public void setFnikename(String fnikename) {
        this.fnikename = fnikename;
    }

    public int getFnewcoupon() {
        return fnewcoupon;
    }

    public void setFnewcoupon(int fnewcoupon) {
        this.fnewcoupon = fnewcoupon;
    }

    public String getFalertinfo() {
        return falertinfo;
    }

    public void setFalertinfo(String falertinfo) {
        this.falertinfo = falertinfo;
    }

    public user(int fuserid, String fopenid, String funionid, double fmoney, int fisvip, String fvipstartdate, String fvipenddate, int fawardtime, String fimageurl, String fnikename, int fnewcoupon, String falertinfo, double fjifen) {
        this.fuserid = fuserid;
        this.fopenid = fopenid;
        this.funionid = funionid;
        this.fmoney = fmoney;
        this.fisvip = fisvip;
        this.fvipstartdate = fvipstartdate;
        this.fvipenddate = fvipenddate;
        this.fawardtime = fawardtime;
        this.fimageurl = fimageurl;
        this.fnikename = fnikename;
        this.fnewcoupon = fnewcoupon;
        this.falertinfo = falertinfo;
        this.fjifen = fjifen;
    }
}
