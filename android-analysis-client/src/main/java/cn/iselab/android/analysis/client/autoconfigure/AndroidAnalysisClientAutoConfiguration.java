package cn.iselab.android.analysis.client.autoconfigure;

import cn.iselab.android.analysis.thrift.AndroidAnalysisThrift;
import com.taocoder.ourea.core.config.ThriftClientConfig;
import com.taocoder.ourea.core.config.ZkConfig;
import com.taocoder.ourea.core.consumer.ConsumerProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author henrylee
 */
@Configuration
public class AndroidAnalysisClientAutoConfiguration {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Bean
    @ConditionalOnMissingBean(ConsumerProxyFactory.class)
    public ConsumerProxyFactory consumerProxyFactory() {
        return new ConsumerProxyFactory();
    }

    @Bean(name = "zkConfig")
    @ConditionalOnMissingBean(ZkConfig.class)
    public ZkConfig zkConfig(@Value("${rpcServer.zkAddress}") String zkAddress) {
        LOG.info("Using zkAddress {}", zkAddress);
        return new ZkConfig(zkAddress);
    }

    @Bean(name = "androidAnalysisThriftClientConfig")
    @ConfigurationProperties(prefix = "androidAnalysisThriftClientConfig")
    public ThriftClientConfig thriftClientConfig() {
        // !!!Attention: if not configured, use timeout=1000 & retryTimes=1
        // Please modify manually if your rpc service requires long time
        return new ThriftClientConfig();
    }

    @Bean(name = "androidAnalysisRpcClient")
    @ConditionalOnMissingBean(name = "androidAnalysisRpcClient")
    public AndroidAnalysisThrift.Iface createClient(ConsumerProxyFactory factory,
                                             @Qualifier("androidAnalysisThriftClientConfig") ThriftClientConfig thriftClientConfig,
                                             ZkConfig zkConfig) {
        return factory.getProxyClient(AndroidAnalysisThrift.Iface.class, thriftClientConfig, zkConfig);
    }

}
