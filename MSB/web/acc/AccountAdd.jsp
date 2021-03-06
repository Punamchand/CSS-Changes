<%--
    Document   : AccountAdd
    Created on : Apr 12, 2015, 7:05:25 PM
    Author     : Anton Franklin
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
                                        <font color="#ffffff">Add Account</font>
                                    </h4>
                                </div>
                            </div>
                            <!-- Add Form-->
                            <div class="col-sm-12" style="margin-bottom: 20px">
                                <s:form action="accountadd" method="post" theme="simple"
                                        onSubmit="return validateForm()" id="acc_form" enctype="multipart/form-data">
                                    <p id="resultMessage" align="center" class="accDetailsError"> <s:property value="resultMessage"  /></p>
                                    <s:if test="successMessage=='Account already Exist'">
                                        <p id="succMessage" align="center" class="accDetailsExists"> <s:property value="successMessage"  /></p>
                                    </s:if>
                                    <s:else>
                                        <p id="succMessage" align="center" class="accDetailsSuccess"> <s:property value="successMessage"  /></p>
                                    </s:else>
                                    <h4><b>Account Information</b></h4>
                                    <div class="col-sm-12">
                                        <div class="row">
                                            <div class="col-lg-3">
                                                <span>
                                                    <label class="labelStyle2"><span class="accDetailsError">*</span>Account Name</label>
                                                    <s:textfield cssClass="form-control" id="account_name" type="text" maxLength="60"
                                                                 name="account.name" placeholder="Account Name" value="%{account.name}"
                                                                 onblur="javascript: nameCheck('#account_name','#accountNameValidation')" />
                                                    <span id="accountNameValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>
                                            <div class="col-lg-3">
                                                <span>
                                                    <label class="labelStyle2"><span class="accDetailsError">*</span> Account URL </label>
                                                    <s:textfield cssClass="form-control" id="account_url" type="text" maxLength="60"
                                                                 name="account.url"  placeholder="Account Url" value="%{account.url}"
                                                                 onblur="javascript: urlCheck('#account_url','#accountURLValidation')"/>
                                                    <span id="accountURLValidation" class="accDetailsError"></span>
                                                </span>
                                                <br />
                                            </div>

                                            <div class="col-lg-3">
                                                <span>
                                                    <label class="labelStyle2"><span class="accDetailsError">*</span> Account Type </label>
                                                    <s:select  cssClass="SelectBoxStyles form-control" id="account_type"
                                                               name="account.typeId" list="accountTypeList"
                                                               value="%{account.typeId}"
                                                               headerKey="" headerValue="Select Account Type"
                                                               cssStyle="width:100%;" onchange="validateDropDown('account_type','accountTypeValidation')"/>
                                                    <span id="accountTypeValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>
                                            <div class="col-lg-3">
                                                <span>
                                                    <label class="labelStyle2"><span class="accDetailsError">*</span> Mail Extention </label>
                                                    <s:textfield  cssClass="form-control" id="email_ext" maxLength="60"
                                                                  name="account.email_ext" list="accountTypeList"
                                                                  value="%{account.email_ext}"
                                                                  cssStyle="width:100%;" onchange="getValidExtention()"/>
                                                    <span id="accountTypeValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>


                                            <%--<div class="col-sm-3" id="vendorType" style="display: none;">
                                              <label class="labelStyle2"><span class="accDetailsError">*</span> Vendor Type: </label>
                                              <s:select  cssClass="SelectBoxStyles form-control" id="vendorType"
                                                         name="account.vendorTypeId" value="%{account.vendorTypeId}"
                                                         list="vendorTypeList"
                                                         headerKey="" headerValue="Select Vendor Type"
                                                         cssStyle="width:100%;"
                                                         />
                                              <span id="accountVendorValidation" class="accDetailsError"></span>
                                            </div>
                                            --%>
                                        </div>
                                    </div>
                                    <h4><b>Account Address</b></h4>
                                    <div class="col-sm-12">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"> Address 1 </label>
                                                    <s:textfield cssClass="form-control" id="address1" type="text" maxLength="100"
                                                                 name="account.address1" placeholder="Address 1"
                                                                 value="%{account.address1}"/>
                                                </span></div>
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"> Address 2 </label>
                                                    <s:textfield cssClass="form-control" id="address2" type="text" maxLength="100"
                                                                 name="account.address2" placeholder="Address 2"
                                                                 value="%{account.address2}"/>
                                                </span></div>

                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"><%--<span class="accDetailsError">*</span>--%> City </label>
                                                    <s:textfield cssClass="form-control" id="acc_city" type="text" maxLength="20"
                                                                 name="account.city" placeholder="City"
                                                                 value="%{account.city}"/>
                                                </span>
                                            </div>
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"> Zip </label>
                                                    <s:textfield cssClass="form-control" id="acc_zip" type="text" maxLength="10"
                                                                 name="account.zip" placeholder="Zip"
                                                                 value="%{account.zip}"/>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2">Country </label>
                                                    <s:select id="acc_country" name="account.country.id" cssClass="SelectBoxStyles form-control"
                                                              headerKey="" headerValue="Select Country" theme="simple" list="countryList"
                                                              listKey="%{id}" listValue="%{name}"
                                                              value="%{account.country.id}"

                                                              onchange="javascript:
                                                              getStates($(acc_country).val(),'#acc_state');
                                                              getStockSymbol($(acc_country).val());"
                                                              cssStyle="width:100%;"  />
                                                    <%-- validateDropDown('acc_country','countryValidation')" --%>
                                                    <span id="countryValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>

                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"> State </label>
                                                    <s:select id="acc_state" name="account.state.id"  cssClass="SelectBoxStyles form-control"
                                                              headerKey="" headerValue="Select State" theme="simple"
                                                              list="stateList" listKey="%{id}" listValue="%{name}"
                                                              cssStyle="width:100%;"
                                                              value="%{account.state.id}"
                                                              />
                                                    <%-- onchange="validateDropDown('acc_state','stateValidation')" --%>
                                                    <span id="stateValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>

                                            <div class="col-sm-3">
                                                <span>
                                                    <label  class="labelStyle2"> Phone </label>
                                                    <s:textfield cssClass="form-control" id="phone1" type="text" maxLength="15"
                                                                 name="account.phone" placeholder="Phone"
                                                                 value="%{account.phone}"/>
                                                    <span id="phoneValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>
                                            <div class="col-sm-3">
                                                <span>
                                                    <label  class="labelStyle2">Fax </label>
                                                    <s:textfield cssClass="form-control" id="fax" type="text" maxLength="15"
                                                                 name="account.fax" placeholder="Fax"
                                                                 value="%{account.fax}"/>
                                                    <span id="faxValidation" class="accDetailsError"></span>
                                                </span>
                                            </div>
                                        </div>
                                        <br/>
                                    </div>

                                    <h4><b>Basic Information</b></h4>
                                    <div class="col-sm-12">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"> Industry</label>
                                                    <s:select id="acc_industry" name="account.industryId"
                                                              cssClass="SelectBoxStyles form-control"
                                                              cssStyle="width:100%;"
                                                              headerKey="" headerValue="Select Industry" theme="simple"
                                                              list="industryList"  value="%{account.industryId}"
                                                              />
                                                    <%--onchange="validateDropDown('acc_industry','industryValidation')" --%>
                                                    <span id="industryValidation" class="accDetailsError"/>
                                                </span>
                                            </div>
                                            <div class="col-sm-3">
                                                <span>
                                                    <label  class="labelStyle2">Region </label>
                                                    <s:textfield cssClass="form-control" id="reqion" type="text" maxLength="20"
                                                                 name="account.region"  placeholder="Region"
                                                                 value="%{account.region}"/>
                                                    <span id="regionValidation" class="accDetailsError"/>
                                                </span>
                                            </div>
                                            <div class="col-sm-3">
                                                <span>
                                                    <label   class="labelStyle2">Territory </label>
                                                    <s:textfield cssClass="form-control" id="acc_territory" type="text" maxLength="20"
                                                                 name="account.territory"  placeholder="Territory"
                                                                 value="%{account.territory}"/>
                                                </span></div>

                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2"> No. of Employees </label>
                                                    <s:textfield cssClass="form-control" id="acc_no_of_employees" type="text" maxLength="11"
                                                                 name="account.noemp" placeholder="No. of Emmployess"
                                                                 value="%{account.noemp}"
                                                                 onblur="noeValidate();"/>
                                                    <span id="employeeValidation" class="accDetailsError"/>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <%-- onblur="isDecimal(this, 'employeeValidation')" this method for no. of employees
                                          <div class="col-sm-3">
                                            <span>
                                              <label class="labelStyle2">Budget </label>
                                              <s:textfield cssClass="form-control" id="acc_budget" type="text"
                                                           name="account.budget"  placeholder="Budget"
                                                           value="%{account.budget}"/>
                                              <s:hidden id="testRealValue"/>
                                              <span id="budgetValidation" class="accDetailsError"/>
                                            </span>
                                          </div>--%>


                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2">Tax ID </label>
                                                    <s:textfield cssClass="form-control" id="acc_tax_id" type="text" maxLength="20"
                                                                 name="account.tax_id" placeholder="Tax ID"
                                                                 value="%{account.tax_id}"/>
                                                    <span id="taxValidation" class="accDetailsError"/>
                                                </span></div>
                                            <!--Linked to State and Country-->
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2">Stock Symbol </label>
                                                    <s:textfield cssClass="form-control" id="acc_stock_symbol" type="text"
                                                                 name="account.stockSymbol"  placeholder="Stock Symbol" readonly="true"
                                                                 value="%{account.stockSymbol}"/>
                                                </span></div>
                                            <div class="col-sm-3">
                                                <span>
                                                    <label class="labelStyle2">Revenue </label>
                                                    <s:textfield cssClass="form-control" id="acc_revenue" type="text" maxLength="20"
                                                                 name="account.revenue"  placeholder="Revenue"
                                                                 value="%{account.revenue}"
                                                                 onblur="revenueValidate();"/>
                                                    <span id="revenueValidation" class="accDetailsError" ></span>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <span>
                                                    <label  class="labelStyle2">Description </label>
                                                    <s:textarea cssClass="form-control" id="description" type="text"
                                                                name="account.description"  placeholder="Description"
                                                                maxlength="200" value="%{account.description}"    />
                                                    <span id="ResponsecharNum" class="charNum"></span>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <%--s:reset type="button" cssClass="cssbutton_emps field-margin" key="reset" value="Clear"/--%>
                                                <sx:submit type="button" cssClass="cssbutton_emps field-margin" key="reset" value="Clear" onclick="clearform();"/>
                                            </div>
                                            <div class="col-sm-4">
                                                <s:reset type="button" cssClass="cssbutton_emps field-margin" onclick="javascript:history.back();" value="Cancel"/>
                                            </div>
                                            <div class="col-sm-4">
                                                <s:submit type="submit" cssClass="cssbutton_emps field-margin" value="Save"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <p id="errorMessage" align="center" class="accDetailsError" >
                                                <s:property value="resultMessage"  />
                                            </p></div>
                                        </s:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer>

    </body>
    
    <script type="text/javascript">
    /*jQuery time*/
     $("#accordian h3").click(function(){
                //slide up all the link lists
                $("#accordian ul ul").slideUp();
                //slide down the link list below the h3 clicked - only if its closed
                if(!$(this).next().is(":visible"))
                {
                        $(this).next().slideDown();
                }
        })
</script>

    
</html>
