/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.projectsdata;

import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.DataSourceDataProvider;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Senay
 */
public class ProjectTeamsDataHandlerServiceImpl implements ProjectTeamsDataHandlerService {

    private Connection connection;

    public List getProjectsTeam(Integer projectID) throws ServiceLocatorException {

        DataSourceDataProvider dataProvider = DataSourceDataProvider.getInstance();

        System.out.println(":::::::::::::: ProjectsDataHandlerServiceImpl ==> getProjectsTeam ::::::::::::::::::");

        List<ProjectTeamsVTO> projectsTeamsList = new ArrayList<ProjectTeamsVTO>();

        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        int i = 0;

        try {
//            queryString = "select * from Project_team as teamTable"
//                    + " inner join users as userTable"
//                    + " on teamTable.usr_id = userTable.usr_id "
//                    + "inner join "
//                    + "usr_skills as skillsTable"
//                    + " on teamTable.usr_id = skillsTable.usr_id "
//                    + "where teamTable.project_id=" + projectID
//                    + " group by teamTable.usr_id";

            queryString = "select t.role_name,teamTable.usr_id,teamTable.project_id,teamTable.current_status,teamTable.reportsto1,teamTable.reportsto2,userTable.first_name,userTable.middle_name,userTable.last_name from Project_team as teamTable"
                    + " inner join users as userTable"
                    + " inner join roles as t "
                    + " on t.role_id = teamTable.designation "
                    + " and teamTable.usr_id = userTable.usr_id "
                    + "where teamTable.project_id=" + projectID
                    + " group by teamTable.usr_id";

            System.out.println("queryString-->" + queryString);

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            String reportsToName;
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                ProjectTeamsVTO team = new ProjectTeamsVTO();
                team.setProjectID(resultSet.getInt("teamTable.project_id"));
                team.setStatus(resultSet.getString("teamTable.current_status"));
                //team.setSkillName(resultSet.getString("skill_name"));
                reportsToName = dataProvider.getFnameandLnamebyUserid(resultSet.getInt("teamTable.reportsto1"));
                team.setReportsTo1Name(reportsToName);
                reportsToName = dataProvider.getFnameandLnamebyUserid(resultSet.getInt("teamTable.reportsto2"));
                team.setReportsTo2Name(reportsToName);
                team.setFirstName(resultSet.getString("userTable.first_name"));
                team.setMiddleName(resultSet.getString("userTable.middle_name"));
                team.setLastName(resultSet.getString("userTable.last_name"));
                team.setDesignation(resultSet.getString("t.role_name"));
                team.setUserID(resultSet.getInt("teamTable.usr_id"));
                projectsTeamsList.add(team);
            }

            System.out.println("The returned list is: " + projectsTeamsList.toString());

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

        System.out.println("::::::::::: In getSubProjects impl and returning the list :::::::::::");

        return projectsTeamsList;

    }

    public String getTeamMemberDetails(ProjectTeamsDataHandlerAction projectTeamsDataHandlerAction) throws ServiceLocatorException {

        DataSourceDataProvider dataProvider = DataSourceDataProvider.getInstance();
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        //int i = 0;
        try {
//            queryString = "select t.title_name,teamTable.project_id,teamTable.usr_id,teamTable.current_status,teamTable.reportsto1,teamTable.reportsto2,userTable.first_name,userTable.last_name from Project_team as teamTable"
//                    + " inner join users as userTable"
//                    + " inner join title as t "
//                    + " on t.title_id = teamTable.designation "
//                    + " and teamTable.usr_id = userTable.usr_id "
//                    + "where 1=1";
queryString = "SELECT r.role_name,teamTable.project_id,teamTable.usr_id,teamTable.current_status,teamTable.reportsto1,teamTable.reportsto2,"+
"userTable.first_name,userTable.last_name FROM Project_team AS teamTable LEFT OUTER JOIN users AS userTable ON (teamTable.usr_id = userTable.usr_id) LEFT OUTER JOIN roles AS r "+
" ON (r.role_id = teamTable.designation) "+
" AND teamTable.usr_id = userTable.usr_id WHERE 1=1 ";


            if (projectTeamsDataHandlerAction.getTeamMemberName() != null && projectTeamsDataHandlerAction.getTeamMemberName().toString().isEmpty() == false) {
                queryString = queryString + " and (userTable.last_name LIKE '" + projectTeamsDataHandlerAction.getTeamMemberName() + "%' OR userTable.first_name LIKE '" + projectTeamsDataHandlerAction.getTeamMemberName() + "%')";
            }
            if (!"DF".equalsIgnoreCase(projectTeamsDataHandlerAction.getStatus())) {

                queryString = queryString + " and teamTable.current_status= '" + projectTeamsDataHandlerAction.getStatus() + "' ";
            }
            queryString = queryString + "and teamTable.project_id=" + projectTeamsDataHandlerAction.getProjectID();
            System.out.println("query String->" + queryString);

            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString += resultSet.getString("userTable.first_name") + "." + resultSet.getString("userTable.last_name") + "|" + resultSet.getString("r.role_name") + "|" + resultSet.getString("teamTable.current_status") + "|" + dataProvider.getFnameandLnamebyUserid(resultSet.getInt("teamTable.reportsto1")) + '|' + dataProvider.getFnameandLnamebyUserid(resultSet.getInt("teamTable.reportsto2")) + '|' + resultSet.getInt("teamTable.project_id") + '|' + resultSet.getInt("teamTable.usr_id") + '^';
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

  

    public String showResourceDetails(ProjectTeamsDataHandlerAction projectTeamsDataHandlerAction) throws ServiceLocatorException {
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = "";
        String resultString = "";
        //int i = 0;
        try {
            queryString = "SELECT DISTINCT(sp.usr_id) AS id,CONCAT(first_name,'.',last_name) AS NAMES, ur.role_id,role_Name"
                    + " FROM prj_sub_prjteam sp LEFT OUTER JOIN project_team pt ON (sp.usr_id=pt.usr_id)"
                    + " LEFT OUTER JOIN usr_roles ur ON (sp.usr_id=ur.usr_id) LEFT OUTER JOIN users u ON "
                    + "(sp.usr_id=u.usr_id) LEFT OUTER JOIN roles r ON (ur.role_id=r.role_id) "
                    + "WHERE ur.primary_flag=1 and sub_project_id=" + projectTeamsDataHandlerAction.getProjectID()+ " AND pt.project_id="+projectTeamsDataHandlerAction.getPpid();
            System.out.println("query String ?>>>>>>>>>>>>>?>>>>"+queryString);
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                resultString += resultSet.getString("Names") + "|" + resultSet.getString("role_Name") + "^";
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
    public int EmpReleasefromProject(ProjectTeamsDataHandlerAction projectTeamsDataHandlerAction) throws ServiceLocatorException {
        int resultString = 0;
        System.out.println("in mirgaion impl");
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            System.out.println("before sp");
            callableStatement = connection.prepareCall("{call spEmpReleasefromProject(?,?,?,?)}");
            callableStatement.setInt(1, projectTeamsDataHandlerAction.getProjectID());
            callableStatement.setInt(2, projectTeamsDataHandlerAction.getUserID());
            callableStatement.setInt(3, projectTeamsDataHandlerAction.getAccountID());
            callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
            callableStatement.execute();
            resultString = callableStatement.getInt(4);
            

        } catch (SQLException ex) {
            ex.printStackTrace();

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
        return resultString;
    }
}
}
