<%-- 
Document   : requirementedit
Created on : May 5, 2015, 1:55:08 AM
Author     : miracle
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<%@page import="com.mss.msp.requirement.RequirementVTO"%>


<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Requirements Edit Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/taskiframe.css"/>">


        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>

        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/addLeaveOverlay.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/requirementAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/vendorAjax.js"/>"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script>
        <script type="text/javascript">
            ; function sortables_init() {
                // Find all tables with class sortable and make them sortable
                ; if (!document.getElementsByTagName) return;
                tbls = document.getElementById("vendorAssociationResults");
                sortableTableRows = document.getElementById("vendorAssociationResults").rows;
                sortableTableName = "vendorAssociationResults";
                for (ti=0;ti<tbls.rows.length;ti++) {
                    thisTbl = tbls[ti];
                    if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
                        ts_makeSortable(thisTbl);
                    }
                }
            };
        </script>

    </head>

    <body style="overflow-x: hidden" onload="doOnloadEditRequirement(); ">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/> 
                </div>
            </div>
        </header>    
        <%
            String flag = "reqSearch";
            String orgId = session.getAttribute(ApplicationConstants.ORG_ID).toString();
        %>
        <section id="generalForm"><!--form-->
            <div class="container">
                <div class="row">
                    <s:include value="/includes/menu/LeftMenu.jsp"/> 
                    <!-- content start -->
                    <div class="col-md-10 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items">
                            <div class="col-lg-14 ">


                                <%-- TOP TABS BEGIN--%>
                                <div class=" panel-info">
                                    <div class="panel-body" id="panel-task-body" >
                                        <s:if test="accountFlag=='Account'">
                                            <div class=""  style="float: left; margin-top:-12px; margin-bottom: 20px">
                                                <label class="labelStyle"> Account&nbsp;Name: </label>                                         
                                                <s:url var="myUrl" action="acc/viewAccount.action">
                                                    &nbsp;&nbsp;<s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                    <s:param name="accFlag">accDetails</s:param>
                                                </s:url>
                                                <s:a href='%{#myUrl}' style="color: #0000FF;"><s:property value="%{account_name}"/></s:a>
                                                </div>
                                        </s:if> 
                                        <div class="" style="float: left; margin-top:-5px; margin-bottom: -2px">
                                            <s:url var="reqUrl" action="acc/viewAccount.action">
                                                <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                <s:param name="accFlag">reqSearch</s:param>
                                            </s:url>
                                            <s:url var="myUrl" action="acc/viewAccount.action">
                                                <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                <s:param name="accFlag">accDetails</s:param>
                                            </s:url>
                                            <s:url var="custReqUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                <s:param name="orgid"><%=orgId%></s:param>
                                                <s:param name="customerFlag">customer</s:param>
                                                <s:param name="accountFlag">MyRequirements</s:param>
                                            </s:url>
                                            <s:url var="venReqUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                <s:param name="accountFlag">MyRequirements</s:param>
                                                <s:param name="vendor">yes</s:param>
                                                <s:param name="orgid"><%=orgId%></s:param>
                                            </s:url>


                                            <label class="">Path: </label> 
                                            <s:if test="accountFlag=='csr'" >
                                                <s:a href='%{#myUrl}' style="color:#FC9A11;"><s:property value="%{accountName}"/></s:a>
                                            </s:if>
                                            <s:else>
                                                <s:a href='#' style="color:#FC9A11;"><s:property value="%{accountName}"/></s:a>
                                            </s:else>
                                            <s:if test="accountFlag=='csr'" >
                                                <s:a href='%{#reqUrl}' style="color:#FC9A11;">->Requirements List</s:a>
                                            </s:if>
                                            <s:else>
                                                <s:if test="vendor!='yes'">
                                                    <s:a href='%{#custReqUrl}' style="color:#FC9A11;">->Requirements List</s:a>        
                                                </s:if>
                                                <s:else>
                                                    <s:a href='%{#venReqUrl}' style="color:#FC9A11;">->Requirements List</s:a>        
                                                </s:else>
                                            </s:else>

                                            <span style="color:#FC9A11;">->
                                                <s:property value="%{jdId}"/>
                                            </span>


                                        </div>
                                        <div class="" style="float: right; margin-top:-5px; margin-bottom: -2px">
                                            <label class="">Id: </label>                                         
                                            <span style="color: green"><s:property value="%{jdId}"/></span>
                                        </div>
                                        <br/>
                                        <!-- Nav tabs -->
                                        <ul class="nav nav-tabs">
                                            <s:if test="vendor!='yes'">
                                                <li class=" active_details" id="detailsLi" ><a aria-expanded="false" href="#details" data-toggle="tab">Requirement Edit</a>
                                                </li>
                                            </s:if>
                                            <s:if test="vendor=='yes'">
                                                <li class=" active_details" id="detailsLi" ><a aria-expanded="false" href="#details" data-toggle="tab">Requirement In Detail</a>
                                                </li>
                                            </s:if>
                                            <s:if test="vendor!='yes'">
                                                <li class="active_details" id="vendorAssociationLi"><a aria-expanded="false" href="#vendorAssociation" data-toggle="tab" onclick="return getVendorAssociationDetails()"   >Vendors</a>
                                                </li>
                                            </s:if>
                                            <li class="active_details"  id="consultantListLi"><a aria-expanded="false" href="#consultantList" data-toggle="tab" onclick="return getConsultantList()"   >Submitted List</a></li>
                                            <%--
                                                                                        <s:if test="accountFlag=='Account'">
                                                                                            <li class="active_details"><a aria-expanded="false" href="#vendorAssociation" data-toggle="tab" onclick="return getVendorAssociationDetails()"   >Vendor Association</a>
                                                                                            </li>
                                                                                            <li class="active_details"><a aria-expanded="false" href="#consultantList" data-toggle="tab" onclick="return getConsultantList()"   >Consultant List</a>
                                                                                            </li>
                                                                                        </s:if>
                                                                                        <s:elseif test="accountFlag=='MyRequirements'">

                                                <li class="active_details"><a aria-expanded="false" href="#consultantList" data-toggle="tab" onclick="return getConsultantList()"   >Consultant List</a>
                                                </li>
                                            </s:elseif>
                                            <s:elseif test="accountFlag=='OnlyMyRequirements'">
                                                <li class="active_details"><a aria-expanded="false" href="#consultantList" data-toggle="tab" onclick="return getConsultantList()"   >Consultant List</a>
                                                </li>
                                            </s:elseif>
                                            <s:elseif test="accountFlag=='VendorReq'">
                                                <li class="active_details"><a aria-expanded="false" href="#consultantList" data-toggle="tab" onclick="return getConsultantList()"   >Consultant List</a>
                                                </li>
                                            </s:elseif>
                                            <s:else>
                                                <li class="active_details"><a aria-expanded="false" href="#consultantList" data-toggle="tab" onclick="return getConsultantList()"   >Consultant List</a>
                                                </li>
                                            </s:else>

                                            --%>

                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade in active" id="details">
                                                <div class="" id="profileBox" style="float: left; margin-top: 5px">

                                                    <div class="backgroundcolor" >
                                                        <div class="panel-heading">
                                                            <h4 class="panel-title">
                                                                <s:if test="vendor!='yes'">
                                                                    <font color="#ffffff"> Edit Requirement </font>
                                                                </s:if>
                                                                <s:if test="vendor=='yes'">
                                                                    <font color="#ffffff">Requirement Details</font>
                                                                </s:if>

                                                                <s:if test="accountFlag=='Account'">
                                                                    <s:url var="myUrl" action="acc/viewAccount.action">
                                                                        <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> 
                                                                        <s:param name="accFlag"><%=flag%></s:param>
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                                                    </s:if>
                                                                    <s:elseif test="accountFlag=='MyRequirements' && customerFlag=='customer'">
                                                                        <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                        --%>
                                                                        <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                            <s:param name="accountFlag">MyRequirements</s:param>
                                                                        <s:param name="orgid"><%=orgId%></s:param>
                                                                        <s:param name="customerFlag">customer</s:param>
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>
                                                                    <s:elseif test="accountFlag=='MyRequirements'">
                                                                        <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                        --%>
                                                                        <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                            <s:param name="accountFlag">MyRequirements</s:param>
                                                                        <s:param name="vendor">yes</s:param>
                                                                        <s:param name="orgid"><%=orgId%></s:param>
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>
                                                                    <s:elseif test="accountFlag=='OnlyMyRequirements'">
                                                                        <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                        --%>
                                                                        <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                            <s:param name="accountFlag">OnlyMyRequirements</s:param>
                                                                        <s:param name="vendor">yes</s:param>
                                                                        <s:param name="orgid"><%=orgId%></s:param>

                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>
                                                                    <s:elseif test="accountFlag=='csr'">

                                                                    <s:url var="myUrl" action="acc/viewAccount.action">                                                        
                                                                        <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                                        <s:param name="accFlag"><%=flag%></s:param>

                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>

                                                                <s:else>
                                                                    <s:url var="myUrl" action="recruitment/consultant/getAllRequirementList.action">
                                                                        <s:param name="orgid"><%=orgId%></s:param>    
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:else>
                                                            </h4>
                                                        </div>

                                                    </div>
                                                    <div class="col-lg-14">
                                                        <div class="panel-body">
                                                            <s:form action="#" method="post" theme="simple" >
                                                                <s:hidden name="RequirementId" id="RequirementId" value="%{requirementVTO.RequirementId}"/>
                                                                <div class="col-md-12"> 
                                                                    <span><editrequirementerror></editrequirementerror></span>
                                                                </div>

                                                                <span cellspacing="30">

                                                                    <div class="col-lg-3 required">

                                                                        <table class="CSSTable_task">
                                                                            <s:if test="#session.typeOfUsr=='VC'">
                                                                                <%--<label class="labelStyle" id="labelLevelStatusReq"> Approver :</label>
                                                                                <s:select name="RequirementContact1" id="RequirementContact1" value="%{requirementVTO.RequirementContact1}" list="EmployeeNames" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()" disabled="true"/>--%>
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Title </label> <s:textfield cssClass="form-control " id="RequirementName" type="text" value="%{requirementVTO.RequirementName}" name="RequirementName" placeholder="" onfocus="removeErrorMessages()" disabled="true"/>

                                                                                <label class="labelStyle" id="labelLevelStatusReq">  Duration</label> <s:textfield cssClass="form-control" id="RequirementDuration" type="text" value="%{requirementVTO.RequirementDuration}" name="RequirementDuration" placeholder="" onfocus="removeErrorMessages()" disabled="true"/>
                                                                                <label class="labelStyle" id="labelLevelStatusReq">Billing Contact </label> <s:select  id="billingContact" name="buildingContact" value="%{requirementVTO.billingContact}" list="EmployeeNames" headerKey="DF" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()" disabled="true"/></td>
                                                                            </s:if> 
                                                                            <s:else> 
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Title </label> <s:textfield cssClass="form-control " id="RequirementName" type="text" value="%{requirementVTO.RequirementName}" name="RequirementName" placeholder="" onfocus="removeErrorMessages()"/>

                                                                                <label class="labelStyle" id="labelLevelStatusReq">  Duration  </label> <s:textfield cssClass="form-control" id="RequirementDuration" type="text" value="%{requirementVTO.RequirementDuration}" name="RequirementDuration" placeholder="" onfocus="removeErrorMessages()"/>
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Approver </label> 
                                                                                <s:select name="RequirementContact1" id="RequirementContact1" value="%{requirementVTO.RequirementContact1}" list="EmployeeNames" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()"/>
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Min Rate </label>
                                                                                <div class="form-group input-group">
                                                                                    <span class="input-group-addon "style="padding-top: 5px" >$</span>

                                                                                    <s:textfield cssClass="form-control " id="RequirementTargetRate" type="text" value="%{requirementVTO.RequirementTargetRate}" name="RequirementTargetRate" placeholder="" onfocus="removeErrorMessages()" onkeypress="return RequirementMinRate(event)"/>
                                                                                    <span class="input-group-addon" style="padding-top: 5px">/Hr</span>
                                                                                </div>

                                                                            </s:else>
                                                                        </table>
                                                                    </div>
                                                                    <div class="col-lg-3 required" >

                                                                        <table class="CSSTable_task">
                                                                            <s:if test="#session.typeOfUsr=='VC'">
                                                                                <%--<label class="labelStyle" id="labelLevelStatusReq"> Requisitioner :</label>
                                                                                <s:select name="RequirementContact2" id="RequirementContact2" value="%{requirementVTO.RequirementContact2}" list="EmployeeNames" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()" disabled="true"/>--%>
                                                                                <label class="labelStyle" id="labelLevelStatusReq">Start Date</label> <s:textfield cssClass="form-control " name="RequirementFrom" id="RequirementFrom" placeholder="StartDate" value="%{requirementVTO.RequirementFrom}" onkeypress="return editRequirementDateValidation()" cssStyle="z-index: 10000004;" onfocus="removeErrorMessages()" disabled="true"/>
                                                                                <label class="labelStyle" id="labelLevelStatusReq">Hr/week/month:</label> <s:select  id="requirementDurationDescriptor" value="%{requirementVTO.requirementDurationDescriptor}" list="#@java.util.LinkedHashMap@{'Hours':'Hours','Weeks':'Weeks','Months':'Months'}"   headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" disabled="true"/> 
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Min Rate </label>
                                                                                <div class="form-group input-group">
                                                                                    <span class="input-group-addon "style="padding-top: 5px" >$</span>

                                                                                    <s:textfield cssClass="form-control " id="RequirementTargetRate" type="text" value="%{requirementVTO.RequirementTargetRate}" name="RequirementTargetRate" placeholder="" onfocus="removeErrorMessages()" onkeypress="return RequirementMinRate(event)" disabled="true"/>
                                                                                    <span class="input-group-addon" style="padding-top: 5px">/Hr</span>
                                                                                </div>
                                                                            </s:if> 
                                                                            <s:else> 
                                                                                <label class="labelStyle" id="labelLevelStatusReq">Start Date</label> <s:textfield cssClass="form-control " name="RequirementFrom" id="RequirementFrom" placeholder="StartDate" value="%{requirementVTO.RequirementFrom}" onkeypress="return editRequirementDateValidation()" cssStyle="z-index: 10000004;" onfocus="removeErrorMessages()" />
                                                                                <label class="labelStyle" id="labelLevelStatusReq">Hr/week/month:</label> <s:select  id="requirementDurationDescriptor" value="%{requirementVTO.requirementDurationDescriptor}" list="#@java.util.LinkedHashMap@{'Hours':'Hours','Weeks':'Weeks','Months':'Months'}" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" /> 


                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Requisitioner </label> 
                                                                                <s:select name="RequirementContact2" id="RequirementContact2" value="%{requirementVTO.RequirementContact2}" list="EmployeeNames" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()" />
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Max Rate </label>    
                                                                                <div class="form-group input-group">
                                                                                    <span class="input-group-addon "style="padding-top: 5px" >$</span>

                                                                                    <s:textfield cssClass="form-control " id="requirementMaxRate" type="text" value="%{requirementVTO.requirementMaxRate}" name="RequirementTargetRate" placeholder="" onfocus="removeErrorMessages()" onkeypress="return RequirementMaxRate(event)"/>
                                                                                    <span class="input-group-addon" style="padding-top: 5px">/Hr</span>
                                                                                </div>


                                                                            </s:else>

                                                                        </table>
                                                                    </div>

                                                                    <div class="col-lg-3 required">     
                                                                        <table class="CSSTable_task">
                                                                            <s:if test="#session.typeOfUsr=='VC'">
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Type </label> <s:select name="RequirementTaxTerm" id="RequirementTaxTerm" value="%{requirementVTO.RequirementTaxTerm}" list="#@java.util.LinkedHashMap@{'CO':'Contract','CH':'Contract to hire','PE':'Permanent'}" headerKey="DF" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages() " disabled="true"/>
                                                                                <%--<s:if test="#session.typeOfUsr!='VC'">
                                                                                    <label class="labelStyle" id="labelLevelStatusReq"> Presales 1 :</label> <s:select name="RequirementPresales1" id="RequirementPresales1" value="%{requirementVTO.RequirementPresales1}" list="Contacts" headerKey="-1" headerValue="--select--" cssClass="selectBoxStyle form-control" onfocus="removeErrorMessages()"/>
                                                                                </s:if>--%>


                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Req.Exp. </label><s:select  id="RequirementYears" list="experienceMap"  value="%{requirementVTO.RequirementExp}" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()" disabled="true"/>
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Max Rate </label>    
                                                                                <div class="form-group input-group">
                                                                                    <span class="input-group-addon "style="padding-top: 5px" >$</span>

                                                                                    <s:textfield cssClass="form-control " id="requirementMaxRate" type="text" value="%{requirementVTO.requirementMaxRate}" name="RequirementTargetRate" placeholder="" onfocus="removeErrorMessages()" onkeypress="return RequirementMaxRate(event)" disabled="true"/>
                                                                                    <span class="input-group-addon" style="padding-top: 5px">/Hr</span>
                                                                                </div>                                                                               

                                                                            </s:if>
                                                                            <s:else>
                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Type </label> <s:select name="RequirementTaxTerm" id="RequirementTaxTerm" value="%{requirementVTO.RequirementTaxTerm}" list="#@java.util.LinkedHashMap@{'CO':'Contract','CH':'Contract to hire','PE':'Permanent'}" headerKey="DF" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()"/>
                                                                                <%--<s:if test="#session.typeOfUsr!='VC'">
                                                                                    <label class="labelStyle" id="labelLevelStatusReq"> Presales 1 :</label> <s:select name="RequirementPresales1" id="RequirementPresales1" value="%{requirementVTO.RequirementPresales1}" list="Contacts" headerKey="-1" headerValue="--select--" cssClass="selectBoxStyle form-control" onfocus="removeErrorMessages()"/>
                                                                                </s:if>--%>


                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Req.Exp. </label><s:select  id="RequirementYears" list="experienceMap"  value="%{requirementVTO.RequirementExp}" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()"/>

                                                                                <label class="labelStyle" id="labelLevelStatusReq"> Status </label><s:select name="RequirementStatus" id="RequirementStatus" value="%{requirementVTO.RequirementStatus}" list="#@java.util.LinkedHashMap@{'C':'Created','O':'Open','F':'Forecast','I':'Inprogess','H':'Hold','W':'Withdrawn','S':'Won','L':'Lost'}" headerKey="DF"  cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()"/>
                                                                                <label class="labelStylereq " style="color:#56a5ec;">Req.Category</label>
                                                                                <s:select id="reqCategoryValue" name="reqCategoryValue" cssClass="SelectBoxStyles form-control"  theme="simple" list="%{reqCategory}" value="%{requirementVTO.reqCatgory}" disabled="true"/>

                                                                            </s:else>
                                                                        </table>
                                                                    </div>
                                                                    <div class="col-lg-3 required" >     

                                                                        <s:if test="#session.typeOfUsr=='VC'">
                                                                            <label class="labelStyle" id="labelLevelStatusReq"> Positions </label> <s:textfield cssClass="form-control" id="RequirementNoofResources" type="text" value="%{requirementVTO.RequirementNoofResources}" name="RequirementNoofResources" placeholder="" onfocus="removeErrorMessages()" disabled="true"/>

                                                                            <label class="labelStyle" id="labelLevelStatusReq"> Location </label> <s:select name="RequirementLocation" id="RequirementLocation" value="%{requirementVTO.RequirementLocation}" list="#@java.util.LinkedHashMap@{'ON':'Onsite','OF':'Offsite','OS':'Offshore'}" headerKey="OF" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()" disabled="true"/>
                                                                            <label class="labelStylereq " style="color:#56a5ec;">Req.Category</label>
                                                                            <s:select id="reqCategoryValue" name="reqCategoryValue" cssClass="SelectBoxStyles form-control"  theme="simple" list="%{reqCategory}" value="%{requirementVTO.reqCatgory}" disabled="true" />

                                                                        </s:if>
                                                                        <s:else>

                                                                            <label class="labelStyle" id="labelLevelStatusReq"> Positions </label> <s:textfield cssClass="form-control" id="RequirementNoofResources" type="text" value="%{requirementVTO.RequirementNoofResources}" name="RequirementNoofResources" placeholder="" onfocus="removeErrorMessages()"/>
                                                                            <label class="labelStyle" id="labelLevelStatusReq"> Location </label> <s:select name="RequirementLocation" id="RequirementLocation" value="%{requirementVTO.RequirementLocation}" list="#@java.util.LinkedHashMap@{'ON':'Onsite','OF':'Offsite','OS':'Offshore'}" headerKey="OF" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()"/>
                                                                            <label class="labelStyle" id="labelLevelStatusReq">Billing Contact </label> <s:select  id="billingContact" name="buildingContact" value="%{requirementVTO.billingContact}" list="EmployeeNames" headerKey="DF" headerValue="--select--" cssClass="SelectBoxStyles form-control" onfocus="removeErrorMessages()"/></td>

                                                                        </s:else>       
                                                                    </div>

                                                            </div>
                                                            <s:if test="#session.typeOfUsr=='VC'">
                                                                <div class="form-group req-textarea required">
                                                                    <label class="labelStyle" id="labelLevelStatusReq">Responsibilities </label> <s:textarea name="RequirementResponse" id="RequirementResponse" cssClass="titleStyle" value="%{requirementVTO.RequirementResponse}" placeholder="Enter Responsibilities Here" rows="3" onkeyup=" ResponseCheckCharacters(this)" onfocus="removeErrorMessages()"  disabled="true"  />
                                                                </div>
                                                                <div class="charNum" id="ResponsecharNum"></div>
                                                                <div class="form-group req-textarea required">
                                                                    <label class="labelStyle" id="labelLevelStatusReq">Description </label> <s:textarea name="RequirementJobdesc" id="RequirementJobdesc" cssClass="titleStyle" value="%{requirementVTO.RequirementJobdesc}" placeholder="Enter Job Description Here" rows="3" onkeyup=" JobCheckCharacters(this)" onfocus="removeErrorMessages()"   disabled="true"  />
                                                                </div>
                                                                <div class="charNum" id="JobcharNum"></div>
                                                                <div class="form-group req-textarea1">
                                                                    <div class="col-lg-6 required">
                                                                        <label class="labelStyle" id="labelLevelStatusReq">Required Skill Set </label> <s:textarea name="RequirementSkills" id="RequirementSkills" cssClass="titleStyle" value="%{requirementVTO.RequirementSkills}" placeholder="Enter Skills Here" rows="3" onkeyup=" SkillCheckCharacters(this)" onfocus="removeErrorMessages()" disabled="true" />

                                                                    </div>
                                                                    <div class="col-lg-6">
                                                                        <label class="labelStyle" id="labelLevelStatusReq">Preferred Skill Set </label> <s:textarea name="RequirementPreferredSkills" id="RequirementPreferredSkills" cssClass="titleStyle" value="%{requirementVTO.RequirementPreferredSkills}" placeholder="Enter Preferred Skills Here" rows="3" onkeyup=" PreferredSkillCheckCharacters(this)" onfocus="removeErrorMessages()" disabled="true" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-12 ">  
                                                                    <div class="col-lg-6">
                                                                        <div class="charNum" id="SkillcharNum"></div></div>
                                                                    <div class="col-lg-6">
                                                                        <div class="charNum" id="PreferredSkillcharNum"></div>
                                                                    </div></div>

                                                                <div id="charNum"></div>
                                                                <div class="form-group req-textarea">
                                                                    <label class="labelStyle" id="labelLevelStatusReq">Comments </label> <s:textarea name="RequirementComments" id="RequirementComments" cssClass="titleStyle" value="%{requirementVTO.RequirementComments}" placeholder="Enter Comments Here" rows="3" onkeyup=" CommentsCheckCharacters(this)" onfocus="removeErrorMessages()" disabled="true" />
                                                                </div>
                                                                <div class="charNum" id="CommcharNum"></div>
                                                            </s:if>
                                                            <s:else>
                                                                <div class="form-group req-textarea required">
                                                                    <label class="labelStyle" id="labelLevelStatusReq">Responsibilities </label> <s:textarea name="RequirementResponse" id="RequirementResponse" cssClass="titleStyle" value="%{requirementVTO.RequirementResponse}" placeholder="Enter Responsibilities Here" rows="3" onkeyup=" ResponseCheckCharacters(this)" onfocus="removeErrorMessages()" />
                                                                </div>
                                                                <div class="charNum" id="ResponsecharNum"></div>
                                                                <div class="form-group req-textarea required">
                                                                    <label class="labelStyle" id="labelLevelStatusReq">Description </label> <s:textarea name="RequirementJobdesc" id="RequirementJobdesc" cssClass="titleStyle" value="%{requirementVTO.RequirementJobdesc}" placeholder="Enter Job Description Here" rows="3" onkeyup=" JobCheckCharacters(this)" onfocus="removeErrorMessages()" />
                                                                </div>
                                                                <div class="charNum" id="JobcharNum"></div>
                                                                <div class="form-group req-textarea1 ">
                                                                    <div class="col-lg-6 required">
                                                                        <label class="labelStyle" id="labelLevelStatusReq">Required Skill Set </label> <s:textarea name="RequirementSkills" id="RequirementSkills" cssClass="titleStyle" value="%{requirementVTO.RequirementSkills}" placeholder="Enter Skills Here" rows="3" onkeyup=" SkillCheckCharacters(this)" onfocus="removeErrorMessages()" />
                                                                    </div>
                                                                    <div class="col-lg-6">
                                                                        <label class="labelStyle" id="labelLevelStatusReq">Preferred Skill Set </label> <s:textarea name="RequirementPreferredSkills" id="RequirementPreferredSkills" cssClass="titleStyle" value="%{requirementVTO.RequirementPreferredSkills}" placeholder="Enter Preferred Skills Here" rows="3" onkeyup=" PreferredSkillCheckCharacters(this)" onfocus="removeErrorMessages()" />

                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-12 ">  
                                                                    <div class="col-lg-6">
                                                                        <div class="charNum" id="SkillcharNum"></div></div>
                                                                    <div class="col-lg-6">
                                                                        <div class="charNum" id="PreferredSkillcharNum"></div>
                                                                    </div></div>
                                                                <div id="charNum"></div>
                                                                <div class="form-group req-textarea">
                                                                    <label class="labelStyle" id="labelLevelStatusReq">Comments </label> <s:textarea name="RequirementComments" id="RequirementComments" cssClass="titleStyle" value="%{requirementVTO.RequirementComments}" placeholder="Enter Comments Here" rows="3" onkeyup=" CommentsCheckCharacters(this)" onfocus="removeErrorMessages()"/>
                                                                </div>
                                                                <div class="charNum" id="CommcharNum"></div>
                                                            </s:else>
                                                        </s:form>       
                                                    </div>     

                                                    <s:if test="accountFlag !='csr'">  
                                                        <s:if test="vendor!='yes'">
                                                            <s:submit cssClass=" col-sm-offset-10 btn cssbutton_update" id="update" onclick="return updaterequirements();" value="Update" theme="simple"/>
                                                        </s:if>
                                                    </s:if>
                                                </div> 

                                            </div>
                                            <div class="tab-pane fade in " id="vendorAssociation">

                                                <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                                    <div>
                                                        <div class="backgroundcolor" >
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">

                                                                    <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                                    <font color="#ffffff">Vendor Search</font>
                                                                    <s:if test="accountFlag=='Account'">
                                                                        <s:url var="myUrl" action="acc/viewAccount.action">
                                                                            <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> 
                                                                            <s:param name="accFlag"><%=flag%></s:param>
                                                                        </s:url>
                                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                                                        </s:if>
                                                                        <s:elseif test="accountFlag=='MyRequirements' && customerFlag=='customer'">
                                                                            <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                            --%>
                                                                            <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                                <s:param name="accountFlag">MyRequirements</s:param>
                                                                            <s:param name="orgid"><%=orgId%></s:param>
                                                                            <s:param name="customerFlag">customer</s:param>
                                                                        </s:url>
                                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                        </s:elseif>
                                                                        <s:elseif test="accountFlag=='MyRequirements'">
                                                                            <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                            --%>
                                                                            <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                                <s:param name="accountFlag">MyRequirements</s:param>
                                                                            <s:param name="vendor">yes</s:param>
                                                                            <s:param name="orgid"><%=orgId%></s:param>
                                                                        </s:url>
                                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                        </s:elseif>
                                                                        <s:elseif test="accountFlag=='OnlyMyRequirements'">
                                                                            <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                            --%>
                                                                            <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                                <s:param name="accountFlag">OnlyMyRequirements</s:param>
                                                                            <s:param name="vendor">yes</s:param>
                                                                            <s:param name="orgid"><%=orgId%></s:param>
                                                                        </s:url>
                                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                        </s:elseif>

                                                                    <s:elseif test="accountFlag=='csr'">

                                                                        <s:url var="myUrl" action="acc/viewAccount.action">                                                        
                                                                            <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                                            <s:param name="accFlag"><%=flag%></s:param>

                                                                        </s:url>
                                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                        </s:elseif>
                                                                        <s:else>
                                                                            <s:url var="myUrl" action="recruitment/consultant/getAllRequirementList.action">
                                                                                <s:param name="orgid"><%=orgId%></s:param> 
                                                                            </s:url>
                                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                        </s:else>
                                                                </h4>
                                                            </div>
                                                        </div>
                                                        <s:hidden id="req_id" value="%{RequirementId}"/>
                                                        <div class="pull-right" style="float: left; margin-top:0px;margin-bottom: -7px;">
                                                            <label class=""> Job Title: </label>                                         
                                                            <span style="color: green"><s:property value="%{requirementVTO.RequirementName}"/></span>
                                                        </div>
                                                        <div class="col-sm-12">
                                                            <div class="col-lg-4">   
                                                                <label class="labelStylereq" style="color:#56a5ec;"> Type Of Tier :</label><s:select name="tireType" id="tireTypeSearch" value="" list="%{typesTiers}" headerKey="-1" headerValue="--select--" cssClass="SelectBoxStyles form-control" onchange="return getVendorsNames()"/>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="labelStylereq" style="color:#56a5ec;"> Status :</label><s:select name="status" id="status" value="" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}" headerKey="DF" headerValue="--select--" cssClass="SelectBoxStyles form-control"/>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <div class="row">
                                                                    <div class="col-lg-6">
                                                                        <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                                        <input type="button" class="cssbutton form-control" value="Search" onclick="return searchVendorAssociationDetails()"/>
                                                                    </div>  
                                                                    <div class="col-lg-6"> 
                                                                        <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                                        <input class="cssbutton form-control vendorAsso_popup_open" value="Add" type="button" onclick="return associationOverlay()"/>
                                                                    </div> 
                                                                </div>   
                                                            </div>                                                       
                                                        </div>
                                                        <div id="vendorAsso_popup">

                                                            <div id="vendorAssocitaionOverlay">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Add Association&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 ><a href="">&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="vendorAsso_popup_close" onclick="associationOverlay();"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>
                                                                </div><div>
                                                                    <span><saveVendorAssociation></saveVendorAssociation></span></div>
                                                                <div class="updateCss ReqinputStyle ">   
                                                                    <table>
                                                                        <label class="labelStyle" id="labelLevelStatusReq"> Type Of Tier :</label><s:select name="tireType" id="tireType" value="" list="%{typesTiers}" headerKey="-1" headerValue="--select--" cssClass="selectBoxStyle form-control" onchange="return getVendorsNames()"/>
                                                                    </table>
                                                                </div> 
                                                                <div class="updateCss ReqinputStyle ">     
                                                                    <table>
                                                                        <label class="labelStyle" id="labelLevelStatusReq"> Access Time :</label><s:textfield name="accessTime" id="accessTime" value=""  cssClass="form-control dateImage"/>
                                                                    </table>
                                                                </div> 
                                                                <div class="updateCss ReqinputStyle ">     
                                                                    <table> <label class="labelStyle" id="labelLevelStatusReq">Vendor Names :</label>
                                                                        <s:select cssClass="selectBoxStyle form-control"  list="{}" name="vendorNames" id="vendorNames" value="%{vendorNames}"  multiple="true" size="4"/>   
                                                                    </table>
                                                                </div> 

                                                                <s:submit cssClass=" col-sm-offset-10 btn cssbutton " value="Save" type="button" onclick="return saveVendorAssociation()"/>
                                                            </div>
                                                        </div>

                                                        <div id="vendorAssoEdit_popup">

                                                            <div id="vendorAssocitaionEditOverlay">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Edit Association&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 ><a href="">&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="vendorAssoEdit_popup_close" onclick="associationEditOverlayclose()"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>

                                                                </div>
                                                                <span class="pull-left" style="margin-left: 15px">Name:&nbsp;<vendorNameDisplay></vendorNameDisplay></span><br>
                                                                <s:hidden id="vendorId" value=""/>

                                                                <div class="updateCss ReqinputStyle " style="margin-left: 10px">   
                                                                    <table>
                                                                        <label class="labelStyle" id="labelLevelStatusReq"> Type Of Tier :</label><s:select name="tireTypeEdit" id="tireTypeEdit" value="" list="%{typesTiers}" headerKey="-1" headerValue="--select--" cssClass="selectBoxStyle form-control" onchange="return getVendorsNames()" disabled="true"/>
                                                                    </table>
                                                                </div> 

                                                                <div class="updateCss ReqinputStyle " style="margin-left: 10px">     
                                                                    <table>
                                                                        <label class="labelStyle" id="labelLevelStatusReq"> Status :</label><s:select name="statusEdit" id="statusEdit" value="" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}" headerKey="DF" headerValue="--select--" cssClass="selectBoxStyle form-control"/>
                                                                    </table>
                                                                </div> 
                                                                <s:submit cssClass=" col-sm-offset-10 btn cssbutton vendorAssoEdit_popup_close"  value="Save" type="button" onclick="return updateVendorAssociation()"/>
                                                            </div>
                                                        </div>


                                                    </div> 
                                                    <div class="col-sm-12">
                                                        <br/> 
                                                        <div class="emp_Content" id="emp_div" align="center">
                                                            <table id="vendorAssociationResults" class="responsive CSSTable_task sortable" border="5">
                                                                <tbody>
                                                                    <tr> 
                                                                        <th>Vendor</th>
                                                                        <th class="unsortable">Vendor Tier </th>
                                                                        <th class="unsortable">No.Of.Submissions</th>
                                                                        <th class="unsortable"> Avg.Rate</th>
                                                                        <%--<th>Created BY </th>--%>
                                                                        <th class="unsortable">Access Time</th>
                                                                        <%--<th>Requirement</th>--%>
                                                                        <th class="unsortable">Status</th>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                            <div id="loadingVendor" class="loadingImg" style="display: none">
                                                                <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>
                                                            </div>
                                                            <div align="right" id="pageNavPosition" style="margin-right: 0vw;"></div>
                                                            <script type="text/javascript">
                                                                var pag = new Pager('vendorAssociationResults', 10); 
                                                                pag.init(); 
                                                                pag.showPageNav('pag', 'pageNavPosition'); 
                                                                pag.showPage(1);
                                                            </script>
                                                        </div>
                                                    </div>
                                                </div> 
                                            </div>


                                            <div class="tab-pane fade in " id="consultantList">
                                                <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                                    <div class="backgroundcolor" >
                                                        <div class="panel-heading">
                                                            <h4 class="panel-title">

                                                                <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                                <font color="#ffffff">Consultant Search</font>
                                                                <s:if test="accountFlag=='Account'">
                                                                    <s:url var="myUrl" action="acc/viewAccount.action">
                                                                        <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> 
                                                                        <s:param name="accFlag"><%=flag%></s:param>
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                                                    </s:if>
                                                                    <s:elseif test="accountFlag=='MyRequirements' && customerFlag=='customer'">
                                                                        <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                        --%>
                                                                        <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                            <s:param name="accountFlag">MyRequirements</s:param>
                                                                        <s:param name="orgid"><%=orgId%></s:param>
                                                                        <s:param name="customerFlag">customer</s:param>
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>
                                                                    <s:elseif test="accountFlag=='MyRequirements'">
                                                                        <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                        --%>
                                                                        <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                            <s:param name="accountFlag">MyRequirements</s:param>
                                                                        <s:param name="vendor">yes</s:param>
                                                                        <s:param name="orgid"><%=orgId%></s:param>
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>
                                                                    <s:elseif test="accountFlag=='OnlyMyRequirements'">
                                                                        <%--  <span class="pull-right"><a href="#"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                                                        --%>
                                                                        <s:url var="myUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                                            <s:param name="accountFlag">OnlyMyRequirements</s:param>
                                                                        <s:param name="vendor">yes</s:param>
                                                                        <s:param name="orgid"><%=orgId%></s:param>

                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>
                                                                    <s:elseif test="accountFlag=='csr'">

                                                                    <s:url var="myUrl" action="acc/viewAccount.action">                                                        
                                                                        <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                                        <s:param name="accFlag"><%=flag%></s:param>

                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:elseif>

                                                                <s:else>
                                                                    <s:url var="myUrl" action="recruitment/consultant/getAllRequirementList.action">
                                                                        <s:param name="orgid"><%=orgId%></s:param> 
                                                                    </s:url>
                                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span> 
                                                                    </s:else>
                                                            </h4>
                                                        </div>
                                                    </div>
                                                    <div class="pull-right" style="float: left; margin-top:0px; margin-bottom: -7px">
                                                        <label class=""> Job Title: </label>                                         
                                                        <span style="color: green"><s:property value="%{requirementVTO.RequirementName}"/></span>
                                                    </div>

                                                    <div class="col-sm-12">
                                                        <s:form action="" theme="simple">
                                                            <s:hidden name="vendor" id="vendor" value="%{vendor}"/>
                                                            <s:hidden name="RequirementId" id="RequirementId" value="%{requirementVTO.RequirementId}"/>
                                                            <s:hidden name="jdId" id="jdId" value="%{jdId}"/>

                                                            <s:hidden name="downloadFlag" id="downloadFlag" value="%{downloadFlag}"/>
                                                            <s:hidden name="customerFlag" id="customerFlag" value="%{customerFlag}"/>
                                                            <s:hidden name="accountFlag" id="accountFlag" value="%{accountFlag}" ></s:hidden>
                                                            <s:hidden name="accountSearchID" id="accountSearchID" value="%{accountSearchID}"></s:hidden>

                                                                <div class="row">
                                                                    <div class="col-lg-4">
                                                                        <label style="color:#56a5ec;" class="labelStylereq">Vendor Name:</label>
                                                                    <s:textfield cssClass="form-control" name="vendorName" id="vendorName" tabindex="1"/>
                                                                </div>

                                                                <div class="col-lg-4">

                                                                    <label style="color:#56a5ec;" class="labelStylereq">Candidate Name:&nbsp;&nbsp;</label>
                                                                    <s:textfield cssClass="form-control" name="consult_name" id="consult_name" placeholder="" value="%{consult_name}" tabindex="1"/>

                                                                </div>

                                                                <div class="col-lg-4">
                                                                    <label style="color:#56a5ec;" class="labelStylereq">E-Mail:</label>
                                                                    <s:textfield cssClass="form-control" name="consult_email" id="consult_email" placeholder="" value="%{consult_email}" tabindex="1"/>

                                                                </div>

                                                            </div>
                                                            <div class="row">
                                                                <div class="col-lg-4">
                                                                    <label style="color:#56a5ec;" class="labelStylereq">Skill Set:</label>
                                                                    <s:textfield cssClass="form-control" name="consult_skill" id="consult_skill" placeholder="" value="%{consult_skill}" tabindex="1"/>
                                                                </div>

                                                                <div class="col-lg-4">
                                                                    <label style="color:#56a5ec;" class="labelStylereq">Phone:</label>
                                                                    <s:textfield cssClass="form-control" name="consult_phno" id="consult_phno" placeholder="" value="%{consult_phno}" tabindex="1"/>
                                                                </div>
                                                                <s:hidden name="consultantFlag" id="consultantFlag" value="%{consultantFlag}"/>

                                                                <div class="col-lg-4">
                                                                    <div class="row">

                                                                        <div class="col-lg-6">
                                                                            <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                                            <s:submit cssClass="cssbutton form-control" id="searchButton" value="search" onclick="return getConsultantListBySearch();"/>
                                                                        </div>
                                                                        <div class="col-lg-6">
                                                                            <s:if test="consultantFlag == 1">
                                                                                <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                                                <a href="/<%=ApplicationConstants.CONTEXT_PATH%>/recruitment/consultant/addConsultant.action" ><input type="button" class=" cssbutton form-control"" value="Add Consultant"></a> &nbsp;
                                                                                </s:if>
                                                                        </div>
                                                                    </div>
                                                                    <s:hidden name="techSearch" id="techSearch" value="%{techSearch}"/>
                                                                    <s:hidden name="reqFlag" id="reqFlag" value="%{reqFlag}"/>
                                                                </div>
                                                            </div>

                                                        </s:form>
                                                    </div>

                                                    <div class="col-sm-12">
                                                        <s:form>
                                                            <s:hidden id="jdId" name="jdId" value="%{jdId}"/>
                                                            <s:if test='downloadFlag=="noResume"'>
                                                                <span id="resume"><font style='color:red;font-size:15px;'>No Attachment exists !!</font></span>
                                                                </s:if>
                                                                <s:if test='downloadFlag=="noFile"'>
                                                                <span id="resume"><font style='color:red;font-size:15px;'>File Not Found !!</font></span>
                                                                </s:if>  
                                                            <br/>
                                                            <div class="task_content" id="task_div" align="center" style="display: none" >

                                                                <div>
                                                                    <div>

                                                                        <table id="consultantListTable" class="responsive CSSTable_task" border="5"cell-spacing="2">

                                                                            <tbody>
                                                                                <tr>
                                                                                    <s:if test="vendor!='yes'">
                                                                                        <th>Vendor</th>
                                                                                    </s:if>
                                                                                    <th>Candidate Name</th>
                                                                                    <th>E-Mail</th>
                                                                                    <th>Skill Set</th>
                                                                                    <th>Phone No</th>
                                                                                    <th>Status</th>
                                                                                    <th>Rate</th>
                                                                                    <th>Resume</th>
                                                                                    <s:if test="vendor!='yes'">
                                                                                        <th>Reviews</th>
                                                                                    </s:if>
                                                                                    <%--   <s:if test="accountFlag=='csr'">    
                                                                                           <th>Login</th>  
                                                                                       </s:if> 
                                                                                           <th>Migration</th>--%>
                                                                                </tr>

                                                                            </tbody>
                                                                        </table>
                                                                        <br/>
                                                                        <div id="loadingConsultant" class="loadingImg">
                                                                            <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>
                                                                        </div>
                                                                        <div align="right" id="taskpageNavPosition" style="margin-right: 0vw;"></div>
                                                                        <script type="text/javascript">
                                                                            var pager = new Pager('consultantListTable', 10); 
                                                                            pager.init(); 
                                                                            pager.showPageNav('pager', 'taskpageNavPosition'); 
                                                                            pager.showPage(1);
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </s:form>

                                                    </div>
                                                    <div id="Migration_popup">
                                                        <div id="recruiterBox" class="marginTasks">
                                                            <div class="backgroundcolor">
                                                                <table>
                                                                    <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Forwarded By Details&nbsp;&nbsp; </font></h4></td>
                                                                    <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="Migration_popup_close" onclick="migration_overlayClose()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                </table>
                                                            </div>
                                                            <div>
                                                                <form>
                                                                    <span><mig></mig></span>
                                                                    <s:hidden name="consult_id" id="consult_id" value=""/>
                                                                    <s:hidden name="req_Id" id="req_Id" value="%{requirementVTO.RequirementId}"/>
                                                                    <s:hidden name="acc_Id" id="acc_Id" value=""/>
                                                                    <s:hidden name="acc_type" id="acc_type" value=""/>
                                                                    <!--s:property value="%{vendor}"/-->
                                                                    <s:hidden name="vendor" id="vendor" value="%{vendor}"/>
                                                                    <s:hidden name="loginId" id="loginId" value="%{#session['loginId']}"/>
                                                                    <s:hidden id="cName" name="cName" value=""/>

                                                                    <center>   
                                                                        <table>

                                                                            <div class="inner-techReviewdiv-elements">
                                                                                <s:textfield cssClass="form-control " 
                                                                                             id="migrationEmailId" 
                                                                                             type="text" value="" 
                                                                                             name="migrationEmailId"
                                                                                             placeholder="" label="EmailId"
                                                                                             onfocus="removeErrorMessages()"/>


                                                                            </div>

                                                                        </table>

                                                                    </center>
                                                                    <br/>
                                                                    <s:submit cssClass="cssbutton migrationButton"
                                                                              id="migrate" 
                                                                              onclick="return userMigration();"
                                                                              value="Migrate" />
                                                                </form>             
                                                            </div>
                                                            <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                                        </div>
                                                    </div>

                                                    <br><br>


                                                </div>


                                            </div>
                                        </div>
                                        <!--End Tabs-->
                                    </div>

                                </div><%-- panel task body complete--%>





                            </div>

                            <%--close of future_items--%>
                        </div>
                    </div>
                </div>
            </div>

            <!-- content end -->
            <%--  Skill Overlay --%>
            <div id="recSkillOverlay_popup">
                <div id="consultantSkillSetBox" class="marginTasks">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Skill Details&nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="recSkillOverlay_popup_close" onclick="consultantSkillCloseOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div>

                        <s:textarea name="customerSkillDetails"   id="customerSkillDetails"   disabled="true" cssClass="form-control textareaSkillOverlay"/> 


                    </div>
                    <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                </div>
            </div>
            <div id="consultantLoginOverlay_popup" >
                <div id="consultantLoginBox" class="marginTasks">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor" >&nbsp;&nbsp; Consultant Login Credentials&nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="consultantLoginOverlay_popup_close" onclick="consultantLoginCredential()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div>
                        <div class="inner-consuldiv-elements">
                            <div  id="outputMessage"></div>
                            <s:hidden id="consultantEmail" name="consultantEmail" value=""/>
                            <s:hidden id="consultantId" name="consultantId" value=""/>
                            <div  id="consultantdivEmail" ></div>
                        </div>
                        <div class="pull-left " >
                            <s:submit type="button" cssClass="consultantLoginOverlay_popup_close" id="contactCancel" onclick="consultantLoginCredential()" value="Cancel"/>  
                        </div>  
                        <div class="pull-right " > 

                            <s:submit type="button" cssClass="cssbutton" id="contactSend" value="Send" onclick="saveConsultantLoginDetails()"/> 

                        </div>

                    </div>
                    <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                </div>   
            </div>                  

            <%--close of overlay --%> 
        </section>


        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer> 

        <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
        <script type="text/javascript">
            var techSearch=document.getElementById("techSearch").value;
            var flag=document.getElementById("downloadFlag").value;
            var reqFalg=document.getElementById("reqFlag").value;
            //var techSearch=$("#techSearch").val();
            if(flag=="noResume"||flag=="noFile")
            {
                //alert("in if");
                document.getElementById('consultantListLi').className='active active_details';
                document.getElementById('details').className='tab-pane fade in';
                document.getElementById('consultantList').className='tab-pane fade in active';
       
                //alert("before show consultantList function");
       
                if(techSearch=="search")
                { getConsultantListBySearch();}
                else if(techSearch!="search")
                {
                    getConsultantList();}
            }
            else if(reqFalg=="consultantTab")
            {
                document.getElementById('consultantListLi').className='active active_details';
                document.getElementById('details').className='tab-pane fade in';
                document.getElementById('consultantList').className='tab-pane fade in active';
                getConsultantList();
         
            }
            else
            {
                document.getElementById('detailsLi').className='active active_details';
         
            }
        </script>
        <script>
            setTimeout(function(){              
                $('#resume').remove();
            },3000);
        </script>
        <script>
            ;   sortables_init();
        </script>
    </body>
</html>

