/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.general;

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
public class DownloadAction implements Action, ServletRequestAware, ServletResponseAware {

    private String inputPath;
    // private String URL="/images/flower.GIF";
    private String contentDisposition = "FileName=inline";
    public InputStream inputStream;
    public OutputStream outputStream;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    //private int id;
    private String fileName;
    private String attachmentLocation;
    private int attachmentId;
    
    private String resultType;
    private String downloadFlag;
    private int taskid;

    /**
     * Creates a new instance of DownloadAction
     */
    public DownloadAction() {
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
    public String downloadAttachment() throws Exception{
        resultType=SUCCESS;
        try {
            //this.setAttachmentLocation(httpServletRequest.getParameter("attachmentLocation"));
            this.setAttachmentId(Integer.parseInt(httpServletRequest.getParameter("attachmentId").toString()));
            System.out.println("=================>Entered into the DownloadAction");
            try{
                this.setAttachmentLocation(dataSourceDataProvider.getInstance().getAttachmentLocation(this.getAttachmentId()));
            }catch(ServiceLocatorException se)
            {
                System.out.println(se.getMessage());
            }
            System.out.println("=================>" + attachmentLocation);
            fileName = this.getAttachmentLocation()
                    .substring(this.getAttachmentLocation().lastIndexOf(Properties.getProperty("OS.Compatabliliy.Download")) + 1, getAttachmentLocation().length());
            httpServletResponse.setContentType("application/force-download");
            System.out.println("=================>" + fileName);
            System.out.println("getAttachmentLocation()-->" + getAttachmentLocation());
             if ( !"null".equals(getAttachmentLocation()) && getAttachmentLocation() != null && getAttachmentLocation().length() != 0) {

            File file = new File(getAttachmentLocation());
            inputStream = new FileInputStream(file);
            outputStream = httpServletResponse.getOutputStream();
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
            setDownloadFlag("AttachmentExists");
             inputStream.close();
                outputStream.close();
             }
             else
             {
               setDownloadFlag("noAttachment");
               resultType = INPUT;
             }
        } catch (FileNotFoundException ex) {
           System.out.println("File not Found");
           setDownloadFlag("noFile");
           resultType = INPUT;
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
      return resultType;
    }

    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }
    
}
