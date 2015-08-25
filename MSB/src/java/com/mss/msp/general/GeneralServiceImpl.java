package com.mss.msp.general;

import com.mss.msp.general.GeneralService;
import com.mss.msp.security.SecurityServiceProvider;
import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.HibernateServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Iterator;
import java.util.Random;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import com.mss.msp.util.Properties;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class GeneralServiceImpl implements GeneralService {

    Connection connection = null;
    CallableStatement callableStatement = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    /**
     * Creates a new instance of GeneralServiceIml
     */
    public GeneralServiceImpl() {
    }

    public String generateUserId(String mailId) {

        /*@param atOccurance is used to store index of mailId upto @ char*/
        int atOccurance = mailId.indexOf("@");

        /*finally those string return here*/
        return mailId.substring(0, atOccurance).toLowerCase();
    }

    public int doUpdateResetPassword(String password, String email) throws ServiceLocatorException {

        //System.out.println("::::doUpdateResetPassword Impll.. :::");
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        int isUpdated = 0;
        String hexa16digitSalt = SecurityServiceProvider.generateRandamSecurityKey(16, 16, 4, 4, 4);
        //String queryString = "UPDATE tblCreTchComments SET ModifiedDate = ?, ModifiedBy = ?, Comments = ?, Status = ? WHERE id = ? AND CreId = ?";
        String encPwd = SecurityServiceProvider.getEncrypt(password.trim(), hexa16digitSalt);
        //System.out.println("plain text-->"+password.trim());
        String queryString = "UPDATE usr_reg SET password=?,salt=? WHERE login_id=?";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);


            preparedStatement.setString(1, encPwd);
            preparedStatement.setString(2, hexa16digitSalt);
            preparedStatement.setString(3, email);


            isUpdated = preparedStatement.executeUpdate();

            // System.err.println("query result -->"+isUpdated);
        } catch (SQLException se) {
            // System.err.println("11 ---se-->"+se.getMessage());
            throw new ServiceLocatorException(se);
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
            } catch (SQLException se) {
                //  System.out.println("se"+se.getMessage());
                throw new ServiceLocatorException(se);
            }
        }

        return isUpdated;
    }

    public int doPasswordLinkStatusUpdate(String email) throws ServiceLocatorException {

        // System.out.println("::::doPasswordLinkStatusUpdate Impll.. :::");
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        int isUpdated = 0;

        String queryString = "UPDATE forgotpasswordlink SET status = ? WHERE email_id = ?";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);


            preparedStatement.setString(1, "InActive");
            preparedStatement.setString(2, email.trim());


            isUpdated = preparedStatement.executeUpdate();
            //System.out.println("For Got password linkUpdated successfully ");
        } catch (SQLException se) {
            throw new ServiceLocatorException(se);
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
            } catch (SQLException se) {
                // System.err.println("linkupdation flow exception --->"+se.getMessage());
                throw new ServiceLocatorException(se);

            }
        }

        return isUpdated;
    }

    public String forGotPwdLinkStatus(String mailId, String ssid) throws ServiceLocatorException {

        String curStatus = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionProvider.getInstance().getConnection();
        String queryString = "SELECT status FROM forgotpasswordlink WHERE email_id like '" + mailId + "' and code ='" + ssid + "'";
        //System.out.println("queryString-->"+queryString);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                curStatus = resultSet.getString("status");
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
        return curStatus;
    }

    public String getPrimaryAction(int orgId, int roleId) throws ServiceLocatorException {
        String action = "";
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        Connection connection = null;
        try {
            
            queryString = " SELECT action_name from home_redirect_action where org_id=" + orgId + " and primaryrole=" + roleId;
            System.err.println("queryString--->"+queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                action = resultSet.getString("action_name");
            }
            //httpServletRequest.getSession(false).setAttribute(ApplicationConstants.PRIMARYROLE, roleId);
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
     * *****************************************************************************
     * Date : April 28 2015
     *
     * Author : jagan chukkala<jchukkala@miraclesoft.com>
     *
     * getCountriesNames() method can be used to get the countries And returns
     * countries Map
     * *****************************************************************************
     */
    public Map getCountriesNames() {
        Map countries = new LinkedHashMap();
        Session session = null;
        try {
            session = HibernateServiceLocator.getInstance().getSession();
            //countries = session.createQuery("select id,name from CountryVto");
            String hqlQuery = "select cv.id,cv.name from CountryVto cv";
            Query query = session.createQuery(hqlQuery);
            List list = query.list();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] o = (Object[]) iterator.next();
                countries.put(o[0], o[1]);
            }
        } catch (ServiceLocatorException e) {
            e.printStackTrace();
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

        return countries;
    }

    /**
     * *****************************************************************************
     * Date : April 29 2015
     *
     * Author : jagan chukkala<jchukkala@miraclesoft.com>
     *
     * getStatesOfCountry() method can be used to get the states by passing
     * country id And returns resultString
     * *****************************************************************************
     */
    public Map getStatesMapOfCountry(HttpServletRequest httpServletRequest, int id) {
        Map states = new LinkedHashMap();
        String resultString = "";
        Session session = null;

        try {
            session = HibernateServiceLocator.getInstance().getSession();
            //countries = session.createQuery("select id,name from CountryVto");

            String hqlQuery = "select id,name from State  WHERE countryId=:countryid";

            Query query = session.createQuery(hqlQuery);
            query.setInteger("countryid", id);
            List list = query.list();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Object[] o = (Object[]) iterator.next();

                //resultString += o[0] + "#" + o[1] + "^";
                states.put(o[0], o[1]);

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
        return states;
    }

    public String getStatesStringOfCountry(HttpServletRequest httpServletRequest, int id) {
        Map states = new LinkedHashMap();
        String resultString = "";
        Session session = null;

        try {
            session = HibernateServiceLocator.getInstance().getSession();
            //countries = session.createQuery("select id,name from CountryVto");

            String hqlQuery = "select id,name from State  WHERE countryId=:countryid";

            Query query = session.createQuery(hqlQuery);
            query.setInteger("countryid", id);
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

    public String getStatesOfCountry(HttpServletRequest httpServletRequest, int id) {
        Map states = new LinkedHashMap();
        String resultString = "";
        Session session = null;

        try {
            session = HibernateServiceLocator.getInstance().getSession();
            //countries = session.createQuery("select id,name from CountryVto");

             String hqlQuery = "select id,name from State s WHERE countryId=:countryid order by s.name asc";

            Query query = session.createQuery(hqlQuery);
            query.setInteger("countryid", id);
            List list = query.list();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Object[] o = (Object[]) iterator.next();

                resultString += o[0] + "#" + o[1] + "^";
                //resultString += resultSet.getInt("title_id") + "#" + resultSet.getString("title_name") + "^";
//System.out.println(o[0]);
//System.out.println(o[1]);
//System.out.println("success");
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
     * @getDefaultRequirementDashBoardDetails() update status in requirement
     * table
     *
     *
     * @Author:ramakrishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:06/03/2015
     *
     **************************************
     */
    public List getDefaultRequirementDashBoardDetails(GeneralAction generalAction) throws ServiceLocatorException {
        String resultString = "";
        ArrayList<CsrDashBoardVTO> csrDashBoardList = new ArrayList<CsrDashBoardVTO>();
        String queryString = "";
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            connection = ConnectionProvider.getInstance().getConnection();
            if (generalAction.getTypeOfUser().equalsIgnoreCase("SA")) {
                queryString = "SELECT COUNT(requirement_id) AS total,"
                        + "COUNT(IF(req_status='O',1, NULL)) 'Open',"
                        + "COUNT(IF(req_status='R',1, NULL)) 'Released',"
                        + "COUNT(IF(req_status='C',1, NULL)) 'Closed',"
                        + "a.account_name,a.account_id "
                        + "FROM acc_requirements "
                        + "LEFT OUTER JOIN accounts a ON(a.account_id=acc_id) "
                        + "WHERE DATE_FORMAT(req_st_date,'%Y')=" + year + " "
                        + "GROUP BY a.account_id";
            } else {
                queryString = "SELECT COUNT(requirement_id) AS total,"
                        + "COUNT(IF(req_status='O',1, NULL)) 'Open',"
                        + "COUNT(IF(req_status='R',1, NULL)) 'Released',"
                        + "COUNT(IF(req_status='C',1, NULL)) 'Closed',"
                        + "a.account_name,a.account_id "
                        + "FROM acc_requirements "
                        + "LEFT OUTER JOIN accounts a ON(a.account_id=acc_id) "
                        + "LEFT OUTER JOIN csrorgrel csr ON(a.account_id=csr.org_id)"
                        + "WHERE DATE_FORMAT(req_st_date,'%Y')=" + year + " "
                        + "AND csr.csr_id=" + generalAction.getUserSessionId() + " "
                        + "GROUP BY a.account_id";
            }

            System.out.println("query...DashBoard....>" + queryString);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                CsrDashBoardVTO csrDashBoardVTO = new CsrDashBoardVTO();
                csrDashBoardVTO.setTotal(resultSet.getString("total"));
                csrDashBoardVTO.setOpen(resultSet.getString("Open"));
                csrDashBoardVTO.setReleased(resultSet.getString("Released"));
                csrDashBoardVTO.setClosed(resultSet.getString("Closed"));
                csrDashBoardVTO.setCustomerName(resultSet.getString("account_name"));
                csrDashBoardVTO.setAccountId(resultSet.getString("account_id"));
                csrDashBoardList.add(csrDashBoardVTO);
            }
            System.out.println("result=========>" + resultString);
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
        return csrDashBoardList;
    }
}
