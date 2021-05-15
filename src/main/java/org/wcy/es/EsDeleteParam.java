package org.wcy.es;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @Author: wcy
 * @Date: 2020/11/3 14:42
 * @Version 1.0
 */
@Accessors(chain = true)
public class EsDeleteParam {

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
    private String index;

    @Getter
    @Setter
    private String id;


    public EsDeleteParam(String id, String index){
        this.index = index;
        this.id = id;
    }
}
