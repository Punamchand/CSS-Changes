<%-- 
    Document   : dashboard
    Created on : July 2nd , 2015, 12:32:23 PM
    Author     : Aklakh Ahmad
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
        <title>Miracle ServicesBay :: Dashboard Page</title>
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
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/vendorDetailsStyles.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/taskiframe.css"/>">



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
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/vendorAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/ConsultantAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/dashBoardAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/vendorDashboardAjax.js"/>'></script>

        <script type="text/javascript" src="<s:url value="/includes/js/general/glinechart.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/general/Barchart.js"/>"></script>
        <script>
            var pager;
            $(document).ready(function(){
                
               
                var paginationSize = 10;
                pager = new Pager('accountSearchResults', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
                
            });
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('accountSearchResults', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
            
        </script>
        <!-- end of new styles -->
    </head>
    <body onload="getCustomerRequirementsDashBoard();">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/> 
                </div>
            </div>

        </header>
        <s:include value="/includes/menu/LeftMenu.jsp"/>

        <section id="generalForm"><!--form-->
            <div class="container">
                <div class="row">
                    <!-- content start -->
                    <div class="col-md-10 col-md-offset-0" style="background-color:#fff">

                        <div style="margin-top: 10px">
                            <headingmess id="headingmessage"  class="acc_menu_heading pull-right" style="display:block">Customer Dashboard</headingmess>    

                        </div> 
                        <ul class="active_details" >
                            <li class="dropdown"  >
                                <a class="dropdown-toggle " data-toggle="dropdown"  href="#" title="Consultant Details"   style="background-color: #000; width:40px;"><img src="<s:url value="/includes/images/toggleMenu.png"/>" height="40" width="38"></a>

                                <!-- Nav tabs -->
                                <ul class="panel-body nav-stacked  dropdown-menu " style="position:absolute">

                                    <li class=" "><a aria-expanded="false" onclick="dashboardMessage(this);" id="customerBoard"  href="#Customer" data-toggle="tab"> Customer Dashboard</a>
                                    </li>
                                    <li class=""><a aria-expanded="false" onclick=" dashboardMessage(this); getDefaultVendorRequirementsDashBoard();" id="vendorBoard"  href="#Vendor" data-toggle="tab">Vendor Dashboard </a>
                                    </li>

                                </ul>
                            </li>
                        </ul>
                        <div class="tab-content" style="padding : 0px">
                            <div class="tab-pane fade in " id="Vendor">
                                <div class="col-sm-12">
                                    <div class="inner-reqdiv-elements">
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <label class="labelStylereq" style="color:#56a5ec;">Year: </label>
                                                <s:textfield cssClass="form-control" name="vdashYears" id="vdashYears" placeholder="Year" value="%{year}" />
                                            </div>
                                            <div class="col-lg-4">
                                                <label class="labelStylereq" style="color:#56a5ec;">Vendor: </label>
                                                <s:select cssClass="SelectBoxStyles form-control" name="vcsrCustomers" id="vcsrCustomers" headerKey="-1" headerValue="All" list="vendorMap" />
                                            </div>
                                            <div class="col-lg-2">
                                                <br>
                                                <s:submit type="button" cssClass="cssbutton_emps form-control "
                                                          value="Search" onclick="return getVendorRequirementsDashBoard()" cssStyle="margin:5px"/>
                                            </div>
                                        </div>
                                    </div>
                                    <table id="VendorDashBoardTable" class="responsive CSSTable_task" border="5" cell-spacing="2">
                                        <tbody>
                                            <tr>
                                                <th>Vendor Name</th>
                                                <th>Processing</th>
                                                <th>Selected</th>
                                                <th>Rejected</th>
                                                <th>Won</th>
                                                <th>Lost</th>
                                                <th>Total</th>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <br/>
                                    <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
                                            <option>10</option>
                                            <option>15</option>
                                            <option>25</option>
                                            <option>50</option>
                                        </select>
                                        Accounts per page
                                    </label>
                                    <div align="right" id="pageNavPosition" style="margin: -31px -1px 9px 5px;"></div>

                                    <script type="text/javascript">
                                        var pager = new Pager('VendorDashBoardTable', 8); 
                                        pager.init(); 
                                        pager.showPageNav('pager', 'pageNavPosition'); 
                                        pager.showPage(1);
                                    </script>
                                </div>
                            </div> 
                            <div class="tab-pane fade in active"  id="Customer" >
                                <div class="col-sm-12">
                                    <s:form  theme="simple" >
                                        <br>

                                        <div class="inner-reqdiv-elements">
                                            <div class="row">
                                                <div class="col-lg-2">
                                                    <label class="labelStylereq" style="color:#56a5ec;">Year: </label>
                                                    <s:textfield cssClass="form-control" name="dashYears" id="dashYears" placeholder="Year" value="%{year}" />
                                                </div>
                                                <div class="col-lg-4">
                                                    <label class="labelStylereq" style="color:#56a5ec;">Customer: </label>
                                                    <s:select cssClass="SelectBoxStyles form-control" name="csrCustomers" id="csrCustomers" headerKey="-1" headerValue="All" list="custerMap" />
                                                </div>
                                                <div class="col-lg-2">
                                                    <br>
                                                    <s:submit type="button" cssClass="cssbutton_emps form-control "
                                                              value="Search" onclick="return getCustomerRequirementsDashBoard()" cssStyle="margin:5px"/>
                                                </div>
                                            </div>
                                        </div>

                                    </s:form>

                                    <br>
                                    <s:form>

                                        <div class="task_content" id="task_div" align="center" style="display: none" >

                                            <div>
                                                <div>
                                                    <table id="dashBoardTable" class="responsive CSSTable_task" border="5" cell-spacing="2">
                                                        <tbody>
                                                            <tr>
                                                                <th>Customer Name</th>
                                                                <th>Open</th>
                                                                <th>Release</th>
                                                                <th>Closed</th>
                                                                <th>Total</th>
                                                            </tr>
                                                            <s:if test="csrDashBoardList ==null">
                                                                <tr>
                                                                    <td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>
                                                                </tr>
                                                            </s:if>
                                                            <s:iterator  value="csrDashBoardList">
                                                                <tr>
                                                                    <s:hidden name="accountId" id="accountId" value="%{accountId}" />
                                                                    <td><s:property value="customerName"></s:property></td>
                                                                    <td><s:property value="open"></s:property></td>
                                                                    <td><s:property value="released"></s:property></td>
                                                                    <td><s:property value="closed"></s:property></td>
                                                                    <td><s:a href="#" cssClass="csrCustomerReq_popup_open" onclick="csrCustReqOverlay();csrCustReqDetails(%{accountId});"><s:property value="total"></s:property></s:a></td>
                                                                </tr>
                                                            </s:iterator>

                                                        </tbody>
                                                    </table>
                                                    <br>
                                                    <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
                                                            <option>10</option>
                                                            <option>15</option>
                                                            <option>25</option>
                                                            <option>50</option>
                                                        </select>
                                                        Accounts per page
                                                    </label>
                                                    <div align="right" id="pageNavPosition1" style="margin: 0px -1px 9px 5px;"></div>
                                                </div>
                                                <script type="text/javascript">
                                                    var pager = new Pager('dashBoardTable', 8); 
                                                    pager.init(); 
                                                    pager.showPageNav('pager', 'pageNavPosition1'); 
                                                    pager.showPage(1);
                                                </script>
                                            </div>
                                        </div>
                                    </s:form>




                                </div>
                            </div>  
                        </div>
                    </div>
                    <br>
                    <div  id="reqCustomerYearChart"  ></div>
                </div>
            </div>

            <div id="csrCustomerReq_popup">
                <div id="recruiterBox" class="marginTasks">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor">No.Of&nbsp;Requirements&nbsp;Posted </font></h4></td>
                            <span class="pull-right"> <h5 ><a href="" class="csrCustomerReq_popup_close" onclick="csrCustReqOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div style="margin: 10px;margin-bottom: -10px"><center>
                            <table id="dashBoardTableOnOverlay"  class="CSSTable_task  " border="2" cell-spacing="1" style="overflow-x:auto;overflow-y:hidden;">
                                <tbody>
                                    <tr>
                                        <th>Month</th>
                                        <th>No.Of.Req</th>
                                    </tr>
                                </tbody>
                            </table>
                        </center>

                    </div><center>
                        <font style="color: #fff">........................ ......................................... .................................</font>
                    </center>
                </div>
            </div>



            <div id="csrVendorReq_popup">
                <div id="csrVenBox" class="marginTasks">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4><font class="titleColor">No.Of&nbsp;Requirements&nbsp;won/loss&nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 ><a href="" class="csrVendorReq_popup_close" onclick="csrVenOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div style="margin: 10px;margin-bottom: -10px"><center>
                            <table id="dashBoardTableVendorOnOverlay"  class="CSSTable_task  " border="2" cell-spacing="1" style="overflow-x:auto;overflow-y:hidden;" >
                                <tbody>
                                    <tr>
                                        <th>Month</th>
                                        <th>Won</th>
                                        <th>Lost</th>
                                    </tr>
                                </tbody>
                            </table>
                            <div id="dashBoardTableVendorOnOverlay" align="right" style="margin-right:0vw"></div>
                            <div   style="width:auto;height:auto" >
                                <div  id="dashBoardTableVendorOnOverlay" class="badge pull-right" style="display:none"></div>                                                       
                            </div>  
                        </center>
                    </div><center>
                        <font style="color: #fff">........................ ......................................... .................................</font>
                    </center>
                </div>
            </div>
        </section ><!--form-->
        <footer id="footer"><!--Footer-->

            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
            <script language="JavaScript" src='<s:url value="/includes/js/general/popupoverlay.js"/>'></script>

        </footer><!--/Footer-->

    </body>
</html>