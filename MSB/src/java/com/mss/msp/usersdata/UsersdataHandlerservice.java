/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usersdata;

import com.mss.msp.usr.task.TaskHandlerAction;
import com.mss.msp.util.ServiceLocatorException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public interface UsersdataHandlerservice {

    public List getEmployeeSearchDetails(UsersdataHandlerAction usersdataHandlerAction, HttpServletRequest httpServletRequest) throws ServiceLocatorException;

    public List getAllEmployeeDetails(HttpServletRequest httpServletRequest) throws ServiceLocatorException;
    //added by praveen<pkatru@miraclesoft.com>

    public UserAddress getEmployeeAddress(HttpServletRequest httpServletRequest, String tableName) throws ServiceLocatorException;

    public EmpDetails getEmployeeDetails(HttpServletRequest httpServletRequest, int userid) throws ServiceLocatorException;

    public boolean updateEmpDetails(UsersdataHandlerAction usersdataHandlerAction, HttpServletRequest httpServletRequest, int userSessionId) throws ServiceLocatorException;

    /* public Map getAllRoles(HttpServletRequest httpServletRequest, int userId) throws ServiceLocatorException;

     public Map getAssignedRoles(HttpServletRequest httpServletRequest, int userId) throws ServiceLocatorException;

     public Map getNotAssignedRoles(HttpServletRequest httpServletRequest, int userId) throws ServiceLocatorException;
     */
    public Map getAllRoles(int userId, String type_of_relation) throws ServiceLocatorException;

    public Map getAssignedRoles(int userId) throws ServiceLocatorException;

    public Map getNotAssignedRoles(int userId, String type_of_relation) throws ServiceLocatorException;

    //upto here
    //added by praveen<pkatru@miraclesoft.com>
    public Map getDepartment_Names(HttpServletRequest httpServletRequest, UsersdataHandlerAction aThis);

    /**
     * to insert roles **?
     *
     *
     */
    public int insertRoles(String[] roles, int employeeId, int primaryRoles) throws ServiceLocatorException;

    public List<UserVTO> getCsrList(UsersdataHandlerAction usersdataHandlerAction, int userId) throws ServiceLocatorException;

    public List<UserVTO> getCsrAccounts(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException;

    public List<UserVTO> getEmployeeCategorization(UsersdataHandlerAction usersdataHandlerAction, int userOrgSessionId) throws ServiceLocatorException;

    public void getUserGroupingData(UsersdataHandlerAction aThis) throws ServiceLocatorException;

    public List getHomeRedirectDetails(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException;

    public HomeVTO getHomeRedirectDetailsForEdit(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException;

  
}
