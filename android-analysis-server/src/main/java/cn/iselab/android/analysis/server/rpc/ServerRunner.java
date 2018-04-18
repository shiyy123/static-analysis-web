package cn.iselab.android.analysis.server.rpc;

import com.taocoder.ourea.core.config.ThriftServerConfig;
import com.taocoder.ourea.core.config.ZkConfig;
import com.taocoder.ourea.core.provider.ServiceProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author henrylee
 */
@Component
public class ServerRunner implements CommandLineRunner {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private RpcHandler handler;

    @Autowired
    private ThriftServerConfig serverConfig;

    @Autowired
    private ZkConfig zkConfig;

    @Autowired
    private ServiceProviderFactory serviceProviderFactory;

    @Override
    public void run(String... args) throws Exception {

    }

}