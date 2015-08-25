<%-- 
    Document   : LeavesSearch
    Created on : Apr 13, 2015, 7:25:55 AM
    Author     : NagireddySeerapu
--%>

<%@page import="com.mss.msp.usersdata.UserVTO"%>
<%@page import="com.mss.msp.usersdata.UsersdataHandlerAction"%>
<%@page import="java.util.Iterator"%>

<%--@ page contentType="text/html; charset=UTF-8" errorPage="../../exception/403.jsp"--%>
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
        <title>Miracle Service Bay :: Employee Search Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/ProfilePage.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/taskiframe.css"/>">
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/dhtmlxcalendar.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>">
        <script type="text/JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/ConsultantAjax.js"/>"></script>

        <%-- script >
            $(document).ready(function() {
                var text_max = 20;
                $('#description_feedback').html(text_max + ' characters remaining');

                $('#consult_description').keyup(function() {
                    var text_length = $('#consult_description').val().length;
                    var text_remaining = text_max - text_length;

                    $('#description_feedback').html(text_remaining + ' characters remaining');
                });
            });   
        </script --%>
    </head>
    <body style="overflow-x: hidden" onload="doOnLoadcnslt();">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/> 
                </div>
            </div>

        </header>

        <section id="generalForm" ><!--form-->
            <div class="container">
                <div class="row">
                    <s:include value="/includes/menu/LeftMenu.jsp"/> 
                    <!-- content start -->
                    <div class="col-md-10 col-md-offset-0 " style="background-color:#fff">
                        <div class="features_items">

                            <div class="col-lg-12 ">

                                <div class="backgroundcolor" >
                                    <div class="panel-heading" id="" style="margin-top:6px">
                                        <h4 class="panel-title">
                                            <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                            <font style="color: #fff">Add Consultant</font>
                                            <span class="pull-right"><a href="../../general/changeMyPassword.action"><img onclick="history.back();return false;" src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></a></span>
                                        </h4>
                                    </div>
                                </div>
                                <s:if test="%{consultFlag =='success'}" >
                                    <font style="color: green"> Consultant Added successfully!</font>
                                </s:if>
                                <s:if test="%{consultFlag =='failure'}">
                                    <font style="color:firebrick">  Sorry, record not Added, try again!</font>
                                </s:if> 
                                <!-- Content start -->
                                <s:form action="addConsultantDetails" theme="simple" method="POST" enctype="multipart/form-data" >

                                    <span><addCnsltError></addCnsltError></span><br/>
                                    <span class="status"></span>

                                    <div class="panel-body" id="task-panel">
                                        <div style="margin-left: -13%">   
                                        <div class="col-lg-4 ">
                                            <span class="required">
                                            <label class="labelStylereq" >Email Id:</label>    
                                            <s:textfield cssClass="form-control" name="consult_email" id="consult_email" placeholder="Email Id"   pattern="[^@]+@[^@]+\.[a-zA-Z]{2,}"  required="true" oninvalid="setCustomValidity('Must be valid email')"   onchange="try{setCustomValidity('')}catch(e){}"   onblur="ConsultEmailValidation()" tabindex="1"  maxlength="60"/>                                                
                                            <label class="labelStylereq" >First Name:</label>
                                            <s:textfield cssClass="form-control" name="consult_fstname" id="consult_fstname"  placeholder="First Name" required="true" onkeyup="fnamevalidate()" tabindex="2" maxlength="30"/>
                                           
                                            <label class="labelStylereq" >Date of Birth:</label>
                                            <s:textfield cssClass="form-control" name="consult_dob"  id="consult_dob"   onkeypress="return enterDateRestrict();" onclick="dobValidate()" tabindex="3" placeholder="Date of Birth"/>
                                            </span>
                                            <label class="labelStylereq" >Alternate Email:</label>
                                            <s:textfield cssClass="form-control" name="consult_alterEmail" id="consult_alterEmail" placeholder="Alternate Email Id" onkeyup="consultValidAlterEmail();" tabindex="4" maxlength="60"/>
                                               
                                        </div>
                                            <div class="col-lg-4">
                                                <label class="labelStylereq" >Available Date:</label>
                                                    <s:textfield cssClass="form-control" name="consult_add_date"  id="consult_add_date"  placeholder="Date" onfocus="removeResultMessage()"    onkeypress="return enterDateRestrict();" onclick="availableValidate()" tabindex="1"/>
                                                <label class="labelStylereq" >Middle Name:</label>  
                                                    <s:textfield cssClass="form-control" name="consult_midname" placeholder="Middle Name" id="consult_midname" tabindex="2" maxlength="30"/>
                                                <label class="labelStylereq" >Home Phone:</label>  
                                                    <s:textfield cssClass="form-control" name="consult_homePhone" id="consult_homePhone" placeholder="Home Phone" tabindex="3"/>                                      
                                                <label class="labelStylereq" >SSN No:</label>    
                                                    <s:textfield cssClass="form-control" name="consult_ssnNo" id="consult_ssnNo" placeholder="SSN Number"   tabindex="4" maxlength="20" />
                                        </div>
                                            <div class="col-lg-4">
                                                <label class="labelStylereq" >Available:</label>
                                                    <s:select cssClass="form-control SelectBoxStyles " name="consult_available " id="consult_available" headerKey="" headerValue="Select availabilty" list="#@java.util.LinkedHashMap@{'Y':'Yes','PRM':'No'}"  onchange="availableValidation()" tabindex="1"/>
                                                <span class="required">
                                                <label class="labelStylereq" >Last Name:</label>    
                                                    <s:textfield cssClass="form-control" name="consult_lstname" id="consult_lstname" placeholder="Last Name"   required="true" onkeyup="lnamevalidate()" tabindex="2" maxlength="30"/>
                                                <label class="labelStylereq" >Mobile No:</label>    
                                                    <s:textfield cssClass="form-control" name="consult_mobileNo" id="consult_mobileNo" placeholder="Mobile Number"  required="true" onkeyup="mobValidation()" tabindex="3" />
                                                </span>
                                                <label class="labelStylereq" >Living Country:</label>    
                                                    <s:select cssClass="form-control SelectBoxStyles" name="consult_lcountry"  id="consult_lcountry" headerKey="" headerValue="Select Country" list="consult_WCountry" onchange="lcountryValidation()" tabindex="4"/>
                                        </div>
                                    </div>

                                          <!-- Contact Information , start  -->
                                            <div class="col-lg-6" style="margin-left:-13%">
                                                <span id="updateResultp">Permanent Address Updated successfully</span>
                                                <div id="AddressBox"> 
                                                    <div class="contactInfoDiv">
                                                        <table >
                                                            <tr id="trStyleContact"><td>&nbsp;&nbsp;Permanent Address &nbsp;&nbsp;</td></tr>

                                                        </table>
                                                    </div>
                                                    <br/>
                                                    <div class="col-lg-10 col-md-offset-1">
                                                        <label class="labelStylereq" >Address:</label>
                                                                <s:textfield cssClass="form-control" id="consult_Address" name="consult_Address"  oninvalid="setCustomValidity('Must be valid fn')"  onchange="try{setCustomValidity('')}catch(e){}"  tabindex="5" onclick="disableCurrentAddress();" maxlength="100" />
                                                        <label class="labelStylereq" >Address2:</label>        
                                                                <s:textfield cssClass="form-control" id="consult_Address2" name="consult_Address2" tabindex="5" onclick="disableCurrentAddress();" maxlength="100"/>
                                                        <span class="required">
                                                        <label class="labelStylereq" >City:</label>        
                                                                <s:textfield cssClass="form-control" id="consult_City" name="consult_City" required="true" oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z]{3,}" onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pcityValidation()" tabindex="5" onclick="disableCurrentAddress();" maxlength="20"/>
                                                        <label class="labelStylereq" >Country:</label>        
                                                                <s:select cssClass="form-control SelectBoxStyles" name="consult_Country" id="consult_Country" headerKey="-1" headerValue="Select Country" list="consult_WCountry" onchange="PermanentStateChange()" tabindex="5" onclick="disableCurrentAddress();"/>
                                                        <label class="labelStylereq" >State:</label>        
                                                                <s:select cssClass="form-control SelectBoxStyles" name="consult_State" id="consult_State"  headerKey="-1" headerValue="Select state" list="{}" listValue="getTranslation(value)" onchange="StateChangeValidation()" tabindex="5"/>  <%-- onchange="alert(this.options[this.selectedIndex].innerHTML)"  --%>
                                                        </span>
                                                        <label class="labelStylereq" >Zip:</label>        
                                                                <s:textfield cssClass="form-control" id="consult_Zip" name="consult_Zip" minLength="4"  maxlength="10" oninvalid="setCustomValidity('Must be valid fn')"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pZipValidation()" tabindex="5" onclick="disableCurrentAddress();"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <span id="updateResultc">Current Address Updated successfully</span>
                                                <div id="eduSecondry">
                                                    <div class="contactInfoDiv" >
                                                        <table >
                                                            <tr id="trStyleContact" ><td>&nbsp;&nbsp;Current Address &nbsp;&nbsp;</td></tr>
                                                        </table>
                                                    </div>
                                                    <div id=""  class="col-lg-10 col-md-offset-1">
                                                        <center>
                                                            <table>
                                                                <s:checkbox name="addconsult_checkAddress"  id="addconsult_checkAddress" onclick="FillAddress();"  ></s:checkbox>
                                                                <label class="checkboxLabel" for="addconsult_checkAddress">Same as Permanent Address</label>
                                                                <span><j2></j2></span>
                                                            
                                                            </table>
                                                        </center>
                                                           <label class="labelStylereq" >Address:</label>    
                                                                <s:textfield cssClass="form-control" id="consult_CAddress" name="consult_CAddress"  oninvalid="setCustomValidity('Must be valid fn')"   onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pCAddressValidation()" tabindex="6"  maxlength="100"/>
                                                            <label class="labelStylereq" >Address2:</label>    
                                                                <s:textfield cssClass="form-control" id="consult_CAddress2" name="consult_CAddress2" tabindex="6" maxlength="100"/>
                                                            <span class="required">
                                                            <label class="labelStylereq" >City:</label>    
                                                                <s:textfield cssClass="form-control" id="consult_CCity" name="consult_CCity" required="true" oninvalid="setCustomValidity('Must be valid fn')" pattern="[a-zA-Z]{3,}"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pCCityValidation()" tabindex="6" maxlength="20"/>
                                                            <label class="labelStylereq" >Country:</label> 
                                                                <s:select cssClass="form-control SelectBoxStyles" name="consult_CCountry" id="consult_CCountry" headerKey="-1" headerValue="Select Country" list="consult_WCountry" onchange="CurrentStateChange()" tabindex="6"/>
                                                            <label class="labelStylereq" >State:</label>    
                                                            <s:select cssClass="form-control SelectBoxStyles" id="consult_CState" name="consult_CState"    headerKey="-1" headerValue="Select state" list="{}" onchange="CurrentStateChangeValidation()" tabindex="6"/>
                                                            </span>
                                                            <label class="labelStylereq" >Zip:</label>   
                                                                <s:textfield cssClass="form-control" id="consult_CZip" name="consult_CZip" minLength="4" maxlength="10" oninvalid="setCustomValidity('Must be valid fn')"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pCZipValidation()" tabindex="6"/>
                                                                <%--s:textfield cssClass="form-control" label="Phone" id="consult_CPhone" name="consult_CPhone" required="true" oninvalid="setCustomValidity('Must be valid fn')"  onchange="try{setCustomValidity('')}catch(e){}" onkeyup="pCPhoneValidation()" tabindex="6"/--%>

                                                    </div>
                                                </div>
                                            </div>                
                                        
                                    </div>  



                                    <div class="form-group">
                                        <label  class="task-label" style="max-height:10px;">Education:</label>
                                        <s:textarea cssClass="titleStyle"   id="consult_education"  name="consult_education" maxlength="500" cols="100" rows="2" onkeyup="checkCharactersDescription(this)" tabindex="7"/>

                                    </div>
                                    <div class="panel-body" id="task-panel"> 
                                        <div style="margin-left: -13%">
                                    <div id="description_feedback"></div>
                                        <div  class="col-lg-4 "  >
                                            <span class="required">     
                                            <label class="labelStylereq" >Title:</label>   
                                                    <s:textfield cssClass="form-control" name="consult_jobTitle" id="consult_jobTitle" placeholder="title" required="true" onkeyup="jobtitleValidate()" tabindex="8" maxlength="30"/>                                      
                                                 <label class="labelStylereq" >Industry:</label>   
                                                    <s:select cssClass="form-control SelectBoxStyles " name="consult_industry " id="consult_industry"   headerKey="" headerValue="Select Industry" list="industry"  required="true" onchange="industryValidate()" tabindex="9"/>
                                        </span> 
                                                <label class="labelStylereq" >Rate/Salary:</label>   
                                                    <s:textfield cssClass="form-control" name="consult_salary" id="consult_salary" placeholder="Rate/Salary"   onkeyup="salValidate()" tabindex="10" maxlength="10"/>
                                        </div>
                                        <div class="col-lg-4 " >
                                            <span class="required">
                                            <label class="labelStylereq" >Experience:</label>
                                                    <s:select cssClass="form-control SelectBoxStyles " name="consult_experience " id="consult_experience" headerKey="" headerValue="Select experience" list="experience" required="true" onchange="expValidate()" tabindex="8"/>
                                            </span>
                                            <label class="labelStylereq" >Referred By:</label>       
                                                    <s:textfield cssClass="form-control" name="consult_referredBy" id="consult_referredBy" placeholder="Referred by"  tabindex="9" maxlength="30"/>
                                             <label class="labelStylereq" >Preferred State:</label>       
                                                   <s:select cssClass="form-control SelectBoxStyles" name="consult_preferredState " id="consult_preferredState"   headerKey="-1" headerValue="Select preferred state" list="{}"  onchange="preState()" tabindex="10" />
                                                    <%--<label class="labelStylereq" >Employee Position:</label>
                                                    <s:select cssClass="form-control SelectBoxStyles"  headerKey="0" name="consult_position" id="consult_position" list="#@java.util.LinkedHashMap@{'CRP':'Contract','PRM':'Permanent'}"  tabindex="10"/>--%>
                                        </div>
                                        <div class="col-lg-4  " > 
                                            <span class="required">
                                            <label class="labelStylereq" >Working Country:</label>
                                                   <s:select cssClass="form-control SelectBoxStyles" name="consult_wcountry" id="consult_wcountry"  headerKey="" headerValue="Select Country" list="consult_WCountry" required="true" onchange="workingCountryValidate()" tabindex="8"/>
                                             </span>
                                             <label class="labelStylereq" >Preferred Country:</label>       
                                                   <s:select cssClass="form-control SelectBoxStyles" name="consult_pcountry" id="consult_pcountry" headerKey="-1" headerValue="Select Country" list="consult_WCountry" onchange="ConsultantStateChange()" tabindex="9"/>
                                             
                                        </div>
                                        </div>
                                    </div>
                                        <div class="">           
                                    <div class="form-group required">
                                        <label class="labelStylereq">Upload resume:</label><s:file  name="file" id="file" cssClass=""  tabindex="10" onblur="return resumeValidation();"/>
                                        <span style="color:#4E4E4E">Upload PDF or Doc or Docx file.</span>
                                    </div> 
                                        </div>
                                    <div class="" >
                                        <div class="form-group required">
                                            <label  class="labelStylereq" style="margin:-0px;">Skills:</label>
                                            <s:textarea cssClass="titleStyle "   id="consult_skill" name="consult_skill" maxlength="100" cols="100" rows="1" required="true" tabindex="12" onkeyup="skillValidation()" onkeydown="checkCharactersSkills(this)"/>
                                        </div>
<span id="skills_feedback"></span>
                                    </div> 
                                    <div class="" >
                                        <div class="form-group">
                                            <label  class="labelStylereq" style="margin:-0px;">Comments:</label>
                                            <s:textarea cssClass="titleStyle "   id="consult_comments" name="consult_comments" maxlength="500" cols="100" rows="2" onkeydown="checkCharactersComments(this)" tabindex="14"/>
                                        </div>
                                        <span id="comments_feedback"></span>
                                    </div> 

                                    <div class="" >
                                        <div class="col-lg-2 pull-left">
                                            <s:reset cssClass="cssbutton form-control" type="button" id="clearForm" value="reset" key="reset"  onclick="clearForm()"/>
                                        </div>
                                        <div class="col-lg-2 pull-right ">
                                            <s:submit cssClass=" cssbutton form-control " id="addConsult" value="save" onclick="return addConsultantValidate()"   />
                                        </div>

                                    </div>
</div>
                                </s:form>

                            </div>
                        </div>

                    </div>
                    <!-- Content end  -->
                </div>

            </div>


        </section>    

        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->

        <script type="text/javascript">
   
            $("#consult_homePhone").mask("(999)-999-9999");
            $("#consult_mobileNo").mask("(999)-999-9999");  
            $("#consult_workPhone").mask("(999)-999-9999");
            $("#consult_Phone").mask("(999)-999-9999");  
            $("#consult_CPhone").mask("(999)-999-9999");
            
 
        </script>
    </body>
</html>