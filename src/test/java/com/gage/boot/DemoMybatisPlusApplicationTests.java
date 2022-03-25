package com.gage.boot;


import com.gage.boot.service.common.CommonTestDataSources;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLException;

@SpringBootTest
class QuickStartTest {
//    @Resource
//    private UserMapper userMapper;

//    @Test
//    public void testSelect() {
//        System.out.println(("----- selectAll method test ------"));
//        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<User>().eq(User::getId, 1);
//        List<User> userList = userMapper.selectList(wrapper);
//        userList.forEach(System.out::println);
//        Assertions.assertEquals(5, userList.size());
//    }


    @Resource
    CommonTestDataSources commonTestDataSources;
    @Test
    public void testDataSources() throws SQLException {
        commonTestDataSources.testMethod();
    }
}
