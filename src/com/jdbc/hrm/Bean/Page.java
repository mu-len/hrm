package com.jdbc.hrm.Bean;

import java.util.List;

public class Page <T>{
    private int totalRecordSum;//总页数
    private int pageIndex;
    private int pageSize;
    private int totalPageSum;
    private List<T> list;

    public int getTotalRecordSum() {
        return totalRecordSum;
    }

    public void setTotalRecordSum(int totalRecordSum) {
        this.totalRecordSum = totalRecordSum;
        setTotalPageSum();
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        setTotalPageSum();
    }

    public int getTotalPageSum() {
        return totalPageSum;
    }

    public void setTotalPageSum() {
        this.totalPageSum = this.totalRecordSum%this.pageSize==0?this.totalRecordSum/this.pageSize:(this.totalRecordSum/this.pageSize)+1;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
