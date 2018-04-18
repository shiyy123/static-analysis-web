package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.Apk;
import cn.iselab.android.analysis.server.web.data.ApkVO;

import java.util.ArrayList;

public interface ApkService {
    public Apk findApkByMD5(String MD5);
    public ArrayList<ApkVO> findAllApk();
    public void save(Apk apk);
    public int updateToAnalysis(String MD5);
    public int updateToFinish(String MD5);
}
