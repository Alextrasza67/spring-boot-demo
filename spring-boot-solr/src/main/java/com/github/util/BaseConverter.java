package com.github.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 常用类型转换
 */
public class BaseConverter {

    public static String object2String(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }

    public static Long object2Long(Object obj) {
        String s = object2String(obj);
        return StringUtils.isBlank(s) ? 0L : Long.valueOf(s);
    }

    public static Double object2Double(Object obj) {
        String s = object2String(obj);
        return StringUtils.isBlank(s) ? 0.0 : Double.valueOf(s);
    }

    public static Integer object2Integer(Object obj) {
        String s = object2String(obj);
        return StringUtils.isBlank(s) ? 0 : Integer.valueOf(s);
    }

    public static String map2String(Map<?, ?> map) {
        StringBuilder str = new StringBuilder();
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                if (key != null) {
                    Object value = map.get(key);
                    if (value != null) {
                        str.append(key.toString()).append("=").append(value.toString()).append("&");
                    }
                }
            }
            str = new StringBuilder(str.toString().endsWith("&") ? str.substring(0, str.length() - 1) : str.toString());
        }
        return str.toString();
    }

    public static String map2JsonString(Map<?, ?> map) {
        StringBuilder str = new StringBuilder("[");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                if (key != null) {
                    Object value = map.get(key);
                    if (value != null) {
                        str.append("{'key':'").append(key).append("', 'value':'").append(map.get(key)).append("'},");
                    }
                }
            }
            str = new StringBuilder(str.toString().endsWith(",") ? str.substring(0, str.length() - 1) : str.toString());
        }
        str.append("]");
        return str.toString();
    }

    /**
     * 通过字典项将以特定分隔符连接的KEY字符串转换成VALUE字符串
     *
     * @param codeMap   字典表
     * @param keyStr    键字符串
     * @param separator 分隔符
     * @return 值字符串
     */
    public static String keyString2ValueStringByCodeMap(Map<String, String> codeMap, String keyStr, String separator) {
        StringBuilder rs = new StringBuilder();
        if (StringUtils.isNotBlank(keyStr) && StringUtils.isNotBlank(separator)) {
            String[] array = keyStr.split(separator);
            if (array.length > 0) {
                for (String anArray : array) {
                    rs.append(object2String(codeMap.get(anArray.trim()))).append(separator);
                }
            }
        }
        rs = new StringBuilder(rs.toString().endsWith(separator) ? rs.substring(0, rs.length() - 1) : rs.toString());
        return rs.toString();
    }

    /**
     * 将MAP的KEY和VALUE类型转换为字符串
     *
     * @param map 待转换的MAP
     * @return 已转换的MAP
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, String> transMapKeyAndValueType2String(Map map) {
        Map<String, String> rs = new LinkedHashMap<>();
        if (map != null && map.size() > 0) {
            for (Integer key : (Iterable<Integer>) map.keySet()) {
                rs.put(String.valueOf(key), object2String(map.get(key)));
            }
        }
        return rs;
    }

    /**
     * 将十进制转换成特定长度的二进制，空位使用0补齐
     *
     * @param max 最大值的十进制
     * @param num 待转换的十进制
     * @return 以字符串形式表示的二进制数字
     */
    public static String patchBinaryNumberWithZeroForMaxLength(int max, int num) {
        // 将最大值转换成二进制字符串，得到一个特定长度，并生成一个由"0"组成的特定长度的字符串作为格式
        String format = StringUtils.repeat("0", Integer.toBinaryString(max).length());
        // 使用格式字符串格式化待转换值的二进制字符串
        return new DecimalFormat(format).format(Long.valueOf(Integer.toBinaryString(num)));
    }

    /**
     * 字符串根据分隔符转化成元素非空的集合
     *
     * @param str 待处理的字符串
     * @param sep 分隔符
     * @return 集合
     */
    public static List<String> str2List(String str, String sep) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(sep)) {
            String[] array = str.split(sep);
            for (String anArray : array) {
                if (StringUtils.isNotBlank(anArray)) {
                    list.add(anArray.trim());
                }
            }
        }
        return list;
    }

    /**
     * Integer 转 int
     *
     * @param num 整型对象
     * @return 整型数值
     */
    public static int integer2Int(Integer num) {
        return num == null ? 0 : num;
    }

    public static String createMethodUniqueKey(Class clazz, String method, JSONObject params) {
        StringBuilder key = new StringBuilder();
        try {
            key.append(clazz.getName());
            key.append(".");
            key.append(method);
            if (params != null && !params.isEmpty()) {
                key.append("?");
                StringBuilder str = new StringBuilder();
                for (String i : params.keySet()) {
                    Object value = params.get(i);
                    str.append(i).append("=").append(value).append("&");
                }
                str.deleteCharAt(str.length() - 1);
                key.append(str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key.toString();
    }

    public static String createMethodUniqueKey(Class clazz, String method, Object object) {
        return createMethodUniqueKey(clazz, method, JSONObject.parseObject(JSON.toJSONString(object)));
    }

    public static JSONObject object2JSONObject(Object object, Class clazz, String[] fieldNames) {
        JSONObject jsonObject = new JSONObject(true);
        try {
            for (String fieldName : fieldNames) {
                Field field = clazz.getDeclaredField(fieldName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = propertyDescriptor.getReadMethod();
                jsonObject.put(fieldName, getMethod.invoke(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray list2JSONArray(List list, Class clazz, String[] fieldNames) {
        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object o : list) {
                jsonArray.add(object2JSONObject(o, clazz, fieldNames));
            }
        }
        return jsonArray;
    }

    public static JSONObject page2JSONObject(Page page, Class clazz, String[] fieldNames) {
        JSONObject result = new JSONObject();
        if (page != null) {
            result.put("totalPages", page.getTotalPages());
            result.put("number", page.getNumber());
            result.put("content", list2JSONArray(page.getContent(), clazz, fieldNames));
        }
        return result;
    }

    /**
     * 获取包含特定注解的属性名数组
     *
     * @param clazz           类对象
     * @param annotationClass 注解对象
     * @return
     */
    public static String[] getAnnotationsFieldNames(Class clazz, Class annotationClass) {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                fieldNames.add(field.getName());
            }
        }
        return fieldNames.toArray(new String[]{});
    }


    public static void main(String[] args) throws Exception {
//        JSONObject param = new JSONObject();
//        param.put("status", 1);
//        param.put("name", 2);
//        System.out.println(createMethodUniqueKey(JedisClient.class, "test1", param));
//
//        KyEntryInfo entity = new KyEntryInfo();
//        entity.setStatus(ConfigConstants.FLAG_TRUE);
//        System.out.println(createMethodUniqueKey(JedisClient.class, "test2", entity));

    }
}
