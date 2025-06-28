package com.qiyuan.common.annotations;

import com.qiyuan.common.validators.RecordPeriodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RecordPeriodValidator.class)
public @interface ValidRecordPeriod {
    String message() default "開始日期必須小於結束日期";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
