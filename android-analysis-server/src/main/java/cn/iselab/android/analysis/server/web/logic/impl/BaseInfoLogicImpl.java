package cn.iselab.android.analysis.server.web.logic.impl;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.service.AaptService;
import cn.iselab.android.analysis.server.service.CertInfoService;
import cn.iselab.android.analysis.server.service.CombineService;
import cn.iselab.android.analysis.server.service.KivulService;
import cn.iselab.android.analysis.server.web.data.ApkForKivul;
import cn.iselab.android.analysis.server.web.logic.BaseInfoLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

@Service
public class BaseInfoLogicImpl implements BaseInfoLogic {

    @Autowired
    CombineService combineService;
    @Autowired
    KivulService kivulService;
    @Autowired
    CertInfoService certInfoService;
    @Autowired
    AaptService aaptService;

    @Override
    public ApkForKivul analysis(String url, String apkID) {
        String apkName=kivulService.downloadFile(url,apkID);
        if (apkName.equals("0")) {
            return null;
        }


        String apk_path = "H:/demo/apks/" + apkName;
        // 获取apk文件md5
        File f = new File(apk_path);
        String MD5 = kivulService.getMD5(f);
        String SHA1=kivulService.getSHA1(f);
        String SHA256=kivulService.getSHA256(f);

        boolean unzipSuccess=kivulService.unzip(apk_path, "H:/demo/zipped_apks/"+MD5);
        if(!unzipSuccess){
            //解压apk失败
            return null;
        }

        kivulService.copyFile(apk_path,"H:/demo/zipped_apks/"+MD5+"/"+MD5+".apk");

        // 获取文件大小
        DecimalFormat df = new DecimalFormat("#.##");
        String size = df.format(f.length() / 1024 / 1024) + "MB";

        // 使用aapt获取apk的应用名和图标路径、版本号，以及安全报告路径
        String[] baseInfo=this.getBaseInfo(MD5);
        String label=baseInfo[0];
        String icon=baseInfo[1];
        String versionName=baseInfo[2];
        String sdkVersion=baseInfo[3];
        String platform = baseInfo[4];

        //获取证书信息
        String info[]=certInfoService.getCertInfo(MD5);
        String status=info[0];
        String cert=info[1];

        return new ApkForKivul(apkID,icon,label,MD5,SHA1,SHA256,platform,apkName,size,url,versionName,sdkVersion,cert,status);
    }

    private String[] getBaseInfo(String MD5) {
        String[] re=new String[5];
        aaptService.analysis(MD5);
        re[0]=aaptService.getLabel();
        re[1]=aaptService.getIcon();
        re[2]=aaptService.getVersionName();
        re[3]=aaptService.getSdkVersion();
        re[4]="Android";
        return re;
    }

    @Override
    public ArrayList<Vulnerability> combine(ArrayList<Vulnerability> manifest, ArrayList<Vulnerability> androbugs) {
        return combineService.combine(manifest,androbugs);
    }
}
