package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.dao.CheckListDao;
import cn.iselab.android.analysis.server.data.*;
import cn.iselab.android.analysis.server.service.FormatFileService;
import cn.iselab.android.analysis.server.service.KivulAnalysisService;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileTranslateVO;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service
public class KivulAnalysisServiceImpl implements KivulAnalysisService {

    @Autowired
    private CheckListDao checkListDao;

    @Autowired
    FormatFileService formatFileService;

    private String packageName;
    private String versionCode;
    private String versionName;
    private String minSDK;
    private String targetSDK;
    private String SHA1;
    private String SHA256;
    private String SHA512;
    private String encrypt_framework = "";
    private String framework = "";
    private String certMD5 = "";

    private ArrayList<String> critical ;
    private ArrayList<String> warning;
    private ArrayList<String> notice;
    private ArrayList<String> info;

    private ArrayList<String> title_c;
    private ArrayList<String> title_w;
    private ArrayList<String> title_n;
    private ArrayList<String> title_i;

    private ArrayList<Vulnerability> vulnerability_list;

    /**
     * @param MD5 the MD5 of the apk file
     */

    @Override
    public int analysis(String MD5) {//analysis the apk which has the same md5

        critical = new ArrayList<String>();
        warning = new ArrayList<String>();
        notice = new ArrayList<String>();
        info = new ArrayList<String>();

        title_c = new ArrayList<String>();
        title_w = new ArrayList<String>();
        title_n = new ArrayList<String>();
        title_i = new ArrayList<String>();

        vulnerability_list = new ArrayList<Vulnerability>();

        Runtime run = Runtime.getRuntime();
        BufferedReader br = null;
//        File report = new File("/home/cary/Test/demo/zipped_apks/" + MD5 + "/report.txt");
        File report = new File(ScanConst.DecompressFilePath + MD5 + "/report.txt");
        try {
            System.out.println("[Info] The apk had not been analyzed.");
            System.out.println("[Info] Start androbugs analysis...");
            Process process = run.exec("python /home/cary/Project/AndroBugs_Framework/androbugs.py -f "
                    + ScanConst.DecompressFilePath + MD5 + "/" + MD5 + ".apk" + " -o " + ScanConst.DecompressFilePath + MD5);
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF8"));
            String lineText = "";
            while ((lineText = br.readLine()) != null) {
                //System.out.println(lineText);
            }
            if (report.exists() && report.isFile()) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(report), "UTF8"));
                String text = "";
                int line = 1;
                boolean isTitleNext = false;
                char item = ' ';
                boolean isBaseInfo=true;
                while ((text = bf.readLine()) != null) {
                    //if is the end of base info part
                    if(text.equals("------------------------------------------------------------------------------------------------")){
                        isBaseInfo=false;
                        line++;
                        continue;
                    }
                    //if is base info part
                    if(isBaseInfo){
                        line++;
                        continue;
                    }

                    if (text.trim().equals("") && isTitleNext) {
                        line++;
                        if (item == 'C') {
                            critical.add("");
                        } else if (item == 'W') {
                            warning.add("");
                        } else if (item == 'N') {
                            notice.add("");
                        } else {
                            info.add("");
                        }
                        isTitleNext = false;
                        continue;
                    }
                    // the apk base information
                    /*if (line < 19) {//the line number may be changed in linux
                        switch (line) {
                            case 8:
                                packageName = text.substring(14);
                                break;
                            case 9:
                                versionName = text.substring(22);
                                break;
                            case 10:
                                versionCode = text.substring(22);
                                break;
                            case 11:
                                minSDK = text.substring(9);
                                break;
                            case 12:
                                targetSDK = text.substring(12);
                                break;
                            case 13:
                                certMD5 = text.substring(8);
                                break;
                            case 14:
                                SHA1 = text.substring(8);
                                break;
                            case 15:
                                SHA256 = text.substring(8);
                                break;
                            case 16:
                                SHA512 = text.substring(8);
                                break;
                        }
                        line++;
                        continue;
                    }*/
                    //the end of the report
                    if(text.equals("------------------------------------------------------------"))
                        break;
                    /*if (!text.equals("\n") && text.charAt(0) == '-') {
                        break;
                    }*/

                    if (text.charAt(0) == '[') {// if is title
                        isTitleNext = true;
                        item = text.charAt(1);
                        if (item == 'C') {
                            title_c.add(text);
                        } else if (item == 'W') {
                            title_w.add(text);
                        } else if (item == 'N') {
                            title_n.add(text);
                        } else {
                            title_i.add(text);
                        }
                    } else {// if is content(now is detail
                        text = text.replaceAll("               ", "");
                        if (isTitleNext) {//if is the first line of the content
                            //System.out.println("first line!!!!! "+text);
                            if (item == 'C') {
                                critical.add(text + "\n");
                            } else if (item == 'W') {
                                warning.add(text + "\n");
                            } else if (item == 'N') {
                                notice.add(text + "\n");
                            } else {
                                info.add(text + "\n");
                            }
                            isTitleNext = false;
                        } else {//if is not the first line of the content
                            if (item == 'C') {
                                String t = critical.get(critical.size() - 1);
                                String data = t + text + "\n";
                                critical.set(critical.size() - 1, data);
                            } else if (item == 'W') {
                                String t = warning.get(warning.size() - 1);
                                String data = t + text + "\n";
                                warning.set(warning.size() - 1, data);
                            } else if (item == 'N') {
                                String t = notice.get(notice.size() - 1);
                                String data = t + text + "\n";
                                notice.set(notice.size() - 1, data);
                            } else {
                                String t = info.get(info.size() - 1);
                                String data = t + text + "\n";
                                info.set(info.size() - 1, data);
                            }
                        }

                    }
                    line++;
                }
            }
            this.dealWithTitle();
            this.classify(MD5);
            System.out.println("[Info] Analysis finished.");
        } catch (Exception e) {
            System.out.println("Analysis Exception!");
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public ArrayList<Vulnerability> getVulnerability() {
        return vulnerability_list;
    }

    /**
     * @param MD5 tha md5 of the apk
     * @Description classify different level of vulnerability (dangerous, warning and normal
     */
    private void classify(String MD5) {
        System.out.println("[Info] Start classify all the vulnerabilities...");
        try {
            String title = "";
            String info = "";
            String category = "";
            String level = "";
            String reference = "";
            String solution = "";
            String risk_level="";
            String type="";
            Long id;
            System.out.println(MD5);
            for (String i : title_c) {
                CheckList cl = checkListDao.findByTitleAndLevel(i, "1");
                if (cl != null) {
                    id=cl.getId();
                    title = cl.getTitle_ch();
                    info = cl.getInfo_ch();
                    category = cl.getCategory();
                    level = cl.getLevel();
                    reference = cl.getReference();
                    solution = cl.getSolution();
                    risk_level=cl.getRisk_level();
                    type=cl.getType();
                    String files = this.getDetails(level, title_c.indexOf(i));
                    ArrayList<FormatFileVO> l = formatFileService.getFormatFileForKivul(files, i);
                    FormatFileTranslateVO detail = new FormatFileTranslateVO(formatFileService.JudgeType(i), l);
                    vulnerability_list.add(new Vulnerability(id,title, info, category, reference, level, solution, detail,risk_level,type));
                } else {
                    System.out.println("[Warning] Cannot find vulnerability : " + i);
                }
            }
            for (String i : title_w) {//classify the warning information
                CheckList cl = checkListDao.findByTitleAndLevel(i, "2");
                if (cl != null) {
                    id=cl.getId();
                    title = cl.getTitle_ch();
                    info = cl.getInfo_ch();
                    category = cl.getCategory();
                    level = cl.getLevel();
                    reference = cl.getReference();
                    solution = cl.getSolution();
                    solution = cl.getSolution();
                    risk_level=cl.getRisk_level();
                    type=cl.getType();
                    String files = this.getDetails(level, title_w.indexOf(i));
                    ArrayList<FormatFileVO> l = formatFileService.getFormatFileForKivul(files, i);
                    FormatFileTranslateVO detail = new FormatFileTranslateVO(formatFileService.JudgeType(i), l);
                    vulnerability_list.add(new Vulnerability(id,title, info, category, reference, level, solution, detail,risk_level,type));
                } else {
                    System.out.println("[Warning] Cannot find vulnerability : " + i);
                }
            }
            for (String i : title_n) {// classify the notice information
                CheckList cl = checkListDao.findByTitle(i);
                if (cl != null) {
                    id=cl.getId();
                    title = cl.getTitle_ch();
                    info = cl.getInfo_ch();
                    category = cl.getCategory();
                    level = cl.getLevel();
                    reference = cl.getReference();
                    solution = cl.getSolution();
                    solution = cl.getSolution();
                    risk_level=cl.getRisk_level();
                    type=cl.getType();
                    String files = this.getDetails(level, title_n.indexOf(i));
                    ArrayList<FormatFileVO> l = formatFileService.getFormatFileForKivul(files, i);
                    FormatFileTranslateVO detail = new FormatFileTranslateVO(formatFileService.JudgeType(i), l);
                    vulnerability_list.add(new Vulnerability(id,title, info, category, reference, level, solution, detail,risk_level,type));
                }
                //check the encryption framework and  MonoDroid framework
                else if (i.contains("Encryption Framework - Bangcle")) {
                    encrypt_framework = "Bangcle";
                } else if (i.contains("Encryption Framework - Ijiami")) {
                    encrypt_framework = "Ijiami";
                } else if (i.contains("Framework - MonoDroid")) {
                    framework = "MonoDroid";
                } else {
                    System.out.println("[Warning] Cannot find vulnerability : " + i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * @param type  the level of vulnerability
     * @param index the index of the vulnerability in the title list and the content list
     * @return the content of the vulnerability at the index of the list
     */
    private String getDetails(String type, int index) {
        String re = "";
        try {
            switch (type) {
                case "1":
                    re = critical.get(index);
                    break;
                case "2":
                    re = warning.get(index);
                    break;
                case "3":
                    re = notice.get(index);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return re;
    }

    /**
     * @Description format the title of the vulnerability
     */
    private void dealWithTitle() {// format the title of the vulnerability: remove the <> and []
        for (int i = 0; i < title_c.size(); ++i) {//critical
            String t = title_c.get(i);
            int index = t.lastIndexOf('>');
            if (index == -1)
                index = t.lastIndexOf(']');
            t = t.substring(index + 1, t.length() - 1).trim();
            //System.out.println(t);
            title_c.set(i, t);
        }
        for (int i = 0; i < title_w.size(); ++i) {//warning
            String t = title_w.get(i);
            int index = t.lastIndexOf('>');
            if (index == -1)
                index = t.lastIndexOf(']');
            t = t.substring(index + 1, t.length() - 1).trim();
            //System.out.println(t);
            title_w.set(i, t);
        }
        for (int i = 0; i < title_n.size(); ++i) {//notice
            String t = title_n.get(i);
            int index = t.lastIndexOf('>');
            if (index == -1)
                index = t.lastIndexOf(']');
            t = t.substring(index + 1, t.length() - 1).trim();
            //System.out.println(t);
            title_n.set(i, t);
        }
        for (int i = 0; i < title_i.size(); ++i) {//info
            String t = title_i.get(i);
            int index = t.lastIndexOf('>');
            if (index == -1)
                index = t.lastIndexOf(']');
            t = t.substring(index + 1, t.length() - 1).trim();
            //System.out.println(t);
            title_i.set(i, t);
        }
    }
}

