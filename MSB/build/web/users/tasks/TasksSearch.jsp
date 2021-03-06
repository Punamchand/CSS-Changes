<%-- 
    Document   : dashboard
    Created on : Feb 3, 2015, 7:50:23 PM
    Author     : Nagireddy
--%>



<%@page import="com.mss.msp.usr.task.TasksVTO"%>

<%@page import="java.util.Iterator"%>
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
        <title>Miracle Service Bay :: Task Search</title>

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
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/taskOverlay.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>

        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <%-- for date picket end--%>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/TaskAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>

        <sx:head />
        <script>
            var pager;
            function onLoad(){
                //alert("onload")
                var paginationSize = parseInt(document.getElementById("paginationOption").value);
                // alert(paginationSize);
                pager = new Pager('teamTaskTable', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            };
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('teamTaskTable', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
        </script>

        <script type="text/javascript">
            function sortables_init() {
                // Find all tables with class sortable and make them sortable
                if (!document.getElementsByTagName) return;
                tbls = document.getElementById("homeRedirectTable");
                sortableTableRows = document.getElementById("homeRedirectTable").rows;
                sortableTableName = "homeRedirectTable";
                for (ti=0;ti<tbls.rows.length;ti++) {
                    thisTbl = tbls[ti];
                    if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
                        ts_makeSortable(thisTbl);
                    }
                }
            };

        </script>
        <script type="text/javascript">
            var myCalendar,myCalendar1;
            //,"docdatepickerfrom1","docdatepicker1"
            function doOnLoad() {
                
                var paginationSize = parseInt(document.getElementById("paginationOption").value);
                // alert(paginationSize);
                pager = new Pager('teamTaskTable', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
                // alert("hii");docdatepickerfrom","docdatepicker
                myCalendar = new dhtmlXCalendarObject(["docdatepickerfrom","docdatepicker"]);
                // alert("hii1");
                myCalendar.setSkin('omega');
                // alert("hii2");
                //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
                myCalendar.setDateFormat("%m-%d-%Y");
                // alert("hii3");
                //alert("myCalendar")
                
                myCalendar1 = new dhtmlXCalendarObject(["startDate","endDate"]);
                // alert("hii1");
                myCalendar1.setSkin('omega');
                // alert("hii2");
                myCalendar1.setDateFormat("%Y/%m/%d");
                
                // default date code start from here
                
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth(); //January is 0!
                var yyyy = today.getFullYear();
                //alert(fromDate)
                if(dd<10) {
                    dd='0'+dd
                } 
                if(mm<10) {
                    mm='0'+mm
                } 
                
                // var from = yyyy+'/'+mm+'/01';
                var from = mm+'-01'+'-'+yyyy;
                mm=today.getMonth()+1;
                if(mm<10) {
                    mm='0'+mm
                } 
                dd=today.getDate()+1;
                var to = mm+'-'+dd+'-'+yyyy;
                var odd=today.getDate()+1;
                var overlayDate=yyyy+'/'+mm+'/'+odd;
                //alert(from+" and "+to)
                document.getElementById("docdatepickerfrom").value=from;
                document.getElementById("docdatepicker").value=to;
                document.getElementById("startDate").value=overlayDate;
                document.getElementById("endDate").value=overlayDate;
            }
                        
            
            
            function enterDateRepository()
            {
                // alert(document.documentForm.docdatepickerfrom.value);
                // document.documentForm.docdatepickerfrom.value="";
                document.getElementById('docdatepickerfrom').value = "";
                document.getElementById('docdatepicker').value = "";
                alert("Please select from the Calender !");
                return false;
            }
            
           
            
            
          
            function checkRange() {
                var fromValue=$('#docdatepickerfrom').val();
                var toValue=$('#docdatepicker').val();
                //alert(fromValue+" and "+toValue)
                if(fromValue==""){
                    alert("from date is madatory")
                    return false;
                }
                if(toValue==""){
                    alert("to date is madatory")
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





    <body style="overflow-x: hidden" onload="doOnLoad(); init(); getTaskType();">
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
                                                <font color="#ffffff">Tasks Search</font>

                                            </h4>
                                        </div>

                                    </div>
                                    <!-- content start -->
                                    <div class="col-sm-12">
                                        <s:form action="showTaskSearchDetails" theme="simple">
                                            <%--br>
                                            <ul class="nav nav-pills">
                                                <s:textfield cssClass=" textbox taskfield-margin" name="docdatepickerfrom" id="docdatepickerfrom" placeholder="FromDate" value="%{docdatepickerfrom}" tabindex="1"  onkeypress="return enterDateRepository();"/>
                                                <s:textfield cssClass=" textbox taskfield-margin" name="docdatepicker" placeholder="ToDate" value="%{docdatepicker}" id="docdatepicker" tabindex="2"  onkeypress="return enterDateRepository();"/>
                                                <s:textfield cssClass="textbox taskfield-margin" id="task_id" name="task_id"  placeholder="Task_Id" />
                                                <s:textfield cssClass="textbox taskfield-margin"  name="task_name" placeholder="Task_Name" />
                                                <s:select  id="status"  name="task_status" label="Status"  cssClass="taskstatus select-margin" headerKey="-1" headerValue="Select status" theme="simple" list="tasksStatusList" />
                                                <div class="taskfieldbtn-margin">
                                                    <s:submit cssClass="cssbutton " id="searchButton" value="search" onclick="checkRange()" />&nbsp;
                                                    <a href="../tasks/addTask.action" ><input type="button" class="cssbutton " value="Addtask"></a> 


                                                </div>
                                            </ul>
                                            <br--%>
                                            <div class="inner-reqdiv-elements">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Task Name: </label>
                                                        <s:textfield cssClass="form-control"  name="task_name" placeholder="Task_Name" />
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color: #56a5ec;">Task Id: </label>
                                                        <s:textfield cssClass="form-control" id="task_id" name="task_id"  placeholder="Task_Id" />
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color: #56a5ec;">Status: </label>
                                                        <s:select  id="status"  name="task_status" label="Status"  cssClass="SelectBoxStyles form-control" headerKey="-1" headerValue="All" theme="simple" list="tasksStatusList" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="inner-reqdiv-elements">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color: #56a5ec;">Start Date: </label>
                                                        <s:textfield cssClass=" form-control dateImage" name="docdatepickerfrom" id="docdatepickerfrom" placeholder="FromDate" value="%{docdatepickerfrom}" tabindex="1"  onkeypress="return enterDateRepository();"/>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color: #56a5ec;">End Date: </label>
                                                        <s:textfield cssClass=" form-control dateImage" name="docdatepicker" placeholder="ToDate" value="%{docdatepicker}" id="docdatepicker" tabindex="2"  onkeypress="return enterDateRepository();"/>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <label class="labelStylereq" style=""></label>
                                                                <s:submit cssClass="cssbutton_emps form-control " id="searchButton" value="search" onclick="checkRange()" cssStyle="margin:5px" />&nbsp;
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <label class="labelStylereq" style=""></label>
                                                                <a href="../tasks/addTask.action" ><input type="button" class="cssbutton_emps form-control " value="Addtask" style="margin:5px"/></a> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </s:form>
                                        <s:form>
                                            <div class="emp_Content" id="emp_div" align="center" style="display: none"    >
                                                <table id="teamTaskTable" class="responsive CSSTable_task " border="5" cell-spacing="2">
                                                    <tbody>
                                                        <tr>
                                                            <th>Title</th>
                                                            <th>Comments</th>
                                                            <th>CreatedBy</th>
                                                            <th>CreatedDate</th>
                                                            <th>Priority</th>
                                                            <th>Status</th>
                                                        </tr>
                                                        <s:if test="teamtaskDetails.size <= 0">
                                                            <tr>
                                                                <td colspan="8"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                            </tr>
                                                        </s:if>
                                                        
                                                        <s:iterator value="teamtaskDetails">
                                                            <s:url var="myUrl" action="../tasks/getTaskDetails.action">
                                                                <s:param name="taskid"><s:property value="task_id"></s:property></s:param>
                                                            </s:url>
                                                            <tr>
                                                                <s:hidden id="taskid" name="taskid" value="task_id"/>
                                                                <td><s:a href='%{#myUrl}'><s:property value="task_name"></s:property></s:a></td>

                                                                <s:if test="task_comments.length()>9">    
                                                                    <td><s:a href="#" onclick="taskCommentsDetailsToViewOnOverlay('%{task_id}');taskCommentsPopup();" cssClass="taskComments_popup_open"><s:property value="%{task_comments.substring(0,10)}"></s:property>..</s:a></td>
                                                                </s:if>
                                                                <s:else>
                                                                    <td><s:property value="task_comments"></s:property></td>
                                                                </s:else>
                                                                <td><s:property value="task_created_by"></s:property></td>
                                                                <td><s:property value="task_created_date"></s:property></td>
                                                                <td><s:property value="taskPriority"></s:property></td>
                                                                <td><s:property value="task_status"></s:property></td>
                                                            </tr>
                                                        </s:iterator>
                                                    </tbody>
                                                </table>
                                                <br/>
                                                <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
                                                        <option>10</option>
                                                        <option>15</option>
                                                        <option>50</option>
                                                    </select>
                                                    Accounts per page
                                                </label>
                                                <div align="right" class="pull-right" id="pageNavPosition" style="margin-right: 0vw;"></div>
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
            <div id="taskComments_popup">
                <div id="preskillBox" class="marginTasks">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Comments Details&nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="taskComments_popup_close" onclick="taskCommentsPopup();" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div>

                        <s:textarea name="taskComments"   id="commentsArea"   disabled="true" cssClass="form-control textareaSkillOverlay"/> 


                    </div>
                    <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                </div>
            </div>

        </section><!--/form-->


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

