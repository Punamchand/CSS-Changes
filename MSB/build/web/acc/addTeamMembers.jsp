<%-- 
    Document   : addTeamMembers
    Created on : Jun 9, 2015, 7:37:47 PM
    Author     : praveen<pkatru@miraclesoft.com>
--%>
<%@page import="com.mss.msp.acc.Account"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<!DOCTYPE html>
<html>
    <head>
        <sx:head cache="true"/>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay ::  Add Team Members</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <%--<link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
<link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css"> --%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <%--<script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script> --%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/fileUploadScript.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.form.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
        <script language="JavaScript" src="<s:url value="/includes/js/account/accountDetailsAJAX.js"/>" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <%-- <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script> --%>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/vendorAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/account/projectOverlays.js"/>"></script>


    </head>
    <body style="overflow-x: hidden" onload="getReportingPerson()">
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
                                <s:if test="%{projectFlag!='addMember'}">
                                    <ul class="nav nav-tabs active_details">
                                        <%--s:if test="project.projectType=='Main Project'"--%>
                                        <li class="active" id="editTeamMember">
                                            <a data-toggle="tab" href="#teamMember">Edit Team Member</a>
                                        </li>    
                                        <li class="active_details" id="assignProjecsTab">
                                            <a data-toggle="tab" href="#assignProject">Assign Projects</a>
                                        </li>

                                        <%--/s:if--%>
                                    </ul>


                                </s:if>

                                <s:else>
                                    <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                        <div style="background-color: #3BB9FF ">
                                            <table>
                                                <tr><td style=""><h4><font color="#ffffff">&nbsp;&nbsp;Add Team Member&nbsp;&nbsp; </font></h4></td>
                                                            <s:url var="myUrl" action="../projectDetails.action">
                                                                <s:param name="projectID"><s:property value="projectID"/></s:param>
                                                                <s:param name="accountID"><s:property value="accountID"/></s:param>
                                                            </s:url>
                                                <span class="pull-right"><s:a href='%{myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="30" width="30"></s:a></span>

                                            </table>
                                        </div>
                                    </div>
                                </s:else>
                                <s:if test="%{projectFlag!='addMember'}">
                                    <div class="tab-content" > 
                                        <div class="tab-pane fade in active" id="teamMember">
                                            <div class="" id="profileBox" style="float: left; margin-top: 5px"> 

                                                <div style="background-color: #3BB9FF ">
                                                    <table>
                                                        <tr><td style=""><h4><font color="#ffffff">Edit Team Member&nbsp;&nbsp; </font></h4></td>
                                                                    <s:url var="myUrl" action="../projectDetails.action">
                                                                        <s:param name="projectID"><s:property value="projectID"/></s:param>
                                                                        <s:param name="accountID"><s:property value="accountID"/></s:param>

                                                            </s:url>
                                                        <span class="pull-right"><s:a href='%{myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="30" width="30"></s:a></span>
                                                    </table>
                                                </div>
                                            </s:if>
                                            <div class="" id="profileBox" style="float: left; margin-top: 5px">

                                                <div style="width:fit-content">

                                                    <form action="addTeamMemberToProject" theme="simple" id="overlayForm" >

                                                        <div>
                                                            <% if (request.getAttribute("resultMessage") != null) {
                                                                    out.println("<font style='color:green;'>" + request.getAttribute("resultMessage") + "</font>");
                                                                }%>
                                                            <s:hidden name="projectID" value="%{projectID}" />
                                                            <s:hidden name="userID" value="%{account.userID}" />
                                                            <div>

                                                                <span id="validationMessage" />


                                                            </div>
                                                            <div class="inner-addtaskdiv-elements">
                                                                <s:hidden name="teamMemberId" id="teamMemberId" value="%{account.userID}"/>
                                                                <s:if test="%{projectFlag!='addMember'}">
                                                                    <div class="col-lg-4 ">
                                                                        <label  for="projectName">Project&nbsp;&nbsp;Name&nbsp;:&nbsp;</label><s:property value="account.projectName"/><%--s:textfield  cssClass="form-control" id="projectName"  name="projectName" value="%{account.projectName}" /--%>
                                                                    </div>   
                                                                </s:if>
                                                                   <s:if test="%{projectFlag!='addMember'}">
                                                                    <div class="col-lg-4">
                                                                        <s:hidden value="%{account.resourceType}" />
                                                                        <label  for="teamMemberNamePopup">Resource</label>
                                                                        <s:select  id="resourceType"  name="resourceType" cssClass="form-control SelectBoxStyles "  theme="simple" list="#@java.util.LinkedHashMap@{'E':'Employee','C':'Consultant'}" disabled="true" value="%{account.resourceType}"/>
                                                                    </div>  
                                                                </s:if>
                                                                <s:else>
                                                                    <div class="col-lg-4">
                                                                        <label  for="teamMemberNamePopup">Resource</label>

                                                                        <s:select  id="resourceType"  name="resourceType" cssClass="form-control SelectBoxStyles "  theme="simple" list="#@java.util.LinkedHashMap@{'E':'Employee','C':'Consultant'}"/>
                                                                    </div>
                                                                </s:else>
                                                                <s:if test="%{projectFlag!='addMember'}">

                                                                    <div class="col-lg-4 required">
                                                                        <s:hidden value="%{account.teamMemberIdname}" />
                                                                        <label  for="teamMemberNamePopup">Name</label><s:textfield  cssClass="form-control" id="teamMemberNamePopup"  name="teamMemberIdname" value="%{account.teamMemberIdname}" readonly="true"/>

                                                                    </div>
                                                                </s:if>
                                                                <s:else>

                                                                    <div class="col-lg-4 required">

                                                                        <label  for="teamMemberNamePopup">Name</label><s:textfield  cssClass="form-control" id="teamMemberNamePopup"  name="teamMemberIdname" placeholder="Member Name" onkeyup="return getTeamMemberNames();" autocomplete='off'/>

                                                                    </div>
                                                                </s:else>
                                                                <div class="col-lg-4">
                                                                    <s:hidden value="%{account.teamMemberStatus}" />
                                                                    <label>Status</label><s:select  id="teamMemberStatusPopup"  name="teamMemberStatus" cssClass="form-control SelectBoxStyles "  headerValue="Select status" theme="simple" list="{'Active','Inactive'}" value="%{account.teamMemberStatus}"/>
                                                                </div>
                                                                

                                                            </div>

                                                            <div class="inner-addtaskdiv-elements">
                                                                
                                                                <div class="col-lg-4">
                                                                    <s:hidden value="%{account.reportsto1}" />
                                                                    <label  for="primaryReportingPopup" >Reports To</label><s:select  cssClass="SelectBoxStyles form-control" id="memberPrimaryReporting" name="reportsto1"  headerKey="-1" headerValue="Select Contact"  list="reportsTOMap" value="%{account.reportsto1}"/>
                                                                </div>
                                                                

                                                            </div>
                                                            <s:if test="%{projectFlag!='addMember'}">

                                                                <div class="inner-addtaskdiv-elements">

                                                                    <div class="col-lg-4 required">
                                                                        <s:hidden value="%{account.consSkills}" />
                                                                        <label  for="consultantSkills">Con. Skills</label><s:textfield  cssClass="form-control" id="consSkills"  name="consSkills" placeholder="Consultant Skills" onkeyup="" readonly="true"/>

                                                                    </div>
                                                                    <div class="col-lg-4 required">
                                                                        <s:hidden value="%{account.rateSalary}" />
                                                                        <label  for="ratePerHr">Rate Salary</label><s:textfield  cssClass="form-control" id="rateSalary"  name="rateSalary" placeholder="Rate Per Hr" onkeyup="" readonly="true"/>

                                                                    </div>
                                                                </div>

                                                            </s:if>    

                                                            <div  class="col-lg-4">
                                                                <br>
                                                                <s:reset cssClass="cssbutton " value="Clear" theme="simple" onclick="resetOverlayForm();"/>
                                                                <s:if test="%{projectFlag!='addMember'}">
                                                                    <s:submit cssClass="cssbutton" value="Update" theme="simple" onclick="return projectTeamMemberValidation();"/>
                                                                </s:if>
                                                                <s:else>
                                                                    <s:submit cssClass="cssbutton" value="Save" theme="simple" onclick="return projectTeamMemberValidation();"/>
                                                                </s:else>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <s:if test="%{projectFlag!='addMember'}">          
                                        <!-- assign projects -->
                                        <div class="tab-pane fade in " id="assignProject"> 
                                            <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                                <s:form action="updateTeamMembersForProject" theme="simple" >
                                                    <div style="background-color: #3BB9FF ">
                                                        <table>
                                                            <tr><td style=""><h4><font color="#ffffff">&nbsp;&nbsp;Assign Project&nbsp;&nbsp; </font></h4></td>
                                                                        <s:url var="myUrl" action="../projectDetails.action">
                                                                            <s:param name="projectID"><s:property value="projectID"/></s:param>
                                                                            <s:param name="accountID"><s:property value="accountID"/></s:param>
                                                                            

                                                                </s:url>
                                                            <span class="pull-right"><s:a href='%{myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="30" width="30"></s:a></span>
                                                        </table>
                                                    </div>
                                                    <s:hidden id="projectID" name="projectID" value="%{projectID}" />
                                                    <s:hidden id="userID" name="userID" value="%{userID}" />
                                                     <s:hidden id="teamMemberFlag" name="teamMemberFlag" value="%{teamMemberFlag}" />

                                                    <div class="col-lg-4 ">
                                                        <label  for="projectName">Project&nbsp;&nbsp;Name&nbsp;:&nbsp;</label><s:property value="account.projectName"/><%--s:textfield  cssClass="form-control" id="projectName"  name="projectName" value="%{account.projectName}" /--%>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label  for="projectEmpName">Employee&nbsp;&nbsp;Name&nbsp;:&nbsp;</label><s:property value="account.teamMemberIdname"/><%--s:textfield  cssClass="form-control" id="projectEmpName"  name="projectEmpName" value="%{account.projectEmpName}" /--%>
                                                    </div>

                                                    <br/> <br/><br/>  
                                                      <% if (request.getAttribute("validateMessage") != null) {
                                                                    out.println("<font style='color:green;'>" + request.getAttribute("validateMessage") + "</font>");
                                                                }%>
                                                    <div class=" ">
                                                        <div class="col-lg-6 " style="margin-left: 5px ;width: auto " >
                                                            <div style="margin-left: 0px ;overflow-x: auto">
                                                                <s:optiontransferselect
                                                                    name="subProjectOption"
                                                                    leftTitle="Avilable Projects"
                                                                    rightTitle="Assigned Projects"
                                                                    list="%{subProject}"
                                                                    headerKey="headerKey"
                                                                    cssStyle="width:150px;height:235px" 							
                                                                    cssClass="form-control"
                                                                    doubleName="assignProjectForTeam"
                                                                    doubleList="%{assignedSubProject}"
                                                                    doubleHeaderKey="doubleHeaderKey"
                                                                    buttonCssStyle="width:50px;height:25px;"
                                                                    doubleCssStyle="width:150px;height:235px"
                                                                    doubleCssClass="form-control"
                                                                    />	
                                                            </div>
                                                            <s:submit cssClass="cssbutton" value="Update" />
                                                        </div> 

                                                    </div>
                                                </s:form>
                                            </div>
                                        </div>
                                    </s:if>            
                                    <!-- assign end projects -->

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <!-- content end -->

        </div>
    </div>



</section><!--/form-->
<script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
<script>
   var projectFlag=document.getElementById("teamMemberFlag").value;
   //alert(projectFlag) 
   if(projectFlag=="assignTeam")
       {
       //alert("--->assignTeam")    
      
      document.getElementById('assignProjecsTab').className='active active_details'; 
      document.getElementById('teamMember').className='tab-pane fade in';
      document.getElementById('assignProject').className='tab-pane fade in active';
      document.getElementById('editTeamMember').className='tab-pane fade in';
       //alert("--->assignTeam<----") 
       }
</script>
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

