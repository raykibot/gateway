package com.luo.gateway.demo1.datasource;

import com.luo.gateway.demo1.session.Configuration;

import java.util.Properties;

public interface DataSourceFactory {

    DataSource getDataSource();

    void setProperties(Configuration configuration, String uri);

}
