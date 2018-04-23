package cn.iselab.android.analysis.server.web.ctrl;

import java.util.ArrayList;
import java.util.List;

import cn.iselab.android.analysis.server.data.*;
import cn.iselab.android.analysis.server.service.*;
import cn.iselab.android.analysis.server.web.data.ApkVO;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileTranslateVO;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileVO;
import cn.iselab.android.analysis.server.web.data.SCVO;
import cn.iselab.android.analysis.server.web.data.SCVulVO;
import cn.iselab.android.analysis.server.web.data.VulnerabilityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalysisDataController {
    @Autowired
    private SCVulService scVulService;

    @Autowired
    private SCService scService;

    @Autowired
    private ApkService as;

    @Autowired
    private SecurityService ss;

    @Autowired
    private RecentAnalysisService recentAnalysisService;

    @Autowired
    private FormatFileService formatFileService;

    @Autowired
    private CertInfoService certInfoService;

//    @RequestMapping(value = "/apks", method = RequestMethod.GET)
//    public List<ApkVO> getApks(@RequestParam(value="userid", required=false) String userid) {
//        ArrayList<ApkVO> apks = as.findAllApk();
//        return apks;
//    }

    @RequestMapping(value = "/scs", method = RequestMethod.GET)
    public List<SCVO> getSCs(@RequestParam(value="userid", required=false) String userid) {
        return scService.findAllSC();
    }

    @RequestMapping(value = "/CertInfo", method = RequestMethod.GET)
    public CertInfo getCertInfo(@RequestParam("MD5") String MD5) {
        CertInfo c=certInfoService.getCert(MD5);
        return c;
    }

    @RequestMapping(value = "/apk", method = RequestMethod.GET)
    public Apk getApkInfo(@RequestParam("MD5") String MD5) {
        Apk apk=as.findApkByMD5(MD5);
        return apk;
    }

    @RequestMapping(value = "/sc", method = RequestMethod.GET)
    public SC getSCInfo(@RequestParam("MD5") String MD5) {
        return scService.findByMd5(MD5);
    }

    @RequestMapping(value = "/analysisTime", method = RequestMethod.GET)
    public RecentAnalysis getAnalysisTime(@RequestParam("MD5") String MD5) {
        Apk apk=as.findApkByMD5(MD5);
        System.out.println(MD5);
        String apkid=apk.getId()+"";
        System.out.println(apkid);
        RecentAnalysis re=recentAnalysisService.findRecentAnalysis(apkid);
        return re;
    }

    @RequestMapping(value = "/communication", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getCommunication(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Communication";
        return ss.getVulnerability(MD5,type);
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getConfiguration(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Configuration";
        return ss.getVulnerability(MD5,type);
    }

    @RequestMapping(value = "/cryptography", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getCryptography(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Cryptography";
        return ss.getVulnerability(MD5,type);
    }

    @RequestMapping(value = "/database", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getDatabase(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Database";
        return ss.getVulnerability(MD5,type);
    }
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getPermission(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Permission";
        return ss.getVulnerability(MD5,type);
    }
    @RequestMapping(value = "/webview", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getWebview(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Webview";
        return ss.getVulnerability(MD5,type);
    }
    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public ArrayList<VulnerabilityVO> getOther(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        String type="Other";
        return ss.getVulnerability(MD5,type);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public VulnerabilityVO getDetail(@RequestParam("MD5") String MD5,@RequestParam("title") String title) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        //Sss.getCommunication(MD5);
        VulnerabilityVO re=ss.getDetail(MD5,title);
        return re;
    }

    @RequestMapping(value = "/formatDetail", method = RequestMethod.GET)
    public FormatFileTranslateVO getFormatFiles(@RequestParam("MD5") String MD5, @RequestParam("title") String title){
        ArrayList<FormatFileVO> list=formatFileService.getFormatFile(MD5,title);
        FormatFileTranslateVO re=new FormatFileTranslateVO(formatFileService.JudgeType(title),list);
        return re;
    }

    /**
     * Process vulnerability belongs to the md5
     * @param MD5
     * @return
     */
    @RequestMapping(value = "/vulnerability", method = RequestMethod.GET)
    public ArrayList<SCVulVO> getAllVulnerability(@RequestParam("MD5") String MD5) {
        //MD5="9ad368a091028d3988429b92eaec36b9";
        return scVulService.getAllSCVul(MD5);
    }
//    @RequestMapping(value = "/vulnerability", method = RequestMethod.GET)
//    public ArrayList<VulnerabilityVO> getAllVulnerability(@RequestParam("MD5") String MD5) {
//        //MD5="9ad368a091028d3988429b92eaec36b9";
//        //Sss.getCommunication(MD5);
//        return ss.getAllVulnerability(MD5);
//    }
}

