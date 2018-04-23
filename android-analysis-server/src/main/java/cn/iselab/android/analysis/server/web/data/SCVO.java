package cn.iselab.android.analysis.server.web.data;

import cn.iselab.android.analysis.server.data.SC;

public class SCVO {
    private Long id;

    private String appName;

    private String md5;

    private String platform;

    private String status;

    private String uuid;

    public SCVO(){
    }

    public SCVO(Long id, String appName, String md5, String platform, String status, String uuid) {
        this.id = id;
        this.appName = appName;
        this.md5 = md5;
        this.platform = platform;
        this.status = status;
        this.uuid = uuid;
    }

    public SCVO(SC sc){
        this.id = sc.getId();
        this.appName = sc.getAppName();
        this.md5 = sc.getMd5();
        this.platform = sc.getPlatform();
        this.status = sc.getStatus();
        this.uuid = sc.getUuid();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
