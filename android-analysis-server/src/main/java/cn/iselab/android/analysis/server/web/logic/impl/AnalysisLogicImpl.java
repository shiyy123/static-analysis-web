package cn.iselab.android.analysis.server.web.logic.impl;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.service.KivulAnalysisService;
import cn.iselab.android.analysis.server.web.logic.AnalysisLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AnalysisLogicImpl implements AnalysisLogic{

    @Autowired
    KivulAnalysisService kivulAnalysisService;

    @Override
    public ArrayList<Vulnerability> analysis(String MD5) {
        int result=kivulAnalysisService.analysis(MD5);
        if(result==0){
            return null;
        }
        return kivulAnalysisService.getVulnerability();
    }

}
