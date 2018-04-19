package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.dao.ApkDao;
import cn.iselab.android.analysis.server.dao.CertInfoDao;
import cn.iselab.android.analysis.server.data.Apk;
import cn.iselab.android.analysis.server.data.CertInfo;
import cn.iselab.android.analysis.server.service.CertInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.Iterator;

@Service
public class CertInfoServiceImpl implements CertInfoService {

    @Autowired
    private CertInfoDao certInfoDao;
    @Autowired
    private ApkDao apkDao;

    private String issued;
    private String certInfo;

    /**
     *
     * @param MD5 the MD5 of apk file
     * @return String数组，re[0]为证书状态，re[1]为证书内容
     */
    @Override
    public String[] getCertInfo(String MD5) {
        String[] re=new String[2];
        Process process = null;
        BufferedReader reader = null;
        StringBuffer bf = null;
        String certFile = "";

        File parent = new File("/home/cary/Test/demo/zipped_apks/" + MD5 + "/META-INF/");
        File[] files = parent.listFiles();
        boolean found = false;
        for (File ff : files) {
            if (ff.getName().equals("CERT.RSA")) {
                found=true;
                certFile = "CERT.RSA";
                break;
            }
        }
        if (!found)
            for (File ff : files) {
                if (!ff.isDirectory()) {
                    String name = ff.getName();
                    if (name.substring(name.indexOf('.') + 1).toLowerCase().equals("rsa")) {
                        certFile = name;
                    } else if (name.substring(name.indexOf('.') + 1).toLowerCase().equals("dsa")) {
                        certFile = name;
                    }
                }
            }
        if (!certFile.equals("")) {
            this.issued = "good";
            try {
                /*Runtime run = Runtime.getRuntime();
                process = run.exec("java -jar ./android-analysis-server/src/main/resources/tools/CertPrint.jar /home/cary/Test/demo/zipped_apks/" + MD5
                        + "/META-INF/" + certFile);

                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                bf = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    bf.append(line);
                    bf.append("\n");
                }
                this.certInfo = bf.toString();*/
                String location="/home/cary/Test/demo/zipped_apks/"+MD5+"/META-INF/"+certFile;
                String info=this.byMyOwn(location);
                this.certInfo=info;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.certInfo = "No Code Signing Certificate Found!";
            this.issued = "missing";
        }
        if(certInfo.indexOf("Issuer: CN=Android Debug")!=-1
                || certInfo.indexOf("Subject: CN=Android Debug")!=-1){
            this.issued="bad";
        }
        re[0]=issued;
        re[1]=certInfo;
        return re;
    }

    @Override
    public void save(CertInfo certInfo) {
        certInfoDao.save(certInfo);
    }

    @Override
    public CertInfo getCert(String MD5) {
        Apk a=apkDao.findByMd5(MD5);
        if(a!=null){
            String id=a.getId()+"";
            return certInfoDao.findByApkID(id);
        }
        return null;
    }


    /**
     *
     * @param location the location of the Cert File
     * @return the Cert Information
     * @throws Exception
     * @throws IOException
     * @throws CertificateException
     */
    private String byMyOwn(String location) throws Exception{
        String re="";
        FileInputStream localFileInputStream = new FileInputStream(location);
        CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
        Collection localCollection = localCertificateFactory.generateCertificates(localFileInputStream);
        Iterator localIterator = localCollection.iterator();
        while (localIterator.hasNext()) {
            Certificate localCertificate = (Certificate)localIterator.next();
            String str = "";
            str = localCertificate.toString().replace(localCertificate.getPublicKey().toString(), "");
            re+=str+"\n";
        }
        return re;
    }
}
