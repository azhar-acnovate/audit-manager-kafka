package AuditManager.kafka.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuditRequest implements Serializable {

    private String jobId;

    private ArrayList<String> productRevIds;

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("jobId", this.jobId);
        hashMap.put("productRevIds", this.productRevIds);
        return hashMap;
    }
}
