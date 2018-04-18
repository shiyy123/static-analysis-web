package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.RecentAnalysisDao;
import cn.iselab.android.analysis.server.data.RecentAnalysis;
import cn.iselab.android.analysis.server.service.RecentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecentAnalysisServiceImpl implements RecentAnalysisService {
    @Autowired
    private RecentAnalysisDao recentAnalysisDao;
    @Override
    public RecentAnalysis findRecentAnalysis(String apkID) {
        RecentAnalysis re=recentAnalysisDao.findByApkID(apkID);
        return re;
    }
}
