/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.invoiceAjax;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author miracle
 */
public class InvoiceAjaxAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private String resultType;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private int invoiceMonth;
    private int invoiceYear;
    private String invoiceResource;
    private boolean cheked;
    private int usrSessionId;
    private int orgSessionId;

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public String generateInvoice() {
        resultType = LOGIN;
        try {
            System.out.println("Ajax Handler action generateInvoice");
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                setUsrSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setOrgSessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                String editoverlay = ServiceLocator.getInvoiceAjaxService().generateInvoice(this);
                System.out.println("this is generate invoice-->"+editoverlay);
                httpServletResponse.setContentType("text");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(""+editoverlay+"");
            }
        } catch (Exception ex) {

            resultType = ERROR;
        }
        return null;
    }

    public int getInvoiceMonth() {
        return invoiceMonth;
    }

    public void setInvoiceMonth(int invoiceMonth) {
        this.invoiceMonth = invoiceMonth;
    }

    public int getInvoiceYear() {
        return invoiceYear;
    }

    public void setInvoiceYear(int invoiceYear) {
        this.invoiceYear = invoiceYear;
    }

    public String getInvoiceResource() {
        return invoiceResource;
    }

    public void setInvoiceResource(String invoiceResource) {
        this.invoiceResource = invoiceResource;
    }

    public boolean isCheked() {
        return cheked;
    }

    public void setCheked(boolean cheked) {
        this.cheked = cheked;
    }

    public int getUsrSessionId() {
        return usrSessionId;
    }

    public void setUsrSessionId(int usrSessionId) {
        this.usrSessionId = usrSessionId;
    }

    public int getOrgSessionId() {
        return orgSessionId;
    }

    public void setOrgSessionId(int orgSessionId) {
        this.orgSessionId = orgSessionId;
    }
    
}
