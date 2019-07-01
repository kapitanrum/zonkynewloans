package com.github.zonky.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "policyPeriodFrom",
        "policyPeriodTo"
})
@Getter
@Setter
@EqualsAndHashCode
public class InsuranceHistory {
    private LocalDate policyPeriodFrom;
    private LocalDate policyPeriodTo;
}
