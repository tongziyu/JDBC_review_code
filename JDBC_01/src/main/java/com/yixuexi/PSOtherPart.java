package com.yixuexi;

import org.junit.Test;

import java.sql.*;

/**
 * @author tongziyu
 * 自增主键回显
 */
public class PSOtherPart {

    @Test
    public void returnPrimaryKey() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");

        String sql = "insert into t_user(account,password,nickname) values(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setObject(1,"王五");
        preparedStatement.setObject(2,"1234562");
        preparedStatement.setObject(3,"董事长");

        int i = preparedStatement.executeUpdate();

        if (i > 0){
            // 取出回显的数值
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int anInt = generatedKeys.getInt(1);

            System.out.println("插入成功,用户的id为："+anInt);
        }else{
            System.out.println("插入失败");
        }
        preparedStatement.close();
        connection.close();

    }

    /**
     * 未经改良的批量插入，执行时间是1616毫秒
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void insertBatchTest() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");

        String sql = "insert into t_user(account,password,nickname) values(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long start =  System.currentTimeMillis();
        for (int i = 0; i < 10000;i++){
            preparedStatement.setObject(1,"王五"+i);
            preparedStatement.setObject(2,"1234562"+i);
            preparedStatement.setObject(3,"董事长"+i);
            preparedStatement.executeUpdate();
        }

        long end = System.currentTimeMillis();
        System.out.println("执行10000次数据插入总耗时：" + (end-start));

        preparedStatement.close();
        connection.close();

    }

    /**
     * 经过改良的批量添加的方法 执行时间165
     */
    @Test
    public void insertBatchTest2() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030&rewriteBatchedStatements=true ");

        String sql = "insert into t_user(account,password,nickname) values(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long start =  System.currentTimeMillis();
        for (int i = 0; i < 10000;i++){
            preparedStatement.setObject(1,"dddd"+i);
            preparedStatement.setObject(2,"dddd"+i);
            preparedStatement.setObject(3,"总监1"+i);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        long end = System.currentTimeMillis();
        System.out.println("执行10000次数据插入总耗时：" + (end-start));

        preparedStatement.close();
        connection.close();

    }
}
