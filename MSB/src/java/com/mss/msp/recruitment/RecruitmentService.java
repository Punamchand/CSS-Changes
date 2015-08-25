/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.recruitment;

import com.mss.msp.requirement.RequirementAction;
import com.mss.msp.util.ServiceLocatorException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author NagireddySeerapu
 */
public interface RecruitmentService {

    public List getMyDefaultConsListDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    public List getConsListDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    public String getRequirementDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    public List getAllRequirementList(HttpServletRequest httpServletRequest, RecruitmentAction aThis);

    public int doAddConsultantDetails(RecruitmentAction recruitmentAjaxHandlerAction, int OId) throws ServiceLocatorException;

    public ConsultantVTO getupdateConsultantDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    public ConsultantVTO doupdateConsultantDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction, int userSessionId, int orgid) throws ServiceLocatorException;

    public String getConsultantListDetails(HttpServletRequest httpServletRequest, RecruitmentAction vendorAjaxHandler) throws ServiceLocatorException;

    public String searchConsultantListDetails(HttpServletRequest httpServletRequest, RecruitmentAction vendorAjaxHandler) throws ServiceLocatorException;

    public int addConsultAttachmentDetails(RecruitmentAction recruitmentaction, HttpServletRequest httpServletRequest);

    public String getActivityDetails(HttpServletRequest httpServletRequest, int consultantId) throws ServiceLocatorException;

    public int doAddConsultantActivityDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    //public List getConsultantActivityDetails(RecruitmentAction recruitmentAction);
    public int doEditConsultantActivityDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction);

    public String getConsultantActivityDetailsbyActivityId(RecruitmentAction recruitmentAction);

    public List getLoginUserRequirementList(HttpServletRequest httpServletRequest, RecruitmentAction aThis);

    public List getCurrentStatus(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction);

    public List getConsultantStatus(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction);

    public int techReviewForward(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    public List getTechReviewDetails(HttpServletRequest httpServletRequest, RecruitmentAction aThis);

    public String getSearchTechReviewList(HttpServletRequest httpServletRequest, RecruitmentAction aThis) throws ServiceLocatorException;

    public String getConsultDetailsOnOverlay(HttpServletRequest httpServletRequest, RecruitmentAction aThis) throws ServiceLocatorException;

    public int saveTechReviewResults(HttpServletRequest httpServletRequest, RecruitmentAction aThis) throws ServiceLocatorException;

    public int doDeleteConsultantAttachment(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction) throws ServiceLocatorException;

    public int updateConsultAttachmentDetails(RecruitmentAction recruitmentaction, HttpServletRequest httpServletRequest);

    public List getTechReviewSearchDetails(HttpServletRequest httpServletRequest, RecruitmentAction recruitmentAction);
    
      public int userMigration(HttpServletRequest httpServletRequest,RecruitmentAction recruitmentAction) throws ServiceLocatorException;
}
