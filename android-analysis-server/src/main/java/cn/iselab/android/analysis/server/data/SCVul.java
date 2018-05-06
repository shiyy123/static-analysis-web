package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SCVul {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long scId;

    @Column
    private String targetTaskId;

    @Column
    private String riskLevel;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215)
    private String solution;

    @Column
    private String vulType;

    @Column(columnDefinition = "MEDIUMTEXT", length = 16777215)
    private String extra;

    @Column(columnDefinition = "MEDIUMTEXT", length = 16777215)
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT", length = 16777215)
    private String description;

    @Column(columnDefinition = "MEDIUMTEXT", length = 16777215)
    private String vulClassify;

    @Column
    private String updateTime;

    @Column
    private String source;

    public SCVul(){
    }

    public SCVul(Long scId, String targetTaskId, String riskLevel, String solution, String vulType, String extra, String name, String description, String vulClassify, String updateTime, String source){
        super();
        this.scId = scId;
        this.targetTaskId = targetTaskId;
        this.riskLevel = riskLevel;
        this.solution = solution;
        this.vulType = vulType;
        this.extra = extra;
        this.name = name;
        this.description = description;
        this.vulClassify = vulClassify;
        this.updateTime = updateTime;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public String getTargetTaskId() {
        return targetTaskId;
    }

    public void setTargetTaskId(String targetTaskId) {
        this.targetTaskId = targetTaskId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getVulType() {
        return vulType;
    }

    public void setVulType(String vulType) {
        this.vulType = vulType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVulClassify() {
        return vulClassify;
    }

    public void setVulClassify(String vulClassify) {
        this.vulClassify = vulClassify;
    }
}
