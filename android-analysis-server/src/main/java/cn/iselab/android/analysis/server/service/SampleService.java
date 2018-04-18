package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.SampleData;

/**
 * @author henrylee
 */
public interface SampleService {

    SampleData save(String name);

    SampleData get(long id);

}
