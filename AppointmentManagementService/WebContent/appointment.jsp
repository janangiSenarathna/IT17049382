<%@ page import="model.Doc" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">

</head>
<body>
<div class="container"></div>
<div class="row"></div>
<div class="col-6"></div>
     <h1>Appointment Management</h1>
     
     <form id="formAppointment" name="formAppointment">
           Doctor Id:
           <input id="docId" name="docId" type="text" class="form-control form-control-sm" >
           <br>Patient Name:
           <input id="pName" name="pName" type="text" class="form-control form-control-sm" >
           <br>Appointment Date:
           <input id="aDate" name="aDate" type="text" class="form-control form-control-sm" >
           <br>Hospital:
           <input id="aPlace" name="aPlace" type="text" class="form-control form-control-sm" >
           <br>
           <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
           
           <input type="hidden" id="hidAppointIdSave" name="hidAppointIdSave" value=""> 
     </form>
     
     <br>
     <div id="divAppointGrid">
        <%
           Doc docObj = new Doc();
           out.print(docObj.readDoc());
        %>
     </div>
</body>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Doc.js"></script>
</html>