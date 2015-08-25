/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.requirement;

import com.mss.msp.util.ServiceLocatorException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public interface RequirementService {

    public int addRequirementDetails(RequirementAction requirementAction, int orgId);

    public RequirementVTO editrequirement(String requirementid) throws ServiceLocatorException;

    public int updateRequirement(HttpServletRequest httpServletRequest, int userid, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getRequirementDetails(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getReqDetailsBySearch(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getSkillDetaisls(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getPreferedSkillDetails(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getSearchRequirementsList(HttpServletRequest httpServletRequest, RequirementAction aThis) throws ServiceLocatorException;

    public String getRecruiterOverlay(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getSkillOverlay(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getPreSkillOverlay(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String storeProofData(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public int doReleaseRequirements(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public int doUpdateStatusReport(HttpServletRequest httpServletRequest, RequirementAction aThis) throws ServiceLocatorException;

    public String getConsultantSkillCSR(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public int getOrgIdCustomer(String requirementid) throws ServiceLocatorException;

    public String getRequirementDashBoardDetails(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getRequirementDashBoardDetailsOnOverlay(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getVendorRequirementDashBoardDetails(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getVendorRequirementsDashBoard(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public String getVendorDashBoardDetailsOnOverlay(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;

    public List getDefaultCustomerRequirementDashBoardDetails(RequirementAction requirementAction) throws ServiceLocatorException;

    public String getCustomerRequirementDashBoardDetails(HttpServletRequest httpServletRequest, RequirementAction requirementAction) throws ServiceLocatorException;
}
