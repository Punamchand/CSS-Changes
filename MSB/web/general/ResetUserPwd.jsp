<%-- 
    Document   : ResetUserPassword
    Created on : Feb 3, 2015, 7:50:23 PM
    Author     : Nagireddy
--%>



<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/403.jsp"%>
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
        <title>Miracle ServicesBay :: Reset User Password Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>"><!--this is for all css in profile view -->
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <!-- end of new styles -->
        <style type="text/css">
            label[for="email"] {
                color: #56a5ec;
            }label[for="pwd1"] {
                color: #56a5ec;
            }label[for="pwd2"] {
                color: #56a5ec;
            }
        </style>
    </head>
    <body>
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">

                    <s:include value="/includes/template/header.jsp"/> 
                </div>
            </div><!--/header_top-->

        </header><!--/header-->

        <section id="generalFormDesign"><!--form-->

            <%--<div class="header-middle"><!--header-middle-->
                <div class="container">
                    
                    <div class="row">
                        <s:include value="/includes/menu/generalTopMenu.jsp"/> 
                    </div>
                        
               </div>
            </div> --%>
            <div class="container">
                <div class="row">
                    <s:include value="/includes/menu/LeftMenu.jsp"/> 
                    <div class="col-md-4 col-md-offset-2" style="background-color:#fff">
                        <div class="features_items"><!--features_items-->
                            <div class="col-lg-12 ">
                                <div class="" id="profileBox" style="float: left; margin-top: 5px">

                                    <div class="backgroundcolor" >


                                        <div class="panel-heading">
                                            <h4 class="panel-title">

                                                <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                <font color="#ffffff">Reset user password</font>

                                            </h4>
                                        </div>

                                    </div>



                                    <!-- Start Special Centered Box -->


                                    <div class="" ><!--login form-->
                                        <form id="ResetPassword" action="changeUserPassword" >
                                            <%//if(request.getAttribute(ApplicationConstants.RESULT_MSG)!=null){
                                                //out.println(request.getAttribute(ApplicationConstants.RESULT_MSG));
                                                //}                                
%>
                                            <font style="color: green;font-size: 12px;"><s:property value="#request.resultMessage"/></font>
                                            <span><resetMessage></resetMessage></span>
                                            <div class=" required"> 
                                             <div class="col-lg-5 req_margin">
                                                <label id="labelLevelStatusReq" >Email</label> 
                                             </div>
                                            <div class="col-lg-6 req_margin">
                                                 <s:textfield id="email" name="emailId" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,}"  cssClass="form-control"  placeholder="Email Id" required="true" oninvalid="setCustomValidity('Must be valid email')"   onchange="try{setCustomValidity('')}catch(e){}" onblur="checkEmailIdExistance();" tabindex="2"/>
                                            </div>
                                            <div class="col-lg-5 req_margin"> 
                                                  <label id="labelLevelStatusReq">New Password</label> 
                                            </div>
                                            <div class="col-lg-6 req_margin">
                                                   <s:password name="newpwd" id="pwd1" placeholder="Password"  required="true" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" cssClass="form-control" onchange="form.cnfrmpwd.pattern=this.value"    title="Password must contain at least 6 characters, including UPPER/lowercase and numbers"   tabindex="3"/>
                                             </div>
                                             <div class="col-lg-5 req_margin">
                                                  <label id="labelLevelStatusReq">Confirm Password</label> 
                                             </div>
                                             <div class="col-lg-6 req_margin">
                                                  <s:password name="cnfrmpwd" id="pwd2" placeholder="Confirm Password" required="true" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}"  cssClass="form-control"  oninvalid="setCustomValidity('Please enter the same Password as above')"   onchange="try{setCustomValidity('')}catch(e){}" tabindex="4"/>
                                            </div> 
                                           </div>
                                    <div class="col-sm-12">
                                              <s:submit  cssClass="cssbutton" type="submit" value="submit"/>
                                     </div>
                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- content start -->
            </div>
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