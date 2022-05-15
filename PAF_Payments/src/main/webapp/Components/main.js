$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
 	{
 	$("#alertSuccess").hide();
 	}
 	$("#alertError").hide();
});

//SAVE
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();
 	
 	// Form validation-------------------
	var status = validateItemForm();
	if (status != true)
	{
	$("#alertError").text(status);
	$("#alertError").show();
	return;
	}
	
	// If valid-------------------------
 	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
 	{
 	url : "PaymentsAPI",
 	type : type,
 	data : $("#formNotice").serialize(),
 	dataType : "text",
 	complete : function(response, status)
 	{
 		onItemSaveComplete(response.responseText, status);
 	}
 	});
 
});

function onItemSaveComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		
 		if (resultSet.status.trim() == "success")
 		{
 			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divItemsGrid").html(resultSet.data);
 		} else if (resultSet.status.trim() == "error")
 		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
 		}
 	} else if (status == "error")
 	{
 		$("#alertError").text("Error while saving.");
 		$("#alertError").show();
 	} else
 	{
 		$("#alertError").text("Unknown error while saving..");
 		$("#alertError").show();
 	} 
 	$("#hidItemIDSave").val("");
 	$("#formNotice")[0].reset();
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	console.log("Update",$("#hidItemIDSave").val());
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	 $("#hidItemIDSave").val($(this).data("noticeid"));
	 //$("#noticeId").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#payID").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#UserID").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#method").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#date").val($(this).closest("tr").find('td:eq(3)').text());
	 $("#amount").val($(this).closest("tr").find('td:eq(4)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
 	$.ajax(
 	{
 		url : "PaymentsAPI",
 		type : "DELETE",
 		data : "noticeId=" + $(this).data("noticeid"),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onItemDeleteComplete(response.responseText, status);
 		}
 	});
});

function onItemDeleteComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		if (resultSet.status.trim() == "success")
 		{
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 
			 $("#divItemsGrid").html(resultSet.data);
 		} else if (resultSet.status.trim() == "error")
 		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
 		}
 		} else if (status == "error")
 		{
			 $("#alertError").text("Error while deleting.");
			 $("#alertError").show();
 		} else
		{
			 $("#alertError").text("Unknown error while deleting..");
			 $("#alertError").show();
 		}
}

// CLIENT-MODEL================================================================
function validateItemForm()
{
	// payID
	let payID = $("#payID").val().trim();
	if (!$.isNumeric(payID)) {
		return "Insert a numerical value for payID.";
	}
	 
	// UserID
	if ($("#UserID").val().trim() == "")
	 {
	 return "Insert UserID.";
	 } 
	 
	// method
	if ($("#method").val().trim() == "")
	 {
	 return "Insert Time.";
	 }
	 
	 
	 // date
	if ($("#date").val().trim() == "")
	 {
	 return "Insert date.";
	 }
	 
	// amount
	let amount = $("#amount").val().trim();
	if (!$.isNumeric(amount)) {
		return "Insert a numerical value for amount.";
	}
	 
	return true;
}

