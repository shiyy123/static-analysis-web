package cn.iselab.android.analysis.server.data;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Dvm_permission {
    private static Map<String, String[]> manifest_permission = new HashMap<String, String[]>();
    private static Map<String, String> manifest_permission_group = new HashMap<String, String>();

    static {
		/*
		 * TODO add the data into manifest_permission and
		 * manifest_permission_group trying to find out a easier way to add data
		 * except using put one by one the way I'm trying to use is using json
		 * file
		 */
        JsonParser parse = new JsonParser();
        try {
            JsonObject json = (JsonObject) parse.parse(new FileReader("./" +
                    "android-analysis-server/src/main/resources/dvm_permission.json"));
            JsonObject permission = json.get("MANIFEST_PERMISSION").getAsJsonObject();
            JsonObject group = json.get("MANIFEST_PERMISSION_GROUP").getAsJsonObject();
            Iterator<?> it = permission.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> entry = (Entry<?, ?>) it.next();
                String key = (String) entry.getKey();
                JsonArray ja = permission.get(key).getAsJsonArray();
                String[] data = new String[3];
                data[0] = ja.get(0).getAsString();
                data[1] = ja.get(1).getAsString();
                data[2] = ja.get(2).getAsString();
                manifest_permission.put(key, data);
            }

            Iterator<?> it2 = group.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<?, ?> entry = (Entry<?, ?>) it2.next();
                String key = (String) entry.getKey();
                String data = group.get(key).getAsString();
                manifest_permission_group.put(key, data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Map<String, String[]> getManifest_permission() {
        return manifest_permission;
    }

    public static Map<String, String> getManifest_permission_group() {
        return manifest_permission_group;
    }

}

