<%--
    Document   : MenuSales
    Created on : Feb 3, 2015, 8:32:32 PM
    Author     : Nagireddy
--%>

<%@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<!DOCTYPE html>

<div class="col-sm-2" >
    <div class="left-sidebar">

        <div class="panel-group category-products" id="accordian">
            <!--category-productsr-->
            <div class="panel panel-default left-menu" id="accordian_my">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordian" href="#sportswear">
                            <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                            Home
                        </a>
                    </h4>
                </div>
                <div id="sportswear" class="panel-collapse collapse">
                    <div class="panel-body" >
                        <ul>
                            <%
                                String usrId = session.getAttribute(ApplicationConstants.USER_ID).toString();
                                String orgId = session.getAttribute(ApplicationConstants.ORG_ID).toString();

                            %>
                            <%--  <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/general/myprofile.action">Profile</a></li>--%>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/accountcontactedit.action?contactId=<%=usrId%>&accountSearchID=<%=orgId%>&flag=customerlogin">Profile</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/timesheets/timesheetSearch.action">Time&nbsp;Sheets</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/tasks/doTasksSearch.action">Tasks</a></li>
                            <%
                                int usrGroupList = Integer.parseInt(session.getAttribute(ApplicationConstants.USER_GROUP_ID).toString());
                                // out.println(usrGroupList.size());
                                if (usrGroupList == 1) {
                            %>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/recruitment/consultant/getMyConsultantSearch.action?consultantFlag=My">Consultant&nbsp;Search</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/recruitment/consultant/addConsultant.action">Add&nbsp;Consultant</a></li>
                            <%}%>

                        </ul>
                    </div>
                </div>
            </div>

            <div class="panel panel-default left-menu" id="accordian_services">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordian" href="#womens">
                            <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                            Utilities

                        </a>
                    </h4>
                </div>
                <div id="womens" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/changeMyPassword.action">Change My Pwd</a></li>
                            <%

                                if (usrGroupList == 1) {
                            %>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/recruitment/consultant/getMyConsultantSearch.action?consultantFlag=Team">Consultant&nbsp;Search</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/recruitment/consultant/getLoginUserRequirementList.action?accountFlag=MyRequirements&orgid=<%=orgId%>&vendor=yes">Requirements</a></li>
                            <% }
                                if (usrGroupList == 2) {
                            %>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/timesheets/getAllTimeSheets.action">Time&nbsp;Sheets</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/invoice/getInvoice.action">Invoice</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/invoice/sow/getSowList.action">SOW&nbspSearch</a></li>
                            <%}%>
                            <s:if test="#session.primaryrole == 9">
                                <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/dashboard/customerDashBoardDetails.action">DashBoard</a></li>
                            </s:if>



                        </ul>
                    </div>
                </div>
            </div>

        </div><!--/category-productsr-->


    </div>
</div>
