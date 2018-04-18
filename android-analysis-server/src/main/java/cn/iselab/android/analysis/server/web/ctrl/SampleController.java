package cn.iselab.android.analysis.server.web.ctrl;

import cn.iselab.android.analysis.server.data.Vulnerability;
import cn.iselab.android.analysis.server.service.*;
import cn.iselab.android.analysis.server.web.data.ApkForKivul;
import cn.iselab.android.analysis.server.web.data.VulnerabilityListVO;
import cn.iselab.android.analysis.server.web.data.wrapper.VunlerabilityListWrapper;
import cn.iselab.android.analysis.server.web.logic.AnalysisLogic;
import cn.iselab.android.analysis.server.web.logic.BaseInfoLogic;
import cn.iselab.android.analysis.server.web.logic.ManifestLogic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;



@RestController
public class SampleController extends BaseController {

    @Autowired
    CombineService combineService;
    @Autowired
    BaseInfoLogic baseInfoLogic;
    @Autowired
    ManifestLogic manifestLogic;
    @Autowired
    AnalysisLogic analysisLogic;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(HttpServletResponse response, HttpServletRequest request) {
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
            }
            String MD5=apk.getMd5();

            ArrayList<Vulnerability> manifestAnalysisResult=manifestLogic.analysis(MD5);

            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(5000);
        }
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
