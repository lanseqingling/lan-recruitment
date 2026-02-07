package com.lanrecruitment.service;

import com.lanrecruitment.dto.AuditDecisionDTO;
import java.util.List;
import com.lanrecruitment.vo.AdminHrAuditVO;
import com.lanrecruitment.vo.AdminJobAuditVO;

public interface AdminAuditService {
    List<AdminHrAuditVO> listPendingHr();

    void auditHr(AuditDecisionDTO dto);

    List<AdminJobAuditVO> listPendingJob();

    void auditJob(AuditDecisionDTO dto);
}
