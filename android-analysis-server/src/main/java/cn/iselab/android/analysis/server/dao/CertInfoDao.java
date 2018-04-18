package cn.iselab.android.analysis.server.dao;

import cn.iselab.android.analysis.server.data.CertInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CertInfoDao extends JpaRepository<CertInfo, Long> {
    public CertInfo findByApkID(String apkID);
}
