package cn.iselab.android.analysis.server.web.utils;

import cn.iselab.android.analysis.server.constant.ResStatus;
import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.constant.ScanType;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileOperationUtils {

    /**
     * Get the file info string by the type
     * @param file upload file
     * @param value info str
     * @param type MD5, SHA1, SHA256
     * @return
     */
    private static String getStringByInfoType(File file, String value, String type) {
        try {
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(byteBuffer);
            BigInteger bi = new BigInteger(1, messageDigest.digest());
            value = bi.toString(16);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return value;
    }

    /**
     * Get the md5 of the file
     * @param file the file need to calculate the md5
     * @return md5 value(string)
     */
    public static String getMD5(File file) {
        String value = "";
        value = getStringByInfoType(file, value, "MD5");
        return value;
    }

    /**
     * Get the SHA1 of the file
     * @param file the file need to calculate the SHA1
     * @return SHA1 value(string)
     */
    public static String getSHA1(File file){
        String value="";
        value = getStringByInfoType(file, value, "SHA1");
        return value;
    }

    /**
     * Get the SHA-256 of the file
     * @param file the file need to calculate the SHA-256
     * @return SHA-256 value(string)
     */
    public static String getSHA256(File file){
        String value="";
        value = getStringByInfoType(file, value, "SHA-256");
        return value;
    }

    /**
     * Create dir recursive if not exist
     * @param filePath
     */
    public static void mkdirIfNotExists(String filePath){
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * uncompressed the zipped file
     * @param storeFile the zip file
     * @param outPath the path of the unzipped file
     */
    public static String deCompressZip(File storeFile, String outPath){
        File file = storeFile;
        String temp = outPath;
        FileInputStream fis;
        ZipInputStream zins;
        String compressDir = null;
        // do decompress
        try {
            fis = new FileInputStream(file);
            zins = new ZipInputStream(fis);
            ZipEntry ze;
            byte[] ch = new byte[256];
            boolean isCompressedDir = true;
            while ((ze = zins.getNextEntry()) != null) {
                File zfile = new File(temp + "/" + ze.getName());
                File fpath = new File(zfile.getParentFile().getPath());
                if(isCompressedDir){
                    compressDir = zfile.getPath();
                    isCompressedDir = false;
                }
                if (ze.isDirectory()) {
                    if (!zfile.exists())
                        zfile.mkdirs();
                    zins.closeEntry();
                } else {
                    if (!fpath.exists())
                        fpath.mkdirs();
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
                    while ((i = zins.read(ch)) != -1)
                        fouts.write(ch, 0, i);
                    zins.closeEntry();
                    fouts.close();
                }
            }
            fis.close();
            zins.close();
        } catch (Exception e) {
            e.printStackTrace();
//            file.delete();
            System.err.println("File" + file.toString() + "decompressed failed, reason" + e.toString());
            System.exit(1);
        }
        return compressDir;
    }

    /**
     * Store the upload file to the curWorkPath/MD5/
     * @param uuid
     */
    public static void storeUploadFile(String uuid, String originalFileName, byte[] uploadFileBytes) {
        String uploadFileDir = ScanConst.WorkPath + File.separator + uuid;
        mkdirIfNotExists(uploadFileDir);
        String scanFileName = uploadFileDir + File.separator + originalFileName;

        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(scanFileName)));
            out.write(uploadFileBytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the folder
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // delete all content int the folder
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // delete empty folder
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all files under the path
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // Delete all file under the folder
                delAllFile(path + "/" + tempList[i]);
                // Delete empty folder
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    public static void main(String[] args){
        delFolder("/home/cary/Test/Test");

    }
}
