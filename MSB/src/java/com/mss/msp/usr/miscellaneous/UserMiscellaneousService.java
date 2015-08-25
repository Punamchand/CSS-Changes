/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usr.miscellaneous;


import com.mss.msp.util.ServiceLocatorException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public interface UserMiscellaneousService {
    
    
 public UsrMiscellaneousVTO getMisscellousDetails(int userid) throws ServiceLocatorException;
    
   
    
}
