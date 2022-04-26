<%@page import="model.Notices" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
if (request.getParameter("phone") != null)
{
	Notices noteObj = new Notices();
	String stsMsg = noteObj.insertNotes(request.getParameter("phone"),
			 request.getParameter("address"),
			 request.getParameter("note"),
			 request.getParameter("zipcode"));
	session.setAttribute("statusMsg", stsMsg); 
}

if (request.getParameter("noticeId") != null)
{
Notices noteObj = new Notices();
String stsMsg = noteObj.deleteNotes(request.getParameter("noticeId"));
session.setAttribute("statusMsg", stsMsg);
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">
<title>Service Management</title>
</head>
<body>

	<div class="container">
	<h1>Service Management</h1>
	<form method="post" action="Notices.jsp">
	Phone Number:<input name="phone" type="text" class="form-control"><br>
	District :<input name="address" type="text" class="form-control"><br>
	Time :<input name="note" type="text" class="form-control"><br>
	Zip Code:<input name="zipcode" type="text" class="form-control"><br>
	<input name="btnSubmit" type="submit" value="Save" class="btn btn-primary">
	</form>
	<div class="alert alert-success">
	<% out.print(session.getAttribute("statusMsg")); %>
	<br>
	</div>
	<%
 Notices noteObj = new Notices();
 out.print(noteObj.readNotes());
%>
	</div>
</body>
</html>