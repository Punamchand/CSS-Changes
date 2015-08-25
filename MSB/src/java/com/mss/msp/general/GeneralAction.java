/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.general;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author miracle
 */
public class GeneralAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private String resultType;
    private String emailId;
    private String password;
    private String sessionId;
    private String oldpwd;
    private String newpwd;
    private String cnfrmpwd;
    int id;
    //dash board
    private int userSessionId;
    private Map custerMap;
    private List csrDashBoardList;
    private int year;
    private Map vendorMap;
    private String typeOfUser;
    /**
     * The resultMessage is used for storing resultMessage.
     */
    private String resultMessage;
    /**
     * The httpServletRequest is used for storing httpServletRequest Object.
     */
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    public GeneralAction() {
    }
    private DataSourceDataProvider dataSourceDataProvider;

    public String dosetPassword() {
        int isUpdated = 0;
        //System.out.println("::::dosetPassword :::");
        try {
            String linkStatus = ServiceLocator.getGeneralService().forGotPwdLinkStatus(getEmailId(), getSessionId());
            //System.out.println("Forgotpasssword link status-->" + linkStatus);
            if (linkStatus.equalsIgnoreCase("Active")) {
                resultType = SUCCESS;
            } else {
                resultType = "LinkExperied";
            }

        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());


            resultType = ERROR;
        }


        return resultType;
    }

    public String doResetPassword() {
        int isUpdated = 0;
        int linkInactive = 0;
        //System.out.println("::::doResetPassword :::");
        try {

            // isUpdated=ServiceLocator.getGeneralService().doUpdateResetPassword(getPassword(),getEmailId());
            isUpdated = ServiceLocator.getGeneralService().doUpdateResetPassword(getPassword(), getEmailId());
            if (isUpdated > 0) {
                //System.out.println("updated successfully !!");
                linkInactive = ServiceLocator.getGeneralService().doPasswordLinkStatusUpdate(getEmailId());
                resultType = SUCCESS;
                resultMessage = "Password Updated successfully";
            } else {
                //System.out.println("error while update");
                resultType = ERROR;
            }
        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());

            //System.err.println("exception --->" + ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    //New Method for registration
    public String doAddRegistration() {
        int isUpdated = 0;
        int linkInactive = 0;
        //System.out.println("::::doAddRegistration :::");

        try {
            resultType = SUCCESS;

            /*isUpdated=ServiceLocator.getGeneralService().doUpdateResetPassword(getPassword(),getEmailId());
             if(isUpdated>0)
             {
             System.out.println("updated successfully !!");
             linkInactive=ServiceLocator.getGeneralService().doPasswordLinkStatusUpdate(getEmailId());
             resultType = SUCCESS;
             resultMessage="Password Updated successfully";
             }
             else
             {
             System.out.println("error while update");
             resultType = ERROR;   
             }*/
        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());

            // System.err.println("exception --->" + ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    //new methods for reset user password and reset my passwords
    public String resetUserPassword() {
        int isUpdated = 0;
        //   System.out.println("::::dosetPassword :::");
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setResultMessage(getResultMessage());
                resultType = SUCCESS;


            }
        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());


            resultType = ERROR;
        }


        return resultType;
    }

    public String changeMyPassword() {
        int isUpdated = 0;
        // System.out.println("::::dosetPassword :::");
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setResultMessage(getResultMessage());
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());


            resultType = ERROR;
        }


        return resultType;
    }

    public String doResetMyPassword() {
        int isUpdated = 0;
        int linkInactive = 0;
        // System.out.println("::::doResetPassword :::");
        try {
            //    System.out.println("::::doResetPassword :::Old Pasword-->"+getOldpwd());
            // System.out.println("::::doResetPassword :::newPassword--->"+getNewpwd());
            //    System.out.println("::::doResetPassword :::confirmPassword----->"+getCnfrmpwd());
            isUpdated = ServiceLocator.getGeneralService().doUpdateResetPassword(getNewpwd(), httpServletRequest.getSession(false).getAttribute(ApplicationConstants.LOGIN_ID).toString());
            if (isUpdated > 0) {
                setResultMessage("Password Updated Successfully");
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());

            //System.err.println("exception --->" + ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    public String doResetUserPassword() {
        int isUpdated = 0;
        int linkInactive = 0;
        //System.out.println("::::doResetUserPassword :::");
        try {
            //  System.out.println("::::doResetUserPassword :::Old Pasword-->"+getEmailId());
            // System.out.println("::::doResetUserPassword :::newPassword--->"+getNewpwd());
            //   System.out.println("::::doResetUserPassword :::confirmPassword----->"+getCnfrmpwd());
            isUpdated = ServiceLocator.getGeneralService().doUpdateResetPassword(getNewpwd(), getEmailId());
            if (isUpdated > 0) {
                setResultMessage("User Password Updated Successfully");
                resultType = SUCCESS;
            } else {
                setResultMessage("Please enter existing user id!");
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());

            // System.err.println("exception --->" + ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    public String dosetSuccessMessage() {

        try {
            //System.out.println(getResultMessage());
            setResultMessage(getResultMessage());
            resultType = SUCCESS;

        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    public String doseterrorMessage() {

        try {
            //System.out.println(getResultMessage());
            setResultMessage(getResultMessage());
            resultType = SUCCESS;

        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    public String regerrorDirect() {

        try {
            //System.out.println(getResultMessage());
            setResultMessage(getResultMessage());
            resultType = SUCCESS;

        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }


        return resultType;
    }

    public String getState() {

        try {
            System.out.println("I am   in States action");
            // Map countries =ServiceLocator.getLocationService().getCountriesNames();
            String states = ServiceLocator.getGeneralService().getStatesOfCountry(httpServletRequest, getId());
            //setStateMap(states);
            System.out.println("list of States----->" + states);
//            setResultMessage(getResultMessage());
//            httpServletRequest.setAttribute("stateList", states);
//            setResultMessage(getResultMessage());
            httpServletResponse.setContentType("text");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write(states);



            //System.out.println(httpServletRequest.getAttribute("statesList"));


        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            //resultType = ERROR;
        }
        return null;
    }

    /**
     * *************************************
     *
     * @getDefaultRequirementDashBoardDetails() update status in requirement
     * table
     *
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:06/03/2015
     *
     **************************************
     */
    public String dashBoardDetails() throws ServiceLocatorException {
        String resulttype = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
            setTypeOfUser(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString());
            setCusterMap(com.mss.msp.util.DataSourceDataProvider.getInstance().customerList(typeOfUser,getUserSessionId()));
            setVendorMap(com.mss.msp.util.DataSourceDataProvider.getInstance().getVendorList());
            csrDashBoardList = (ServiceLocator.getGeneralService().getDefaultRequirementDashBoardDetails(this));
            System.out.println(">>>>>>>>ACTION>>>>>>" + getCsrDashBoardList().toString());
            setYear(Calendar.getInstance().get(Calendar.YEAR));
            resulttype = SUCCESS;
        }
        return resulttype;
    }
    
   /**
     * *************************************
     *
     * @resetEmailVerify() to verify the email for
     * password reset
     *
     *
     * @Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * @Created Date:07/15/2015
     *
     **************************************
     */
    public String resetEmailVerify() throws ServiceLocatorException {
        String resulttype = LOGIN;
       int result=0;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
        
            System.out.println("emailid----->"+getEmailId());
             System.out.println("userId--->"+httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID));
            System.out.println("orgid---"+httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID));
            System.out.println("user session id---->"+httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID));
            try {
                result = DataSourceDataProvider.getInstance().checkResetEmailId(getEmailId(),Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
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

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     *
     * This method is used to set the Servlet Response
     *
     * @param httpServletResponse
     */
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String resetPwd() {

        //System.out.println("::::in resetPwd:::");
        // System.out.println("::::getpwd :::"+getPwd());


        return "success";
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOldpwd() {
        return oldpwd;
    }

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    public String getCnfrmpwd() {
        return cnfrmpwd;
    }

    public void setCnfrmpwd(String cnfrmpwd) {
        this.cnfrmpwd = cnfrmpwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(int userSessionId) {
        this.userSessionId = userSessionId;
    }

    public Map getCusterMap() {
        return custerMap;
    }

    public void setCusterMap(Map custerMap) {
        this.custerMap = custerMap;
    }

    public List getCsrDashBoardList() {
        return csrDashBoardList;
    }

    public void setCsrDashBoardList(List csrDashBoardList) {
        this.csrDashBoardList = csrDashBoardList;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map getVendorMap() {
        return vendorMap;
    }

    public void setVendorMap(Map vendorMap) {
        this.vendorMap = vendorMap;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
  
}
