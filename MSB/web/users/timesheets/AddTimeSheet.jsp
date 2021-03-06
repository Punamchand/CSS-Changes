


<%-- 
    Document   : TimeSheet Add
    Created on : May 21, 2015, 1:55:08 AM
    Author     : miracle
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<%@ page import="com.mss.msp.usr.timesheets.UsrTimeSheetAction"%>


<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: TimeSheet Add Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>

        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/timesheet.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>

    </head>
    <body onload="getProjets();onloadTotal()">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/> 
                </div>
            </div>
        </header>         
        <section id="generalForm"><!--form-->
            <div class="container">
                <div class="row">
                    <s:include value="/includes/menu/LeftMenu.jsp"/> 
                    <!-- content start -->
                    <div class="col-md-9 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items" style="background-color:#F9F9F9">
                            <div id="projects_popup">
                                <div id="projectsOverlay">
                                    <div class="backgroundcolor">
                                        <table>
                                            <tr><td><h4 style=""><font color="#ffffff">Present Projects</font></h4></td>
                                            </tr>
                                            <span class=" pull-right"><h5><a href="timesheetSearch.action" class="projects_popup_close" onclick="addTimeSheetOverlayClose()"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                        </table>
                                    </div>
                                    
                                        <form action="#" theme="simple" >
                                            <div class="overlaytimesheet">
                                                <span><addTimesheerResult></addTimesheerResult></span>


                                                <div class="inner-addSkillDiv-elements textfieldLabel">

                                                    <span style="display: none" id="checkbox1">
                                                        <s:checkbox cssClass="checkboxAlign" name="p1" id="p1" />&nbsp;&nbsp;<s:textfield name="projectOver1" id="projectOver1" cssClass="noBorder textLabel-i" value="" disabled="true" readonly="true" /></span>   
                                                    <span style="display: none" id="checkbox2">
                                                        <br><s:checkbox cssClass="checkboxAlign" name="p2" id="p2" />&nbsp;&nbsp;<s:textfield name="projectOver2" id="projectOver2" cssClass="noBorder textLabel-i" value="" disabled="true" readonly="true" /></span>       
                                                    <span style="display: none" id="checkbox3">
                                                        <br><s:checkbox cssClass="checkboxAlign" name="p3" id="p3" />&nbsp;&nbsp;<s:textfield name="projectOver3" id="projectOver3" cssClass="noBorder textLabel-i" value="" disabled="true" readonly="true" /></span>         
                                                    <span style="display: none" id="checkbox4">
                                                        <br><s:checkbox cssClass="checkboxAlign" name="p4" id="p4" />&nbsp;&nbsp;<s:textfield name="projectOver4" id="projectOver4" cssClass="noBorder textLabel-i" value="" disabled="true" readonly="true" /></span>         
                                                    <span style="display: none" id="checkbox5">
                                                        <br><s:checkbox cssClass="checkboxAlign" name="p5" id="p5" />&nbsp;&nbsp;<s:textfield name="projectOver5" id="projectOver5" cssClass="noBorder textLabel-i" value="" disabled="true" readonly="true" /></span>         

                                                   
                                                </div>
                                            </div>
                                                        <div class="col-md-12">
                                                        <a href="../timesheets/addTimeSheet.action" ><input type="button" class="cssbutton pull-right" value="Go" onclick="return projectsData()"></a>&nbsp; 
                                </div>
                                        </form>
                                     
                                </div>
                            </div>
                            <div id="timesheetMisc_popup">
                                <div id="timesheetMisc_Overlay">
                                    <div class="backgroundcolor">
                                        <table>
                                            <tr><td><h4 style=""><font color="#ffffff">Miscellaneous</font></h4></td>
                                            </tr>
                                            <span class=" pull-right"><h5><a href="" class="timesheetMisc_popup_close" onclick="addTimeSheetOverlayClose()"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                        </table>
                                    </div>
                                    
                                        <form action="#" theme="simple" >
                                            <div class="overlaytimesheetMis">
                                                <span><addTimesheerResult></addTimesheerResult></span>


                                                <div class="inner-addSkillDiv-elements textfieldLabel">


                                                    <s:checkbox cssClass="checkboxAlign" name="vacation" id="vacation" /><label class="labelStyle add-to" id="labelLevelStatusReq">Vacation</label> 

                                                    <br><s:checkbox cssClass="checkboxAlign" name="holiday" id="holiday" /><label class="labelStyle add-to" id="labelLevelStatusReq">Holiday</label>         
                                                </div>
                                            </div>
                                                    <div class="col-md-12">
                                                    <input type="button"  class="cssbutton pull-right" value="Go" onclick="return miscellaneousData()"/>&nbsp; 
                                                    </div>
                                        </form>
                                     
                                </div>
                            </div>

                            <div class="col-lg-14 ">


                                <div class="" id="profileBox" style="float: left; margin-top: 5px">

                                    <div class="backgroundcolor" >
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <font color="#ffffff"> Add TimeSheet </font>
                                                <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>

                                            </h4>
                                        </div>

                                    </div>

                                </div>

                                <div class="col-lg-12">
                                    <s:form id="timeSheetsForm" action="AddTimesheet.action" method="post" theme="simple" >

                                        <div class="col-md-14"> 
                                            <span><edittimesheetserror></edittimesheetserror></span>
                                        </div>

                                        <span cellspacing="30">
                                           <%-- <div class="inner-addtaskdiv-elements">
                                              <label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">  <s:text name="Employee Name :"/>
                                                  <s:property value="empName"/></label>
                                            </div>--%>
                                           <s:hidden name="usr_id" id="usr_id" value=""/>
                                           <s:hidden name="timesheetFlag" id="timesheetFlag" value=""/>
                                           
                                            <div class="inner-addtaskdiv-elements1">
                                                <span style="display: none" id="projectButton" > <a href="#" ><input type="button" class="timesheetbutton projects_popup_open pull-left" style="margin-right: 3px;" value="Projects" onclick="ProjectsOverlayOpen1()"></a> </span>
                                                <a href="#" ><input type="button" class="timesheetbutton timesheetMisc_popup_open pull-left" value="Miscellaneous" onclick="MiscellaneousOverlayOpen()"></a>
                                              <%-- <s:reset cssClass="timesheetbutton pull-right" style="margin-left:4px;" value="Clear" theme="simple"  />  --%>   
                                            </div>
                                                <br/>
                                                <br/>
                                            <div class="inner-addtaskdiv-elements1">
                                                <label class="labelStyle" id="labelLevelStatusReq">Week Start Date:</label> <s:textfield cssClass="timesheetdatebox" name="timeSheetStartDate" id="timeSheetStartDate" placeholder="StartDate" value="%{timeSheetVTO.timeSheetStartDate}" onchange="return betweenDate();" readonly="true" cssStyle="z-index: 10000004;" tabindex="1"/>
                                                <label class="labelStyle" id="labelLevelStatusReq">Week End Date:</label><s:textfield cssClass="timesheetdatebox" name="timeSheetEndDate" value="%{timeSheetVTO.timeSheetEndDate}" id="timeSheetEndDate" placeholder="EndDate" onchange="return betweenDate();" readonly="true" tabindex="1"/>
                                                <label class="labelStyle" id="labelLevelStatusReq">Submit Date:</label> <s:textfield cssClass="timesheetdatebox" name="timeSheetSubmittedDate" id="timeSheetSubmittedDate" placeholder="SubmitDate" value="%{timeSheetVTO.submittedDate}" onchange="return betweenDate();" cssStyle="z-index: 10000004;" readonly="true" tabindex="1"/>
                                            </div>
                                           <%-- <div class="inner-addtaskdiv-elements1">
                                                <a href="#" ><input type="button" class="timesheetbutton projects_popup_open" value="new" onclick="ProjectsOverlayOpen1()"></a>
                                                <a href="#" ><input type="button" class="timesheetbutton timesheetMisc_popup_open" value="Miscellaneous" onclick="MiscellaneousOverlayOpen()"></a>
                                            </div>--%>
                                            <div>            
                                                <div class="inner-addtaskdiv-elements" id="addtimesheet">
                                                    <table class="responsive"> 
                                                        <tr>
                                                            <td><label class="labelStyle ReqinputStyleTime add-to" id="labelLevelStatusReq" ></label></td>
                                                            <td><s:textfield name="weeklyDates1" id="weeklyDates1" cssClass="noBorder tsDate tsColor" value="%{timeSheetVTO.weeklyDates1}" readonly="true" tabindex="-1"/></td>
                                                            <td><s:textfield name="weeklyDates2" id="weeklyDates2" cssClass="noBorder tsDate" value="%{timeSheetVTO.weeklyDates2}" readonly="true" tabindex="-1"/></td>
                                                            <td><s:textfield name="weeklyDates3" id="weeklyDates3" cssClass="noBorder tsDate" value="%{timeSheetVTO.weeklyDates3}" readonly="true" tabindex="-1"/></td>
                                                            <td><s:textfield name="weeklyDates4" id="weeklyDates4" cssClass="noBorder tsDate" value="%{timeSheetVTO.weeklyDates4}" readonly="true" tabindex="-1"/></td>
                                                            <td><s:textfield name="weeklyDates5" id="weeklyDates5" cssClass="noBorder tsDate" value="%{timeSheetVTO.weeklyDates5}" readonly="true" tabindex="-1"/></td>
                                                            <td><s:textfield name="weeklyDates6" id="weeklyDates6" cssClass="noBorder tsDate" value="%{timeSheetVTO.weeklyDates6}" readonly="true" tabindex="-1"/></td>
                                                            <td><s:textfield name="weeklyDates7" id="weeklyDates7" cssClass="noBorder tsDate tsColor" value="%{timeSheetVTO.weeklyDates7}" readonly="true" tabindex="-1"/></td>

                                                        </tr>
                                                        <tr>
                                                            <td><label class="labelStyle ReqinputStyleTime" id="labelLevelStatusReq"> Projects</label></td>

                                                            <td> <label class="labelStyle ReqinputStyleTimeSheet timesheet tsColor" id="labelLevelStatusReq">Sun</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet timesheet" id="labelLevelStatusReq">Mon</label></td>
                                                            <td> <label class="labelStyle ReqinputStyleTimeSheet timesheet" id="labelLevelStatusReq">Tue</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet timesheet" id="labelLevelStatusReq">Wed</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet timesheet" id="labelLevelStatusReq">Thu</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet timesheet" id="labelLevelStatusReq">Fri</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet timesheet tsColor" id="labelLevelStatusReq">Sat</label></td>
                                                            <td><label class="labelStyle-i ReqinputStyleTimeSheet" id="labelLevelStatusReq">Total</label></td>
                                                        </tr> 
                                                        &nbsp;&nbsp;&nbsp; 
                                                        <%--  <tr  id ="projectdash" >

                                                            <td> <span style="height:50px"><a href="#" ><input type="button" class="timesheetbutton projects_popup_open" value="new" onclick="ProjectsOverlayOpen1()"></a></span></td>

                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                           <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                        </tr>--%>

                                                        <tr style="display:none" id ="projectid1">

                                                            <td>

                                                                <s:textfield name="project1" id="project1" cssClass="noBorder textLabel" value="" disabled="true" readonly="true" />
                                                                <s:hidden name="project1key" id="project1key" cssClass="noBorder textLabel" value="0" readonly="true" /></td>
                                                            <td><s:textfield name="projectNameSun1" id="projectNameSun1" cssClass="form-control SmallTextBox_Time" onchange="return projectSun1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameMon1" id="projectNameMon1" cssClass="form-control SmallTextBox_Time" onchange="return projectMon1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameTue1" id="projectNameTue1" cssClass="form-control SmallTextBox_Time" onchange="return projectTue1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameWed1" id="projectNameWed1" cssClass="form-control SmallTextBox_Time" onchange="return projectWed1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameThu1" id="projectNameThu1" cssClass="form-control SmallTextBox_Time" onchange="return projectThu1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameFri1" id="projectNameFri1" cssClass="form-control SmallTextBox_Time" onchange="return projectFri1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameSat1" id="projectNameSat1" cssClass="form-control SmallTextBox_Time" onchange="return projectSat1();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameAll1" id="projectNameAll1" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr>
                                                        <tr style="display:none" id ="projectid2">
                                                            <td><s:textfield name="project2" id="project2" cssClass="noBorder textLabel" value="" disabled="true" />
                                                                <s:hidden name="project2key" id="project2key" cssClass="noBorder textLabel" value="0" readonly="true" /></td>
                                                            <td><s:textfield name="projectNameSun2" id="projectNameSun2" cssClass="form-control SmallTextBox_Time" onchange="return projectSun2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameMon2" id="projectNameMon2" cssClass="form-control SmallTextBox_Time" onchange="return projectMon2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameTue2" id="projectNameTue2" cssClass="form-control SmallTextBox_Time" onchange="return projectTue2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameWed2" id="projectNameWed2" cssClass="form-control SmallTextBox_Time" onchange="return projectWed2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameThu2" id="projectNameThu2" cssClass="form-control SmallTextBox_Time" onchange="return projectThu2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameFri2" id="projectNameFri2" cssClass="form-control SmallTextBox_Time" onchange="return projectFri2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameSat2" id="projectNameSat2" cssClass="form-control SmallTextBox_Time" onchange="return projectSat2();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameAll2" id="projectNameAll2" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr>
                                                        <tr style="display:none" id ="projectid3">
                                                            <td><s:textfield name="project3" id="project3" cssClass="noBorder textLabel" value="" disabled="true" />
                                                                <s:hidden name="project3key" id="project3key" cssClass="noBorder textLabel" value="0" readonly="true" /></td>
                                                            <td><s:textfield name="projectNameSun3" id="projectNameSun3" cssClass="form-control SmallTextBox_Time" onchange="return projectSun3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameMon3" id="projectNameMon3" cssClass="form-control SmallTextBox_Time" onchange="return projectMon3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameTue3" id="projectNameTue3" cssClass="form-control SmallTextBox_Time" onchange="return projectTue3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameWed3" id="projectNameWed3" cssClass="form-control SmallTextBox_Time" onchange="return projectWed3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameThu3" id="projectNameThu3" cssClass="form-control SmallTextBox_Time" onchange="return projectThu3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameFri3" id="projectNameFri3" cssClass="form-control SmallTextBox_Time" onchange="return projectFri3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameSat3" id="projectNameSat3" cssClass="form-control SmallTextBox_Time" onchange="return projectSat3();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameAll3" id="projectNameAll3" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr>
                                                        <tr style="display:none" id ="projectid4">
                                                            <td><s:textfield name="project4" id="project4" cssClass="noBorder textLabel" value="" disabled="true" />
                                                                <s:hidden name="project4key" id="project4key"  value="0" readonly="true" /></td>
                                                            <td><s:textfield name="projectNameSun4" id="projectNameSun4" cssClass="form-control SmallTextBox_Time" onchange="return projectSun4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameMon4" id="projectNameMon4" cssClass="form-control SmallTextBox_Time" onchange="return projectMon4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameTue4" id="projectNameTue4" cssClass="form-control SmallTextBox_Time" onchange="return projectTue4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameWed4" id="projectNameWed4" cssClass="form-control SmallTextBox_Time" onchange="return projectWed4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameThu4" id="projectNameThu4" cssClass="form-control SmallTextBox_Time" onchange="return projectThu4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameFri4" id="projectNameFri4" cssClass="form-control SmallTextBox_Time" onchange="return projectFri4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameSat4" id="projectNameSat4" cssClass="form-control SmallTextBox_Time" onchange="return projectSat4();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameAll4" id="projectNameAll4" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr>
                                                        <tr style="display:none" id ="projectid5">
                                                            <td><s:textfield name="project5" id="project5" cssClass="noBorder textLabel" value="" disabled="true" />
                                                                <s:hidden name="project5key" id="project5key" cssClass="noBorder textLabel" value="0" readonly="true" /></td>
                                                            <td><s:textfield name="projectNameSun5" id="projectNameSun5" cssClass="form-control SmallTextBox_Time" onchange="return projectSun5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameMon5" id="projectNameMon5" cssClass="form-control SmallTextBox_Time" onchange="return projectMon5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameTue5" id="projectNameTue5" cssClass="form-control SmallTextBox_Time" onchange="return projectTue5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameWed5" id="projectNameWed5" cssClass="form-control SmallTextBox_Time" onchange="return projectWed5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameThu5" id="projectNameThu5" cssClass="form-control SmallTextBox_Time" onchange="return projectThu5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameFri5" id="projectNameFri5" cssClass="form-control SmallTextBox_Time" onchange="return projectFri5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameSat5" id="projectNameSat5" cssClass="form-control SmallTextBox_Time" onchange="return projectSat5();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="projectNameAll5" id="projectNameAll5" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr>



                                                        <tr>
                                                            <td><label class="labelStyle ReqinputStyleTime add-to" id="labelLevelStatusReq">Internal</label></td>
                                                            <td><s:textfield name="internalSun" id="internalSun" cssClass=" form-control SmallTextBox_Time" onchange="return internalSunday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalMon" id="internalMon" cssClass=" form-control SmallTextBox_Time" onchange="return internalMonday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalTue" id="internalTue" cssClass=" form-control SmallTextBox_Time" onchange="return internalTuesday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalWed" id="internalWed" cssClass=" form-control SmallTextBox_Time" onchange="return internalWednesday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalThu" id="internalThu" cssClass=" form-control SmallTextBox_Time" onchange="return internalThursday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalFri" id="internalFri" cssClass=" form-control SmallTextBox_Time" onchange="return internalFriday();"  value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalSat" id="internalSat" cssClass=" form-control SmallTextBox_Time" onchange="return internalSaturday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="internalAll" id="internalAll" cssClass=" form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr> 
                                                        <%-- <tr id ="miscellaneousDash">

                                                            <td><input type="button" action="" class="timesheetbutton timesheetMisc_popup_open" value="Miscellaneous" onclick="MiscellaneousOverlayOpen()"></td>

                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle RevacationAllqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                            <td><label class="labelStyle ReqinputStyleTimeSheet" id="labelLevelStatusReq">-</label></td>
                                                        </tr>--%>
                                                        <tr  style="display: none" id="miscellaneousVacation">
                                                            <td><label class="labelStyle ReqinputStyleTime add-to" id="labelLevelStatusReq">Vacation</label></td>
                                                            <td><s:textfield name="vacationSun" id="vacationSun" cssClass="form-control SmallTextBox_Time" onchange="return vacationSunday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationMon" id="vacationMon" cssClass="form-control SmallTextBox_Time" onchange="return vacationMonday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationTue" id="vacationTue" cssClass="form-control SmallTextBox_Time" onchange="return vacationTuesday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationWed" id="vacationWed" cssClass="form-control SmallTextBox_Time" onchange="return vacationWednesday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationThu" id="vacationThu" cssClass="form-control SmallTextBox_Time" onchange="return vacationThursday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationFri" id="vacationFri" cssClass="form-control SmallTextBox_Time" onchange="return vacationFriday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationSat" id="vacationSat" cssClass="form-control SmallTextBox_Time" onchange="return vacationSaturday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="vacationAll" id="vacationAll" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr> 
                                                        <tr style="display: none" id="miscellaneousHoliday">
                                                            <td><label class="labelStyle ReqinputStyleTime add-to" id="labelLevelStatusReq">Holiday</label></td>
                                                            <td><s:textfield name="holidaySun" id="holidaySun" cssClass="form-control SmallTextBox_Time" onchange="return holidaySunday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidayMon" id="holidayMon" cssClass="form-control SmallTextBox_Time" onchange="return holidayMonday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidayTue" id="holidayTue" cssClass="form-control SmallTextBox_Time" onchange="return holidayTuesday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidayWed" id="holidayWed" cssClass="form-control SmallTextBox_Time" onchange="return holidayWednesday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidayThu" id="holidayThu" cssClass="form-control SmallTextBox_Time" onchange="return holidayThursday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidayFri" id="holidayFri" cssClass="form-control SmallTextBox_Time" onchange="return holidayFriday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidaySat" id="holidaySat" cssClass="form-control SmallTextBox_Time" onchange="return holidaySaturday();" value="0.0" onkeypress="return acceptNumbers(event)" tabindex="1"/></td>
                                                            <td><s:textfield name="holidayAll" id="holidayAll" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr> 
                                                        <%--<tr style="display: none" id="miscellaneousComptime">
                                                            <td><label class="labelStyle ReqinputStyleTime" id="labelLevelStatusReq" style="margin-bottom: 10px;">CompTime</label></td>
                                                            <td><s:textfield name="comptimeSun" id="comptimeSun" cssClass="form-control SmallTextBox_Time" onchange="return comptimeSunday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeMon" id="comptimeMon" cssClass="form-control SmallTextBox_Time" onchange="return comptimeMonday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeTue" id="comptimeTue" cssClass="form-control SmallTextBox_Time" onchange="return comptimeTuesday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeWed" id="comptimeWed" cssClass="form-control SmallTextBox_Time" onchange="return comptimeWednesday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeThu" id="comptimeThu" cssClass="form-control SmallTextBox_Time" onchange="return comptimeThursday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeFri" id="comptimeFri" cssClass="form-control SmallTextBox_Time" onchange="return comptimeFriday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeSat" id="comptimeSat" cssClass="form-control SmallTextBox_Time" onchange="return comptimeSaturday();" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                            <td><s:textfield name="comptimeAll" id="comptimeAll" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" onkeypress="return acceptNumbers(event)"/></td>
                                                        </tr>--%> 
                                                        <tr>
                                                            <td><label class="labelStyle-i ReqinputStyleTime add-to" id="labelLevelStatusReq">Total</label></td>
                                                            <td><s:textfield name="totalSun" id="totalSun" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalMon" id="totalMon" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalTue" id="totalTue" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalWed" id="totalWed" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalThu" id="totalThu" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalFri" id="totalFri" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalSat" id="totalSat" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                            <td><s:textfield name="totalAll" id="totalAll" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/></td>
                                                        </tr> 
                                                    </table>

                                                </div>  
                                                <div class="col-md-12" style="margin-left: -15px;">
                                                    <div class="inner-addtaskdiv-elements">    
                                                        <div class="updateCss ReqinputStyleTime" > 

                                                            <label class="labelStyle-i add-to" id="labelLevelStatusReq">Total Billable Hrs</label>
                                                           
                                                            <label class="labelStyle-i add-to" id="labelLevelStatusReq">Total Holiday Hrs</label>
                                                            <label class="labelStyle-i add-to" id="labelLevelStatusReq">Total Vacation Hrs</label>
                                                            



                                                        </div>
                                                        
                                                        <div class="updateCss ReqinputStyleTimeSheet ">
                                                           
                                                            <s:textfield name="totalBillHrs" id="totalBillHrs" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/>
                                                            <s:textfield name="totalHolidayHrs" id="totalHolidayHrs" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/>
                                                            <s:textfield name="totalVacationHrs" id="totalVacationHrs" cssClass="form-control SmallTextBox_Time" readonly="true" value="0.0" tabindex="-1"/>
                                                            

                                                        </div>
                                                        <div class="updateCss form-group req-textarea" style="width:60%">
                                                            <label class="labelStyle" id="labelLevelStatusReq">Notes:</label> <s:textarea name="timeSheetNotes" id="timeSheetNotes" cssClass="commentsStyle" value="" placeholder="Enter Here" rows="3" onkeydown="timeSheetsNotes(this)" onblur="removeErrorMessages()" tabindex="1"/>
                                                        </div>
                                                        <div class="col-sm-8 col-md-offset-3">
                                                          <div class="charNum" id="notes"></div>
                                                        </div>

                                                    </div>

                                                </div>       
                                                        <s:label><font color="red">*NOTE: After submitting this timesheet you can't edit.</font></s:label>
                                                <div class="inner-addtaskdiv-elements1"> 
                                                   <s:submit cssClass="timesheetbutton pull-right"  value="save&submit" theme="simple" cssStyle="margin-left:3px" onclick="setTemVar1()"/>
                                                   <s:submit cssClass="timesheetbutton pull-right"  value="save" theme="simple" cssStyle="margin-left:3px"/>
                                                   <a href="#" ><input type="button" class="timesheetbutton pull-right" value="Clear" onclick="clearTimesheets()"></a>
                                                <s:hidden name="tempVar" id="tempVar" value=""/>
                                                </div>
                                                  <%--  <s:reset cssClass="cssbutton " value="Clear" theme="simple"  /> &nbsp;    
                                                    <s:submit cssClass="cssbutton"  value="submit" theme="simple"/>
                                                </div>--%>
                                            </s:form>       
                                        </div>   




                                </div>

                               </div>                  
                                <%--close of future_items--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- content end -->

    </section>



    <footer id="footer"><!--Footer-->
        <div class="footer-bottom" id="footer_bottom">
            <div class="container">
                <s:include value="/includes/template/footer.jsp"/>
            </div>
        </div>
    </footer> 

</body>
</html>

