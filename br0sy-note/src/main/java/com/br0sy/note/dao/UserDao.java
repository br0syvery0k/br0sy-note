package com.br0sy.note.dao;

import com.br0sy.note.po.User;
import com.br0sy.note.util.DBUtil;
import com.br0sy.note.dao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dao层是介于业务逻辑层和数据库层之间的一个类
 *      主要作用就是写一些数据库操作代码
 *      实现操作数据库的方法
 */
public class UserDao {

    /**
     * 调用了BaseDao的方法，直接拿到对象
     *      1.定义SQL语句
     *      2。定义参数集合
     *      3.调用BaseDao方法
     * @param userName
     * @return
     */
    public User queryUserByName(String userName) {
        User user = null;
        String sql = "select * from tb_user where uname = ?";
        List<Object> params = new ArrayList<>();
        params.add(userName);
        user = (User)BaseDao.queryRow(sql, params, User.class);
        return user;
    }

    /**
     * 通过用户的昵称查询一个用户
     * @param userNick
     * @return
     */
    public User queryUserByNick(String userNick) {
        User user = null;
        String sql = "select * from tb_user where nick = ?";
        List<Object> params = new ArrayList<>();
        params.add(userNick);
        user = (User)BaseDao.queryRow(sql, params, User.class);
        return user;
    }

    public User queryUserByUserId(Integer userId) {
        User user = null;
        String sql = "select * from tb_user where userId = ?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        user = (User) BaseDao.queryRow(sql, params, User.class);
        return user;
    }

    /**
     * 编写一个添加用户的函数
     *             返回1或0
     *                  1:表示插入成功
     *                  0：表示插入失败
     *             不做判断
     */
    public int addUser(String userName, String userPwd, String userNick, String userMood, String userHead) {
        int row = 0;
        String sql = "insert into tb_user (uname,upwd,nick,head,mood) values (?,?,?,?,?);";
        List<Object> params = new ArrayList<>();
        params.add(userName);
        params.add(userPwd);
        params.add(userNick);
        params.add(userHead);
        params.add(userMood);
        row = BaseDao.excuteUpdate(sql, params);
        return row; // 这里因为每次只插入一行，所以说肯定是
    }

    /**
     *  修改用户信息
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    public int userUpdate(User user) {
        int row = 0;
        String sql = "update tb_user set nick=?,mood=?,head=? where userId=?";
        List<Object> params = new ArrayList<>();
        params.add(user.getNick());
        params.add(user.getMood());
        params.add(user.getHead());
        params.add(user.getUserId());
        row = BaseDao.excuteUpdate(sql, params);
        return row;
    }

    /**
     *
     */

    /**
     * 不调用BaseDao的方法，纯写的查询
     * @param userName
     * @return
     */
    public User queryUserByName02(String userName) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 拿到connection（数据库对象）
            connection = DBUtil.getConnection();
            // sql查询语句
            String sql = "select * from tb_user where uname = ?";
            // SQL操作对象
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, userName);
            // 返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(userName);
                user.setHead(resultSet.getString("head"));
                user.setNick(resultSet.getString("nick"));
                user.setMood(resultSet.getString("mood"));
                user.setUpwd(resultSet.getString("upwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最终都要关闭连接
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }
}
