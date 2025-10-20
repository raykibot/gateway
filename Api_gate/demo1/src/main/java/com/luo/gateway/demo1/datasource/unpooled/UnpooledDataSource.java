package com.luo.gateway.demo1.datasource.unpooled;

import com.luo.gateway.demo1.datasource.Connection;
import com.luo.gateway.demo1.datasource.DataSource;
import com.luo.gateway.demo1.datasource.DataSourceFactory;
import com.luo.gateway.demo1.datasource.DataSourceType;
import com.luo.gateway.demo1.datasource.connection.DubboConnection;
import com.luo.gateway.demo1.mapping.HttpStatement;
import com.luo.gateway.demo1.session.Configuration;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class UnpooledDataSource implements DataSource {

    private Configuration configuration;
    private HttpStatement httpStatement;

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public void setHttpStatement(HttpStatement httpStatement) {
        this.httpStatement = httpStatement;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private DataSourceType dataSourceType;


    @Override
    public Connection getConnection() {
        switch (dataSourceType) {
            case HTTP :
                //TODO 预留接口 后续处理
                break;
            case Dubbo:
                String application = httpStatement.getApplication();
                String interfaceName = httpStatement.getInterfaceName();
                ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
                RegistryConfig registryConfig = configuration.getRegistryConfig(application);
                ReferenceConfig<GenericService> referenceConfig = configuration.getReferenceConfig(interfaceName);
                return new DubboConnection(applicationConfig, registryConfig, referenceConfig);
            default:
                break;
        }
        throw new RuntimeException(dataSourceType + "没有数据源实现");
    }
}
