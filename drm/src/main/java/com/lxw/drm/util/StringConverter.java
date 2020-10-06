/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @author xichen.lxw
 * @version $Id: StringConverter.java, v 0.1 2020-10-06 11:34 上午 xichen.lxw Exp $$
 */
public class StringConverter {
    /**
     * TODO 需要考虑java的类型转换。
     *
     *  float 4 字节 32位IEEE 754单精度
     *  double 8 字节 64位IEEE 754双精度
     *  byte 1字节 -128到127
     *  short 2 字节 -32,768到32,767
     *  int 4 字节 -2,147,483,648到2,147,483,647
     *  long 8 字节 -9,223,372,036,854,775,808到9,223,372,036, 854,775,807
     *  char 2 字节 整个Unicode字符集
     *  boolean 1 位 True或者false
     *
     * @param type the type
     * @param value the value
     * @return object
     */
    public static Object convertObjectFromString(Class<?> type, String value) {
        if (type == String.class) {
            return value;
        } else if (type == Float.class || type == float.class) {
            return Float.valueOf(value);
        } else if (type == Double.class || type == double.class) {
            return Double.valueOf(value);
        } else if (type == Byte.class || type == byte.class) {
            return Byte.valueOf(value);
        } else if (type == Short.class || type == short.class) {
            return Short.valueOf(value);
        } else if (type == Integer.class || type == int.class) {
            return Integer.valueOf(value);
        } else if (type == Long.class || type == long.class) {
            return Long.valueOf(value);
        } else if (type == Character.class || type == char.class) {
            return Character.valueOf(value.toCharArray()[0]);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.valueOf(value);
        } else if (type == List.class) {
            //如果属性类型是List，则认为是我们特殊定义的List<HashMap<Object, Object>>类型，用它特有的方式解析。
            return paresJson2ListMap(value);
        } else if (type.isArray()) {
            //如果属性类型是数组，则当JSON数组来解析。返回封装后的ResourceArray对象
            Class<?> componentType = type.getComponentType();
            return JSON.parseArray(value, componentType).toArray(
                (Object[]) Array.newInstance(componentType, 0));
        } else {
            //其他情况都认为是自定义类型，按JSON对象来解析。
            return JSON.parseObject(value, type);
        }
    }

    /**
     * Pares json to list map list.
     *
     * @param s the s
     * @return the list
     */
    public static List<HashMap<Object, Object>> paresJson2ListMap(String s) {
        List<HashMap<Object, Object>> result = new ArrayList<HashMap<Object, Object>>();
        s = s.substring(1, s.length() - 1);
        while (s.contains("{")) {
            HashMap<Object, Object> tmpMap = new HashMap<Object, Object>();
            String mapStr = s.substring(s.indexOf("{") + 1, s.indexOf("}"));
            String[] entryStr = mapStr.split(",");
            for (String entry : entryStr) {
                entry = entry.trim();
                String[] keyAndValue = entry.split("=");
                if (keyAndValue.length == 1) {
                    tmpMap.put(keyAndValue[0], "");
                }
                if (keyAndValue.length == 2) {
                    tmpMap.put(keyAndValue[0], keyAndValue[1]);
                }
            }
            result.add(tmpMap);
            s = s.substring(s.indexOf("}") + 1);

        }
        return result;
    }

    /**
     * 按照signature，将字符串value转为目标类型
     * @param signature the signature
     * @param value the value
     * @return object
     */
    public static Object convertObjectFromString(String signature, String value) {
        return convertObjectFromString(getTypeAnaway(signature), value);
    }

    /**
     * 通过字符串名称获得基本类型、或对象类型
     * @param name the name
     * @return type anaway
     */
    public static Class<?> getTypeAnaway(String name) {
        if (name.equals("byte")) {
            return byte.class;
        }
        if (name.equals("short")) {
            return short.class;
        }
        if (name.equals("int")) {
            return int.class;
        }
        if (name.equals("long")) {
            return long.class;
        }
        if (name.equals("char")) {
            return char.class;
        }
        if (name.equals("float")) {
            return float.class;
        }
        if (name.equals("double")) {
            return double.class;
        }
        if (name.equals("boolean")) {
            return boolean.class;
        }
        if (name.equals("void")) {
            return void.class;
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 从Object到String
     * TODO 暂时直接使用toString返回
     * @param obj the obj
     * @return string
     */
    public static String convertObjectToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Float || obj instanceof Double || obj instanceof Integer
            || obj instanceof Long || obj instanceof Short || obj instanceof Byte
            || obj instanceof Boolean || obj instanceof Character) {
            return obj.toString();
        }
        if (obj instanceof List) {
            return obj.toString();
        }
        return JSON.toJSONString(obj);
    }
}