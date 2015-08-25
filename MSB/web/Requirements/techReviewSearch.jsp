<%--
    Document   : AccountDetails
    Created on : May 3, 2015, 2:08:50 PM
    Author     : rama krishna<lankireddy@miraclesoft.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Tech Review search Page</title>

        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/taskiframe.css"/>">

        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/Ajax/AppConstants.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>

        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/EmployeeProfile.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/addLeaveOverlay.js"/>'></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.maskedinput.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/requirementAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/vendorAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/techReviewAjax.js"/>"></script>

    </head>
    <body onload="doOnLoad();">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/>
                </div>
            </div>

        </header>
        <%-- ------------MIDDLE -----------------------------------------%>
        <section id="generalForm"><!--form-->
            <div class="container">
                <div class="row">
                    <s:include value="/includes/menu/LeftMenu.jsp"/>
                    <div class="col-md-10 col-md-offset-0" style="background-color:#fff">
                        <div class="features_items">

                            <div class="" style="float: left; margin-top:2px; margin-bottom: -2px">

                                <s:url var="csrMyUrl" action="acc/viewAccount.action">
                                    <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                    <s:param name="accFlag">accDetails</s:param>
                                </s:url>
                                <s:url var="csrReqUrl" action="acc/viewAccount.action">
                                    <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                    <s:param name="accFlag">reqSearch</s:param>
                                </s:url>

                                <s:url var="csrReqEditUrl" action="Requirements/requirementedit.action">
                                    <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                     <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                    <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                    <s:param name="accountFlag">csr</s:param>
                                    <s:param name="reqFlag">consultantTab</s:param>
                                </s:url>
                                    
                                   <s:url var="custReqUrl" action="recruitment/consultant/getLoginUserRequirementList.action">
                                                 <s:param name="orgid"><s:property value="accountSearchID"/></s:param>
                                                <s:param name="customerFlag">customer</s:param>
                                                <s:param name="accountFlag">MyRequirements</s:param>
                                            </s:url>   
                               <s:url var="custReqEditUrl" action="Requirements/requirementedit.action">
                                    <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                    <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                    <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                    <s:param name="customerFlag">customer</s:param>
                                    <s:param name="reqFlag">consultantTab</s:param>
                                </s:url>



                                <label class="">Path: </label> 
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrMyUrl}' style="color:#FC9A11;"><s:property value="%{accountName}"/></s:a>
                                </s:if>
                                <s:else>
                                    <s:a href='#' style="color:#FC9A11;"><s:property value="%{accountName}"/></s:a>
                                </s:else>
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrReqUrl}' style="color:#FC9A11;">->Requirements List</s:a>
                                </s:if>
                                <s:else>
                                    <s:a href='%{#custReqUrl}' style="color:#FC9A11;">->Requirements List</s:a>        
                                </s:else>
                                <s:if test="accountFlag=='csr'" >
                                    <s:a href='%{#csrReqEditUrl}' style="color:#FC9A11;">-><s:property value="%{jdId}"/></s:a>
                                </s:if>
                                 <s:else>
                                    <s:a href='%{#custReqEditUrl}' style="color:#FC9A11;">-><s:property value="%{jdId}"/></s:a>
                                </s:else>    
                                <span style="color:#FC9A11;">->Tech Review</span>



                            </div>

                            <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                <div class="backgroundcolor" >
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <font color="#ffffff">Tech Review Details</font>
                                       <s:url var="contechReqEditUrl" action="Requirements/requirementedit.action">
                                    <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                    
                                    <s:param name="customerFlag">customer</s:param> 
                                    <s:param name="jdId" ><s:property value="%{jdId}" /></s:param> 
                                    <s:param name="reqFlag">consultantTab</s:param>
                                    <s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param>
                                </s:url>
                                            <span class="pull-right"><s:a href='%{#contechReqEditUrl}'><img  src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                          </h4>
                                    </div>
                                </div>


                                <div id="emailPhoneShow_popup">
                                    <div id="emailPhoneShowOnOverlay" >
                                        <div style="background-color: #3bb9ff ; padding: 0px">
                                            <table>
                                                <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Techie Details&nbsp;&nbsp; </font></h4></td>
                                                </tr>
                                                <span class=" pull-right"><h5><a href="" class="emailPhoneShow_popup_close" onclick="techReviewEmailPhoneOverlay();"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
                                            </table>
                                        </div>
                                        <div>
                                            <form action="#" theme="simple" >
                                                <div>
                                                    <div class="inner-reqdiv-elements">
                                                        <table>
                                                            <span><error></error></span>
                                                            <s:textfield name="email"  label="Email-Id:" id="email"  style="background-color:white;color:black;border:solid 1px #B0B0B0 ;" disabled="true" cssClass="form-control"/>
                                                            <s:textfield name="contactNo"  label="Contact No:" id="contactNo"  style="background-color:white;color:black;border:solid 1px #B0B0B0 ;" disabled="true" cssClass="form-control"/>

                                                        </table>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>


                                <div id="techReviewCommentsOverlay_popup">
                                    <div id="reviewCommentsOverlay">
                                        <div class="backgroundcolor">
                                            <table>
                                                <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Review Comments&nbsp;&nbsp; </font></h4></td>
                                                <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="" class="techReviewCommentsOverlay_popup_close" onclick="techReviewCommentsOverlayJs()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                            </table>
                                        </div>
                                        <div>

                                            <s:textarea name="reviewComments" id="reviewComments"   disabled="true" cssClass="form-control textareaSkillOverlay"/> 


                                        </div>
                                        <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                    </div>
                                </div>



                                <div id="techReviewResults_popup">
                                    <div id="techReviewBoxResults" class="techReviewPopupStyle">
                                        <div class="backgroundcolor">
                                            <table>
                                                <tr><td><h4 style="font-family:cursive"><font class="titleColor">&nbsp;&nbsp;Tech Review Results of Consultant&nbsp;&nbsp; </font></h4></td>
                                                <span class="pull-right"> <h5 >&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="techReviewResults_popup_close" onclick="techReviewResultsOverlay()" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a></h5></span>
                                            </table>
                                        </div>
                                        <%--form start from here --%>
                                        <%--<div class="pull-right "><s:submit cssClass="cssbutton" onclick="saveTechReviewResults();" value="Save"></s:submit></div>--%>
                                        <span><e></e></span><br>
                                        <s:hidden name="consultId" id="consultId"/>
                                        <label class="headingLabel">Consultant Details:</label>
                                        <div id="reviewalignBox">
                                            <div class="inner-techReviewdiv-elements">
                                                <label class="popuplabel">Name : </label>
                                                <s:textfield type="text"
                                                             name="consultantName"
                                                             cssClass="techReviewInputStyle"
                                                             id="consultantName"
                                                             disabled="true"
                                                             />
                                                <label class="popuplabel">Title: </label>
                                                <s:textfield type="text"
                                                             name="consultantJobTitle"
                                                             cssClass="techReviewInputStyle"
                                                             id="consultantJobTitle"
                                                             disabled="true"
                                                             />
                                                <label class="">Email:&nbsp;&nbsp; </label>
                                                <s:textfield type="text"
                                                             name="consultantEmail"
                                                             cssClass="techReviewInputStyle"
                                                             id="consultantEmail"
                                                             disabled="true"
                                                             />
                                            </div>
                                            <div class="inner-techReviewdiv-elements">
                                                <label class="popuplabel">Mobile: </label>
                                                <s:textfield type="text"
                                                             name="consultantMobile"
                                                             cssClass="techReviewInputStyle"
                                                             id="consultantMobile"
                                                             disabled="true"
                                                             />
                                                <label class="popuplabel">Sch.Date:</label>
                                                <s:textfield type="text"
                                                             name="interviewDate"
                                                             cssClass="techReviewInputStyle"
                                                             id="interviewDate"
                                                             disabled="true"
                                                             />
                                                <label class="">Status:</label>
                                                <s:select cssClass="techReviewSelectStyle" 
                                                          name="finalTechReviewStatus" 
                                                          id="finalTechReviewStatus" 
                                                          list="#@java.util.LinkedHashMap@{'Processing':'Processing','Rejected':'Rejected','ShortListed':'ShortListed','Selected':'Selected'}" 
                                                          disabled="true"
                                                          />
                                            </div>

                                        </div>
                                        <label class="headingLabel">Skill Details:</label>

                                        <div id="reviewalignBox">
                                            <div class="inner-techReviewdiv-elements">
                                                <s:textarea type="text"
                                                            name="consultantSkill"
                                                            cssClass="reviewareacss"
                                                            id="consultantSkill"
                                                            disabled="true"
                                                            />
                                            </div>

                                        </div>
                                        <label class="headingLabel">Rating Details:(**Rating between 1(min) to 10(max))</label>

                                        <div id="reviewalignBox">

                                            <div class="inner-techReviewdiv-elements">
                                                <label class="">Technical Skills: </label>
                                                <s:textfield type="text"
                                                             name="techSkill"
                                                             cssClass="ratingInputStyle"
                                                             id="techSkill"
                                                             value=""
                                                             disabled="true"
                                                             />

                                                <label class="">Domain Skills: </label>
                                                <s:textfield type="text"
                                                             name="domainSkill"
                                                             cssClass="ratingInputStyle"
                                                             id="domainSkill"
                                                             value=""
                                                             disabled="true"
                                                             />  
                                                <label class="">Communication Skills:</label>
                                                <s:textfield type="text"
                                                             name="comSkill"
                                                             cssClass="ratingInputStyle"
                                                             id="comSkill"
                                                             placeholder=""
                                                             value=""
                                                             disabled="true"
                                                             /> 

                                            </div>
                                        </div>

                                        <label class="headingLabel">Comments:</label>

                                        <div id="reviewalignBox">

                                            <div class="inner-techReviewdiv-elements">
                                                <s:textarea id="consultantComments"
                                                            name="consultantComments"
                                                            cssClass="reviewareacss"
                                                            type="text"
                                                            placeholder="Any comments"
                                                            value=""
                                                            disabled="true"
                                                            />
                                            </div>
                                        </div>

                                        <font style="color: #ffffff">..................... ..............................  ..........................................</font>
                                    </div>
                                    <%--close of future_items--%>
                                </div>







                                <s:url var="myUrl" action="Requirements/requirementedit.action">
                                    <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                    <s:param name="accountSearchID"><s:property value="%{accountSearchID}"/></s:param> 
                                    <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                    <s:param name="reqFlag">consultantTab</s:param>
                                </s:url>
                                <label class="">Consultant Name:<font style="color: #FF8A14;"><s:a href='%{#myUrl}'><s:property value="%{consult_name}"/></s:a></font></label>
                                    <s:url var="ReqUrl" action="Requirements/requirementedit.action">
                                        <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                        <s:param name="accountSearchID"><s:property value="%{accountSearchID}"/></s:param> 
                                        <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                    </s:url>
                                <label class="pull-right ">Job Title:<font style="color: #FF8A14; pointer-events: hand;"><s:a href='%{#ReqUrl}'><s:property value="%{reqName}"/></s:a></font></label>
                                <div><span id="validationMessage" /> </div>

                                <div class="inner-reqdiv-elements">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec">Interview Date:</label>
                                            <s:textfield cssClass="techReviewInputStyle dateImage" id="searchInterviewDate"  name="searchInterviewDate" onkeyup="return enterDateRepository();"  />
                                        </div>
                                        <s:hidden name="empIdTechReview" id="empIdTechReview" />

                                        <div class="col-lg-4">
                                            <label class="labelStylereq" style="color:#56a5ec">Employee Name:</label>
                                            <s:textfield cssClass="form-control" id="eNameTechReview"  name="eNameTechReview" onkeyup="return getEmpForTechReview();" autocomplete='off'/>
                                        </div>
                                        <div class="col-lg-2">
                                            <label class="labelStylereq" style="color:#56a5ec"></label>
                                            <s:submit id="" cssClass="cssbutton_emps form-control" type="submit" onclick="searchTechReviews();" value="Search" cssStyle="margin:5px 0px 0px"/>
                                        </div>
                                        <s:form action="../Requirements/forwardTechReview.action" id="#" theme="simple" enctype="multipart/form-data" >
                                            <s:hidden name="requirementId" id="requirementId" value="%{requirementId}" />
                                            <s:hidden name="consult_id" id="consult_id" value="%{consult_id}" />
                                            <s:hidden name="accountSearchID" id="accountSearchID" value="%{accountSearchID}" />
                                            <s:hidden name="accountFlag" id="accountFlag" value="%{accountFlag}" />
                                            <s:hidden name="jdId" id="jdId" value="%{jdId}" />

                                            <s:url var="reviewUrl" action="Requirements/forwardTechReview.action">
                                                <s:param name="requirementId"><s:property value="%{requirementId}"/></s:param> 
                                                <s:param name="consult_id" ><s:property value="%{consult_id}" /></s:param> 
                                                <s:param name="accountSearchID"><s:property value="%{accountSearchID}"/></s:param> 
                                                <s:param name="jdId"><s:property value="%{jdId}"/></s:param> 
                                                <s:param name="accountFlag" ><s:property value="%{accountFlag}" /></s:param> 

                                         </s:url>
                                            
                                         <div class="col-lg-2">
                                                <label class="labelStylereq" style="color:#56a5ec"></label>
                                                <%--  <s:submit cssClass="cssbutton form-control" value="Add Review" cssStyle="margin:5px 0px 0px" />--%>
                                                <s:a href='%{#reviewUrl}'><input type="button" class="cssAddReview form-control" value="Add Review" /></s:a>
                                                </div>

                                            <%--<a href="../Requirements/forwardTechReview.action"><input type="button" class="cssbutton " value="Forward Review" /></a> --%>
                                        </s:form>
                                    </div>
                                </div><br>
                                <s:form>

                                    <div class="task_content" id="task_div" align="center" style="display: none" >

                                        <div>
                                            <div>
                                                <table id="techReviewSearchTable" class="responsive CSSTable_task" border="5"cell-spacing="2">
                                                    <tbody>
                                                        <tr>
                                                            <th>Review Type</th>
                                                            <th>Date of Review</th>
                                                            <th>Review By Techie</th>
                                                            <!--<th>Review By Techie2</th>-->
                                                            <th>Comments</th>
                                                            <th>Status</th>
                                                        </tr>
                                                        <s:if test="consultantList ==null">
                                                            <tr>
                                                                <td colspan="6"><font style="color: red;font-size: 15px;">No Records to display</font></td>
                                                            </tr>
                                                        </s:if>
                                                        <s:iterator  value="consultantList">
                                                            <!--build url TO goto Account Details-->

                                                            <s:hidden name="requirementId" id="requirementId" value="%{requirementId}" />
                                                            <s:hidden name="consult_id" id="consult_id" value="%{consult_id}" />
                                                            <s:hidden name="reviewType" id="reviewType" value="%{reviewType}" />
                                                            <s:hidden name="forwardedToId" id="forwardedToId" value="%{forwardedToId}" />
                                                            <tr>
                                                                <td><s:a href="#" onclick="techReviewResultsToViewOnOverlay('%{reviewType}');techReviewResultsOverlay();" cssClass="techReviewResults_popup_open"><s:property value="reviewType"></s:property></s:a></td>
                                                                <%--<td><s:property value="reviewType"></s:property></td>--%>
                                                                <td><s:property value="dateOfReview"></s:property></td>
                                                                <%--<td><s:property value="forwardedToName"></s:property></td>--%>
                                                                <td><s:a href="#" onclick="getMailPhoneOfReviewedBy(%{forwardedToId});techReviewEmailPhoneOverlay();" cssClass="emailPhoneShow_popup_open"><s:property value="forwardedToName"></s:property></s:a></td>

                                                                <%-- <td><s:a href="#" onclick="getMailPhoneOfReviewedBy(%{forwardedToId1});techReviewEmailPhoneOverlay();" cssClass="emailPhoneShow_popup_open"><s:property value="forwardedToName1"></s:property></s:a></td>--%>
                                                                <%--<td><s:property value="techieTitle"></s:property></td>--%>
                                                                <td><s:a href="#" onclick="techReviewCommentsOverlay('%{conTechReviewId}');techReviewCommentsOverlayJs();" cssClass="techReviewCommentsOverlay_popup_open" ><s:property value="%{comments.substring(0,20)}"></s:property></s:a></td>
                                                                <%--<td><s:property value="comments"></s:property></td>--%>
                                                                <td><s:property value="status"></s:property></td>
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
                                                <div align="right" id="pageNavPosition" style="margin: -31px -1px 9px 5px;"></div>
                                            </div>
                                            <script type="text/javascript">
                                                var pager = new Pager('techReviewSearchTable', 8); 
                                                pager.init(); 
                                                pager.showPageNav('pager', 'pageNavPosition'); 
                                                pager.showPage(1);
                                            </script>
                                        </div>
                                    </div>
                                </s:form>
                                <%--</s:form>--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <%-- ------------MIDDLE -----------------------------------------%>
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->
        <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
        <div style="display: none; position: absolute; top:170px;left:320px;overflow:auto; z-index: 1900000" id="menu-popup">
            <table id="completeTable" border="1" bordercolor="#e5e4f2" style="border: 1px dashed gray;" cellpadding="0" class="cellBorder" cellspacing="0" />
        </div>
    </body>
</html>