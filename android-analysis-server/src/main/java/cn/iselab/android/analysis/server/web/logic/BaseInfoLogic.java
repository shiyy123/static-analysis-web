package cn.iselab.android.analysis.server.web.logic;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.web.data.ApkForKivul;

import java.io.File;
import java.util.ArrayList;

public interface BaseInfoLogic {
    public ApkForKivul analysis(String url, String apkID);
    public ArrayList<Vulnerability> combine(ArrayList<Vulnerability> manifest,ArrayList<Vulnerability> androbugs);
}
