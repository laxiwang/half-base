package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class AgentChangeVo {
    private  String simplename;
    private String fullname;
    private  String levelName;
    private BigDecimal sum;
    private BigDecimal h51;
    private BigDecimal tuiguang;
    private BigDecimal xiaoshou;
    private BigDecimal give;
    private BigDecimal ios;
    private BigDecimal android;

    public String getsimplename() {
        return simplename;
    }

    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    public String getfullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getlevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public BigDecimal getsum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal geth51() {
        return h51;
    }

    public void setH51(BigDecimal h51) {
        this.h51 = h51;
    }

    public BigDecimal gettuiguang() {
        return tuiguang;
    }

    public void settuiguang(BigDecimal h52) {
        this.tuiguang = tuiguang;
    }

    public BigDecimal getxiaoshou() {
        return xiaoshou;
    }

    public void setxiaoshou(BigDecimal xiaoshou) {
        this.xiaoshou = xiaoshou;
    }

    public BigDecimal getgive() {
        return give;
    }

    public void setGive(BigDecimal give) {
        this.give = give;
    }

    public BigDecimal getios() {
        return ios;
    }

    public void setIos(BigDecimal ios) {
        this.ios = ios;
    }

    public BigDecimal getandroid() {
        return android;
    }

    public void setAndroid(BigDecimal android) {
        this.android = android;
    }

}
