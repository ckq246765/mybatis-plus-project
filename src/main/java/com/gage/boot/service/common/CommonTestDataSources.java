package com.gage.boot.service.common;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.gage.boot.bean.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import java.sql.SQLException;

@Service
public class CommonTestDataSources {

    @Resource
    private com.gage.boot.service.db1.IUserService db1UserService;
    @Resource
    private com.gage.boot.service.db2.IUserService  db2UserService;
//    自定义事务
//    @Resource(name = "userTransactionManager")
//    UserTransactionManager userTransactionManager;

    @Transactional(value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    @Transactional(transactionManager = "userTransactionManager", rollbackFor = Exception.class)   // 不生效？
    public void exceptionAdd() throws SystemException {
        User user = User.builder().id(1000L).name("gage").age(20).email("gagekeqi@sina.com").build();
//        try {
//            userTransactionManager.begin();  // 手动开启事务
            db1UserService.insertDB1User(user);
            int i = 1 / 0;
            db2UserService.insertDB2User(user);
//            userTransactionManager.commit();
//        }catch (Exception ex){
//            userTransactionManager.rollback();
//        }
    }

    public void testMethod() throws SQLException {
        // 初始化
        db1UserService.initDB1();
        db2UserService.initDB2();
        System.out.println("----------第一次查询------------");
        db1UserService.getAllDB1User().forEach(System.out::println);
        db2UserService.getAllDB2User().forEach(System.out::println);
        System.out.println("----------正常插入------------");
        User user = User.builder().id(1111L).name("gage").age(20).email("gagekeqi@sina.com").build();
        db1UserService.insertDB1User(user);
        db2UserService.insertDB2User(user);
        System.out.println("----------第二次查询------------");
        db1UserService.getAllDB1User().forEach(System.out::println);
        db2UserService.getAllDB2User().forEach(System.out::println);
        System.out.println("----------异常插入-----------");
        try{
            this.exceptionAdd();
        }catch (Exception ex){
            System.out.println(String.format("----------异常信息----------- %s", ex) );
        }

        System.out.println("----------异常插入后查询------------");
        db1UserService.getAllDB1User().forEach(System.out::println);
        db2UserService.getAllDB2User().forEach(System.out::println);
    }

}
