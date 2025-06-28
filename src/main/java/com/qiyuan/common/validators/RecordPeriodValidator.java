package com.qiyuan.common.validators;

import com.qiyuan.common.annotations.ValidRecordPeriod;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecordPeriodValidator implements ConstraintValidator<ValidRecordPeriod, RecordPeriodRequest> {
    @Override
    public boolean isValid(RecordPeriodRequest value, ConstraintValidatorContext context) {
        if (value == null) return true; // 交給 @NotNull 檢查
        if (value.getStartTime() == null || value.getEndTime() == null) return true; // 交給 @NotNull 檢查
        return value.getStartTime().before(value.getEndTime());
    }
}
