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
        <title>Miracle Service Bay :: Tech Review Page</title>

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
                    <div class="col-md-10 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items">
                            
                            <div class="" style="float: left; margin-top:2px; margin-bottom: -2px">
                                <s:hidden name="requirementId" id="requirementId" value="%{requirementId}" />
                                <s:hidden name="consult_id" id="consult_id" value="%{consult_id}" />
                                <s:hidden name="accountFlag" id="accountFlag" value="%{accountFlag}" />
                                <s:hidden name="jdId" id="jdId" value="%{jdId}" />
                                <s:hidden name="accountSearchID" id="accountSearchID" value="%{accountSearchID}" />



                                <s:url var="csrMyUrl" action="acc/viewAccount.action">
                                    <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                    <s:param name="accFlag">accDetails</s:param>
                                </s:url>
                                <s:url var="csrReqUrl" action="acc/viewAccount.action">
                                    <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                    <s:param name="accFlag">reqSearch</s:param>
                                </s:url>

                                <s:url var="csrReqEditUrl" action="Requirements/requirementedit.action">
                                    <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                    <s:param name="accountSearchID"><s:property value="%{accountSearchID}"/></s:param> 
                                    <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                    <s:param name="accountFlag">csr</s:param>
                                </s:url>
                                    <s:url var="csrTechReviewtUrl" action="Requirements/techReview.action">
                                    <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param>
                                    <s:param name="consult_id"><s:property value="%{consult_id}"/></s:param>
                                    <s:param name="accountSearchID"><s:property value="%{accountSearchID}"/></s:param> 
                                    <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                    <s:param name="accountFlag">csr</s:param>
                                </s:url>


                                <label class="">Path: </label> 
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrMyUrl}' style="color:#FC9A11;"><s:property value="%{accountName}"/></s:a>
                                </s:if>
                                <s:else>
                                    <s:a href='#' style="color:#FC9A11;"><s:property value="%{accountName}"/></s:a>
                                </s:else>
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrReqUrl}' style="color:#FC9A11;">->Requirements List</s:a>
                                </s:if>
                                <s:else>
                                    <s:a href='#' style="color:#FC9A11;">->Requirements List</s:a>        
                                </s:else>
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrReqEditUrl}' style="color:#FC9A11;">-><s:property value="%{jdId}"/></s:a>
                                </s:if>
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrTechReviewtUrl}' style="color:#FC9A11;">->Tech Review</s:a>
                                </s:if>
                                    <span style="color: #FC9A11;">->Forward Review</span>



                            </div>
                            
                            
                            <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                <div class="backgroundcolor" >
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                       <s:url var="revUrl" action="Requirements/techReview.action">
                                        <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                        <s:param name="consult_id" ><s:property value="%{consult_id}" /></s:param> 
                                        <s:param name="accountSearchID"><s:property value="%{accountSearchID}"/></s:param> 
                                        <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                        <s:param name="accountFlag" ><s:property value="%{accountFlag}" /></s:param> 
                                      </s:url>
                          
                                            <font color="#ffffff">Tech Review Details</font>
                                            <span class="pull-right"><s:a href='%{#revUrl}'><img  src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                    </h4>
                                    </div>
                                </div>

                                <%--<s:form action="" id="#" theme="simple" enctype="multipart/form-data" >--%>
                                <s:hidden name="requirementId" id="requirementId" value="%{requirementId}" />
                                <s:hidden name="consult_id" id="consult_id" value="%{consult_id}" />
                                <label class="">Consultant Name:<font style="color: #FF8A14;"><s:property value="%{consult_name}"/></font></label>
                                <label class="pull-right ">Requirement Name:<font style="color: #FF8A14;"><s:property value="%{reqName}"/></font></label>
                                <%--div class="inner-techReviewdiv-elements">
                                    <span><e></e></span><br>
                                    <%--  <label class="techReviewlabelStyle">Interview:</label><s:select cssClass="techReviewSelectStyle" name="interview" id="interview" list="#@java.util.LinkedHashMap@{'I':'Internal'}" headerKey="-1" value="1" onchange="return getEmpForTechReview();"/>--%>
                                    <%--label class="">Interview Mode:</label><s:select cssClass="techReviewSelectStyle" name="interviewType" id="interviewType" list="#@java.util.LinkedHashMap@{'Face to Face':'Face to Face','Telephonic':'Telephonic','Skype':'Skype','Written':'Written','hr':'hr'}" headerKey="-1" value="1" onchange="setLocationForFaceToFace();"/>
                                    <s:hidden name="empIdTechReview" id="empIdTechReview" />
                                    <s:hidden name="empIdTechReview2" id="empIdTechReview2" />
                                    <label class="">Techie1 Name:</label><s:textfield cssClass="techReviewInputStyle" id="eNameTechReview"  name="eNameTechReview" onkeyup="return getEmpForTechReview();" />
                                    <label class="">Techie2 Name:</label><s:textfield cssClass="techReviewInputStyle" id="eNameTechReview2"  name="eNameTechReview" onkeyup="return getEmpForTechReview2();" />
                                    <div class="inner-techReviewdiv-elements"><span id="validationMessage" /></div>   
                                </div--%>
                                <div class="inner-reqdiv-elements">
                                    <span><e></e></span><br>
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <label class="labelStylereq" style="color: #56a5ec">Interview Mode:</label>
                                            <s:select cssClass="SelectBoxStyles form-control" name="interviewType" id="interviewType" list="#@java.util.LinkedHashMap@{'Face to Face':'Face to Face','Telephonic':'Telephonic','Skype':'Skype','Written':'Written','hr':'hr'}" headerKey="-1" value="1" onchange="setLocationForFaceToFace();"/>
                                        </div>
                                        <s:hidden name="empIdTechReview" id="empIdTechReview" />
                                        <s:hidden name="empIdTechReview2" id="empIdTechReview2" />
                                        <div class="col-lg-3">
                                            <label class="labelStylereq" style="color: #56a5ec">Techie Name:</label>
                                            <s:textfield cssClass="form-control" id="eNameTechReview"  name="eNameTechReview" onkeyup="return getEmpForTechReview();" autocomplete='off'/>
                                        </div>
                                       <%-- <div class="col-lg-3">
                                           <%-- <label class="labelStylereq" style="color: #56a5ec">Techie2 Name:</label>--%>
                                            <%--<s:textfield cssClass="form-control" id="eNameTechReview2"  name="eNameTechReview" onkeyup="return getEmpForTechReview2();" autocomplete='off'/>--%>
                                            <s:hidden id="eNameTechReview2"  name="eNameTechReview" value=" "/>
                                       <%-- </div>--%>
                                        <div class="col-lg-3">
                                            <label class="labelStylereq" style="color: #56a5ec">Interview Date:</label>
                                            <s:textfield cssClass="techReviewInputStyle dateImage" id="interviewDate"  name="interviewDate" onkeyup="return enterDateRepository();"  />
                                        </div>
                                        <div class="col-lg-3">
                                            <label class="labelStylereq" style="color: #56a5ec">Zone:</label>
                                            <s:select cssClass="SelectBoxStyles form-control" name="timeZone" id="timeZone" list="#@java.util.LinkedHashMap@{'IST':'IST','EST':'EST','CST':'CST','PST':'PST'}" headerKey="-1" value="1"/>
                                        </div>
                                  
                                        <div class="inner-techReviewdiv-elements"><span id="validationMessage" /></div>  
                                    </div>
                                </div>

                                <%--div class="inner-techReviewdiv-elements">
                                    <label class="">Interview Date:</label><s:textfield cssClass="techReviewInputStyle" id="interviewDate"  name="interviewDate" onkeyup="return enterDateRepository();"  />
                                    <s:select cssClass="techReviewSelectStyleForZone" name="timeZone" id="timeZone" list="#@java.util.LinkedHashMap@{'IST':'IST','EST':'EST','CST':'CST','PST':'PST'}" headerKey="-1" value="1"/>
                                    <span id="location">
                                        <label class="">Location:</label><s:textarea maxlength="200" name="interviewLocation" id="interviewLocation"  />
                                    </span>
                                </div--%>
                                <div class="inner-reqdiv-elements">
                                    <div class="row">
                                              <div class="col-lg-12" id="location">
                                            <label class="labelStylereq" style="color: #56a5ec">Location:</label>
                                            <s:textarea cssClass="form-control" maxlength="200" name="interviewLocation" id="interviewLocation"  />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-11">
                                    <div class="panel panel-warning">
                                        <div class="panel-heading"> 
                                            <div class="form-group">
                                                <s:checkbox name="techReviewAlert" style="color:#0066FF;" id="techReviewAlert" label="Do you need alert" onchange="toggleDisabled(this.checked)" />
                                            </div>
                                        </div>
                                        <div class="panel-body" id="techAlertContent">
                                            <%--label class="techReviewlabelStyle">Alert Date:</label><s:textfield cssClass="techReviewInputStyle" id="reviewAlertDate"  name="reviewAlertDate" onkeyup="return enterDateRepository();" onchange="return dateValidationWithInterviewDate();" />
                                            <label style="color:#56a5ec;" class="techReviewlabelStyle">Alert message:</label>
                                            <s:textarea name="alerttextarea" id="alertMessageTechReview"  /--%>
                                            <div class="inner-reqdiv-elements">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color: #56a5ec">Alert Date:</label>
                                                        <s:textfield cssClass="techReviewInputStyle dateImage" id="reviewAlertDate"  name="reviewAlertDate" onkeyup="return enterDateRepository();" onchange="return dateValidationWithInterviewDate();" />
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color: #56a5ec">Alert message:</label>
                                                        <s:textarea cssClass="form-control" name="alerttextarea" id="alertMessageTechReview"  />
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="inner-techReviewdiv-elements">
                                    <s:submit cssClass="cssbutton pull-right" value="Forward" onclick="forwardReviewToCustomer();" theme="simple"  />
                                </div>
                                <%--</s:form>--%>
                            </div>
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
        <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>
    </body>
</html>