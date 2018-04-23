package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.SCDao;
import cn.iselab.android.analysis.server.data.RecentAnalysis;
import cn.iselab.android.analysis.server.data.SC;
import cn.iselab.android.analysis.server.service.SCService;
import cn.iselab.android.analysis.server.web.data.SCVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SCServiceImpl implements SCService {
    @Autowired
    private SCDao scRepository;

    @Override
    public SC findByMd5(String md5) {
        return scRepository.findByMd5(md5);
    }

    @Override
    public void save(SC sc){
        scRepository.save(sc);
    }

    @Override
    public ArrayList<SCVO> findAllSC() {
        ArrayList<SCVO> re = new ArrayList<>();
        List<SC> l = scRepository.findAll();
        for (SC sc : l){
            SCVO scvo = new SCVO(sc);
            // TODO Judge whether the file analysis before
//            long id=sc.getId();
//            RecentAnalysis recent=recentAnalysisDao.findByApkID(id+"");
//            if(recent==null){
//                av.setIsAnalysis(0);
//            }else{
//                av.setIsAnalysis(1);
//            }
            re.add(scvo);
        }
        return re;
    }
}
