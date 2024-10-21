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
public class QiwkRequest implements Serializable {

    private String jobId;

    private String stepId;

    private String startDate;

    private String endDate;

    private String mode;

    private String objectName;

    private ArrayList<String> selectColumns;

    private List<HashMap<String, String>> whereColumns;

    private int fromIndex;

    private int toIndex;

    private ArrayList<String> productRevIds;

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("objectName", this.objectName);
        hashMap.put("jobId", this.jobId);
        hashMap.put("stepId", this.stepId);
        hashMap.put("startDate", this.startDate);
        hashMap.put("endDate", this.endDate);
        hashMap.put("fromIndex", Integer.valueOf(this.fromIndex));
        hashMap.put("toIndex", Integer.valueOf(this.toIndex));
        hashMap.put("mode", this.mode);
        hashMap.put("productRevIds", this.productRevIds);
        return hashMap;
    }
}
