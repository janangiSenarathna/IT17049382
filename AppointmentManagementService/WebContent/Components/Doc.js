$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
//	$('#btnUpdate').hide();
});

// SAVE==============================================
$("#btnSave").on("click",  function(event)
{
	//Clear alerts--------------------------------
	
	
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	//Form Validation---------------------------
//	var status = validateformAppointment();
//	alert(status);
//	if(status != true)
//		{
//		  $("#alertError").text(status);
//		  $("#alertError").show();
//		  return;
//		}
	
	if(document.getElementById('docId').value !=='' && document.getElementById('pName').value !== '' && document.getElementById('aDate').value !== '' && document.getElementById('aPlace').value!== ''){
	
	//If valid-----------------------------------
	//$("#formAppointment").submit();
			var type = ($("#hidAppointIdSave").val() == "") ? "POST" : "PUT";
			
			$.ajax(
			{
				url : "DocAPI",
				type : type,
				data : $("#formAppointment").serialize(),
				dataType : "text",
				complete : function(response, status)
				{
					onAppointmentSaveComplete(response.responseText, status);
				}
			});
		}else{
			
			var status = validateformAppointment();

			alert(status)
		}
			
});

function onAppointmentSaveComplete(response,status)
{
	if(status == "success")
		{
		  var resultSet = JSON.parse(response);
		  
		  if (resultSet.status.trim() == "success")
			  {
			     $("#alertSuccess").text("Successfully Saved");
			     $("#alertSuccess").show();
			     
			     $("#divAppointGrid").html(resultSet.data);
			  }
		  else if (resultSet.status.trim() == "error")
			  {
			     $("#alertError").text(resultSet.data);
			     $("#alertError").show();
			  }
		  else if (status == "error")
			  {
			     $("#alertError").text("Error while saving");
			     $("#alertError").show();
			  }
		  else
			  {
			     $("#alertError").text("Unknown error while saving");
			     $("#alertError").show();
			  }
		  $("#hidAppointIdSave").val("");
		  $("#formAppointment")[0].reset();
		}
}

//UPDATE==================================================================================================
$(document).on("click", ".btnUpdate", function(event)
		{
	var appID = $(this).data('appointid');
	
	
		   document.getElementById('hidAppointIdSave').value = appID;
	       document.getElementById('docId').value = $(this).closest("tr").find('td:eq(0)').text();
	       document.getElementById('pName').value =$(this).closest("tr").find('td:eq(1)').text();
	       document.getElementById('aDate').value =$(this).closest("tr").find('td:eq(2)').text();
	       document.getElementById('aPlace').value =$(this).closest("tr").find('td:eq(3)').text();
	       
	       
		
		});



//REMOVE=====================================================================================================
$(document).on("click", ".btnRemove", function(event)
{
   $.ajax(
   {
	   url : "DocAPI",
	   type : "DELETE",
	   data : "appointmentId=" + $(this).data("appointid"),
	   dataType : "text",
	   complete : function(response, status)
	   {
		   onItemDeleteComplete(response.responseText, status);
	   }
			   
		   
   });	
});

function onItemDeleteComplete(response, status)
{
	if(status == "success")
		{
		  var resultSet =JSON.parse(response);
		  
		  if(resultSet.status.trim() == "success")
			  {
			    $("#alertSuccess").text("Successfully deleted");
			    $("#alertSuccess").show();
			    
			    $("#divAppointGrid").html(resultSet.data);
			  }
		  else if(resultSet.status.trim() == "error")
			  {
			    $("#alertError").text(resultSet.data);
			    $("#alertError").show();
			  }
		  else if(status == "error")
			  {
			    $("#alertError").text("Error while deleting");
			    $("#alertError").show();
			  }
		  else
			  {
			    $("#alertError").text("Unknown error while deleting");
			    $("#alertError").show();
			  }
		}
}


//Client Model==============================================================================================
function validateformAppointment()
{
	
	var docId = document.getElementById('docId').value;
	if(docId === '')
	{
		return "Insert Doctor ID";
	}else if(document.getElementById('pName').value === "")
	{
	   return "Insert Patient's Name";
	}else if(document.getElementById('aDate').value === "")
	{
	   return "Insert Appointment Date";
	}else if(document.getElementById('aPlace').value === "")
	{
	   return "Insert Hospital Name";
	}else{
		return true;
	}
	

}


