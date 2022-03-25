package com.gage.boot.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gage.boot.bean.User;
import org.springframework.stereotype.Component;

@Component("db1UserMapper")
public interface UserMapper extends BaseMapper<User> {

}
