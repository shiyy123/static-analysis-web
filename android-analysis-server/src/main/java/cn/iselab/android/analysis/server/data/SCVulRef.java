package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SCVulRef {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long vulId;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215)
    private String solution;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215)
    private String description;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215)
    private String location;

    public SCVulRef(){
    }

    public SCVulRef(Long vulId, String solution, String description, String location) {
        super();
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
