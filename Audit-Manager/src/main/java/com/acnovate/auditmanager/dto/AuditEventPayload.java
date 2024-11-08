package com.acnovate.auditmanager.dto;

import java.util.HashMap;

public class AuditEventPayload {
	
	private String ObjectName;
	private HashMap<String, String> dataMap;
	
	public String getObjectName() {
		return ObjectName;
	}
	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}

	public HashMap<String, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(HashMap<String, String> dataMap) {
		this.dataMap = dataMap;
	}	
}