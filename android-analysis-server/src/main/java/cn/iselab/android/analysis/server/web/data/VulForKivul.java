package cn.iselab.android.analysis.server.web.data;

/**
 * Created by HenryLee on 2017/5/2.
 */
public class VulForKivul {
    private int apk_id;
    private int vul_id;
    private int type_num;
    private String type_name;
    private String name;
    private String description;
    private String appearance_scene;
    private String solution;
    private int severity_points;
    private String risk_level;
    private String source;
    private String[] reference;
    private int workerId;

    public int getApk_id() {
        return apk_id;
    }

    public void setApk_id(int apk_id) {
        this.apk_id = apk_id;
    }

    public int getVul_id() {
        return vul_id;
    }

    public void setVul_id(int vul_id) {
        this.vul_id = vul_id;
    }

    public int getType_num() {
        return type_num;
    }

    public void setType_num(int type_num) {
        this.type_num = type_num;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
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

    public String getAppearance_scene() {
        return appearance_scene;
    }

    public void setAppearance_scene(String appearance_scene) {
        this.appearance_scene = appearance_scene;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getSeverity_points() {
        return severity_points;
    }

    public void setSeverity_points(int severity_points) {
        this.severity_points = severity_points;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getReference() {
        return reference;
    }

    public void setReference(String[] reference) {
        this.reference = reference;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
}
