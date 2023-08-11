package com.yixuexi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description:
 * @Author: 易学习
 * @Date: 2023/8/10 13:38
 */
public class BankDao {
    /**
     * TODO:加钱操作
     * @param account
     * @param money
     * @param connection 加钱和减钱使用同一个Connection对象,才可以完成事务的操作
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void addMoney(String account,int money,Connection connection) throws ClassNotFoundException, SQLException {

        String sql = "update t_bank set money = money + ? where account = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,money);
        preparedStatement.setObject(2,account);

        int i = preparedStatement.executeUpdate();

        if (i > 0) {
            System.out.println("加钱成功");
        }else{
            System.out.println("加钱失败");
        }

        preparedStatement.close();


    }
    /**
     * TODO:减钱操作
     * @param account
     * @param money
     * @param connection 加钱和减钱使用同一个Connection对象,才可以完成事务的操作
     * @return void
     */
    public void subMoney(String account,int money,Connection connection) throws ClassNotFoundException, SQLException {

        String sql = "update t_bank set money = money - ? where account = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,money);
        preparedStatement.setObject(2,account);

        int i = preparedStatement.executeUpdate();

        if (i > 0) {
            System.out.println("减钱成功");
        }else{
            System.out.println("减钱失败");
        }
        preparedStatement.close();

    }

}
