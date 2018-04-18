package cn.iselab.android.analysis.server.rpc;

import cn.iselab.android.analysis.thrift.AndroidAnalysisThrift;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author henrylee
 */
@Service
public class RpcHandler implements AndroidAnalysisThrift.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(RpcHandler.class);

    @Override
    public String sayHi(String name) throws TException {
        return "Hello, " + name;
    }
}
