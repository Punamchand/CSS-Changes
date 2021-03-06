/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.reccruitmentAjax;

/**
 * *****************************************************************************
 *
 * Project : servicesBay
 *
 * Package :
 *
 * Date : April 23, 2015, 3:01 PM
 *
 * Author : Ramakrishna<lankireddy@miraclesoft.com>
 *
 * File : DownloadAction.java
 *
 * Copyright 2007 Miracle Software Systems, Inc. All rights reserved. MIRACLE
 * SOFTWARE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * *****************************************************************************
 */
import com.mss.msp.general.*;
import com.mss.msp.usrajax.*;
import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.DataSourceDataProvider;

import com.mss.msp.util.Properties;
import com.mss.msp.util.ServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import com.opensymphony.xwork2.Action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author
 *
 * This Class.... ENTER THE DESCRIPTION HERE
 */
public class ConsultantDownloadAction implements Action, ServletRequestAware, ServletResponseAware {

    private String inputPath;
    // private String URL="/images/flower.GIF";
    private String contentDisposition = "FileName=inline";
    public InputStream inputStream;
    public OutputStream outputStream;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    //private int id;
    private String fileName;
    private String consult_AttachmentLocation;
    private int acc_attachment_id;
    
    
    private String resume;
    private String resultMessage;
    private String resultType;
    private int accountSearchID;
    private String accountFlag;
    private String customerFlag;
    private int requirementId;
    private int consult_id;
    private String consultFlag;
    private String techSearch;
    private String reviewStartDate;
    private String reviewEndDate;
    private String techReviewStatus;
    private String consult_name;
    private String consult_email;
    private String consult_skill;
    private String consult_phno;
    
    private String jdId;
    private String vendor;
    /**
     * Creates a new instance of DownloadAction
     */
    public ConsultantDownloadAction() {
    }
    private DataSourceDataProvider dataSourceDataProvider;

    public String execute() throws Exception {
        return null;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    
    
    

    @SuppressWarnings("static-access")
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse; 
    }
    public String downloadResume()throws Exception{
       try {
           resultType=SUCCESS;
            //this.setAttachmentLocation(httpServletRequest.getParameter("attachmentLocation"));
            this.setAcc_attachment_id(Integer.parseInt(httpServletRequest.getParameter("acc_attachment_id").toString()));
            System.out.println("=================>Entered into the DownloadAction");
            try {
                this.setConsult_AttachmentLocation(dataSourceDataProvider.getInstance().getConsult_AttachmentLocation(this.getAcc_attachment_id()));
            } catch (ServiceLocatorException se) {
                System.out.println("in sub try"+se.getMessage());
            }
                fileName = this.getConsult_AttachmentLocation()
                        .substring(this.getConsult_AttachmentLocation().lastIndexOf(Properties.getProperty("OS.Compatabliliy.Download")) + 1, getConsult_AttachmentLocation().length());
                httpServletResponse.setContentType("application/force-download");
                System.out.println("=================>" + fileName);
                //String=fileLocaiton
                System.out.println("getAttachmentLocation()-->" + getConsult_AttachmentLocation());
                           if (!"\\".equals(getConsult_AttachmentLocation()) && !"null".equals(getConsult_AttachmentLocation()) && getConsult_AttachmentLocation() != null && getConsult_AttachmentLocation().length() != 0) {


                File file = new File(getConsult_AttachmentLocation());
                System.out.println(file);
                inputStream = new FileInputStream(file);
                outputStream = httpServletResponse.getOutputStream();
                if (outputStream == null) {
                    System.out.println("yes");
                } else {
                    System.out.println("        jjjjjjjjjjjj   no");
                    httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                    int noOfBytesRead = 0;
                    byte[] byteArray = null;
                    System.out.println("Iam hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                    while (true) {
                        byteArray = new byte[1024];
                        noOfBytesRead = inputStream.read(byteArray);
                        if (noOfBytesRead == -1) {
                            break;
                        }
                        outputStream.write(byteArray, 0, noOfBytesRead);
                        //System.out.println("while");
                    }
                    inputStream.close();
                    outputStream.close();
                }
               setResultMessage("record exists");
                System.out.println(getConsultFlag());
                setResume("Resume");
                
            }
            else{
                   System.out.println("in else");
                setResume("noResume");
                System.out.println("in reqqlllllllllllllll"+getConsult_id());
               // httpServletRequest.getSession(false).setAttribute("resultMessage", "<font style='color:red;font-size:15px;'>No Attachment exists !!</font>");
               resultType=INPUT;
              }
        } catch (FileNotFoundException ex) {
            System.out.println("finle not found");
             setResume("noFile");
           resultType=INPUT;
            
        } catch (IOException ex) {
            System.out.println("ioeeeeeee"+ex.getMessage());
        } catch (Exception ex) {
            System.out.println("eeeeeeee"+ex.getMessage());
            // ex.printStackTrace();
        } 
        return resultType;
    }
    public String getConsult_AttachmentLocation() {
        return consult_AttachmentLocation;
    }

    public void setConsult_AttachmentLocation(String consult_AttachmentLocation) {
        this.consult_AttachmentLocation = consult_AttachmentLocation;
    }

    public int getAcc_attachment_id() {
        return acc_attachment_id;
    }

    public void setAcc_attachment_id(int acc_attachment_id) {
        this.acc_attachment_id = acc_attachment_id;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getCustomerFlag() {
        return customerFlag;
    }

    public void setCustomerFlag(String customerFlag) {
        this.customerFlag = customerFlag;
    }

    public int getAccountSearchID() {
        return accountSearchID;
    }

    public void setAccountSearchID(int accountSearchID) {
        this.accountSearchID = accountSearchID;
    }

   
    public int getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }

    public int getConsult_id() {
        return consult_id;
    }

    public void setConsult_id(int consult_id) {
        this.consult_id = consult_id;
    }

    public String getConsultFlag() {
        return consultFlag;
    }

    public void setConsultFlag(String consultFlag) {
        this.consultFlag = consultFlag;
    }

    public String getTechSearch() {
        return techSearch;
    }

    public void setTechSearch(String techSearch) {
        this.techSearch = techSearch;
    }

    public String getReviewStartDate() {
        return reviewStartDate;
    }

    public void setReviewStartDate(String reviewStartDate) {
        this.reviewStartDate = reviewStartDate;
    }

    public String getReviewEndDate() {
        return reviewEndDate;
    }

    public void setReviewEndDate(String reviewEndDate) {
        this.reviewEndDate = reviewEndDate;
    }

    public String getTechReviewStatus() {
        return techReviewStatus;
    }

    public void setTechReviewStatus(String techReviewStatus) {
        this.techReviewStatus = techReviewStatus;
    }

    public String getConsult_name() {
        return consult_name;
    }

    public void setConsult_name(String consult_name) {
        this.consult_name = consult_name;
    }

    public String getConsult_email() {
        return consult_email;
    }

    public void setConsult_email(String consult_email) {
        this.consult_email = consult_email;
    }

    public String getConsult_skill() {
        return consult_skill;
    }

    public void setConsult_skill(String consult_skill) {
        this.consult_skill = consult_skill;
    }

    public String getConsult_phno() {
        return consult_phno;
    }

    public void setConsult_phno(String consult_phno) {
        this.consult_phno = consult_phno;
    }

    public String getJdId() {
        return jdId;
    }

    public void setJdId(String jdId) {
        this.jdId = jdId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

   
}