
var editCalendar;
function doOnLoad() {
    editCalendar = new dhtmlXCalendarObject(["start_date","end_date"]);
    editCalendar.setSkin('omega');
    editCalendar.setDateFormat("%m-%d-%Y");
    editCalendar.hideTime();
    var task_related=document.getElementById("taskRelatedTo").value;
    if(task_related==2){
        $("#csrDiv").css('visibility', 'hidden');
    }
}
/* start, function creted by Aklakh for select task types */
function getTaskType(){
    var task_related=document.getElementById("taskRelatedTo").value;
    //alert(task_related)
    if(task_related==1){
        $("#csrDiv").css('visibility', 'visible');
        var url='../general/getTask.action?task_related_to='+task_related;
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
                setTaskTypes(req.responseText);
                getRelatedNames();
            } 
        
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    if(task_related==2){
        $("#csrDiv").css('visibility', 'hidden');
    }
    getRelatedNames();
}
function setTaskTypes(responseTypes){
    var taskType = document.getElementById("taskType");
    var flag=responseTypes.split("FLAG");
    var addTypes=flag[0].split("^");
    var $select = $('#taskType');
    $select.find('option').remove();   
    for(var i=0;i<addTypes.length-1;i++){        
        var Values=addTypes[i].split("#");
        {
            $('<option>').val(Values[0]).text(Values[1]).appendTo($select); 
        }
    }
}


function getRelatedNames(){
    var taskType=document.getElementById("taskType").value;
    var task_related_to=document.getElementById("taskRelatedTo").value;
    //alert(task_related_to)
    var url='../general/getRelatedNames.action?taskType='+taskType+'&task_related_to='+task_related_to;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            setPrimaryAssigned(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
function setPrimaryAssigned(responseTypes){
    var taskType = document.getElementById("primary_assign");
    var flag=responseTypes.split("FLAG");
    var addTypes=flag[0].split("^");
    var $select = $('#primary_assign');
    $select.find('option').remove();   
    for(var i=0;i<addTypes.length-1;i++){        
        var Values=addTypes[i].split("#");
        {
            $('<option>').val(Values[0]).text(Values[1]).appendTo($select); 
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
/* end, function creted by Aklakh for select task types */




function doDeactiveAttachment(id)
{
    var taskid=document.getElementById("taskid").value;
    var url='../general/doDeactiveAttachment.action?taskAttachId='+id;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            window.location.reload(true);           
        //location.reload();
        } 
        
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null); 
}
function doDownload(id)
{
    $("#resume").html("");
    var task_id=$("#taskid").val();
    var path=document.getElementById(id).value;
    window.location = '../general/downloadAttachment.action?attachmentId='+id+'&taskid='+task_id;
}


$(document).ready(function() {

    specialBox = document.getElementById('taskAttachOverlay');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#taskAttachments_popup').popup( );
});

//function doUpdateTaskInfo()
//{
//   
//    var taskRelatedTo=document.getElementById("taskRelatedTo").value;
//
//    var taskStatus=document.getElementById("task_status").value;
//
//    var startDate=document.getElementById("start_date").value;
//
//    var taskid=document.getElementById("taskid").value;
//
//    var taskType=document.getElementById("taskType").value;
//
//    var priority=document.getElementById("task-label").value;
//
//    var endDate=document.getElementById("end_date").value;
//
//    var taskName=document.getElementById("task-textform").value;
//
//    var task_comments=document.getElementById("task_comments").value;
//    
//    var pri_assign_to=document.getElementById("primary_assign").value;
//    
//    var sec_assign_to=document.getElementById("sec_pri_id").value;
//
//    var url='../tasks/doupdateTaskDetails.action?taskRelatedTo='+taskRelatedTo+'&taskStatus='+taskStatus+
//    '&startDate='+startDate+
//    '&taskid='+taskid+
//    '&priority='+priority+
//    '&taskType='+taskType+
//    '&endDate='+endDate+
//    '&taskName='+taskName+
//    '&task_comments='+task_comments+
//    '&pri_assign_to='+pri_assign_to+'&sec_assign_to='+sec_assign_to;
//    var req=initRequest(url);
//    req.onreadystatechange = function() {
//        if (req.readyState == 4 && req.status == 200) {
//            
//            $("UpdateTaskInfo").html(" <b><font color='green'>Task Information updated Successfully.</font></b>");
//        } 
//    };
//    req.open("GET",url,"true");
//    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//    req.send(null);
//    return false;
//}
function doUpdateTaskInfo()
{
   
    var taskRelatedTo=document.getElementById("taskRelatedTo").value;
    var taskStatus=document.getElementById("task_status").value;
    var startDate=document.getElementById("start_date").value;

    var taskid=document.getElementById("taskid").value;

    var taskType=document.getElementById("taskType").value;

    var priority=document.getElementById("taskPriority").value;

    var endDate=document.getElementById("end_date").value;

    var taskName=document.getElementById("task-textform").value;
    var task_comments=document.getElementById("task_comments").value;

    var pri_assign_to=document.getElementById("primary_assign").value;
   

    var sec_assign_to=document.getElementById("secondaryId").value;
    if(editTaskValidation()){
        var url='../tasks/doupdateTaskDetails.action?taskRelatedTo='+taskRelatedTo+'&taskStatus='+taskStatus+
        '&startDate='+startDate+
        '&taskid='+taskid+
        '&priority='+priority+
        '&taskType='+taskType+
        '&endDate='+endDate+
        '&taskName='+taskName+
        '&task_comments='+task_comments+
        '&pri_assign_to='+pri_assign_to+'&sec_assign_to='+sec_assign_to;
        //alert(url);
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
            
                $("UpdateTaskInfo").html(" <b><font color='green'>Task Information updated Successfully.</font></b>");
            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    return false;
}

function doGetNotesDetails(){
    var taskid=document.getElementById("taskid").value;
    var url='../tasks/getNotesDetails.action?taskid='+taskid;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        document.getElementById('loadingTask').style.display = 'block';
        if (req.readyState == 4 && req.status == 200) {
            $('#loadingTask').hide();
            populateNotes(req.responseText)
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);

}
function populateNotes(response){
    var eduList=response.split("^");
   
   
    var RecordsData ='<table id="taskpagenav" class="CSSTable_task responsive" border="5" cell-spacing="2"><tr><th>Notes Name</th><th>Notes Comments</th><th >Created Date</th></tr>';
    for(var i=0;i<eduList.length-1;i++){        
        var Values=eduList[i].split("|");
        {  
                                                         
            
            RecordsData = RecordsData+'<tr><td><a href="" class="notes_popup_open" onclick=" showNotesOverlayDetails('+Values[0]+','+Values[1]+');" >'+Values[2]+'</td></a>'+
            '<td>'+Values[3]+'</td>'+  
            '<td>'+Values[4]+'</td></tr>'
                              
        }
    }
    
    RecordsData=RecordsData+ ' </table> <div id="pageNavPosition" align="right" style="margin-right: 0vw;"/>';    
    task_notes_populate.innerHTML=RecordsData; 
   
    tpager.init();  
    tpager.showPageNav('tpager', 'task_pageNavPosition'); 
    tpager.showPage(1);
    
}

function showNotesOverlayDetails(id,taskid){
    var taskid=taskid;
    var id=id;
    var url='../tasks/doGetNotesDetailsOverlay.action?taskid='+taskid+'&id='+id;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            populateNotesOverlay(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);


}
function populateNotesOverlay(response){
    document.getElementById("id").value="";
    document.getElementById("taskidoverlay").value="";
    document.getElementById("noteNames").value="";
    document.getElementById("noteComments").value="";
    var Values=response.split("|");
    
    document.getElementById("id").value=Values[0];
    document.getElementById("taskidoverlay").value=Values[1];
    document.getElementById("noteNames").value=Values[2];
    document.getElementById("noteComments").value=Values[3];

}

function updateNoteDetails(){
    var id=document.getElementById("id").value;
    var taskid=document.getElementById("taskidoverlay").value;
    var note_name=document.getElementById("noteNames").value;
    var note_comments=document.getElementById("noteComments").value;
    if(editNotesValidation()){  
        var url='../tasks/doUpdateNotesDetails.action?taskid='+taskid+'&id='+id+'&note_name='+note_name+'&note_comments='+note_comments;
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
                //alert("success")
                $("UpdateNoteInfo").html(" <b><font color='green'> &nbsp&nbspNotes Information updated Successfully.</font></b>");

            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    return false;
}

function addNotesDetails(){
    var taskid=document.getElementById("taskid").value;
    var note_name=document.getElementById("noteNamesadd").value;
    var note_comments=document.getElementById("noteCommentsadd").value;
    if(addNotesValidation()){
        var url='../tasks/DoInsertNotesDetails.action?taskid='+taskid+'&note_name='+note_name+'&note_comments='+note_comments;
        var req=initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4 && req.status == 200) {
                $("InsertNoteInfo").html(" <b><font color='green'> &nbsp&nbspNotes Information Added Successfully.</font></b>");
                doGetNotesDetails()
            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
    return false;
   
}
$(document).ready(function() {

    specialBox = document.getElementById('AddNoteOverlay');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#Note_popup').popup( );
});


$(document).ready(function() {

    specialBox = document.getElementById('NotesOverlay');
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    } else {
        specialBox.style.display = "block";      
    }
    $('#notes_popup').popup( );
});




function getNotesDetailsBySearch()
{
  
    var notes_id=document.getElementById("notes_id").value;
 
    var notesName=document.getElementById("notes_NameSearch").value;
    
    if(notesName=="" || notesName==null){
        $("notesErrorMsg").html(" <b><font color='red'>notesName is Required</font></b>.");
        $("#notes_NameSearch").css("border", "1px solid red");
       
        return false;
    }
    else if(notes_id=="" || notes_id==null){
        $("notesErrorMsg").html(" <b><font color='red'>notesid is Required</font></b>.");
        $("#notes_id").css("border", "1px solid red");
        return false;
    }
    else
    {
        var url='../tasks/getNotesDetailsBySearch.action?notes_id='+notes_id+'&notesName='+notesName;
        // alert(url);
        var req=initRequest(url);
        req.onreadystatechange = function() {
            document.getElementById('loadingTask').style.display = 'block';
            if (req.readyState == 4 && req.status == 200) {
                // alert(req.responseText)
                $('#loadingTask').hide();
          
                populateNotes(req.responseText);          
            } 
        };
        req.open("GET",url,"true");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    
        return false;
    }
}

function clearResultMsg()
{
    $("notesErrorMsg").html(""); 
    $("#notes_id").css("border", "1px solid #3BB9FF");
    $("#notes_NameSearch").css("border", "1px solid #3BB9FF");
}
function removeResultMsg(){
    $("InsertNoteInfo").html("");
    $("UpdateNoteInfo").html("");
    $("#noteNames").css("border","1px solid #3BB9FF");
    $("#noteNamesadd").css("border","1px solid #3BB9FF");
    $("#noteCommentsadd").css("border","1px solid #3BB9FF");
    $("#noteComments").css("border","1px solid #3BB9FF");
}
function editTaskValidation(){
    var startDate=document.getElementById("start_date").value;
    var endDate=document.getElementById("end_date").value;
    var taskName=document.getElementById("task-textform").value;
    if(startDate==""){
        $("editTask").html("<b><font color='red'> enter start date</font></b>");
        $("#start_date").css("border","1px solid red");
        return false;
    }
    else{
        $("editTask").html("");
        $("#start_date").css("border","1px solid #3BB9FF")
    }
    if(endDate==""){
        $("editTask").html("<b><font color='red'> enter end date</font></b>");
        $("#end_date").css("border","1px solid red");
        return false;
    }
    else{
        $("editTask").html("");
        $("#end_date").css("border","1px solid #3BB9FF")
    }
    if(taskName==""){
        $("editTask").html("<b><font color='red'> enter title</font></b>");
        $("#task-textform").css("border","1px solid red");
        return false;
    }
    else{
        $("editTask").html("");
        $("#task-textform").css("border","1px solid #3BB9FF")
    }
    return true;
}
function addTaskValidation(){
    var startDate=document.getElementById("startDate").value;
    var endDate=document.getElementById("endDate").value;
    var taskName=document.getElementById("task_name").value;
    if(startDate==""){
        $("editTask").html("<b><font color='red'> enter start date</font></b>");
        $("#startDate").css("border","1px solid red");
        return false;
    }
    else{
        $("editTask").html("");
        $("#startDate").css("border","1px solid #3BB9FF")
    }
    if(endDate==""){
        $("editTask").html("<b><font color='red'> enter end date</font></b>");
        $("#endDate").css("border","1px solid red");
        return false;
    }
    else{
        $("editTask").html("");
        $("#endDate").css("border","1px solid #3BB9FF")
    }
    if(taskName==""){
        $("editTask").html("<b><font color='red'> enter title</font></b>");
        $("#task_name").css("border","1px solid red");
        return false;
    }
    else{
        $("editTask").html("");
        $("#task_name").css("border","1px solid #3BB9FF")
    }
    return true;

}
function editNotesValidation(){
    var note_name=document.getElementById("noteNames").value;
    var note_comments=document.getElementById("noteComments").value;
    if(note_name==""||note_name==null){
        $("UpdateNoteInfo").html("<b><font color='red'> enter name</font></b>");
        $("#noteNames").css("border","1px solid red");
        return false;
    }
    else{
        $("UpdateNoteInfo").html("");
        $("#noteNames").css("border","1px solid #3BB9FF");
        
    }
    if(note_comments==""||note_comments==null){
        $("UpdateNoteInfo").html("<b><font color='red'> enter comments</font></b>");
        $("#noteComments").css("border","1px solid red");
        return false;
    }
    else{
        $("UpdateNoteInfo").html("");
        $("#noteComments").css("border","1px solid #3BB9FF");
        
    }
    return true;
}
function addNotesValidation(){
    var note_name=document.getElementById("noteNamesadd").value;
    var note_comments=document.getElementById("noteCommentsadd").value;
    if(note_name==""||note_name==null){
        $("InsertNoteInfo").html("<b><font color='red'> enter name</font></b>");
        $("#noteNamesadd").css("border","1px solid red");
        return false;
    }
    else{
        $("InsertNoteInfo").html("");
        $("#noteNamesadd").css("border","1px solid #3BB9FF");
        
    }
    if(note_comments==""||note_comments==null){
        $("InsertNoteInfo").html("<b><font color='red'> enter comments</font></b>");
        $("#noteCommentsadd").css("border","1px solid red");
        return false;
    }
    else{
        $("InsertNoteInfo").html("");
        $("#noteCommentsadd").css("border","1px solid #3BB9FF");
        
    }
    return true;
}
////////////////////////////////new methods start from here 07282015//////////////////////////////////////
function taskCommentsPopup(){
    //alert(addEditFlag)
    var specialBox = document.getElementById("preskillBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
    
    
    } else {
        specialBox.style.display = "block";      
    
    
    }
    // Initialize the plugin    
    
    $('#taskComments_popup').popup(      
        ); 
}
function taskCommentsDetailsToViewOnOverlay(taskid){
    //alert("HI "+homeId)
    var url='../tasks/getCommentsOnOverlay.action?taskid='+taskid;
//    alert(url)
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            $("#commentsArea").val(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}