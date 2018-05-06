package cn.iselab.android.analysis.server.web.utils;

import cn.iselab.android.analysis.server.constant.PlatformType;
import cn.iselab.android.analysis.server.constant.ScanConst;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CommonUtils {

    // time format
    private static SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // file size format
    private static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * In case the block of the stream, and the process will die
     * @param inputStream the stream of process
     */
    public static void storeMessage(final InputStream inputStream, final String storeFilePath){
        new Thread(() -> {
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);
            File file = new File(storeFilePath);
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
                String line;
                while ((line = br.readLine()) != null){
                    fileOutputStream.write(line.getBytes("utf-8"));
                    fileOutputStream.write("\r\n".getBytes());
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                System.err.println("print processMessage Error");
            }

        }).start();
    }

    /**
     * Get the current Time
     * @return
     */
    public static String getCurTime(){
        return dff.format(new Date());
    }

    /**
     * Get the scan file size
     */
    public static String getFileSize(File file){
        return df.format(file.length() / 1024 / 1024) + "MB";
    }


    /**
     * Get the upload file's scan type
     * @param filePath
     * @return
     */
    public static PlatformType getScanFileType(String filePath) {
        PlatformType platformType = PlatformType.Unknown;

        if(filePath.toLowerCase().endsWith(".apk")){
            platformType = PlatformType.APK;
        }else if(filePath.toLowerCase().endsWith(".ipa")){
            platformType = PlatformType.IPA;
        }else {
            String decompressPath = FileOperationUtils.deCompressZip(new File(filePath), ScanConst.TempFilePath);
            File file = new File(decompressPath);
            for (String s: Objects.requireNonNull(file.list())){
                if(s.endsWith(".xcodeproj")){
                    platformType = PlatformType.iOS_Source;
                } else if(s.equals("AndroidManifest.xml")){
                    platformType = PlatformType.Android_Source;
                }
            }
            File manifestFile = new File(decompressPath + File.separator + "app/src/main/AndroidManifest.xml");
            if(manifestFile.exists()) {
                return PlatformType.Android_Source;
            }
            manifestFile.delete();
            FileOperationUtils.delFolder(decompressPath);
        }
        return platformType;
    }

    public static JSONArray httpGetJSONArray(String url){
        JSONArray jsonResult = null;
        try{
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = new JSONArray(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            }else {
                System.err.println("Get request fail" + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static JSONObject httpGetJSONObject(String url){
        JSONObject jsonResult = null;
        try{
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = new JSONObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            }else {
                System.err.println("Get request fail" + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try{
            if(null!=jsonParam){
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");

            if(result.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String str;
                try{
                    str = EntityUtils.toString(result.getEntity());
                    jsonResult = new JSONObject(str);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }



    public static void main(String[] args){
        String url = "http://127.0.0.1:5000/result/7d53c68c-e994-4649-bce2-735a65221f77";
        JSONObject param = new JSONObject();

//        param.put("toolName", "mt_asc");
//        param.put("jobs", "-F /workspace/android_hello.zip");
//        JSONObject jsonObject = httpPost(url, param);
//        JSONObject jsonObject = httpGet(url);
//        System.out.println(jsonObject.toString());
    }
}
