package cn.iselab.android.analysis.server.web.data.wrapper;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileTranslateVO;
import cn.iselab.android.analysis.server.web.data.VulForKivul;
import cn.iselab.android.analysis.server.web.data.VulnerabilityListVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class VunlerabilityListWrapper {
    public VulnerabilityListVO wrap(ArrayList<Vulnerability> list,String apk_id) {
        VulnerabilityListVO vo = new VulnerabilityListVO();
        ArrayList<VulForKivul> vul_list=new ArrayList<VulForKivul>();

        for(Vulnerability v:list){
            VulForKivul vfk=new VulForKivul();
            vfk.setApk_id(Integer.parseInt(apk_id));
            vfk.setVul_id(v.getId().intValue());
            vfk.setType_num(Integer.parseInt(getType(v.getCategory())));
            vfk.setType_name(v.getType());
            vfk.setName(v.getTitle());
            vfk.setDescription(v.getInfo());
            vfk.setAppearance_scene(warpDetail(v.getDetail()));
            vfk.setSolution(v.getSolution());
            vfk.setSeverity_points(changeLevelNum(v.getLevel()));
            vfk.setRisk_level(v.getRisk_level());
            vfk.setSource("android");
            vfk.setReference(v.getReference().split("\n"));
            vul_list.add(vfk);
        }

        vo.setList(vul_list);

        return vo;
    }

    private static String[] getURL(String urls){
        return null;
    }

    private static int changeLevelNum(String level){
        return 15-Integer.parseInt(level)*5;
    }

    private static String getLevel(String level){
        String re="";
        switch (level){
            case "1":re= "高";break;
            case "2":re= "中";break;
            case "3":re= "低";break;
        }
        return re;
    }

    private static String warpDetail(FormatFileTranslateVO detail){
        Gson gson = new Gson();
        return gson.toJson(detail);
    }

    private static String getType(String type){
        String x="";
        switch (type){
            case "Communication":x="1";break;
            case "Configuration":x="2";break;
            case "Cryptography":x="3";break;
            case "Permission":x="4";break;
            case "Database":x="5";break;
            case "Webview":x="6";break;
            case "Other":x="7";break;
        }
        return x;
    }
}
