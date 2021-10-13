package com.customer.manager.server;

import com.customer.manager.entity.Customer;

import java.util.List;

public interface CustomerServer {
    /**
     * 查询所有客户信息
     */
    List<Customer> queryAllCustomer();

    /**
     * 根据客户号查询单个客户信息
     */
    Customer queryCustomerByCustomerNo(String customerNo);


    int queryCustomerByIdNo(String idNo);

    int queryCustomerByPhone(String phone);

    void insertCustomer(Customer customer);

    Customer queryCustomerByPhoneAndPassword(String phone, String password);

    void updateCustomer(Customer customer);

    void getVerifyCode(String phone);

    int verifyVerifyCode(String phone, String verifyCode);

    void deleteVerifyCode(String phone);

    int queryRealNameAuthentication(String idNo, String name);
}
