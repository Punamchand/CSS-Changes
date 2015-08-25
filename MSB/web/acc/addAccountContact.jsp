<%-- 
   Document   : Add Account contact
   Author     : manikanta,meeralla@miraclesoft.com
--%>


<%@ page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay ::  Add Contact Page</title>

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
        <style type="text/css">
            #placement-examples .east { margin-left: 450px; }
        </style>
        <script type="text/javascript">
            $(function() {
                // placement examples
			
                $('.east').powerTip({ placement: 'e' });
			
            });
        </script>
    </head>
    <body style="overflow-x: hidden">
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


                                    <!-- content start -->
                                    <br>
                                    <% String accFlag = "accDetails";%> 
                                    <div class="col-lg-12"  style=" margin-top:-12px; margin-bottom: 20px">
                                        <label >Account Name:   </label>                                       
                                        <s:url var="myUrl" action="viewAccount.action">
                                            <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                            <s:param name="accFlag"><%= accFlag%></s:param>
                                        </s:url>
                                        <s:a href='%{#myUrl}' style="color: #0000FF;"><s:property value="%{accountName}"/></s:a> 

                                            <div class="backgroundcolor" >
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">

                                                        <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->

                                                    <% String flag = "conSearch";
                                                    %>
                                                    <font color="#ffffff"> Add Contact</font>
                                                    <s:url var="myUrl" action="../acc/viewAccount.action">
                                                        <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> 
                                                        <s:param name="accFlag"><%=flag%></s:param>
                                                    </s:url>
                                                    <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>

                                                    </h4>
                                                </div>

                                            </div>
                                            <span id="addContactError"> </span> 
                                            &nbsp;&nbsp;<span id="InsertContactInfo"></span>

                                            <div>
                                            <s:form name="contactform" action="addContact" cssClass="form-horizontal" theme="simple"  enctype="multipart/form-data" onsubmit="return contactInfoValidation()">
                                                <div>
                                                    <%--<s:hidden value="%{userid}" name="userid"/>
                                                    <s:hidden id="usr_edu_id" name="usr_edu_id" /> <br> --%>
                                                    <s:hidden name="AccountSearchOrgId" id="AccountSearchOrgId" value="%{accountSearchID}"/>
                                                    <s:hidden name="accountType" id="accountType" value="%{accountType}"/>
                                                    <s:hidden id="email_ext" name="email_ext" value="%{email_ext}" />


                                                    <div class="inner-reqdiv-elements">
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle"><span style="color:red;">*</span>First Name</label><s:textfield  name="ContactFname"  id="ContactFname"  cssClass="form-control" placeholder="first Name" required="true" onkeyup="contactFirstNameValidation()"/>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Middle&nbsp;Name</label><s:textfield name="ContactMname"  id="ContactMname"  cssClass="form-control" placeholder="middle Name" pattern='[A-Za-z\\s]*' />
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle"><span style="color:red;">*</span>Last Name</label><s:textfield name="ContactLname"  id="ContactLname"  cssClass="form-control" placeholder="last Name" required="true" onkeyup="contactLastNameValidation()"/>
                                                            </div>


                                                        </div>
                                                    </div>
                                                    <div class="inner-reqdiv-elements">
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle"><span style="color:red;">*</span>Email </label>
                                                                <s:textfield type="text" maxlength="25" n="3" autocomplete="off" cssClass="form-control "
                                                                             name="ContactEmail" id="ContactEmail" value="" required="true"  onchange="EmailValidation()" style="padding-right: 150px;"  spellcheck="false" >
                                                                    <span class="urlDomain">@<s:property value="%{email_ext}" /></span>
                                                                </s:textfield>
                                                                <%--label class="addAcclabelStyle"><span style="color:red;">*</span>Email</label><s:textfield cssClass="form-control" placeholder="email" name="ContactEmail" id="ContactEmail" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,}" oninvalid="setCustomValidity('Must be valid email')"   onchange="try{setCustomValidity('')}catch(e){}" required="true"  onkeyup="EmailValidation()" --%>

                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Alternate Email</label><s:textfield cssClass="form-control" placeholder="Alternate email" name="ContactAEmail" id="ContactAEmail" value="%{empDetails.email2}" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,}" oninvalid="setCustomValidity('Must be valid email')"   onchange="try{setCustomValidity('')}catch(e){}"/>

                                                            </div>
                                                            <div class="col-lg-4">
                                                                 <label class="addAcclabelStyle">Gender</label>
                                                            <s:select cssClass="SelectBoxStyles form-control " id="gender" name="gender" label="gender" list="#@java.util.LinkedHashMap@{'M':'Male','F':'Female'}" />
                                                               <%-- <label class="addAcclabelStyle"><span style="color:red;">*</span>Designation</label><s:textfield cssClass="form-control" placeholder="Designation"  name="contactDesignation" id="designation"  required="true" onkeyup="contactDesignationValidation()"/>--%>
                                                                <%--label class="addAcclabelStyle">Designation:</label><s:select cssClass="form-control SelectBoxStyles" placeholder="Designation"  name="contactDesignation" id="designation" headerkey="-1" list="designations" /--%>
                                                            </div>

                                                        </div>
                                                    </div>

                                                    <div class="inner-reqdiv-elements">
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle"><span style="color:red;">*</span>Office&nbsp;Phone</label><s:textfield id="Officephone" cssClass="form-control" name="Officephone" required="true" type="text" placeholder="Phone #" />
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Mobile Phone</label><s:textfield cssClass="form-control" label="Phone" id="conPhone" name="conPhone" placeholder="Mobile Phone #"  oninvalid="setCustomValidity('Must be valid fn')"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pPhoneValidation()" />
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Home Phone</label><s:textfield cssClass="form-control" label="Phone" id="homePhone" name="conCPhone" placeholder="Office Phone #"  oninvalid="setCustomValidity('Must be valid fn')"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pPhoneValidation()" />
                                                            </div>

                                                        </div>
                                                    </div>

                                                    <!-- Add By Aklakh, start -->
                                                    <h4><b>Contact Address</b></h4>  
                                                    <div class="inner-reqdiv-elements">
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Address</label> <s:textfield   cssClass="form-control" id="conAddress" name="conAddress"     />  <!-- onkeyup="paddresValidation()" -->
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Address2</label>     <s:textfield cssClass="form-control"  id="conAddress2" name="conAddress2"/>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">City</label>     <s:textfield cssClass="form-control" id="conCity" name="conCity"  oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z\s]{3,}" /> 
                                                            </div>

                                                        </div>
                                                    </div>
                                                    <div class="inner-reqdiv-elements">
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Country</label>    <s:select cssClass="form-control SelectBoxStyles" name="conCountry" id="conCountry" headerKey="-1" headerValue="Select Country" list="countryNames" onchange="ConPermanentStateChange()" tabindex="1"/>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">State</label>   <s:select cssClass="form-control SelectBoxStyles" name="conState" id="conState" headerKey="-1" headerValue="Select  state" list="{}" listValue="getTranslation(value)"  />
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Zip</label>    <s:textfield cssClass="form-control"  id="conZip" name="conZip" minLength="4"    />
                                                            </div>
                                                        </div>
                                                    </div>


                                                    <!-- Add By Aklakh, end -->




                                                    <div class="inner-reqdiv-elements">
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <label class="addAcclabelStyle">Image</label><div><s:file cssClass="form-control" name="taskAttachment" id="taskAttachment" onchange="return fileFormatValidation();"/></div>
                                                            </div>

                                                        </div>
                                                    </div>



                                                    <%--<div class="inner-addAcc-elements ">
                                                        </div>--%>



                                                    <%--<label class="addAcclabelStyle">Departments:</label><s:textfield cssClass="addAccInputStyle" id="departments1" name="departments1" placeholder="Department" />--%>

                                                    <%-- <label class="addAcclabelStyle">Departments:</label><s:select cssClass="addAccSelectStyle " id="departments" name="departments" label="Departments" headerKey="0" headerValue="-Select-" list="departments" onchange="getTitlesForDepatments()"/>--%>

                                                    <%--<s:textfield cssClass="form-control" label="Email" value="%{empDetails.email2}" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,}" oninvalid="setCustomValidity('Must be valid email')"   onchange="try{setCustomValidity('')}catch(e){}"  tabindex="2" name="email2" id="email2" onkeyup="EmailValidation2()"/>

                                                                                                    <label class="addAcclabelStyle">Titles:</label><s:textfield cssClass="addAccInputStyle" placeholder="Title"  name="titles" id="titlesId" />--%>
                                                    <%-- <label class="addAcclabelStyle">Titles:</label><s:select cssClass="addAccSelectStyle " label="Titles" name="titles" id="titlesId" headerKey="0" headerValue="-Select-" list="{}"/>--%>


                                                    <%--    <div class="inner-addAcc-elements">


                                                                                                    <div class="checkbox-inline " >
                                                                                                        <label for="is_manager" class="checkbox  ">
                                                                                                            <s:checkbox name="isManager" id="isManager" type="checkbox"/>Is Manager</label>
                                                                                                    </div>
                                                                                                    <div class="checkbox-inline " >
                                                                                                        <label for="team_leader" class="checkbox ">
                                                                                                            <s:checkbox name="isTeamLead" id="isTeam" type="checkbox"/>Is Team Leader</label>
                                                                                                    </div>
                                                                                                </div>--%>
                                                    <%-- <div class="inner-addAcc-elements">--%>


                                                    <%--     <s:checkbox name="" id="isManager" value="%{empDetails.is_manager}"/><label>is Manager</label>
                                                         <s:checkbox name="" id="isTeam" value="%{empDetails.is_team_lead}"/><label>is TeamLeader</label> --%>
                                                    <%--</div>--%>


                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-body" id="task-panel">

                                        <div class="row">

                                            <!-- Contact Information , start  -->
                                            <%--div class="col-lg-6 col-md-offset-0">

                                                <div id="AddressBox">
                                                    <div class="contactInfoDiv">

                                                        <table>

                                                            <tr id="trStyleContact"><td>Permanent Address:  </td></tr>
                                                            <span id="spanUpdatep" class="pull-right">

                                                        </table>

                                                    </div>
                                                    <div id="margins" class="col-lg-11 col-md-offset-1 ">


                                                        <br/>
                                                        <span><errmsg></errmsg></span>
                                                        <br>
                                                        <table>
                                                            <label class="addAcclabelStyle">Address:</label> <s:textfield   cssClass="form-control" id="conAddress" name="conAddress" required="true" oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z\s]{3,}" onchange="try{setCustomValidity('')}catch(e){}"  onkeyup="paddresValidation()"  />
                                                            <label class="addAcclabelStyle">Address2:</label>     <s:textfield cssClass="form-control"  id="conAddress2" name="conAddress2"/>
                                                            <label class="addAcclabelStyle">City:</label>     <s:textfield cssClass="form-control" id="conCity" name="conCity" required="true" oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z\s]{3,}" onchange="try{setCustomValidity('')}catch(e){}" onkeyup="contactPcityValidation()" /> 
                                                            <label class="addAcclabelStyle">Zip:</label>    <s:textfield cssClass="form-control"  id="conZip" name="conZip" maxLength="5"  required="true"  />
                                                            <label class="addAcclabelStyle">Country:</label>    <s:select cssClass="form-control SelectBoxStyles" name="conCountry" id="conCountry" headerKey="" headerValue="Select Country" list="countryNames" onchange="ConPermanentStateChange()" tabindex="1"/>
                                                            <label class="addAcclabelStyle">State:</label>   <s:select cssClass="form-control SelectBoxStyles" name="conState" id="conState" headerKey="" headerValue="Select  state" list="{'Relocation','Travel'}" listValue="getTranslation(value)"  />
                                                        </table>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-lg-6 col-md-offset-0">

                                                <div id="AddressBox">
                                                    <div class="contactInfoDiv" >

                                                        <table >

                                                            <tr id="trStyleContact" ><td>&nbsp;&nbsp;Current&nbsp;Address &nbsp;&nbsp;</td></tr>
                                                            <span id="spanUpdatec" class="pull-right">
                                                        </table>

                                                    </div>
                                                    <div id="margins" class="col-lg-11 col-md-offset-1">



                                                        <table>  <s:checkbox  name="add_checkAddress"  id="add_checkAddress"  onclick="FillContactAddressAdding()"   ></s:checkbox> <label class="addAcclabelStyle">&nbsp;Same as Permanent Address </label> 

                                                            <br> <errmsgc></errmsgc>
                                                            <br> <label class="addAcclabelStyle">Address:</label> <s:textfield cssClass="form-control" id="conCAddress" name="conCAddress" required="true" oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z]{3,}"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="CaddresValidation()" />

                                                            <label class="addAcclabelStyle">Address2:</label><s:textfield cssClass="form-control" id="conCAddress2" name="conCAddress2" />
                                                            <label class="addAcclabelStyle">City:</label><s:textfield cssClass="form-control"  id="conCCity" name="conCCity" required="true" oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z]{3,}"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="ccityValidation()" />

                                                            <label class="addAcclabelStyle">Zip:</label><s:textfield cssClass="form-control" id="conCZip" name="conCZip" maxLength="5" required="true" />
                                                            <label class="addAcclabelStyle">Country:</label>  <s:select cssClass="form-control SelectBoxStyles" name="conCCountry" id="conCCountry"  headerKey="" headerValue="Select Country" list="countryNames" onchange="ConCurrentStateChange()" tabindex="1"/>
                                                            <label class="addAcclabelStyle">State:</label><s:select cssClass="form-control SelectBoxStyles" name="conCState" id="conCState" headerKey="" headerValue="Select  state" list="{'Relocation','Travel'}" listValue="getTranslation(value)"  />
                                                            </table>


                                                    </div>
                                                </div>
                                            </div--%>
                                        </div>
                                    </div>
                                </div>

                                <div class="inner-addAcc-elements">
                                    <span class="pull-right">
                                        <s:reset cssClass="cssbutton" id="button_manage"  value="Clear"/>&nbsp;
                                        <s:submit  value="Save" cssClass="cssbutton" theme="simple"></s:submit>
                                        </span>
                                    </div>
                                    <br/>




                            </s:form>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>
    <script type="text/javascript" >
        $("#conPhone").mask("(999)-999-9999");
        $("#conCPhone").mask("(999)-999-9999");
    </script>
    <script type="text/javascript" >
        $("#Officephone").mask("(999)-999-9999");
        $("#moblieNumber").mask("(999)-999-9999");
        $("#homePhone").mask("(999)-999-9999");
    </script>

    <!-- Content Close -->
</div>


<%--close of future_items--%>





<!-- content end -->
</section><!--/form-->

<footer id="footer"><!--Footer-->
    <div class="footer-bottom" id="footer_bottom">
        <div class="container">
            <s:include value="/includes/template/footer.jsp"/>
        </div>
    </div>
</footer><!--/Footer-->
</body>
</html>
