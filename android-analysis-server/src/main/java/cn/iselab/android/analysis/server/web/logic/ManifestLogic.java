package cn.iselab.android.analysis.server.web.logic;

import cn.iselab.android.analysis.server.data.Vulnerability;

import java.util.ArrayList;

/**
 * Created by HenryLee on 2017/4/17.
 */
public interface ManifestLogic {
    public ArrayList<Vulnerability> analysis(String MD5) throws Exception;
}
