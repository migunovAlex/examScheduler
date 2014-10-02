package com.examscheduler.controllers.tools;

import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.entity.Auditory;

public class AuditoryConverter {
	
	public Auditory convertFromDTO(AuditoryDTO auditoryDTO){
		Auditory auditory = new Auditory();
		auditory.setAudNumber(auditoryDTO.getAudNumber());
		auditory.setMaxPerson(auditoryDTO.getMaxPerson());
		return auditory;
	}
	
	public AuditoryDTO convertedFromPersistence(Auditory auditory){
		AuditoryDTO auditoryDTO = new AuditoryDTO();
		auditoryDTO.setId(auditory.getId());
		auditoryDTO.setAudNumber(auditory.getAudNumber());
		auditoryDTO.setMaxPerson(auditory.getMaxPerson());
		return auditoryDTO;
		
	}
}
