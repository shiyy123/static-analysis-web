package cn.iselab.android.analysis.server.service;

/**
 * Created by gg on 2017/2/28.
 */
public interface AaptService {
    public void analysis(String md5);
    public String getLabel();
    public String getVersionName();
    public String getIcon();
    public String getSdkVersion();
}

