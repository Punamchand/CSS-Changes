<%--
    Document   : AccountDetails
    Created on : May 3, 2015, 2:08:50 PM
    Author     : rama krishna<lankireddy@miraclesoft.com>
--%>

<%@page import="com.mss.msp.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: SOW Edit Page</title>

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
        <script type="text/javascript" src="<s:url value="/includes/js/fileUploadScript.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/jquery.form.js"/>"></script>
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
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/GeneralAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/techReviewAjax.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/includes/js/Ajax/sowAjax.js"/>"></script>

        <script>
            var pager;
            
            function pagerOption(){
                //alert("HI")
                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                //alert(paginationSize);

                pager = new Pager('sowResults', parseInt(paginationSize));
            pager.init();
            pager.showPageNav('pager', 'pageNavPosition');
            pager.showPage(1);

        };
        function attachPagerOption(){
            //alert("HI")
            paginationSize = document.getElementById("attachPaginationOption").value;
            if(isNaN(paginationSize))
            //alert(paginationSize);

            pager = new Pager('sowAttachment', parseInt(paginationSize));
        pager.init();
        pager.showPageNav('pager', 'pageNavPosition');
        pager.showPage(1);

    };
        </script>
    </head>
    <body>
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

                            <div class="col-lg-14 ">
                                <ul class="nav nav-tabs">
                                    <li class="active_details" id="edit" ><a aria-expanded="false" href="#SOWEdit" data-toggle="tab">SOW Edit</a>
                                    </li>
                                    <li class="active_details" id="Attachments" ><a aria-expanded="false" href="#attachments" data-toggle="tab">Attachments</a>
                                    </li>
                                </ul>

                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="SOWEdit">
                                        <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                            <div class="backgroundcolor" >
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <font color="#ffffff">SOW Edit</font>
                                                        <s:url var="myUrl" action="getSowList.action">
                                                            <%--<s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> --%>
                                                        </s:url>
                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                                            <%--<span class="pull-right"><s:a href='%{#contechReqEditUrl}'><img  src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>--%>
                                                    </h4>
                                                </div>
                                            </div>

                                            <s:form action="/invoice/sow/SOWInsertAction" onsubmit="return sowValidation();" theme="simple">

                                                <s:hidden name="consultantId" id="consultantId" value="%{consultantId}"/>
                                                <s:hidden name="requirementId" id="requirementId" value="%{requirementId}"/>
                                                <s:hidden name="customerId" id="customerId" value="%{customerId}"/>
                                                <s:hidden name="vendorId" id="vendorId" value="%{vendorId}"/>
                                                <s:hidden name="consultantName" id="consultantName" value="%{consultantName}"/>
                                                <s:hidden name="vendorName" id="vendorName" value="%{vendorName}"/>
                                                <s:hidden name="requirementName" id="requirementName" value="%{requirementName}"/>
                                                <s:hidden name="customerName" id="customerName" value="%{customerName}"/>


                                                <div class="inner-reqdiv-elements">
                                                    <span id="errorSpan"><e></e></span>

                                                    <div class="row">
                                                        <div class="col-lg-4">
                                                            <label class="labelStylereq" style="color:#56a5ec">Consultant Name:</label>
                                                            <s:textfield cssClass="form-control" id="consultantName"  value="%{consultantName}" disabled="true"/>
                                                        </div>

                                                        <s:if test="#session.typeOfUsr == 'AC'">
                                                            <div class="col-lg-4">
                                                                <label class="labelStylereq" style="color:#56a5ec">Vendor Name:</label>
                                                                <s:textfield cssClass="form-control" id="vendorName" value="%{vendorName}" disabled="true"/>
                                                            </div>
                                                        </s:if>
                                                        <s:if test="#session.typeOfUsr == 'VC'">
                                                            <div class="col-lg-4">
                                                                <label class="labelStylereq" style="color:#56a5ec">Customer Name:</label>
                                                                <s:textfield cssClass="form-control" id="customerName"  value="%{customerName}" disabled="true"/>
                                                            </div>
                                                        </s:if>

                                                        <div class="col-lg-4">
                                                            <label class="labelStylereq" style="color:#56a5ec">Requirement Name:</label>
                                                            <s:textfield cssClass="form-control" id="requirementName"  value="%{requirementName}" disabled="true"/>
                                                        </div>
                                                        <div class="col-lg-4 required">
                                                            <label class="labelStylereq" style="color:#56a5ec">Pay Rate:</label>
                                                            <s:textfield cssClass="form-control" id="rateSalary"  name="rateSalary" value="%{rateSalary}" >
                                                                <span class="urlDomain">$/Hr</span>
                                                            </s:textfield>
                                                        </div>
                                                        <%--<div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec">Pay Mode:</label>
                                                        <s:select id="payType" cssClass="SelectBoxStyles form-control" name="payType" list="#@java.util.LinkedHashMap@{'ch':'Cheque','NEFT':'NEFT'}"/>
                                                    </div>--%>
                                                        <div class="col-lg-4 required">
                                                            <label class="labelStylereq" style="color:#56a5ec">Status:</label>
                                                            <s:if test="#session.typeOfUsr == 'VC'">
                                                                <s:if test="status == 'SOWApproved'">
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWCreated':'Created','SOWApproved':'Approved','SOWRejected':'Rejected','SOWSubmitted':'Submitted','SOWDenied':'Denied'}" value="%{status}"/>
                                                                </s:if>
                                                                <s:elseif test="status == 'SOWRejected'">
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWCreated':'Created','SOWRejected':'Rejected','SOWSubmitted':'Submitted','SOWDenied':'Denied'}" value="%{status}"/>
                                                                </s:elseif>
                                                                <s:elseif test="status == 'SOWReleased'">
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWCreated':'Created','SOWRejected':'Rejected','SOWSubmitted':'Submitted','SOWDenied':'Denied','SOWReleased':'Released'}" value="%{status}"/>
                                                                </s:elseif>
                                                                <s:else>
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'Selected':'Selected','SOWCreated':'Created','SOWSubmitted':'Submitted','SOWDenied':'Denied'}" value="%{status}"/>
                                                                </s:else>
                                                            </s:if>
                                                            <s:if test="#session.typeOfUsr == 'AC'">
                                                                <s:if test="status == 'SOWReleased'">
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWReleased':'Released'}" value="%{status}"/>
                                                                </s:if>
                                                                <s:elseif test="status == 'SOWApproved'">
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWSubmitted':'Submitted','SOWApproved':'Approved','SOWRejected':'Rejected','SOWDenied':'Denied','SOWReleased':'Released'}" value="%{status}"/>
                                                                </s:elseif>
                                                                <s:elseif test="status == 'SOWDenied'">
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWDenied':'Denied'}" value="%{status}"/>
                                                                </s:elseif>
                                                                <s:else>
                                                                    <s:select id="status" cssClass="SelectBoxStyles form-control" name="status" list="#@java.util.LinkedHashMap@{'SOWSubmitted':'Submitted','SOWApproved':'Approved','SOWRejected':'Rejected','SOWDenied':'Denied'}" value="%{status}"/>
                                                                </s:else>

                                                            </s:if>
                                                        </div>
                                                        <div class="col-lg-4 required">
                                                            <label class="labelStylereq" style="color:#56a5ec">Net Terms:</label>
                                                            <s:select id="payTerms" cssClass="SelectBoxStyles form-control " name="payTerms" list="#@java.util.LinkedHashMap@{'15':'15','30':'30','45':'45','60':'60','90':'90'}" value="%{payTerms}">
                                                                <span class="paymentDoller">Days</span>
                                                            </s:select>
                                                        </div>
                                                        <s:if test="#session.typeOfUsr == 'VC'">
                                                            <div class="col-lg-12">
                                                                <label class="labelStylereq" style="color:#56a5ec">Vendor Comments:</label>
                                                                <s:textarea name="vendorComments" id="vendorComments" cssClass="form-control"/>
                                                            </div>
                                                            <div class="col-lg-12">
                                                                <label class="labelStylereq" style="color:#56a5ec">Customer Comments:</label>
                                                                <s:textarea name="customerComments" id="customerComments" cssClass="form-control" readonly="true"/>
                                                            </div>
                                                        </s:if>
                                                        <s:if test="#session.typeOfUsr == 'AC'">
                                                            <div class="col-lg-12">
                                                                <label class="labelStylereq" style="color:#56a5ec">Vendor Comments:</label>
                                                                <s:textarea name="vendorComments" id="vendorComments" cssClass="form-control" readonly="true"/>
                                                            </div>
                                                            <div class="col-lg-12">
                                                                <label class="labelStylereq" style="color:#56a5ec">Customer Comments:</label>
                                                                <s:textarea name="customerComments" id="customerComments" cssClass="form-control" />
                                                            </div>
                                                        </s:if>

                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-2 pull-right">
                                                            <label class="labelStylereq" style="color:#56a5ec"></label>
                                                            <s:if test="#session.typeOfUsr == 'AC'">
                                                                <s:if test="status == 'SOWReleased'"></s:if>
                                                                <s:else>
                                                                    <s:submit  cssClass="cssbutton_emps form-control pull-right" type="submit" value="Submit" cssStyle="margin:5px 0px 0px"/>
                                                                </s:else>
                                                            </s:if>
                                                            <s:else>
                                                                <s:if test="status == 'SOWApproved'"></s:if>
                                                                <s:elseif test="status == 'SOWSubmitted'"></s:elseif>
                                                                <s:elseif test="status == 'SOWReleased'"></s:elseif>
                                                                <s:else>
                                                                    <s:submit  cssClass="cssbutton_emps form-control pull-right" type="submit" value="Submit" cssStyle="margin:5px 0px 0px"/>
                                                                </s:else>
                                                            </s:else>

                                                        </div>
                                                    </div>
                                                </div>
                                            </s:form>
                                        </div>
                                    </div>
                                    <div id="sowAttachment_popup">
                                        <div id="SOWAttachBox">
                                            <div style="background-color: #3BB9FF ">
                                                <table>
                                                    <tr><td style=""><h4><font color="#ffffff">&nbsp;&nbsp;SOW Attachments Details&nbsp;&nbsp; </font></h4></td>
                                                    <span class=" pull-right"><h5><a href="#" class="sowAttachment_popup_close" onclick="addSOWAttachmentOverLay();"><img src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25"></a>&nbsp;</h5></span>
                                                </table>
                                            </div>
                                            <span id="attachSpan"><attachTag></attachTag></span>
                                            <div id="message"></div>
                                            <br>
                                            <s:form action="addSOWAttachments" id="SOWAttachmentId" onsubmit="return sowAttachmentValidation();" theme="simple" enctype="multipart/form-data" >
                                                <div>
                                                    <div class="inner-addtaskdiv-elements">
                                                        <s:hidden name="consultantId" id="consultantId" value="%{consultantId}"/>
                                                        <s:hidden name="requirementId" id="requirementId" value="%{requirementId}"/>
                                                        <s:hidden name="customerId" id="customerId" value="%{customerId}"/>
                                                        <s:hidden name="vendorId" id="vendorId" value="%{vendorId}"/>
                                                        <s:hidden name="rateSalary" id="rateSalary" value="%{rateSalary}"/>
                                                        <s:hidden name="consultantName" id="consultantName" value="%{consultantName}"/>
                                                        <s:hidden name="customerName" id="customerName" value="%{customerName}"/>
                                                        <s:hidden name="requirementName" id="requirementName" value="%{requirementName}"/>
                                                        <s:hidden name="vendorName" id="vendorName" value="%{vendorName}"/>
                                                        <s:hidden name="status" id="status" value="%{status}"/>

                                                        <s:file name="file" id="file"/>
                                                    </div>
                                                    <%--<s:submit cssClass="cssbutton task_popup_close" value="AddTask" theme="simple" onclick="addTaskFunction();" />--%>
                                                    <center><s:submit cssClass="cssbutton" value="ADD" theme="simple"  /></center><br>
                                                </div>
                                            </s:form>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade in " id="attachments" >
                                        <s:hidden name="tabFlag" id="tabFlag" value="%{tabFlag}"/>
                                        <s:hidden name="uploadRes" id="uploadRes" value="%{uploadRes}"/>
                                        <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                            <div class="backgroundcolor" >
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <font color="#ffffff">Attachments</font>
                                                        <s:url var="myUrl" action="getSowList.action">
                                                            <%--<s:param name="accountSearchID"><s:property value="accountSearchID"/></s:param> --%>
                                                        </s:url>
                                                        <span class="pull-right"><s:a href='%{#myUrl}'><img src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>
                                                            <%--<span class="pull-right"><s:a href='%{#contechReqEditUrl}'><img  src="<s:url value="/includes/images/repeat.png"/>" height="25" width="25"></s:a></span>--%>
                                                    </h4>
                                                </div>
                                            </div>
                                            <s:form>
                                                <div class="row">
                                                    <div class="col-lg-2 pull-right">
                                                        <a href="#" class="sowAttachment_popup_open" onclick="addSOWAttachmentOverLay();"><button type="button" class="cssbutton_emps form-control pull-right">Add Attachment</button></a>
                                                    </div>
                                                </div>
                                                <br>
                                                <span id="uploadSpan"><upload></upload></span>
                                                <s:if test="fileExists=='noFile'">
                                                    <span><font style="color: red">Sorry File Not Exists!</font></span>
                                                    </s:if>
                                                    <s:elseif test="fileExists=='NotFound'">
                                                    <span><font style="color: red">Sorry File Not Found!</font></span>
                                                    </s:elseif>


                                                <s:else>

                                                </s:else>

                                                <div class="task_content" id="task_div" align="center" style="display: none" >
                                                    <div>
                                                        <div>
                                                            <table id="sowAttachment" class="responsive CSSTable_task" border="5"cell-spacing="2">
                                                                <tbody>

                                                                    <tr>
                                                                        <th>Consultant&nbsp;Name</th>
                                                                        <th>Attachment &nbsp;Name</th>
                                                                        <th>Uploaded&nbsp;By</th>
                                                                        <th>Download</th>
                                                                    </tr>

                                                                    <s:if test="sowVTO.size == 0">
                                                                        <tr>
                                                                            <td colspan="4"><font style="color: red;font-size: 15px;">No Records to display</font></td>
                                                                        </tr>
                                                                    </s:if>
                                                                    <s:iterator  value="sowVTO">
                                                                        <tr>
                                                                            <td><s:property value="consultantName"></s:property></td>
                                                                            <td><s:property value="sowAttachmentName"></s:property></td>
                                                                            <td><s:property value="sowAttachmentUploadedBy"></s:property></td>
                                                                                <td><center>
                                                                            <a href="#" onclick=doSOWAttachmentDownload(<s:property value="sowAttachmentId"/>);>    
                                                                            <img src="<s:url value="/includes/images/download.png"/>" height="20" width="20">
                                                                        </a>
                                                                    </center>
                                                                    </td>
                                                                    </tr>        
                                                                </s:iterator>
                                                                </tbody>
                                                            </table>
                                                            <label> Display <select id="attachPaginationOption" onchange="attachPagerOption()" style="width: auto">
                                                                    <option>05</option>
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
                                                    paginationSize = document.getElementById("attachPaginationOption").value; 
                                                    var pager = new Pager('sowAttachment', parseInt(paginationSize)); 
                                                    pager.init(); 
                                                    pager.showPageNav('pager', 'pageNavPosition'); 
                                                    pager.showPage(1);
                                                        </script>
                                                    </div>
                                                </div>
                                            </s:form>
                                        </div>
                                    </div>
                                </div>
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
        <script type="text/javascript">
    var tabFlag=document.getElementById("tabFlag").value;
    var uploadRes=document.getElementById("uploadRes").value;
    if(uploadRes=='S'){
        $("upload").html(" <b><font color='green'>Attachment Uploaded Successfully!</font></b>");
        $("#uploadSpan").show().delay(5000).fadeOut();
    }else if(uploadRes=='F'){
        $("upload").html(" <b><font color='red'>Failed To upload!</font></b>");
        $("#uploadSpan").show().delay(5000).fadeOut();
    }else{
        $("upload").html("");
    }
    if(tabFlag=="AT")
    {
        document.getElementById('Attachments').className='active active_details';
        document.getElementById('SOWEdit').className='tab-pane fade in';
        document.getElementById('attachments').className='tab-pane fade in active';
    }else{
        document.getElementById('edit').className='active active_details';
        document.getElementById('SOWEdit').className='tab-pane fade in active';
    }
    
        </script>
    </body>
</html>