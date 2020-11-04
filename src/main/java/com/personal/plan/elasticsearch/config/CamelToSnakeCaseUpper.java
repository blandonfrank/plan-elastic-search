package com.personal.plan.elasticsearch.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Custom naming property to match what's coming from Elastic search at serializing time
 * An extension of snake case, except all is upper case
 */
public class CamelToSnakeCaseUpper extends PropertyNamingStrategy.SnakeCaseStrategy {

    @Override
    public String translate(String input) {
        return super.translate(input).toUpperCase();
    }

}
