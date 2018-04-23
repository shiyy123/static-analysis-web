package cn.iselab.android.analysis.server.web.data;

import cn.iselab.android.analysis.server.data.SCVul;

public class SCVulVO {

    private Long id;

    private Long scId;

    private String targetTaskId;

    private String riskLevel;

    private String solution;

    private String vulType;

    private String extra;

    private String name;

    private String description;

    private String updateTime;

    private String source;

    public SCVulVO(){
    }

    public SCVulVO(SCVul scVul){
        this.id = scVul.getId();
        this.scId = scVul.getScId();
        this.targetTaskId = scVul.getTargetTaskId();
        this.riskLevel = scVul.getRiskLevel();
        this.solution = scVul.getSolution();
        this.vulType = scVul.getVulType();
        this.extra = scVul.getExtra();
        this.name = scVul.getName();
        this.description = scVul.getDescription();
        this.updateTime = scVul.getUpdateTime();
        this.source = scVul.getSource();
    }

    public SCVulVO(Long id, Long scId, String targetTaskId, String riskLevel, String solution, String vulType, String extra, String name, String description, String updateTime, String source) {
        this.id = id;
        this.scId = scId;
        this.targetTaskId = targetTaskId;
        this.riskLevel = riskLevel;
        this.solution = solution;
        this.vulType = vulType;
        this.extra = extra;
        this.name = name;
        this.description = description;
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
}
