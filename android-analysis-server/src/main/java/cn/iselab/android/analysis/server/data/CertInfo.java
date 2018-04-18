package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CertInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String apkID;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215, nullable = false)
    private String cert;

    public CertInfo() {
    }

    public CertInfo(String apkID, String status, String cert) {
        this.apkID = apkID;
        this.status = status;
        this.cert = cert;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApkID() {
        return apkID;
    }

    public void setApkID(String apkID) {
        this.apkID = apkID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
}
