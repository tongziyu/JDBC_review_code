package com.yixuexi;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description: 学习Druid连接池
 * @Author: 易学习
 * @Date: 2023/8/11 20:04
 */
public class DruidTest {

    /**
     * Description: 使用软编码的方式进行创建数据库连接池
     * 1.读取外部配置文件
     * 2.使用连接池的工具类的工厂模式,创建连接池
     *
     */
    @Test
    public void testCommon() throws Exception {
        Properties properties = new Properties();

        // 将src下的druid.properties获取流
        InputStream inp = DruidTest.class.getClassLoader().getResourceAsStream("druid.properties");

        properties.load(inp);

        // 获取到连接池了
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();

        // 进行crud
        String sql = "select * from t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("account"));
        }

        // 回收连接
        connection.close();

    }


    /**
     * Description: 硬编码的方式创建数据库连接池,平时不用这种
     * 直接使用代码设置连接池连接方式
     * @throws: SQLException
     */
    @Test
    public void test() throws SQLException {

        // 创建连接池
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/jdbc_study");
        dataSource.setUsername("root");
        dataSource.setPassword("20001030");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");


        Connection connection = dataSource.getConnection();
        String sql = "select * from t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String account = resultSet.getString("account");
            String password = resultSet.getString("password");
            System.out.println(account + "   " + password);
        }

    }


}
