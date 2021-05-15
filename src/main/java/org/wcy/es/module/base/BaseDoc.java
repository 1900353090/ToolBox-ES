package org.wcy.es.module.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @Author: wcy
 * @Date: 2020/6/10 19:22
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class BaseDoc implements Base {
    private static final long serialVersionUID = 1L;


    @Id
    protected String id;

    @Field(type = FieldType.Boolean)
    protected Boolean remove;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern ="uuuu-MM-dd HH:mm:ss.SSS")
    protected LocalDateTime createTime;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern ="uuuu-MM-dd HH:mm:ss.SSS")
    protected LocalDateTime updateTime;

    @Field(type = FieldType.Long)
    protected String createUserId;

    @Field(type = FieldType.Long)
    protected String lastUserId;

}
