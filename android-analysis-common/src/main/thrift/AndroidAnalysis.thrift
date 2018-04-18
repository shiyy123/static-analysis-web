namespace java cn.iselab.android.analysis.thrift

include "RpcBase.thrift"

service AndroidAnalysisThrift {

    string sayHi(
        1: string name
    )

}

