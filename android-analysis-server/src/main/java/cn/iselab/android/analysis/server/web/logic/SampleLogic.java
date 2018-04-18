package cn.iselab.android.analysis.server.web.logic;

import cn.iselab.android.analysis.server.web.data.SampleDataVO;

/**
 * @author henrylee
 */
public interface SampleLogic {

    SampleDataVO save(String name);

    SampleDataVO get(long id);

}
