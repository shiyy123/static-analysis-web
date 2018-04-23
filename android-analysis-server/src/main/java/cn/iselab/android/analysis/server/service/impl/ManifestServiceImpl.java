package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.dao.CheckListDao;
import cn.iselab.android.analysis.server.data.CheckList;
import cn.iselab.android.analysis.server.data.Dvm_permission;
import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.service.ManifestService;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.iselab.android.analysis.server.web.data.FormatData.ComponentVO;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileTranslateVO;
import cn.iselab.android.analysis.server.web.data.FormatData.FormatFileVO;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManifestServiceImpl implements ManifestService {
    // permission and it's detail are one to one correspondence
    private ArrayList<String[]> permission_with_detail;
    private ArrayList<String> permission;

    private ArrayList<String> activity;
    private ArrayList<String> service;
    private ArrayList<String> provider;
    private ArrayList<String> receiver;
    private ArrayList<String> library;
    private ArrayList<String> exported;
    private ArrayList<String> vulnerability;

    ArrayList<ComponentVO> listForTaskAffinity;
    ArrayList<ComponentVO> listForLaunchMode;
    ArrayList<ComponentVO> listForIntent;
    ArrayList<ComponentVO> listForAction;
    ArrayList<ComponentVO> listForGrantUriPermission;
    ArrayList<ComponentVO> listForSecretCode;
    ArrayList<ComponentVO> listForSMS;

    private String mainAct;
    private String versionCode;
    private String versionName;
    private String packageName;
    private String minSDK;
    private String targetSDK;
    private String maxSDK;
    private String MD5;
    private String f;// file name

    private Document doc;

    @Autowired
    CheckListDao checkListDao;

    /**
     * @param MD5 the MD5 of the apk
     */
    public void AnlysisManifest(String MD5) throws Exception {
        //this.f = file;
        mainAct = "";
        versionCode = "";
        versionName = "";
        packageName = "";
        minSDK = "";
        targetSDK = "";
        maxSDK = "";
        permission_with_detail = new ArrayList<String[]>();
        permission = new ArrayList<String>();
        activity = new ArrayList<String>();
        service = new ArrayList<String>();
        provider = new ArrayList<String>();
        receiver = new ArrayList<String>();
        library = new ArrayList<String>();
        exported = new ArrayList<String>();
        vulnerability = new ArrayList<String>();

        listForTaskAffinity = new ArrayList<ComponentVO>();
        listForLaunchMode = new ArrayList<ComponentVO>();
        listForIntent = new ArrayList<ComponentVO>();
        listForAction = new ArrayList<ComponentVO>();
        listForSecretCode = new ArrayList<ComponentVO>();
        listForGrantUriPermission = new ArrayList<ComponentVO>();
        listForSMS = new ArrayList<ComponentVO>();

        this.MD5 = MD5;

        PrintWriter pw = null;
        BufferedReader reader = null;
        Process process = null;
        StringBuffer bf = null;
        // unzip(file,"I:/demo/zipped_apks/"+file);
        File f = new File(ScanConst.DecompressFilePath + MD5 + "/output.xml");
        Runtime run = Runtime.getRuntime();
        process = run.exec("java -jar ./android-analysis-server/src/main/resources/tools/APKParser.jar "
                + ScanConst.DecompressFilePath + MD5 + "/" + MD5 + ".apk");
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        bf = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            bf.append(line);
            bf.append("\n");
        }
        pw = new PrintWriter(new FileOutputStream(f));
        pw.write(bf.toString());
        pw.flush();
        pw.close();
        this.get_manifest();
        this.analysis_manifest();
    }

    /**
     * @return the list of vulnerability I analyzed
     */
    @Override
    public ArrayList<Vulnerability> getVulnerability() {
        ArrayList<Vulnerability> re = new ArrayList<Vulnerability>();
        for (String v : vulnerability) {
            CheckList cl = checkListDao.findByTitle(v);
            ArrayList<FormatFileVO> list = new ArrayList<FormatFileVO>();
            if (v.equals("Task Affinity Checking")) {
                for (ComponentVO cv : listForTaskAffinity) {
                    list.add(cv);
                }
            } else if (v.equals("Launch Mode Checking")) {
                for (ComponentVO cv : listForLaunchMode) {
                    list.add(cv);
                }
            } else if (v.equals("Intent Priority Checking")) {
                for (ComponentVO cv : listForIntent) {
                    list.add(cv);
                }
            } else if (v.equals("Improper Content Provider Permissions Checking")) {
                for (ComponentVO cv : listForGrantUriPermission) {
                    list.add(cv);
                }
            } else if (v.equals("Action Priority Checking")) {
                for (ComponentVO cv : listForAction) {
                    list.add(cv);
                }
            } else if (v.equals("SMS Receiver Checking")) {
                for (ComponentVO cv : listForSMS) {
                    list.add(cv);
                }
            } else if (v.equals("Android Secret Code Checking")) {
                for (ComponentVO cv : listForSecretCode) {
                    list.add(cv);
                }
            }
            if (cl != null) {
                Long id = cl.getId();
                String title = cl.getTitle_ch();
                String info = cl.getInfo_ch();
                String category = cl.getCategory();
                String level = cl.getLevel();
                String reference = cl.getReference();
                String solution = cl.getSolution();
                FormatFileTranslateVO detail = new FormatFileTranslateVO("component", list);
                String risk_level=cl.getRisk_level();
                String type=cl.getType();
                re.add(new Vulnerability(id, title, info, category, reference, level, solution, detail,risk_level,type));
            }
        }
        return re;
    }

    /**
     * 解析manifest，获取一些基本元素信息
     */
    private void get_manifest() throws Exception{
        File file = new File(ScanConst.ScanFilePath + MD5 + "/output.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        this.doc = document;
        Element root = document.getRootElement();
        List perm = root.elements("uses-permission");

        Element app = root.element("application");
        List act = app.elements("activity");
        List svc = app.elements("service");
        List pro = app.elements("provider");
        List rec = app.elements("receiver");
        List lib = app.elements("uses-library");
        Element sdk = root.element("uses-sdk");

        // permission
        for (Iterator it = perm.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Attribute a = elm.attribute("name");
            permission.add(a.getText());
        }

        for (String i : permission) {
            if (i.indexOf(".") != -1) {
                String per = i.substring(i.lastIndexOf(".") + 1);
                if (Dvm_permission.getManifest_permission().containsKey(per)) {
                    permission_with_detail.add(Dvm_permission.getManifest_permission().get(per));
                } else {
                    String[] temp = {"dangerous", "Unknown permission from android reference",
                            "Unknown permission from android reference"};
                    permission_with_detail.add(temp);
                }
            }
        }

        // activity
        for (Iterator it = act.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Attribute a = elm.attribute("name");
            activity.add(a.getText());
            // TODO fix if more than one MAIN
            if (mainAct.length() < 1) {
                Element ee = elm.element("intent-filter");// intent-filter
                // node
                if (ee == null)
                    continue;
                List action = ee.elements("action");
                for (Iterator itt = action.iterator(); itt.hasNext(); ) {
                    Element el = (Element) itt.next();
                    String val = el.attributeValue("name");
                    if (val.equals("android.intent.action.MAIN")) {
                        mainAct = a.getText();
                    }
                }
                if (mainAct.equals("")) {
                    List category = ee.elements("category");
                    for (Iterator itt = category.iterator(); itt.hasNext(); ) {
                        Element el = (Element) itt.next();
                        String val = el.attributeValue("name");
                        if (val.equals("android.intent.category.LAUNCHER")) {
                            mainAct = a.getText();
                        }
                    }
                }
            }
        }

        // service
        for (Iterator it = svc.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Attribute a = elm.attribute("name");
            service.add(a.getText());
        }

        // provider
        for (Iterator it = pro.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Attribute a = elm.attribute("name");
            provider.add(a.getText());
        }

        // receiver
        for (Iterator it = rec.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Attribute a = elm.attribute("name");
            receiver.add(a.getText());
        }

        // uses-library
        for (Iterator it = lib.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Attribute a = elm.attribute("name");
            library.add(a.getText());
        }

        minSDK = sdk.attribute("minSdkVersion") == null ? "" : sdk.attribute("minSdkVersion").getText();
        maxSDK = sdk.attribute("maxSdkVersion") == null ? "" : sdk.attribute("maxSdkVersion").getText();
        targetSDK = sdk.attribute("targetSdkVersion") == null ? "" : sdk.attribute("targetSdkVersion").getText();
        versionCode = root.attribute("versionCode") == null ? "" : root.attribute("versionCode").getText();
        versionName = root.attribute("versionName") == null ? "" : root.attribute("versionName").getText();
        packageName = root.attribute("package") == null ? "" : root.attribute("package").getText();

    }

    /**
     * 分析manifest解析出的基本元素 不一定有必要 留下的do something是分析出问题的地方 处理、存储方式待定
     * 源代码中是直接处理成html代码存到数据库中
     */
    private void analysis_manifest() throws Exception{
        File file = new File(ScanConst.DecompressFilePath + MD5 + "/output.xml");
        SAXReader reader = new SAXReader();
        Document document = null;
        document = reader.read(file);

        this.doc = document;

        Element manifest = doc.getRootElement();

        // application
        Element app = manifest.element("application");
        Attribute debuggable = app.attribute("debugggable");
        Attribute allowBackup = app.attribute("allowBackup");
        Attribute testOnly = app.attribute("testOnly");
        if (debuggable != null && debuggable.getText().equals("true")) {
            String v = "Android Debug Mode Checking";
            vulnerability.add(v);
        }
        if (allowBackup != null && allowBackup.getText().equals("true")) {
            String v = "AndroidManifest Adb Backup Checking";
            vulnerability.add(v);
        } else if (allowBackup != null && allowBackup.getText().equals("false")) {
            //do nothing
        } else {
            String v = "AllowBackup not set";
            vulnerability.add(v);
        }
        if (testOnly != null && testOnly.getText().equals("true")) {
            String v = "AndroidManifest Test Mode Checking";
            vulnerability.add(v);
        }

        List nodes = app.elements();
        for (Iterator it = nodes.iterator(); it.hasNext(); ) {
            String itmname = "";
            Element e = (Element) it.next();
            String ad = "";
            String cnt_id = "";
            if (e.getName().equals("activity")) {
                itmname = "Activity";
                cnt_id = "act";
                ad = "n";
            } else if (e.getName().equals("activity-alias")) {
                itmname = "Activity-Alias";
                cnt_id = "act";
                ad = "n";
            } else if (e.getName().equals("provider")) {
                itmname = "Content Provider";
                cnt_id = "cnt";
            } else if (e.getName().equals("receiver")) {
                itmname = "Broadcast Receiver";
                cnt_id = "bro";
            } else if (e.getName().equals("service")) {
                itmname = "Service";
                cnt_id = "ser";
            } else {
                itmname = "NIL";
            }
            String item = "";
            // Task Affinity
            if ((itmname.equals("Activity") || itmname.equals("Activity-Alias"))
                    && e.attribute("taskAffinity") != null) {
                item = e.attributeValue("name");
                String v = "Task Affinity Checking";
                if (!vulnerability.contains(v))
                    vulnerability.add(v);
                listForTaskAffinity.add(new ComponentVO(itmname, item));
            }
            // launch mode
            if ((itmname.equals("Activity") || itmname.equals("Activity-Alias")) && e.attribute("launchMode") != null
                    && (e.attributeValue("launchMode").equals("singleInstance")
                    || e.attributeValue("launchMode").equals("singleTask"))) {
                item = e.attributeValue("name");
                String v = "Launch Mode Checking";
                if (!vulnerability.contains(v))
                    vulnerability.add(v);
                listForLaunchMode.add(new ComponentVO(itmname, item));
            }

            // Exported Check
            item = "";
            boolean isInf = false;
            boolean isPermExist = false;
            if (!itmname.equals("NIL")) {
                Attribute a = e.attribute("exported");
                if (a != null && a.getText().equals("true")) {
                    String perm = "";
                    item = e.attributeValue("name");
                    if (e.attribute("permission") != null) {
                        // permission exists
                        // save the permission name to perm
                        isPermExist = true;
                    }
                    if (!item.equals(mainAct)) {
                        if (isPermExist) {
                            String port = "";
                            if (permission.contains(e.attributeValue("permission"))) {
                                // TODO
                                // maybe it's better to change the data
                                // structure of permission level from list to
                                // map
                                // port is the permission
                                // level:normal/dangrous..
                            }
                            // do something
                        } else {
                            if (itmname.equals("Activity") || itmname.equals("Activity-Alias")) {
                                exported.add(e.attributeValue("name"));
                            }
                            // do something
                            // change exported count of the four components
                        }
                    }
                    String v = "Exported";
                    if (!vulnerability.contains(v))
                        vulnerability.add(v);
                } else if (a != null && !a.getText().equals("false")) {
                    // Check for Implicitly Exported
                    // Logic to support intent-filter
                    List intentfilters = e.elements();
                    for (Iterator i = intentfilters.iterator(); i.hasNext(); ) {
                        Element el = (Element) i.next();
                        String inf = el.getName();
                        if (inf.equals("intent-filter")) {
                            isInf = true;
                        }
                    }
                    if (isInf) {
                        item = e.attributeValue("name");
                        if (e.attribute("permission") != null) {
                            // permission exists
                            // save the permission name to perm
                            isPermExist = true;
                        }
                        if (!item.equals(mainAct)) {
                            if (isPermExist) {
                                String port = "";
                                if (permission.contains(e.attributeValue("permission"))) {
                                    // TODO
                                    // maybe it's better to change the data
                                    // structure of permission level from list
                                    // to map
                                    // port is the permission
                                    // level:normal/dangrous..
                                }
                                // do something
                            } else {
                                if (itmname.equals("Activity") || itmname.equals("Activity-Alias")) {
                                    exported.add(e.attributeValue("name"));
                                }
                                String v = "Exported";
                                if (!vulnerability.contains(v))
                                    vulnerability.add(v);
                                // do something
                                // change exported count of the four components
                            }
                        }
                    }
                }
            }
        }

        // GRANT-URI-PERMISSIONS
        List pro = app.elements("provider");
        for (Iterator it = pro.iterator(); it.hasNext(); ) {
            int key = 0;
            Element elm = (Element) it.next();
            String name = elm.attributeValue("name");
            Element grant = elm.element("grant-uri-permission");
            if (grant == null)
                continue;
            Attribute a = grant.attribute("pathPrefix");
            Attribute b = grant.attribute("path");
            Attribute c = grant.attribute("pathPattern");
            if (a != null && a.getText().equals("/")) {
                // do something
                key = 1;
            } else if (b != null && b.getText().equals("/")) {
                // do something
                key = 1;
            } else if (c != null && c.getText().equals("*")) {
                // do something
                key = 1;
            }
            if (key == 1) {
                String v = "Improper Content Provider Permissions Checking";
                if (!vulnerability.contains(v))
                    vulnerability.add(v);
                listForGrantUriPermission.add(new ComponentVO("provider", elm.attributeValue("name")));
            }
        }

        List act = app.elements("activity");
        List svc = app.elements("service");
        List rec = app.elements("receiver");
        List aa = app.elements("activity-alias");
        for (Iterator it = svc.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            act.add(elm.clone());
        }
        for (Iterator it = rec.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            act.add(elm.clone());
        }
        for (Iterator it = aa.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            act.add(elm.clone());
        }

        // data
        for (Iterator it = act.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Element intent_filter = elm.element("intent-filter");
            if (intent_filter == null)
                continue;
            Element data = intent_filter.element("data");
            if (data == null)
                continue;
            Attribute a = data.attribute("scheme");
            Attribute b = data.attribute("port");
            if (a != null && a.getText().equals("android_secret_code")) {
                if (data.attribute("host") != null) {
                    String xmlhost = data.attribute("host").getText();
                    // do something
                    String v = "Android Secret Code Checking";
                    if (!vulnerability.contains(v))
                        vulnerability.add(v);
                    listForSecretCode.add(new ComponentVO(elm.getName(), elm.attributeValue("name")));
                }
            } else if (b != null) {
                String dataport = b.getText();
                // do something
                String v = "SMS Receiver Checking";
                if (!vulnerability.contains(v))
                    vulnerability.add(v);
                listForSMS.add(new ComponentVO(elm.getName(), elm.attributeValue("name")));
            }
        }

        // intents
        for (Iterator it = act.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Element intent_filter = elm.element("intent-filter");
            if (intent_filter == null)
                continue;
            Attribute a = intent_filter.attribute("priority");
            if (a != null && isDigit(a.getText())) {
                String value = a.getText();
                if (Integer.parseInt(value) > 100) {
                    // do something
                    String v = "Intent Priority Checking";
                    if (!vulnerability.contains(v))
                        vulnerability.add(v);
                    listForIntent.add(new ComponentVO(elm.getName(), elm.attributeValue("name")));
                }
            }
        }

        // action
        for (Iterator it = act.iterator(); it.hasNext(); ) {
            Element elm = (Element) it.next();
            Element intent_filter = elm.element("intent-filter");
            if (intent_filter == null)
                continue;
            Element action = intent_filter.element("action");
            if (action == null)
                continue;
            Attribute a = action.attribute("priority");
            if (a != null && isDigit(a.getText())) {
                String value = a.getText();
                if (Integer.parseInt(value) > 100) {
                    String v = "Intent Action Checking";
                    if (!vulnerability.contains(v))
                        vulnerability.add(v);
                    listForAction.add(new ComponentVO(elm.getName(), elm.attributeValue("name")));
                }
            }
        }
    }

    private static boolean isDigit(String a) {
        int len = a.length();
        for (int i = 0; i < len; ++i) {
            if (a.charAt(i) < 48 || a.charAt(i) > 57)
                return false;
        }
        return true;
    }
}
