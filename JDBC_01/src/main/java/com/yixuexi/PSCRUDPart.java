package com.yixuexi;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用PrepareStatement 对象完成增删改查操作
 * @author tongziyu
 */
public class PSCRUDPart {
    /**
     * 新增张三信息
     * account ： zhangsan
     * password:123456
     * nickname：董事长
     */
    @Test
    public void insertTest() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");

        String sql = "insert into t_user(account,password,nickname) values(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,"zhangsan");
        preparedStatement.setObject(2,"123456");
        preparedStatement.setObject(3,"董事长");

        int i = preparedStatement.executeUpdate();
        if (i > 0){
            System.out.println("新增成功");
        }else {
            System.out.println("新增失败");
        }

        preparedStatement.close();
        connection.close();
    }

    /**
     * 删除id为3的信息
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void deleteTest() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");
        String sql = "delete from t_user where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,9);
        int i = preparedStatement.executeUpdate();
        if (i > 0 ){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }

        preparedStatement.close();
        connection.close();

    }

    /**
     * 将张三的董事长改成执行总裁
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void updateTest() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");
        String sql = "update t_user set nickname = ? where account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"执行总裁");
        preparedStatement.setObject(2,"zhangsan");

        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
        preparedStatement.close();
        connection.close();

    }

    /**
     * 将查询的结果封装到List<Map> list中
     *
     * Map中的key就是列名，value就是数据库中的值
     *
     * 难点：如何获取列的名称
     */
    @Test
    public void selectTest() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_study?user=root&password=20001030");

        String sql = "select * from t_user";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        // 获得表的列信息
        ResultSetMetaData metaData = resultSet.getMetaData();

        List<Map> list = new ArrayList<>();

        // 获取列的数量
        int columnCount = metaData.getColumnCount();

        while(resultSet.next()){
            Map map = new HashMap();
            // 得上数量之后，进行遍历，得到每一个列的名字
            for(int i = 1; i <= columnCount;i++){
                // 获取列的名称
                // 这里使用getColumnlabel()方法,因为这个方法会获取列的别名，如果列没有别名的话，会获取真正的列名
                String columnLabel = metaData.getColumnLabel(i);
                // 将key=列名， value=值 放入map中
                map.put(columnLabel,resultSet.getString(columnLabel));
            }
            list.add(map);
        }
        System.out.println(list);
    }
}
