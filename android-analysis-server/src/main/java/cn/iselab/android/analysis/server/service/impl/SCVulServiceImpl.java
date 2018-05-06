package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.SCDao;
import cn.iselab.android.analysis.server.dao.SCVulDao;
import cn.iselab.android.analysis.server.data.SC;
import cn.iselab.android.analysis.server.data.SCVul;
import cn.iselab.android.analysis.server.service.SCVulService;
import cn.iselab.android.analysis.server.web.data.SCVulVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SCVulServiceImpl implements SCVulService {
    @Autowired
    private SCDao scDao;

    @Autowired
    private SCVulDao scVulDao;

    @Autowired
    private SCVulDao scVulRepository;

    @Override
    public ArrayList<SCVulVO> getAllSCVul(String md5) {
        ArrayList<SCVulVO> scVulVOArrayList = new ArrayList<>();
        Long scId = scDao.findByMd5(md5).getId();
        List<SCVul> scVuls = scVulDao.findByScId(scId);
        for(SCVul scVul: scVuls){
            scVulVOArrayList.add(new SCVulVO(scVul));
        }
        return scVulVOArrayList;
    }

    @Override
    public Long save(SCVul scVul) {
        SCVul scVul1  = scVulRepository.save(scVul);
        return scVul1.getId();
    }

    @Override
    public SCVulVO getDetail(String MD5, String name) {
        Long scId = scDao.findByMd5(MD5).getId();
        return new SCVulVO(scVulDao.findByScIdAndName(scId, name));
    }
}
