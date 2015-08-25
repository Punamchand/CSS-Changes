/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.invoiceAjax;

import com.mss.msp.util.ServiceLocatorException;

/**
 *
 * @author miracle
 */
public interface InvoiceAjaxService {

    public String generateInvoice(InvoiceAjaxAction aThis)throws ServiceLocatorException;
    
}
