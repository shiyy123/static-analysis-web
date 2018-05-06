package cn.iselab.android.analysis.server.service.impl;

import cn.iselab.android.analysis.server.constant.PlatformType;
import cn.iselab.android.analysis.server.constant.ScanConst;
import cn.iselab.android.analysis.server.constant.ScanStatus;
import cn.iselab.android.analysis.server.dao.SCDao;
import cn.iselab.android.analysis.server.data.SC;
import cn.iselab.android.analysis.server.data.SCVul;
import cn.iselab.android.analysis.server.data.SCVulRef;
import cn.iselab.android.analysis.server.service.SCAnalysisService;
import cn.iselab.android.analysis.server.service.SCVulRefService;
import cn.iselab.android.analysis.server.service.SCVulService;
import cn.iselab.android.analysis.server.web.utils.CommonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class SCAnalysisServiceImpl implements SCAnalysisService {

    @Autowired
    private SCVulRefService scVulRefService;

    @Autowired
    private SCVulService scVulService;

    @Autowired
    private SCDao scDao;

    private void save2Database(JSONArray jsonArray, Long scId){
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // Get all columns of the SCVul
            String targetTaskId = jsonObject.get("targetTaskId").toString();
            String riskLevel = jsonObject.get("riskLevel").toString();
            String solution= jsonObject.get("solution").toString();
            String vulType = jsonObject.get("vulType").toString();
            String extra = jsonObject.get("extra").toString();
            String name = jsonObject.get("name").toString();
            String description = jsonObject.get("description").toString();
            String vulClassify = jsonObject.get("vulClassify").toString();
            String updateTime = jsonObject.get("updateTime").toString();
            String source = jsonObject.get("source").toString();

            // construct scVul
            SCVul scVul = new SCVul(scId, targetTaskId, riskLevel, solution, vulType, extra, name, description, vulClassify, updateTime, source);
            // Get the id the inserted scVul
            Long scVulId = scVulService.save(scVul);
            JSONArray vulReferences = jsonObject.getJSONArray("vulReferences");
            for (int j = 0; j < vulReferences.length(); j++) {
                JSONObject vulRef =  vulReferences.getJSONObject(j);
                String vulRefSolution = vulRef.get("solution").toString();
                String vulRefDescription = vulRef.get("description").toString();
                String vulRefLocation = vulRef.get("location").toString();
                // construct scVulRef
                SCVulRef scVulRef = new SCVulRef(scVulId, vulRefSolution, vulRefDescription, vulRefLocation);
                scVulRefService.save(scVulRef);
            }
        }
    }

    @Override
    public int analysis(String md5) {
        // get the upload source code(sc)
        SC sc = scDao.findByMd5(md5);

        String curWorkPath = ScanConst.WorkPath + File.separator + sc.getUuid();
        String scanFilePath = curWorkPath + File.separator + sc.getAppName();
        Long scId = sc.getId();
        String platformType = sc.getPlatform();

        // Modify the status to scanning
        scDao.update(ScanStatus.scanning, md5);
        Process process;
        try {
            if(platformType.equals(PlatformType.Android_Source.name())){
                process = Runtime.getRuntime().exec("android_source_scan start -F " + scanFilePath, null, new File(curWorkPath));
                String resFilePath = curWorkPath + File.separator + "scanRes.txt";
                CommonUtils.storeMessage(process.getInputStream(), resFilePath);
                process.waitFor();
                // Read the scan res file
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(resFilePath)));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                // Get the res json array
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                save2Database(jsonArray, scId);

            }else if(platformType.equals(PlatformType.iOS_Source.name()) || platformType.equals(PlatformType.IPA.name())){
                // TODO, transport scan file to mac
                // Start
                String url = ScanConst.MacUrl + ":" + ScanConst.MacPort + "/start";
                JSONObject params = new JSONObject();
                params.put("toolName", "mt_asc");
                params.put("jobs", "-F /Users/qing/Desktop/helloworld.ipa");
                params.put("platformType", PlatformType.iOS_Source.name());
                JSONObject jsonStart = CommonUtils.httpPost(url, params);
                String jobId = jsonStart.get("jobId").toString();

                // Get status until get scan res
                url = ScanConst.MacUrl + ":" + ScanConst.MacPort + "/status/" + jobId;
                JSONObject jsonStatus = CommonUtils.httpGetJSONObject(url);
                while (jsonStatus.get("status").toString().equals("PROGRESS_CALCULATING")){
                    jsonStatus = CommonUtils.httpGetJSONObject(url);
                }

                // Get res and save to database
                url = ScanConst.MacUrl + ":" + ScanConst.MacPort + "/result/" + jobId;
                JSONArray jsonArray = CommonUtils.httpGetJSONArray(url);
                save2Database(jsonArray, scId);

            }else {
                System.err.println("Not find the type of the scan file");
            }

            // Modify the status to scanned
            scDao.update(ScanStatus.scanned, md5);
        } catch (IOException | InterruptedException e) {
            // Scan error, modify the status to scan
            scDao.update(ScanStatus.scan, md5);
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
}
