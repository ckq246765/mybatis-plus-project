package com.gage.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * db1数据库配置：
 */
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.gage.boot.mapper.db1.**"}
, sqlSessionFactoryRef = "db1SqlSessionFactory"
)
public class DB1DataSourceConfig {

    public static final String DATABASE_PREFIX = "spring.datasource.db1.";
    public static final String DATA_SOURCE_NAME = "db1DataSource";
    public static final String SQL_SESSION_FACTORY = "db1SqlSessionFactory";
    public static final String SQL_SESSION_TEMPLATE = "db1SqlSessionTemplate";
    public static final String SQL_TRANSACTION_MANAGER = "db1TxManger";


    /**
     * 通过配置文件创建DataSource，一个数据库对应一个DataSource
     * @param environment 环境变量，spring-boot会自动将IOC中的environment实例设置给本参数值
     * 由于IOC中有多个DataSource实例，必须给其中一个实例加上@Primary
     */
    @Primary
    @Bean(DATA_SOURCE_NAME)
    public DataSource dataSource(Environment environment) {
        return DataSourceUtil.createAtomikosDataSourceBean(DATA_SOURCE_NAME, environment, DATABASE_PREFIX);
    }

    /**
     * 通过dataSource创建SqlSessionFactory
     * 由于IOC中有多个DataSource实例，必须给其中一个实例加上@Primary
     */
    @Primary
    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource) throws Exception {
        return DataSourceUtil.createSqlSessionFactory(dataSource);
    }

    @Primary
    @Bean(name = SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory){
        return DataSourceUtil.createSqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置事务：
     * 事务会统一交给Atomikos全局事务，（因为是用了AtomikosDataSourceBean管理数据源），所以不能添加其他事务管理器
     * @param dataSource
     * @return
     */
//    @Primary
//    @Bean(name = SQL_TRANSACTION_MANAGER)
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource){
//        return DataSourceUtil.createDataSourceTransactionManager(dataSource);
//    }
}
