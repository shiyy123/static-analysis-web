package cn.iselab.android.analysis.server.web.data.FormatData;

/**
 * Created by HenryLee on 2017/3/25.
 */
public class FormatPerVO extends FormatFileVO {
    private String name;
    private String level;
    private String intro;
    private String work;

    public FormatPerVO() {
    }

    public FormatPerVO(String name, String level, String intro, String work) {
        this.name = name;
        this.level = level;
        this.intro = intro;
        this.work = work;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
