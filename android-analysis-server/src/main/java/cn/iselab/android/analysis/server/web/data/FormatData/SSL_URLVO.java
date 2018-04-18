package cn.iselab.android.analysis.server.web.data.FormatData;

import java.util.ArrayList;

/**
 * Created by HenryLee on 2017/3/25.
 */
public class SSL_URLVO extends FormatFileVO {
    private String url;
    private ArrayList<CmdVO> class_list;

    public SSL_URLVO() {
    }

    public SSL_URLVO(String url, ArrayList<CmdVO> class_list) {
        this.url = url;
        this.class_list = class_list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<CmdVO> getClass_list() {
        return class_list;
    }

    public void setClass_list(ArrayList<CmdVO> class_list) {
        this.class_list = class_list;
    }
}
