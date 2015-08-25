var popup;
var isIE;
function readyStateHandler(req,responseHandler) {
    return function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                document.getElementById("loadActMessage").style.display = 'none';
                responseHandler(req.responseText);
            }
        }
        else {
            document.getElementById("loadActMessage").style.display = 'block';
        //alert("HTTP error ---"+req.status+" : "+req.statusText);
        }
    }
}

function initRequest(url) {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
    else
    if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    
}

function forgotPassword() {
    //alert("In New ForGot Password");
    //  alert("in showwiehht method");
    document.getElementById('resultMessage').innerHTML="";
    var email=(document).getElementById('forgotEmailId').value;
    document.getElementById('Loading').style.display = "";
    // alert("Email-->"+email+"---"+ValidateEmailForgotPwd(email));
    
    if(ValidateEmailForgotPwd(email)){
        var url=CONTENXT_PATH+"/forgotPassword.action?emailId="+email;
        // alert("url-->"+url);
        var req = initRequest(url);
        req.onreadystatechange = function() {
            loadingContent();
            if (req.readyState == 4) {
                if (req.status == 200) {
                    document.getElementById('Loading').style.display = 'none';
                    forgotpasswordResponsehandler(req.responseText);
                } 
            }
        };
        //  alert(url)
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }else{
        // alert("hello");
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter valid email id!!</font>";
    }
}

function forgotpasswordResponsehandler(response){
    //respone;
    //alert("response-->"+response);
    //document.getElementById('Loading').style.display = "none";
    document.getElementById('Loading').innerHTML = " ";
    document.getElementById('resultMessage').innerHTML = response;
}

function loadingContent(){
    document.getElementById('Loading').textContent="Loading....."; 
    
}


function ValidateEmailForgotPwd(inputText)  
{   
    //alert("ValidateEmail");
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,})+$/;  
    if(inputText.match(mailformat))  
    {  
        // alert("in true");
        return true;  
    }  
    else  
    {  
        return false;  
    }  
} 
//Added By Kiran
function getSecReportNames() {
    // alert("id is"+id)
    var v_empName=(document).getElementById('secondaryReport1').value;
    //var v_empName=id.value;
    //alert("---"+id.valueOf());
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            var url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName;
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        parseCustMessagesSecRep(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}


function getSecReport2Names() {
    // alert("id is"+id)
    var v_empName=(document).getElementById('secondaryReport2').value;
    //var v_empName=id.value;
    //alert("---"+id.valueOf());
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            var url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName;
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        parseCustMessagesSecRep2(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}


function getAllEmpNames() {
    
    var v_empName=(document).getElementById('primaryReport').value;
    //var v_empName=id.value;
    //alert("---"+id.valueOf());
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            var url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName;
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        parseCustMessages(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function clearTable() {
    if (completeTable) {
        completeTable.setAttribute("bordercolor", "white");
        completeTable.setAttribute("border", "0");
        completeTable.style.visible = false;
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = " ";
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable.removeChild(completeTable.childNodes[loop]);
        }
    }
}

function parseCustMessages(responseXML) {
    //alert("hii");
    clearTable();
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            appendCustomer(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("primaryReport"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
}

function parseCustMessagesSecRep(responseXML) {
    //alert("hii");
    clearTable();
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            appendReportingPerson1(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("secondaryReport1"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font> ";
    }
}

function parseCustMessagesSecRep2(responseXML) {
    //alert("hii");
    clearTable();
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            appendReportingPerson2(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("secondaryReport2"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
    }
}

function appendCustomer(empName,loginId) {
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:set_cust('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function appendReportingPerson1(empName,loginId) {
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:set_ReportingPerson1('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function appendReportingPerson2(empName,loginId) {
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:set_ReportingPerson2('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}
function set_cust(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("primaryReport").value =empName;
    document.getElementById("reportsToId").value =loginId;
   
}
function set_ReportingPerson1(empName,loginId){
    clearTable();
    //alert("in set_ReportingPerson1");
    document.getElementById("secondaryReport1").value =empName;
    document.getElementById("empId").value =loginId;
   
}
function set_ReportingPerson2(empName,loginId){
    clearTable();
    //alert("in set_ReportingPerson2");
    document.getElementById("secondaryReport2").value =empName;
    document.getElementById("empId").value =loginId;
   
}
function init() {
    //var menu = document.getElementById("auto-row");
    //var menu1 = document.getElementById("auto-row1");
    autorow = document.getElementById("menu-popup");
    autorow.style.display ="none";
    // autorow1 = document.getElementById("menu-popup");
    // autorow1.style.display ="none";
    //autorow.style.top = getElementY(menu) + "px";
    //autorow1.style.top = getElementY(menu1) + "px";
    var height = document.documentElement.clientHeight - 120;
    //  autorow1.style.height = Math.max(height, 150);
    //  autorow1.style.overflowY = "scroll";
    autorow.style.height = Math.max(height, 150);
    autorow.style.overflowY = "scroll";
    completeTable = document.getElementById("completeTable");
    completeTable.setAttribute("bordercolor", "white");
}
function findPosition( oElement ) {
    if( typeof( oElement.offsetParent ) != undefined ) {
        for( var posX = 0, posY = 0; oElement; oElement = oElement.offsetParent ) {
            posX += oElement.offsetLeft;
            posY += oElement.offsetTop;
        }
        return posX+","+posY;
    } else {
        return oElement.x+","+oElement.y;
    }
}



 



function performAction(action,element) {
    
    url = CONTENXT_PATH+action+"?roleId="+element.value;
    // alert(url)
    document.location = url;
}
    
function getSecondaryAssignedNames(){
    //alert("secondary_assign")
    var taskType=(document).getElementById('taskType').value;
    var v_empName=(document).getElementById('secondaryReport').value;
    //var v_empName=id.value;
    //alert("---"+id.valueOf());
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            var url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName+'&projectID='+taskType;
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        parseSecondaryAssigned(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function parseSecondaryAssigned(responseXML) {
    //alert("hii");
    clearTable();
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    

    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
    //alert("hii");
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            appendSecondaryAssigned(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("secondaryReport"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
}

function appendSecondaryAssigned(empName,loginId) {
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setsecondary('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function setsecondary(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("secondaryReport").value =empName;
    document.getElementById("secondaryId").value =loginId;
   
}
   
//function getSecondaryAssignedNames(){
//    // alert("secondary_assign")
//    var v_empName=(document).getElementById('secondaryReport').value;
//    //var v_empName=id.value;
//    //alert("---"+id.valueOf());
//    if (v_empName == "") {
//        clearTable();
//    } else {
//        
//        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
//    
//        if(v_empName.length>=3){
//            var url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName;
//            //alert("url-->"+url);
//            var req = initRequest(url);
//            req.onreadystatechange = function() {
//                if (req.readyState == 4) {
//                    if (req.status == 200) {
//                        parseSecondaryAssigned(req.responseXML);
//                    } else if (req.status == 204){
//                        clearTable();
//                    }
//                }
//            };
//            req.open("GET", url, true);
//            req.send(null);
//        }
//    }
//}
//
//function parseSecondaryAssigned(responseXML) {
//    //alert("hii");
//    clearTable();
//    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
//    if (consultants.childNodes.length > 0) {
//        completeTable.setAttribute("bordercolor", "black");
//        completeTable.setAttribute("border", "0");
//    } else {
//        clearTable();
//    }
//    if(consultants.childNodes.length<10) {
//        // autorow1.style.overflowY = "hidden";
//        autorow.style.overflowY = "hidden";
//    }
//    else {
//        // autorow1.style.overflowY = "scroll";
//        autorow.style.overflowY = "scroll";
//    }
//    var consultant = consultants.childNodes[0];
//    var chk=consultant.getElementsByTagName("VALID")[0];
//    isExist = chk.childNodes[0].nodeValue;
//    if(chk.childNodes[0].nodeValue =="true") {
//        var validationMessage=document.getElementById("validationMessage");
//        validationMessage.innerHTML = "";
//        document.getElementById("menu-popup").style.display = "block";
//        for (loop = 0; loop < consultants.childNodes.length; loop++) {
//            var consultant = consultants.childNodes[loop];
//            var loginId = consultant.getElementsByTagName("EMPID")[0];
//            var empName = consultant.getElementsByTagName("NAME")[0];
//            appendSecondaryAssigned(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
//        }
//        var position;
//        position = findPosition(document.getElementById("secondaryReport"));
//        
//        //var position = findPosition(document.getElementById("assignedToUID"));
//        posi = position.split(",");
//        document.getElementById("menu-popup").style.left = posi[0]+"px";
//        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
//        document.getElementById("menu-popup").style.display = "block";
//    } 
//    if(chk.childNodes[0].nodeValue =="false") {
//        var validationMessage=document.getElementById("validationMessage");
//        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
//    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
//    }
//}
//
//function appendSecondaryAssigned(empName,loginId) {
//    var row;
//    var nameCell;
//    if (!isIE) {
//        row = completeTable.insertRow(completeTable.rows.length);
//        nameCell = row.insertCell(0);
//    } else {
//        row = document.createElement("tr");
//        nameCell = document.createElement("td");
//        row.appendChild(nameCell);
//        completeTable.appendChild(row);
//    }
//    row.className = "popupRow";
//    nameCell.setAttribute("bgcolor", "#fff");
//    var linkElement = document.createElement("a");
//    linkElement.className = "popupItem";
//
//
//    linkElement.setAttribute("href",
//        "javascript:setsecondary('"+empName +"','"+ loginId +"')");
//    linkElement.appendChild(document.createTextNode(empName));
//    linkElement["onclick"] = new Function("hideScrollBar()");
//    nameCell.appendChild(linkElement);
//}
//
//function setsecondary(empName,loginId){
//    clearTable();
//    // alert("in set_cust");
//    document.getElementById("secondaryReport").value =empName;
//    document.getElementById("secondaryId").value =loginId;
//   
//}


//for Technical review emp suggestion code::::::;;



function getEmpForTechReview(){
    var requirementId=$("#requirementId").val();
    //alert(requirementId)
    var url;
    var interview=$("#interview").val();
    var v_empName=(document).getElementById('eNameTechReview').value;
    //var v_empName=id.value;
    //alert("---"+id.valueOf());
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            //            if(interview=="I"){
            //                url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName+'&techReview=TR&requirementId='+requirementId;
            //            alert(url)
            //            }
            //            else{
                
            url=CONTENXT_PATH+"/getExternalEmployeeDetails.action?empName="+v_empName+'&requirementId='+requirementId;
            //alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        parseEmpForTechReview(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function parseEmpForTechReview(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendEmpForTechReview(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("eNameTechReview"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}

function appendEmpForTechReview(empName,loginId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setEmpForTechReview('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function setEmpForTechReview(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("eNameTechReview").value =empName;
    document.getElementById("empIdTechReview").value =loginId;
   
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//for Technical review emp suggestion code::::::;;



function getEmpForTechReview2(){
    var requirementId=$("#requirementId").val();
    //alert(requirementId)
    var url;
    var interview=$("#interview").val();
    var v_empName=(document).getElementById('eNameTechReview2').value;
    //var v_empName=id.value;
    //alert("---"+id.valueOf());
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            //            if(interview=="I"){
            //                url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName+'&techReview=TR&requirementId='+requirementId;
            //            //alert(url)
            //            }
            //            else{
            //                
            url=CONTENXT_PATH+"/getExternalEmployee2Details.action?empName="+v_empName+'&requirementId='+requirementId;
            //alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        parseEmpForTechReview2(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function parseEmpForTechReview2(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendEmpForTechReview2(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("eNameTechReview2"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}

function appendEmpForTechReview2(empName,loginId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setEmpForTechReview2('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function setEmpForTechReview2(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("eNameTechReview2").value =empName;
    document.getElementById("empIdTechReview2").value =loginId;
   
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


function getTeamMemberNames() {
    var resourceType=document.getElementById("resourceType").value;
    var projectID = document.getElementById("projectID").value;
    // alert(resourceType)
    var v_empName=(document).getElementById('teamMemberNamePopup').value;
    //var v_empName=id.value;
    //alert("---"+v_empName);
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=2){
            var url=CONTENXT_PATH+"/getExternalEmployeeDetails.action?empName="+v_empName+'&resourceType='+resourceType+'&projectID='+projectID;
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        parseTeamMemberNames(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function parseTeamMemberNames(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendTeamMemberNames(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("teamMemberNamePopup"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Employee doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}

function appendTeamMemberNames(empName,loginId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setTeamMemberNames('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function setTeamMemberNames(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("teamMemberNamePopup").value =empName;
    document.getElementById("teamMemberId").value =loginId;
   
}
////////////////////////////////////////////for csr auto populate/////////////////////////////////////////////
function getCSRNames(){
    // alert("csr");
    var fromCSR=$("#csrName").val();
    //var toCSR=$("#toCSR").val();
    if(fromCSR =="")
    {
        clearTable();
    }
    else if(fromCSR!=""){
        if(fromCSR.length>=3)
        {
            url=CONTENXT_PATH+"/getCsrNamesAutoPopulate.action?csrName="+fromCSR;
            //       alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        // alert(req.responseXML);
                        parseCsrname(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }    
}

function parseCsrname(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendEmpForCsrname(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("csrName"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  CSR doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}
function appendEmpForCsrname(empName,loginId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setEmpForCsrName('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}
function setEmpForCsrName(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("csrName").value =empName;
    document.getElementById("csrId").value =loginId;
   
}
function getUserNameForCategory(){
    // alert("csr");
    var fromCSR=$("#userName").val();
    //var toCSR=$("#toCSR").val();
    if(fromCSR =="")
    {
        clearTable();
    }
    else if(fromCSR!=""){
        if(fromCSR.length>=3)
        {
            url=CONTENXT_PATH+"/getExternalEmployeeDetails.action?empName="+fromCSR;
            //       alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        // alert(req.responseXML);
                        parseUserNameForCategory(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }    
}

function parseUserNameForCategory(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendUserNameForCategory(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("userName"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  User doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}
function appendUserNameForCategory(empName,loginId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setUserNameForCategory('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}
function setUserNameForCategory(empName,loginId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("userName").value =empName;
    document.getElementById("userId").value =loginId;
    checkIsExitOrNot();
}
function doUserGroupingMethod(){
    var cb1 = $('#usrPrimary').is(':checked');        
    var primary;
    //    alert(cb1)
    if(cb1){
        primary=1;
    }
    else{
        primary=0;
    }
    var userId=document.getElementById("userId").value;
    var usrCatType=document.getElementById("usrCatType").value;
    //  var usrCategoryValue=document.getElementById("usrCategoryValue").value;
    
    var userCatArry = [];    
    $("#usrCategoryValue :selected").each(function(){
        userCatArry.push($(this).val()); 
    });
    //alert(userCatArry)
    
    var usrStatus=document.getElementById("usrStatus").value; 
    var usrDescription=document.getElementById("usrDescription").value;
    var groupingId=document.getElementById("groupingId").value;
    if(allValidateGrouping(userId,usrCatType,userCatArry,usrStatus,usrCatType,groupingId)){
        var url='../general/doUserGroupingMethod.action?userId='+userId+
        '&userCatArry='+userCatArry+
        '&usrStatus='+usrStatus+
        '&groupingId='+groupingId+
        '&usrDescription='+usrDescription+
        '&usrCatType='+usrCatType+
        '&primary='+primary;
        alert(url)
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4) {
                if (req.status == 200) 
                {
                    // alert("success"+req.responseText)
                    if(req.responseText=="Something were Wrong!"){
                        $("userGrouping").html(" <b><font color='red'>"+req.responseText+"</font></b>.").fadeOut(5000);
                       
                    }else{
                        $("userGrouping").html(" <b><font color='green'>"+req.responseText+"</font></b>").fadeOut(5000);
                        
                        if(req.responseText=="grouping updated succesfully."){}else{
                            clearGroupingData();
                        }
                    }
                } 
                else
                {
                    //  alert("Error occured");
                    $("userGrouping").html(" <b><font color='red'>"+"Please check the Internet connection!"+"</font></b>.");
                }
            }
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
}

function CalculateLeangth(){
    var desch=document.getElementById("usrDescription").value;
    var desc=desch.length;
    if(desc>=250){
        document.getElementById("usrDescription").value=document.getElementById("usrDescription").value.substr(0,250);
    }else
    {
        $("deascOpt").html("<font color='green'>Need "+ (250-desc) +" More characters."+"</font>.");
    }
    if(desc==250){
        $("deascOpt").html("<font color='red'> cannot enter more then 250 characters."+"</font>.");
    }
    
}
function checkIsExitOrNot(){
    var userId=document.getElementById("userId").value;
    var usrName=document.getElementById("userName").value;
    if(usrName.length<3){
        userId=document.getElementById("userId").value=0;
        return flase;
    }
    if(userId>0)
    {
        var url='../general/checkIsExitOrNotForGrouping.action?userId='+userId;
        // alert(url)
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4) {
                if (req.status == 200) 
                {
                    // alert("success"+req.responseText)
                    if(req.responseText=="true"){
                        $("userGrouping").html(" <b><font color='red'>"+"User Already Exist."+"</font></b>.");
                        document.getElementById("userName").value="";
                    }else{
                        
                    }
                }
            } 
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function allValidateGrouping(userId,usrCatType,userCatArry,usrStatus){
    var usrName=document.getElementById("userName").value;
    if(usrName.length<3){
        userId=document.getElementById("userId").value=0;
        $("userGrouping").html("<b><font color='red'>"+"Please Select Proper Name."+"</font></b>");
        return flase;
    }
    if(userId==0){
        $("userGrouping").html(" <b><font color='red'>"+"Please Select Proper Name."+"</font></b>");
        return flase;
    }
    
    if(usrCatType==-1){
        $("userGrouping").html(" <b><font color='red'>"+"Please Select Category Type."+"</font></b>");
        return flase;
    }
    if(usrStatus==-1){
        $("userGrouping").html(" <b><font color='red'>"+"Please Select Status."+"</font></b>");
        return flase;
    }
    if(userCatArry.length==0){
        $("userGrouping").html(" <b><font color='red'>"+"Please Select Category Values."+"</font></b>");
        return flase;
    }
    return true;
}
function clearErrosMsgForGrouping(){
    $("userGrouping").html("");
}
function clearGroupingData(){
    //var userId=document.getElementById("userId").value="";
    document.getElementById("userName").value="";
    userId=document.getElementById("userId").value=0;
    document.getElementById("usrCatType").value="-1";
    //   var userCatArry = [];    
    //    $("#usrCategoryValue :selected").each(function(){
    //        userCatArry.push($(this).val())=""; 
    //    });
   
    document.getElementById("usrStatus").value="-1"; 
    document.getElementById("usrDescription").value="";
    document.getElementById("groupingId").value="0";
    getCategoryList()
}
function getCategoryList()
{
    //alert("helo")
    var usrCatType=document.getElementById("usrCatType").value;
    //alert(usrCatType)
    var url='../general/getCategoryList.action?usrCatType='+usrCatType;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) 
            {
                // alert(req.responseText)
                populateCatList(req.responseText);
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function populateCatList(response){
    //alert(response)
    // var topicId = document.getElementById("usrCategoryValue");
    var flag=response.split("FLAG");
    var addList=flag[0].split("^");
    var $select = $('#usrCategoryValue');
    $select.find('option').remove();   
    for(var i=0;i<addList.length-1;i++){        
        var Values=addList[i].split(",");
        {  
            
            $('<option><b>').val(Values[0]).text(Values[1]).appendTo($select); 
        }
    }
}


////////////////////////////////////////////////////////////////for home redirection////////////////////
//added by ramakrishna<lankireddy@miraclesoft.com>
////////////////////////////////////////////////////////////////////////////////////////////////////////

function getAccountNamesForHomeRedirection(){
    var accountType=$("#accountType").val();
    var url;
    var v_empName=(document).getElementById('accountName').value;
    if (v_empName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(v_empName.length>=3){
            //            if(interview=="I"){
            //                url=CONTENXT_PATH+"/getEmployeeDetails.action?empName="+v_empName+'&techReview=TR&requirementId='+requirementId;
            //            alert(url)
            //            }
            //            else{
                
            url=CONTENXT_PATH+"/getAccountNamesForHomeRedirection.action?empName="+v_empName+'&accountType='+accountType;
            //            alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        parseAccountNamesNamesForHomeRedirection(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function parseAccountNamesNamesForHomeRedirection(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("EMPLOYEES")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var loginId = consultant.getElementsByTagName("EMPID")[0];
            var empName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendAccountNamesNamesForHomeRedirection(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("accountName"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Account doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}

function appendAccountNamesNamesForHomeRedirection(empName,loginId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setAccountNamesNamesForHomeRedirection('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function setAccountNamesNamesForHomeRedirection(empName,loginId){
    clearTable();
    document.getElementById("accountName").value =empName;
    document.getElementById("accountId").value =loginId;
   
}

//////////////////////////////////////////////////////////////////////////////////////////////////////



////Added by mani
function authAccOverlayFun(des)
{
    document.getElementById('outputMessageOfauthAcc').innerHTML=des;
    var specialBox = document.getElementById('authAccBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#authAccOverlay_popup').popup(      
        );  
}
function addAuthAccOverlayFun()
{
    
    var specialBox = document.getElementById('addAuthAccBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#addAuthAccOverlay_popup').popup(      
        );  
}
//for seeting the action id,action name...from page to overlay hidden form fields
function setAccAuthorizationValues(actionId,actionName,actionStatus,actionDesc){
    // alert(userId);
    // alert(orgId);
    document.getElementById("overlayActionId").value=actionId;
    document.getElementById("overlayActionName").value=actionName;
    document.getElementById("overlayActionStatus").value=actionStatus;
    document.getElementById("overlayActionDesc").value=actionDesc;
    document.getElementById("action_name").value=actionName;
    document.getElementById("accauthStatus").value=actionStatus;
    document.getElementById("addingAccAuthDesc").value=actionDesc;
}
function insertOrUpdateAccAuth(flag){
    
    var actionName= document.getElementById("action_name").value;
    var accauthStatus= document.getElementById("accauthStatus").value;
    var addingAccAuthDesc=document.getElementById("addingAccAuthDesc").value;
    var actionId=document.getElementById("overlayActionId").value;
    // var status=document.getElementById("status").value;
    // alert(actionName);
    //alert(accauthStatus); 
    // alert(addingAccAuthDesc);
    // alert(actionId);
    if(isNaN(actionId)){
        var url='accauth/insertOrUpdateAccAuth.action?flag='+flag+'&desc='+addingAccAuthDesc+'&status='+accauthStatus+'&actionName='+actionName;
    }
    else
    {
        var url='accauth/insertOrUpdateAccAuth.action?actionId='+actionId+'&flag='+flag+'&desc='+addingAccAuthDesc+'&status='+accauthStatus+'&actionName='+actionName;
    }
    var req=initRequest(url);
    //alert(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            
            if (req.status == 200) {
                //  document.getElementById("outputMessageOfUpdate").innerHTML="Status is Updated"
                //populateAccAuthTable(req.responseText);
                location.reload();
            } 
            else
            {
                alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}

 

function addOrUpdateChecking(flag){
    //  alert(flag);
    if(flag=='a'){
        // alert("add");
        var update = document.getElementById("updateDiv");
        //alert(update);
        update.style.display="none";
        var status=document.getElementById("statusDiv");
        status.style.display="none";
        var accName=document.getElementById("accNameDiv");
        accName.style.display=""; 
        var desc=document.getElementById("descDiv");
        desc.style.display="";
        var add = document.getElementById("addDiv");
        add.style.display=""; 
        
        document.getElementById("heading").innerHTML="Add Action Authorization"
    }
    if(flag=='u'){
        //  alert("Updatew");
        var add = document.getElementById("addDiv");
        add.style.display="none";
        var status=document.getElementById("statusDiv");
        status.style.display="";
        var update = document.getElementById("updateDiv");
        update.style.display="";
        var accName=document.getElementById("accNameDiv");
        accName.style.display=""; 
        var desc=document.getElementById("descDiv");
        desc.style.display="";
        document.getElementById("heading").innerHTML="Update Action Authorization"
       
    }
}

function getRolesForAccType(){
    //alert("hello");
    var accType=document.getElementById("accType").value;
    var url='../accauth/getRolesForAccType.action?accType='+accType;
    //alert(url);
    var req=initRequest(url);
    req.onreadystatechange = function() {
       
        if (req.readyState == 4 && req.status == 200) {
            setRoles(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function setRoles(data){
    var topicId = document.getElementById("roles");
    var flag=data.split("FLAG");
    var addList=flag[0].split("^");
    var $select = $('#roles');
    $select.find('option').remove();   
    for(var i=0;i<addList.length-1;i++){        
        var Values=addList[i].split("#");
        {  
            
            $('<option>').val(Values[0]).text(Values[1]).appendTo($select); 
        }
    }
}

////////////////////////////for Acccount name Suggestions

function getAccountNames() {
    
    var accName=(document).getElementById('accountNamePopup').value;
    var accType=(document).getElementById('accType').value;

    
    //var v_empName=id.value;
    //alert("---"+v_empName);
    if (accName == "") {
        clearTable();
    } else {
        
        //alert("Empname->"+v_empName+"--len-->"+v_empName.length);
    
        if(accName.length>=3){
            var url='../accauth/getAccountNames.action?accType='+ accType +'&accName='+accName;
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        parseAccountNames(req.responseXML);
                    } else if (req.status == 204){
                        clearTable();
                    }
                }
            };
            req.open("GET", url, true);
            req.send(null);
        }
    }
}

function parseAccountNames(responseXML) {
    //alert("hii");
    clearTable();
    //alert("hii");
    var consultants = responseXML.getElementsByTagName("ACCOUNTS")[0];
    //alert(consultants.childNodes.length);
    if (consultants.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "0");
    } else {
        clearTable();
    }
    //alert("Hello"+consultants.childNodes.length)
 

    //alert("Hello")
    
    var consultant = consultants.childNodes[0];
    var chk=consultant.getElementsByTagName("VALID")[0];
    isExist = chk.childNodes[0].nodeValue;
    if(chk.childNodes[0].nodeValue =="true") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "";
        document.getElementById("menu-popup").style.display = "block";
        for (loop = 0; loop < consultants.childNodes.length; loop++) {
            var consultant = consultants.childNodes[loop];
            var accId = consultant.getElementsByTagName("ACCOUNTID")[0];
            var accName = consultant.getElementsByTagName("NAME")[0];
            //alert(empName.childNodes[0].nodeValue)
            //alert(loginId.childNodes[0].nodeValue)

            appendAccounutNames(accName.childNodes[0].nodeValue,accId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("accountNamePopup"));
        
        //var position = findPosition(document.getElementById("assignedToUID"));
        posi = position.split(",");
        document.getElementById("menu-popup").style.left = posi[0]+"px";
        document.getElementById("menu-popup").style.top = (parseInt(posi[1])+20)+"px";
        document.getElementById("menu-popup").style.display = "block";
    } 
    if(chk.childNodes[0].nodeValue =="false") {
        var validationMessage=document.getElementById("validationMessage");
        validationMessage.innerHTML = "<font color=red>  Account doesn't Exists </font>";
    //document.getElementById('validationMessage').innerHTML = "<font color=red> Employee doesn't Exists!</font>";
    }
    if(consultants.childNodes.length<10) {
        // autorow1.style.overflowY = "hidden";
        autorow.style.overflowY = "hidden";
    }
    else {
        // autorow1.style.overflowY = "scroll";
        autorow.style.overflowY = "scroll";
    }
}

function appendAccounutNames(accName,accId) {
    //alert("deaswwwasfd")
    var row;
    var nameCell;
    if (!isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
    nameCell.setAttribute("bgcolor", "#fff");
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";


    linkElement.setAttribute("href",
        "javascript:setAccountNames('"+accName +"','"+ accId +"')");
    linkElement.appendChild(document.createTextNode(accName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}

function setAccountNames(accName,accId){
    clearTable();
    // alert("in set_cust");
    document.getElementById("accountNamePopup").value =accName;
    document.getElementById("orgId").value =accId;
   
}


function getActionResorucesSearchResults()
{
    
    var accType=document.getElementById("accType").value;
    //alert(accType);
    var status=document.getElementById("status").value;
    //   alert(status);
    var roles=document.getElementById("roles").value;
    var accountName=document.getElementById("accountNamePopup").value;
    var action_id=document.getElementById("action_id").value;
  

    //var csrStatus=document.getElementById("csrStatus").value;
    var url='../accauth/getActionResorucesSearchResults.action?actionId='+ action_id +'&accType='+ accType +'&status='+status+'&accName='+accountName+'&roles='+roles;
    // alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                //alert(req.responseText)
                populategetActionResorucesSearchTable(req.responseText)
            } 
            else
            {
                alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
 
}
function populategetActionResorucesSearchTable(response){

    var eduList=response.split("^");
    var updateFlag="update";
    var table = document.getElementById("empCategorizationResults");
    // var name =  document.getElementById("account_name").value;
    //var table = document.getElementById("contactPageNav");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        for(var i=0;i<eduList.length-1;i++){   
       
            var Values=eduList[i].split("|");
            {  
                //alert(Values[6])
         
                var row = $("<tr />")
                $("#empCategorizationResults").append(row); 
                row.append($("<td><a href='actionResourcesForAddOrUpdate.action?id="+Values[0]+"&action_id="+Values[5]+"&action_name="+Values[6]+"&accountName="+Values[1]+"&accType="+Values[7]+"&status="+Values[3]+"&description="+Values[4] +"&rollName="+Values[2]+"&flag="+updateFlag+"'  >" + Values[1] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($("<td>" + Values[4] + "</td>"));
                row.append($("<td><a href='#' onclick=actionResourceTermination(" + Values[0] + ","+ Values[5] +")><img src='../../includes/images/delete.png' height=20 width=20 ></td>"));

            }
        }
    }
    else {
        var row = $("<tr />")
        $("#empCategorizationResults").append(row);
        row.append($('<td colspan="6"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
  
    acPager.init(); 
    acPager.showPageNav('acPager', 'pageNavPosition'); 
    acPager.showPage(1);
}

function insertOrUpdateActionResources(flag){
    // alert(flag);
    var status;
    var accType=document.getElementById("accType").value;
    //alert(accType);
    if(flag=='1'){
        status=document.getElementById("status").value;
    }
    else
    {
        status="Active";
    }
    var blockFlag=0;
    if(document.getElementById("blockFlag").checked)
    {
        blockFlag=1; 
    }
    var actionId=document.getElementById("action_id").value;
    var roles=document.getElementById("roles").value;
    var orgId=document.getElementById("orgId").value;
    var id=document.getElementById("id").value;
    var desc=document.getElementById("addingAccAuthDesc").value;

    
    
    // alert(blockFlag);
    //alert(status);
    // alert(roles);
    // alert(orgId);

    //var csrStatus=document.getElementById("csrStatus").value;
    var url='../accauth/insertOrUpdateActionResources.action?blockFlag='+blockFlag+'&actionId='+actionId+'&desc='+desc+'&flag='+flag+'&id='+ id +'&accType='+ accType +'&status='+status+'&orgId='+orgId+'&roles='+roles;
    // alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                
                document.getElementById("outputMessage").innerHTML=req.responseText;

            // populategetActionResorucesSearchTable(req.responseText)
            } 
            else
            {
                alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}


function actionResourceTermination(id,action_id){
    swal({
    
        title: "Do you want delete Action for this resources",
  
        //text: "Tranfering csr",
        textSize:"170px",
        type: "warning",
  
        showCancelButton: true,
        confirmButtonColor: "#3498db",
  
        //cancelButtonColor: "#56a5ec",
        cancelButtonText: "No",
        confirmButtonText: "Yes",
        closeOnConfirm: false,
        closeOnCancel: false
 
    },
    function(isConfirm){
        if (isConfirm) {
            // alert(groupingId);
            // alert(id);
            //  alert(action_id);
            var url='accauth/actionResourceTermination.action?id='+id+'&actionId='+action_id;
            var req=initRequest(url);
            //alert(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        document.getElementById("outputMessage").innerHTML=req.responseText;
                        getActionResorucesSearchResults();
                        swal("Deleted!", "Deleted Successfully....", "success");
                    } 
                    else
                    {
                        swal("Sorry Not Deleted", "Deletion not done ", "error");
                    }
                }
            };
            req.open("GET",url,"true");
            req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            req.send(null);
        }
        else {
     
            swal("Cancelled", "Deleted cancelled ", "error");
        }
    });
}


function getPresentTimeYear() {
           
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()//January is 0!
    var yyyy = today.getFullYear();
    if(mm==0){
        mm=12;
    }
    if(mm==12){
        yyyy=yyyy-1;
    }
    // document.getElementById("invoiceMonth").value=mm;
    document.getElementById("invoiceYear").value=yyyy;
    document.getElementById("invoiceMonthOver").value=mm;
    document.getElementById("invoiceYearOver").value=yyyy;
    $('#resourceAll').change(function(){
        var cheked=($(this).is(":checked"));
        
        if(!cheked){
            $('#OverResourceName').show();
        }
        else
            $('#OverResourceName').hide();

    });
}
function generateInvoice(){
    var invoiceMonth=document.getElementById("invoiceMonthOver").value;
    var invoiceYear=  document.getElementById("invoiceYearOver").value;
    var invoiceResource=document.getElementById("resourceNameOver").value;
    var cheked;
    cheked=( $('#resourceAll').is(":checked"));
    //alert(cheked)
    if(isvalidationInvoice(invoiceMonth,invoiceYear,invoiceResource,cheked)){
        var url=CONTENXT_PATH+'/invoice/generateInvoice.action?invoiceMonth='+invoiceMonth+'&invoiceYear='+invoiceYear+'&invoiceResource='+invoiceResource+'&cheked='+cheked;
        alert(url)
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    //alert("hm"+req.responseText)
                    if(req.responseText==1)
                    {                        
                        $("invoiceGenerarionMessage").html(" <b><font color='green'>"+"Successfully Invoice Generated."+"</font></b>").fadeOut(5000);
                    // alert("success")
                    }
                    else if(req.responseText== -1){
                        //alert("SOW not Exist")
                        $("invoiceGenerarionMessage").html(" <b><font color='red'>"+"SOW Need To be Approved."+"</font></b>").fadeOut(5000);
                    }
                    else if(req.responseText== -2)
                    {
                        $("invoiceGenerarionMessage").html(" <b><font color='red'>"+"User Not Exist."+"</font></b>").fadeOut(5000);
                    //alert("user not Exist")
                    }
                    else if(req.responseText== -3){
                        $("invoiceGenerarionMessage").html(" <b><font color='red'>"+"SOW Not Exist For the User."+"</font></b>").fadeOut(5000);
                    //alert("sow not exist for user")
                    }
                    else if(req.responseText== -4){
                        $("invoiceGenerarionMessage").html(" <b><font color='red'>"+"Invoice Already Exist."+"</font></b>").fadeOut(5000);
                    //alert("invoice already exist")
                    }
                    else if(req.responseText== -5){
                        $("invoiceGenerarionMessage").html(" <b><font color='red'>"+"Time Sheet not Found"+"</font></b>").fadeOut(5000);
                    //alert("Timesheet Not found")
                    }


                } 
                else
                {
                    alert("Error occured");
                }
            }
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    return false;
}
function generateInvoiceOverlay(){
    
    setupOverlay('InvoiceGenerationOverlay','#InvoiceGenerationOverlay_popup');
    return false;
}
function setupOverlay(overlayBox, popupId){
    var specialBox = document.getElementById(overlayBox);
    if(specialBox.style.display == "block"){
        specialBox.style.display = "none";
    } else {
        specialBox.style.display = "block";
        document.getElementById("resourceNameOver").value="";
    }
    // Initialize the plugin
    $(popupId).popup();
}

function isvalidationInvoice(invoiceMonth,invoiceYear,invoiceResource,cheked){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()//January is 0!
    var yyyy = today.getFullYear();
    if(mm==0){
        mm=12;
    }
    if(mm==12){
        yyyy=yyyy-1;
    }
    if(yyyy>=invoiceYear){
        if(mm>=invoiceMonth){
           
        }else
        {
            $("invoiceGenerarionMessage").html(" <b><font color='red'>Month Not Valid</font></b>.");
            return false; 
        }
    }
    else
    {
        $("invoiceGenerarionMessage").html(" <b><font color='red'>Year Not Valid</font></b>.");
        return false;
    }
    if(cheked){
        
    }else{
        re=/[^@]+@[^@]+\.[a-zA-Z]{2,}/;
        if(re.test(invoiceResource))
        {
        }
        else
        {
            $("invoiceGenerarionMessage").html(" <b><font color='red'>must be valid email</font></b>.");
            return false;
        }
    }
    $("invoiceGenerarionMessage").html(" ");
    return true;
}
function setPaybleDate(){
    var  invoiceStatus=document.getElementById("invoiceStatus").value;
    //  alert(invoiceStatus)
    if(invoiceStatus=='approved'){
        var netTerms=document.getElementById("netTerms").value;
        var myDate = new Date(new Date().getTime()+(netTerms*24*60*60*1000));

        var date = myDate;
        var dd = date.getDate();
        var mm = date.getMonth()+1//January is 0!
        var yyyy = date.getFullYear();
        
        //alert(netTerms+" "+dd+" "+mm+" "+yyyy+" ")
        var pamentDate=mm+"-"+dd+"-"+yyyy;
        document.getElementById("pamentDate").value=pamentDate;
    // alert(dat)
        
    }
    else if(invoiceStatus=='paid'){
        return true;
    }
    else{
        document.getElementById("pamentDate").value="";
    }
}
function getBalanceAmt(){
    var  invoiceStatus=document.getElementById("invoiceStatus").value;
    if(invoiceStatus=='paid'){
        var totalAmt=document.getElementById("totalAmt").value;
        var paidAmt=document.getElementById("paidAmt").value;
        var transNO=document.getElementById("transNO").value;
        
        var amt=parseFloat(totalAmt)
        var paid;
        if(paidAmt!=""){
            paid=parseFloat(paidAmt);
        }else{
            paid="";
        }
        var balAmt;
        //   alert("hell"+amt+".."+paid)
        if(paid==0||paid==""){
            //  alert("pj")
            $("errorMsg").html(" <b><font color='red'>Please Paid Enter Amount.</font></b>.");
            return false;
        }
        else if(transNO==""){
            $("errorMsg").html(" <b><font color='red'>Please Enter transaction Number.</font></b>.");
            return false;
        }
        else if(amt < paid){
            //   alert("hi")
            document.getElementById("paidAmt").value="";
            $("errorMsg").html(" <b><font color='red'>Paid Amount Should be Less then with Total.</font></b>.");
            return false;
        }else
        {
            $("errorMsg").html("");
            balAmt=totalAmt-paidAmt;
            document.getElementById("balanceAmt").value=balAmt;
            return true;
        }
       
    }else
    {
        return true;
    }
}

