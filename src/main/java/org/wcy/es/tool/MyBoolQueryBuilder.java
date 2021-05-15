package org.wcy.es.tool;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.index.query.*;
import org.wcy.es.annotation.ESColumn;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>Title : MyBoolQueryBuilder.java</p>
 * <p>Description : es，bool查询</p>
 * <p>DevelopTools : IntelliJ IDEA 2018.2.3 x64</p>
 * <p>DevelopSystem : Windows 10</p>
 * <p>Company : org.wcy</p>
 * @author : WangChenYang
 * @date : 2020/11/6 10:26
 * @version : 0.0.1
 */
public class MyBoolQueryBuilder extends BoolQueryBuilder {

    /**
     * @Description: 获取查询对象
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/11/9-13:57
    */
    private AbstractQueryBuilder<?> getQueryBuilder(ESColumn.Vague vague, String name, Object value) {
        switch (vague) {
            case TERMS:
                Collection<Object> list = (Collection<Object>) value;
                Set<Object> values = new HashSet<>();
                for(Object obj:list) {
                    values.add(obj);
                }
                return new TermsQueryBuilder(name, values);
            case TERM:
                return new TermQueryBuilder(name, value);
            case MARCH:
                return new MatchQueryBuilder(name, value);
            default:
                return getQueryBuilder(ESColumn.Vague.MARCH, name, value);
        }
    }

    /**
     * @Description: 首字母转大写
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/3/14-12:35
     */
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))) {
            return s;
        }else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * @Description: match为模糊查询，会进行相应的分词操作
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/11/6-10:28
    */
    public MyBoolQueryBuilder query(Object para) {
        if(Objects.isNull(para)) {
            return this;
        }
        Class<?> c = para.getClass();
        //获取c的当前成员变量的属性描述器
        Field[] fields = c.getDeclaredFields();
        //PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(c);
        for(Field field:fields) {
            ESColumn esColumn = field.getAnnotation(ESColumn.class);
            if(Objects.isNull(esColumn)) {
                continue;
            }
            //获取当前属性描述的读取方法
            Object value = null;
            try {
                Method get = c.getMethod("get" + toUpperCaseFirstOne(field.getName()));
                value = get.invoke(para);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(Objects.nonNull(value) && value.toString().length() > 0) {
                String name = field.getName();
                if(Strings.isNotBlank(esColumn.field())) {
                    name = esColumn.field();
                }
                AbstractQueryBuilder<?> queryBuilder = getQueryBuilder(esColumn.vague(), name, value);
                switch (esColumn.querytype()) {
                    case MUST:
                            if(esColumn.equality()) {
                                this.must(queryBuilder);
                            }else {
                                this.mustNot(queryBuilder);
                            }
                        break;
                    case SHOULD:
                            this.should(queryBuilder);
                        break;
                    default:
                        break;
                }
            }
        }
        return this;
    }

}
