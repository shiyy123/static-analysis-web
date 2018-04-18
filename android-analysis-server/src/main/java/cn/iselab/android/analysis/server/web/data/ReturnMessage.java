package cn.iselab.android.analysis.server.web.data;

/**
 * Created by HenryLee on 2017/5/4.
 */
public class ReturnMessage {
    private int status;
    private String message;

    public ReturnMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
