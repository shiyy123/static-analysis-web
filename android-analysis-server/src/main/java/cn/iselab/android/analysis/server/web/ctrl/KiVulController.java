package cn.iselab.android.analysis.server.web.ctrl;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.web.data.ApkForKivul;
import cn.iselab.android.analysis.server.web.data.ReturnMessage;
import cn.iselab.android.analysis.server.web.data.VulnerabilityListVO;
import cn.iselab.android.analysis.server.web.data.wrapper.VunlerabilityListWrapper;
import cn.iselab.android.analysis.server.web.logic.AnalysisLogic;
import cn.iselab.android.analysis.server.web.logic.BaseInfoLogic;
import cn.iselab.android.analysis.server.web.logic.ManifestLogic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.net.URLDecoder;
import java.util.ArrayList;

@RestController
public class KiVulController {

    @Autowired
    BaseInfoLogic baseInfoLogic;
    @Autowired
    ManifestLogic manifestLogic;
    @Autowired
    AnalysisLogic analysisLogic;


    @RequestMapping(value = "/analysisFromKiVul", method = RequestMethod.POST)
    public String analysis(HttpServletResponse response,HttpServletRequest request) {

        int status=200;
        String message="Success";

        Gson g=new Gson();

        String token=request.getHeader("token");
        if(token==null || !token.equals("mooctest.net")){
            status=5000;
            message="WRONG_TOKNE";
            return g.toJson(new ReturnMessage(status,message));
        }

        try {
            String body="";
            String str="";
            BufferedReader br = request.getReader();
            while((str=br.readLine())!=null){
                body+=str;
            }
            System.out.println(body);
            String json= URLDecoder.decode(body,"utf-8");

            JsonParser parse = new JsonParser();
            JsonElement e=parse.parse(json);
            JsonObject info=e.getAsJsonObject();
            //TODO
            //change the element name
            String apkID=info.get("apk_id").getAsString();
            String url=info.get("url").getAsString();
            JsonArray choose=info.get("item").getAsJsonArray();
            ArrayList<String> test_cry=new ArrayList<String>();
            for(int i=0;i<choose.size();++i){
                test_cry.add(choose.get(i).getAsString());
            }

            ApkForKivul apk=baseInfoLogic.analysis(url,apkID);
            if(apk==null){
                response.setStatus(5000);
                status=5001;
                message="APK_ERROR";
                return g.toJson(new ReturnMessage(status,message));
            }
            String MD5=apk.getMd5();

            ArrayList<Vulnerability> manifestAnalysisResult=manifestLogic.analysis(MD5);

            ArrayList<Vulnerability> androbugsAnalysisResult=analysisLogic.analysis(MD5);

            ArrayList<Vulnerability> re=baseInfoLogic.combine(manifestAnalysisResult,androbugsAnalysisResult);
            VunlerabilityListWrapper w=new VunlerabilityListWrapper();
            VulnerabilityListVO vo=w.wrap(re,apkID);

            System.out.println(postToKivul(vo));

            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(5000);
            status=5002;
            message="ANALYSIS_ERROR";
            return g.toJson(new ReturnMessage(status,message));
        }
        return g.toJson(new ReturnMessage(status,message));
    }

    private String postToKivul(VulnerabilityListVO vo) throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        ObjectMapper mapper=new ObjectMapper();
        String url="http://kivul.mooctest.net:8080/kivul-api/vuls/addVuls";
        HttpPost post=new HttpPost(url);
        String serializedParam = mapper.writeValueAsString(vo);
        StringEntity entity = new StringEntity(serializedParam, "UTF-8");
        entity.setContentType("application/json");
        post.setHeader("Content-Type","application/json");
        post.setHeader("token","mooctest.net");
        post.setEntity(entity);
        HttpResponse result=httpClient.execute(post);
        return EntityUtils.toString(result.getEntity());
    }
}











