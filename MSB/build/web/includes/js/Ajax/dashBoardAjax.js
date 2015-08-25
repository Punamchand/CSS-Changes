google.load('visualization', '1.1', {
    'packages': ['corechart']
});
google.setOnLoadCallback(getCustomerRequirementsDashBoard);

function getCustomerRequirementsDashBoard(){
    $("#reqCustomerYearChart").css('visibility', 'visible');
    var dashYears=$('#dashYears').val();
    var csrCustomers=$('#csrCustomers').val();
    //alert("HI "+csrCustomers+"  "+dashYears)
    
    var url='../dashboard/getRequirementDashBoardDetails.action?dashYears='+dashYears+'&csrCustomer='+csrCustomers;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            populateDashBoardTableForCsrRequirements(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}
function populateDashBoardTableForCsrRequirements(response){
    
    //alert(response)
    var dashBoardReq=response.split("^");
    var table = document.getElementById("dashBoardTable");
    
    var cust = new Array();
    var open=new Array();
    var release =new Array();
    var close=new Array();
    
    var total=0;
    
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
                $("#dashBoardTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                row.append($("<td>" + Values[4] + "</td>"));
                row.append($("<td>" + Values[1] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($('<td><a href="#" class="csrCustomerReq_popup_open" onclick="csrCustReqDetails('+Values[5]+');csrCustReqOverlay();">'+ Values[0] +"</td>"));
                //total= parseInt(total)+ parseInt(Values[0]);
                
                
                cust.push(Values[4]);
                open.push(parseInt(Values[1]));
                release.push(parseInt(Values[2]));
                close.push(parseInt(Values[3]));
            }
        }
        showChart(cust,open,release,close);
    }
    else{
        $("#reqCustomerYearChart").css('visibility', 'hidden');
        var row = $("<tr />")
        $("#dashBoardTable").append(row);
        row.append($('<td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}
function csrCustReqOverlay(){
    var specialBox = document.getElementById("recruiterBox");
    if(specialBox.style.display == "block"){       
        specialBox.style.display = "none";         
        

    } else {
        specialBox.style.display = "block";      
        

    }
    // Initialize the plugin    

    $('#csrCustomerReq_popup').popup(      
        );    
    return false;
}
function csrCustReqDetails(accountId)
{
    var dashYears=$("#dashYears").val();
    //alert("HI  "+accountId+" "+dashYears)
    var url='../dashboard/getRequirementDashBoardDetailsOnOverlay.action?dashYears='+dashYears+'&accountId='+accountId;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            populateDashBoardTableForCsrRequirementsOnOverlay(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}
function populateDashBoardTableForCsrRequirementsOnOverlay(response){
    //alert(response)
    var dashBoardReq=response.split("^");
    var table = document.getElementById("dashBoardTableOnOverlay");
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
                $("#dashBoardTableOnOverlay").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                row.append($("<td>" + Values[0] + "</td>"));
                row.append($("<td>" + Values[1] + "</td>"));
                total= parseInt(total)+ parseInt(Values[1]);
            }
        }
        var row = $("<tr />")
        $("#dashBoardTableOnOverlay").append(row);
        row.append($('<td><font style= "color:red">Total</font></td>'));
        row.append($("<td><font style= 'color:red'>" + total + "</font></td>"));
    }
}

// aklakh javascript start
function dashboardMessage(message)
{
    //  alert(message.id);
    if(message.id=="customerBoard"){
        document.getElementById("headingmessage").innerHTML="Customer Dashboard";
    }
    if(message.id=="vendorBoard"){
        document.getElementById("headingmessage").innerHTML="Vendor Dashboard";
    }
}


function getCustomerDashboardList(){
    $("#individualCustomerYearChart").css('visibility', 'visible');
    var dashYears=$('#year').val();
    var dashMonths=$('#month').val();
    //alert("HI "+csrCustomers+"  "+dashYears)
    
    var url='../dashboard/getCustomerRequirementDashBoardDetails.action?dashYears='+dashYears+'&dashMonths='+dashMonths;
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            //alert(req.responseText)
            populateCustomerDashBoardTable(req.responseText);
        } 
    };
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
    return false;
}

function populateCustomerDashBoardTable(response){
    //alert(response.length)
    var dashBoardReq=response.split("^");
    var table = document.getElementById("customerDashboardResults");
    var month = new Array();
    var open=new Array();
    var release =new Array();
    var close=new Array();
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }
    if(response.length!=0){

        var total=0;
        for(var i=0;i<dashBoardReq.length-1;i++){   
            //alert(techReviewList[0])
            var Values=dashBoardReq[i].split("|");
            {  
                //alert(Values[0])
                var row = $("<tr />")
                $("#customerDashboardResults").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
                row.append($("<td>" + Values[0] + "</td>"));
                row.append($("<td>" + Values[1] + "</td>"));
                row.append($("<td>" + Values[2] + "</td>"));
                row.append($("<td>" + Values[3] + "</td>"));
                row.append($("<td>" + Values[4] + "</td>"));
                
                month.push(Values[0]);
                open.push(parseInt(Values[1]));
                release.push(parseInt(Values[2]));
                close.push(parseInt(Values[3]));
            }
        }
        showCustomerChart(month,open,release,close);
        
    }
    else{
        $("#individualCustomerYearChart").css('visibility', 'hidden');
        var row = $("<tr />")
        $("#customerDashboardResults").append(row);
        row.append($('<td colspan="5"><font style="color: red;font-size: 15px;">No Records to display</font></td>'))
    }
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
}

function showChart(cust,open,release,close)
{
    //alert(month.length);
     
    var Combined = new Array();
    Combined[0] = ['Customer', 'open', 'release','close'];
    for (var i = 0; i < cust.length; i++){
        Combined[i + 1] = [ cust[i], open[i], release[i],close[i] ];
    }
    //second parameter is false because first row is headers, not data.
    var data = google.visualization.arrayToDataTable(Combined, false);
    
    var options = {
        //        width: 370,
        //        height:300,
        title: 'Customer Requirements Yearly Analysis',
        colors: ['#0000FF', '#00FF00', '#FF0000']
    // animation: {
    //duration: 1000,
    //easing: 'linear'
    //vAxis: {
    //viewWindow: {
    //max: 8
    }
    var chart = new google.visualization.ColumnChart(document.getElementById('reqCustomerYearChart'));
    drawChart();
    function drawChart() {
        // Instantiate and draw our chart, passing in some options.
        chart.draw(data, options);
    }
    $(window).resize(function () {
        drawChart();
    });
}


function showCustomerChart(month,open,release,close)
{
    //alert(month.length);
     
    var Combined = new Array();
    Combined[0] = ['Month', 'open', 'release','close'];
    for (var i = 0; i < month.length; i++){
        Combined[i + 1] = [ month[i], open[i], release[i],close[i] ];
    }
    //second parameter is false because first row is headers, not data.
    var data = google.visualization.arrayToDataTable(Combined, false);
    
    var options = {
        //        width: 370,
        //        height:300,
        title: 'Customer Requirements Yearly Analysis',
        colors: ['#0000FF', '#00FF00', '#FF0000']
    // animation: {
    //duration: 1000,
    //easing: 'linear'
    //vAxis: {
    //viewWindow: {
    //max: 8
    }
    var chart = new google.visualization.ColumnChart(document.getElementById('individualCustomerYearChart'));
    drawChart();
    function drawChart() {
        // Instantiate and draw our chart, passing in some options.
        chart.draw(data, options);
    }
    $(window).resize(function () {
        drawChart();
    });
}