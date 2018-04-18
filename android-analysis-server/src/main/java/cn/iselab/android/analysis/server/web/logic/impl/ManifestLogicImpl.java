package cn.iselab.android.analysis.server.web.logic.impl;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.service.ManifestService;
import cn.iselab.android.analysis.server.web.logic.ManifestLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ManifestLogicImpl implements ManifestLogic {

    @Autowired
    ManifestService manifestService;

    @Override
    public ArrayList<Vulnerability> analysis(String MD5) throws Exception{
        manifestService.AnlysisManifest(MD5);
        return manifestService.getVulnerability();
    }
}
