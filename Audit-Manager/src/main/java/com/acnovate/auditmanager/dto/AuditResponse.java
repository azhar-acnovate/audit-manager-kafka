package com.acnovate.auditmanager.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class AuditResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> hashMap = new LinkedHashMap<>();
		return hashMap;
	}
}
