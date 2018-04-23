package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.ApkDao;
import cn.iselab.android.analysis.server.dao.Apk_vulnerabilityDao;
import cn.iselab.android.analysis.server.dao.CheckListDao;
import cn.iselab.android.analysis.server.data.Apk;
import cn.iselab.android.analysis.server.data.CheckList;
import cn.iselab.android.analysis.server.data.Dvm_permission;
import cn.iselab.android.analysis.server.service.FormatFileService;
import cn.iselab.android.analysis.server.web.data.FormatData.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@Service
public class FormatFileServiceImpl implements FormatFileService {

    @Autowired
    private CheckListDao checkListDao;
    @Autowired
    private ApkDao apkDao;
    @Autowired
    private Apk_vulnerabilityDao apkVulnerabilityDao;

    @Override
    public ArrayList<FormatFileVO> getFormatFileForKivul(String files,String title){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String file_type=this.JudgeType(title);
        if(file_type.equals("none")){
            return re;
        }
        re=this.formatFile(files,file_type);
        return re;
    }

    @Override
    public ArrayList<FormatFileVO> getFormatFile(String MD5, String title) {
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();

        CheckList cl=checkListDao.findByTitle(title);
        Apk apk=apkDao.findByMd5(MD5);
        String apkID=apk.getId()+"";
        if(cl!=null){
            String checkID=cl.getId()+"";
            String files=apkVulnerabilityDao.findByApkIDAndCheckID(apkID,checkID).getFiles();
            String file_type=this.JudgeType(title);
            if(file_type.equals("none")){
                return re;
            }
            re=this.formatFile(files,file_type);
        }
        return re;
    }

    @Override
    public String JudgeType(String title){
        String re="none";
        switch (title){
            //Done
            case "Google Cloud Messaging Suggestion":re="none";break;
            case "Android SQLite Databases Vulnerability Checking":re="none";break;
            case "AndroidManifest Adb Backup Checking":re="none";break;
            case "Master Key Type I Vulnerability":re="none";break;
            case "Android Debug Mode Checking":re="none";break;
            case "Unnecessary Permission Checking":re="none";break;
            case "Accessing the Internet Checking":re="none";break;
            case "Rooting System with Master Key Vulnerability":re="none";break;
            //Done
            case "HttpURLConnection Android Bug Checking 1":re="show_path";break;
            case "HttpURLConnection Android Bug Checking 2":re="show_path";break;
            case "File Unsafe Delete Checking":re="show_path";break;
            case "WebView Potential XSS Attacks Checking":re="show_path";break;
            case "Dynamic Code Loading":re="show_path";break;
            case "External Storage Accessing":re="show_path";break;
            case "Getting IMEI and Device ID":re="show_path";break;
            case "Getting ANDROID_ID":re="show_path";break;
            case "Codes for Sending SMS":re="show_path";break;
            case "WebView RCE Vulnerability Checking":re="show_path";break;
            case "KeyStore Protection Checking 1":re="show_path";break;
            case "KeyStore Protection Checking 2":re="show_path";break;
            case "KeyStore Type Checking":re="show_path";break;
            case "Runtime Command Checking":re="show_path";break;
            case "Runtime Critical Command Checking":re="show_path";break;
            case "SSL Implementation Checking (Insecure component)":re="show_path";break;
            case "SSL Implementation Checking (HttpHost)":re="show_path";break;
            case "SQLiteDatabase Transaction Deprecated Checking":re="show_path";break;
            //Done
            case "AndroidManifest Exported Components Checking":re="component";break;
            case "AndroidManifest \"intent-filter\" Settings Checking 1":re="component";break;
            case "AndroidManifest Exported Lost Prefix Checking":re="component";break;
            case "AndroidManifest ContentProvider Exported Checking 1":re="component";break;
            case "AndroidManifest ContentProvider Exported Checking 2":re="component";break;
            case "AndroidManifest \"intent-filter\" Settings Checking 2":re="component";break;
            case "Implicit Service Checking":re="component";break;
            //Done
            case "WebView Local File Access Attacks Checking":re="easy_print";break;
            //Done
            case "SSL Certificate Verification Checking 2":re="X509";break;
            case "SSL Certificate Verification Checking 1":re="X509";break;
            //Done
            case "AndroidManifest PermissionGroup Checking":re="permission";break;
            case "AndroidManifest System Use Permission Checking":re="permission";break;
            case "AndroidManifest Critical Use Permission Checking":re="permission";break;
            //Done
            case "Base64 String Encryption 1":re="Base64";break;
            case "Base64 String Encryption 2":re="Base64";break;
            //Done
            case "SSL Connection Checking":re="SSL_URL";break;

            //Done
            case "SSL Implementation Checking (Verifying Host Name in Custom Classes)":re="easy_show";break;
            case "SSL Implementation Checking (WebViewClient for WebView)":re="easy_show";break;

            //Done
            case "SSL Implementation Checking (Verifying Host Name in Fields)":re="singleOrshow";break;

            //Done
            case "SC Sandbox Permission Checking":re="SandBox";break;

            //TODO
            //No demo to verify
            case "Fragment Vulnerability Checking":re="Fragment";break;
            //TODO
            case "AndroidManifest Normal ProtectionLevel of Permission Checking":re="protect_level";break;
            case "AndroidManifest Dangerous ProtectionLevel of Permission Checking":re="protect_level";break;
            default:re="none";
        }
        return re;
    }

    private ArrayList<FormatFileVO> formatFile(String file,String type){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        if(file.equals("")){
            return re;
        }
        if(type.equals("component")){
            re=this.dealWithComponent(file);
        }else if(type.equals("easy_print")){
            re=this.dealWithEasyPrint(file);
        }else if(type.equals("Base64")){
            re=this.dealWithBase64String(file);
        }else if(type.equals("SSL_URL")){
            re=this.dealWithSSL_URL(file);
        }else if(type.equals("permission")){
            re=this.dealWithPermissions(file);
        }else if(type.equals("X509")){
            re=this.dealWithX509(file);
        }else if(type.equals("show_path")){
            re=this.dealWithShowPath(file);
        }else if(type.equals("easy_show")){
            re=this.dealWithEasyShow(file);
        }else if(type.equals("singleOrshow")){
            re=this.dealWithSingleShow(file);
        }else if(type.equals("SandBox")){
            re=this.dealWithSandBox(file);
        }else if(type.equals("Fragment")){
            re=this.dealWithFragment(file);
        }else if(type.equals("protect_level")){
            re=this.dealWithProteceLevel(file);
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithProteceLevel(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] items=all.split("Permission : ");
        for(int i=1;i<items.length;++i){
            String item=items[i].trim();
            String[] parts=item.split("    -> used by ");
            String permission=parts[0];
            ArrayList<ComponentVO> list=new ArrayList<ComponentVO>();
            for(int j=1;j<parts.length;++j){
                String part=parts[j];
                String component=part.substring(1,part.indexOf(")")).trim();
                String location=part.substring(part.indexOf(")")+1).trim();
                list.add(new ComponentVO(component,location));
            }
            re.add(new ProtectLevelVO(permission,list));
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithFragment(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] items=all.split("--------------------------------------------------");
        for(String item:items){
            if(item.startsWith("You MUST override") || item.startsWith("These \"PreferenceActivity\"")){
                //Don't know weather to show
                /*String[] c=item.split("Class : ");
                for(int i=1;i<c.length;++i){
                    re.add(new CmdVO(c[i],"isValidFragment()",""));
                }*/
            }else if(item.startsWith("You override ") || item.startsWith("Please make sure")){
                String[] c=item.split("    =>");
                for(int i=1;i<c.length;++i){
                    String l=c[i];
                    String class_name=l.split("->")[0].trim();
                    String method_name=l.split("->")[1].split("\\)")[0].trim()+")";
                    String descriptor=l.split("->")[1].split("\\)")[1].trim();
                    re.add(new CmdVO(class_name,method_name,descriptor));
                }
            }else if(item.startsWith("All of the potential")){
                //Don't know weather to show either
                /*String[] c=item.split("Class : ");
                for(int i=1;i<c.length;++i){
                    re.add(new CmdVO(c[i],"",""));
                }*/
            }
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithSandBox(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] items=all.split("--------------------------------------------------");
        for(String item:items){
            String[] t=item.split("=>");
            for(int i=1;i<t.length;++i){
                re.add(this.getDetailCmdVO(t[i]));
            }
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithSingleShow(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] items=all.split("=>");
        for(int i=1;i<items.length;++i){
            String item=items[i];
            if(item.contains("(0x")){
                re.add(this.getDetailCmdVO(item));
            }else{
                String class_name=item.split("->")[0].trim();
                String method_name=item.split("->")[1].split(" ")[0].trim();
                String descriptor=item.split("->")[1].split(" ")[1].trim();
                re.add(new DetailCmdVO("",class_name,method_name,descriptor));
            }
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithEasyShow(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] items=all.split("Method:");
        for(int i=1;i<items.length;++i){
            String item=items[i];
            String[] parts=item.split("=>");
            String method_string=parts[0];

            String method_class=method_string.split("->")[0].trim();
            String method_name=method_string.split("->")[1].split("\\)")[0].trim()+")";
            String method_descriptor=method_string.split("->")[1].split("\\)")[1].trim();

            CmdVO cmd=new CmdVO(method_class,method_name,method_descriptor);

            ArrayList<DetailCmdVO> list=new ArrayList<DetailCmdVO>();
            for(int j=1;j<parts.length;++j){
                String part=parts[j];
                list.add(this.getDetailCmdVO(part));
            }
            re.add(new EasyShowVO(cmd,list));
        }
        return re;
    }

    private DetailCmdVO getDetailCmdVO(String item){
        int int_type=item.trim().split("->").length;
       // System.out.println("Item :" + item);
        if(int_type==3){
            //type="dstOnly";
            String info_var_idx=item.split("--->")[0].trim();
            String dst=item.split("--->")[1].trim();

            String info_var=info_var_idx.substring(0,info_var_idx.lastIndexOf("("));
            String path_idx=info_var_idx.substring(info_var_idx.lastIndexOf("(")+1,info_var_idx.lastIndexOf(")"));

            String dst_class_name=dst.split("->")[0].trim();
            String dst_method_name=dst.split("->")[1].split("\\)")[0].trim()+")";
            String dst_descriptor=dst.split("->")[1].split("\\)")[1].trim();

            return new DetailCmdVO(info_var,path_idx,dst_class_name,dst_method_name,dst_descriptor);
        }else if(int_type==4){
            //type="dst_src";
            String src_idx=item.split("--->")[0].trim();
            String src=src_idx.substring(0,src_idx.lastIndexOf("("));
            String dst=item.split("--->")[1].trim();

            String src_class_name=src.split("->")[0].trim();
            String src_method_name=src.split("->")[1].split("\\)")[0].trim()+")";
            String src_descriptor=src.split("->")[1].split("\\)")[1].trim();

            String dst_class_name=dst.split("->")[0].trim();
            String dst_method_name=dst.split("->")[1].split("\\)")[0].trim()+")";
            String dst_descriptor=dst.split("->")[1].split("\\)")[1].trim();

            String path_idx=src_idx.substring(src_idx.lastIndexOf("(")+1,src_idx.lastIndexOf(")"));

            return new DetailCmdVO(path_idx,dst_class_name,dst_method_name,dst_descriptor,src_class_name,src_method_name,src_descriptor);
        }else{
            //type="srcOnly";
            String src=item.substring(0,item.lastIndexOf("("));

            String path_idx=item.substring(item.lastIndexOf("(")+1,item.lastIndexOf(")"));

            String src_class_name=src.split("->")[0].trim();
            String src_method_name=src.split("->")[1].split("\\)")[0].trim()+")";
            String src_descriptor=src.split("->")[1].split("\\)")[1].trim();

            return new DetailCmdVO(path_idx,src_class_name,src_method_name,src_descriptor);
        }
    }

    private ArrayList<FormatFileVO> dealWithShowPath(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] items=all.split("=>");
        for(int i=1;i< items.length;++i){
            String item=items[i];
            //System.out.println("xx: "+item);
            re.add(this.getDetailCmdVO(item));
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithX509(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String confirm="";
        String maybe="";
        if(all.contains("--------------------------------------------------")){
            confirm=all.split("--------------------------------------------------")[0];
            maybe=all.split("--------------------------------------------------")[1];
        }else{
            confirm=all;
        }
        int isConfirm=1;
        String[] item=confirm.split("=>");
        for(int i=1;i<item.length;++i){
            ArrayList<CmdVO> used_class=new ArrayList<CmdVO>();
            String[] items=item[i].split("-> used by:");
            String class_name=items[0];
            for(int j=1;j<items.length;++j){
                String class_name_uesd=items[j].split("->")[0].trim();
                String method_name_used=items[j].split("->")[1].split("\\)")[0].trim()+")";
                String descriptor_used=items[j].split("->")[1].split("\\)")[1].trim();
                used_class.add(new CmdVO(class_name_uesd,method_name_used,descriptor_used));
            }
            re.add(new X509VO(isConfirm,class_name,used_class));
        }
        if(maybe.equals("")){
            return re;
        }else{
            isConfirm=0;
            item=maybe.split("=>");
            for(int i=1;i<item.length;++i){
                ArrayList<CmdVO> used_class=new ArrayList<CmdVO>();
                String[] items=item[i].split("-> used by:");
                String class_name=items[0];
                for(int j=1;j<items.length;++j){
                    String class_name_uesd=items[j].split("->")[0].trim();
                    String method_name_used=items[j].split("->")[1].split("\\)")[0].trim()+")";
                    String descriptor_used=items[j].split("->")[1].split("\\)")[1].trim();
                    used_class.add(new CmdVO(class_name_uesd,method_name_used,descriptor_used));
                }
                re.add(new X509VO(isConfirm,class_name,used_class));
            }
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithPermissions(String file){
        Map<String, String[]> map= Dvm_permission.getManifest_permission();
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] item=file.split("\n");
        for(String i:item){
            String permission="";
            if(i.startsWith("System") || i.startsWith("Critical")){
                String temp=i.split(":")[1].trim();
                temp=temp.substring(1,temp.lastIndexOf("\"")).trim();
                permission=temp.split("\\.")[2];
            }else{
                String temp=i.substring(i.indexOf("'")+1,i.lastIndexOf("'")).trim();
                permission=temp.split("\\.")[2];
            }
            String[] detail=map.get(permission);
            re.add(new FormatPerVO(permission,detail[0],detail[1],detail[2]));
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithSSL_URL(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] item=all.split("URL:");
        for(int i=1;i<item.length;++i){
            String oneURL=item[i];
            String[] u=oneURL.split("=>");
            String url=u[0];
            ArrayList<CmdVO> list=new ArrayList<CmdVO>();
            for(int j=1;j<u.length;++j){
                String class_name=u[j].split("->")[0].trim();
                String method_name=u[j].split("->")[1].split("\\)")[0].trim()+")";
                String descriptor=u[j].split("->")[1].split("\\)")[1].trim();
                list.add(new CmdVO(class_name,method_name,descriptor));
            }
            re.add(new SSL_URLVO(url,list));
        }
        return re;
    }

    private ArrayList<FormatFileVO> dealWithBase64String(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] item=all.split("Decoded String:");
        for(int k=1;k<item.length;++k){
            String oneItem=item[k];
            String[] l=oneItem.split("->From class:");
            String decode=l[0].split("->Original Encoding String:")[0].trim();
            String encode=l[0].split("->Original Encoding String:")[1].trim();
            ArrayList<CmdVO> list=new ArrayList<CmdVO>();
            for(int i=1;i<l.length;++i){
                String class_name=l[i].split("->")[0].trim();
                String method_name=l[i].split("->")[1].split("\\)")[0].trim()+")";
                String descriptor=l[i].split("->")[1].split("\\)")[1].trim();
                list.add(new CmdVO(class_name,method_name,descriptor));
            }
            re.add(new Base64VO(encode,decode,list));
        }
        return re;
    }

        private ArrayList<FormatFileVO> dealWithComponent(String file){
            ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
            String[] line=file.split("\n");
            for(String l:line){
                String component=l.split("=>")[0].trim();
                String location=l.split("=>")[1].trim();
                /*System.out.println("Component : "+component);
                System.out.println("Location : "+location);
                System.out.println("------------------------------");*/
                re.add(new ComponentVO(component,location));
            }
            return re;
        }

    private ArrayList<FormatFileVO> dealWithEasyPrint(String file){
        ArrayList<FormatFileVO> re=new ArrayList<FormatFileVO>();
        String[] line=file.split("\n");
        String all="";
        for(String l:line){
            all+=l.trim();
        }
        String[] item=all.split("\\|");
        for(int i=1;i<item.length;++i){
            String l=item[i];
            String class_name=l.split("->")[0].trim();
            String method_name=l.split("->")[1].split("\\)")[0].trim()+")";
            String descriptor=l.split("->")[1].split("\\)")[1].trim();
            /*System.out.println("Class : "+class_name);
            System.out.println("Method : "+method_name);
            System.out.println("Descriptor : "+descriptor);*/
            re.add(new CmdVO(class_name,method_name,descriptor));
        }
        return re;
    }
}
