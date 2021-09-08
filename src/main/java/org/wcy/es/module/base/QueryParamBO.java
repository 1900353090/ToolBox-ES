package org.wcy.es.module.base;


import org.wcy.es.module.QueryParam;

/**
 * <p>查询条件参数类</p>
 *
 * @Author: seowen
 * @Date: 2020/6/17 10:30
 * @Version 1.0
 */
public class QueryParamBO implements QueryParam {

    /**
     * 页码
     */
    protected Integer pageNo = DEFAULT_PAGE_NO;
    /**
     * 分页大小
     */
    protected Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 页码
     */
    @Override
    public Integer getPageNo() {
        if (this.pageNo == null) {
            return DEFAULT_PAGE_NO;
        }

        return this.pageNo;
    }

    /**
     * 页码
     */
    @Override
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 分页大小
     */
    @Override
    public Integer getPageSize() {
        if (this.pageSize == null) {
            return DEFAULT_PAGE_SIZE;
        }
        if (this.pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }
        return this.pageSize;
    }

    /**
     * 分页大小
     */
    @Override
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
