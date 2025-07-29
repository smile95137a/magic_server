package com.qiyuan.web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanternBlessingVO implements Serializable {
    private static final long serialVersionUID = 274336518433723488L;
    
	private String id;             
    private String name;
    private String message;
    private Date createTime;
    private String lanternCode;
    private int blessing;
}
