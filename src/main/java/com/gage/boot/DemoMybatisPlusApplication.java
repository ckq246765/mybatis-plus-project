package com.gage.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@MapperScan("com.gage.boot.mapper") // 但数据源配置的全局扫描
public class DemoMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisPlusApplication.class, args);
    }

    /**
     * 自定义事务：
     * @return
     */
//    @Bean(name = "userTransactionManager")
//    public UserTransactionManager userTransactionManager () {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        return userTransactionManager;
//    }


    /**
     * 注入事物管理器
     * @return
     */
//    @Primary
//    @Bean(name = "xatx")
//    public JtaTransactionManager regTransactionManager () {
//
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        UserTransaction userTransaction = new UserTransactionImp();
//        return new JtaTransactionManager(userTransaction, userTransactionManager);
//    }

}
