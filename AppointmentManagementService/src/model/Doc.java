package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Doc {
  private Connection connect()
  {
	  Connection con = null;
	  try
	  {
		  Class.forName("com.mysql.jdbc.Driver");
		  con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare","root","");
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }
	  return con;
  }
  
  public String readDoc()
  {
	  String output = "";
	  
	  try
	  {
		  Connection con = connect();
		  
		  if(con == null)
		  {
			  return "Error while connecting to database for reading";
		  }
		  
		  //prepare html table to be displayed
		  output = "<table border='1'><tr><th>Doctor ID</th><th>Patient Name</th><th>Date</th>" + "<th>Hospital</th><th>Update</th><th>Remove</th></tr>";
		  String query = "select * from appointment";
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery(query);
		  
		  //iterate through the rows in the result set
		  while (rs.next())
		  {
			    String appointmentId = Integer.toString(rs.getInt("appointmentId"));
				String docId = rs.getString("docId");
				String pName = rs.getString("pName");
				java.sql.Date aDate = rs.getDate("aDate");
				String aPlace = rs.getString("aPlace");
				
				//add into the html table
				output +="<tr><td><input id='hidAppointIDUpdate' name='hidAppointIDUpdate' type='hidden' value='" + appointmentId + "'>" + docId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + aDate + "</td>";
				output += "<td>" + aPlace + "</td>";
				
				//buttons
				output +="<td><input name ='btnUpdate' type ='button' value='Update' class='btnUpdate btn btn-secondary' id='update' data-appointid='" + appointmentId + "'></td><td><input name='btnRemove' type = 'button' value='Remove' class='btnRemove btn btn-danger' data-appointid='" + appointmentId + "'>" + "</td></tr>";
				
		  }
		  
		  con.close();
		  //complete the html table
		  output += "</table>";
	  }
	  catch (Exception e)
	  {
		  output = "Error while reading the appointments";
		  System.err.println(e.getMessage());
	  }
	  
	  return output;
  }
  
  public String insertDoc(String code, String name, String date, String place)
  {
	  String output ="";
	  
	  try
	  {
		  Connection con = connect();
		  
		  if(con == null)
		  {
			  return "Error while connecting to the database for inserting";
		  }
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			date = df.format(d);
			java.sql.Date aDate1 = new java.sql.Date(d.getTime());
		  
		  //create a prepared statement
			String query = "INSERT INTO appointment(appointmentId,docId,pName,aDate,aPlace)" +" Values(?,?,?,?,?);";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDate(4, aDate1);
			preparedStmt.setString(5, place);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newDoc = readDoc();
			output = "{\"status\":\"success\",\"data\": \"" + newDoc + "\"}";
		  
	  }
	  catch (Exception e)
	  {
		  output = "{\"status\":\"error\", \"data\":\"Error while inserting the appointment\"}";
		  System.err.println(e.getMessage());
	  }
	  
	  return output;
  }
  
  public String updateDoc(String appointmentId, String docId, String pName, String aDate, String aPlace )
  {
	  String output = "";
	  try
	  {
		  Connection con = connect();
		  
		  if(con == null)
		  {
			  return "Error while connecting to database while updating";
		  }
		  
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			aDate = df.format(d);
			java.sql.Date aDate1 = new java.sql.Date(d.getTime());
			
		    //create prepared statement
			String query = "UPDATE appointment SET docId=?,pName=?,aDate=?,aPlace=? WHERE appointmentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setString(1, docId);
			preparedStmt.setString(2, pName);
			preparedStmt.setDate(3, aDate1);
			preparedStmt.setString(4, aPlace);
			preparedStmt.setInt(5, Integer.parseInt(appointmentId));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newDoc = readDoc();
			output = "{\"status\":\"success\",\"data\":\"" + newDoc + "\"}";
	  }
	  catch(Exception e)
	  {
		  output = "{\"status\":\"error\",\"data\":\"Error while updating the appointment\"}";
		  System.err.println(e.getMessage());
	  }
	  
	  return output;
  }
  
  public String deleteDoc(String appointmentId)
  {
	  String output ="";
	  
	  try
	  {
		  Connection con = connect();
		  
		  if(con == null)
		  {
			  return "Error while connecting to the database for deleting";
		  }
		  
		  //create a prepared statement
		  String query = "delete from appointment where appointmentId=?";
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		  
		  //binding values
		  preparedStmt.setInt(1, Integer.parseInt(appointmentId));
		  
		  //execute the statement
		  preparedStmt.execute();
		  con.close();
		  
		  String newDoc = readDoc();
		  output = "{\"status\":\"success\",\"data\":\"" + newDoc + "\"}";
	  }
	  catch(Exception e)
	  {
		  output = "{\"status\":\"error\",\"data\":\"Error while deleting the appointment\"}";
		  System.err.println(e.getMessage());
	  }
	  return output;
  }

}
