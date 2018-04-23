package cn.iselab.android.analysis.server.service;

import cn.iselab.android.analysis.server.data.SC;
import cn.iselab.android.analysis.server.web.data.SCVO;

import java.util.ArrayList;

public interface SCService {
    public SC findByMd5(String md5);
    public void save(SC sc);
    public ArrayList<SCVO> findAllSC();
}
