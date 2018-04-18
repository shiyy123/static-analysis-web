package cn.iselab.android.analysis.server.service;

import java.io.File;

/**
 * Created by HenryLee on 2017/4/9.
 */
public interface KivulService {
    public String downloadFile(String url,String apkID);
    public boolean unzip(String file, String path);
    public String findApkAndAnalysis(String path);
    public String getMD5(File file);
    public String getSHA1(File file);
    public String getSHA256(File file);
    public boolean copyFile(String oldPath,String newPath);
}
