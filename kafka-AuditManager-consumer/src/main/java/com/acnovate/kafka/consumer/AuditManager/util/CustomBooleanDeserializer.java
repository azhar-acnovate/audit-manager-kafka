package com.acnovate.kafka.consumer.AuditManager.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.apache.commons.lang3.StringUtils;

public class CustomBooleanDeserializer extends StdConverter<String, Boolean> {
    @Override
    public Boolean convert(String value) {
        // Custom logic to handle non-standard boolean representations
        if (StringUtils.isNotBlank(value)) {
            if ("1".equals(value)) {
                return true;
            } else if ("0".equals(value)) {
                return false;
            } else {
                // Handle other cases or throw an exception if needed
                throw new IllegalArgumentException("Invalid boolean representation: " + value);
            }
        } else {
            return null;
        }
    }
}