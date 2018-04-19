package cn.iselab.android.analysis.server.web.utils;

import cn.iselab.android.analysis.server.constant.ScanConst;

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
     * Decompress the compressed file(zip)
     * @param file: the file to decompress
     * @param path: the dir to store the decompress file
     *
     */
    public static void unzip(String file, String path) {
        try {
            // the zipped file path
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(ScanConst.ScanFilePath + file));
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = path; // output dir
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
}
