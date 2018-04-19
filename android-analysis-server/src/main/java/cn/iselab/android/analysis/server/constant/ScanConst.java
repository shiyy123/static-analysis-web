package cn.iselab.android.analysis.server.constant;

import cn.iselab.android.analysis.server.web.utils.FileOperationUtils;

public class ScanConst {
    public static String ScanFilePath = "/home/cary/Test/demo/ScanFiles/";
    public static String WorkPath = "/home/cary/Test/demo/WorkPath/";
    public static String DecompressFilePath = "/home/cary/Test/demo/DecompressFile/";

    /*
     Do some preparations, create directory
     */
    static {
        FileOperationUtils.mkdirIfNotExists(ScanFilePath);
        FileOperationUtils.mkdirIfNotExists(WorkPath);
        FileOperationUtils.mkdirIfNotExists(DecompressFilePath);
    }
}
