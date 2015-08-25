/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usr.miscellaneous;

import com.mss.msp.util.HibernateServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author miracle
 */
public class UserMiscellaneousServiceImpl implements UserMiscellaneousService{
    
   
    
     /*******************************************************************************
 * Date    : April 18 2015
 *
 * Author  : manikanta eeralla<meeralla@miraclesoft.com>
        
 * getMisscellousDetails(int userid) method can be used to get misscellousInfo by using user id, 
 * And returns record for the respected user
 * *****************************************************************************
 */   
    
     public UsrMiscellaneousVTO getMisscellousDetails(int userid) throws ServiceLocatorException
     {
         UsrMiscellaneousVTO usrMiscellaneousVTO;
       try {
         /* HibernateServiceLocator is a utility user-defined class that retrives session object */
          
 
          Session session = HibernateServiceLocator.getInstance().getSession();
          
        /* Every DB operation is a transaction so we have to bedin the transation */
          Transaction transaction=session.beginTransaction();
          
        /* UsrMiscellaneousVTO is POJO class. By using get method of hibernate we can the record for specified user */
          usrMiscellaneousVTO=(UsrMiscellaneousVTO)session.get(UsrMiscellaneousVTO.class, userid);
   
        // System.out.println(usrMiscellaneousVTO.toString());
         
          /* After completion of transaction close the session */
          session.close();
          } catch (Exception ex) {
            System.out.println("ingetMisscellousDetails"+ex.getMessage());
            throw new ServiceLocatorException(ex);
        }
          return usrMiscellaneousVTO;
     }
}
