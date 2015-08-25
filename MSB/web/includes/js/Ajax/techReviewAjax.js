
//==========================================================================================================================

var techReviewDate,reviewAlertDate,reviewStartDate,reviewEndDate,searchInterviewDate;
function doOnLoad() {
                
    $('#techAlertContent').hide();
    $('#techReviewAlert').change(function(){
        if($(this).is(":checked"))
            $('#techAlertContent').fadeIn('slow');
        else
            $('#techAlertContent').fadeOut('slow');
    });
    
    
    techReviewDate = new dhtmlXCalendarObject(["interviewDate"]);
    // alert("hii1");
    techReviewDate.setSkin('omega');
    // alert("hii2");
    //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
    techReviewDate.setDateFormat("%m-%d-%Y %H:%i ");
    
    
    searchInterviewDate = new dhtmlXCalendarObject(["searchInterviewDate"]);
    // alert("hii1");
    searchInterviewDate.setSkin('omega');
    // alert("hii2");
    //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
    searchInterviewDate.setDateFormat("%m-%d-%Y");
    
    reviewAlertDate = new dhtmlXCalendarObject(["reviewAlertDate"]);
    // alert("hii1");
    reviewAlertDate.setSkin('omega');
    // alert("hii2");
    //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
    reviewAlertDate.setDateFormat("%Y/%m/%d %H:%i");
    
    
    
    reviewStartDate = new dhtmlXCalendarObject(["reviewStartDate"]);
    // alert("hii1");
    reviewStartDate.setSkin('omega');
    // alert("hii2");
    //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
    reviewStartDate.setDateFormat("%m-%d-%Y");
    
    reviewEndDate = new dhtmlXCalendarObject(["reviewEndDate"]);
    // alert("hii1");
    reviewEndDate.setSkin('omega');
    // alert("hii2");
    //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
    reviewEndDate.setDateFormat("%m-%d-%Y");

    //default date
    
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth(); //January is 0!
    var yyyy = today.getFullYear();
    //alert(fromDate)
    if(dd<10) {
        dd='0'+dd
    } 
    if(mm<10) {
        mm='0'+mm
    } 
    mm=today.getMonth()+1;
    if(mm<10) {
        mm='0'+mm
    } 
    var dd=today.getDate();
    var dateValue=yyyy+'/'+mm+'/'+dd;
    
//document.getElementById("reviewStartDate").value=dateValue;
    
    
}
function enterDateRepository()
{
    document.getElementById('interviewDate').value = "";
    document.getElementById('reviewAlertDate').value = "";

    alert("Please select from the Calender !");
    return false;
}

function forwardReviewToCustomer(){
    //alert("came")
    var flag=true;
    var reviewAlertDate=new Date($("#reviewAlertDate").val());
    var interviewDate=new Date($("#interviewDate").val());
    var interviewType=$("#interviewType").val();
    var interviewLocation=$("#interviewLocation").val();
    //alert(interviewType)
    if($("#interviewDate").val()=="" ){
        $("e").html(" <b><font color='red'>Interview Date is Mandatory</font></b>");
        flag=false;
        return false;
    }
    else{
        $("e").html("");
    }
    if(interviewType=='Face to Face'){
        if(interviewLocation==""){
            $("e").html(" <b><font color='red'>Location should not be empty for Face to Face interview</font></b>"); 
            flag=false;
        }
        else{
            $("e").html("");
        }    
    }
    if($('#techReviewAlert').prop('checked')){
        if($("#reviewAlertDate").val()=="" ){
            $("e").html(" <b><font color='red'>Tech Review Date is Mandatory</font></b>");
            flag=false;
            //alert("Empty reviewAlertDate")
            return false;
        } 
        else{
            $("e").html("");
        }  
        if(interviewDate<reviewAlertDate){
            $("e").html(" <b><font color='red'>Tech Review Date must be less then Interview Date</font></b>");
            flag=false;
        }
        else{
            $("e").html("");
        }
        
    }
    if(flag){
        
        var requirementId=$('#requirementId').val();
        var consult_id=$('#consult_id').val();
        var interview=$('#interview').val();
        var interviewType=$('#interviewType').val();
        var empIdTechReview=$('#empIdTechReview').val();
        var empIdTechReview2=$('#empIdTechReview2').val();
        //alert(empIdTechReview)
        //alert(empIdTechReview2)
        var interviewDate=$('#interviewDate').val();
        var reviewAlertDate=$('#reviewAlertDate').val();
        var alertMessageTechReview=$('#alertMessageTechReview').val();
        var timeZone=$('#timeZone').val();
        var interviewLocation=$('#interviewLocation').val();

        var url='.././Requirements/techReviewForward.action?requirementId='+requirementId+'&consult_id='+consult_id+'&interview='+interview+'&interviewType='+interviewType+'&empIdTechReview='+empIdTechReview+'&interviewDate='+interviewDate+'&reviewAlertDate='+reviewAlertDate+'&alertMessageTechReview='+alertMessageTechReview+'&timeZone='+timeZone+'&empIdTechReview2='+empIdTechReview2+'&interviewLocation='+interviewLocation;
        ;
        //alert(url)
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
                //alert("Success")
                $("e").html(" <b><font color='green'>Tech Review Forwarded Succesfully</font></b>");
                
                document.getElementById("empIdTechReview").value="";
                document.getElementById("eNameTechReview").value="";
                document.getElementById("interviewDate").value="";
                document.getElementById("interviewLocation").value="";
                document.getElementById("interviewType").selectedIndex = "0";
            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    
    
    return false;

}
function getSearchTechReviewList(){
    var reviewStartDate=$('#reviewStartDate').val();
    var reviewEndDate=$('#reviewEndDate').val();
    var techReviewStatus=$('#techReviewStatus').val();
    // alert(techReviewStatus)
    var url='.././Requirements/getSearchTechReviewList.action?reviewStartDate='+reviewStartDate+'&techReviewStatus='+techReviewStatus+'&reviewEndDate='+reviewEndDate;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            populateTechReviewTable(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}
function populateTechReviewTable(response){
    //alert(response.length)
    var techReviewList=response.split("^");
    var consultFlag="customer";
    var techSearch="search";
    document.getElementById("techSearch").value=techSearch;
    var table = document.getElementById("techReviewTable");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        
        for(var i=0;i<techReviewList.length-1;i++){   
            //alert(techReviewList[0])
            var Values=techReviewList[i].split("|");
            {  
                //$("#requirementId").val(Values[9]);
                //alert($('#requirementId').val()+" "+Values[9])
                var row = $("<tr />")
                $("#techReviewTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                row.append($('<td><a href=../consultant/editConsultantDetails.action?techReviewFlag=techReview&consultFlag='+consultFlag+'&consult_id='+Values[0]+">" + Values[1] + "</a></td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                 row.append($("<td>" + Values[10]+ "</td>"));
                row.append($('<td><a href="#" class="Forwarded_popup_open" onclick="showOverlayForwardedBy('+Values[7]+');">'+ Values[4] +"</td>"));
                //row.append($("<td>" + Values[4] + "</td>"));
                row.append($("<td>" + Values[5] + "</td>"));
                row.append($("<td>" + Values[8] + "</td>"));
                if(Values[8]=='Rejected'){
                    row.append($("<td><figcaption><button type='button' value="+ Values[6] +" ><img src='./../../includes/images/download.png' height='20' width='20' style='opacity:0.3'></button></figcaption></td>"));
                    row.append($('<td><a href="#" class="" style="opacity:0.3"> Click'+"</td>"));
                
                }else{
                    row.append($("<td><figcaption><button type='button' value="+ Values[6] +" onclick=doTechReviewAttachmentDownload("+ Values[6] +")><img src='./../../includes/images/download.png' height='20' width='20' ></button></figcaption></td>"));
                     row.append($('<td><a href="#" class="techReview_popup_open" onclick="techReviewOverlay('+Values[9]+',\'' + Values[8] + '\');techReviewDetailsOnOverlay('+Values[0]+','+Values[11]+');"> Click'+"</td>"));
                }
            }
        }
}
else{
    var row = $("<tr />")
    $("#techReviewTable").append(row);
    row.append($('<td colspan="9"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
}
pager.init(); 
pager.showPageNav('pager', 'pageNavPosition'); 
pager.showPage(1);
}
function doTechReviewAttachmentDownload(acc_attachment_id)
{
    $("#resume").html("");
    var techSearch=document.getElementById("techSearch").value;
    
    //alert(techSearch);
    var reviewStartDate=$('#reviewStartDate').val();
    var reviewEndDate=$('#reviewEndDate').val();
    var techReviewStatus=$('#techReviewStatus').val();
    window.location = './recruitment/consultant/downloadAttachmentTechReview.action?acc_attachment_id='+acc_attachment_id+'&techSearch='+techSearch+'&reviewStartDate='+reviewStartDate+'&techReviewStatus='+techReviewStatus+'&reviewEndDate='+reviewEndDate;
}

function techReviewOverlay(reqId,status){
       $("e").html(" ");
    document.getElementById("techSkill").value="";
    document.getElementById("domainSkill").value="";
    document.getElementById("comSkill").value="";
    document.getElementById("consultantComments").value=""; 
    $("#requirementId").val(reqId);
	$("#finalTechReviewStatus").val(status);
    if(status=="Selected")
    {
        document.getElementById("saveTechReview").style.display="none";
    }
    else
    {
        document.getElementById("saveTechReview").style.display="";   
    }
    var specialBox = document.getElementById("techReviewBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#techReview_popup').popup(      
        );    
    return false;
}
function techReviewDetailsOnOverlay(consultId,conTechId){
    //alert("hi "+consultId)
   var req_Id=$("#requirementId").val()
    var contechId=conTechId;
    var url='.././Requirements/getConsultDetailsOnOverlay.action?consultantId='+consultId+'&contechId='+contechId+'&req_Id='+req_Id;;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            var techReviewList=req.responseText.split("^");
            for(var i=0;i<techReviewList.length-1;i++){   
                var Values=techReviewList[i].split("|");
                {  
                    $("#contechId").val(contechId);
                    $("#consultId").val(consultId);
                    $("#consultantName").val(Values[0]);
                    $("#consultantEmail").val(Values[1]);
                    $("#consultantMobile").val(Values[2]);
                    $("#interviewDate").val(Values[3]);
                    //$("#techTitle").val(Values[5]);
                    $("#ResumeId").val(Values[4]);
                     if(Values[5]=='null' || Values[5]=="")
                    {
                      Values[5]=" "; 
                    }
                    $("#consultantJobTitle").val(Values[5]);
                    
                    if(Values[6]=='null' || Values[6]=="")
                    {
                      Values[6]=" "; 
                    }
                    $("#consultantSkill").val(Values[6]);
                    $("#interviewType").val(Values[7]);
                }
            }
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}

function saveTechReviewResults(){
    var contechId =$("#contechId").val();
    var requirementId=$('#requirementId').val();
    //alert(requirementId)
    var techSkill=$("#techSkill").val();
    var domainSkill=$("#domainSkill").val();
    var comSkill=$("#comSkill").val();
     if(techSkill=="")
     {
     $("e").html(" <b><font color='green'>Technical Skills Should not be empty</font></b>");    
     return false;
     }
    if(domainSkill=="")
     {
     $("e").html(" <b><font color='green'>Domain Skills Should not be empty</font></b>");    
     return false;
     } 
     if(comSkill=="")
     {
     $("e").html(" <b><font color='green'>Communication Skills Should not be empty</font></b>");    
     return false;
     }  
    var rating =((parseInt(techSkill, 10)+parseInt(domainSkill, 10)+parseInt(comSkill, 10))/3);
    var consultantComments=$("#consultantComments").val();
    var finalTechReviewStatus=$("#finalTechReviewStatus").val();
    var techTitle=$("#techTitle").val();
    var consultId=$("#consultId").val();
    var interviewType=$("#interviewType").val();
    //alert("hi "+techSkill+"  "+comSkill+"   "+domainSkill+"  "+consultantComments+"   "+finalTechReviewStatus+"  "+rating+"  "+techTitle+"  "+consultId)
    var url='.././Requirements/saveTechReviewResults.action?techSkill='+techSkill+'&domainSkill='+domainSkill+'&comSkill='+comSkill+'&rating='+rating+'&consultantComments='+consultantComments+'&finalTechReviewStatus='+finalTechReviewStatus+'&techTitle='+techTitle+'&consultId='+consultId+'&requirementId='+requirementId+'&interviewType='+interviewType+'&contechId='+contechId;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            if(req.responseText==1){
                //alert(req.responseText)
                 getSearchTechReviewList()
                $("e").html(" <b><font color='green'>Tech Review Result saved Succesfully</font></b>");
            }else{
                $("e").html(" <b><font color='red'>Error Occured</font></b>");
            }
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}



function showOverlayForwardedBy(id){
    // alert(id)
    var url='getRecruiterOverlay.action?id='+id;
    
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert("result is::")
            // setVendorStates(req.responseText);
            setForwardedBy(req.responseText)
        } 
    //alert(req.readyState +" and "+req.status)

    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);   
   
    var specialBox = document.getElementById("recruiterBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#Forwarded_popup').popup(      
        );    
    return false;
}
function setForwardedBy(response){
    var Values=response.split("|");
    document.getElementById("recruiterNameOverlay").value=Values[0];
    document.getElementById("recruiterEmailIdOverlay").value=Values[1];
    document.getElementById("recruiterPhoneOverlay").value=Values[2];

}
function closeForwardedByOverlay()
{
    var specialBox = document.getElementById("recruiterBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#Forwarded_popup').popup(      
        );    
    return false;    
}

function searchTechReviews(){
    
    var requirementId=$('#requirementId').val();
    var consult_id=$('#consult_id').val();
    var interviewDate=$('#searchInterviewDate').val();
    var empIdTechReview=$('#empIdTechReview').val();
    //alert("HIIIIIIIIIIIIIIIIII "+requirementId+" "+consult_id+" "+interviewDate+" "+empIdTechReview)
    var url='.././Requirements/getConsultantTechReviews.action?requirementId='+requirementId+'&consult_id='+consult_id+'&interviewDate='+interviewDate+'&empIdTechReview='+empIdTechReview;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            populateTechReviewSearchTable(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;

}
function populateTechReviewSearchTable(response){
    /// alert(response)
    var techReviewList=response.split("^");
    
    var table = document.getElementById("techReviewSearchTable");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){
        
        for(var i=0;i<techReviewList.length-1;i++){   
            //alert(techReviewList[0])
            var Values=techReviewList[i].split("|");
            {  
                //alert(Values[3]+" "+Values[7])
                $("#consult_id").val(Values[0]);
                $("#requirementId").val(Values[1]);
                $("#reviewType").val(Values[9]);
                $("#forwardedToId").val(Values[7]);
                
                var row = $("<tr />")
                $("#techReviewSearchTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                //row.append($("<td>" + Values[9] + "</td>"));
                row.append($('<td><a href="#" class="techReviewResults_popup_open" onclick="techReviewResultsToViewOnOverlay();techReviewResultsOverlay();" >'+Values[9]+"</td>"));
                //row.append($('<td><a href="#" class="emailPhoneShow_popup_open" onclick="getMailPhoneOfReviewedBy('+Values[7]+');techReviewEmailPhoneOverlay();" >'+Values[9]+"</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                if(Values[3]!='null' && Values[3]!=""){
                    row.append($('<td><a href="#" class="emailPhoneShow_popup_open" onclick="getMailPhoneOfReviewedBy('+Values[7]+');techReviewEmailPhoneOverlay();" >'+Values[3]+"</td>"));
                }else{
                    Values[3]="";
                    row.append($("<td>" + Values[3] + "</td>"));
                }
//                if(Values[11]!='null' && Values[11]!=""){
//                    row.append($('<td><a href="#" class="emailPhoneShow_popup_open" onclick="getMailPhoneOfReviewedBy('+Values[10]+');techReviewEmailPhoneOverlay();" >'+Values[11]+"</td>"));
//                }else{
//                    Values[11]="";
//                    row.append($("<td>" + Values[11] + "</td>"));
//                }
                if(Values[4]!='null' && Values[4]!=""){
                    row.append($('<td><a href="#" class="techReviewCommentsOverlay_popup_open" onclick="techReviewCommentsOverlay('+Values[8]+');techReviewCommentsOverlayJs();" >'+Values[4].substr(0, 20)+"..</td>"));
                }else{
                    Values[4]="";
                    row.append($("<td>" + Values[4] + "</td>"));
                }
                
                //row.append($("<td>" + Values[11] + "</td>"));
                row.append($("<td>" + Values[6] + "</td>"));
            }
        }
    }
    else{
        var row = $("<tr />")
        $("#techReviewSearchTable").append(row);
        row.append($('<td colspan="6"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}

function getMailPhoneOfReviewedBy(val)
{
    var url='../acc/getEmailPhoneDetails.action?userId='+val;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                var details=req.responseText.split("|");
                document.getElementById("email").value=details[0];
                document.getElementById("contactNo").value=details[1];

            } 
            
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function techReviewEmailPhoneOverlay(){
    var specialBox = document.getElementById("emailPhoneShowOnOverlay");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#emailPhoneShow_popup').popup(      
        );    
    return false;
}
function techReviewCommentsOverlay(val)
{
    var url='.././Requirements/techReviewCommentsOverlay.action?conTechReviewId='+val;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                document.getElementById("reviewComments").value=req.responseText;
            } 
        }
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function techReviewCommentsOverlayJs(){
    var specialBox = document.getElementById("reviewCommentsOverlay");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    }
    else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#techReviewCommentsOverlay_popup').popup(      
        );    
    return false;
}
//for viewing results
function techReviewResultsToViewOnOverlay(reviewType){
    var requirementId=$('#requirementId').val();
    var consult_id=$('#consult_id').val();
    //alert("Before "+reviewType)
    if(reviewType==undefined || reviewType==""){
        //alert(reviewType)
        reviewType=$('#reviewType').val();
    //alert(reviewType)
    }
    var forwardedToId=$('#forwardedToId').val();

    //alert("hi "+requirementId+"  "+consult_id+"  "+reviewType)
    var url='.././Requirements/getTechReviewResultOnOverlay.action?requirementId='+requirementId+'&consult_id='+consult_id+'&reviewType='+reviewType+'&forwardedToId='+forwardedToId;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            var techReviewList=req.responseText.split("^");
            for(var i=0;i<techReviewList.length-1;i++){   
                var Values=techReviewList[i].split("|");
                {  
                    
                   $("#consultId").val(consult_id);
                    $("#consultantName").val(Values[0]);
                    $("#consultantEmail").val(Values[1]);
                    $("#consultantMobile").val(Values[2]);
                    $("#interviewDate").val(Values[3]);
                    //$("#techTitle").val(Values[5]);
                    $("#ResumeId").val(Values[4]);
                    if(Values[5]=='null' || Values[5]==""){
                        Values[5]="";
                    }
                    $("#consultantJobTitle").val(Values[5]);
                    
                    if(Values[6]=='null' || Values[6]=="")
                        {
                       Values[6]="";     
                        }
                    $("#consultantSkill").val(Values[6]);         
                            
                    if(Values[7]=='null' || Values[7]==""){
                        Values[7]="";
                    }
                    $("#techSkill").val(Values[7]);
                    if(Values[8]=='null' || Values[8]==""){
                        Values[8]="";
                    }
                    $("#domainSkill").val(Values[8]);
                    if(Values[9]=='null' || Values[9]==""){
                        Values[9]="";
                    }
                    $("#comSkill").val(Values[9]);
                    if(Values[10]=='null' || Values[10]==""){
                        Values[10]="";
                    }
                    $("#consultantComments").val(Values[10]);
                    if(Values[11]=='null' || Values[11]==""){
                        Values[11]="";
                    }
                    $("#finalTechReviewStatus").val(Values[11]);
                }
            }
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}
function techReviewResultsOverlay(){
    var specialBox = document.getElementById("techReviewBoxResults");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#techReviewResults_popup').popup(      
        );    
    return false;
}
function setLocationForFaceToFace(){
    var interviewType=$('#interviewType').val();
    if(interviewType=='Face to Face'){
        $("#location").css('visibility', 'visible');
    }
    else{
        $("#location").css('visibility', 'hidden');
    }
}
function skillRateValidation()
{
    
    var skillRate=document.getElementById("techSkill").value;
    var domainSkill=document.getElementById("domainSkill").value;
    var comSkill=document.getElementById("comSkill").value;
    if(skillRate>10){
        
        var skillvalue=skillRate/10;
        //  alert(skillvalue)
        document.getElementById("techSkill").value=parseInt(skillvalue);
        
    }
    if(domainSkill>10){
         skillvalue=domainSkill/10;
        document.getElementById("domainSkill").value=parseInt(skillvalue);
    }
    if(comSkill>10){
        skillvalue=comSkill/10;
        document.getElementById("comSkill").value=parseInt(skillvalue);
    }
}
function acceptNumbers(evt){
    
    var iKeyCode = (evt.which) ? evt.which : evt.keyCode
    if (iKeyCode != 46 && iKeyCode > 31 && (iKeyCode < 48 || iKeyCode > 57))
    {
        $("#skillValid").html(" <b><font color='green'>enter only numbers</font></b>");
        return false;
    }
    else
    {
                    
        $("#skillValid").html("");
        return true;
    }
};
