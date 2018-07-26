package com.example.zhjt.Bean;

public class Coupon {
    String money;
    String couponType;
    String couponText;
    String time;
    String fullReduce;
    public Coupon(String money,String couponType,String couponText,String time,String fullReduce){
        this.money=money;
        this.couponType=couponType;
        this.couponText=couponText;
        this.time=time;
        this.fullReduce=fullReduce;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCouponText() {
        return couponText;
    }

    public void setCouponText(String couponText) {
        this.couponText = couponText;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullReduce() {
        return fullReduce;
    }

    public void setFullReduce(String fullReduce) {
        this.fullReduce = fullReduce;
    }
}
