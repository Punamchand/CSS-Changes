/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.projectsdata;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Senay
 */
public class ProjectTeamsDataHandlerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private String resultType;
    private Integer projectID;
    private Integer userID;
    private String status;
    private String createdDate;
    private Integer createdBy;
    private String modifiedDate;
    private Integer modifiedBy;
    private Integer reportsTo1;
    private Integer reportsTo2;
    private String teamMemberName;
    private int ppid;
    private int accountID;
    /**
     * The projectsTeamList list is used for storing data for team members per
     * project
     */
    List<ProjectTeamsVTO> projectsTeamList = new ArrayList<ProjectTeamsVTO>();
    Map teamReportsToList = new HashMap();
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private DataSourceDataProvider dataSourceDataProvider;
    java.util.Date currentDate = new java.util.Date();

    public String getProjectsTeam() {
        System.out.println(":::::::::::::: ProjectTeamsDataHandlerAction ==> getProjectsTeam ::::::::::::::::::");

        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttributeNames() != null) {
                System.out.println("In getProjectsTeam ==> first if ::: the projectID is " + projectID);
                projectsTeamList = ServiceLocator.getProjectTeamsDataHandlerService().getProjectsTeam(projectID);
                System.out.println("The size of projectsTeamList is: " + projectsTeamList.size());
                if (projectsTeamList.size() > 0) {
                    System.out.println("The returned projectsTeamList is " + projectsTeamList.toString());
                    resultType = SUCCESS;
                } else {
                    System.out.println(" ::::: The projectsTeamList does not contain any output :::::::::::");
                    resultType = SUCCESS;
                }
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    public String getTeamReportsTo() {
        System.out.println(":::::::::::::: ProjectTeamsDataHandlerAction ==> getTeamReportsTo ::::::::::::::::::");

        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttributeNames() != null) {
                System.out.println("In getTeamReportsTo ==> first if :::");
                teamReportsToList = dataSourceDataProvider.getReportToPersonByOrgId();
                System.out.println("The size of projectsTeamList is: " + teamReportsToList.size());
                if (teamReportsToList.size() > 0) {
                    System.out.println("The returned projectsTeamList is " + teamReportsToList.toString());
                    resultType = SUCCESS;
                } else {
                    System.out.println(" ::::: The projectsTeamList does not contain any output :::::::::::");
                    resultType = SUCCESS;
                }
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return Action.SUCCESS;
    }

    public String getTeamMemberDetails() {
        resultType = LOGIN;
        //String teamMembersResultString = "";
        try {
            //  System.out.println("Ajax Handler action");
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                String teamMembersResultString = ServiceLocator.getProjectTeamsDataHandlerService().getTeamMemberDetails(this);
                System.out.println("===============>in ProjectAction----->" + teamMembersResultString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(teamMembersResultString);
                //resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }
//praveen <pkatru@miraclesoft.com>

    public String showResourceDetails() {
        resultType = LOGIN;
        String resultString = "";
        try {
            //  System.out.println("Ajax Handler action");
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                resultString = ServiceLocator.getProjectTeamsDataHandlerService().showResourceDetails(this);
                //System.out.println("===============>in ProjectAction----->" + teamMembersResultString);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(resultString);
                //resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }

        return null;
    }
    public String EmpReleasefromProject(){
         resultType = LOGIN;
         System.out.println("in exmp relesae prjct.............................div");
         String result;
        try {
             if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                int updateTeamStatus = ServiceLocator.getProjectTeamsDataHandlerService().EmpReleasefromProject(this);
                System.out.println("===============>in ProjectAction----->" + updateTeamStatus);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                if(updateTeamStatus>0){
                    result="1";
                httpServletResponse.getWriter().write(result);
                }
                else{
                    result="0";
                 httpServletResponse.getWriter().write(result);   
                }
                //resultType = SUCCESS;
            } else {
                return resultType;
            }
        } catch (Exception e) {
            resultType = ERROR;
        }
        return null;
    }
    public void setServletRequest(HttpServletRequest hsr) {
        this.httpServletRequest = hsr;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getReportsTo1() {
        return reportsTo1;
    }

    public void setReportsTo1(Integer reportsTo1) {
        this.reportsTo1 = reportsTo1;
    }

    public Integer getReportsTo2() {
        return reportsTo2;
    }

    public void setReportsTo2(Integer reportsTo2) {
        this.reportsTo2 = reportsTo2;
    }

    public List<ProjectTeamsVTO> getProjectsTeamList() {
        return projectsTeamList;
    }

    public void setProjectsTeamList(List<ProjectTeamsVTO> projectsTeamList) {
        this.projectsTeamList = projectsTeamList;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public DataSourceDataProvider getDataSourceDataProvider() {
        return dataSourceDataProvider;
    }

    public void setDataSourceDataProvider(DataSourceDataProvider dataSourceDataProvider) {
        this.dataSourceDataProvider = dataSourceDataProvider;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Map getTeamReportsToList() {
        return teamReportsToList;
    }

    public void setTeamReportsToList(Map teamReportsToList) {
        this.teamReportsToList = teamReportsToList;
    }

    public String getTeamMemberName() {
        return teamMemberName;
    }

    public void setTeamMemberName(String teamMemberName) {
        this.teamMemberName = teamMemberName;
    }

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
    
}
