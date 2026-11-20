package org.demo.common.domain;

public class Pageable {

    private int pageIndex;
    private int pageSize;

    public Pageable() {
        this.pageIndex = 1;
        this.pageSize = 10;
    }

    public Pageable(int pageIndex, int pageSize) {
        if (pageIndex < 1 || pageSize < 1) {
            throw new IllegalArgumentException("pageIndex or pageSize must > 0");
        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
