/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.invoice.sow;

import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataUtility;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author miracle
 */
public class SOWServiceImpl implements SOWService {

    private Connection connection;
    private Statement statement;

    public List getSowDetails(SOWAction sowAction) throws ServiceLocatorException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>IN IMPL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        ArrayList<SowVTO> listVto = new ArrayList<SowVTO>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlquery = "";
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>TYPE OF USER>>>>>>>>>>>>>" + sowAction.getTypeOfUser());
            connection = ConnectionProvider.getInstance().getConnection();
            if ("VC".equalsIgnoreCase(sowAction.getTypeOfUser())) {
                sqlquery = "SELECT rcr.reqId,r.req_name,ar.account_name,rcr.consultantId,"
                        + "CONCAT(u.first_name,'.',u.last_name) AS consultantName,u.email1,"
                        + "r.req_created_date,rcr.tech_review_date,rcr.createdbyOrgId AS vendorOrgId,"
                        + "r.acc_id,rcr.STATUS,rcr.rate_salary,av.account_name AS vendorName "
                        + "FROM req_con_rel rcr "
                        + "LEFT OUTER JOIN acc_requirements r ON(r.requirement_id=rcr.reqId) "
                        + "LEFT OUTER JOIN accounts ar ON(ar.account_id=r.acc_id) "
                        + "LEFT OUTER JOIN users u ON(u.usr_id=rcr.consultantId) "
                        + "LEFT OUTER JOIN accounts av ON(av.account_id=rcr.createdbyOrgId)"
                        + "WHERE (rcr.STATUS LIKE 'selected' OR rcr.STATUS like '%SOW%')"
                        + "AND rcr.createdbyOrgId=" + sowAction.getSessionOrgId() + " ";
            } else {
                sqlquery = "SELECT rcr.reqId,r.req_name,ar.account_name,rcr.consultantId,"
                        + "CONCAT(u.first_name,'.',u.last_name) AS consultantName,u.email1,"
                        + "r.req_created_date,rcr.tech_review_date,rcr.createdbyOrgId AS vendorOrgId,"
                        + "r.acc_id,rcr.STATUS,rcr.rate_salary,av.account_name AS vendorName "
                        + "FROM req_con_rel rcr "
                        + "LEFT OUTER JOIN acc_requirements r ON(r.requirement_id=rcr.reqId) "
                        + "LEFT OUTER JOIN accounts ar ON(ar.account_id=r.acc_id) "
                        + "LEFT OUTER JOIN users u ON(u.usr_id=rcr.consultantId) "
                        + "LEFT OUTER JOIN accounts av ON(av.account_id=rcr.createdbyOrgId)"
                        + "WHERE rcr.STATUS IN('SOWSubmitted','SOWApproved','SOWReleased','SOWDenied','SOWRejected')"
                        + "AND r.acc_id=" + sowAction.getSessionOrgId() + " ";
            }
            System.out.println(">>>>QueryString>>>>>" + sqlquery);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlquery);
            while (resultSet.next()) {
                SowVTO sowVto = new SowVTO();
                sowVto.setRequirementId(resultSet.getString("reqId"));
                sowVto.setRequirementName(resultSet.getString("req_name"));
                sowVto.setCustomerName(resultSet.getString("account_name"));
                sowVto.setConsultantId(resultSet.getString("consultantId"));
                sowVto.setConsultantName(resultSet.getString("consultantName"));
                sowVto.setRateSalary(resultSet.getString("rate_salary"));
                sowVto.setReqCreatedDate(com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("req_created_date")));
                sowVto.setTechReviewDate(com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("tech_review_date")));
                sowVto.setVendorId(resultSet.getString("vendorOrgId"));
                sowVto.setCustomerId(resultSet.getString("acc_id"));
                sowVto.setVendorName(resultSet.getString("vendorName"));
                sowVto.setReviewStatus(resultSet.getString("STATUS"));
                sowVto.setConsultantEmailId(resultSet.getString("email1"));

                listVto.add(sowVto);
            }
        } catch (SQLException ex) {
            listVto = null;
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
                if (statement != null) {
                    statement.close();
                    statement = null;
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
        }
        return listVto;
    }

    public List getSOWSearchResults(SOWAction sowAction) throws ServiceLocatorException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>IN IMPL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        ArrayList<SowVTO> listVto = new ArrayList<SowVTO>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>TYPE OF USER>>>>>>>>>>>>>" + sowAction.getTypeOfUser());
            connection = ConnectionProvider.getInstance().getConnection();
            if ("VC".equalsIgnoreCase(sowAction.getTypeOfUser())) {
                queryString = "SELECT rcr.reqId,r.req_name,ar.account_name,rcr.consultantId,"
                        + "CONCAT(u.first_name,'.',u.last_name) AS consultantName,u.email1,"
                        + "r.req_created_date,rcr.tech_review_date,rcr.createdbyOrgId AS vendorOrgId,"
                        + "r.acc_id,rcr.STATUS,rcr.rate_salary,av.account_name AS vendorName "
                        + "FROM req_con_rel rcr "
                        + "LEFT OUTER JOIN acc_requirements r ON(r.requirement_id=rcr.reqId) "
                        + "LEFT OUTER JOIN accounts ar ON(ar.account_id=r.acc_id) "
                        + "LEFT OUTER JOIN users u ON(u.usr_id=rcr.consultantId) "
                        + "LEFT OUTER JOIN accounts av ON(av.account_id=rcr.createdbyOrgId)"
                        + "WHERE (rcr.STATUS LIKE 'selected' OR rcr.STATUS like '%SOW%')"
                        + "AND rcr.createdbyOrgId=" + sowAction.getSessionOrgId() + " ";
            } else {
                queryString = "SELECT rcr.reqId,r.req_name,ar.account_name,rcr.consultantId,"
                        + "CONCAT(u.first_name,'.',u.last_name) AS consultantName,u.email1,"
                        + "r.req_created_date,rcr.tech_review_date,rcr.createdbyOrgId AS vendorOrgId,"
                        + "r.acc_id,rcr.STATUS,rcr.rate_salary,av.account_name AS vendorName "
                        + "FROM req_con_rel rcr "
                        + "LEFT OUTER JOIN acc_requirements r ON(r.requirement_id=rcr.reqId) "
                        + "LEFT OUTER JOIN accounts ar ON(ar.account_id=r.acc_id) "
                        + "LEFT OUTER JOIN users u ON(u.usr_id=rcr.consultantId) "
                        + "LEFT OUTER JOIN accounts av ON(av.account_id=rcr.createdbyOrgId)"
                        + "WHERE rcr.STATUS IN('SOWSubmitted','SOWApproved','SOWReleased','SOWDenied','SOWRejected')"
                        + "AND r.acc_id=" + sowAction.getSessionOrgId() + " ";
            }
            if (!"".equals(sowAction.getConsultantName())) {

                queryString = queryString + " AND (u.first_name LIKE '" + sowAction.getConsultantName() + "%' "
                        + "OR u.last_name LIKE '" + sowAction.getConsultantName() + "%') ";
            }
            if (!"".equals(sowAction.getRequirementName())) {
                queryString = queryString + " AND r.req_name LIKE '%" + sowAction.getRequirementName() + "%' ";
            }
            if ("AC".equalsIgnoreCase(sowAction.getTypeOfUser())) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>customer logged in");
                if (!"".equals(sowAction.getVendorName())) {
                    queryString = queryString + " AND av.account_name LIKE '%" + sowAction.getVendorName() + "%' ";
                }
            } else if ("VC".equalsIgnoreCase(sowAction.getTypeOfUser())) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>vendor logged in");
                if (!"".equals(sowAction.getCustomerName())) {
                    queryString = queryString + " AND ar.account_name LIKE '%" + sowAction.getCustomerName() + "%' ";
                }
            } else {
            }
            if (!"-1".equalsIgnoreCase(sowAction.getStatus())) {
                queryString = queryString + "AND rcr.STATUS LIKE '" + sowAction.getStatus() + "'  ";
            }
            System.out.println(">>>>QueryString>>>>>" + queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                SowVTO sowVto = new SowVTO();
                sowVto.setRequirementId(resultSet.getString("reqId"));
                sowVto.setRequirementName(resultSet.getString("req_name"));
                sowVto.setCustomerName(resultSet.getString("account_name"));
                sowVto.setVendorName(resultSet.getString("vendorName"));
                sowVto.setConsultantId(resultSet.getString("consultantId"));
                sowVto.setConsultantName(resultSet.getString("consultantName"));
                sowVto.setRateSalary(resultSet.getString("rate_salary"));
                sowVto.setReqCreatedDate(com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("req_created_date")));
                sowVto.setTechReviewDate(com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("tech_review_date")));
                sowVto.setVendorId(resultSet.getString("vendorOrgId"));
                sowVto.setCustomerId(resultSet.getString("acc_id"));
                sowVto.setReviewStatus(resultSet.getString("STATUS"));
                sowVto.setConsultantEmailId(resultSet.getString("email1"));

                listVto.add(sowVto);
            }
        } catch (SQLException ex) {
            listVto = null;
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
                if (statement != null) {
                    statement.close();
                    statement = null;
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
        }
        return listVto;
    }

    public String doAddUpdateSOWDetails(SOWAction sowAction) throws ServiceLocatorException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>IN IMPL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlquery = "";
        String result = "";
        boolean isExceute = false;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("****************** ENTERED INTO THE TRY BLOCK *******************");
            callableStatement = connection.prepareCall("{CALL sowAddUpdate(?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, sowAction.getUserSeessionId());
            System.out.println(">>>>>>>>>>>1");
            callableStatement.setInt(2, Integer.parseInt(sowAction.getConsultantId()));
            System.out.println(">>>>>>>>>>>2");
            callableStatement.setInt(3, Integer.parseInt(sowAction.getCustomerId()));
            System.out.println(">>>>>>>>>>>3");
            callableStatement.setInt(4, Integer.parseInt(sowAction.getVendorId()));
            System.out.println(">>>>>>>>>>>4");
            callableStatement.setInt(5, Integer.parseInt(sowAction.getRequirementId()));
            System.out.println(">>>>>>>>>>>5 "+sowAction.getRateSalary());
            callableStatement.setDouble(6, Double.parseDouble(sowAction.getRateSalary()));
            System.out.println(">>>>>>>>>>>6");
            callableStatement.setString(7, sowAction.getStatus());
            System.out.println(">>>>>>>>>>>7");
            callableStatement.setInt(8, Integer.parseInt(sowAction.getPayTerms()));
            System.out.println(">>>>>>>>>>>8");
            callableStatement.setString(9, sowAction.getVendorComments());
            System.out.println(">>>>>>>>>>>9");
            callableStatement.setString(10, sowAction.getCustomerComments());
            System.out.println(">>>>>>>>>>>10");
            callableStatement.registerOutParameter(11, Types.VARCHAR);
            System.out.println(">>>>>>>>>>>11");
            isExceute = callableStatement.execute();
            result = callableStatement.getString(11);
            System.out.println("result is>>>>>>>" + result);
            if (result.equalsIgnoreCase("Exist")) {
                sowAction.setResultMessage("Record Updated Successfully!");
                System.out.println("****************** in impl result after Procedure:::::::::" + result);
            } else if (result.equalsIgnoreCase("Success")) {
                sowAction.setResultMessage("Record Inserted Successfully!");
                System.out.println("****************** in impl result after Procedure:::::::::" + result);
            }
        } catch (SQLException ex) {
            System.out.println("doAddUpdateSOW>>>>" + ex.getMessage());
            ex.printStackTrace();
        }catch(Exception e){
            System.out.println(">>>Exception>>>"+e.getMessage());
        }
            finally {

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
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                try {
                    throw new ServiceLocatorException(ex);
                } catch (ServiceLocatorException ex1) {
                    System.out.println("errror-->" + ex1.getMessage());
                    ex1.printStackTrace();
                }
            }
        }
        return result;
    }

    public String getSOWEditDetails(SOWAction sowAction) throws ServiceLocatorException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>IN IMPL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>TYPE OF USER>>>>>>>>>>>>>" + sowAction.getTypeOfUser());
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println(">" + sowAction.getConsultantId() + ">" + sowAction.getRequirementId() + ">" + sowAction.getConsultantName() + ">" + sowAction.getCustomerId());
            queryString = "SELECT customercomments,vendorcomments,payterms FROM usrsow WHERE usr_id=" + sowAction.getConsultantId() + " AND req_id=" + sowAction.getRequirementId();

            System.out.println(">>>>QueryString>>>>>" + queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                sowAction.setCustomerComments(resultSet.getString("customercomments"));
                sowAction.setVendorComments(resultSet.getString("vendorcomments"));
                sowAction.setPayTerms(resultSet.getString("payterms"));
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
            } catch (SQLException ex) {
                try {
                    throw new ServiceLocatorException(ex);
                } catch (ServiceLocatorException ex1) {
                    ex1.printStackTrace();
                }
            }
        }
        return resultString;
    }

    public int doAddSOWAttachment(SOWAction sowAction) throws ServiceLocatorException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>IN attachment IMPL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        int result = 0;
        try {
            String fpath = sowAction.getFilesPath();
            StringTokenizer st = new StringTokenizer(fpath);
            StringTokenizer st2 = new StringTokenizer(fpath, "\\");
            String finalPath = "";
            while (st2.hasMoreElements()) {
                //System.out.println(".............>>>>>>>>>>>"+st2.nextElement());
                finalPath = finalPath + st2.nextElement() + "\\" + "\\";
            }

            System.out.println(">>>>Length>>>" + finalPath.length());
            System.out.println(">>>>After removing last 2 chars>>>" + finalPath.substring(0, finalPath.length() - 2));
            sowAction.setFilesPath(finalPath.substring(0, finalPath.length() - 2));
            System.out.println("values in impl are:::::::::::" + sowAction.getConsultantId() + " " + sowAction.getFileFileName() + " " + sowAction.getFilesPath());
            connection = ConnectionProvider.getInstance().getConnection();
            queryString = "INSERT INTO "
                    + "acc_rec_attachment(object_id,object_type,attachment_name,attachment_path,STATUS,opp_created_by) "
                    + "VALUES(" + sowAction.getConsultantId() + ",'SOW'" + ",'" + sowAction.getFileFileName() + "'" + ",'" + sowAction.getFilesPath() + "'" + ",'Active'," + sowAction.getUserSeessionId() + ")";

            System.out.println(">>>>QueryString>>>>>" + queryString);
            statement = connection.createStatement();
            result = statement.executeUpdate(queryString);

        } catch (SQLException ex) {
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
                if (statement != null) {
                    statement.close();
                    statement = null;
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
        }
        return result;
    }

    public List getSOWAttachments(SOWAction sowAction) throws ServiceLocatorException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>IN IMPL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        ArrayList<SowVTO> listVto = new ArrayList<SowVTO>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlquery = "";
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>TYPE OF USER>>>>>>>>>>>>>" + sowAction.getTypeOfUser());
            connection = ConnectionProvider.getInstance().getConnection();
            sqlquery = "SELECT acc_attachment_id,object_id,CONCAT(u.first_name,'.',u.last_name) AS NAME,"
                    + "attachment_path,attachment_name,CONCAT(up.first_name,'.',up.last_name) AS uploadedBy "
                    + "FROM acc_rec_attachment "
                    + "LEFT OUTER JOIN users u ON(u.usr_id=object_id) "
                    + "LEFT OUTER JOIN users up ON(up.usr_id=opp_created_by) "
                    + "WHERE object_type='SOW' "
                    + "AND object_id=" + sowAction.getConsultantId();

            System.out.println(">>>>QueryString>>>>>" + sqlquery);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlquery);
            while (resultSet.next()) {
                SowVTO sowVto = new SowVTO();
                sowVto.setConsultantId(resultSet.getString("object_id"));
                sowVto.setConsultantName(resultSet.getString("NAME"));
                sowVto.setSowAttachmentName(resultSet.getString("attachment_name"));
                sowVto.setSowAttachmentPath(resultSet.getString("attachment_path"));
                sowVto.setSowAttachmentUploadedBy(resultSet.getString("uploadedBy"));
                sowVto.setSowAttachmentId(resultSet.getInt("acc_attachment_id"));
                listVto.add(sowVto);
            }
        } catch (SQLException ex) {
            listVto = null;
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
                if (statement != null) {
                    statement.close();
                    statement = null;
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
        }
        return listVto;
    }
}
