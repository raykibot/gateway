package com.luo.gateway.demo1.datasource.unpooled;


import com.luo.gateway.demo1.datasource.DataSource;
import com.luo.gateway.demo1.datasource.DataSourceFactory;
import com.luo.gateway.demo1.datasource.DataSourceType;
import com.luo.gateway.demo1.session.Configuration;

public class UnpooledDatasourceFactory implements DataSourceFactory {


    private UnpooledDataSource dataSource;

    public UnpooledDatasourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        dataSource.setConfiguration(configuration);
        dataSource.setDataSourceType(DataSourceType.Dubbo);
        dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

}
