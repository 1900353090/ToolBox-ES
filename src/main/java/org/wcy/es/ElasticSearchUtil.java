package org.wcy.es;

import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: wcy
 * @Date: 2020/7/16 15:22
 * @Version 1.0
 */
public class ElasticSearchUtil {


    /**
     * 给对象，赋值 高亮属性值
     * @param highlightFields
     * @param clazz
     * @param mgdGoods
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object setObject(Map<String, HighlightField> highlightFields, Class<?> clazz, Object mgdGoods){
        Set<String> strings = highlightFields.keySet();

        if (CollectionUtils.isNotEmpty(strings)){
            for (String k:strings){
                //获取 高亮字段对象
                HighlightField highlightField = highlightFields.get(k);
                Text[] fragments = highlightField.fragments();

                if (k.indexOf(".")>0) {
                    String[] split = k.split("\\.");
                    for (int a=0;a<split.length;a++) {
                        char[] sc = split[a].toCharArray();
                        sc[0] -= 32;
                        split[a] = String.valueOf(sc);
                    }
                    int i=0;
                    Method method = BeanUtils.findMethod(mgdGoods.getClass(), "get" + split[i]);
                    Object invoke1 = null;
                    try {
                        invoke1 = method.invoke(mgdGoods);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    Object invoke = getField(invoke1, split, ++i,fragments[0].toString());


                }else {
                    char[] sc = k.toCharArray();
                    sc[0] -= 32;
                    k = String.valueOf(sc);
                    Method method = BeanUtils.findMethod(mgdGoods.getClass(), "set" + k, String.class);
                    try {
                        Object invoke = method.invoke(mgdGoods, fragments[0].toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mgdGoods;
    }

    /**
     *
     * @param obj  成员变量
     * @param split
     * @param i
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getField(Object obj,String[] split,int i,String value){

        Method method = null;
        try {
            if (i>=split.length-1){
                method = BeanUtils.findMethod(obj.getClass(), "set" + split[i], String.class);
                return method.invoke(obj,value);
            }
            method = BeanUtils.findMethod(obj.getClass(), "get" + split[i]);
            return ElasticSearchUtil.getField( method.invoke(obj),split,++i,value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 如果为 null,返回 空字符串
     * @param value
     * @return
     */
    public static Object fillValue(Object value){
        return Objects.isNull(value)?"":value;
    }

}
