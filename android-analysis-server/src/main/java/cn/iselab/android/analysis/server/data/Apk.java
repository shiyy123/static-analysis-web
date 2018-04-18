package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Apk {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String icon;

    @Column(nullable = false)
    private String md5;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String versionName;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String uploadTime;

    @Column(nullable = false)
    private String sha1;

    @Column(nullable = false)
    private String sha256;

    @Column(nullable = false)
    private String sdkVersion;

    public Apk(){

    }

    public Apk(String name, String label, String icon, String md5, String url,String platform,String versionName,String size,String status,String uploadTime,String sha1,String sha256,String sdkVersion) {
        super();
        this.name = name;
        this.label = label;
        this.icon = icon;
        this.md5 = md5;
        this.url = url;
        this.platform=platform;
        this.versionName=versionName;
        this.size=size;
        this.status=status;
        this.uploadTime=uploadTime;
        this.sha1=sha1;
        this.sha256=sha256;
        this.sdkVersion=sdkVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }
}
