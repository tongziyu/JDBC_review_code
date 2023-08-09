package com.yixuexi;

import java.sql.*;
import java.util.Scanner;

/**
 * @author 仝子瑜
 * 使用预编译的执行对象，完成登录功能
 * TODO: 防止注入攻击
 */
public class PSUserLoginPart {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        String account = scanner.next();
        String password = scanner.next();
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");
        // 3.编写SQL
        // 提前将SQL写出来
        String sql = "select * from t_user where account = ? and password = ?";
        // 4.获取执行对象
        // 将SQL放入PrepareStatement 提前编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        /** 设置参数，在JDBC中，下标从1开始
         * parameterIndex 表示在第几个问号
         */
        preparedStatement.setObject(1,account);
        preparedStatement.setObject(2,password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }


    }

}
