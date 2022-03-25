package com.gage.boot.service.db2;

import com.gage.boot.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {

    void initDB2() throws SQLException;

    List<User> getAllDB2User();

    int insertDB2User(User user);
}
