package com.xyz.caofancpu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caofanCPU on 2018/7/4.
 * 分页实体
 *
 * @param <T>
 */
@Deprecated
public class PageInfo<T> implements Serializable {
    
    private int pageNo = 1;// 当前页编号
    private int dbIndex = 0;// 起始行，通常该属性通过pageNo和pageSize计算得到
    private int pageSize = 10;// 每页显示条数
    private int totalRecordNumber;// 总共的记录条数
    private int totalPageNumber;// 总共的页数，通过总共的记录条数以及每页大小计算而得
    
    private List<T> beanList;
    
    public int getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(int pageNo) {
        //在设置当前页码的时候，就同时设置分页起始行
        int temp = (pageNo - 1) < 0 ? 0 : (pageNo - 1);
        this.dbIndex = temp * pageSize;
        this.pageNo = pageNo;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getDbIndex() {
        return dbIndex;
    }
    
    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }
    
    public int getTotalRecordNumber() {
        return totalRecordNumber;
    }
    
    public void setTotalRecordNumber(int totalRecordNumber) {
        if (totalRecordNumber % pageSize == 0) {
            this.totalPageNumber = totalRecordNumber / pageSize;
        } else {
            this.totalPageNumber = totalRecordNumber / pageSize + 1;
        }
        this.totalRecordNumber = totalRecordNumber;
    }
    
    public int getTotalPageNumber() {
        return totalPageNumber;
    }
    
    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }
    
    public List<T> getBeanList() {
        return beanList;
    }
    
    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }
    
    @Override
    public String toString() {
        return "PageInfo [currentPageNo=" + pageNo + ", dbIndex=" + dbIndex + ", dbNumber=" + pageSize + ", totalReacordNumber="
                + totalRecordNumber + ", totalPageNumber=" + totalPageNumber + "]";
    }
    
}
