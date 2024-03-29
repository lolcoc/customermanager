package com.customer.manager.controller;

import com.customer.manager.entity.Customer;
import com.customer.manager.server.CustomerServer;
import com.customer.manager.util.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.PipedOutputStream;
import java.sql.Date;
import java.util.*;

@Controller
@ResponseBody
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerServer customerServer;

    /**
     *  1. 用户注册
     *  2. 用户登录
     *  3. 查看个人信息
     *  4. 查询所有人信息
     *  5. 实名认证
     *  6. 手机号认证
     *  7. 修改手机号
     *  8. 修改个人信息
     *
     */

    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "idNo", value = "身份证号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "出生年", required = true, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "家庭住址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "invitationCode", value = "邀请码", required = false, dataType = "String")
    })
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String,Object> register(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "idNo") String idNo,
                                     @RequestParam(value = "phone") String phone,
                                     @RequestParam(value = "verifyCode") String verifyCode,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "sex") String sex,
                                     @RequestParam(value = "birthday") String birthday,
                                     @RequestParam(value = "address") String address,
                                     @RequestParam(value = "invitationCode") String invitationCode
                                     ){
        Map<String,Object> map = new HashMap<>();
        Customer customer = new Customer();

        //校验身份证号唯一、手机号唯一
        int count = customerServer.queryCustomerByIdNo(idNo);
        if (count != 0){
            map.put("resultCode","error");
            map.put("massage","该身份证已注册");
            return map;
        }
        //实名认证
        count = customerServer.queryRealNameAuthentication(idNo,name);
        if (count == 0){
            map.put("resultCode","error");
            map.put("massage","姓名与身份证不符");
            return map;
        }
        count = customerServer.queryCustomerByPhone(phone);
        if (count != 0){
            map.put("resultCode","error");
            map.put("massage","该手机号已注册");
            return map;
        }else {
         count = customerServer.verifyVerifyCode(phone,verifyCode);
         if (count == 0){
             map.put("resultCode","error");
             map.put("massage","验证码有误");
             return map;
         }
         customerServer.deleteVerifyCode(phone);
        }

        //自动生成客户编号
        Calendar now = Calendar.getInstance();
        boolean flag = true;
        while (flag) {
            String customerNo = DateUtil.getMillisString();
            Customer result = customerServer.queryCustomerByCustomerNo(customerNo);
            if (result == null) {
                customer.setCustomerNo(customerNo);
                flag = false;
            }
        }
        customer.setName(name);
        customer.setIdNo(idNo);
        customer.setPhone(phone);
        customer.setPassword(password);
        customer.setSex(Integer.parseInt(sex));
        customer.setBirthday(DateUtil.stringToDate(birthday));
        customer.setAddress(address);
        customer.setInvitationCode(invitationCode);
        customerServer.insertCustomer(customer);
        map.put("resultCode","success");
        map.put("massage","注册成功");
        return map;
    }

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map coustomerLogin(@RequestParam(value = "phone") String phone ,@RequestParam(value = "password") String password){
        Map<String,Object> map = new HashMap<>();
        Customer customer = new Customer();
        Customer result = customerServer.queryCustomerByPhoneAndPassword(phone, password);
        if (result == null) {
            map.put("resultCode","error");
            map.put("massage","账号或密码错误");
            return map;
        }
        customer.setName(result.getName());
        customer.setIntegral(result.getIntegral());
        customer.setInvitationCode(result.getInvitationCode());
        map.put("resultCode","success");
        map.put("massage",result.getName());
        return map;
    }
    @ApiOperation(value = "查询所有客户信息")
    @RequestMapping(value = "/queryAllCustomers",method = RequestMethod.POST)
    public List<Customer> queryAllCustomers(){
        List<Customer> customers = customerServer.queryAllCustomer();
        return customers;
    }

    @ApiOperation(value = "根据客户号查询客户信息")
    @ApiImplicitParam(name = "customerNo", value = "用户编号", required = true, dataType = "String")
    @RequestMapping(value = "/queryCustomerByCustomerNo",method = RequestMethod.POST)
    public Customer queryCustomerByCustomerNo(@RequestParam(value = "customerNo") String customerNo){
        Customer customer = customerServer.queryCustomerByCustomerNo(customerNo);
        return customer;
    }

    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerNo", value = "用户编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "家庭住址", required = true, dataType = "String")
    })
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public Map<String,Object> modifyCustomer(@RequestParam(value = "customerNo") String customerNo,
                                            @RequestParam(value = "phone") String phone,
                                            @RequestParam(value = "password") String password,
                                            @RequestParam(value = "address") String address
                                            ){
        Map<String,Object> map = new HashMap();
        Customer customer = new Customer();
        customer.setCustomerNo(customerNo);
        customer.setPhone(phone);
        customer.setPassword(password);
        customer.setAddress(address);
        customerServer.updateCustomer(customer);
        map.put("resultCode","success");
        map.put("massage","修改成功！");
        return map;
    }

    @ApiOperation(value = "获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getVerifyCode",method = RequestMethod.POST)
    public Map<String,Object> getVerifyCode(@RequestParam(value = "phone") String phone){
        Map<String,Object> map = new HashMap();
        customerServer.getVerifyCode(phone);
        map.put("resultCode","success");
        map.put("massage","获取验证码成功！");
        return map;
    }

    @ApiOperation(value = "test_table")
    @RequestMapping(value = "/insertTestTable",method = RequestMethod.POST)
    public Map<String,Object> insertTestTable(){
        new Thread(){
            @Override
            public void run(){
                customerServer.insertTestTable(new java.util.Date());
            }
        }.start();
        Map<String,Object> map = new HashMap();
        map.put("resultCode","success");
        map.put("massage","插入成功 ！");
        return map;
    }
}
