<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	if(session.getAttribute("user") == null)
		response.sendRedirect("login.html");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<title>settings</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	
	<link href="img/favicon.ico" rel="icon" type="image/x-icon" />
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/sb-admin.css" rel="stylesheet"/>
    <link href="css/dashboard.css" rel="stylesheet"/>
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/profile.css" rel="stylesheet"/>
    
</head>
<body>

	<div class="top-message"></div>
	<div class="blackWindow"></div>
	<div class="loader"></div> 
	<div class="container">
		<!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="kpi">Admin <i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Admin <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li class="active">
                            <a href="settings.jsp"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="" id="btn_logOut"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <div class="collapse navbar-collapse navbar-ex1-collapse nav navbar-left top-nav">
		    	<ul class="nav navbar-nav">
		        	<li>
		            	<a href="kpi"><i class="fa fa-bar-chart"></i> KPI's</a>
		            </li>
		            <li>
		            	<a href="noBidReason"><i class="fa fa-dot-circle-o"></i> No Bid Reason</a>
		            </li>
		        </ul>
			</div>
        </nav>
        
        <form class="form-horizontal">
			<div class="form-group">
		    	<label for="email" class="col-sm-2 control-label">Email</label>
		    	<div class="col-sm-10">
		      		<input type="email" class="form-control" id="email" placeholder="Email">
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="pass" class="col-sm-2 control-label">Password</label>
		    	<div class="col-sm-10">
		      		<input type="password" class="form-control" id="pass" value="<%=session.getAttribute("pass")%>">
		    	</div>
		  	</div>
		  	<div class="form-group">
		  		<div class="col-sm-offset-2 col-sm-10">
		    		<button type="button" class="btn btn-primary">Save</button>
		    	</div>
		  	</div>
		  	<input style="display: none;" type="text" class="form-control" id="id" value="<%=session.getAttribute("user")%>">
		</form>
        
	</div>
	
	<script src="js/jquery.min.js"></script>
    <script src="js/dashboard.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/profile.js"></script>

</body>
</html>