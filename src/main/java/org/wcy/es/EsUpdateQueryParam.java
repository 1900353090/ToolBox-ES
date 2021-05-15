package org.wcy.es;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.elasticsearch.script.Script;


/**
 * @Author: wcy
 * @Date: 2020/11/3 14:42
 * @Version 1.0
 */
@Accessors(chain = true)
public class EsUpdateQueryParam {

    /**
     * 默认1秒超时
     */
    private static final int DEFAULT_TIME_OUT = 1;

    /**
     * 超时时间，单位 秒
     */
    @Getter
    @Setter
    private Integer timeout = DEFAULT_TIME_OUT;
    @Getter
    @Setter
    private String[] indices;

    @Getter
    @Setter
    private Script script;


    private EsUpdateQueryParam(){}

    public EsUpdateQueryParam(String... indices){
        this.indices = indices;
    }

    public EsUpdateQueryParam(Script script, String... indices){
        this.indices = indices;
        this.script = script;
    }

}
