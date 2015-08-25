<!--
    Author     : Riza
-->
<%@page import="com.mss.msp.acc.projectsdata.ProjectsVTO"%>
<%@page import="com.mss.msp.acc.projectsdata.ProjectsDataHandlerAction"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.mss.msp.util.ApplicationConstants"%>
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
        <title>Miracle Services Bay :: Projects</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/profilediv.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/accountDetails/projects.css"/>">
        <%-- aklakh js single file start --%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <%--script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script--%>
        <%-- aklakh js single file end --%>
        <%-- aklakh css single file start --%>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <%-- aklakh css single file end --%>
        <%-- for date picket start--%>
        <%--script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script--%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/taskOverlay.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>

        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/account/projectOverlays.js"/>'></script>
        <%-- for date picket end--%>

        <sx:head />

    </head>
    <body>
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <s:include value="/includes/template/header.jsp"/>
                </div>
            </div>

        </header>

        <div>
            <section id="generalForm"><!--form-->
                <div class="container">
                    <div class="row">
                        <s:include value="/includes/menu/LeftMenu.jsp"/>
                        <!-- content start -->
                        <div class="col-md-10 col-md-offset-0" style="background-color:#fff">
                            <div class="features_items">
                                <div class="col-lg-12 ">
                                    <div class="" id="" style="float: left; margin-top: 5px">


                                        <div id="projectsPage">
                                            <s:action name="getAccountProjects" executeResult="true" namespace="/"/>
                                        </div>

                                        <!-- content start -->
                                    </div>
                                    <%--close of future_items--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- content end -->
            </section><!--/form-->
        </div>
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div class="container">
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->
        <script>
            ;
            
            function searchProjects(){
                $.ajax({url:"<%=request.getContextPath()%>/mainProjectsSearch.action"
                        +"?projectReqSkillSet=" + document.getElementById("projectReqSkillSet").value
                        +"&projectName=" + document.getElementById("projectName").value
                        +"&projectStartDate=" + document.getElementById("projectStartDate").value
                        +"&projectTargetDate=" + document.getElementById("projectTargetDate").value
                        +"&accountID=<s:property value='accountID'/>"
                        +"&projectType="+"MP"
                    ,
                    success: function(data){
                        console.log('testing');
                        window.setTimeout("pagerOption();", 1000);
                        window.setTimeout("doOnLoad();", 1000);
                        $(projectsPage).children().remove();
                        $("#projectsPage").html(data);
                    },
                    type: 'GET'
                });
            };
            function addProject(){
                projName = document.getElementById("projectNamePopup").value;
                $.ajax({url:"<%=request.getContextPath()%>/addMainProject.action"
                        +"?projectName=" + document.getElementById("projectNamePopup").value
                        +"&project_status=" + document.getElementById("project_statusPopup").value
                        +"&projectStartDate=" + document.getElementById("projectStartDateOverlay").value
                        +"&projectTargetDate=" + document.getElementById("projectTargetDateOverlay").value
                        +"&projectReqSkillSet=" + document.getElementById("projectReqSkillSetPopup").value
                        +"&project_description=" + document.getElementById("project_descriptionPopup").value
                        +"&accountID=<s:property value="accountID"/>"
                    ,
                   // success: function(data){
                    //    $(document).children().remove();
                      //  document.write(data);
                    //},
                    success: function(data){
                    $("#addProjectValidation").html("<font color='green'><b>Project inserted successfully.</b></font>");
                    $("#projects").html(data);
                    resetOverlayForm();
            },
                    type: 'GET'
                });
            };
            function resetOverlayForm(){
                document.getElementById("overlayForm").reset();
                $("#projectNameError").html("Project name is valid.");
                document.getElementById("projectNameError").style.display = "none";
            };
        </script>
    </body>
</html>
