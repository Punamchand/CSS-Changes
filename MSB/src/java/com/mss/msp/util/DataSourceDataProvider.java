/**
 * *****************************************************************************
 *
 * Project : ServicesBay V1
 *
 * Package :
 *
 * Date : Feb 16, 2015, 7:53 PM
 *
 * Author : Services Bay Team
 *
 * File : DataSourceDataProvider.java
 *
 * Copyright 2015 Miracle Software Systems, Inc. All rights reserved. MIRACLE
 * SOFTWARE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * *****************************************************************************
 */
package com.mss.msp.util;

import com.mss.msp.recruitment.RecruitmentAction;
import com.mss.msp.requirement.RequirementVTO;
import com.mss.msp.usr.task.TaskHandlerAction;
import com.mss.msp.usr.task.TasksVTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public class DataSourceDataProvider {

    private static DataSourceDataProvider _instance;
    private Connection connection;

    /**
     * Creates a new instance of DataSourceDataProvider
     */
    private DataSourceDataProvider() {
    }

    /**
     * @return An instance of the DataServiceLocator class
     * @throws ServiceLocatorException
     */
    public static DataSourceDataProvider getInstance() throws ServiceLocatorException {
        try {
            if (_instance == null) {
                _instance = new DataSourceDataProvider();
            }
        } catch (Exception ex) {
            throw new ServiceLocatorException(ex);
        }
        return _instance;
    }

    /*
     * Author :Prasad Kandregula
     * Date   :March 03 2015
     * Method :getUserIdAndStatusByEmail() 
     */
    public String getUserIdAndStatusByEmail(String email) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String status = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        //String queryString = "Select Id,ProjectName,ProjectType from tblProjects where Id ="+projectId;
        //String queryString = "SELECT usr_id,cur_status FROM users WHERE login_id LIKE '"+email.trim()+"'";
        String queryString = "SELECT u.usr_id,cur_status FROM users u LEFT OUTER JOIN usr_reg ur "
                + "ON(u.usr_id=ur.usr_id) WHERE login_id LIKE '" + email.trim() + "'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                usr_Id = resultSet.getInt("usr_id");
                status = resultSet.getString("cur_status");
                isRecordExists = 1;
            }
            if (isRecordExists == 1) {
                resultString = usr_Id + "^" + status;
            } else {
                resultString = "NoRecordExists";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    /**
     *
     * @param emailId
     * @return
     * @throws ServiceLocatorException
     */
    public int checkLoginIdExistance(String emailId) throws ServiceLocatorException {

        int count = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "Select count(usr_id) as id from users where email1 like '" + emailId + "'";
        //System.out.println("queryString-->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                count = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return count;
    }

    /**
     *
     */
    public Map getUsrRolesMap(int usrId) throws ServiceLocatorException {

        Map rolesMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select r.role_id as roleId,role_name from usr_roles ur left outer join roles r on(ur.role_id=r.role_id) where usr_id=" + usrId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                rolesMap.put(resultSet.getInt("roleId"), resultSet.getString("role_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return rolesMap;

    }

    public String getUsrPrimaryRole(int usrId) throws ServiceLocatorException {
        int primaryrole = 0;
        String resultString = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        System.err.println("In getUsrPrimaryRole");
        String queryString = "SELECT r.role_id as roleId,role_name FROM usr_roles ur LEFT OUTER JOIN roles r ON(ur.role_id=r.role_id) WHERE usr_id=" + usrId + " and primary_flag=1";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                primaryrole = resultSet.getInt("roleId");
                resultString = primaryrole + "#" + resultSet.getString("role_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    public int getOrgIdByEmailExt(String loginId) throws ServiceLocatorException {
        int orgId = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        //  System.out.println("logininfo-->" + loginId.split("\\@")[1]);

        String queryString = "SELECT org_id FROM siteaccess_mail_ext WHERE email_ext='" + loginId.split("\\@")[1] + "'";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                orgId = resultSet.getInt("org_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return orgId;
    }

    /* public int getactionsCount(int orgId, String typeofuser, int prole) throws ServiceLocatorException {
     int count = 0;
     Connection connection = null;
     Statement statement = null;
     ResultSet resultSet = null;
     connection = ConnectionProvider.getInstance().getConnection();

     String queryString = "SELECT count(action_name) as count FROM home_redirect_action where org_id=" + orgId + " and type_of_user='" + typeofuser + "' and primaryrole=" + prole;
     // System.out.println("queryString-->" + queryString);
     System.out.println("queryString in getactionsCount-->" + queryString);
     try {
     statement = connection.createStatement();
     resultSet = statement.executeQuery(queryString);
     while (resultSet.next()) {
     count = resultSet.getInt("count");
     }
     } catch (SQLException ex) {
     ex.printStackTrace();
     } finally {

     try {
     // resultSet Object Checking if it's null then close and set null
     if (resultSet != null) {
     resultSet.close();
     resultSet = null;
     }

     if (statement != null) {
     statement.close();
     statement = null;
     }

     if (connection != null) {
     connection.close();
     connection = null;
     }
     } catch (SQLException ex) {
     throw new ServiceLocatorException(ex);
     }
     }
     return count;
     }*/
    //get roles map by orgId
    public Map getTaskStatusByOrgId() throws ServiceLocatorException {

        Map tasksStatusMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select id,task_status_name from lk_task_status";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                tasksStatusMap.put(resultSet.getInt("id"), resultSet.getString("task_status_name"));
            }
        } catch (SQLException ex) {
            // System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //  System.out.println("tasksStatusMap-->" + tasksStatusMap);
        return tasksStatusMap;

    }

    //get task related to map
    public Map getTaskrelatedToMap() throws ServiceLocatorException {

        Map tasksRelatedtoMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT task_relatedto_id,task_relatedto_name FROM lk_taskrelated_to WHERE STATUS LIKE 'Active'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                tasksRelatedtoMap.put(resultSet.getInt("task_relatedto_id"), resultSet.getString("task_relatedto_name"));
            }
        } catch (SQLException ex) {
            //  System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        // System.out.println("tasksStatusMap-->" + tasksRelatedtoMap);
        return tasksRelatedtoMap;

    }

    //task related to by orgId
    public Map getTaskrelatedToMapByOrgId(String orgId) throws ServiceLocatorException {

        Map tasksRelatedtoMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT task_relatedto_id,task_relatedto_name FROM lk_taskrelated_to WHERE STATUS LIKE 'Active' AND org_id=" + orgId + "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                tasksRelatedtoMap.put(resultSet.getInt("task_relatedto_id"), resultSet.getString("task_relatedto_name"));
            }
        } catch (SQLException ex) {
            //  System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        // System.out.println("tasksStatusMap-->" + tasksRelatedtoMap);
        return tasksRelatedtoMap;

    }

    public Map getTasksTypeByRelatedId(String relatedToId) throws ServiceLocatorException {

        Map tasksTypesMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT task_types_id,task_type_name FROM lk_task_types WHERE STATUS LIKE 'Active' AND task_rel_toId==" + relatedToId + "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                tasksTypesMap.put(resultSet.getInt("task_relatedto_id"), resultSet.getString("task_relatedto_name"));
            }
        } catch (SQLException ex) {
            //   System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        // System.out.println("tasksStatusMap-->" + tasksTypesMap);
        return tasksTypesMap;

    }

    /**
     * *****************************************************************************
     * Date : APRIL 14, 2015, 3:35 PM IST
     *
     * Author : Praveen kumar<pkatru@miraclesoft.com>
     *
     * ForUse : getting Employee Designation getting Employee Designation based
     * on userId and return map object
     * *****************************************************************************
     */
    public Map GetEmployeeDesignation(int userId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        Map MapDesignations = new HashMap();


        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String sqlString = "select is_team_lead,is_manager from usr_miscellaneous where usr_id=?";
            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, userId);
            resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                MapDesignations.put("is_team_lead", resultset.getInt("is_team_lead"));
                MapDesignations.put("is_manager", resultset.getInt("is_manager"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultset != null) {
                    resultset.close();
                    resultset = null;
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return MapDesignations;
    }

    /*Methods from here created by rk*/
    /**
     * *****************************************************************************
     * Date : APRIL 16, 2015, 8:30 PM IST
     *
     * Author : Praveen kumar<pkatru@miraclesoft.com> Author :
     * RamaKrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting MyTeamMembers based on userId on userId and return map
     * object
     * *****************************************************************************
     */
    public Map getMyTeamMembers(int reportsTo) throws ServiceLocatorException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map myTeamMembers = new TreeMap();

        try {

            //queryString = "SELECT m.usr_id,m.reports_to,m.is_team_lead,m.is_manager,m.is_sbteam,m.is_PMO,m.opt_contact,u.first_name,u.last_name FROM usr_miscellaneous m LEFT OUTER JOIN users u ON (m.usr_id=u.usr_id) WHERE m.reports_to=?";

            queryString = "SELECT pt.usr_id,pt.designation,first_name,last_name,pt.current_status FROM project_team pt LEFT OUTER JOIN users u ON ((pt.usr_id=u.usr_id)) WHERE pt.reportsto1=? AND pt.current_status LIKE 'Active'";
            preparedStatement = connection.prepareStatement(queryString);
            System.out.println("query==========>" + queryString);

            myTeamMembers = getMyTeamMembersUpTo(reportsTo, preparedStatement);

            //myTeamMembers.put(reportsTo, getFname_Lname(reportsTo));


            //Closing Cache Checking
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }


        // System.out.println("myTeamMembers"+myTeamMembers);
        // System.out.println("I am Out of Data Source Provider");
        return myTeamMembers; // returning the object.
    }

    /**
     * *****************************************************************************
     * Date : APRIL 16, 2015, 8:30 PM IST Author :
     * Praveenkumar<pkatru@miraclesoft.com> Author
     * :RamaKrishna<lankireddy@miraclesoft.com> ForUse : getting TeamMembers
     * under userId And return map object
     * *****************************************************************************
     */
    public Map getMyTeamMembersUpTo(int reportsTo, java.sql.PreparedStatement theStatement) throws ServiceLocatorException {
        ResultSet resultSet = null;
        Map myTeamManagersMap = new TreeMap();
        Map tempMap = new TreeMap();
        int[] keys = new int[100];
        int keyCnt = 0;
        int key = 0;
        String value = null;

        try {
            //System.out.println("Main ReportsTo:" + reportsTo);
            theStatement.setInt(1, reportsTo);


            resultSet = theStatement.executeQuery();
            while (resultSet.next()) {
                key = resultSet.getInt("usr_id");
                value = resultSet.getString("first_name") + "." + resultSet.getString("last_name");
                myTeamManagersMap.put(key, value);
                // If the Team Member is a Manager then Get his Team Members List
                // if ((resultSet.getInt("designation")) != 0 || (resultSet.getInt("designation")) != 0 || (resultSet.getInt("is_PMO") != 0) || (resultSet.getInt("is_sbteam") != 0)) {
                if (DataUtility.getInstance().getTimsheetAccessingRolesList().contains(resultSet.getInt("designation"))) {
                    keys[keyCnt] = key;
                    keyCnt++;
                    //  System.out.println("keyCnt--- Value"+keyCnt);

                }
            }

            for (int i = 0; i < keyCnt; i++) {
                key = keys[i];

                tempMap = getMyTeamMembersUpTo(key, theStatement);

                if (tempMap.size() > 0) {
                    Iterator tempIterator = tempMap.entrySet().iterator();
                    while (tempIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) tempIterator.next();
                        key = Integer.parseInt(entry.getKey().toString());
                        value = entry.getValue().toString();
                        myTeamManagersMap.put(key, value);
                        entry = null;
                    }
                }
                // System.out.println("keyCnt value"+keyCnt);
                // System.out.println("i value"+i);
            }

        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        }
        myTeamManagersMap = sortMapByValues(myTeamManagersMap);
        return myTeamManagersMap; // returning the object.
    } //closing the method

    /**
     * *****************************************************************************
     * Date : APRIL 16, 2015, 8:30 PM IST Author :
     * Praveenkumar<pkatru@miraclesoft.com> Author :
     * RamaKrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : sorting of map taken from Nagireddy<nseerapu@miraclesoft.com>
     * sir
     * *****************************************************************************
     */
    public < K, V extends Comparable< ? super V>> Map< K, V> sortMapByValues(final Map< K, V> mapToSort) {
        List< Map.Entry< K, V>> entries =
                new ArrayList< Map.Entry< K, V>>(mapToSort.size());

        entries.addAll(mapToSort.entrySet());

        Collections.sort(entries,
                new Comparator< Map.Entry< K, V>>() {
                    public int compare(
                            final Map.Entry< K, V> entry1,
                            final Map.Entry< K, V> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue());
                    }
                });

        Map< K, V> sortedMap = new LinkedHashMap< K, V>();

        for (Map.Entry< K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 18, 2015, 2:23 AM IST Author
     * :Praveenkumar<pkatru@miraclesoft.com>
     *
     * ForUse : getting child organizations and organization names based on
     * organization id
     * *****************************************************************************
     */
    public Map getOrganizationRelations(int org_id) {
        Map childmap = new LinkedHashMap();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "SELECT DISTINCT account_id,account_name FROM accounts JOIN org_rel ON(account_id=related_org_id) AND org_id=? WHERE type_of_relation='M' OR type_of_relation='CH'";

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, org_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                childmap.put(resultSet.getInt("account_id"), resultSet.getString("account_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return childmap;
    }

    /**
     * *************************************
     *
     * @getReportToPersonByOrgId() This method is used to set the department
     * name in employee search field employeeSearch.jsp
     * @Author:Aklakh Ahmad
     *
     * @Created Date:04/20/2015
     *
     **************************************
     */
    public Map getDepartmentNameByOrgId() throws ServiceLocatorException {

        Map departmentNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select dept_id, dept_name from departments";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                departmentNameMap.put(resultSet.getInt("dept_id"), resultSet.getString("dept_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getDepartmentNameByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("departmentNameMap-->" + departmentNameMap);
        return departmentNameMap;

    }

    /**
     * *************************************
     *
     * @getReportToPersonByOrgId() This method is used to set the reporting
     * person name in employee search field employeeSearch.jsp
     * @Author:Aklakh Ahmad
     *
     * @Created Date:04/20/2015
     *
     **************************************
     */
    public Map getReportToPersonByOrgId() throws ServiceLocatorException {

        Map reportToPersonMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT DISTINCT mis.usr_id, CONCAT_WS(' ',u.first_name, u.last_name)AS name  "
                + "FROM users u JOIN usr_miscellaneous mis ON(u.usr_id=mis.usr_id)"
                + "WHERE(mis.is_team_lead=1 OR mis.is_manager=1)";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                reportToPersonMap.put(resultSet.getInt("usr_id"), resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("getReportToPersonByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("reportToPersonMap-->" + reportToPersonMap);
        return reportToPersonMap;

    }
    /*
     * Author :Kiran Arogya
     * Date   :April 04 2015
     * Method :getFnameandLnamebyUserid()
     * This methood is used to get first name and last name based on userid from users table.
     */

    public String getFnameandLnamebyUserid(int userId) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String user_name = "";
        String status = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT CONCAT(first_name,' ',last_name) AS NAME FROM users WHERE usr_id=" + userId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                user_name = resultSet.getString("NAME");
                isRecordExists = 1;
            }
            if (isRecordExists == 1) {
                resultString = user_name;
            } else {
                resultString = " - ";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {

                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    /**
     * *****************************************************************************
     * Date : April 14 2015
     *
     * Author : jagan chukkala<jchukkala@miraclesoft.com>
     *
     * getReportingPersonByUserId() method can be used to get reporting person
     * by using user id, And returns Reporting person name for the respected
     * user
     * *****************************************************************************
     */
    public String getReportingPersonByUserId(int userId) throws ServiceLocatorException {

        String reporting_person = " ";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        System.out.println("userid is" + userId);
        String queryString = "SELECT CONCAT_WS(' ',first_name,last_name) AS name FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id = " + userId + ")";
        // String queryString1="SELECT usr_id FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id = "+userId+")";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                //reporting_person=put(resultSet.getInt("task_relatedto_id"), resultSet.getString("task_relatedto_name"));
                reporting_person = resultSet.getString("name");


            }
            // reporting_person=  reporting_person.concat(reporting);

            //reporting_person=reporting_person.concat(reporting);
            System.out.println("reporting person is" + reporting_person);
        } catch (SQLException ex) {
            System.out.println("getReportingPersonByUserId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("reporting person is-->" + reporting_person);
        return reporting_person;

    }

    /**
     * *****************************************************************************
     * Date : April 14 2015
     *
     * Author : jagan chukkala<jchukkala@miraclesoft.com>
     *
     * getReportingPersonByUserId() method can be used to get reporting person
     * by using user id, And returns Reporting person name for the respected
     * user
     * *****************************************************************************
     */
    public int getReportingPersonIDByUserId(int userId) throws ServiceLocatorException {

        int reporting_personId = 0;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        System.out.println("userid is" + userId);
        //String queryString = "SELECT CONCAT_WS(' ',first_name,last_name) AS name FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id = "+userId+")";
        String queryString = "SELECT reports_to FROM usr_miscellaneous WHERE usr_id=" + userId;
        //String queryString1="SELECT usr_id FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id = "+userId+")";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                //reporting_person=put(resultSet.getInt("task_relatedto_id"), resultSet.getString("task_relatedto_name"));
                reporting_personId = resultSet.getInt("reports_to");


            }
            // reporting_person=  reporting_person.concat(reporting);

            //reporting_person=reporting_person.concat(reporting);
            System.out.println("reporting person is" + reporting_personId);
        } catch (SQLException ex) {
            System.out.println("getReportingPersonByUserId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("reporting person is-->" + reporting_personId);
        return reporting_personId;

    }

//To get modified person added by divya
    public String getModifiedPersonByUserId(int userId) throws ServiceLocatorException {

        String modified_person = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        System.out.println("userid is" + userId);
        //String queryString = "SELECT CONCAT_WS(' ',first_name,last_name) AS name FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id = "+userId+")";
        String queryString = "SELECT CONCAT_WS(' ',first_name,last_name) AS name FROM users WHERE usr_id=" + userId;
        //String queryString1="SELECT usr_id FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id = "+userId+")";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                //reporting_person=put(resultSet.getInt("task_relatedto_id"), resultSet.getString("task_relatedto_name"));
                modified_person = resultSet.getString("name");


            }
            // reporting_person=  reporting_person.concat(reporting);

            //reporting_person=reporting_person.concat(reporting);
            System.out.println("reporting person is" + modified_person);
        } catch (SQLException ex) {
            System.out.println("getReportingPersonByUserId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("reporting person is-->" + modified_person);
        return modified_person;

    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:23 PM IST
     * Author:RAmakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting attachment details based on the task id
     * *****************************************************************************
     */
    public List getAttachmentDetails(HttpServletRequest httpServletRequest, TaskHandlerAction taskHandlerAction) throws ServiceLocatorException {


        ArrayList<TasksVTO> fileslist = new ArrayList<TasksVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {
            //queryString = "SELECT t.task_id,t.task_name,t.task_created_date,t.task_comments,t.task_status,u.usr_id FROM task_list t LEFT OUTER JOIN users u  ON(t.task_created_by=u.usr_id) WHERE 1=1 and task_status LIKE 'Active' ";
            queryString = "SELECT id,attachment_name,attachment_path FROM task_attachments WHERE task_id=" + taskHandlerAction.getTaskid() + " AND STATUS='Active'";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                TasksVTO tasksVTO = new TasksVTO();
                tasksVTO.setAttachmentId(resultSet.getString("id"));
                tasksVTO.setAttachmentName(resultSet.getString("attachment_name"));
                tasksVTO.setAttachmentPath(resultSet.getString("attachment_path"));
                fileslist.add(tasksVTO);
            }

            System.out.println("queryString-->" + fileslist);

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return fileslist;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:23 PM IST
     * Author:RAmakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting attachment data based on the attachment id
     * *****************************************************************************
     */
    public String getAttachmentLocation(int id) throws ServiceLocatorException {


        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "", attachmentLocation = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {
            System.out.println("in DownloadAction");
            //queryString = "SELECT t.task_id,t.task_name,t.task_created_date,t.task_comments,t.task_status,u.usr_id FROM task_list t LEFT OUTER JOIN users u  ON(t.task_created_by=u.usr_id) WHERE 1=1 and task_status LIKE 'Active' ";
            queryString = "SELECT id,attachment_name,attachment_path FROM task_attachments WHERE id=" + id + "";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                attachmentLocation = resultSet.getString("attachment_path");
            }

            System.out.println("queryString-->" + attachmentLocation);

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return attachmentLocation;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:16 PM IST Author
     * :ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting task type based on task id
     * *****************************************************************************
     */
    public Map getTaskTypeById(TaskHandlerAction taskHandlerAction) throws ServiceLocatorException {
        Map map = new HashMap();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //String querystrings = "SELECT task_types_id,task_type_name FROM task_list  JOIN  lk_task_types ON(task_related_to=task_rel_toId) WHERE task_id=?";
        String querystrings = "SELECT p.proj_name,p.project_id FROM acc_projects p "
                + "LEFT OUTER JOIN prj_sub_prjteam t ON(t.sub_project_id=p.project_id) "
                + "WHERE t.usr_id=?";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(querystrings);
            preparedStatement.setInt(1, taskHandlerAction.getUserSessionId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getInt("project_id"), resultSet.getString("proj_name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return map;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:23 PM IST
     * Author:Praveenkumar<pkatru@miraclesoft.com>
     *
     * ForUse : getting primary assigned to based on task id
     * *****************************************************************************
     */
    public Map getPrimaryAssignTo(int taskId) throws ServiceLocatorException {
        Map map = new HashMap();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String string = "SELECT ur.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM task_list t  JOIN  usr_roles ur ON (t.task_related_to=ur.role_id) JOIN users u ON(ur.usr_id=u.usr_id) WHERE t.task_id=?";

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(string);
            preparedStatement.setInt(1, taskId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return map;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 28, 2015, 11:23 PM IST
     * Author:Praveenkumar<pkatru@miraclesoft.com>
     *
     * ForUse : getting reporters Email Id by using user Id
     * *****************************************************************************
     */
    public List getReportsTo(int usrId) {
        ArrayList reportsTo = new ArrayList();
        ArrayList emailIds = new ArrayList();
        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        try {
            queryString = "SELECT u.usr_id, u.reports_to FROM usr_miscellaneous m LEFT OUTER JOIN usr_miscellaneous u ON m.reports_to=u.usr_id WHERE m.usr_id=? ";

            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, usrId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reportsTo.add(resultSet.getInt("usr_id"));
                reportsTo.add(resultSet.getInt("reports_to"));
            }
            emailIds = (ArrayList) getReportingEmailId(reportsTo);
            System.out.println("queryString-->");

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        System.out.println(reportsTo);
        return emailIds;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 28, 2015, 11:23 PM IST
     * Author:Praveenkumar<pkatru@miraclesoft.com>
     *
     * ForUse : getting email id by passing list of user id's
     * *****************************************************************************
     */
    public List getReportingEmailId(List listUserIds) {
        Connection connection = null;
        ArrayList emailId = new ArrayList();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String ids = "";
        ids = listUserIds.get(0).toString() + "," + listUserIds.get(1).toString();
        System.out.println(ids);
        try {
            //queryString = "SELECT t.task_id,t.task_name,t.task_created_date,t.task_comments,t.task_status,u.usr_id FROM task_list t LEFT OUTER JOIN users u  ON(t.task_created_by=u.usr_id) WHERE 1=1 and task_status LIKE 'Active' ";
            queryString = "SELECT email1 from users where usr_id in (" + ids + ")";
            System.out.println(queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                emailId.add(resultSet.getString("email1"));
            }

            System.out.println("queryString-->");

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {

                sqle.printStackTrace();
            }
        }
        return emailId;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 28, 2015, 11:23 PM IST Author:divya
     * gandreti<dgandreti@miralcesoft.com>
     *
     * ForUse : getting Email Id by using user Id
     * *****************************************************************************
     */
    public String getEmailIdbyuser(int userid) {
        String email = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        try {
            //queryString = "SELECT t.task_id,t.task_name,t.task_created_date,t.task_comments,t.task_status,u.usr_id FROM task_list t LEFT OUTER JOIN users u  ON(t.task_created_by=u.usr_id) WHERE 1=1 and task_status LIKE 'Active' ";
            queryString = "SELECT email1 from users where usr_id= " + userid;
            System.out.println(queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                email = resultSet.getString("email1");
            }

            System.out.println("queryString-->");

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {

                sqle.printStackTrace();
            }
        }




        return email;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 28, 2015, 11:23 PM IST Author:divya
     * gandreti<dgandreti@miralcesoft.com>
     *
     * ForUse : getting Email Id by using user Id
     * *****************************************************************************
     */
    public int getUserIdByLeaveId(int leave_id) {
        int usr_id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        try {
            //queryString = "SELECT t.task_id,t.task_name,t.task_created_date,t.task_comments,t.task_status,u.usr_id FROM task_list t LEFT OUTER JOIN users u  ON(t.task_created_by=u.usr_id) WHERE 1=1 and task_status LIKE 'Active' ";
            queryString = "SELECT usr_id from usr_leaves where leave_id= " + leave_id;
            System.out.println(queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                usr_id = resultSet.getInt("usr_id");
            }

            System.out.println("queryString-->");

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {

                sqle.printStackTrace();
            }
        }


        return usr_id;

    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting primary assigned to based on task id
     * *****************************************************************************
     */
    public String getEmailId(int userId) throws ServiceLocatorException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String mailId = "";

        String string = "SELECT email1 from users WHERE usr_id=?";

        try {
            //System.out.println("in DSDP EMAILID METHOD  " + userId);
            //System.out.println("in DSDP query  " + string);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(string);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mailId = resultSet.getString("email1");
            }
            System.out.println("================>EmailId:::::" + mailId);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return mailId;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting primary assigned to based on task id
     * *****************************************************************************
     */
    public String getStatusById(int statusId) throws ServiceLocatorException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String status = "";

        String string = "SELECT task_status_name from lk_task_status WHERE id=?";

        try {
            System.out.println("in DSDP EMAILID METHOD  " + statusId);
            System.out.println("in DSDP query  " + string);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(string);
            preparedStatement.setInt(1, statusId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status = resultSet.getString("task_status_name");
            }
            System.out.println("================>status:::::" + status);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return status;
    }

    /*
     * Author :Kiran Arogya
     * Date   :Apr 29 2015
     * Method :getUserIdAndStatusByEmail() 
     */
    public String getFirstnameandLastnameByEmail(String email) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String name = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        //String queryString = "Select Id,ProjectName,ProjectType from tblProjects where Id ="+projectId;
        //String queryString = "SELECT usr_id,cur_status FROM users WHERE login_id LIKE '"+email.trim()+"'";
        String queryString = "SELECT CONCAT(first_name,' ',last_name) AS NAME,usr_id  FROM users WHERE email1='" + email.trim() + "'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                name = resultSet.getString("NAME");
                usr_Id = resultSet.getInt("usr_id");
                isRecordExists = 1;
            }
            if (isRecordExists == 1) {
                resultString = name;
            } else {
                resultString = "NoRecordExists";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    public Map getCountryNames() throws ServiceLocatorException {

        Map countryNameMap = new LinkedHashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select id, country from lk_country ORDER BY country ASC";
        System.out.println("queryString=====>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                countryNameMap.put(resultSet.getInt("id"), resultSet.getString("country"));
            }
        } catch (SQLException ex) {
            System.out.println("getCountryNames method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("departmentNameMap-->" + countryNameMap);
        return countryNameMap;

    }

    /**
     * *****************************************************************************
     * Date : May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting vendor Types
     * *****************************************************************************
     */
    public Map getVendorType() throws ServiceLocatorException {

        Map vendorTypeMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select id,acc_type_name from lk_acc_type";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                vendorTypeMap.put(resultSet.getInt("id"), resultSet.getString("acc_type_name"));
            }
        } catch (SQLException ex) {
            // System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //  System.out.println("tasksStatusMap-->" + tasksStatusMap);
        return vendorTypeMap;

    }

    /**
     * *****************************************************************************
     * Date : May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting industry type Types
     * *****************************************************************************
     */
    public Map getIndystryTypes() throws ServiceLocatorException {
        // System.out.println("im in getIndystryTypes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Map industryList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select id,acc_industry_name from lk_acc_industry_type where status='Active'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                industryList.put(resultSet.getInt("id"), resultSet.getString("acc_industry_name"));
            }
            //  System.out.println(industryList.toString());
        } catch (SQLException ex) {
            // System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //  System.out.println("tasksStatusMap-->" + tasksStatusMap);
        return industryList;

    }

    /**
     * *************************************
     *
     * @getOrganizationByOrgId() This method is used to set the organization
     * name in add consultant field addConsultant.jsp
     * @Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * @Created Date:04/29/2015
     *
     **************************************
     */
    public Map getOrganizationByOrgId(int orgId) throws ServiceLocatorException {

        Map organizationNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT a.account_id, a.account_name FROM accounts a, org_rel o WHERE a.account_id=o.related_org_id AND o.STATUS='active' AND o.org_id=" + orgId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {

                organizationNameMap.put(resultSet.getInt("a.account_id"), resultSet.getString("a.account_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getOrganizationByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("OrganizationNameMap-->" + organizationNameMap);
        return organizationNameMap;

    }

    /**
     * *************************************
     *
     * @getIndustryName() This method is used to set the industry name in add
     * consultant field addConsultant.jsp
     * @Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * @Created Date:04/29/2015
     *
     **************************************
     */
    public Map getIndustryName() throws ServiceLocatorException {

        Map industryNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id, acc_industry_name FROM lk_acc_industry_type WHERE STATUS='active'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {

                industryNameMap.put(resultSet.getInt("id"), resultSet.getString("acc_industry_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getIndustryName method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("IndustryNameMap-->" + industryNameMap);
        return industryNameMap;

    }

    public Map getYearsOfExp() throws ServiceLocatorException {

        Map ExperienceMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id,exp_years FROM lk_years_of_exp";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                ExperienceMap.put(resultSet.getInt("id"), resultSet.getString("exp_years"));
            }
        } catch (SQLException ex) {
            System.out.println("getDepartmentNameByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("departmentNameMap-->" + ExperienceMap);
        return ExperienceMap;

    }

    public Map getNameByOrgId(int org_id) throws ServiceLocatorException {

        Map EmployeeNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT usr_id,CONCAT_WS(' ',first_name,last_name) AS name1 FROM users WHERE org_id=" + org_id;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                EmployeeNameMap.put(resultSet.getInt("usr_id"), resultSet.getString("name1"));
            }
        } catch (SQLException ex) {
            System.out.println("getDepartmentNameByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("departmentNameMap-->" + EmployeeNameMap);
        return EmployeeNameMap;

    }

    /**
     * *****************************************************************************
     * ForUSE :getVendorTypes() getting vendor types return map object Date: May
     * 5, 2015, 11:23 PM IST Author:praveen kumar<pkatru@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getVendorTierTypes() throws ServiceLocatorException {
        // System.out.println("im in getIndystryTypes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Map industryList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select id,vendor_type from lk_vendor_type";
        System.out.println("in dssp tier types");
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                industryList.put(resultSet.getInt("id"), resultSet.getString("vendor_type"));
            }
            //  System.out.println(industryList.toString());
        } catch (SQLException ex) {
            // System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //  System.out.println("tasksStatusMap-->" + tasksStatusMap);
        return industryList;

    }

    /**
     * *****************************************************************************
     * ForUSE :getStateNameById() getting vendor types return map object Date:
     * May 5, 2015, 11:23 PM IST Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getStateNameById(String stateId) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String name = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT NAME FROM lk_states WHERE id=" + stateId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                name = resultSet.getString("NAME");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return name;
    }

    /**
     * *****************************************************************************
     * ForUSE :getFnameandLnamebyStringId() getting vendor types return map
     * object Date: May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getFnameandLnamebyStringId(String userId) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String user_name = "";
        String status = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT CONCAT(first_name,' ',last_name) AS NAME FROM users WHERE usr_id=" + userId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                user_name = resultSet.getString("NAME");
                isRecordExists = 1;
            }
            if (isRecordExists == 1) {
                resultString = user_name;
            } else {
                resultString = "";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {

                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    /**
     * *****************************************************************************
     * ForUSE :getEmailPhoneDetails() getting vendor types return map object
     * Date: May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getEmailPhoneDetails(int userId) throws ServiceLocatorException {
        System.out.println("%%%%%%%%%%%%% ENTERED INTO THE DSDP %%%%%%%%>>>>>>>" + userId);
        String resultString = "";
        int usr_Id = 0;
        String details = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT email1,phone1 FROM users WHERE usr_id=" + userId;
        System.out.println("queryString---------->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                details += resultSet.getString("email1") + "|" + resultSet.getString("phone1");
            }
            System.out.println("Result in dsdp------->" + details);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return details;
    }

    public Map getRecruitmentDeptNames(int org_id) throws ServiceLocatorException {

        Map EmployeeNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT usr_id,CONCAT(first_name,'.',last_name) AS NAMES FROM users  WHERE org_id=" + org_id;
        System.out.println("WWWWWWWWWWWWWWWWW===========>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                EmployeeNameMap.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            System.out.println("getDepartmentNameByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("departmentNameMap-->" + EmployeeNameMap);
        return EmployeeNameMap;

    }

    /**
     * *****************************************************************************
     * ForUSE :isVendor() getting vendor is vendor or not Date: May 5, 2015,
     * 11:23 PM IST Author:praveen kumar<pkatru@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public boolean isVendor(int acc_id) throws ServiceLocatorException {
        // System.out.println("im in getIndystryTypes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Connection connection = null;
        boolean isvendor = false;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select * from org_rel where acc_type=5 and related_org_id=" + acc_id;
        //System.out.println("in dssp tier types");
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                isvendor = true;
            }
            //  System.out.println(industryList.toString());
        } catch (SQLException ex) {
            // System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //  System.out.println("tasksStatusMap-->" + tasksStatusMap);
        return isvendor;

    }

    /**
     * *****************************************************************************
     * ForUSE :getSalesTeam() getting sales team members Date: May 5, 2015,
     * 11:23 PM IST Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getSalesTeam(int vendorId) throws ServiceLocatorException {
        Map salesTeamList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_miscellaneous m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) WHERE m.dept_id=7";
        String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
                + "FROM usr_roles m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) "
                + "WHERE m.role_id=3 AND m.usr_id NOT IN(SELECT teamMember_Id FROM accteam WHERE acc_id=" + vendorId + ")";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                salesTeamList.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return salesTeamList;


    }

    /**
     * *****************************************************************************
     * ForUSE :getSalesTeam() getting assigned sales team members Date: May 5,
     * 2015, 11:23 PM IST Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getVendorSalesTeam(int vendorId) throws ServiceLocatorException {
        Map vendorSalesTeamList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        String queryString = "SELECT a.teamMember_Id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM accteam a LEFT OUTER JOIN users u ON(u.usr_id=a.teamMember_Id) WHERE a.acc_id=" + vendorId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                vendorSalesTeamList.put(resultSet.getInt("teamMember_Id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return vendorSalesTeamList;


    }

    /**
     * *****************************************************************************
     * Date : May 11 2015
     *
     * Author : jagan chukkala<jchukkala@miraclesoft.com>
     *
     * getAccountNameById() method can be used to get account name by using org
     * id, And returns accounts name
     * *****************************************************************************
     */
    public String getAccountNameById(int accountId) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String account_name = "";
        String status = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT account_name FROM accounts WHERE account_id=" + accountId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                account_name = resultSet.getString("account_name");
                isRecordExists = 1;
            }
            if (isRecordExists == 1) {
                resultString = account_name;
            } else {
                resultString = " - ";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {

                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    /**
     * *************************************
     *
     * @checkConsultantLoginId() This method is used to check the consultants
     * existence
     * @Author:Aklakh Ahmad
     *
     * @Created Date:05/12/2015
     *
     **************************************
     */
    public int checkConsultantLoginId(String emailId, int vendorId) throws ServiceLocatorException {

        int count = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "Select count(*) as id from users where email1 like '" + emailId + "'";
        //System.out.println("queryString-->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                count = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return count;
    }

    public Map getAllStates() throws ServiceLocatorException {

        Map stateMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id,name FROM lk_states";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                stateMap.put(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllState method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("stateMap-->" + stateMap);
        return stateMap;

    }

    /**
     * *****************************************************************************
     * ForUSE :getSalesTeam() getting sales team members Date: May 5, 2015,
     * 11:23 PM IST Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getAccTeam(int accountSearchID) throws ServiceLocatorException {
        Map salesTeamList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_miscellaneous m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) WHERE m.dept_id=7";
        String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
                + "FROM usr_roles m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) "
                + "WHERE m.role_id=3 AND m.usr_id NOT IN(SELECT teamMember_Id FROM accteam WHERE acc_id=" + accountSearchID + " )";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                salesTeamList.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return salesTeamList;


    }

    /**
     * *****************************************************************************
     * ForUSE :getSalesTeam() getting assigned sales team members Date: May 5,
     * 2015, 11:23 PM IST Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getAccSalesTeam(int accountSearchID) throws ServiceLocatorException {
        Map accSalesTeamList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        String queryString = "SELECT a.teamMember_Id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM accteam a LEFT OUTER JOIN users u ON(u.usr_id=a.teamMember_Id) WHERE a.acc_id=" + accountSearchID;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                accSalesTeamList.put(resultSet.getInt("teamMember_Id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return accSalesTeamList;


    }

    /**
     * *****************************************************************************
     * Date : May 14 2015
     *
     * Author : Divya<dgandreti@miraclesoft.com>
     *
     * getConsultantListDetailsBySearch() getting consultant list by searching.
     *
     *
     * *****************************************************************************
     */
    public String getConsult_AttachmentLocation(int consult_acc_attachment_id) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "", attachmentLocation = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {
            System.out.println("in DownloadAction");
            //queryString = "SELECT t.task_id,t.task_name,t.task_created_date,t.task_comments,t.task_status,u.usr_id FROM task_list t LEFT OUTER JOIN users u  ON(t.task_created_by=u.usr_id) WHERE 1=1 and task_status LIKE 'Active' ";
            queryString = "SELECT attachment_path,attachment_name FROM acc_rec_attachment WHERE acc_attachment_id=" + consult_acc_attachment_id + "";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {

                attachmentLocation += resultSet.getString("attachment_path") + "\\" + resultSet.getString("attachment_name");

            }

            System.out.println("queryString-->" + attachmentLocation);

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return attachmentLocation;

    }

    /**
     * *****************************************************************************
     * ForUSE :getAllAccTeam() getting sales team members Date: May 19, 2015,
     * Author:jagan chukkala<jchukkla@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getAllAccTeam(int accountSearchID) throws ServiceLocatorException {
        Map allAccTeam = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_miscellaneous m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) WHERE m.dept_id=7";
        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
        //        + "FROM usr_roles m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) "
        //        + "WHERE m.role_id=3 AND m.usr_id =(SELECT teamMember_Id FROM accteam)";
        String queryString = "SELECT ur.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_roles ur LEFT OUTER JOIN users u ON (u.usr_id=ur.usr_id)WHERE ur.role_id=3";
        System.out.println("queryString----->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                allAccTeam.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("allAccTeam-----<>" + allAccTeam);

        return allAccTeam;


    }

    /**
     * *****************************************************************************
     * ForUSE :getPrimaryAccount() to get the primary account of sales Date: May
     * 19, 2015, Author:jagan chukkala<jchukkla@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public int getPrimaryAccount(int accountSearchId) throws ServiceLocatorException {
        int primaryAccount = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        System.out.println("hello primary" + accountSearchId);

        String queryString = "SELECT teamMember_Id FROM accteam WHERE acc_id=" + accountSearchId + " AND primaryflag=1";
        System.out.println("queryString" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                primaryAccount = resultSet.getInt("teamMember_Id");
            }
            if (primaryAccount > 0) {
                System.out.println("data is there");
            } else {
                System.out.println("data is null");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        System.out.println("primary Account ---->" + primaryAccount);
        return primaryAccount;

    }

    public Map getAllVendorTeam() throws ServiceLocatorException {
        Map allVendorTeam = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_miscellaneous m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) WHERE m.dept_id=7";
        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
        //        + "FROM usr_roles m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) "
        //        + "WHERE m.role_id=3 AND m.usr_id =(SELECT teamMember_Id FROM accteam)";
        String queryString = "SELECT ur.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_roles ur LEFT OUTER JOIN users u ON (u.usr_id=ur.usr_id)WHERE ur.role_id=3";
        System.out.println("queryString----->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                allVendorTeam.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("allVendorTeam-----<>" + allVendorTeam);

        return allVendorTeam;


    }

    /**
     * *****************************************************************************
     * Date : May 19 2015
     *
     * Author : praveeb<pkatru@miraclesoft.com>
     *
     * getcountryName through country id
     *
     *
     * *****************************************************************************
     */
    public String getCountry(int id) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";

        //System.err.println(days+"Diff in Dyas...");
        try {
            System.out.println("in DownloadAction");
            queryString = " SELECT country FROM lk_country WHERE id=" + id;

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getString("country");
            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultString;

    }

    /**
     * *****************************************************************************
     * Date : May 19 2015
     *
     * Author : praveeb<pkatru@miraclesoft.com>
     *
     * getStateName through country id
     *
     *
     * *****************************************************************************
     */
    public String getStateName(int id) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";

        //System.err.println(days+"Diff in Dyas...");
        try {
            System.out.println("in DownloadAction");
            queryString = " SELECT name FROM lk_states WHERE id=" + id;

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getString("name");
            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultString;

    }

    public int updateAccountLastAccessedBy(int accId, int usrId, String accessDesc) throws ServiceLocatorException {

        //String projectType = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int c = 0;

        try {

            connection = ConnectionProvider.getInstance().getConnection();
            // System.out.println("count-->"+count);
            //String queryString1 = "update tblProjects set TotalResources="+count+" where Id="+projectId;
            String queryString1 = "UPDATE accounts SET last_access_by=" + usrId + ",last_accdesc='" + accessDesc + "',last_access_date='" + DateUtility.getInstance().getCurrentMySqlDateTime() + "' WHERE account_id=" + accId;
            //  System.out.println("queryString1-->"+queryString1);
            statement = connection.createStatement();
            c = statement.executeUpdate(queryString1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }

            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return c;
    }

    /**
     * *****************************************************************************
     * Date : May 23 2015
     *
     * Author : Aklakh ahmad<mahmad@miraclesoft.com>
     *
     * getAdmin(int usrId)
     *
     * to get the the admin role
     * *****************************************************************************
     */
    public int getAdmin(int usrId) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int adminRole = 0;

        //System.err.println(days+"Diff in Dyas...");
        try {
            System.out.println("in DownloadAction");
            queryString = "SELECT COUNT(role_id)  AS id FROM usr_roles WHERE role_id=1 AND usr_id=" + usrId;

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                adminRole = resultSet.getInt("id");
            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return adminRole;

    }

    public String getProjectMap(int userId) throws ServiceLocatorException {
        String projectDetails = "";
        List projectList = new ArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES FROM usr_miscellaneous m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) WHERE m.dept_id=7";
        //String queryString = "SELECT m.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
        //        + "FROM usr_roles m LEFT OUTER JOIN users u ON(u.usr_id=m.usr_id) "
        //        + "WHERE m.role_id=3 AND m.usr_id =(SELECT teamMember_Id FROM accteam)";
        String queryString = "SELECT acp.Project_id,proj_name FROM acc_projects acp LEFT OUTER JOIN prj_sub_prjteam psp ON (acp.project_id=psp.sub_project_id) WHERE usr_id=" + userId + " AND current_status LIKE 'Active' limit 5";
        System.out.println("queryString----->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
//                projectList.add(resultSet.getInt("Project_id"));
//                projectList.add(resultSet.getString("proj_name"));
                projectDetails = projectDetails + resultSet.getInt("Project_id") + "|" + resultSet.getString("proj_name") + "^";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("projectMap----->" + projectDetails);

        return projectDetails;


    }

    /**
     * *****************************************************************************
     * Date : May 26 2015
     *
     * Author : manikanta<meeralla@miraclesoft.com>
     *
     * ForUse: getting Department names of organization
     *
     * *****************************************************************************
     */
    public Map getDepartmentNames(int orgId) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        Map departments = new HashMap();

        //System.err.println(days+"Diff in Dyas...");
        try {
            System.out.println("in getDepartmentNames");
            queryString = " SELECT dept_id,dept_name FROM departments WHERE dept_name NOT IN('HR','Recruitments','Sales') AND org_id=" + orgId;

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                departments.put(resultSet.getInt("dept_id"), resultSet.getString("dept_name"));
            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return departments;

    }

    /**
     * *****************************************************************************
     * ForUSE :getVendorTypes() getting vendor types return map object Date: May
     * 5, 2015, 11:23 PM IST Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getAddVendorTierTypes(String id) throws ServiceLocatorException {
        System.out.println("im in getVendorTierTypes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Map industryList = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id,vendor_type FROM lk_vendor_type WHERE id NOT IN "
                + "(SELECT vendor_tier_id FROM customer_ven_rel WHERE customer_id=" + id + ")";
        System.out.println("in dssp tier types");
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                industryList.put(resultSet.getInt("id"), resultSet.getString("vendor_type"));
            }
            System.out.println(industryList.toString());
        } catch (SQLException ex) {
            // System.out.println("getTaskStatusByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //  System.out.println("tasksStatusMap-->" + tasksStatusMap);
        return industryList;

    }

    /**
     * *****************************************************************************
     * ForUSE :getFnameandLnamebyStringId() getting account types return map
     * object Date: May 5, 2015, 11:23 PM IST
     * Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getAccountType(int accId) throws ServiceLocatorException {

        String resultString = "";
        int usr_Id = 0;
        String user_name = "";
        String status = "";
        int isRecordExists = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT l.acc_type_name FROM lk_acc_type l LEFT OUTER JOIN org_rel o ON(l.id=o.acc_type) WHERE o.related_org_Id=" + accId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                user_name = resultSet.getString("acc_type_name");
                isRecordExists = 1;
            }
            if (isRecordExists == 1) {
                resultString = user_name;
            } else {
                resultString = "";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {

                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    /**
     * *****************************************************************************
     * ForUSE :getTypeOfAccount() getting account types return VC OR AC object
     * Date: May 29, 2015, 11:23 PM IST
     * Author:manikanta<meeralla@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getTypeOfAccount(int orgId) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int accType = 0;
        try {
            queryString = " SELECT acc_type FROM org_rel WHERE related_org_id=" + orgId;
            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                accType = resultSet.getInt("acc_type");
            }
            if (accType == 5) {
                resultString = "VC";
            }
            if (accType == 1) {
                resultString = "AC";
            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultString;

    }

    /**
     * *****************************************************************************
     * ForUSE :getTierOneOrg_Id()getting tier one organization id's return array
     * list Date:06,02, 2015, Author:praveen<pkatru@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public ArrayList getTierOneOrg_Id(boolean flag, int org_id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int count = 0;
        ArrayList<Integer> array = new ArrayList<Integer>();

        try {
            if (flag) {
                queryString = "SELECT DISTINCT(vendor_id) FROM customer_ven_rel WHERE is_permanent=1 AND STATUS='Active' AND customer_id=" + org_id;
            } else {
                queryString = "SELECT DISTINCT(vendor_id) FROM customer_ven_rel WHERE vendor_tier_id=1 AND STATUS='Active' AND customer_id=" + org_id;
            }
            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {

                array.add(resultSet.getInt("vendor_id"));

            }
            System.out.println("upto here very fine in dsdp");
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return array;
    }

    /**
     * *************************************
     *
     * @getPermanentStates() This method is used to get the permanent state on
     * the basic of basic country
     * @Author:Aklakh Ahmad
     *
     * @Created Date:05/15/2015
     *
     **************************************
     */
    public Map getPermanentStates(int conId) throws ServiceLocatorException {

        Map pStateMap = new LinkedHashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id,name FROM lk_states where countryId=" + conId + " ORDER BY name ASC";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                pStateMap.put(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllState method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("PstateMap-->" + pStateMap);
        return pStateMap;

    }

    /**
     * *************************************
     *
     * @getCurrentStates() This method is used to get the current state on the
     * basic of basic country
     * @Author:Aklakh Ahmad
     *
     * @Created Date:05/15/2015
     *
     **************************************
     */
    public Map getCurrentStates(int cId) throws ServiceLocatorException {

        Map cStateMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id,name FROM lk_states where countryId=" + cId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                cStateMap.put(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllState method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("CstateMap-->" + cStateMap);
        return cStateMap;

    }

    /**
     * *************************************
     *
     * @getPreferredStates() This method is used to get the preferred state on
     * the basic of basic country
     * @Author:Aklakh Ahmad
     *
     * @Created Date:05/15/2015
     *
     **************************************
     */
    public Map getPreferredStates(int countryId) throws ServiceLocatorException {

        Map stateMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT id,name FROM lk_states where countryId=" + countryId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                stateMap.put(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllState method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("stateMap-->" + stateMap);
        return stateMap;

    }

    public String getOrganizationName(int aInt) throws ServiceLocatorException {
        String orgName = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "SELECT account_name FROM accounts WHERE account_id=" + aInt;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                orgName = resultSet.getString("account_name");
            }
        } catch (SQLException ex) {
            System.out.println("getAllState method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return orgName;
    }

    /**
     * *****************************************************************************
     * ForUSE :getMailIdsOfVendorManager()getting tier one organization id's
     * return array list Date:06,02, 2015,
     * Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getMailIdsOfVendorManager() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String resultStr = "";
        int count = 0;

        try {
            queryString = "SELECT u.email1 FROM users u "
                    + "LEFT OUTER JOIN usr_miscellaneous um ON(um.usr_id=u.usr_id) "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=u.org_id) "
                    + "WHERE (um.is_team_lead=1 OR um.is_manager=1) "
                    + "AND o.acc_type=5 AND o.type_of_vendor=1";
            System.out.println("queryString--In DSDP getMailIdsOfVendorManager>>>>" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {

                resultString += resultSet.getString("email1") + ",";

            }
            if (null != resultString && resultString.length() > 0) {
                int endIndex = resultString.lastIndexOf(",");
                if (endIndex != -1) {
                    resultStr = resultString.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
                }
            }
            System.out.println("getMailIdsOfVendorManager>>>>>>>" + resultStr);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultStr;
    }

    /**
     * *****************************************************************************
     * ForUSE :getMailIdsOfVendorManager()getting tier one organization id's
     * return array list Date:06,02, 2015,
     * Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public RequirementVTO setRequirementDetails(String reqId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String resultStr = "";
        int count = 0;
        RequirementVTO requirementVTO = new RequirementVTO();

        try {
            queryString = "SELECT req_name,req_desc,req_st_date,req_tr_date,no_of_resources FROM acc_requirements WHERE requirement_id=" + reqId;
            System.out.println("queryString--In DSDP setRequirementDetails>>>>>" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>After execute query in dsdp for setReqDetailsssssssssssssssssssssssssssssssssssssssss");
            while (resultSet.next()) {
                System.out.println("VALUEW IN WHILE>>>>>>>>>>>>>>>" + resultSet.getString("req_name") + "     " + resultSet.getString("req_desc") + " " + resultSet.getString("no_of_resources") + " " + resultSet.getString("req_st_date") + "  " + resultSet.getDate("req_tr_date"));
                requirementVTO.setReqName(resultSet.getString("req_name"));
                requirementVTO.setReqDesc(resultSet.getString("req_desc"));
                System.out.println("first two are ok");
                requirementVTO.setReqStartDate(com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("req_st_date")));
                requirementVTO.setReqEndDate(com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("req_tr_date")));
                requirementVTO.setReqResources(resultSet.getString("no_of_resources"));
                System.out.println("AFTER SETTING VALUES IN WHILE");
            }
//            if (null != resultString && resultString.length() > 0) {
//                int endIndex = resultString.lastIndexOf(",");
//                if (endIndex != -1) {
//                    resultStr = resultString.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
//                }
//            }
            System.out.println("setRequirementDetails>>>>>>>" + resultStr);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return requirementVTO;
    }

    /**
     * *****************************************************************************
     * ForUSE :getMailIdsOfVendorManager()getting tier one organization id's
     * return array list Date:06,02, 2015,
     * Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getMailIdsOfVendorManagerAndLeads(String vendorIdList) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String resultStr = "";
        int count = 0;

        try {
            queryString = "SELECT u.email1 FROM users u "
                    + "LEFT OUTER JOIN usr_miscellaneous um ON(um.usr_id=u.usr_id) "
                    + "WHERE (um.is_team_lead=1 OR um.is_manager=1) "
                    + "AND u.cur_status='Active' "
                    + "AND u.org_id IN(" + vendorIdList + ")";
            System.out.println("queryString--In DSDP getMailIdsOfVendorManagerLeads>>>>" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {

                resultString += resultSet.getString("email1") + ",";

            }
            if (null != resultString && resultString.length() > 0) {
                int endIndex = resultString.lastIndexOf(",");
                if (endIndex != -1) {
                    resultStr = resultString.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
                }
            }
            System.out.println("getMailIdsOfVendorManager>>>>>>>" + resultStr);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultStr;
    }

    /**
     * *****************************************************************************
     * ForUSE :getMailIdsOfVendorManager()getting tier one organization id's
     * return array list Date:06,02, 2015,
     * Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public void getMailIdsOfConAndEmp(RecruitmentAction recruitmentAction) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String resultStr = "";
        int count = 0;

        try {
            queryString = "SELECT cd.job_title,CONCAT(c.first_name,'.',c.last_name)AS NAME,c.email1 AS conEmail,"
                    + "u.email1 AS empEmail,cd.consultant_skills,ct.scheduled_date,ct.scheduled_time,"
                    + "ct.forwarded_by,ar.req_name,ct.forwarded_to_name,ct.review_type,ct.interview_lacation,usr.email1 AS empEmail2 "
                    + "FROM users c "
                    + "LEFT OUTER JOIN con_techreview ct ON(ct.consultant_id=c.usr_id) "
                    + "LEFT OUTER JOIN users u ON(u.usr_id=ct.forwarded_to) "
                    + "LEFT OUTER JOIN users usr ON(usr.usr_id=ct.forwarded_to1)"
                    + "LEFT OUTER JOIN usr_details cd ON(cd.usr_id=c.usr_id) "
                    + "LEFT OUTER JOIN acc_requirements ar ON(ar.requirement_id=ct.req_id)"
                    + "WHERE ct.consultant_id=" + recruitmentAction.getConsult_id() + " "
                    + "AND ct.req_id=" + recruitmentAction.getRequirementId() + " "
                    + "AND ct.review_type='" + recruitmentAction.getInterviewType() + "'";

            System.out.println("queryString--In DSDP getMailIdsOfVendorManager>>>>" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                recruitmentAction.setEmpEmail2(resultSet.getString("empEmail2"));
                recruitmentAction.setInterviewType(resultSet.getString("review_type"));
                recruitmentAction.setInterviewLocation(resultSet.getString("interview_lacation"));
                recruitmentAction.setForwardedToName(resultSet.getString("forwarded_to_name"));
                recruitmentAction.setReqName(resultSet.getString("req_name"));
                recruitmentAction.setConsult_jobTitle(resultSet.getString("job_title"));
                recruitmentAction.setConsult_name(resultSet.getString("NAME"));
                recruitmentAction.setConEmail(resultSet.getString("conEmail"));
                recruitmentAction.setEmpEmail(resultSet.getString("empEmail"));
                recruitmentAction.setConSkills(resultSet.getString("consultant_skills"));
                recruitmentAction.setReviewDate(com.mss.msp.util.DateUtility.getInstance().convertDateYMDtoMDY(resultSet.getString("scheduled_date")));
                recruitmentAction.setReviewTime(resultSet.getString("scheduled_time"));
                recruitmentAction.setForwardedByName(this.getFnameandLnamebyStringId(resultSet.getString("forwarded_by")));

            }
            if (null != resultString && resultString.length() > 0) {
                int endIndex = resultString.lastIndexOf(",");
                if (endIndex != -1) {
                    resultStr = resultString.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
                }
            }
            System.out.println("getMailIdsOfConAndEmp>>>>>>>" + recruitmentAction.getConEmail() + "  " + recruitmentAction.getEmpEmail());
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        //return resultStr;
    }

    /**
     * *****************************************************************************
     * Date : June 10 2015
     *
     * Author : praveeb<pkatru@miraclesoft.com>
     *
     *
     *
     *
     * *****************************************************************************
     */
    public Map getManagerAndDirectersByOrgID(int org_id, int projectId) throws ServiceLocatorException {

        Map EmployeeNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        //String queryString = "SELECT usr_id,CONCAT(first_name,'.',last_name) AS NAMES FROM users  WHERE org_id=" + org_id;
        String queryString = "SELECT p.usr_id,CONCAT_WS(' ',u.first_name,u.last_name) AS NAMES FROM Project_team p LEFT OUTER JOIN users u ON(p.usr_id=u.usr_id) WHERE  (account_id=" + org_id + " AND project_id=" + projectId + ") and (designation='Di' OR designation='M')";
        System.out.println("WWWWWWWWWWWWWWWWW===========>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                EmployeeNameMap.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (SQLException ex) {
            System.out.println("getDepartmentNameByOrgId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("departmentNameMap-->" + EmployeeNameMap);
        return EmployeeNameMap;

    }

    /**
     * *****************************************************************************
     * ForUSE :getReqNameById()getting requirement name by id return array list
     * Date:06,02, 2015, Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getReqNameById(int reqId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String resultStr = "";
        int count = 0;

        try {
            queryString = "SELECT req_name FROM acc_requirements WHERE requirement_id=" + reqId;
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {

                resultString = resultSet.getString("req_name");

            }

            System.out.println("getReqNameById>>>>>>>" + resultString);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * *****************************************************************************
     * ForUSE :getConsultNameById()getting consult name by id return array list
     * Date:06,02, 2015, Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public String getConsultNameById(int conId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String resultStr = "";
        int count = 0;

        try {
            queryString = "SELECT CONCAT(first_name,'.',last_name) as Name FROM users WHERE usr_id=" + conId;
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>query>>>>>" + queryString);
            while (resultSet.next()) {

                resultString = resultSet.getString("Name");

            }

            System.out.println("getReqNameById>>>>>>>" + resultString);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * *****************************************************************************
     * ForUSE :getsubProject() getting the sub project of particular project
     * Date: June 12, 2015, Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getSubProject(int projectID, int userID) throws ServiceLocatorException {
        Map allSubProject = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        System.out.println("====== Sub project =======");
        //String queryString = "SELECT project_id, proj_name FROM acc_projects WHERE proj_type='SP' AND parent_project_id=" + projectID;
        String queryString = "SELECT project_id, proj_name FROM acc_projects WHERE proj_type='SP' AND parent_project_id=" + projectID + "  AND project_id NOT IN(SELECT ap.project_id FROM acc_projects ap LEFT OUTER JOIN prj_sub_prjteam sp ON(ap.parent_project_id=sp.project_id ) WHERE ap.project_id=sp.sub_project_id AND ap.proj_type='SP' AND sp.usr_id=" + userID + " AND sp.project_id=" + projectID + ")";
        System.out.println("queryString----->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                allSubProject.put(resultSet.getInt("project_id"), resultSet.getString("proj_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("allSubProject-----<>" + allSubProject);

        return allSubProject;


    }

    /**
     * *****************************************************************************
     * ForUSE :getAssignedSubProject() getting the sub project of particular
     * project Date: June 12, 2015, Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * *****************************************************************************
     */
    public Map getAssignedSubProject(int projectID, int userID) throws ServiceLocatorException {
        Map assignSubProject = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        String queryString = "SELECT ap.project_id, ap.proj_name "
                + "FROM acc_projects ap LEFT OUTER JOIN prj_sub_prjteam sp ON(ap.parent_project_id=sp.project_id )"
                + "WHERE ap.project_id=sp.sub_project_id AND ap.proj_type='SP' AND sp.usr_id=" + userID + " AND sp.project_id=" + projectID;
        System.out.println("queryString----->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                assignSubProject.put(resultSet.getInt("project_id"), resultSet.getString("proj_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("allSubProject-----<>" + assignSubProject);

        return assignSubProject;


    }

    /**
     * *************************************
     *
     * @getReportToPersonByOrgId() This method is used to set the department
     * name in employee search field employeeSearch.jsp
     * @Author:vinod<vsiram@miraclesoft.com>
     *
     * @Created Date:06/19/2015
     *
     **************************************
     */
    public Map getDesignation() throws ServiceLocatorException {

        Map departmentNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT title_id,title_name FROM title WHERE STATUS='Active' ORDER BY rank ASC";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                departmentNameMap.put(resultSet.getInt("title_id"), resultSet.getString("title_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getDesignation method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("Designation-->" + departmentNameMap);
        return departmentNameMap;

    }

    /**
     * *************************************
     *
     * @getContactPersonsByProjectIdHeigerDesignationId() This method is used to
     * set the set designations
     *
     * @Author:pravee<pkatru@miraclesoft.com>
     *
     * @Created Date:06/22/2015
     *
     **************************************
     */
    public Map getContactPersonsByProjectIdHeigerDesignationId(int projectID, int designation, int usr_id) throws ServiceLocatorException {
        Map departmentNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        System.out.println("in dsdp======= project id" + projectID);
        System.out.println("in dsdp======= designation id" + designation);
        System.out.println("in dsdp======= usr Id" + usr_id);

        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT pt.usr_id,CONCAT (first_name,'.',last_name) names FROM Project_team pt JOIN users u ON (pt.usr_id=u.usr_id) WHERE pt.designation IN (13,3,4,5,6) AND project_id=" + projectID + " AND current_status='Active' AND pt.usr_id NOT IN(" + usr_id + ")";
        System.out.println("query=====" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                departmentNameMap.put(resultSet.getInt("usr_id"), resultSet.getString("names"));
            }
        } catch (SQLException ex) {
            System.out.println("getDesignation method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("Designation-->" + departmentNameMap);
        return departmentNameMap;
    }

    /**
     * *************************************
     *
     * @getDesignationId() get designation id trough project id and usr id
     *
     *
     * @Author:pravee<pkatru@miraclesoft.com>
     *
     * @Created Date:06/22/2015
     *
     **************************************
     */
    private int getDesignationId(int usr_id, int projectId) throws ServiceLocatorException {
        Map departmentNameMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT designation FROM Project_team WHERE usr_id=" + usr_id + " AND project_id=" + projectId;
        System.out.println("query=====" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                id = resultSet.getInt("designation");
            }
        } catch (SQLException ex) {
            System.out.println("getDesignation method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return id;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 16, 2015, 8:30 PM IST
     *
     * Author :RamaKrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting TeamMembers based on userId on userId and return map
     * object
     * *****************************************************************************
     */
    public String getTeamMembers(int reportsTo) throws ServiceLocatorException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map myTeamMembers = new TreeMap();
        String finalTeam = "";

        try {

            queryString = "SELECT m.usr_id,m.reports_to,m.is_team_lead,m.is_manager,m.is_sbteam,m.is_PMO,m.opt_contact,u.first_name,u.last_name FROM usr_miscellaneous m LEFT OUTER JOIN users u ON (m.usr_id=u.usr_id) WHERE m.reports_to=?";
            preparedStatement = connection.prepareStatement(queryString);
            System.out.println("query==========>" + queryString);

            //myTeamMembers = getTeamMembersUpTo(reportsTo, preparedStatement);
            finalTeam = getTeamMembersUpTo(reportsTo, preparedStatement);


            //Closing Cache Checking
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //return myTeamMembers; // returning the object.
        return finalTeam;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 16, 2015, 8:30 PM IST Author :
     * :RamaKrishna<lankireddy@miraclesoft.com> ForUse : getting TeamMembers
     * under userId And return map object
     * *****************************************************************************
     */
    public String getTeamMembersUpTo(int reportsTo, java.sql.PreparedStatement theStatement) throws ServiceLocatorException {
        ResultSet resultSet = null;
        Map myTeamManagersMap = new TreeMap();
        String resultTeam = "";
        Map tempMap = new TreeMap();
        String tempTeam = "";
        int[] keys = new int[100];
        int keyCnt = 0;
        int key = 0;
        String value = null;

        try {
            //System.out.println("Main ReportsTo:" + reportsTo);
            theStatement.setInt(1, reportsTo);


            resultSet = theStatement.executeQuery();
            while (resultSet.next()) {
                key = resultSet.getInt("usr_id");
                value = resultSet.getString("first_name") + "." + resultSet.getString("last_name");
                myTeamManagersMap.put(key, value);
                resultTeam += "" + key + "#" + value + "^";
                // If the Team Member is a Manager then Get his Team Members List
                if ((resultSet.getInt("is_manager")) != 0 || (resultSet.getInt("is_team_lead")) != 0 || (resultSet.getInt("is_PMO") != 0) || (resultSet.getInt("is_sbteam") != 0)) {
                    keys[keyCnt] = key;
                    keyCnt++;
                    //  System.out.println("keyCnt--- Value"+keyCnt);

                }
            }

            for (int i = 0; i < keyCnt; i++) {
                key = keys[i];

                //tempMap = getTeamMembersUpTo(key, theStatement);
                tempTeam = getTeamMembersUpTo(key, theStatement);
                if (tempMap.size() > 0) {
                    Iterator tempIterator = tempMap.entrySet().iterator();
                    while (tempIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) tempIterator.next();
                        key = Integer.parseInt(entry.getKey().toString());
                        value = entry.getValue().toString();
                        myTeamManagersMap.put(key, value);
                        resultTeam += "" + key + "#" + value + "^";
                        entry = null;
                    }
                }
                // System.out.println("keyCnt value"+keyCnt);
                // System.out.println("i value"+i);
            }

        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        }
        //myTeamManagersMap = sortMapByValue(myTeamManagersMap);
        //return myTeamManagersMap; // returning the object.
        return resultTeam;

    } //closing the method

    /**
     * *****************************************************************************
     * Date : APRIL 16, 2015, 8:30 PM IST Author :
     * RamaKrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : sorting of map taken from Nagireddy<nseerapu@miraclesoft.com>
     * sir
     * *****************************************************************************
     */
    public < K, V extends Comparable< ? super V>> Map< K, V> sortMapByValue(final Map< K, V> mapToSort) {
        List< Map.Entry< K, V>> entries =
                new ArrayList< Map.Entry< K, V>>(mapToSort.size());

        entries.addAll(mapToSort.entrySet());

        Collections.sort(entries,
                new Comparator< Map.Entry< K, V>>() {
                    public int compare(
                            final Map.Entry< K, V> entry1,
                            final Map.Entry< K, V> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue());
                    }
                });

        Map< K, V> sortedMap = new LinkedHashMap< K, V>();

        for (Map.Entry< K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    //praveen <pkatru@miraclesoft.com>
    //06232015: Date

    public int getNoOfResourcesInProject(int projectId, String prjFlag) throws ServiceLocatorException {
        int resultInt = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        if ("Main Project".equalsIgnoreCase(prjFlag)) {
            queryString = "SELECT COUNT(usr_id) AS COUNT FROM Project_team WHERE project_id=" + projectId;
        } else {
            queryString = "SELECT COUNT(DISTINCT(usr_id)) AS COUNT FROM prj_sub_prjteam WHERE sub_project_id=" + projectId;
        }
        System.out.println("query in dsdp.." + queryString);
        try {
            try {
                connection = ConnectionProvider.getInstance().getConnection();
            } catch (ServiceLocatorException ex) {
                Logger.getLogger(DataSourceDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            }

            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultInt = resultSet.getInt("COUNT");
            }
        } catch (SQLException ex) {
            System.out.println("getDesignation method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultInt;
    }

    /**
     * *****************************************************************************
     * Date : APRIL 21, 2015, 11:16 PM IST Author
     * :ramakrishna<lankireddy@miraclesoft.com>
     *
     * ForUse : getting task type based on task id
     * *****************************************************************************
     */
    public Map getCSRTeam(TaskHandlerAction taskHandlerAction) throws ServiceLocatorException {
        Map map = new HashMap();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //String querystrings = "SELECT task_types_id,task_type_name FROM task_list  JOIN  lk_task_types ON(task_related_to=task_rel_toId) WHERE task_id=?";
        String querystrings = "SELECT CONCAT(u.first_name,'.',u.last_name) AS NAMES,u.usr_id FROM users u LEFT OUTER JOIN usr_roles r ON(r.usr_id=u.usr_id) WHERE r.role_id=1";
        System.out.println("querystrings>>>>>>>>>>>>>>>" + querystrings);
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(querystrings);
            while (resultSet.next()) {
                map.put(resultSet.getInt("usr_id"), resultSet.getString("NAMES"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return map;
    }

    /**
     * *****************************************************************************
     * Date : june 26, 2015,
     *
     * Author :praveen<pkatru@miraclesoft.com>
     *
     * ForUse : getting no of submission by requirement id and organization id
     * *****************************************************************************
     */
    public int getNoOfSubmisions(int req_id, int orgId) throws ServiceLocatorException {
        //System.out.println("getNoOfSubmisions start.");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        int resultString = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            queryString = "SELECT COUNT(createdbyOrgId) as count FROM req_con_rel  WHERE  status not like '%SOW%' and reqId=" + req_id + " AND createdbyOrgId=" + orgId;
            //  System.out.println("getNoOfSubmisions query ->"+queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getInt("count");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {

                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //System.out.println("getNoOfSubmisions end.");
        return resultString;
    }

    /**
     * *****************************************************************************
     * Date : june 26, 2015,
     *
     * Author :praveen<pkatru@miraclesoft.com>
     *
     * ForUse : getting AvgRateByOrg by requirement id and organization id
     * *****************************************************************************
     */
    public double getAvgRateByOrg(int req_id, int orgId) throws ServiceLocatorException {
        // System.out.println("getAvgRateByOrg start.");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        double resultString = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            queryString = "SELECT AVG(rate_salary) as avar FROM req_con_rel WHERE status not like '%SOW%' and reqId=" + req_id + " AND createdbyOrgId=" + orgId;
            //   System.out.println("getAvgRateByOrg query ->"+queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getInt("avar");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {

                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        //System.out.println("getAvgRateByOrg end.");
        return resultString;
    }

    /**
     * *************************************
     *
     * @customerList() This method is used to set the department name in
     * employee search field employeeSearch.jsp
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:06/19/2015
     *
     **************************************
     */
    public Map customerList(String typeOfUser, int userSessionId) throws ServiceLocatorException {

        Map customerMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";
        if (typeOfUser.equalsIgnoreCase("SA")) {
            queryString = "SELECT a.account_id,a.account_name FROM accounts a LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) WHERE o.acc_type=1";
        } else {
            queryString = "SELECT a.account_id,a.account_name "
                    + "FROM accounts a "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                    + "LEFT OUTER JOIN csrorgrel csr ON(csr.org_id=a.account_id) "
                    + "WHERE o.acc_type=1 "
                    + "AND csr.csr_id=" + userSessionId;
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                customerMap.put(resultSet.getInt("account_id"), resultSet.getString("account_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getDesignation method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("Designation-->" + customerMap);
        return customerMap;

    }

    /**
     * *****************************************************************************
     * Date : July 2 2015
     *
     * Author :Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * ForUse : getting extension of url on the basic of org_id
     * *****************************************************************************
     */
    public String getUrlExtension(int orgId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();

        String url_ext = "";

        try {

            queryString = "SELECT email_ext from siteaccess_mail_ext WHERE org_id=" + orgId;
            System.out.println("query==========>" + queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                url_ext = resultSet.getString("email_ext");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {

                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }

        return url_ext;
    }

    /**
     * *************************************
     *
     * @getVendorList() This method is used to set the department name in
     * employee search field employeeSearch.jsp
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:06/19/2015
     *
     **************************************
     */
    public Map getVendorList() throws ServiceLocatorException {

        Map customerMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT a.account_id,a.account_name FROM accounts a LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) WHERE o.acc_type=5";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                customerMap.put(resultSet.getInt("account_id"), resultSet.getString("account_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getDesignation method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("vendors-->" + customerMap);
        return customerMap;

    }

    //praveen
    public String getTypeOfUser(int userId) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        String accType = "";
        try {
            queryString = "SELECT type_of_user FROM users WHERE usr_id=" + userId;
            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getString("type_of_user");
            }


        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultString;

    }

    /**
     * *************************************
     *
     * @checkResetEmailId() This method is used to check the existence
     * @Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * @Created Date:07/15/2015
     *
     **************************************
     */
    public int checkResetEmailId(String emailId, int orgId) throws ServiceLocatorException {

        int count = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";
        if (orgId == 10001) {
            queryString = "Select count(email1) as id from users where email1 like '" + emailId + "'";
            //System.out.println("siteadimin"+queryString);
        } else {
            queryString = "Select count(email1) as id from users where email1 like '" + emailId + "'AND work_for_org=" + orgId;
            //System.out.println("remain"+queryString);
        }
        //System.out.println("queryString-->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                count = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return count;
    }

    /**
     * *************************************
     *
     * @getCsrAccountCount() This method is used to get Csr Account Count
     * @Author:Manikanta<meeralla@miraclesoft.com>
     *
     * @Created Date:07/15/2015
     *
     **************************************
     */
    public int getCsrAccountCount(int usrId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT COUNT(*) AS COUNT FROM csrorgrel WHERE status='Active' AND csr_id=" + usrId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                count = resultSet.getInt("COUNT");
            }
            System.out.println("queryString==>In getCsrAccountCount" + queryString);
        } catch (SQLException ex) {
            System.out.println("getCsrAccountCount method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return count;

    }

    /**
     * *************************************
     *
     * @customerList() This method is used to get category of the group in it.
     * @Author:Praveen<pkatru@miraclesoft.com>
     *
     * @Created Date:07/14/2015
     *
     **************************************
     */
    public Map getRequiteCategory(int grpId) throws ServiceLocatorException {
        Map customerMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select grpcategory,catname from lkusr_grpcategory  where grpid=" + grpId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                customerMap.put(resultSet.getInt("grpcategory"), resultSet.getString("catname"));
            }
        } catch (SQLException ex) {
            System.out.println("req category method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("vendors-->" + customerMap);
        return customerMap;

    }

    /**
     * *************************************
     *
     * @customerList() This method is used to set the department name in
     * employee search field employeeSearch.jsp
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:06/19/2015
     *
     **************************************
     */
    public Map getProjectList(String roleValue, int userSessionId, int orgId) throws ServiceLocatorException {

        Map projectMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";
        if (roleValue.equalsIgnoreCase("Director")) {
            queryString = "SELECT project_id,proj_name FROM acc_projects WHERE acc_id=" + orgId;
        } else {
            queryString = "SELECT project_id,proj_name FROM acc_projects WHERE created_by=" + userSessionId + " AND acc_id=" + orgId;
        }
        System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                projectMap.put(resultSet.getInt("project_id"), resultSet.getString("proj_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getProjectList method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return projectMap;

    }

    /**
     * *************************************
     *
     * @getCsrAccountCount() This method is used to get Csr Account Count
     * @Author:Manikanta<meeralla@miraclesoft.com>
     *
     * @Created Date:07/17/2015
     *
     **************************************
     */
    public String getUserNameByUserId(int userId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT concat(first_name,'.',last_name) as name FROM users WHERE usr_id=" + userId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                result = resultSet.getString("name");
            }
        } catch (SQLException ex) {
            System.out.println("getCsrAccountCount method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return result;
    }

    public Map GetProjectManagersListByOrgId(int sessionOrgId) throws ServiceLocatorException {
        Map managerMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";

        queryString = "SELECT u.usr_id, concat(first_name,'.',last_name) Names FROM users u JOIN usr_roles ur ON (ur.usr_id=u.usr_id ) WHERE ur.role_id=3 AND org_id=" + sessionOrgId;

        // System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                managerMap.put(resultSet.getInt("usr_id"), resultSet.getString("Names"));
            }
        } catch (SQLException ex) {
            System.out.println("getProjectList method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return managerMap;
    }

    public Map getRolesForAccType(String orgType) throws ServiceLocatorException {
        Map rolesMap = new HashMap();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        //  Statement statement = null;
        ResultSet resultSet = null;
        //int result = 0;
        //String resultMessage = "";
        String queryString = "";
        //StringBuffer sb = new StringBuffer();
//        System.out.println("getActionName-->" + accAuthAjaxHandlerAction.getActionName());
//        System.out.println("Status-->" + accAuthAjaxHandlerAction.getStatus());
//        System.out.println("Desc-->" + accAuthAjaxHandlerAction.getDesc());

        try {

            connection = ConnectionProvider.getInstance().getConnection();

            queryString = "SELECT `role_id`,`role_name` FROM `servicebay`.`roles` WHERE org_type='" + orgType + "' ";

            System.out.println("queryString-->" + queryString);

            preparedStatement = connection.prepareStatement(queryString);
            // preparedStatement.setInt(1, dept_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // resultMessage += resultSet.getInt("role_id") + "#" + resultSet.getString("role_name") + "^";
                rolesMap.put(resultSet.getInt("role_id"), resultSet.getString("role_name"));
            }

            //System.out.println("String-->" + sb);
        } catch (SQLException sqle) {
            throw new ServiceLocatorException(sqle);
        } finally {
            try {
                if (resultSet != null) {

                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sql) {
                //System.err.print("Error :"+sql);
            }

        }
        return rolesMap;


    }

    /**
     * *************************************
     *
     * @getHomeRedirectDetails() method is used to get Requirement details of
     * account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public Map getAllAccounts() throws ServiceLocatorException {
        Map accountsMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";

        queryString = "SELECT account_id,account_name FROM accounts";

        // System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                accountsMap.put(resultSet.getInt("account_id"), resultSet.getString("account_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllAccounts method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return accountsMap;
    }

    /**
     * *************************************
     *
     * @getAllRoles() method is used to get Requirement details of account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public Map getAllRoles(String accType) throws ServiceLocatorException {
        Map rolesMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";
        if (accType.equalsIgnoreCase("VC")) {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active'  AND org_type='V'";
        } else if (accType.equalsIgnoreCase("AC")) {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active' AND org_type='C'";
        } else {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active'";
        }
        // System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                rolesMap.put(resultSet.getInt("role_id"), resultSet.getString("role_name"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllRoles method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return rolesMap;
    }

    /**
     * *************************************
     *
     * @getAllRoles() method is used to get Requirement details of account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public String getAllRolesString(String accType) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";
        String resultString = "";
        if (accType.equalsIgnoreCase("VC")) {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active'  AND org_type='V'";
        } else if (accType.equalsIgnoreCase("AC")) {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active' AND org_type='C'";
        } else if (accType.equalsIgnoreCase("M")) {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active' AND org_type='M'";
        } else {
            queryString = "SELECT role_id,role_name FROM roles WHERE STATUS='Active'";
        }
        // System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString += resultSet.getInt("role_id") + "|" + resultSet.getString("role_name") + "^";
            }
        } catch (SQLException ex) {
            System.out.println("getAllRoles method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultString;
    }

    /**
     * *****************************************************************************
     * Date : July 23, 2015, 2:23 AM IST Author
     * :Vinodkumar<vsiram@miraclesoft.com>
     *
     * ForUse : getting organization type of relation based on orgid
     * *****************************************************************************
     */
    public String getOrganizationType(String org_id) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT type_of_relation FROM org_rel WHERE related_org_id=" + org_id;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                result = resultSet.getString("type_of_relation");
            }
        } catch (SQLException ex) {
            System.out.println("getOrganizationType method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return result;
    }

    /**
     * *****************************************************************************
     * Date : July 23, 2015, 2:23 AM IST Author
     * :Divya<dgandreti@miraclesoft.com>
     *
     * ForUse : getting organization type of relation based on orgid
     * *****************************************************************************
     */
    public int getCategoryByUserId(int usrId) {
        //   ArrayList category = new ArrayList();
        int groupid = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        try {
            queryString = "SELECT cat_type FROM usr_grouping WHERE usr_id=? ";

            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, usrId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupid = resultSet.getInt("cat_type");

            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return groupid;
    }
//aklaq

    public String getEmiltExistOrNot(String resourceType, String conEmail, int sessionOrgId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";

        queryString = "SELECT usr_id,org_id,type_of_user FROM users WHERE cur_status='Active' AND  email1  ='" + conEmail + "'";

        System.out.println(">>>>>>  getEmiltExistOrNot -->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                if ("IC".equalsIgnoreCase(resultSet.getString("type_of_user"))) {
                    result = resultSet.getString("usr_id") + "#IC";
                } else {
                    if (sessionOrgId == resultSet.getInt("org_id")) {
                        result = resultSet.getString("usr_id") + "#VC";
                    } else {
                        result = null;
                    }
                }

            }
            return result;

        } catch (SQLException ex) {
            System.out.println("getProjectList method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return result;
    }
//aklaq

    public String getIsExistConsultantByReqId(String reqId, String result) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String resultmsg = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";

        queryString = "SELECT COUNT(consultantId) COUNT FROM req_con_rel WHERE reqId=" + reqId + " AND consultantId=" + result;

        System.out.println(">>>>>>>>>>>>getIsExistConsultantByReqId --->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultmsg = resultSet.getString("count");
            }


        } catch (SQLException ex) {
            System.out.println("getProjectList method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultmsg;
    }

    public String getUserSubCategoryByUsrId(int usrId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String resultmsg = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "";

        queryString = "SELECT sub_cat FROM usr_grouping WHERE usr_id=" + usrId;

        System.out.println(">>>>>>>>>>>>getUserSubCategoryByUsrId --->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultmsg = resultSet.getString("sub_cat");
            }


        } catch (SQLException ex) {
            System.out.println("getUserSubCategoryByUsrId method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return resultmsg;
    }

    /**
     * *************************************
     *
     * @getAllRoles() method is used to get Requirement details of account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public Map getReporingByProjectId(int prjId) throws ServiceLocatorException {
        Map rolesMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT CONCAT(u.first_name,'.',u.last_name) AS NAME,pt.usr_id "
                + "FROM project_team pt "
                + "LEFT OUTER JOIN users u ON(u.usr_id=pt.usr_id) "
                + "WHERE pt.project_id=" + prjId + " "
                + "AND pt.designation IN(3,4,5,13,6) "
                + "AND pt.current_status='Active'";

        System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                rolesMap.put(resultSet.getInt("usr_id"), resultSet.getString("NAME"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllRoles method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ServiceLocatorException(ex);
            }
        }
        return rolesMap;
    }

    public Map getRequiteCategory() throws ServiceLocatorException {
        Map customerMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select id,grpname from lkusr_group";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                customerMap.put(resultSet.getInt("id"), resultSet.getString("grpname"));
            }
        } catch (SQLException ex) {
            System.out.println("req category method-->" + ex.getMessage());
            ex.printStackTrace();

        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        System.out.println("vendors-->" + customerMap);
        return customerMap;

    }

    public boolean isHeadHunterOrNot(String requirementId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        boolean flag = false;
        String queryString = "SELECT * FROM acc_requirements WHERE  tax_term='PE' AND requirement_id=" + requirementId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException ex) {
            System.out.println("req category method-->" + ex.getMessage());
            ex.printStackTrace();

        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return flag;
    }

    public int getusrIdByemailId(String emailId) throws ServiceLocatorException {

        int usr_id = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "Select usr_id from users where email1 like '" + emailId + "'";
        //System.out.println("queryString-->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                usr_id = resultSet.getInt("usr_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return usr_id;
    }

    public List getProjectTeamMembersList(int projectId) {
        ArrayList projectMembers = new ArrayList();
        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        try {
            queryString = "SELECT usr_id FROM project_team WHERE project_id=" + projectId;

            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectMembers.add(resultSet.getInt("usr_id"));

            }


        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        System.out.println(projectMembers);
        return projectMembers;
    }

    public int getUsrRoleById(int usrId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int roleId = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT role_id FROM usr_roles WHERE usr_id=" + usrId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                roleId = resultSet.getInt("role_id");
            }
            System.out.println("queryString==>In getCsrAccountCount" + queryString);
        } catch (SQLException ex) {
            System.out.println("getCsrAccountCount method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return roleId;

    }

    /**
     * *************************************
     *
     * @getSubProjectTeamMap() method is used to get Requirement details of
     * account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public Map getSubProjectTeamMap(int prjId) throws ServiceLocatorException {
        Map rolesMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT CONCAT(TRIM(u.first_name),'.',TRIM(u.last_name)) AS NAME,u.usr_id "
                + "FROM users u "
                + "LEFT OUTER JOIN prj_sub_prjteam pt ON(pt.usr_id=u.usr_id) "
                + "WHERE pt.sub_project_id=" + prjId;

        System.out.println(">>>>>>>>>>>>PROJECT LIST>>>>" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                rolesMap.put(resultSet.getInt("usr_id"), resultSet.getString("NAME"));
            }
        } catch (SQLException ex) {
            System.out.println("getAllRoles method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ServiceLocatorException(ex);
            }
        }
        return rolesMap;
    }

    /**
     * *************************************
     *
     * @doCheckEmailExistsOrNot() method is used to know user email already in
     * migration rable or not
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public int doCheckEmailExistsOrNot(int conId, int reqId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int roleId = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT m.id FROM con_or_ven_mig_rel m WHERE m.reqId=" + reqId + " AND m.consultantid=" + conId;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                roleId = resultSet.getInt("id");
            }
            System.out.println("queryString==>In getCsrAccountCount" + queryString);
        } catch (SQLException ex) {
            System.out.println("getCsrAccountCount method-->" + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                // resultSet Object Checking if it's null then close and set null
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return roleId;

    }
}
