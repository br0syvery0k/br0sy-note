package com.br0sy.note.dao;

import com.br0sy.note.util.DBUtil;

import javax.swing.text.html.ObjectView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础的JDBC类
 *      更新 （添加、修改、删除）
 *      查询
 *          1、查询一个字段
 *          2、查询集合
 *          3、查询某个对象
 */
public class BaseDao {

    /**
     * 更新函数
     *      输入sql语句和要查询的内容，进行更新
     * @param sql
     * @param params
     * @return
     */
    public static int excuteUpdate(String sql, List<Object> params) {
        int row = 0; // 受影响的行数
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 连接
            connection = DBUtil.getConnection();
            // 预编译
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                // 循环设置参数
                for (int i = 0; i < params.size(); ++i) {
                    // 将参数设置到sql语句中去
                    preparedStatement.setObject(i+1, params.get(i));
                }
            }
            // 执行更新，返回受影响的行数
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, preparedStatement, connection);
        }
        // 返回受影响的行数
        return row;
    }

    /**
     * 查询一个单一的结果
     * @param sql
     * @param params
     * @return
     */
    public static Object findSingleValue(String sql, List<Object> params) {
        Object object = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                // 设置参数
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i+1, params.get(i));
                }
            }
            // resultSet此时就是查询的结果集（姑且认为是指针）
            resultSet = preparedStatement.executeQuery();
            // 判断并分析结果集
            if (resultSet.next()) {
                object = resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }
        return object;
    }

    /**
     * 妈的看了很多文章，终于理解这里面在写什么了
     * 方法作用：返回查询的集合，并且我们不用拘泥于是哪个类
     *      主要就是利用到了Java反射的原理，非常灵活，但是也很复杂，不过理解了就还好一点了
     * @param sql
     * @param params
     * @param cls
     * @return
     */
    public static List queryRows(String sql, List<Object> params, Class cls) {
        List list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                // 设置参数
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i+1, params.get(i));
                }
            }
            // resultSet此时就是查询的结果集（姑且认为是指针）
            resultSet = preparedStatement.executeQuery();

            // 得到结果集的元数据对象
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            // 得到查询字段的数量
            int fieldNum = resultSetMetaData.getColumnCount();

            // 判断并分析结果集
            while (resultSet.next()) {
                // 实例化 class 对象
                Object object = cls.getDeclaredConstructor().newInstance();
                // 得到数据库中查询的每一个列名
                for (int i = 1; i <= fieldNum; i++) {
                    // getColumnName()获取列名
                    String columnName = resultSetMetaData.getColumnName(i);
                    // 获取Field对象，其中存的就是属性对象
                    Field field = cls.getDeclaredField(columnName);
                    // 拼接set方法，获得字符串
                    String setMethod = "set" + columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                    // 拿到类中的set方法 setMethod：方法名  field.getType():方法返回的参数类型
                    Method method = cls.getDeclaredMethod(setMethod, field.getType());
                    // 从resultSet集中，取出对应列的字段值
                    Object value = resultSet.getObject(columnName);
                    // 调用set方法
                    method.invoke(object, value);
                }
                list.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return list;
    }

    /**
     * 查询某个对象Object
     * @param sql
     * @param params
     * @param cls
     * @return
     */
    public static Object queryRow(String sql, List<Object> params, Class cls) {
        List list = queryRows(sql, params, cls);
        Object object = null;
        if (list != null && list.size() > 0) {
            // 查询第一个
            object = list.get(0);
        }
        return object;
        // 下面这个是练习，是和上面那个方法差不多的，只是把while改成了if，为了巩固一下所以写的（才不是因为想不到上面的代码）
//        Object obj = null;
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = DBUtil.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            if (params != null && params.size() > 0) {
//                for (int i = 0; i < params.size(); i++) {
//                    preparedStatement.setObject(i+1, params.get(i));
//                }
//            }
//            resultSet = preparedStatement.executeQuery();
//            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//
//            // 查询的字段数量
//            int fieldNum = resultSetMetaData.getColumnCount();
//
//            if (resultSet.next()) {
//                Object object = cls.getDeclaredConstructor().newInstance();
//                for (int i = 1; i < fieldNum; i++) {
//                    // 获取列名
//                    String columnName = resultSetMetaData.getColumnName(i);
//                    // 获取属性
//                    Field field = cls.getDeclaredField(columnName);
//                    String setMethod = "set" + columnName.substring(0,1).toUpperCase() + columnName.substring(1);
//                    Method method = cls.getDeclaredMethod(setMethod, field.getType());
//                    Object value = resultSet.getObject(columnName);
//                    method.invoke(object, value);
//                }
//                obj = object;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.close(resultSet, preparedStatement, connection);
//        }
//        return obj;
    }
}
