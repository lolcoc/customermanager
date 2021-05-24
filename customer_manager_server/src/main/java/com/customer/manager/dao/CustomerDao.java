package com.customer.manager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

}
