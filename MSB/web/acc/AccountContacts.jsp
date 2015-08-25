<%-- 
    Document   : AccountContacts
    Created on : Apr 26, 2015, 10:25:50 PM
    Author     : Greg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<div class="row">
    <s:form action="doAddAccount" method="post" theme="simple" >
        <div class="col-sm-12">
            <div class="col-sm-4">
                <s:textfield id="firstNameContacts"
                             cssClass="textbox field-margin"  
                             type="text"
                             name="firstName"  
                             placeholder="First Name"/>   
                <s:textfield id="lastNameContacts"
                             name="lastName"
                             cssClass="textbox field-margin inputTextBlue" 
                             theme="simple"
                             type="text" 
                             placeholder="Last Name" />
            </div>
            <div class="col-sm-4">
                <s:textfield id="emailContacts"
                             name="email"
                             cssClass="textbox field-margin inputTextBlue" 
                             theme="simple"
                             type="text" 
                             placeholder="Email" />
                <s:textfield id="titleContacts"
                             cssClass="textbox field-margin" 
                             name="title" 
                             type="text"  
                             placeholder="Title" />
            </div>
            <div class="col-sm-4">
                <s:textfield id="phoneContacts"
                             cssClass="textbox field-margin" 
                             name="phone" 
                             type="text"  
                             placeholder="Phone #" /> 
            </div>
            <s:submit type="submit" 
                      cssClass="cssbutton_emps field-margin" 
                      value="Search"/>
            <span id="validationMessage" />

        </div>
    </s:form>    
</div>
<%--s:select id="status"
                              name="status" 
                              label="Status"  
                              cssClass="selectBoxStyle field-margin" 
                              accesskey=""
                              theme="simple" 
                              list="{'Active','Inactive','Registered'}" /--%>  