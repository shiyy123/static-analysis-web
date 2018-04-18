package cn.iselab.android.analysis.server.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author henrylee
 */
public class BaseData {

    @Override
    public String toString() {

        Class clazz = getClass();
        Field[] fields = clazz.getDeclaredFields();

        String fieldFormat = "%s=%s, ";
        String format = "%s[%s]";

        Method[] methods = clazz.getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
                methodMap.put(method.getName().toLowerCase(), method);
            }
        }

        StringBuilder fieldsSB = new StringBuilder();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldValueStr = null;
            try {
                Method m = methodMap.get("get" + fieldName.toLowerCase());
                if (m == null) {
                    continue;
                }
                fieldValueStr = String.valueOf(m.invoke(this));
            } catch (IllegalAccessException | InvocationTargetException e) {
                fieldValueStr = null;
                e.printStackTrace();
            }
            fieldsSB.append(String.format(fieldFormat, fieldName, fieldValueStr));
        }

        String fieldsStr = fieldsSB.toString();
        if (fieldsStr.lastIndexOf(", ") != -1) {
            fieldsStr = fieldsStr.substring(0, fieldsStr.lastIndexOf(", "));
        }

        return String.format(format, clazz.getSimpleName(), fieldsStr);

    }

}

