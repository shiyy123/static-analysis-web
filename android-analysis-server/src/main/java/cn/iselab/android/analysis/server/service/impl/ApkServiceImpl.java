package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.ApkDao;
import cn.iselab.android.analysis.server.dao.RecentAnalysisDao;
import cn.iselab.android.analysis.server.data.Apk;
import cn.iselab.android.analysis.server.data.RecentAnalysis;
import cn.iselab.android.analysis.server.service.ApkService;
import cn.iselab.android.analysis.server.web.data.ApkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApkServiceImpl implements ApkService {
    @Autowired
    private ApkDao apkRepository;
    @Autowired
    private RecentAnalysisDao recentAnalysisDao;


    /**
     *
     * @param MD5 the MD5 of the apk to find
     * @return the entity of the apk
     */
    @Override
    public Apk findApkByMD5(String MD5) {
        Apk apk=apkRepository.findByMd5(MD5);
        return apk;
    }

    /**
     *
     * @return the list of  all the apks
     */
    @Override
    public ArrayList<ApkVO> findAllApk() {
        ArrayList<ApkVO> re= new ArrayList<ApkVO>();
        List<Apk> l=apkRepository.findAll();
        for(Apk a:l){
            ApkVO av=new ApkVO(a);
            long id=a.getId();
            RecentAnalysis recent=recentAnalysisDao.findByApkID(id+"");
            if(recent==null){
                av.setIsAnalysis(0);
            }else{
                av.setIsAnalysis(1);
            }
            re.add(av);
        }
        return re;
    }

    /**
     *
     * @param apk the apk to be saved to the database
     */
    @Override
    public void save(Apk apk) {
        apkRepository.save(apk);
    }

    /**
     *
     * @param MD5 the MD5 of apk
     * @return the result of update the status of the apk to is analyzing
     */
    @Override
    public int updateToAnalysis(String MD5) {
        Apk apk=apkRepository.findByMd5(MD5);
        if(apk==null) return 0;
        return apkRepository.update("0",MD5);
    }

    /**
     *
     * @param MD5 the MD5 of apk
     * @return the result of update the status of the apk to finished analyzing
     */
    @Override
    public int updateToFinish(String MD5) {
        return apkRepository.update("1",MD5);
    }
}
