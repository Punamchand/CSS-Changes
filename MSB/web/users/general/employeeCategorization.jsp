<%-- 
    Document   : Employee categorization
    Created on : July 17, 2015, 7:50:23 PM
    Author     : Manikanta
--%>

<%@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Employee categorization Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/profilediv.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/sweetalert.css"/>">
        <%-- <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
             <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">--%>

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script language="JavaScript" src="<s:url value="/includes/js/account/accountDetailsAJAX.js"/>" type="text/javascript"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/GeneralAjax.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/sweetalert.min.js"/>"></script>
        <script>
            var pager;
            function onLoad(){
                //alert("onload")
                var paginationSize = parseInt(document.getElementById("paginationOption").value);
                // alert(paginationSize);
                pager = new Pager('empCategorizationResults', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            };
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('empCategorizationResults', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
        </script>
        <script type="text/javascript">
            function sortables_init() {
                // Find all tables with class sortable and make them sortable
                if (!document.getElementsByTagName) return;
                tbls = document.getElementById("empCategorizationResults");
                sortableTableRows = document.getElementById("empCategorizationResults").rows;
                sortableTableName = "empCategorizationResults";
                for (ti=0;ti<tbls.rows.length;ti++) {
                    thisTbl = tbls[ti];
                    if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
                        ts_makeSortable(thisTbl);
                    }
                }
            };

        </script>
    </head>
    <body style="overflow-x: hidden" onload="onLoad();">
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
                    <div class="col-md-9 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items">
                            <div class="col-lg-14 ">
                                <div class="" id="profileBox" style="float: left; margin-top: 5px">

                                    <div class="backgroundcolor" >
                                        <div class="panel-heading">
                                            <h4 class="panel-title">

                                                <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                <font color="#ffffff">Employee Categorization</font>
                                            </h4>
                                        </div>

                                    </div>
                                    <!-- content start -->
                                    <div class="col-sm-12">
                                        <div class="col-lg-4">
                                            <s:hidden name="teamMemberId" id="teamMemberId" value="0"/>
                                            <label class="labelStylereq" style="color:#56a5ec;">Employee Name: </label>
                                            <s:textfield id="empName"
                                                         cssClass="form-control"
                                                         type="text"
                                                         name="empName"
                                                         placeholder="Employee Name" />
                                            <%--onkeyup="return getTeamMemberNames();" /> --%>
                                            <span id="validationMessage" />
                                        </div>
                                       <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;">Category Group: </label>
                                            <s:select  id="categoryType"
                                                       name="categoryType"
                                                       cssClass="SelectBoxStyles form-control"
                                                       headerKey="-1" 
                                                       headerValue="Select Category"
                                                       theme="simple"
                                                       list="categoryTypes"
                                                       />
                                        </div>
                                       <%-- <div class="col-lg-4">
                                            <s:hidden name="teamMemberId" id="teamMemberId" value="0"/>
                                            <label class="labelStylereq" style="color:#56a5ec;">Employee Name: </label>
                                            <s:textfield id="teamMemberNamePopup"
                                                         cssClass="form-control"
                                                         type="text"
                                                         name="empName"
                                                         placeholder="Employee Name"
                                                         onkeyup="return getTeamMemberNames();" /> 
                                            <span id="validationMessage" />
                                        </div> --%>
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;">Status: </label>
                                            <s:select  id="empStatus"
                                                       name="empStatus"
                                                       cssClass="SelectBoxStyles form-control"
                                                       headerKey="-1"  
                                                       theme="simple"
                                                       list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active','All':'All'}"
                                                       />
                                        </div >
                                        <div class="col-lg-4"></div>
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;"></label> 
                                            <a href='getUserGroping.action'/><input type="button" class="cssbutton" value="Add" style="float: right"/></a>

                                        </div>
                                        <div class="col-lg-4">

                                            <s:submit type="submit"
                                                      cssClass="cssbutton pull-right"
                                                      value="Search"  onclick="return getEmpCategories();"/>

                                        </div>
                                        <%--<s:submit cssClass="css_button" value="show"/><br>--%>
                                        <div class="col-sm-12">

                                            <s:form>
                                                <div class="emp_Content" id="emp_div" align="center" style="display: none"    >
                                                    <table id="empCategorizationResults" class="responsive CSSTable_task sortable" border="5" cell-spacing="2">
                                                        <tbody>
                                                            <tr>
                                                               <th>Name</th>
                                                                <th>Category Group</th>
                                                                <th>Role</th>
                                                               <%-- <th class="unsortable">Is Primary</th> --%>
                                                                <th class="unsortable">Status</th>
                                                                <th class="unsortable">Created By</th>
                                                                <th class="unsortable">Delete</th>
                                                            </tr>
                                                            <s:if test="userVTO.size == 0">
                                                                <tr>
                                                                    <td colspan="7"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                                </tr>
                                                            </s:if>
                                                            <s:iterator value="userVTO">
                                                                <tr>
                                                                    <s:hidden id="groupingId" name="groupingId" value="groupingId"/>
                                                                    <s:hidden id="userId" name="userId" value="empId"/>
                                                                    <s:hidden id="subCategory" name="subCategory" value="subCategory"/>
                                                                    <s:hidden id="catogoryTypeId" name="catogoryTypeId" value="catogoryTypeId"/>
                                                                    <s:url id="myUrl" action="getUserGroping.action">
                                                                        <s:param name="groupingId"><s:property value="groupingId"/></s:param>
                                                                        <s:param name="userId" value="%{empId}" ></s:param> 
                                                                    </s:url>
                                                                    <td><s:a href='%{#myUrl}'><s:property value="empName"></s:property></s:a></td>
                                                                    <td><s:a href="#" cssClass="categorizationOverlay_popup_open" onclick="categorizationOverlay();getEmpCategoryNames('%{subCategory}');"><s:property value="catogoryGroup"></s:property></s:a></td>
                                                                    <td><s:property value="role"></s:property></td>
                                                                    <%-- <td><s:property value="isPrimary"></s:property></td>--%>
                                                                    <td><s:property value="status"></s:property></td>
                                                                    <td><s:property value="createdBy"></s:property></td>
                                                                    <td><s:a href="#" onclick="empCategoryTermination('%{groupingId}');"><img src="<s:url value="/includes/images/delete.png"/>" height="25" width="25"></s:a></td>
                                                                </tr>
                                                            </s:iterator>
                                                        </tbody>
                                                    </table>
                                                    <br/>
                                                    <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
                                                            <option>5</option>
                                                            <option>10</option>
                                                            <option>15</option>
                                                            <option>50</option>
                                                        </select>
                                                        Accounts per page
                                                    </label>
                                                    <div align="right" id="pageNavPosition" style="margin-right: 0vw;"></div>
                                                </s:form>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <%--close of future_items--%>

                            </div>
                        </div>
                    </div>

                </div>

            </div>
             <%-- Start overlay for emp category names --%>
            <div id="categorizationOverlay_popup">
                <div id="categorizationBox">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor" >&nbsp;&nbsp;Assigned Groups &nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="categorizationOverlay_popup_close" onclick="categorizationOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                         <div style="margin: 10px;margin-bottom: -10px"><center>
                            <table id="empCategorizationTableOverlay"  class="CSSTable_task  " border="2" cell-spacing="1" style="overflow-x:auto;overflow-y:hidden;">
                                <tbody>
                                    <tr>
                                    </tr>
                                </tbody>
                            </table>
                        </center>

                    </div>
                    <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                </div>   
            </div> 

            <%-- end overlay for emp category names --%>

            <!-- content end -->
        </section><!--/form-->
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>

        </footer>
        <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>

        <!--/Footer-->
        <script>
            sortables_init();
        </script>
        <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>
    </body>
</html>


