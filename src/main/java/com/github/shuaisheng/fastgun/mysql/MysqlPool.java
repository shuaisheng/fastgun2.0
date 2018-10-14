package com.github.shuaisheng.fastgun.mysql;

import com.alibaba.druid.pool.DruidDataSource;


public class MysqlPool {

    /**
     * spring boot需要将自带的DataSourceAutoConfiguration禁掉
     * @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
     * @param url
     * @param user
     * @param password
     * @param driver
     */
    public MysqlPool (String url, String user, String password,String driver){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(user);
        datasource.setPassword(password);
        datasource.setDriverClassName(driver);

        // 初始化大小，最小，最大
        datasource.setInitialSize(1);
        datasource.setMinIdle(3);
        datasource.setMaxActive(20);
        // 配置获取连接等待超时的时间
        datasource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(30000);

        datasource.setValidationQuery("select 1");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);

        // 打开PSCache，并且指定每个连接上PSCache的大小
        datasource.setPoolPreparedStatements(true);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);

        /*try {
            // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            // filters: stat,wall,slf4j
            //datasource.setFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }

        // 合并多个DruidDataSource的监控数据
        // useGlobalDataSourceStat: true
        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        // connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
        datasource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");

        */
    }
}
