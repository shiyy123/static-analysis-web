package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.RecentAnalysis;

/**
 * Created by gg on 2017/3/2.
 */
public interface RecentAnalysisService {
    public RecentAnalysis findRecentAnalysis(String apkID);
}
