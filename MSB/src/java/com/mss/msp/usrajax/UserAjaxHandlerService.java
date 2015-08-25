package com.mss.msp.usrajax;

import com.mss.msp.requirement.RequirementAction;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author miracle
 */
public interface UserAjaxHandlerService {

    /**
     * Creates a new instance of AjaxHandlerService
     */
    public String getStates(String country) throws ServiceLocatorException;

    public int addForGotPasswordDetails(String emailId, String urlLink, String key) throws ServiceLocatorException;

    public int doUserRegister(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    // public String getEmployeeDetails(String query) throws ServiceLocatorException;
    public String getEmployeeDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;
    /*methods for contact address updation by ramakrishna start*/

    public String getEmpAddressDetails(HttpServletRequest httpServletRequest, int empId) throws ServiceLocatorException;

    public int setEmpAddressDetails(HttpServletRequest httpServletRequest, int empId, String address, String address2, String city, String state, String zip, String country, String phone, String address_flag) throws ServiceLocatorException;

    public int setEmpCAddressDetails(HttpServletRequest httpServletRequest, int empId, String address, String address2, String city, String state, String zip, String country, String phone, String address_flag) throws ServiceLocatorException;
    /*methods for contact address updation by ramakrishna end*/

    /* -- Add by triveni for skill Details -- */
    public String getSkillDetails(HttpServletRequest httpServletRequest, int employeeId) throws ServiceLocatorException;

    public int setEditSkillDetails(HttpServletRequest httpServletRequest, int userSessionId, int skill_id, UserAjaxHandlerAction aThis) throws ServiceLocatorException;

    public String getEditOverlayDetails(HttpServletRequest httpServletRequest, int employeeId, int skill_id) throws ServiceLocatorException;

    public int getAddSkills(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction aThis) throws ServiceLocatorException;

    /* --end--*/
    /* -- Add by Aklakh for educationa Details -- */
    public String getQualificationDetails(HttpServletRequest httpServletRequest, int employeeId) throws ServiceLocatorException;
    //  public int getInsertedQualificationDetails(HttpServletRequest httpServletRequest, int userid, String qualification, Timestamp year_start, Timestamp year_end, Double percentage, String university,String institute, String specialization )throws ServiceLocatorException;

    public String getEditQualificationDetails(HttpServletRequest httpServletRequest, int userid, int usr_edu_id) throws ServiceLocatorException;
    //  public int getEduUpdateDetails( HttpServletRequest httpServletRequest,int usr_edu_id)throws ServiceLocatorException;
    /* --end--*/

    public int getEduUpdateDetails(HttpServletRequest httpServletRequest, int usr_edu_id, UserAjaxHandlerAction aThis) throws ServiceLocatorException;

    public int getInsertedQualificationDetails(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction aThis) throws ServiceLocatorException;
    /* ---START ReportInformation  action add by RK---   */

    public String getReportInfo(HttpServletRequest httpServletRequest, int empId) throws ServiceLocatorException;
    /* ---END ReportInformation  action add by RK---   */

    // new method for role submit
    public String roleSubmit(HttpServletRequest httpServletRequest, int roleId, int orgId) throws ServiceLocatorException;

    //added by praveen<pkatru@miraclesoft.com>
    public String getTitles(HttpServletRequest httpServletRequest, int dept_id) throws ServiceLocatorException;

    public void updateMiscellaneousInfo(HttpServletRequest httpServletRequest, UserAjaxHandlerAction aThis);

    public String getSecurityDetails(HttpServletRequest httpServletRequest, int empId) throws ServiceLocatorException;

    public String updateSecurityDetails(HttpServletRequest httpServletRequest, EmpConfidentialVTO empConfidentialVTO) throws ServiceLocatorException;
    /* ---Start, Add task types by Aklakh---  */

    public String getTypesOfTask(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;
    /* ---End, Add task types by Aklakh---  */

    public String getRelatedToNames(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction);
    //Added By Jagan

    public int getInsertedLeaveDetails(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getAttachment(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction);

    public int doDeactiveAttachment(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction);
    //added by manikanta

    public String getEmployeeNames(HttpServletRequest httpServletRequest, int dept_id) throws ServiceLocatorException;

    public String getStatesOfCountry(HttpServletRequest httpServletRequest, int countryId) throws ServiceLocatorException;

    public String getLeavesListDetails(HttpServletRequest httpServletRequest, UserAjaxHandlerAction aThis) throws ServiceLocatorException;

    public String getExternalEmployeeDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getExternalEmployee2Details(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getVendorNames(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getCsrNames(String csrName) throws ServiceLocatorException;

    public String csrTermination(int userId) throws ServiceLocatorException;

    public String changeCsrAccountStatus(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getCsrAccount(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getEmpCategories(UserAjaxHandlerAction userAjaxHandlerAction, int sessionOrgId) throws ServiceLocatorException;

    public String doUserGroupingMethod(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String empCategoryTermination(UserAjaxHandlerAction userAjaxHandlerAction, int sessionOrgId) throws ServiceLocatorException;

    public String getHomeRedirectSearchDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getAccountNames(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String storeAddOrEditHomeRedirectDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getHomeRedirectCommentsDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public String getCategoryList(UserAjaxHandlerAction aThis) throws ServiceLocatorException;

    public String getEmpCategoryNames(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException;

    public boolean isUserGroupExist(int userId) throws ServiceLocatorException;
}
