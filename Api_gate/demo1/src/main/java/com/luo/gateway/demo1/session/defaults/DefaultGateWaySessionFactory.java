package com.luo.gateway.demo1.session.defaults;

import com.luo.gateway.demo1.datasource.Connection;
import com.luo.gateway.demo1.datasource.DataSource;
import com.luo.gateway.demo1.datasource.DataSourceFactory;
import com.luo.gateway.demo1.datasource.unpooled.UnpooledDatasourceFactory;
import com.luo.gateway.demo1.executor.Executor;
import com.luo.gateway.demo1.executor.SimpleExecutor;
import com.luo.gateway.demo1.session.Configuration;
import com.luo.gateway.demo1.session.GateWaySession;
import com.luo.gateway.demo1.session.GateWaySessionFactory;

public class DefaultGateWaySessionFactory implements GateWaySessionFactory {


    private final Configuration configuration;

    public DefaultGateWaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GateWaySession openSession(String uri) {

        //获取数据源
        DataSourceFactory dataSourceFactory = new UnpooledDatasourceFactory();
        dataSourceFactory.setProperties(configuration,uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        Connection connection = dataSource.getConnection();

        Executor executor = configuration.newInstance(connection);
        return new DefaultGateWaySession(configuration,executor,uri);
    }
}
