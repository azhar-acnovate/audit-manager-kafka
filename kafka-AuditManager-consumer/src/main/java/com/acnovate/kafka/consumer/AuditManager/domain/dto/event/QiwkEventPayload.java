package com.acnovate.kafka.consumer.AuditManager.domain.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QiwkEventPayload {

	private String ObjectName;
	private String mode;
	private HashMap<String,String> dataMap;


}
