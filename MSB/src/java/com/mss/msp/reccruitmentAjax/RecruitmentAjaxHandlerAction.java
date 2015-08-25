/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.reccruitmentAjax;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author NagireddySeerapu
 */
public class RecruitmentAjaxHandlerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    public RecruitmentAjaxHandlerAction() {
    }
    // variable for addConsultant created by Aklakh
    private HttpServletResponse httpServletResponse;
    private HttpServletRequest httpServletRequest;
    private String resultMessage;
    private int orgId;
    private String consultantId;
    private String resultType;
    private String cnslt_email;
    private String cnslt_fstname;
    private String cnslt_gender;
    private String cnslt_homePhone;
    private String cnslt_add_date;
    private String cnslt_lstname;
    private String cnslt_dob;
    private String cnslt_mobileNo;
    private String cnslt_available;
    private String cnslt_midname;
    private String cnslt_mStatus;
    private int cnslt_lcountry;
    private String addAddressFlag;
    private String addConsult_Address;
    private String addConsult_Address2;
    private String addConsult_City;
    private String addConsult_Country;
    private int addConsult_State;
    private String addConsult_Zip;
    private String addConsult_Phone;
    private String currentAddressFlag;
    private String addConsult_CAddress;
    private String addConsult_CAddress2;
    private String addConsult_CCity;
    private String addConsult_CCountry;
    private int addConsult_CState;
    private String addConsult_CZip;
    private String addConsult_CPhone;
    private int cnslt_industry;
    private String cnslt_salary;
    private int cnslt_wcountry;
    private int cnslt_organization;
    private String cnslt_experience;
    private int cnslt_preferredState;
    private String cnslt_jobTitle;
    private String cnslt_workPhone;
    private String cnslt_referredBy;
    private int cnslt_pcountry;
    private String cnslt_description;
    private String cnslt_comments;
    private Map cnslt_WCountry;
    private Map organization;
    private Map industry;
    private int consult_id;
    private int UserSessionId;
    private String consult_attachment_created_date;
    //for tech review search
    private String requirementId;
    //using consult_id;
    private String interviewDate;
    private String empIdTechReview;
    private int conTechReviewId;
    private String reviewType;
    private String forwardedToId;
    private int consultantCheck;
    private String resourceType;
    private String reqId;
    private String conEmail;
    private int sessionOrgId;

    public int getSessionOrgId() {
        return sessionOrgId;
    }

    public void setSessionOrgId(int sessionOrgId) {
        this.sessionOrgId = sessionOrgId;
    }

    public String getConEmail() {
        return conEmail;
    }

    public void setConEmail(String conEmail) {
        this.conEmail = conEmail;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getConsultanceExistance() {
        resultMessage = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            try {
                String resultNumber = "";
                setSessionOrgId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                String result = com.mss.msp.util.DataSourceDataProvider.getInstance().getEmiltExistOrNot(this.getResourceType(), this.getConEmail(), this.getSessionOrgId());

                String checkResult = "";


                if (result != null) {
                    String id[] = result.split("#");
                    resultNumber = 1 + "#" + id[1];
                    checkResult = com.mss.msp.util.DataSourceDataProvider.getInstance().getIsExistConsultantByReqId(this.getReqId(), id[0]);
                    int i = Integer.parseInt(checkResult);
                    if (i == 0) {
                        resultNumber = 2 + "#" + id[1];//file 
                    } else {
                        resultNumber = 3 + "#" + id[1];//already exist
                    }

                }

                //not valid email id
                System.out.println("in recruitment action" + resultNumber);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(resultNumber);
                resultMessage = null;
            } catch (Exception ex) {
                ex.printStackTrace();
                resultMessage = ERROR;
            }
        }
        return resultMessage;
    }

    /**
     * *****************************************************************************
     * Date : May 5 2015
     *
     * Author : Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * doEmailVerify() method can be used to verify the existing email
     *
     * *****************************************************************************
     */
    public String doEmailVerify() {
        System.out.println("-------In action class-------");
        resultMessage = LOGIN;
        int result;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID) != null) {
            int sessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
            try {
                result = DataSourceDataProvider.getInstance().checkConsultantLoginId(getConsultantId(), sessionId);
                //  System.out.println("result-------"+result);
                if (result == 0) {
                    httpServletResponse.setContentType("text");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.getWriter().write(SUCCESS);
                } else {
                    httpServletResponse.setContentType("text");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.getWriter().write(ERROR);
                }
                System.err.println("resultString---->" + result);

            } catch (Exception ex) {
                httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            }
        }
        return null;
    }

    /**
     * *****************************************************************************
     * Date : May 5 2015
     *
     * Author : Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * doAddnewConsultant() method can be used to add the new consultant on the
     * basic of organization id
     *
     * *****************************************************************************
     */
    public String doAddnewConsultant() {
        try {
            System.out.println(">>>>>>In Add consultant action<<<<<<<<<<");
            //  System.out.println("Org_id--------------->>>" + DataSourceDataProvider.getInstance().getOrgIdByEmailExt(getConsultantId()));
            System.out.println("After ORG");
            // if (DataSourceDataProvider.getInstance().getOrgIdByEmailExt(getConsultantId()) > 0) {
            String oId = httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString();
            int orgid = Integer.valueOf(oId);
            System.out.println("OID---" + orgid);
            setOrganization(DataSourceDataProvider.getInstance().getOrganizationByOrgId(orgid));
            setCnslt_WCountry(ServiceLocator.getLocationService().getCountriesNamesMap());        // getGeneralService().getCountriesNames());
            setIndustry(DataSourceDataProvider.getInstance().getIndustryName());
            int res = ServiceLocator.getRecruitmentAjaxHandlerService().getAddedConsultantDetails(this, orgid);
            //int searchDetails = ServiceLocator.getRecruitmentService().getAddedConsultantDetails(httpServletRequest, orgId, this);

            System.out.println("result-----------======>>>" + res);
            httpServletResponse.setContentType("text/html");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write(res);


            //   } else {

            // resultType = SUCCESS;
            // }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            // resultType = ERROR;
        }
        return null;
    }

    /**
     * Method : Fetches the attachment list.
     *
     */
    public String getAttachmentList() {
        // resultMessage = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            try {
                System.out.println("in attachment");
                String attachmentList = ServiceLocator.getRecruitmentAjaxHandlerService().getAttachmentDetails(httpServletRequest, this);
                int Response = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                //System.out.println("reporting person---->" + getReportingPerson());
                //System.out.println("leave list ------ " + leavesListDetails.size());
                // resultMessage = SUCCESS;

                System.out.println("-----after impl in action" + attachmentList);

                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(attachmentList);


            } catch (Exception ex) {
                //List errorMsgList = ExceptionToListUtility.errorMessagetUserLeavesServiceges(ex);
                System.out.println("I am in error" + ex.toString());
                httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());


                //resultMessage = ERROR;
            }
        }// Session validator if END
        return null;
    }

    /**
     * *****************************************************************************
     * Date : May 19 2015
     *
     * Author : Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * consultantLoginDetails() method is used to add the consultant login
     * details
     *
     * *****************************************************************************
     */
    public String consultantLoginDetails() {
        resultType = LOGIN;
        String reponseString = "";
        try {
            System.out.println(" Consultant Ajax Handler action -->" + getConsultantId());
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                UserSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                reponseString = ServiceLocator.getRecruitmentAjaxHandlerService().saveConsultantLoginDetails(getConsult_id(), UserSessionId);
                //System.out.println("===============>in titles" + repoString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(reponseString);

                //  resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }

    /**
     * *****************************************************************************
     * Date : May 19 2015
     *
     * Author : ramakrishna<lankireddy@miraclesoft.com>
     *
     * getConsultantTechReviews() method is used to add the consultant login
     * details
     *
     * *****************************************************************************
     */
    public String getConsultantTechReviews() {
        resultType = LOGIN;
        String reponseString = "";
        try {
            System.out.println(" Consultant Ajax Handler action -->" + getConsultantId());
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {

                reponseString = ServiceLocator.getRecruitmentAjaxHandlerService().getConsultantTechReviews(this);
                //System.out.println("===============>in titles" + repoString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(reponseString);

                //  resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }

    /**
     * *****************************************************************************
     * Date : May 19 2015
     *
     * Author : ramakrishna<lankireddy@miraclesoft.com>
     *
     * getConsultantTechReviews() method is used to add the consultant login
     * details
     *
     * *****************************************************************************
     */
    public String techReviewCommentsOverlay() {
        resultType = LOGIN;
        String reponseString = "";
        try {
            System.out.println(" Consultant Ajax Handler action -->" + getConsultantId());
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {

                reponseString = ServiceLocator.getRecruitmentAjaxHandlerService().techReviewCommentsOverlay(this);
                //System.out.println("===============>in titles" + repoString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(reponseString);

                //  resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }

    /**
     * *************************************
     *
     * @getSearchTechReviewList() method is used to get Requirement details of
     * account
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/18/2015
     *
     **************************************
     */
    public String getTechReviewResultOnOverlay() {
        resultType = LOGIN;
        String reponseString = "";
        try {
            System.out.println(" Consultant Ajax Handler action -->" + getConsultantId());
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {

                reponseString = ServiceLocator.getRecruitmentAjaxHandlerService().getTechReviewResultOnOverlay(this);
                //System.out.println("===============>in titles" + repoString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(reponseString);

                //  resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }

    public String checkConsultantExist() {
        resultType = LOGIN;
        String responseString = "";
        try {
            System.out.println("Ajax Handler action -->checkConsultantExist");
            System.out.println("orgid" + getConsultantCheck());
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                // userSessionId = Integer.parseInt(servletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                int userCount = ServiceLocator.getRecruitmentAjaxHandlerService().getConsultantCount(getConsultantCheck());
                if (userCount == 1) {
                    responseString = "Consultant is already Registered Please Check Email";
                }
                if (userCount == 0) {
                    responseString = "Do you want to send Login Credentials To Email?";
                }
                //System.out.println("===============>in titles" + repoString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(responseString);

                //  resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest) {

        this.httpServletRequest = httpServletRequest;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getCnslt_email() {
        return cnslt_email;
    }

    public void setCnslt_email(String cnslt_email) {
        this.cnslt_email = cnslt_email;
    }

    public String getCnslt_fstname() {
        return cnslt_fstname;
    }

    public void setCnslt_fstname(String cnslt_fstname) {
        this.cnslt_fstname = cnslt_fstname;
    }

    public String getCnslt_gender() {
        return cnslt_gender;
    }

    public void setCnslt_gender(String cnslt_gender) {
        this.cnslt_gender = cnslt_gender;
    }

    public String getCnslt_homePhone() {
        return cnslt_homePhone;
    }

    public void setCnslt_homePhone(String cnslt_homePhone) {
        this.cnslt_homePhone = cnslt_homePhone;
    }

    public String getCnslt_add_date() {
        return cnslt_add_date;
    }

    public void setCnslt_add_date(String cnslt_add_date) {
        this.cnslt_add_date = cnslt_add_date;
    }

    public String getCnslt_lstname() {
        return cnslt_lstname;
    }

    public void setCnslt_lstname(String cnslt_lstname) {
        this.cnslt_lstname = cnslt_lstname;
    }

    public String getCnslt_dob() {
        return cnslt_dob;
    }

    public void setCnslt_dob(String cnslt_dob) {
        this.cnslt_dob = cnslt_dob;
    }

    public String getCnslt_mobileNo() {
        return cnslt_mobileNo;
    }

    public void setCnslt_mobileNo(String cnslt_mobileNo) {
        this.cnslt_mobileNo = cnslt_mobileNo;
    }

    public String getCnslt_available() {
        return cnslt_available;
    }

    public void setCnslt_available(String cnslt_available) {
        this.cnslt_available = cnslt_available;
    }

    public String getCnslt_midname() {
        return cnslt_midname;
    }

    public void setCnslt_midname(String cnslt_midname) {
        this.cnslt_midname = cnslt_midname;
    }

    public String getCnslt_mStatus() {
        return cnslt_mStatus;
    }

    public void setCnslt_mStatus(String cnslt_mStatus) {
        this.cnslt_mStatus = cnslt_mStatus;
    }

    public int getCnslt_lcountry() {
        return cnslt_lcountry;
    }

    public void setCnslt_lcountry(int cnslt_lcountry) {
        this.cnslt_lcountry = cnslt_lcountry;
    }

    public String getAddAddressFlag() {
        return addAddressFlag;
    }

    public void setAddAddressFlag(String addAddressFlag) {
        this.addAddressFlag = addAddressFlag;
    }

    public String getAddConsult_Address() {
        return addConsult_Address;
    }

    public void setAddConsult_Address(String addConsult_Address) {
        this.addConsult_Address = addConsult_Address;
    }

    public String getAddConsult_Address2() {
        return addConsult_Address2;
    }

    public void setAddConsult_Address2(String addConsult_Address2) {
        this.addConsult_Address2 = addConsult_Address2;
    }

    public String getAddConsult_City() {
        return addConsult_City;
    }

    public void setAddConsult_City(String addConsult_City) {
        this.addConsult_City = addConsult_City;
    }

    public String getAddConsult_Country() {
        return addConsult_Country;
    }

    public void setAddConsult_Country(String addConsult_Country) {
        this.addConsult_Country = addConsult_Country;
    }

    public int getAddConsult_State() {
        return addConsult_State;
    }

    public void setAddConsult_State(int addConsult_State) {
        this.addConsult_State = addConsult_State;
    }

    public String getAddConsult_Zip() {
        return addConsult_Zip;
    }

    public void setAddConsult_Zip(String addConsult_Zip) {
        this.addConsult_Zip = addConsult_Zip;
    }

    public String getAddConsult_Phone() {
        return addConsult_Phone;
    }

    public void setAddConsult_Phone(String addConsult_Phone) {
        this.addConsult_Phone = addConsult_Phone;
    }

    public String getCurrentAddressFlag() {
        return currentAddressFlag;
    }

    public void setCurrentAddressFlag(String currentAddressFlag) {
        this.currentAddressFlag = currentAddressFlag;
    }

    public String getAddConsult_CAddress() {
        return addConsult_CAddress;
    }

    public void setAddConsult_CAddress(String addConsult_CAddress) {
        this.addConsult_CAddress = addConsult_CAddress;
    }

    public String getAddConsult_CAddress2() {
        return addConsult_CAddress2;
    }

    public void setAddConsult_CAddress2(String addConsult_CAddress2) {
        this.addConsult_CAddress2 = addConsult_CAddress2;
    }

    public String getAddConsult_CCity() {
        return addConsult_CCity;
    }

    public void setAddConsult_CCity(String addConsult_CCity) {
        this.addConsult_CCity = addConsult_CCity;
    }

    public String getAddConsult_CCountry() {
        return addConsult_CCountry;
    }

    public void setAddConsult_CCountry(String addConsult_CCountry) {
        this.addConsult_CCountry = addConsult_CCountry;
    }

    public int getAddConsult_CState() {
        return addConsult_CState;
    }

    public void setAddConsult_CState(int addConsult_CState) {
        this.addConsult_CState = addConsult_CState;
    }

    public String getAddConsult_CZip() {
        return addConsult_CZip;
    }

    public void setAddConsult_CZip(String addConsult_CZip) {
        this.addConsult_CZip = addConsult_CZip;
    }

    public String getAddConsult_CPhone() {
        return addConsult_CPhone;
    }

    public void setAddConsult_CPhone(String addConsult_CPhone) {
        this.addConsult_CPhone = addConsult_CPhone;
    }

    public int getCnslt_industry() {
        return cnslt_industry;
    }

    public void setCnslt_industry(int cnslt_industry) {
        this.cnslt_industry = cnslt_industry;
    }

    public String getCnslt_salary() {
        return cnslt_salary;
    }

    public void setCnslt_salary(String cnslt_salary) {
        this.cnslt_salary = cnslt_salary;
    }

    public int getCnslt_wcountry() {
        return cnslt_wcountry;
    }

    public void setCnslt_wcountry(int cnslt_wcountry) {
        this.cnslt_wcountry = cnslt_wcountry;
    }

    public int getCnslt_organization() {
        return cnslt_organization;
    }

    public void setCnslt_organization(int cnslt_organization) {
        this.cnslt_organization = cnslt_organization;
    }

    public String getCnslt_experience() {
        return cnslt_experience;
    }

    public void setCnslt_experience(String cnslt_experience) {
        this.cnslt_experience = cnslt_experience;
    }

    public int getCnslt_preferredState() {
        return cnslt_preferredState;
    }

    public void setCnslt_preferredState(int cnslt_preferredState) {
        this.cnslt_preferredState = cnslt_preferredState;
    }

    public String getCnslt_jobTitle() {
        return cnslt_jobTitle;
    }

    public void setCnslt_jobTitle(String cnslt_jobTitle) {
        this.cnslt_jobTitle = cnslt_jobTitle;
    }

    public String getCnslt_workPhone() {
        return cnslt_workPhone;
    }

    public void setCnslt_workPhone(String cnslt_workPhone) {
        this.cnslt_workPhone = cnslt_workPhone;
    }

    public String getCnslt_referredBy() {
        return cnslt_referredBy;
    }

    public void setCnslt_referredBy(String cnslt_referredBy) {
        this.cnslt_referredBy = cnslt_referredBy;
    }

    public int getCnslt_pcountry() {
        return cnslt_pcountry;
    }

    public void setCnslt_pcountry(int cnslt_pcountry) {
        this.cnslt_pcountry = cnslt_pcountry;
    }

    public String getCnslt_description() {
        return cnslt_description;
    }

    public void setCnslt_description(String cnslt_description) {
        this.cnslt_description = cnslt_description;
    }

    public String getCnslt_comments() {
        return cnslt_comments;
    }

    public void setCnslt_comments(String cnslt_comments) {
        this.cnslt_comments = cnslt_comments;
    }

    public Map getCnslt_WCountry() {
        return cnslt_WCountry;
    }

    public void setCnslt_WCountry(Map cnslt_WCountry) {
        this.cnslt_WCountry = cnslt_WCountry;
    }

    public Map getOrganization() {
        return organization;
    }

    public void setOrganization(Map organization) {
        this.organization = organization;
    }

    public Map getIndustry() {
        return industry;
    }

    public void setIndustry(Map industry) {
        this.industry = industry;
    }

    public int getConsult_id() {
        return consult_id;
    }

    public void setConsult_id(int consult_id) {
        this.consult_id = consult_id;
    }

    public int getUserSessionId() {
        return UserSessionId;
    }

    public void setUserSessionId(int UserSessionId) {
        this.UserSessionId = UserSessionId;
    }

    public String getConsult_attachment_created_date() {
        return consult_attachment_created_date;
    }

    public void setConsult_attachment_created_date(String consult_attachment_created_date) {
        this.consult_attachment_created_date = consult_attachment_created_date;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getEmpIdTechReview() {
        return empIdTechReview;
    }

    public void setEmpIdTechReview(String empIdTechReview) {
        this.empIdTechReview = empIdTechReview;
    }

    public int getConTechReviewId() {
        return conTechReviewId;
    }

    public void setConTechReviewId(int conTechReviewId) {
        this.conTechReviewId = conTechReviewId;
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public String getForwardedToId() {
        return forwardedToId;
    }

    public void setForwardedToId(String forwardedToId) {
        this.forwardedToId = forwardedToId;
    }

    public int getConsultantCheck() {
        return consultantCheck;
    }

    public void setConsultantCheck(int consultantCheck) {
        this.consultantCheck = consultantCheck;
    }
}
