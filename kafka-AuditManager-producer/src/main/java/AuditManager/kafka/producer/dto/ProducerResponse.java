package AuditManager.kafka.producer.dto;

import java.io.Serializable;

public class ProducerResponse implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2766166037994582959L;
	public String topic;
	public String offset;
	public String partition;
	public String error;
	
	
	
	public ProducerResponse() {
		super();
	}

	

	public ProducerResponse(String topic, String offset, String partition) {
		super();
		this.topic = topic;
		this.offset = offset;
		this.partition = partition;
	}



	public ProducerResponse(String topic, String offset, String partition, String error) {
		super();
		this.topic = topic;
		this.offset = offset;
		this.partition = partition;
		this.error = error;
	}
	
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getPartition() {
		return partition;
	}
	public void setPartition(String partition) {
		this.partition = partition;
	}
	
	
	

}
