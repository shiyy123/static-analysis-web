package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.SCVul;
import cn.iselab.android.analysis.server.web.data.SCVulVO;

import java.util.ArrayList;

public interface SCVulService {
    public ArrayList<SCVulVO> getAllSCVul(String md5);
    public Long save(SCVul scVul);
}
