package com.br0sy.note;

import com.br0sy.note.dao.BaseDao;
import com.br0sy.note.dao.UserDao;
import com.br0sy.note.po.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestUserDao {

    /**
     * 测试是否返回了数据库数据对象
     */
    @Test
    public void QueryUserByName() {
        // 数据库连接对象
        UserDao userDao = new UserDao();
        // 用户对象
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }

    @Test
    public void testAdd() {
        String sql = "insert into tb_user (uname,upwd,nick,head,mood) values (?,?,?,?,?)";
        List<Object> params = new ArrayList<>();
        // 这里设计数据库时，设置了userId为自增，所以不需要去设置
        params.add("lisi");
        params.add("c9d87ef00591631536b48bc9552a97e1");
        params.add("lisi");
        params.add("404.jpg");
        params.add("Hello");
        int row = BaseDao.excuteUpdate(sql, params);
        System.out.println(row);
    }

    @Test
    public void textAdd() {

    }
}
