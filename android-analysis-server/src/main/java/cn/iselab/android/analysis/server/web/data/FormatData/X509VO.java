package cn.iselab.android.analysis.server.web.data.FormatData;

import java.util.ArrayList;

/**
 * Created by HenryLee on 2017/3/25.
 */
public class X509VO extends FormatFileVO {
    private int isConfirm;
    private String class_name;
    private ArrayList<CmdVO> used_list;

    public X509VO() {
    }

    public X509VO(int isConfirm, String class_name, ArrayList<CmdVO> used_list) {
        this.isConfirm = isConfirm;
        this.class_name = class_name;
        this.used_list = used_list;
    }

    public int getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public ArrayList<CmdVO> getUsed_list() {
        return used_list;
    }

    public void setUsed_list(ArrayList<CmdVO> used_list) {
        this.used_list = used_list;
    }
}
