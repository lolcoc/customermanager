package com.customer.manager.server.Impl;

import com.customer.manager.dao.CustomerDao;
import com.customer.manager.entity.Customer;
import com.customer.manager.server.CustomerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServerImpl implements CustomerServer {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> queryAllCustomer() {
        return customerDao.queryAllCustomer();
    }

    @Override
    public Customer queryCustomerByCustomerNo(String customerNo) {
        return customerDao.queryCustomerByCustomerNo(customerNo);
    }

    @Override
    public int queryCustomerByIdNo(String idNo) {
        return customerDao.queryCustomerByIdNo(idNo);
    }

    @Override
    public int queryCustomerByPhone(String phone) {
        return customerDao.queryCustomerByPhone(phone);
    }

    @Override
    public void insertCustomer(Customer customer) {
        String invitationCode = customer.getInvitationCode();
        if (null != invitationCode) {
            Customer invitationCustomer = customerDao.getCustomerByInvitationCode(invitationCode);
            if (null != invitationCustomer ) {
                int integral = invitationCustomer.getIntegral();
                customerDao.insertInvitationRelationship(invitationCustomer.getCustomerNo(), customer.getCustomerNo(), new Date());
                customerDao.updateCustomerIntegral(invitationCustomer.getCustomerNo(),++integral);
            }
        }
        invitationCode = (int)((Math.random()*9+1)*10000000)+"";
        customer.setInvitationCode(invitationCode);
        customerDao.insertCustomer(customer);
    }

    @Override
    public Customer queryCustomerByPhoneAndPassword(String phone, String password) {
        return customerDao.queryCustomerByPhoneAndPassword(phone,password);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    @Override
    public void getVerifyCode(String phone) {
        customerDao.deleteVerifyCode(phone);
        int verifyCode = (int)((Math.random()*9+1)*1000);
        // 将验证码发送到手机 待写
        customerDao.getVerifyCode(phone,verifyCode);
    }

    @Override
    public int verifyVerifyCode(String phone, String verifyCode) {
        return customerDao.verifyVerifyCode(phone, verifyCode);
    }

    @Override
    public void deleteVerifyCode(String phone) {
        customerDao.deleteVerifyCode(phone);
    }

    @Override
    public int queryRealNameAuthentication(String idNo, String name) {
        // 查询本地实名认证库
        int count = customerDao.queryRealNameAuthentication(idNo,name);
        // 查询第三方实名认证 待写
        return count;
    }

    @Override
    @Async
    public void insertTestTable(java.util.Date date) {
        try {
            Thread.sleep(500000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sql = "insert into test_table (test_date)" +
                " values ( ? ) ";
        customerDao.insertTestTable(date );
    }
}
