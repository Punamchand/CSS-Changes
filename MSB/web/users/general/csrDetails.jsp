<%-- 
    Document   : CSR Details
    Created on : July 16, 2015, 7:50:23 PM
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
        <title>Miracle Service Bay :: CSR Details Page</title>

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
        <script language="JavaScript" src="<s:url value="/includes/js/account/accountDetailsAJAX.js"/>" type="text/javascript"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script>
        <script>
            var pager;
            function onLoad(){
                //alert("onload")
                var paginationSize = parseInt(document.getElementById("paginationOption").value);
                // alert(paginationSize);
                pager = new Pager('csrAccountResults', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            };
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('csrAccountResults', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
        </script>
        <script type="text/javascript">
            function sortables_init() {
                // Find all tables with class sortable and make them sortable
                if (!document.getElementsByTagName) return;
                tbls = document.getElementById("csrAccountResults");
                sortableTableRows = document.getElementById("csrAccountResults").rows;
                sortableTableName = "csrAccountResults";
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
                                                <font color="#ffffff">CSR Accounts</font>
                                                <s:url var="myUrl" action="csrList.action">
                                                </s:url>
                                                <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                            </h4>
                                        </div>

                                    </div>
                                    <!-- content start -->
                                    <div class="col-sm-12">

                                        <div>  <label class="labelStylereq" style="color:#56a5ec;">CSR Name: </label>
                                            <span style="color: #0c0"><s:property value="csrName"/></span>
                                        </div>
                                            <s:hidden name="csrUserId" id="csrUserId" value="%{userId}"/>  
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;">Account Name: </label>
                                            <s:textfield id="accountName"
                                                         cssClass="form-control"
                                                         type="text"
                                                         name="accountName"
                                                         placeholder="Account Name"/> 
                                        </div>
                                        <div class="col-lg-4">
                                                <label class="labelStylereq" style="color:#56a5ec;">Status: </label>
                                                <s:select  id="csrStatus"
                                                           name="csrStatus"
                                                           cssClass="SelectBoxStyles form-control"
                                                           headerKey="-1"  
                                                           theme="simple"
                                                           list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'Inactive','All':'All'}"
                                                           />
                                            </div>
                                        <br>

                                        <div class="col-lg-4">
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                    <span class="pull-right"><s:submit type="submit"
                                                              cssClass="cssbutton_emps form-control"
                                                              value="Search" cssStyle="margin:5px" onclick="return accountSearch();"/></span>
                                                </div>

                                            </div>
                                        </div>
                                        <%--<s:submit cssClass="css_button" value="show"/><br>--%>
                                        <div class="col-sm-12">

                                            <s:form>
                                                <div class="emp_Content" id="emp_div" align="center" style="display: none"    >
                                                    <table id="csrAccountResults" class="responsive CSSTable_task sortable" border="5" cell-spacing="2">
                                                        <tbody>
                                                            <tr>
                                                                <th>Account Name</th>
                                                                <th class="unsortable">Status</th>
                                                            </tr>
                                                            <s:if test="userVTO.size == 0">
                                                                <tr>
                                                                    <td colspan="5"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                                </tr>
                                                            </s:if>
                                                            <s:iterator value="userVTO">
                                                                <tr>
                                                                    <s:hidden id="userId" name="userId" value="empId"/>
                                                                    <s:hidden id="orgId" name="orgId" value="orgId"/> 
                                                                    <td><s:property value="accountName"></s:property></td>
                                                                    <%--<td><s:property value="email1"></s:property></td>--%>
                                                                    <td><s:a href="#" cssClass="csrTerminateOverlay_popup_open" onclick="csrTerminateOverlay();csrStatusValue('%{orgId}','%{empId}')"><s:property value="cur_status"></s:property></s:a></td>
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
            <%-- Start overlay for csr termination --%>
            <div id="csrTerminateOverlay_popup" >
                <div id="csrTerminateBox">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor" >&nbsp;&nbsp;Change Status &nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="csrTerminateOverlay_popup_close" onclick="csrTerminateOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div>
                        <div class="inner-reqdiv-elements">
                            <s:hidden id="overlayUserId" name="csrUserId"/>
                            <s:hidden id="overlayOrgId" name="csrOrgId"/>
                            <div id="outputMessageOfUpdate"></div>
                            <div class="col-lg-10">
                                <label class="labelStylereq" style="color:#56a5ec;">Status: </label>
                                <s:select  id="status"
                                           name="status"
                                           cssClass="SelectBoxStyles form-control"
                                           headerKey="-1" 
                                           theme="simple"
                                           list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'Inactive'}"
                                           />
                            </div>
                        </div>

                        <div class="pull-left " >
                            <s:submit type="button" cssClass="cssbutton csrTerminateOverlay_popup_close" id="contactCancel" onclick="csrTerminateOverlay()" value="Cancel"/>  
                        </div>  
                        <div class="pull-right " > 

                            <s:submit type="button" cssClass="cssbutton" id="contactSend" value="Ok" onclick="changeCsrAccountStatus()"/> 

                        </div>

                    </div>
                    <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                </div>   
            </div> 

            <%-- end overlay for csr termination --%>

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
    </body>
</html>


