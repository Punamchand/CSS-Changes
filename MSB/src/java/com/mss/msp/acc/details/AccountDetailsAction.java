/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.details;

import com.mss.msp.acc.Account;
import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.HibernateServiceLocator;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.mss.msp.util.DataSourceDataProvider;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ParameterAware;

/**
 *
 * @author Greg
 */
public class AccountDetailsAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ParameterAware {

    private AccountDetails accountDetails;
    private Map<Integer,String> states;
    private Map<Integer,String> countries;
    private Map<Integer,String> industries;
    private Map<Integer,String> accTypes;
    private Map<Integer,String> vendTypes;
    private String accountSearchID;
    private String resultType;
    private String resultMessage;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private DataSourceDataProvider dataSourceDataProvider;
    private Map accTeamList;
    private Map availAccTeamList;
    private int userSessionId;
    private Map parameters;
    //added by manikanta
    private int contactId;
    private AccountContactVTO accountContactVTO;
    private String ContactFname;
    private String ContactLname;
    private String ContactMname;
    private String phone;
    private String conCZip;
    private String Officephone;
    private String OfficeAddress;
    private String status;
    private int AccountSearchOrgId;
    private String ContactEmail;
    private int id;
    private String conAddress;
    private String conAddress2;
    private String conCity;
    private String conZip;
    private int conCountry;
    private int conCCountry;
    private int conState;
    private String conPhone;
    private String conCAddress;
    private String conCAddress2;
    private String conCCity;
    private String conCPhone;
    private int conCState;
//    private File picture;
//    private String pictureFileName;
//    private String pictureFileContentType;
    // private String message = "";
    //private String filePath;
    private String addAddressFlag;
    private String currentAddressFlag;
//    private File taskAttachment;
//    private String taskAttachmentContentType;
//    private String taskAttachmentFileName;
//    private String filePath;
    private boolean checkAddress;
    private Map countryNames;
    private Map state;
    private String resultFlag;
    private Map allAccTeam;
    private int primaryAccount;
    private String accFlag;
    private String account_name;
    private int roleId;
    private Map departments;
    private int department;
    private int titles;
    private boolean isManager;
    private boolean isTeamLead;
    private int addManager;
    private int addTeamLead;
    private Map vendorTierMap;
    private Map addVendorTierMap;
    private String moblieNumber;
    private String ContactEmail2;
    private String homePhone;
     // Add by Aklakh
    private String userType;
    private String workLocation;
    private int accountType;
    private String contactDesignation;
    private Map designations;
    private String gender;
    

    public AccountDetailsAction() {
    }

    public String view() {
        System.out.println("===== ACCOUNT VIEW =====");
        try {
            String id = (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString());
            if (accountSearchID != null) {
                id = accountSearchID;
            } else {
                this.accountSearchID = id;
            }
            if (com.mss.msp.util.DataSourceDataProvider.getInstance().isVendor(Integer.parseInt(getAccountSearchID()))) {
                setUserType("vendor");
            System.out.println("Yes , I m  a vendor");
            }else{
                setUserType("other");
                System.out.println("NO , I m not a vendor");
            }
            if (id != null) {
                System.out.println("FETCHING INFO for FROM NEW METHOD" + id);
                
                setPrimaryAccount(dataSourceDataProvider.getInstance().getPrimaryAccount(Integer.parseInt(getAccountSearchID())));
                //setDepartments(dataSourceDataProvider.getInstance().getDepartmentNames(Integer.parseInt(getAccountSearchID())));
                //setDesignations(dataSourceDataProvider.getInstance().getDesignation());
                setVendorTierMap(dataSourceDataProvider.getInstance().getVendorTierTypes());
                setAddVendorTierMap(dataSourceDataProvider.getInstance().getAddVendorTierTypes(id));
                setAllAccTeam(dataSourceDataProvider.getInstance().getAllAccTeam(Integer.parseInt(getAccountSearchID())));
                setAccTeamList(dataSourceDataProvider.getInstance().getAccTeam(Integer.parseInt(getAccountSearchID())));
                setAvailAccTeamList(dataSourceDataProvider.getInstance().getAccSalesTeam(Integer.parseInt(getAccountSearchID())));
               
                accountDetails = ServiceLocator.getAccountDetailsService().viewAccountDetails(Integer.parseInt(id));
                //accountDetails.setLkState(ServiceLocator.getLookUpHandlerService().getName("lk_states", "name", Integer.parseInt(accountDetails.getState())));
                //accountDetails.setLkCountry(ServiceLocator.getLookUpHandlerService().getName("lk_country", "country", Integer.parseInt(accountDetails.getCountry())));
                if (accountDetails.getIndustry() != null && !accountDetails.getIndustry().equals("")) {
                    accountDetails.setLkIndustry(ServiceLocator.getLookUpHandlerService().getName("lk_acc_industry_type", "acc_industry_name", Integer.parseInt(accountDetails.getIndustry())));
                }
//                if (accountDetails.getVendor() != null && !accountDetails.getVendor().equals("")) {
//                    accountDetails.setLkVendor(ServiceLocator.getLookUpHandlerService().getName("lk_vendor_type", "vendor_type", accountDetails.getVendor()));
//                }
                if(accountDetails.getCountry()!=null && !accountDetails.getCountry().equals("")) { 
                accountDetails.setStockSymbol(ServiceLocator.getLocationService().lookupCountryCurrency(Integer.parseInt(accountDetails.getCountry())));
                states = ServiceLocator.getLocationService().getStatesMapOfCountry(null,Integer.parseInt(accountDetails.getCountry()));
                }else{
                 accountDetails.setStockSymbol("");
                 states=ServiceLocator.getLocationService().getStatesMapOfCountry(null,1);
                }
                accountDetails.setLkAccountType(ServiceLocator.getLookUpHandlerService().getName("lk_acc_type", "acc_type_name", accountDetails.getAccountType()));
                System.out.println("==========LOADED==========" + accountDetails);
                //LOAD VALUES FOR ACCOUNTDETAILS DROPDOWN SELECTS
                countries = ServiceLocator.getLocationService().getCountryNames();
                
                
                System.err.println("states-->"+states);
                industries = ServiceLocator.getLookUpHandlerService().getIndustryTypesMap();
                accTypes = ServiceLocator.getLookUpHandlerService().getAccountTypesMap();
               // vendTypes = ServiceLocator.getLookUpHandlerService().getVendorTypesMap();
                
                //OTHER STUFF LOAD
                setCountryNames(dataSourceDataProvider.getInstance().getCountryNames());
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setRoleId(dataSourceDataProvider.getInstance().getAdmin(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString())));
                resultMessage = "";
                resultType = SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem viewing account : ==> (" + e.getMessage() + ");");
            resultMessage = "Problem viewing account : ==> (" + e.getMessage() + ");";
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     * *****************************************************************************
     * Date : May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : updating vendor sales team details
     * *****************************************************************************
     */
    public String updateAccTeam() throws Exception {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("======================Entered in the Action==========================");
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                System.out.println(">>>>>>>UserId:::>" + getUserSessionId() + "   " + getAccountSearchID());
//                System.out.println("List>>>>>>>" + getAvailAccTeamList().toString());
                String[] rightParams = (String[]) parameters.get("accSalesTeam");
                System.out.println(">>>>>>>>>>>>>>>>>Length>>>>>>" + rightParams.length + " >>>>>>>accountId>>" + getAccountSearchID());
                int updateResult = ServiceLocator.getAccountService().updateAccSalesTeam(this, rightParams, getPrimaryAccount());

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

    public String getAccountContactDetailsForEdit() {

        resultMessage = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            try {
                setAccountContactVTO(ServiceLocator.getAccountDetailsService().editAccountContacts(getContactId()));
                setCountryNames(dataSourceDataProvider.getInstance().getCountryNames());
                //setDepartments(dataSourceDataProvider.getInstance().getDepartmentNames(Integer.parseInt(getAccountSearchID())));
                setAccount_name(DataSourceDataProvider.getInstance().getAccountNameById(Integer.parseInt(getAccountSearchID())));
                if ("update".equals(getResultFlag())) {
                    addActionMessage("Account Contact Details Updated Successfully");
                }
                //setState(DataSourceDataProvider.getInstance().getAllStates());
                resultMessage = SUCCESS;


            } catch (Exception ex) {
                httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
                resultMessage = ERROR;
            }

        }

        return resultMessage;
    }

    public String updateAccountContactDetails() {
        HttpSession session = getHttpServletRequest().getSession(true);

        if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
            if (isCheckAddress()) {
                addAddressFlag = "PC";
                System.out.println("checkAddress" + addAddressFlag);
            } else {
                addAddressFlag = "P";
                System.out.println("checkAddress" + addAddressFlag);

            }
            if (isIsManager()) {
                addManager = 1;
                System.out.println("Is Manager" + addManager);
            } else {
                addManager = 0;
                System.out.println("Is Manager" + addManager);
            }
            if (isIsTeamLead()) {
                addTeamLead = 1;
                System.out.println("Is TeamLead" + addTeamLead);
            } else {
                addTeamLead = 0;
                System.out.println("Is TeamLead" + addTeamLead);
            }
            System.out.println("checkAddress" + isCheckAddress());
            try {
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                String resultString = ServiceLocator.getAccountDetailsService().updateAccountContactDetails(this);
                System.out.println("result " + resultString);
                if (resultString.equalsIgnoreCase("Updated")) {
                    dataSourceDataProvider.getInstance().updateAccountLastAccessedBy(Integer.parseInt(getAccountSearchID()), getUserSessionId(), "ContactUpdated");
                }
                if (getUserSessionId() == getContactId()) {
                    session.setAttribute(ApplicationConstants.FIRST_NAME, getContactFname());
                    session.setAttribute(ApplicationConstants.Last_NAME, getContactLname());
                }
                resultMessage = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                resultMessage = ERROR;
            }
            System.out.println("checkAddress--" + isCheckAddress());
        }
        return resultMessage;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public Map<Integer,String> getStates() {
        return states;
    }

    public void setStates(Map<Integer,String> states) {
        this.states = states;
    }

    public Map<Integer,String> getCountries() {
        return countries;
    }

    public void setCountries(Map<Integer,String> countries) {
        this.countries = countries;
    }

    public Map<Integer,String> getIndustries() {
        return industries;
    }

    public void setIndustries(Map<Integer,String> industries) {
        this.industries = industries;
    }

    public Map<Integer,String> getAccTypes() {
        return accTypes;
    }

    public void setAccTypes(Map<Integer,String> accTypes) {
        this.accTypes = accTypes;
    }

    public String getAccountSearchID() {
        return accountSearchID;
    }

    public void setAccountSearchID(String accountSearchID) {
        this.accountSearchID = accountSearchID;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public Map<Integer,String> getVendTypes() {
        return vendTypes;
    }

    public void setVendTypes(Map<Integer,String> vendTypes) {
        this.vendTypes = vendTypes;
    }

    public Map getAccTeamList() {
        return accTeamList;
    }

    public void setAccTeamList(Map accTeamList) {
        this.accTeamList = accTeamList;
    }

    public Map getAvailAccTeamList() {
        return availAccTeamList;
    }

    public void setAvailAccTeamList(Map availAccTeamList) {
        this.availAccTeamList = availAccTeamList;
    }

    public int getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(int userSessionId) {
        this.userSessionId = userSessionId;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public String getContactFname() {
        return ContactFname;
    }

    public void setContactFname(String ContactFname) {
        this.ContactFname = ContactFname;
    }

    public String getContactLname() {
        return ContactLname;
    }

    public void setContactLname(String ContactLname) {
        this.ContactLname = ContactLname;
    }

    public String getContactMname() {
        return ContactMname;
    }

    public void setContactMname(String ContactMname) {
        this.ContactMname = ContactMname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConCZip() {
        return conCZip;
    }

    public void setConCZip(String conCZip) {
        this.conCZip = conCZip;
    }

    public String getOfficephone() {
        return Officephone;
    }

    public void setOfficephone(String Officephone) {
        this.Officephone = Officephone;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }

    public void setOfficeAddress(String OfficeAddress) {
        this.OfficeAddress = OfficeAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAccountSearchOrgId() {
        return AccountSearchOrgId;
    }

    public void setAccountSearchOrgId(int AccountSearchOrgId) {
        this.AccountSearchOrgId = AccountSearchOrgId;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String ContactEmail) {
        this.ContactEmail = ContactEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConAddress() {
        return conAddress;
    }

    public void setConAddress(String conAddress) {
        this.conAddress = conAddress;
    }

    public String getConAddress2() {
        return conAddress2;
    }

    public void setConAddress2(String conAddress2) {
        this.conAddress2 = conAddress2;
    }

    public String getConCity() {
        return conCity;
    }

    public void setConCity(String conCity) {
        this.conCity = conCity;
    }

    public String getConZip() {
        return conZip;
    }

    public void setConZip(String conZip) {
        this.conZip = conZip;
    }

    public int getConCountry() {
        return conCountry;
    }

    public void setConCountry(int conCountry) {
        this.conCountry = conCountry;
    }

    public int getConCCountry() {
        return conCCountry;
    }

    public void setConCCountry(int conCCountry) {
        this.conCCountry = conCCountry;
    }

    public int getConState() {
        return conState;
    }

    public void setConState(int conState) {
        this.conState = conState;
    }

    public String getConPhone() {
        return conPhone;
    }

    public void setConPhone(String conPhone) {
        this.conPhone = conPhone;
    }

    public String getConCAddress() {
        return conCAddress;
    }

    public void setConCAddress(String conCAddress) {
        this.conCAddress = conCAddress;
    }

    public String getConCAddress2() {
        return conCAddress2;
    }

    public void setConCAddress2(String conCAddress2) {
        this.conCAddress2 = conCAddress2;
    }

    public String getConCCity() {
        return conCCity;
    }

    public void setConCCity(String conCCity) {
        this.conCCity = conCCity;
    }

    public String getConCPhone() {
        return conCPhone;
    }

    public void setConCPhone(String conCPhone) {
        this.conCPhone = conCPhone;
    }

    public int getConCState() {
        return conCState;
    }

    public void setConCState(int conCState) {
        this.conCState = conCState;
    }

    public String getAddAddressFlag() {
        return addAddressFlag;
    }

    public void setAddAddressFlag(String addAddressFlag) {
        this.addAddressFlag = addAddressFlag;
    }

    public String getCurrentAddressFlag() {
        return currentAddressFlag;
    }

    public void setCurrentAddressFlag(String currentAddressFlag) {
        this.currentAddressFlag = currentAddressFlag;
    }

    public boolean isCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(boolean checkAddress) {
        this.checkAddress = checkAddress;
    }

    public Map getCountryNames() {
        return countryNames;
    }

    public void setCountryNames(Map countryNames) {
        this.countryNames = countryNames;
    }

    public Map getState() {
        return state;
    }

    public void setState(Map state) {
        this.state = state;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public AccountContactVTO getAccountContactVTO() {
        return accountContactVTO;
    }

    public void setAccountContactVTO(AccountContactVTO accountContactVTO) {
        this.accountContactVTO = accountContactVTO;
    }

    public Map getAllAccTeam() {
        return allAccTeam;
    }

    public void setAllAccTeam(Map allAccTeam) {
        this.allAccTeam = allAccTeam;
    }

    public int getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(int primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getAccFlag() {
        return accFlag;
    }

    public void setAccFlag(String accFlag) {
        this.accFlag = accFlag;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Map getDepartments() {
        return departments;
    }

    public void setDepartments(Map departments) {
        this.departments = departments;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getTitles() {
        return titles;
    }

    public void setTitles(int titles) {
        this.titles = titles;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isIsTeamLead() {
        return isTeamLead;
    }

    public void setIsTeamLead(boolean isTeamLead) {
        this.isTeamLead = isTeamLead;
    }

    public int getAddManager() {
        return addManager;
    }

    public void setAddManager(int addManager) {
        this.addManager = addManager;
    }

    public int getAddTeamLead() {
        return addTeamLead;
    }

    public void setAddTeamLead(int addTeamLead) {
        this.addTeamLead = addTeamLead;
    }

    public Map getVendorTierMap() {
        return vendorTierMap;
    }

    public void setVendorTierMap(Map vendorTierMap) {
        this.vendorTierMap = vendorTierMap;
    }

    public Map getAddVendorTierMap() {
        return addVendorTierMap;
    }

    public void setAddVendorTierMap(Map addVendorTierMap) {
        this.addVendorTierMap = addVendorTierMap;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlagname() {
        return flagname;
    }

    public void setFlagname(String flagname) {
        this.flagname = flagname;
    }
    private String flag;
    private String flagname;

    public String getMoblieNumber() {
        return moblieNumber;
    }

    public void setMoblieNumber(String moblieNumber) {
        this.moblieNumber = moblieNumber;
    }

    public String getContactEmail2() {
        return ContactEmail2;
    }

    public void setContactEmail2(String ContactEmail2) {
        this.ContactEmail2 = ContactEmail2;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getContactDesignation() {
        return contactDesignation;
    }

    public void setContactDesignation(String contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

   

    public Map getDesignations() {
        return designations;
    }

    public void setDesignations(Map designations) {
        this.designations = designations;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
}
