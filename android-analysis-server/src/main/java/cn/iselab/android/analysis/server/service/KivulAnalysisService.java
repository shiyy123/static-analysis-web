package cn.iselab.android.analysis.server.service;


import cn.iselab.android.analysis.server.data.Vulnerability;

import java.util.ArrayList;

public interface KivulAnalysisService {
    public int analysis(String md5);
    public ArrayList<Vulnerability> getVulnerability();
}
