package cn.iselab.android.analysis.server.configure;

import com.taocoder.ourea.core.config.ThriftServerConfig;
import com.taocoder.ourea.core.config.ZkConfig;
import com.taocoder.ourea.core.provider.ServiceProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author henrylee
 */
@Configuration
@ConditionalOnProperty(name = "rpcServer.enabled", matchIfMissing = true)
public class RpcServerConfiguration {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Bean(name = "zkConfig")
    public ZkConfig zkConfig(@Value("${rpcServer.zkAddress}") String zkAddress) {
        LOG.info("Using zkAddress {}", zkAddress);
        return new ZkConfig(zkAddress);
    }


    @Bean
    @ConfigurationProperties(prefix = "rpcServer")
    public ThriftServerConfig serverConfig() {
        return new ThriftServerConfig();
    }

    @Bean
    public ServiceProviderFactory serviceProviderFactory() {
        return new ServiceProviderFactory();
    }

}