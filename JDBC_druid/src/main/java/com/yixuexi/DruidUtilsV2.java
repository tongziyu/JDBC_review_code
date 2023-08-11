package com.yixuexi;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description: 利用线程本地变量,存储连接信息,确保一个线程的多个方法可以获取同一个connection
 *               优势: 事务操作时,service dao方法属于同一个线程,不用再传递参数,可以直接获取connection对象,是同一个
 * @Author: 易学习
 * @Date: 2023/8/11 22:12
 */
public class DruidUtilsV2 {
    // 静态私有的DataSource
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
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
        //查看线程本地变量中,是否存在connection
        Connection connection = threadLocal.get();

        if (connection == null){
            // 线程本地没有,连接池获取
            connection = dataSource.getConnection();
            // 存入线程本地变量
            threadLocal.set(connection);
        }

        return connection;
    }
    // 释放连接的方法
    public static void freeConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null){
            // 删除线程本地变量中的connection
            threadLocal.remove();
            // 关闭事务,开启自动提交
            connection.setAutoCommit(true);
            connection.close();
        }

    }
}
