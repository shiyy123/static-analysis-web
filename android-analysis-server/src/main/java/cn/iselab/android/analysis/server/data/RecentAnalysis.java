package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RecentAnalysis {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String apkID;

    @Column(nullable = false)
    private String analysisTime;

    @Column(nullable = false)
    private String costTime;

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

    public String getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(String analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public RecentAnalysis() {
    }

    public RecentAnalysis( String apkID,String analysisTime, String costTime) {
        this.analysisTime = analysisTime;
        this.costTime = costTime;
        this.apkID = apkID;
    }
}
