package com.examscheduler.dto.summary;

import com.examscheduler.dto.AuditoryDTO;

public class AuditorySummary extends AbstractSummary{
	
	private AuditoryDTO auditoryDTO;
	
	public AuditorySummary(){
		super();
	}

	public AuditoryDTO getAuditoryDTO() {
		return auditoryDTO;
	}

	public void setAuditoryDTO(AuditoryDTO auditoryDTO) {
		this.auditoryDTO = auditoryDTO;
	}
}
