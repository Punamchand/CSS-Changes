//$('#account_name').blur(function(){
//    console.log($(this).val());
//    $.ajax({
//        type:'POST',
//        url: 'MSB/acc/ajaxAccountNameCheck?accountNameCheck='+$(this).val(),
//        dataType:'text',
//        success:function(data,stat,xhr){
//            console.log('RESPONSE SAYS '+data+" " + xhr.getResponseHeader('exists'));
//            if(xhr.getResponseHeader('exists')==='free'){
//                $('#account_name').css('border', '1px solid green')
//            }else{
//                $('#account_name').css('border', '1px solid red')
//            }
//        },
//        error: function(data,stat,xhr){
//            $('#account_url').css('border', '1px solid red')
//
//        }
//    })
//});
//$('#account_url').blur(function(){
//    console.log($(this).val());
//    $.ajax({
//        type:'POST',
//        url: 'MSB/acc/ajaxAccountURLCheck?accountURLCheck='+$(this).val(),
//        dataType:'text',
//        success:function(data,stat,xhr){
//console.log('RESPONSE SAYS '+data+" " + xhr.getResponseHeader('urlexists'));
//            if(xhr.getResponseHeader('urlexists')==='free'){
//                $('#account_url').css('border', '1px solid green')
//            }else{
//                $('#account_url').css('border', '1px solid red')
//            }
//        },
//        error: function(data,stat,xhr){
//            //console.log('RESPONSE SAYS '+data+" " + xhr.getResponseHeader('urlexists'));
//            $('#account_url').css('border', '1px solid red')
//
//        }
//
//    })
//});
//});
                                                                                            
//});
        

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


function headingMessage(message)
{
    // alert(message.id);
    if(message.id=="accountdetailshead"){
   
        document.getElementById("headingmessage").innerHTML="Account Details";
    }

    if(message.id=="softwareshead"){
        document.getElementById("headingmessage").innerHTML="Account Softwares";
    }
    if(message.id=="assignteamhead"){
        document.getElementById("headingmessage").innerHTML="Assign Team";
    }
    if(message.id=="contactshead"){
        document.getElementById("headingmessage").innerHTML="Contacts";
        showContacts();
    }
    if(message.id=="acitivitieshead"){
        document.getElementById("headingmessage").innerHTML="Activities";
    }
    if(message.id=="requirementshead"){
        document.getElementById("headingmessage").innerHTML="Account Requirements";
    }

    if(message.id=="opportunitieshead"){
        document.getElementById("headingmessage").innerHTML="Opportunities";
    }

    if(message.id=="projectshead"){
        document.getElementById("headingmessage").innerHTML="Projects";
    }
    if(message.id=="greensheetshead"){
        document.getElementById("headingmessage").innerHTML="Green Sheets";
    }
    if(message.id=="vendors"){
        document.getElementById("headingmessage").innerHTML="Vendor Tier's";
    }
    if(message.id=="csrAccounts"){
        document.getElementById("headingmessage").innerHTML="Csr Account";
        getCsrDetailsTable();
    }

}

function showContacts()
{
    //alert("In ");
    //alert(document.getElementById("accountsearchid").value);
    var orgId= document.getElementById("accountSearchID").value;
    //alert(orgId);
    var url='../acc/getContacts.action?accountSearchOrgId='+orgId;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        document.getElementById('loadingContact').style.display = 'block';
        if (req.readyState == 4) {
            
            if (req.status == 200) {
                $('#loadingContact').hide();
                
                populateContactTable(req.responseText);
                
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
function populateContactTable(response){
    // alert(response);  
    var eduList=response.split("^");
    var OrgID= document.getElementById("accountSearchID").value;
    var table = document.getElementById("contactPageNav");
    var accName =  document.getElementById("account_name").value;
    var accountType =  document.getElementById("account_type").value; 
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
         
         
                var row = $("<tr />")
                //alert("row--?"+row);
      
                $("#contactPageNav").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                // row.append($('<td><a href="" class="eduEdit_popup_open " onclick=" showEditEduOverlayDetails('+Values[0]+');" > ' + Values[1] + "</td>"));
                //row.append($("<td>" + Values[0] + "</td>"));
                //reqRow.append($('<td><a href="../Requirements/requirementedit.action?requirementId='+Values[0]+'" > ' + Values[1] + "</td>"));
                row.append($('<td><a href="../acc/accountcontactedit.action?accountType='+ accountType +'&account_name='+ accName +'&accountSearchID='+ OrgID +'&contactId='+Values[0]+'" > '+ Values[1] + "</td>"));
                // row.append($('<td><a href="../acc/accountcontactedit.action?accountSearchID='+OrgID+'&account_name='+name+'&contactId='+Values[0]+'" > '+ Values[1] + "</td>"));

                /* if(Values[2]=='null'){
                    row.append($("<td>" +'-----' + "</td>"));
                }else{
                    row.append($("<td>" + Values[2] + "</td>"));
                }*/
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($("<td>" + Values[4] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($('<td><center><a href="../users/general/getUserRoles.action?userid='+Values[0]+'&&accountSearchID='+OrgID+'" >'+"<img src='./../includes/images/roleImage.png' height='20' width='30' >" + "</center></td>"));
                row.append($('<td><a href="#" class="contactLoginOverlay_popup_open" onclick="contactLoginOverlay('+Values[0]+','+ OrgID +',\'' + Values[3] + '\')">'  + "<img src='./../includes/images/user-login.png' height='20' width='30' >" + "</td>"));
            //row.append($("<td>" + Values[4] + "</td>"));
            //row.append($("<td>" + Values[7] + "</td>"));
            //onclick="saveContactDetails(' + Values[0] +');" > '
            }
        }
    }
    else {
        var row = $("<tr />")
        $("#contactPageNav").append(row);
        row.append($('<td colspan="6"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
  
    acPager.init(); 
    acPager.showPageNav('acPager', 'contactPageNavPosition'); 
    acPager.showPage(1);
}

function saveContactDetails()
{
    var usrid= document.getElementById("contactId").value;
    var orgid= document.getElementById("orgId").value;
    // var orgId= document.getElementById("accountsearchid").value;
    //alert(usrid);
    // alert(orgid);
    var url='../acc/saveContacts.action?accountSearchOrgId='+ orgid +'&orgUserId='+usrid;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                //alert()
                document.getElementById("outputMessage").innerHTML=req.responseText;
                if(req.responseText=="Login credentials Send Succesfully to email")
                {
                    document.getElementById('contactSend').style.display="none";   
                    document.getElementById('contactCancel').style.display="none";       
                }
                    
                    
            } 
            else
            {
            //alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);

    return false;
}


function getContactSearchResults(){
    var firstName=$("#firstNameContacts").val();
    // alert(userid);
    var lastName=$("#lastNameContacts").val();
    var email=$("#emailContacts").val();
    var status=$("#statusContacts").val();
    var phone=$("#phoneContacts").val();
    
    var orgId= document.getElementById("accountSearchID").value;
    //alert(orgId);
     
    var url='../acc/getContactSearchResults.action?accountSearchOrgId='+orgId +'&firstName='+firstName +'&lastName='+lastName +'&email='+email +'&status='+status+ '&phone='+ phone ;
    // alert(url);
    var req=initRequest(url);
    req.onreadystatechange = function() {
        document.getElementById('loadingContact').style.display = 'block';
        if (req.readyState == 4) {
            if (req.status == 200) {
                $('#loadingContact').hide();
                //alert("in ajax-->" +(req.responseText))
                //alert("Record Updated Successfully");
                populateContactTable(req.responseText);
               
            //$("securityinfo").html(" <b><font color='green'>Confidential information Saved Successfully</font></b>");
            } 
            else
            {
               
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
    
                 
function FillContactAddress() {
    if(checkAddress.checked == true) {
        //alert(checkAddress.checked);
        conCAddress.value= conAddress.value;
        document.getElementById("conCAddress").disabled = true;
        conCAddress2.value= conAddress2.value;
        document.getElementById("conCAddress2").disabled = true;
        conCCity.value=conCity.value;
        document.getElementById("conCCity").disabled = true;
        conCCountry.value=conCountry.value;        
        document.getElementById("conCCountry").disabled = true;  
        
        var $options = $("#conState > option").clone();
        $('#conCState').append($options);
        conCState.value=conState.value; 
        document.getElementById("conCState").disabled = true; 
        conCZip.value=conZip.value;
        document.getElementById("conCZip").disabled = true;
        conCPhone.value=conPhone.value;
        document.getElementById("conCPhone").disabled = true;
    }
    if(checkAddress.checked == false) {
        document.getElementById("conCAddress").disabled = false;
        conCAddress.value='';
        document.getElementById("conCAddress2").disabled = false;
        conCAddress2.value='';
        document.getElementById("conCCity").disabled = false;
        conCCity.value='';
        document.getElementById("conCCountry").disabled = false;
        conCCountry.value='';
        document.getElementById("conCState").disabled = false;
        conCState.value='';
        document.getElementById("conCZip").disabled = false;
        conCZip.value='';
        document.getElementById("conCPhone").disabled = false;
        conCPhone.value='';

    }
}
function FillContactAddressAdding() {
    if(add_checkAddress.checked == true) {
        //alert(checkAddress.checked);
        
        
        document.getElementById('add_checkAddress').value=true;
        conCAddress.value= conAddress.value;
        document.getElementById("conCAddress").disabled = true;
        conCAddress2.value= conAddress2.value;
        document.getElementById("conCAddress2").disabled = true;
        conCCity.value=conCity.value;
        document.getElementById("conCCity").disabled = true;
        conCCountry.value=conCountry.value;        
        document.getElementById("conCCountry").disabled = true;  
        
        var $options = $("#conState > option").clone();
        $('#conCState').append($options);
        conCState.value=conState.value; 
        document.getElementById("conCState").disabled = true; 
        conCZip.value=conZip.value;
        document.getElementById("conCZip").disabled = true;
        conCPhone.value=conPhone.value;
        document.getElementById("conCPhone").disabled = true;
    }
    if(add_checkAddress.checked == false) {
        document.getElementById('add_checkAddress').value=false;
 
        document.getElementById("conCAddress").disabled = false;
        conCAddress.value='';
        document.getElementById("conCAddress2").disabled = false;
        conCAddress2.value='';
        document.getElementById("conCCity").disabled = false;
        conCCity.value='';
        document.getElementById("conCCountry").disabled = false;
        conCCountry.value='';
        document.getElementById("conCState").disabled = false;
        conCState.value='';
        document.getElementById("conCZip").disabled = false;
        conCZip.value='';
        document.getElementById("conCPhone").disabled = false;
        conCPhone.value='';

    }
}


function contactInfoValidation(){
    var first_name=document.getElementById("ContactFname").value;
    re = /^[A-Za-z\s]+$/;
    if(!re.test(first_name)||first_name.length<3)
    {
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid First Name</font></b>.");
        $("#ContactFname").css("border", "1px solid red");
        return false;
    }
    re = /^[A-Za-z0-9\s]+$/;
    var last_name=document.getElementById("ContactLname").value;
    if(!re.test(last_name)||last_name.length<3)
    {
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid Last Name</font></b>.");
        $("#ContactLname").css("border", "1px solid red");
        return false;
    }
    var file=$("#taskAttachment").val();
    alert(file);
    if(file!='')
    {
        var allowed_extensions = new Array("jpg","gif","png");
        var file_extension = file.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

        for(var i = 0; i < allowed_extensions.length; i++)
        {
            if(allowed_extensions[i]==file_extension)
            {
                return true; // valid file extension
            }
        }
        $("#InsertContactInfo").html(" <b><font color='red'>The file uploaded is invalid type</font></b>");

        return false;
    }
    

}

    
   

function contactFirstNameValidation(){
    
    $("#actionMessage").html(""); 
    var first_name=document.getElementById("ContactFname").value;
    re = /^[A-Za-z\s]+$/;
    if(!re.test(first_name)||first_name.length<3)
    {
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid First Name</font></b>.");
        $("#ContactFname").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#InsertContactInfo").html("");
        $("#ContactFname").css("border", "1px solid green");

    }
    return true;
}

function contactLastNameValidation(){
    $("#actionMessage").html("");
    re = /^[A-Za-z0-9\s]+$/;
    var last_name=document.getElementById("ContactLname").value;
    if(!re.test(last_name)||last_name.length<3)
    {
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid Last Name</font></b>.");
        $("#ContactLname").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#InsertContactInfo").html("");
        $("#ContactLname").css("border", "1px solid green");
        return true;
    }
}





function ContactEmailValidate(){
    $("#actionMessage").html(""); 
    var ContactEmail = document.getElementById("ContactEmail").value;
    var url='../acc/contactEmailCheck.action?ContactEmail='+ContactEmail;
    // alert(ContactEmail);
    // alert(url);
    
   
    var req=initRequest(url);
    
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                // alert(req.responseText);
                resultEmail(req.responseText);
               
            } 
            else
            {
                            
            }
        }
    };
    
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    
//    function EmailValidation1(){
//   
//    var status=document.getElementById("email1").value;
//   
//    re=/[^@]+@[^@]+\.[a-zA-Z]{2,}/;
//    if(!re.test(status))
//    {
//        $("j").html(" <b><font color='red'>must be valid  corp.email</font></b>.");
//        $("#email1").css("border", "1px solid red");
//    }
//    else
//    {
//        $("j").html("");
//        $("#email1").css("border", "1px solid green");
//    }
//}
}
function resultEmail(response){
    if(response=="success"){
        $("#InsertContactInfo").html("  <b><font color='green'>E-mail is Available</font></b>");
        return true;
    }
    else{
        document.getElementById("ContactEmail").value="";
        $("#InsertContactInfo").html(" <b><font color=#B20000>E-mail is Already Exists !</font></b>");
        return false;
    }
}

function ContactAddressValidation()
{   
    // alert("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii ");
    var OfficeAddress=document.getElementById("OfficeAddress").value;
    letters = /^[0-9A-Za-z]+$/;  
    if(OfficeAddress.value.match(letters))  
    {  
        alert("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii ");
        return true;  
    }  
    else  
    {  
        $("InsertContactInfo").html(" <b><font color=#B20000>User address must have alphanumeric characters only</font></b>");
        //uadd.focus(); 
        $("#OfficeAddress").css("border", "1px solid red");
        return false;  
    }  
}
function paddresValidation(){
    
    //alert("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii ")
    var Address=document.getElementById("conAddress").value;
    re = /^[A-Za-z]+$/;
    if(Address.length<2)
    {
        $("#spanUpdatep").css('visibility', 'hidden');
        $("errmsg").html(" <b><font color='red'>must be valid Address</font></b>");
        $("#conAddress").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#spanUpdatep").css('visibility', 'visible');
        $("errmsg").html(" ");
        $("#conAddress").css("border", "1px solid green");
    }
    
}


function CaddresValidation(){

    var Address=document.getElementById("conCAddress").value;
    re = /^[A-Za-z]+$/;
    if(Address.length<2)
    {
        $("#spanUpdatep").css('visibility', 'hidden');
        $("errmsgc").html(" <b><font color='red'>must be valid Address</font></b>");
        $("#Address").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#spanUpdatep").css('visibility', 'visible');
        $("errmsgc").html(" ");
        $("#Address").css("border", "1px solid green");
    }
}


function removeContactoverlay(){                        
    //alert("hi");
    document.forms["contactform"].reset(); 
    $("ContactEmail").html(" ");
    //$('InsertContactInfo').html(" ");
    $('InsertContactInfo').html(" ");  
    $("#conZip").css("border", "1px solid #53C2FF ");
    $("#ContactFname").css("border", "1px solid #53C2FF ");
    $("#ContactLname").css("border", "1px solid #53C2FF ");
    $("errmsg").html(" ");
    $("#conCZip").css("border", "1px solid #53C2FF ");
}


function removebordercolor(){
    $("#ContactFname").css("border", "1px solid #53C2FF ");
    $("#conZip").css("border", "1px solid #53C2FF ");
    $('InsertContactInfo').html(" ");   

}


function Removebordercolor(){
    $("#ContactLname").css("border", "1px solid #53C2FF "); 
    //  $('InsertContactInfo').html(" ");
    // $('UpdateNoteInfo').html(" ");
    $('InsertContactInfo').html(" ");
}



function ConPermanentStateChange()
{
    
    var id=document.getElementById('conCountry').value;
    //alert(id);
    var url='../acc/getConState.action?id='+id;
   
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            // alert(req.responseText);
           
            ConPermanentState(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function ConPermanentState(data){
    //alert(data);
    var topicId = document.getElementById("conState");
    var flag=data.split("FLAG");
    var addList=flag[0].split("^");
    var $select = $('#conState');
    $select.find('option').remove();
    $('<option>').val(-1).text('Select State').appendTo($select);
    for(var i=0;i<addList.length-1;i++){        
        var Values=addList[i].split("#");
        {  
            
            $('<option>').val(Values[0]).text(Values[1]).appendTo($select); 
        }
    }
}
function ConCurrentStateChange()
{
    //alert("Consultant ajax");
    var id=document.getElementById('conCCountry').value;
    //alert(id);
    var url='../acc/getConState.action?id='+id;
    // alert(url);
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText);
           
            ConCurrentState(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function ConCurrentState(data){
    //alert(data);
    var topicId = document.getElementById("conCState");
    var flag=data.split("FLAG");
    var addList=flag[0].split("^");
    var $select = $('#conCState');
    $select.find('option').remove();   
    for(var i=0;i<addList.length-1;i++){        
        var Values=addList[i].split("#");
        {  
            
            $('<option>').val(Values[0]).text(Values[1]).appendTo($select); 
        }
    }
}

function contactPZipValidation(){
    
    //alert("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii ")
    var conZip=document.getElementById("conZip").value;
    //var Zip_C=document.getElementById("conCZip").value;
    if( isNaN($('#conZip').val()))
    {
        $("#spanUpdatep").css('visibility', 'hidden');
        $("errmsg").html(" <b><font color='red'>must be valid Zip</font></b>");
        $("#conZip").css("border", "1px solid red");
        document.getElementById("conZip").value=""; 
        return false;
    }
    else if(conZip.length!=5)
    {
        $("#spanUpdatep").css('visibility', 'hidden');
        $("errmsg").html(" <b><font color='red'>must be valid Zip</font></b>");
        $("#conZip").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#spanUpdatep").css('visibility', 'visible');
        $("errmsg").html(" ");
        $("#conZip").css("border", "1px solid green");
    }

    
}

function contactCZipValidation(){
    
    //alert("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii ")
   
    var conCZip=document.getElementById("conCZip").value;
    if(isNaN($('#conCZip').val()))
    {
        $("#showUpdatec").css('visibility', 'hidden');
        $("errmsgc").html(" <b><font color='red'>must be valid Zip</font></b>");
        $("#conCZip").css("border", "1px solid red");
        document.getElementById("conCZip").value=""; 
    }
    
    else if(conCZip.length!=5)
    {
        $("#showUpdatec").css('visibility', 'hidden');
        $("errmsgc").html(" <b><font color='red'>must be valid Zip</font></b>");
        $("#conCZip").css("border", "1px solid red");
        return false;
    }
    
    else
    {
        $("#showUpdatec").css('visibility', 'visible');
        $("errmsgc").html(" ");
        $("#conCZip").css("border", "1px solid green");
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


function ccityValidation(){
    
    var City=document.getElementById("conCCity").value;
    if(City.length<3)
    {
        $("#spanUpdatep").css('visibility', 'hidden');
        $("errmsgc").html(" <b><font color='red'>must be valid City</font></b>");
        $("#conCCity").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#spanUpdatep").css('visibility', 'visible');
        $("errmsgc").html(" ");
        $("#conCCity").css("border", "1px solid green");
    }
    
}


function contactPcityValidation(){
    $("#actionMessage").html(""); 
    var City=document.getElementById("conCity").value;
    if(City.length<3)
    {
        // $("#spanUpdatep").css('visibility', 'hidden');
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid City</font></b>");
        $("#conCity").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#spanUpdatep").css('visibility', 'visible');
        $("#InsertContactInfo").html(" ");
        $("#conCity").css("border", "1px solid green");
    }
    
}

$("#conZip").keypress(function (e) {
    //if the letter is not digit then display error and don't type anything
    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        //display error message
        $("#errmsg").html("Digits Only").show().fadeOut("slow");
        return false;
    }
});
   
$("#conCZip").keypress(function (e) {
    //if the letter is not digit then display error and don't type anything
    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        //display error message
        $("#errmsg").html("Digits Only").show().fadeOut("slow");
        return false;
    }
});
   
function removeDataAfterContactOverlay()
{
    document.forms["contactform"].reset(); 
    document.getElementById("conCAddress").disabled = '';
      
        
    document.getElementById("conCAddress2").disabled = '';
        
    document.getElementById("conCCity").disabled = '';
        
    document.getElementById("conCCountry").disabled = '';
        
    document.getElementById("conCState").disabled =  '';
    document.getElementById("conCountry").disabled = '';
        
    document.getElementById("conState").disabled =  '';
       
    document.getElementById("conCZip").disabled =  '';
        
    document.getElementById("conCPhone").disabled = '';
        
     
    // alert("hi");
    document.getElementById('ContactFname').value='';
   
    document.getElementById('ContactEmail').value='';
    document.getElementById('ContactLname').value='';
    document.getElementById('ContactMname').value='';
    document.getElementById('Officephone').value='';
    document.getElementById('conAddress').value='';
    document.getElementById('conAddress2').value='';
    document.getElementById('conCity').value='';
    document.getElementById('conZip').value='';
    document.getElementById('conCountry').value='';
    document.getElementById('conState').value='';
    document.getElementById('conPhone').value='';
    document.getElementById('conCAddress').value='';
    document.getElementById('conCAddress2').value='';
    document.getElementById('conCCity').value='';
    document.getElementById('conCZip').value='';
    document.getElementById('conCCountry').value='';
    document.getElementById('conCState').value='';
    document.getElementById('conCPhone').value='';
    document.getElementById('add_checkAddress').value='';
    document.getElementById('taskAttachment').value='';
    //$('checkAddress').html(" ");
    // $("#addContactError").html(" ");
    $("#InsertContactInfo").html(" ");
    
    $("#ContactLname").css("border", "1px solid #53C2FF ");
    $("#ContactFname").css("border", "1px solid #53C2FF ");
    $("#conAddress").css("border", "1px solid #53C2FF ");
    $("#conCAddress").css("border", "1px solid #53C2FF ");
    $("#conCCity").css("border", "1px solid #53C2FF ");
    $("#ContactEmail").css("border", "1px solid #53C2FF ");
    
    $('errmsgc').html(" ");
    $('errmsg').html(" ");
}


function EmailValidation(){
   
    var domain=document.getElementById("email_ext").value;
    var status=document.getElementById("ContactEmail").value;
    var email=status+'@'+domain;
    // // re=/^[a-z0-9][-a-z0-9_\+\.]/;
    re=/^[a-zA-Z0-9\.'\-\+\_\%\$]+$/;
    if(!re.test(status))
    {
        //$("#addContactError").html("");
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid  corp.email</font></b>.");
        $("#ContactEmail").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#InsertContactInfo").html("");
        $("#ContactEmail").css("border", "1px solid green");
        var url='../acc/contactEmailCheck.action?ContactEmail='+email;
        // alert(ContactEmail);
        // alert(url);
    
   
        var req=initRequest(url);
    
        req.onreadystatechange = function() {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    //                 alert(req.responseText);
                    resultEmail(req.responseText);
               
                } 
                else
                {
                            
                }
            }
        };
    
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    
    }
}
function clearContactOverlay(){
    document.forms["contactform"].reset();
    document.getElementById("conState").value='';
    document.getElementById("conCState").value='';
    // $("#addContactError").html(" ");
    $("#InsertContactInfo").html(" ");
    $("#ContactLname").css("border", "1px solid #53C2FF ");
    $("#ContactFname").css("border", "1px solid #53C2FF ");
    $("#conAddress").css("border", "1px solid #53C2FF ");
    $("#conCAddress").css("border", "1px solid #53C2FF ");
    $("#conCCity").css("border", "1px solid #53C2FF ");
    $("#conCity").css("border", "1px solid #53C2FF ");
    $("#ContactEmail").css("border", "1px solid #53C2FF ");
    $('errmsgc').html(" ");
    $('errmsg').html(" ");
}
$(document).ready(function() {

    var specialBox = document.getElementById('ContactBoxOverlay');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#Contact_popup').popup(      
        );
    
});
function lockScreen(){
       
    document.getElementById('jquery-msg-overlay').style.display="block";
//  $.msg();   
}
function unlockScreen(){
  
    document.getElementById('jquery-msg-overlay').style.display="none";
}
 
function getTitlesForDepatments(){
    //alert("hello");
    var dept=document.getElementById("departments").value;
    var url='../acc/getTitlesForDepartments.action?dept_id='+dept;
    //alert(url);
    var req=initRequest(url);
    req.onreadystatechange = function() {
       
        if (req.readyState == 4 && req.status == 200) {
            setTitle(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function setTitle(data){
    var topicId = document.getElementById("titlesId");
    var flag=data.split("FLAG");
    var addList=flag[0].split("^");
    var $select = $('#titlesId');
    $select.find('option').remove();   
    for(var i=0;i<addList.length-1;i++){        
        var Values=addList[i].split("#");
        {  
            
            $('<option>').val(Values[0]).text(Values[1]).appendTo($select); 
        }
    }
}
function contactLoginOverlay(usrid,orgid,emailId)
{      
    document.getElementById("contactId").value=usrid;
    document.getElementById("orgId").value=orgid;
    document.getElementById("contactEmail").innerHTML="("+emailId+")";
    //     var url='../acc/getContactEmail.action?userId='+usrid;
    //     var req=initRequest(url);
    //    req.onreadystatechange = function() {
    //        if (req.readyState == 4) {
    //            if (req.status == 200) {
    //                //alert(req.responseText)
    //                document.getElementById("contactEmail").innerHTML="("+req.responseText+")";
    //               
    //            } 
    //            else
    //            {
    //               // alert("Error occured");
    //            }
    //        }
    //    };
    //    req.open("GET",url,"true");
    //    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    //    req.send(null);
    //    
    contactOverlayLogin();
    contactCheck(usrid);
       
   
}
function contactCheck(usrid)
{
    var url='../acc/checkContactExist.action?orgUserId='+usrid;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                // alert(req.responseText)
                document.getElementById("outputMessage").innerHTML=req.responseText;
                if(req.responseText=="User contact is already Registered Please Check Email")
                {
                    document.getElementById('contactSend').style.display="none";   
                    document.getElementById('contactCancel').style.display="none";   
                }
                if(req.responseText=="Do you want to send Login Credentials To Email?")  
                {
                    document.getElementById('contactSend').style.display="";   
                    document.getElementById('contactCancel').style.display="";   
                    
                }
            } 
            else
            {
            // alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
 
}
function contactOverlayLogin()
{
    var specialBox = document.getElementById('contactLoginBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#contactLoginOverlay_popup').popup(      
        );  
}

function contactDesignationValidation(){
    
    $("#actionMessage").html("");  
    var designation=document.getElementById("designation").value;
    re = /^[A-Za-z\s]+$/;
    if(!re.test(designation)||designation.length<2)
    {
        $("#InsertContactInfo").html(" <b><font color='red'>must be valid designation</font></b>.");
        $("#designation").css("border", "1px solid red");
        return false;
    }
    else
    {
        $("#InsertContactInfo").html("");
        $("#designation").css("border", "1px solid green");

    }
    return true;
}

function removeActionMessage(){
    $("#actionMessage").html("");  
} 

function getCsrDetailsTable()
{
    var org_id=document.getElementById("viewAccountID").value;
    //alert(org_id);
    var csrName=document.getElementById("csrName").value;
    var csrEmail=document.getElementById("csrEmail").value;
    var csrphone=document.getElementById("csrPhone").value;
    var csrStatus=document.getElementById("csrStatus").value;
    var url='../acc/getCsrDetailsTable.action?orgUserId='+org_id+'&csrName='+csrName+'&csrEmail='+csrEmail+'&csrphone='+csrphone+'&csrStatus='+csrStatus;
    // alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                //alert(req.responseText)
                populateCsrTable(req.responseText)
            } 
            else
            {
            // alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
 
}
function populateCsrTable(response){

    var eduList=response.split("^");
    
    var table = document.getElementById("csrDetailsTable");
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
                //alert("row--?"+row);
      
                $("#csrDetailsTable").append(row); 
                row.append($('<td><a href="#" class="csraccountspopup_open" onclick="csraccountspopup('+Values[0]+')">' + Values[1] + "</a></th>"));
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($("<td>" + Values[4] + "</td>"));
                row.append($("<td>" + Values[5] + "</td>"));
                row.append($("<td>" + Values[6] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
            }
        }
    }
    else {
        var row = $("<tr />")
        $("#csrDetailsTable").append(row);
        row.append($('<td colspan="6"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
  
    reqPager.init(); 
    reqPager.showPageNav('reqPager', 'csrDetailsTablepageNavi'); 
    reqPager.showPage(1);
}
   

function doAddAccountToCsr()
{
    //    alert("hello")
    var org_id=document.getElementById("orgUId").value;
    var csrId=document.getElementById("csrId").value;
    var url='../acc/doAddAccountToCsr.action?orgUserId='+org_id+'&csrId='+csrId;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                if(req.responseText>0){
                    $("csrResult").html(" <b><font color='green'>Account successfully added to CSR</font></b>.");
                }
                else
                {
                    $("csrResult").html(" <b><font color='red'>Account Already Exist for CSR</font></b>.");
                }
            } 
            else
            {
            // alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
  
}
function csrStatusChange(){
    var csrId=document.getElementById("csrIdInOverlay").value;
    var csrStatus=document.getElementById("csrStatusOverlay").value;
    var orgUserId=document.getElementById("accountSearchID").value;
   
    var url='../acc/csrStatusChange?csrId='+csrId+'&csrStatus='+csrStatus+'&orgUserId='+orgUserId;
    
    // alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                //alert(req.responseText)
                getCsrDetailsTable();
            }
            else
            {
            // alert("Error occured");
            }
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function csraccountspopup(id){
    // alert(id);
    document.getElementById("csrIdInOverlay").value=id;
    setupOverlay('csraccoutsoverlay','#csraccountspopup');
}
function csraccountspopupclose(){
 
    setupOverlay('csraccoutsoverlay','#csraccountspopup');
}
function setupOverlay(overlayBox, popupId){
    var specialBox = document.getElementById(overlayBox);
    if(specialBox.style.display == "block"){
        specialBox.style.display = "none";
    } else {
        specialBox.style.display = "block";
    }
    // Initialize the plugin
    $(popupId).popup();
};
function setTransferCopy(){
    var type=$('#typeTransfer').val();
    document.getElementById("fromCSR").value="";
    document.getElementById("toCSR").value="";
    document.getElementById("fromCSRID").value="";
    document.getElementById("toCSRID").value="";
    document.getElementById("validationMessage").value="";
    
//    if(type=='TA'){
//        $("#transfer").css('visibility', 'visible');
//        $("#copy").css('visibility', 'hidden');
//    }
//    else{
//         $("#transfer").css('visibility', 'hidden');
//        $("#copy").css('visibility', 'visible');
//    }

};
function validateAccounts(){
    var fromCSR=$("#fromCSRID").val();
    var toCSR=$("#toCSRID").val();
    if(fromCSR==""){
        $("#validationMessage").html("<font color='red'>Please enter existing fromCSR</font>");
        $("#fromCSR").css('border','1px solid red');
        return false;
    }  
    else{
        $("#validationMessage").html("");
        $("#fromCSR").css('border','1px solid #ccc');
       
    }
    if(toCSR==""){
        $("#validationMessage").html("<font color='red'>Please enter existing toCSR</font>");
        $("#toCSR").css('border','1px solid red');
        return false;
    }  
    else{
        $("#validationMessage").html("");
        $("#toCSR").css('border','1px solid #ccc');
        
    }
    return true;
}
function transferAccounts(){

    var fromCSRID=$("#fromCSRID").val();
    var toCSRID=$("#toCSRID").val();
    var typeTransfer=$("#typeTransfer").val();
    var fromCSR=$("#fromCSR").val();
    var toCSR=$("#toCSR").val();
    var title1;
    if(validateAccounts()){
        if(typeTransfer=='TA')    
            title1="Are you sure to Transfer?";
        else
            title1="Are you sure to Copy?";
    
        swal({
    
            title: title1,
  
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
                var url='../acc/assignAccount.action?fromCSRID='+fromCSRID+'&toCSRID='+toCSRID+'&typeTransfer='+typeTransfer;
                //alert(url)
                var req=initRequest(url);
                //alert("hih")
                req.onreadystatechange = function() {
                    if (req.readyState == 4) {
                        // alert("hi res")
                        if (req.status == 200) {
                            //alert(req.responseText)
                            if(req.responseText=="success"){
                                if(typeTransfer=='TA')
                                    document.getElementById("validationMessage").innerHTML="<font color='green'>Accounts Transfer successfully</font>";
                                else
                                    document.getElementById("validationMessage").innerHTML="<font color='green'>Accounts Copied successfully</font>";
                            }
                            else
                            {
                                if(typeTransfer=='TA')      
                                    document.getElementById("validationMessage").innerHTML="<font color='green'>no Accounts to Transfer</font>";
                                else
                                    document.getElementById("validationMessage").innerHTML="<font color='green'>no Accounts to Copy</font>";  
                            }
                        } 
                    }
                };
                req.open("GET",url,"true");
                req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                req.send(null);
    
                if(typeTransfer=='TA')
                    swal("Transfered!", "Accounts Transferred....", "success");
                else
                    swal("Copied!","Accounts Copied....","success");    
            } else {
                if(typeTransfer=='TA')
                    swal("Cancelled", "Transferring cancelled ", "error");
                else
                    swal("Cancelled","Copping cancelled","error") ;
            }
        });

    } 
    return false;
   
}
function getCSR(){
    //alert("csr");
    var fromCSR=$("#fromCSR").val();
    //var toCSR=$("#toCSR").val();
    if(fromCSR =="")
    {
        clearTable();
    }
    else if(fromCSR!=""){
        if(fromCSR.length>=3)
        {
            url=CONTENXT_PATH+"/getCsrNamesAutoPopulate.action?csrName="+fromCSR;
            //alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        //alert("jagan"+req.responseXML);
                        parseCsrFromname(req.responseXML);
                    //alert("hafan");
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

function parseCsrFromname(responseXML) {
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

            appendEmpFromCsrname(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("fromCSR"));
        
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
function appendEmpFromCsrname(empName,loginId) {
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
        "javascript:setEmpFromCsrName('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}
function setEmpFromCsrName(empName,loginId){
    //alert("setemp")
    clearTable();
    // alert("in set_cust");
    var csrTo=document.getElementById("toCSRID").value
    if(loginId==csrTo)
    {
        document.getElementById("validationMessage").innerHTML="<font color='red'>From Csr and To csr Should not be same</>"        
        document.getElementById("fromCSR").value ="";
        document.getElementById("fromCSRID").value ="";
          
    }
    else
    {
        document.getElementById("fromCSR").value =empName;
        document.getElementById("fromCSRID").value =loginId;
        
    }    
   
}
function clearTable() {
    //alert("clearTable")
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




function getToCSR(){
    //alert("csr");
    var fromCSR=$("#toCSR").val();
    //var toCSR=$("#toCSR").val();
    if(fromCSR =="")
    {
        clearTable();
    }
    else if(fromCSR!=""){
        if(fromCSR.length>=3)
        {
            url=CONTENXT_PATH+"/getCsrNamesAutoPopulate.action?csrName="+fromCSR;
            //alert(url)
            //            }
            //alert("url-->"+url);
            var req = initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //alert(v_empName)
                        //alert(req.responseXML);
                        parseToCsrname(req.responseXML);
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
function parseToCsrname(responseXML) {
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

            appendEmpForToCsrname(empName.childNodes[0].nodeValue,loginId.childNodes[0].nodeValue);
        }
        var position;
        position = findPosition(document.getElementById("toCSR"));
        
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
function appendEmpForToCsrname(empName,loginId) {
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
        "javascript:setEmpForToCsrName('"+empName +"','"+ loginId +"')");
    linkElement.appendChild(document.createTextNode(empName));
    linkElement["onclick"] = new Function("hideScrollBar()");
    nameCell.appendChild(linkElement);
}
function setEmpForToCsrName(empName,loginId){
    clearTable();
    // alert("in set_cust");
  
    var fromCsr=document.getElementById("fromCSRID").value
    if(fromCsr == loginId)
    {
        document.getElementById("validationMessage").innerHTML="<font color='red'>From Csr and To csr Should not be same</>"        
        document.getElementById("toCSR").value ="";
    }
    else
    {
        document.getElementById("toCSR").value =empName;
        document.getElementById("toCSRID").value =loginId;        
    }
}

function csrTerminateOverlay()
{
    
    var specialBox = document.getElementById('csrTerminateBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#csrTerminateOverlay_popup').popup(      
        );  
    $("#outputMessageOfUpdate").html("");
}

function csrTerminateOverlayValue(name,usrId){
    // alert(name);
    //alert(usrId);
    document.getElementById('csrName').innerHTML=name;
    document.getElementById('userId').value=usrId;
}
//function csrTermination(usrId){
//    //alert(usrId);
//    // var x;
//    if (confirm("Terminate the CSR?") == true) {
//        // x = "You pressed OK!";
//        var url='users/general/csrTermination.action?userid='+usrId;
//        alert(url);
//        var req=initRequest(url);
//        req.onreadystatechange = function() {
//            if (req.readyState == 4) {
//                if (req.status == 200) {
//                    //document.getElementById("demo").value =responseText;
//                    populateCsrSearchTable(req.responseText);
//                } 
//                else
//                {
//                    alert("Error occured");
//                }
//            }
//        };
//        req.open("GET",url,"true");
//        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//        req.send(null);
//       
//    } 
////document.getElementById("demo").innerHTML = x;
//}
function csrTermination(usrId){
    swal({
    
        title: "Terminate the CSR?",
  
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
            var url='users/general/csrTermination.action?userid='+usrId;
            //alert(url);
            var req=initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        //document.getElementById("demo").value =responseText;
                        populateCsrSearchTable(req.responseText);
                        swal("Terminated!", "Terminated Successfully....", "success");
                    } 
                    else
                    {
                        swal("notTerminated!", "Termination Not Done....", "error");
                    }
                }
            };
            req.open("GET",url,"true");
            req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            req.send(null);
       
   
   
    
        } else {
     
            swal("Cancelled", "Termination cancelled ", "error");
 
      
        }
    });
}


function populateCsrSearchTable(response){
    // alert(response);  
    var eduList=response.split("^");
    //var OrgID= document.getElementById("accountSearchID").value;
    var table = document.getElementById("csrResults");
    
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        for(var i=0;i<eduList.length-1;i++){   
       
            var Values=eduList[i].split("|");
            {  
                var row = $("<tr />")
                //alert("row--?"+row);
                $("#csrResults").append(row); 
                // row.append($("<td>" + Values[0] + "</td>"));
                row.append($("<td>" + Values[1] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                //row.append($("<td>" + Values[4] + "</td>"));
                row.append($('<td><a href="getCsrAccounts.action?userId='+ Values[0] +'&csrName='+ Values[1] +'" > '+ Values[4] + "</td>"));
                row.append($("<td><a href='#' onclick=csrTermination(" + Values[0] + ")><img src='../../includes/images/delete.png' height=20 width=20 ></td>"));
            // attach_row.append($("<td><figcaption><button type='button' id='id' value="+ Values[4] +" onclick=doConsultAttachmentDownload("+ Values[4] +")><img src='../../includes/images/download.png' height=20 width=20 ></button></figcaption></td>"));
            }
        }
    }
    else {
        var row = $("<tr />")
        $("#csrResults").append(row);
        row.append($('<td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
  
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}
function csrStatusValue(orgId,userId){
    // alert(userId);
    // alert(orgId);
    document.getElementById("overlayUserId").value=userId;
    document.getElementById("overlayOrgId").value=orgId;
}

function changeCsrAccountStatus(){
    
    var orgId= document.getElementById("overlayOrgId").value;
    var usrId= document.getElementById("overlayUserId").value;
    var status=document.getElementById("status").value;
    // alert(usrId);
    //alert(orgId); 
    //alert(status);
    var url='users/general/changeCsrAccountStatus.action?userid='+usrId+'&orgId='+orgId+'&status='+status;
    var req=initRequest(url);
    //alert(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            
            if (req.status == 200) {
                document.getElementById("outputMessageOfUpdate").innerHTML="Status is Updated"
                populateCsrAccountsTable(req.responseText);
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
function populateCsrAccountsTable(response){
    // alert(response);  
    var eduList=response.split("^");
    var table = document.getElementById("csrAccountResults");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        for(var i=0;i<eduList.length-1;i++){   
       
            var Values=eduList[i].split("|");
            {  
         
         
                var row = $("<tr />")
                //alert("row--?"+row);
      
                $("#csrAccountResults").append(row);
                //row.append($("<td>" + Values[0] + "</td>")); //csrId
                // row.append($("<td>" + Values[1] + "</td>"));//csrOrgid
                row.append($("<td>" + Values[2] + "</td>"));
                //row.append($("<td>" + Values[3] + "</td>"));//status
                row.append($('<td><a href="#" class="csrTerminateOverlay_popup_open" onclick="csrTerminateOverlay();csrStatusValue('+Values[1]+','+Values[0]+')">'  + Values[3] + "</td>"));
            //row.append($("<td>" + Values[4] + "</td>"));
            //row.append($("<td>" + Values[7] + "</td>"));
            //onclick="saveContactDetails(' + Values[0] +');" > '
            }
        }
    }
    else {
        var row = $("<tr />")
        $("#csrAccountResults").append(row);
        row.append($('<td colspan="2"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
  
    pager.init(); 
    pager.showPageNav('pager', 'csrAccPageNavPosition'); 
    pager.showPage(1);
}
function accountSearch(){
    
    var status= document.getElementById("csrStatus").value;
    var usrId= document.getElementById("csrUserId").value;
    var accountName= document.getElementById("accountName").value;
    //alert(usrId);
    //alert(orgId); 
    //alert(accountName);
    //alert(status);
    var url='users/general/getCsrAccount.action?userid='+usrId+'&accountName='+accountName+'&status='+status;
    var req=initRequest(url);
    //alert(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            
            if (req.status == 200) {
                populateCsrAccountsTable(req.responseText);
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


function removeErrorMsg()
{
    var toCSR=$("#toCSR").val();
    var fromCSR=$("#fromCSR").val();
    if(toCSR==""){
        document.getElementById("toCSRID").value=""; 
    }
    if(fromCSR==""){
        document.getElementById("fromCSRID").value=""; 
    }
    //alert("hello jagan")
    $("#validationMessage").html("");
    $("#fromCSR").css('border','1px solid #ccc');
    $("#toCSR").css('border','1px solid #ccc');
    return false;
}

function getEmpCategories(){
    
    // var userId=document.getElementById("userId").value;
    var empStatus= document.getElementById("empStatus").value;
    
    var empName= document.getElementById("empName").value;

    // var empName= document.getElementById("teamMemberNamePopup").value;
    var empId= document.getElementById("teamMemberId").value;

    var empCategoryType= document.getElementById("categoryType").value;
    
    // var empCategoryName= document.getElementById("categoryNames").value;

    // alert(userId);
    // alert(empStatus); 
    // alert(empName);
    // alert(empCategoryType);
    var url='users/general/getEmpCategories.action?empId='+empId+'&empName='+empName+'&status='+empStatus+'&categoryType='+empCategoryType;
    var req=initRequest(url);
    //alert(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            
            if (req.status == 200) {
                populateEmpCategoriesTable(req.responseText);
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

function populateEmpCategoriesTable(response){
    //  alert(response);  
    var eduList=response.split("^");
    var table = document.getElementById("empCategorizationResults");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        for(var i=0;i<eduList.length-1;i++){   
       
            var Values=eduList[i].split("|");
            {  
         
                
                var row = $("<tr />")
                //alert("row--?"+row);
                
                var x=Values[4]; 
                $("#empCategorizationResults").append(row);
                //row.append($("<td>" + Values[0] + "</td>")); //groupingId
                // row.append($("<td>" + Values[1] + "</td>"));//usrOrgid
                row.append($("<td><a href='getUserGroping.action?groupingId="+Values[0]+"'>" + Values[5] + "</a></td>"));
                // row.append($('<td><a href="#" class="csrTerminateOverlay_popup_open" onclick="csrTerminateOverlay();csrStatusValue('+Values[1]+','+Values[0]+')">'  + Values[3] + "</td>"));

                row.append($('<td><a href="#" class="categorizationOverlay_popup_open" onclick="categorizationOverlay(); getEmpCategoryNames('+ x +')">' + Values[2] + "</td>"));
                row.append($("<td>" + Values[7] + "</td>"));
                row.append($("<td>" + Values[6] + "</td>"));
                row.append($("<td>" + Values[8] + "</td>"));
                row.append($("<td><a href='#' onclick=empCategoryTermination(" + Values[0] + ")><img src='../../includes/images/delete.png' height=20 width=20 ></td>"));
            //row.append($("<td>" + Values[4] + "</td>"));
            //row.append($("<td>" + Values[7] + "</td>"));
            //onclick="saveContactDetails(' + Values[0] +');" > '
            }
        }
    }
    else {
        var row = $("<tr />")
        $("#empCategorizationResults").append(row);
        row.append($('<td colspan="7"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
  
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}
//function empCategoryTermination(groupingId){
//    
//    // alert(groupingId);
//    if (confirm("Do you want delete Employee in this category") == true) {
//    var url='users/general/empCategoryTermination.action?groupingId='+groupingId;
//    var req=initRequest(url);
//    //alert(url);
//    req.onreadystatechange = function() {
//        if (req.readyState == 4) {
//            
//            if (req.status == 200) {
//                populateEmpCategoriesTable(req.responseText);
//            } 
//            else
//            {
//                alert("Error occured");
//            }
//        }
//    };
//    req.open("GET",url,"true");
//    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//    req.send(null);
//    }
//}
function empCategoryTermination(groupingId){
    swal({
    
        title: "Do you want delete Employee in this category",
  
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
            var url='users/general/empCategoryTermination.action?groupingId='+groupingId;
            var req=initRequest(url);
            //alert(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4) {
            
                    if (req.status == 200) {
                        //populateEmpCategoriesTable(req.responseText);
                        getEmpCategories();
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
   
   
    
        } else {
     
            swal("Cancelled", "Deleted cancelled ", "error");
 
      
        }
    });
}

function categorizationOverlay()
{
    // alert("overlay");
    var specialBox = document.getElementById('categorizationBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#categorizationOverlay_popup').popup(      
        );  
}
function getEmpCategoryNames(categoryList){
    //alert(categoryList);
    if(categoryList==""){
        categoryList=null;
    }
    //alert(categoryList);

    var url='users/general/getEmpCategoryNames.action?categoryNamesList='+categoryList;//+'&empName='+empName+'&status='+empStatus+'&categoryType='+empCategoryType;
    var req=initRequest(url);
    //alert(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            
            if (req.status == 200) {
                // alert(req.responseText);
                populateOverLayCategoriesTable(req.responseText);
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

function populateOverLayCategoriesTable(response){
    // alert(response);  
    var dashBoardReq=response.split("^");
    var table = document.getElementById("empCategorizationTableOverlay");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){

        var total=0;
        for(var i=0;i<dashBoardReq.length-1;i++){   
            //alert(techReviewList[0])
            var Values=dashBoardReq[i].split("|");
            {  
                var row = $("<tr />")
                $("#empCategorizationTableOverlay").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                row.append($("<td>" + Values[1] + "</td>"));
            }
        }
       
    }
    else{
        var row = $("<tr />")
        $("#empCategorizationTableOverlay").append(row);
        row.append($('<td colspan="1"><font style="color: red;font-size: 15px;">No Assigned Groups</font></td>'))
    }

}
function fileFormatValidation(){
    var file=$("#taskAttachment").val();
    alert(file);
    if(file!='')
    {
        var allowed_extensions = new Array("jpg","gif","png");
        var file_extension = file.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

        for(var i = 0; i < allowed_extensions.length; i++)
        {
            if(allowed_extensions[i]==file_extension)
            {
                $("#InsertContactInfo").html("");
                return true; // valid file extension
            }
        }
        $("#InsertContactInfo").html(" <b><font color='red'>The file uploaded is invalid type</font></b>");

        return false;
    }
    return true;
}
   
