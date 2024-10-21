package AuditManager.kafka.producer.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class QiwkCsrfTokenResponse {

    private ArrayList<HashMap<String, Object>> items;

    public String getToken() {
        HashMap<String, Object> itemsMap = items.get(0);
        if (itemsMap == null) {
            return null;
        }
        Object obj = itemsMap.get("attributes");
        Map tokenMap = (Map) obj;
        return (String) tokenMap.get("nonce");
    }
}
