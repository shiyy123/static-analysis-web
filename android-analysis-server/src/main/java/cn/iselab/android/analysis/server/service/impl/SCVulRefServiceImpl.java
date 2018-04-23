package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.SCVulRefDao;
import cn.iselab.android.analysis.server.data.SCVulRef;
import cn.iselab.android.analysis.server.service.SCVulRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCVulRefServiceImpl implements SCVulRefService {
    @Autowired
    private SCVulRefDao scVulRefRepository;

    @Override
    public void save(SCVulRef scVulRef) {
        scVulRefRepository.save(scVulRef);
    }
}
