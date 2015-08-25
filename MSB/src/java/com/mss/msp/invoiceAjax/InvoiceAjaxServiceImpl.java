/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.invoiceAjax;

import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author miracle
 */
public class InvoiceAjaxServiceImpl implements InvoiceAjaxService {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public String generateInvoice(InvoiceAjaxAction invoiceAjaxAction) throws ServiceLocatorException {

        boolean response = false;
        ResultSet resultSet = null;
        CallableStatement callableStatement = null;
        String responseFromSP = "";
        String sqlquery = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            sqlquery = "";

            System.out.println("this is for checked " + invoiceAjaxAction.isCheked());
            System.out.println("this is for month " + invoiceAjaxAction.getInvoiceMonth());
            System.out.println("this is for year " + invoiceAjaxAction.getInvoiceYear());
            System.out.println("this is for Resource Name " + invoiceAjaxAction.getInvoiceResource());
            System.out.println("this is user session id " + invoiceAjaxAction.getUsrSessionId());
            System.out.println("this is org session id " + invoiceAjaxAction.getOrgSessionId());

            if (!invoiceAjaxAction.isCheked()) {
                callableStatement = connection.prepareCall("{CALL invoiceGeneration(?,?,?,?,?,?)}");
//            response = statement.execute(sqlquery);
            } else {
                callableStatement = connection.prepareCall("{CALL GenerateAllEmpInvoice(?,?,?,?,?,?)}");
            }
            callableStatement.setInt(1, invoiceAjaxAction.getInvoiceMonth());
            callableStatement.setInt(2, invoiceAjaxAction.getInvoiceYear());
            callableStatement.setString(3, invoiceAjaxAction.getInvoiceResource());
            callableStatement.setInt(4, invoiceAjaxAction.getOrgSessionId());
            callableStatement.setInt(5, invoiceAjaxAction.getUsrSessionId());
            response = callableStatement.execute();
            responseFromSP = callableStatement.getString(6);
            System.out.println("this is printing is executed -->" + response);
            System.out.println("this is printing response from sp-->" + responseFromSP);

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
                try {
                    throw new ServiceLocatorException(ex);
                } catch (ServiceLocatorException ex1) {
                    ex1.printStackTrace();
                }
            }
        }

        return responseFromSP;
    }
}
