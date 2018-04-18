package cn.iselab.android.analysis.server.web.data.FormatData;

/**
 * Created by HenryLee on 2017/3/25.
 */
public class DetailCmdVO extends FormatFileVO {
    private String type;

    private String info_var;
    private String path_idx;
    private String dst_class_name;
    private String dst_method_name;
    private String dst_descriptor;

    private String src_class_name;
    private String src_method_name;
    private String src_descriptor;

    public DetailCmdVO() {}

    public DetailCmdVO(String info_var, String path_idx, String dst_class_name, String dst_method_name, String dst_descriptor) {
        this.type="dstOnly";
        this.info_var=info_var;
        this.path_idx = path_idx;
        this.dst_class_name = dst_class_name;
        this.dst_method_name = dst_method_name;
        this.dst_descriptor = dst_descriptor;
    }

    public DetailCmdVO(String path_idx, String dst_class_name, String dst_method_name, String dst_descriptor, String src_class_name, String src_method_name, String src_descriptor) {
        this.type="dst_src";
        this.path_idx = path_idx;
        this.dst_class_name = dst_class_name;
        this.dst_method_name = dst_method_name;
        this.dst_descriptor = dst_descriptor;
        this.src_class_name = src_class_name;
        this.src_method_name = src_method_name;
        this.src_descriptor = src_descriptor;
    }

    public DetailCmdVO(String path_idx, String src_class_name, String src_method_name, String src_descriptor) {
        this.type="srcOnly";
        this.path_idx = path_idx;
        this.src_class_name = src_class_name;
        this.src_method_name = src_method_name;
        this.src_descriptor = src_descriptor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo_var() {
        return info_var;
    }

    public void setInfo_var(String info_var) {
        this.info_var = info_var;
    }

    public String getPath_idx() {
        return path_idx;
    }

    public void setPath_idx(String path_idx) {
        this.path_idx = path_idx;
    }

    public String getDst_class_name() {
        return dst_class_name;
    }

    public void setDst_class_name(String dst_class_name) {
        this.dst_class_name = dst_class_name;
    }

    public String getDst_method_name() {
        return dst_method_name;
    }

    public void setDst_method_name(String dst_method_name) {
        this.dst_method_name = dst_method_name;
    }

    public String getDst_descriptor() {
        return dst_descriptor;
    }

    public void setDst_descriptor(String dst_descriptor) {
        this.dst_descriptor = dst_descriptor;
    }

    public String getSrc_class_name() {
        return src_class_name;
    }

    public void setSrc_class_name(String src_class_name) {
        this.src_class_name = src_class_name;
    }

    public String getSrc_descriptor() {
        return src_descriptor;
    }

    public void setSrc_descriptor(String src_descriptor) {
        this.src_descriptor = src_descriptor;
    }

    public String getSrc_method_name() {
        return src_method_name;
    }

    public void setSrc_method_name(String src_method_name) {
        this.src_method_name = src_method_name;
    }
}
