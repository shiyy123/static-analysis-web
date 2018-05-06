package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.SCDao;
import cn.iselab.android.analysis.server.dao.SCVulDao;
import cn.iselab.android.analysis.server.dao.SCVulRefDao;
import cn.iselab.android.analysis.server.data.SCVulRef;
import cn.iselab.android.analysis.server.service.SCVulRefService;
import cn.iselab.android.analysis.server.web.data.SCVulRefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SCVulRefServiceImpl implements SCVulRefService {
    @Autowired
    private SCDao scDao;

    @Autowired
    private SCVulDao scVulDao;

    @Autowired
    private SCVulRefDao scVulRefRepository;

    @Override
    public void save(SCVulRef scVulRef) {
        scVulRefRepository.save(scVulRef);
    }

    @Override
    public ArrayList<SCVulRefVO> getSCVulRef(String MD5, String name) {
        ArrayList<SCVulRefVO> res = new ArrayList<>();
        Long scId = scDao.findByMd5(MD5).getId();
        Long scVulId = scVulDao.findByScIdAndName(scId, name).getId();
        ArrayList<SCVulRef> scVulRefs = scVulRefRepository.findByVulId(scVulId);
        for (SCVulRef scVulRef: scVulRefs) {
            res.add(new SCVulRefVO(scVulRef));
        }
        return res;
    }
}
