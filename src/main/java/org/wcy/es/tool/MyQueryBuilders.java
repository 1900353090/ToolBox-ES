package org.wcy.es.tool;

/**
 * <p>Title : MyQueryBuilder.java</p>
 * <p>Description : es条件构造器封装类</p>
 * <p>DevelopTools : IntelliJ IDEA 2018.2.3 x64</p>
 * <p>DevelopSystem : Windows 10</p>
 * <p>Company : org.wcy</p>
 * @author : WangChenYang
 * @date : 2020/11/6 10:19
 * @version : 0.0.1
 */
public final class MyQueryBuilders {

    private MyQueryBuilders() {

    }

    /** 
     * @Description: es，bool查询
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/11/6-10:25
    */
    public static MyBoolQueryBuilder boolQuery() {
        return new MyBoolQueryBuilder();
    }

}
