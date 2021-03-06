/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.accauth;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public class AccAuthServiceImpl implements AccAuthServices {

    private Connection connection;

    public List getAccAuthrization(AccAuthAction accAuthAction, HttpServletRequest httpServletRequest) throws ServiceLocatorException {
        ArrayList<AccauthVTO> searchklist = new ArrayList<AccauthVTO>();
//        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
//        int i = 0;
//        //System.err.println(days+"Diff in Dyas...");
        try {
            queryString = "SELECT `action_id`, `action_name`, `status`, `description` FROM `servicebay`.`ac_action` where status='" + accAuthAction.getStatus() + "' LIMIT 0,150  ";
            System.out.println("queryString--->getAccAuthrization-->" + queryString);

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                AccauthVTO accauthVTO = new AccauthVTO();
                accauthVTO.setAction_id(resultSet.getInt("action_id"));
                accauthVTO.setAction_name(resultSet.getString("action_name"));
                accauthVTO.setStatus(resultSet.getString("status"));
                accauthVTO.setDescription(resultSet.getString("description"));
                //usersVTO.setPhone1(resultSet.getString("phone1"));
                searchklist.add(accauthVTO);
                System.out.println("in while" + searchklist.size());
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
        return searchklist;
    }

    public List searchAccAuthorization(AccAuthAction accAuthAction, HttpServletRequest httpServletRequest) throws ServiceLocatorException {
        ArrayList<AccauthVTO> searchklist = new ArrayList<AccauthVTO>();
//        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
//        int i = 0;
//        //System.err.println(days+"Diff in Dyas...");
        try {
            queryString = "SELECT `action_id`, `action_name`, `status`, `description` FROM `servicebay`.`ac_action` where  1=1  ";
            System.out.println("queryString--->getAccAuthrization-->" + queryString);

            if (accAuthAction.getStatus() != null) {

                if ("All".equals(accAuthAction.getStatus())) {
                    queryString = queryString + " and status like '%%'  ";
                } else {
                    queryString = queryString + " and status= '" + accAuthAction.getStatus() + "'  ";
                }
            }
            if (accAuthAction.getAction_name() != null || !"".equals(accAuthAction.getAction_name())) {

                queryString = queryString + " and  action_name LIKE '%" + accAuthAction.getAction_name() + "%' ";

            }
            queryString = queryString + " LIMIT 0,150";

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                AccauthVTO accauthVTO = new AccauthVTO();
                accauthVTO.setAction_id(resultSet.getInt("action_id"));
                accauthVTO.setAction_name(resultSet.getString("action_name"));
                accauthVTO.setStatus(resultSet.getString("status"));
                accauthVTO.setDescription(resultSet.getString("description"));
                //usersVTO.setPhone1(resultSet.getString("phone1"));
                searchklist.add(accauthVTO);
                System.out.println("in while" + searchklist.size());
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
        return searchklist;
    }

    public List searchActionResources(AccAuthAction accAuthAction, HttpServletRequest httpServletRequest) throws ServiceLocatorException {
        ArrayList<AccauthVTO> searchklist = new ArrayList<AccauthVTO>();
//        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
//        int i = 0;
//        //System.err.println(days+"Diff in Dyas...");
        try {
            queryString = "SELECT a.id,a.action_id,a.STATUS,a.description,CASE a.org_id WHEN 0 THEN 'All'  ELSE account_name END AS account_name,role_name,type_of_relation,action_name,role_id FROM ac_action aa LEFT OUTER JOIN  ac_resources a ON(aa.action_id=a.action_id) LEFT OUTER JOIN accounts "
                    + " ON(a.org_id=accounts.account_id) LEFT OUTER JOIN roles ON(a.usr_role_id=roles.role_id)"
                    + " LEFT OUTER JOIN org_rel ON(a.org_id=org_rel.related_org_Id) "
                    + " WHERE a.action_id=" + accAuthAction.getAction_id() + " AND type_of_relation = 'C' AND a.STATUS='Active'";
            System.out.println("queryString--->getAccAuthrization-->" + queryString);

//            if (accAuthAction.getStatus() != null) {
//
//                if ("All".equals(accAuthAction.getStatus())) {
//                    queryString = queryString + " and status like '%%'  ";
//                } else {
//                    queryString = queryString + " and status= '" + accAuthAction.getStatus() + "'  ";
//                }
//            }
//            if (accAuthAction.getAction_name() != null || !"".equals(accAuthAction.getAction_name())) {
//
//                queryString = queryString + " and  action_name LIKE '%" + accAuthAction.getAction_name() + "%' ";
//
//            }
            queryString = queryString + " LIMIT 0,150";

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                AccauthVTO accauthVTO = new AccauthVTO();
                accauthVTO.setId(resultSet.getInt("a.id"));

                accauthVTO.setAction_id(resultSet.getInt("a.action_id"));
                //accauthVTO.setAction_name(resultSet.getString("action_name"));
                accauthVTO.setAccountName(resultSet.getString("account_name"));
                accauthVTO.setRollName(resultSet.getString("role_name"));
                accauthVTO.setRoleId(resultSet.getInt("role_id"));
                accauthVTO.setAccType(resultSet.getString("type_of_relation"));
                accauthVTO.setAction_name(resultSet.getString("action_name"));

                accauthVTO.setStatus(resultSet.getString("a.status"));
                accauthVTO.setDescription(resultSet.getString("a.description"));
                //usersVTO.setPhone1(resultSet.getString("phone1"));
                searchklist.add(accauthVTO);
                System.out.println("in while" + searchklist.size());
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
        return searchklist;

    }
}
