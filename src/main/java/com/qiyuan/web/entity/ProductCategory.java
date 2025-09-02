package com.qiyuan.web.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    private String id;

    /** 分類名稱 */
    private String name;

    /** 分類描述 */
    private String description;

    /** 啟用狀態 */
    private Boolean status;

    /** 分類順序 */
    private Integer sortOrder;

    /** 建立時間 */
    private Date createTime;

    /** 更新時間 */
    private Date updateTime;
}
