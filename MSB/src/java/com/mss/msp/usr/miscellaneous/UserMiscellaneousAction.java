/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usr.miscellaneous;

import com.mss.msp.util.ApplicationConstants;
import com.mss.msp.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 *  @author Vinod Siram <vsiram@miraclesoft.com>
 */
public class UserMiscellaneousAction extends ActionSupport implements ServletRequestAware{
     public UserMiscellaneousAction() {
    }
    
    /**
     * The httpServletRequest is used for storing httpServletRequest Object.
     */
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    /**
     * The resultMessage is used for storing resultMessage.
     */
    private String resultMessage;
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    /**
     *  Method : getting the misscelloneous details
     *  
     */
    public void getMisscellousDetails(){
        
       // resultMessage = LOGIN;
       // if(httpServletRequest.getSession(false).getAttribute(ApplicationConstants.USER_ID) != null){
            try{
              
                
                 UsrMiscellaneousVTO misscellaneousDetails = ServiceLocator.getUserMiscellaneousService().getMisscellousDetails(getUserid());
                
                 /*System.out.println("user id "+misscellaneousDetails.getUserid());
                 System.out.println("dept id "+misscellaneousDetails.getDeptId());
                 System.out.println("is team lead "+misscellaneousDetails.getIsTeamLead());
                 System.out.println("is pmo "+misscellaneousDetails.getIsPMO());
                 System.out.println("is sbteam "+misscellaneousDetails.getIsSbteam());*/
           // resultMessage = SUCCESS;
            }catch (Exception ex) {
            //List errorMsgList = ExceptionToListUtility.errorMessages(ex);

           // httpServletRequest.getSession(false).setAttribute("errorMessage:", ex.toString());

                System.out.println("In catch");
                System.out.println(ex);
           // resultMessage = ERROR;
            }
        //}// Session validator if END
        //return resultMessage;
    }
    
    
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     *
     * This method is used to set the Servlet Response
     *
     * @param httpServletResponse
     */
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
    
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    
    public String getResultMessage() {
        return resultMessage;
    }
    
}