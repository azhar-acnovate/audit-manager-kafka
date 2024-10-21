package AuditManager.kafka.producer.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
@Component
public class QiwkResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String jobId;

//	private String stepId;
//
//	private String startDate;
//
//	private String endDate;
//
//	private String mode;
//
//	private String objectName;
//
//	private ArrayList<String> selectColumns;
//
//	private List<HashMap<String, String>> whereColumns;
//
//	private int fromIndex;
//
//	private int toIndex;
//
//	private List<HashMap<String, String>> objectResponseList;
//
//	private int totalResultsCount;
//
//	private int filteredResultsCount;
//
//	private String responseCode;
//
//	private String responseMessage;

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

//	public String getStepId() {
//		return this.stepId;
//	}
//
//	public void setStepId(String stepId) {
//		this.stepId = stepId;
//	}
//
//	public String getStartDate() {
//		return this.startDate;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//
//	public String getEndDate() {
//		return this.endDate;
//	}
//
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//
//	public String getMode() {
//		return this.mode;
//	}
//
//	public void setMode(String mode) {
//		this.mode = mode;
//	}
//
//	public String getObjectName() {
//		return this.objectName;
//	}
//
//	public void setObjectName(String objectName) {
//		this.objectName = objectName;
//	}
//
//	public ArrayList<String> getSelectColumns() {
//		return this.selectColumns;
//	}
//
//	public void setSelectColumns(ArrayList<String> selectColumns) {
//		this.selectColumns = selectColumns;
//	}
//
//	public List<HashMap<String, String>> getWhereColumns() {
//		return this.whereColumns;
//	}
//
//	public void setWhereColumns(List<HashMap<String, String>> whereColumns) {
//		this.whereColumns = whereColumns;
//	}
//
//	public int getFromIndex() {
//		return this.fromIndex;
//	}
//
//	public void setFromIndex(int fromIndex) {
//		this.fromIndex = fromIndex;
//	}
//
//	public int getToIndex() {
//		return this.toIndex;
//	}
//
//	public void setToIndex(int toIndex) {
//		this.toIndex = toIndex;
//	}
//
//	public List<HashMap<String, String>> getObjectResponseList() {
//		return this.objectResponseList;
//	}
//
//	public void setObjectResponseList(List<HashMap<String, String>> objectResponseList) {
//		this.objectResponseList = objectResponseList;
//	}
//
//	public int getTotalResultsCount() {
//		return this.totalResultsCount;
//	}
//
//	public void setTotalResultsCount(int totalResultsCount) {
//		this.totalResultsCount = totalResultsCount;
//	}
//
//	public int getFilteredResultsCount() {
//		return this.filteredResultsCount;
//	}
//
//	public void setFilteredResultsCount(int filteredResultsCount) {
//		this.filteredResultsCount = filteredResultsCount;
//	}
//
//	public String getResponseCode() {
//		return this.responseCode;
//	}
//
//	public void setResponseCode(String responseCode) {
//		this.responseCode = responseCode;
//	}
//
//	public String getResponseMessage() {
//		return this.responseMessage;
//	}
//
//	public void setResponseMessage(String responseMessage) {
//		this.responseMessage = responseMessage;
//	}
//
//	public QiwkResponse() {
//	}
//
//	public QiwkResponse(String responseCode, String responseMessage) {
//		this.responseCode = responseCode;
//		this.responseMessage = responseMessage;
//	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> hashMap = new LinkedHashMap<>();
//		hashMap.put("objectName", this.objectName);
		hashMap.put("jobId", this.jobId);
//		hashMap.put("stepId", this.stepId);
//		hashMap.put("startDate", this.startDate);
//		hashMap.put("endDate", this.endDate);
//		hashMap.put("fromIndex", Integer.valueOf(this.fromIndex));
//		hashMap.put("toIndex", Integer.valueOf(this.toIndex));
//		hashMap.put("mode", this.mode);
//		hashMap.put("totalResultsCount", Integer.valueOf(this.totalResultsCount));
//		hashMap.put("filteredResultsCount", Integer.valueOf(this.filteredResultsCount));
//		hashMap.put("responseCode", this.responseCode);
//		hashMap.put("responseMessage", this.responseMessage);
		return hashMap;
	}
}
