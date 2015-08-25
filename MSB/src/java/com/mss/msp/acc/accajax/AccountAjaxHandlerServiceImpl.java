/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.accajax;

import com.mss.msp.acc.Account;
import com.mss.msp.security.SecurityServiceProvider;
import com.mss.msp.usersdata.UserVTO;
import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DateUtility;
import com.mss.msp.util.HibernateServiceLocator;
import com.mss.msp.util.Properties;
import com.mss.msp.util.ServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author NagireddySeerapu
 */
public class AccountAjaxHandlerServiceImpl implements AccountAjaxHandlerService {

    private Connection connection;

    public Account ajaxAccountUpdate(Account account) {
        Session session = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            System.out.println("IN UPDATE AJAX SERVICE");
            connection = ConnectionProvider.getInstance().getConnection();
            //update


            session = HibernateServiceLocator.getInstance().getSession();
            List<Integer> q1 = session.createSQLQuery("SELECT id from lk_country where lk_country.country='" + account.getCountry() + "';").list();
            List<Integer> q2 = session.createSQLQuery("SELECT id from lk_states where lk_states.name='" + account.getState() + "';").list();
            List<Integer> q3 = session.createSQLQuery("SELECT id from lk_acc_industry_type where lk_acc_industry_type.acc_industry_name='" + account.getIndustry() + "';").list();
            List<Integer> q4 = session.createSQLQuery("SELECT id from lk_acc_type where lk_acc_type.acc_type_name='" + account.getType() + "';").list();
            System.out.println(q1.get(0) + " " + q2.get(0) + " " + q3.get(0) + " " + q4.get(0));


            preparedStatement = connection.prepareStatement("UPDATE accounts SET "
                    + "account_name = ? ," + //1
                    "status= ? ," + //2
                    "account_url= ? " + //3
                    "WHERE account_id = ?;" //4
                    );
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getStatus());
            preparedStatement.setString(3, account.getUrl());
            preparedStatement.setInt(4, account.getId());

            int x = preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("UPDATE acc_basic_info SET "
                    + "acc_phone = ? , " + //1
                    "acc_fax = ? , " + //2
                    "acc_industry_type = ? , " + //3
                    "acc_region = ? , " + //4
                    "acc_no_of_employees = ? , " + //5
                    "acc_revenue = ? , " + //6
                    "acc_it_budget = ? , " + //7
                    "acc_tax_id = ? , " + //8
                    "acc_description = ? , " + //9
                    "acc_stock_symbol = ? " + //10
                    "WHERE acc_id= ?");             //11
            preparedStatement.setString(1, "1231231233");//account.getPhone());
            preparedStatement.setString(2, "1231231233");//account.getFax());
            preparedStatement.setInt(3, q3.get(0));//LOOK UP
            preparedStatement.setString(4, account.getRegion());
            preparedStatement.setInt(5, account.getNoemp());
            preparedStatement.setInt(6, account.getRevenue().intValue());
            preparedStatement.setInt(7, account.getBudget().intValue());
            preparedStatement.setString(8, account.getTax_id());
            preparedStatement.setString(9, account.getDescription());
            preparedStatement.setString(10, account.getStockSymbol());
            preparedStatement.setInt(11, account.getId());
            x = preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("UPDATE acc_address SET " + //1
                    "acc_address1 = ? , " + //2
                    "acc_address2 = ? , " + //3
                    "acc_zip = ? , " + //4
                    "acc_country = ? ," + //5
                    "acc_state = ? " + //6
                    "WHERE acc_id= ? ");        //7
            preparedStatement.setString(1, account.getAddress1());
            preparedStatement.setString(2, account.getAddress2());
            preparedStatement.setString(3, account.getZip());
            preparedStatement.setInt(4, q1.get(0));
            preparedStatement.setInt(5, q2.get(0));
            preparedStatement.setInt(6, account.getId());
            x = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("UPDATE org_rel SET " + //1
                    "acc_type = ? WHERE org_id= ?");

            preparedStatement.setInt(1, q4.get(0));
            preparedStatement.setInt(2, account.getId());

            x = preparedStatement.executeUpdate();
//            session = HibernateServiceLocator.getInstance().getSession();
//             List<Integer> q1 = session.createSQLQuery("SELECT id from lk_country where lk_country.country='"+account.getCountry()+"';").list();
//             List<Integer> q2 = session.createSQLQuery("SELECT id from lk_states where lk_states.name='"+account.getStateName()+"';").list();
//             List<Integer> q3 = session.createSQLQuery("SELECT id from lk_acc_industry_type where lk_acc_industry_type.acc_industry_name='"+account.getIndustry()+"';").list();
//             List<Integer> q4 = session.createSQLQuery("SELECT id from lk_acc_type where lk_acc_type.acc_type_name='"+account.getAccountType()+"';").list();
//             System.out.println(q1.get(0)+" "+q2.get(0)+" "+q3.get(0)+" "+q4.get(0));
//            Transaction tx= session.beginTransaction();
//            Account old = account;//(Account)session.(Account.class, account.getId());
//            System.out.println("AFTER TRANSACTION====="+old);
//            old.setCountry_id(q1.get(0));
//            System.out.println("AFTER COUNTRY====="+old);
//            old.setState(q2.get(0).toString());
//            System.out.println("AFTER STATE====="+old);
//            old.setIndustry(q3.get(0).toString());
//            System.out.println("AFTER Industry====="+old);
//            old.setType(q4.get(0).toString());
//            System.out.println("AFTER Type====="+old);
//
//            old.setName(account.getName());
//            old.setUrl(account.getUrl());
//            old.setPhone(account.getPhone());
//            old.setFax(account.getFax());
//            old.setStatus(account.getStatus());
//            old.setAddress1(account.getAddress1());
//            old.setAddress2(account.getAddress2());
//            old.setZip(account.getZip());
//            old.setBudget(account.getBudget());
//            old.setRegion(account.getRegion());
//            old.setCountry(account.getCountry());
//            old.setStockSymbol(account.getStockSymbol());
//            old.setNoemp(account.getNoemp());
//            old.setRevenue(account.getRevenue());
//            old.setDescription(account.getDescription());
//
//            System.out.println("OLD====="+old);
//            session.saveOrUpdate(old);
//            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
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

            if (session != null) {
                try {
                    session.close();
                    session = null;
                } catch (HibernateException he) {
                    he.printStackTrace();
                }
            }
        }
        return account;
    }

    /**
     *
     * @param name the name to search for
     * @param id the id of the account trying to rename itself
     * @return boolean true if name exists and is not the current account name,
     * false if the account name does not exists or is the current account name.
     * @throws ServiceLocatorException
     */
    public boolean checkForAccountName(String name, int id) throws ServiceLocatorException {
        boolean exists = false;
        Session session = HibernateServiceLocator.getInstance().getSession();
        String q = "select distinct a.id, a.name from Account a where a.name=:accName";
        List<Object[]> likeAccounts = session.createQuery(q).setParameter("accName", name).list();
        if (likeAccounts.size() > 0) {
            exists = true;
        }
        try {
            // Closing hibernate session
            session.close();
            session = null;
        } catch (HibernateException he) {
            throw new ServiceLocatorException(he);
        } finally {
            if (session != null) {
                try {
                    session.close();
                    session = null;
                } catch (HibernateException he) {
                    he.printStackTrace();
                }
            }
        }
        //System.out.println("AJAX : CHECK FOR NAME:" + name + " IN USE :" + exists);
        return exists;
    }

    public boolean checkForAccountURL(String url, int id) throws ServiceLocatorException {
        boolean exists = false;
        Session session = HibernateServiceLocator.getInstance().getSession();
        String q = "select distinct a.id, a.url from Account a where a.url=:accurl";
        List<Object[]> likeAccounts = session.createQuery(q).setParameter("accurl", url).list();
        if (likeAccounts.size() > 0) {
            exists = true;
        } else {
            exists = false;
        }
        try {
            // Closing hibernate session
            session.close();
            session = null;
        } catch (HibernateException he) {
            throw new ServiceLocatorException(he);
        } finally {
            if (session != null) {
                try {
                    session.close();
                    session = null;
                } catch (HibernateException he) {
                    he.printStackTrace();
                }
            }
        }
        System.out.println("AJAX : CHECK FOR URL:" + url + " IN USE :" + exists);
        return exists;
    }

    public String getContactDetails(int searchOrgId, String typeOfAccount) {


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserVTO usersVTO = new UserVTO();
        int i = 0;
        try {

//            queryString = "SELECT u.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAME, t.title_name, u.email1,u.phone1 "
//                    + "FROM users u LEFT OUTER JOIN usr_miscellaneous ums ON (u.usr_id = ums.usr_id) LEFT OUTER JOIN title t ON(t.title_id=ums.title_id) "
//                    + "WHERE u.org_id='" + searchOrgId + "' AND type_of_user='" + typeOfAccount + "' AND u.cur_status  LIKE 'Active' ORDER BY NAME LIMIT 100";

            queryString = "SELECT u.cur_status,u.usr_id,CONCAT(u.first_name,'.',u.last_name) AS NAME,  u.email1,u.office_phone, u.designation  "
                    + "FROM users u   "
                    + "WHERE u.org_id='" + searchOrgId + "' AND type_of_user='" + typeOfAccount + "' AND u.cur_status  LIKE 'Active' ORDER BY NAME LIMIT 100";
            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after statements ");
            while (resultSet.next()) {

                System.out.println("In while qualification");

                usersVTO.setUsr_id(resultSet.getInt("usr_id"));
                usersVTO.setEmpName(resultSet.getString("name"));
                usersVTO.setEmail1(resultSet.getString("email1"));
                usersVTO.setWorkPhone(resultSet.getString("office_phone"));
                usersVTO.setStatus(resultSet.getString("cur_status"));

                resultString += usersVTO.getUsr_id() + "|" + usersVTO.getEmpName() + "|" + usersVTO.getStatus() + "|" + usersVTO.getEmail1() + "|" + usersVTO.getWorkPhone() + '^';

                System.out.println("---------------->" + resultString);

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

    public String saveUserContacts(int orgUserId, int userSessionId) throws ServiceLocatorException {

        Connection connection = null;
        Statement statement = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        boolean isExceute = false;
        int updatedRows = 0;

//        int i = getUserCount(orgUserId);
//        if (i == 1) {
//            resultString = "User contact is already Registered";
//        } else {
        String query = "select first_name,last_name,email1,cur_status from users where usr_id=" + orgUserId;
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
                callableStatement = connection.prepareCall("{CALL addContacts(?,?,?,?,?,?,?)}");

                System.out.println("password" + plainPassword);
                callableStatement.setString(1, status);
                callableStatement.setInt(2, orgUserId);
                callableStatement.setString(3, email);
                System.out.println("status" + status);
                System.out.println("email" + email);

                //  System.out.println("here we print after date changing...........");
                callableStatement.setString(4, pwdSalt);
                callableStatement.setString(5, encPwd);
                callableStatement.setInt(6, userSessionId);
                callableStatement.registerOutParameter(7, Types.INTEGER);
                //  System.out.println("hello here print after prepare call parameter ");

                isExceute = callableStatement.execute();
                updatedRows = callableStatement.getInt(7);
                if (updatedRows > 0) {

                    doAddMailManagerStatusActivation(email, firstName, lastName, plainPassword, "serviceBayLoginCredentialsForContact", userSessionId);

                    System.out.println("password" + plainPassword);
                    resultString = "Login credentials Send Succesfully to email";
                    System.out.println("statement okay");
                }

            } else {

                resultString = "User Contact is not in Active";
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
        // }
        return resultString;


    }

    public int getUserCount(int userId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";


        int i = 0;
        try {

            queryString = "select usr_id from usr_reg where usr_id=" + userId;


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

    public String getContactSearchResults(AccountAjaxHandler accountAjaxHandler, int orgUserId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserVTO usersVTO = new UserVTO();
        try {
            // queryString = "SELECT usr_id,concat(first_name,'.',last_name) as name,email1,phone1,cur_status FROM users WHERE org_id=" + orgUserId + " AND type_of_user='" + accountAjaxHandler.getTypeOfAccount() + "' ";
            queryString = "SELECT cur_status,usr_id,concat(first_name,'.',last_name) as name,designation,email1,office_phone FROM users WHERE org_id=" + orgUserId + " AND type_of_user='" + accountAjaxHandler.getTypeOfAccount() + "' ";

            if (!"".equals(accountAjaxHandler.getEmail())) {
                queryString = queryString + " and  email1 like '%" + accountAjaxHandler.getEmail() + "%' ";
            }
            if (!"".equals(accountAjaxHandler.getFirstName())) {
                queryString = queryString + " and first_name like '%" + accountAjaxHandler.getFirstName() + "%' ";
            }
            if (!"-1".equals(accountAjaxHandler.getLastName())) {
                queryString = queryString + " and last_name like '%" + accountAjaxHandler.getLastName() + "%' ";
            }
            if (!"-1".equalsIgnoreCase(accountAjaxHandler.getPhone())) {
                queryString = queryString + " and office_phone like '%" + accountAjaxHandler.getPhone() + "%' ";
            }
            if (!"DF".equalsIgnoreCase(accountAjaxHandler.getStatus())) {

                queryString = queryString + " and cur_status = '" + accountAjaxHandler.getStatus() + "' ";
            }


            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after statements ");
            while (resultSet.next()) {

                System.out.println("In while qualification");

                usersVTO.setUsr_id(resultSet.getInt("usr_id"));
                usersVTO.setEmpName(resultSet.getString("name"));
                usersVTO.setEmail1(resultSet.getString("email1"));
                usersVTO.setPhone(resultSet.getString("office_phone"));
                // usersVTO.setStatus(resultSet.getString("cur_status"));
                usersVTO.setUsr_title(resultSet.getString("cur_status"));

                resultString += usersVTO.getUsr_id() + "|" + usersVTO.getEmpName() + "|" + usersVTO.getUsr_title() + "|" + usersVTO.getEmail1() + "|" + usersVTO.getPhone() + '^';

                System.out.println("---------------->" + resultString);

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

        return resultString;
    }

    /**
     * *************************************
     *
     * @getDefaultVendorTiers() method is used to get Requirement details of
     * account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public String getDefaultVendorTiers(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException {
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserVTO usersVTO = new UserVTO();
        try {
            queryString = "SELECT c.id,c.vendor_tier_id,lk.vendor_type,c.created_date,"
                    + "c.STATUS,CONCAT(u.first_name,'.',u.last_name) AS NAME,a.account_name,c.is_permanent "
                    + "FROM customer_ven_rel c "
                    + "LEFT OUTER JOIN lk_vendor_type lk ON(lk.id=c.vendor_tier_id) "
                    + "LEFT OUTER JOIN accounts a ON(a.account_id=c.vendor_id)"
                    + "LEFT OUTER JOIN users u ON(u.usr_id=c.created_by) "
                    + "WHERE c.customer_id=" + accountAjaxHandler.getAccountSearchID();

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after statements ");
            while (resultSet.next()) {
                resultString += resultSet.getString("vendor_type") + "|" + resultSet.getString("NAME") + "|" + com.mss.msp.util.DateUtility.getInstance().convertToviewFormatInDash(resultSet.getString("created_date")) + "|" + resultSet.getString("STATUS") + "|" + resultSet.getString("vendor_tier_id") + "|" + resultSet.getString("id") + "|" + resultSet.getString("account_name") + "|" + resultSet.getString("is_permanent") + "^";

            }
            System.out.println("---------------->" + resultString);
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
     * *************************************
     *
     * @addVendorTierToCustmer() method is used to get Requirement details of
     * account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public String addVendorTierToCustmer(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException {
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserVTO usersVTO = new UserVTO();
        try {
            queryString = "INSERT INTO customer_ven_rel(customer_id,vendor_tier_id,STATUS,created_by) "
                    + "VALUES(" + accountAjaxHandler.getAccountSearchID() + "," + accountAjaxHandler.getVendorTier() + ",'Active'," + accountAjaxHandler.getUserSessionId() + ")  ";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            int res = statement.executeUpdate(queryString);
            System.out.println("after statements " + res);

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
     * *************************************
     *
     * @editVendorTierDetails() method is used to get Requirement details of
     * account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public String editVendorTierDetails(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException {
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {
            System.out.println("Date-->" + DateUtility.getInstance().getCurrentMySqlDate());
            queryString = "UPDATE customer_ven_rel SET vendor_tier_id=" + accountAjaxHandler.getVendorTierId() + ","
                    + "is_permanent=" + accountAjaxHandler.getPf() + ","
                    + "STATUS='" + accountAjaxHandler.getVendorTierStatus() + "',"
                    + "modified_by=" + accountAjaxHandler.getUserSessionId() + ","
                    + "modified_date='" + DateUtility.getInstance().getCurrentMySqlDate() + "' "
                    + "WHERE id=" + accountAjaxHandler.getTierId() + " "
                    + "AND customer_id=" + accountAjaxHandler.getAccountSearchID();
            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            int res = statement.executeUpdate(queryString);
            System.out.println("after statements " + res);

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
     * *************************************
     *
     * @getDefaultVendorTiers() method is used to get Requirement details of
     * account
     *
     * @Author:Ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/06/2015
     *
     **************************************
     */
    public String searchVendorTierDetails(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException {
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        UserVTO usersVTO = new UserVTO();
        try {
            queryString = "SELECT c.id,c.vendor_tier_id,lk.vendor_type,c.created_date,"
                    + "c.STATUS,CONCAT(u.first_name,'.',u.last_name) AS NAME,a.account_name,c.is_permanent "
                    + "FROM customer_ven_rel c "
                    + "LEFT OUTER JOIN lk_vendor_type lk ON(lk.id=c.vendor_tier_id) "
                    + "LEFT OUTER JOIN accounts a ON(a.account_id=c.vendor_id)"
                    + "LEFT OUTER JOIN users u ON(u.usr_id=c.created_by) "
                    + "WHERE c.customer_id=" + accountAjaxHandler.getAccountSearchID();
            if (!"-1".equalsIgnoreCase(accountAjaxHandler.getVendorTierType())) {
                queryString += " AND c.vendor_tier_id=" + accountAjaxHandler.getVendorTierType();
            }
            if (!"-1".equalsIgnoreCase(accountAjaxHandler.getTierStatus())) {
                queryString += " AND c.STATUS='" + accountAjaxHandler.getTierStatus() + "'";
            }


            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after statements ");
            while (resultSet.next()) {
                resultString += resultSet.getString("vendor_type") + "|" + resultSet.getString("NAME") + "|" + com.mss.msp.util.DateUtility.getInstance().convertToviewFormatInDash(resultSet.getString("created_date")) + "|" + resultSet.getString("STATUS") + "|" + resultSet.getString("vendor_tier_id") + "|" + resultSet.getString("id") + "|" + resultSet.getString("account_name") + "|" + resultSet.getString("is_permanent") + "^";

            }
            System.out.println("---------------->" + resultString);
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
     * *************************************
     *
     * @getReportingPersonDetails() method is used to get Reporting person
     * details of account
     *
     * @Author:Aklakh Ahmad<mahmad@miraclesoft.com>
     *
     * @Created Date: 22nd June 2015
     *
     **************************************
     */
    public String getReportingPersonDetails(AccountAjaxHandler accountAjaxHandler, int designationId) throws ServiceLocatorException {
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";

        try {
            queryString = "SELECT u.usr_id , CONCAT(u.first_name,'.',u.last_name) AS NAME "
                    + "FROM users u LEFT OUTER JOIN Project_team pt ON(u.usr_id=pt.usr_id) "
                    + "WHERE pt.designation IN(SELECT title_id FROM title  WHERE rank <" + designationId + " ) "
                    + "AND pt.project_id=" + accountAjaxHandler.getProjectID();



            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("After Connection");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after statements ");
            while (resultSet.next()) {
                resultString += resultSet.getInt("usr_id") + "|" + resultSet.getString("NAME") + "^";

            }
            System.out.println("---------------->" + resultString);
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
     * *************************************
     *
     * @getLoggedInEmpTasksDetails() method is used to get loggedIn user task
     * details and displays in TaskSearch Grid
     *
     * @Author:RK Ankireddy
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
    public String getVendorDetails(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException {
        System.out.println("in account impl>>>>>>>>>>>>>" + accountAjaxHandler.getVendorId());

        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "", resultString = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {

            queryString = "SELECT account_url,STATUS FROM accounts WHERE account_id=" + accountAjaxHandler.getVendorId() + "";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString = resultSet.getString("account_url") + "|" + resultSet.getString("STATUS");
            }

            System.out.println("resultString-->" + resultString);

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
     * @getLoggedInEmpTasksDetails() method is used to get loggedIn user task
     * details and displays in TaskSearch Grid
     *
     * @Author:RK Ankireddy
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
    public String saveVendorTierDetails(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException {
        System.out.println("in account impl>>>>>>>>>>>>>" + accountAjaxHandler.getVendorId());

        StringBuffer stringBuffer = new StringBuffer();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "", resultString = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {

            queryString = "INSERT INTO customer_ven_rel(customer_id,vendor_id,vendor_tier_id,STATUS,is_permanent,comments,created_by) "
                    + "VALUES(" + accountAjaxHandler.getAccountSearchID() + "," + accountAjaxHandler.getVendorId() + "," + accountAjaxHandler.getVendorTierId() + ",'Active'," + accountAjaxHandler.getPf() + ",'" + accountAjaxHandler.getVendorComments() + "'," + accountAjaxHandler.getUserSessionId() + ")";

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(queryString);
            if (i > 0) {
                resultString = "Success";
            }

            System.out.println("resultString-->" + resultString);

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
    
    public String getVendorTierOverlayDetails(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException{
        String resultString="";
        Statement statement=null;
        ResultSet resultSet=null;
        String queryString="";
        System.out.println("in imple get vendor tier overlay dtails");
        try{
            queryString="SELECT vendor_tier_id,STATUS,is_permanent FROM customer_ven_rel WHERE id="+accountAjaxHandler.getTierId();
            connection=ConnectionProvider.getInstance().getConnection();
            statement= connection.createStatement();
            System.out.println("query"+queryString);
            resultSet=statement.executeQuery(queryString);
    
            while(resultSet.next()){
              resultString+=resultSet.getString("vendor_tier_id")+"|"+resultSet.getString("status")+"|"+resultSet.getString("is_permanent") ; 
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(connection!=null)
                {
                    connection.close();
                    connection =null;
                }
                
            }
            catch(Exception ex)
                {
                    ex.printStackTrace();
                }
        }
        return resultString;
        
    }
  public String getCsrDetailsTable(AccountAjaxHandler accountAjaxHandler, int orgUserId)throws ServiceLocatorException {
        String resultString = "";
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        System.out.println("in getCsrDetailsTable Impl method");
        try {
            queryString = "SELECT csr_id ,CONCAT(first_name,'.',last_name)AS NAME,csr.STATUS,email1,csr.created_date,csr.created_by,phone1  FROM csrorgrel csr JOIN users ON(csr_id=usr_id) WHERE csr.org_id=" + orgUserId;
            if (accountAjaxHandler.getCsrName() != null) {
                queryString += " and (first_name like '%" + accountAjaxHandler.getCsrName() + "%' or last_name like '%" + accountAjaxHandler.getCsrName() + "%')";
            }
            if (accountAjaxHandler.getCsrEmail() != null) {
                queryString += " and email1 like '%" + accountAjaxHandler.getCsrEmail() + "%'";
            }
            if (accountAjaxHandler.getCsrphone() != null) {
                queryString += " and phone1 like '%" + accountAjaxHandler.getCsrphone() + "%'";
            }
            if (accountAjaxHandler.getCsrStatus()!= null) {
                queryString += " and csr.status ='" + accountAjaxHandler.getCsrStatus() + "'";
            } else {
                queryString += " and csr.status = 'Active'";
            }
            System.out.println("this is query --> " + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                resultString += resultSet.getString("csr_id") + "|" + resultSet.getString("NAME") + "|" + resultSet.getString("STATUS") + "|" + resultSet.getString("email1") + "|" + resultSet.getString("phone1") + "|" + com.mss.msp.util.DataSourceDataProvider.getInstance().getFnameandLnamebyStringId(resultSet.getString("created_by")) + "|" + resultSet.getString("created_date") + "^";
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    public int doAddAccountToCsr(AccountAjaxHandler accountAjaxHandler, int orgUserId) throws ServiceLocatorException{
        String resultString = "";
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int result = 0;
        System.out.println("in imple get vendor tier overlay dtails");
        try {
            boolean msg = isCsrAlredyExistForAccout(accountAjaxHandler.getCsrId(), accountAjaxHandler.getOrgUserId());
            System.out.println("the result message-->" + msg);
            if (msg) {
                queryString = "INSERT INTO csrorgrel (csr_id,org_id,STATUS,created_by) VALUE(" + accountAjaxHandler.getCsrId() + "," + accountAjaxHandler.getOrgUserId() + ",'Active'," + accountAjaxHandler.getUserSessionId() + ")";
                connection = ConnectionProvider.getInstance().getConnection();
                statement = connection.createStatement();
                System.out.println("query" + queryString);
                result = statement.executeUpdate(queryString);
            } else {
                result = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public boolean isCsrAlredyExistForAccout(String usrId, int orgid) {
        String resultString = "";
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        boolean result = true;
        System.out.println("in imple get vendor tier overlay dtails");
        try {


            queryString = "select csr_id from csrorgrel where csr_id=" + usrId + " and org_id=" + orgid;
            System.out.println("the query is checking-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("query" + queryString);
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                result = false;
            }


        } catch (Exception e) {
            e.printStackTrace();
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

    public int csrStatusChange(AccountAjaxHandler accountAjaxHandler, int orgUserId)throws ServiceLocatorException {
        String resultString = "";
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int result = 0;
        System.out.println("in imple get vendor tier overlay dtails");
        try {
            queryString = "update csrorgrel set status='" + accountAjaxHandler.getCsrStatus() + "' where csr_id=" + accountAjaxHandler.getCsrId() + " and org_id=" + accountAjaxHandler.getOrgUserId();
            System.out.println("the query is checking-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            System.out.println("query" + queryString);
            result = statement.executeUpdate(queryString);
        } catch (Exception e) {
            e.printStackTrace();
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
     public int accountTransferOrCopy(AccountAjaxHandler accountAjaxHandler) throws ServiceLocatorException{
        int isTransfer=0;
        Connection connection = null;
        CallableStatement callableStatement = null;
        String resultString= null;
        try{
        connection = ConnectionProvider.getInstance().getConnection();
               if(connection!=null){
               callableStatement = connection.prepareCall("{call assignAccounts(?,?,?,?,?)}");
              callableStatement.setInt(1,accountAjaxHandler.getFromCSRID());
              callableStatement.setInt(2,accountAjaxHandler.getToCSRID());
              callableStatement.setString(3,accountAjaxHandler.getTypeTransfer());
              callableStatement.setInt(4,accountAjaxHandler.getUserSessionId());
              callableStatement.registerOutParameter(5,java.sql.Types.INTEGER);
              callableStatement.execute() ;
               System.out.println("Execute=========>");
            isTransfer= callableStatement.getInt(5);
            if (isTransfer > 0) {
                System.out.println("****************** in impl result>0  after adding:::::::::" +isTransfer);
            } else {
                System.out.println("****************** in impl result after adding:::::::::" +isTransfer);
            }
               }
        }
        catch(Exception e){
            System.out.println("exception "+e);
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    return isTransfer;
    }


}
