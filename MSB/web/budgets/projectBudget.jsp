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
        <title>Miracle Service Bay ::Projects Budget Page</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/profilediv.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/taskiframe.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/sweetalert.css"/>">
        <%-- <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
             <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">--%>

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <%--script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script--%>
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
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/budgetAjax.js"/>'></script>
        <%--script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script--%>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sweetalert.min.js"/>'></script>




        <script>
            var pager;
            $(document).ready(function(){

                var paginationSize = 10;
                pager = new Pager('projectBudgetTable', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            });
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('projectBudgetTable', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
        </script>
        <script type="text/javascript">
            function sortables_init() {
                // Find all tables with class sortable and make them sortable
                if (!document.getElementsByTagName) return;
                tbls = document.getElementById("projectBudgetTable");
                sortableTableRows = document.getElementById("projectBudgetTable").rows;
                sortableTableName = "projectBudgetTable";
                for (ti=0;ti<tbls.rows.length;ti++) {
                    thisTbl = tbls[ti];
                    if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
                        ts_makeSortable(thisTbl);
                    }
                }
            };
        </script>
    </head>
    <body style="overflow-x: hidden" >
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
                                                <font color="#ffffff">Budget Details</font>
                                            </h4>
                                        </div>
                                    </div>
                                    <span> <br/></span>
                                    <!-- content start -->
                                    <div class="col-sm-12">
                                        <s:form theme="simple" >
                                            <s:hidden name="addEditFlag" id="addEditFlag"/>
                                            <s:hidden name="roleValue" id="roleValue" value="%{roleValue}"/>
                                            <div class="inner-reqdiv-elements">
                                                <div class="row">
                                                    <div class="col-lg-2">
                                                        <label class="" style="color:#56a5ec;">Year: </label>
                                                        <s:textfield cssClass="form-control" id="budgetYear"
                                                                     name="year" value="%{year}"
                                                                     />
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="" style="color:#56a5ec;">Projects: </label>
                                                        <s:select cssClass="SelectBoxStyles form-control" name="project" id="project" headerKey="-1" headerValue="All" list="projectsMap" />
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <label class="" style="color:#56a5ec;">Quarter: </label>
                                                        <s:select cssClass="SelectBoxStyles form-control" name="quarterId" id="quarterId" headerKey="-1" headerValue="All" list="#@java.util.LinkedHashMap@{'Q1':'Q1','Q2':'Q2','Q3':'Q3','Q4':'Q4'}" />
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <label class="" style="color:#56a5ec;">Status: </label>
                                                        <s:select cssClass="SelectBoxStyles form-control" name="status" id="status" headerKey="-1" headerValue="All" list="#@java.util.LinkedHashMap@{'Entered':'Entered','Submited':'Submited','Approved':'Approved','Rejected':'Rejected'}" />
                                                    </div>

                                                </div>
                                                <div class="row">
                                                    <div class="col-lg-2">
                                                        <s:a href="#" cssClass="projectBudget_popup_open" onclick="projectBudgetOverlay('Add');"><input type="button" class="cssbutton_emps form-control" value="Add Budget" style="margin:5px" ></s:a>
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <s:a href="#" ><input type="button" class="cssbutton_emps form-control" value="Search" style="margin:5px" onclick="getProjectBudgetSearch();"></s:a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </s:form>
                                    <span> <br/></span>
                                        <%--<s:submit cssClass="css_button" value="show"/><br>--%>
                                    <div class="col-sm-12">

                                        <s:form>
                                            <div class="emp_Content" id="emp_div" align="center"  >
                                                <span><d></d></span>
                                                <table id="projectBudgetTable" class="responsive CSSTable_task sortable" border="5">
                                                    <tbody>
                                                        <tr>
                                                            <th>Project Name</th>
                                                            <th>Year</th>
                                                            <th>Quarter</th>
                                                            <th class="unsortable">Estimated Budget </th>
                                                            <th class="unsortable">Remaining Budget </th>
                                                            <th>Status</th>
                                                            <th class="unsortable">Comments</th>
                                                            <s:if test="roleValue!='Director'">
                                                                <th class="unsortable">Delete</th>
                                                            </s:if>
                                                        </tr>
                                                        <s:if test="projectBudgetList.size == 0">
                                                            <tr>
                                                                <td colspan="8"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                            </tr>
                                                        </s:if>
                                                        <s:iterator  value="projectBudgetList">
                                                            <tr>
                                                                <s:if test="roleValue!='Director'">
                                                                    <s:if test="status=='Submited'">
                                                                        <td><s:property value="projectName"></s:property></td>
                                                                    </s:if>
                                                                    <s:elseif test="status=='Approved'">
                                                                        <td><s:property value="projectName"></s:property></td>
                                                                    </s:elseif>
                                                                    <s:else>
                                                                        <td><s:a href="#" onclick="projectBudgetDetailsToViewOnOverlay('%{id}');projectBudgetOverlay('Edit');" cssClass="projectBudget_popup_open"><s:property value="projectName"></s:property></s:a></td> 
                                                                        <%--   <td><s:a href="#" onclick="projectBudgetDetailsToViewOnOverlay('%{id}');" cssClass="projectBudget_popup_open"><s:property value="projectName"></s:property></s:a></td>--%>
                                                                    </s:else>
                                                                </s:if>
                                                                <s:if test="roleValue=='Director'">
                                                                    <s:if test="status=='Submited'">
                                                                        <td><s:a href="#" onclick="projectBudgetDetailsToViewOnOverlay('%{id}');projectBudgetOverlay('Edit');" cssClass="projectBudget_popup_open"><s:property value="projectName"></s:property></s:a></td>
                                                                    </s:if>
                                                                    <s:elseif test="status=='Approved'">
                                                                        <td><s:a href="#" onclick="projectBudgetDetailsToViewOnOverlay('%{id}');projectBudgetOverlay('Edit');" cssClass="projectBudget_popup_open"><s:property value="projectName"></s:property></s:a></td>
                                                                    </s:elseif>
                                                                    <s:else>
                                                                        <td><s:property value="projectName"></s:property></td>
                                                                    </s:else>
                                                                </s:if>
                                                                <td><s:property value="year"></s:property></td>
                                                                <td><s:property value="quarterId"></s:property></td>
                                                                <td><s:property value="estimatedBudget"></s:property></td>
                                                                <td><s:property value="remainingBudget"></s:property></td>
                                                                <td><s:property value="status"></s:property></td>
                                                                <td><s:property value="comments"></s:property></td>
                                                                <s:if test="roleValue!='Director'">
                                                                    <s:if test="status=='Submited'">
                                                                        <td><img style="opacity: 0.4;" src="<s:url value="/includes/images/deleteImage.png"/>" height="20" width="25"></td>
                                                                        </s:if>
                                                                        <s:elseif test="status=='Approved'">
                                                                        <td><img style="opacity: 0.4;" src="<s:url value="/includes/images/deleteImage.png"/>" height="20" width="25"></td>
                                                                        </s:elseif>
                                                                        <s:else>
                                                                        <td><s:a href="#" onclick="return doBudgetRecordDelete('%{id}');"><img src="<s:url value="/includes/images/deleteImage.png"/>" height="20" width="25"></s:a></td>
                                                                        </s:else>
                                                                    </s:if>
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
                                                var dashPager = new Pager('projectBudgetTable', 10);
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
                </div>
            </div>        <!-- content end -->






            <div id="projectBudget_popup">
                <div id="projectBudgetBox" class="techReviewPopupStyle">
                    <div class="backgroundcolor">
                        <table>
                            <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Budget Details&nbsp;&nbsp; </font></h4></td>
                            <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="projectBudget_popup_close" onclick="closeProjectBudgetOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                        </table>
                    </div>
                    <%--form start from here --%>

                    <span><e></e></span><br>
                    <label class="headingLabel">Project Details:</label>
                    <div id="reviewalignBox">
                        <div class="inner-techReviewdiv-elements">
                            <label class="popuplabel">Project : </label>
                            <s:select cssClass="techReviewSelectStyle" 
                                      name="oproject" 
                                      id="oproject" 
                                      list="projectsMap" 
                                      />
                            <span class="required">
                            <label class="popuplabel">Year: </label>
                            <s:textfield type="text"
                                         name="oyear"
                                         cssClass="techReviewInputStyle"
                                         id="oyear" 
                                         />
                            </span>
                            <label class="">Quarter:</label>
                            <s:select cssClass="techReviewSelectStyle" 
                                      name="oquarterId" 
                                      id="oquarterId" 
                                      list="#@java.util.LinkedHashMap@{'Q1':'Q1','Q2':'Q2','Q3':'Q3','Q4':'Q4'}" 
                                      />
                        </div>
                    </div>
                    <label class="headingLabel">Budget Details:</label>

                    <div id="reviewalignBox">
                        <div class="inner-techReviewdiv-elements">
                            <span class="required">
                            <label class="">Estimated Budget: </label>
                            <s:textfield type="text"
                                         name="oestimateBudget"
                                         cssClass="techReviewInputStyle"
                                         id="oestimateBudget"
                                         />
                            </span>
                        </div>
                    </div>
                    <label class="headingLabel">Comments:</label>
                    <div id="reviewalignBox">
                        <div class="inner-techReviewdiv-elements">
                            <s:textarea id="ocomments"
                                        name="ocomments"
                                        cssClass="reviewareacss"
                                        type="text"
                                        placeholder="Any comments"
                                        value="" onkeyup="checkCharactersComment(this)"/>
                        </div>
                    </div>
                        <div class="charNum" id="description_feedback"></div>
                    <div class="inner-techReviewdiv-elements">
                        <div id="oLaybuttons">
                            <s:if test="roleValue=='Director'">
                                <div class="pull-right "><s:submit cssClass="cssbutton" onclick="saveBudgetDetails('A');" value="Approve"></s:submit></div>
                                <div class="pull-right "><s:submit cssClass="cssbutton" onclick="saveBudgetDetails('R');" value="Reject"></s:submit></div>
                            </s:if>
                            <s:else>
                                <div class="pull-right "><s:submit cssClass="cssbutton" onclick="saveBudgetDetails('SB');" value="Save&Submit"></s:submit></div>
                                <div class="pull-right "><s:submit cssClass="cssbutton" onclick="saveBudgetDetails('S');" value="Save"></s:submit></div>
                            </s:else>
                        </div>
                    </div>
                </div>
                <%--close of future_items--%>
            </div>
        </section><!--/form-->
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->
        <script>
            ;   sortables_init();
        </script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/popupoverlay.js"/>'></script>
    </body>
</html>
