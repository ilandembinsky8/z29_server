<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	if(session.getAttribute("user") == null)
		response.sendRedirect("login.html");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<title>No bid Reason</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>

    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/sb-admin.css" rel="stylesheet"/>
    <link href="css/dashboard.css" rel="stylesheet"/>
    
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="css/bootstrap-multiselect.css" rel="stylesheet"/>
    <link href="css/no-bid-reason.css" rel="stylesheet"/>
     
</head>
<body>
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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu alert-dropdown">
                        <li>
                            <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">View All</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Admin <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
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
		            <li class="active">
		            	<a href="noBidReason"><i class="fa fa-dot-circle-o"></i> No Bid Reason</a>
		            </li>
		        </ul>
			</div>
        </nav>
       
		<div class="row">
	    	<div class="col-xs-12">
				<h3>Fill to show a tree</h3> 
			</div>
			<div class="col-xs-12">
			
				<form class="form-horizontal" role="form">	
			    	<div class="form-group">
			        	<label for="telnum" class="col-sm-2 control-label">Date</label>
			            <div class="col-xs-12 col-sm-10 col-lg-5 margin-bottom-6">
			            	<div class="input-group">
			            		<div class="input-group-addon">Reference</div>
			                    <input class="form-control" type="datetime-local" id="fromDate">
			                </div>
			            </div>
			            <div class="col-xs-12 col-sm-10 col-sm-offset-2 col-lg-5 col-lg-offset-0">
			            	<div class="input-group">
			                	<div class="input-group-addon">Compare To</div>
			                    <input class="form-control" type="datetime-local" id=toDate>
			                </div>
			            </div>
			        </div>
			            
			        <div class="form-group">
			        	<label for="firstname" class="col-sm-2 control-label">Exchange Name</label>         
			            <div class="col-sm-10">
			            	<select class="form-control" id="exSelect">
			                	<option>select</option>
				  			</select>
			            </div>               
			        </div>
                                           
                       <div class="form-group">
			        	<div class="col-sm-2" id="noBidReasonLabel"></div>
			            	<div class="col-sm-10">
			            		<div id="noBidReason"></div>
			            	</div>
                       </div>
                           
                       <div class="form-group">
			        	<div class="col-sm-2" id="noBidReasonLabel"></div>
			            <div class="col-sm-10">
			            	<div id="noBidReason"></div>
			            </div>
                       </div>
			        
			    </form>
		    </div>
		</div>
        <div id="tree-container"></div>   
    </div>

  	<script src="js/jquery.min.js"></script>
    <script src="js/dashboard.js"></script>
    <script src="js/bootstrap-multiselect.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/d3.v3.min.js"></script>
    <script src="js/tree.js"></script>
    <script src="js/noBidReasons.js"></script>
    
</body>
</html>