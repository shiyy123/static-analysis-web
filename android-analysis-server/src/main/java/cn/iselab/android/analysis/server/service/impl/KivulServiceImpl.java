package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.service.KivulService;
import cn.iselab.android.analysis.server.web.utils.FileOperationUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class KivulServiceImpl implements KivulService{
    @Override
    public String downloadFile(String url, String apkID) {
        String dir= ScanConst.ScanFilePath;
        String name=getFileNameFromUrl(url);
        String fileName=apkID+"-"+name;
        try {
            URL httpurl = new URL(url);
            File file=new File(dir+fileName);
            FileUtils.copyURLToFile(httpurl, file);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return fileName;
    }

    private static String getFileNameFromUrl(String url){
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        int index = url.lastIndexOf("/");
        if(index > 0){
            name = url.substring(index + 1);
            if(name.trim().length()>0){
                return name;
            }
        }
        return name;
    }

    @Override
    public boolean unzip(String file, String path) {
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(file));// 输入源zip路径
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
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(file + "解压成功");
        return true;
    }

    @Override
    public String findApkAndAnalysis(String path) {
        File f=new File(path);
        File[] files=f.listFiles();
        for(File file:files){
            if(file.isFile()){
                String name=file.getName();
                if(name.endsWith(".apk")){
                    return file.getName();
                }
            }
        }
        return "0";
    }

    @Override
    public String getMD5(File file) {
        return FileOperationUtils.getMD5(file);
    }

    @Override
    public String getSHA1(File file){
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

    @Override
    public String getSHA256(File file){
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

    @Override
    public boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
