function addSOWAttachmentOverLay(){
    specialBox = document.getElementById('SOWAttachBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#sowAttachment_popup').popup( );
}
function doSOWAttachmentDownload(acc_attachment_id)
{
    var consultantId=$('#consultantId').val();
    var requirementId=$('#requirementId').val();
    var customerId=$('#customerId').val();
    var vendorId=$('#vendorId').val();
    var consultantName=$('#consultantName').val();
    var vendorName=$('#vendorName').val();
    var requirementName=$('#requirementName').val();
    var customerName=$('#customerName').val();
    var status=$('#status').val();
    var rateSalary=$('#rateSalary').val();
    var url='sowDownloadAttachment.action?acc_attachment_id='+acc_attachment_id
    +'&consultantId='+consultantId
    +'&requirementId='+requirementId
    +'&customerId='+customerId
    +'&vendorId='+vendorId
    +'&consultantName='+consultantName
    +'&vendorName='+vendorName
    +'&requirementName='+requirementName
    +'&customerName='+customerName
    +'&status='+status
    +'&rateSalary='+rateSalary
    ;
    //alert(url)
     window.location = url;
    //window.location = 'sowDownloadAttachment.action?acc_attachment_id='+acc_attachment_id;
}
function migration_overlay(cName,consult_id,reqId){
    document.getElementById('consultid').value=consult_id;
    document.getElementById('req_Id').value=reqId;
    //alert(cName+"AND "+consult_id+" AND "+reqId+" hidden>"+document.getElementById('consultid').value);
    var loginId=document.getElementById("loginId").value;
    var usrName=cName.substring(cName.lastIndexOf('@')+1);
    var login= loginId.substring(loginId.lastIndexOf('@')+1);
    // var emailId;
    //alert(usrName+"pop......"+login+"..............................")
    if(usrName==login)
    {
        document.getElementById('migrationEmailId').value=cName;
    }
    else{
        document.getElementById('migrationEmailId').value="";  
    }
    var specialBox = document.getElementById("recruiterBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#Migration_popup').popup(      
        );    
    $("mig").html("");
           
}
function migration_overlayClose(){
    
    var specialBox = document.getElementById("recruiterBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#Migration_popup').popup(      
        );    
    $("mig").html("");        
}
function userMigration(){
    // alert("in mig")
    var vendor=$('#vendor').val();
    var req_Id=document.getElementById("req_Id").value;
    var consult_id=document.getElementById("consultid").value;
    //var vConsult=document.getElementById("vConsult").value;
    var migrationEmailId=document.getElementById("migrationEmailId").value;
    var loginId=document.getElementById("loginId").value;
    // var cName=$("#cName").val();
    
    // var usrName=cName.substring(0,cName.lastIndexOf('@')+1);
    var emailId = migrationEmailId.substring(
        migrationEmailId.lastIndexOf('@') + 1);
        
    var login= loginId.substring(loginId.lastIndexOf('@')+1);
    if(emailId==login)
    {
        //        var con=usrName.concat(emailId);
        //        document.getElementById('migrationEmailId').value=con;
        //        emailId=document.getElementById('migrationEmailId').value;   
       
      
        var url="Requirements/userMigration.action?req_Id="+req_Id+"&consult_id="+consult_id+"&migrationEmailId="+migrationEmailId;
        //alert(url)
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
                //alert(req.responseText)
                if(req.responseText==1){
                    $("mig").html(" <b><font color='green'>migrated Succesfully</font></b>");
                }else if(req.responseText==2){
                    $("mig").html(" <b><font color='red'>This User Already Migrated!</font></b>");
                }else{
                    $("mig").html(" <b><font color='red'>Error Occured</font></b>");
                }
            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    else{
        $("mig").html(" <b><font color='red'>enter valid EmailId</font></b>");
    // alert("enter valid email")
    }
    return false;
}

function sowValidation(){
    var rateSalary=$('#rateSalary').val();
    //alert(rateSalary)
    var rVal=true;
    if(rateSalary=="" || rateSalary==null){
        $("e").html(" <b><font color='red'>Pay Rate shoud not empty!</font></b>");
        $("#errorSpan").show().delay(5000).fadeOut();
        rVal=false;
    }
    return rVal;
}
function sowAttachmentValidation(){
    var file=$('#file').val();
    //alert(file)
    var p=file.lastIndexOf(".");
    var e=file.substring(p+1, file.length);
    //alert(e)
        var rVal=false;
        if(e=="pdf" || e=="doc" || e=="docx"){
            rVal=true;
        }else{
            $("attachTag").html(" <b><font color='red'>only pdf or doc files allowed!</font></b>");
            $("#attachSpan").show().delay(5000).fadeOut();
        }
    return rVal;
}