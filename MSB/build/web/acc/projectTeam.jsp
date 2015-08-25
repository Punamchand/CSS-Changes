<!--
    Author     : Riza
-->

<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>

<!-- new styles -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Miracle Service Bay :: Project Team</title>

<script>
    //           Pagination Script
    var pager;
    function teamPagerOption(){

        paginationSize = document.getElementById("teamPaginationOption").value;
        pager = new Pager('membersSearchResults', parseInt(paginationSize));
        pager.init();
        pager.showPageNav('pager', 'memberPageNavPosition');
        pager.showPage(1);

    };
    //            End Pagination Script

    function getProjectsReportsToList(){
        console.log("calling");
        $.ajax({url:"<%=request.getContextPath()%>/getTeamReportsTo.action",
            success: function(data){
                console.log(data);
                window.setTimeout("teamPagerOption();", 1000);
            },
            type: 'GET'
        });
    }
    
</script>

<div>
    <div id="addProjectTeamMember_popup" class="overlay">
        <div id="addMemberPopupOverlay" >
            <div id="addProjectTeamMemberOverlay">

                <div style="background-color: #3BB9FF ">
                    <table>
                        <tr><td style=""><h4><font color="#ffffff">&nbsp;&nbsp;Add Team Member&nbsp;&nbsp; </font></h4></td>
                        <span class=" pull-right"><h5><a href="" class="addProjectTeamMember_popup_close"><img id="addProjectTeamMemberPopupClose" src="<s:url value="/includes/images/close_button.jpg"/>" height="25" width="25" ></a>&nbsp;</h5></span>
                    </table>
                </div>
                <div style="width:fit-content">
                    <form action="addTeamMemberToProject" theme="simple" id="overlayForm" >
                        <div>
                            <div class="inner-addtaskdiv-elements">
                                <label class="labelStyle field-margin" for="teamMemberNamePopup" style="width: 125px; margin-left: auto">Name</label><s:textfield  cssClass="inputStyle" id="teamMemberNamePopup" type="text" name="teamMemberName" placeholder="Member Name"/>
                                <label class="labelStyle field-margin">Status</label><s:select  id="teamMemberStatusPopup"  name="teamMemberStatus" cssClass="selectstyle " headerKey="-1" headerValue="Select status" theme="simple" list="{'Active','Inactive'}" />
                            </div>
                            <div class="inner-addtaskdiv-elements">
                                <label class="labelStyle field-margin" for="primaryReportingPopup" style="width: 125px; margin-left: auto">Primary Reporting</label><s:textfield  cssClass="inputStyle" id="memberPrimaryReportingPopup" type="text" name="memberPrimaryReporting" placeholder="Reporting to" onkeyup="getProjectsReportsToList();"/>
                                <label class="labelStyle field-margin" for="secondaryReportingPopup" style="width: 125px; margin-left: auto">Secondary Reporting</label><s:textfield  cssClass="inputStyle" id="memberSecondaryReportingPopup" type="text" name="memberSecondaryReporting" placeholder="Reporting to"/>
                            </div>
                            <div  class="inner-addtaskdiv-elements" style="text-align: right">
                                <s:reset cssClass="cssbutton " value="Clear" theme="simple" onclick="resetOverlayForm();"/>
                                <s:submit cssClass="cssbutton" value="Add Team Member" theme="simple" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <section id="generalForm" style="margin-bottom: 1px"><!--form-->
        <div class="container">
            <div class="row">

                <!-- content start -->
                <% int projectId = Integer.parseInt(request.getParameter("projectID").toString());

                %>
                <div class="col-md-9 col-md-offset-0" style="background-color:#fff">
                    <div class="features_items" >
                        <div class="col-lg-12 " >
                            <div class="" id="profileBox" style="margin-top: 5px; width: 90%">

                                <div class="backgroundcolor" >
                                    <div class="panel-heading">
                                        <h4 class="panel-title">

                                            <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                            <font color="#ffffff">Team Members</font>

                                        </h4>
                                    </div>

                                </div>
                                <!-- content start -->

                                <label class="labelStyle" style="color: green; width: auto;margin-left: 20px" id="teamMemberAddResult"><s:property value="projectsActionResponse"/></label>

                                <div class="col-sm-12" id="profileBox" style="margin-top: -20px">
                                    <div >
                                        <div style="width: 100%">
                                            <s:form action="" theme="simple" method="GET" id="teamMemberSearchForm">
                                                <br>
                                                <s:hidden name="projectID" value="%{projectID}" id="projectID" />
                                                <span><emp></emp></span>
                                               <div class="inner-reqdiv-elements">
                                                   <div class="row">
                                                  <div class="col-lg-4">    
                                                      <label class="labelStylereq">TeamMember Name</label>   
                                                   <s:textfield cssClass="form-control" id="teamMemberName" type="text"  placeholder="Team Member Name"/>
                                                  </div>      
                                                  <div class="col-lg-4">       
                                                            <%-- <s:textfield cssClass="textbox" id="teamMemberStatus" type="text" placeholder="Team Member Status"/>--%>
                                                            <label class="labelStylereq">Status:</label>  
                                                            <s:select id="status" name="status"  cssClass="form-control SelectBoxStyles" headerKey="DF" list="#@java.util.LinkedHashMap@{'Active':'Active','In-Active':'In-Active'}"/>
                                                  </div>
                                               
                                                <div class="col-lg-4" style="margin-right: -100px;margin-top: 20px">
                                                   <input type="button" class="cssbutton"  value="Search" onclick="return searchTeamMembers();" align="right"/>
                                                    <a href='acc/setTeamMembersForProject.action?projectID=<%=projectId%>&projectFlag=addMember'><input type="button" class="cssbutton" value="Add Team Member" style="float: right"></a>  
                                                </div>
                                               
                                                </div>
                                                </div>
                                            </s:form>
                                                                             </div>
                                        <div>
                                            <s:form>
                                                <div style="width: fit-content">
                                                            <div class="emp_Content" id="emp_div" align="center" style="width:auto;margin: auto" >
                                                        <table id="membersSearchResults" class="responsive CSSTable_task" border="5" style="width: 100%;margin: auto" >

                                                            <tr>
                                                                <th>Employee Name</th>
                                                                <th>Designation</th>
                                                                <th>Current Status</th>
                                                                <%--<th>Skills</th> --%>
                                                                <th>Reports To</th>
                                                                <%--<th>Reports To 2</th>--%>
                                                                <th>Release</th>
                                                            </tr>
                                                            <s:if test="projectsTeamList ==null || projectsTeamList.size() == 0">
                                                                <tr>
                                                                    <td colspan="9"><font style="color: red;font-size: 15px;">No Records to display</font></td>
                                                                </tr>
                                                            </s:if>

                                                            <s:iterator  value="projectsTeamList">
                                                                <s:hidden id="status" name="status" value="%{status}"/>
                                                                <s:url action="acc/setTeamMembersForProject.action" var="getProjectDetails">
                                                                            <s:param name="projectID"><s:property value="projectID"/></s:param>
                                                                            <s:param name="userID" ><s:property  value="userID"/></s:param>
                                                                           
                                                                           
                                                                    </s:url>
                                                                <tr>
                                                                   
                                                                     <td><s:a href="%{getProjectDetails}"><s:property value="firstName"/>.<s:property value="lastName"/></s:a></td>
                                                                    <td><s:property value="designation"/></td>
                                                                    <td><s:property value="status"/></td>
                                                                    <%--<td><s:property value="skillName"/></td> --%>
                                                                    <td><s:property value="reportsTo1Name"/></td>
                                                                   <%--<td><s:property value="reportsTo2Name"/></td>--%>
                                                                   <s:if test="status=='Active'">
                                                                   <td><a href="#" onclick="EmpReleasefromProject('<s:property value="userID"/>')"><img src="includes/images/release.png" height="25" width="25"></a></td>
                                                                   </s:if>
                                                                   <s:else>
                                                                       <td>Released</td>
                                                                   </s:else>
                                                                </tr>
                                                            </s:iterator>

                                                        </table>
                                                        <br/>
                                                         <label> Showing:
                                                            <select id="teamPaginationOption" onchange="teamPagerOption();" style="width: auto">
                                                                <option>5</option>
                                                                <option>10</option>
                                                                <option>15</option>
                                                            </select>
                                                        </label>
                                                        <div align="right" id="memberPageNavPosition" style="margin-right: 0vw;"></div>
                                                    </div>
                                                </div>
                                            </s:form>
                                            <%--script type="text/javascript">
                                                var pager = new Pager('membersSearchResults', 5); 
                                                pager.init(); 
                                                pager.showPageNav('pager', 'pageNavPosition'); 
                                                pager.showPage(1);
                                            </script--%>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <%--close of future_items--%>
            </div>
        </div>

    </section>
</div>

<script>
    ;
    window.setTimeout("teamPagerOption();", 1000);
    setupAddProjectTeamMemberOverlay();
</script>
