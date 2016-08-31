package com.cdesign.spittr.web.response;

import java.util.List;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
public class PageResponse<T> {
    public List<T> data;
    public int totalPages;
    public long totalElements;
    public boolean first;
    public boolean last;
    public boolean hasNext;
    public boolean hasPrevious;

    public PageResponse(List<T> data,
                        int totalPages,
                        long totalElements,
                        boolean first,
                        boolean last,
                        boolean hasNext,
                        boolean hasPrevious) {
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.last = last;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}