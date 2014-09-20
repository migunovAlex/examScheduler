package com.examscheduler.dto.summary;

import java.util.List;

import com.examscheduler.dto.AuditoryDTO;

public class AuditoryListSummary extends AbstractSummary{

	private List<AuditoryDTO> auditoryList;
	
	public AuditoryListSummary(){
		super();
	}

	public List<AuditoryDTO> getAuditoryList() {
		return auditoryList;
	}

	public void setAuditoryList(List<AuditoryDTO> auditoryList) {
		this.auditoryList = auditoryList;
	}
	
	
}
