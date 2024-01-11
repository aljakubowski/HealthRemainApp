package com.alja.visit.service;

import com.alja.visit.model.VisitEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class VisitSorter {

    private static final String DEFAULT_SORT_DIRECTION = "ASC";
    private static final String DEFAULT_SORT_COLUMN = "visitStartDate";

    public Sort getDefaultSort() {
        Sort.Direction defaultSortDirection = Sort.Direction.valueOf(DEFAULT_SORT_DIRECTION);
        return Sort.by(defaultSortDirection, DEFAULT_SORT_COLUMN);
    }
    public void sortVisitsDefault(List<VisitEntity> visitEntities) {
        visitEntities.sort(Comparator.comparing(VisitEntity::getVisitStartDate));
    }
}
