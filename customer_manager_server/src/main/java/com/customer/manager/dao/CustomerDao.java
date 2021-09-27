package com.customer.manager.dao;

import com.customer.manager.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> queryAllCustomer() {
        String sql = "select * from customer";
        List list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Customer.class));
        return list;
    }

    public Customer queryCustomerByCustomerNo(String customerNo) {

        String sql = "select * from customer where customer_no = ? ";
        Customer query = jdbcTemplate.query(sql, new ResultSetExtractor<Customer>() {
            @Override
            public Customer extractData(ResultSet result) throws SQLException, DataAccessException {
                    return setCustomerValue(result);
            }
        }, customerNo);
        return query;
    }

    private Customer setCustomerValue(ResultSet result) throws SQLException{
        if (!result.next()){
            return null;
        }
        Customer customer = new Customer();
        try {
            customer.setCustomerNo(result.getString("customer_no"));
            customer.setName(result.getString("name"));
            customer.setIdNo(result.getString("id_no"));
            customer.setPhone(result.getString("phone"));
            customer.setBirthday(result.getDate("birthday"));
            customer.setSex(result.getInt("sex"));
            customer.setAddress(result.getString("address"));
            customer.setIntegral(result.getInt("integral"));
            customer.setInvitationCode(result.getString("invitation_code"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public int queryCustomerByIdNo(String idNo) {
        String sql = "select count(1) from customer where id_no = ? ";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class, idNo);
        return integer;
    }

    public int queryCustomerByPhone(String phone) {
        String sql = "select count(1) from customer where phone = ? ";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class, phone);
        return integer;
    }

    public void insertCustomer(Customer customer) {
        String sql = "insert into customer ( customer_no, name, id_no, phone, password, birthday, sex, address, integral, invitation_code )" +
                    " values ( ? ,? ,? ,? , ?, ?, ?, ?, ?, ?) ";
        jdbcTemplate.update(sql,customer.getCustomerNo(), customer.getName(), customer.getIdNo(), customer.getPhone(), customer.getPassword(), customer.getBirthday(),
                            customer.getSex(), customer.getAddress(), customer.getIntegral(), customer.getInvitationCode());
    }

    public Customer queryCustomerByPhoneAndPassword(String phone, String password) {
        String sql = "select * from customer where phone = ? and password = ? ";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Customer>() {
            @Override
            public Customer extractData(ResultSet result) throws SQLException, DataAccessException {
                    return setCustomerValue(result);
            }
        },phone, password);
    }

    public void updateCustomer(Customer customer) {
        String sql = "update customer set phone = ? , password = ? , integral = ? where customer_no = ?";
        jdbcTemplate.update(sql,customer.getPhone(), customer.getPassword(), customer.getIntegral(), customer.getCustomerNo());
    }
}
