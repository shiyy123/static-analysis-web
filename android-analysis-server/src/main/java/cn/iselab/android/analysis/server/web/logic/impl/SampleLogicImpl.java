package cn.iselab.android.analysis.server.web.logic.impl;

import cn.iselab.android.analysis.server.service.SampleService;
import cn.iselab.android.analysis.server.web.data.SampleDataVO;
import cn.iselab.android.analysis.server.web.data.wrapper.SampleDataWrapper;
import cn.iselab.android.analysis.server.web.logic.SampleLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author henrylee
 */
@Service
public class SampleLogicImpl implements SampleLogic {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SampleDataWrapper sampleDataWrapper;

    @Override
    public SampleDataVO save(String name) {
        return sampleDataWrapper.wrap(sampleService.save(name));
    }

    @Override
    public SampleDataVO get(long id) {
        return sampleDataWrapper.wrap(sampleService.get(id));
    }

}
