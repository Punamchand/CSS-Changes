/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.details;

import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.HibernateServiceLocator;
import com.mss.msp.util.ServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Greg
 */
public class AccountDetailsHandlerServiceImpl implements AccountDetailsHandlerService {

    public AccountDetails viewAccountDetails(int id) throws ServiceLocatorException {
        AccountDetails details = new AccountDetails();
        Session session = HibernateServiceLocator.getInstance().getSession();

        Query query = session.createQuery("from AccountDetails where id = :accId");
        query.setParameter("accId", id);
        List temp = query.list();
        if (temp.size() > 0) {
            details = (AccountDetails) temp.get(0);
        }



        //get look up names fomr lk tables =>CHANGE LATER<=
       /* if((id>0)){
         details = (AccountDetails) query.list().get(0);
         }
         Query help = session.createSQLQuery("SELECT lk.acc_type_name from lk_acc_type lk where lk.id = "+details.getAccountType()+";");

         List temp=help.list();
         if(temp.size()>0) {
         details.setAccountType(temp.get(0).toString());
         }
         help=session.createSQLQuery("SELECT lk.acc_industry_name from lk_acc_industry_type lk where lk.id = "+account.getIndustry()+";");
         temp=help.list();
         if(temp.size()>0) {
         account.setIndustry(temp.get(0).toString());
         }
         help=session.createSQLQuery("SELECT lk.country from lk_country lk where lk.id = "+account.getCountry_id()+";");
         temp=help.list();
         if(temp.size()>0) {
         account.setCountry(temp.get(0).toString());
         }
         account.setStateName((ServiceLocator.getLocationService().lookupStateById(Integer.parseInt(account.getState())).getName()));
         //        for(Object x :help.list()){
         //            System.out.println("=========LOOK=========="+x.toString());
         //        }

         */
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

        return details;
    }

    public AccountContactVTO editAccountContacts(int contactId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
       // StringBuffer sb = new StringBuffer();
        String queryString = "";
        AccountContactVTO accountContactVTO = new AccountContactVTO();
        Map state1 = new HashMap();
        Map state2 = new LinkedHashMap();
        //Map titleMap = new HashMap();

        try {
            // queryString = "SELECT u.first_name,u.last_name,u.middle_name,u.image_path,u.org_id,u.phone1,u.phone2,u.email1,u.email2,u.office_phone,u.cur_status,ua.address,ua.address_flag, ua.address2, ua.city, ua.state, ua.zip, ua.country,ua.phone,m.dept_id,m.title_id,m.is_team_lead,m.is_manager FROM usr_address ua LEFT OUTER JOIN users u ON (ua.usr_id=u.usr_id)  LEFT OUTER JOIN usr_miscellaneous m ON (u.usr_id=m.usr_id) WHERE u.usr_id =" + contactId;
            queryString = "SELECT u.gender,u.first_name,u.last_name,u.middle_name,u.image_path,u.org_id,u.phone1,u.phone2,u.email1,u.email2,u.office_phone,u.cur_status,u.designation,ua.address,ua.address_flag, ua.address2, ua.city, ua.state, ua.zip, ua.country,ua.phone FROM usr_address ua LEFT OUTER JOIN users u ON (ua.usr_id=u.usr_id)   WHERE u.usr_id =" + contactId;

            System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            DataSourceDataProvider dsdp = DataSourceDataProvider.getInstance();



            while (resultSet.next()) {
                System.out.println("In while");
//                if ((resultSet.getInt("is_team_lead")) == 1) {
//                    accountContactVTO.setIsTeamLead("true");
//                } else {
//                    accountContactVTO.setIsTeamLead("false");
//                }
//
//                if ((resultSet.getInt("is_manager")) == 1) {
//                    accountContactVTO.setIsManager("true");
//                } else {
//                    accountContactVTO.setIsManager("false");
//                }
//                if (resultSet.getString("address_flag").equalsIgnoreCase("pc")) {
//                    System.out.println("In PC If");
//                    accountContactVTO.setFirstName(resultSet.getString("u.first_name"));
//                    accountContactVTO.setLastName(resultSet.getString("u.last_name"));
//                    accountContactVTO.setMiddleName(resultSet.getString("u.middle_name"));
//                    //accountContactVTO.setEmail(resultSet.getString("u.email1"));
//                    accountContactVTO.setCheckAddress(true);
//                    accountContactVTO.setOfficePhone(resultSet.getString("u.office_phone"));
//                    // accountContactVTO.setOfficeAddress(resultSet.getString("u.office_address"));
//                    //empLeaves.setReportsTo(resultSet.getInt("reports_to"));
//                    accountContactVTO.setStatus(resultSet.getString("u.cur_status"));
//                    accountContactVTO.setMoblieNumber(resultSet.getString("u.phone1"));
//                    accountContactVTO.setHomePhone(resultSet.getString("u.phone2"));
//                    accountContactVTO.setEmail(resultSet.getString("u.email1"));
//                    accountContactVTO.setEmail2(resultSet.getString("u.email2"));
//                    accountContactVTO.setProfileImage(resultSet.getString("u.image_path"));
//                    accountContactVTO.setOrgId(resultSet.getInt("u.org_id"));
//                   // accountContactVTO.setWorkLocation(resultSet.getString("u.work_location"));
//
//                    accountContactVTO.setConPAddress(resultSet.getString("ua.address"));
//                    accountContactVTO.setConCAddress(resultSet.getString("ua.address"));
//
//                    accountContactVTO.setConPAddress2(resultSet.getString("ua.address2"));
//                    accountContactVTO.setConCAddress2(resultSet.getString("ua.address2"));
//
//                    accountContactVTO.setConPZip(resultSet.getString("ua.zip"));
//                    accountContactVTO.setConCZip(resultSet.getString("ua.zip"));
//
//                    accountContactVTO.setConPCity(resultSet.getString("ua.city"));
//                    accountContactVTO.setConCCity(resultSet.getString("ua.city"));
//
//                    accountContactVTO.setConPPhone(resultSet.getString("ua.phone"));
//                    accountContactVTO.setConCPhone(resultSet.getString("ua.phone"));
//
//
//                    accountContactVTO.setConPState(resultSet.getInt("ua.state"));
//                    accountContactVTO.setConCState(resultSet.getInt("ua.state"));
//
//
//                    System.out.println("State " + resultSet.getInt("ua.state"));
//
//
//                    accountContactVTO.setConPCountry(resultSet.getInt("ua.country"));
//                    accountContactVTO.setConCCountry(resultSet.getInt("ua.country"));
//
//
//                    System.out.println("country " + resultSet.getInt("ua.country"));
//
//
//                    try {
//                        //    System.out.println("i am in innser try loop........1");
//                        String stateQuery = "SELECT id,name FROM lk_states where countryId=?";
//                        preparedStatement = connection.prepareStatement(stateQuery);
//                        //  System.out.println("i am in innser try loop........2");
//                        preparedStatement.setInt(1, accountContactVTO.getConPCountry());
//
//                        ResultSet resultset1 = null;
//                        resultset1 = preparedStatement.executeQuery();
//                        while (resultset1.next()) {
//                            //  System.out.println("i am in innser try loop........3");
//                            state1.put(resultset1.getInt("id"), resultset1.getString("name"));
//                            state2.put(resultset1.getInt("id"), resultset1.getString("name"));
//                        }
//                        accountContactVTO.setState1(state1);
//                        accountContactVTO.setState2(state2);
//
//                        //System.out.println("i am in innser try loop........4");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    accountContactVTO.setDepartment(resultSet.getInt("m.dept_id"));
//
//                    accountContactVTO.setContactDesignation(resultSet.getInt("m.title_id"));
//
//                    accountContactVTO.setTitles(dsdp.getDesignation());
//
//                    System.out.println("VTO" + accountContactVTO.toString());
//                    return accountContactVTO;
//                }
//                if (resultSet.getString("address_flag").equalsIgnoreCase("p")) {
//                    System.out.println("In p if");
//                    accountContactVTO.setFirstName(resultSet.getString("u.first_name"));
//                    accountContactVTO.setLastName(resultSet.getString("u.last_name"));
//                    accountContactVTO.setMiddleName(resultSet.getString("u.middle_name"));
//                    //accountContactVTO.setEmail(resultSet.getString("u.email1"));
//                    accountContactVTO.setCheckAddress(false);
//                    accountContactVTO.setOfficePhone(resultSet.getString("u.office_phone"));
//                    // accountContactVTO.setOfficeAddress(resultSet.getString("u.office_address"));
//                    //empLeaves.setReportsTo(resultSet.getInt("reports_to"));
//                    accountContactVTO.setStatus(resultSet.getString("u.cur_status"));
//                    accountContactVTO.setMoblieNumber(resultSet.getString("u.phone1"));
//                    accountContactVTO.setHomePhone(resultSet.getString("u.phone2"));
//                    accountContactVTO.setEmail(resultSet.getString("u.email1"));
//                    accountContactVTO.setEmail2(resultSet.getString("u.email2"));
//                    accountContactVTO.setProfileImage(resultSet.getString("u.image_path"));
//                    accountContactVTO.setOrgId(resultSet.getInt("u.org_id"));
//                   // accountContactVTO.setWorkLocation(resultSet.getString("u.work_location"));
//
//                    accountContactVTO.setConPAddress(resultSet.getString("ua.address"));
//
//
//                    accountContactVTO.setConPAddress2(resultSet.getString("ua.address2"));
//
//
//                    accountContactVTO.setConPZip(resultSet.getString("ua.zip"));
//
//
//                    accountContactVTO.setConPCity(resultSet.getString("ua.city"));
//
//
//                    accountContactVTO.setConPPhone(resultSet.getString("ua.phone"));
//
//
//
//                    accountContactVTO.setConPState(resultSet.getInt("ua.state"));
//
//
//                    accountContactVTO.setConPCountry(resultSet.getInt("ua.country"));
//
//                    System.out.println("After--->accountContactVTO.getConPCountry() country is " + accountContactVTO.getConPCountry());
//
//                    try {
//                        //    System.out.println("i am in innser try loop........1");
//                        String stateQuery = "SELECT id,name FROM lk_states where countryId=?";
//                        preparedStatement = connection.prepareStatement(stateQuery);
//                        //  System.out.println("i am in innser try loop........2");
//                        preparedStatement.setInt(1, accountContactVTO.getConPCountry());
//                        System.out.println("accountContactVTO.getConPCountry() country is " + accountContactVTO.getConPCountry());
//                        ResultSet resultset1 = null;
//                        resultset1 = preparedStatement.executeQuery();
//                        while (resultset1.next()) {
//                            //  System.out.println("i am in innser try loop........3");
//                            state1.put(resultset1.getInt("id"), resultset1.getString("name"));
//                        }
//                        accountContactVTO.setState1(state1);
//                        //System.out.println("i am in innser try loop........4");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    accountContactVTO.setDepartment(resultSet.getInt("m.dept_id"));
//
//                    accountContactVTO.setContactDesignation(resultSet.getInt("m.title_id"));
//
//                    accountContactVTO.setTitles(dsdp.getDesignation());
//
//                }
             //  else{
                    System.out.println("no flag value");
                    accountContactVTO.setFirstName(resultSet.getString("u.first_name"));
                    accountContactVTO.setLastName(resultSet.getString("u.last_name"));
                    accountContactVTO.setMiddleName(resultSet.getString("u.middle_name"));
                    //accountContactVTO.setEmail(resultSet.getString("u.email1"));
                    accountContactVTO.setCheckAddress(false);
                    accountContactVTO.setOfficePhone(resultSet.getString("u.office_phone"));
                    // accountContactVTO.setOfficeAddress(resultSet.getString("u.office_address"));
                    //empLeaves.setReportsTo(resultSet.getInt("reports_to"));
                    accountContactVTO.setStatus(resultSet.getString("u.cur_status"));
                    accountContactVTO.setMoblieNumber(resultSet.getString("u.phone1"));
                    accountContactVTO.setHomePhone(resultSet.getString("u.phone2"));
                    accountContactVTO.setEmail(resultSet.getString("u.email1"));
                    accountContactVTO.setEmail2(resultSet.getString("u.email2"));
                    accountContactVTO.setProfileImage(resultSet.getString("u.image_path"));
                    accountContactVTO.setOrgId(resultSet.getInt("u.org_id"));
                    accountContactVTO.setGender(resultSet.getString("u.gender"));
                   // accountContactVTO.setWorkLocation(resultSet.getString("u.work_location"));
                    accountContactVTO.setConPAddress(resultSet.getString("ua.address"));
                    accountContactVTO.setConPAddress2(resultSet.getString("ua.address2"));
                    accountContactVTO.setConPZip(resultSet.getString("ua.zip"));
                    accountContactVTO.setConPCity(resultSet.getString("ua.city"));
                    accountContactVTO.setConPPhone(resultSet.getString("ua.phone"));
                    accountContactVTO.setConPState(resultSet.getInt("ua.state"));
                    accountContactVTO.setConPCountry(resultSet.getInt("ua.country"));
                    System.out.println("After---->accountContactVTO.getConCCountry() country is " + accountContactVTO.getConCCountry());

                    try {
                        //    System.out.println("i am in innser try loop........1");
                        String stateQuery = "SELECT id,name FROM lk_states where countryId=? ORDER BY name ASC";
                        preparedStatement = connection.prepareStatement(stateQuery);
                        System.out.println("accountContactVTO.getConPCountry() country is " + accountContactVTO.getConPCountry());

                        //  System.out.println("i am in innser try loop........2");
                        preparedStatement.setInt(1, accountContactVTO.getConPCountry());
                        ResultSet resultset1 = null;
                        resultset1 = preparedStatement.executeQuery();
                        while (resultset1.next()) {
                            //  System.out.println("i am in innser try loop........3");
                            state2.put(resultset1.getInt("id"), resultset1.getString("name"));
                        }
                        accountContactVTO.setState1(state2);
                        //System.out.println("i am in innser try loop........4");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   // accountContactVTO.setDepartment(resultSet.getInt("m.dept_id"));
                   // accountContactVTO.setContactDesignation(resultSet.getInt("m.title_id"));
                   // accountContactVTO.setTitles(dsdp.getDesignation());

               // }


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
        System.out.println("return VTO " + accountContactVTO);
        return accountContactVTO;
    }

    public String updateAccountContactDetails(AccountDetailsAction accountDetailsAction) throws ServiceLocatorException {

        Connection connection = null;
        CallableStatement callableStatement = null;
        //PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean isExceute = false;
        String resultString = "";
        int updatedRows = 0;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            //String query = "select cur_status from users where usr_id=" + AccountAjaxHandler.getId();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            String status = "";
//            while (resultSet.next()) {
//                status = resultSet.getString("cur_status");
//            }

            //callableStatement = connection.prepareCall("{CALL updateAccContact(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
             callableStatement = connection.prepareCall("{CALL updateAccContact(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, accountDetailsAction.getContactEmail());
            callableStatement.setString(2, accountDetailsAction.getContactFname());
            callableStatement.setString(3, accountDetailsAction.getContactLname());
            callableStatement.setString(4, accountDetailsAction.getContactMname());
            callableStatement.setString(5, accountDetailsAction.getOfficephone());
            callableStatement.setString(6, accountDetailsAction.getStatus());
           // callableStatement.setString(7, accountDetailsAction.getAddAddressFlag());
            callableStatement.setString(7, accountDetailsAction.getConAddress());
            callableStatement.setString(8, accountDetailsAction.getConAddress2());
            callableStatement.setString(9, accountDetailsAction.getConCity());
            callableStatement.setInt(10, accountDetailsAction.getConCountry());
            callableStatement.setInt(11, accountDetailsAction.getConState());
            callableStatement.setString(12, accountDetailsAction.getConZip());
            callableStatement.setString(13, accountDetailsAction.getConPhone());
//            callableStatement.setString(15, accountDetailsAction.getConCAddress());
//            callableStatement.setString(16, accountDetailsAction.getConCAddress2());
//            callableStatement.setString(17, accountDetailsAction.getConCCity());
//            callableStatement.setInt(18, accountDetailsAction.getConCCountry());
//            callableStatement.setInt(19, accountDetailsAction.getConCState());
//            callableStatement.setString(20, accountDetailsAction.getConCZip());
         //   callableStatement.setString(14, accountDetailsAction.getConCPhone());
            callableStatement.setInt(14, accountDetailsAction.getContactId());
          //  callableStatement.setInt(23, accountDetailsAction.getDepartment());
            callableStatement.setString(15, accountDetailsAction.getGender());
          //  callableStatement.setInt(25, accountDetailsAction.getAddTeamLead());
           // callableStatement.setInt(26, accountDetailsAction.getAddManager());
            callableStatement.setString(16, accountDetailsAction.getFlagname());
            callableStatement.setString(17, accountDetailsAction.getMoblieNumber());
            callableStatement.setString(18, accountDetailsAction.getContactEmail2());
            callableStatement.setString(19, accountDetailsAction.getHomePhone());
            //callableStatement.setString(31, accountDetailsAction.getWorkLocation());
            callableStatement.registerOutParameter(20, Types.INTEGER);
            System.out.println("hello here print after prepare call parameter ");

            isExceute = callableStatement.execute();
            updatedRows = callableStatement.getInt(20);
            if (updatedRows > 0) {
                resultString = "Updated";
            }
            System.out.println("is updatedRows " + updatedRows);
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
