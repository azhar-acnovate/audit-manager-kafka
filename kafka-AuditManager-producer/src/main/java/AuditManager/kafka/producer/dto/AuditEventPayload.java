package AuditManager.kafka.producer.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class AuditEventPayload {
	
	private String ObjectName;
	private String mode;
	private HashMap<String, String> dataMap;
	
	public String getObjectName() {
		return ObjectName;
	}
	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public HashMap<String, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(HashMap<String, String> dataMap) {
		this.dataMap = dataMap;
	}	
}