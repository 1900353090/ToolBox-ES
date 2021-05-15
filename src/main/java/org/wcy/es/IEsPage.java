package org.wcy.es;

import cn.hutool.db.sql.Order;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wcy
 * @Date: 2020/11/5 16:10
 * @Version 1.0
 */
public interface IEsPage<T> extends Serializable {


    /**
     * 获取排序信息，排序的字段和正反序
     *
     * @return 排序信息
     */
    List<Order> orders();


    /**
     * 当前分页总页数
     */
    default long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 内部什么也不干
     * <p>只是为了 json 反序列化时不报错</p>
     */
    default IEsPage<T> setPages(long pages) {
        // to do nothing
        return this;
    }


    /**
     * 分页记录列表
     *
     * @return 分页对象记录列表
     */
    List<T> getRecords();

    /**
     * 设置分页记录列表
     */
    IEsPage<T> setRecords(List<T> records);

    /**
     * 当前满足条件总行数
     *
     * @return 总条数
     */
    long getTotal();

    /**
     * 设置当前满足条件总行数
     */
    IEsPage<T> setTotal(long total);

    /**
     * 获取每页显示条数
     *
     * @return 每页显示条数
     */
    long getSize();

    /**
     * 设置每页显示条数
     */
    IEsPage<T> setSize(long size);

    /**
     * 当前页
     *
     * @return 当前页
     */
    long getCurrent();

    /**
     * 设置当前页
     */
    IEsPage<T> setCurrent(long current);

    /**
     * IEsPage 的泛型转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 IEsPage
     */
    @SuppressWarnings("unchecked")
    default <R> IEsPage<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        return ((IEsPage<R>) this).setRecords(collect);
    }


}
