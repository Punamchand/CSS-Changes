<%--
Document   : AccountDetails
Created on : Apr 10, 2015, 2:08:50 PM
Author     : Greg
--%>

<%@page import="com.mss.msp.recruitment.ConsultantVTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <sx:head cache="true"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Miracle Service Bay :: Employee Search Page</title>

        <script language="JavaScript" type="text/javascript" src="<s:url value="/struts/dojo/struts_dojo.js"/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value="/struts/ajax/dojoRequire.js"/>" ></script>
        <%--ACCOUNT DETAILS SPECIFIC--%>
        <link rel="stylesheet" type="text/css" href="<s:url value='/includes/css/accountDetails/details.css'/>">
        <%----%>
        <link rel="stylesheet" href="<s:url value="/struts/xhtml/styles.css"/>" type="text/css">

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/requirementStyle.css"/>">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/accountDetails/projects.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/jquery.msg.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/addAccountStyles.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/demo.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/set1.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/normalize.css"/>">
        
        

        <script language="JavaScript" src="<s:url value="/struts/utils.js"/>" type="text/javascript"></script>


        <script type="text/JavaScript" src="<s:url value='/includes/js/general/dhtmlxcalendar.js'/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <%--script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script--%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>

        <%--script type="text/javascript" src="<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>"></script--%>

<!--        <script type="text/JavaScript" src="<s:url value="/includes/js/general/ProfilePage.js"/>"></script>-->
        <!-- this file for writing all profile function and  jquerys -->
        <!-- this is overlay jquery responsive and centered-->
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/vendorAjax.js"/>"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/CountriesAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/account/projectOverlays.js"/>'></script>

        <script type="text/javascript">
            // Pagination Script
            var pager;
            function pagerOption(){
                paginationSize = document.getElementById("paginationOption").value;
                pager = new Pager('projectResults', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            };

            var myCalendar;
            //,"docdatepickerfrom1","docdatepicker1"
            function doOnLoadRequirement() {
                myCalendar = new dhtmlXCalendarObject(["reqStart","reqEnd"]);
                myCalendar.setSkin('omega');
                //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
                myCalendar.setDateFormat("%m-%d-%Y");
                myCalendar.hideTime();



                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth(); //January is 0!
                var yyyy = today.getFullYear();
                if(dd<10) {
                    dd='0'+dd
                }
                if(mm<10) {
                    mm='0'+mm
                }

                var from = mm+'/'+'01'+'/'+yyyy;

                mm=today.getMonth()+1;
                if(mm<10) {
                    mm='0'+mm
                }
                dd=today.getDate()+1;
                var to = mm+'/'+dd+'/'+yyyy;
                // document.getElementById("reqStart").value=from;
                // document.getElementById("reqEnd").value=to;

            }



            function enterDateRepository()
            {
                // document.documentForm.docdatepickerfrom.value="";
                document.getElementById('reqStart').value = "";
                document.getElementById('reqEnd').value = "";
                alert("Please select from the Calender !");
                return false;
            }

        </script>
        <script type="text/javascript">
            function ajaxReplaceDiv(actionUrl,divId,data)
            {
                data= (typeof data === 'undefined') ? '{}' : data;
                $.ajax({
                    type: "POST",
                    url:CONTENXT_PATH+actionUrl,
                    data:data,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success:function(data){
                        $(divId).children().remove();
                        $(divId).html(data);
                    }
                });
            }
        </script>
        <script type="text/javascript">
            var vendorHidden = true;
            function AccountTypeDropDown(){
                if($('#account_type').val() === 'Vendor' && vendorHidden){
                    $('#vendorType').show();
                    vendorHidden = false;
                }else if($('#account_type').val() === ""){
                    if(!vendorHidden){
                        $('#No. of Employees: vendorType').hide();
                        vendorHidden = true;
                    }
                }else{
                    if(!vendorHidden){
                        $('#vendorType').hide();
                        vendorHidden = true;
                    }
                }

            }
        </script>
    </head>
    <body onload="doOnLoadRequirement(),AccountTypeDropDown();">
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
                    <div class="col-md-10 col-md-offset-0">
                        <div class="features_items">
                            <div class=" ">
                                <div class="" id="profileBox" style="float: left; margin-top: 5px">

                                    <div class="<%--backgroundcolor--%>" >
                                        <div class="row">
                                            <div class="">
                                                <div class="panel panel-info" style="margin-bottom:-8px ">
                                                    <div class="panel-body" id="panel-task-body" >
                                                        <div class="header-label" style="float: unset; margin-top:-12px; margin-bottom: 5px;background-color: darkseagreen;text-align: -webkit-center;">
                                                            <label style="margin-left: 20px;font-size: 20px;color: midnightblue;">Account Name:</label>
                                                            <s:url var="myUrl" action="viewAccount.action">
                                                                <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                                                <s:param name="accFlag">accDetails</s:param>
                                                            </s:url>
                                                            <s:a href='%{#myUrl}' style="color: #0000FF; font-size:18px;"><s:property value="%{accountDetails.name}"/></s:a>
                                                            <% String typeOfUser = "";
                                                                typeOfUser = session.getAttribute("typeOfUsr").toString();

                                                                //   if ("SA".equalsIgnoreCase(typeOfUser)) {
                                                            %>
                                                            <s:if test="#session.primaryrole == 0 || #session.primaryrole == 1" >
                                                                <span style="float: right;"><a href="searchAccountsBy.action" ><img src="<s:url value="/includes/images/backButton.png"/>" height="25" width="25"></a></span>
                                                                    </s:if>       <%--        //                                                                }
                                                                    --%> 
                                                        </div>
                                                        <div  ><headingmess id="headingmessage"  class="acc_menu_heading pull-right" style="display:block">Account Details</headingmess>    </div>
                                                        <!-- Nav tabs -->
                                                        <ul class="active_details" >
                                                            <li class="dropdown"  >
                                                                <a class=" active_details " onclick="lockScreen();" data-toggle="dropdown"  href="#" title="Employee Details"   style="background-color: #fff; width:40px;"><img src="<s:url value="/includes/images/toggleMenu.png"/>" height="40" width="38"></a>
                                                                <ul class="active_details dropdown-menu  " style="position:absolute; z-index:10000;">
                                                                    <li class="  "><a aria-expanded="false" href="#details" data-toggle="tab" id="accountdetailshead" onclick="headingMessage(this); unlockScreen();" >Account Details</a>
                                                                    </li>

                                                                    <s:if test="#session.primaryrole != 0 && #session.primaryrole != 2" >
                                                                        <s:if test="%{userType!='vendor'}">
                                                                            <li class=""><a aria-expanded="false" href="#requirements" data-toggle="tab" id="requirementshead" onclick="getSearchRequirementsList(); headingMessage(this); unlockScreen();">Requirements</a>
                                                                            </li>
                                                                        </s:if>
                                                                    </s:if>
                                                                    <li class=""><a aria-expanded="false" href="#contacts" data-toggle="tab" id="contactshead" onclick="headingMessage(this); unlockScreen();">Contacts</a>
                                                                    </li>
                                                                    <s:if test="%{userType!='vendor'}">
                                                                        <s:if test="#session.primaryrole == 0" >
                                                                            <li class=""><a aria-expanded="false" href="#subvendors" data-toggle="tab" id="vendors" onclick="getVendors();headingMessage(this); unlockScreen();">Vendors</a>
                                                                            </li>
                                                                        </s:if>
                                                                        <s:if test="#session.primaryrole == 1" >
                                                                            <li class=""><a aria-expanded="false" href="#subvendors" data-toggle="tab" id="vendors" onclick="getVendors();headingMessage(this); unlockScreen();">Vendors</a>
                                                                            </li>
                                                                        </s:if>

                                                                        <%--<%   if ("SA".equalsIgnoreCase(typeOfUser) || "E".equalsIgnoreCase(typeOfUser)) {%>
                                                                    <li class=""><a aria-expanded="false" href="#subvendors" data-toggle="tab" id="vendors" onclick="getVendors();headingMessage(this); unlockScreen();">Vendors</a>
                                                                    </li><%                                                                            }%>--%>
                                                                    </s:if>
                                                                    <%if ("SA".equalsIgnoreCase(typeOfUser)) {%>
                                                                    <s:if test="%{accountDetails.accountType != 5}">
                                                                        <li class=""><a aria-expanded="false" href="#csrAccountsRef" data-toggle="tab" id="csrAccounts" onclick="headingMessage(this); unlockScreen();">Assign CSR</a>
                                                                        </li></s:if>
                                                                    <%                                                                        }%>


                                                                </ul>
                                                            </li>
                                                        </ul>
                                                        <div id="jquery-msg-overlay" class="black-on-white " style="position:absolute; z-index:1500; width: 0%; top:71px; right:0px; left:0px; bottom: 0px;display:none" onclick="unlockScreen();">
                                                            <img src="<s:url value="/includes/images/blank.png"/>" id="jquery-msg-bg" style="width: 100%; height: 100%; top: 0px; left: 0px;"/>
                                                        </div>
                                                        <span><j><b><font color="red"></font></b></j></span>
                                                        <div class="tab-content" style="padding : 0px;margin-top: 10px">
                                                            <div class="tab-pane fade in" id="details" style="background-color: lavender;">
                                                                <br/>
                                                                <div id="editMessage" style="display: none" ><font style="color:green ;font: bold; font-size: large">Account  updated successfully!</font></div>
                                                                    <s:form action="ajaxAccountUpdate" id="accountDetailsForm" theme="simple" >
                                                                        <s:hidden name="viewAccountID" id="viewAccountID" value="%{accountSearchID}"></s:hidden>
                                                                    <div class="panel-body" id="task-panel" style="border: 3px solid darkslategray;border-radius: 25px;padding: 15px;margin-bottom: 10px;">
                                                                        <div class="row">
                                                                            <span><editAccountError></editAccountError></span>
                                                                            <div class="col-lg-12 header-label">
                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2">Account Name  </label>
                                                                                    <s:textfield type="text" 
                                                                                                 name="accountDetails.name"
                                                                                                 cssClass="form-control"
                                                                                                 id="account_name"
                                                                                                 value="%{accountDetails.name}"
                                                                                                 readonly="true"/>
                                                                                    <span id="nameError" class="accDetailsError"></span>
                                                                                </div>
                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2">Account URL  </label>
                                                                                    <s:textfield type="text"
                                                                                                 name="accountDetails.url"
                                                                                                 cssClass="form-control"
                                                                                                 id="account_url"
                                                                                                 value="%{accountDetails.url}"
                                                                                                 readonly="true"/>
                                                                                    <span id="urlError" class="accDetailsError"></span>
                                                                                </div>
                                                                                <div class="col-lg-3">
                                                                                    <s:hidden id="account_type" name="accountDetails.accountType" value="%{accountDetails.accountType}" />
                                                                                    <label style="color:#56a5ec;" class="labelStyle2">Account Type </label>
                                                                                    <br><s:select  id="account_type"
                                                                                               value="%{accountDetails.accountType}"
                                                                                               name="accountDetails.accountType"
                                                                                               cssClass="SelectBoxStyles form-control"
                                                                                               headerKey="-1"
                                                                                               headerValue="Select account Type"
                                                                                               theme="simple"
                                                                                               list="accTypes"
                                                                                               onchange="javascript: AccountTypeDropDown();" 
                                                                                               disabled="true"/>

                                                                                    <span id="typeError" class="accDetailsError"></span>
                                                                                </div>

                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2"><span style="color:red;">*</span>Status  </label>
                                                                                    <br>
                                                                                    <s:if test="#session.primaryrole == 0 || #session.primaryrole == 1">
                                                                                        <s:select  id="status"
                                                                                                   value="%{accountDetails.status}"
                                                                                                   name="accountDetails.status"
                                                                                                   cssClass="SelectBoxStyles form-control"
                                                                                                   headerKey="-1"
                                                                                                   headerValue="Status status" required="true"
                                                                                                   theme="simple"
                                                                                                   list="{'Active','In-Active'}"
                                                                                                   onchange="accStatusValidate();"/>
                                                                                    </s:if>
                                                                                    <s:else>
                                                                                        <s:select  id="status"
                                                                                                   value="%{accountDetails.status}"

                                                                                                   cssClass="SelectBoxStyles form-control"
                                                                                                   headerKey="-1"
                                                                                                   headerValue="Status status" required="true"
                                                                                                   theme="simple"
                                                                                                   list="{'Active','In-Active'}"
                                                                                                   onchange="accStatusValidate();" disabled="true"/>
                                                                                        <s:hidden name="accountDetails.status"   value="%{accountDetails.status}"/>
                                                                                    </s:else>
                                                                                    <span id="statusError" class="accDetailsError"></span>
                                                                                    <%-- IF ACCOUNT TYPE --%>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div style="border: 1px solid black;padding: 8px;">
                                                                            <div class="row header-label">
                                                                                <h5><b>Account Address</b></h5>
                                                                                <div class="col-lg-12">
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Address 1  </label>
                                                                                        <s:textfield type="text" maxLength="100"
                                                                                                     name="accountDetails.address1"
                                                                                                     cssClass="form-control"
                                                                                                     id="account_address1"
                                                                                                     value="%{accountDetails.address1}"/>
                                                                                        <span id="address1Error" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Address 2  </label>
                                                                                        <s:textfield type="text" maxLength="100"
                                                                                                     name="accountDetails.address2"
                                                                                                     cssClass="form-control"
                                                                                                     id="account_address2"
                                                                                                     value="%{accountDetails.address2}"/>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2"><%--<span style="color:red;">*</span>--%>City  </label>
                                                                                        <s:textfield id="account_city" maxLength="20"
                                                                                                     name="accountDetails.city"
                                                                                                     type="text"
                                                                                                     cssClass="form-control"
                                                                                                     required="true"
                                                                                                     value="%{accountDetails.city}" 
                                                                                                     onkeyup="accCityValidate();"
                                                                                                     />

                                                                                        <span id="cityError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Zip  </label>
                                                                                        <s:textfield id="account_zip" maxLength="11"
                                                                                                     name="accountDetails.zip"
                                                                                                     type="text"
                                                                                                     cssClass="form-control"
                                                                                                     value="%{accountDetails.zip}"/>

                                                                                        <span id="zipError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="row">
                                                                            <div class="col-lg-12 header-label">
                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2"><%--<span style="color:red;">*</span>--%>Country  </label>
                                                                                    <br><s:select   id="account_country"
                                                                                                value="accountDetails.country"
                                                                                                name="accountDetails.country"
                                                                                                cssClass="SelectBoxStyles form-control"
                                                                                                headerKey="-1"
                                                                                                headerValue="Select Country"
                                                                                                theme="simple"
                                                                                                list="countries"
                                                                                                onchange="javascript: getStates($('#account_country').val(),'#account_state');getStockSymbol($('#account_country').val()); accCountryValidate();"
                                                                                                />
                                                                                    <span id="countryError" class="countryError"></span>
                                                                                </div>
                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2"><%--<span style="color:red;">*</span>--%>State  </label>
                                                                                    <br><s:select   id="account_state"
                                                                                                value="%{accountDetails.state}"
                                                                                                name="accountDetails.state"
                                                                                                cssClass="SelectBoxStyles form-control"
                                                                                                headerKey="-1"
                                                                                                headerValue="Select State"
                                                                                                theme="simple"
                                                                                                list="states"
                                                                                                onchange="accStateValidate();"/>
                                                                                    <span id="stateError" class="accDetailsError"></span>
                                                                                </div>
                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2"><%--<span style="color:red;">*</span>--%>Phone  </label>
                                                                                    <s:textfield  id="phone1" 
                                                                                                  cssClass="form-control"
                                                                                                  name="accountDetails.phone"
                                                                                                  type="text"
                                                                                                  value="%{accountDetails.phone}"
                                                                                                  onkeyup="accPhoneValidate();"/>
                                                                                    <span id="phoneError" class="accDetailsError"></span>
                                                                                </div>
                                                                                <div class="col-lg-3">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2">Fax  </label>
                                                                                    <s:textfield  id="fax" maxLength="15"
                                                                                                  cssClass="form-control"
                                                                                                  name="accountDetails.fax"
                                                                                                  type="text"
                                                                                                  value="%{accountDetails.fax}"
                                                                                                  onkeypress="return faxValidate(event)"
                                                                                                  />

                                                                                    <span id="faxError" class="accDetailsError"></span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        </div>
                                                                                    
                                                                        <div style="border: 1px solid black;padding: 8px; margin-top: 10px">
                                                                            <div class="row header-label">
                                                                                <h5><b>Basic Information</b></h5>
                                                                                <div class="col-lg-12">
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2"><%--<span style="color:red;">*</span>--%>Industry </label>
                                                                                        <br><s:select   id="account_industry"
                                                                                                    name="accountDetails.industry"
                                                                                                    cssClass="SelectBoxStyles form-control"
                                                                                                    headerKey="-1"
                                                                                                    headerValue="Select an industry"
                                                                                                    type="text"
                                                                                                    value="%{accountDetails.industry}"
                                                                                                    list="industries"
                                                                                                    onchange="accIndustryValidate()"/>
                                                                                        <span id="industryError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Region  </label>
                                                                                        <s:textfield id="account_region" maxLength="20"
                                                                                                     name="accountDetails.region"
                                                                                                     type="text"
                                                                                                     cssClass="form-control"
                                                                                                     value="%{accountDetails.region}"
                                                                                                     onkeyup="regionValidate();"/>
                                                                                        <span id="regionError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Territory  </label>
                                                                                        <s:textfield id="account_territory" maxLength="20"
                                                                                                     name="accountDetails.territory"
                                                                                                     type="text"
                                                                                                     cssClass="form-control"
                                                                                                     value="%{accountDetails.territory}"
                                                                                                     onkeyup="territoryValidate();"/>
                                                                                        <span id="territoryError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">No. of Employees  </label>
                                                                                        <s:textfield id="account_noemp" maxLength="11"
                                                                                                     name="accountDetails.noemp"
                                                                                                     cssClass="form-control"
                                                                                                     type="text"
                                                                                                     value="%{accountDetails.noemp}"
                                                                                                     onkeyup="accNoOfEmpValidate();"/>
                                                                                        <span id="noempError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="row">
                                                                                <div class="col-lg-12 header-label">
                                                                                    <%-- <div class="col-lg-3">
                                                                                         <label style="color:#56a5ec;" class="labelStyle2">Budget  </label>
                                                                                         <s:textfield id="account_budget"
                                                                                                      name="accountDetails.budget"
                                                                                                      cssClass="form-control"
                                                                                                      type="text"
                                                                                                      value="%{accountDetails.budget}"/>
                                                                                         <span id="budgetError" class="accDetailsError"></span>
                                                                                     </div> --%>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Tax ID  </label>
                                                                                        <s:textfield id="account_taxid" maxLength="20"
                                                                                                     name="accountDetails.taxId"
                                                                                                     cssClass="form-control"
                                                                                                     type="text"
                                                                                                     value="%{accountDetails.taxId}"
                                                                                                     onkeypress="return taxValidate(event)"/>
                                                                                        <span id="taxError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Stock Symbol  </label>
                                                                                        <%--------------------CURRENCY TYPE--------------------%>
                                                                                        <s:textfield id="stock_symbol"
                                                                                                     name="accountDetails.stockSymbol"
                                                                                                     cssClass="form-control"
                                                                                                     type="text"
                                                                                                     value="%{accountDetails.stockSymbol}"
                                                                                                     readonly="true"/>
                                                                                        <span id="stockError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                    <div class="col-lg-3">
                                                                                        <label style="color:#56a5ec;" class="labelStyle2">Revenue  </label>
                                                                                        <s:textfield id="account_revenue" maxLength="11"
                                                                                                     cssClass="form-control"
                                                                                                     name="accountDetails.revenue"
                                                                                                     type="text"
                                                                                                     value="%{accountDetails.revenue}"
                                                                                                     onkeypress="return revenueValidate(event)"/>
                                                                                        <span id="revenueError" class="accDetailsError"></span>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="row">
                                                                            <div class="col-lg-12">
                                                                                <div class="col-lg-12 header-label">
                                                                                    <label style="color:#56a5ec;" class="labelStyle2">Description  </label>
                                                                                    <s:textarea id="account_description"
                                                                                                name="accountDetails.description"
                                                                                                cssClass="form-control"
                                                                                                cssStyle="height:4em;"
                                                                                                type="text"
                                                                                                maxlength="200"
                                                                                                value="%{accountDetails.description}"
                                                                                                onkeydown="ResponseCheckCharacters('#account_description')"/>
                                                                                    <span id="ResponsecharNum" class="charNum"></span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                                    
                                                                         </div>
                                                                        <div id="loadingAccount" class="loadingImg" style="display: none">
                                                                            <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>
                                                                        </div>

                                                                        <div class="row">


                                                                            <div class="col-lg-12" >
                                                                                <s:if test="#session.primaryrole == 0 ||#session.primaryrole == 1 ||#session.primaryrole == 2 ||#session.primaryrole == 8 " >
                                                                                    <a id="detailsFormSubmit"  style="height:37px;" href="#save" class="active_details cssbutton_emps pull-right"><div class="" >SAVE</div></a>
                                                                                </s:if>

                                                                                <%--    <a id="detailsFormSubmit" href="#save" class="active_details cssbutton_emps pull-right"><div class="details_button" >SAVE</div></a> --%>
                                                                            </div>
                                                                            <script>
                                                                                $('#detailsFormSubmit').on('click',function(){
                                                                                    document.getElementById('editMessage').style.display="none";
                                                                                    var url="ajaxAccountUpdate";
                                                                                    var validateAccount=true; //editAccountValidation();
                                                                                    if(validateAccount){
                                                                                        document.getElementById('loadingAccount').style.display="block";
                                                                                        $.ajax({
                                                                                            type: "POST",
                                                                                            url: url,
                                                                                            data: $("#accountDetailsForm").serialize(), // serializes the form's elements.
                                                                                            success: function(data)
                                                                                            {
                                                                                                document.getElementById('loadingAccount').style.display="none";
                                                                                                document.getElementById('editMessage').style.display="block";
                                                                                                $("#editMessage").fadeOut(5000);
                                                                                                // location.reload();
                                                                                            }

                                                                                        });
                                                                                    }
                                                                                });
                                                                            </script>

                                                                        </div>

                                                                    </div>

                                                                </s:form>
                                                            </div>
                                                            <div class="tab-pane fade in " id="softwares">
                                                                <div class="panel-body"  >
                                                                    <div class="row">
                                                                        <div class="col-lg-12">
                                                                            <div class="row">
                                                                                <div id="softwareDiv"  >

                                                                                </div>
                                                                                <div style="float: right;">
                                                                                    <s:if test="%{roleId==1}">
                                                                                        <a href="#save" id="checkListButton" onclick="javascript: saveSoftwaresAjax();">
                                                                                            <div class="details_button">UPDATE</div></a>
                                                                                        </s:if>
                                                                                        <s:elseif  test="%{userSessionId== primaryAccount}">
                                                                                        <a href="#save" id="checkListButton" onclick="javascript: saveSoftwaresAjax();">
                                                                                            <div class="details_button">UPDATE</div></a>

                                                                                    </s:elseif>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>


                                                            <div class="tab-pane fade in " id="team">
                                                                <div class="panel-body" id="task-panel" >
                                                                    <div class="row">
                                                                        <div class="col-lg-12">
                                                                            <s:form action="updateAccTeam" theme="simple">
                                                                                <label for="leftTitle"  id="primaryAssign1">Primary Assign</label><label  id="primaryAssign2"> : </label>
                                                                                <s:select   id="accountindustry"
                                                                                            name="primaryAccount"
                                                                                            cssClass="selectstyle"
                                                                                            headerKey="-1"
                                                                                            headerValue="Select an Account Team"
                                                                                            type="text"
                                                                                            value="%{primaryAccount}"
                                                                                            list="allAccTeam"/>
                                                                                <div class="form-controls">
                                                                                    <label for="leftTitle" style="margin-left: 5px">
                                                                                        Assign team&nbsp;:
                                                                                    </label>
                                                                                </div>
                                                                                <div class="row " style="margin-left: 5px ;width: auto " >

                                                                                    <div style="margin-left: 0px ;overflow-x: auto">
                                                                                        <s:hidden name="accountSearchID" id="accountSearchID"  value="%{accountSearchID}"></s:hidden>
                                                                                        <s:optiontransferselect
                                                                                            label="User Roles"
                                                                                            name="accTeam"
                                                                                            leftTitle="Avilable Members"
                                                                                            rightTitle="Added Members"
                                                                                            list="accTeamList"
                                                                                            headerKey="headerKey"
                                                                                            cssStyle="width:150px;height:235px"
                                                                                            cssClass="form-control"
                                                                                            doubleName="accSalesTeam"
                                                                                            doubleList="availAccTeamList"
                                                                                            doubleValue="%{primaryAccount}"
                                                                                            doubleHeaderKey="doubleHeaderKey"
                                                                                            buttonCssStyle="width:50px;height:25px;"
                                                                                            doubleCssStyle="width:150px;height:235px"
                                                                                            doubleCssClass="form-control"
                                                                                            />

                                                                                    </div>
                                                                                    <%--<div class="pull-right submitDiv">
                                                                                    <a href="#" ><input type="button" class="cssbutton " value="Save" onclick="updateVendorSales();"></a>
                                                                                </div>
                                                                                    <s:submit cssClass="pull-right btn cssbutton"  value="Update" /> --%>
                                                                                    <s:if test="%{roleId==1}">
                                                                                        <s:submit cssClass="pull-right btn cssbutton"  value="Update" />
                                                                                    </s:if>
                                                                                    <s:elseif  test="%{userSessionId== primaryAccount}">
                                                                                        <s:submit cssClass="pull-right btn cssbutton"  value="Update" />
                                                                                    </s:elseif>

                                                                                </div>

                                                                            </s:form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <!----here we end -->
                                                            <div class="tab-pane fade in" id="contacts">
                                                                <%-- <s:include value="/acc/AccountContacts.jsp"/>--%>
                                                                <div class="row">
                                                                    <%-- <s:form action="getContactDetails" method="get" theme="simple" > --%>
                                                                    <div class="col-sm-12">

                                                                        <div class="inner-reqdiv-elements">
                                                                            <div class="row">
                                                                                <div class="col-lg-4">
                                                                                    <s:hidden name="accFlag" id="accFlag" value="%{accFlag}"/>
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">First Name: </label>
                                                                                    <s:textfield id="firstNameContacts"
                                                                                                 cssClass="form-control"
                                                                                                 type="text"
                                                                                                 name="firstName"
                                                                                                 placeholder="First Name"/> 
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Last Name: </label>
                                                                                    <s:textfield id="lastNameContacts"
                                                                                                 name="lastName"
                                                                                                 cssClass="form-control"
                                                                                                 theme="simple"
                                                                                                 type="text"
                                                                                                 placeholder="Last Name" />
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Email Id: </label>
                                                                                    <s:textfield id="emailContacts"
                                                                                                 name="email"
                                                                                                 cssClass="form-control"
                                                                                                 theme="simple"
                                                                                                 type="text"
                                                                                                 placeholder="Email" />
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                        <div class="inner-reqdiv-elements">
                                                                            <div class="row">
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Phone: </label> 
                                                                                    <s:textfield id="x`Contacts"
                                                                                                 cssClass="form-control"
                                                                                                 name="phone"
                                                                                                 type="text"
                                                                                                 placeholder="Phone #" />
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Status: </label>
                                                                                    <s:select id="statusContacts"
                                                                                              cssClass="SelectBoxStyles form-control"
                                                                                              name="status"
                                                                                              list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}"
                                                                                              headerKey="DF"

                                                                                              placeholder="Status" />
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <div class="row">
                                                                                        <div class="col-lg-6">
                                                                                            <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                                                            <s:submit type="submit" id=""
                                                                                                      cssClass="cssbutton_emps form-control"
                                                                                                      value="Search" onclick="getContactSearchResults()" cssStyle="margin:5px"/>
                                                                                        </div>
                                                                                        <div class="col-lg-6">
                                                                                            <label class="labelStylereq" style="color:#56a5ec;"></label> 
                                                                                            <%--<s:submit id="" cssClass="Contact_popup_open cssbutton_emps form-control"  onclick="removeDataAfterContactOverlay()"  onfocus="clearContactOverlay()" value="Add Contact" style="margin:5px"/>--%>
                                                                                            <a href='setContacts.action?accountType=<s:property value="%{accountDetails.accountType}"/>&accountSearchID=<s:property value="%{accountSearchID}"/>'><input type="button" class="cssbutton" value="Add Contact" style="float: right"></a>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                        <span id="validationMessage" />

                                                                    </div>
                                                                    <%-- </s:form>--%>
                                                                    <!-- for grid-->
                                                                    <%-- <div class="col-sm-4"><div id="outputMessage"></div></div>--%>
                                                                    <div class="col-sm-12">
                                                                        <s:form>

                                                                            <table id="contactPageNav" class="responsive CSSTable_task" border="5"cell-spacing="2">

                                                                                <tbody>
                                                                                    <tr>
                                                                                        <th>Name</th>
                                                                                        <%--<th>Title</th>--%>
                                                                                        <th>E-mail</th>
                                                                                        <th>Phone</th>
                                                                                        <th>Status</th>
                                                                                        <th>Assign Roles</th>
                                                                                        <th>Login</th>
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>
                                                                            <br/>
                                                                            <div id="loadingContact" class="loadingImg" style="display: none">
                                                                                <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>
                                                                            </div>
                                                                            <div id="contactPageNavPosition" align="right" style="margin-right:0vw"></div>
                                                                            <div style="width:auto;height:auto" >
                                                                                <div  id="editSpan" class="badge pull-right" style="display:none"></div>
                                                                            </div>

                                                                        </s:form>

                                                                        <%-- account overlay --%>


                                                                        <%-- account overlay ends --%>


                                                                        <script type="text/javascript" >
                                                                            $("#conPhone").mask("(999)-999-9999");
                                                                            $("#homePhone").mask("(999)-999-9999");
                                                                        </script>
                                                                        <script type="text/javascript" >
                                                                            $("#Officephone").mask("(999)-999-9999");
                                                                        </script>

                                                                        <script type="text/javascript">

                                                                            var acPager = new Pager('contactPageNav', 10);
                                                                            acPager.init();
                                                                            acPager.showPageNav('acPager', 'contactPageNavPosition');
                                                                            acPager.showPage(1);


                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <%-- Vendor Association byRK code End--%>

                                                            <%-- Requirement Tab start by Aklakh--%>        
                                                            <div class="tab-pane fade in " id="requirements">
                                                                <div class="row">
                                                                    <div class="col-sm-12">
                                                                        <s:hidden name="vendor" id="vendor" value="%{vendor}" ></s:hidden>
                                                                        <s:hidden name="accountFlag" id="accountFlag" value="%{accountFlag}" ></s:hidden>
                                                                        <%--div class="row">
                                                                            <div class="col-sm-4">
                                                                                <label class="labelStylereq  field-margincontact" style="color:#56a5ec;">Req Title:</label>
                                                                            <s:textfield cssClass="textbox " name="jobTitle" id="jobTitle" placeholder="Job Title"/>
                                                                        </div>
                                                                        <div class="col-sm-4">
                                                                            <label class="labelStylereq field-margincontact" style="color:#56a5ec;">Skills:</label>
                                                                            <s:textfield cssClass="textbox  " name="requirementSkill" id="requirementSkill" placeholder="Skill"/>
                                                                        </div>
                                                                        <div class="col-sm-4">
                                                                            <s:if test="vendor=='yes'">
                                                                                <label class="labelStylereq field-margincontact" style="color:#56a5ec;">Status:</label>
                                                                                <s:select id="requirementStatus" name="requirementStatus" cssClass="selectBoxStyle " headerKey="-1" headerValue="requirementStatus" theme="simple" list="#@java.util.LinkedHashMap@{'R':'Released','C':'Closed'}" />
                                                                            </s:if>
                                                                            <s:else>
                                                                                <label class="labelStylereq field-margincontact" style="color:#56a5ec;">Status:</label>
                                                                                <s:select id="requirementStatus" name="requirementStatus" cssClass="selectBoxStyle " headerKey="-1" headerValue="requirementStatus" theme="simple" list="#@java.util.LinkedHashMap@{'O':'Created','R':'Released','C':'Closed'}" />
                                                                            </s:else>
                                                                        </div>
                                                                    </div--%>
                                                                        <div class="inner-reqdiv-elements">
                                                                            <div class="row">
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec">Job Title:</label>
                                                                                    <s:textfield cssClass="form-control " name="jobTitle" id="jobTitle" placeholder="Job Title"/>
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec">Skills:</label>
                                                                                    <s:textfield cssClass="form-control  " name="requirementSkill" id="requirementSkill" placeholder="Skill"/>
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <s:if test="vendor=='yes'">
                                                                                        <label class="labelStylereq" style="color:#56a5ec">Status:</label>
                                                                                        <s:select id="requirementStatus" name="requirementStatus" cssClass="SelectBoxStyles form-control " headerKey="-1" headerValue="requirementStatus" theme="simple" list="#@java.util.LinkedHashMap@{'R':'Released','C':'Closed'}" />
                                                                                    </s:if>
                                                                                    <s:else>
                                                                                        <label class="labelStylereq" style="color:#56a5ec">Status:</label>
                                                                                        <s:select id="requirementStatus" name="requirementStatus" cssClass="SelectBoxStyles form-control " headerKey="-1" headerValue="requirementStatus" theme="simple" list="#@java.util.LinkedHashMap@{'O':'Opened','R':'Released','C':'Closed'}" />
                                                                                    </s:else>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                        <%--div class="row"> 
                                                                            <div class="col-sm-4">

                                                                                <label class="labelStylereq field-margincontact" style="color:#56a5ec;">StartDate:</label>  
                                                                                <s:textfield cssClass="  calendarImage " name="reqStart" id="reqStart" placeholder="FromDate"  tabindex="1"  onkeypress="return enterDateRepositoryReq();" />
                                                                            </div>
                                                                            <div class="col-sm-4">
                                                                                <label class="labelStylereq field-margincontact"  style="color:#56a5ec;">EndDate:</label>
                                                                                <s:textfield cssClass="calendarImage    " name="reqEnd" placeholder="ToDate"  id="reqEnd" tabindex="2"  onkeypress="return enterDateRepositoryReq();"/>
                                                                            </div>
                                                                            <div>
                                                                                <s:submit type="button" cssClass="cssbutton_emps field-marginRequirements" value="Search" onclick="return getSearchRequirementsList()"/>
                                                                            </div>
                                                                        </div--%>

                                                                        <div class="inner-reqdiv-elements">
                                                                            <div class="row">
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">StartDate:</label>
                                                                                    <s:textfield cssClass="form-control dateImage" name="reqStart" id="reqStart" placeholder="FromDate"  tabindex="1"  onkeypress="return enterDateRepositoryReq();" />
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">EndDate:</label>
                                                                                    <s:textfield cssClass="form-control dateImage" name="reqEnd" placeholder="ToDate"  id="reqEnd" tabindex="2"  onkeypress="return enterDateRepositoryReq();"/>
                                                                                </div>
                                                                                <div class="col-lg-2">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                                                    <s:submit type="button" cssClass="cssbutton_emps form-control" value="Search" onclick="return getSearchRequirementsList()" cssStyle="margin:5px;"/>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-sm-12" style="align:center">
                                                                        <span><releaseMessage></releaseMessage></span>
                                                                        <s:form>


                                                                            <table id="reqTableInAccount" class="responsive CSSTable_task" border="5"cell-spacing="2">
                                                                                <tbody>
                                                                                    <tr>
                                                                                        <th>Job Id</th>
                                                                                        <th>Job Title</th>
                                                                                        <th>Positions</th>
                                                                                        <th>Skill set</th>
                                                                                        <th>Pre Skills</th>
                                                                                        <th>Recruiter1</th>
                                                                                        <th>Recruiter2</th>
                                                                                        <th>Status</th>
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>
                                                                            <br/>
                                                                            <div id="loadingReq" class="loadingImg" style="display: none">
                                                                                <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>
                                                                            </div>
                                                                            <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
                                                                                    <option>10</option>
                                                                                    <option>15</option>
                                                                                    <option>25</option>
                                                                                    <option>50</option>
                                                                                </select>
                                                                                Accounts per page
                                                                            </label>
                                                                            <div align="right" id="reqPageNavPosition" style="margin-right: 0vw;"></div>

                                                                            <script type="text/javascript">
                                                                                var reqPag = new Pager('reqTableInAccount', 10); 
                                                                                reqPag.init(); 
                                                                                reqPag.showPageNav('reqPag', 'reqPageNavPosition'); 
                                                                                reqPag.showPage(1);
                                                                            </script>

                                                                        </s:form>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <%-- Requirement Tab end by Aklakh--%>  

                                                            <%-- csr ACCOUNT Tab start by PRAVEEN--%>        
                                                            <div class="tab-pane fade in " id="csrAccountsRef">
                                                                <div class="row">
                                                                    <div class="col-sm-12">
                                                                        <s:hidden name="viewAccountID" id="viewAccountID" value="%{accountSearchID}"></s:hidden>
                                                                        <s:hidden name="accountFlag" id="accountFlag" value="%{accountFlag}" ></s:hidden>
                                                                        <div class="inner-reqdiv-elements">
                                                                            <div class="row">
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec">CSR:</label>
                                                                                    <s:textfield cssClass="form-control " name="Name" id="csrName" />
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <label class="labelStylereq" style="color:#56a5ec">Email:</label>
                                                                                    <s:textfield cssClass="form-control  " name="Email" id="csrEmail" />
                                                                                </div>
                                                                                <div class="col-lg-4">

                                                                                    <label class="labelStylereq" style="color:#56a5ec">Phone:</label>
                                                                                    <s:textfield cssClass="form-control  " name="csrPhone" id="csrPhone"/>
                                                                                </div>
                                                                                <div class="col-lg-4">

                                                                                    <label class="labelStylereq" style="color:#56a5ec">Status:</label>
                                                                                    <s:select id="csrStatus" name="csrStatus" cssClass="SelectBoxStyles form-control " value="Active"  theme="simple" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}" />
                                                                                </div>
                                                                                <div class="col-lg-4">
                                                                                    <s:url var="csrUrl" action="../acc/goAddintAccToCsr.action">
                                                                                        <s:param name="orgUserId"><s:property value="accountSearchID"/></s:param>
                                                                                    </s:url>
                                                                                    <s:a href='%{#csrUrl}' style="color: #0000FF;"><s:submit value="Add" cssClass="cssbutton_emps"/></s:a>
                                                                                </div>

                                                                                <s:submit value="Search" cssClass="cssbutton_emps pull-left" onclick="getCsrDetailsTable()"/>
                                                                            </div>
                                                                        </div>
                                                                        <br>
                                                                    </div>

                                                                    <div class="col-sm-12" style="align:center">
                                                                        <span><releaseMessage></releaseMessage></span>

                                                                        <s:form>
                                                                            <table id="csrDetailsTable" class="responsive CSSTable_task" border="5"cell-spacing="2">
                                                                                <tbody>
                                                                                    <tr>
                                                                                        <th>CSR</th>
                                                                                        <th>Email</th>
                                                                                        <th>Phone</th>
                                                                                        <th>Created By</th>
                                                                                        <th>Created&nbsp;Date</th>
                                                                                        <th>Status</th>
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>
                                                                            <br/>
                                                                            <div id="loadingReq" class="loadingImg" style="display: none">
                                                                                <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>
                                                                            </div>
                                                                            <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
                                                                                    <option>10</option>
                                                                                    <option>15</option>
                                                                                    <option>25</option>
                                                                                    <option>50</option>
                                                                                </select>
                                                                                Accounts per page
                                                                            </label>
                                                                            <div align="right" id="csrDetailsTablepageNavi" style="margin-right: 0vw;"></div>

                                                                            <script type="text/javascript">
                                                                                var reqPager = new Pager('csrDetailsTable', 10); 
                                                                                reqPager.init(); 
                                                                                reqPager.showPageNav('reqPager', 'csrDetailsTablepageNavi'); 
                                                                                reqPager.showPage(1);
                                                                            </script>

                                                                        </s:form>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <%-- Csr account end over here--%>  

                                                            <%-- Vendor Association byRK code Start--%>
                                                            <div class="tab-pane fade in " id="subvendors">

                                                                <!-- content start -->
                                                                <div class="panel-body"  >
                                                                    <div class="row">
                                                                        <div class="col-sm-12">
                                                                            <div id="addVendorTier_popup">
                                                                                <div id="AddVendorTierOverlay" >
                                                                                    <div style="background-color: #3bb9ff ; padding: 0px">
                                                                                        <table>
                                                                                            <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Add Vendor Tier&nbsp;&nbsp; </font></h4></td>
                                                                                            </tr>
                                                                                            <span class=" pull-right"><h5><a href="" class="addVendorTier_popup_close" onclick="addVendorTierOverlay();" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div id="VendorTierDiv">
                                                                                        <span><e></e></span>
                                                                                        <div class="innerAddConElements">
                                                                                            <label class="labelStyleAddCon">Type Of Tier</label><s:select id="vendorTier" cssClass="reqSelectStyle" name="vendorTier" list="addVendorTierMap"  />
                                                                                        </div>
                                                                                        <div class="">
                                                                                            <s:submit id="addVendorTier" cssClass="cssbutton" value="Add" onclick="addVendorTierType();" />
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div id="editVendorTier_popup">
                                                                                <div id="EditVendorTierOverlay" >
                                                                                    <div style="background-color: #3bb9ff ; padding: 0px">
                                                                                        <table>
                                                                                            <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Edit Vendor Tier Details&nbsp;&nbsp; </font></h4></td>
                                                                                            </tr>
                                                                                            <span class=" pull-right"><h5><a href="" class="editVendorTier_popup_close" onclick="editVendorTierOverlayClose();" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div id="VendorTierDiv">
                                                                                        <span><e1></e1></span>
                                                                                        <div class="innerAddConElements">
                                                                                            <s:hidden id="tierId" name="tierId"/>
                                                                                            <label class="labelStyleAddCon">Status</label><s:select id="vendorTierStatus" cssClass="SelectBoxStyles form-control" name="vendorTierStatus" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}"  />

                                                                                        </div>
                                                                                        <div class="innerAddConElements">
                                                                                            <label class="labelStyleAddCon">Tier:</label><s:select cssClass="SelectBoxStyles form-control" name="vendorTier" id="vendorTier" headerKey="0" headerValue="--select--" list="vendorTierMap"/>
                                                                                        </div>
                                                                                        <div class="innerAddConElements">
                                                                                            <label class="labelStyleAddCon">Head Hunter:</label><s:checkbox name="PF" id="PF" />
                                                                                        </div>
                                                                                        <div class="">
                                                                                            <s:submit id="editVendorTierStatus"  cssClass="cssbutton" value="Save" onclick="editVendorTierDetails();" />
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <%--div class="inner-reqdiv-elements">
                                                                                <label class="vendorTierlabelStyle" style="color:#56a5ec;">Type Of Tier:</label>
                                                                                <s:select id="vendorTierType" name="vendorTierType" cssClass="reqSelectStyle" headerKey="-1" headerValue="All" theme="simple" list="vendorTierMap" />
                                                                                <label class="" style="color:#56a5ec;">Status:</label>
                                                                                <s:select id="TierStatus" name="TierStatus" cssClass="reqSelectStyle" headerKey="-1" headerValue="All"  theme="simple" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}" />
                                                                                <input type="button" value="Search" class="cssbutton" onclick="searchVendorTier();"/>
                                                                                <input type="button" class="addVendorTier_popup_open cssbutton" value="Add" onclick="addVendorTierOverlay();" onfocus="clearAddOverlay()"/>
                                                                            </div--%>
                                                                            <div class="inner-reqdiv-elements">
                                                                                <div class="row">
                                                                                    <div class="col-lg-4">
                                                                                        <label class="labelStyle" style="color:#56a5ec">Type of Tier:</label>
                                                                                        <s:select id="vendorTierType" name="vendorTierType" cssClass="SelectBoxStyles form-control" headerKey="-1" headerValue="All" theme="simple" list="vendorTierMap" />
                                                                                    </div>
                                                                                    <div class="col-lg-4">
                                                                                        <label class="labelStylereq" style="color:#56a5ec">Status:</label>
                                                                                        <s:select id="TierStatus" name="TierStatus" cssClass="SelectBoxStyles form-control" headerKey="-1" headerValue="All"  theme="simple" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}" />
                                                                                    </div>
                                                                                    <div class="col-lg-4">
                                                                                        <div class="row">
                                                                                            <div class="col-lg-6">
                                                                                                <label class="labelStylereq" style="color:#56a5ec"></label>
                                                                                                <s:submit type="button" value="Search" cssClass="cssbutton_emps form-control" onclick="searchVendorTier()" cssStyle="margin:2px"/>
                                                                                            </div>
                                                                                            <div class="col-lg-6">
                                                                                                <label class="labelStylereq" style="color:#56a5ec"></label>
                                                                                                <s:form action="addVendorForCustomer" theme="simple">
                                                                                                    <s:hidden name="accountSearchID" id="accountSearchID"/>
                                                                                                    <s:submit type="button" cssClass="cssbutton_emps form-control" value="Add" cssStyle="margin:2px"/>
                                                                                                </s:form>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>

                                                                            <s:form>
                                                                                <div class="task_content" id="task_div" align="center" style="display: none" >

                                                                                    <div>
                                                                                        <div>
                                                                                            <table id="vendorTierTable" class="responsive CSSTable_task" border="5"cell-spacing="2">
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <th>Vendor</th>
                                                                                                        <th>Tier</th>
                                                                                                        <th>Created Date</th>
                                                                                                        <th>is_HeadHunter</th>
                                                                                                        <th>Status</th>
                                                                                                    </tr>
                                                                                                </tbody>
                                                                                            </table>
                                                                                            <br/>
                                                                                            <div id="loadingVendor" class="loadingImg">
                                                                                                <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader1.gif"/>"   ></span>   ></span>
                                                                                            </div>
                                                                                            <div align="right" id="vendorTierTablepageNavPosition" style="margin-right: 0vw;"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </s:form>
                                                                            <script type="text/javascript">
                                                                                var pager = new Pager('vendorTierTable', 10);
                                                                                pager.init();
                                                                                pager.showPageNav('pager', 'vendorTierTablepageNavPosition');
                                                                                pager.showPage(1);


                                                                            </script>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>






                                                            <%--<div class="tab-pane fade in " id="activities">
                                                                <div class="panel-body"  >
                                                                    <div class="row">
                                                                        <div class="col-lg-12">
                                                                            <div class="row">
                                                                                <div id="projectsDiv"  >
                                                                                    acts
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="tab-pane fade in " id="projects">
                                                                <div class="panel-body"  >
                                                                    <div class="row">
                                                                        <div class="col-lg-12">
                                                                            <div class="row">
                                                                                <div id="projectsDiv"  >
                                                                                    Loading Projects
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <!-- start of oppurtunities content -->
                                                            <div class="tab-pane fade in " id="opportunities">
                                                                <div class="panel-body"  >
                                                                    <div class="row">
                                                                        <div class="col-lg-12">
                                                                            <div class="row">
                                                                                <div id="projectsDiv"  >
                                                                                    ops
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <!-- End of opportunities content -->
                                                            <!-- start of requirements content -->
                                                            <div class="tab-pane fade in " id="requirements">

                                                                <div class="panel-body"  >
                                                                    <div class="row">
                                                                        <div class="col-sm-12">
                                                                            <div id="skillShow_popup">
                                                                                <div id="skillShowBoxOverlay" >
                                                                                    <div style="background-color: #3bb9ff ; padding: 0px">
                                                                                        <table>
                                                                                            <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Skill Details&nbsp;&nbsp; </font></h4></td>
                                                                                            </tr>
                                                                                            <span class=" pull-right"><h5><a href="" class="skillShow_popup_close" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div>
                                                                                        <form action="#" theme="simple" >
                                                                                            <div>
                                                                                                <div class="inner-reqdiv-elements">
                                                                                                    <table>
                                                                                                        <span><error></error></span>
                                                                                                        <s:textarea name="skillDetails"  label="skillDetails" id="skillDetails"  style="background-color:white;color:black;border:solid 1px #B0B0B0 ;" disabled="true" cssClass="form-control"/>
                                                                                                    </table>
                                                                                                </div>
                                                                                            </div>
                                                                                        </form>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div id="addConsultant_popup">
                                                                                <div id="skillShowBoxOverlay" >
                                                                                    <div style="background-color: #3bb9ff ; padding: 0px">
                                                                                        <table>
                                                                                            <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Add Consultant Details&nbsp;&nbsp; </font></h4></td>
                                                                                            </tr>
                                                                                            <span class=" pull-right"><h5><a href="" class="addConsultant_popup_close" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div>
                                                                                        <s:form action="" theme="simple" >
                                                                                            <div id="mainAddConDiv">
                                                                                                <span><e1></e1></span>
                                                                                                <div class="innerAddConElements">
                                                                                                    <s:hidden name="conId" id="conId" ></s:hidden>
                                                                                                    <s:hidden name="reqId" id="reqId" ></s:hidden>
                                                                                                    <label class="labelStyleAddCon">Email:</label><s:textfield name="email" id="conEmail" theme="simple" cssClass="addConInputStyle" onblur="getEmailExistance();"/>
                                                                                                </div>
                                                                                                <div class="innerAddConElements">
                                                                                                    <label class="labelStyleAddCon">ProofType</label><s:select id="proofType" cssClass="reqSelectStyle" name="proofType" list="#@java.util.LinkedHashMap@{'PN':'PAN','PP':'Passport'}" onchange="setPPorPAN(this.value);" />
                                                                                                </div>
                                                                                                <div>
                                                                                                    <div class="innerAddConElements" id="ppId">
                                                                                                        <label class="labelStyleAddCon">PassPortNo:</label><s:textfield name="ppno" id="ppno" theme="simple" cssClass="addConInputStyle"/>*
                                                                                                    </div>
                                                                                                    <div class="innerAddConElements" id="panId">
                                                                                                        <label class="labelStyleAddCon">Pan no:</label><s:textfield name="pan" id="pan" theme="simple" cssClass="addConInputStyle"/>*
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="">
                                                                                                    <s:submit id="addConSubmit" cssClass="cssbutton" value="Submit" onclick="return storeProofData()" />
                                                                                                </div>
                                                                                            </div>
                                                                                        </s:form>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <s:form action="../Requirements/addRequirements" method="post" theme="simple" >
                                                                                <br><br>
                                                                                <s:hidden id="accountSearchID" name="accountSearchID" value="%{accountSearchID}"></s:hidden>
                                                                                <s:hidden id="account_name" name="account_name" value="%{accountDetails.name}"></s:hidden>
                                                                                    <div class="inner-reqdiv-elements">
                                                                                        <label class="labelStylereq" style="color:#56a5ec;">Req ID:</label>
                                                                                    <s:textfield cssClass="reqInputStyle" name="requirementId" id="requirementId" placeholder="Requirement Id"/>
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Job Title:</label>
                                                                                    <s:textfield cssClass="reqInputStyle" name="jobTitle" id="jobTitle" placeholder="Job Title"/>
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Skills:</label>
                                                                                    <s:textfield cssClass="reqInputStyle" name="requirementSkill" id="requirementSkill" placeholder="Skill"/>

                                                                                </div>
                                                                                <div class="inner-reqdiv-elements">
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">Status:</label>
                                                                                    <s:select id="requirementStatus" name="requirementStatus" cssClass="reqSelectStyle" headerKey="-1" headerValue="--Select--" theme="simple" list="#@java.util.LinkedHashMap@{'O':'Open','F':'Forecast','I':'Inprogess','H':'Hold','W':'Withdrawn','S':'Won','L':'Lost'}" />
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">StartDate:</label>
                                                                                    <s:textfield cssClass=" reqInputStyle dateImage" name="reqStart" id="reqStart" placeholder="FromDate"  tabindex="1"  onkeypress="return enterDateRepository();"/>
                                                                                    <label class="labelStylereq" style="color:#56a5ec;">EndDate:</label>
                                                                                    <s:textfield cssClass=" reqInputStyle dateImage" name="reqEnd" placeholder="ToDate"  id="reqEnd" tabindex="2"  onkeypress="return enterDateRepository();"/>

                                                                                </div>
                                                                                <div class="inner-reqdiv-elements pull-right">
                                                                                    <s:submit value="AddRequirement" cssClass="cssbutton"></s:submit>
                                                                                        <a href="#" ><input type="button" class="cssbutton " onclick="getReqDetailsBySearch()" value="Search"></a>

                                                                                    </div>
                                                                            </s:form>
                                                                            <br>
                                                                            <s:form>
                                                                                <div class="task_content" id="task_div" align="center" style="display: none" >

                                                                                    <div>
                                                                                        <div>
                                                                                            <table id="reqTable" class="responsive CSSTable_task" border="5"cell-spacing="2">
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <th>Title</th>
                                                                                                        <th>No.of Positions</th>
                                                                                                        <th>Req Skill Set</th>
                                                                                                        <th>Pre Skill Set</th>
                                                                                                        <th>StartDate</th>
                                                                                                        <th>Status</th>
                                                                                                        <th>Add Consultant</th>

                                                                                                    </tr>
                                                                                                </tbody>
                                                                                            </table>
                                                                                            <br/>
                                                                                            <div align="right" id="reqpageNavPosition" style="margin-right: 0vw;"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </s:form>
                                                                            <script type="text/javascript">
                                                                                var pager = new Pager('reqTable', 10);
                                                                                pager.init();
                                                                                pager.showPageNav('pager', 'reqpageNavPosition');
                                                                                pager.showPage(1);


                                                                            </script>
                                                                        </div>
                                                                    </div>
                                                                    <div class="tab-pane fade in " id="projects">
                                                                    </div>
                                                                </div>
                                                            </div>--%>
                                                        </div>
                                                        <!--  overlay start  -->
                                                        <div id="recSkillOverlay_popup">
                                                            <div id="reqskillBox" class="marginTasks">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Skill Details&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="recSkillOverlay_popup_close" onclick="requiredSkillOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>
                                                                </div>
                                                                <div>

                                                                    <s:textarea name="skillDetails"   id="reqSkillDetails"   disabled="true" cssClass="form-control textareaSkillOverlay"/> 


                                                                </div>
                                                                <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                                            </div>

                                                            <%--close of future_items--%>
                                                        </div>

                                                        <div id="preSkillOverlay_popup">
                                                            <div id="preskillBox" class="marginTasks">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Preferred Skill Details&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="preSkillOverlay_popup_close" onclick="preferredSkillOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>
                                                                </div>
                                                                <div>

                                                                    <s:textarea name="skillDetails"   id="preSkillDetails"   disabled="true" cssClass="form-control textareaSkillOverlay"/> 


                                                                </div>
                                                                <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                                            </div>
                                                        </div>

                                                        <div id="recruiterOverlay_popup">
                                                            <div id="recruiterBox" class="marginTasks">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Recruiter contact Information&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="recruiterOverlay_popup_close" onclick="closeRequirementOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>
                                                                </div>
                                                                <div><center>
                                                                        <table >
                                                                            <s:textfield label="Name" cssClass="form-control " id="recruiterNameOverlay" />
                                                                            <s:textfield label="Email Id" cssClass="form-control margin" id="recruiterEmailIdOverlay" />
                                                                            <s:textfield label="Phone" cssClass="form-control margin" id="recruiterPhoneOverlay" />
                                                                        </table>
                                                                    </center>
                                                                </div>
                                                                <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                                            </div>   
                                                        </div>
                                                        <div id="contactLoginOverlay_popup" >
                                                            <div id="contactLoginBox" class="marginTasks">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor" >&nbsp;&nbsp; contact Login Credentials&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="contactLoginOverlay_popup_close" onclick="contactOverlayLogin()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>
                                                                </div>
                                                                <div>
                                                                    <div class="inner-reqdiv-elements">
                                                                        <div  id="outputMessage"></div>
                                                                        <s:hidden id="orgId" name="orgId" value=""/>
                                                                        <s:hidden id="contactId" name="contactId" value=""/>
                                                                        <div  id="contactEmail" ></div>
                                                                    </div>
                                                                    <div class="pull-left " >
                                                                        <s:submit type="button" cssClass="contactLoginOverlay_popup_close" id="contactCancel" onclick="contactOverlayLogin()" value="Cancel"/>  
                                                                    </div>  
                                                                    <div class="pull-right " > 

                                                                        <s:submit type="button" cssClass="cssbutton" id="contactSend" value="Send" onclick="saveContactDetails()"/> 

                                                                    </div>

                                                                </div>
                                                                <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                                            </div>   
                                                        </div>                  
                                                        <div id="csraccountspopup" >
                                                            <div id="csraccoutsoverlay" class="marginTasks">
                                                                <div class="backgroundcolor">
                                                                    <table>
                                                                        <tr><td><h4 style="font-family:cursive"><font class="titleColor" >&nbsp;&nbsp; Account Status Changing&nbsp;&nbsp; </font></h4></td>
                                                                        <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="csraccountspopup_close" onclick="csraccountspopupclose()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                                                    </table>
                                                                </div>
                                                                <div>
                                                                    <span><csrOverl></csrOverl></span>
                                                                    <div class="inner-reqdiv-elements">
                                                                        <label class="labelStylereq" style="color:#56a5ec">Status:</label>                                                                        
                                                                        <s:select id="csrStatusOverlay" name="csrStatusOverlay" cssClass="SelectBoxStyles form-control "   theme="simple" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}" />                                                                        
                                                                        <s:hidden id="csrIdInOverlay"/>
                                                                        <s:hidden name="accountSearchID" id="accountSearchID"/>
                                                                    </div>

                                                                    <div class="pull-right " > 

                                                                        <s:submit type="button" cssClass="cssbutton" id="contactSend" value="Save" onclick="csrStatusChange()"/> 

                                                                    </div>

                                                                </div>
                                                                <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                                            </div>   
                                                        </div>
                                                        <!--  overlay end  -->    
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </section>
                    <script type="text/javascript">
                        $("#phone1").mask("(999)-999-9999");
                    </script>
                    <%--------------MIDDLE -----------------------------------------%>
                    <footer id="footer"><!--Footer-->
                        <div class="footer-bottom" id="footer_bottom">
                            <div class="container">
                                <s:include value="/includes/template/footer.jsp"/>
                            </div>
                        </div>
                    </footer><!--/Footer-->
                    <s:hidden id="testRealValue"/>
                    <%--ACCOUNT DETAILS SPECIFIC--%>
                    <script language="JavaScript" src="<s:url value="/includes/js/account/accountDetailsAJAX.js"/>" type="text/javascript"></script>
                    <script language="JavaScript" src="<s:url value="/includes/js/account/formVerification.js"/>" type="text/javascript"></script>
                    <script language="JavaScript" src="<s:url value="/includes/js/account/accountValidation.js"/>" type="text/javascript"></script>
                    <%----%>
                    <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
                    <script type="text/JavaScript" src="<s:url value="/includes/js/general/taskOverlay.js"/>"></script>
                    <script type="text/javascript">
                        //alert("before flag");
                        // alert(document.getElementById("conFlag").value);
                        var flag=document.getElementById("accFlag").value;

                        //alert(flag);
                        if(flag=="CsrSearch")
                        {
                            //alert("in if");
                            // document.getElementById('details').className="";
                            document.getElementById("headingmessage").innerHTML="csrAccountsRef";
                            document.getElementById('csrAccountsRef').className='tab-pane fade in active';
                            // var obj=document.getElementById('contacts');
                            //alert(obj);
                            //alert("before show contacts function");
                            getCsrDetailsTable();
                            // alert("after show contacts function");


                        }
                        if(flag=="conSearch")
                        {
                            //alert("in if");
                            // document.getElementById('details').className="";
                            document.getElementById("headingmessage").innerHTML="Contacts";
                            document.getElementById('contacts').className='tab-pane fade in active';
                            // var obj=document.getElementById('contacts');
                            //alert(obj);
                            //alert("before show contacts function");
                            showContacts();
                            // alert("after show contacts function");


                        }
                        if(flag=="accDetails")
                        {
                            //alert("in accDetails if");
                            // document.getElementById('details').className="";
                            document.getElementById("headingmessage").innerHTML="Account Details";
                            document.getElementById('details').className='tab-pane fade in active';
                        }
                        if(flag=="proSearch")
                        {
                            //alert("in accDetails if");
                            // document.getElementById('details').className="";
                            document.getElementById("headingmessage").innerHTML="Projects";
                            document.getElementById('projects').className='tab-pane fade in active';
                            var orgId=document.getElementById("accountSearchID").value;
                            javascript: ajaxReplaceDiv('/getAccountProjects','#projects','accountID='+orgId);
                        }
                        if(flag=="reqSearch")
                        {
                            //alert("in accDetails if");
                            // document.getElementById('details').className="";
                            document.getElementById("headingmessage").innerHTML="Account Requirements";
                            document.getElementById('requirements').className='tab-pane fade in active';
                            //var orgId=document.getElementById("accountSearchID").value;
                            getSearchRequirementsList();
                        }
                        if(flag=="assignTeamUpdate")
                        {

                            document.getElementById("headingmessage").innerHTML="Assign Team";
                            document.getElementById('team').className='tab-pane fade in active';
                            //var orgId=document.getElementById("accountSearchID").value;
                            //getRequirementDetails();
                        }
                        if(flag=="requirements")
                        {
                            alert("requirements");
                            document.getElementById("headingmessage").innerHTML="Requirements";
                            document.getElementById('requirements').className='tab-pane fade in active';
                            //var orgId=document.getElementById("accountSearchID").value;
                            //getRequirementDetails();
                        }
                        if(flag=="venSearch")
                        {
                            //alert("venSearch");
                            document.getElementById("headingmessage").innerHTML="Vendor Tier's";
                            document.getElementById('subvendors').className='tab-pane fade in active';
                            //var orgId=document.getElementById("accountSearchID").value;
                           
                            getVendors();
                        }
                    </script>
                    </body>
                    </html>
