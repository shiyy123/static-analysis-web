package cn.iselab.android.analysis.server.web.data.FormatData;

/**
 * C:class
 * M:method
 * D:descriptor
 * description: the cmd type of files in detail
 */
public class CmdVO extends FormatFileVO {
    private String class_name;
    private String method_name;
    private String descriptor;

    public CmdVO(String class_name, String method_name, String descriptor) {
        this.class_name = class_name;
        this.method_name = method_name;
        this.descriptor = descriptor;
    }

    public CmdVO() {
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
