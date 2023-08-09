package com.yixuexi;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * 使用Statement测试登录是否有SQL注入问题
 * @author tongziyu
 */
public class StatementUserLoginPart {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);

        String username = scanner.next();
        String password = scanner.next();

        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study", "root", "20001030");
        // 2.获取连接的第二种方法
        //Properties info = new Properties();
        //info.put("user","root");
        //info.put("password","20001030");
        // Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study", info);
        // 2.获取连接的第三种方法
        // Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");
        // 3.获取执行对象
        Statement statement = connection.createStatement();
        // SQL
        String sql = "select * from t_user where account = '"+username+"' and password = '"+password+"'";
        // 4.执行SQL语句
        ResultSet resultSet1 = statement.executeQuery(sql);
        // 5.处理结果集
        if(resultSet1.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

        // 6.关闭资源
        resultSet1.close();
        statement.close();
        connection.close();

    }
}
