package com.alja.visit.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VisitSorter {

    private static final String DEFAULT_SORT_DIRECTION = "ASC";
    private static final String DEFAULT_SORT_COLUMN = "visitStartDate";

    public Sort getDefaultSort() {
        Sort.Direction defaultSortDirection = Sort.Direction.valueOf(DEFAULT_SORT_DIRECTION);
        return Sort.by(defaultSortDirection, DEFAULT_SORT_COLUMN);
    }
}
