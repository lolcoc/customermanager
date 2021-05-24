package com.customer.manager.entity;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {

    private static final long serialVersionUID = -4003872822466585831L;
    //客户编号
    private String customerNo;
    //姓名
    private String name;
    //身份证
    private String IDNo;
    //手机
    private String phone;
    //性别
    private int sex;
    //出生日期
    private Date brithday;
    //家庭住址
    private String address;
    //积分
    private int Integral;
    //邀请码
    private String InvitationCode;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDNo() {
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public String getInvitationCode() {
        return InvitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        InvitationCode = invitationCode;
    }
}
