package cn.iselab.android.analysis.server.service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.service.AaptService;
import org.springframework.stereotype.Service;


@Service
public class AaptServiceImpl implements AaptService {
    private String label;
    private String icon;
    private String versionName;
    private String sdkVersion;


    /**
     *
     * @param md5 the md5 of the apk to be analyzed
     */
    @Override
    public void analysis(String md5) {
        File f = new File(ScanConst.DecompressFilePath + md5 + "/aapt.txt");
        BufferedReader read = null;
        Runtime run = Runtime.getRuntime();
        StringBuffer bf = null;
        PrintWriter pw = null;
        try {
            Process p = run.exec(
                    "./android-analysis-server/src/main/resources/tools/aapt.exe dump badging" + ScanConst.DecompressFilePath + md5 + "/" + md5 + ".apk");
            read = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF8"));
            bf = new StringBuffer();
            String line;
            while ((line = read.readLine()) != null) {
                if (line.contains("application:")) {
                    String str1 = line.substring(line.indexOf("'") + 1);
                    String str2 = str1.substring(0, str1.indexOf("'"));
                    this.label = str2;
                    if (line.contains("icon=")) {
                        String str3 = line.substring(line.indexOf("icon=") + 4);
                        String str4 = str3.substring(str3.indexOf("'") + 1, str3.lastIndexOf("'"));
                        this.icon = str4;
                    }
                    // System.out.println(this.label+":"+this.icon);
                }
                if (line.contains("versionName=")) {
                    String s1 = line.substring(line.indexOf("versionName"));
                    String s2 = s1.substring(s1.indexOf("'") + 1, s1.lastIndexOf("'"));
                    this.versionName = s2;
                }else if(line.contains("sdkVersion:")){
                    String s1=line.substring(line.indexOf("'")+1,line.lastIndexOf("'"));
                    sdkVersion=s1;
                }
                bf.append(line);
                bf.append("\n");
            }
            pw = new PrintWriter(new FileOutputStream(f));
            pw.write(bf.toString());
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return the label(chinese name) of the apk
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     *
     * @return the versionName(x.y.z) of the apk
     */
    @Override
    public String getVersionName() {
        return versionName;
    }

    /**
     *
     * @return the icon location of the apk
     */
    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public String getSdkVersion() {
        return sdkVersion;
    }
}

