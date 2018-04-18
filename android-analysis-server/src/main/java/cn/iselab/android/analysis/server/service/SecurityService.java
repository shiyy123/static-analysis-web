package cn.iselab.android.analysis.server.service;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileVO;
import cn.iselab.android.analysis.server.web.data.VulnerabilityVO;

import java.util.ArrayList;

public interface SecurityService {
    public ArrayList<VulnerabilityVO> getVulnerability(String md5, String type);
    public VulnerabilityVO getDetail(String MD5,String title);
    public ArrayList<VulnerabilityVO> getAllVulnerability(String md5);

}
