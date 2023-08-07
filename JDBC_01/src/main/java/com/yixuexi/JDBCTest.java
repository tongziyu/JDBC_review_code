package com.yixuexi;

import java.sql.*;

/**
 * JDBCTest: 测试Statement
 * @author tongziyu
 */
public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_study";
        String username = "root";
        String password = "20001030";
        Connection connection = DriverManager.getConnection(url, username, password);
        // 3.获得执行对象
        Statement statement = connection.createStatement();
        // 4.执行SQL
        String sql = "select * from t_user";
        ResultSet resultSet = statement.executeQuery(sql);
        // 5.解析结果集
        while(resultSet.next()){
            Integer id = Integer.valueOf(resultSet.getString("id"));
            String account = resultSet.getString("account");
            String dbPassword = resultSet.getString("password");
            String nickname = resultSet.getString("nickname");
            System.out.println(id +"--"+ account +"--"+dbPassword +"--"+ nickname);
        }
        // 6.释放资源
        connection.close();
        statement.close();
        resultSet.close();


    }
}
