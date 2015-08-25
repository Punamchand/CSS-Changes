<%--
    Document   : Vendor Dashboard
    Created on : July 01, 2015, 07:10:41 PM
--%>

<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Dashboard Page</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/profilediv.css"/>'>
        <%-- <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
             <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">--%>

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/CountriesAjax.js"/>"></script>
        <%-- <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>--%>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/vendorAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/dashBoardAjax.js"/>'></script>

        <script type="text/javascript" src="<s:url value="/includes/js/general/glinechart.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/general/Barchart.js"/>"></script>
    </head>
    <body style="overflow-x: hidden" onload="getCustomerDashboardList();">
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
                            <div class="col-lg-12 ">
                                <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                    <div class="backgroundcolor" >
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                <font color="#ffffff">Dashboard</font>
                                            </h4>
                                        </div>
                                    </div>
                                    <span> <br/></span>
                                    <!-- content start -->
                                    <div class="col-sm-12">
                                        <s:form theme="simple" >

                                            <div class="inner-reqdiv-elements">
                                                <div class="row">
                                                    <div class="col-lg-2">
                                                        <label class="" style="color:#56a5ec;">Year: </label>
                                                        <s:textfield cssClass="form-control" id="year"
                                                                     name="year" placeholder="Year" 
                                                                     />
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <label class="" style="color:#56a5ec;">Month: </label>
                                                        <s:select id="month" cssClass="form-control SelectBoxStyles" name="month" headerKey="-1" headerValue="All" list="#@java.util.LinkedHashMap@{'01':'January','02':'February','03':'March','04':'April','05':'May','06':'June','07':'July','08':'August','09':'September','10':'October','11':'November','12':'December'}"/>
                                                    </div>

                                                    <div class="col-lg-2">
                                                        <div class="row">
                                                            <div class="col-lg-11">
                                                                <label class="" style="color:#56a5ec;"></label> 
                                                                <%--<s:submit type="submit" cssClass="cssbutton_emps form-control"
                                                                        value="Search" onclick="getVendorDashboardList();"/> --%>
                                                                <a href="#" ><input type="button" class="cssbutton_emps form-control" value="Search" style="margin:5px" onclick="getCustomerDashboardList();"></a>
                                                            </div>
                                                        </div>
                                                    </div>   
                                                </div>
                                            </div>
                                        </div>


                                        <%--div class="row">
                                            <div class="col-sm-4"> <s:submit type="submit" cssClass="cssbutton_emps field-margin"
                                                      value="Search" cssStyle="margin:0px"/></div>
                                            <div class="col-sm-4"></div>
                                        </div--%>

                                    </s:form>
                                    <span> <br/></span>
                                        <%--<s:submit cssClass="css_button" value="show"/><br>--%>
                                    <div class="col-sm-12">

                                        <s:form>
                                            <s:hidden id="accountSearchID" value="%{id}" ></s:hidden>
                                                <div class="emp_Content" id="emp_div" align="center" style="display: none"    >
                                                    <table id="customerDashboardResults" class="responsive CSSTable_task" border="5">
                                                        <tbody>
                                                            <tr>
                                                            <%--<th>Req.Won</th>
                                                                <th>Req.Lost</th> --%>
                                                            <th>Month</th>
                                                            <th>Opened </th>
                                                            <th>Released</th>
                                                            <th>Closed</th>
                                                            <th>Total</th>
                                                        </tr>
                                                        <s:if test="customerDashBoardList.size == 0">
                                                            <tr>
                                                                <td colspan="5"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                            </tr>
                                                        </s:if>

                                                        <s:iterator  value="customerDashBoardList">
                                                            <tr>
                                                                <%-- <td><s:property value="noOfReqWon"></s:property></td>
                                                                     <td><s:property value="noOfReqLose"></s:property></td>--%>
                                                                <td><s:property value="month"></s:property></td>
                                                                <td><s:property value="open"></s:property></td>
                                                                <td><s:property value="released"></s:property></td>
                                                                <td><s:property value="closed"></s:property></td>
                                                                <td><s:property value="total"></s:property></td>

                                                                </tr>
                                                        </s:iterator>

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
                                                <div align="right" class="pull-right" id="pageNavPosition" style="margin-right: 0vw;"></div>
                                            </s:form>
                                            <script type="text/javascript">
                                                var dashPager = new Pager('customerDashboardResults', 10);
                                                dashPager.init();
                                                dashPager.showPageNav('dashPager', 'pageNavPosition');
                                                dashPager.showPage(1);
                                            </script>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <%--close of future_items--%>
                        </div>
                    </div>
                    <br>
                    <div class="col-lg-10">
                        <div  id="individualCustomerYearChart"  ></div>
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
    </body>
</html>
