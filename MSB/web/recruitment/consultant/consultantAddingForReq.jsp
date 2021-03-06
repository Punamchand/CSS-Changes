<%-- 
    Document   : consultantAddingForReq
    Created on : Jul 20, 2015, 6:51:13 PM
    Author     : praveen<pkatru@miraclesoft.com>
--%>

<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Consultant Adding For Requirement Page</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/profilediv.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/requirementStyle.css"/>'>

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/CountriesAjax.js"/>"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/requirement.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script>
        <%--script type="text/javascript" src="<s:url value="/includes/js/Ajax/vendorAjax.js"/>"></script--%>
    </head>
    <body style="overflow-x: hidden"">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/>
                </div>
            </div>
        </header>

        <s:include value="/includes/menu/LeftMenu.jsp"/>
        <section id="generalForm"><!--form-->
            <div  class="container">
                <div class="row">
                    <!-- content start -->
                    <div class="col-md-10 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items">
                            <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                <div class="backgroundcolor" >
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <font color="#ffffff">Add Consultant For Job </font> 
                                            <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                            <s:url var="myUrl" action="getLoginUserRequirementList.action">
                                                 <s:param name="accountFlag">MyRequirements</s:param> 
                                                <s:param name="orgid"><s:property value="%{orgid}"/></s:param> 
                                                <s:param name="vendor">yes</s:param>
                                            </s:url>
                                            <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>

                                            </h4>

                                        </div>

                                    </div>

                                    <div>
                                        <div class="pull-left" style="float: left; margin-top:0px;margin-bottom: -7px;">
                                            <label class=""> Job Title: </label>                                         
                                            <span style="color: green"><s:property value="%{jobTitle}"/></span>
                                    </div>
                                    <div class="pull-right" style="float: left; margin-top:0px;margin-bottom: -7px;">
                                        <label class=""> Job ID: </label>                                         
                                        <span style="color: green"><s:property value="%{jdId}"/></span>
                                    </div>
                                    <br>
                                    <div class="pull-left" style="float: left; margin-top:0px;margin-bottom: -7px;">
                                        <label class=""> Rate(Min - Max): </label>                                         
                                        <span style="color: green">$<s:property value="%{targetRate}"/>/Hr</span> - <span style="color: green">$<s:property value="%{maxRate}"/>/Hr</span>
                                    </div>
                                    <br>
                                    <s:if test="hasActionMessages()">
                                        <div  >
                                            <span id="actionMessage"><s:actionmessage cssClass="actionContactMessagecolor"/></span>
                                        </div>
                                    </s:if>
                                    <s:form action="storeProofData" theme="simple"  enctype="multipart/form-data" onsubmit="return addconsultantValidation();">

                                        <span><e1></e1></span>
                                        <div class="col-lg-12">
                                            <s:hidden name="reqId" id="reqId" value="%{requirementId}"/>

                                            <s:hidden name="resourceType" id="resourceType"/>
                                             <s:hidden name="jdId" id="jdId" value="%{jdId}"/>
                                            <s:hidden name="jobTitle" id="jobTitle" value="%{jobTitle}"/>
                                            <s:hidden name="orgId" id="orgid" value="%{orgid}"/>
                                            <s:hidden name="targetRate" id="targetRate" value="%{targetRate}"/>



                                            <div class="col-lg-4">
                                                <label class="labelStyleAddCon"><font color="red">*</font>Email:</label><s:textfield name="conEmail" id="conEmail" theme="simple" cssClass="form-control" onblur="getEmailExistance();" onclick="clearConultantAddOverlay()"/>
                                            </div>
                                            <div class="col-lg-4">
                                                <label class="labelStyleAddCon"><font color="red">*</font>Rate/Hr:</label>
                                                <div class="input-group">
                                                    <s:textfield name="ratePerHour" id="ratePerHour" theme="simple" cssClass="form-control"  onkeyup="return ratePerHourValidation();" onclick="clearConultantAddOverlay()" ><span class="input-group-addon">$</span></s:textfield>
                                                      <span class="input-group-addon">/Hr</span>
                                                </div>
                                            </div>


                                            </div>
                                            <div class="col-lg-12">

                                                <div class="col-lg-4">

                                                <s:hidden id="proofType" name="proofType" value="N"/>
                                            </div>
                                            <div class="col-lg-4">
                                                <div  id="ppId">
                                                    <s:hidden name="ppno" id="ppno" value="" theme="simple" cssClass="form-control"/>
                                                </div>
                                                <div  id="panId">
                                                    <s:hidden name="pan" id="pan" value="" theme="simple" cssClass="form-control"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="col-lg-12">
                                                <label class="labelStyleAddCon">Skills</label><s:textarea id="propsedSkills" name="propsedSkills" cssClass=" form-control"   />
                                            </div>
                                        </div>
                                        <div class="col-lg-14" id="IsEmployee">
                                            <label class="labelStylereq" style="margin-left:  25px"><font color="red">*</font>Upload&nbsp;resume:</label><s:file  cssStyle="margin-left:  25px" name="file" id="file" cssClass=""  tabindex="12" />
                                            <span style="color:red;margin-left: 25px">Upload PDF or Doc or Docx file.</span>
                                        </div>
                                        <div class="pull-right">
                                            <s:submit id="addConSubmit" cssClass="cssbutton" value="Submit" onclick="return storeProofData()" />
                                        </div>


                                    </s:form>
                                </div>
                                <%--close of future_items--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>        <!-- content end -->
    </section><!--/form-->
    <footer id="footer"><!--Footer-->
        <div class="footer-bottom" id="footer_bottom">
            <div class="container">
                <s:include value="/includes/template/footer.jsp"/>
            </div>
        </div>
    </footer><!--/Footer-->
    <script language="JavaScript" src='<s:url value="/includes/js/general/popupoverlay.js"/>'></script>

</body>
</html>
