<%-- 
    Document   : addingAccToCsr
    Created on : Jul 15, 2015, 9:08:43 PM
    Author     : praveen<pkatru@miraclesoft.com>
--%>

<%@page import="com.mss.msp.usersdata.UserVTO"%>
<%@page import="com.mss.msp.usersdata.UsersdataHandlerAction"%>
<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/ErrorDisplay.jsp"%>
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
        <title>Miracle Service Bay :: Add Account</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value='/includes/css/accountDetails/details.css'/>">


        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/account/formVerification.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>

        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>

        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value="/includes/js/general/ProfilePage.js"/>" ></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/CountriesAjax.js"/>"></script>
        <script language="JavaScript" src="<s:url value="/includes/js/account/accountDetailsAJAX.js"/>" type="text/javascript"></script>

        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>


    </head>

    <body onload="getStockSymbol($(acc_country).val());">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/>
                </div>
            </div>

        </header>

        <div class="container">
            <div class="row">
                <!-- Main Content-->
                <s:include value="/includes/menu/LeftMenu.jsp"/>
                <div class="col-md-10" style="">
                    <!-- Add Form Area -->
                    <div class="col-lg-12">
                        <div class="" id="profileBox" style="float: left; margin-top: 15px; margin-bottom: 20px">
                            <!-- Add Form Header-->
                            <div class="backgroundcolor" >
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                        <font color="#ffffff">Adding Account to Csr</font>
                                        <s:url var="myUrl" action="viewAccount.action">
                                            <s:param name="accountSearchID"><s:property value="orgUserId"/></s:param> 
                                            <s:param name="accFlag">CsrSearch</s:param>
                                        </s:url>
                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                    </h4>
                                </div>
                            </div> 
                            <div class="col-lg-12">
                                <span><csrResult></csrResult></span>
                                <div class="inner-reqdiv-elements">
                                    <div class="row">

                                        <div class="col-lg-4">

                                            <label class="labelStylereq" style="color:#56a5ec">Account Name:</label>
                                            <s:textfield name="accountName" cssClass="form-control" disabled="true"/>

                                            <s:hidden name="orgUId" value="%{orgUserId}"/>
                                        </div>
                                        <div class="col-lg-4">

                                            <label class="labelStylereq" style="color:#56a5ec">CSR:</label>
                                            <s:textfield cssClass="form-control " name="Name" id="csrName" onkeyup="return getCSRNames();" autocomplete='off'/>
                                            <s:hidden name="csrId" id="csrId"/>
                                            <span id="validationMessage" />
                                        </div>
                                        <div class="col-lg-4">

                                            <label class="labelStylereq" style="color:#56a5ec">Status:</label>
                                            <s:select id="csrStatus" name="csrStatus" cssClass="SelectBoxStyles form-control "   theme="simple" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}"  disabled="true"/>
                                        </div>

                                        <s:submit value="Save" cssClass="cssbutton_emps pull-right" onclick="doAddAccountToCsr()"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- Add Form-->
            </div>
        </div>
                                    <div style="height: 322px;"></div>
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer>
        <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>
    </body>
</html>
