package cn.iselab.android.analysis.server.web.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
