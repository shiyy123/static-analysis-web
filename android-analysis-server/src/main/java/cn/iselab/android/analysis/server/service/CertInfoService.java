package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.CertInfo;

/**
 * Created by gg on 2017/3/2.
 */
public interface CertInfoService {
    public String[] getCertInfo(String MD5);
    public void save(CertInfo certInfo);
    public CertInfo getCert(String MD5);
}
