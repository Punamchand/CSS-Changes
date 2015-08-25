/*
 * AjaxHandlerServiceImpl.java
 *
 * Created on June 11, 2008, 12:57 AM
 *greensheetListSearch
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.mss.msp.usrajax;

import com.mss.msp.security.SecurityServiceProvider;
import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;

import com.mss.msp.util.DateUtility;
import javax.servlet.http.HttpServletRequest;
import com.mss.msp.util.Properties;
import com.mss.msp.util.ServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;

import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.commons.lang.StringEscapeUtils;
import com.mss.msp.usersdata.UserAddress;
import com.mss.msp.usersdata.UserVTO;
import com.mss.msp.usr.leaves.LeavesVTO;
import com.mss.msp.usr.leaves.UserLeavesAction;
import com.mss.msp.util.HibernateServiceLocator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.hibernate.HibernateException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author miracle
 */
public class UserAjaxHandlerServiceImpl implements UserAjaxHandlerService {

    /**
     *
     * Creating a reference variable for Connection
     */
    private Connection connection;
    /**
     *
     * Creating a reference variable for preparedStatement
     */
    private PreparedStatement preparedStatement;
    /**
     *
     * Creating a reference variable for Resultset
     */
    private ResultSet resultSet;
    /**
     *
     * Creating a reference variable for String Buffer
     */
    private StringBuffer stringBuffer;
    /**
     *
     * Creating a String queryString used to store SQL Query
     */
    private String queryString;
    /**
     *
     * Creating a boolean flag to return true or false
     */
    private boolean flag;
    /**
     *
     * Creating a String noRecords to replace spaces in Ajax response
     */
    private String noRecords = "no records";

    public String getStates(String country) throws ServiceLocatorException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        stringBuffer = new StringBuffer();
        queryString = null;
        queryString = "SELECT StateName,CountryName FROM vwCountryState where CountryName = " + "'" + country + "'";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            //stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            stringBuffer.append("<xml version=\"1.0\">");
            stringBuffer.append("<COUNTRY countryName=\"" + country + "\">");
            stringBuffer.append("<STATE>--Please Select--</STATE>");
            while (resultSet.next()) {
                stringBuffer.append("<STATE>" + resultSet.getString("StateName") + "</STATE>");
            }
            stringBuffer.append("</COUNTRY>");
            stringBuffer.append("</xml>");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ServiceLocatorException sle) {
            sle.printStackTrace();
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
                ex.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    public int addForGotPasswordDetails(String emailId, String urlLink, String key) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int updatedRows = 0;

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            //queryString = "insert into forgotpasswordlink (email_id,url_link,code) values(?,?,?)";
            queryString = "insert into forgotpasswordlink (email_id,url_link,code) values(?,?,?)";


            System.out.println("queryString-->" + queryString);
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, emailId);
            preparedStatement.setString(2, urlLink);
            preparedStatement.setString(3, key);
            //preparedStatement.setTimestamp(3,DateUtility.getInstance().getCurrentMySqlDateTime());

            updatedRows = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ServiceLocatorException sle) {
            sle.printStackTrace();
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
                ex.printStackTrace();
            }
        }
        return updatedRows;
    }

    /*Method for iserting User registartion details
     * Date : 03/10/2015
     * 
     */
    public int doUserRegister(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        int updatedRows = 0;
        boolean isExceute = false;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            //queryString = "insert into forgotpasswordlink (email_id,url_link,code) values(?,?,?)";
        /*   IN frmLogiId VARCHAR(60), 
             IN frmPassword VARCHAR(100), 
             IN frmFirstName VARCHAR(30), 
             IN frmMiddleName VARCHAR(30), 
             IN frmLastName VARCHAR(30), 
             IN frmProfileImagePath VARCHAR(60), 
             IN frmGender VARCHAR(2),
             IN frmMaritialStatus VARCHAR(2),
             IN frmDob TIMESTAMP,
             IN frmPhone VARCHAR(15),
             IN frmPerOfficeAddress VARCHAR(100),
             IN frmPerAddressLine2 VARCHAR(100),
             IN frmPerCity VARCHAR(10),
             IN frmPerState VARCHAR(10),
             IN frmPerZip VARCHAR(10),
             IN frmPerCountry VARCHAR(10),
             IN frmPerPhone VARCHAR(15),
             IN frmAddressFlag VARCHAR(2),
             IN frmContactAddressLine1 VARCHAR(100),
             IN frmContactAddressLine2 VARCHAR(100),
             IN frmContactCity VARCHAR(10),
             IN frmContactState VARCHAR(10),
             IN frmContactZip VARCHAR(10),
             IN frmContactCountry VARCHAR(10),
             IN frmContactPhone VARCHAR(15),
             -- IN frmContactAddressFlag VARCHAR(2),
             OUT frmResponse INT(5)*/
            callableStatement = connection.prepareCall("{CALL spUsrResgistration(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, userAjaxHandlerAction.getLoginId());
            // String hexa16digitSalt=SecurityServiceProvider.generateRandamSecurityKey(16,16,4,4,4);
            //String queryString = "UPDATE tblCreTchComments SET ModifiedDate = ?, ModifiedBy = ?, Comments = ?, Status = ? WHERE id = ? AND CreId = ?";
            //String encPwd=SecurityServiceProvider.getEncrypt(userAjaxHandlerAction.getPassword().trim(),hexa16digitSalt);
            // callableStatement.setString(2, encPwd);
            callableStatement.setString(2, userAjaxHandlerAction.getFirstName());
            callableStatement.setString(3, userAjaxHandlerAction.getMiddleName());
            callableStatement.setString(4, userAjaxHandlerAction.getLastName());
            callableStatement.setString(5, userAjaxHandlerAction.getFilePath());
            if (userAjaxHandlerAction.getGender().equalsIgnoreCase("male")) {
                callableStatement.setString(6, "M");
            } else {
                callableStatement.setString(6, "F");
            }
            // callableStatement.setString(7, userAjaxHandlerAction.getGender());
            if (userAjaxHandlerAction.getMaritalStatus().equalsIgnoreCase("single")) {
                callableStatement.setString(7, "S");
            } else {
                callableStatement.setString(7, "M");
            }

            callableStatement.setDate(8, DateUtility.getInstance().getMysqlDate(userAjaxHandlerAction.getDob()));
            callableStatement.setString(9, userAjaxHandlerAction.getPhone());
            callableStatement.setString(10, userAjaxHandlerAction.getOfficeAddress1());
            callableStatement.setString(11, userAjaxHandlerAction.getOfficeAddress2());
            callableStatement.setString(12, userAjaxHandlerAction.getOfficeCity());
            callableStatement.setString(13, userAjaxHandlerAction.getOfficeState());
            callableStatement.setString(14, userAjaxHandlerAction.getZip());
            callableStatement.setString(15, userAjaxHandlerAction.getOfficeCountry());
            callableStatement.setString(16, userAjaxHandlerAction.getPhone());

            // callableStatement.setString(18, hexa16digitSalt);
            callableStatement.setString(17, userAjaxHandlerAction.getRegType());
            callableStatement.setString(18, "Registered");
            //System.out.println("userAjaxHandlerAction.getLivingCountry()--->" + userAjaxHandlerAction.getLivingCountry());
            callableStatement.setString(19, userAjaxHandlerAction.getLivingCountry());
            //System.out.println("userAjaxHandlerAction.getLoginId()" + userAjaxHandlerAction.getLoginId());
            callableStatement.setInt(20, DataSourceDataProvider.getInstance().getOrgIdByEmailExt(userAjaxHandlerAction.getLoginId()));
            callableStatement.registerOutParameter(21, Types.INTEGER);
            isExceute = callableStatement.execute();

            updatedRows = callableStatement.getInt(21);

            if (updatedRows > 0) {
                try {
                    //System.out.println("in addmailing");
                    addmailling(userAjaxHandlerAction.getLoginId());

                } catch (SQLException ex) {
                    // System.out.println("in addmailing" + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            // System.out.println("after update rows");

        } catch (SQLException ex) {
            // System.out.println("Exception register-->" + ex.getMessage());
            ex.printStackTrace();
        } catch (ServiceLocatorException sle) {
            sle.printStackTrace();
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
        return updatedRows;
    }

    public String getEmployeeDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        System.out.println(">>>>>>>>>>>" + userAjaxHandlerAction.getTechReview());
        if (userAjaxHandlerAction.getTechReview().equalsIgnoreCase("TR")) {
            queryString = "SELECT CONCAT(TRIM(u.first_name),'.',TRIM(u.last_name)) AS FullName,u.usr_id FROM users u LEFT OUTER JOIN acc_requirements ar ON(ar.acc_id=u.org_id) LEFT OUTER JOIN usr_miscellaneous m ON(m.usr_id=u.usr_id) WHERE (last_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' OR first_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%') AND ar.requirement_id=" + userAjaxHandlerAction.getRequirementId() + " AND u.cur_status='Active' LIMIT 30";
            //queryString = "SELECT CONCAT(TRIM(u.first_name),'.',TRIM(u.last_name)) AS FullName,u.usr_id FROM users u left OUTER JOIN usr_miscellaneous um ON(um.usr_id=u.usr_id) WHERE (last_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' OR first_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%') AND cur_status='Active' AND (um.is_team_lead=1 OR um.is_manager=1) AND u.org_id="+userAjaxHandlerAction.getSessionOrgId()+" LIMIT 30";
        } else {
            queryString = "SELECT CONCAT(TRIM(u.first_name),'.',TRIM(u.last_name)) AS FullName,u.usr_id FROM users u "
                    + "LEFT OUTER JOIN prj_sub_prjteam pt ON(pt.usr_id=u.usr_id) "
                    + "WHERE (last_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' OR first_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%') "
                    + "and cur_status='Active' "
                    + "AND pt.sub_project_id=" + userAjaxHandlerAction.getProjectID() + " "
                    + "AND org_id=" + userAjaxHandlerAction.getSessionOrgId() + " LIMIT 30";
        }
        System.out.println("getEmployeeDetails secondary assigned.....>" + queryString);
        //queryString = "SELECT concat(trim(first_name),'.',trim(last_name)) AS FullName,usr_id FROM users WHERE (last_name LIKE '" + empName + "%' OR first_name LIKE '" + empName + "%') and cur_status='Active' LIMIT 30";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            // System.out.println("query-->getEmployeeDetails"+queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();

            int count = 0;

            sb.append("<xml version=\"1.0\">");
            sb.append("<EMPLOYEES>");
            while (resultSet.next()) {
                sb.append("<EMPLOYEE><VALID>true</VALID>");

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
                sb.append("<EMPID>" + resultSet.getInt(2) + "</EMPID>");
                sb.append("</EMPLOYEE>");
                isGetting = true;
                count++;
            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<EMPLOYEE><VALID>false</VALID></EMPLOYEE>");
            }
            sb.append("</EMPLOYEES>");
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
    /*contact info geting */

    public String getEmpAddressDetails(HttpServletRequest httpServletRequest, int empId) throws ServiceLocatorException {
        //System.out.println("==================================In impl for address retrieval=================================");
        ArrayList<UserVTO> eduList = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "", aflag = "";
        UserAddress userAddress = new UserAddress();
        try {
            queryString = " SELECT address_flag, address, address2, city, state, zip, country,phone "
                    + "  FROM usr_address  "
                    + "WHERE usr_id ='" + empId + "' and status='Active'";
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                if (resultSet.getString("address_flag").equalsIgnoreCase("pc")) {
                    resultString = resultSet.getString("address_flag") + '#' + resultSet.getString("address") + '&' + resultSet.getString("address2") + '&' + resultSet.getString("city") + '&' + resultSet.getString("state") + '&' + resultSet.getString("zip") + '&' + resultSet.getString("country") + '&' + resultSet.getString("phone");
                    //System.out.println("Result string:::::"+resultString);
                    return resultString;
                }
                if (resultSet.getString("address_flag").equalsIgnoreCase("p")) {
                    resultString = "false" + '#' + resultSet.getString("address") + '&' + resultSet.getString("address2") + '&' + resultSet.getString("city") + '&' + resultSet.getString("state") + '&' + resultSet.getString("zip") + '&' + resultSet.getString("country") + '&' + resultSet.getString("phone");
                }
                if (resultSet.getString("address_flag").equalsIgnoreCase("c")) {
                    resultString += '#' + resultSet.getString("address") + '&' + resultSet.getString("address2") + '&' + resultSet.getString("city") + '&' + resultSet.getString("state") + '&' + resultSet.getString("zip") + '&' + resultSet.getString("country") + '&' + resultSet.getString("phone");
                }
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
        //System.out.println("Result string:::::"+resultString);
        return resultString;
    }

    /*these methods for contact info updation created by ramakrishna  start*/
    public int setEmpAddressDetails(HttpServletRequest httpServletRequest, int empId, String address, String address2, String city, String state, String zip, String country, String phone, String address_flag) throws ServiceLocatorException {

        PreparedStatement preparedStatement = null;
        Statement statement = null;
        String queryString = "", queryString2 = "";
        String resultString = "";
        // UserAddress userAddress = new UserAddress();
        try {
            if (address_flag.equalsIgnoreCase("p")) {
                queryString = "update usr_address set address_flag='p',address='" + address + "',city='" + city + "',state='" + state + "',zip='" + zip + "',country='" + country + "',phone='" + phone + "',address2='" + address2 + "',status='Active' where usr_id='" + empId + "' and address_flag not in('c')";
                queryString2 = "update usr_address set status='Active' where usr_id='" + empId + "' and address_flag='c'";
            } else {
                queryString = "update usr_address set address_flag='pc',address='" + address + "',city='" + city + "',state='" + state + "',zip='" + zip + "',country='" + country + "',phone='" + phone + "',address2='" + address2 + "',status='Active' where usr_id='" + empId + "' and address_flag not in('c')";
                queryString2 = "update usr_address set status='In-Active' where usr_id='" + empId + "' and address_flag='c'";
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            int updateResult = statement.executeUpdate(queryString);
            int updateResult2 = 0;
            if (!queryString2.isEmpty()) {
                updateResult2 = statement.executeUpdate(queryString2);
            }
            return updateResult;
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
        return 0;
    }

    public int setEmpCAddressDetails(HttpServletRequest httpServletRequest, int empId, String address, String address2, String city, String state, String zip, String country, String phone, String address_flag) throws ServiceLocatorException {
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        String queryString = "", queryString2 = "", queryStringInsert = "";
        String resultString = "";
        try {
            //System.out.println("im in impl method ");
            queryString = "update usr_address set address_flag='" + address_flag + "',address='" + address + "',city='" + city + "',state='" + state + "',zip='" + zip + "',country='" + country + "',phone='" + phone + "',address2='" + address2 + "',status='Active' where usr_id='" + empId + "' and address_flag='c'";
            queryString2 = "update usr_address set address_flag='p' where usr_id=" + empId + " and address_flag='pc'";
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            int updateResult = 0, updateResult2 = 0, updateResultInsert = 0;
            updateResult = statement.executeUpdate(queryString);
            //System.out.println("first query value by update "+updateResult);
            if (updateResult == 0) {
                queryStringInsert = "insert into usr_address(usr_id,address_flag,address,city,state,zip,country,phone,address2,status) values(" + empId + ",'" + address_flag + "','" + address + "','" + city + "','" + state + "','" + zip + "','" + country + "','" + phone + "','" + address2 + "','Active')";
            }
            if (queryStringInsert.isEmpty()) {
                updateResult2 = statement.executeUpdate(queryString2);
            } else {
                updateResultInsert = statement.executeUpdate(queryStringInsert);
                updateResult2 = statement.executeUpdate(queryString2);
            }
            //System.out.println("insert query value by update " + updateResultInsert + " and 2nd query " + updateResult2);
            //System.out.println("second query value by update "+updateResult2);
            return updateResult;
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
        return 0;
    }

    /*these methods for contact info updation created by ramakrishna  end*/

    /* Start , impl added by triveni for skill Details */
    public String getSkillDetails(HttpServletRequest httpServletRequest, int employeeId) throws ServiceLocatorException {

        ArrayList<UserSkills> skillList = new ArrayList<UserSkills>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int i = 0;
        try {

            queryString = " SELECT skill.id , skill.skill_name, skill.skill_rate, skill.comments"
                    + "  FROM   users u , usr_skills skill "
                    + " WHERE u.usr_id=skill.usr_id  AND u.usr_id ='" + employeeId + "' GROUP BY id ";

            //  System.out.println("get details queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            //     System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            //    System.out.println("after statements ");
            while (resultSet.next()) {


                UserSkills userskills = new UserSkills();
                userskills.setSkill_id(resultSet.getInt("id"));
                userskills.setSkill_name(resultSet.getString("skill_name"));
                userskills.setSkill_rate(resultSet.getString("skill_rate"));
                userskills.setComments(resultSet.getString("comments"));
                // eduList.add(usersVTO);
                resultString += userskills.getSkill_id() + "|" + userskills.getSkill_name() + "|" + userskills.getSkill_rate() + '|' + userskills.getComments() + '^';
                //   System.out.println("---------> skill result table"+resultString);

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

    public int setEditSkillDetails(HttpServletRequest httpServletRequest, int userSessionId, int skill_id, UserAjaxHandlerAction userajaxhandleraction) throws ServiceLocatorException {

        ArrayList<UserSkills> skillList = new ArrayList<UserSkills>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryStringupdate = "";

        int isUpdated = 0;
        int i = 0;
        try {

            //  System.out.println("------->"+skill_name);
            queryStringupdate = " update usr_skills SET skill_name=?,skill_rate=?,comments=?,modified_by=?,modified_date=? WHERE id =" + skill_id;

            //System.out.println("get edit skill details update query" + queryStringupdate);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryStringupdate);

            preparedStatement.setString(1, userajaxhandleraction.getSkill_name());

            preparedStatement.setString(2, userajaxhandleraction.getSkill_rate());
            preparedStatement.setString(3, userajaxhandleraction.getComments());
            preparedStatement.setInt(4, userSessionId);
            preparedStatement.setTimestamp(5, com.mss.msp.util.DateUtility.getInstance().getCurrentMySqlDateTime());



            isUpdated = preparedStatement.executeUpdate();
            // System.out.println("edit skill update---------------------------->" + isUpdated);

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
        return isUpdated;


    }

    public String getEditOverlayDetails(HttpServletRequest httpServletRequest, int employeeId, int skill_id) throws ServiceLocatorException {

        ArrayList<UserSkills> skillList = new ArrayList<UserSkills>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int i = 0;
        try {

            queryString = " SELECT id,skill_name, skill_rate, comments FROM usr_skills WHERE id=" + skill_id + "";


            //   System.out.println("edit overlay queryString-->" + queryString);

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {


                UserSkills userskills = new UserSkills();

                userskills.setSkill_id(resultSet.getInt("id"));
                userskills.setSkill_name(resultSet.getString("skill_name"));
                userskills.setSkill_rate(resultSet.getString("skill_rate"));
                userskills.setComments(resultSet.getString("comments"));
                // eduList.add(usersVTO);
                resultString += userskills.getSkill_id() + "|" + userskills.getSkill_name() + '|' + userskills.getSkill_rate() + '|' + userskills.getComments() + '^';

//                System.out.println("--->"+userskills.getSkill_id());
//                System.out.println("------->"+resultString);

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

    public int getAddSkills(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction userajaxhandleraction) throws ServiceLocatorException {
//       ArrayList<UserVTO>  = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int result = 0;
        UserSkills userskills = new UserSkills();
        int i = 0;

        try {

            queryString = " INSERT into usr_skills(usr_id, skill_name,  skill_rate, comments,created_by)"
                    + "VALUES(?,?,?,?,?)";


            //  System.out.println(" add details queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);



            preparedStatement.setInt(1, userajaxhandleraction.getUserid());
            preparedStatement.setString(2, userajaxhandleraction.getSkill_name());
            preparedStatement.setString(3, userajaxhandleraction.getSkill_rate());
            preparedStatement.setString(4, userajaxhandleraction.getComments());
            preparedStatement.setInt(5, userSessionId);

            result = preparedStatement.executeUpdate();
            if (result > 0) {
                return result;
            } else {
                return result;
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
        return result;
    }
    /* End , impl added by triveni for Skill Details */

    /*
     * this is for using insert data into email logger table
     *
     * added by praveen<pkatru@miraclesoft.com>
     * 
     */
    public int addmailling(String loginId) throws SQLException, ServiceLocatorException {
        String toAdd = "", bodyContent = "", bcc = "", cc = "", Subject = "";
        //java.util.Properties prop = new java.util.Properties();
        //  InputStream input = null;

        // System.out.println("In addmailing1");

        // input = new FileInputStream("MSBProperties.properties");

        // load a properties file
        //  prop.load(input);
        //  toAdd = prop.getProperty("MSB.reg");
        String FromAdd = Properties.getProperty("MSB.from");
        toAdd = loginId;
        // System.out.println("Here we print the properties" + toAdd);
        Subject = "Registration_Request";
        StringBuilder htmlText = new StringBuilder();
        // System.out.println("In addmailing2");
        htmlText.append("<html>");
        htmlText.append("<body>");
        htmlText.append("<table align='center'>");
        htmlText.append("<tr style='background:#99FF33;height:40px;width:100%;'>");
        htmlText.append("<td>");
        htmlText.append("<font color='white' size='4' face='Arial'>");
        htmlText.append("<p>MSB Reset Password</p>");
        htmlText.append("</font>");
        htmlText.append("</td>");
        htmlText.append("</tr>");
        htmlText.append("<tr>");
        htmlText.append("<td>");
        htmlText.append("<table style='background:#FFFFCC;width:100%;'>");
        htmlText.append("<tr>");
        htmlText.append("<td>");
        htmlText.append("<font color='#3399FF' size='2' face='Arial' style='font-weight:600;'>");
        htmlText.append("<p>Hello,</p><br/>");
        htmlText.append("<p>We recieved a request to the password associated with this email address .</p>");
        htmlText.append("<p>If you made this request,please follow the instructions below.</p><br/>");
        //htmlText.append(" <font color=\"red\">  <a href='" + forGotPwdURL + "?"+ "emailId=" + emailId + "&sessionId=" + session7digit + "'> "+ " Click here </a> </font>  to reset your password.");
        htmlText.append("<p>If you did not have your password reset, you can safely ignore this email.</p>");
        htmlText.append("<p>We assure you that your customer account is safe.</p>");
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
        //System.out.println("In addmailing3");
        bodyContent = htmlText.toString();
        //System.out.println("In addmailing4");
        int ReturnStatus = new com.mss.msp.util.MailManager().doaddemailLog(FromAdd, toAdd, bcc, cc, Subject, bodyContent, 0);
        //System.out.println("logger is created on addmailling method.... ");
        return ReturnStatus;
    }

    /* Start , impl added by Aklakh for Education Details */
    public String getQualificationDetails(HttpServletRequest httpServletRequest, int employeeId) throws ServiceLocatorException {

        ArrayList<UserVTO> eduList = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserVTO usersVTO = new UserVTO();
        int i = 0;
        try {

            queryString = " SELECT DISTINCT edu.usr_edu_id, edu.qualification, edu.usr_university, edu.usr_institution, edu.year_start, edu.year_end, edu.percentage, edu.usr_specialization"
                    + "  FROM   users u , usr_education edu "
                    + " WHERE u.usr_id=edu.usr_id  AND u.usr_id ='" + employeeId + "'";

            // System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            // System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            // System.out.println("after statements ");
            while (resultSet.next()) {

                //System.out.println("In while qualification");

                usersVTO.setEdu_id(resultSet.getInt("usr_edu_id"));
                usersVTO.setQualification(resultSet.getString("qualification"));
                usersVTO.setUniversity(resultSet.getString("usr_university"));
                usersVTO.setInstitution(resultSet.getString("usr_institution"));
                usersVTO.setYear_start(com.mss.msp.util.DateUtility.getInstance().convertDateToView(resultSet.getDate("year_start")));
                usersVTO.setYear_end(com.mss.msp.util.DateUtility.getInstance().convertDateToView(resultSet.getDate("year_end")));
                usersVTO.setPercentage(resultSet.getDouble("percentage"));
                usersVTO.setSpecialization(resultSet.getString("usr_specialization"));
                // eduList.add(usersVTO);
                resultString += usersVTO.getEdu_id() + "|" + usersVTO.getQualification() + "|" + usersVTO.getUniversity() + '|' + usersVTO.getInstitution() + '|' + usersVTO.getYear_start() + '|' + usersVTO.getYear_end() + '|' + usersVTO.getPercentage() + '|' + usersVTO.getSpecialization() + '^';

                //   System.out.println("---------------->"+resultString);

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
    /* End , impl added by Aklakh for Education Details */

    /* Start , impl added by Aklakh for add Education Details */
    public int getInsertedQualificationDetails(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction userajaxhandleraction) throws ServiceLocatorException {
//       ArrayList<UserVTO>  = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int result = 0;
        UserVTO userVTO = new UserVTO();
        int i = 0;
        DateUtility dateUtility = new DateUtility();
        String eduAddStartYear, eduAddEndyear;
        //System.out.println("Date==================>>>>: " + userajaxhandleraction.getYear_start());
        try {
            eduAddStartYear = dateUtility.getInstance().convertStringToMySQLDate(userajaxhandleraction.getYear_start());
            eduAddEndyear = dateUtility.getInstance().convertStringToMySQLDate(userajaxhandleraction.getYear_end());
            queryString = " INSERT into usr_education(usr_id, qualification,  year_start, year_end, percentage, usr_university,  usr_institution, usr_specialization,created_by)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)";


            //      System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            //    System.out.println("After Connection");
//           System.out.println(""+qualification);
            preparedStatement.setInt(1, userajaxhandleraction.getUserid());
            preparedStatement.setString(2, userajaxhandleraction.getQualification());
            preparedStatement.setString(3, eduAddStartYear);
            preparedStatement.setString(4, eduAddEndyear);
            preparedStatement.setDouble(5, userajaxhandleraction.getPercentage());
            preparedStatement.setString(6, userajaxhandleraction.getUniversity());
            preparedStatement.setString(7, userajaxhandleraction.getInstitution());
            preparedStatement.setString(8, userajaxhandleraction.getSpecialization());
            preparedStatement.setInt(9, userSessionId);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                // System.out.println("===================================== record added ====================================================================>");
                return result;
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
        return result;
    }

    /* End , impl added by Aklakh for add Education Details */
    /* Start , impl added by Aklakh for update Education Details */
    public String getEditQualificationDetails(HttpServletRequest httpServletRequest, int userid, int usr_edu_id) throws ServiceLocatorException {
        ArrayList<UserVTO> updateEduList = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int i = 0;
        try {

            queryString = " SELECT usr_edu_id, qualification, year_start, year_end, percentage, usr_university, usr_institution, usr_specialization FROM usr_education WHERE usr_edu_id=" + usr_edu_id + "";


            //  System.out.println("queryString-->" + queryString);

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {


                UserVTO userVTO = new UserVTO();

                userVTO.setEdu_id(resultSet.getInt("usr_edu_id"));
                userVTO.setQualification(resultSet.getString("qualification"));
                userVTO.setUniversity(resultSet.getString("usr_university"));
                userVTO.setInstitution(resultSet.getString("usr_institution"));
                userVTO.setYear_start(com.mss.msp.util.DateUtility.getInstance().convertDateToView(resultSet.getDate("year_start")));
                userVTO.setYear_end(com.mss.msp.util.DateUtility.getInstance().convertDateToView(resultSet.getDate("year_end")));
                userVTO.setPercentage(resultSet.getDouble("percentage"));
                userVTO.setSpecialization(resultSet.getString("usr_specialization"));


                // eduList.add(usersVTO);
                resultString += userVTO.getEdu_id() + "|" + userVTO.getQualification() + '|' + userVTO.getUniversity() + '|' + userVTO.getInstitution() + '|' + userVTO.getYear_start() + '|' + userVTO.getYear_end() + '|' + userVTO.getPercentage() + '|' + userVTO.getSpecialization() + '^';

                //    System.out.println("--->"+userVTO.getEdu_id());
                // System.out.println("------->get edit details" + resultString);

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
    /* End , impl added by Aklakh for update Education Details */

    public int getEduUpdateDetails(HttpServletRequest httpServletRequest, int usr_edu_id, UserAjaxHandlerAction userajaxhandleraction) throws ServiceLocatorException {
        ArrayList<UserVTO> updateEduOverlayList = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        // System.out.println("---> usr_edu_id" + usr_edu_id);
        int isUpdated = 0;
        int i = 0;
        String eduStartYear, eduEndyear;
        eduStartYear = com.mss.msp.util.DateUtility.getInstance().convertStringToMySQLDate(userajaxhandleraction.getStart_year());
        eduEndyear = com.mss.msp.util.DateUtility.getInstance().convertStringToMySQLDate(userajaxhandleraction.getEnd_year());
        try {
            //  System.out.println("===============================>" + userajaxhandleraction.getStart_year());
            // System.out.println("-------> In Overlay"+qualification);
            queryString = " UPDATE usr_education SET qualification=?, year_start=?, year_end=?, percentage=?,usr_university=?, usr_institution=?,usr_specialization=?,modified_by=?,modified_date=?   WHERE usr_edu_id =" + usr_edu_id;

            // System.out.println("update query" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);

            preparedStatement.setString(1, userajaxhandleraction.getQualification());
            preparedStatement.setString(2, eduStartYear);
            preparedStatement.setString(3, eduEndyear);
            preparedStatement.setDouble(4, userajaxhandleraction.getPercentage());
            preparedStatement.setString(5, userajaxhandleraction.getUniversity());
            preparedStatement.setString(6, userajaxhandleraction.getInstitution());
            preparedStatement.setString(7, userajaxhandleraction.getSpecialization());
            preparedStatement.setInt(8, userajaxhandleraction.getUserSessionId());
            preparedStatement.setTimestamp(9, com.mss.msp.util.DateUtility.getInstance().getCurrentMySqlDateTime());
            //  System.out.println("update query----->" + queryString);

            isUpdated = preparedStatement.executeUpdate();
            //  System.out.println("update Qualification ---------------------------->" + isUpdated);


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
        return isUpdated;
    }
    /*START Repoting information created by RK*/

    public String getReportInfo(HttpServletRequest httpServletRequest, int empId) throws ServiceLocatorException {
        ArrayList<UserVTO> eduList = new ArrayList<UserVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserAddress userAddress = new UserAddress();
        //DataSourceDataProvider dataSourceDataprovider = DataSourceDataProvider.getInstance();
        try {
            //String iReportId = dataSourceDataprovider.getIReportToId(empId);
            //System.out.println("====================IREPORTID " + iReportId);
            queryString = "SELECT  usr_id, first_name,last_name FROM users WHERE usr_id=(SELECT reports_to FROM usr_miscellaneous WHERE usr_id=" + empId + ")";
            //queryString = " SELECT CONCAT(first_name,'.',last_name) AS reportstoName FROM users WHERE usr_id = "+iReportId+"";
            // System.out.println("===========================>" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                //resultString = resultSet.getString("reportstoName") + "#" + iReportId;
                //   resultString = resultSet.getString("reportstoName") + "#" + resultSet.getInt("reports_to_id");
                resultString = resultSet.getString("first_name") + "." + resultSet.getString("last_name") + "#" + resultSet.getInt("usr_id");

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

    // new method for role submit
    public String roleSubmit(HttpServletRequest httpServletRequest, int roleId, int orgId) throws ServiceLocatorException {
        String action = "";
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {

            queryString = " SELECT action_name from home_redirect_action where org_id=" + orgId + " and primaryrole=" + roleId;
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                action = resultSet.getString("action_name");
            }
            httpServletRequest.getSession(false).setAttribute(ApplicationConstants.PRIMARYROLE, roleId);
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

        return action;
    }

    /**
     *
     * This method is used to getting Title Names based on Department Name
     * Return generated String. added by praveen<pkatru@miraclesoft.com>
     */
    public String getTitles(HttpServletRequest httpServletRequest, int dept_id) throws ServiceLocatorException {
        String resultString = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String sqlString = "select title_id,title_name from title where dept_id=?";
            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, dept_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultString += resultSet.getInt("title_id") + "#" + resultSet.getString("title_name") + "^";
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
        return resultString;
    }

    /**
     *
     * This method is used to update miscellaneous information
     *
     * added by praveen<pkatru@miraclesoft.com>
     */
    public void updateMiscellaneousInfo(HttpServletRequest httpServletRequest, UserAjaxHandlerAction aThis) {

        Statement statement = null;
        ResultSet resultSet = null;
        int org_id = 0;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String checkString = "select id from usr_miscellaneous where usr_id=" + aThis.getUserid();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(checkString);
            while (resultSet.next()) {
                org_id = resultSet.getInt("id");
            }
            if (org_id == 0) {
                String insertQuery = "insert into usr_miscellaneous(usr_id,is_team_lead,is_manager,is_PMO,is_sbteam,dept_id,title_id,opt_contact,reports_to,created_by) values(?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, aThis.getUserid());
                preparedStatement.setInt(2, aThis.getIsTeam());
                preparedStatement.setInt(3, aThis.getIsManager());
                preparedStatement.setInt(4, aThis.getIsPmo());
                preparedStatement.setInt(5, aThis.getIsSbteam());
                //  preparedStatement.setInt(6, aThis.getOrg_id());
                preparedStatement.setInt(6, aThis.getDept_id());
                preparedStatement.setInt(7, aThis.getTitle_id());
                preparedStatement.setInt(8, aThis.getIsOpt());
                preparedStatement.setInt(9, aThis.getReport_id());
                preparedStatement.setInt(10, aThis.getUserSessionId());
                preparedStatement.execute();
                //   System.out.println("data is inserted.....");

            } else {

                String miscllQuery = "update usr_miscellaneous set is_team_lead=?,is_manager=?,is_PMO=?,is_sbteam=?,dept_id=?,title_id=?,opt_contact=?,reports_to=?,modified_by=?,modified_date=? where usr_id=?";
                // System.out.println("hear we use update  miscellaneous" + miscllQuery);
                preparedStatement = connection.prepareStatement(miscllQuery);
                // preparedStatement.setInt(1, aThis.getUserid());
                preparedStatement.setInt(1, aThis.getIsTeam());
                preparedStatement.setInt(2, aThis.getIsManager());
                preparedStatement.setInt(3, aThis.getIsPmo());
                preparedStatement.setInt(4, aThis.getIsSbteam());
                //preparedStatement.setInt(5, aThis.getOrg_id());
                preparedStatement.setInt(5, aThis.getDept_id());
                preparedStatement.setInt(6, aThis.getTitle_id());
                preparedStatement.setInt(7, aThis.getIsOpt());
                preparedStatement.setInt(8, aThis.getReport_id());

                preparedStatement.setInt(9, aThis.getUserSessionId());
                preparedStatement.setTimestamp(10, com.mss.msp.util.DateUtility.getInstance().getCurrentMySqlDateTime());
                preparedStatement.setInt(11, aThis.getUserid());
                preparedStatement.execute();
                // System.out.println("data updated.....");

            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
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
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

        }

    }

    public String getSecurityDetails(HttpServletRequest httpServletRequest, int empId) throws ServiceLocatorException {

        // System.out.println("getSecurityDetails---->" + empId);
        String resultString = "";
        Session session = HibernateServiceLocator.getInstance().getSession();
        //Creating a transaction for the session object.
        Transaction tran = session.beginTransaction();
        //UserAjaxHandlerAction e=(UserAjaxHandlerAction)session.get(UserAjaxHandlerAction.class, empId);
        // System.out.println(e.getEmpBankAccName());
        // List list = session.createQuery("from EmpConfidentialVTO e where e.empId=:empId").list();
        String hqlQuery = "FROM EmpConfidentialVTO e WHERE e.userid=:usrid"; //"SELECT m FROM MemberModel m WHERE m.username = :username"

        DateUtility dateUtility = new DateUtility();
        Query query = session.createQuery(hqlQuery);
        query.setInteger("usrid", empId);
        // System.out.println("hql query---->" + hqlQuery);
        List list = query.list();
        // System.out.println("list---->" + list);
        Iterator itr = list.iterator();                   //query.setParameter("id", 10L);//from Employee e where e.employeeId=:id
        while (itr.hasNext()) {
            EmpConfidentialVTO empConfidentialVTO = (EmpConfidentialVTO) itr.next();
            String ssnPanNo = empConfidentialVTO.getSsnPanNo();
            String panName = empConfidentialVTO.getPanName();
            String empBankName = empConfidentialVTO.getEmpBankName();
            String[] empPassportExpDate1 = (empConfidentialVTO.getEmpPassportExpDate().toString()).split(" ");
            String empPassportExpDate = empPassportExpDate1[0];
            String empPassExp = dateUtility.getInstance().convertToviewFormat(empPassportExpDate);

            String empBankAccNo = empConfidentialVTO.getEmpBankAccNo();
            String empBankAccName = empConfidentialVTO.getEmpBankAccName();
            String empBankAccIfsc = empConfidentialVTO.getEmpBankIfsc();
            String empUan = empConfidentialVTO.getEmpUan();
            String empPfno = empConfidentialVTO.getEmpPfNo();
            String empPassportNo = empConfidentialVTO.getEmpPassportNo();
            //    System.out.println("ssnNo is " + ssnPanNo);
            resultString += ssnPanNo + '|' + panName + '|' + empBankName + '|' + empPassExp + '|' + empBankAccNo + '|' + empBankAccName + '|' + empBankAccIfsc + '|' + empUan + '|' + empPfno + '|' + empPassportNo;
        }

        session.close();
        // System.out.println("security details respose string--->" + resultString);
        return resultString;

    }

    public String updateSecurityDetails(HttpServletRequest httpServletRequest, EmpConfidentialVTO empConfidentialVTO) throws ServiceLocatorException {


        String resultString = "";
        Session session = HibernateServiceLocator.getInstance().getSession();
        //Creating a transaction for the session object.
        Transaction tran = session.beginTransaction();

        session.saveOrUpdate(empConfidentialVTO);
        tran.commit();
        if (tran.wasCommitted()) {
            resultString = "Confidential Details Updated Successfully";
        }
        // System.out.println("Inserted");
        session.close();


        return resultString;
        //Storing values into the TreeList.
        // empActivityTypesList.add(desc);

    }// closing for loop.

    /* Start, Add task types by Aklakh */
    public String getTypesOfTask(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        ArrayList<UserVTO> taskTypes = new ArrayList<UserVTO>();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {
            //queryString = "SELECT tt.task_types_id, tt.task_type_name FROM lk_task_types tt WHERE tt.task_rel_toId=" + task_related + " ";
            queryString = "SELECT p.proj_name,p.project_id FROM acc_projects p "
                    + "LEFT OUTER JOIN prj_sub_prjteam t ON(t.sub_project_id=p.project_id) "
                    + "WHERE t.usr_id=" + userAjaxHandlerAction.getUserSessionId() + "";
            System.out.println("queryString getTypesOfTask-->" + queryString);

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                resultString += resultSet.getInt("project_id") + "#" + resultSet.getString("proj_name") + "^";

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
    /*end aklakh code*/

    /**
     * *************************************
     *
     * @getRelatedToNames() method is used to get task details and appends on
     * TaskEdit screen
     *
     * @Author:RK Ankireddy
     *
     * @Created Date:04/15/2015
     *
     * @Corresponded Action:getTaskDetails.action in users.xml
     *
     **************************************
     */
    public String getRelatedToNames(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction) {
        ArrayList<UserVTO> taskTypes = new ArrayList<UserVTO>();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {
            if (userAjaxHandlerAction.getTask_related_to().equalsIgnoreCase("1")) {
                queryString = " SELECT u.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
                        + "FROM users u "
                        + "LEFT OUTER JOIN prj_sub_prjteam p ON(p.usr_id=u.usr_id) "
                        + "WHERE p.sub_project_id=?";
                connection = ConnectionProvider.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setInt(1, userAjaxHandlerAction.getTaskType());
            }
            if (userAjaxHandlerAction.getTask_related_to().equalsIgnoreCase("2")) {
                queryString = " SELECT u.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAMES "
                        + "FROM users u "
                        + "LEFT OUTER JOIN usr_roles p ON(p.usr_id=u.usr_id) "
                        + "WHERE role_id=1";
                connection = ConnectionProvider.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(queryString);
            }
            System.out.println("queryString-->" + queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultString += resultSet.getInt("usr_id") + "#" + resultSet.getString("NAMES") + "^";

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
     * Date : April 15 2015
     *
     * Author : jagan chukkala<jchukkala@miraclesoft.com>
     *
     * getInsertedLeaveDetails() method can be used to add the leave by the user
     * And returns Result
     * *****************************************************************************
     */
//    public int getInsertedLeaveDetails(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction userajaxhandleraction) throws ServiceLocatorException {
//        
//        CallableStatement callableStatement = null;
//        PreparedStatement preparedStatement = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        String queryString = "";
//        String resultString = "";
//        int result = 0;
////        UserVTO userVTO = new UserVTO();
////        int i = 0;
//        DateUtility dateUtility = new DateUtility();
//        String startDate="";
//        String endDate="";
//        System.out.println("user id is==================>>>>: " +userSessionId);
//        System.out.println("From leave==================>>>>: " +userajaxhandleraction.getFromleave());
//        System.out.println("To leave====================>>>>: " +userajaxhandleraction.getToleave());
//        System.out.println("leave Type==================>>>>: " +userajaxhandleraction.getLeavetype());
//        System.out.println("reports to==================>>>>: "+userajaxhandleraction.getReportsTo());
//        System.out.println("reasson is==================>>>>: "+userajaxhandleraction.getReason());
//        System.out.println("leave status is==================>>>>: "+userajaxhandleraction.getLeavestatus());
//        int reportingPersonId=DataSourceDataProvider.getInstance().getReportingPersonIDByUserId(userSessionId);
//        System.out.println("reporting id is---->"+reportingPersonId);
//                
//        try {
//            startDate  = dateUtility.getInstance().convertStringToMySQLDate(userajaxhandleraction.getFromleave());
//             endDate=dateUtility.getInstance().convertStringToMySQLDate(userajaxhandleraction.getToleave());
//            queryString = " INSERT into usr_leaves(usr_id, leave_startdate,leave_enddate,leave_status,leave_type,leave_reason,reports_to,created_by)"
//                    + "VALUES(?,?,?,?,?,?,?,?)";
//
//
//              System.out.println("queryString-->" + queryString);
//            connection = ConnectionProvider.getInstance().getConnection();
//            preparedStatement = connection.prepareStatement(queryString);
//            //    System.out.println("After Connection");
//           
//            preparedStatement.setInt(1, userSessionId);
//            preparedStatement.setString(2, startDate);
//            preparedStatement.setString(3, endDate);
//            preparedStatement.setString(4, userajaxhandleraction.getLeavestatus());
//            preparedStatement.setString(5, userajaxhandleraction.getLeavetype());
//            preparedStatement.setString(6, userajaxhandleraction.getReason());
//            
//            preparedStatement.setInt(7, reportingPersonId);
//            
//            preparedStatement.setInt(8, userSessionId);
//            result = preparedStatement.executeUpdate();
//            if (result > 0) {
//                System.out.println("===================================== record added ====================================================================>");
//                return result;
//            }
//
//
//        } catch (Exception sqe) {
//            sqe.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                    resultSet = null;
//                }
//                if (statement != null) {
//                    statement.close();
//                    statement = null;
//                }
//                if (connection != null) {
//                    connection.close();
//                    connection = null;
//                }
//            } catch (SQLException sqle) {
//                sqle.printStackTrace();
//            }
//        }
//        return result;
//    }
    /* *************************************
     *
     * @showTaskSearchDetails() method is used to get task details based on
     * search scenario
     *
     * @Author:RK Ankireddy
     *
     * @Created Date:04/15/2015
     *
     *
     **************************************
     */
    public String getAttachment(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction) {
        ArrayList<UserVTO> taskTypes = new ArrayList<UserVTO>();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {
            queryString = " SELECT id,attachment_name,attachment_path FROM task_attachments WHERE task_id=? AND STATUS='Active'";
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, userAjaxHandlerAction.getTaskid());
            System.out.println("queryString-->" + queryString);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //System.out.println("hiiiiiiiiiiiiiiiiii");
                resultString += resultSet.getInt("id") + "#" + resultSet.getString("attachment_name") + "#" + resultSet.getString("attachment_path") + "^";
            }
            System.out.println("Result string is::::---->" + resultString);
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
    /* *************************************
     *
     * @showTaskSearchDetails() method is used to get task details based on
     * search scenario
     *
     * @Author:RK Ankireddy
     *
     * @Created Date:04/15/2015
     *
     *
     **************************************
     */

    public int doDeactiveAttachment(HttpServletRequest httpServletRequest, UserAjaxHandlerAction userAjaxHandlerAction) {
        System.out.println("Enter the old dragon impl    " + userAjaxHandlerAction.getTaskAttachId());

        ArrayList<UserVTO> taskTypes = new ArrayList<UserVTO>();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int queryResult = 0;
        try {
            queryString = " UPDATE task_attachments SET STATUS='In-Active',modified_by=?,modified_date=? WHERE id=?";
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, userAjaxHandlerAction.getUserSessionId());
            preparedStatement.setTimestamp(2, com.mss.msp.util.DateUtility.getInstance().getCurrentMySqlDateTime());
            preparedStatement.setString(3, userAjaxHandlerAction.getTaskAttachId());
            System.out.println("queryString-->" + queryString);
            queryResult = preparedStatement.executeUpdate();
            if (queryResult > 0) {
                System.out.println("??????????????????????????????Updated succesfully???????????????????????????????????????");
            } else {
                System.out.println("??????????????????????????????Update fail------------------------------------------------");
            }
            System.out.println("Result string is::::---->" + resultString);
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
        return queryResult;

    }

    /**
     * *****************************************************************************
     * Date : April 28 2015
     *
     * Author : KIRAN <addodi@miraclesoft.com>
     *
     * getInsertedLeaveDetails() method can be used to add the leave by the user
     * And returns Result
     * *****************************************************************************
     */
    public int getInsertedLeaveDetails(HttpServletRequest httpServletRequest, int userSessionId, UserAjaxHandlerAction userajaxhandleraction) throws ServiceLocatorException {

        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int result = 0;
//        UserVTO userVTO = new UserVTO();
//        int i = 0;
        DateUtility dateUtility = new DateUtility();
        String startDate = "";
        String endDate = "";
        int reportingPersonId = DataSourceDataProvider.getInstance().getReportingPersonIDByUserId(userSessionId);


        try {
            startDate = dateUtility.getInstance().convertStringToMySQLDateInDash(userajaxhandleraction.getFromleave());
            endDate = dateUtility.getInstance().convertStringToMySQLDateInDash(userajaxhandleraction.getToleave());
            queryString = " INSERT into usr_leaves(usr_id, leave_startdate,leave_enddate,leave_status,leave_type,leave_reason,reports_to,created_by)"
                    + "VALUES(?,?,?,?,?,?,?,?)";

            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, userSessionId);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);
            preparedStatement.setString(4, userajaxhandleraction.getLeavestatus());
            preparedStatement.setString(5, userajaxhandleraction.getLeavetype());
            preparedStatement.setString(6, userajaxhandleraction.getReason());

            preparedStatement.setInt(7, reportingPersonId);

            preparedStatement.setInt(8, userSessionId);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("===================================== record added ========================>");

                //new com.mss.msp.util.MailManager().doaddemailLog(userajaxhandleraction.getLeaveTo(), userajaxhandleraction.getLeaveSupTo(), "", "", "Leave Request Mail", "bodycontent", userSessionId);
                return result;
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
        return result;
    }

    //added by manikanta
    public String getEmployeeNames(HttpServletRequest httpServletRequest, int dept_id) throws ServiceLocatorException {
        String resultString = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String sqlString = "SELECT u.usr_id as uid,CONCAT(first_name,'.',last_name) as empname FROM users u LEFT OUTER JOIN usr_miscellaneous um ON(um.usr_id=u.usr_id) WHERE um.dept_id=?";
            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, dept_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultString += resultSet.getInt("uid") + "#" + resultSet.getString("empname") + "^";
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
        return resultString;
    }

    public String getStatesOfCountry(HttpServletRequest httpServletRequest, int countryId) throws ServiceLocatorException {
        Map states = new LinkedHashMap();
        String resultString = "";
        Session session = null;

        try {
            session = HibernateServiceLocator.getInstance().getSession();
            //countries = session.createQuery("select id,name from CountryVto");
            String hqlQuery = "select id,name from State  WHERE countryId=:countryid";
            if (countryId <= 0) {
                countryId = 1;
            }

            Query query = session.createQuery(hqlQuery);
            query.setInteger("countryid", countryId);
            List list = query.list();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Object[] o = (Object[]) iterator.next();

                resultString += o[0] + "#" + o[1] + "^";
                //states.put(o[0],o[1]);

            }

        } catch (ServiceLocatorException e) {
            System.out.println(e);
        } finally {
            // Closing hibernate session
            if (session != null) {
                session.close();

                if (session.isOpen()) {
                    try {
                        session.flush();
                        session.close();
                        session = null;
                    } catch (HibernateException he) {
                        he.printStackTrace();
                    }
                }
            }
        }
        //System.out.println("List of States are"+states);
        return resultString;
    }

    /**
     * *************************************
     *
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:jagan chukkala<jchukkala@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getLeavesListDetails(HttpServletRequest httpServletRequest, UserAjaxHandlerAction aThis) throws ServiceLocatorException {
        ArrayList<LeavesVTO> leaveslist = new ArrayList<LeavesVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = " ";
        UserLeavesAction userLeavesAction = new UserLeavesAction();
        //String queryString1 = "";
        DateUtility dateUtility = new DateUtility();
        String startDate = "";
        String endDate = "";

        try {


            queryString = "SELECT leave_id,usr_id,DATE(leave_startdate) as l_sdate,DATE(leave_enddate) as l_edate,leave_status,leave_type,leave_reason,reports_to,created_date,created_by,modified_by FROM usr_leaves WHERE 1=1 ";
            if (userLeavesAction.getLeave_startdate() != null && userLeavesAction.getLeave_startdate().toString().isEmpty() == false) {
                startDate = dateUtility.getInstance().convertStringToMySQLDateInDash(userLeavesAction.getLeave_startdate());
                queryString = queryString + " and leave_startdate >= '" + startDate + "'";
            }

            if (userLeavesAction.getLeave_enddate() != null && userLeavesAction.getLeave_enddate().toString().isEmpty() == false) {
                endDate = dateUtility.getInstance().convertStringToMySQLDateInDash(userLeavesAction.getLeave_enddate());
                queryString = queryString + " and leave_enddate <= '" + endDate + "'";
            }


            queryString = queryString + " and usr_id=" + aThis.getUserSessionId() + " LIMIT 20";
            System.out.println("status is" + aThis.getLeavestatus());
            System.out.println("type is" + userLeavesAction.getLeave_type());
            //System.out.println("type is"+userajaxhandleraction.getLeavetype());
            System.out.println("queryString helloooo -->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
            //String name="SELECT CONCAT(first_name,' ',last_name) AS NAME FROM users WHERE usr_id=resultSet.getInt("reports_to")";
            while (resultSet.next()) {
                //queryString1= queryString1+ resultSet.getInt("reports_to");
                LeavesVTO leavesVTO = new LeavesVTO();
                leavesVTO.setLeaveid(resultSet.getInt("leave_id"));
                leavesVTO.setLeavestartdate(dateUtility.getInstance().convertToviewFormatInDash(resultSet.getString("l_sdate")));
                leavesVTO.setLeaveenddate(dateUtility.getInstance().convertToviewFormatInDash(resultSet.getString("l_edate")));
                String status = resultSet.getString("leave_status");
                //leavesVTO.setModifiedby(resultSet.getInt("modified_by"));
                // leavesVTO.setApprovedBy(approvedBy);
                if ("C".equalsIgnoreCase(status)) {
                    leavesVTO.setLeavestatus("Canceled");
                } else if ("O".equalsIgnoreCase(status)) {
                    leavesVTO.setLeavestatus("Applied");
                } else if ("A".equalsIgnoreCase(status)) {
                    leavesVTO.setLeavestatus("Approved");
                } else if ("R".equalsIgnoreCase(status)) {
                    leavesVTO.setLeavestatus("Rejected");
                }
                String leaveType = resultSet.getString("leave_type");
                if ("PA".equalsIgnoreCase(leaveType)) {
                    leavesVTO.setLeavetype("Post Approval");
                } else if ("CT".equalsIgnoreCase(leaveType)) {
                    leavesVTO.setLeavetype("CompTime");
                } else if ("VA".equalsIgnoreCase(leaveType)) {
                    leavesVTO.setLeavetype("Vacation");
                } else if ("TO".equalsIgnoreCase(leaveType)) {
                    leavesVTO.setLeavetype("TimeOFF");
                } else if ("CL".equalsIgnoreCase(leaveType)) {
                    leavesVTO.setLeavetype("Cancel Leave");
                }
                leavesVTO.setReportsto(dsdp.getFnameandLnamebyUserid(resultSet.getInt("reports_to")));
                leavesVTO.setApprovedBy(dsdp.getFnameandLnamebyUserid(resultSet.getInt("modified_by")));
                leaveslist.add(leavesVTO);
                resultString = resultString + resultSet.getInt("leave_id") + "|" + dateUtility.getInstance().convertToviewFormatInDash(resultSet.getString("l_sdate")) + "|" + dateUtility.getInstance().convertToviewFormatInDash(resultSet.getString("l_edate")) + "|" + leavesVTO.getLeavestatus() + "|" + leavesVTO.getLeavetype() + "|" + leavesVTO.getReportsto() + "|" + leavesVTO.getApprovedBy() + "^";
                System.out.println("resultString is-->" + resultString);
            }
            // System.out.println("----------->" + leaveslist);
        } catch (Exception ex) {
            System.out.println(ex.toString());
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
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * *************************************
     *
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getExternalEmployeeDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        if (!"C".equalsIgnoreCase(userAjaxHandlerAction.getResourceType())) {
            queryString = "SELECT concat(trim(first_name),'.',trim(last_name)) AS FullName,usr_id FROM users WHERE (last_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' OR first_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%') and cur_status='Active' AND org_id=" + userAjaxHandlerAction.getSessionOrgId() + " ORDER BY FullName LIMIT 30";
        } else {

            queryString = "SELECT CONCAT(TRIM(first_name),'.',TRIM(last_name)) AS FullName,usr_id FROM con_or_ven_mig_rel cvm LEFT OUTER JOIN USERS u ON (cvm.consultantid=u.usr_id) "
                    + "WHERE accountId=" + userAjaxHandlerAction.getSessionOrgId() + " AND curStatus!='Released' AND Afrtypeofusr LIKE 'VC' and (last_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' OR first_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%') ORDER BY FullName LIMIT 30 ";
        }

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("query-->getEmployeeDetails" + queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            int count = 0;
            sb.append("<xml version=\"1.0\">");
            sb.append("<EMPLOYEES>");
            List projectTeamList = DataSourceDataProvider.getInstance().getProjectTeamMembersList(userAjaxHandlerAction.getProjectID());
            while (resultSet.next()) {
                if (!projectTeamList.contains(resultSet.getInt(2))) {
                    sb.append("<EMPLOYEE><VALID>true</VALID>");
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
                    sb.append("<EMPID>" + resultSet.getInt(2) + "</EMPID>");
                    sb.append("</EMPLOYEE>");
                    isGetting = true;
                    count++;
                }
            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<EMPLOYEE><VALID>false</VALID></EMPLOYEE>");
            }
            sb.append("</EMPLOYEES>");
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

    /**
     * *************************************
     *
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getExternalEmployee2Details(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        queryString = "SELECT concat(trim(first_name),'.',trim(last_name)) AS FullName,usr_id FROM users WHERE (last_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' OR first_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%') and cur_status='Active' AND org_id=" + userAjaxHandlerAction.getSessionOrgId() + " LIMIT 30";

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            // System.out.println("query-->getEmployeeDetails"+queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();

            int count = 0;

            sb.append("<xml version=\"1.0\">");
            sb.append("<EMPLOYEES>");
            while (resultSet.next()) {
                sb.append("<EMPLOYEE><VALID>true</VALID>");

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
                sb.append("<EMPID>" + resultSet.getInt(2) + "</EMPID>");
                sb.append("</EMPLOYEE>");
                isGetting = true;
                count++;
            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<EMPLOYEE><VALID>false</VALID></EMPLOYEE>");
            }
            sb.append("</EMPLOYEES>");
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

    /**
     * *************************************
     *
     * @getVendorNames() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getVendorNames(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        queryString = "SELECT a.account_name,a.account_id "
                + "FROM accounts a "
                + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                + "WHERE a.account_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
                + "AND  o.acc_type=5 "
                + "AND a.account_id NOT IN(SELECT vendor_id FROM customer_ven_rel WHERE customer_id=" + userAjaxHandlerAction.getCustomerId() + ")";

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("query-->getVendorNames" + queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();

            int count = 0;

            sb.append("<xml version=\"1.0\">");
            sb.append("<EMPLOYEES>");
            while (resultSet.next()) {
                sb.append("<EMPLOYEE><VALID>true</VALID>");

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
                sb.append("<EMPID>" + resultSet.getInt(2) + "</EMPID>");
                sb.append("</EMPLOYEE>");
                isGetting = true;
                count++;
            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<EMPLOYEE><VALID>false</VALID></EMPLOYEE>");
            }
            sb.append("</EMPLOYEES>");
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

    public String getCsrNames(String csrName) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        queryString = "SELECT CONCAT(u.first_name,'.',u.last_name)AS NAME,u.usr_id FROM users u LEFT OUTER JOIN usr_roles ur ON (u.usr_id=ur.usr_id) WHERE CONCAT(u.first_name,' ',u.last_name) LIKE '%" + csrName + "%' AND u.cur_status='Active' AND ur.role_id=1";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("query-->getCsrNames" + queryString);
            preparedStatement = connection.prepareStatement(queryString);
            //System.out.println("beforeExecution");

            resultSet = preparedStatement.executeQuery();
            //System.out.println("resultSet" + resultSet);
            int count = 0;

            sb.append("<xml version=\"1.0\">");
            sb.append("<EMPLOYEES>");
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
                sb.append("<EMPLOYEE><VALID>true</VALID>");

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
                sb.append("<EMPID>" + resultSet.getInt(2) + "</EMPID>");
                sb.append("</EMPLOYEE>");
                isGetting = true;
                count++;

            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<EMPLOYEE><VALID>false</VALID></EMPLOYEE>");
            }
            sb.append("</EMPLOYEES>");
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
        //System.out.println("csr name--->" + sb.toString());
        return sb.toString();
    }

    public String csrTermination(int userId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        //StringBuffer sb = new StringBuffer();

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String queryStringupdate = " update csrorgrel SET status=? WHERE csr_id =" + userId;

            //System.out.println("get edit skill details update query" + queryStringupdate);
            preparedStatement = connection.prepareStatement(queryStringupdate);
            preparedStatement.setString(1, "In-Active");
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                Connection connection1 = null;
                Statement statement1 = null;
                ResultSet resultSet1 = null;
                DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
                connection1 = ConnectionProvider.getInstance().getConnection();
                String queryString1 = "SELECT DISTINCT(u.usr_id),concat(u.first_name,'.',u.last_name) as name,u.email1,u.cur_status FROM users u "
                        + " LEFT OUTER JOIN csrorgrel csr  ON(u.usr_id=csr.csr_id) LEFT OUTER JOIN usr_roles ur "
                        + " ON(u.usr_id=ur.usr_id) LEFT OUTER JOIN roles r ON(ur.role_id=r.role_id)"
                        + " WHERE ur.role_id=1 AND primary_flag=1";
                statement1 = connection1.createStatement();
                resultSet1 = statement1.executeQuery(queryString1);
                while (resultSet1.next()) {

                    resultMessage += resultSet1.getInt("u.usr_id") + "|"
                            + resultSet1.getString("name") + "|"
                            + resultSet1.getString("u.email1") + "|"
                            + resultSet1.getString("u.cur_status") + "|"
                            + dsdp.getCsrAccountCount(resultSet1.getInt("u.usr_id")) + "^";
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

    public String changeCsrAccountStatus(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        //StringBuffer sb = new StringBuffer();

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("csruserid" + userAjaxHandlerAction.getUserid());
            System.out.println("csrOrgid" + userAjaxHandlerAction.getOrgId());
            String queryStringupdate = " update csrorgrel SET status=? WHERE csr_id =" + userAjaxHandlerAction.getUserid() + ""
                    + " AND org_id=" + userAjaxHandlerAction.getOrgId() + "";

            //System.out.println("get edit skill details update query" + queryStringupdate);
            System.out.println("queryStringupdate--->" + queryStringupdate);
            preparedStatement = connection.prepareStatement(queryStringupdate);
            preparedStatement.setString(1, userAjaxHandlerAction.getStatus());
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                Connection connection1 = null;
                Statement statement1 = null;
                ResultSet resultSet1 = null;
                DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
                connection1 = ConnectionProvider.getInstance().getConnection();
                String queryString1 = "SELECT csr.csr_id,a.account_id,a.account_name,csr.status FROM accounts a LEFT OUTER JOIN csrorgrel csr ON(a.account_id=csr.org_id) WHERE csr.csr_id=" + userAjaxHandlerAction.getUserid() + "";

                statement1 = connection1.createStatement();
                resultSet1 = statement1.executeQuery(queryString1);
                while (resultSet1.next()) {

                    resultMessage += resultSet1.getInt("csr.csr_id") + "|"
                            + resultSet1.getInt("a.account_id") + "|"
                            + resultSet1.getString("a.account_name") + "|"
                            + resultSet1.getString("csr.status") + "^";
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

    public String getCsrAccount(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        String resultMessage = "";
        try {
            Connection connection1 = null;
            Statement statement1 = null;
            ResultSet resultSet1 = null;

            DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
            connection1 = ConnectionProvider.getInstance().getConnection();
            String queryString1 = "SELECT csr.csr_id,a.account_id,a.account_name,csr.status FROM accounts a LEFT OUTER JOIN csrorgrel csr ON(a.account_id=csr.org_id) WHERE csr.csr_id=" + userAjaxHandlerAction.getUserid() + "";

            if (userAjaxHandlerAction.getStatus() != null) {

                if ("All".equals(userAjaxHandlerAction.getStatus())) {
                    queryString1 = queryString1 + " and csr.status like '%%'  ";
                } else {
                    queryString1 = queryString1 + " and csr.status= '" + userAjaxHandlerAction.getStatus() + "'  ";
                }
            }
            if (userAjaxHandlerAction.getAccountName() != null || !"".equals(userAjaxHandlerAction.getAccountName())) {
                queryString1 = queryString1 + " and a.account_name LIKE '" + userAjaxHandlerAction.getAccountName() + "%'";
            }
//LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
            statement1 = connection1.createStatement();
            resultSet1 = statement1.executeQuery(queryString1);
            while (resultSet1.next()) {

                resultMessage += resultSet1.getInt("csr.csr_id") + "|"
                        + resultSet1.getInt("a.account_id") + "|"
                        + resultSet1.getString("a.account_name") + "|"
                        + resultSet1.getString("csr.status") + "^";
            }
            System.out.println("In Result if" + queryString1);


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

    public String getEmpCategories(UserAjaxHandlerAction userAjaxHandlerAction, int sessionOrgId) throws ServiceLocatorException {
        String resultMessage = "";
        try {
            Connection connection1 = null;
            Statement statement1 = null;
            ResultSet resultSet1 = null;

            DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
            connection1 = ConnectionProvider.getInstance().getConnection();

            String queryString1 = " SELECT uc.sub_cat,lk.grpname,r.role_name,uc.id,u.usr_id,u.org_id,uc.cat_type,CONCAT(u.first_name,'.',u.last_name) AS name,uc.is_primary,uc.status,uc.created_by FROM usr_grouping uc "
                    + "LEFT OUTER JOIN lkusr_group lk ON(lk.id=uc.cat_type)"
                    + " LEFT OUTER JOIN users u ON(uc.usr_id=u.usr_id) LEFT OUTER JOIN usr_roles ur ON(ur.usr_id=u.usr_id)"
                    + " LEFT OUTER JOIN roles r ON(ur.role_id=r.role_id)"
                    + " WHERE u.org_id=" + sessionOrgId + "";

            if (userAjaxHandlerAction.getStatus() != null) {

                if ("All".equals(userAjaxHandlerAction.getStatus())) {
                    queryString1 = queryString1 + " and uc.status like '%%'  ";
                } else {
                    queryString1 = queryString1 + " and uc.status= '" + userAjaxHandlerAction.getStatus() + "'  ";
                }
            }
            if (userAjaxHandlerAction.getEmpName() != null || !"".equals(userAjaxHandlerAction.getEmpName())) {
                if (userAjaxHandlerAction.getEmpId() != 0) {
                    queryString1 = queryString1 + " and u.usr_id= '" + userAjaxHandlerAction.getEmpId() + "'  ";
                } else {
                    queryString1 = queryString1 + " and  (u.first_name LIKE '%" + userAjaxHandlerAction.getEmpName() + "%' or u.last_name like '%" + userAjaxHandlerAction.getEmpName() + "%')";
                }
            }
            if (userAjaxHandlerAction.getCategoryType() != -1) {
                queryString1 = queryString1 + " and lk.id= '" + userAjaxHandlerAction.getCategoryType() + "'";
            }
//LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
            statement1 = connection1.createStatement();
            resultSet1 = statement1.executeQuery(queryString1);
            String primary = "";
            while (resultSet1.next()) {

//                int primaryNumber = resultSet1.getInt("uc.is_primary");
//                if (primaryNumber == 0) {
//                    primary = "NO";
//                } else {
//                    primary = "YES";
//                }


                System.out.println("uc.sub_cat---->" + resultSet1.getString("uc.sub_cat"));

                UserVTO userVTO = new UserVTO();
                userVTO.setGroupingId(resultSet1.getInt("uc.id"));
                userVTO.setEmpId(resultSet1.getInt("u.usr_id"));
                userVTO.setCatogoryGroup(resultSet1.getString("lk.grpname"));

                // userVTO.setOrgId(resultSet.getInt("a.account_id"));
                userVTO.setCatogoryTypeId(resultSet1.getInt("uc.cat_type"));
                userVTO.setSubCategory(resultSet1.getString("uc.sub_cat"));

                userVTO.setEmpName(resultSet1.getString("name"));
                userVTO.setRole(resultSet1.getString("r.role_name"));
//                int primaryNumber = resultSet.getInt("uc.is_primary");
//                if (primaryNumber == 0) {
//                    userVTO.setIsPrimary("NO");
//                } else {
//                    userVTO.setIsPrimary("YES");
//                }
                userVTO.setStatus(resultSet1.getString("uc.status"));
                userVTO.setCreatedBy(dsdp.getUserNameByUserId(resultSet1.getInt("uc.created_by")));
                System.out.println("userVTO.getSubCategory()---->" + userVTO.getSubCategory());
                resultMessage += userVTO.getGroupingId() + "|"
                        + userVTO.getEmpId() + "|"
                        + userVTO.getCatogoryGroup() + "|"
                        + userVTO.getCatogoryTypeId() + "|"
                        + ("'" + userVTO.getSubCategory() + "'") + "|"
                        // + dsdp.getCategoryName(resultSet1.getInt("uc.cat_type")) + "|"
                        + userVTO.getEmpName() + "|"
                        // + primary + "|"
                        + userVTO.getStatus() + "|"
                        + userVTO.getRole() + "|"
                        + userVTO.getCreatedBy() + "^";
            }
            System.out.println("In Result if" + queryString1);


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

    public String doUserGroupingMethod(UserAjaxHandlerAction userAjaxHandlerAction) {
        String resultString = "";
        Statement statement = null;
        String queryString = "";
        int result;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();


            if (isUserGroupExist(userAjaxHandlerAction.getUserId())) {
                queryString = "update usr_grouping set is_primary=" + userAjaxHandlerAction.getPrimary() + ", cat_type=" + userAjaxHandlerAction.getUsrCatType() + " ,sub_cat='" + userAjaxHandlerAction.getUserCatArry() + "',STATUS='" + userAjaxHandlerAction.getUsrStatus() + "',description='" + userAjaxHandlerAction.getUsrDescription() + "',modified_date='" + com.mss.msp.util.DateUtility.getInstance().getCurrentMySqlDateTime() + "',modified_by=" + userAjaxHandlerAction.getUserSessionId() + " where usr_id=" + userAjaxHandlerAction.getUserId();
                resultString = "grouping updated succesfully.";
            } else {
                queryString = "insert into usr_grouping (usr_id,work_type,cat_type,sub_cat,status,description,created_by,is_primary) values"
                        + "(" + userAjaxHandlerAction.getUserId() + "," + "'REC'," + userAjaxHandlerAction.getUsrCatType() + ",'" + userAjaxHandlerAction.getUserCatArry() + "','" + userAjaxHandlerAction.getUsrStatus() + "','" + userAjaxHandlerAction.getUsrDescription() + "'," + userAjaxHandlerAction.getUserSessionId() + "," + userAjaxHandlerAction.getPrimary() + ")";
                resultString = "grouping Added succesfully.";
            }

            System.out.println("the query is-->" + queryString);
            result = statement.executeUpdate(queryString);
            if (result == 1) {
                System.out.println("the record successfully saved");

            }
        } catch (Exception sqe) {
            resultString = "Something were Wrong!";
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

    public boolean isUserGroupExist(int userId) throws ServiceLocatorException {
        boolean result = false;
        Statement statement = null;
        String queryString = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            queryString = "SELECT usr_id FROM usr_grouping WHERE usr_id=" + userId;
            System.out.println("this is query is exist-->" + queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                result = true;
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
        return result;
    }

    public String empCategoryTermination(UserAjaxHandlerAction userAjaxHandlerAction, int sessionOrgId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        String resultMessage = "";
        //StringBuffer sb = new StringBuffer();

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            // System.out.println("csruserid" + userAjaxHandlerAction.getUserid());
            // System.out.println("csrOrgid" + userAjaxHandlerAction.getOrgId());
            String queryStringupdate = " update usr_grouping SET status=? WHERE id =" + userAjaxHandlerAction.getGroupingId() + "";

            //System.out.println("get edit skill details update query" + queryStringupdate);
            System.out.println("empCategoryTermination--->" + queryStringupdate);
            preparedStatement = connection.prepareStatement(queryStringupdate);
            preparedStatement.setString(1, "In-Active");
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                Connection connection1 = null;
                Statement statement1 = null;
                ResultSet resultSet1 = null;
                DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();
                connection1 = ConnectionProvider.getInstance().getConnection();
                // String queryString1 = "SELECT csr.csr_id,a.account_id,a.account_name,csr.status FROM accounts a LEFT OUTER JOIN csrorgrel csr ON(a.account_id=csr.org_id) WHERE csr.csr_id=" + userAjaxHandlerAction.getUserid() + "";
                String queryString1 = " SELECT uc.id,u.usr_id,u.org_id,uc.cat_type,CONCAT(u.first_name,'.',u.last_name) AS name,uc.is_primary,uc.status,uc.created_by FROM usr_grouping uc LEFT OUTER JOIN users u ON(uc.usr_id=u.usr_id)"
                        + " WHERE u.org_id=" + sessionOrgId + "";

                statement1 = connection1.createStatement();
                resultSet1 = statement1.executeQuery(queryString1);

                String primary = "";
                while (resultSet1.next()) {

                    int primaryNumber = resultSet1.getInt("uc.is_primary");
                    if (primaryNumber == 0) {
                        primary = "NO";
                    } else {
                        primary = "YES";
                    }

                    resultMessage += resultSet1.getInt("uc.id") + "|"
                            + resultSet1.getInt("u.usr_id") + "|"
                            //      + dsdp.getCategoryName(resultSet1.getInt("uc.cat_type")) + "|"
                            + resultSet1.getString("name") + "|"
                            + primary + "|"
                            + resultSet1.getString("uc.status") + "|"
                            + dsdp.getUserNameByUserId(resultSet1.getInt("uc.created_by")) + "^";
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

    /**
     * *************************************
     *
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getHomeRedirectSearchDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        String resultString = "";
        Statement statement = null;
        String queryString = "";
        int result;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            queryString = "SELECT h.id,a.account_name,h.type_of_user,r.role_name,h.action_name,h.STATUS,h.description "
                    + "FROM home_redirect_action h "
                    + "LEFT OUTER JOIN accounts a ON(a.account_id=h.org_id) "
                    + "LEFT OUTER JOIN roles r ON(r.role_id=h.primaryrole) "
                    + "where 1=1 ";

            if (!"-1".equalsIgnoreCase(userAjaxHandlerAction.getAccountName())) {
                queryString = queryString + " AND h.org_id= '" + userAjaxHandlerAction.getAccountName() + "'  ";
            }
            if (!"-1".equalsIgnoreCase(userAjaxHandlerAction.getPrimaryRole())) {
                queryString = queryString + " AND h.primaryrole= '" + userAjaxHandlerAction.getPrimaryRole() + "'  ";
            }
            if (!"-1".equalsIgnoreCase(userAjaxHandlerAction.getTypeOfUser())) {
                queryString = queryString + " AND h.type_of_user= '" + userAjaxHandlerAction.getTypeOfUser() + "'  ";
            }
            System.out.println("the query is-->" + queryString);
            resultSet = statement.executeQuery(queryString);
            String typeOfUser = "";
            while (resultSet.next()) {
                if (resultSet.getString("type_of_user").equalsIgnoreCase("SA")) {
                    typeOfUser = "Site Admin";
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("AC")) {
                    typeOfUser = "Customer";
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("VC")) {
                    typeOfUser = "Vendor";
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("E")) {
                    typeOfUser = "Employee";
                } else if (resultSet.getString("type_of_user").equalsIgnoreCase("CO")) {
                    typeOfUser = "Consultant";
                } else {
                    typeOfUser = "";
                }
                resultString += resultSet.getString("id") + "|"
                        + resultSet.getString("account_name") + "|"
                        + typeOfUser + "|"
                        + resultSet.getString("role_name") + "|"
                        + resultSet.getString("action_name") + "|"
                        + resultSet.getString("description") + "|"
                        + resultSet.getString("STATUS") + "^";
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
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getAccountNames(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        boolean isGetting = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        if (userAjaxHandlerAction.getAccountType().equalsIgnoreCase("AC")) {
            queryString = "SELECT a.account_name,a.account_id "
                    + "FROM accounts a "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                    + "WHERE account_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
                    + "AND a.STATUS='Active' "
                    + "AND o.acc_type=1 "
                    + "AND type_of_relation='C'"
                    + "LIMIT 30";
        } else if (userAjaxHandlerAction.getAccountType().equalsIgnoreCase("VC")) {
            queryString = "SELECT a.account_name,a.account_id "
                    + "FROM accounts a "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                    + "WHERE account_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
                    + "AND a.STATUS='Active' "
                    + "AND o.acc_type=5 "
                    + "LIMIT 30";
        } else if (userAjaxHandlerAction.getAccountType().equalsIgnoreCase("M")) {
            queryString = "SELECT a.account_name,a.account_id "
                    + "FROM accounts a "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                    + "WHERE account_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
                    + "AND a.STATUS='Active' "
                    + "AND o.acc_type=1 "
                    + "AND type_of_relation='M'"
                    + "LIMIT 30";
        } else {
            queryString = "SELECT a.account_name,a.account_id "
                    + "FROM accounts a "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                    + "WHERE account_name LIKE '" + userAjaxHandlerAction.getEmpName() + "%' "
                    + "AND a.STATUS='Active' "
                    + "LIMIT 30";
        }

        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("query-->getEmployeeDetails" + queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();

            int count = 0;

            sb.append("<xml version=\"1.0\">");
            sb.append("<EMPLOYEES>");
            while (resultSet.next()) {
                sb.append("<EMPLOYEE><VALID>true</VALID>");

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
                sb.append("<EMPID>" + resultSet.getInt(2) + "</EMPID>");
                sb.append("</EMPLOYEE>");
                isGetting = true;
                count++;
            }

            if (!isGetting) {
                //sb.append("<EMPLOYEES>" + sb.toString() + "</EMPLOYEES>");
                //} else {
                isGetting = false;
                //nothing to show
                //  response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                sb.append("<EMPLOYEE><VALID>false</VALID></EMPLOYEE>");
            }
            sb.append("</EMPLOYEES>");
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

    /**
     * *************************************
     *
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String storeAddOrEditHomeRedirectDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        String resultString = "";
        Statement statement = null;
        String queryString = "";
        int result = 0;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            if (userAjaxHandlerAction.getHomeRedirectActionId() > 0) {
                queryString = "UPDATE home_redirect_action SET "
                        + "org_id=" + userAjaxHandlerAction.getAccountId() + ","
                        + "type_of_user='" + userAjaxHandlerAction.getTypeOfUser() + "',"
                        + "primaryrole=" + userAjaxHandlerAction.getRoleName() + " ,"
                        + "action_name='" + userAjaxHandlerAction.getActionName() + "',"
                        + "STATUS='" + userAjaxHandlerAction.getHomeRedirectStatus() + "',"
                        + "description='" + userAjaxHandlerAction.getHomeRedirectDescription() + "' "
                        + "WHERE id=" + userAjaxHandlerAction.getHomeRedirectActionId() + "";
            } else {
                queryString = "INSERT INTO home_redirect_action"
                        + "(org_id,type_of_user,primaryrole,action_name,description,STATUS,created_by) "
                        + "VALUES("
                        + "" + userAjaxHandlerAction.getAccountId() + ","
                        + "'" + userAjaxHandlerAction.getTypeOfUser() + "',"
                        + "" + userAjaxHandlerAction.getRoleName() + ","
                        + "'" + userAjaxHandlerAction.getActionName() + "',"
                        + "'" + userAjaxHandlerAction.getHomeRedirectDescription() + "',"
                        + "'Active',"
                        + "" + userAjaxHandlerAction.getUserSessionId() + ""
                        + ")";
            }



            System.out.println("the storeAddOrEditHomeRedirectDetails query is-->" + queryString);
            result = statement.executeUpdate(queryString);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>result>>" + result);
            if (result > 0) {
                resultString = "Success";
            } else {
                resultString = "Fail";
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>resultString>>" + resultString);
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
     * @getLeavesListDetails() getting leaves details of user
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:10/may/2015
     *
     *
     **************************************
     *
     */
    public String getHomeRedirectCommentsDetails(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {
        String resultString = "";
        Statement statement = null;
        String queryString = "";
        int result = 0;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            queryString = "SELECT description FROM home_redirect_action WHERE id=" + userAjaxHandlerAction.getHomeRedirectActionId();

            System.out.println("the getHomeRedirectCommentsDetails query is-->" + queryString);
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getString("description");
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>result>>" + result);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>resultString>>" + resultString);
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

    public String getCategoryList(UserAjaxHandlerAction aThis) throws ServiceLocatorException {
        String result = "";
        Map map1;
        map1 = com.mss.msp.util.DataSourceDataProvider.getInstance().getRequiteCategory(aThis.getUsrCatType());
        Map map;
        map = map1;
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            // System.out.println("Key = " + key + ", Value = " + value);
            result += key + "," + value + "^";
        }

        return result;
    }

    public String getEmpCategoryNames(UserAjaxHandlerAction userAjaxHandlerAction) throws ServiceLocatorException {

        String resultString = "";
        Statement statement = null;
        String queryString = "";
        int result = 0;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("userAjaxHandlerAction.getCategoryList()--->" + userAjaxHandlerAction.getCategoryNamesList());
            queryString = "SELECT catName,grpid FROM lkusr_grpcategory WHERE grpcategory IN (" + userAjaxHandlerAction.getCategoryNamesList() + ")";

            System.out.println("the getEmpCategoryNames query is-->" + queryString);
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString += resultSet.getInt("grpid") + "|"
                        + resultSet.getString("catName") + "^";
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>result>>" + result);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>resultString>>" + resultString);
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
}
