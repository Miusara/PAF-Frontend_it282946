<%@page import="model.Payments" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>

	<div class="container">
		<h1>Payment Management</h1>
	
		<form id="formNotice" name="formNotice" method="post" action="Payments.jsp">
			Payment ID:<input id="payID" name="payID" type="text" class="form-control"><br>
			Customer ID:<input id="UserID" name="UserID" type="text" class="form-control"><br>
			Payment Method:<input id="method" name="method" type="text" class="form-control"><br>
			Date:<input id="date" name="date" type="text" class="form-control"><br>
			Amount:<input id="amount" name="amount" type="text" class="form-control"><br>
			
			<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
			<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
		</form>
	
	<br>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	
	<br>
	
	<div id="divItemsGrid">
	<%
	Payments noteObj = new Payments();
 		out.print(noteObj.readPay());
	%>
	</div>
	
	</div>
</body>
</html>