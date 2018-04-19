package cn.iselab.android.analysis.server.web.ctrl;

import cn.iselab.android.analysis.server.constant.ResStatus;
import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.constant.ScanType;
import cn.iselab.android.analysis.server.data.Apk;
import cn.iselab.android.analysis.server.data.CertInfo;
import cn.iselab.android.analysis.server.service.AaptService;
import cn.iselab.android.analysis.server.service.ApkService;
import cn.iselab.android.analysis.server.service.CertInfoService;
import cn.iselab.android.analysis.server.web.utils.DebugUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.iselab.android.analysis.server.web.utils.FileOperationUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Controller
public class UploadController {

    @Autowired
    AaptService aaptService;
    @Autowired
    ApkService apkService;
    @Autowired
    CertInfoService certInfoService;

    String type;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("afile") MultipartFile file, HttpServletResponse response) {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            String type = name.substring(name.indexOf('.') + 1);
            this.type = type;
            if(!(type.equals(ScanType.apk.name()) || type.equals(ScanType.ipa.name())
                    || type.equals(ScanType.zip.name()))){
                response.setStatus(ResStatus.file_type_error);
                return null;
            }
            String uuid = UUID.randomUUID().toString();
            try {
                if (type.equals(ScanType.apk.name()) || type.equals(ScanType.ipa.name())
                        || type.equals(ScanType.zip.name())) {
                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(new File(ScanConst.ScanFilePath + uuid + "-" + name)));
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(ResStatus.file_transport_error);
                return null;
            }

            //Get current time
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=dff.format(new Date());

            // Get the md5 value of need to scan(uploaded file)
            File f = new File(ScanConst.ScanFilePath + uuid + "-" + name);
            String MD5 = FileOperationUtils.getMD5(f);
            String sha1 = FileOperationUtils.getSHA1(f);
            String sha256 = FileOperationUtils.getSHA256(f);

            // Get the file size
            DecimalFormat df = new DecimalFormat("#.##");
            String size = df.format(f.length() / 1024 / 1024) + "MB";

            // Copy the scan file to the decompressed dir, set the name as the md5 value of ot
            File directory=new File(ScanConst.DecompressFilePath + MD5);
            if(!directory.exists()){
                // decompress upload file
                FileOperationUtils.unzip(uuid + "-" + name, ScanConst.DecompressFilePath + MD5);
                try {
                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(new File(ScanConst.DecompressFilePath + MD5 + "/" + MD5 + ".apk")));
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(ResStatus.file_transport_error);
                    return null;
                }
            }

            // 使用aapt获取apk的应用名和图标路径、版本号，以及安全报告路径
//            aaptService.analysis(MD5);
//            String label=aaptService.getLabel();
//            String icon=aaptService.getIcon();
//            String versionName=aaptService.getVersionName();
//            String sdkVersion=aaptService.getSdkVersion();
//            String url = "/analysis/?name=" + name + "&type=" + type + "&checksum=" + MD5;// app
//            url="";
//            String platform = "Android";
//            if (type.equals("ipa")) {
//                platform = "IOS";
//            }
//
//            Apk apk_find = apkService.findApkByMD5(MD5);
//            if (apk_find == null) {
//                Apk apk = new Apk(name, label, icon, MD5, url, platform, versionName, size,"-1",time,sha1,sha256,sdkVersion);
//                try{
//                    apkService.save(apk);// save to database
//                }catch (Exception e){
//                    response.setStatus(ResStatus.file_transport_error);
//                    return null;
//                }
//            }
//
//            String apkID="";
//            Apk a=apkService.findApkByMD5(MD5);
//            apkID=a.getId()+"";
//
//            //获取证书信息
//            this.getCertInfo(apkID,MD5);

        } else {
            response.setStatus(ResStatus.file_scan_error);
            return null;
        }
        return null;
    }


    private void getCertInfo(String apkID,String MD5){
        String info[]=certInfoService.getCertInfo(MD5);
        String status=info[0];
        String cert=info[1];
        if(certInfoService.getCert(MD5)==null)
            certInfoService.save(new CertInfo(apkID,status,cert));
    }
}
