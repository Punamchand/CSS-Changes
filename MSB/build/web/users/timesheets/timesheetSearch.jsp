<%-- 
    Document   : dashboard
    Created on : Feb 3, 2015, 7:50:23 PM
    Author     : Nagireddy
--%>



<%@ page import="com.mss.msp.usr.timesheets.TimesheetVTO"%>
<%@ page import="com.mss.msp.usr.timesheets.UsrTimeSheetAction"%>
<%@ page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<!DOCTYPE html>
<html>
    <head>


        <!-- new styles -->

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Time sheets Search</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">

        <%-- aklakh js single file start --%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <%-- aklakh js single file end --%>
        <%-- aklakh css single file start --%>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <%-- aklakh css single file end --%>
        <%-- for date picket start--%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>

        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <%-- for date picket end--%>
        
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/general/timesheet.js"/>"></script>

        <sx:head />

        <script type="text/javascript">
            var myCalendar,myCalendar1,mycalenderweek;
            //,"docdatepickerfrom1","docdatepicker1"
            function doOnLoad() {
                // alert("hii");docdatepickerfrom","docdatepicker
                myCalendar = new dhtmlXCalendarObject(["docdatepickerfrom","docdatepicker"]);
                 //alert("hii1");
                myCalendar.setSkin('omega');
                // alert("hii2");
                //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
                myCalendar.setDateFormat("%m-%d-%Y");
                // alert("hii3");
                //alert("myCalendar")
                mycalenderweek = new dhtmlXCalendarObject(["weekrange"]);
                // alert("hii1");
                mycalenderweek.setSkin('omega');
                // alert("hii2");
                //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
                mycalenderweek.setDateFormat("%m/%d/%Y");
               //
                myCalendar1 = new dhtmlXCalendarObject(["startDate","endDate"]);
                // alert("hii1");
                myCalendar1.setSkin('omega');
                 //alert("hii2");
                myCalendar1.setDateFormat("%Y/%m/%d");
                
                // default date code start from here
                
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth(); //January is 0!
                var yyyy = today.getFullYear();
                //alert(today)
                if(dd<10) {
                    dd='0'+dd
                } 
                if(mm<10) {
                    mm='0'+mm
                } 
                
                //var from = yyyy+'/'+mm+'/01';
                var from = mm+'-01'+'-'+yyyy;
                mm=today.getMonth()+1;
                if(mm<10) {
                    mm='0'+mm
                } 
                dd=today.getDate()+1;
                if(dd<10) {
                    dd='0'+dd
                } 
               // var to = yyyy+'/'+mm+'/'+dd;
                var to = mm+'-'+dd+'-'+ yyyy;
                var odd=today.getDate()+1;
                var overlayDate=yyyy+'/'+mm+'/'+odd;
               // alert(from+" and "+to)
               // document.getElementById("docdatepickerfrom").value=from;
               // document.getElementById("docdatepicker").value=to;
                //document.getElementById("startDate").value=overlayDate;
                //document.getElementById("endDate").value=overlayDate;
            }
             
            
            
            function enterDateRepository()
            {
                // alert(document.documentForm.docdatepickerfrom.value);
                // document.documentForm.docdatepickerfrom.value="";
               // document.getElementById('docdatepickerfrom').value = "";
               // document.getElementById('docdatepicker').value = "";
               // alert("Please select from the Calender !");
                return false;
            }
            
           
            
            
          
            function checkRange() {
                var fromValue=$('#docdatepickerfrom').val();
                var toValue=$('#docdatepicker').val();
                //alert(fromValue+" and "+toValue)
                if(fromValue==""){
                    alert("from date is madatory");
                    return false;
                }
                if(toValue==""){
                    alert("to date is madatory");
                    return false;
                }
                
                /*  var res = fromValue.split(" ");
                fromValue=res[0];
                var res1 = toValue.split(" ");
                toValue=res1[0];
                //alert(fromValue+" and "+toValue)
                if (Date.parse(fromValue) > Date.parse(toValue)) {
                    alert("Invalid Date Range!\nFrrom Date cannot be after To Date!")
                    return false;
                }*/
                var addStartDate = Date.parse(fromValue);
                var addEndDate = Date.parse(toValue);

                var difference = (addEndDate - addStartDate) / (86400000 * 7);
                if (difference < 0) {
                    alert("The start date must come before the end date.");
                    $("errorEduAdd").html(" <b><font color='red'>start date must be less than end date</font></b>.");
                    $("#fromValue").css("border", "1px solid red");
      
                    $("#toValue").css("border","1px solid red");
                    return false;
                }
            }; 
            
            
            $(document).ready(function(){
        
                $('#alert-content').hide();
                $('#alert-check').change(function(){
                                    
                                
                    if($(this).is(":checked"))
                        $('#alert-content').show();
                    else
                        $('#alert-content').hide();

                });
            });
            
            
        </script>


    </head>





    <body style="overflow-x: hidden" onload="doOnLoad();checkWeekStatus()">
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
                    <div class="col-md-10 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items">
                            <div class="col-lg-14 ">
                                <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                    <div class="backgroundcolor" >
                                        <div class="panel-heading">
                                            <h4 class="panel-title">

                                                <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                <font color="#ffffff">Time Sheets Search</font>

                                            </h4>
                                        </div>

                                    </div>
                                    <s:url var="myUrl" action="#"></s:url>
                                    <!-- content start onsubmit="return validateDates()" -->
                                    <div class="col-sm-12">
                                        <s:form action="myTimesheetSearch" theme="simple" >
                                            <br>
                                            <ul class="nav nav-pills">
                                                <s:hidden value="%{reportingPerson}" name="reportingPerson" id="reportingPerson"/>
                                               <div class="col-lg-4">
                                                    <label class="labelStylereq" style="color:#56a5ec;">From Date:</label>  
                                                    <s:textfield cssClass="form-control" label="from date" name="startDate" id="docdatepickerfrom" placeholder="FromDate" value="%{startDate}" tabindex="1"  onkeypress="return enterDateRepository();"/>
                                                </div>
                                                <div class="col-lg-4">
                                                    <label class="labelStylereq" style="color:#56a5ec;">To Date:</label>
                                                    <s:textfield cssClass="form-control" label="To date" name="endDate" placeholder="ToDate" value="%{endDate}" id="docdatepicker" tabindex="2"  onkeypress="return enterDateRepository();"/>
                                                </div>
                                                <div class="col-lg-4">
                                                    <label class="labelStylereq" style="color:#56a5ec;">Status:</label>
                                                    <s:select id="tmstatus" name="tmstatus" cssClass="SelectBoxStyles form-control" headerKey="-1" headerValue="Select Status" theme="simple" list="#@java.util.LinkedHashMap@{'CA':'Entered','SU':'Submitted','AP':'Approved'}"/>
                                                </div>
                                               <div class="col-lg-offset-8">

                                                    <a href="#" onclick="addTimeSheetOverlayOpen();return clearPreviousDate();"><input type="button" class="cssbutton addTimeSheet_open" value="Add Timesheet"></a>&nbsp;&nbsp;&nbsp; 
                                                        <s:submit cssClass="cssbutton" id="searchButton" value="search" />
                                                </div>
                                                <%
                                                    if(request.getAttribute(ApplicationConstants.RESULT_MSG)!=null){
                                                   out.println(request.getAttribute(ApplicationConstants.RESULT_MSG));
                                                   }
                                                %>  
                                            </ul>
                                            <br>
                                              <%
                                                    if(request.getParameter("resultMessage")!=null){
                                                   out.println(request.getParameter("resultMessage"));
                                                   }
                                                %>  
                                        </s:form>
                                        <s:form>
                                            <div class="task_content" id="task_div" align="center" style="display: none" >
                                                 <div>
                                                    <div>
                                                        <span><addTimesheerResult></addTimesheerResult></span>    
                                                        <table id="timesheet_results" class="responsive CSSTable_task" border="5" cell-spacing="2">

                                                            <tbody>
                                                                <tr>
                                                                    <th>Edit</th>
                                                                    <th>Start Date</th>
                                                                    <th>End Date</th>
                                                                    <th>Status</th>
                                                                    <th>Submitted Date</th>
                                                                    <th>Approved/Rejected by</th>
                                                                    <th>Approved/Rejected Date</th>
                                                                    <th>Delete</th>

                                                                </tr>
                                                                <%

                                                                    int c = 0;
                                                                    //if(session.getAttribute("TMResultmessage") != null){
                                                                   // out.println(session.getAttribute("TMResultmessage"));
                                                                   //  }
                                                                    //out.println("request for time sheets---> "+session.getAttribute("timesheetsData"));
                                                                    if ((List) session.getAttribute("timesheetsData") != null) {

                                                                        List l = (List) session.getAttribute("timesheetsData");
                                                                        //out.println("timesheet list"+l);
                                                                        Iterator it = l.iterator();
                                                                        while (it.hasNext()) {
                                                                            if (c == 0) {
                                                                                c = 1;
                                                                            }
                                                                            TimesheetVTO usa = (TimesheetVTO) it.next();
                                                                            int tms_id = usa.getTimesheetid();
                                                                            int usr_id=usa.getUsr_id();
                                                                            String tm_start_date = usa.getDateStart();
                                                                            String tm_end_date = usa.getDateEnd();
                                                                            String cur_status = usa.getReportsto1status();
                                                                            String reports_to = usa.getReportsto1();
                                                                            String tm_submitted_date = usa.getSubmittedDate();
                                                                            String tm_approved_date = usa.getApprovedDate();
                                                                %>
                                                                <tr>
                                                                    <td>

                                                                        <%--s:a href='%{#myUrl}'></s:a--%>
                                                                        <s:url var="myUrl" action="../timesheets/getTimeSheets.action?timesheetFlag=My">
                                                                            <s:param name="timesheetid"><%=tms_id%></s:param>
                                                                        <s:param name="usr_id"><%=usr_id%></s:param>
                                                                            </s:url>

                                                                        <s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/edit_Msb.png"/>" height="20" width="20"></s:a></td>
                                                                    <td><%= tm_start_date%></td>
                                                                    <td><%= tm_end_date%></td>
                                                                    <td><%= cur_status%></td>
                                                                    <td><%= tm_submitted_date%></td>
                                                                    <td><%= reports_to%></td>
                                                                    <td><%= tm_approved_date%></td>
                                                                    <td>
                                                                        <s:url var="deleteUrl" action="deleteTimesheet">
                                                                            <s:param name="timesheetid"><%=tms_id%></s:param>
                                                                            
                                                                        </s:url>
                                                                        <s:a onclick="return confirm('Are you sure you want to delete this record')" id="deleteTimesheet" href='%{#deleteUrl}'><img src="<s:url value="/includes/images/deleteImage.png"/>" height="20" width="25"></s:a></td>
                                                                </tr> 

                                                                <%
                                                                        }

                                                                    }
                                                                    if (c == 0) {
                                                                %>
                                                                <tr>
                                                                    <td colspan="8"><font style="color: red;font-size: 15px;">No Records to display</font></td>
                                                                </tr> 
                                                                <%    }
                                                                %>

                                                            </tbody>
                                                        </table>
                                                        <br/>
                                                        <%
                                                            if (c == 1) {
                                                        %>
                                                       
                                                        <%                                                            }

                                                            if (session.getAttribute("TMResultmessage") != null) {
                                                                session.removeAttribute("TMResultmessage");
                                                            }

                                                        %>


                                                            <div align="right" id="timesheetpageNavPosition" style="margin-right: 0vw;"></div>
                                                        <script type="text/javascript">
                                                            var pager = new Pager('timesheet_results', 10); 
                                                            pager.init(); 
                                                            pager.showPageNav('pager', 'timesheetpageNavPosition'); 
                                                            pager.showPage(1);
                                                        </script>

                                                    </div>
                                                </div>
                                            </div>
                                        </s:form>
                                    </div>
                                </div>
                            </div>
                            <%--close of future_items--%>
                        </div>
                    </div>
                </div>
            </div>
            <!-- content end -->


        </section><!--/form-->

        <div id="addTimeSheet">
            <div id="addTimeSheetOverlay" >
                <div style="background-color: #3bb9ff ; padding: 0px">
                    <table>
                        <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Add&nbsp;Time&nbsp;Sheet&nbsp;&nbsp; </font></h4></td>
                        </tr>
                        <span class=" pull-right"><h5><a href="" class="addTimeSheet_close" onclick="addTimeSheetOverlayClose()"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                    </table>
                </div>
                <div>
                    <form action="#" theme="simple" >
                        <div>
                            <div class="inner-addSkillDiv-elements textfieldLabel">

                                
                                <s:radio id="radiobutton" name="myRadioButton" 
                                         list="#@java.util.LinkedHashMap@{'1' : 'submit timesheet for this week'}" onclick="return checkWeekStatus()" value="1"/>
                                <br> <s:radio id="radiobutton" name="myRadioButton" 
                                         list="#@java.util.LinkedHashMap@{'2':'previous week starting on'}" onclick="return checkWeekStatus()" value="2"/>
                                <br>
                                <s:textfield cssClass=" form-control dateImage" name="weekrange" id="weekrange" />

                                <br> 
                                <span><addTimesheerResult></addTimesheerResult></span>
                                <%--s:select id="projectType" name="projectType" label="Project Type" cssClass="form-control"
                                          list="#@java.util.LinkedHashMap@{'1':'Internal','2':'External'}" onclick="return checkWeekStatus()"/--%>
                                <%--s:submit cssClass="col-sm-offset-4 btn cssbutton" value="Go"  onclick="false" ../timesheets/addTimeSheet.action  ></s:submit--%>
                                <a href="#" ><input type="button" class="cssbutton " value="Go" onclick="return checkPreviousDate()"></a>&nbsp; 
                            </div>
                        </div>
                    </form>
                </div> 
            </div>
        </div>

        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->

        <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>

    </body>
</html>

