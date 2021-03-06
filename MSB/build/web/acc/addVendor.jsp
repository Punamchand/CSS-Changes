<%--
    Document   : AccountDetails
    Created on : May 3, 2015, 2:08:50 PM
    Author     : rama krishna<lankireddy@miraclesoft.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Add Vendor Page</title>

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
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/techReviewAjax.js"/>"></script>

    </head>
    <body onload="doOnLoad();">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/>
                </div>
            </div>

        </header>
        <%-- ------------MIDDLE -----------------------------------------%>
        <section id="generalForm"><!--form-->
            <div class="container">
                <div class="row">
                    <s:include value="/includes/menu/LeftMenu.jsp"/>
                    <div class="features_items">
                        <div class="" id="profileBox" style="float: left; margin-top: 5px">
                            <div class="backgroundcolor" >
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <font color="#ffffff">Adding a Vendor For Customer</font>
                                        <s:url var="myUrl" action="../acc/viewAccount.action">
                                            <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> 
                                            <s:param name="accFlag">venSearch</s:param>
                                        </s:url>
                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                      </h4>
                                </div>
                            </div>
                            <label class="">Account Name:<font style="color: #FF8A14;"><s:property value="%{accountName}"/></font></label>
                            <div class="inner-reqdiv-elements">
                                <span><e></e></span>
                                <div class="row">
                                    <div class="col-lg-3">
                                        <s:hidden name="accountSearchID" id="accountSearchID" value="%{accountSearchID}" />
                                        <s:hidden name="vendorId" id="vendorId" />
                                        <label class="labelStylereq" style="color: #56a5ec">Vendor Name:</label>
                                        <s:textfield cssClass="form-control" id="vendorName"  name="vendorName" onkeyup="return getVendorNames();" />
                                    </div>
                                    <div class="col-lg-3">
                                        <label class="labelStylereq" style="color: #56a5ec">URL:</label>
                                        <s:textfield cssClass="form-control " id="vendorURL"  name="vendorURL" disabled="true" />
                                    </div>

                                    <div class="col-lg-3">
                                        <label class="labelStylereq" style="color: #56a5ec">Tier:</label>
                                        <s:select cssClass="SelectBoxStyles form-control" name="vendorTier" id="vendorTier" headerKey="0" headerValue="--select--" list="vendorTierMap"/>
                                    </div>
                                    <div class="col-lg-2"><br>
                                        <label class="labelStylereq" style="color: #56a5ec">Head Hunter:</label>
                                        <s:checkbox name="PF" id="PF" />
                                    </div>
                                </div>
                            </div>
                            <div class="inner-reqdiv-elements">
                                <div class="inner-techReviewdiv-elements"><span id="validationMessage" /></div> 
                                <div class="row">
                                    <div class="col-lg-12">
                                        <label class="labelStylereq" style="color: #56a5ec">Comments:</label>
                                        <s:textarea cssClass="form-control" cssStyle="width=100%" name="vendorComments" id="vendorComments"/>
                                    </div>
                                </div>
                                <div class="row">

                                    <div class="col-lg-4 pull-right">
                                        <br>
                                        <s:submit cssClass="cssbutton pull-right" value="Save" onclick="saveVendorTierDetails();" theme="simple"  />
                                    </div>
                                </div>
                            </div>
                            <%--</s:form>--%>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <%-- ------------MIDDLE -----------------------------------------%>
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->
        <div style="display: none; position: absolute; top:186px;left:246px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>
    </body>
</html>