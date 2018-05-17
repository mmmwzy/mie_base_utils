package com.mie.base.utils.reflect;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by WangRicky on 2018/5/17.
 */
public abstract class ReflectUtils {
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    public ReflectUtils() {
    }

    public static Object getValue(Object instance, String propertyName) {
        if (StringUtils.isBlank(propertyName)) {
            throw new IllegalArgumentException("can not accept null");
        } else {
            propertyName = propertyName.trim();
            if (instance instanceof Map) {
                Map map = (Map)instance;
                if (map.containsKey(propertyName)) {
                    return map.get(propertyName);
                }
            }

            MetaObject reflectObject = MetaObject.forObject(instance, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
            return reflectObject.getValue(propertyName);
        }
    }

    public static void setValue(Object instance, String propertyName, Object value) {
        if (StringUtils.isBlank(propertyName)) {
            throw new IllegalArgumentException("can not accept null");
        } else {
            propertyName = propertyName.trim();
            MetaObject reflectObject = MetaObject.forObject(instance, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
            reflectObject.setValue(propertyName, value);
        }
    }

    public static Object invokeMethod(Object instance, String methodName, Object... objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(instance, methodName, objects);
    }
}

