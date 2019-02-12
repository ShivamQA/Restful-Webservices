/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatpoint.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.json.*;
import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

@Path("/hello")
public class Hello {
    // This method is called if HTML and XML is not requested  
	DBConnection db = new DBConnection();
	public static String Email = "";
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@FormParam("email") String email, @FormParam("pwd") String pwd ) throws URISyntaxException {
    	Email = email;
    	
    	try{  
    		Connection con = db.getConnection();  
    		Statement stmt=con.createStatement();  
    		
    		ResultSet rs=stmt.executeQuery("select Email,Password from AddUser");  
    		while(rs.next())  
    		{
    			if(email.equals(rs.getString(1)) && pwd.equals(rs.getString(2)))
    			{
    				URI location = new URI("http://localhost:8014/RestJersey/home.html");
    		    	return Response.seeOther(location).build(); 	
    			}
    			
    			
    			
    		}
    		db.closeConnection(); 
    		}catch(Exception e){ System.out.println(e);}  
    	
		URI location = new URI("http://localhost:8014/RestJersey/login.html");
    	return Response.seeOther(location).status(401).build();		
    	
        
    }
    @POST
    @Path("/signup")
    @Produces(MediaType.TEXT_PLAIN)
    public Response signup(@FormParam("uname") String name,@FormParam("email") String email, @FormParam("pwd") String pwd ) throws URISyntaxException {
    	try{  
    		Connection con = db.getConnection();
    		PreparedStatement ps=con.prepareStatement("insert into AddUser values(?,?,?)");  
    		
    		ps.setString(1,name);  
    		ps.setString(2,email);  
    		ps.setString(3,pwd);  
    		ps.executeUpdate();  	
    		
    		db.closeConnection();
    		}catch(Exception e){ System.out.println(e);}  
    		  
    	URI location = new URI("http://localhost:8014/RestJersey/index.html");
    	return Response.seeOther(location).build(); 
    	
        
    }
    @GET
    @Path("/viewall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewall() throws URISyntaxException {
    	String output = "";
    	JSONArray jArray = new JSONArray();
    	JSONObject jobj = new JSONObject();
        
    	try{  
    		Connection con = db.getConnection();
    		Statement stmt=con.createStatement();  
    		
    		//output="<html><body style="+"background-color:powderblue;"+">";
			/*
			 * while(rs.next()) { output += "<p>"+rs.getString(1) + " : " + rs.getString(2)
			 * + "</p>"; } output+="</body></html>"; db.closeConnection(); return output;
			 */
    		
    		ResultSet rs1=stmt.executeQuery("select DISTINCT Name from UserComments");
		ArrayList<String> name = new ArrayList<String>();
		
			  //output="<html><body style="+"background-color:powderblue;"+">";
			  while(rs1.next()) 
			  {  
	name.add(rs1.getString(1));	
	
			  }
			  for(int j=0;j<name.size();j++) {
			  ResultSet rs2 = stmt.executeQuery("Select Comments from UserComments where Name='"+name.get(j)+"'");
				  //ar.add(rs1.getString(1));
			  ArrayList<String> ar = new ArrayList<String>();
				  while(rs2.next())
				  {
					  
					  ar.add(rs2.getString(1));
				  }
				  jobj.put(name.get(j), ar);
				  
				  
			  }
			  jArray.put(jobj);
			  return Response.status(200).entity(jArray.toString()).build();
    		}catch(Exception e){ System.out.println(e);}  
    		  
//    	URI location = new URI("http://localhost:8014/RestJersey/index.html");
//    	return Response.seeOther(location).build(); 
    	
    	return null;
        
    }
    @GET
    @Path("/YourComments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewYourComments() throws URISyntaxException {
    	String output = "";
    	String name="";
    	JSONArray jArray = new JSONArray();
    	JSONObject jobj = new JSONObject();
        
    	
    	try{  
    		Connection con = db.getConnection();
    		Statement stmt=con.createStatement();
    			
    		String query = "select Name from AddUser where Email =?";
    		PreparedStatement ps = con.prepareStatement(query);
    		ps.setString(1,Email);
    		ResultSet rs = ps.executeQuery();
            
    		while(rs.next())  
    		{
    			
    			name = rs.getString(1);
    			
    		}
    	ArrayList<String> ar = new ArrayList<String>();
    		ResultSet rs1=stmt.executeQuery("select * from UserComments where Name='"+name+"'");
		
			  //output="<html><body style="+"background-color:powderblue;"+">";
			  while(rs1.next()) 
			  { 
				  //ar.add(rs1.getString(1));
				  ar.add(rs1.getString(2));
			}
			  jobj.put(name, ar);
			  jArray.put(jobj);
    		db.closeConnection();
    		return Response.status(200).entity(jArray.toString()).build();	
    		}catch(Exception e){ System.out.println(e);}  

    	return null;
    }
    @POST
    @Path("/AddComment")
    @Produces(MediaType.TEXT_PLAIN)
    public Response AddComment(@FormParam("comment") String comment ) throws URISyntaxException {
    	String name ="";
    	try{  
    		Connection con = db.getConnection();
    		String query = "select Name from AddUser where Email =?";
    		PreparedStatement ps = con.prepareStatement(query);
    		ps.setString(1,Email);
    		ResultSet rs = ps.executeQuery();
    	 		while(rs.next())  
    		{
    			name = rs.getString(1);
    		}
    		
    		
    		PreparedStatement ps1=con.prepareStatement("insert into UserComments values(?,?)");  
    		
    		ps1.setString(1,name);  
    		ps1.setString(2,comment);  
    		  
    		ps1.executeUpdate();  	
    		
    		db.closeConnection(); 
    		}catch(Exception e){ System.out.println(e);}  
    		  
    	URI location = new URI("http://localhost:8014/RestJersey/home.html");
    	return Response.seeOther(location).build(); 
    	
        
    }    

}
