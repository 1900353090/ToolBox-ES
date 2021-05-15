package org.wcy.es.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.annotation.*;

/**
 * <p>Title : ESColumn.java</p>
 * <p>Description : </p>
 * <p>DevelopTools : IntelliJ IDEA 2018.2.3 x64</p>
 * <p>DevelopSystem : Windows 10</p>
 * <p>Company : org.wcy</p>
 * @author : WangChenYang
 * @date : 2020/11/6 17:42
 * @version : 0.0.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESColumn {

    @Getter
    @AllArgsConstructor
    public enum Vague {
        MARCH(1,"分词查询"),
        TERM(2,"精准查询"),
        TERMS(3,"集合查询");
        private Integer value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum Querytype {
        MUST(1,"必须满足"),
        SHOULD(2,"其中一个满足");
        private Integer value;
        private String desc;
    }

    //查询类型
    Vague vague() default Vague.TERM;

    //查询方式
    Querytype querytype() default Querytype.MUST;

    //字段名
    String field() default "";

    //等于(true)和不等于(false)
    boolean equality() default true;

}
