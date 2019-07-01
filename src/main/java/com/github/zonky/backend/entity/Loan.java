package com.github.zonky.backend.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "url",
        "name",
        "story",
        "purpose",
        "photos",
        "userId",
        "nickName",
        "termInMonths",
        "interestRate",
        "revenueRate",
        "annuity",
        "annuityWithInsurance",
        "rating",
        "topped",
        "amount",
        "currency",
        "remainingInvestment",
        "reservedAmount",
        "investmentRate",
        "covered",
        "datePublished",
        "published",
        "deadline",
        "investmentsCount",
        "questionsCount",
        "region",
        "mainIncomeType",
        "insuranceActive",
        "insuranceHistory"
})
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Loan {

    private Integer id;
    private String url;
    private String name;
    private String story;
    private Loan.Purpose purpose;
    private List<Photo> photos = new ArrayList<>();
    private Integer userId;
    private String nickName;
    private Double termInMonths;
    private Double interestRate;
    private Double revenueRate;
    private Double annuity;
    private Double annuityWithInsurance;
    private String rating;
    private Object topped;
    private Double amount;
    private String currency;
    private Double remainingInvestment;
    private Double reservedAmount;
    private Double investmentRate;
    private Boolean covered;
    private ZonedDateTime datePublished;
    private Boolean published;
    private ZonedDateTime deadline;
    private Double investmentsCount;
    private Double questionsCount;
    private String region;
    private String mainIncomeType;
    private Boolean insuranceActive;
    private List<InsuranceHistory> insuranceHistory = new ArrayList<>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * For mapping with not defined property.
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public enum Purpose {

        REFINANCING("REFINANCING"),
        AUTO_MOTO("AUTO_MOTO"),
        EDUCATION("EDUCATION"),
        TRAVEL("TRAVEL"),
        ELECTRONICS("ELECTRONICS"),
        HEALTH("HEALTH"),
        HOUSEHOLD("HOUSEHOLD"),
        OWN_PROJECT("OWN_PROJECT"),
        OTHER("OTHER");
        private final static Map<String, Loan.Purpose> CONSTANTS = new HashMap<>();

        static {
            for (Loan.Purpose c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        Purpose(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Loan.Purpose fromValue(String value) {
            Loan.Purpose constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

    }

}
