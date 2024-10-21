package com.acnovate.kafka.consumer.AuditManager.domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LCSResponse<T> {

    private String objectName;
    private List<T> objectResponseList;

}
