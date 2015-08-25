<%-- 
    Document   : invoiceEdit
    Created on : Jul 29, 2015, 8:18:12 PM
   Author     : praveen<pkatru@miraclesoft.com>
--%>
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
        <title>Miracle Service Bay :: User Grouping</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value='/includes/css/accountDetails/details.css'/>">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">

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
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <!--<script>
            var myCalendar1;
            function onloadDate(){
                // alert("hii1");

                myCalendar1 = new dhtmlXCalendarObject(["pamentDate"]);
                myCalendar1.setSkin('omega');
                // alert("hii2");
                myCalendar1.setDateFormat("%m-%d-%Y");
            }
        </script>
        -->
    </head>

    <body onload="onloadDate();">
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
                                        <font color="#ffffff">Invoice Edit</font>
                                        <s:url var="myUrl" action="getInvoice.action">
                                        </s:url>
                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                    </h4>
                                </div>
                            </div> 
                            <s:form action="doEditInvoiceDetatils" theme="simple">
                                <span><errorMsg></errorMsg></span>
                                <s:hidden name="invoiceVTOClass.invoiceId" value="%{invoiceVTOClass.invoiceId}"/>
                                <div class="col-lg-12">
                                    <s:if test="invoiceVTOClass.status=='approved' || invoiceVTOClass.status=='rejected'">
                                        <div class="col-lg-3">
                                        </s:if>
                                        <s:else>
                                            <div class="col-lg-6">
                                            </s:else>
                                            <label class="labelStylereq" style="color:#56a5ec">Resource Name</label>
                                            <s:textfield id="" name="" value="%{invoiceVTOClass.userName}" cssClass=" form-control " disabled="true" />
                                        </div>
                                        <s:if test="invoiceVTOClass.status=='approved' || invoiceVTOClass.status=='rejected'">
                                            <div class="col-lg-3">
                                            </s:if>
                                            <s:else>
                                                <div class="col-lg-6">
                                                </s:else>

                                                <label class="labelStylereq" style="color:#56a5ec">Organization</label>
                                                <s:textfield id="" name="" value="%{invoiceVTOClass.custName}" cssClass=" form-control "  disabled="true"/>
                                            </div>
                                            <s:if test="invoiceVTOClass.status=='approved' || invoiceVTOClass.status=='rejected'">
                                                <div class="col-lg-3">
                                                    <s:if test="invoiceVTOClass.status=='approved'">
                                                        <label class="labelStylereq" style="color:#56a5ec">Approver</label>
                                                    </s:if>
                                                    <s:else>
                                                        <label class="labelStylereq" style="color:#56a5ec">Rejecter</label>
                                                    </s:else>
                                                    <s:textfield id="" name="" value="%{invoiceVTOClass.custApprName}" cssClass=" form-control "  disabled="true"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <s:if test="invoiceVTOClass.status=='approved'">
                                                        <label class="labelStylereq" style="color:#56a5ec">Approver Date</label>
                                                    </s:if>
                                                    <s:else>
                                                        <label class="labelStylereq" style="color:#56a5ec">Rejecter Date</label>
                                                    </s:else>
                                                    <s:textfield id="" name="" value="%{invoiceVTOClass.custApprDate}" cssClass=" form-control "  disabled="true"/>
                                                </div>
                                            </s:if>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="col-lg-3">
                                                <label class="labelStylereq" style="color:#56a5ec">Month</label>
                                                <s:select id="invoiceMonth" name="invoiceVTOClass.invoicemonth" value="%{invoiceVTOClass.invoicemonth}" cssClass="SelectBoxStyles form-control " theme="simple" list="#@java.util.LinkedHashMap@{'1':'January','2':'February','3':'March','4':'April','5':'May','6':'June','7':'July','8':'August','9':'September','10':'October','11':'November','12':'December'}"   />
                                            </div>
                                            <div class="col-lg-3">
                                                <label class="labelStylereq" style="color:#56a5ec">Year</label>
                                                <s:textfield id="invoiceYear" name="invoiceVTOClass.invoiceyear" value="%{invoiceVTOClass.invoiceyear}" cssClass=" form-control "  />
                                            </div>
                                            <div class="col-lg-3">
                                                <label class="labelStylereq" style="color:#56a5ec">Status</label>
                                                <s:if test="#session.typeOfUsr=='VC'">
                                                    <s:if test="invoiceVTOClass.status=='created'">
                                                        <s:select id="invoiceStatus" name="invoiceVTOClass.status" value="%{invoiceVTOClass.status}" cssClass="SelectBoxStyles form-control "  list="#@java.util.LinkedHashMap@{'created':'Created','submitted':'Submitted'}"  />
                                                    </s:if>
                                                    <s:elseif test="invoiceVTOClass.status=='submitted' || invoiceVTOClass.status=='rejected'">
                                                        <s:select id="invoiceStatus" name="invoiceVTOClass.status" value="%{invoiceVTOClass.status}" cssClass="SelectBoxStyles form-control "  list="#@java.util.LinkedHashMap@{'submitted':'Submitted','rejected':'Rejected'}"  />
                                                    </s:elseif>
                                                    <s:else>
                                                        <s:select id="invoiceStatus" name="invoiceVTOClass.status" value="%{invoiceVTOClass.status}" cssClass="SelectBoxStyles form-control "  list="#@java.util.LinkedHashMap@{'created':'Created','submitted':'Submitted','approved':'Approved','rejected':'Rejected','paid':'Paid'}"  disabled="true" />
                                                    </s:else>
                                                </s:if>
                                                <s:else>
                                                    <s:if test="invoiceVTOClass.status=='submitted'">
                                                        <s:select id="invoiceStatus" name="invoiceVTOClass.status" value="%{invoiceVTOClass.status}" cssClass="SelectBoxStyles form-control "  list="#@java.util.LinkedHashMap@{'submitted':'Submitted','approved':'Approved','rejected':'Rejected'}"  onchange="return setPaybleDate();"/>
                                                    </s:if>
                                                    <s:elseif test="invoiceVTOClass.status=='approved'">
                                                        <s:select id="invoiceStatus" name="invoiceVTOClass.status" value="%{invoiceVTOClass.status}" cssClass="SelectBoxStyles form-control "  list="#@java.util.LinkedHashMap@{'approved':'Approved','paid':'Paid'}"  onchange="return setPaybleDate();"/>
                                                    </s:elseif>
                                                    <s:else>
                                                        <s:select id="invoiceStatus" name="invoiceVTOClass.status" value="%{invoiceVTOClass.status}" cssClass="SelectBoxStyles form-control "  list="#@java.util.LinkedHashMap@{'submitted':'Submitted','approved':'Approved','rejected':'Rejected','paid':'Paid'}"  onchange="return setPaybleDate();" disabled="true"/>
                                                    </s:else>
                                                </s:else>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="col-lg-3">
                                                <label class="labelStylereq" style="color:#56a5ec">Total Hours</label>
                                                <s:textfield id="" name="invoiceVTOClass.totalHrs" value="%{invoiceVTOClass.totalHrs}" cssClass=" form-control "  />
                                            </div>
                                            <div class="col-lg-3">
                                                <label class="labelStylereq" style="color:#56a5ec">Total Amount</label>
                                                <s:textfield id="totalAmt" name="invoiceVTOClass.totalAmt" value="%{invoiceVTOClass.totalAmt}" cssClass=" form-control "  />
                                            </div>
                                            <div class="col-lg-3">
                                                <label class="labelStylereq" style="color:#56a5ec">Net Terms</label>
                                                <s:select id="netTerms" name="invoiceVTOClass.netTerms" value="%{invoiceVTOClass.netTerms}" cssClass="SelectBoxStyles form-control" list="#@java.util.LinkedHashMap@{'15':'15','30':'30','45':'45','60':'60','75':'75','90':'90'}" disabled="true"  />
                                            </div>

                                            <s:if test="#session.typeOfUsr=='VC' ">
                                                <div class="col-lg-3">
                                                    <label class="labelStylereq" style="color:#56a5ec">Payment Date.</label>
                                                    <s:textfield id="" name="" value="%{invoiceVTOClass.paymentDate}"  cssClass=" form-control "  disabled="true"/>
                                                </div>
                                            </s:if>
                                            <s:else>
                                                <div class="col-lg-3">
                                                    <label class="labelStylereq" style="color:#56a5ec">Payable Date.</label>
                                                    <s:textfield id="pamentDate" name="invoiceVTOClass.paymentDate"  value="%{invoiceVTOClass.paymentDate}" cssClass=" form-control " readonly="true" />
                                                </div>
                                            </s:else>

                                        </div>
                                        <div class="col-lg-12">
                                            <s:if test="(invoiceVTOClass.status=='approved' && #session.typeOfUsr!='VC' )||invoiceVTOClass.status=='paid'">
                                                <div class="col-lg-3">
                                                    <label class="labelStylereq" style="color:#56a5ec">Pay Type</label>
                                                    <s:select id="" name="invoiceVTOClass.payType" value="%{invoiceVTOClass.payType}" cssClass="SelectBoxStyles form-control"  list="#@java.util.LinkedHashMap@{'CH':'Check','FT':'Fund Trasfer'}"/>
                                                </div>
                                                <s:if test="#session.typeOfUsr=='VC'">
                                                    <div class="col-lg-3">
                                                        <label class="labelStylereq" style="color:#56a5ec">Payment Trns.No.</label>
                                                        <s:textfield id="" name="" value="%{invoiceVTOClass.cheOrTransno}" cssClass=" form-control "  disabled="true"/>
                                                    </div>
                                                </s:if>
                                                <s:else>
                                                    <div class="col-lg-3">
                                                        <label class="labelStylereq" style="color:#56a5ec">Payment Trns.No.</label>
                                                        <s:textfield id="transNO" name="invoiceVTOClass.cheOrTransno" value="%{invoiceVTOClass.cheOrTransno}" cssClass=" form-control"/>
                                                    </div>
                                                </s:else>
                                                <s:if test="#session.typeOfUsr=='VC' ">
                                                    <div class="col-lg-3">
                                                        <label class="labelStylereq" style="color:#56a5ec">Paid Amount.</label>
                                                        <s:textfield id="paidAmt" name="" value="%{invoiceVTOClass.paidAmt}"  cssClass=" form-control "  disabled="true"/>
                                                    </div></s:if>
                                                <s:else>
                                                    <div class="col-lg-3">
                                                        <label class="labelStylereq" style="color:#56a5ec">Paid Amount.</label>
                                                        <s:textfield id="paidAmt" name="invoiceVTOClass.paidAmt" value="%{invoiceVTOClass.paidAmt}"  cssClass=" form-control" onblur="getBalanceAmt()" />
                                                    </div>  
                                                </s:else>
                                                <div class="col-lg-3">
                                                    <label class="labelStylereq" style="color:#56a5ec">Balance Amount</label>
                                                    <s:textfield id="balanceAmt" name="invoiceVTOClass.balanceAmt" value="%{invoiceVTOClass.balanceAmt}" cssClass=" form-control " readonly="true" />
                                                </div>


                                            </s:if>

                                        </div>
                                        <div class="col-lg-12">
                                            <div class="col-lg-12">
                                                <label class="labelStylereq" style="color:#56a5ec">Description</label>
                                                <s:textarea id="" name="invoiceVTOClass.description" value="%{invoiceVTOClass.description}" cssClass=" form-control"  />
                                            </div>

                                        </div>
                                        <div class="col-lg-12">
                                            <s:if test="invoiceVTOClass.status!='paid'">

                                                <s:if test="(invoiceVTOClass.status=='created' && #session.typeOfUsr=='VC') || invoiceVTOClass.status=='rejected'">
                                                    <s:submit value="Save" cssClass="cssbutton pull-right" />
                                                </s:if>
                                                <s:elseif test="#session.typeOfUsr!='VC' && #session.typeOfUsr=='approved'">
                                                    <sx:submit  value="Paid" cssClass="cssbutton pull-right"  onclick="return getBalanceAmt();"/>
                                                </s:elseif>
                                                <s:else>
                                                    <sx:submit  value="Save" cssClass="cssbutton pull-right"  onclick="return getBalanceAmt();"/>
                                                </s:else>
                                            </s:if>

                                        </div>
                                    </s:form>



                                </div>
                            </div><!-- Add Form-->
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
                <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
                <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
                    <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
                </div>
                </body>
                </html>
