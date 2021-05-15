package org.wcy.es;

import cn.hutool.db.sql.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: wcy
 * @Date: 2020/11/5 16:08
 * @Version 1.0
 */
public class EsPage<T> implements IEsPage<T> {
    private static final long serialVersionUID = 8545996863226528798L;

    /**
     * 查询数据列表
     */
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;

    /**
     * 当前页
     */
    protected long current = 1;

    /**
     * 是否进行 count 查询
     */
    protected boolean isSearchCount = true;

    /**
     * 排序字段信息
     */
    @Getter
    @Setter
    protected List<Order> orders = new ArrayList<>();

    public EsPage() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public EsPage(long current, long size) {
        this(current, size, 0);
    }

    public EsPage(long current, long size, long total) {
        this(current, size, total, true);
    }

    public EsPage(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public EsPage(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }


    @Override
    public List<Order> orders() {
        return getOrders();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IEsPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public IEsPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public IEsPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public IEsPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
