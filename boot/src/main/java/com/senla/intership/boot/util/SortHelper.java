package com.senla.intership.boot.util;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SortHelper {

    public static Sort getAllSortValues(String direction,String[] sort){
        return Sort.by(Arrays.stream(sort)
                .map(s -> s.split(",", 1))
                .map(array -> new Sort.Order(orderDirection(direction), array[0]).ignoreCase())
                .collect(Collectors.toList()));
    }
    public static Sort.Direction orderDirection(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("DESC")) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }
}
