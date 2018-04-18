package cn.iselab.android.analysis.server.web.data.FormatData;

import java.util.ArrayList;

/**
 * Created by HenryLee on 2017/3/27.
 */
public class ProtectLevelVO extends FormatFileVO{
    private String permission;
    private ArrayList<ComponentVO> used_list;

    public ProtectLevelVO() {
    }

    public ProtectLevelVO(String permission, ArrayList<ComponentVO> used_list) {
        this.permission = permission;
        this.used_list = used_list;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public ArrayList<ComponentVO> getUsed_list() {
        return used_list;
    }

    public void setUsed_list(ArrayList<ComponentVO> used_list) {
        this.used_list = used_list;
    }
}
