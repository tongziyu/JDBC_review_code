package com.yixuexi;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description: Druid 工具类
 * @Author: 易学习
 * @Date: 2023/8/11 21:00
 */
public class DruidUtils {

    // 静态私有的DataSource
    private static DataSource dataSource;
    static{
        Properties pros = new Properties();
        InputStream is = DruidUtils.class.getResourceAsStream("/druid.properties");
        try{
            System.out.println(is);
            pros.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 获得连接的方法
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
    // 释放连接的方法
    public static void freeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * 测试工具类
     * @throws Exception
     */
    @Test
     public void test() throws Exception {
        Connection connection = getConnection();

        System.out.println(connection);
        String sql = "update t_bank set money = ? where account = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,2000);
        preparedStatement.setObject(2,"lisi");

        int i = preparedStatement.executeUpdate();
        System.out.println(i);

        freeConnection(connection);
    }
}
