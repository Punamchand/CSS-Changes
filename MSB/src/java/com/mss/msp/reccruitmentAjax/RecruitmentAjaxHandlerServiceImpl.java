/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.reccruitmentAjax;

import com.mss.msp.recruitment.ConsultantVTO;
import com.mss.msp.recruitment.RecruitmentAction;
import com.mss.msp.security.SecurityServiceProvider;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import com.mss.msp.util.DateUtility;
import com.mss.msp.util.Properties;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author NagireddySeerapu
 */
public class RecruitmentAjaxHandlerServiceImpl implements RecruitmentAjaxHandlerService {

    private Connection connection;

    /**
     * *****************************************************************************
     * Date : May 5 2015
     *
     * Author : Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * getAddedConsultantDetails() method is used to enter the consultant record
     * into database using stored procedure
     *
     * *****************************************************************************
     */
    public int getAddedConsultantDetails(RecruitmentAjaxHandlerAction raha, int orgId) throws ServiceLocatorException {
        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int addResult = 0;
        boolean isExceute = false;
        DateUtility dateUtility = new DateUtility();
        String availableDate, dobDate;
        try {
            availableDate = dateUtility.getInstance().convertStringToMySQLDate(raha.getCnslt_add_date());
            dobDate = dateUtility.getInstance().convertStringToMySQLDate(raha.getCnslt_dob());
            System.out.println("---------------------In Impl class------------");
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("****************** ENTERED INTO THE TRY BLOCK *******************");
            callableStatement = connection.prepareCall("{CALL addConsultant(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            System.out.println("ORGID--------->>>>>>" + orgId);
            callableStatement.setInt(1, orgId);
            callableStatement.setString(2, raha.getCnslt_email());
            callableStatement.setString(3, availableDate);
            callableStatement.setString(4, raha.getCnslt_available());
            callableStatement.setString(5, raha.getCnslt_fstname());
            callableStatement.setString(6, raha.getCnslt_lstname());
            callableStatement.setString(7, raha.getCnslt_midname());
            callableStatement.setString(8, raha.getCnslt_gender());
            callableStatement.setString(9, dobDate);
            callableStatement.setString(10, raha.getCnslt_mStatus());
            callableStatement.setString(11, raha.getCnslt_homePhone());
            callableStatement.setString(12, raha.getCnslt_mobileNo());
            callableStatement.setInt(13, raha.getCnslt_lcountry());
            callableStatement.setString(14, raha.getAddAddressFlag());
            callableStatement.setString(15, raha.getAddConsult_Address());
            callableStatement.setString(16, raha.getAddConsult_Address2());
            callableStatement.setString(17, raha.getAddConsult_City());
            callableStatement.setString(18, raha.getAddConsult_Country());
            callableStatement.setInt(19, raha.getAddConsult_State());
            callableStatement.setString(20, raha.getAddConsult_Zip());
            callableStatement.setString(21, raha.getAddConsult_Phone());
            callableStatement.setString(22, raha.getAddAddressFlag());
            callableStatement.setString(23, raha.getAddConsult_CAddress());
            callableStatement.setString(24, raha.getAddConsult_CAddress2());
            callableStatement.setString(25, raha.getAddConsult_CCity());
            callableStatement.setString(26, raha.getAddConsult_CCountry());
            callableStatement.setInt(27, raha.getAddConsult_CState());
            callableStatement.setString(28, raha.getAddConsult_CZip());
            callableStatement.setString(29, raha.getAddConsult_CPhone());

            callableStatement.setInt(30, raha.getCnslt_industry());
            callableStatement.setInt(31, raha.getCnslt_organization());
            callableStatement.setString(32, raha.getCnslt_jobTitle());

            callableStatement.setString(33, raha.getCnslt_salary());
            callableStatement.setString(34, raha.getCnslt_experience());
            callableStatement.setString(35, raha.getCnslt_workPhone());

            callableStatement.setInt(36, raha.getCnslt_pcountry());
            callableStatement.setInt(37, raha.getCnslt_preferredState());
            callableStatement.setInt(38, raha.getCnslt_wcountry());

            callableStatement.setString(39, raha.getCnslt_description());
            callableStatement.setString(40, raha.getCnslt_comments());
            callableStatement.setString(41, raha.getCnslt_referredBy());
            //  System.out.println("after 1st valueeeeeeeeeeeeeee");

            isExceute = callableStatement.execute();
            System.out.println("Execute=========>" + isExceute);
            addResult = callableStatement.getInt(42);
            if (addResult > 0) {
                System.out.println("****************** in impl result>0  after adding:::::::::" + addResult);
            } else {
                System.out.println("****************** in impl result after adding:::::::::" + addResult);
            }


        } catch (Exception sqe) {
            sqe.printStackTrace();
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
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return addResult;
    }

    /**
     * ****************************************************************************
     * Date : April 20 2015
     *
     * Author : triveni niddara <tniddara@miraclesoft.com>
     *
     * getEmployeeLeaves method can be used to retrive existing leaves data by
     * using leave id, And returns EmpLeaves Object for the respected user
     * *****************************************************************************
     */
    public String getAttachmentDetails(HttpServletRequest httpServletRequest, RecruitmentAjaxHandlerAction recruitmentajaxhandleraction) throws ServiceLocatorException {
        ArrayList<ConsultantVTO> consultlist = new ArrayList<ConsultantVTO>();

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        String queryString = "";
        String resultString = "";

        ConsultantVTO consult = new ConsultantVTO();

        try {
            queryString = "SELECT * FROM acc_rec_attachment WHERE object_type='CV' AND object_id=? order by status";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(queryString);

            preparedStatement.setInt(1, recruitmentajaxhandleraction.getConsult_id());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                consult.setConsult_acc_attachment_id(resultSet.getString("acc_attachment_id"));
                consult.setConsult_object_id(resultSet.getInt("object_id"));
                consult.setConsult_attachment_name(resultSet.getString("attachment_name"));
                consult.setConsult_object_type(resultSet.getString("object_type"));
                consult.setConsult_attachment_path(resultSet.getString("attachment_path"));
                consult.setConsult_attachment_created_date(com.mss.msp.util.DateUtility.getInstance().convertToviewFormat(resultSet.getString("opp_created_date")));

                consult.setConsult_attachment_status(resultSet.getString("status"));

                resultString += consult.getConsult_attachment_name() + "|" + consult.getConsult_object_type() + '|' + consult.getConsult_attachment_path() + "|" + consult.getConsult_attachment_created_date() + "|" + consult.getConsult_acc_attachment_id() + "|" + consult.getConsult_attachment_status() + '^';

                System.out.println("-----attachment values---->" + consult.getConsult_acc_attachment_id() + consult.getConsult_object_id() + consult.getConsult_attachment_path() + consult.getConsult_attachment_name() + consult.getConsult_attachment_status());


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
        return resultString;
    }

    /**
     * ****************************************************************************
     * Date : May 19 2015
     *
     * Author : Aklakh Ahmad <mahmad@miraclesoft.com>
     *
     * saveConsultantLoginDetails() method can be used to save the login details
     * of consultant
     * *****************************************************************************
     */
    public String saveConsultantLoginDetails(int consult_id, int UserSessionId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        boolean isExceute = false;
        int updatedRows = 0;

//        int i = getConsultantCount(consult_id);
//        if (i == 1) {
//            resultString = "Consultant already Registered!";
//        } else {
        String query = "select first_name,last_name,email1,cur_status from consultants where consultant_id=" + consult_id;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            String status = "";
            String email = "";
            int createdBy = 0;
            String firstName = "";
            String lastName = "";
            while (resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                status = resultSet.getString("cur_status");
                email = resultSet.getString("email1");
                // createdBy = resultSet.getInt("created_by");

            }
            if ("Active".equalsIgnoreCase(status)) {

                String plainPassword = SecurityServiceProvider.generateRandamSecurityKey(6, 6, 1, 1, 0);
                String pwdSalt = SecurityServiceProvider.generateRandamSecurityKey(10, 10, 2, 3, 3);
                String encPwd = SecurityServiceProvider.getEncrypt(plainPassword, pwdSalt);
                callableStatement = connection.prepareCall("{CALL addConsultantLoginDetails(?,?,?,?,?,?,?)}");

                System.out.println("password" + plainPassword);
                callableStatement.setString(1, status);
                callableStatement.setInt(2, consult_id);
                callableStatement.setString(3, email);
                System.out.println("status" + status);
                System.out.println("email" + email);

                //  System.out.println("here we print after date changing...........");
                callableStatement.setString(4, pwdSalt);
                callableStatement.setString(5, encPwd);
                callableStatement.setInt(6, UserSessionId);
                callableStatement.registerOutParameter(7, Types.INTEGER);
                //  System.out.println("hello here print after prepare call parameter ");

                isExceute = callableStatement.execute();
                updatedRows = callableStatement.getInt(7);
                if (updatedRows > 0) {

                    doAddMailManagerStatusActivation(email, firstName, lastName, plainPassword, "serviceBayLoginCredentialsForConsultant", UserSessionId);

                    System.out.println("password" + plainPassword);
                    resultString = "Consultant login credentials sent succesfully!";
                    System.out.println("statement okay");
                }

            } else {

                resultString = "Consultant  is not in Active";
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
        //}
        return resultString;

    }

    // Add by Aklakh
    public int getConsultantCount(int consult_id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";


        int i = 0;
        try {

            queryString = "select consultant_id from consultant_reg where consultant_id=" + consult_id;


            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after statements ");
            while (resultSet.next()) {
                i++;
                System.out.println("count" + i);
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return i;
    }

// Add By Aklakh
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

    /**
     * ****************************************************************************
     * Date : May 19 2015
     *
     * Author : ramakrishna<lankireddy@miraclesoft.com>
     *
     * getConsultantTechReviews() method can be used to save the login details
     * of consultant
     * *****************************************************************************
     */
    public String getConsultantTechReviews(RecruitmentAjaxHandlerAction recruitmentAjaxHandlerAction) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";

        try {
            queryString = "SELECT forwarded_to_name1,forwarded_to1,id,review_type,forwarded_to,consultant_id,req_id,scheduled_date,forwarded_to_name,comments,techie_title,STATUS "
                    + "FROM con_techreview "
                    + "WHERE consultant_id=" + recruitmentAjaxHandlerAction.getConsult_id() + " "
                    + "AND req_id=" + recruitmentAjaxHandlerAction.getRequirementId() + " ";
            if (!"".equals(recruitmentAjaxHandlerAction.getInterviewDate())) {
                queryString = queryString + " AND scheduled_date= '" + com.mss.msp.util.DateUtility.getInstance().convertStringToMySQLDate1(recruitmentAjaxHandlerAction.getInterviewDate()) + "' ";
            }
            if (!"0".equals(recruitmentAjaxHandlerAction.getEmpIdTechReview())) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>EMP ID>>>>>>>>>>" + recruitmentAjaxHandlerAction.getEmpIdTechReview());
                queryString = queryString + " AND forwarded_to= " + recruitmentAjaxHandlerAction.getEmpIdTechReview() + " ";
            }

            System.out.println("gueryString>>>> " + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                resultString += resultSet.getString("consultant_id") + "|"
                        + resultSet.getString("req_id") + "|"
                        + com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("scheduled_date")) + "|"
                        + resultSet.getString("forwarded_to_name") + "|"
                        + resultSet.getString("comments") + "|"
                        + resultSet.getString("techie_title") + "|"
                        + resultSet.getString("STATUS") + "|"
                        + resultSet.getString("forwarded_to") + "|"
                        + resultSet.getString("id") + "|"
                        + resultSet.getString("review_type") + "|"
                        + resultSet.getString("forwarded_to1") + "|"
                        + resultSet.getString("forwarded_to_name1") + "^";
            }
            System.out.println("resultString>>>" + resultString);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * ****************************************************************************
     * Date : May 19 2015
     *
     * Author : ramakrishna<lankireddy@miraclesoft.com>
     *
     * getConsultantTechReviews() method can be used to save the login details
     * of consultant
     * *****************************************************************************
     */
    public String techReviewCommentsOverlay(RecruitmentAjaxHandlerAction recruitmentAjaxHandlerAction) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";

        try {
            queryString = "SELECT comments FROM con_techreview WHERE id=" + recruitmentAjaxHandlerAction.getConTechReviewId();
            System.out.println("gueryString>>>> " + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                resultString = resultSet.getString("comments");
            }
            System.out.println("resultString>>>" + resultString);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * ****************************************************************************
     * Date : May 19 2015
     *
     * Author : ramakrishna<lankireddy@miraclesoft.com>
     *
     * getConsultantTechReviews() method can be used to save the login details
     * of consultant
     * *****************************************************************************
     */
    public String getTechReviewResultOnOverlay(RecruitmentAjaxHandlerAction recruitmentAjaxHandlerAction) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";

        try {
            queryString = "SELECT CONCAT(c.first_name,'.',c.last_name) AS NAME,c.email1,c.phone1,cd.job_title,"
                    + "rcr.con_skill,cr.scheduled_date,ar.acc_attachment_id,cr.tech_skills,"
                    + "cr.domain_skills,cr.commmunication_skills,cr.comments,cr.STATUS  "
                    + "FROM users c "
                    + "LEFT OUTER JOIN con_techreview cr ON(cr.consultant_id=c.usr_id)"
                    + "LEFT OUTER JOIN acc_rec_attachment ar ON(ar.object_id=c.usr_id)"
                    + "LEFT OUTER JOIN usr_details cd ON(cd.usr_id=c.usr_id) "
                    // + "LEFT OUTER JOIN usr_miscellaneous um ON(um.usr_id=" + recruitmentAjaxHandlerAction.getForwardedToId() + ") "
                    // + "LEFT OUTER JOIN title t ON(t.title_id=um.title_id) "
                    + "LEFT OUTER JOIN req_con_rel rcr ON(rcr.consultantId=c.usr_id)"
                    + "WHERE c.usr_id=" + recruitmentAjaxHandlerAction.getConsult_id() + " "
                    + "AND ar.STATUS='active' "
                    + "AND cr.req_id=" + recruitmentAjaxHandlerAction.getRequirementId() + " "
                    + "AND review_type='" + recruitmentAjaxHandlerAction.getReviewType() + "'";
            System.out.println("gueryString>>>> " + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                resultString = resultSet.getString("NAME") + "|" + resultSet.getString("email1") + "|" + resultSet.getString("phone1") + "|" + com.mss.msp.util.DateUtility.getInstance().convertDateToViewInDashformat(resultSet.getDate("scheduled_date")) + "|" + resultSet.getString("acc_attachment_id") + "|" + resultSet.getString("job_title") + "|" + resultSet.getString("con_skill") + "|" + resultSet.getString("tech_skills") + "|" + resultSet.getString("domain_skills") + "|" + resultSet.getString("commmunication_skills") + "|" + resultSet.getString("comments")  + "|" + resultSet.getString("STATUS") + "^";
            }
            System.out.println("resultString>>>" + resultString);
        } catch (Exception sqe) {
            sqe.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return resultString;
    }
}
