/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.invoice.sow;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.Properties;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author miracle
 */
public class SOWAction extends ActionSupport implements ServletRequestAware {
    
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private String resultType;
    private int userSeessionId;
    private int sessionOrgId;
    private List sowVTO;
    private String typeOfUser;
    private String consultantName;
    private String vendorName;
    private String requirementName;
    private String customerName;
    private String status;
    private String consultantId;
    private String requirementId;
    private String customerId;
    private String vendorId;
    private String rateSalary;
    private String vendorComments;
    private String customerComments;
    private String payTerms;
    private String payRate;
    private String procResults;
    private File file;
    private String fileContentType;
    private String fileFileName;
    private String filesPath;
    private List sowAttachmentVTO;
    private String tabFlag;
    private String resultMessage;
    private String fileExists;
    private String uploadRes;
    
    public String getSowList() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("im in getSowList Action...............................");
                userSeessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                setSessionOrgId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setTypeOfUser(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString());
                setSowVTO(ServiceLocator.getSOWService().getSowDetails(this));
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }
    
    public String getSOWSearchResults() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("im in getSowList Action...............................");
                userSeessionId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString());
                setSessionOrgId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setTypeOfUser(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString());
                setSowVTO(ServiceLocator.getSOWService().getSOWSearchResults(this));
                System.out.println(">>>>LIST SIZE::::" + getSowVTO().size());
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }
    
    public String getSOWEditDetails() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("im in getSowList Action...............................");
                setUserSeessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setSessionOrgId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setTypeOfUser(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString());
                ServiceLocator.getSOWService().getSOWEditDetails(this);
                setSowVTO(ServiceLocator.getSOWService().getSOWAttachments(this));
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }
    
    public String doAddUpdateSOWDetails() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("im in doAddUpdateSOWDetails Action...............................");
                setUserSeessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setSessionOrgId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setTypeOfUser(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString());
                System.out.println(">>>>>>>>>getRateSalary() "+getRateSalary());
                procResults = ServiceLocator.getSOWService().doAddUpdateSOWDetails(this);
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }

    /**
     * *****************************************************************************
     * Date : June 1 2015
     *
     * Author : ramakrishna<lankireddy@miraclesoft.com>
     *
     * addSOWAttachments() method can be used to add the SOW
     *
     * *****************************************************************************
     */
    public String addSOWAttachments() throws Exception {
        System.out.println("entered into updateAttachment  action");
        resultType = LOGIN;
        int addresult = 0;
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%% File Data Start %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("File Name is:" + getFileFileName());
        System.out.println("File ContentType is:" + getFileContentType());
        System.out.println("Files Directory is:" + filesPath + "-------->" + getFilesPath());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%% File Data End %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("User Id----------------------------->" + httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID));
        
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString() != null) {
                if (getFileFileName() == null) {
                    System.out.println("file is null so it adds only data in  table");
                } else {
                    filesPath = Properties.getProperty("Sow.Attachment");
                    File createPath = new File(filesPath);
                    Date dt = new Date();
                    /*The month is generated from here*/
                    
                    String month = "";
                    if (dt.getMonth() == 0) {
                        month = "Jan";
                    } else if (dt.getMonth() == 1) {
                        month = "Feb";
                    } else if (dt.getMonth() == 2) {
                        month = "Mar";
                    } else if (dt.getMonth() == 3) {
                        month = "Apr";
                    } else if (dt.getMonth() == 4) {
                        month = "May";
                    } else if (dt.getMonth() == 5) {
                        month = "Jun";
                    } else if (dt.getMonth() == 6) {
                        month = "Jul";
                    } else if (dt.getMonth() == 7) {
                        month = "Aug";
                    } else if (dt.getMonth() == 8) {
                        month = "Sep";
                    } else if (dt.getMonth() == 9) {
                        month = "Oct";
                    } else if (dt.getMonth() == 10) {
                        month = "Nov";
                    } else if (dt.getMonth() == 11) {
                        month = "Dec";
                    }
                    short week = (short) (Math.round(dt.getDate() / 7) + 1);
                    
                    createPath = new File(createPath.getAbsolutePath() + File.separator + getConsultantId() + File.separator + String.valueOf(dt.getYear() + 1900) + File.separator + month + File.separator + String.valueOf(week));
                    createPath.mkdir();
                    
                    File theFile = new File(createPath.getAbsolutePath());
                    
                    System.out.println("File Path::" + theFile);
                    
                    setFilesPath(theFile.toString());
                    /*copies the file to the destination*/
                    File destFile = new File(theFile, getFileFileName());
                    System.out.println(">>DestFile>>" + destFile);
                    //setFilePath(destFile.toString());
                    System.out.println("THE FINAL PATH IT IS SET IN THE ACTION::>>>>>" + getFilesPath());
                    FileUtils.copyFile(file, destFile);
                }
                setUserSeessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                addresult = ServiceLocator.getSOWService().doAddSOWAttachment(this);
                setTabFlag("AT");
                if(addresult==1){
                    setUploadRes("S");
                }else{
                    setUploadRes("F");
                }
                
                System.out.println(">>>>>>>>>>>>"+getTabFlag()+">>"+getUploadRes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        } catch (Exception ex) {
            System.out.println("Exception in update attachment  action-->" + ex.getMessage());
            resultType = ERROR;
        }
        
        return SUCCESS;
    }
    
    public String getSOWAttachments() {
        resultType = LOGIN;
        try {
            if (httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null) {
                System.out.println("im in doAddUpdateSOWDetails Action...............................");
                setUserSeessionId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID).toString()));
                setSessionOrgId(Integer.parseInt(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.ORG_ID).toString()));
                setTypeOfUser(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.TYPE_OF_USER).toString());
                setSowVTO(ServiceLocator.getSOWService().getSOWAttachments(this));
                
                resultType = SUCCESS;
            }
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());
            resultType = ERROR;
        }
        return resultType;
    }
    
    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    
    public int getUserSeessionId() {
        return userSeessionId;
    }
    
    public void setUserSeessionId(int userSeessionId) {
        this.userSeessionId = userSeessionId;
    }
    
    public int getSessionOrgId() {
        return sessionOrgId;
    }
    
    public void setSessionOrgId(int sessionOrgId) {
        this.sessionOrgId = sessionOrgId;
    }
    
    public List getSowVTO() {
        return sowVTO;
    }
    
    public void setSowVTO(List sowVTO) {
        this.sowVTO = sowVTO;
    }
    
    public String getTypeOfUser() {
        return typeOfUser;
    }
    
    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
    
    public String getConsultantName() {
        return consultantName;
    }
    
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getRequirementName() {
        return requirementName;
    }
    
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getConsultantId() {
        return consultantId;
    }
    
    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }
    
    public String getRequirementId() {
        return requirementId;
    }
    
    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    
    public String getRateSalary() {
        return rateSalary;
    }
    
    public void setRateSalary(String rateSalary) {
        this.rateSalary = rateSalary;
    }
    
    public String getVendorComments() {
        return vendorComments;
    }
    
    public void setVendorComments(String vendorComments) {
        this.vendorComments = vendorComments;
    }
    
    public String getCustomerComments() {
        return customerComments;
    }
    
    public void setCustomerComments(String customerComments) {
        this.customerComments = customerComments;
    }
    
    public String getPayTerms() {
        return payTerms;
    }
    
    public void setPayTerms(String payTerms) {
        this.payTerms = payTerms;
    }
    
    public String getPayRate() {
        return payRate;
    }
    
    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }
    
    public String getProcResults() {
        return procResults;
    }
    
    public void setProcResults(String procResults) {
        this.procResults = procResults;
    }
    
    public File getFile() {
        return file;
    }
    
    public void setFile(File file) {
        this.file = file;
    }
    
    public String getFileContentType() {
        return fileContentType;
    }
    
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
    
    public String getFileFileName() {
        return fileFileName;
    }
    
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    
    public String getFilesPath() {
        return filesPath;
    }
    
    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }
    
    public List getSowAttachmentVTO() {
        return sowAttachmentVTO;
    }
    
    public void setSowAttachmentVTO(List sowAttachmentVTO) {
        this.sowAttachmentVTO = sowAttachmentVTO;
    }
    
    public String getTabFlag() {
        return tabFlag;
    }
    
    public void setTabFlag(String tabFlag) {
        this.tabFlag = tabFlag;
    }
    
    public String getResultMessage() {
        return resultMessage;
    }
    
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    
    public String getFileExists() {
        return fileExists;
    }
    
    public void setFileExists(String fileExists) {
        this.fileExists = fileExists;
    }

    public String getUploadRes() {
        return uploadRes;
    }

    public void setUploadRes(String uploadRes) {
        this.uploadRes = uploadRes;
    }

}
