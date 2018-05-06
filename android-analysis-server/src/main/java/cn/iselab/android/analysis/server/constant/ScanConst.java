package cn.iselab.android.analysis.server.constant;

import cn.iselab.android.analysis.server.web.utils.FileOperationUtils;

public class ScanConst {
    public static String ScanFilePath = "/home/cary/Test/demo/ScanFiles/";
    public static String WorkPath = "/home/cary/Test/demo/WorkPath/";
    public static String DecompressFilePath = "/home/cary/Test/demo/DecompressFile/";
    public static String TempFilePath = "/home/cary/Test/demo/TempFile/";

    public static String MacUrl = "http://127.0.0.1";
    public static String MacPort = "5000";
    /*
     Do some preparations, create directory
     */
    static {
        FileOperationUtils.mkdirIfNotExists(ScanFilePath);
        FileOperationUtils.mkdirIfNotExists(WorkPath);
        FileOperationUtils.mkdirIfNotExists(DecompressFilePath);
        FileOperationUtils.mkdirIfNotExists(TempFilePath);
    }
}
