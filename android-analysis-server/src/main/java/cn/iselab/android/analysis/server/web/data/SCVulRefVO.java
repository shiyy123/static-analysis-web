package cn.iselab.android.analysis.server.web.data;

import cn.iselab.android.analysis.server.data.SCVulRef;

public class SCVulRefVO {
    private Long id;

    private Long vulId;

    private String solution;

    private String description;

    private String location;

    public SCVulRefVO(){
    }

    public SCVulRefVO(SCVulRef scVulRef) {
        this.id = scVulRef.getId();
        this.vulId = scVulRef.getVulId();
        this.solution = scVulRef.getSolution();
        this.description = scVulRef.getDescription();
        this.location = scVulRef.getLocation();
    }

    public SCVulRefVO(Long id, Long vulId, String solution, String description, String location) {
        this.id = id;
        this.vulId = vulId;
        this.solution = solution;
        this.description = description;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVulId() {
        return vulId;
    }

    public void setVulId(Long vulId) {
        this.vulId = vulId;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
