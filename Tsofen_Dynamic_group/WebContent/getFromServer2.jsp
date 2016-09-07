<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="com.google.gson.Gson" %>
  <%@page import="tsofen.courses.test.*" %> 
 
<jsp:useBean id="con" class="tsofen.course.db.MyConnection" scope="session"></jsp:useBean>
<%
String index=request.getParameter("index");
Gson g=new Gson();
DataTestRetrive data=new DataTestRetrive();
JSONArray array=ApiFunctions.getQuery(con.getCon(), Integer.parseInt(index));
out.print(array);

%>