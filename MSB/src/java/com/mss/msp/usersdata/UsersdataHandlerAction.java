/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usersdata;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author miracle
 */
public class UsersdataHandlerAction extends ActionSupport implements ServletRequestAware, ParameterAware {

    private String resultType;
    /**
     * The resultMessage is used for storing resultMessage.
     */
    private String resultMessage;
    private String empLoginId;
    private String first_name;
    private String last_name;
    private String cur_status;
    private String Phone1;
    private String empName;
    private String status;
    private String workPhone;
    private String organization;
    private String reportingName;
    private String userType;
    private String dob;
    private String working_country;
    private String living_country;
    private String alias;
    private String marital_status;
    private String gender;
    private String email1;
    private String email2;
    private String corp_phone;
    private String fax;
    private String current_status;
    //variables address updation added by Rk
    private String address;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String address_flag;
    private int userSessionId;
    private String emp_position;
    private int resultFlag;
    //variables sddress updation
    /**
     * The httpServletRequest is used for storing httpServletRequest Object.
     */
    private int userid;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private UserAddress userAddress;
    private EmpDetails empDetails;
    private Map orgRoles;
    private Map notAssignedRoles;
    private Map assignedRoles;
    //added by praveen <pkatru@miraclesoft.com>
    private Map department;
    private Map Title_name;
    private int primaryRole;
    private Map parameters;
    private List leftSideEmployeeRoles;
    private List addedRolesList;
    private boolean is_manager;
    private boolean opt_contact;
    private boolean team_leader;
    private String departmentId;
    private String reportingPerson;
    private Map departments;
    private Map reportsTo;
    private Map countries;
    List<UserVTO> userVTO = new ArrayList<UserVTO>();
    private String csrName;
    private int userId;
    private Map categoryTypes;
    private int userOrgSessionId;
    private int categoryType;
    private int primary;
    //private int userId;
    private int usrCategoryValue;
    private String usrStatus;
    private String usrDescription;
    private String userName;
    private int groupingId;
    private boolean primaryvalue;
    private Map usrCategory;
    //for home Rediredt
    private List homeVTO;
    private Map accountsMap;
    private Map rolesMap;
    private int homeRedirectActionId;
    private String accountType = "All";
    private HomeVTO homeVto;
    private String accountSearchID;

    public UsersdataHandlerAction() {
    }
    private DataSourceDataProvider dataSourceDataProvider;

    public String employeeSearch() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setDepartments(dataSourceDataProvider.getInstance().getDepartmentNameByOrgId());
                setReportsTo(dataSourceDataProvider.getInstance().getReportToPersonByOrgId());
                List searchDetails = ServiceLocator.getUsersdataHandlerservicee().getAllEmployeeDetails(httpServletRequest);
                //System.out.println(searchDetails.size());
                if (searchDetails.size() > 0) {
                    //System.out.println("in if emp sarech");
                    httpServletRequest.getSession(false).setAttribute("empData", searchDetails);
                    resultType = SUCCESS;
                } else {
                    httpServletRequest.getSession(false).setAttribute("empData", null);
                    resultType = SUCCESS;
                }
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    public String doGetEmployeeSearchDetails() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setDepartments(dataSourceDataProvider.getInstance().getDepartmentNameByOrgId());
                setReportsTo(dataSourceDataProvider.getInstance().getReportToPersonByOrgId());
                List searchDetails = ServiceLocator.getUsersdataHandlerservicee().getEmployeeSearchDetails(this, httpServletRequest);
                //System.out.println(searchDetails.size());
                if (searchDetails.size() > 0) {
                    //System.out.println("in if emp sarech");
                    httpServletRequest.getSession(false).setAttribute("empData", searchDetails);
                    resultType = SUCCESS;
                } else {
                    // System.out.println("in ifelse emp sarech");
                    httpServletRequest.getSession(false).setAttribute("empData", null);
                    resultType = SUCCESS;
                }
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     *
     * This method is used to personal employee profile
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public String getMyProfile() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                // System.err.println("get my profile action");
                if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString().equalsIgnoreCase("CO")) {
                    setUserAddress(ServiceLocator.getUsersdataHandlerservicee().getEmployeeAddress(httpServletRequest, "consultant_address"));
                } else {
                    setUserAddress(ServiceLocator.getUsersdataHandlerservicee().getEmployeeAddress(httpServletRequest, "usr_address"));
                }


                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     *
     * This method is used to GET employee profile
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public String getempProfile() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                // System.err.println("get emp profile action");
                setPrimaryRole(Integer.parseInt(DataSourceDataProvider.getInstance().getUsrPrimaryRole(this.getUserid()).split("$$")[0]));
                //setNotAssignedRoles(ServiceLocator.getUsersdataHandlerservicee().getNotAssignedRoles(httpServletRequest, this.getUserid()));
                //setAssignedRoles(ServiceLocator.getUsersdataHandlerservicee().getAssignedRoles(httpServletRequest, this.getUserid()));
                //setOrgRoles(ServiceLocator.getUsersdataHandlerservicee().getAllRoles(httpServletRequest, this.getUserid()));
                //above method written by jagan
                setCountries(com.mss.msp.util.DataSourceDataProvider.getInstance().getCountryNames());
                setEmpDetails(ServiceLocator.getUsersdataHandlerservicee().getEmployeeDetails(httpServletRequest, this.getUserid()));
                setDepartment(ServiceLocator.getUsersdataHandlerservicee().getDepartment_Names(httpServletRequest, this));
                if (getResultFlag() > 0) {
                    addActionMessage("Profile update successfully");
                }

                resultType = SUCCESS;

            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    public String doAddOrUpdateEmpRoles() throws Exception {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                //int usrId=Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                //setUserid(usrId);
                //System.out.println("doAddOrUpdateEmpRoles");

                //System.out.println("primary roll-->" + getPrimaryRole() + "--getaddedoles-->" + getAddedRolesList());

                String[] rightParams = (String[]) parameters.get("addedRolesList");
                //leftSideEmployeeRoles

                // System.out.println("rightParams.length--->" + rightParams.length);

                int insertedRows = ServiceLocator.getUsersdataHandlerservicee().insertRoles(rightParams, getUserid(), getPrimaryRole());
                // resultMessage = "<font color=\"green\" size=\"1.5\">Roles has been successfully Added!</font>";
                if (insertedRows > 0) {
                    addActionMessage("Roles has been successfully Added!");
                } else {
                    addActionMessage("No Records Updated!");
                }
                httpServletRequest.setAttribute(ApplicationConstants.RESULT_MSG, resultMessage);
                resultType = SUCCESS;

            }
        } catch (Exception ex) {
            //System.out.println("doAddOrUpdateEmpRoles-->" + ex.getMessage());
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }

        return resultType;
    }

    /**
     *
     * This method is used to update employee profile
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public String updateEmpProfile() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                userSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                //  System.err.println("update profile action");

                ServiceLocator.getUsersdataHandlerservicee().updateEmpDetails(this, httpServletRequest, userSessionId);
                //  System.out.println("hear updation is completed....");
                addActionMessage("Profile Updated successfully");
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     *
     * This method is used to GET employee profile
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public String getUserRoles() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                // System.err.println("get emp profile action");
                String role = DataSourceDataProvider.getInstance().getUsrPrimaryRole(this.getUserid());
                String[] parts = role.split("#");
                String part1 = parts[0]; // 004
                String part2 = parts[1];
                String type_of_relation = com.mss.msp.util.DataSourceDataProvider.getInstance().getOrganizationType(this.getAccountSearchID());
                setPrimaryRole(Integer.parseInt(part1));
                /*setOrgRoles(ServiceLocator.getUsersdataHandlerservicee().getAllRoles(httpServletRequest, this.getUserid()));
                 setNotAssignedRoles(ServiceLocator.getUsersdataHandlerservicee().getNotAssignedRoles(httpServletRequest, this.getUserid()));
                 setAssignedRoles(ServiceLocator.getUsersdataHandlerservicee().getAssignedRoles(httpServletRequest, this.getUserid()));
                 */
                setOrgRoles(ServiceLocator.getUsersdataHandlerservicee().getAllRoles(this.getUserid(), type_of_relation));
                setNotAssignedRoles(ServiceLocator.getUsersdataHandlerservicee().getNotAssignedRoles(this.getUserid(), type_of_relation));
                setAssignedRoles(ServiceLocator.getUsersdataHandlerservicee().getAssignedRoles(this.getUserid()));
                setEmpDetails(ServiceLocator.getUsersdataHandlerservicee().getEmployeeDetails(httpServletRequest, this.getUserid()));

                if (getResultFlag() > 0) {
                    addActionMessage("Profile update successfully");
                }
                resultType = SUCCESS;

            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     * *****************************************************************************
     * Date : july 15 2015 Author : manikanta<meeralla@miraclesoft.com>
     * getCsrList() getting the csrs List
     * *****************************************************************************
     */
    public String getCsrList() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                userSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                //  System.err.println("update profile action");

                userVTO = ServiceLocator.getUsersdataHandlerservicee().getCsrList(this, userSessionId);
                //  System.out.println("hear updation is completed....");
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     * *****************************************************************************
     * Date : july 15 2015 Author : manikanta<meeralla@miraclesoft.com>
     * getCsrAccounts() getting the csrs Accounts
     * *****************************************************************************
     */
    public String getCsrAccounts() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                userSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                //  System.err.println("update profile action");

                userVTO = ServiceLocator.getUsersdataHandlerservicee().getCsrAccounts(this);
                //  System.out.println("hear updation is completed....");
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }
    private int usrCatType;
    private String catValues;
    private Map catValuesMap;
    private String catActiveId;

    public Map getCatValuesMap() {
        return catValuesMap;
    }

    public String getCatActiveId() {
        return catActiveId;
    }

    public void setCatActiveId(String catActiveId) {
        this.catActiveId = catActiveId;
    }

    public void setCatValuesMap(Map catValuesMap) {
        this.catValuesMap = catValuesMap;
    }

    public String getCatValues() {
        return catValues;
    }

    public void setCatValues(String catValues) {
        this.catValues = catValues;
    }

    public int getUsrCatType() {
        return usrCatType;
    }

    public void setUsrCatType(int usrCatType) {
        this.usrCatType = usrCatType;
    }
    private List catValuesList;

    public List getCatValuesList() {
        return catValuesList;
    }

    public void setCatValuesList(List catValuesList) {
        this.catValuesList = catValuesList;
    }

    public String getEmployeeCategorization() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                //userSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                userOrgSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString());
                //  System.err.println("update profile action");
                setCategoryTypes(dataSourceDataProvider.getInstance().getRequiteCategory());
                userVTO = ServiceLocator.getUsersdataHandlerservicee().getEmployeeCategorization(this, userOrgSessionId);
                //  System.out.println("hear updation is completed....");
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     *
     * This method is used to GET employee profile
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public String getUserGroping() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                userSessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                setUsrCategory(com.mss.msp.util.DataSourceDataProvider.getInstance().getRequiteCategory());
                ServiceLocator.getUsersdataHandlerservicee().getUserGroupingData(this);
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;

    }

    /**
     * *****************************************************************************
     * Date : july 15 2015 Author :ramakrishna<lankireddy@miraclesoft.com>
     * getHomeRedirectDetails() getting HomeRedirectDetails List
     * *****************************************************************************
     */
    public String getHomeRedirectDetails() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setAccountsMap(com.mss.msp.util.DataSourceDataProvider.getInstance().getAllAccounts());
                setRolesMap(com.mss.msp.util.DataSourceDataProvider.getInstance().getAllRoles(getAccountType()));
                homeVTO = ServiceLocator.getUsersdataHandlerservicee().getHomeRedirectDetails(this);
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     * *****************************************************************************
     * Date : july 15 2015 Author :ramakrishna<lankireddy@miraclesoft.com>
     * getHomeRedirectDetails() getting HomeRedirectDetails List
     * *****************************************************************************
     */
    public String doAddOrEditHomeRedirect() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setUserSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                System.out.println(">>>>>>>>>>homeRedirectActionId>>>>" + getHomeRedirectActionId());
                setRolesMap(com.mss.msp.util.DataSourceDataProvider.getInstance().getAllRoles(getAccountType()));
                if (getHomeRedirectActionId() >= 0) {
                    homeVto = ServiceLocator.getUsersdataHandlerservicee().getHomeRedirectDetailsForEdit(this);
                }
                System.out.println(">>>>" + homeVto.toString());
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    @Override
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

    public String getEmpLoginId() {
        return empLoginId;
    }

    public void setEmpLoginId(String empLoginId) {
        this.empLoginId = empLoginId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCur_status() {
        return cur_status;
    }

    public void setCur_status(String cur_status) {
        this.cur_status = cur_status;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String Phone1) {
        this.Phone1 = Phone1;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getReportingName() {
        return reportingName;
    }

    public void setReportingName(String reportingName) {
        this.reportingName = reportingName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the userAddress
     */
    public UserAddress getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getWorking_country() {
        return working_country;
    }

    public void setWorking_country(String working_country) {
        this.working_country = working_country;
    }

    public String getLiving_country() {
        return living_country;
    }

    public void setLiving_country(String living_country) {
        this.living_country = living_country;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        if ("1".equalsIgnoreCase(marital_status)) {
            this.marital_status = "S";
        } else {
            this.marital_status = "M";
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if ("1".equalsIgnoreCase(gender)) {
            this.gender = "M";
        } else {
            this.gender = "F";
        }
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getCorp_phone() {
        return corp_phone;
    }

    public void setCorp_phone(String corp_phone) {
        this.corp_phone = corp_phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public EmpDetails getEmpDetails() {
        return empDetails;
    }

    public void setEmpDetails(EmpDetails empDetails) {
        this.empDetails = empDetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress_flag() {
        return address_flag;
    }

    public void setAddress_flag(String address_flag) {
        this.address_flag = address_flag;
    }

    public Map getDepartment() {
        return department;
    }

    public void setDepartment(Map department) {
        this.department = department;
    }

    public Map getTitle_name() {
        return Title_name;
    }

    public void setTitle_name(Map Title_name) {
        this.Title_name = Title_name;
    }

    public int getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(int primaryRole) {
        this.primaryRole = primaryRole;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public List getLeftSideEmployeeRoles() {
        return leftSideEmployeeRoles;
    }

    public void setLeftSideEmployeeRoles(List leftSideEmployeeRoles) {
        this.leftSideEmployeeRoles = leftSideEmployeeRoles;
    }

    public List getAddedRolesList() {
        return addedRolesList;
    }

    public void setAddedRolesList(List addedRolesList) {
        this.addedRolesList = addedRolesList;
    }

    public Map getOrgRoles() {
        return orgRoles;
    }

    public void setOrgRoles(Map orgRoles) {
        this.orgRoles = orgRoles;
    }

    public Map getNotAssignedRoles() {
        return notAssignedRoles;
    }

    public void setNotAssignedRoles(Map notAssignedRoles) {
        this.notAssignedRoles = notAssignedRoles;
    }

    public Map getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(Map assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public String getEmp_position() {
        return emp_position;
    }

    public void setEmp_position(String emp_position) {
        this.emp_position = emp_position;
    }

    public boolean isIs_manager() {
        return is_manager;
    }

    public void setIs_manager(boolean is_manager) {
        this.is_manager = is_manager;
    }

    public boolean isOpt_contact() {
        return opt_contact;
    }

    public void setOpt_contact(boolean opt_contact) {
        this.opt_contact = opt_contact;
    }

    public boolean isTeam_leader() {
        return team_leader;
    }

    public void setTeam_leader(boolean team_leader) {
        this.team_leader = team_leader;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getReportingPerson() {
        return reportingPerson;
    }

    public void setReportingPerson(String reportingPerson) {
        this.reportingPerson = reportingPerson;
    }

    public Map getDepartments() {
        return departments;
    }

    public void setDepartments(Map departments) {
        this.departments = departments;
    }

    public Map getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Map reportsTo) {
        this.reportsTo = reportsTo;
    }

    public Map getCountries() {
        return countries;
    }

    public void setCountries(Map countries) {
        this.countries = countries;
    }

    public int getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(int resultFlag) {
        this.resultFlag = resultFlag;
    }

    public List<UserVTO> getUserVTO() {
        return userVTO;
    }

    public void setUserVTO(List<UserVTO> userVTO) {
        this.userVTO = userVTO;
    }

    public String getCsrName() {
        return csrName;
    }

    public void setCsrName(String csrName) {
        this.csrName = csrName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map getCategoryTypes() {
        return categoryTypes;
    }

    public void setCategoryTypes(Map categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public int getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(int userSessionId) {
        this.userSessionId = userSessionId;
    }

    public int getUserOrgSessionId() {
        return userOrgSessionId;
    }

    public void setUserOrgSessionId(int userOrgSessionId) {
        this.userOrgSessionId = userOrgSessionId;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getUsrCategoryValue() {
        return usrCategoryValue;
    }

    public void setUsrCategoryValue(int usrCategoryValue) {
        this.usrCategoryValue = usrCategoryValue;
    }

    public String getUsrStatus() {
        return usrStatus;
    }

    public void setUsrStatus(String usrStatus) {
        this.usrStatus = usrStatus;
    }

    public String getUsrDescription() {
        return usrDescription;
    }

    public void setUsrDescription(String usrDescription) {
        this.usrDescription = usrDescription;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(int groupingId) {
        this.groupingId = groupingId;
    }

    public boolean isPrimaryvalue() {
        return primaryvalue;
    }

    public void setPrimaryvalue(boolean primaryvalue) {
        this.primaryvalue = primaryvalue;
    }

    public Map getUsrCategory() {
        return usrCategory;
    }

    public void setUsrCategory(Map usrCategory) {
        this.usrCategory = usrCategory;
    }

    public List getHomeVTO() {
        return homeVTO;
    }

    public void setHomeVTO(List homeVTO) {
        this.homeVTO = homeVTO;
    }

    public Map getAccountsMap() {
        return accountsMap;
    }

    public void setAccountsMap(Map accountsMap) {
        this.accountsMap = accountsMap;
    }

    public Map getRolesMap() {
        return rolesMap;
    }

    public void setRolesMap(Map rolesMap) {
        this.rolesMap = rolesMap;
    }

    public int getHomeRedirectActionId() {
        return homeRedirectActionId;
    }

    public void setHomeRedirectActionId(int homeRedirectActionId) {
        this.homeRedirectActionId = homeRedirectActionId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public HomeVTO getHomeVto() {
        return homeVto;
    }

    public void setHomeVto(HomeVTO homeVto) {
        this.homeVto = homeVto;
    }

    public String getAccountSearchID() {
        return accountSearchID;
    }

    public void setAccountSearchID(String accountSearchID) {
        this.accountSearchID = accountSearchID;
    }
}
