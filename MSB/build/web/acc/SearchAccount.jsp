<%--
    Document   : accountSearch
    Created on : Apr 10, 2015, 11:55:41 AM
    Author     : Kyle Bissell
--%>

<%@ page contentType="text/html; charset=UTF-8" errorPage="../exception/403.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- new styles -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Miracle Service Bay :: Account Search Page</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/font-awesome.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/animate.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/main.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/responsive.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/GridStyle.css"/>">
        <link rel="stylesheet" type="text/css" href='<s:url value="/includes/css/general/profilediv.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar.css"/>' type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/general/dhtmlxcalendar_omega.css"/>' type="text/css">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/demo.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/set1.css"/>">
        <link rel="stylesheet" type="text/css" href="<s:url value="/includes/css/general/normalize.css"/>">

        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.min.js"/>"></script>
        <%--script type="text/JavaScript" src="<s:url value="/includes/js/general/GridNavigation.js"/>"></script--%>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/jquery.scrollUp.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/bootstrap.min.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/main.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/general/jquery.toggle.js"/>"></script>
        <script type="text/JavaScript" src="<s:url value="/includes/js/CountriesAjax.js"/>"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/Ajax/vendorAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/general/sortable.js"/>'></script>
<!--        <script language="text/JavaScript" src="<s:url value="/includes/js/general/classie.js"/>"></script>-->
        
        <script>
                   

  $(document).ready(function(){
      
        'use strict';

            // class helper functions from bonzo https://github.com/ded/bonzo

            function classReg( className ) {
              return new RegExp("(^|\\s+)" + className + "(\\s+|$)");
            }

            // classList support for class management
            // altho to be fair, the api sucks because it won't accept multiple classes at once
            var hasClass, addClass, removeClass;

            if ( 'classList' in document.documentElement ) {
              hasClass = function( elem, c ) {
                return elem.classList.contains( c );
              };
              addClass = function( elem, c ) {
                elem.classList.add( c );
              };
              removeClass = function( elem, c ) {
                elem.classList.remove( c );
              };
            }
            else {
              hasClass = function( elem, c ) {
                return classReg( c ).test( elem.className );
              };
              addClass = function( elem, c ) {
                if ( !hasClass( elem, c ) ) {
                  elem.className = elem.className + ' ' + c;
                }
              };
              removeClass = function( elem, c ) {
                elem.className = elem.className.replace( classReg( c ), ' ' );
              };
            }

            function toggleClass( elem, c ) {
              var fn = hasClass( elem, c ) ? removeClass : addClass;
              fn( elem, c );
            }

            var classie = {
              // full names
              hasClass: hasClass,
              addClass: addClass,
              removeClass: removeClass,
              toggleClass: toggleClass,
              // short names
              has: hasClass,
              add: addClass,
              remove: removeClass,
              toggle: toggleClass
            };

            // transport
            if ( typeof define === 'function' && define.amd ) {
              // AMD
              define( classie );
            } else {
              // browser global
              window.classie = classie;
            }

            })( window );

        </script>
         <script>
                   

                    $(document).ready(function(){
      
       // trim polyfill : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/Trim
				if (!String.prototype.trim) {
					(function() {
						// Make sure we trim BOM and NBSP
						var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
						String.prototype.trim = function() {
							return this.replace(rtrim, '');
						};
					})();
				}

				[].slice.call( document.querySelectorAll( 'input.input__field' ) ).forEach( function( inputEl ) {
					// in case the input is already filled..
					if( inputEl.value.trim() !== '' ) {
						classie.add( inputEl.parentNode, 'input--filled' );
					}

					// events:
					inputEl.addEventListener( 'focus', onInputFocus );
					inputEl.addEventListener( 'blur', onInputBlur );
				} );

				function onInputFocus( ev ) {
					classie.add( ev.target.parentNode, 'input--filled' );
				}

				function onInputBlur( ev ) {
					if( ev.target.value.trim() === '' ) {
						classie.remove( ev.target.parentNode, 'input--filled' );
					}
				}
			})();

        </script>
        <script>
            var pager;
            $(document).ready(function(){

                var paginationSize = parseInt(document.getElementById("paginationOption").value);
                pager = new Pager('accountSearchResults', paginationSize);
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);
            });
            function pagerOption(){

                paginationSize = document.getElementById("paginationOption").value;
                if(isNaN(paginationSize))
                    alert(paginationSize);

                pager = new Pager('accountSearchResults', parseInt(paginationSize));
                pager.init();
                pager.showPageNav('pager', 'pageNavPosition');
                pager.showPage(1);

            };
        </script>


        <script type="text/javascript">
            var myCalendar;

            function doOnLoad(){
                myCalendar = new dhtmlXCalendarObject(["accountLastAccessDate"]);
                myCalendar.setSkin('omega');
                myCalendar.setDateFormat("%m-%d-%Y ");
                var today = new Date();
                var maxPastDate = new Date(today.getFullYear()-3,today.getMonth(),today.getDate());
                myCalendar.setSensitiveRange(maxPastDate, today);
                document.getElementById("accountLastAccessDate").value=overlayDate;
            }
            function sortables_init() {
                // Find all tables with class sortable and make them sortable
                if (!document.getElementsByTagName) return;
                tbls = document.getElementById("accountSearchResults");
                sortableTableRows = document.getElementById("accountSearchResults").rows;
                sortableTableName = "accountSearchResults";
                for (ti=0;ti<tbls.rows.length;ti++) {
                    thisTbl = tbls[ti];
                    if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
                        ts_makeSortable(thisTbl);
                    }
                }
            };

        </script>

    </head>
    <body style="overflow-x: hidden" onload="doOnLoad();">
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div>
                    <s:include value="/includes/template/header.jsp"/>
                </div>
            </div>
        </header>
     <div class="container">
        <div class="row">
        <s:include value="/includes/menu/LeftMenu.jsp"/>
        <section id="generalForm"><!--form-->
            <div  class="container">
                <div class="row">
                    <!-- content start -->
                    <div class="col-md-10 col-md-offset-0" >
                        <div class="features_items">
                            <div class="col-lg-12 ">
                                <div class="" id="profileBox" style="float: left; margin-top: 5px">
                                    <div class="backgroundcolor" >
                                        <div class="panel-heading">
                                            <h4 class="panel-title" style="font-size: 20px;">
                                                <!--<span class="pull-right"><a href="" class="profile_popup_open" ><font color="#DE9E2F"><b>Edit</b></font></a></span>-->
                                                <font color="#ffffff">Account Search</font>
                                            </h4>
                                        </div>
                                    </div>
                                    <!-- content start -->
                                    <div class="col-sm-12" style="background-color: lightgoldenrodyellow;">
                                        <s:form action="searchAccountsBy"  method="post" theme="simple" >
                                            <br>
                                            <%--div class="row">
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="" >Name:</label>
                                                    <s:textfield  cssClass="textbox" label="accountName" id="accountName"
                                                                  type="text" name="account.name" placeholder="Account Name"
                                                                  value="%{account.name}"
                                                                  />
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">URL:</label>
                                                    <s:textfield cssClass="textbox" id="accountUrl" type="text"
                                                                 name="account.url" placeholder="Account Url"
                                                                 value="%{account.url}"/>
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="" >Zip:</label>
                                                    <s:textfield cssClass="textbox" id="accountZip" type="dropdown"
                                                                 name="account.zip" placeholder="Zip"
                                                                 value="%{account.zip}"/>
                                                </div>
                                            </div--%>
                                            <div class="inner-reqdiv-elements">
                                                
                                                <section class="content bgcolor-7">
                                                    <span class="input input--jiro">
                                                         
                                                        <s:textfield cssClass="input__field input__field--jiro" label="accountName" id="accountName"
                                                                      type="text" name="account.name" placeholder="Account Name"
                                                                      value="%{account.name}"/>
					<label class="input__label input__label--jiro" for="accountName">
						<span class="input__label-content input__label-content--jiro">Name</span>
					</label>
				</span>
                                                     <span class="input input--jiro">
					 <s:textfield cssClass="input__field input__field--jiro" id="accountUrl" type="text"
                                                                     name="account.url" placeholder="Account Url"
                                                                     value="%{account.url}"/>
					<label class="input__label input__label--jiro" for="accountUrl">
						<span class="input__label-content input__label-content--jiro">URL</span>
					</label>
				</span>
                                                     <span class="input input--jiro">
					<s:textfield cssClass="input__field input__field--jiro" id="accountZip" type="dropdown"
                                                                     name="account.zip" placeholder="Zip"
                                                                     value="%{account.zip}"/>
					<label class="input__label input__label--jiro" for="accountZip">
						<span class="input__label-content input__label-content--jiro">Zip</span>
					</label>
				</span>
                                                </section>
                                                
<!--                                                <div class="row">
                                   
                                                    <div class="col-lg-4">
                                                     
                                                     <label class="labelStylereq" style="color:#56a5ec;">Name: </label>
                                                        <s:textfield cssClass="css-input" label="accountName" id="accountName"
                                                                      type="text" name="account.name" placeholder="Account Name"
                                                                      value="%{account.name}"/>
                                                    </div>
                                                    
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">URL:</label>                                                  
                                                        <s:textfield cssClass="css-input" id="accountUrl" type="text"
                                                                     name="account.url" placeholder="Account Url"
                                                                     value="%{account.url}"/>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Zip:</label>
                                                        <s:textfield cssClass="css-input" id="accountZip" type="dropdown"
                                                                     name="account.zip" placeholder="Zip"
                                                                     value="%{account.zip}"/>
                                                    </div>
                                                </div>-->
                                            </div>


                                            <%--div class="row">
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">Date:</label>
                                                    <s:textfield cssClass="textbox" id="accountLastAccessDate"
                                                                 type="dropdown" name="account.lastAccessDate"
                                                                 placeholder="Last access date" value="%{account.lastAccessDate}"
                                                                 />
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">Country:</label>
                                                    <s:select  id="Countries" type="dropdown"
                                                               listKey="%{id}" listValue="%{name}"
                                                               name="account.country.id" placeholder="Country"
                                                               value="%{account.country.id}"
                                                               list="countries" label="Country" headerKey="-1"
                                                               headerValue="Select Country"
                                                               cssClass="selectBoxStyle" onchange="javascript: getStates($(Countries).val(),'#accountState')"/>
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">State:</label>
                                                    <s:select  id="accountState" type="dropdown"
                                                               name="account.state.id" placeholder="State"
                                                               value="%{account.state.id}"
                                                               list="accountState" label="State" headerKey="-1"
                                                               listKey="%{id}" listValue="%{name}"
                                                               headerValue="Select State"
                                                               cssClass="selectBoxStyle"/>
                                                </div>
                                            </div--%>
                                            <div class="inner-reqdiv-elements" style="margin-top: 15px">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Date: </label>
                                                        <s:textfield cssClass="css-input dateImage" id="accountLastAccessDate"
                                                                     type="dropdown" name="account.lastAccessDate"
                                                                     placeholder="Last access date" value="%{account.lastAccessDate}"
                                                                     />
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Country:</label>
                                                        <s:select  id="Countries" type="dropdown"
                                                                   listKey="%{id}" listValue="%{name}"
                                                                   name="account.country.id" placeholder="Country"
                                                                   value="%{account.country.id}"
                                                                   list="countries" label="Country" headerKey="-1"
                                                                   headerValue="Select Country"
                                                                   cssClass="SelectBoxStyles css-input" onchange="javascript: getStates($(Countries).val(),'#accountState')"/>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">State:</label>
                                                        <s:select  id="accountState" type="dropdown"
                                                                   name="account.state.id" placeholder="State"
                                                                   value="%{account.state.id}"
                                                                   list="accountState" label="State" headerKey="-1"
                                                                   listKey="%{id}" listValue="%{name}"
                                                                   headerValue="Select State"
                                                                   cssClass="SelectBoxStyles css-input"/>
                                                    </div>
                                                </div>
                                            </div>


                                            <%--div class="row">
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">Type:</label>
                                                    <s:select  id="accountType" type="dropdown"
                                                               name="account.typeId" placeholder="Type"
                                                               value="%{account.typeId}"
                                                               list="types" label="AccountType" headerKey="-1"
                                                               headerValue="Select Account Type"
                                                               cssClass="selectBoxStyle"/>
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">Industry:</label>
                                                    <s:select  id="accountIndustry" type="dropdown"
                                                               name="account.industryId" placeholder="Industry"
                                                               value="%{account.industryId}"
                                                               list="industries" label="Industry" headerKey="-1"
                                                               headerValue="Select Industry"
                                                               cssClass="selectBoxStyle"/>
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="accountLabel" style="">Status:</label>
                                                    <s:select  id="accountStatus" type="dropdown"
                                                               name="account.status" placeholder="Status"
                                                               value="%{account.status}"
                                                               list="accountStatus" label="Status" headerKey="-1"
                                                               headerValue="Select Status"
                                                               cssClass="selectBoxStyle "/>
                                                </div>
                                            </div--%>
                                            <div class="inner-reqdiv-elements" style="margin-top: 15px">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Type: </label>
                                                        <s:select  id="accountType" type="dropdown"
                                                                   name="account.typeId" placeholder="Type"
                                                                   value="%{account.typeId}"
                                                                   list="types" label="AccountType" headerKey="-1"
                                                                   headerValue="Select Account Type"
                                                                   cssClass="SelectBoxStyles css-input"/>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Industry:</label>
                                                        <s:select  id="accountIndustry" type="dropdown"
                                                                   name="account.industryId" placeholder="Industry"
                                                                   value="%{account.industryId}"
                                                                   list="industries" label="Industry" headerKey="-1"
                                                                   headerValue="Select Industry"
                                                                   cssClass="SelectBoxStyles css-input"/>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <label class="labelStylereq" style="color:#56a5ec;">Status:</label>
                                                        <s:select  id="accountStatus" type="dropdown"
                                                                   name="account.status" placeholder="Status"
                                                                   value="%{account.status}"
                                                                   list="accountStatus" label="Status" headerKey="-1"
                                                                   headerValue="Select Status"
                                                                   cssClass="SelectBoxStyles css-input"/>
                                                    </div>
                                                    <div class="col-lg-2">

                                                        <s:submit type="submit" cssClass="cssbutton_emps form-control"
                                                                  value="Search" cssStyle="margin-left:0px"/>
                                                    </div>
                                                </div>
                                            </div>

                                            <%--div class="row">
                                                <div class="col-sm-4"> <s:submit type="submit" cssClass="cssbutton_emps field-margin"
                                                          value="Search" cssStyle="margin:0px"/></div>
                                                <div class="col-sm-4"></div>
                                            </div--%>

                                        </s:form>
                                        <br>
                                        <%--<s:submit cssClass="css_button" value="show"/><br>--%>
                                        <div class="col-sm-12" style="margin-top: 15px">
                                            <s:form>
                                                <s:hidden id="accountSearchID" value="%{id}" ></s:hidden>
                                                    <div class="emp_Content" id="emp_div" align="center" style="display: none"    >
                                                        <table id="accountSearchResults" class="responsive CSSTable_task sortable CSSTableGenerator" border="5">
                                                            <tbody>
                                                                <tr>
                                                                    <th>Name</th>
                                                                    <th class="unsortable">URL </th>
                                                                    <th>Account Type</th>
                                                                    <th class="unsortable">LastAccessBy</th>
                                                                    <th class="unsortable">LastAccessDate</th>
                                                                    <th class="unsortable">State</th>
                                                                    <th>Status</th>
                                                                </tr>
                                                            <s:if test="accounts.size == 0">
                                                                <tr>
                                                                    <td colspan="7"><font style="color: red;font-size: 15px;text-align: center">No Records to display</font></td>
                                                                </tr>
                                                            </s:if>
                                                            <% String flag = "accDetails";%>
                                                            <s:iterator  value="accounts">
                                                                <!--build url TO goto Account Details-->
                                                                <s:url id="accountDetailsUrl" action="viewAccount" namespace="/acc" encode="true">
                                                                    <s:param name="accountSearchID" value="%{id}" />
                                                                    <s:param name="accFlag"><%= flag%></s:param>
                                                                </s:url>
                                                                <tr>
                                                                    <td><s:a href="%{accountDetailsUrl}"><s:property value="name"></s:property></s:a></td>
                                                                    <td><s:a href="#" onclick="window.open('http://%{url}');"><s:property value="url" /></s:a></td>
                                                                    <td><s:property value="type"></s:property></td>
                                                                    <%--<td><s:a href="javascript:getEmpMailPhone(%{lastAccessById});" class="emailPhoneShow_popup_open"><s:property value="lastAccessBy"></s:property></s:a></td>--%>
                                                                    <td><s:a href="#" onclick="getEmpMailPhone(%{lastAccessById});" cssClass="emailPhoneShow_popup_open"><s:property value="lastAccessBy"></s:property></s:a></td>
                                                                    <td><s:property value="lastAccessDateString"></s:property></td>
                                                                    <td><s:property value="state.name"></s:property></td>
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
                                                    <div align="right" id="pageNavPosition" style="margin-right: 0vw;"></div>
                                                </s:form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--close of future_items--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
            <div id="emailPhoneShow_popup">
                <div id="emailPhoneShowBoxOverlay" >
                    <div style="background-color: #3bb9ff ; padding: 0px">
                        <table>
                            <tr><td><h4 style=""><font color="#ffffff">&nbsp;&nbsp;Employee Details&nbsp;&nbsp; </font></h4></td>
                            </tr>
                            <span class=" pull-right"><h5><a href="" class="emailPhoneShow_popup_close" ><img src="<s:url value="/includes/images/close_button.jpg"/>" height="23" style="margin-right:10px" width="23"></a></h5></span>
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
            <!-- content end -->
        </section><!--/form-->
        </div>
     </div>
        <footer id="footer"><!--Footer-->
            <div class="footer-bottom" id="footer_bottom">
                <div>
                    <s:include value="/includes/template/footer.jsp"/>
                </div>
            </div>
        </footer><!--/Footer-->
        <script type="text/javascript" src="<s:url value="/includes/js/general/popupoverlay.js"/>"></script>
        <script>
            sortables_init();
        </script>
    </body>
</html>
