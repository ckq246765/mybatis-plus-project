package com.gage.boot.service.db1;

import com.gage.boot.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {

    void initDB1() throws SQLException;

    List<User> getAllDB1User() throws SQLException;

    int insertDB1User(User user);
}
