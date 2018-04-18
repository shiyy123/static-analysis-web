package cn.iselab.android.analysis.server.web.data;

import cn.iselab.android.analysis.server.data.Apk;

public class ApkVO {
    private Long id;

    private String name;

    private String label;

    private String icon;

    private String md5;

    private String url;

    private String platform;

    private String versionName;

    private String size;

    private int isAnalysis;

    private String status;

    public ApkVO() {
    }

    public ApkVO(Long id, String name, String label, String icon, String md5, String url, String platform, String versionName, String size, int isAnalysis) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.icon = icon;
        this.md5 = md5;
        this.url = url;
        this.platform = platform;
        this.versionName = versionName;
        this.size = size;
        this.isAnalysis = isAnalysis;
    }

    public ApkVO(Apk apk){
        this.id = apk.getId();
        this.name = apk.getName();
        this.label = apk.getLabel();
        this.icon = apk.getIcon();
        this.md5 = apk.getMd5();
        this.url = apk.getUrl();
        this.platform = apk.getPlatform();
        this.versionName = apk.getVersionName();
        this.size = apk.getSize();
        this.status=apk.getStatus();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setIsAnalysis(int isAnalysis) {
        this.isAnalysis = isAnalysis;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public String getMd5() {
        return md5;
    }

    public String getUrl() {
        return url;
    }

    public String getPlatform() {
        return platform;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getSize() {
        return size;
    }

    public int getIsAnalysis() {
        return isAnalysis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
