package com.mie.base.utils.UUID;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class UUIDGenerator {
    public UUIDGenerator() {
    }

    public static String generateUUID() {
        StringBuffer uuid = new StringBuffer();
        uuid.append(DateFormatUtils.format(new Date(), "yyyyMMddhhmmss"));
        uuid.append('-');
        uuid.append(UUID.randomUUID().toString().replaceAll("-", ""));
        return uuid.substring(0, 32);
    }

    public static String generateUUIDAndSetId(HashMap<String, Object> mybatisSqlContextMap) {
        return generateUUIDAndSetId(mybatisSqlContextMap, "id");
    }

    public static String generateUUIDAndSetId(HashMap<String, Object> mybatisSqlContextMap, String propertyName) {
        String uuid = generateUUID();

        try {
            Object object = mybatisSqlContextMap.get("_parameter");
            String nameOfGetMethod = "get" + StringUtils.capitalize(propertyName);
            Method getIdMethod = object.getClass().getMethod(nameOfGetMethod);
            if (getIdMethod != null) {
                Object id = getIdMethod.invoke(object);
                if (id != null && id instanceof String && StringUtils.isNotBlank((String)id)) {
                    return (String)id;
                }
            }

            String nameOfSetMethod = "set" + StringUtils.capitalize(propertyName);
            Method setIdMethod = object.getClass().getMethod(nameOfSetMethod, String.class);
            if (setIdMethod != null) {
                setIdMethod.invoke(object, uuid);
            }
        } catch (NoSuchMethodException var8) {
            var8.printStackTrace();
        } catch (SecurityException var9) {
            var9.printStackTrace();
        } catch (IllegalAccessException var10) {
            var10.printStackTrace();
        } catch (IllegalArgumentException var11) {
            var11.printStackTrace();
        } catch (InvocationTargetException var12) {
            var12.printStackTrace();
        }

        return uuid;
    }
}
