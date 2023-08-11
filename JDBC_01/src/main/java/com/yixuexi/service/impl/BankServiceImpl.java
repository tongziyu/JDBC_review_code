package com.yixuexi.service.impl;

import com.yixuexi.dao.BankDao;
import com.yixuexi.service.BankService;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description:
 * @Author: 易学习
 * @Date: 2023/8/10 13:39
 */
public class BankServiceImpl implements BankService {

    @Test
    public void test() throws SQLException, ClassNotFoundException {
        transfer();
    }

    /**
     * Service层,进行事务控制
     */
    @Override
    public void transfer() throws ClassNotFoundException, SQLException {
        /*BankDao bankDao = new BankDao();
        try {
            bankDao.addMoney("zhangsan",500);

            bankDao.subMoney("lisi",500);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }*/
        // 进行事务控制
        BankDao bankDao = new BankDao();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");
        try {

            // 关闭自动提交
            connection.setAutoCommit(false);
            bankDao.addMoney("zhangsan",500,connection);
            bankDao.subMoney("lisi",500,connection);

            // 都执行后,进行提交
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            connection.close();
        }

    }
}
