package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileVO;

import java.util.ArrayList;

public interface FormatFileService {
    public ArrayList<FormatFileVO> getFormatFile(String MD5, String title);
    public String JudgeType(String title);
    public ArrayList<FormatFileVO> getFormatFileForKivul(String files,String title);
}
