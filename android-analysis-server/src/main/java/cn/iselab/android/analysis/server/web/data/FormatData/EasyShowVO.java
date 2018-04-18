package cn.iselab.android.analysis.server.web.data.FormatData;

import java.util.ArrayList;

/**
 * Created by HenryLee on 2017/3/26.
 */
public class EasyShowVO extends FormatFileVO {
    private CmdVO method;
    private ArrayList<DetailCmdVO> used_list;
    private boolean isEmpty;

    public EasyShowVO() {
    }

    public EasyShowVO(CmdVO method, ArrayList<DetailCmdVO> used_list) {
        this.method = method;
        this.used_list = used_list;
        this.isEmpty=used_list.isEmpty();
    }

    public CmdVO getMethod() {
        return method;
    }

    public void setMethod(CmdVO method) {
        this.method = method;
    }

    public ArrayList<DetailCmdVO> getUsed_list() {
        return used_list;
    }

    public void setUsed_list(ArrayList<DetailCmdVO> used_list) {
        this.used_list = used_list;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
