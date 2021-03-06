<%-- 
    Document   : CSR Search
    Created on : july 15, 2015, 7:50:23 PM
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
        <title>Miracle Service Bay ::Home Redirect grid Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/profilediv.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/sweetalert.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/taskiframe.css"/>">
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
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/sweetalert.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/homeRedirectAjax.js"/>"></script>

        <script>
            var pager;
            function onLoad(){
                //alert("onload")
                var paginationSize = parseInt(document.getElementById("paginationOption").value);
                // alert(paginationSize);
                pager = new Pager('homeRedirectTable', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            };
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('homeRedirectTable', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
        </script>

        <script type="text/javascript">
            function sortables_init() {
                // Find all tables with class sortable and make them sortable
                if (!document.getElementsByTagName) return;
                tbls = document.getElementById("homeRedirectTable");
                sortableTableRows = document.getElementById("homeRedirectTable").rows;
                sortableTableName = "homeRedirectTable";
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
                                                <font color="#ffffff">Home Redirect Search</font>
                                            </h4>
                                        </div>
                                    </div>
                                    <!-- content start -->
                                    <div class="col-sm-12">
                                        <%--<s:form action="csrList" theme="simple" >--%>
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;">Account Name: </label>

                                            <s:select  id="accountName"
                                                       name="accountName"
                                                       cssClass="SelectBoxStyles form-control"
                                                       headerKey="-1"
                                                       headerValue="All"
                                                       theme="simple"
                                                       list="accountsMap"
                                                       />
                                        </div>
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;">Type of User:</label>
                                            <s:select  id="typeOfUser"
                                                       name="typeOfUser"
                                                       cssClass="SelectBoxStyles form-control"
                                                       headerKey="-1"
                                                       headerValue="All"
                                                       theme="simple"
                                                       list="#@java.util.LinkedHashMap@{'SA':'Site Admin','AC':'Customer','VC':'Vendor','E':'Employee','CO':'Consultant'}"
                                                       />
                                        </div>
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec;">Primary Role:</label>
                                            <s:select  id="primaryRole"
                                                       name="primaryRole"
                                                       cssClass="SelectBoxStyles form-control"
                                                       headerKey="-1" 
                                                       headerValue="All"
                                                       theme="simple"
                                                       list="rolesMap"
                                                       />
                                        </div>
                                        <div class="col-lg-4"></div>
                                        <div class="col-lg-4"></div>
                                        <div class="col-lg-4">
                                            <div class="row">
                                                <s:url id="homeRedirectUrl" action="doAddOrEditHomeRedirect.action">
                                                    <s:param name="homeRedirectActionId">0</s:param>
                                                </s:url>

                                                <div class="col-lg-6">
                                                    <s:a href='%{#homeRedirectUrl}'><input type="button"  class="cssbutton_emps form-control" value="Add" style="margin:5px"/></s:a> 
                                                    </div>
                                                    <div class="col-lg-6">
                                                        <label class="labelStylereq" style="color:#56a5ec;"></label>
                                                        <span class="pull-right"><s:submit type="submit"
                                                              cssClass="cssbutton_emps form-control"
                                                              value="Search" cssStyle="margin:5px" onclick="getHomeRedirectSearchDetails();"/></span>
                                                </div>
                                            </div>
                                        </div>
                                        <%--</s:form>       --%>
                                        <br>
                                        <%--<s:submit cssClass="css_button" value="show"/><br>--%>
                                        <div class="col-sm-12">

                                            <s:form>
                                                <div class="emp_Content" id="emp_div" align="center" style="display: none"    >
                                                    <table id="homeRedirectTable" class="responsive CSSTable_task sortable" border="5" cell-spacing="2">
                                                        <tbody>
                                                            <tr>
                                                                <th>Action name</th>
                                                                <th>Account Name</th>
                                                                <th>Type of User</th>
                                                                <th>Primary Role</th>
                                                                <th class="unsortable">Description</th>
                                                                <th>Status</th>

                                                            </tr>
                                                            <s:if test="homeVTO.size == 0">
                                                                <tr>
                                                                    <td colspan="4"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                                </tr>
                                                            </s:if>
                                                            <s:iterator value="homeVTO">
                                                                <s:url id="homeRedirectEditUrl" action="doAddOrEditHomeRedirect.action">
                                                                    <s:param name="homeRedirectActionId"><s:property value="homeId"></s:property></s:param>
                                                                </s:url>
                                                                <tr>
                                                                    <s:hidden id="homeId" name="homeId" value="homeId"/>
                                                                    <td><s:a href='%{#homeRedirectEditUrl}'><s:property value="actionName"></s:property></s:a></td>
                                                                    <td><s:property value="accountName"></s:property></td>
                                                                    <td><s:property value="typeOfUSer"></s:property></td>
                                                                    <td><s:property value="roleName"></s:property></td>

                                                                    <s:if test="description.length()>9">    

                                                                        <td><s:a href="#" onclick="homeRedirectCommentsDetailsToViewOnOverlay('%{homeId}');homeRedirectCommentsPopup();" cssClass="homeRedirectComments_popup_open"><s:property value="%{description.substring(0,10)}"></s:property>..</s:a></td>
                                                                    </s:if>
                                                                    <s:else>
                                                                        <td><s:property value="description"></s:property></td>
                                                                    </s:else>
                                                                    <%--<td><s:property value="description"></s:property></td>--%>
                                                                    <td><s:property value="status"></s:property></td>
                                                                    </tr>
                                                            </s:iterator>
                                                        </tbody>
                                                    </table>
                                                    <br/>
                                                    <label> Display <select id="paginationOption" onchange="pagerOption()" style="width: auto">
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



            
                    
                    
            <div id="homeRedirectComments_popup">
                <div id="preskillBox" class="marginTasks">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Comments Details&nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="homeRedirectComments_popup_close" onclick="homeRedirectCommentsPopup();" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <div>

                        <s:textarea name="skillDetails"   id="commentsArea"   disabled="true" cssClass="form-control textareaSkillOverlay"/> 


                    </div>
                    <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                </div>
            </div>






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


