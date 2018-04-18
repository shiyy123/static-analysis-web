package cn.iselab.android.analysis.server.web.ctrl;

import cn.iselab.android.analysis.server.dao.CertInfoDao;
import cn.iselab.android.analysis.server.data.Apk;
import cn.iselab.android.analysis.server.data.CertInfo;
import cn.iselab.android.analysis.server.service.AaptService;
import cn.iselab.android.analysis.server.service.ApkService;
import cn.iselab.android.analysis.server.service.CertInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    public String handleFileUpload(@RequestParam("afile") MultipartFile file,HttpServletResponse response) {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            String type = name.substring(name.indexOf('.') + 1);
            this.type = type;
            if(!(type.equals("apk") || type.equals("ipa"))){
                response.setStatus(222);
                return null;
            }
            String uuid = UUID.randomUUID().toString();
            try {
                if (type.equals("apk")) {
                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(new File("H:/demo/apks/" + uuid + "-" + name)));
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(4444);
                return null;
            }

            //获取当前时间
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=dff.format(new Date());

            // 获取apk文件md5
            File f = new File("H:/demo/apks/" + uuid + "-" + name);
            String MD5 = getMD5(f);
            String sha1=getSHA1(f);
            String sha256=getSHA256(f);

            // 获取文件大小
            DecimalFormat df = new DecimalFormat("#.##");
            String size = df.format(f.length() / 1024 / 1024) + "MB";

            // 将apk文件复制到解压后的文件夹中
            File directory=new File("H:/demo/zipped_apks/" + MD5);
            if(!directory.exists()){
                unzip(uuid+"-"+ name, "H:/demo/zipped_apks/" + MD5);// 解压apk
                try {
                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(new File("H:/demo/zipped_apks/" + MD5 + "/" + MD5 + ".apk")));
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(4444);
                    return null;
                }
            }

            // 使用aapt获取apk的应用名和图标路径、版本号，以及安全报告路径
            aaptService.analysis(MD5);
            String label=aaptService.getLabel();
            String icon=aaptService.getIcon();
            String versionName=aaptService.getVersionName();
            String sdkVersion=aaptService.getSdkVersion();
            String url = "/analysis/?name=" + name + "&type=" + type + "&checksum=" + MD5;// app
            url="";
            String platform = "Android";
            if (type.equals("ipa")) {
                platform = "IOS";
            }

            Apk apk_find = apkService.findApkByMD5(MD5);
            if (apk_find == null) {
                Apk apk = new Apk(name, label, icon, MD5, url, platform, versionName, size,"-1",time,sha1,sha256,sdkVersion);
                try{
                    apkService.save(apk);// save to database
                }catch (Exception e){
                    response.setStatus(4444);
                    return null;
                }
            }

            String apkID="";
            Apk a=apkService.findApkByMD5(MD5);
            apkID=a.getId()+"";

            //获取证书信息
            this.getCertInfo(apkID,MD5);

        } else {
            response.setStatus(2222);
            return null;
        }
        return null;
    }

    private static String getMD5(File file) {
        String value = "";// apk文件md5值
        try {
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return value;
    }

    private static String getSHA1(File file){
        String value="";
        try{
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            sha1.update(byteBuffer);
            BigInteger bi = new BigInteger(1, sha1.digest());
            value = bi.toString(16);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private static String getSHA256(File file){
        String value="";
        try{
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(byteBuffer);
            BigInteger bi = new BigInteger(1, sha256.digest());
            value = bi.toString(16);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     *
     * @param file:待解压文件
     * @param path:解压目录
     *
     */
    private static void unzip(String file, String path) {
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream("H:/demo/apks/" + file));// 输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = path; // 输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry;
            try {
                while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                    Fout = new File(Parent, entry.getName());
                    if (!Fout.exists()) {
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream Bout = new BufferedOutputStream(out);
                    int b;
                    while ((b = Bin.read()) != -1) {
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                }
                Bin.close();
                Zin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(file + "解压成功");
    }

    private void getCertInfo(String apkID,String MD5){
        String info[]=certInfoService.getCertInfo(MD5);
        String status=info[0];
        String cert=info[1];
        if(certInfoService.getCert(MD5)==null)
            certInfoService.save(new CertInfo(apkID,status,cert));
    }
}
