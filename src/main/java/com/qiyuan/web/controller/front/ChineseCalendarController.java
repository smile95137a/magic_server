package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.ChineseCalendarRequest;
import com.qiyuan.web.service.ChineseCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
@Tag(name = "農民曆查詢", description = "查詢指定日期的農民曆資訊")
@RequiredArgsConstructor
public class ChineseCalendarController {

    private final ChineseCalendarService chineseCalendarService;

    @Operation(summary = "查詢農民曆資料", description = "根據年/月/日查詢農民曆對應資訊")
    @PostMapping("/query")
    public List<Map<String, Object>> queryCalendar(@RequestBody @Validated ChineseCalendarRequest req) throws Exception {
        return chineseCalendarService.queryCalendar(req.getYear(), req.getMonth(), req.getDay());
    }
}
