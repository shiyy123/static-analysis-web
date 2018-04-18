package cn.iselab.android.analysis.server.web.data.FormatData;

import java.util.ArrayList;

/**
 * Created by HenryLee on 2017/3/24.
 */
public class FormatFileTranslateVO {
    private String type;
    private ArrayList<FormatFileVO> list;

    public FormatFileTranslateVO() {
    }

    public FormatFileTranslateVO(String type, ArrayList<FormatFileVO> list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<FormatFileVO> getList() {
        return list;
    }

    public void setList(ArrayList<FormatFileVO> list) {
        this.list = list;
    }
}
