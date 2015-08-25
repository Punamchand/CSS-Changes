<%-- 
    Document   : header
    Created on : Feb 3, 2015, 7:52:40 PM
    Author     : Nagireddy
--%>


<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" isErrorPage="true"%>
<%@ page import="com.mss.msp.util.ApplicationConstants"%>
<script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
<script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/general.css"/>">
<!DOCTYPE html>

<%-- <div class="row headerAlingment"></div>--%>
<div class="row">
    <div class="col-sm-6">
        <div class="contactinfo">
            <ul class="nav nav-pills">
                <li><a href=""><img src="<s:url value="/includes/images/logo.jpg"/>" alt="loin" width="260" height="33"/></a></li>
            </ul>
        </div>
    </div>




    <s:if test="#session.userId == null">                                                        
        <div class="col-sm-6" id="col-sm-6">
            <div class="social-icons pull-right">
                <ul class="nav navbar-nav">
                    <%--li><a href="#"><i class="fa"><img src="<s:url value="/includes/images/help.jpg"/>" alt="loin" width="20" height="20"/></a></i></li--%>
                   <%-- <li><a href="<%=request.getContextPath()%>/general/login.action"><i class="fa"><img class="fa" src="<s:url value="/includes/images/login.png"/>" alt="loin" height="20"/></a></i></li>--%>
                </ul>
            </div>
        </div>				
    </s:if>
    <s:else>
        <%--  <s:hidden type="hidden" id="orgId"  name="orgId" value="%{#session.orgId}"  /> ---%>
        <div class="col-sm-6" id="col-sm-6" style="padding-bottom: 0px">
            <div class="social-icons pull-right">
                <ul class="nav navbar-nav">
                    <table>
                        <td> 
                            <s:url id="image" action="rImage" namespace="/renderImage">
                                <s:param name="path" value="%{#session.usrImagePath}"></s:param>
                            </s:url>
                            <img alt="Employee Image" src="<s:property value="#image"/>"  alt="loin" height="40px" width="40px">
                        </td>
                         <td style="white-space: nowrap">
                            <font style="color:#FAF6F6;font-size:15px;font-weight:400;font-family:Roboto,sans-serif">
                            <s:text name="Welcome :"/>
                            </font>
                            <font style="color:#D6E3F2;font-size:15px;font-weight:600;font-family:Roboto,sans-serif">
                            <s:property value="#session.firstName"/>&nbsp;<s:property value="#session.lastName"/>
                            </font>
                            <br>
                           <%-- <%
                                String userType = session.getAttribute(ApplicationConstants.TYPE_OF_USER).toString();
                                //out.println("userType"+userType);
                                if (userType.equalsIgnoreCase("E") || userType.equalsIgnoreCase("SA")) {
                            %>--%>
                            <font style="color:#FAF6F6;font-size:15px;font-weight:400;font-family:Roboto,sans-serif">
                            <s:text name="Role "/>&nbsp;:<%--<s:property value="#session.primaryrolevalue" />--%>
                            </font>
                            <s:select headerKey="0" list="#session.rolesMap" value="#session.primaryrole" theme="simple" id="headSelectBoxStyle" onchange="performAction('/general/roleSubmit.action',this)" /> 

                            <%--<%}%>--%>
                        </td>
                        <td>
                            <%
                                String userConsult = session.getAttribute(ApplicationConstants.TYPE_OF_USER).toString();
                                //out.println("userType"+userType);
                                if (!"CO".equalsIgnoreCase(userConsult)) {
                            %>
                            <%--li><a href="#"><i class="fa"><img src="<s:url value="/includes/images/help.jpg"/>" alt="loin" width="20" height="20"/></a></i></li--%>
                        <li><a href="<%=request.getContextPath()%>/general/logout.action"><i class="fa"><img class="fa" src="<s:url value="/includes/images/lock-logout.png"/>" alt="loin" height="30px" width="30px" title="Logout"/></a></i></li>
                                    <%} else {
                                    %>


                        <li><a href="<%=request.getContextPath()%>/recruitment/consultantLogin/logoutConsultant.action"><i class="fa"><img class="fa" src="<s:url value="/includes/images/lock-logout.png"/>" alt="loin" height="30px" width="30px" title="Logout"/></a></i></li>
                                    <%}%>
                        </td>
                    </table>
                </ul>
            </div>
        </div>
    </s:else>
</div>
