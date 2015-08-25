/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function setupAddProjectOverlay(){
    setupOverlay('addProjectOverlay','#addProject_popup');
};

function setupAddSubProjectOverlay(){
    setupOverlay('addSubProjectOverlay','#addSubProject_popup');
};

function setupAddProjectTeamMemberOverlay(){
    setupOverlay('addProjectTeamMemberOverlay','#addProjectTeamMember_popup');
};

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

var subProjectsAreLoaded = true;
var projectTeamisLoaded  = false;

function loadSubProjects(parentProjectId){
    if(!subProjectsAreLoaded){
        ajaxReplaceDiv('/getSubProjects','#subProjects','parentProjectID='+parentProjectId);
        subProjectsAreLoaded =true;
    }
};

function loadProjectTeamMembers(parentProjectId){
    if(!projectTeamisLoaded){
        ajaxReplaceDiv('/getProjectsTeamMembers','#projectTeam','projectID='+parentProjectId);
        projectTeamisLoaded=true;
    }
};


function ajaxReplaceDiv(actionUrl,divId,data)
{
    data= (typeof data === 'undefined') ? '{}' : data;
    $.ajax({
        type: "POST",
        url:CONTENXT_PATH+actionUrl,
        data:data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success:function(data){
            $(divId).children().remove();
            $(divId).html(data);
        }
    });
};


var projName;

function checkProjectName(projName){

    $.ajax({
        url:"./checkProjectNames.action?projectName="+ projName,
        success: function(data){

            if(data == "true"){
                $("updateProject").html(" <b><font color='red'>Project name exists!</font></b>");
               // document.getElementById("projectNameError").style.display = "block";
                 document.getElementById("editprojectName").value="";
                $("#editprojectName").focus();
            }

            else{
                $("updateProject").html("<b><font color='green'>Project name is valid.</font></b>");
                 $("#editprojectName").css('border','1px solid green');
               // $("#projectNameError").html("Project name is valid.");
               // document.getElementById("projectNameError").style.display = "none";
            }

        },
        type: 'GET'
    });
};


//developed by rk////////////////////////////////////////////////////////////////////////////////////////




function addVendorTierOverlay(){
    //$("#AddVendorTierOverlay").reset();
    var specialBox = document.getElementById("AddVendorTierOverlay");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#addVendorTier_popup').popup(      
        );    
    return false;
}
function editVendorTierOverlayClose(){
   var specialBox = document.getElementById("EditVendorTierOverlay");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#editVendorTier_popup').popup(      
        );    
            $("e1").html("");
    return false;  
}
function editVendorTierOverlay(tierId){
    
    $('#tierId').val(tierId);
    //+'&vendorTier='+vendorTier
    
    var url='editVendorTierOverlay.action?tierId='+tierId;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText+"hhhhhhhhhh")
            populateVendorTierOverlay(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);

    var specialBox = document.getElementById("EditVendorTierOverlay");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#editVendorTier_popup').popup(      
        );    
    return false;
}

function populateVendorTierOverlay(response){
//alert(response)
    document.getElementById("vendorTierStatus").value="";
    document.getElementById("vendorTier").value="";
    document.getElementById("PF").value="";
    var Values=response.split("|");
    
   document.getElementById("vendorTierStatus").value=Values[1];
    document.getElementById("vendorTier").value=Values[0];
    if(Values[2]==1)
        document.getElementById('PF').checked=true;
   else
        document.getElementById('PF').checked=false;

}
function getVendors(){
    //alert("hi")
    var accountSearchID=$('#accountSearchID').val();

    var url='getDefaultVendorTiers.action?accountSearchID='+accountSearchID;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        document.getElementById('loadingVendor').style.display = 'block';
        if (req.readyState == 4 && req.status == 200) {
            $('#loadingVendor').hide();
            populateVendorTierTable(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}

function populateVendorTierTable(response){
    //alert(response)
    var techReviewList=response.split("^");
    //alert(techReviewList[0])
    var table = document.getElementById("vendorTierTable");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        
        for(var i=0;i<techReviewList.length-1;i++){   
            //alert(techReviewList[0])
            var Values=techReviewList[i].split("|");
            {  
                var row = $("<tr/>")
                $("#vendorTierTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                row.append($('<td><a href="#" class="editVendorTier_popup_open" onclick="editVendorTierOverlay('+Values[5]+');">'+Values[6]+"</td>"));
                if(Values[0]!='null' && Values[0]!=""){
                    row.append($('<td>'+Values[0]+"</td>"));
                }else{
                    Values[0]="";
                    row.append($('<td>'+Values[0]+"</td>"));
                }
                row.append($("<td>" + Values[2] + "</td>"));
                if(Values[7]==1){
                    pf="Yes";
                }else{
                    pf="No";
                }
                row.append($("<td>" +pf+ "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
            }
        }
    }
    else{
        var row = $("<tr />")
        $("#vendorTierTable").append(row);
        row.append($('<td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'vendorTierTablepageNavPosition'); 
    pager.showPage(1);
}
function addVendorTierType(){
    var accountSearchID=$('#accountSearchID').val();
    var vendorTier=$('#vendorTier').val();
    var url='addVendorTierToCustmer.action?accountSearchID='+accountSearchID+'&vendorTier='+vendorTier;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert("Success")
            $("e").html(" <b><font color='green'>vendor tier added Succesfully</font></b>");
            getVendors();
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}
function editVendorTierDetails(){
    
    var accountSearchID=$('#accountSearchID').val();
    var vendorTierStatus=$('#vendorTierStatus').val();
    var vendorTier=$('#vendorTier').val();
    var pf=$("#PF").is(':checked') ? 1 : 0;  
    var tierId=$('#tierId').val();
 var flag=true;
    if(pf==0 && vendorTier==0 ){
        flag=false;
        $("e1").html(" <b><font color='red'>select either Tier or Head Hunter</font></b>");
    }
    //alert("HIIIIIIIIIIIIII"+accountSearchID+" "+vendorTierStatus+"  "+tierId)
   if(flag==true){
       var url='editVendorTierDetails.action?accountSearchID='+accountSearchID+'&tierId='+tierId+'&vendorTierStatus='+vendorTierStatus+'&VendorTierId='+vendorTier+'&pf='+pf;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert("Success")
            $("e1").html(" <b><font color='green'>vendor tier updated Succesfully</font></b>");
            getVendors();
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
   }
    return false;
}

function searchVendorTier(){
    var vendorTierType=$('#vendorTierType').val();
    var TierStatus=$('#TierStatus').val();
    var accountSearchID=$('#accountSearchID').val();

    //alert("HIIII "+vendorTierType+" "+TierStatus+"  "+accountSearchID)
    var url='searchVendorTierDetails.action?vendorTierType='+vendorTierType+'&TierStatus='+TierStatus+'&accountSearchID='+accountSearchID;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        document.getElementById('loadingVendor').style.display = 'block';
        if (req.readyState == 4 && req.status == 200) {
            $('#loadingVendor').hide();
            populateVendorTierTable(req.responseText)
        
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}

function reqSkillSetOverlay(response){
    document.getElementById("reqSkillSetDetails").value=response;
    var specialBox = document.getElementById("projectSkillSetBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#projectSkillOverlay_popup').popup(      
        ); 
}

function reqSkillSetOverlayClose(){
    var specialBox = document.getElementById('projectSkillSetBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#projectSkillOverlay_popup').popup(      
        );
}
function projectDescriptinOverlay(response){
    document.getElementById("projectDescription").value=response;
    var specialBox = document.getElementById("projectDescBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#projectsDescOverlay_popup').popup(      
        ); 
}

function projectDescriptinOverlayClose(response){
    document.getElementById("projectDescription").value=response;
    var specialBox = document.getElementById("projectDescBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#projectsDescOverlay_popup').popup(      
        ); 
}
function subprojectSkillSetOverlay(response){
    document.getElementById("subprojectSkillSetDetails").value=response;
    var specialBox = document.getElementById("subprojectSkillSetBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#subprojectSkillOverlay_popup').popup(      
        ); 
}

function subprojectSkillSetOverlayClose(){
    var specialBox = document.getElementById('subprojectSkillSetBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#subprojectSkillOverlay_popup').popup(      
        );
}
function subprojectDescriptionOverlay(response){
    document.getElementById("subprojectDescription").value=response;
    var specialBox = document.getElementById("subprojectDescBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#subprojectsDescOverlay_popup').popup(      
        ); 
}

function subprojectDescriptionOverlayClose(){
    var specialBox = document.getElementById('subprojectDescBox');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    // Initialize the plugin    
    $('#subprojectsDescOverlay_popup').popup(      
        );
}


function searchTeamMembers(){
    //alert("membersSearchResults");
    var teamMemberName=$('#teamMemberName').val();
    var status=$('#status').val();
    var projectID=$('#projectID').val();
    
    
    var url='getTeamMemberDetails.action?projectID='+projectID+'&teamMemberName='+teamMemberName+'&status='+status;
    // alert(url);
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                // alert(req.responseText);
                populateProjectTeamMembersTable(req.responseText);
            // alert(req.responseText);
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
    return false;
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


function populateProjectTeamMembersTable(response){
    //alert(response)
    var techReviewList=response.split("^");
    //alert(techReviewList[0])
    var table = document.getElementById("membersSearchResults");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        
        for(var i=0;i<techReviewList.length-1;i++){   
            //alert(techReviewList[0])
            var Values=techReviewList[i].split("|");
            {  
                var row = $("<tr/>")
                $("#membersSearchResults").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                //row.append($('<td><a href=editVendorTierDetails.action?VendorTierId='+Values[4]+">" + Values[0] + "</a></td>"));
                row.append($('<td><a href=acc/setTeamMembersForProject.action?userID='+Values[6]+'&projectID='+Values[5]+">"+Values[0]+"</a></td>"));

             
                row.append($("<td>"+Values[1]+"</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($("<td>" + Values[4] + "</td>"));
                
                
            //row.append($("<td>" + Values[4] + "</td>")); we got project id here
            // row.append($("<td>" + Values[5] + "</td>")); we got user id here
                
            }
        }
    }
    else{
        var row = $("<tr />")
        $("#membersSearchResults").append(row);
        row.append($('<td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}


function showResourceTeam(pid){
   
    var ppid=document.getElementById("ppid").value;
    //alert(ppid);
    var projectID=pid;
    var url='showResourceDetails.action?projectID='+projectID+'&ppid='+ppid;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            populateResourceInTable(req.responseText)

        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    setupOverlay('resourceOverlay','#resourceOverlay_popup');  
 
    
}
function populateResourceInTable(responce){
    var techReviewList=responce.split("^");
    //alert(techReviewList[0])
    var table = document.getElementById("resourceTable");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    //alert("praveen")
    
    if(responce.length>0){
        
        for(var i=0;i<techReviewList.length-1;i++){   
            //alert(techReviewList[0])
            var Values=techReviewList[i].split("|");
            {  
                var row = $("<tr/>")
                $("#resourceTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                //row.append($('<td><a href=editVendorTierDetails.action?VendorTierId='+Values[4]+">" + Values[0] + "</a></td>"));
                row.append($("<td>"+Values[0]+"</td>"));
                row.append($("<td>"+Values[1]+"</td>"));
            }
        }
    }
    else{
        var row = $("<tr />")
        $("#resourceTable").append(row);
        row.append($('<td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}
function showResourceTeamOverlayClose(){
    setupOverlay('resourceOverlay','#resourceOverlay_popup');   
}
function addProjectValidation()
{
 //alert("hai");
    var project_statusPopup=document.getElementById("project_statusPopup").value;
    var projectNamePopup=document.getElementById("projectNamePopup").value;
    var projectStartDateOverlay=document.getElementById("projectStartDateOverlay").value;
    var projectTargetDateOverlay=document.getElementById("projectTargetDateOverlay").value;
    if(projectNamePopup=="")
        {
        $("#addProjectValidation").html(" <b><font color='red'>project name is required</font></b>");
        $("#projectNamePopup").css("border", "1px solid red");
        return false;
        }
        if(project_statusPopup==-1)
        {
        $("#addProjectValidation").html(" <b><font color='red'>project status is required</font></b>");
        $("#project_statusPopup").css("border", "1px solid red");
        return false;
        }
        if(projectStartDateOverlay=="")
        {
        $("#addProjectValidation").html(" <b><font color='red'>project start date is required</font></b>");
        $("#projectStartDateOverlay").css("border", "1px solid red");
        return false;
        }
        if(projectTargetDateOverlay=="")
        {
        $("#addProjectValidation").html(" <b><font color='red'>project end date is required</font></b>");
        $("#projectTargetDateOverlay").css("border", "1px solid red");
        return false;
       }
       if(projectStartDateOverlay>=projectTargetDateOverlay)
         {
        $("#addProjectValidation").html(" <b><font color='red'>invalid range</font></b>");
        $("#projectStartDateOverlay").css("border", "1px solid red");
        $("#projectTargetDateOverlay").css("border", "1px solid red");
        return false;
       }  
      else
       {
       $("#addProjectValidation").html("");
       $("#projectStartDateOverlay").css("border", "1px solid #3BB9FF"); 
       $("#projectTargetDateOverlay").css("border", "1px solid #3BB9FF"); 
        $("#projectNamePopup").css("border", "1px solid #3BB9FF"); 
        $("#project_statusPopup").css("border", "1px solid #3BB9FF");
        return true;
       }
       
        
}
function projectDateValidation()
{
    document.getElementById('projectStartDateOverlay').value = "";
     document.getElementById('projectTargetDateOverlay').value = "";
    return false;
};
function removeResults(){
    $("#addProjectValidation").html("");
    $("#projectStartDateOverlay").css("border", "1px solid #3BB9FF"); 
       $("#projectTargetDateOverlay").css("border", "1px solid #3BB9FF"); 
        $("#projectNamePopup").css("border", "1px solid #3BB9FF"); 
        $("#project_statusPopup").css("border", "1px solid #3BB9FF");
        $("#projectNameError").html("");
        resetOverlayForm();
}
function projectTeamMemberValidation(){
   // alert("in");
   var teamMemberName= document.getElementById("teamMemberNamePopup").value;
    var id=document.getElementById("teamMemberId").value;
   // alert(id);
    var designation=document.getElementById("designation").value;
   // alert(designation);
    if(id==0||teamMemberName==""){
    $("#teamMemberNamePopup").css("border","1px solid red");
    $("#validationMessage").html("<b><font color='red'>enter name</font></b>");
    return false;
    }
    else
            {
    $("#teamMemberNamePopup").css("border","1px solid #3BB9FF");
    $("#validationMessage").html("");
    }
    if(designation=="DF"){
        $("#designation").css("border","1px solid red");
    $("#validationMessage").html("<b><font color='red'>select designation</font></b>");
    return false; 
    }
    else
            {
    $("#designation").css("border","1px solid #3BB9FF");
    $("#validationMessage").html("");
    }
   return true;     
}
function updateProjectValidation()
{
 //alert("hai");
    //var project_statusPopup=document.getElementById("project.project_status").value;
    var projectNamePopup=document.getElementById("editprojectName").value;
    var projectStartDateOverlay=document.getElementById("projectStartDate").value;
    var projectTargetDateOverlay=document.getElementById("projectTargetDate").value;
    
    if(projectNamePopup=="")
        {
        $("updateProject").html(" <b><font color='red'>project name is required</font></b>");
        $("#editprojectName").css("border", "1px solid red");
        return false;
        }
         if(projectStartDateOverlay=="")
        {
        $("updateProject").html(" <b><font color='red'>project start date is required</font></b>");
        $("#projectStartDate").css("border", "1px solid red");
        return false;
        }
        if(projectTargetDateOverlay=="")
        {
        $("updateProject").html(" <b><font color='red'>project end date is required</font></b>");
        $("#projectTargetDate").css("border", "1px solid red");
        return false;
       }
       if(projectStartDateOverlay>=projectTargetDateOverlay)
         {
        $("updateProject").html(" <b><font color='red'>invalid range</font></b>");
        $("#projectStartDate").css("border", "1px solid red");
        $("#projectTargetDate").css("border", "1px solid red");
        return false;
       }  
      
       $("updateProject").html("");
       $("#projectStartDate").css("border", "1px solid #3BB9FF"); 
       $("#projectTargetDate").css("border", "1px solid #3BB9FF"); 
        $("#editprojectName").css("border", "1px solid #3BB9FF"); 
        
       
        return true;
      
      
}

function EmpReleasefromProject(userID){
    //alert("divy");
   // alert("prasad-->"+userID);
    var accountID=document.getElementById("accountID").value;
   // alert(accountID+"account")
    var projectID=document.getElementById("projectID").value;
    swal({
    
            title: "Do want to Terminate.....?",
  
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
            
        if(isConfirm){
        var url="./EmpReleasefromProject.action?projectID="+projectID+"&accountID="+accountID+"&userID="+userID;
       // alert(url)
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
               // alert(req.responseText)
                if(req.responseText==1){
                    $("emp").html(" <b><font color='green'> Terminated Succesfully</font></b>");
                }else{
                    $("emp").html(" <b><font color='red'>Error Occured</font></b>");
                }
            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
        swal("Success!", "Successfully Terminated....", "success");
        }
        else
            swal("Cancelled","Terminate cancelled","error") ;
        });
        return false;
   
}
function checkCharactersProjects(id){
    //alert("in sff leave"+id);
    var elem = document.getElementById("charNumProjects");
    $(id).keyup(function(){
        el = $(this);
        if(el.val().length >= 200){
            el.val( el.val().substr(0, 200) );
        } else {
            //alert("in elase")
            elem.style.color="green";
            $("#charNumProjects").text(200-el.val().length+' Characters remaining . ');
        }
        if(el.val().length==200)
        {
            //alert("in cannot")
            elem.style.color="red";
            $("#charNumProjects").text(' Cannot enter  more than 200 Characters .');    
        }
        
    })
    return false;
};
function checkCharactersSkill(id){
    //alert("in sff leave"+id);
    var elem = document.getElementById("charNumSkill");
    $(id).keyup(function(){
        el = $(this);
        if(el.val().length >= 100){
            el.val( el.val().substr(0, 100) );
        } else {
           // alert("in elase")
           elem.style.color="green";
            $("#charNumSkill").text(100-el.val().length+' Characters remaining . ');
        }
        if(el.val().length==100)
        {
           // alert("in cannot")
           elem.style.color="red";
            $("#charNumSkill").text(' Cannot enter  more than 100 Characters .');    
        }
        
    })
    return false;
};
function checkCharProjects(id){
    //alert("in sff leave"+id);
     var elem = document.getElementById("Projects");
    $(id).keyup(function(){
        el = $(this);
        if(el.val().length >= 200){
            el.val( el.val().substr(0, 200) );
        } else {
            //alert("in elase")
            elem.style.color="green";
            $("#Projects").text(200-el.val().length+' Characters remaining . ');
        }
        if(el.val().length==200)
        {
            //alert("in cannot")
            elem.style.color="red";
            $("#Projects").text(' Cannot enter  more than 200 Characters .');    
        }
        
    })
    return false;
};
function checkCharSkill(id){
    //alert("in sff leave"+id);'
    var elem = document.getElementById("Skill");
    $(id).keyup(function(){
        el = $(this);
        if(el.val().length >= 100){
            el.val( el.val().substr(0, 100) );
        } else {
           // alert("in elase")
           elem.style.color="green";
            $("#Skill").text(100-el.val().length+' Characters remaining . ');
        }
        if(el.val().length==100)
        {
           // alert("in cannot")
           elem.style.color="red";
            $("#Skill").text(' Cannot enter  more than 100 Characters .');    
        }
        
    })
    return false;
};

function removeMsg(){
    $("#charNumSkill").html(" ");
    $("#charNumProjects").html("");
    $("#Skill").html(" ");
    $("#Projects").html("");
}
