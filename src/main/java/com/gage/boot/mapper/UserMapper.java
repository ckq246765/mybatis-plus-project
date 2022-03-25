package com.gage.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gage.boot.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from User")
    List<User> myGetUser();

}
