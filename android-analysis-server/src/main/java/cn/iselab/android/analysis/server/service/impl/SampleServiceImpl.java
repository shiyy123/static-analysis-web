package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.SampleDao;
import cn.iselab.android.analysis.server.data.SampleData;
import cn.iselab.android.analysis.server.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author henrylee
 */
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDao sampleDao;

    @Override
    public SampleData save(String name) {
        SampleData newSample = new SampleData();
        newSample.setName(name);
        return sampleDao.save(newSample);
    }

    @Override
    public SampleData get(long id) {
        return sampleDao.findOne(id);
    }

}
