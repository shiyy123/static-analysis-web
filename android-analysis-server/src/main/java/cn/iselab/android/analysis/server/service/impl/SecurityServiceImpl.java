package cn.iselab.android.analysis.server.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.iselab.android.analysis.server.dao.ApkDao;
import cn.iselab.android.analysis.server.dao.Apk_vulnerabilityDao;
import cn.iselab.android.analysis.server.dao.CheckListDao;
import cn.iselab.android.analysis.server.dao.RecentAnalysisDao;
import cn.iselab.android.analysis.server.data.*;
import cn.iselab.android.analysis.server.service.SecurityService;
import cn.iselab.android.analysis.server.web.data.VulnerabilityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private CheckListDao checkListDao;
    @Autowired
    private ApkDao apkDao;
    @Autowired
    private Apk_vulnerabilityDao apkVulnerabilityDao;
    @Autowired
    private RecentAnalysisDao recentAnalysisDao;

    @Override
    public ArrayList<VulnerabilityVO> getVulnerability(String MD5, String type){
        ArrayList<VulnerabilityVO> re=new ArrayList<VulnerabilityVO>();
        Long apkID= apkDao.findByMd5(MD5).getId();
        Apk_vulnerability avs[]= apkVulnerabilityDao.findByApkID(apkID+"");
        for(Apk_vulnerability av:avs){
            String checkID=av.getCheckID();
            CheckList cl= checkListDao.findById(Long.parseLong(checkID));
            String category=cl.getCategory();
            String title=cl.getTitle();
            String title_en=cl.getTitle_ch();
            String level=cl.getLevel();
            String info=cl.getInfo();
            String info_ch=cl.getInfo_ch();
            String reference=cl.getReference();
            String solution=cl.getSolution();
            String files=av.getFiles();
            if(category.equals(type)){
                re.add(new VulnerabilityVO(title,level,info,files,reference,solution,title_en,info_ch,category));
            }
        }
        return re;
    }

    @Override
    public ArrayList<VulnerabilityVO> getAllVulnerability(String MD5){
        ArrayList<VulnerabilityVO> re=new ArrayList<VulnerabilityVO>();
        Long apkID= apkDao.findByMd5(MD5).getId();
        Apk_vulnerability avs[]= apkVulnerabilityDao.findByApkID(apkID+"");
        for(Apk_vulnerability av:avs){
            String checkID=av.getCheckID();
            CheckList cl= checkListDao.findById(Long.parseLong(checkID));
            String category=cl.getCategory();
            String title=cl.getTitle();
            String title_en=cl.getTitle_ch();
            String level=cl.getLevel();
            String info=cl.getInfo();
            String info_ch=cl.getInfo_ch();
            String reference=cl.getReference();
            String solution=cl.getSolution();
            String files=av.getFiles();
            re.add(new VulnerabilityVO(title,level,info,files,reference,solution,title_en,info_ch,category));
        }
        return re;
    }

    public VulnerabilityVO getDetail(String MD5,String title){
        //System.out.println(title);
        VulnerabilityVO re=null;
        CheckList cl= checkListDao.findByTitle(title);
        Apk apk= apkDao.findByMd5(MD5);
        String apkID=apk.getId()+"";
        if(cl!=null){
            String checkID=cl.getId()+"";
            String category=cl.getCategory();
            String t=cl.getTitle();
            String title_en=cl.getTitle_ch();
            String info=cl.getInfo();
            String info_ch=cl.getInfo_ch();
            String level=cl.getLevel();
            String reference=cl.getReference();
            String solution=cl.getSolution();
            String files= apkVulnerabilityDao.findByApkIDAndCheckID(apkID, checkID).getFiles();
            re=new VulnerabilityVO(t,level,info,files,reference,solution,title_en,info_ch,category);
        }
        return re;
    }
}

