package cn.iselab.android.analysis.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CheckList {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String level;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215, nullable = false)
    private String info;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215, nullable = true)
    private String reference;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215, nullable = true)
    private String solution;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition="MEDIUMTEXT", length = 16777215, nullable = false)
    private String info_ch;

    @Column(nullable = false)
    private String title_ch;

    @Column(nullable = false)
    private String risk_level;

    @Column(nullable = false)
    private String type;


    public CheckList(){}

    public CheckList(String title, String level, String info, String category) {
        super();
        this.title = title;
        this.level = level;
        this.info = info;
        this.category = category;
    }

    public CheckList(String title, String level, String info, String category,String reference) {
        super();
        this.title = title;
        this.level = level;
        this.info = info;
        this.category = category;
        this.reference=reference;
    }

    public CheckList(String title, String level, String info, String category,String reference,String title_ch,String info_ch) {
        super();
        this.title = title;
        this.level = level;
        this.info = info;
        this.category = category;
        this.reference=reference;
        this.title_ch=title_ch;
        this.info_ch=info_ch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInfo_ch() {
        return info_ch;
    }

    public void setInfo_ch(String info_ch) {
        this.info_ch = info_ch;
    }

    public String getTitle_ch() {
        return title_ch;
    }

    public void setTitle_ch(String title_ch) {
        this.title_ch = title_ch;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}