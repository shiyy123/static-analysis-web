package cn.iselab.android.analysis.server.web.ctrl;

import cn.iselab.android.analysis.server.constant.*;
import cn.iselab.android.analysis.server.data.SC;
import cn.iselab.android.analysis.server.data.CertInfo;
import cn.iselab.android.analysis.server.service.*;
import cn.iselab.android.analysis.server.web.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.iselab.android.analysis.server.web.utils.FileOperationUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;


@Controller
public class UploadController {

    @Autowired
    SCService scService;

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
            String originalFileName = file.getOriginalFilename();
            String scanType = originalFileName.substring(originalFileName.indexOf('.') + 1);
            this.type = scanType;

            // Filter the upload file type
            if(!(scanType.equals(ScanType.apk.name()) || scanType.equals(ScanType.ipa.name())
                    || scanType.equals(ScanType.zip.name()))){
                response.setStatus(ResStatus.file_type_error);
                return null;
            }

            // Get the unique id of the upload file
            String uuid = UUID.randomUUID().toString();
            String curWorkPath = ScanConst.WorkPath + File.separator + uuid;
            String scanFilePath = curWorkPath + File.separator + originalFileName;

            // Store the uploaded file
            try {
                FileOperationUtils.storeUploadFile(uuid, originalFileName, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(ResStatus.file_transport_error);
                return null;
            }

            // Get current time
            String curTime = CommonUtils.getCurTime();

            // Get the md5 value of need to scan(uploaded file)
            File scanFile = new File(scanFilePath);
            String md5 = FileOperationUtils.getMD5(scanFile);
            String sha1 = FileOperationUtils.getSHA1(scanFile);
            String sha256 = FileOperationUtils.getSHA256(scanFile);

            // Get scan file size
            String scanFileSize = CommonUtils.getFileSize(scanFile);

            String platform = CommonUtils.getScanFileType(scanFilePath).name();

            // Begin to save the data to the database,
            // if the upload file is ipa/ios_source, when analysis it,
            // it needs the sc_Id to do analysis, and store data to database by the scId
            SC sc_find = scService.findByMd5(md5);
            if(sc_find == null) {
                // Never scan this file before
                SC sc = new SC(originalFileName, md5, platform, ScanStatus.scan, uuid);
                // save sc to database
                try{
                    scService.save(sc);
                }catch (Exception e){
                    response.setStatus(ResStatus.file_transport_error);
                    return null;
                }
            }
            response.setStatus(ResStatus.success);
//            String name = file.getOriginalFilename();
//            String type = name.substring(name.indexOf('.') + 1);
//            this.type = type;
//            // Filter the upload file type
//            if(!(type.equals(ScanType.apk.name()) || type.equals(ScanType.ipa.name())
//                    || type.equals(ScanType.zip.name()))){
//                response.setStatus(ResStatus.file_type_error);
//                return null;
//            }
//
//            // unique identifier of the file
//            String uuid = UUID.randomUUID().toString();
//
//
//            // Store the uploaded file
//            String scanFileName = ScanConst.ScanFilePath + uuid + "-" + name;
//            try {
//                if (type.equals(ScanType.apk.name()) || type.equals(ScanType.ipa.name())
//                        || type.equals(ScanType.zip.name())) {
//                    BufferedOutputStream out = new BufferedOutputStream(
//                            new FileOutputStream(new File(scanFileName)));
//                    out.write(file.getBytes());
//                    out.flush();
//                    out.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                response.setStatus(ResStatus.file_transport_error);
//                return null;
//            }
//
//            //Get current time
//            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String time=dff.format(new Date());
//
//            // Get the md5 value of need to scan(uploaded file)
//            File f = new File(ScanConst.ScanFilePath + uuid + "-" + name);
//            String MD5 = FileOperationUtils.getMD5(f);
//            String sha1 = FileOperationUtils.getSHA1(f);
//            String sha256 = FileOperationUtils.getSHA256(f);
//
//            // Get the file size
//            DecimalFormat df = new DecimalFormat("#.##");
//            String size = df.format(f.length() / 1024 / 1024) + "MB";
//
//            // Copy the scan file to the decompressed dir, set the name as the md5 value of ot
//            File directory=new File(ScanConst.DecompressFilePath + MD5);
//            if(!directory.exists()){
//                // decompress upload file
//                FileOperationUtils.unzip(uuid + "-" + name, ScanConst.DecompressFilePath + MD5);
//                try {
//                    BufferedOutputStream out = new BufferedOutputStream(
//                            new FileOutputStream(new File(ScanConst.DecompressFilePath + MD5 + "/" + MD5 + ".apk")));
//                    out.write(file.getBytes());
//                    out.flush();
//                    out.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    response.setStatus(ResStatus.file_transport_error);
//                    return null;
//                }
//            }
//
////            sourceCodeService.analysis(scanFileName);
//
//
//            // 使用aapt获取apk的应用名和图标路径、版本号，以及安全报告路径
//            //aaptService.analysis(MD5);
////            String label=aaptService.getLabel();
//            String label="label";
////            String icon=aaptService.getIcon();
//            String icon="icon";
////            String versionName=aaptService.getVersionName();
//            String versionName="versionName";
////            String sdkVersion=aaptService.getSdkVersion();
//            String sdkVersion="sdkVersion";
//
//            String url = "/analysis/?name=" + name + "&type=" + type + "&checksum=" + MD5;// app
//            url="";
//
//            String platform = "Android";
//            if (type.equals("ipa")) {
//                platform = "IOS";
//            }
//
//            Apk apk_find = apkService.findApkByMD5(MD5);
//            if (apk_find == null) {
//
//                Apk apk = new Apk(name, label, icon, MD5, url, platform, versionName, size,"-1",time,sha1,sha256,sdkVersion);
//                // save to database
//                try{
//                    apkService.save(apk);
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
