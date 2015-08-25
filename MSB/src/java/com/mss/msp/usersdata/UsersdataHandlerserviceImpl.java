/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usersdata;

import com.mss.msp.security.SecurityServiceProvider;
import com.mss.msp.usr.task.TasksVTO;
import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.MailManager;
import com.mss.msp.util.Properties;
import com.mss.msp.util.ServiceLocatorException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public class UsersdataHandlerserviceImpl implements UsersdataHandlerservice {

    private Connection connection;

    public List getEmployeeSearchDetails(UsersdataHandlerAction usersdataHandlerAction, HttpServletRequest httpServletRequest) throws ServiceLocatorException {
        ArrayList<UserVTO> searchklist = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {
            queryString = "SELECT u.usr_id,u.email1,CONCAT_WS(' ',u.first_name,u.last_name) AS name,u.cur_status,u.phone1 FROM  users u LEFT OUTER JOIN usr_miscellaneous um ON (u.usr_id=um.usr_id ) WHERE org_id = " + httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID) + " AND (type_of_user !='R')  ";
            if (!"".equals(usersdataHandlerAction.getEmpLoginId())) {
                queryString = queryString + " and   u.email1 like '%" + usersdataHandlerAction.getEmpLoginId() + "%'  ";
            }
            if (!"".equals(usersdataHandlerAction.getEmpName())) {
                queryString = queryString + " and (u.first_name like '%" + usersdataHandlerAction.getEmpName() + "%' or u.last_name like '%" + usersdataHandlerAction.getEmpName() + "%') ";
            }
            if (!"-1".equals(usersdataHandlerAction.getStatus())) {
                queryString = queryString + " and u.cur_status like '%" + usersdataHandlerAction.getStatus() + "%'  ";
            }
            if (!"-1".equalsIgnoreCase(usersdataHandlerAction.getWorkPhone())) {
                queryString = queryString + " and u.phone1 like '%" + usersdataHandlerAction.getWorkPhone() + "%' ";
            }
            if (!"-1".equalsIgnoreCase(usersdataHandlerAction.getDepartmentId())) {
                queryString = queryString + "and um.dept_id= '" + usersdataHandlerAction.getDepartmentId() + "' ";
            }
            if (!"-1".equalsIgnoreCase(usersdataHandlerAction.getReportingPerson())) {
                queryString = queryString + "and um.reports_to='" + usersdataHandlerAction.getReportingPerson() + "'";
            }
            if (usersdataHandlerAction.isIs_manager()) {
                queryString = queryString + " and um.is_manager=1";
            }
            if (usersdataHandlerAction.isOpt_contact()) {
                queryString = queryString + " and  um.opt_contact = 1";
            }
            if (usersdataHandlerAction.isTeam_leader()) {
                queryString = queryString + " and um.is_team_lead =1";
            }

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                UserVTO usersVTO = new UserVTO();
                usersVTO.setEmpId(resultSet.getInt("usr_id"));
                usersVTO.setEmpLoginId(resultSet.getString("email1"));
                usersVTO.setEmpName(resultSet.getString("name"));
                usersVTO.setCur_status(resultSet.getString("cur_status"));
                usersVTO.setPhone1(resultSet.getString("phone1"));
                searchklist.add(usersVTO);
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

    public List getAllEmployeeDetails(HttpServletRequest httpServletRequest) throws ServiceLocatorException {
        ArrayList<UserVTO> searchklist = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {
            queryString = "SELECT usr_id,email1,CONCAT_WS(' ',first_name,last_name) AS name,cur_status,phone1 FROM users WHERE cur_status like 'Active' AND type_of_user !='R' AND org_id = " + httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID);

            // System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                UserVTO usersVTO = new UserVTO();
                usersVTO.setEmpId(resultSet.getInt("usr_id"));
                usersVTO.setEmpLoginId(resultSet.getString("email1"));
                usersVTO.setEmpName(resultSet.getString("name"));
                usersVTO.setCur_status(resultSet.getString("cur_status"));
                usersVTO.setPhone1(resultSet.getString("phone1"));
                searchklist.add(usersVTO);

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

    /**
     *
     * This method is used to getting employee myprofile address
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public UserAddress getEmployeeAddress(HttpServletRequest httpServletRequest, String tableName) throws ServiceLocatorException {
        // ArrayList<UserAddress> addressList = new ArrayList<UserAddress>();
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlquery = "";
        UserAddress userAddress = new UserAddress();
        try {
            if ("usr_address".equalsIgnoreCase(tableName)) {
                sqlquery = "select  address_flag,address,city,state,zip,country,phone,address2 from usr_address where usr_id=" + httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) + " and status='Active'";
            } else if ("consultant_address".equalsIgnoreCase(tableName)) {
                System.out.println("this seession login id from consultant-->" + httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID));
                sqlquery = "select  address_flag,address,city,state,zip,country,phone,address2 from consultant_address where usr_consultant_id=" + httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) + " and status='Active'";
                System.out.println("query-->" + sqlquery);
            }
            // System.out.println(sqlquery + "  sql query from address");
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlquery);


            while (resultSet.next()) {

                if ("PC".equalsIgnoreCase(resultSet.getString("address_flag"))) {
                    userAddress.setP_address(resultSet.getString("address"));
                    userAddress.setP_city(resultSet.getString("city"));
                    userAddress.setP_state(com.mss.msp.util.DataSourceDataProvider.getInstance().getStateName(resultSet.getInt("state")));
                    userAddress.setP_zip(resultSet.getString("zip"));
                    userAddress.setP_country(
                            com.mss.msp.util.DataSourceDataProvider.getInstance().getCountry(resultSet.getInt("country")));
                    userAddress.setP_phone(resultSet.getString("phone"));
                    userAddress.setP_address2(resultSet.getString("address2"));
                    userAddress.setAddress_flag("true");
                    userAddress.setC_address(resultSet.getString("address"));
                    userAddress.setC_city(resultSet.getString("city"));
                    userAddress.setC_state(com.mss.msp.util.DataSourceDataProvider.getInstance().getStateName(resultSet.getInt("state")));
                    userAddress.setC_zip(resultSet.getString("zip"));
                    userAddress.setC_country(com.mss.msp.util.DataSourceDataProvider.getInstance().getCountry(resultSet.getInt("country")));
                    userAddress.setC_phone(resultSet.getString("phone"));
                    userAddress.setC_address2(resultSet.getString("address2"));


                }
                if ("C".equalsIgnoreCase(resultSet.getString("address_flag"))) {
                    userAddress.setC_address(resultSet.getString("address"));
                    userAddress.setC_city(resultSet.getString("city"));
                    userAddress.setC_state(com.mss.msp.util.DataSourceDataProvider.getInstance().getStateName(resultSet.getInt("state")));
                    userAddress.setC_zip(resultSet.getString("zip"));
                    userAddress.setC_country(com.mss.msp.util.DataSourceDataProvider.getInstance().getCountry(resultSet.getInt("country")));
                    userAddress.setC_phone(resultSet.getString("phone"));
                    userAddress.setC_address2(resultSet.getString("address2"));
                    userAddress.setAddress_flag("false");
                }
                if ("P".equalsIgnoreCase(resultSet.getString("address_flag"))) {
                    userAddress.setP_address(resultSet.getString("address"));
                    userAddress.setP_city(resultSet.getString("city"));
                    userAddress.setP_state(com.mss.msp.util.DataSourceDataProvider.getInstance().getStateName(resultSet.getInt("state")));
                    userAddress.setP_zip(resultSet.getString("zip"));
                    userAddress.setP_country(com.mss.msp.util.DataSourceDataProvider.getInstance().getCountry(resultSet.getInt("country")));
                    userAddress.setP_phone(resultSet.getString("phone"));
                    userAddress.setP_address2(resultSet.getString("address2"));
                    userAddress.setAddress_flag("false");


                }
            }
            //         addressList.add(userAddress);

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
        return userAddress;
    }

    public EmpDetails getEmployeeDetails(HttpServletRequest httpServletRequest, int userid) throws ServiceLocatorException {

        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "SELECT u.first_name,u.last_name,u.dob,u.gender,u.marital_status,u.living_country,u.working_country,u.phone1,u.alias_name,u.image_path,u.office_phone,u.fax,u.email1,u.email2,emp_position,u.cur_status,a.account_name,a.account_id FROM users u LEFT OUTER JOIN accounts a ON ((SELECT org_id FROM users WHERE usr_id=" + userid + ")=a.account_id)  WHERE u.usr_id=" + userid;

        Map titlesMap = new HashMap();
        EmpDetails empdetails = new EmpDetails();
        try {

            //   System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                empdetails.setOrgid(resultSet.getInt("account_id"));
                empdetails.setFirst_name(resultSet.getString("first_name"));
                empdetails.setLast_name(resultSet.getString("last_name"));
                //   empdetails.setDob(com.mss.msp.util.DateUtility.getInstance().convertDateToView(resultSet.getDate("dob")));

                //    System.out.println(empdetails.getDob() + " in result set we print");
                // empdetails.setGender(resultSet.getString("gender"));
                if ("M".equalsIgnoreCase(resultSet.getString("gender"))) {
                    empdetails.setGender("1");
                } else {
                    empdetails.setGender("2");
                }
                empdetails.setPhone1(resultSet.getString("phone1"));
                empdetails.setAlias(resultSet.getString("alias_name"));
                // System.out.println(empdetails.getAlias() + " here we print alias name");
                empdetails.setLiving_country(resultSet.getString("living_country"));
                // empdetails.setMarital_status(resultSet.getString("marital_status"));
                if ("S".equalsIgnoreCase(resultSet.getString("marital_status"))) {
                    empdetails.setMarital_status("1");
                } else {
                    empdetails.setMarital_status("2");
                }
                empdetails.setEmp_position(resultSet.getString("emp_position"));
                empdetails.setCorp_phone(resultSet.getString("office_phone"));
                empdetails.setFax(resultSet.getString("fax"));
                empdetails.setEmail1(resultSet.getString("email1"));
                empdetails.setEmail2(resultSet.getString("email2"));
                empdetails.setCurrent_status(resultSet.getString("cur_status"));
                empdetails.setWorking_country(resultSet.getString("working_country"));
                empdetails.setImage_path(resultSet.getString("image_path"));
                empdetails.setAccount_name(resultSet.getString("account_name"));


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
        return empdetails;

    }

    /**
     *
     * This method is used to update employee profile
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public boolean updateEmpDetails(UsersdataHandlerAction usersdataHandlerAction, HttpServletRequest httpServletRequest, int userSessionId) throws ServiceLocatorException {
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean isExceute = false;
        int updatedRows = 0;
        /* System.out.println(usersdataHandlerAction.getFirst_name() + "  here we print first name");
         System.out.println(usersdataHandlerAction.getLast_name() + "  here we print last name");
         System.out.println(usersdataHandlerAction.getDob() + "  here we print dob name");
         System.out.println(usersdataHandlerAction.getAlias() + "  here we print alias name");
         System.out.println(usersdataHandlerAction.getGender() + "  here we print gender name ----------->");
         System.out.println(usersdataHandlerAction.getWorking_country() + "  here we prin country name");
         System.out.println(usersdataHandlerAction.getLiving_country() + "  here we print country name");
         System.out.println(usersdataHandlerAction.getPhone1() + "  here we print phone name");
         System.out.println(usersdataHandlerAction.getMarital_status() + "  here we print mstatus name");
         System.out.println(usersdataHandlerAction.getUserid() + " user id printing action class");

         System.out.println(usersdataHandlerAction.getCorp_phone() + " hear we print corp phone");
         System.out.println(usersdataHandlerAction.getEmail1() + " hear we print email");
         System.out.println(usersdataHandlerAction.getEmail2() + " hear we print email2");
         System.out.println(usersdataHandlerAction.getFax() + " hear we print Fax phone");
         System.out.println(usersdataHandlerAction.getCurrent_status() + " hear we print corrent status");

         */
        String date;
        //System.out.println("up to here okay");
        date = com.mss.msp.util.DateUtility.getInstance().convertStringToMySQLDate(usersdataHandlerAction.getDob());
        //System.out.println(date + "... here we print date");


        // System.out.println("in update emp details");

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            /* String queryString = "update users set first_name=?,last_name=?,dob=?,alias_name=?,gender=?,working_country=?,phone1=?,marital_status=?,living_country=? where usr_id=?";
                    
             System.out.println("qyery print \n" + queryString);
             connection = ConnectionProvider.getInstance().getConnection();
             System.out.println("connection okay");
             preparedStatement = connection.prepareStatement(queryString);
             preparedStatement.setString(1, usersdataHandlerAction.getFirst_name());
             preparedStatement.setString(2, usersdataHandlerAction.getLast_name());
             preparedStatement.setString(3, date);
             preparedStatement.setString(4, usersdataHandlerAction.getAlias());
             preparedStatement.setString(5, usersdataHandlerAction.getGender());
             preparedStatement.setString(6, usersdataHandlerAction.getWorking_country());
             preparedStatement.setString(7, usersdataHandlerAction.getPhone1());
             preparedStatement.setString(8, usersdataHandlerAction.getMarital_status());
             preparedStatement.setString(9, usersdataHandlerAction.getLiving_country());
             preparedStatement.setInt(10, usersdataHandlerAction.getUserid());
             preparedStatement.executeUpdate();
             System.out.println("statement okay");
            
             */
            String query = "select cur_status from users where usr_id=" + usersdataHandlerAction.getUserid();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            String status = "";
            while (resultSet.next()) {
                status = resultSet.getString("cur_status");
            }
            if ("Active".equalsIgnoreCase(status)) {
                String queryString = "update users set first_name=?,last_name=?,dob=?,alias_name=?,gender=?,working_country=?,phone1=?,marital_status=?,living_country=?,office_phone=?,fax=?,email1=?,email2=?,cur_status=?,emp_position=?,modified_by=?,modified_date=? where usr_id=?";

                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1, usersdataHandlerAction.getFirst_name());
                preparedStatement.setString(2, usersdataHandlerAction.getLast_name());
                preparedStatement.setString(3, date);
                preparedStatement.setString(4, usersdataHandlerAction.getAlias());
                preparedStatement.setString(5, usersdataHandlerAction.getGender());
                preparedStatement.setString(6, usersdataHandlerAction.getWorking_country());
                preparedStatement.setString(7, usersdataHandlerAction.getPhone1());
                preparedStatement.setString(8, usersdataHandlerAction.getMarital_status());
                preparedStatement.setString(9, usersdataHandlerAction.getLiving_country());
                preparedStatement.setString(10, usersdataHandlerAction.getCorp_phone());
                preparedStatement.setString(11, usersdataHandlerAction.getFax());
                preparedStatement.setString(12, usersdataHandlerAction.getEmail1());
                preparedStatement.setString(13, usersdataHandlerAction.getEmail2());
                preparedStatement.setString(14, usersdataHandlerAction.getCurrent_status());
                preparedStatement.setString(15, usersdataHandlerAction.getEmp_position());
                preparedStatement.setInt(16, userSessionId);
                preparedStatement.setTimestamp(17, com.mss.msp.util.DateUtility.getInstance().getCurrentMySqlDateTime());
                preparedStatement.setInt(18, usersdataHandlerAction.getUserid());

                preparedStatement.executeUpdate();
                //  System.out.println("statement okay");
            } else {
                //   System.out.println("in else update emp details" + userSessionId);
                String plainPassword = SecurityServiceProvider.generateRandamSecurityKey(6, 6, 1, 1, 0);
                String pwdSalt = SecurityServiceProvider.generateRandamSecurityKey(10, 10, 2, 3, 3);
                String encPwd = SecurityServiceProvider.getEncrypt(plainPassword, pwdSalt);
                callableStatement = connection.prepareCall("{CALL updateEmpProfile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                //  System.out.println("hello here print after prepare call");
                callableStatement.setString(1, usersdataHandlerAction.getFirst_name());
                callableStatement.setString(2, usersdataHandlerAction.getLast_name());
                callableStatement.setDate(3, com.mss.msp.util.DateUtility.getInstance().getMysqlDate(usersdataHandlerAction.getDob()));
                //  System.out.println("here we print after date changing...........");
                callableStatement.setString(4, usersdataHandlerAction.getAlias());
                callableStatement.setString(5, usersdataHandlerAction.getGender());
                callableStatement.setString(6, usersdataHandlerAction.getWorking_country());
                callableStatement.setString(7, usersdataHandlerAction.getMarital_status());
                callableStatement.setString(8, usersdataHandlerAction.getLiving_country());
                callableStatement.setString(9, usersdataHandlerAction.getEmail1());
                callableStatement.setString(10, usersdataHandlerAction.getEmail2());
                callableStatement.setString(11, usersdataHandlerAction.getCorp_phone());
                callableStatement.setString(12, usersdataHandlerAction.getFax());
                callableStatement.setString(13, usersdataHandlerAction.getPhone1());
                callableStatement.setString(14, usersdataHandlerAction.getCurrent_status());
                callableStatement.setInt(15, 1);
                callableStatement.setInt(16, usersdataHandlerAction.getUserid());
                //  System.out.println("hello here print after prepare call------------> after setting");
                callableStatement.setString(17, pwdSalt);
                callableStatement.setString(18, encPwd);
                callableStatement.setInt(19, userSessionId);
                callableStatement.setString(20, usersdataHandlerAction.getEmp_position());
                callableStatement.registerOutParameter(21, Types.INTEGER);
                //  System.out.println("hello here print after prepare call parameter ");

                isExceute = callableStatement.execute();
                updatedRows = callableStatement.getInt(21);
                //Send Email to the user
                if (updatedRows > 0) {
                    doAddMailManagerStatusActivation(usersdataHandlerAction.getEmail1(), usersdataHandlerAction.getFirst_name(), usersdataHandlerAction.getLast_name(), plainPassword, "serviceBayLoginCredentials", userSessionId);
                }
                // Insert query do add email logger MailManager usersdataHandlerAction.getEmail1() cc bc subject content
                //  System.out.println("update okay");
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {

                if (callableStatement != null) {
                    callableStatement.close();
                    callableStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public void doAddMailManagerStatusActivation(String email1, String first_name, String last_name, String plainPassword, String subject, int createdBy) throws SQLException, ServiceLocatorException {
        String toAdd = "", bodyContent = "", bcc = "", cc = "", SubjectStatusActivation = "";
        //java.util.Properties prop = new java.util.Properties();
        // InputStream input = null;
        // input = new FileInputStream("msb.properties");
        // load a properties file
        //  prop.load(input);
        // toAdd = prop.getProperty("MSB.reg");
        //String FromAdd = prop.getProperty("MSB.from");
        String FromAdd = Properties.getProperty("MSB.from");
        String Empname = first_name;
        Empname = Empname.concat("." + last_name);
        //  System.out.println("Empname" + Empname);
        toAdd = email1;
        // System.out.println("Here we print the properties" + toAdd);
        SubjectStatusActivation = subject;
        StringBuilder htmlText = new StringBuilder();
        htmlText.append("<html>");
        htmlText.append("<body>");
        htmlText.append("<table align='center'>");
        htmlText.append("<tr style='background:#99FF33;height:40px;width:100%;'>");
        htmlText.append("<td>");
        htmlText.append("<font color='white' size='4' face='Arial'>");
        htmlText.append("<p>MSB Login Credentials</p>");
        htmlText.append("</font>");
        htmlText.append("</td>");
        htmlText.append("</tr>");
        htmlText.append("<tr>");
        htmlText.append("<td>");
        htmlText.append("<table style='background:#FFFFCC;width:100%;'>");
        htmlText.append("<tr>");
        htmlText.append("<td>");
        htmlText.append("<font color='#3399FF' size='2' face='Arial' style='font-weight:600;'>");
        htmlText.append("<p>Hello " + Empname + ",</p><br/>");
        htmlText.append("<p>You have been recently added to Servicebay</p>");
        htmlText.append("<p>Please login with below credentials</p><br/>");
        htmlText.append("Email :  " + toAdd + "<br/>");
        htmlText.append("Password : " + plainPassword + "");
        htmlText.append("<p>If you did not have not registered, you can safely ignore this email.</p>");
        htmlText.append("</font>");
        htmlText.append("</td>");
        htmlText.append("</tr>");
        htmlText.append("</table>");
        htmlText.append("</td>");
        htmlText.append("</tr>");
        htmlText.append("<tr>");
        htmlText.append("<td>");
        htmlText.append("<font color='red', size='2' face='Arial' style='font-weight:600;'>*Note:Please do not reply to this e-mail. It was generated by our System.</font>'");
        htmlText.append("</td>");
        htmlText.append("</tr>");
        htmlText.append("</table>");
        htmlText.append("</body>");
        htmlText.append("</html>");
        htmlText.append("</body>");
        htmlText.append("</html>");

        bodyContent = htmlText.toString();

        new com.mss.msp.util.MailManager().doaddemailLog(FromAdd, toAdd, bcc, cc, SubjectStatusActivation, bodyContent, createdBy);
        // System.out.println("logger is created after Status activating email method.... ");
    }
//modified by praveen

    public Map getAllRoles(int userId, String type_of_relation) throws ServiceLocatorException {
        Map orgRoles = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT  role_id ,role_name FROM roles  WHERE 1=1 and org_type='" + type_of_relation + "' ";
        /* if ("SA".equalsIgnoreCase(type_of_user)) {
         queryString += " and org_type='M'";
         }
         if ("AC".equalsIgnoreCase(type_of_user)) {
         queryString += " and org_type='C'";
         }
         if ("VC".equalsIgnoreCase(type_of_user)) {
         queryString += " and org_type='V'";
         }*///System.out.println(query);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                orgRoles.put(resultSet.getInt("role_id"), resultSet.getString("role_name"));

            }

        } catch (SQLException ex) {
            // System.out.println("getOrgAllRoles method-->" + ex.getMessage());
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
        //System.out.println("orgRolesMap-->" + orgRoles);
        return orgRoles;

    }
//modified by praveen

    public Map getAssignedRoles(int userId) throws ServiceLocatorException {
        Map rolesMap = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "select r.role_id as roleId,role_name from usr_roles ur left outer join roles r on(ur.role_id=r.role_id) where usr_id=" + userId;
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

//modified by praveen
    public Map getNotAssignedRoles(int userId, String type_of_relation) throws ServiceLocatorException {
        Map notAssignedRoles = new HashMap();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT  role_id ,role_name FROM roles  WHERE role_id NOT IN(SELECT role_id FROM usr_roles WHERE usr_id=" + userId + ") and org_type='" + type_of_relation + "' ";

        /*  if ("SA".equalsIgnoreCase(type_of_user)) {
         queryString += " and org_type='M'";
         }
         if ("AC".equalsIgnoreCase(type_of_user)) {
         queryString += " and org_type='C'";
         }
         if ("VC".equalsIgnoreCase(type_of_user)) {
         queryString += " and org_type='V'";
         }*/

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                notAssignedRoles.put(resultSet.getInt("role_id"), resultSet.getString("role_name"));
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

        return notAssignedRoles;


    }

    /**
     *
     * This method is used to getting department names return Map object added
     * by praveen<pkatru@miraclesoft.com>
     */
    public Map getDepartment_Names(HttpServletRequest httpServletRequest, UsersdataHandlerAction aThis) {
        Map mapDept_names = new HashMap();
        //Connection connection = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionProvider.getInstance().getConnection();

            String sqlquery = "SELECT a.account_name, d.dept_id ,d.dept_name FROM departments  AS d LEFT OUTER JOIN accounts  AS a ON (d.org_id=a.account_id) WHERE d.org_id=(SELECT org_id FROM users WHERE usr_id=?)";
            preparedStatement = connection.prepareStatement(sqlquery);

            preparedStatement.setInt(1, aThis.getUserid());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                mapDept_names.put(resultSet.getString("dept_id"), resultSet.getString("dept_name"));

            }

        } catch (SQLException ex) {
            mapDept_names = null;
            ex.printStackTrace();
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
                try {
                    throw new ServiceLocatorException(ex);
                } catch (ServiceLocatorException ex1) {
                    ex1.printStackTrace();
                }
            }
            return mapDept_names;
        }
    }

    public int insertRoles(String[] assignedRoleIds, int employeeId, int primaryRoleId) throws ServiceLocatorException {
        Statement statement = null;

        /**
         * The statement is useful to execute the above queryString
         */
        ResultSet resultSet;

        /**
         * The connection is object to useful to create the statement object
         */
        Connection connection = null;
        String queryString;
        int insertedRows = 0;
        int updateRows = 0;
        int deletedRows = 0;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            /**
             * The queryString is created depends on the employeeId
             */
            queryString = "DELETE FROM usr_roles WHERE usr_id=" + employeeId;
            deletedRows = statement.executeUpdate(queryString);
            statement.close();
            statement = null;
            statement = connection.createStatement();


            /**
             * it loops the roles.length and inserts the data into database for
             * each addedVals
             *
             * @throws NullPointerException If a NullPointerException exists and
             * its <code>{@link
             *          java.lang.NullPointerException}</code>
             */
            //System.out.println("assignedRoleIds.length-->" + assignedRoleIds.length);
            for (int counter = 0; counter < assignedRoleIds.length; counter++) {
                if (Integer.parseInt(assignedRoleIds[counter]) == primaryRoleId) {
                    queryString = "Insert into usr_roles(primary_flag,usr_id,role_id) values(1," + employeeId + ", " + assignedRoleIds[counter] + ")";
                } else {
                    queryString = "Insert into usr_roles(primary_flag,usr_id,role_id) values(0," + employeeId + ", " + assignedRoleIds[counter] + ")";
                }
                insertedRows = statement.executeUpdate(queryString);
            }


        } catch (Exception e) {
            throw new ServiceLocatorException(e);
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

            } catch (SQLException se) {
                throw new ServiceLocatorException(se);
            }
        }
        return insertedRows;
    }

    public List<UserVTO> getCsrList(UsersdataHandlerAction usersdataHandlerAction, int userId) throws ServiceLocatorException {
        List<UserVTO> userVTOList = new ArrayList<UserVTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
        connection = ConnectionProvider.getInstance().getConnection();

        // String queryString = "SELECT  role_id ,role_name FROM roles  WHERE role_id NOT IN(SELECT role_id FROM usr_roles WHERE usr_id=" + userId + ")";

        String queryString = "SELECT DISTINCT(u.usr_id),concat(u.first_name,'.',u.last_name) as name,u.email1,u.cur_status FROM users u "
                + " LEFT OUTER JOIN csrorgrel csr  ON(u.usr_id=csr.csr_id) LEFT OUTER JOIN usr_roles ur "
                + " ON(u.usr_id=ur.usr_id) LEFT OUTER JOIN roles r ON(ur.role_id=r.role_id)"
                + " WHERE ur.role_id=1 AND primary_flag=1";

        if (usersdataHandlerAction.getEmail1() != null) {
            queryString = queryString + " and u.email1 like '%" + usersdataHandlerAction.getEmail1() + "%'  ";
        }
        if (usersdataHandlerAction.getEmpName() != null) {
            queryString = queryString + " and (u.first_name like '%" + usersdataHandlerAction.getEmpName() + "%'  "
                    + "or u.last_name like '%" + usersdataHandlerAction.getEmpName() + "%')  ";
        }

//        if (usersdataHandlerAction.getEmpName() != null) {
//            queryString = queryString + " and u.first_name like '%" + usersdataHandlerAction.getEmpName() + "%'  ";
//        }
        if (usersdataHandlerAction.getStatus() != null) {
            if ("All".equals(usersdataHandlerAction.getStatus())) {
                queryString = queryString + " and u.cur_status like '%%'  ";
            } else {
                queryString = queryString + " and u.cur_status= '" + usersdataHandlerAction.getStatus() + "'  ";
            }
        }


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                UserVTO userVTO = new UserVTO();
                userVTO.setEmpId(resultSet.getInt("u.usr_id"));
                userVTO.setFirst_name(resultSet.getString("name"));
                userVTO.setEmail1(resultSet.getString("u.email1"));
                userVTO.setCur_status(resultSet.getString("u.cur_status"));
                userVTO.setNoOfAccounts(dsdp.getCsrAccountCount(resultSet.getInt("u.usr_id")));
                userVTOList.add(userVTO);
            }
            System.out.println("In getCsrList------>" + queryString);

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
        return userVTOList;
    }

    public List<UserVTO> getCsrAccounts(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException {
        List<UserVTO> userVTOList = new ArrayList<UserVTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        // String queryString = "SELECT  role_id ,role_name FROM roles  WHERE role_id NOT IN(SELECT role_id FROM usr_roles WHERE usr_id=" + userId + ")";


        String queryString = "SELECT csr.csr_id,a.account_id,a.account_name,csr.status FROM accounts a LEFT OUTER JOIN csrorgrel csr ON(a.account_id=csr.org_id) WHERE csr.status='Active' AND csr.csr_id=" + usersdataHandlerAction.getUserId();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                UserVTO userVTO = new UserVTO();
                userVTO.setEmpId(resultSet.getInt("csr.csr_id"));
                userVTO.setOrgId(resultSet.getInt("a.account_id"));
                userVTO.setAccountName(resultSet.getString("a.account_name"));
                userVTO.setCur_status(resultSet.getString("csr.status"));
                userVTOList.add(userVTO);
            }
            System.out.println("In getCsrList------>" + queryString);

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
        return userVTOList;
    }

    public List<UserVTO> getEmployeeCategorization(UsersdataHandlerAction usersdataHandlerAction, int userOrgSessionId) throws ServiceLocatorException {
        List<UserVTO> userVTOList = new ArrayList<UserVTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
        connection = ConnectionProvider.getInstance().getConnection();


        String queryString = " SELECT uc.sub_cat,lk.grpname,r.role_name,uc.id,u.usr_id,u.org_id,uc.cat_type,CONCAT(u.first_name,'.',u.last_name) AS name,uc.status,uc.created_by FROM usr_grouping uc "
                + "LEFT OUTER JOIN lkusr_group lk ON(lk.id=uc.cat_type)"
                + " LEFT OUTER JOIN users u ON(uc.usr_id=u.usr_id) LEFT OUTER JOIN usr_roles ur ON(ur.usr_id=u.usr_id)"
                + " LEFT OUTER JOIN roles r  ON(ur.role_id=r.role_id) "
                + " WHERE uc.status='Active' AND u.org_id=" + userOrgSessionId + "";


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                UserVTO userVTO = new UserVTO();
                userVTO.setGroupingId(resultSet.getInt("uc.id"));
                userVTO.setEmpId(resultSet.getInt("u.usr_id"));
                userVTO.setCatogoryGroup(resultSet.getString("lk.grpname"));

                // userVTO.setOrgId(resultSet.getInt("a.account_id"));
                userVTO.setCatogoryTypeId(resultSet.getInt("uc.cat_type"));
                userVTO.setSubCategory(resultSet.getString("uc.sub_cat"));

                userVTO.setEmpName(resultSet.getString("name"));
                userVTO.setRole(resultSet.getString("r.role_name"));
//                int primaryNumber = resultSet.getInt("uc.is_primary");
//                if (primaryNumber == 0) {
//                    userVTO.setIsPrimary("NO");
//                } else {
//                    userVTO.setIsPrimary("YES");
//                }
                userVTO.setStatus(resultSet.getString("uc.status"));
                userVTO.setCreatedBy(dsdp.getUserNameByUserId(resultSet.getInt("uc.created_by")));
                userVTOList.add(userVTO);
            }
            System.out.println("In getCsrList------>" + queryString);

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
        return userVTOList;
    }
    //praveen

    public void getUserGroupingData(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException {
        connection = ConnectionProvider.getInstance().getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        String queryString = "SELECT CONCAT(first_name,'.',last_name) NAMES,sub_cat, ug.id,ug.usr_id,ug.cat_type,ug.is_primary,ug.STATUS,ug.description FROM usr_grouping ug JOIN users u ON (ug.usr_id=u.usr_id) WHERE ug.id=" + usersdataHandlerAction.getGroupingId();
        System.out.println("this is getUserGroupingData  query-->" + queryString);

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                usersdataHandlerAction.setGroupingId(resultSet.getInt("id"));
                usersdataHandlerAction.setUserName(resultSet.getString("NAMES"));
                usersdataHandlerAction.setUserId(resultSet.getInt("usr_id"));
                usersdataHandlerAction.setUsrCatType(resultSet.getInt("cat_type"));
                if (resultSet.getInt("is_primary") == 1) {
                    usersdataHandlerAction.setPrimaryvalue(true);
                } else {
                    usersdataHandlerAction.setPrimaryvalue(false);
                }
                String str = resultSet.getString("sub_cat");
                StringTokenizer stk = new StringTokenizer(str, ",");
                List list = new ArrayList();

                while (stk.hasMoreTokens()) {
                    list.add(stk.nextToken());
                }
                // System.out.println("list----------->"+list);
                //  List list = new ArrayList(idsMap.keySet());

                usersdataHandlerAction.setCatValuesList(list);
                usersdataHandlerAction.setUsrDescription(resultSet.getString("description"));
                usersdataHandlerAction.setUsrStatus(resultSet.getString("status"));
                usersdataHandlerAction.setCatValuesMap(com.mss.msp.util.DataSourceDataProvider.getInstance().getRequiteCategory(resultSet.getInt("cat_type")));
                System.out.println("this is printing --->" + resultSet.getString("sub_cat"));
                System.out.println("here fine for result set");
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
    public List getHomeRedirectDetails(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException {
        List<HomeVTO> homeVTOList = new ArrayList<HomeVTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();

        String queryString = "SELECT h.id,a.account_name,h.type_of_user,r.role_name,h.action_name,h.STATUS,h.description "
                + "FROM home_redirect_action h "
                + "LEFT OUTER JOIN accounts a ON(a.account_id=h.org_id) "
                + "LEFT OUTER JOIN roles r ON(r.role_id=h.primaryrole)";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                HomeVTO homeVTO = new HomeVTO();
                homeVTO.setHomeId(resultSet.getInt("id"));
                if (resultSet.getString("account_name") == null || resultSet.getString("account_name").equals("")) {
                    homeVTO.setAccountName("All");
                } else {
                    homeVTO.setAccountName(resultSet.getString("account_name"));
                }
                homeVTO.setRoleName(resultSet.getString("role_name"));
                if (resultSet.getString("type_of_user").equalsIgnoreCase("SA")) {
                    homeVTO.setTypeOfUSer("Site Admin");
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("AC")) {
                    homeVTO.setTypeOfUSer("Customer");
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("VC")) {
                    homeVTO.setTypeOfUSer("Vendor");
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("E")) {
                    homeVTO.setTypeOfUSer("Employee");
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("CO")) {
                    homeVTO.setTypeOfUSer("Consultant");
                } else {
                    homeVTO.setTypeOfUSer("");
                }
                //homeVTO.setTypeOfUSer(resultSet.getString("type_of_user"));
                homeVTO.setActionName(resultSet.getString("action_name"));
                homeVTO.setStatus(resultSet.getString("STATUS"));
                homeVTO.setDescription(resultSet.getString("description"));
                homeVTOList.add(homeVTO);
            }
            System.out.println("In homeRedirect List query String------>" + queryString);

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
        return homeVTOList;
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
    public HomeVTO getHomeRedirectDetailsForEdit(UsersdataHandlerAction usersdataHandlerAction) throws ServiceLocatorException {
        List<HomeVTO> homeVTOList = new ArrayList<HomeVTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        HomeVTO homeVTO = new HomeVTO();

        String queryString = "SELECT h.id,a.account_name,h.type_of_user,r.role_name,h.action_name,h.STATUS,"
                + "h.description,l.acc_type_name,h.org_id,h.primaryrole   "
                + "FROM home_redirect_action h "
                + "LEFT OUTER JOIN accounts a ON(a.account_id=h.org_id) "
                + "LEFT OUTER JOIN roles r ON(r.role_id=h.primaryrole) "
                + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=h.org_id) "
                + "LEFT OUTER JOIN lk_acc_type l ON(l.id=o.acc_type) "
                + "WHERE h.id=" + usersdataHandlerAction.getHomeRedirectActionId();
        System.out.println(">>>>>>>>>>>>>query for ---->" + queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                homeVTO.setHomeId(resultSet.getInt("id"));
                if (resultSet.getString("account_name") == null) {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>account name is null");
                    homeVTO.setAccountName("");
                } else {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>account name in not null");
                    homeVTO.setAccountName(resultSet.getString("account_name"));
                }
                if (resultSet.getString("acc_type_name") == null) {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>acc_type_name  is null");
                    homeVTO.setAccountType(resultSet.getString("type_of_user"));
                } else {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>acc_type_name  in not null");
                    if (resultSet.getString("acc_type_name").equalsIgnoreCase("Customer")) {
                        homeVTO.setAccountType("AC");
                    } else if (resultSet.getString("acc_type_name").equalsIgnoreCase("Vendor")) {
                        homeVTO.setAccountType("VC");
                    } else {
                        homeVTO.setAccountType("M");
                    }
                }
                if (0 == Integer.parseInt(resultSet.getString("org_id"))) {
                    homeVTO.setAccountId("0");
                } else {
                    homeVTO.setAccountId(resultSet.getString("org_id"));
                }
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>PRIMARY ROLE?>>>>>>>>>>" + resultSet.getString("primaryrole"));
                homeVTO.setRoleName(resultSet.getString("primaryrole"));
                homeVTO.setTypeOfUSer(resultSet.getString("type_of_user"));
                homeVTO.setActionName(resultSet.getString("action_name"));
                homeVTO.setStatus(resultSet.getString("STATUS"));
                homeVTO.setDescription(resultSet.getString("description"));
                //homeVTOList.add(homeVTO);
            }
            System.out.println("In homeRedirect List query String------>" + queryString);

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
        return homeVTO;
    }
}
