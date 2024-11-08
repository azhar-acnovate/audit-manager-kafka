package com.acnovate.auditmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuditRequest implements Serializable {

    private ArrayList<String> productRevIds;

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("productRevIds", this.productRevIds);
        return hashMap;
    }
}
