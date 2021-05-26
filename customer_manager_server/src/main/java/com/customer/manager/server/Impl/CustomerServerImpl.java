package com.customer.manager.server.Impl;

import com.customer.manager.dao.CustomerDao;
import com.customer.manager.entity.Customer;
import com.customer.manager.server.CustomerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
