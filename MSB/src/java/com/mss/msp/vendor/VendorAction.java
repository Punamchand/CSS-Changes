/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.vendor;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author praveen kumar<pkatru@miraclesoft.com>
 * @author rama krishna<lankireddy@miraclesoft.com>
 */
public class VendorAction extends ActionSupport implements ServletRequestAware, ParameterAware {

    private String resultType;

    public VendorAction() {
    }
    /**
     * The httpServletRequest is used for storing httpServletRequest Object.
     */
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    /**
     * The resultMessage is used for storing resultMessage.
     */
    private String resultMessage;
    private List vendorListVto;
    private int teamMemberId;
    private String vendorName;
    private String vendorCity;
    private String vendorState;
    private String vendorUrl;
    private String Status;
    private String vendorPhone;
    private int teamMember_id;
    private int org_id;
    private Map countryNames;
    private int countryId;
    /**
     * The resultMessage is used for storing resultMessage.
     */
    // variables for vendorDetails.jsp to show values
    private Map vendorTypeList;
    private Map countryList;
    private Map stateList;
    private int vendorType;
    private int vendorcountry;
    private VendorListVTO vendorListVTO;
    private Map industryList;
    private DataSourceDataProvider dataSourceDataProvider;
    private int vendorId;
    //for assigned team//
    private Map salesList;
    private Map vendorSalesList;
    private Map parameters;
    private List vendorSalesTeam;
    private List salesTeam;
    private int userSessionId;
    private String vendorFlag;
    private Map teamMembersList;
    private String teamMembers;
    private int sessionOrg_id;
    private Map allVendorTeam;
    private int primaryAccount;
    //for Redirection
    private String venFlag;
    public int year;
    List<VendorDashboardList> vendorDashboardList = new ArrayList<VendorDashboardList>();
    //for assigned team//

    /**
     * *****************************************************************************
     * Date : May 5, 2015, 11:23 PM IST Author:praveen
     * kumar<pkatru@miraclesoft.com>
     *
     * ForUse : getting vendor details in grid
     * *****************************************************************************
     */
    public String getVendorDetails() {
        resultMessage = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            try {
                setSessionOrg_id(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                this.setTeamMemberId(getUserSessionId());
                setTeamMembersList(dataSourceDataProvider.getInstance().getMyTeamMembers(getUserSessionId()));
                vendorListVto = ServiceLocator.getVendorService().getVendorDetails(httpServletRequest, this);
                countryNames = com.mss.msp.util.DataSourceDataProvider.getInstance().getCountryNames();
                resultMessage = SUCCESS;
            } catch (Exception ex) {
                resultMessage = ERROR;
            }
        }// Session validator if END
        return resultMessage;
    }

    /**
     * *****************************************************************************
     * Date : May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting vendor details
     * *****************************************************************************
     */
    public String showVendorDetails() {
        resultMessage = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            try {
                if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$ ENTERED INTO ACTION $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    setAllVendorTeam(dataSourceDataProvider.getInstance().getAllVendorTeam());
                    System.out.println("get all Vendors" + getAllVendorTeam());
                    setPrimaryAccount(dataSourceDataProvider.getInstance().getPrimaryAccount(getVendorId()));
                    setVendorTypeList(dataSourceDataProvider.getInstance().getVendorType());
                    setCountryList(ServiceLocator.getLocationService().getCountriesNamesMap());
                    setIndustryList(dataSourceDataProvider.getInstance().getIndystryTypes());
                    setSalesList(dataSourceDataProvider.getInstance().getSalesTeam(getVendorId()));
                    setVendorSalesList(dataSourceDataProvider.getInstance().getVendorSalesTeam(getVendorId()));
                    vendorListVTO = (ServiceLocator.getVendorService().getVendorDetailsById(this, httpServletRequest));
                    setVendorcountry(vendorListVTO.getVendorCountry());
                    setStateList(ServiceLocator.getLocationService().getStatesMapOfCountry(httpServletRequest, getVendorcountry()));
                    System.out.println("AFTER COMPLETING IMPL RETURNED TO ACTION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|||||||");
                    resultMessage = SUCCESS;
                }
                resultMessage = SUCCESS;
            } catch (Exception ex) {
                //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

                httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());


                resultMessage = ERROR;
            }
        }// Session validator if END
        return resultMessage;
    }

    /**
     * *****************************************************************************
     * Date : May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : updating vendor sales team details
     * *****************************************************************************
     */
    public String vendorSalesUpdate() throws Exception {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("======================Entered in the Action==========================");
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setVendorTypeList(dataSourceDataProvider.getInstance().getVendorType());
                setCountryList(ServiceLocator.getLocationService().getCountriesNamesMap());
                setIndustryList(dataSourceDataProvider.getInstance().getIndystryTypes());
                //setVendorcountry(vendorListVTO.getVendorCountry());
                //setStateList(ServiceLocator.getGeneralService().getStatesMapOfCountry(httpServletRequest, getVendorcountry()));
                System.out.println("List>>>>>>>" + getVendorSalesTeam().toString());
                String[] rightParams = (String[]) parameters.get("vendorSalesTeam");
                System.out.println(">>>>>>>>>>>>>>>>>Length>>>>>>" + rightParams.length + " >>>>>>>vendorId>>" + getVendorId());
                int updateResult = ServiceLocator.getVendorService().updateVendorSalesTeam(this, rightParams, getPrimaryAccount());

                if (updateResult > 0) {
                    addActionMessage("team has been successfully updated!");
                } else {
                    addActionMessage("No Records Updated!");
                }
                httpServletRequest.setAttribute(ApplicationConstants.RESULT_MSG, resultMessage);
                resultType = SUCCESS;

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            resultType = ERROR;
        }

        return resultType;
    }
    
     /**
     * ****************************************************************************
     * Date : June 02 2015
     *
     * Author : manikanta eeralla<meeralla@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String showVendorDashboard() {
        resultMessage = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            try {
                int year;

                year = Calendar.getInstance().get(Calendar.YEAR);
                setYear(year);
                //setSessionOrg_id(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                //setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                // this.setTeamMemberId(getUserSessionId());
                // setTeamMembersList(dataSourceDataProvider.getInstance().getMyTeamMembers(getUserSessionId()));
                // vendorListVto = ServiceLocator.getVendorService().getVendorDetails(httpServletRequest, this);
                // countryNames = com.mss.msp.util.DataSourceDataProvider.getInstance().getCountryNames();
                int orgId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString());
                vendorDashboardList = ServiceLocator.getVendorService().showVendorDashboard(orgId);
                resultMessage = SUCCESS;
            } catch (Exception ex) {
                resultMessage = ERROR;
            }
        }// Session validator if END
        return resultMessage;
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

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public List getVendorListVto() {
        return vendorListVto;
    }

    public void setVendorListVto(List vendorListVto) {
        this.vendorListVto = vendorListVto;
    }

    public int getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(int teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public Map getCountryNames() {
        return countryNames;
    }

    public void setCountryNames(Map countryNames) {
        this.countryNames = countryNames;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCity() {
        return vendorCity;
    }

    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }

    public String getVendorState() {
        return vendorState;
    }

    public void setVendorState(String vendorState) {
        this.vendorState = vendorState;
    }

    public String getVendorUrl() {
        return vendorUrl;
    }

    public void setVendorUrl(String vendorUrl) {
        this.vendorUrl = vendorUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public int getTeamMember_id() {
        return teamMember_id;
    }

    public void setTeamMember_id(int teamMember_id) {
        this.teamMember_id = teamMember_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public Map getVendorTypeList() {
        return vendorTypeList;
    }

    public void setVendorTypeList(Map vendorTypeList) {
        this.vendorTypeList = vendorTypeList;
    }

    public Map getCountryList() {
        return countryList;
    }

    public void setCountryList(Map countryList) {
        this.countryList = countryList;
    }

    public Map getStateList() {
        return stateList;
    }

    public void setStateList(Map stateList) {
        this.stateList = stateList;
    }

    public int getVendorType() {
        return vendorType;
    }

    public void setVendorType(int vendorType) {
        this.vendorType = vendorType;
    }

    public int getVendorcountry() {
        return vendorcountry;
    }

    public void setVendorcountry(int vendorcountry) {
        this.vendorcountry = vendorcountry;
    }

    public VendorListVTO getVendorListVTO() {
        return vendorListVTO;
    }

    public void setVendorListVTO(VendorListVTO vendorListVTO) {
        this.vendorListVTO = vendorListVTO;
    }

    public Map getIndustryList() {
        return industryList;
    }

    public void setIndustryList(Map industryList) {
        this.industryList = industryList;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public Map getSalesList() {
        return salesList;
    }

    public void setSalesList(Map salesList) {
        this.salesList = salesList;
    }

    public Map getVendorSalesList() {
        return vendorSalesList;
    }

    public void setVendorSalesList(Map vendorSalesList) {
        this.vendorSalesList = vendorSalesList;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public List getVendorSalesTeam() {
        return vendorSalesTeam;
    }

    public void setVendorSalesTeam(List vendorSalesTeam) {
        this.vendorSalesTeam = vendorSalesTeam;
    }

    public List getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(List salesTeam) {
        this.salesTeam = salesTeam;
    }

    public int getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(int userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getVendorFlag() {
        return vendorFlag;
    }

    public void setVendorFlag(String vendorFlag) {
        this.vendorFlag = vendorFlag;
    }

    public Map getTeamMembersList() {
        return teamMembersList;
    }

    public void setTeamMembersList(Map teamMembersList) {
        this.teamMembersList = teamMembersList;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public int getSessionOrg_id() {
        return sessionOrg_id;
    }

    public void setSessionOrg_id(int sessionOrg_id) {
        this.sessionOrg_id = sessionOrg_id;
    }

    public Map getAllVendorTeam() {
        return allVendorTeam;
    }

    public void setAllVendorTeam(Map allVendorTeam) {
        this.allVendorTeam = allVendorTeam;
    }

    public int getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(int primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getVenFlag() {
        return venFlag;
    }

    public void setVenFlag(String venFlag) {
        this.venFlag = venFlag;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<VendorDashboardList> getVendorDashboardList() {
        return vendorDashboardList;
    }

    public void setVendorDashboardList(List<VendorDashboardList> vendorDashboardList) {
        this.vendorDashboardList = vendorDashboardList;
    }
    
}
