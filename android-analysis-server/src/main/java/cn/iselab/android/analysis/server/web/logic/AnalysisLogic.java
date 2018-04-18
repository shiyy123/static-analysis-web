package cn.iselab.android.analysis.server.web.logic;

import cn.iselab.android.analysis.server.data.Vulnerability;

import java.util.ArrayList;

public interface AnalysisLogic {
    public ArrayList<Vulnerability> analysis(String MD5);
}
