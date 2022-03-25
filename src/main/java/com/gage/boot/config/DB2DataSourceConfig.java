package com.gage.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.gage.boot.mapper.db2.**"}
, sqlSessionFactoryRef = "db2SqlSessionFactory"
)
public class DB2DataSourceConfig {

    public static final String DATABASE_PREFIX = "spring.datasource.db2.";
    public static final String DATA_SOURCE_NAME = "db2DataSource";
    public static final String SQL_SESSION_FACTORY = "db2SqlSessionFactory";
    public static final String SQL_SESSION_TEMPLATE = "db2SqlSessionTemplate";
    public static final String SQL_TRANSACTION_MANAGER = "db2TxManger";

    /**
     * 配置数据源：
     * @param environment
     * @return
     */
    @Bean(DATA_SOURCE_NAME)
    public DataSource dataSource(Environment environment) {
        return DataSourceUtil.createAtomikosDataSourceBean(DATA_SOURCE_NAME, environment, DATABASE_PREFIX);
    }

    /**
     * 配置数据源工厂：
     * @param dataSource
     * @return
     * @throws Exception
     */
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
