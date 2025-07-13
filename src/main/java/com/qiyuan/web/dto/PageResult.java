package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private int total;      // 總筆數
    private int page;       // 第幾頁
    private int size;       // 每頁幾筆
    private List<T> list;   // 本頁資料
}
