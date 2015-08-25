function ResponseCheckCharacters(id){
    $(id).keyup(function(){
        el = $(this);
        if(el.val().length >= 200){
            el.val( el.val().substr(0, 200) );
        } else {
            $("#ResponsecharNum").text(200-el.val().length+' Characters remaining . ');
        }
        if(el.val().length==200)
        {
            $("#ResponsecharNum").text(' Cannot enter  more than 200 Characters .');
        }

    })
    return false;
};

/*
 * Numeric Validation and Comma Appends
 */

var revenueFlag=true;
var nOEFlag=true;

//function isNumber(n) {
//  return !isNaN(parseFloat(n)) && isFinite(n);
//}
//
//function addCommas(input, id){
//  nStr = input.value;
//  if(!isNumber(nStr))
//  {
//    document.getElementById(id).innerHTML = "This is not a valid entry";
//    return;
//  }
//  document.getElementById(id).innerHTML = "";
//  document.getElementById("testRealValue").value = nStr;
//  var offset = nStr.length % 3;
//  if (offset == 0)
//    input.value = nStr.substring(0, offset)
//    + nStr.substring(offset).replace(/([0-9]{3})(?=[0-9]+)/g, "$1,");
//  else
//    input.value = nStr.substring(0, offset)
//    + nStr.substring(offset).replace(/([0-9]{3})/g,    ",$1");
//}
//
//function isDecimal(input, id)
//{
//  if(input.value % 1 != 0)
//  {
//    document.getElementById(id).innerHTML = "No.Of.Employees must be numeric";
//    return;
//  }
//  else
//  {
//    addCommas(input, id);
//  }
//}

/*
 * Account Information
 */
function validateDropDown(id, validId)
{
  var e = document.getElementById(id);
  var strUser = e.options[e.selectedIndex].value;
  var isValid = true;
  if(strUser==0)

  {
    //document.getElementById(validId).innerHTML = "Please make a selection";
    isValid = false;
  }else{
    document.getElementById(validId).innerHTML = "";

  }
  return isValid;
}

/*
 * Checking empty form fields
 */
function validateForm() {
  var hasNullField = false;
  //var notRequired = ['acc_city','address1','acc_zip','address2','fax','reqion','acc_territory','acc_budget','acc_tax_id','acc_revenue','testRealValue','acc_no_of_employees','description'];
  var notRequired = ['address1','address2','acc_city','acc_zip','acc_country','acc_state','phone1','fax','acc_industry','reqion','acc_territory','acc_no_of_employees','acc_tax_id','acc_stock_symbol','description','acc_revenue'];
  $(':input', '#acc_form').each(function() {

    if((this.value === '' || this.value === '-1' || this.value == null) && notRequired.indexOf(this.id) < 0){
      if(this.id ==='vendorType'){
        if($('#account_type').val() === '5'){
          hasNullField=true;
          return;
        }
      } else{
        hasNullField = true;
        return;
      }
    }
  });
  if(hasNullField){
    console.log('nil');
     $('#succMessage').html('');
    $('#resultMessage').html('<span>You Must Complete All required Fields *</span>');
    $('#errorMessage').html('<span>You Must Complete All required Fields *</span>');
  }
  else if(!hasNullField)
    {
        $('#resultMessage').html('');
        $('#errorMessage').html('');
    }
    if(revenueFlag==false || nOEFlag==false){
        //alert("in if false flag")
        //$('#resultMessage').html('<span>Invalid data present in the fields!</span>');
        return false;
    }
  return !hasNullField;
}

/*
 *
 * Alphanumeric Restriction
 *
 */
function alphanumeric(){
  var region=document.getElementById("acc_reqion"); //acc_reqion-->ID of textbox
  var taxID=document.getElementById("acc_tax_id"); //acc_reqion-->ID of textbox
  var alphanum=/^[0-9a-bA-B]+$/; //This contains A to Z , 0 to 9 and A to B
  if(!region.value.match(alphanum) || !taxID.value.match(alphanum)){
    console.log('Not alahpanumeric');
    return false;
  }
  return true;
}
function urlCheck(textBoxId,errorTextId){
  $(textBoxId).css('border', '');
  if($(textBoxId).val() == ''){
    $(errorTextId).html('<span>You must enter an URL</span>');
    $(textBoxId).css('border', '1px solid red');
    setTimeout(function(){
      $(textBoxId).css('border', '');
      $(errorTextId).children().remove();
    },3000);
    return;
  }
  $.ajax({
    type:'POST',
    url: 'MSB/acc/ajaxAccountURLCheck?accountURLCheck='+$(textBoxId).val(),
    dataType:'text',
    success:function(data,stat,xhr){
      console.log('RESPONSE SAYS '+data+" " + xhr.getResponseHeader('urlexists'));
      if(xhr.getResponseHeader('urlexists')==='free'  && $(textBoxId).val() != ''){
        $(textBoxId).css('border', '1px solid green')
        $(errorTextId).children().remove();

      }else{
        $(errorTextId).children().remove();
        if($(textBoxId).val() != ''){
          $(errorTextId).html('<span>This Url already exists</span>');
        } else {
          $(errorTextId).html('<span>You must enter an URL</span>');
        }

        $(textBoxId).css('border', '1px solid red');
        $(textBoxId).val('');
        setTimeout(function(){
          $(textBoxId).css('border', '');
          $(errorTextId).children().remove();
        },3000);

      }
    },
    error: function(data,stat,xhr){
      $(textBoxId).css('border', '1px solid red');

    }

  })
}

function nameCheck(textBoxId,errorTextId){
  if($(textBoxId).val() == ''){
    $(errorTextId).html('<span>You must enter an name</span>');
    $(textBoxId).css('border', '1px solid red');
    setTimeout(function(){
      $(textBoxId).css('border', '');
      $(errorTextId).children().remove();
    },3000);
    return;
  }
  $.ajax({
    type:'POST',
    url: 'MSB/acc/ajaxAccountNameCheck?accountNameCheck='+$(textBoxId).val(),
    dataType:'text',
    success:function(data,stat,xhr){
      console.log('RESPONSE SAYS '+data+" " + xhr.getResponseHeader('exists'));
      if(xhr.getResponseHeader('exists')==='free' && $(textBoxId).val() != ''){
        $(textBoxId).css('border', '1px solid green')
        $(errorTextId).children().remove();
      }else{
        $(errorTextId).children().remove();
        if($(textBoxId).val() != ''){
          $(errorTextId).html('<span>This name already exists</span>');
        } else {
          $(errorTextId).html('<span>You must enter an name</span>');
        }
        $(textBoxId).css('border', '1px solid red');
        $(textBoxId).val('');
        setTimeout(function(){
          $(textBoxId).css('border', '');
          $(errorTextId).children().remove();
        },3000);

      }
    },
    error: function(data,stat,xhr){
      //console.log('RESPONSE SAYS '+data+" " + xhr.getResponseHeader('urlexists'));
      $(textBoxId).css('border', '1px solid red');

    }
  })

};

//var vendorHidden = true;
//
//function AccountTypeDropDown(){
//  if($('#account_type').val() === '5' && vendorHidden){
//
//    $('#vendorType').show();
//    vendorHidden = false;
//  }else if($('#account_type').val() === ""){
//    if(!vendorHidden){
//      $('#vendorType').hide();
//      vendorHidden = true;
//    }
//  }else{
//    if(!vendorHidden){
//      $('#vendorType').hide();
//      vendorHidden = true;
//    }
//  }
//};

function getValidExtention(){
  var mailExtention=document.getElementById("email_ext").value;
  if(mailExtention==""){
      return false;
  }else{
      //alert(mailExtention)
  }
  var emailExp = /^[a-zA-Z]+[a-z]+[.]+[a-zA-z]{2,4}$/;
  if(mailExtention.match(emailExp))
      {
          //alert("fine")
      }
      else{
          alert("Please enter valid email extension!");
          mailExtention.focus();
          return false;
      }
}

 function revenueValidate(){

    var rev=document.getElementById('acc_revenue').value;
    Exp=/^[0-9]*$/;
    if(!Exp.test(rev))
    {
        $("#acc_revenue").css("border", "1px solid red");
        $('#revenueValidation').css("color","red");
        $("#revenueValidation").html("Must be valid Revenue");
        revenueFlag=false;
        return false;
    }
    else(revenueFlag=true)
    {
        //revenueFlag=true;
        $("#revenueValidation").html("");
        $("#acc_revenue").css("border", "1px solid green");
        return true;
    }
};

function noeValidate(){
  
    var emp=document.getElementById('acc_no_of_employees').value;
    Exp=/^[0-9]*$/;
    if(!Exp.test(emp))
    {
        $("#acc_no_of_employees").css("border", "1px solid red");
        $('#employeeValidation').css("color","red");
        $("#employeeValidation").html("Must be valid employee number");
        nOEFlag=false;
        return false;
    }
    else(nOEFlag=true)
    {
        //nOEFlag=true;
        $("#employeeValidation").html("");
        $("#acc_no_of_employees").css("border", "1px solid green");
        return true;
    }
};


function clearform() {
//    alert("hi clearform");
    $('#resultMessage').html(" ");
    $('#errorMessage').html(" ");
    $("#revenueValidation").html("");
    $("#employeeValidation").html("");
    $("#acc_revenue").css("border", "1px solid green");
    $("#acc_no_of_employees").css("border", "1px solid green");
    $('#succMessage').html(" ");
   // document.getElementById('acc_form').value="";
    document.getElementById('account_name').value="";
    document.getElementById('account_url').value="";
    document.getElementById('account_type').value="";
    document.getElementById('email_ext').value="";
    document.getElementById('address1').value="";
    document.getElementById('address2').value="";
    document.getElementById('acc_city').value="";
    document.getElementById('acc_zip').value="";
    document.getElementById('acc_country').value="";
    document.getElementById('acc_state').value="";
    document.getElementById('phone1').value="";
    document.getElementById('fax').value="";
    document.getElementById('acc_industry').value="";
    document.getElementById('reqion').value="";
    document.getElementById('acc_territory').value="";
    document.getElementById('acc_no_of_employees').value="";
    document.getElementById('acc_tax_id').value="";
    document.getElementById('acc_stock_symbol').value="";
    document.getElementById('acc_revenue').value="";
    document.getElementById('description').value="";
        
}
