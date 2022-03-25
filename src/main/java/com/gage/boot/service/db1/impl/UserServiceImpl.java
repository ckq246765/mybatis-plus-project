package com.gage.boot.service.db1.impl;

import com.gage.boot.bean.User;
import com.gage.boot.mapper.db1.UserMapper;
import com.gage.boot.service.db1.IUserService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service("db1UserService")
public class UserServiceImpl implements IUserService {

    @Resource(name = "db1UserMapper")
    UserMapper userMapper;
    @Resource(name = "db1SqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    /**
     * 暂时使用（初始化）：
     * @throws SQLException
     */
    public void initDB1() throws SQLException {
        SqlSession session = sqlSessionFactory.openSession();
        Connection connection = session.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE user\n" +
                "(\n" +
                "    id BIGINT(20) NOT NULL COMMENT '主键ID',\n" +
                "    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',\n" +
                "    age INT(11) NULL DEFAULT NULL COMMENT '年龄',\n" +
                "    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',\n" +
                "    PRIMARY KEY (id)\n" +
                ")");
        statement.execute("INSERT INTO user (id, name, age, email) VALUES\n" +
                "(1, 'Jone', 18, 'test1@baomidou.com'),\n" +
                "(2, 'Jack', 20, 'test2@baomidou.com'),\n" +
                "(3, 'Tom', 28, 'test3@baomidou.com'),\n" +
                "(4, 'Sandy', 21, 'test4@baomidou.com'),\n" +
                "(5, 'Billie', 24, 'test5@baomidou.com')");
    }

    @Override
    public List<User> getAllDB1User() throws SQLException {
        return userMapper.selectList(null);
    }

    @Override
    public int insertDB1User(User user){
        return userMapper.insert(user);
    }

}
