/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.accauthajax;

import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocatorException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author miracle
 */
public class AccAuthAjaxHandlerServiceImpl implements AccAuthAjaxHandlerService {

    public String insertOrUpdateAccAuth(AccAuthAjaxHandlerAction accAuthAjaxHandlerAction) throws ServiceLocatorException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        String queryString = "";
        //StringBuffer sb = new StringBuffer();
        System.out.println("getActionName-->" + accAuthAjaxHandlerAction.getActionName());
        System.out.println("Status-->" + accAuthAjaxHandlerAction.getStatus());
        System.out.println("Desc-->" + accAuthAjaxHandlerAction.getDesc());

        try {

            connection = ConnectionProvider.getInstance().getConnection();
            if (accAuthAjaxHandlerAction.getFlag() == 0) {
                queryString = "insert into ac_action (action_name,status,description) values('" + accAuthAjaxHandlerAction.getActionName() + "'," + "'Active','" + accAuthAjaxHandlerAction.getDesc() + "')";
                System.out.println("queryString-->" + queryString);
                statement = connection.createStatement();
                result = statement.executeUpdate(queryString);

            } else {
                queryString = " update ac_action SET action_name=?,status=?,description=? WHERE action_id =" + accAuthAjaxHandlerAction.getActionId();

                //System.out.println("get edit skill details update query" + queryStringupdate);
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1, accAuthAjaxHandlerAction.getActionName());
                preparedStatement.setString(2, accAuthAjaxHandlerAction.getStatus());
                preparedStatement.setString(3, accAuthAjaxHandlerAction.getDesc());

                result = preparedStatement.executeUpdate();
            }
            if (result > 0) {
                Connection connection1 = null;
                Statement statement1 = null;
                ResultSet resultSet1 = null;
                DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
                connection1 = ConnectionProvider.getInstance().getConnection();
                String queryString1 = "SELECT `action_id`, `action_name`, `status`, `description` FROM `servicebay`.`ac_action` where status='" + accAuthAjaxHandlerAction.getStatus() + "' LIMIT 0,150  ";

                statement1 = connection1.createStatement();
                resultSet1 = statement1.executeQuery(queryString1);
                while (resultSet1.next()) {

                    resultMessage += resultSet1.getInt("action_id") + "|"
                            + resultSet1.getString("action_name") + "|"
                            + resultSet1.getString("status") + "|"
                            + resultSet1.getString("description") + "^";
                }
                System.out.println("In Result if" + queryString1);

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
        return resultMessage;

    }

    public String getRolesForAccType(AccAuthAjaxHandlerAction accAuthAjaxHandlerAction) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        String queryString = "";
        //StringBuffer sb = new StringBuffer();
//        System.out.println("getActionName-->" + accAuthAjaxHandlerAction.getActionName());
//        System.out.println("Status-->" + accAuthAjaxHandlerAction.getStatus());
//        System.out.println("Desc-->" + accAuthAjaxHandlerAction.getDesc());

        try {

            connection = ConnectionProvider.getInstance().getConnection();

            queryString = "SELECT `role_id`,`role_name` FROM `servicebay`.`roles` WHERE org_type='" + accAuthAjaxHandlerAction.getAccType() + "' ";

            System.out.println("queryString-->" + queryString);

            preparedStatement = connection.prepareStatement(queryString);
            // preparedStatement.setInt(1, dept_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultMessage += resultSet.getInt("role_id") + "#" + resultSet.getString("role_name") + "^";
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
        return resultMessage;


    }

    public String getAccountNames(AccAuthAjaxHandlerAction accAuthAjaxHandlerAction) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        String queryString = "";
        queryString = "SELECT a.account_name ,a.account_id FROM accounts a LEFT OUTER JOIN org_rel o ON(a.account_id=o.related_org_Id) WHERE o.type_of_relation='" + accAuthAjaxHandlerAction.getAccType() + "'"
                + "AND a.account_name LIKE '" + accAuthAjaxHandlerAction.getAccName() + "%'";

        try {
            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            // System.out.println("query-->getEmployeeDetails"+queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();

            int count = 0;

            sb.append("<xml version=\"1.0\">");
            sb.append("<ACCOUNTS>");
            while (resultSet.next()) {
                sb.append("<ACCOUNT><VALID>true</VALID>");

                if (resultSet.getString(1) == null || resultSet.getString(1).equals("")) {
                    sb.append("<NAME>NoRecord</NAME>");
                } else {
                    String title = resultSet.getString(1);
                    if (title.contains("&")) {
                        title = title.replace("&", "&amp;");
                    }
                    sb.append("<NAME>" + title + "</NAME>");
                }
                //sb.append("<NAME>" +resultSet.getString(1) + "</NAME>");
                sb.append("<ACCOUNTID>" + resultSet.getInt(2) + "</ACCOUNTID>");
                sb.append("</ACCOUNT>");
                isGetting = true;
                count++;
            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<ACCOUNT><VALID>false</VALID></ACCOUNT>");
            }
            sb.append("</ACCOUNTS>");
            sb.append("</xml>");
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
        return sb.toString();
    }

    public String getActionResorucesSearchResults(AccAuthAjaxHandlerAction accAuthAjaxHandlerAction) throws ServiceLocatorException {
        String resultMessage = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {


            DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
            connection = ConnectionProvider.getInstance().getConnection();
            String queryString = "SELECT a.id,a.action_id,a.STATUS,a.description,CASE a.org_id WHEN 0 THEN 'All'  ELSE account_name END AS account_name,role_name,action_name,type_of_relation FROM ac_action aa LEFT OUTER JOIN ac_resources a ON(aa.action_id=a.action_id) LEFT OUTER JOIN accounts "
                    + " ON(a.org_id=accounts.account_id) LEFT OUTER JOIN roles ON(a.usr_role_id=roles.role_id)"
                    + " LEFT OUTER JOIN org_rel ON(a.org_id=org_rel.related_org_Id) "
                    + " WHERE a.action_id=" + accAuthAjaxHandlerAction.getActionId() + "";
            if (accAuthAjaxHandlerAction.getAccType() != null || !"".equals(accAuthAjaxHandlerAction.getAccType())) {
                queryString = queryString + " and type_of_relation LIKE '" + accAuthAjaxHandlerAction.getAccType() + "%'";
            }
            if (accAuthAjaxHandlerAction.getRoles() != -1) {
                queryString = queryString + " and roles.role_id = " + accAuthAjaxHandlerAction.getRoles() + "";
            }
            if (accAuthAjaxHandlerAction.getStatus() != null) {

                if ("All".equals(accAuthAjaxHandlerAction.getStatus())) {
                    queryString = queryString + " and a.STATUS like '%%'  ";
                } else {
                    queryString = queryString + " and a.STATUS= '" + accAuthAjaxHandlerAction.getStatus() + "'  ";
                }
            }
            if (accAuthAjaxHandlerAction.getAccName() != null || !"".equals(accAuthAjaxHandlerAction.getAccName())) {
                queryString = queryString + " and account_name like '" + accAuthAjaxHandlerAction.getAccName() + "%'";
            }
//            if (accAuthAjaxHandlerAction.getAccountName() != null || !"".equals(userAjaxHandlerAction.getAccountName())) {
//                queryString1 = queryString1 + " and a.account_name LIKE '" + userAjaxHandlerAction.getAccountName() + "%'";
//            }
//LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {

                resultMessage += resultSet.getInt("a.id") + "|"
                        + resultSet.getString("account_name") + "|"
                        + resultSet.getString("role_name") + "|"
                        + resultSet.getString("a.status") + "|"
                        + resultSet.getString("a.description") + "|"
                        + resultSet.getInt("a.action_id") + "|"
                        + resultSet.getString("action_name") + "|"
                        + resultSet.getString("type_of_relation") + "^";

                /*   accauthVTO.setId(resultSet.getInt("a.id"));

                 accauthVTO.setAction_id(resultSet.getInt("a.action_id"));
                 //accauthVTO.setAction_name(resultSet.getString("action_name"));
                 accauthVTO.setAccountName(resultSet.getString("account_name"));
                 accauthVTO.setRollName(resultSet.getString("role_name"));
                 accauthVTO.setAccType(resultSet.getString("type_of_relation"));


                 accauthVTO.setStatus(resultSet.getString("a.status"));
                 accauthVTO.setDescription(resultSet.getString("a.description"));*/
            }
            System.out.println("In Result if" + queryString);


            //System.out.println("String-->" + sb);
        } catch (SQLException sqle) {
            throw new ServiceLocatorException(sqle);
        } finally {
            try {
                if (resultSet != null) {

                    resultSet.close();
                    resultSet = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sql) {
                //System.err.print("Error :"+sql);
            }

        }
        return resultMessage;
    }

    public String insertOrUpdateActionResources(AccAuthAjaxHandlerAction accAuthAjaxHandlerAction) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        String queryString = "";
        //StringBuffer sb = new StringBuffer();
        System.out.println("getActionName-->" + accAuthAjaxHandlerAction.getActionName());
        System.out.println("Status-->" + accAuthAjaxHandlerAction.getStatus());
        System.out.println("Desc-->" + accAuthAjaxHandlerAction.getDesc());

        try {

            connection = ConnectionProvider.getInstance().getConnection();
            if (accAuthAjaxHandlerAction.getFlag() == 0) {
                String status = accAuthAjaxHandlerAction.getStatus();
                queryString = "insert into ac_resources (action_id,org_id,usr_role_id,status,description,block_flag)"
                        + " values(" + accAuthAjaxHandlerAction.getActionId() + "," + accAuthAjaxHandlerAction.getOrgId() + "," + accAuthAjaxHandlerAction.getRoles() + ",'" + status + "','" + accAuthAjaxHandlerAction.getDesc() + "'," + accAuthAjaxHandlerAction.getBlockFlag()+ ")";
                System.out.println("queryString-->" + queryString);
                statement = connection.createStatement();
                result = statement.executeUpdate(queryString);
                resultMessage = "Added Successfuiiy";


            } else {
                queryString = " update ac_resources SET org_id=?,usr_role_id=?,status=?,description=?,block_flag=? WHERE id =" + accAuthAjaxHandlerAction.getId();

                //System.out.println("get edit skill details update query" + queryStringupdate);
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setInt(1, accAuthAjaxHandlerAction.getOrgId());
                preparedStatement.setInt(2, accAuthAjaxHandlerAction.getRoles());
                preparedStatement.setString(3, accAuthAjaxHandlerAction.getStatus());
                preparedStatement.setString(4, accAuthAjaxHandlerAction.getDesc());
                preparedStatement.setInt(5, accAuthAjaxHandlerAction.getBlockFlag());

                result = preparedStatement.executeUpdate();
                resultMessage = "Updated Successfuiiy";

            }


            System.out.println("In Result if" + queryString);


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
        System.out.println("resultMessage" + resultMessage);
        return resultMessage;

    }

    public String actionResourceTermination(AccAuthAjaxHandlerAction accAuthAjaxHandlerAction) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        String queryString = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
           
                queryString = " update ac_resources SET status=? WHERE id =" + accAuthAjaxHandlerAction.getId();

                //System.out.println("get edit skill details update query" + queryStringupdate);
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1, "In-Active");

                result = preparedStatement.executeUpdate();
            
            if (result > 0) {
              resultMessage="Deleted Successfully";  
//                Connection connection1 = null;
//                Statement statement1 = null;
//                ResultSet resultSet1 = null;
//               // DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
//                connection1 = ConnectionProvider.getInstance().getConnection();
//               // String queryString1 = "SELECT `action_id`, `action_name`, `status`, `description` FROM `servicebay`.`ac_action` where status='" + accAuthAjaxHandlerAction.getStatus() + "' LIMIT 0,150  ";
//                
//                String queryString1 = "SELECT a.id,a.action_id,a.STATUS,a.description,CASE a.org_id WHEN 0 THEN 'All'  ELSE account_name END AS account_name,role_name,action_name,type_of_relation FROM ac_action aa LEFT OUTER JOIN ac_resources a ON(aa.action_id=a.action_id) LEFT OUTER JOIN accounts "
//                    + " ON(a.org_id=accounts.account_id) LEFT OUTER JOIN roles ON(a.usr_role_id=roles.role_id)"
//                    + " LEFT OUTER JOIN org_rel ON(a.org_id=org_rel.related_org_Id) "
//                    + " WHERE a.action_id=" + accAuthAjaxHandlerAction.getActionId() + "";
//                statement1 = connection1.createStatement();
//                resultSet1 = statement1.executeQuery(queryString1);
//                while (resultSet1.next()) {
//
//                  resultMessage += resultSet.getInt("a.id") + "|"
//                        + resultSet.getString("account_name") + "|"
//                        + resultSet.getString("role_name") + "|"
//                        + resultSet.getString("a.status") + "|"
//                        + resultSet.getString("a.description") + "|"
//                        + resultSet.getInt("a.action_id") + "|"
//                        + resultSet.getString("action_name") + "|"
//                        + resultSet.getString("type_of_relation") + "^";
//                }
//                System.out.println("In Result if" + queryString1);
//
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
        return resultMessage;

    }
}
