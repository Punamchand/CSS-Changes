/**
 * *************************************
 *
 * @Author:praveen kumar<pkatru@miraclesoft.com>
 * @Author:rama krishna<lankireddy@miraclesoft.com>
 * @Created Date:05/05/2015
 *
 *
 * *************************************
 */
package com.mss.msp.vendor;

import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocatorException;
import com.mss.msp.vendor.vendorajax.VendorAjaxHandler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VendorServiceImpl implements VendorService {

    Connection connection = null;
    CallableStatement callableStatement = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

     /**
     * *************************************
     *
     * @getVendorDetails()
     *
     * @Author:praveen kumar<pkatru@miraclesoft.com>
     *
     * @Created Date:05/05/2015
     *
     * For USe:getting search details in default
     * *************************************
     */
    public List getVendorDetails(HttpServletRequest httpServletRequest, VendorAction vendorAction) throws ServiceLocatorException {

        ArrayList<VendorListVTO> vendorlist = new ArrayList<VendorListVTO>();
        StringBuffer stringBuffer = new StringBuffer();
        String queryString = "";
        int i = 0;
        //System.err.println(days+"Diff in Dyas...");
        try {
                 Map map = DataSourceDataProvider.getInstance().getMyTeamMembers(vendorAction.getUserSessionId());
            //ConsultantListDetails.add(map);
            

            String key, retrunValue = "";
            int mapsize = map.size();
            if (map.size() > 0) {
                Iterator tempIterator = map.entrySet().iterator();
                while (tempIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) tempIterator.next();
                    key = entry.getKey().toString();
                    mapsize--;
                    if (mapsize != 0) {
                        retrunValue += key + ",";
                    } else {
                        retrunValue += key;
                    }
                    entry = null;
                }
            }
            queryString = "SELECT account_id,ac.created_by,org_id,last_access_date,ac.STATUS,account_name,account_url,acc_type,type_of_relation,acc_industry_name,aa.acc_city,lks.NAME,abc.acc_phone,act.teamMember_Id "
                    + "FROM accounts ac LEFT OUTER JOIN org_rel orl ON (ac.account_id=orl.related_org_id) "
                    + "LEFT OUTER JOIN accteam act ON(ac.account_id=act.acc_id) LEFT OUTER JOIN acc_address aa ON (orl.related_org_id=aa.acc_id) "
                    + "LEFT OUTER JOIN acc_basic_info abc ON (orl.related_org_id=abc.acc_id) LEFT OUTER JOIN  lk_acc_industry_type it "
                    + "ON (it.id=acc_type) LEFT OUTER JOIN lk_states lks ON (lks.id=aa.acc_state)"
                    + "WHERE orl.org_id="+vendorAction.getSessionOrg_id()+" AND acc_type=5 AND act.teamMember_Id=?";
             if("My".equalsIgnoreCase(vendorAction.getVendorFlag())){
            queryString = queryString + " and ac.created_by = '" + vendorAction.getUserSessionId() + "'";
            }
             if("Team".equalsIgnoreCase(vendorAction.getVendorFlag())){
            
                 queryString = queryString + " and ac.created_by in(" + retrunValue + ")";
             }
             
           System.out.println("queryString-->" + queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, vendorAction.getTeamMemberId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VendorListVTO vendorListVto = new VendorListVTO();
                vendorListVto.setOrg_id(resultSet.getInt("account_id"));
                vendorListVto.setTeamMember_id(resultSet.getInt("teamMember_id"));
                vendorListVto.setVendorName(resultSet.getString("account_name"));
                vendorListVto.setVendorUrl(resultSet.getString("account_url"));
                vendorListVto.setVendorCity(resultSet.getString("acc_city"));
                vendorListVto.setVendorState(resultSet.getString("name"));
                vendorListVto.setStatus(resultSet.getString("status"));
                vendorListVto.setVendorPhone(resultSet.getString("acc_phone"));
                vendorListVto.setLastAccessDate(resultSet.getString("last_access_date"));
                vendorListVto.setIndustry(resultSet.getString("acc_industry_name"));
                vendorlist.add(vendorListVto);

            }

//            System.out.println("queryString-->" + vendorlist);

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
        return vendorlist;
    }

    /**
     * *************************************
     *
     * @getVendorDetailsById()
     *
     * @Author:rama krishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/05/2015
     *
     * For USe:getting details of particular vendor
     * *************************************
     */
    public VendorListVTO getVendorDetailsById(VendorAction vendorAction, HttpServletRequest httpServletRequest) throws ServiceLocatorException {
        VendorListVTO vendorListVTO = new VendorListVTO();
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        try {
//            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%ENTERED IN TO IMPL TRY%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
            queryString = "SELECT a.account_name,a.account_url,a.STATUS,ad.acc_address1,ad.acc_address2,"
                    + "ad.acc_city,ad.acc_state,ad.acc_country,ad.acc_zip,acb.acc_phone,acb.acc_fax,acb.acc_region,"
                    + "acb.acc_territory,acb.acc_no_of_employees,acb.acc_revenue,acb.acc_it_budget,"
                    + "acb.acc_tax_id,acb.acc_stock_symbol,acb.acc_description,acb.acc_industry_type, "
                    + "i.acc_industry_name AS industry,atp.acc_type_name AS TYPE,o.acc_type "
                    + "FROM accounts a "
                    + "LEFT OUTER JOIN acc_address ad ON(ad.acc_id=a.account_id) "
                    + "LEFT OUTER JOIN acc_basic_info acb ON(acb.acc_id=a.account_id) "
                    + "LEFT OUTER JOIN lk_acc_industry_type i ON(i.id=acb.acc_industry_type) "
                    + "LEFT OUTER JOIN org_rel o ON(o.related_org_Id=a.account_id) "
                    + "LEFT OUTER JOIN lk_acc_type atp ON(atp.id=o.acc_type) "
                    + "WHERE a.account_id="+vendorAction.getVendorId()+" AND o.acc_type=5 ";

//            System.out.println(queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                vendorListVTO.setVendorName(resultSet.getString("account_name"));
                vendorListVTO.setVendorURL(resultSet.getString("account_url"));
                vendorListVTO.setVendorStatus(resultSet.getString("status"));
                vendorListVTO.setVendorAddress1(resultSet.getString("acc_address1"));
                vendorListVTO.setVendorAddress2(resultSet.getString("acc_address2"));
                vendorListVTO.setVendorCity(resultSet.getString("acc_city"));
                vendorListVTO.setVendorState(resultSet.getString("acc_state"));
                vendorListVTO.setVendorCountry(resultSet.getInt("acc_country"));
                vendorListVTO.setVendorZip(resultSet.getString("acc_zip"));
                vendorListVTO.setVendorPhone(resultSet.getString("acc_phone"));
                vendorListVTO.setVendorFax(resultSet.getString("acc_fax"));
                vendorListVTO.setVendorRegion(resultSet.getString("acc_region"));
                vendorListVTO.setVendorTerritory(resultSet.getString("acc_territory"));
                vendorListVTO.setEmpCount(resultSet.getInt("acc_no_of_employees"));
                vendorListVTO.setVendorTerritory(resultSet.getString("acc_territory"));
                vendorListVTO.setVendorRvenue(resultSet.getInt("acc_revenue"));
                vendorListVTO.setVendorBudget(resultSet.getInt("acc_it_budget"));
                vendorListVTO.setVendorTaxid(resultSet.getString("acc_tax_id"));
                vendorListVTO.setStockSymbol(resultSet.getString("acc_stock_symbol"));
                vendorListVTO.setVendorDescription(resultSet.getString("acc_description"));
                vendorListVTO.setVendorIndustryName(resultSet.getString("industry"));
                vendorListVTO.setVendorType(resultSet.getInt("acc_type"));
                vendorListVTO.setVendorIndustry(resultSet.getInt("acc_industry_type"));

//                System.out.println("THE INDUSTRY::::::" + resultSet.getString("industry"));
            }
//            System.out.println("%%%%%%%%%%%%%%%%%%Exiting While ::::::::" + vendorListVTO);
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
        return vendorListVTO;

    }

    /**
     * *************************************
     *
     * @updateVendorSalesTeam()
     *
     * @Author:rama krishna<lankireddy@miraclesoft.com>
     *
     * @Created Date:05/05/2015
     *
     * For USe:updating details of particular vendor sales team
     * *************************************
     */
    public int updateVendorSalesTeam(VendorAction vendorAction, String[] salesId,int primaryAccount) throws ServiceLocatorException {
        Statement statement = null;

        ResultSet resultSet;

        Connection connection = null;
        String queryString;
        int insertedRows = 0;
        int updateRows = 0;
        int deletedRows = 0;
        try {
            System.out.println("%%%%%%%%%%%%%%%%%%Entered in to the IMPL%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("VendorID>>>>>>>>>>>>>>>>>>>>" + vendorAction.getVendorId());
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            queryString = "DELETE FROM accteam WHERE acc_id=" + vendorAction.getVendorId();
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
//            //System.out.println("assignedRoleIds.length-->" + assignedRoleIds.length);
           for (int counter = 0; counter < salesId.length; counter++) {
                System.out.println("IN IMPL>>>>>>>>>>>>>>>>" + salesId[counter]);
                if(Integer.parseInt(salesId[counter]) == primaryAccount){
                queryString = "Insert into accteam(primaryflag,acc_id,teamMember_Id,created_by) values(1," + vendorAction.getVendorId() + "," + salesId[counter] + "," + vendorAction.getUserSessionId() + ")";
                //System.out.println(queryString);
                }
                else{
                  queryString = "Insert into accteam(primaryflag,acc_id,teamMember_Id,created_by) values(0," + vendorAction.getVendorId() + "," + salesId[counter] + "," + vendorAction.getUserSessionId() + ")";  
                //System.out.println(queryString);
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
    
    /**
     * ****************************************************************************
     * Date : June 02 2015
     *
     * Author : manikanta eeralla<meeralla@miraclesoft.com>
     *
     * *****************************************************************************
     */

    public List<VendorDashboardList> showVendorDashboard(int orgId) {

        List<VendorDashboardList> vendorDashboardList = new ArrayList<VendorDashboardList>();
        // CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int year;

        year = Calendar.getInstance().get(Calendar.YEAR);
        try {
            queryString="SELECT MONTHNAME(c.created_Date) AS MONTH,"
                    + "COUNT(requirement_id) AS requirements,"
                    + "COUNT(IF(rcr.STATUS='Processing',1, NULL)) 'Processing',"
                    + "COUNT(IF(rcr.STATUS='Selected',1, NULL)) 'Selected',"
                    + "COUNT(IF(rcr.STATUS='Rejected',1, NULL)) 'Rejected',"
                    + "COUNT(IF(rcr.STATUS='Selected',1, NULL)) 'Won',"
                    + "COUNT(IF(rcr.STATUS<>'Selected',1, NULL)) 'Lost' "
                    + "FROM acc_requirements a LEFT OUTER JOIN req_con_rel rcr "
                    + "ON(a.requirement_id=rcr.reqId) LEFT OUTER JOIN consultants c "
                    + "ON(c.consultant_id=rcr.consultantId) "
                    + "WHERE c.created_by_org_id= " + orgId + "  AND c.created_Date LIKE '%" + year + "%' "
                    + "GROUP BY DATE_FORMAT(c.created_Date, '%m')";
            

            
            
            
//            queryString = "SELECT COUNT(rcr.consultantId),COUNT(IF(rcr.STATUS='Processing',1, NULL)) 'Processing',COUNT(IF(rcr.STATUS='ShortListed',1, NULL)) 'ShortListed',COUNT(IF(rcr.STATUS='Rejected',1, NULL)) 'Rejected',IF(((a.req_status='C') AND (rcr.STATUS='ShortListed')),COUNT(a.req_status='C'),0  ) AS 'Won',(COUNT(rcr.consultantId)) - (COUNT(rcr.STATUS='ShortListed')) AS 'Lost' "
//                    + " FROM acc_requirements a LEFT OUTER JOIN "
//                    + "req_con_rel rcr ON(a.requirement_id=rcr.reqId) "
//                    + "LEFT OUTER JOIN consultants c ON(c.consultant_id=rcr.consultantId) "
//                    + "WHERE c.created_by_org_id=" + orgId + " AND c.created_Date LIKE '%" + year + "%' ";



            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                VendorDashboardList dashboardList = new VendorDashboardList();
                dashboardList.setMonths(resultSet.getString("MONTH"));
                dashboardList.setRequirementCount(resultSet.getInt("requirements"));
                dashboardList.setNoOfReqWon(resultSet.getInt("Won"));
                dashboardList.setNoOfReqLose(resultSet.getInt("Lost"));
                dashboardList.setNoOfConProcessing(resultSet.getInt("Processing"));
                dashboardList.setNoOfConSelected(resultSet.getInt("Selected"));
                dashboardList.setNoOfConRejected(resultSet.getInt("Rejected"));
                vendorDashboardList.add(dashboardList);

//                System.out.println("THE INDUSTRY::::::" + resultSet.getString("industry"));
            }
            System.out.println("%%%%%%%%%%%%%%%%%%queryString--> ::::::::" + queryString);
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
        return vendorDashboardList;
    }
}
