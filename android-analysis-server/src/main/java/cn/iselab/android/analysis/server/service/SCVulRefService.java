package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.SCVulRef;
import cn.iselab.android.analysis.server.web.data.SCVulRefVO;

import java.util.ArrayList;

public interface SCVulRefService {
    public void save(SCVulRef scVulRef);
    public ArrayList<SCVulRefVO> getSCVulRef(String MD5, String name);
}
