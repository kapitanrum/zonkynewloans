package com.github.zonky.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Page<T> {

    private List<T> content;
    private int totalCount;
}
