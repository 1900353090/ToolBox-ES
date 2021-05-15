package org.wcy.es.module;

/**
 * @author wcy
 * @since 2019/7/9
 */
public interface QueryParam {

    /**
     * 默认页码，第一页
     */
    int DEFAULT_PAGE_NO = 1;
    /**
     * 默认分页大小，默认10条记录
     */
    int DEFAULT_PAGE_SIZE = 10;
    /**
     * 每页最大条数
     */
    int MAX_PAGE_SIZE = 20;

    Integer getPageNo();

    /**
     * 页码
     */
    void setPageNo(Integer pageNo);

    /**
     * 分页大小
     */
    Integer getPageSize();

    /**
     * 分页大小
     */
    void setPageSize(Integer pageSize);
}
