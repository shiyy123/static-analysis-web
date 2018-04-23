package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SC {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String appName;

    @Column
    private String md5;

    @Column
    private String platform;

    @Column
    private String status;

    @Column
    private String uuid;

    public SC(){

    }

    public SC(String appName, String md5, String platform, String status, String uuid){
        super();

        this.appName = appName;
        this.md5 = md5;
        this.platform = platform;
        this.status = status;
        this.uuid = uuid;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
