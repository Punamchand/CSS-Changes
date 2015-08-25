/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usr.timesheets;

import com.mss.msp.util.ServiceLocatorException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author miracle
 */
public interface UsrTimesheetService {
    public List getTimesheetListDetails(HttpServletRequest httpServletRequest, UsrTimeSheetAction usrTimeSheetAction) throws ServiceLocatorException;
    public List getTimesheetList(HttpServletRequest httpServletRequest, UsrTimeSheetAction usrTimeSheetAction) throws ServiceLocatorException;
    
    public List getTeamTimeSheetListDefault(HttpServletRequest httpServletRequest, UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;        
    public List getAllTimeSheetList(HttpServletRequest httpServletRequest, UsrTimeSheetAction usrTimeSheetAction) throws ServiceLocatorException;
    public int deleteTimeSheet(HttpServletRequest httpServletRequest, UsrTimeSheetAction usrTimeSheetAction) throws ServiceLocatorException;
    public int AddTimesheet(HttpServletRequest httpServletRequest, UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;
    public List getTeamTimeSheetList(HttpServletRequest httpServletRequest, UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;
    //public List getTeamTimesheetListDetails(HttpServletRequest httpServletRequest, UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;
    
    public TimesheetVTO getUserTimeSheet(HttpServletRequest httpServletRequest,UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;
    public int editTimeSheet(HttpServletRequest httpServletRequest,UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;
    public int approveRejectTimeSheet(HttpServletRequest httpServletRequest,UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;
    public List getweekStartAndEndDays(Calendar cal);
    public String checkTimeSheetExists(List li,int empID);
    public TimesheetVTO getWeekDaysBean(List li);
     public List getAllTimeSheets(HttpServletRequest httpServletRequest, UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;                
   
     public List getAllTimeSheetsSearch(HttpServletRequest httpServletRequest, UsrTimeSheetAction userTimeSheetAction) throws ServiceLocatorException;        
}
