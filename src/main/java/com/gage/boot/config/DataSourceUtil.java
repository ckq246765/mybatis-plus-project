package com.gage.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public class DataSourceUtil {
    public static final String DATA_SOURCE_PREFIX = "spring.datasource.";

    /**
     * 创建AtomikosDataSourceBean是使用Atomikos连接池的首选类
     */
    public static AtomikosDataSourceBean createAtomikosDataSourceBean(String uniqueResourceName, Environment environment, String dataBase ){
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        // 这些设置大家可以进入源码中看java-doc
        // 数据源唯一标识
        atomikosDataSourceBean.setUniqueResourceName(uniqueResourceName);
        // XADataSource实现类，使用DruidXADataSource
        atomikosDataSourceBean.setXaDataSourceClassName(environment.getProperty(DATA_SOURCE_PREFIX+"type"));
        // 最小连接数，默认1
        atomikosDataSourceBean.setMinPoolSize(environment.getProperty(DATA_SOURCE_PREFIX+"min-pool-size", Integer.class));
        // 最大连接数，默认1
        atomikosDataSourceBean.setMaxPoolSize(environment.getProperty(DATA_SOURCE_PREFIX+"max-pool-size", Integer.class));
        // 设置连接在池中被自动销毁之前保留的最大秒数。 可选，默认为0（无限制）。
        atomikosDataSourceBean.setMaxLifetime(environment.getProperty(DATA_SOURCE_PREFIX+"max-life-time", Integer.class));
        // 返回连接前用于测试连接的SQL查询
        atomikosDataSourceBean.setTestQuery(environment.getProperty(DATA_SOURCE_PREFIX+"test-query"));


//        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
//        mysqlXADataSource.setDatabaseName(environment.getProperty(dataBase+"name"));
//        mysqlXADataSource.setURL(environment.getProperty(dataBase+"url"));
//        mysqlXADataSource.setUser(environment.getProperty(dataBase+"username"));
//        mysqlXADataSource.setPassword(environment.getProperty(dataBase+"password"));


        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setDbType("h2");
        druidXADataSource.setDriverClassName(environment.getProperty(dataBase+"driver-class-name"));
        druidXADataSource.setUrl(environment.getProperty(dataBase+"url"));
        druidXADataSource.setUsername(environment.getProperty(dataBase+"username"));
        druidXADataSource.setPassword(environment.getProperty(dataBase+"password"));

        atomikosDataSourceBean.setXaDataSource(druidXADataSource);

        return atomikosDataSourceBean;
    }

    /**
     * 创建SqlSessionFactory实例:
     *         SqlSession session = sqlSessionFactory.openSession();
     *         Connection connection = session.getConnection();
     *         Statement statement = connection.createStatement();
     *         statement.execute("CREATE TABLE user\n" +
     *                 "(\n" +
     *                 "    id BIGINT(20) NOT NULL COMMENT '主键ID',\n" +
     *                 "    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',\n" +
     *                 "    age INT(11) NULL DEFAULT NULL COMMENT '年龄',\n" +
     *                 "    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',\n" +
     *                 "    PRIMARY KEY (id)\n" +
     *                 ")");
     *         statement.execute("INSERT INTO user (id, name, age, email) VALUES\n" +
     *                 "(1, 'Jone', 18, 'test1@baomidou.com'),\n" +
     *                 "(2, 'Jack', 20, 'test2@baomidou.com'),\n" +
     *                 "(3, 'Tom', 28, 'test3@baomidou.com'),\n" +
     *                 "(4, 'Sandy', 21, 'test4@baomidou.com'),\n" +
     *                 "(5, 'Billie', 24, 'test5@baomidou.com')");
     */
    public static SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception{
        /**
         * 必须使用MybatisSqlSessionFactoryBean，
         * 不能使用SqlSessionFactoryBean，不然会报invalid bound statement (not found)
         *
         * com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration#sqlSessionFactory(javax.sql.DataSource)
         * 源码中也是使用MybatisSqlSessionFactoryBean
         * 并且源码中使用了@ConditionalOnMissingBean，即IOC中如果存在了SqlSessionFactory实例，mybatis-plus就不创建SqlSessionFactory实例了
         */
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        sessionFactoryBean.setConfiguration(configuration);
        return sessionFactoryBean.getObject();
    }

    public static SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    /**
     * 定义事务：
     * @param dataSource
     * @return
     */
    public static DataSourceTransactionManager createDataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        dataSourceTransactionManager.setDefaultTimeout(3000);
        return dataSourceTransactionManager;
    }

}
