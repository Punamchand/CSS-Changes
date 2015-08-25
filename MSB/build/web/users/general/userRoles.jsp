<%-- 
    Document   : userRoles
    Created on : Jul 14, 2015, 12:54:47 AM
    Author     : praveen kumar<pkatru@miraclesoft.com>
--%>


<%@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>

<!DOCTYPE html>
<html>
    <head>

        <!-- new styles -->

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Roles Details Page</title>
        <sx:head cache="true"/>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>"><!--this is for all css in profile view -->

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/dhtmlxcalendar.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <script type="text/JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>" > </script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script> 

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/ProfilePage.js"/>"></script> 
        <!-- this file for writing all profile function and  jquerys -->
        <!-- this is overlay jquery responsive and centered-->

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>


        <script type="text/javascript">
             
            var epager=new EmpPager('edu_pagenav',5);
            epager.init(); 
            epager.showPageNav('epager', 'edu_pageNavPosition'); 
            epager.showPage(1);

          
        </script>   
        <script type="text/javascript">
            var pager = new EmpPager('skilpagenav', 5);
            pager.init();     
            pager.showPageNav('pager', 'pageNavPosition'); 
            pager.showPage(1);
            // alert("skil in jsp")
        </script>

        <style>

        </style>
        <!-- end of new styles -->
    </head>
    <body style="font-family: 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans'" onload="doOnLoad(); doOnLoadDatePicker(); init(); initReport('<%= request.getParameter("userid")%>');">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">

                    <s:include value="/includes/template/header.jsp"/> 
                </div>
            </div><!--/header_top-->

        </header><!--/header-->
        <section id="generalForm"><!--form-->

            <%--<div class="header-middle"><!--header-middle-->
                <div class="container">
                    
                    <div class="row">
                        <s:include value="/includes/menu/generalTopMenu.jsp"/> 
                    </div>
                        
               </div>
            </div> --%>


            <div class="container">
                <div class="row" >

                    <s:include value="/includes/menu/LeftMenu.jsp"/> 


                    <!-- content start -->

                    <div class="col-sm-9 padding-right" style="background-color:#fff">
                        <div class="features_items" ><!--features_items--> 	                                               
                            <!-- maps start -->
                            <!-- maps end s_items--> 	                                               
                            <!-- maps start -->

                            <div class="col-lg-12 ">
                                <div class="" id="profileBox1" >

                                    <div class="backgroundcolor" >
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <font class="titleColor">Roles Information</font>
                                                <%--  <span class="pull-right"><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/general/doEmployeeSearch.action"><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>--%>
                                                </a>

                                                <s:url var="myUrl" action="../../acc/viewAccount.action">
                                                    <s:param name="accountSearchID"><s:property value="empDetails.orgid"/></s:param> 
                                                    <s:param name="accFlag">conSearch</s:param>
                                                </s:url>
                                                <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>

                                                </h4>
                                            </div>

                                        </div>
                                    <%--
                                         <div id="picture" style="float: left;margin-top: 20px" >

                                        <s:url id="image" action="rImage" namespace="/renderImage">
                                            <s:param name="path" value="empDetails.image_path"></s:param>
                                        </s:url>
                                        <img  src="<s:property value="#image"/>"  alt="loin" height="125px" width="120px">
                                    </div>
                                    <s:if test="hasActionMessages()">
                                        <div >
                                            <s:actionmessage cssClass="actionMessagecolor"/>
                                        </div>
                                    </s:if> --%>


                                    <div class="updateCss">

                                        <div class="col-lg-12">
                                            <%
                                                String userid = request.getAttribute("userid").toString();
                                                // System.out.println(request.getAttribute("userid") + "in jsp we printing");
%>

                                            <span ><j></j></span>


                                            <s:hidden value="%{userid}" name="userid"/>

                                            <label for="leftTitle" style="margin-left: 5px">Name&nbsp;:&nbsp;</label> <s:property value="empDetails.first_name"></s:property>.<s:property value="empDetails.last_name"></s:property>
                                            <label for="leftTitle" style="margin-left: 5px">A/C&nbsp;Name&nbsp;:&nbsp;</label><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/viewAccount.action?accountSearchID=<s:property value="empDetails.orgid"/>&accFlag=accDetails"><s:property value="empDetails.account_name"/></a>

                                            <div class="col-lg-12 textfieldLabel" id="reportingBox">
                                                <div class=" panel-body">
                                                    <!-- <div class="panel-collapse collapse margins" id="rolesBox" > -->
                                                    <s:form action="doAddOrUpdateRoles" theme="simple">

                                                        <s:hidden value="%{userid}" name="userid"/>
                                                        <s:hidden value="%{accountSearchID}" name="accountSearchID"/>
                                                        <label for="leftTitle" style="margin-left: 5px">Primary Role</label><label style="margin-left: 20px;color:#2148f0"> : </label>

                                                        <s:select headerKey="0" name="primaryRole" headerValue="Select Roles" list="orgRoles"  theme="simple" cssClass="SelectBoxStyles form-control" value="%{primaryRole}" /> 

                                                        <div class="form-controls">
                                                            <label for="leftTitle" style="margin-left: 5px">
                                                                Assign Roles&nbsp;:

                                                            </label>


                                                        </div>


                                                        <div class="row " style="margin-left: 5px ;width: auto " >

                                                            <div style="margin-left: 0px ;overflow-x: auto">

                                                                <s:optiontransferselect
                                                                    label="User Roles"
                                                                    name="leftSideEmployeeRoles"
                                                                    leftTitle="Avilable Roles"
                                                                    rightTitle="Added Roles"
                                                                    list="notAssignedRoles"
                                                                    headerKey="headerKey"
                                                                    cssStyle="width:120px;height:235px" 							
                                                                    cssClass="form-control"
                                                                    doubleName="addedRolesList"
                                                                    doubleList="assignedRoles"
                                                                    doubleHeaderKey="doubleHeaderKey"
                                                                    doubleValue="%{primaryRole}"
                                                                    doubleCssStyle="width:120px;height:235px"
                                                                    doubleCssClass="form-control"
                                                                    />	

                                                            </div>
                                                            <s:submit cssClass="pull-right btn cssbutton" value="Save"  />                                                                                                    

                                                        </div>
                                                    </s:form>   
                                                </div> 
                                            </div>
                                        </div>
                                    </div>     
                                </div>
                            </div>    



                            <!-- tab ending  -->




                        </div>
                    </div>

                    <!-- hear Code end -->
                </div>
            </div>


            <!-- content end -->

        </section><!--/form-->


        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->






        <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
        <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>
    </body>
</html>

