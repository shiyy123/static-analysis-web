package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.Vulnerability;

import java.io.IOException;
import java.util.ArrayList;

public interface ManifestService {
    public void AnlysisManifest(String MD5) throws Exception;
    public ArrayList<Vulnerability> getVulnerability();
}
