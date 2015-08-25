function getProjectBudgetSearch(){
    var year=$('#budgetYear').val();
    var project=$('#project').val();
    var quarterId=$('#quarterId').val();
    var status=$('#status').val();

    //alert("HI "+csrCustomers+"  "+dashYears)
    
    var url='../budgets/getProjectBudgetSearchResults.action?year='+year+'&project='+project+'&quarterId='+quarterId+'&status='+status;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            populateProjectBudgetTable(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}


function populateProjectBudgetTable(response){
    
    //alert(response)
    var dashBoardReq=response.split("^");
    var table = document.getElementById("projectBudgetTable");
    var roleValue=$('#roleValue').val();
    
    //alert(roleValue)
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length>0){

        
        for(var i=0;i<dashBoardReq.length-1;i++){   
            //alert(techReviewList[0])
            var Values=dashBoardReq[i].split("|");
            {  
                var row = $("<tr />")
                $("#projectBudgetTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                
                
                if(roleValue!='Director'){
                    if(Values[6]=='Submited' || Values[6]=='Approved'){
                        row.append($("<td>" + Values[1] + "</td>"));
                    }else{
                        row.append($('<td><a href="#" class="projectBudget_popup_open" onclick="projectBudgetDetailsToViewOnOverlay('+Values[8]+');projectBudgetOverlay(Edit);">'+ Values[1] +"</td>"));
                    }
                }
                if(roleValue=='Director'){
                    if(Values[6]=='Submited' || Values[6]=='Approved'){
                        row.append($('<td><a href="#" class="projectBudget_popup_open" onclick="projectBudgetDetailsToViewOnOverlay('+Values[8]+');projectBudgetOverlay(Edit);">'+ Values[1] +"</td>"));
                    }else{
                        row.append($("<td>" + Values[1] + "</td>"));
                    }
                }
                row.append($("<td>" + Values[5] + "</td>"));
                row.append($("<td>" + Values[4] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($("<td>" + Values[6] + "</td>"));
                if(Values[7]!='null' && Values[7]!=""){
                    row.append($("<td>" + Values[7] + "</td>"));
                }else{
                    Values[7]="";
                    row.append($("<td>" + Values[7] + "</td>"));
                }
                if(roleValue!='Director'){
                    if(Values[6]=='Submited' || Values[6]=='Approved'){
                        row.append($('<td><img style="opacity: 0.4;" src="../includes/images/deleteImage.png" height="20" width="25"></td>'));
                    }else{
                        row.append($('<td><a href="#" onclick="doBudgetRecordDelete('+Values[8]+');"><img src="../includes/images/deleteImage.png" height="20" width="25"></td>'));
                    }
                }
            }
        }
    }
    else{
        var row = $("<tr />")
        $("#projectBudgetTable").append(row);
        row.append($('<td colspan="8"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}


function projectBudgetOverlay(addEditFlag){
    //alert(addEditFlag)
    $("#oproject").val("");
    $("#oyear").val("");
    $("#oquarterId").val("");
    $("#oestimateBudget").val("");
    $("#ocomments").val("");
    $("e").html("");
    $("d").html("");
    
    $("#oLaybuttons").css('visibility', 'visible');
    $("#addEditFlag").val(addEditFlag);
    var specialBox = document.getElementById("projectBudgetBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#projectBudget_popup').popup(      
        );    
    //getProjectBudgetSearch();
    return false;
}


function closeProjectBudgetOverlay(){
    window.location = "ProjectBudgetDetails.action";
}


function saveBudgetDetails(formFlag){
    //alert(formFlag)
    var flg=true;
    var project=$('#oproject').val();
    var year=$('#oyear').val();
    var quarterId=$('#oquarterId').val();
    var estimateBudget=$('#oestimateBudget').val();
    var comments=$('#ocomments').val();
    var flag=formFlag;
    var addEditFlag=$('#addEditFlag').val();
    // alert(flag+" "+project+" "+year+" "+quarterId+" "+estimateBudget+" "+comments)
    re=/^[0-9]*$/;
    // alert(flag+" "+project+" "+year+" "+quarterId+" "+estimateBudget+" "+comments)
     //if($("#oyear").val()=="" && !re.test(oyear))
      if(year==""){
         $("#oyear").css("border", "1px solid red");
        $("e").html(" <b><font color='red'>Year is Mandatory</font></b>");
        
        flg=false;
        return false;
    }
    else if(!re.test(year)){
       
       $("#oyear").css("border", "1px solid red");
        $("e").html(" <b><font color='red'>please enter numeric values</font></b>");
                flg=false;
        return false;
    }
    else{
       
       $("e").html("");
        $("#oyear").css("border", "1px solid green");
    }
     if(estimateBudget==""){
         $("#oestimateBudget").css("border", "1px solid red");
        $("e").html(" <b><font color='red'>Estimated Budget is Mandatory</font></b>");
        flg=false;
        return false;
    }
    else if(!re.test(estimateBudget)){
        $("#oestimateBudget").css("border", "1px solid red");
        $("e").html(" <b><font color='red'>please enter numeric values</font></b>");
        flg=false;
        return false;
    }
    else if(!re.test(estimateBudget)){
        $("e").html("");
        $("#oestimateBudget").css("border", "1px solid green");
    }

    if(flg){
        
    
    var url='../budgets/saveProjectBudgetDetails.action?year='+year+'&project='+project+'&quarterId='+quarterId+'&estimateBudget='+estimateBudget+'&comments='+comments+'&flag='+flag+'&addEditFlag='+addEditFlag;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            if(req.responseText=='Added'){
                $("e").html(" <b><font color='Green'>Budget Details Added For Project Selected</font></b>");
            //getProjectBudgetSearch();
            // window.location = "ProjectBudgetDetails.action";
            }else if(req.responseText=='Exist'){
                $("e").html(" <b><font color='red'>Budget Details Already Exists!</font></b>");
            }else if(req.responseText=='Updated'){
                $("e").html(" <b><font color='green'>Budget Details Updated!</font></b>");
            //getProjectBudgetSearch();
            //window.location = "ProjectBudgetDetails.action";
            }else if(req.responseText=='DirectorStatusUpdated'){
                $("e").html(" <b><font color='green'>Budget Details Saved!</font></b>");
            //getProjectBudgetSearch();
            //window.location = "ProjectBudgetDetails.action";
            }else if(req.responseText=='DirectorStatusFail'){
                $("e").html(" <b><font color='green'>Budget Details Failed to Approve!</font></b>");
            //getProjectBudgetSearch();
            //window.location = "ProjectBudgetDetails.action";
            }else{
                $("e").html(" <b><font color='red'>Error Check Details</font></b>");
            }
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    }
    return false;

}
function projectBudgetDetailsToViewOnOverlay(id)
{
    var url='../budgets/getProjectBudgetDetailsToViewOnOverlay.action?budgetId='+id;
    //alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            setOverlayValues(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}
function setOverlayValues(response){
    var dashBoardReq=response.split("^");
    if(response.length>0){
        for(var i=0;i<dashBoardReq.length-1;i++){   
            var Values=dashBoardReq[i].split("|");
            {  
                if(Values[6]=='Approved'){
                    $("#oLaybuttons").css('visibility', 'hidden');
                }
                $("#oproject").val(Values[1]);
                $("#oyear").val(Values[5]);
                $("#oquarterId").val(Values[4]);
                $("#oestimateBudget").val(Values[2]);
                if(Values[7]!='null' && Values[7]!=""){
                    $("#ocomments").val(Values[7]);
                }else{
                    Values[7]='';
                    $("#ocomments").val(Values[7]);
                }
                
            }
        }
    }
//projectBudgetOverlay('Edit');
}



function doBudgetRecordDelete(id){
    swal({
    
        title: "Do you want delete?",
  
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
            var url='../budgets/doBudgetRecordDelete.action?budgetId='+id;
            //alert(url)
            var req=initRequest(url);
            req.onreadystatechange = function() {
                if (req.readyState == 4 && req.status == 200) {
                    //alert(req.responseText)
                    if(req.responseText=='Success'){
                        //$("d").html(" <b><font color='green'>Record Deleted!</font></b>");
                        getProjectBudgetSearch();
                        swal("Deleted!", "Deleted Successfully....", "success");
                    }else{
                        //$("d").html(" <b><font color='red'>Failed to Delete!</font></b>");
                        swal("Sorry!", "Delete Failed....", "Error");
                    }
                } 
            };
            req.open("GET",url,"true");
            req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            req.send(null);
            return false;
   
   
    
        } else {
     
            swal("Cancelled", "Deletion cancelled ", "error");
 
      
        }
    });
}


//function doBudgetRecordDelete(id){
//    //alert("deleted "+id)
//    var url='../budgets/doBudgetRecordDelete.action?budgetId='+id;
//    //alert(url)
//    var req=initRequest(url);
//    req.onreadystatechange = function() {
//        if (req.readyState == 4 && req.status == 200) {
//            alert(req.responseText)
//            if(req.responseText=='Success'){
//                $("d").html(" <b><font color='green'>Record Deleted!</font></b>");
//                getProjectBudgetSearch();
//            }else{
//                $("d").html(" <b><font color='red'>Failed to Delete!</font></b>");
//            }
//        } 
//    };
//    req.open("GET",url,"true");
//    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//    req.send(null);
//    return false;
//}

 function checkCharactersComment(id){
    $(id).keyup(function(){
        el = $(this);
        if(el.val().length >= 250){
            el.val( el.val().substr(0, 250) );
        } else {
            $("#description_feedback").text(250-el.val().length+' Characters remaining . ');
        }
        if(el.val().length==250)
        {
            $("#description_feedback").text(' Cannot enter  more than 250 Characters .');    
        }
        
    })
    return false;
}

