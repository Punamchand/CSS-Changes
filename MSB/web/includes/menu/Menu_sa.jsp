<%--
    Document   : empMenu
    Created on : Feb 3, 2015, 8:32:32 PM
    Author     : Nagireddy
--%>

<%@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<!DOCTYPE html>

<script type="text/javascript">
    /*jQuery time*/

</script>

<div class="col-sm-2" style="margin-left: 0%">
    <div class="left-sidebar">

        <div class="panel-group category-products" id="accordian">
            <!--category-products-->
            <ul>
                <li>
                        <h3></span>Accounts</h3>
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/accountadd.action">Add Account</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/searchAccountsBy.action">Accounts&nbsp;Search</a> </li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/general/csrList.action">CSR Search</a></li>   
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/assignedRoles.action">Assign&nbsp;Accounts</a></li>
                        </ul>
                </li>
                <li>
                        <h3></span>Utilities</h3>
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/resetUserPassword.action">Reset User Pwd</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/changeMyPassword.action">Change My Pwd</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/accauth/getAccAuthrization.action">Act. Authorization</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/general/getHomeRedirectDetails.action">Home Redirect</a></li>
                        </ul>
                </li>
                <li>
                        <h3></span>Dashboard</h3>
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/dashboard/dashBoardDetails.action">DashBoard</a></li>
                            <%-- <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/changeMyPassword.action">Change My Pwd</a></li>     --%>
                        </ul>
                </li>
            </ul>
<!--                        
            <div class="panel panel-default left-menu" id="accordian_my">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordian" href="#sportswear">
                            <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                            Accounts
                        </a>
                    </h4>
                </div>
                <div id="sportswear" class="panel-collapse collapse">
                    <div class="panel-body" >
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/accountadd.action">Add Account</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/searchAccountsBy.action">Accounts&nbsp;Search</a> </li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/general/csrList.action">CSR Search</a></li>   
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/acc/assignedRoles.action">Assign&nbsp;Accounts</a></li>

                        </ul>
                    </div>
                </div>
            </div>
                            
            <div class="panel panel-default left-menu" id="accordian_team">
                <div class="panel-heading" >
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordian" href="#mens">
                            <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                            Utilities
                        </a>
                    </h4>
                </div>
                <div id="mens" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/resetUserPassword.action">Reset User Pwd</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/changeMyPassword.action">Change My Pwd</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/accauth/getAccAuthrization.action">Act. Authorization</a></li>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/users/general/getHomeRedirectDetails.action">Home Redirect</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="panel panel-default left-menu" id="accordian_services">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordian" href="#womens">
                            <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                            Dashboard
                        </a>
                    </h4>
                </div>
                <div id="womens" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/dashboard/dashBoardDetails.action">DashBoard</a></li>
                            <%-- <li><a href="/<%=ApplicationConstants.CONTEXT_PATH%>/general/changeMyPassword.action">Change My Pwd</a></li>     --%>
                        </ul>
                    </div>
                </div>
            </div>-->

        </div><!--/category-products-->


    </div>
</div>
