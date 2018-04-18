package cn.iselab.android.analysis.server.web.data;

/**
 * Created by HenryLee on 2017/4/18.
 */
public class ApkForKivul {
    private String apkID;
    private String icon;
    private String label;
    private String md5;
    private String sha1;
    private String sha256;
    private String platform;
    private String name;
    private String size;
    private String url;
    private String versionName;
    private String sdkVersion;
    private String certInfo;
    private String certStatus;

    public ApkForKivul() {
    }

    public ApkForKivul(String apkID, String icon, String label, String md5, String sha1, String sha256, String platform, String name, String size, String url, String versionName, String sdkVersion, String certInfo, String certStatus) {
        this.apkID = apkID;
        this.icon = icon;
        this.label = label;
        this.md5 = md5;
        this.sha1 = sha1;
        this.sha256 = sha256;
        this.platform = platform;
        this.name = name;
        this.size = size;
        this.url = url;
        this.versionName = versionName;
        this.sdkVersion = sdkVersion;
        this.certInfo = certInfo;
        this.certStatus = certStatus;
    }

    public String getApkID() {
        return apkID;
    }

    public void setApkID(String apkID) {
        this.apkID = apkID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo;
    }

    public String getCertStatus() {
        return certStatus;
    }

    public void setCertStatus(String certStatus) {
        this.certStatus = certStatus;
    }
}
