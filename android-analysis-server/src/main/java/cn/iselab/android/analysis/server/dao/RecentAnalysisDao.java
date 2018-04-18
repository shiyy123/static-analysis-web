package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.RecentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface RecentAnalysisDao extends JpaRepository<RecentAnalysis, Long> {
    public RecentAnalysis findByApkID(String apkID);
    public RecentAnalysis findById(Long id);
}
