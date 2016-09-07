<%@page import="org.json.simple.JSONArray"%>
<%@page import="tsofen.course.db.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="tsofen.courses.test.*" %> 
 
<%
MyConnection con = new MyConnection();
JSONArray array=ApiFunctions.getQuery(con.getCon());
out.print(array);

%>
